package lt.petuska.hazelcast.explorer.public.dsl.config.environment.target

import com.hazelcast.core.HazelcastInstance
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import lt.petuska.hazelcast.explorer.public.dsl.HzeDsl
import lt.petuska.hazelcast.explorer.public.dsl.config.environment.EnvironmentBuilder

class TargetBuilder(
  environmentId: String,
  val name: String,
  val client: () -> HazelcastInstance,
  val displayName: String,
  val readOnly: Boolean
) {
  val id = "$environmentId[$name]"
  private val maps = mutableListOf<Map>()
  private val topics = mutableListOf<Topic>()
  
  fun addMap(map: Map) = maps.add(map)
  fun addTopic(topic: Topic) = topics.add(topic)
  
  fun build() = Target(id, name, displayName, readOnly, maps, topics, client)
}

@HzeDsl
fun EnvironmentBuilder.target(
  name: String,
  client: () -> HazelcastInstance,
  displayName: String = name,
  readOnly: Boolean = this.readOnly,
  builder: TargetBuilder.() -> Unit
): Target {
  val factory = TargetBuilder(id, name, client, displayName, readOnly)
  factory.builder()
  return factory.build().also { addTarget(it) }
}