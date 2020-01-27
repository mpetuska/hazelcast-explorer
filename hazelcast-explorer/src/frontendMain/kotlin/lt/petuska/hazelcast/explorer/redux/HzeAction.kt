package lt.petuska.hazelcast.explorer.redux

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import redux.RAction

sealed class HzeAction : RAction {
  data class SetTheme(val theme: Theme) : HzeAction()
  data class SelectEnvironment(val environment: Environment?) : HzeAction()
  data class SelectTarget(val target: Target?) : HzeAction()
  data class SelectExploreType(val exploreType: ExploreType?) : HzeAction()
  
  data class SelectRestMethod(val restMethod: HttpMethod) : HzeAction()
  data class SelectMap(val map: Map?) : HzeAction()
  data class SetMapInsertedKey(val insertedKey: String?) : HzeAction()
  data class SetMapInsertedJson(val insertedJson: String?) : HzeAction()
  data class SetMapServerResponseStatus(val status: HttpStatusCode?) : HzeAction()
  data class SetMapServerResponseJson(val json: String?) : HzeAction()
  object ResetMapServerResponse : HzeAction()
  
  data class SelectTopic(val topic: Topic?) : HzeAction()
  data class SetTopicSubscription(val subscription: (() -> Unit)?) : HzeAction()
  data class AddTopicMessage(val message: String) : HzeAction()
}