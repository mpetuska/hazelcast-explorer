package lt.petuska.hazelcast.explorer.route.entity

import com.google.gson.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import lt.petuska.hazelcast.explorer.configuration.domain.*
import lt.petuska.hazelcast.explorer.service.entity.map.*
import org.kodein.di.generic.*
import org.kodein.di.ktor.*
import java.util.zip.*

private val PipelineContext<Unit, ApplicationCall>.env
  get() = call.parameters.getOrFail("environment").let {
    val hzeConfig by kodein().instance<HzeConfig>()
    hzeConfig.environments.find { e -> e.name == it } ?: throw NotFoundException("Environment [$it] not found")
  }
private val PipelineContext<Unit, ApplicationCall>.target
  get() = call.parameters.getOrFail("target").let {
    env.targets.find { t -> t.name == it } ?: throw NotFoundException("Target [${env.name}][$it] not found")
  }
private val PipelineContext<Unit, ApplicationCall>.map
  get() = call.parameters.getOrFail("map-name").let {
    target.maps?.find { m -> m.name == it }
        ?: throw NotFoundException("Map [${env.name}][${target.name}][$it] not found")
  }
private val PipelineContext<Unit, ApplicationCall>.mapService
  get() = run {
    val tmp by kodein().instance<MapService<*, *>>(MapService.instanceTag(map))
    tmp
  }
private val PipelineContext<Unit, ApplicationCall>.mapEntityId
  get() = call.parameters.getOrFail("map-entity-id").let {
    try {
      prettyGson.fromJson(it, map.keyType.java)
    } catch (e: Exception) {
      throw BadRequestException("Invalid key type. Expected: ${map.keyType.simpleName}")
    }
  }
private val PipelineContext<Unit, ApplicationCall>.prettyGson
  get() = run {
    val prettyGson by kodein().instance<Gson>("pretty")
    prettyGson
  }

fun Route.mapRoute() = route("map/{map-name}") {

  get {
    call.respond(mapService.keys)
  }
  route("value") {
    get {
      val keys = produce(Dispatchers.IO) {
        mapService.keys.forEach { send(it) }
      }
      call.response.header(
          HttpHeaders.ContentDisposition,
          ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "${map.name}.zip").toString()
      )
      call.respondOutputStream {
        val zos = ZipOutputStream(this@respondOutputStream)

        launch(Dispatchers.IO) {
          for (key in keys) {
            try {
              val value = mapService[key]
              val entry = ZipEntry("$key.json")
              val bytes = prettyGson.toJson(value).toByteArray()
              entry.size = bytes.size.toLong()
              zos.putNextEntry(entry)
              zos.write(bytes)
              zos.closeEntry()
            } catch (e: Exception) {
              e.printStackTrace()
            }
          }
          zos.finish()
        }.join()
      }
    }
    route("{map-entity-id}") {
      get {
        mapService[mapEntityId]?.let { entity ->
          call.respond(entity)
        } ?: throw NotFoundException("Map Entity [${env.name}][${target.name}][${map.name}][$mapEntityId] not found")
      }
      post {
        mapService[mapEntityId]?.let {
          throw BadRequestException("Map Entity [${env.name}][${target.name}][${map.name}][$mapEntityId] already exists. Use PUT to replace it.")
        } ?: run {
          val entity = call.receive(map.valueType)
          mapService.create(mapEntityId, entity)
          call.respond(HttpStatusCode.Created)
        }
      }
      put {
        mapService[mapEntityId]?.let {
          val entity = call.receive(map.valueType)
          mapService.replace(mapEntityId, entity)
          call.respond(HttpStatusCode.NoContent)
        } ?: throw BadRequestException("Map Entity [${env.name}][${target.name}][${map.name}][$mapEntityId] does not exist. Use POST to create it.")
      }
      delete {
        mapService[mapEntityId]?.let {
          mapService.delete(mapEntityId)
          call.respond(HttpStatusCode.NoContent)
        } ?: throw NotFoundException("Map Entity [${env.name}][${target.name}][${map.name}][$mapEntityId] not found")
      }
    }
  }
}
