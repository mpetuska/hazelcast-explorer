package lt.petuska.hazelcast.explorer.component.app

import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import react.*

interface AppProps : RProps {
  var title: String
  var theme: Theme
  var selectedEnvironment: EnvironmentDTO?
  var selectTarget: TargetDTO?
}