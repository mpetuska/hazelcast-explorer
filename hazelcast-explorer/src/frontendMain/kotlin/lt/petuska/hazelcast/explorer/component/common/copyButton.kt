package lt.petuska.hazelcast.explorer.component.common

import kotlinx.html.js.onClickFunction
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.service.util.CopyService
import react.RBuilder
import react.dom.button
import react.dom.span
import kotlin.browser.document

fun RBuilder.copyButton(copyText: String, type: BType = BType.INFO, afterCopy: (copiedText: String) -> Unit = {}) =
  if (document.queryCommandSupported("copy")) {
    button(classes = "btn btn-sm btn-outline-$type") {
      attrs {
        onClickFunction = {
          CopyService.copyTextToClipboard(copyText)
          afterCopy(copyText)
        }
      }
      span("far fa-copy") {}
    }
  } else null