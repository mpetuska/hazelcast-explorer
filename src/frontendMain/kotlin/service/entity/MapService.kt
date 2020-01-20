package lt.petuska.hazelcast.explorer.service.entity

import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpStatement
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.MapDTO
import lt.petuska.hazelcast.explorer.service.FetchService
import lt.petuska.hazelcast.explorer.util.promise

object MapService : FetchService() {
  fun getAllKeys(map: MapDTO?) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}"
    httpClient.get<HttpStatement>(url).execute()
  }
  
  fun getValue(map: MapDTO?, key: String?) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}/value/${key!!}"
    httpClient.get<HttpStatement>(url).execute()
  }
  
  fun deleteValue(map: MapDTO?, key: String?) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}/value/${key!!}"
    httpClient.delete<HttpStatement>(url).execute()
  }
  
  fun createValue(map: MapDTO?, key: String?, valueJson: String) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}/value/${key!!}"
    httpClient.post<HttpStatement>(url) {
      body = TextContent(valueJson, ContentType.Application.Json)
    }.execute()
  }
  
  fun replaceValue(map: MapDTO?, key: String?, valueJson: String) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}/value/${key!!}"
    httpClient.put<HttpStatement>(url) {
      body = TextContent(valueJson, ContentType.Application.Json)
    }.execute()
  }
}