package lt.petuska.hazelcast.explorer.service.entity

import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.response.*
import io.ktor.http.*
import io.ktor.http.content.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import lt.petuska.hazelcast.explorer.service.*
import lt.petuska.hazelcast.explorer.util.*

object MapService : FetchService() {
  fun getAllKeys(map: MapDTO?) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}"
    httpClient.get<HttpResponse>(url)
  }

  fun getValue(map: MapDTO?, key: String?) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}/value/${key!!}"
    httpClient.get<HttpResponse>(url)
  }

  fun deleteValue(map: MapDTO?, key: String?) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}/value/${key!!}"
    httpClient.delete<HttpResponse>(url)
  }

  fun createValue(map: MapDTO?, key: String?, valueJson: String) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}/value/${key!!}"
    httpClient.post<HttpResponse>(url) {
      body = TextContent(valueJson,ContentType.Application.Json)
    }
  }

  fun replaceValue(map: MapDTO?, key: String?, valueJson: String) = promise {
    val url = "/api/entity/${map!!.environment}/${map.target}/map/${map.name}/value/${key!!}"
    httpClient.put<HttpResponse>(url) {
      body = TextContent(valueJson,ContentType.Application.Json)
    }
  }
}