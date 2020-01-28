package lt.petuska.hazelcast.explorer.component.common

import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import react.RBuilder
import react.dom.div
import react.dom.span

fun RBuilder.labeledPillBox(label: String, type: BType, value: String) =
  div("border rounded m-1 d-inline-flex align-items-center p-1") {
    span("mr-2") { +label }
    span("badge badge-pill badge-$type") {
      +value
    }
  }