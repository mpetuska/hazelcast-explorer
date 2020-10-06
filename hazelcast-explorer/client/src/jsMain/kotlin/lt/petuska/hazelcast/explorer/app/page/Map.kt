package lt.petuska.hazelcast.explorer.app.page

import dev.fritz2.dom.html.HtmlElements
import kotlinx.coroutines.flow.Flow
import lt.petuska.hazelcast.explorer.app.component.mapPageControl

internal fun HtmlElements.map(hidden: Flow<Boolean>) = div("container") {
  hidden.bindAttr("hidden")

  mapPageControl()
  responsePanel()
}

private fun HtmlElements.responsePanel() = div("border rounded m-1 mt-2 mb-2") {
  // attrs.hidden = props.serverResponseStatus == null
  // div("d-flex align-items-center p-2") {
  //   span { +"Response" }
  //   div("d-flex-inline align-items-center ml-auto") {
  //     props.serverResponseJson?.let {
  //       copyButton(JSON.stringify(it)) {
  //         NotificationService.info("Response copied to clipboard")
  //       }
  //       span("mr-2") {}
  //     }
  //     span("mr-2") { +"Status:" }
  //     span("badge badge-pill badge-${props.serverResponseStatus.bType().typeName}") {
  //       props.serverResponseStatus?.description?.let { attrs.title = it }
  //       +"${props.serverResponseStatus?.value}"
  //     }
  //   }
  // }
  // div("border-top") {
  //   attrs.hidden = props.serverResponseJson == null
  //   JSONInput {
  //     attrs {
  //       key = props.theme.themeName
  //       placeholder = props.serverResponseJson
  //       locale = LocaleEN
  //       width = "100%"
  //       height = "100%"
  //       confirmGood = false
  //       reset = false
  //       colors = if (props.theme == Theme.DARK) IntelliJDarculaColors else IntelliJColors
  //       waitAfterKeyPress = 5000
  //       viewOnly = true
  //     }
  //   }
  // }
}
