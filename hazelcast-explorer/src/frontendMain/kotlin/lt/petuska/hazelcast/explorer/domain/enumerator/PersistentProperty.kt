package lt.petuska.hazelcast.explorer.domain.enumerator

import io.ktor.http.HttpMethod
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState

enum class PersistentProperty(
  val key: String,
  val selector: HzeState.() -> String?,
  val updater: HzeState.(String?) -> HzeAction?
) {
  THEME("thm", { "$theme" }, { s -> s?.let { HzeAction.SetTheme(Theme.valueOf(it)) } }),
  ENVIRONMENT(
    "env",
    { environment?.name },
    { HzeAction.SelectEnvironment(lt.petuska.hazelcast.explorer.config.environments.find { e -> e.name == it }) }),
  TARGET(
    "tar",
    { target?.name },
    { HzeAction.SelectTarget(environment?.targets?.find { t -> t.name == it }) }),
  EXPLORE_TYPE(
    "et",
    { exploreType?.name },
    {
      it?.let { p ->
        HzeAction.SelectExploreType(ExploreType.valueOf(p))
      }
    }
  ),
  HTTP_METHOD(
    "http",
    { mapMethod.value },
    {
      it?.let { method ->
        HzeAction.SelectRestMethod(HttpMethod.parse(method))
      }
    }
  ),
  SELECTED_MAP(
    "map",
    { map?.name },
    {
      it?.let { mapName ->
        target?.maps?.find { m -> m.name == mapName }?.let { map ->
          HzeAction.SelectMap(map)
        }
      }
    }
  ),
  SELECTED_TOPIC(
    "topic",
    { topic?.name },
    {
      it?.let { topicName ->
        target?.topics?.find { t -> t.name == topicName }?.let { topic ->
          HzeAction.SelectTopic(topic)
        }
      }
    }
  ),
  INSERTED_KEY(
    "mkey",
    { mapKey },
    {
      it?.let { key ->
        HzeAction.SetMapInsertedKey(key)
      }
    }
  )
}