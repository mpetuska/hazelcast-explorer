package lt.petuska.hazelcast.explorer.types.jsonInput


class JSONInputColors(
  var default: String? = null,
  var string: String? = null,
  var number: String? = null,
  var colon: String? = null,
  var keys: String? = null,
  var keys_whiteSpace: String? = null,
  var primitive: String? = null,
  var error: String? = null,
  var background: String? = null,
  var background_warning: String? = null
)

val IntelliJColors = JSONInputColors(
  default = "#A7B6C6",
  
  string = "#008000",
  number = "#0000FF",
  colon = "#000080",
  keys = "#660E7A",
  keys_whiteSpace = "#660E7A",
  primitive = "#000080",
  
  error = "#FF0000",
  background = "#F0F0F0",
  background_warning = "#FFFFFF"
)

val IntelliJDarculaColors = JSONInputColors(
  default = "#A7B6C6",
  string = "#6A8759",
  number = "#6897BB",
  colon = "#CC7832",
  keys = "#9876AA",
  keys_whiteSpace = "#9876AA",
  primitive = "#CC7832",
  error = "#FF0000",
  background = "#2B2B2B",
  background_warning = "#313335"
)