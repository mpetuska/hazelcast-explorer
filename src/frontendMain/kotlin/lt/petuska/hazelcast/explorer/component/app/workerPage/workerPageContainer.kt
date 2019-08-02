package lt.petuska.hazelcast.explorer.component.app.workerPage

import lt.petuska.hazelcast.explorer.util.*

val workerPage = connectClass<WorkerPage, WorkerPageProps>(
  mapStateToProps = { state ->
    exploreType = state.selectedExploreType
    target = state.selectedTarget
  }
)