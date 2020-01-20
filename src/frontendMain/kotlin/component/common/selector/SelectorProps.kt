package component.common.selector

import react.RProps

interface SelectorProps : RProps {
  var onSelectionChange: (String) -> Unit
  var onDestroy: (() -> Unit)?
  var values: Set<String>
  var placeholderText: String?
  var selectedValue: String
  var required: Boolean
  var id: String
  var classes: String
  var disabled: Boolean
}