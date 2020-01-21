package component.common.selector

import kotlinx.html.hidden
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import lt.petuska.hazelcast.explorer.component.StatelessComponent
import org.w3c.dom.HTMLSelectElement
import react.RBuilder
import react.dom.option
import react.dom.select

class Selector(props: SelectorProps) : StatelessComponent<SelectorProps>(props) {
  override fun RBuilder.render() {
    select(classes = "form-control ${props.classes}") {
      attrs {
        id = props.id
        disabled = props.disabled
        multiple = false
        required = props.required
        value = props.selectedValue
        onChangeFunction = {
          val target = it.target as HTMLSelectElement
          props.onSelectionChange(target.value)
        }
      }
      props.placeholderText?.let {
        option {
          attrs {
            value = ""
            disabled = true
            hidden = true
          }
          +it
        }
      }
      props.values.forEach {
        option {
          attrs {
            value = it
            label = it
          }
          +it
        }
      }
    }
  }
  
  override fun componentWillUnmount() {
    props.onDestroy?.let { it() }
  }
}