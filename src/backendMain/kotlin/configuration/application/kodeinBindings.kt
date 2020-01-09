package lt.petuska.hazelcast.explorer.configuration.application

import com.google.gson.*
import kotlinx.serialization.json.*
import lt.petuska.hazelcast.explorer.configuration.domain.*
import lt.petuska.hazelcast.explorer.service.entity.map.*
import org.kodein.di.*
import org.kodein.di.generic.*

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
    }
  }
}