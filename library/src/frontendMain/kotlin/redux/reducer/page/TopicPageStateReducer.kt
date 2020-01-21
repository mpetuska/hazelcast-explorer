package lt.petuska.hazelcast.explorer.redux.reducer.page

import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer
import lt.petuska.hazelcast.explorer.redux.state.TopicPageState
import kotlin.js.Date


object TopicPageStateReducer : HzeReducer<TopicPageState>(HzeState::topicPageState) {
  override fun reduce(state: TopicPageState, action: HzeAction) = when (action) {
    is HzeAction.SelectTopic -> state.copy(selectedTopic = action.topic)
    is HzeAction.AddTopicMessage -> state.copy(receivedMessages = state.receivedMessages.toMutableList().apply {
      add(0, Pair(Date(Date.now()), JSON.parse(action.message)))
    })
    is HzeAction.SetTopicSubscription -> {
      var messages = state.receivedMessages
      if (state.subscription != action.subscription && action.subscription != null) {
        messages = listOf()
      }
      if (state.subscription != action.subscription || action.subscription == null) {
        state.subscription?.invoke()
      }
      state.copy(subscription = action.subscription, receivedMessages = messages)
    }
    else -> state
  }
}