package component.app.workerPage.mapPage.control.getComboButton

import kotlinx.html.js.*
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.*
import react.*
import react.dom.*

class GetMapButton(props: GetMapButtonProps) : RComponent<GetMapButtonProps, GetMapButtonState>(props) {

  private fun GetMapButtonProps.validateInputs(tmpState: GetMapButtonState? = null) = run {
    val stateSnap = tmpState ?: state
    val mapSelected = props.selectedMap != null
    val keyInserted = !props.insertedKey.isNullOrBlank()
    if (stateSnap.selected == getOptions[0]) {
      mapSelected && keyInserted
    } else {
      mapSelected
    }
  }

  override fun GetMapButtonState.init(props: GetMapButtonProps) {
    if (selected == undefined) {
      selected = getOptions[0]
    }
    validInput = props.validateInputs(this)
  }

  override fun componentWillReceiveProps(nextProps: GetMapButtonProps) {
    state.init(props)
  }

  override fun RBuilder.render() {
    div("btn-group m-1") {
      synchronousButton {
        attrs {
          key = state.selected.key
          text = state.selected.text
          type = props.type
          onClick = state.selected.onClick
          onCancel = state.selected.onCancel
          disabled = !props.validateInputs()
        }
      }
      button(classes = "btn btn-outline-${props.type} dropdown-toggle dropdown-toggle-split") {
        attrs {
          set("aria-haspopup", true)
          set("aria-expanded", false)
          set("data-toggle", "dropdown")
        }
        span("sr-only") { +"Toggle Dropdown" }
      }
      div("dropdown-menu") {
        getOptions.forEach { option ->
          a(classes = "dropdown-item cursor-pointer") {
            attrs.onClickFunction = {
              if (state.selected != option) {
                setState {
                  selected = option
                }
              }
            }
            +option.text
          }
        }
      }
    }
  }

  companion object {
    private val getOptions = listOf(
        DropdownButtonOption("get-value", "Get Value",
            {
              //TODO
              println("Getting Value")
              it()
            },
            onCancel = { println("cancelled") }
        ),
        DropdownButtonOption("get-keys", "Get All Keys",
            {
              //TODO
              println("Getting All Keys")
              it()
            },
            onCancel = { println("cancelled") }
        ),
        DropdownButtonOption("get-values", "Get All Values",
            {
              //TODO
              println("Getting All Values")
              it()
            },
            onCancel = { println("cancelled") }
        )
    )
  }
}