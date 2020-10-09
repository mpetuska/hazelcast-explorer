package lt.petuska.hazelcast.explorer.api

import dev.fritz2.remote.Request
import dev.fritz2.remote.remote
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lt.petuska.hazelcast.explorer.domain.HzeConfig
import lt.petuska.hazelcast.explorer.util.parseBody
import org.w3c.fetch.Response

internal abstract class API(private val url: String) {
  private val client: Request
    get() = remote("$BASE_URL/$url")
      .acceptJson()
      .contentType("application/json")

  protected suspend inline fun <reified R> executeRequest(
    url: String = "",
    body: Any? = null,
    urlParams: Map<String, Any?> = mapOf(),
    crossinline call: suspend Request.(String) -> Response
  ) = client.run {
    body?.let {
      body(Json.encodeToString(body))
    } ?: this
  }.call(
    url.let {
      urlParams.entries.fold(it) { acc, entry -> acc.replace("{${entry.key}}", "${entry.value ?: ""}") }
    }
  ).parseBody<R>()

  protected suspend inline fun <reified R> executeGet(
    url: String = "",
    urlParams: Map<String, Any> = mapOf(),
    body: Any? = null
  ) = executeRequest<R>(url, body, urlParams, Request::get)

  protected suspend inline fun <reified R> executePost(
    url: String = "",
    urlParams: Map<String, Any> = mapOf(),
    body: Any? = null
  ) = executeRequest<R>(url, body, urlParams, Request::post)

  protected suspend inline fun <reified R> executePut(
    url: String = "",
    urlParams: Map<String, Any> = mapOf(),
    body: Any? = null
  ) = executeRequest<R>(url, body, urlParams, Request::put)

  protected suspend inline fun <reified R> executeDelete(
    url: String = "",
    urlParams: Map<String, Any> = mapOf(),
    body: Any? = null
  ) = executeRequest<R>(url, body, urlParams, Request::delete)

  companion object {
    private val BASE_URL = "${window.location.origin}/api"
  }
}

internal object HZEConfigAPI : API("/meta/hzeConfig") {
  suspend fun get() = executeGet<HzeConfig>()
}

internal object MapAPI : API("/entity/{envName}/{tarName}/map/{mapName}") {
  private const val envKey = "envName"
  private const val tarKey = "tarName"
  private const val mapKey = "mapName"

  suspend fun getKeys(envName: String, tarName: String, mapName: String) = executeGet<Any>(
    urlParams = mapOf(
      envKey to envName,
      tarKey to tarName,
      mapKey to mapName,
    )
  )

  // TODO
  suspend fun getValues(envName: String, tarName: String, mapName: String) = executeGet<Any>(
    url = "/value",
    urlParams = mapOf(
      envKey to envName,
      tarKey to tarName,
      mapKey to mapName,
    )
  )

  suspend fun getValue(envName: String, tarName: String, mapName: String, key: String) = executeGet<Any>(
    url = "/value/{key}",
    urlParams = mapOf(
      envKey to envName,
      tarKey to tarName,
      mapKey to mapName,
      "key" to key,
    )
  )

  suspend fun postValue(envName: String, tarName: String, mapName: String, key: String, body: Any) = executePost<Any>(
    url = "/value/{key}",
    urlParams = mapOf(
      envKey to envName,
      tarKey to tarName,
      mapKey to mapName,
      "key" to key,
    ),
    body = body,
  )

  suspend fun putValue(envName: String, tarName: String, mapName: String, key: String, body: Any) = executePut<Any>(
    url = "/value/{key}",
    urlParams = mapOf(
      envKey to envName,
      tarKey to tarName,
      mapKey to mapName,
      "key" to key,
    ),
    body = body,
  )

  suspend fun deleteValue(envName: String, tarName: String, mapName: String, key: String) = executeDelete<Any>(
    url = "/value/{key}",
    urlParams = mapOf(
      envKey to envName,
      tarKey to tarName,
      mapKey to mapName,
      "key" to key,
    ),
  )
}
