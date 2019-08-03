package lt.petuska.hazelcast.explorer.redux

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*

data class HzeState(
    val title: String = "Hazelcast Explorer",
    val theme: Theme = Theme.DARK,
    val environments: List<EnvironmentDTO>? = null,
    val selectedEnvironment: EnvironmentDTO? = null,
    val selectedTarget: TargetDTO? = null,
    val selectedExploreType: ExploreType? = null,

    val selectedRestMethod: RestMethod = RestMethod.GET,
    val selectedMap: MapDTO? = null,
    val selectedTopic: TopicDTO? = null,
    val insertedKey: String? = null,
    val insertedJson: String? = null
)