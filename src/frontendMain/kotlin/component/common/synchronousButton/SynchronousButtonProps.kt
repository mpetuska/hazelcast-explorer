package lt.petuska.hazelcast.explorer.component.common.synchronousButton

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import react.*

interface SynchronousButtonProps : RProps {
  var onClick: (cb: () -> Unit) -> Unit
  var text: String
  var type: BType
  var classes: String
  var disabled: Boolean
  var key: String
}