package lt.petuska.hazelcast.explorer.component.common.synchronousButton

import react.*

interface SynchronousButtonState : RState {
  var isFetching: Boolean
  var currentRequestTimestamp: Long?
}