package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import lt.petuska.hazelcast.explorer.component.StatelessComponent
import lt.petuska.hazelcast.explorer.component.common.selector.selector
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.synchronousButton
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.service.entity.TopicService
import lt.petuska.hazelcast.explorer.store
import react.RBuilder
import react.dom.div
import react.dom.form
import react.dom.textArea

class TopicPage(props: TopicPageProps) : StatelessComponent<TopicPageProps>(props) {
  override fun componentWillUnmount() {
    store.dispatch(HzeAction.SetTopicSubscription(null))
  }
  
  override fun RBuilder.render() {
    div("container") {
      form(classes = "form-inline justify-content-center mb-2") {
        topicSelector()
        synchronousButton {
          attrs {
            text = if (props.subscribed) "Unsubscribe" else "Subscribe"
            type = if (props.subscribed) BType.DANGER else BType.SUCCESS
            disabled = props.selectedTopic == null
            onClick = {
              if (props.subscribed) {
                store.dispatch(HzeAction.SetTopicSubscription(null))
              } else {
                val subscription = TopicService.subscribe(props.selectedTopic) {
                  store.dispatch(HzeAction.AddTopicMessage(it))
                }
                store.dispatch(HzeAction.SetTopicSubscription(subscription))
              }
              it()
            }
          }
        }
      }
      //TODO prettify
      props.messages.forEach {
        textArea {
          +it
        }
      }
    }
  }
  
  private fun RBuilder.topicSelector() = selector {
    attrs {
      disabled = props.subscribed
      classes = "m-1"
      required = true
      values = props.target?.topics?.map { it.displayName }?.toSet() ?: setOf()
      placeholderText = "Select topic..."
      selectedValue = props.target?.topics?.find { it == props.selectedTopic }.also {
        store.dispatch(HzeAction.SelectTopic(it))
      }?.displayName
      onSelectionChange = {
        store.dispatch(HzeAction.SelectTopic(props.target?.topics?.find { m -> it == m.displayName }))
      }
    }
  }
}