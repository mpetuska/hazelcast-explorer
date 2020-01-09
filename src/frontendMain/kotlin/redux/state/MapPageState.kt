package lt.petuska.hazelcast.explorer.redux.state

import io.ktor.http.*
import kotlinext.js.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*

data class MapPageState(
    val selectedRestMethod: HttpMethod = HttpMethod.Get,
    val selectedMap: MapDTO? = null,
    val insertedKey: String? = null,
    val insertedJson: String? = null,

    val currentRequestTimestamp: Long? = null,
    val serverResponseStatus: HttpStatusCode? = null,
    val serverResponseJson: JsObject? = null
)