package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control

import io.ktor.client.statement.readText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinext.js.JsObject
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import lt.petuska.hazelcast.explorer.component.StatelessComponent
import lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control.getComboButton.getMapButton
import lt.petuska.hazelcast.explorer.component.common.selector.selector
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.synchronousButton
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.service.entity.MapService
import lt.petuska.hazelcast.explorer.service.util.NotificationService
import lt.petuska.hazelcast.explorer.store
import lt.petuska.hazelcast.explorer.types.jsonInput.IntelliJColors
import lt.petuska.hazelcast.explorer.types.jsonInput.IntelliJDarculaColors
import lt.petuska.hazelcast.explorer.types.jsonInput.JSONInput
import lt.petuska.hazelcast.explorer.types.jsonInput.LocaleEN
import lt.petuska.hazelcast.explorer.util.launch
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.dom.div
import react.dom.form
import react.dom.input
import react.dom.key
import react.key

class MapPageControl(props: MapPageControlProps) :
  StatelessComponent<MapPageControlProps>(
    props
  ) {
  override fun RBuilder.render() {
    form(classes = "form-inline justify-content-center mb-2") {
      httpMethodSelector()
      mapSelector()
      keyInput()
      actionButton()
    }
    when (props.selectedHttpMethod) {
      HttpMethod.Post,
      HttpMethod.Put -> jsonInput()
    }
  }
  
  private fun RBuilder.httpMethodSelector() = selector {
    attrs {
      classes = "m-1"
      required = true
      values = props.httpMethods
      selectedValue = props.selectedHttpMethod.value
      onSelectionChange = {
        store.dispatch(HzeAction.SelectRestMethod(HttpMethod.parse(it)))
      }
    }
  }
  
  private fun RBuilder.mapSelector() = selector {
    attrs {
      classes = "m-1"
      required = true
      values = props.target?.maps?.map { it.displayName }?.toSet() ?: setOf()
      placeholderText = "Select map..."
      selectedValue = props.target?.maps?.find { it == props.selectedMap }.also {
        if (props.selectedMap != it) {
          store.dispatch(HzeAction.SelectMap(it))
        }
      }?.displayName
      onSelectionChange = {
        store.dispatch(HzeAction.SelectMap(props.target?.maps?.find { m -> it == m.displayName }))
      }
    }
  }
  
  private fun RBuilder.keyInput() = input(InputType.text, classes = "form-control m-1") {
    attrs {
      key = props.target?.name ?: ""
      required = props.selectedHttpMethod != HttpMethod.Get
      value = props.insertedKey ?: ""
      placeholder = "Enter key${if (props.selectedHttpMethod == HttpMethod.Get) " (optional)" else ""}..."
      onChangeFunction = {
        val target = it.target as HTMLInputElement
        store.dispatch(HzeAction.SetMapInsertedKey(target.value))
      }
    }
  }
  
  private fun RBuilder.actionButton() {
    when (props.selectedHttpMethod) {
      HttpMethod.Get -> getMapButton {
        attrs.key = "${HttpMethod.Get.value}-${props.selectedMap?.id}"
      }
      HttpMethod.Delete -> synchronousButton {
        attrs {
          key = "${HttpMethod.Delete.value}-${props.selectedMap?.id}"
          disabled = !inputValid()
          classes = "m-1"
          text = "Delete Value"
          type = BType.DANGER
          onClick = {
            store.dispatch(HzeAction.ResetMapServerResponse)
            MapService.deleteValue(props.environment!!, props.target!!, props.selectedMap!!, props.insertedKey)
              .then { httpResponse ->
                launch {
                  it()
                  store.dispatch(HzeAction.SetMapServerResponseStatus(httpResponse.status))
                  store.dispatch(HzeAction.SetMapServerResponseJson(httpResponse.readText()))
                  if (httpResponse.status == HttpStatusCode.NoContent) {
                    NotificationService.success("Deleted Value ${props.selectedMap?.id}[${props.insertedKey}]")
                  } else {
                    NotificationService.error("Error Deleting Value ${props.selectedMap?.id}[${props.insertedKey}]")
                  }
                }
              }.catch {
                it()
                NotificationService.warning("Error Deleting Value ${props.selectedMap?.id}[${props.insertedKey}]")
              }
          }
        }
      }
      HttpMethod.Post -> synchronousButton {
        attrs {
          key = "${HttpMethod.Post.value}-${props.selectedMap?.id}"
          disabled = !inputValid()
          classes = "m-1"
          text = "Create Value"
          type = BType.WARNING
          onClick = {
            store.dispatch(HzeAction.ResetMapServerResponse)
            MapService.createValue(
              props.environment!!,
              props.target!!,
              props.selectedMap!!,
              props.insertedKey,
              props.insertedJson!!
            ).then {
              launch {
                it()
                store.dispatch(HzeAction.SetMapServerResponseStatus(it.status))
                store.dispatch(HzeAction.SetMapServerResponseJson(it.readText()))
                if (it.status == HttpStatusCode.Created) {
                  NotificationService.success("Created Value ${props.selectedMap?.id}[${props.insertedKey}]")
                } else {
                  NotificationService.error("Error Creating Value ${props.selectedMap?.id}[${props.insertedKey}]")
                }
              }
            }.catch {
              it()
              NotificationService.warning("Error Creating Value ${props.selectedMap?.id}[${props.insertedKey}]")
            }
          }
        }
      }
      HttpMethod.Put -> synchronousButton {
        attrs {
          key = "${HttpMethod.Put.value}-${props.selectedMap?.id}"
          disabled = !inputValid()
          classes = "m-1"
          text = "Update Value"
          type = BType.INFO
          onClick = {
            store.dispatch(HzeAction.ResetMapServerResponse)
            MapService.replaceValue(
              props.environment!!,
              props.target!!,
              props.selectedMap!!,
              props.insertedKey,
              props.insertedJson!!
            ).then {
              launch {
                it()
                store.dispatch(HzeAction.SetMapServerResponseStatus(it.status))
                store.dispatch(HzeAction.SetMapServerResponseJson(it.readText()))
                if (it.status == HttpStatusCode.NoContent) {
                  NotificationService.success("Replaced Value ${props.selectedMap?.id}[${props.insertedKey}]")
                } else {
                  NotificationService.error("Error Replacing Value ${props.selectedMap?.id}[${props.insertedKey}]")
                }
              }
            }.catch {
              it()
              NotificationService.warning("Error Replacing Value ${props.selectedMap?.id}[${props.insertedKey}]")
            }
          }
        }
      }
    }
  }
  
  private fun RBuilder.jsonInput() = div("border rounded m-1 h-50") {
    JSONInput {
      attrs {
        key = props.theme.themeName
        props.insertedJson?.let { placeholder = JSON.parse<JsObject>(it) }
        locale = LocaleEN
        width = "100%"
        height = "400px"
        confirmGood = false
        reset = false
        colors = if (props.theme == Theme.DARK) IntelliJDarculaColors else IntelliJColors
        waitAfterKeyPress = 500
        onChange = {
          if (it.error == false) store.dispatch(HzeAction.SetMapInsertedJson(it.json))
        }
      }
    }
  }
  
  private fun inputValid() = when (props.selectedHttpMethod) {
    HttpMethod.Get,
    HttpMethod.Delete -> props.insertedKey != null && props.selectedMap != null
    HttpMethod.Post,
    HttpMethod.Put -> props.insertedKey != null && props.selectedMap != null && props.insertedJson != null
    else -> false
  }
}