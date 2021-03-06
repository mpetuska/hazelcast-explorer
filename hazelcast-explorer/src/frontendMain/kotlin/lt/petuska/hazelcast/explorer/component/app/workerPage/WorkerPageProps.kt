package lt.petuska.hazelcast.explorer.component.app.workerPage

import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import react.RProps

interface WorkerPageProps : RProps {
  var exploreType: ExploreType?
  var target: Target?
}