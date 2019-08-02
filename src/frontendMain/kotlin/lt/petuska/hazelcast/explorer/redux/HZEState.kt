package lt.petuska.hazelcast.explorer.redux

import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*

data class HZEState(
    val title: String = "Hazelcast Explorer",
    val theme: Theme = Theme.DARK,
    val environments: List<EnvironmentDTO>? = null,
    val selectedEnvironment: EnvironmentDTO? = null,
    val selectedTarget: TargetDTO? = null,
    val selectedExploreType: ExploreType? = null
)