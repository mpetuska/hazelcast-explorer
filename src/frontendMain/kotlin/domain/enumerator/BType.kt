package lt.petuska.hazelcast.explorer.domain.enumerator

enum class BType(val typeName: String) {
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