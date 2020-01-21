package lt.petuska.hazelcast.explorer.configuration.application

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.serialization.json.Json
import lt.petuska.hazelcast.explorer.configuration.domain.HzeConfig
import lt.petuska.hazelcast.explorer.service.entity.map.MapService
import lt.petuska.hazelcast.explorer.service.entity.map.TopicService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun Kodein.Builder.registerBindings(hzeConfig: HzeConfig) {
  bind() from singleton { hzeConfig }
  bind() from singleton { Json.nonstrict }
  bind() from singleton { Gson() }
  bind("pretty") from singleton { GsonBuilder().setPrettyPrinting().create() }
  
  hzeConfig.environments.forEach { env ->
    env.targets.forEach { tar ->
      tar.maps?.forEach { map ->
        bind(MapService.instanceTag(map)) from singleton { MapService(tar, map) }
      }
      tar.topics?.forEach { topic ->
        bind(TopicService.instanceTag(topic)) from singleton { TopicService(tar, topic) }
      }
    }
  }
}