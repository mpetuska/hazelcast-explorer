package lt.petuska.hazelcast.explorer.configuration.app

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.serialization.json.Json
import lt.petuska.hazelcast.explorer.domain.HzeConfig
import lt.petuska.hazelcast.explorer.service.entity.map.MapService
import lt.petuska.hazelcast.explorer.service.entity.map.TopicService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

internal fun Kodein.Builder.registerBindings(hzeConfig: HzeConfig) {
  bind() from singleton { hzeConfig }
  bind() from singleton { Json }
  bind() from singleton { Gson() }
  bind("pretty") from singleton { GsonBuilder().setPrettyPrinting().create() }

  hzeConfig.environments.forEach { env ->
    env.targets.forEach { target ->
      target.maps?.forEach { map ->
        bind(map.id) from singleton { MapService(target, map) }
      }
      target.topics?.forEach { topic ->
        bind(topic.id) from singleton { TopicService(target, topic) }
      }
    }
  }
}
