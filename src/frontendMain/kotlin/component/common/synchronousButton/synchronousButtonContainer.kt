package lt.petuska.hazelcast.explorer.component.common.synchronousButton

import lt.petuska.hazelcast.explorer.util.*

val synchronousButton = connectClass<SynchronousButton, SynchronousButtonProps>(
    mapStateToProps = { _, ownProps ->
      onClick = ownProps.onClick
      text = ownProps.text
      type = ownProps.type
      onClick = ownProps.onClick
      classes = ownProps.classes.ifUndefined("")
      disabled = ownProps.disabled.ifUndefined(false)
      key = ownProps.key
    }
)