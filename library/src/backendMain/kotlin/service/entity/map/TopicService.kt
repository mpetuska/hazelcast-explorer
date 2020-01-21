package lt.petuska.hazelcast.explorer.service.entity.map

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.TargetDescriptor
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.TopicDescriptor
import lt.petuska.hazelcast.explorer.service.HazelcastClientService
import kotlin.reflect.full.cast

class TopicService<V : Any>(
  private val targetDescriptor: TargetDescriptor,
  private val topicDescriptor: TopicDescriptor<V>
) {
  init {
    if (targetDescriptor.topics?.contains(topicDescriptor) != true) {
      throw IllegalStateException("Target [${targetDescriptor.environment}][${targetDescriptor.name}] does not contain map [${topicDescriptor.name}]")
    }
  }
  
  fun subscribe(onMessage: suspend (V) -> Unit): suspend () -> Unit {
    val subId = topic.addMessageListener {
      GlobalScope.launch { onMessage(it.messageObject) }
    }
    return {
      topic.removeMessageListener(subId)
    }
  }
  
  private fun castValue(key: Any): V = topicDescriptor.valueType.cast(key)
  private val topic
    get() = HazelcastClientService.getClient(targetDescriptor).getTopic<V>(topicDescriptor.name)
  
  companion object {
    fun instanceTag(topicDescriptor: TopicDescriptor<*>) =
      "${topicDescriptor.environment}-${topicDescriptor.target}-${topicDescriptor.name}"
  }
}