package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.component.app.*
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.*
import lt.petuska.hazelcast.explorer.domain.*
import react.*
import react.dom.*

class TopicPage(props: TopicPageProps) : StatelessComponent<TopicPageProps>(props) {
  override fun RBuilder.render() {
    div("container") {
      synchronousButton {
        attrs {
          text = "Test"
          type = BType.SUCCESS
          onClick = {
            GlobalScope.launch {
              delay(50000)
              it(200, null)
            }
          }
          onCancel = {
            println("cancelled!")
          }
        }
      }
    }
  }
}