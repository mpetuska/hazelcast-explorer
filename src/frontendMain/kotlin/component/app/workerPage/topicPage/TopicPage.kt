package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.component.*
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import react.*
import react.dom.*

class TopicPage(props: TopicPageProps) : StatelessComponent<TopicPageProps>(props) {
  override fun RBuilder.render() {
    div("container") {
      synchronousButton {
        attrs {
          text = "Test Topic"
          type = BType.SUCCESS
          onClick = {
            GlobalScope.launch {
              delay(50000)
              it()
            }
          }
        }
      }
    }
  }
}