package component.app.workerPage.mapPage.control

import component.app.workerPage.mapPage.control.getComboButton.*
import io.ktor.client.response.*
import io.ktor.http.*
import kotlinext.js.*
import kotlinx.html.*
import kotlinx.html.js.*
import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.component.*
import lt.petuska.hazelcast.explorer.component.common.selector.*
import lt.petuska.hazelcast.explorer.component.common.synchronousButton.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.service.entity.*
import lt.petuska.hazelcast.explorer.service.util.*
import lt.petuska.hazelcast.explorer.types.jsonInput.*
import lt.petuska.hazelcast.explorer.util.*
import org.w3c.dom.*
import react.*
import react.dom.*

class MapPageControl(props: MapPageControlProps) : StatelessComponent<MapPageControlProps>(props) {
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
      values = setOf(HttpMethod.Get.value, HttpMethod.Post.value, HttpMethod.Put.value, HttpMethod.Delete.value)
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
        store.dispatch(HzeAction.SelectMap(it))
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
        attrs.key = "${HttpMethod.Get.value}-${props.target?.environment}-${props.target?.name}"
      }
      HttpMethod.Delete -> synchronousButton {
        attrs {
          key = "${HttpMethod.Delete.value}-${props.target?.environment}-${props.target?.name}"
          disabled = !inputValid()
          classes = "m-1"
          text = "Delete Value"
          type = BType.DANGER
          onClick = {
            store.dispatch(HzeAction.ResetMapServerResponse)
            MapService.deleteValue(props.selectedMap, props.insertedKey).then {
              launch {
                it()
                store.dispatch(HzeAction.SetMapServerResponseStatus(it.status))
                store.dispatch(HzeAction.SetMapServerResponseJson(it.content.readText()))
                if (it.status == HttpStatusCode.NoContent) {
                  NotificationService.success("Deleted Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
                } else {
                  NotificationService.error("Error Deleting Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
                }
              }
            }.catch {
              it()
              NotificationService.warning("Error Deleting Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
            }
          }
        }
      }
      HttpMethod.Post -> synchronousButton {
        attrs {
          key = "${HttpMethod.Post.value}-${props.target?.environment}-${props.target?.name}"
          disabled = !inputValid()
          classes = "m-1"
          text = "Create Value"
          type = BType.WARNING
          onClick = {
            store.dispatch(HzeAction.ResetMapServerResponse)
            MapService.createValue(props.selectedMap, props.insertedKey, props.insertedJson!!).then {
              launch {
                it()
                store.dispatch(HzeAction.SetMapServerResponseStatus(it.status))
                store.dispatch(HzeAction.SetMapServerResponseJson(it.content.readText()))
                if (it.status == HttpStatusCode.Created) {
                  NotificationService.success("Created Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
                } else {
                  NotificationService.error("Error Creating Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
                }
              }
            }.catch {
              it()
              NotificationService.warning("Error Creating Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
            }
          }
        }
      }
      HttpMethod.Put -> synchronousButton {
        attrs {
          key = "${HttpMethod.Put.value}-${props.target?.environment}-${props.target?.name}"
          disabled = !inputValid()
          classes = "m-1"
          text = "Update Value"
          type = BType.INFO
          onClick = {
            store.dispatch(HzeAction.ResetMapServerResponse)
            MapService.replaceValue(props.selectedMap, props.insertedKey, props.insertedJson!!).then {
              launch {
                it()
                store.dispatch(HzeAction.SetMapServerResponseStatus(it.status))
                store.dispatch(HzeAction.SetMapServerResponseJson(it.content.readText()))
                if (it.status == HttpStatusCode.NoContent) {
                  NotificationService.success("Replaced Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
                } else {
                  NotificationService.error("Error Replacing Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
                }
              }
            }.catch {
              it()
              NotificationService.warning("Error Replacing Value ${props.selectedMap?.idString()}[${props.insertedKey}]")
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