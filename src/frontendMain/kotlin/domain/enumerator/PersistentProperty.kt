package lt.petuska.hazelcast.explorer.domain.enumerator

import io.ktor.http.*
import lt.petuska.hazelcast.explorer.redux.*

enum class PersistentProperty(
    val key: String,
    val selector: HzeState.() -> String?,
    val updater: HzeState.(String?) -> HzeAction?
) {
  THEME("thm", { "$theme" }, { s -> s?.let { HzeAction.SetTheme(Theme.valueOf(it)) } }),
  ENVIRONMENT(
      "env",
      { selectedEnvironment?.name },
      { HzeAction.SelectEnvironment(environments?.find { e -> e.name == it }) }),
  TARGET(
      "tar",
      { selectedTarget?.name },
      { HzeAction.SelectTarget(selectedEnvironment?.targets?.find { t -> t.name == it }) }),
  EXPLORE_TYPE(
      "et",
      { selectedExploreType?.name },
      {
        it?.let { p ->
          HzeAction.SelectExploreType(ExploreType.valueOf(p))
        }
      }
  ),
  HTTP_METHOD(
      "http",
      { mapMageState.selectedRestMethod.value },
      {
        it?.let { method ->
          HzeAction.SelectRestMethod(HttpMethod.parse(method))
        }
      }
  ),
  SELECTED_MAP(
      "map",
      { mapMageState.selectedMap?.name },
      {
        it?.let { mapName ->
          selectedTarget?.maps?.find { m -> m.name == mapName }?.let { map ->
            HzeAction.SelectMap(map)
          }
        }
      }
  ),
  INSERTED_KEY(
      "mkey",
      { mapMageState.insertedKey },
      {
        it?.let { key ->
          HzeAction.SetMapInsertedKey(key)
        }
      }
  )
}