package lt.petuska.hazelcast.explorer.redux.state

import lt.petuska.hazelcast.explorer.domain.environment.target.entity.TopicDTO

data class TopicPageState(
  val selectedTopic: TopicDTO? = null,
  val subscription: (() -> Unit)? = null,
  val receivedMessages: List<String> = mutableListOf()
)