package lt.petuska.hazelcast.explorer.route.entity

import io.ktor.features.NotFoundException
import io.ktor.http.cio.websocket.Frame
import io.ktor.routing.Route
import io.ktor.util.getOrFail
import io.ktor.websocket.DefaultWebSocketServerSession
import io.ktor.websocket.application
import io.ktor.websocket.webSocket
import lt.petuska.hazelcast.explorer.service.entity.map.TopicService
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

private val DefaultWebSocketServerSession.topic
  get() = topicName.let {
    call.target.topics?.find { m -> m.name == it }
      ?: throw NotFoundException("Topic [${call.env.name}][${call.target.name}][$it] not found")
  }
private val DefaultWebSocketServerSession.topicService
  get() = run {
    val tmp by kodein { application }.instance<TopicService>(topic.id)
    tmp
  }
private val DefaultWebSocketServerSession.topicName
  get() = call.parameters.getOrFail("topic-name")

internal fun Route.topicRoute() = webSocket("topic/{topic-name}") {
  val unsubscribe = topicService.subscribe {
    outgoing.send(Frame.Text(call.prettyGson.toJson(it)))
  }
  @Suppress("ControlFlowWithEmptyBody")
  for (frame in incoming) {
  }
  unsubscribe()
}
