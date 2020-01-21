package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import lt.petuska.hazelcast.explorer.util.connectClass

val topicPage = connectClass<TopicPage, TopicPageProps>(
  mapStateToProps = { state ->
    theme = state.theme
    target = state.selectedTarget
    selectedTopic = state.topicPageState.selectedTopic
    subscribed = state.topicPageState.subscription != null
    messages = state.topicPageState.receivedMessages
  }
)