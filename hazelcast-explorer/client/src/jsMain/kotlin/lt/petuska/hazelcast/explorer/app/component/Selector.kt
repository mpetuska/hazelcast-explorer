package lt.petuska.hazelcast.explorer.app.component

import dev.fritz2.binding.Handler
import dev.fritz2.binding.const
import dev.fritz2.binding.handledBy
import dev.fritz2.dom.Tag
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.w3c.dom.HTMLSelectElement

internal fun Tag<*>.selector(
  classes: String = "",
  id: String? = null,
  disabled: Flow<Boolean> = const(false),
  required: Flow<Boolean> = const(false),
  placeholder: String? = null,
  value: Flow<String?>,
  values: Flow<List<Pair<String, String>>?>,
  onChange: Handler<String?>,
) = values.render { vals ->
  select("form-control $classes", id) {
    disabled.combine(value) { d, v -> d || (vals?.size ?: 0 <= 1 && !v.isNullOrEmpty()) }.bindAttr("disabled")
    const(false).bindAttr("multiple")
    required.bindAttr("required")
    changes.map { it.target.unsafeCast<HTMLSelectElement>().value.takeIf { v -> v.isNotEmpty() } } handledBy onChange

    fun opt(v: String, dis: Boolean = false, txt: String = v, hidden: Boolean = false) = option {
      this.value = const(v)
      const(dis).bindAttr("disabled")
      const(hidden).bindAttr("hidden")
      value.map { it == v }.bindAttr("selected")
      +txt
    }
    placeholder?.let {
      opt(
        "",
        true,
        it,
        true,
      )
    }
    vals?.forEach { str ->
      val (vId, vName) = str
      opt(vId, txt = vName)
    }
  }
}.bind()
