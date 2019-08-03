package component.common.selector

import react.*

interface SelectorProps : RProps {
  var onSelectionChange: (String) -> Unit
  var onDestroy: (() -> Unit)?
  var values: Set<String>
  var placeholderText: String?
  var selectedValue: String
  var required: Boolean
  var id: String
}