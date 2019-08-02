package lt.petuska.hazelcast.explorer.service.util

import kotlinext.js.*

object NotificationService {
  fun info(message: String) = send(message, NotificationService.NotificationType.INFO)
  fun success(message: String) = send(message, NotificationService.NotificationType.SUCCESS)
  fun warning(message: String) = send(message, NotificationService.NotificationType.WARNING)
  fun error(message: String) = send(message, NotificationService.NotificationType.ERROR)

  fun send(message: String, type: NotificationType) {
    val notificationOptions = jsObject<dynamic> {
      this.message = message
    }
    val notificationSettings = jsObject<dynamic> {
      this.type = type.value
      placement = jsObject<dynamic> {
        from = "bottom"
        align = "right"
      }
      spacing = 5
      delay = 5000
      mouse_over = "pause"
      newest_on_top = true
      allow_dismiss = true
      animate = jsObject<dynamic> {
        enter = "animated fadeInUp"
        exit = "animated fadeOutUp"
      }
    }
    js("$").notify(notificationOptions, notificationSettings)
  }

  enum class NotificationType(val value: String) {
    INFO("info"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("danger")
  }
}