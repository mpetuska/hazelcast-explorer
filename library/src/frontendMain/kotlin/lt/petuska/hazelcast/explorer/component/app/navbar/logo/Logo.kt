package lt.petuska.hazelcast.explorer.component.app.navbar.logo

import kotlinext.js.require
import kotlinx.css.height
import kotlinx.css.px
import lt.petuska.hazelcast.explorer.component.StatelessComponent
import react.RBuilder
import react.RProps
import react.dom.img
import styled.css
import styled.styledSpan

@Suppress("UnsafeCastFromDynamic")
class Logo : StatelessComponent<RProps>() {
  private val reactSvgUri: String = require("img/react.svg").default
  private val kotlinSvgUri: String = require("img/kotlin.svg").default
  private val ktorPngUri: String = require("img/ktor.png").default
  
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
