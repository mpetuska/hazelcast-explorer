package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import kotlinx.html.hidden
import kotlinx.html.title
import lt.petuska.hazelcast.explorer.component.StatelessComponent
import lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control.mapPageControl
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.types.jsonInput.IntelliJColors
import lt.petuska.hazelcast.explorer.types.jsonInput.IntelliJDarculaColors
import lt.petuska.hazelcast.explorer.types.jsonInput.JSONInput
import lt.petuska.hazelcast.explorer.types.jsonInput.LocaleEN
import lt.petuska.hazelcast.explorer.util.bType
import react.RBuilder
import react.dom.div
import react.dom.span
import react.key

class MapPage(props: MapPageProps) : StatelessComponent<MapPageProps>(props) {
  
  override fun RBuilder.render() {
    div("container") {
      mapPageControl {}
      responsePanel()
    }
  }
  
  private fun RBuilder.responsePanel() = div("border rounded m-1 mt-2 mb-2") {
    attrs.hidden = props.serverResponseStatus == null
    div("d-flex align-items-center p-2") {
      span { +"Response" }
      div("ml-auto") {
        span("mr-2") { +"Status:" }
        span("badge badge-pill badge-${props.serverResponseStatus.bType().typeName}") {
          props.serverResponseStatus?.description?.let { attrs.title = it }
          +"${props.serverResponseStatus?.value}"
        }
      }
    }
    div("border-top") {
      attrs.hidden = props.serverResponseJson == null
      JSONInput {
        attrs {
          key = props.theme.themeName
          placeholder = props.serverResponseJson
          locale = LocaleEN
          width = "100%"
          height = "100%"
          confirmGood = false
          reset = false
          colors = if (props.theme == Theme.DARK) IntelliJDarculaColors else IntelliJColors
          waitAfterKeyPress = 5000
          viewOnly = true
        }
      }
    }
  }
}
