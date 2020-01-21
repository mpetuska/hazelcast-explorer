package lt.petuska.hazelcast.explorer.redux.state

import kotlinext.js.JsObject
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.TopicDTO
import kotlin.js.Date

data class TopicPageState(
  val selectedTopic: TopicDTO? = null,
  val subscription: (() -> Unit)? = null,
  val receivedMessages: List<Pair<Date, JsObject>> = mutableListOf()
)