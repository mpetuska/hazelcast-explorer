package component.app.workerPage.mapPage.control.getComboButton

data class DropdownButtonOption(
    val key: String,
    val text: String,
    val onClick: (cb: () -> Unit) -> Unit,
    val onCancel: (() -> Unit)? = null
)