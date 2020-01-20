package lt.petuska.hazelcast.explorer.service.entity

import io.ktor.client.features.websocket.webSocket
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.TopicDTO
import lt.petuska.hazelcast.explorer.service.FetchService

object TopicService : FetchService() {
  fun subscribe(topic: TopicDTO?, onMessage: (String) -> Unit): () -> Unit {
    val url = "/api/entity/${topic!!.environment}/${topic.target}/topic/${topic.name}"
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