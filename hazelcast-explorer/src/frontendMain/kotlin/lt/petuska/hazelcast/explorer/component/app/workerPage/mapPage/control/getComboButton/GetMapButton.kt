package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control.getComboButton

import io.ktor.client.statement.readText
import io.ktor.http.HttpStatusCode
import kotlinx.html.js.onClickFunction
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.synchronousButton
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.service.entity.MapService
import lt.petuska.hazelcast.explorer.service.util.NotificationService
import lt.petuska.hazelcast.explorer.store
import lt.petuska.hazelcast.explorer.util.launch
import org.w3c.dom.HTMLAnchorElement
import react.RBuilder
import react.RComponent
import react.dom.a
import react.dom.button
import react.dom.div
import react.dom.span
import react.setState
import kotlin.browser.document

class GetMapButton(props: GetMapButtonProps) : RComponent<GetMapButtonProps, GetMapButtonState>(props) {
  
  private fun GetMapButtonProps.validateInputs(tmpState: GetMapButtonState? = null) = run {
    val stateSnap = tmpState ?: state
    val mapSelected = props.selectedMap != null
    val keyInserted = !props.insertedKey.isNullOrBlank()
    if (stateSnap.selected.key == "get-value") {
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
  
  private val getOptions
    get() = listOf(
      DropdownButtonOption(
        "get-value",
        "Get Value"
      ) {
        store.dispatch(HzeAction.ResetMapServerResponse)
        MapService.getValue(props.environment!!, props.target!!, props.selectedMap!!, props.insertedKey).then {
          launch {
            it()
            store.dispatch(HzeAction.SetMapServerResponseStatus(it.status))
            store.dispatch(HzeAction.SetMapServerResponseJson(it.readText()))
            if (it.status == HttpStatusCode.OK) {
              NotificationService.success("Retrieved Value ${props.selectedMap?.id}[${props.insertedKey}]\"")
            } else {
              NotificationService.error("Error Retrieving Value  ${props.selectedMap?.id}[${props.insertedKey}]")
            }
          }
        }.catch {
          it()
          NotificationService.warning("Error Retrieving Value  ${props.selectedMap?.id}[${props.insertedKey}]")
        }
      },
      DropdownButtonOption(
        "get-keys",
        "Get All Keys"
      ) {
        store.dispatch(HzeAction.ResetMapServerResponse)
        MapService.getAllKeys(props.environment!!, props.target!!, props.selectedMap!!).then {
          launch {
            it()
            store.dispatch(HzeAction.SetMapServerResponseStatus(it.status))
            store.dispatch(HzeAction.SetMapServerResponseJson(it.readText()))
            if (it.status == HttpStatusCode.OK) {
              NotificationService.success("Retrieved Map Keys From ${props.selectedMap?.id}")
            } else {
              NotificationService.error("Error Retrieving Map Keys From  ${props.selectedMap?.id}")
            }
          }
        }.catch {
          it()
          NotificationService.warning("Error Retrieving Map Keys From  ${props.selectedMap?.id}")
        }
      },
      DropdownButtonOption(
        "download-values",
        "Download All Values"
      ) {
        val el = document.createElement("a") as HTMLAnchorElement
        el.href = "/api/entity/${props.environment?.name}/${props.target?.name}/map/${props.selectedMap?.name}/value"
        el.target = "_self"
        el.hidden = true
        el.setAttribute("style", "position: 'absolute', left: '-9999px'")
        document.body?.appendChild(el)
        el.click()
        document.body?.removeChild(el)
        NotificationService.info("Started ${props.selectedMap?.id} values download")
        it()
      }
    )
}