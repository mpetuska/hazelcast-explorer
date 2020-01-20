package lt.petuska.hazelcast.explorer.component.app

import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.EnvironmentDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import react.RProps

interface AppProps : RProps {
  var title: String
  var theme: Theme
  var selectedEnvironment: EnvironmentDTO?
  var selectTarget: TargetDTO?
}