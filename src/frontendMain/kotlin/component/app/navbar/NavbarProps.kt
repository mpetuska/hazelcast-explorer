package lt.petuska.hazelcast.explorer.component.app.navbar

import io.ktor.http.HttpMethod
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.EnvironmentDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import react.RProps

interface NavbarProps : RProps {
  var title: String
  var theme: Theme
  var environments: List<EnvironmentDTO>?
  var selectedEnvironment: EnvironmentDTO?
  var selectedTarget: TargetDTO?
  var selectedExploreType: ExploreType?
  var selectedMethod: HttpMethod
}