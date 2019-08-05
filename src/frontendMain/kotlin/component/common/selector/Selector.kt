package component.common.selector

import kotlinx.html.*
import kotlinx.html.js.*
import lt.petuska.hazelcast.explorer.component.*
import org.w3c.dom.*
import react.*
import react.dom.*

class Selector(props: SelectorProps) : StatelessComponent<SelectorProps>(props) {
  override fun RBuilder.render() {
    select(classes = "form-control ${props.classes}") {
      attrs {
        id = props.id
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