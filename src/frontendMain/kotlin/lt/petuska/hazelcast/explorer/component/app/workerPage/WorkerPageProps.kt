package lt.petuska.hazelcast.explorer.component.app.workerPage

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import react.*

interface WorkerPageProps : RProps {
  var exploreType: ExploreType?
  var target: TargetDTO?
}