package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import kotlinext.js.JsObject
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.TopicDTO
import react.RProps
import kotlin.js.Date

interface TopicPageProps : RProps {
  var theme: Theme
  var target: TargetDTO?
  var selectedTopic: TopicDTO?
  var subscribed: Boolean
  var messages: List<Pair<Date, JsObject>>
}