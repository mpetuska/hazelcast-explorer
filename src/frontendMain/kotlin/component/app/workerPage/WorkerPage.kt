package lt.petuska.hazelcast.explorer.component.app.workerPage

import kotlinx.html.*
import kotlinx.html.js.*
import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.component.app.*
import lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.*
import lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.manager.*
import lt.petuska.hazelcast.explorer.redux.*
import react.*
import react.dom.*

class WorkerPage(props: WorkerPageProps) : StatelessComponent<WorkerPageProps>(props) {
  override fun RBuilder.render() {
    var availableExploreType: ExploreType? = null
    var activeTab: RClass<RProps>? = null
    div("container") {
      ul("nav nav-tabs nav-fill mb-3") {
        ExploreType.values().forEach {
          when (it) {
            ExploreType.MAP -> if (!props.target?.maps.isNullOrEmpty()) {
              availableExploreType = it
              if (addTab(it)) activeTab = mapPage
            }
            //TODO Topics
            ExploreType.TOPIC -> if (!props.target?.topics.isNullOrEmpty()) {
              availableExploreType = it
              if (addTab(it)) activeTab = topicPage
            }
          }
        }
      }
      activeTab?.let { it {} }
          ?: availableExploreType?.let {
            store.dispatch(HZEAction.SelectExploreType(it))
          }
    }
  }

  private fun RDOMBuilder<UL>.addTab(exploreType: ExploreType): Boolean {
    val active = props.exploreType == exploreType
    li("nav-item") {
      a(
          HashQueryManager.buildHashLink(
              HashQueryManager.HashProperty.EXPLORE_TYPE to exploreType.name
          ), classes = "nav-link ${if (active) "active" else ""}"
      ) {
        attrs.onClickFunction = {
          store.dispatch(HZEAction.SelectExploreType(exploreType))
        }
        +"${exploreType.displayName} Explorer"
      }
    }
    return active
  }
}