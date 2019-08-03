package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.component.app.*
import lt.petuska.hazelcast.explorer.component.common.selector.*
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*
import react.*
import react.dom.*

class MapPage(props: MapPageProps) : StatelessComponent<MapPageProps>(props) {
  override fun RBuilder.render() {
    div("container") {
      form(classes = "form-inline justify-content-center mb-2") {
        selector {
          attrs {
            required = true
            values = RestMethod.values().map { it.name }.toSet()
            selectedValue = props.selectedRestMethod.name
            onSelectionChange = {
              store.dispatch(HzeAction.SelectRestMethod(RestMethod.valueOf(it)))
            }
          }
        }
        selector {
          attrs {
            required = true
            values = props.target?.maps?.map { it.displayName }?.toSet() ?: setOf()
            placeholderText = "Select map..."
            selectedValue = props.selectedMap?.displayName
            onSelectionChange = {
              store.dispatch(HzeAction.SelectMap(props.target?.maps?.find { m -> it == m.displayName }))
            }
          }
        }
      }
    }
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
