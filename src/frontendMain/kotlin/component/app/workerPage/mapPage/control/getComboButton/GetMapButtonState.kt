package component.app.workerPage.mapPage.control.getComboButton

import react.RState

interface GetMapButtonState : RState {
  var selected: DropdownButtonOption
  var validInput: Boolean
}