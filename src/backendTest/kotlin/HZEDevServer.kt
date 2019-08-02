package lt.petuska.hazelcast.explorer

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import lt.petuska.hazelcast.explorer.configuration.domain.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.*
import org.slf4j.event.*

fun Application.devServer() {
  val hzeConfig = buildStubConfig()
  install(CallLogging) {
    level = Level.INFO
  }
  hazelcastExplorer(hzeConfig)
  routing {
    get("/test") {
      call.respond("OK")
    }
  }
}

fun buildStubConfig(startNodes: Boolean = false) = hazelcastExplorerConfig {
  environment("LOCAL", "Local") {
    val gridClient = LocalHazelcastProvider("local_grid").apply { if (startNodes) startNode() }
    target("M", "M Only", gridClient::getClient) {
      map("local_grid_map1", Long::class, String::class)
      map("local_grid_map1", Long::class, String::class)
    }
    val clusterClient = LocalHazelcastProvider("local_cluster").apply { if (startNodes) startNode() }
    target("T", "T Only", clusterClient::getClient) {
      topic("local_cluster_topic1", String::class)
      topic("local_cluster_topic2", String::class)
    }
  }
  environment("TEST", "Test") {
    val gridClient = LocalHazelcastProvider("test_grid").apply { if (startNodes) startNode() }
    target("MT", "M & T", gridClient::getClient) {
      map("test_grid_map1", Long::class, String::class)
      topic("test_grid_topic1", String::class)
    }
    val clusterClient = LocalHazelcastProvider("test_cluster").apply { if (startNodes) startNode() }
    target("NONE", "None", clusterClient::getClient) {
    }
  }
}