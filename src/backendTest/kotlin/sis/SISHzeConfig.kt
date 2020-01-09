package lt.petuska.hazelcast.explorer.sis

import lt.petuska.hazelcast.explorer.dsl.*
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.*
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.*
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.entity.*
import tv.sis.channelautomation.common.capgrid.util.*
import tv.sis.sismodel.constants.*
import tv.sis.tools.grid.explorer.hazelcast.*

val sisHzeConfig = hazelcastExplorerConfig {
  environment(
      name = "LOCAL",
      displayName = "Local",
      envKey = "development",
      local = true,
      dataGrid = true,
      capGrid = true,
      traderGrid = true
  )
  environment(
      name = "DEV",
      displayName = "Development",
      envKey = "devenv",
      dataGrid = true,
      capGrid = true,
      traderGrid = true
  )
  environment(
      name = "QA",
      displayName = "QA",
      envKey = "qa",
      dataGrid = true,
      capGrid = true,
      traderGrid = true
  )
  environment(
      name = "QA_LL",
      displayName = "QA Livelike",
      envKey = "qa_livelike",
      dataGrid = true,
      capGrid = true
  )
  environment(
      name = "UAT",
      displayName = "UAT",
      envKey = "uat",
      dataGrid = true,
      traderGrid = true
  )
  environment(
      name = "UAT_LL",
      displayName = "UAT Livelike",
      envKey = "livelike",
      dataGrid = true,
      capGrid = true
  )
  environment(
      name = "PROD",
      displayName = "Production",
      envKey = "production",
      dataGrid = true,
      capGrid = true,
      traderGrid = true,
      production = true
  )
}

private fun HazelcastExplorerConfigBuilder.environment(name: String, displayName: String = name, envKey: String, dataGrid: Boolean = false, capGrid: Boolean = false, traderGrid: Boolean = false, local: Boolean = false, production: Boolean = false) {
  environment(name = name, displayName = displayName, local = local, production = production) {
    if (dataGrid) {
      target(name = "DATA_GRID", displayName = "Data Grid", client = { DataGridClientBuilder.build(envKey) }) {
        SDMaps.values().forEach {
          map(it.mapName, it.keyType.kotlin, it.valueType.kotlin)
        }
        SDTopics.values().forEach {
          topic(it.topicName, it.valueType.kotlin)
        }
      }
    }
    if (capGrid) {
      target(name = "CAP_GRID", displayName = "CAP Grid", client = { CapGridClientBuilder.build(envKey) }) {
        CapMaps.values().forEach {
          map(it.mapName, it.keyType.kotlin, it.valueType.kotlin)
        }
      }
    }
    if (traderGrid) {
      target(name = "TRADER_GRID", displayName = "Trader Grid", client = { TraderGridClientBuilder.build(envKey) }) {
        TraderGridClientBuilder.TraderGridMaps.values().forEach {
          map(it.mapName, it.keyType.kotlin, it.valueType.kotlin)
        }
      }
    }
  }
}