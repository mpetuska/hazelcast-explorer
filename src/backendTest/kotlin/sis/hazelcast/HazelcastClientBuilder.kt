package tv.sis.tools.grid.explorer.hazelcast

import com.hazelcast.core.HazelcastInstance

interface HazelcastClientBuilder {
  fun build(environment: String): HazelcastInstance
  fun getMapKeyValueMapping(): Map<String, Pair<Class<*>, Class<*>>>
  fun getTopicValueMapping(): Map<String, Class<*>>
}