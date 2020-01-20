package lt.petuska.hazelcast.explorer.component

import react.RComponent
import react.RProps
import react.RState

abstract class StatelessComponent<P : RProps> : RComponent<P, RState> {
  constructor() : super()
  constructor(props: P) : super(props)
}