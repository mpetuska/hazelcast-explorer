package lt.petuska.hazelcast.explorer.component.common.synchronousButton

import lt.petuska.hazelcast.explorer.domain.*
import react.*

interface SynchronousButtonProps : RProps {
  var onClick: (cb: (httpStatusCode: Int, message: String?) -> Unit) -> Unit
  var onCancel: (() -> Unit)?
  var text: String
  var type: BType
  var cancelable: Boolean
  var classes: String
}