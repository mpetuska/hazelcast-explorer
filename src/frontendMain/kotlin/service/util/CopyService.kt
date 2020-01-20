package lt.petuska.hazelcast.explorer.service.util

import org.w3c.dom.HTMLTextAreaElement
import kotlin.browser.document

object CopyService {
  fun copyTextToClipboard(text: String) {
    val el = document.createElement("textarea") as HTMLTextAreaElement
    el.value = text
    el.setAttribute("readonly", "")
    el.setAttribute("style", "position: 'absolute', left: '-9999px'")
    document.body?.appendChild(el)
    el.select()
    document.execCommand("copy")
    document.body?.removeChild(el)
  }
}