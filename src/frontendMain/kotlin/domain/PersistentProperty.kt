package lt.petuska.hazelcast.explorer.domain

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*

enum class PersistentProperty(
    val key: String,
    val selector: HZEState.() -> String?,
    val updater: HZEState.(String?) -> HZEAction?
) {
  THEME("theme", { "$theme" }, { s -> s?.let { HZEAction.SetTheme(Theme.valueOf(it)) } }),
  ENVIRONMENT(
      "env",
      { selectedEnvironment?.name },
      { HZEAction.SelectEnvironment(environments?.find { e -> e.name == it }) }),
  TARGET(
      "tar",
      { selectedTarget?.name },
      { HZEAction.SelectTarget(selectedEnvironment?.targets?.find { t -> t.name == it }) }),
  EXPLORE_TYPE(
      "exploreType",
      { selectedExploreType?.name },
      {
        it?.let { p ->
          HZEAction.SelectExploreType(ExploreType.valueOf(p))
        }
      }
  )
}