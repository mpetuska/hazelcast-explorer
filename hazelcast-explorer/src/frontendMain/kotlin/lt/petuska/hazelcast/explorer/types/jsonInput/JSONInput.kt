@file:JsModule("react-json-editor-ajrm")
@file:JsNonModule

package lt.petuska.hazelcast.explorer.types.jsonInput

import kotlinext.js.JsObject
import react.RClass
import react.RProps

external interface JSONInputProps : RProps {
  var id: String?
  var locale: JsObject
  var placeholder: dynamic
  var reset: Boolean?
  var viewOnly: Boolean?
  var onChange: ((JSONContent) -> Unit)?
  var confirmGood: Boolean?
  var height: String?
  var width: String?
  var onKeyPressUpdate: Boolean?
  var waitAfterKeyPress: Int?
  var modifyErrorText: ((String) -> String)?
  var theme: String?
  var colors: JSONInputColors?
  var style: JsObject?
}

external interface JSONContent {
  var plainText: String
  var markupText: String
  var lines: Int
  var error: dynamic
  var json: String
  var jsObject: JsObject
}

/**
 * https://www.npmjs.com/package/react-json-editor-ajrm
 * https://stackoverflow.com/questions/51431848/how-to-import-node-module-in-react-kotlin
 */
@JsName("default")
external val JSONInput: RClass<JSONInputProps>