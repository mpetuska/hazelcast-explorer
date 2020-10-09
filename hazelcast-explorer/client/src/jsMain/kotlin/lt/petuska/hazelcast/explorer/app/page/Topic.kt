package lt.petuska.hazelcast.explorer.app.page

import dev.fritz2.binding.const
import dev.fritz2.dom.Tag
import dev.fritz2.dom.html.HtmlElements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lt.petuska.hazelcast.explorer.app.component.labeledPillBox
import lt.petuska.hazelcast.explorer.app.component.selector
import lt.petuska.hazelcast.explorer.app.router
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.store.SelectionStore

internal fun HtmlElements.topic(hidden: Flow<Boolean>) = div("container") {
  hidden.bindAttr("hidden")
  form("form-inline justify-content-center") {
    topicSelector()
    // synchronousButton {
    //   text = if (props.subscribed) "Unsubscribe" else "Subscribe"
    //   type = if (props.subscribed) BType.DANGER else BType.SUCCESS
    //   disabled = props.topic == null
    //   onClick = {
    //     if (props.subscribed) {
    //       store.dispatch(HzeAction.SetTopicSubscription(null))
    //       NotificationService.error("Unsubscribed to Topic [${props.topic?.displayName}]")
    //     } else {
    //       val subscription = TopicService.subscribe(props.environment!!, props.target!!, props.topic!!) {
    //         store.dispatch(HzeAction.AddTopicMessage(it))
    //         NotificationService.info("[${Date(Date.now()).let { d -> "${d.getHours()}:${d.getMinutes()}:${d.getSeconds()}" }}] Message received for Topic [${props.topic?.displayName}]")
    //       }
    //       store.dispatch(HzeAction.SetTopicSubscription(subscription))
    //       NotificationService.success("Subscribed to Topic [${props.topic?.displayName}]")
    //     }
    //     it()
    //   }
    // }
  }
  metadataPanel()
  responsePanel()
}

private fun Tag<*>.topicSelector() = selector(
  classes = "m-1",
  required = const(true),
  placeholder = "Select topic...",
  value = router.map { it.topic?.name },
  values = router.map { it.target?.topics?.map { t -> t.name to t.displayName } },
  onChange = SelectionStore.selectTopic
)

private fun HtmlElements.responsePanel() = div("border rounded m-1 mt-2 mb-2") {
  // attr("hidden", )
  // attrs.hidden = props.messages.isEmpty() && !props.subscribed
  // div("d-flex align-items-center p-2") {
  //   span { +"Messages" }
  // }
  // div("border-top overflow-auto") {
  //   props.messages.forEachIndexed { i, it ->
  //     div("border-top overflow-auto") {
  //       div("d-flex align-items-center p-2") {
  //         span { +"Message #${props.messages.size - i}" }
  //         div("d-flex-inline align-items-center ml-auto") {
  //           copyButton(JSON.stringify(it.second)) {
  //             NotificationService.info("Response copied to clipboard")
  //           }
  //           span("mr-2") {}
  //           span("mr-2") { +"Received at:" }
  //           span("badge badge-pill badge-${BType.INFO.typeName}") {
  //             +"${it.first.toTimeString()} ${it.first.toDateString()}"
  //           }
  //         }
  //       }
  //       JSONInput {
  //         attrs {
  //           key = props.theme.themeName
  //           placeholder = it.second
  //           locale = LocaleEN
  //           width = "100%"
  //           height = "100%"
  //           confirmGood = false
  //           reset = false
  //           colors = if (props.theme == Theme.DARK) IntelliJDarculaColors else IntelliJColors
  //           waitAfterKeyPress = 5000
  //           viewOnly = true
  //         }
  //       }
  //     }
  //   }
  // }
}

private fun HtmlElements.metadataPanel() = div("d-flex justify-content-center") {
  labeledPillBox(
    "Value Type:",
    type = router.map { if (it.topic?.readOnly == true) BType.WARNING else BType.INFO },
    value = router.map { it.topic?.valueType?.simpleName ?: "N/A" },
    hidden = router.map { it.topic?.valueType?.simpleName == null },
  )
}
