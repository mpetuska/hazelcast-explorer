package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import kotlinext.js.JsObject
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import react.RProps
import kotlin.js.Date

interface TopicPageProps : RProps {
  var theme: Theme
  var environment: Environment?
  var target: Target?
  var topic: Topic?
  var subscribed: Boolean
  var messages: List<Pair<Date, JsObject>>
}