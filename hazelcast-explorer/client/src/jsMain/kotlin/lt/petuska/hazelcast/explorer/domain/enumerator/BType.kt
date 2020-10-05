package lt.petuska.hazelcast.explorer.domain.enumerator

internal enum class BType(private val typeName: String) {
  PRIMARY("primary"),
  SECONDARY("secondary"),
  SUCCESS("success"),
  DANGER("danger"),
  WARNING("warning"),
  INFO("info"),
  LIGHT("light"),
  DARK("dark");

  override fun toString(): String {
    return typeName
  }
}
