package lt.petuska.hazelcast.explorer.redux

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinext.js.JsObject
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.Transient
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.withName
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import kotlin.js.Date

@Serializer(forClass = HttpMethod::class)
object HttpMethodSerializer : KSerializer<HttpMethod> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName("HttpMethodSerializer")
  override fun deserialize(decoder: Decoder): HttpMethod {
    val value = decoder.decodeString()
    return HttpMethod(value)
  }
  
  override fun serialize(encoder: Encoder, obj: HttpMethod) {
    encoder.encodeString(obj.value)
  }
}

@Serializer(forClass = HttpStatusCode::class)
object HttpStatusCodeSerializer : KSerializer<HttpStatusCode> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName("HttpStatusCodeSerializer")
  override fun deserialize(decoder: Decoder): HttpStatusCode {
    val value = decoder.decodeInt()
    return HttpStatusCode.fromValue(value)
  }
  
  override fun serialize(encoder: Encoder, obj: HttpStatusCode) {
    encoder.encodeInt(obj.value)
  }
}

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName("DateSerializer")
  override fun deserialize(decoder: Decoder): Date {
    val dateString = decoder.decodeString()
    return Date(dateString)
  }
  
  override fun serialize(encoder: Encoder, obj: Date) {
    encoder.encodeString(obj.toDateString())
  }
}

@Serializable
data class HzeState(
  val theme: Theme = Theme.DARK,
  val environment: Environment? = null,
  val target: Target? = null,
  val exploreType: ExploreType? = null,
  
  @Serializable(with = HttpMethodSerializer::class)
  val mapMethod: HttpMethod = HttpMethod.Get,
  val map: Map? = null,
  val mapKey: String? = null,
  val mapJson: String? = null,
//  @Serializable(with = HttpStatusCodeSerializer::class)
  @Transient
  val mapResponseStatus: HttpStatusCode? = null,
  @Transient
  val mapResponseJson: JsObject? = null,
  
  val topic: Topic? = null,
  @Transient
  val topicSubscription: (() -> Unit)? = null,
  @Transient
  val topicMessages: List<Pair<@Serializable(with = DateSerializer::class) Date, JsObject>> = listOf()
) {
  val Environment?.readOnly
    get() = this?.readOnly == true
  
  val Target?.readOnly
    get() = this?.readOnly == true || environment.readOnly
  
  val Map?.readOnly
    get() = this?.readOnly == true || environment.readOnly || target.readOnly
}