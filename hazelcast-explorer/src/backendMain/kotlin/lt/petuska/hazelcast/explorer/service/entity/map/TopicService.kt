package lt.petuska.hazelcast.explorer.service.entity.map

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import lt.petuska.hazelcast.explorer.service.HazelcastClientService
import kotlin.reflect.full.cast

internal class TopicService(
  private val target: Target,
  private val topicDescriptor: Topic
) {
  init {
    if (target.topics?.contains<Any>(topicDescriptor) != true) {
      throw IllegalStateException("Target ${target.id} does not contain topic [${topicDescriptor.name}]")
    }
  }
  
  fun subscribe(onMessage: suspend (Any) -> Unit): suspend () -> Unit {
    val subId = topic.addMessageListener {
      GlobalScope.launch { onMessage(it.messageObject) }
    }
    return {
      topic.removeMessageListener(subId)
    }
  }
  
  private fun castValue(key: Any): Any = topicDescriptor.valueType.cast(key)
  private val topic
    get() = HazelcastClientService.getClient(target).getTopic<Any>(topicDescriptor.name)
}