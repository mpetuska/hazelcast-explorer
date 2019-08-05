package lt.petuska.hazelcast.explorer.component

import react.*

abstract class StatelessComponent<P : RProps> : RComponent<P, RState> {
  constructor() : super()
  constructor(props: P) : super(props)
}