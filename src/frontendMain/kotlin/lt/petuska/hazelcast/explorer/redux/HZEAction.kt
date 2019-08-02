package lt.petuska.hazelcast.explorer.redux

import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import redux.*

sealed class HZEAction : RAction {
  data class SetTitle(val title: String) : HZEAction()
  data class SetTheme(val theme: Theme) : HZEAction()
  data class HZEConfigLoaded(val hzeConfig:HazelcastExplorerConfigDTO) : HZEAction()
  data class SelectEnvironment(val environment: EnvironmentDTO?):HZEAction()
  data class SelectTarget(val target:TargetDTO?):HZEAction()
  data class SelectExploreType(val exploreType: ExploreType):HZEAction()
}