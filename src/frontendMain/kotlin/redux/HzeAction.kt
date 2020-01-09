package lt.petuska.hazelcast.explorer.redux

import io.ktor.http.*
import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import redux.*

sealed class HzeAction : RAction {
  data class SetTitle(val title: String) : HzeAction()
  data class SetTheme(val theme: Theme) : HzeAction()
  data class HzeConfigLoaded(val hzeConfig: HzeConfigDTO) : HzeAction()
  data class SelectEnvironment(val environment: EnvironmentDTO?) : HzeAction()
  data class SelectTarget(val target: TargetDTO?) : HzeAction()
  data class SelectExploreType(val exploreType: ExploreType?) : HzeAction()

  data class SelectRestMethod(val restMethod: HttpMethod) : HzeAction()
  data class SelectMap(val map: MapDTO?) : HzeAction()
  data class SetMapInsertedKey(val insertedKey: String?) : HzeAction()
  data class SetMapInsertedJson(val insertedJson: String?) : HzeAction()
  data class SetMapCurrentRequestTimestamp(val timestamp: Long?) : HzeAction()
  data class SetMapServerResponseStatus(val status: HttpStatusCode?) : HzeAction()
  data class SetMapServerResponseJson(val json: String?) : HzeAction()
  object ResetMapServerResponse : HzeAction()

  data class SelectTopic(val topic: TopicDTO?) : HzeAction()
}