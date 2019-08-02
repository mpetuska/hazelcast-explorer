package domain.route

import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.common.*

object BaseRoute : RouteDef<DTO>(null, "/api")
object MetaRoute : RouteDef<DTO>(BaseRoute, "/meta")
object HzeConfigRoute : RouteDef<HzeConfigDTO>(MetaRoute, "/hzeConfig")