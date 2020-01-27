package lt.petuska.hazelcast.explorer.component.app

import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import react.RProps

interface AppProps : RProps {
  var selectedEnvironment: Environment?
  var selectTarget: Target?
}