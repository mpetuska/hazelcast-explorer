package lt.petuska.hazelcast.explorer.component.app

import react.*

abstract class StatelessComponent<P : RProps> : RComponent<P, RState> {
  constructor() : super()
  constructor(props: P) : super(props)

  companion object {}
}