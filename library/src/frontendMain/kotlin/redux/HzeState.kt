package lt.petuska.hazelcast.explorer.redux

import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.EnvironmentDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import lt.petuska.hazelcast.explorer.redux.state.MapPageState
import lt.petuska.hazelcast.explorer.redux.state.TopicPageState

data class HzeState(
  val loaded: Boolean = false,
  val title: String = "Hazelcast Explorer",
  val theme: Theme = Theme.DARK,
  val environments: List<EnvironmentDTO>? = null,
  val selectedEnvironment: EnvironmentDTO? = null,
  val selectedTarget: TargetDTO? = null,
  val selectedExploreType: ExploreType? = null,
  
  val mapMageState: MapPageState = MapPageState(),
  val topicPageState: TopicPageState = TopicPageState()
)