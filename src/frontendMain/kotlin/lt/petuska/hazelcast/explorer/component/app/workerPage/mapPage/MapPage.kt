package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.component.app.*
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.*
import lt.petuska.hazelcast.explorer.domain.*
import react.*
import react.dom.*

class MapPage(props: MapPageProps) : StatelessComponent<MapPageProps>(props) {
  override fun RBuilder.render() {
    div("container") {
      synchronousButton {
        attrs {
          text = "Test Map"
          type = BType.DANGER
          cancelable = true
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