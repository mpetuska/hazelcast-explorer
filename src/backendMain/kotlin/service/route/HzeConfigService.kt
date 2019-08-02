package service.route

import domain.route.*
import lt.petuska.hazelcast.explorer.configuration.domain.*
import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.service.route.*

class HzeConfigService constructor(_hzeConfig: HzeConfig) : RouteService<HzeConfigDTO>(HzeConfigRoute) {
  private val hzeConfig = _hzeConfig.toDTO()

  override val onGet = { _: String ->
    hzeConfig
  }
}