package lt.petuska.hazelcast.explorer.component.common.synchronousButton

import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.js.*
import react.*
import react.dom.*
import styled.*

class SynchronousButton(props: SynchronousButtonProps) :
    RComponent<SynchronousButtonProps, SynchronousButtonState>(props) {

  override fun SynchronousButtonState.init() {
    isFetching = false
    currentRequestTimestamp = null
  }

  override fun RBuilder.render() {
    styledButton {
      css {
        classes = "btn btn-outline-${props.type} ${props.classes} font-weight-bold".split(" ").toMutableList()
        if (state.isFetching) {
          if (props.onCancel != null) {
            opacity = 0.75
          }
          classes.add("pl-3 pr-3")
        } else {
          classes.add("pl-4 pr-4")
        }
      }
      attrs {
        key = props.key
        disabled = props.disabled == true || (state.isFetching && props.onCancel == null)
        onClickFunction = {
          if (state.isFetching) {
            setState {
              isFetching = false
              currentRequestTimestamp = null
            }
            props.onCancel?.invoke()
          } else {
            val ts = currentTimeMillis()
            setState {
              isFetching = true
              currentRequestTimestamp = ts
            }
            props.onClick {
              if (state.currentRequestTimestamp == ts) {
                setState {
                  isFetching = false
                  currentRequestTimestamp = null
                }
              }
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

  override fun componentWillUnmount() {
    if (state.isFetching) {
      props.onCancel?.invoke()
    }
  }
}