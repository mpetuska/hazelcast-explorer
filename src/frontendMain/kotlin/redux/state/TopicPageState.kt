package lt.petuska.hazelcast.explorer.redux.state

import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*

data class TopicPageState(
    val selectedTopic: TopicDTO? = null,
    val subscribedTopic: TopicDTO? = null,
    val receivedMessages: List<String> = mutableListOf()
)