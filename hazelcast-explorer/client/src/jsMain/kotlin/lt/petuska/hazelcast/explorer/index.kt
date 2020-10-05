package lt.petuska.hazelcast.explorer

import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount
import dev.fritz2.remote.getBody
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import lt.petuska.hazelcast.explorer.api.hzeConfigAPI
import lt.petuska.hazelcast.explorer.app.app
import lt.petuska.hazelcast.explorer.domain.HzeConfig
import lt.petuska.hazelcast.explorer.util.require

internal lateinit var config: HzeConfig private set

private fun HzeConfig.trim() = copy(
  environments = environments.map { env ->
    val targets = env.targets.filter { tar -> !tar.maps.isNullOrEmpty() || !tar.topics.isNullOrEmpty() }
    env.copy(targets = targets)
  }.filter { env -> env.targets.isNotEmpty() }
)

internal fun main() {
  require("bootstrap")
  require("bootstrap/dist/css/bootstrap.min.css")

  GlobalScope.launch {
    val res = hzeConfigAPI.get().getBody()
    config = Json.decodeFromString<HzeConfig>(res).trim()
    render {
      app()
    }.mount("root")
  }
}
