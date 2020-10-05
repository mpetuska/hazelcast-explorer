package lt.petuska.hazelcast.explorer.dsl

import lt.petuska.hazelcast.explorer.domain.HazelcastClient
import lt.petuska.hazelcast.explorer.domain.Target
import lt.petuska.hazelcast.explorer.domain.entity.Map
import lt.petuska.hazelcast.explorer.domain.entity.Topic

public class TargetBuilder(
  environmentId: String,
  public val name: String,
  public val client: () -> HazelcastClient,
  public val displayName: String,
  public val readOnly: Boolean
) {
  public val id: String = "$environmentId[$name]"
  private val maps = mutableListOf<Map>()
  private val topics = mutableListOf<Topic>()

  public fun addMap(map: Map): Boolean = maps.add(map)
  public fun addTopic(topic: Topic): Boolean = topics.add(topic)

  public fun build(): Target = Target(id, name, displayName, readOnly, maps, topics, client)
}

@HzeDsl
public fun EnvironmentBuilder.target(
  name: String,
  client: () -> HazelcastClient,
  displayName: String = name,
  readOnly: Boolean = this.readOnly,
  builder: TargetBuilder.() -> Unit
): Target {
  val factory = TargetBuilder(id, name, client, displayName, readOnly)
  factory.builder()
  return factory.build().also { addTarget(it) }
}
