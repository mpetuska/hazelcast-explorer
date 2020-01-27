package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import lt.petuska.hazelcast.explorer.util.connectClass

val topicPage = connectClass<TopicPage, TopicPageProps>(
  mapStateToProps = { state ->
    theme = state.theme
    environment = state.environment
    target = state.target
    topic = state.topic
    subscribed = state.topicSubscription != null
    messages = state.topicMessages
  }
)