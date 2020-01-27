package lt.petuska.hazelcast.explorer.service.entity

import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpStatement
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.service.FetchService
import lt.petuska.hazelcast.explorer.util.promise

object MapService : FetchService() {
  fun getAllKeys(environment: Environment, target: Target, map: Map) = promise {
    val url = "/api/entity/${environment.name}/${target.name}/map/${map.name}"
    httpClient.get<HttpStatement>(url).execute()
  }
  
  fun getValue(environment: Environment, target: Target, map: Map, key: String?) = promise {
    val url = "/api/entity/${environment.name}/${target.name}/map/${map.name}/value/${key!!}"
    httpClient.get<HttpStatement>(url).execute()
  }
  
  fun deleteValue(environment: Environment, target: Target, map: Map, key: String?) = promise {
    val url = "/api/entity/${environment.name}/${target.name}/map/${map.name}/value/${key!!}"
    httpClient.delete<HttpStatement>(url).execute()
  }
  
  fun createValue(environment: Environment, target: Target, map: Map, key: String?, valueJson: String) = promise {
    val url = "/api/entity/${environment.name}/${target.name}/map/${map.name}/value/${key!!}"
    httpClient.post<HttpStatement>(url) {
      body = TextContent(valueJson, ContentType.Application.Json)
    }.execute()
  }
  
  fun replaceValue(environment: Environment, target: Target, map: Map, key: String?, valueJson: String) = promise {
    val url = "/api/entity/${environment.name}/${target.name}/map/${map.name}/value/${key!!}"
    httpClient.put<HttpStatement>(url) {
      body = TextContent(valueJson, ContentType.Application.Json)
    }.execute()
  }
}