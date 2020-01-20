package lt.petuska.hazelcast.explorer.component.app.workerPage

import kotlinx.html.UL
import kotlinx.html.js.onClickFunction
import lt.petuska.hazelcast.explorer.component.StatelessComponent
import lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.mapPage
import lt.petuska.hazelcast.explorer.component.app.workerPage.topicPage.topicPage
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.enumerator.PersistentProperty
import lt.petuska.hazelcast.explorer.manager.HashQueryManager
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.store
import react.RBuilder
import react.RClass
import react.RProps
import react.dom.RDOMBuilder
import react.dom.a
import react.dom.div
import react.dom.li
import react.dom.ul

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
            ExploreType.TOPIC -> if (!props.target?.topics.isNullOrEmpty()) {
              availableExploreType = it
              if (addTab(it)) activeTab = topicPage
            }
          }
        }
      }
      activeTab?.let { it {} }
        ?: availableExploreType?.let {
          store.dispatch(HzeAction.SelectExploreType(it))
        }
    }
  }
  
  private fun RDOMBuilder<UL>.addTab(exploreType: ExploreType): Boolean {
    val active = props.exploreType == exploreType
    li("nav-item") {
      a(
        HashQueryManager.buildHashLink(
          PersistentProperty.EXPLORE_TYPE to exploreType.name
        ), classes = "nav-link ${if (active) "active" else ""}"
      ) {
        attrs.onClickFunction = {
          store.dispatch(HzeAction.SelectExploreType(exploreType))
        }
        +"${exploreType.displayName} Explorer"
      }
    }
    return active
  }
  
  override fun componentWillUnmount() {
    store.dispatch(HzeAction.SelectExploreType(null))
  }
}