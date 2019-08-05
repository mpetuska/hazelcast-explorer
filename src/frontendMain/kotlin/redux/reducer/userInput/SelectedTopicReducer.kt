package lt.petuska.hazelcast.explorer.redux.reducer.userInput

import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*
import lt.petuska.hazelcast.explorer.redux.state.*


object SelectedTopicReducer : HzeReducer<TopicPageState>(HzeState::topicPageState) {
  override fun reduce(state: TopicPageState, action: HzeAction) = when (action) {
    is HzeAction.SelectTopic -> state.copy(selectedTopic = action.topic)
    else -> state
  }
}