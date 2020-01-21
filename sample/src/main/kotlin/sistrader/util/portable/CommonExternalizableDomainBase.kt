package sistrader.util.portable

import com.google.gson.Gson
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


/**
 * Abstract domain object implementing common support methods as well as {@link Externalizable} interface. Implemented methods:
 * <ul>
 *     <li>{@link Externalizable#writeExternal}</li>
 *     <li>{@link Externalizable#readExternal}</li>
 *     <li>{@link Object#hashCode}</li>
 *     <li>{@link Object#equals}</li>
 *     <li>{@link Object#toString}</li>
 * </ul>
 */
abstract class CommonExternalizableDomainBase : Externalizable {
  companion object {
    private val GSON = Gson()
  }
  
  override fun writeExternal(objectOutput: ObjectOutput) {
    objectOutput.writeUTF(this.toJson())
  }
  
  @Override
  override fun readExternal(objectInput: ObjectInput) {
    val newObj = GSON.fromJson(objectInput.readUTF(), this::class.java)
    newObj::class.memberProperties.filterIsInstance<KMutableProperty<*>>().forEach {
      val accessibility = it.isAccessible
      it.isAccessible = true
      it.setter.call(this, it.getter.call(newObj))
      it.isAccessible = accessibility
    }
  }
  
  /**
   * Parses this object into JSON via {@link Gson} library.
   * @return a JSON string representing this object.
   */
  protected fun toJson(): String {
    return GSON.toJson(this)
  }
}