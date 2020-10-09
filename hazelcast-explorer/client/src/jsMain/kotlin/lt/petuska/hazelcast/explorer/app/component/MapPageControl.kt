package lt.petuska.hazelcast.explorer.app.component

import dev.fritz2.binding.const
import dev.fritz2.dom.Tag
import dev.fritz2.dom.html.HtmlElements
import kotlinx.coroutines.flow.map
import lt.petuska.hazelcast.explorer.app.router
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.domain.enumerator.HttpMethod
import lt.petuska.hazelcast.explorer.store.SelectionStore

internal fun HtmlElements.mapPageControl() {
  form("form-inline justify-content-center") {
    httpMethodSelector()
    mapSelector()
    // keyInput()
    // actionButton()
  }
  metadataPanel()
  // jsonInput()
  // when (props.selectedHttpMethod) {
  //   HttpMethod.Post,
  //   HttpMethod.Put -> jsonInput()
  // }
}

private fun Tag<*>.httpMethodSelector() = selector(
  classes = "m-1",
  required = const(true),
  value = router.map { "${it.httpMethod}" },
  values = router.map { HttpMethod.values().filter { t -> t.isValid(it.target, it.map) }.map { t -> "$t" to "$t" } },
  onChange = SelectionStore.selectHttpMethod
)

private fun Tag<*>.mapSelector() = selector(
  classes = "m-1",
  required = const(true),
  placeholder = "Select map...",
  value = router.map { it.map?.name },
  values = router.map { it.target?.maps?.map { t -> t.name to t.displayName } },
  onChange = SelectionStore.selectMap
)

private fun HtmlElements.metadataPanel() = div("d-flex justify-content-center") {
  labeledPillBox(
    "Key Type:",
    router.map { if (it.map?.readOnly == true) BType.WARNING else BType.INFO },
    router.map { it.map?.keyType?.simpleName ?: "N/A" },
    router.map { it.map?.keyType?.simpleName == null },
  )

  labeledPillBox(
    "Value Type:",
    router.map { if (it.map?.readOnly == true) BType.WARNING else BType.INFO },
    router.map { it.map?.valueType?.simpleName ?: "N/A" },
    router.map { it.map?.valueType?.simpleName == null },
  )
}
