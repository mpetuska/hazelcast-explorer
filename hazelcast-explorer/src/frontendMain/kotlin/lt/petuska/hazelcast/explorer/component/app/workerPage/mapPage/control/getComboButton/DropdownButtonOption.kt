package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control.getComboButton

data class DropdownButtonOption(
  val key: String,
  val text: String,
  val onClick: (cb: () -> Unit) -> Unit
)