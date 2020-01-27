package lt.petuska.hazelcast.explorer.component.app

import lt.petuska.hazelcast.explorer.component.StatelessComponent
import lt.petuska.hazelcast.explorer.component.app.navbar.navbar
import lt.petuska.hazelcast.explorer.component.app.workerPage.workerPage
import react.RBuilder
import react.dom.div
import react.dom.h4

class App(appProps: AppProps) : StatelessComponent<AppProps>(appProps) {
  override fun RBuilder.render() {
    navbar {}
    div("container") {
      when {
        props.selectedEnvironment == null -> {
          h4("text-center") { +"Please select an environment & target to explore" }
        }
        props.selectTarget == null -> {
          h4("text-center") { +"Please select a target to explore" }
        }
        else -> workerPage {}
      }
    }
  }
}