package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import kotlinx.html.hidden
import lt.petuska.hazelcast.explorer.component.StatelessComponent
import lt.petuska.hazelcast.explorer.component.common.copyButton
import lt.petuska.hazelcast.explorer.component.common.labeledPillBox
import lt.petuska.hazelcast.explorer.component.common.selector.selector
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.synchronousButton
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.service.entity.TopicService
import lt.petuska.hazelcast.explorer.service.util.NotificationService
import lt.petuska.hazelcast.explorer.store
import lt.petuska.hazelcast.explorer.types.jsonInput.IntelliJColors
import lt.petuska.hazelcast.explorer.types.jsonInput.IntelliJDarculaColors
import lt.petuska.hazelcast.explorer.types.jsonInput.JSONInput
import lt.petuska.hazelcast.explorer.types.jsonInput.LocaleEN
import lt.petuska.hazelcast.explorer.types.virtualList.VirtualList
import react.RBuilder
import react.dom.div
import react.dom.form
import react.dom.span
import react.key
import kotlin.js.Date

class TopicPage(props: TopicPageProps) : StatelessComponent<TopicPageProps>(props) {
  override fun componentWillUnmount() {
    store.dispatch(HzeAction.SetTopicSubscription(null))
  }
  
  override fun RBuilder.render() {
    div("container") {
      form(classes = "form-inline justify-content-center") {
        topicSelector()
        synchronousButton {
          attrs {
            text = if (props.subscribed) "Unsubscribe" else "Subscribe"
            type = if (props.subscribed) BType.DANGER else BType.SUCCESS
            disabled = props.topic == null
            onClick = {
              if (props.subscribed) {
                store.dispatch(HzeAction.SetTopicSubscription(null))
                NotificationService.error("Unsubscribed to Topic [${props.topic?.displayName}]")
              } else {
                val subscription = TopicService.subscribe(props.environment!!, props.target!!, props.topic!!) {
                  store.dispatch(HzeAction.AddTopicMessage(it))
                  NotificationService.info("[${Date(Date.now()).let { d -> "${d.getHours()}:${d.getMinutes()}:${d.getSeconds()}" }}] Message received for Topic [${props.topic?.displayName}]")
                }
                store.dispatch(HzeAction.SetTopicSubscription(subscription))
                NotificationService.success("Subscribed to Topic [${props.topic?.displayName}]")
              }
              it()
            }
          }
        }
      }
      metadataPanel()
      responsePanel()
    }
  }
  
  private fun RBuilder.topicSelector() = selector {
    attrs {
      disabled = props.subscribed
      classes = "m-1"
      required = true
      values = props.target?.topics?.map { it.displayName }?.toSet() ?: setOf()
      placeholderText = "Select topic..."
      selectedValue = props.topic?.displayName
      onSelectionChange = {
        store.dispatch(HzeAction.SelectTopic(props.target?.topics?.find { m -> it == m.displayName }))
      }
    }
  }
  
  private fun RBuilder.responsePanel() = div("border rounded m-1 mt-2 mb-2") {
    attrs.hidden = props.messages.isEmpty() && !props.subscribed
    div("d-flex align-items-center p-2") {
      span { +"Messages" }
    }
    div("border-top overflow-auto") {
      props.messages.forEachIndexed { i, it ->
        div("border-top overflow-auto") {
          div("d-flex align-items-center p-2") {
            span { +"Message #${props.messages.size - i}" }
            div("d-flex-inline align-items-center ml-auto") {
              copyButton(JSON.stringify(it.second)) {
                NotificationService.info("Response copied to clipboard")
              }
              span("mr-2") {}
              span("mr-2") { +"Received at:" }
              span("badge badge-pill badge-${BType.INFO.typeName}") {
                +"${it.first.toTimeString()} ${it.first.toDateString()}"
              }
            }
          }
          JSONInput {
            attrs {
              key = props.theme.themeName
              placeholder = it.second
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
  }
  
  private fun RBuilder.metadataPanel() = props.topic?.valueType?.simpleName?.let { valueType ->
    div("d-flex justify-content-center") {
      labeledPillBox("Value Type:", if (props.topic?.readOnly == true) BType.WARNING else BType.INFO, valueType)
    }
  }
}