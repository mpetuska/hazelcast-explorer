package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import lt.petuska.hazelcast.explorer.component.*
import lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control.*
import react.*
import react.dom.*

class MapPage(props: MapPageProps) : StatelessComponent<MapPageProps>(props) {

  override fun RBuilder.render() {
    div("container") {
      mapPageControl {}
    }
  }
}
