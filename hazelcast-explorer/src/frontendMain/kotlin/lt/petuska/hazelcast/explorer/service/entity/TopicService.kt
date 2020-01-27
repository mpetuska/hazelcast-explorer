package lt.petuska.hazelcast.explorer.service.entity

import io.ktor.client.features.websocket.webSocket
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import lt.petuska.hazelcast.explorer.service.FetchService

object TopicService : FetchService() {
  fun subscribe(environment: Environment, target: Target, topic: Topic, onMessage: (String) -> Unit): () -> Unit {
    val url = "/api/entity/${environment.name}/${target.name}/topic/${topic.name}"
    val job = GlobalScope.launch {
      httpClient.webSocket(path = url) {
        for (frame in incoming) {
          if (frame is Frame.Text) {
            onMessage(frame.readText())
          }
        }
      }
    }
    return {
      GlobalScope.launch {
        job.cancel()
      }
    }
  }
}