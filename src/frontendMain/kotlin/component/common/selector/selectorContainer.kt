package lt.petuska.hazelcast.explorer.component.common.selector

import component.common.selector.*
import lt.petuska.hazelcast.explorer.util.*
import react.*

interface ContainerProps : RProps {
  var onSelectionChange: (String) -> Unit
  var onDestroy: (() -> Unit)?
  var values: Set<String>
  var placeholderText: String?
  var selectedValue: String?
  var required: Boolean
  var id: String
  var classes: String
}

val selector = connectClass<ContainerProps, SelectorProps, Selector, SelectorProps>(
    mapStateToProps = { _, ownProps ->
      onSelectionChange = ownProps.onSelectionChange
      onDestroy = ownProps.onDestroy
      values = ownProps.values
      placeholderText = ownProps.placeholderText
      selectedValue = ownProps.selectedValue.ifNullOrUndefined("")
      required = ownProps.required
      id = ownProps.id
      classes = ownProps.classes
    }
)