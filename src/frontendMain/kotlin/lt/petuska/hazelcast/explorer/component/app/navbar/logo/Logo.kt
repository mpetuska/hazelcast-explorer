package lt.petuska.hazelcast.explorer.component.app.navbar.logo

import kotlinext.js.*
import kotlinx.css.*
import lt.petuska.hazelcast.explorer.component.app.*
import react.*
import react.dom.*
import styled.*

class Logo : StatelessComponent<RProps>() {
  private val reactSvgUri: String = require("img/react.svg") as String
  private val kotlinSvgUri: String = require("img/kotlin.svg") as String
  private val ktorPngUri: String = require("img/ktor.png") as String

  init {
    require("css/logo.css")
  }

  override fun RBuilder.render() {
    styledSpan {
      css {
        height = 35.px
        classes.add("Logo")
      }
      img(src = reactSvgUri, classes = "Logo-react") {}
      img(src = kotlinSvgUri, classes = "Logo-kotlin") {}
      img(src = ktorPngUri, classes = "Logo-ktor") {}
    }
  }
}
