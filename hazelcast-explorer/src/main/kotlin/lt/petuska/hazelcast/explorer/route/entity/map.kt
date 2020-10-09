package lt.petuska.hazelcast.explorer.route.entity

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.features.BadRequestException
import io.ktor.features.NotFoundException
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondOutputStream
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import io.ktor.util.getOrFail
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import lt.petuska.hazelcast.explorer.service.entity.map.MapService
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

private val PipelineContext<Unit, ApplicationCall>.map
  get() = call.parameters.getOrFail("map-name").let {
    call.target.maps?.find { m -> m.name == it }
      ?: throw NotFoundException("Map [${call.env.name}][${call.target.name}][$it] not found")
  }
private val PipelineContext<Unit, ApplicationCall>.mapService
  get() = run {
    val tmp by kodein().instance<MapService>(map.id)
    tmp
  }
private val PipelineContext<Unit, ApplicationCall>.mapEntityId
  get() = call.parameters.getOrFail("map-entity-id").let {
    try {
      call.prettyGson.fromJson(it, map.keyType.java)
    } catch (e: Exception) {
      throw BadRequestException("Invalid key type. Expected: ${map.keyType.simpleName}")
    }
  }

private fun PipelineContext<Unit, ApplicationCall>.checkWriteAccess() {
  if (map.readOnly || call.target.readOnly || call.env.readOnly) {
    throw BadRequestException("Map [${call.env.name}][${call.target.name}][${map.name}] is read-only. Any modifications to data are forbidden!")
  }
}

internal fun Route.mapRoute() = route("map/{map-name}") {

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
        ContentDisposition.Attachment.withParameter(
          ContentDisposition.Parameters.FileName,
          "${map.name}.zip"
        ).toString()
      )
      call.respondOutputStream {
        val zos = ZipOutputStream(this@respondOutputStream)

        launch(Dispatchers.IO) {
          for (key in keys) {
            try {
              val value = mapService[key]
              val entry = ZipEntry("$key.json")
              val bytes = call.prettyGson.toJson(value).toByteArray()
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
        }
          ?: throw NotFoundException("Map Entity [${call.env.name}][${call.target.name}][${map.name}][$mapEntityId] not found")
      }
      post {
        checkWriteAccess()
        mapService[mapEntityId]?.let {
          throw BadRequestException("Map Entity [${call.env.name}][${call.target.name}][${map.name}][$mapEntityId] already exists. Use PUT to replace it.")
        } ?: run {
          val entity = call.receive(map.valueType)
          mapService.create(mapEntityId, entity)
          call.respond(HttpStatusCode.Created)
        }
      }
      put {
        checkWriteAccess()
        mapService[mapEntityId]?.let {
          val entity = call.receive(map.valueType)
          mapService.replace(mapEntityId, entity)
          call.respond(HttpStatusCode.NoContent)
        }
          ?: throw BadRequestException("Map Entity [${call.env.name}][${call.target.name}][${map.name}][$mapEntityId] does not exist. Use POST to create it.")
      }
      delete {
        checkWriteAccess()
        mapService[mapEntityId]?.let {
          mapService.delete(mapEntityId)
          call.respond(HttpStatusCode.NoContent)
        }
          ?: throw NotFoundException("Map Entity [${call.env.name}][${call.target.name}][${map.name}][$mapEntityId] not found")
      }
    }
  }
}
