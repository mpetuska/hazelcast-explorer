package lt.petuska.hazelcast.explorer.component.common.synchronousButton

import kotlinx.html.*
import kotlinx.html.js.*
import react.*
import react.dom.*
import styled.*

class SynchronousButton(props: SynchronousButtonProps) :
    RComponent<SynchronousButtonProps, SynchronousButtonState>(props) {

  override fun SynchronousButtonState.init() {
    isFetching = false
  }

  override fun RBuilder.render() {
    styledButton {
      css {
        classes = "btn btn-outline-${props.type} ${props.classes} font-weight-bold".split(" ").toMutableList()
        if (state.isFetching) {
          classes.add("pl-3 pr-3")
        } else {
          classes.add("pl-4 pr-4")
        }
      }
      attrs {
        key = props.key
        disabled = props.disabled == true || state.isFetching
        onClickFunction = {
          setState {
            isFetching = true
          }
          props.onClick {
            setState {
              isFetching = false
            }
          }
        }
      }
      if (state.isFetching) {
        div("spinner-grow spinner-grow-sm") {
          attrs {
            role = "status"
          }
        }
      }
      +props.text
    }
  }
}