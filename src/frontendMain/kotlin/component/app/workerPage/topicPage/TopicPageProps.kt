package lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage

import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.TopicDTO
import react.RProps

interface TopicPageProps : RProps {
  var target: TargetDTO?
  var selectedTopic: TopicDTO?
  var subscribed: Boolean
  var messages: List<String>
}