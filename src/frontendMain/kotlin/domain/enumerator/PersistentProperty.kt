package lt.petuska.hazelcast.explorer.domain.enumerator

import lt.petuska.hazelcast.explorer.redux.*

enum class PersistentProperty(
    val key: String,
    val selector: HzeState.() -> String?,
    val updater: HzeState.(String?) -> HzeAction?
) {
  THEME("theme", { "$theme" }, { s -> s?.let { HzeAction.SetTheme(Theme.valueOf(it)) } }),
  ENVIRONMENT(
      "env",
      { selectedEnvironment?.name },
      { HzeAction.SelectEnvironment(environments?.find { e -> e.name == it }) }),
  TARGET(
      "tar",
      { selectedTarget?.name },
      { HzeAction.SelectTarget(selectedEnvironment?.targets?.find { t -> t.name == it }) }),
  EXPLORE_TYPE(
      "exploreType",
      { selectedExploreType?.name },
      {
        it?.let { p ->
          HzeAction.SelectExploreType(ExploreType.valueOf(p))
        }
      }
  )
}