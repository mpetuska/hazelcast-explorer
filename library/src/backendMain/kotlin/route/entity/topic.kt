package lt.petuska.hazelcast.explorer.route.entity

import com.google.gson.Gson
import io.ktor.features.NotFoundException
import io.ktor.http.cio.websocket.Frame
import io.ktor.routing.Route
import io.ktor.util.getOrFail
import io.ktor.websocket.DefaultWebSocketServerSession
import io.ktor.websocket.application
import io.ktor.websocket.webSocket
import lt.petuska.hazelcast.explorer.configuration.domain.HzeConfig
import lt.petuska.hazelcast.explorer.service.entity.map.TopicService
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

private val DefaultWebSocketServerSession.env
  get() = call.parameters.getOrFail("environment").let {
    val hzeConfig by kodein { application }.instance<HzeConfig>()
    hzeConfig.environments.find { e -> e.name == it } ?: throw NotFoundException("Environment [$it] not found")
  }
private val DefaultWebSocketServerSession.target
  get() = call.parameters.getOrFail("target").let {
    env.targets.find { t -> t.name == it } ?: throw NotFoundException("Target [${env.name}][$it] not found")
  }
private val DefaultWebSocketServerSession.topic
  get() = topicName.let {
    target.topics?.find { m -> m.name == it }
      ?: throw NotFoundException("Topic [${env.name}][${target.name}][$it] not found")
  }
private val DefaultWebSocketServerSession.topicService
  get() = run {
    val tmp by kodein { application }.instance<TopicService<*>>(TopicService.instanceTag(topic))
    tmp
  }
private val DefaultWebSocketServerSession.topicName
  get() = call.parameters.getOrFail("topic-name")

private val DefaultWebSocketServerSession.prettyGson
  get() = run {
    val prettyGson by kodein { application }.instance<Gson>("pretty")
    prettyGson
  }

fun Route.topicRoute() = webSocket("topic/{topic-name}") {
  val unsubscribe = topicService.subscribe {
    outgoing.send(Frame.Text(prettyGson.toJson(it)))
  }
  for (frame in incoming) {
  }
  unsubscribe()
}
