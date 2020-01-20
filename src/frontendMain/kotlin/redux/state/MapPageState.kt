package lt.petuska.hazelcast.explorer.redux.state

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinext.js.JsObject
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.MapDTO

data class MapPageState(
  val selectedRestMethod: HttpMethod = HttpMethod.Get,
  val selectedMap: MapDTO? = null,
  val insertedKey: String? = null,
  val insertedJson: String? = null,
  
  val currentRequestTimestamp: Long? = null,
  val serverResponseStatus: HttpStatusCode? = null,
  val serverResponseJson: JsObject? = null
)