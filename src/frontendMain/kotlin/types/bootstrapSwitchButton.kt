@file:Suppress(
  "INTERFACE_WITH_SUPERCLASS",
  "OVERRIDING_FINAL_MEMBER",
  "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
  "CONFLICTING_OVERLOADS",
  "EXTERNAL_DELEGATION"
)
@file:JsModule("bootstrap-switch-button-react")
@file:JsNonModule

package lt.petuska.hazelcast.explorer.types

import react.Component
import react.RProps
import react.RState

external interface BootstrapSwitchButtonState : RState {
  var checked: Boolean
  var disabled: Boolean
}

external interface BootstrapSwitchButtonProps : RProps {
  var onChange: ((checked: Boolean) -> Unit)? get() = definedExternally; set(value) = definedExternally
  var checked: Boolean? get() = definedExternally; set(value) = definedExternally
  var size: String
  var onlabel: String? get() = definedExternally; set(value) = definedExternally
  var offlabel: String? get() = definedExternally; set(value) = definedExternally
  var offstyle: dynamic /* "primary" | "secondary" | "success" | "danger" | "warning" | "info" | "light" | "dark" | "outline-primary" | "outline-secondary" | "outline-success" | "outline-danger" | "outline-warning" | "outline-info" | "outline-light" | "outline-dark" */
  var onstyle: dynamic /* "primary" | "secondary" | "success" | "danger" | "warning" | "info" | "light" | "dark" | "outline-primary" | "outline-secondary" | "outline-success" | "outline-danger" | "outline-warning" | "outline-info" | "outline-light" | "outline-dark" */
  var style: String? get() = definedExternally; set(value) = definedExternally
  var width: Int
}

/**
 * https://stackoverflow.com/questions/51431848/how-to-import-node-module-in-react-kotlin
 */
@JsName("default")
external class BootstrapSwitchButton : Component<BootstrapSwitchButtonProps, BootstrapSwitchButtonState> {
  override fun render() = definedExternally
}