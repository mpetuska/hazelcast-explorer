package lt.petuska.hazelcast.explorer.redux.reducer.userInput

import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*


object SelectedTopicReducer : HzeReducer<TopicDTO?>(HzeState::selectedTopic) {
  override fun reduce(state: TopicDTO?, action: HzeAction) = when (action) {
    is HzeAction.SelectTopic -> action.topic
    else -> state
  }
}