package lt.petuska.hazelcast.explorer.component.app.navbar

import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import react.*

interface NavbarProps : RProps {
  var title: String
  var theme: Theme
  var environments: List<EnvironmentDTO>?
  var selectedEnvironment: EnvironmentDTO?
  var selectedTarget: TargetDTO?
  var selectedExploreType: ExploreType?
}