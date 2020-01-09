package lt.petuska.hazelcast.explorer.redux.reducer.page

import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*
import lt.petuska.hazelcast.explorer.redux.state.*


object TopicPageStateReducer : HzeReducer<TopicPageState>(HzeState::topicPageState) {
  override fun reduce(state: TopicPageState, action: HzeAction) = when (action) {
    is HzeAction.SelectTopic -> state.copy(selectedTopic = action.topic)
    else -> state
  }
}