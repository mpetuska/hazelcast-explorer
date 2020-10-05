package lt.petuska.hazelcast.explorer.api

import dev.fritz2.remote.remote
import kotlinx.browser.window

internal val hzeConfigAPI = remote("${window.location.origin}/api/meta/hzeConfig")
  .acceptJson()
  .contentType("application/json")
