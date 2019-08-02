//package lt.petuska.hazelcast.explorer.service.entity.map
//
//import com.google.gson.*
//import io.ktor.features.*
//import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.*
//import lt.petuska.hazelcast.explorer.domain.common.*
//import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
//import lt.petuska.hazelcast.explorer.service.*
//import javax.inject.*
//
//actual class MapService(
//  private val gson: Gson,
//  private val clientService: HazelcastClientService,
//  environments: Set<EnvironmentDescriptor>
//) : IMapService {
//  private val targets = environments.flatMap { it.targets }
//
//  override suspend fun getAll(mapDTO: MapDTO): String {
//    return clientService.getClient(mapDTO.targetDescriptor).getMap<Any, Any>(mapDTO.name)
//      .let { gson.toJson(it) }
//  }
//
//  override suspend fun getAllKeys(mapDTO: MapDTO): List<String> {
//    return clientService.getClient(mapDTO.targetDescriptor).getMap<Any, Any>(mapDTO.name).keys.map { gson.toJson(it) }
//  }
//
//  override suspend fun getAllValues(mapDTO: MapDTO): List<String> {
//    return clientService.getClient(mapDTO.targetDescriptor).getMap<Any, Any>(mapDTO.name).values.map { gson.toJson(it) }
//  }
//
//  override suspend fun findByKey(mapDTO: MapDTO, key: String): NullableWrapper<String> {
//    return clientService.getClient(mapDTO.targetDescriptor).getMap<Any, Any>(mapDTO.name)[mapDTO.convert(key)].let {
//      it?.let { v -> gson.toJson(v) }
//    }.wrapNullable()
//  }
//
//  private fun MapDTO.convert(key: String): Any {
//    val keyType = targetDescriptor.maps.find { it.toDTO() == this }?.keyType
//      ?: throw NotFoundException("Map [$name] not found in [($environment) $target]")
//    return gson.fromJson(key, keyType.java)
//  }
//
//  private val MapDTO.targetDescriptor
//    get() = targets.find { it.environment == environment && it.name == target }
//      ?: throw NotFoundException("Target [$target] not found")
//}