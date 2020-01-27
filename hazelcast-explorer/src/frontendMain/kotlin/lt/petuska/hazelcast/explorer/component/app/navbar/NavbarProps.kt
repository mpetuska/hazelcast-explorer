package lt.petuska.hazelcast.explorer.component.app.navbar

import io.ktor.http.HttpMethod
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import react.RProps

interface NavbarProps : RProps {
  var theme: Theme
  var selectedEnvironment: Environment?
  var selectedTarget: Target?
  var selectedExploreType: ExploreType?
  var selectedMethod: HttpMethod
}