package lt.petuska.hazelcast.explorer.component.common.synchronousButton

import react.RState

interface SynchronousButtonState : RState {
  var isFetching: Boolean
}