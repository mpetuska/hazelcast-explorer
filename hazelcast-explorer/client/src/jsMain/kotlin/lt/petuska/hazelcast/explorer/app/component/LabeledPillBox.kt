package lt.petuska.hazelcast.explorer.app.component

import dev.fritz2.binding.const
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.HtmlElements
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lt.petuska.hazelcast.explorer.domain.enumerator.BType

internal fun HtmlElements.labeledPillBox(
  label: String,
  type: Flow<BType>,
  value: Flow<String>,
  hidden: Flow<Boolean> = const(false),
  content: Div.() -> Unit = {},
) = div("border rounded m-1 align-items-center p-1 pr-2 pl-2") {
  className = hidden.map { if (it) "d-none" else "d-inline-flex" }
  span("mr-2") {
    +label
  }
  value.render {
    span("badge badge-pill") {
      className = type.map { t -> "badge-$t" }
      +it
    }
  }.bind()
  content()
}
