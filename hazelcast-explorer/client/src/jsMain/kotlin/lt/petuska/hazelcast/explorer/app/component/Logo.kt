package lt.petuska.hazelcast.explorer.app.component

import dev.fritz2.binding.const
import dev.fritz2.dom.html.HtmlElements
import lt.petuska.hazelcast.explorer.util.require

private val fritzPngUri = require("img/fritz.png")
private val kotlinPngUri = require("img/kotlin.png")
private val ktorPngUri = require("img/ktor.png")

internal fun HtmlElements.logo() = span("Logo mr-2 ml-2") {
  img("Logo-fritz") {
    src = const(fritzPngUri)
  }
  img("Logo-kotlin") {
    src = const(kotlinPngUri)
  }
  img("Logo-ktor") {
    src = const(ktorPngUri)
  }
}
