package lt.petuska.hazelcast.explorer.serialization

import kotlin.reflect.KClass

public expect class SerializableKClass<T : Any>(simpleName: String?, qualifiedName: String?) : KClass<T>
