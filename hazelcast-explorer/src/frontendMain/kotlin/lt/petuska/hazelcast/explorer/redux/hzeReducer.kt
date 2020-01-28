package lt.petuska.hazelcast.explorer.redux

import io.ktor.http.HttpMethod
import kotlinext.js.JsObject
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import redux.RAction
import redux.Reducer
import kotlin.js.Date

private fun Environment?.findTarget(target: Target?) = this?.targets?.find { it.name == target?.name }
private fun Target?.findMap(map: Map?) = this?.maps?.find { it.name == map?.name }
private fun Target?.findTopic(topic: Topic?) = this?.topics?.find { it.name == topic?.name }

val hzeReducer: Reducer<HzeState, RAction> = { state, action ->
  if (action !is HzeAction)
    state
  else
    when (action) {
      is HzeAction.SetTheme -> state.copy(theme = action.theme)
      
      is HzeAction.SelectEnvironment -> with(state) {
        val environment = action.environment
        val target = environment.findTarget(target)
        val map = target.findMap(map)
        val topic = target.findTopic(topic)
        val mapMethod = if (map.readOnly) HttpMethod.Get else mapMethod
        copy(environment = environment, target = target, mapMethod = mapMethod, map = map, topic = topic)
      }
      
      is HzeAction.SelectTarget -> with(state) {
        val target = action.target
        val map = target.findMap(map)
        val topic = target.findTopic(topic)
        val mapMethod = if (map.readOnly) HttpMethod.Get else mapMethod
        copy(target = target, mapMethod = mapMethod, map = map, topic = topic)
      }
      
      is HzeAction.SelectExploreType -> with(state) {
        val type = action.exploreType
        if ((type == ExploreType.MAP && target?.maps != null) ||
          (type == ExploreType.TOPIC && target?.topics != null)
        ) {
          copy(exploreType = type)
        } else state
      }
      
      is HzeAction.SelectRestMethod -> with(state) {
        val mapMethod = if (map.readOnly) HttpMethod.Get else action.restMethod
        copy(mapMethod = mapMethod)
      }
      
      is HzeAction.SelectMap -> with(state) {
        val map = action.map
        val mapMethod = if (map.readOnly) HttpMethod.Get else mapMethod
        copy(mapMethod = mapMethod, map = map)
      }
      
      is HzeAction.SetMapInsertedKey -> state.copy(mapKey = action.insertedKey?.takeIf { it.isNotBlank() })
      
      is HzeAction.SetMapInsertedJson -> state.copy(mapJson = action.insertedJson?.takeIf { it.isNotBlank() })
      
      is HzeAction.SetMapServerResponseStatus -> state.copy(mapResponseStatus = action.status)
      
      is HzeAction.SetMapServerResponseJson -> state.copy(mapResponseJson = action.json?.let { JSON.parse<JsObject>(it) })
      
      HzeAction.ResetMapServerResponse -> state.copy(mapResponseJson = null, mapResponseStatus = null)
      
      is HzeAction.SelectTopic -> state.copy(topic = action.topic)
      
      is HzeAction.SetTopicSubscription -> with(state) {
        val messages = if (topicSubscription != action.subscription && action.subscription != null) {
          listOf()
        } else topicMessages
        if (topicSubscription != action.subscription || action.subscription == null) {
          topicSubscription?.invoke()
        }
        state.copy(topicSubscription = action.subscription, topicMessages = messages)
      }
      
      is HzeAction.AddTopicMessage -> state.copy(topicMessages = state.topicMessages.toMutableList().apply {
        add(0, Pair(Date(Date.now()), JSON.parse(action.message)))
      })
    }
}