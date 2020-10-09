package lt.petuska.hazelcast.explorer.route.entity

import com.google.gson.Gson
import io.ktor.application.ApplicationCall
import io.ktor.features.NotFoundException
import io.ktor.util.getOrFail
import lt.petuska.hazelcast.explorer.domain.HzeConfig
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

internal val ApplicationCall.env
  get() = parameters.getOrFail("environment").let {
    val hzeConfig by kodein { application }.instance<HzeConfig>()
    hzeConfig.environments.find { e -> e.name == it } ?: throw NotFoundException("Environment [$it] not found")
  }

internal val ApplicationCall.target
  get() = parameters.getOrFail("target").let {
    env.targets.find { t -> t.name == it } ?: throw NotFoundException("Target [${env.name}][$it] not found")
  }

internal val ApplicationCall.prettyGson
  get() = run {
    val prettyGson by kodein { application }.instance<Gson>("pretty")
    prettyGson
  }
