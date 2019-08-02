(function(root, factory) {
  if (typeof define === 'function' && define.amd) 
    define(['exports', 'kotlin', 'kotlinx-serialization-kotlinx-serialization-runtime', 'ktor-ktor-http', 'kotlinx-io', 'ktor-ktor-client-json'], factory);
  else if (typeof exports === 'object') 
    factory(module.exports, require('kotlin'), require('kotlinx-serialization-kotlinx-serialization-runtime'), require('ktor-ktor-http'), require('kotlinx-io'), require('ktor-ktor-client-json'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'ktor-ktor-client-serialization'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'ktor-ktor-client-serialization'.");
    }
    if (typeof this['kotlinx-serialization-kotlinx-serialization-runtime'] === 'undefined') {
      throw new Error("Error loading module 'ktor-ktor-client-serialization'. Its dependency 'kotlinx-serialization-kotlinx-serialization-runtime' was not found. Please, check whether 'kotlinx-serialization-kotlinx-serialization-runtime' is loaded prior to 'ktor-ktor-client-serialization'.");
    }
    if (typeof this['ktor-ktor-http'] === 'undefined') {
      throw new Error("Error loading module 'ktor-ktor-client-serialization'. Its dependency 'ktor-ktor-http' was not found. Please, check whether 'ktor-ktor-http' is loaded prior to 'ktor-ktor-client-serialization'.");
    }
    if (typeof this['kotlinx-io'] === 'undefined') {
      throw new Error("Error loading module 'ktor-ktor-client-serialization'. Its dependency 'kotlinx-io' was not found. Please, check whether 'kotlinx-io' is loaded prior to 'ktor-ktor-client-serialization'.");
    }
    if (typeof this['ktor-ktor-client-json'] === 'undefined') {
      throw new Error("Error loading module 'ktor-ktor-client-serialization'. Its dependency 'ktor-ktor-client-json' was not found. Please, check whether 'ktor-ktor-client-json' is loaded prior to 'ktor-ktor-client-serialization'.");
    }
    root['ktor-ktor-client-serialization'] = factory(typeof this['ktor-ktor-client-serialization'] === 'undefined' ? {} : this['ktor-ktor-client-serialization'], kotlin, this['kotlinx-serialization-kotlinx-serialization-runtime'], this['ktor-ktor-http'], this['kotlinx-io'], this['ktor-ktor-client-json']);
  }
}(this, function(_, Kotlin, $module$kotlinx_serialization_kotlinx_serialization_runtime, $module$ktor_ktor_http, $module$kotlinx_io, $module$ktor_ktor_client_json) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var KSerializer = $module$kotlinx_serialization_kotlinx_serialization_runtime.kotlinx.serialization.KSerializer;
  var throwCCE = Kotlin.throwCCE;
  var KClass = Kotlin.kotlin.reflect.KClass;
  var get_list = $module$kotlinx_serialization_kotlinx_serialization_runtime.kotlinx.serialization.get_list_gekvwj$;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var wrapFunction = Kotlin.wrapFunction;
  var serializer = $module$kotlinx_serialization_kotlinx_serialization_runtime.kotlinx.serialization.serializer_1yb8b7$;
  var TextContent = $module$ktor_ktor_http.io.ktor.http.content.TextContent;
  var readText = $module$kotlinx_io.kotlinx.io.core.readText_q10u79$;
  var List = Kotlin.kotlin.collections.List;
  var defaultSerializer = $module$kotlinx_serialization_kotlinx_serialization_runtime.kotlinx.serialization.internal.defaultSerializer_1yb8b7$;
  var kotlin_js_internal_StringCompanionObject = Kotlin.kotlin.js.internal.StringCompanionObject;
  var serializer_0 = $module$kotlinx_serialization_kotlinx_serialization_runtime.kotlinx.serialization.serializer_6eet4j$;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var Json = $module$kotlinx_serialization_kotlinx_serialization_runtime.kotlinx.serialization.json.Json;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var JsonSerializer = $module$ktor_ktor_client_json.io.ktor.client.features.json.JsonSerializer;
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  var json = $module$ktor_ktor_client_json.io.ktor.client.features.json;
  function KotlinxSerializer(json) {
    KotlinxSerializer$Companion_getInstance();
    if (json === void 0) 
      json = Json.Companion.plain;
    this.json_0 = json;
    this.mappers_0 = LinkedHashMap_init();
    this.listMappers_0 = LinkedHashMap_init();
  }
  KotlinxSerializer.prototype.setMapper_cfhkba$ = function(type, serializer) {
  var tmp$, tmp$_0, tmp$_1, tmp$_2;
  tmp$_2 = this.mappers_0;
  tmp$_1 = Kotlin.isType(tmp$_0 = type, KClass) ? tmp$_0 : throwCCE();
  var value = Kotlin.isType(tmp$ = serializer, KSerializer) ? tmp$ : throwCCE();
  tmp$_2.put_xwzc9p$(tmp$_1, value);
};
  KotlinxSerializer.prototype.setListMapper_cfhkba$ = function(type, serializer) {
  var tmp$;
  var $receiver = this.listMappers_0;
  var value = Kotlin.isType(tmp$ = get_list(serializer), KSerializer) ? tmp$ : throwCCE();
  $receiver.put_xwzc9p$(type, value);
};
  KotlinxSerializer.prototype.register_ewacr1$ = defineInlineFunction('ktor-ktor-client-serialization.io.ktor.client.features.json.serializer.KotlinxSerializer.register_ewacr1$', wrapFunction(function() {
  var getKClass = Kotlin.getKClass;
  return function(T_0, isT, mapper) {
  this.setMapper_cfhkba$(getKClass(T_0), mapper);
};
}));
  KotlinxSerializer.prototype.registerList_ewacr1$ = defineInlineFunction('ktor-ktor-client-serialization.io.ktor.client.features.json.serializer.KotlinxSerializer.registerList_ewacr1$', wrapFunction(function() {
  var getKClass = Kotlin.getKClass;
  return function(T_0, isT, mapper) {
  this.setListMapper_cfhkba$(getKClass(T_0), mapper);
};
}));
  KotlinxSerializer.prototype.register_30y1fr$ = defineInlineFunction('ktor-ktor-client-serialization.io.ktor.client.features.json.serializer.KotlinxSerializer.register_30y1fr$', wrapFunction(function() {
  var getKClass = Kotlin.getKClass;
  var serializer = _.$$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'].kotlinx.serialization.serializer_1yb8b7$;
  return function(T_0, isT) {
  var mapper = serializer(getKClass(T_0));
  this.setMapper_cfhkba$(getKClass(T_0), mapper);
};
}));
  KotlinxSerializer.prototype.registerList_30y1fr$ = defineInlineFunction('ktor-ktor-client-serialization.io.ktor.client.features.json.serializer.KotlinxSerializer.registerList_30y1fr$', wrapFunction(function() {
  var getKClass = Kotlin.getKClass;
  var serializer = _.$$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'].kotlinx.serialization.serializer_1yb8b7$;
  return function(T_0, isT) {
  var mapper = serializer(getKClass(T_0));
  this.setListMapper_cfhkba$(getKClass(T_0), mapper);
};
}));
  KotlinxSerializer.prototype.write_ydd6c4$ = function(data, contentType) {
  var tmp$;
  var serializer = this.lookupSerializerByData_0(data);
  var content = this.json_0.stringify_tf03ej$(Kotlin.isType(tmp$ = serializer, KSerializer) ? tmp$ : throwCCE(), data);
  return new TextContent(content, contentType);
};
  KotlinxSerializer.prototype.read_2ov5dd$ = function(type, body) {
  var tmp$;
  var mapper = this.lookupSerializerByType_0(type.type);
  var text = readText(body);
  return this.json_0.parse_awif5v$(Kotlin.isType(tmp$ = mapper, KSerializer) ? tmp$ : throwCCE(), text);
};
  KotlinxSerializer.prototype.lookupSerializerByData_0 = function(data) {
  var tmp$, tmp$_0, tmp$_1;
  if (Kotlin.isType(data, List)) {
    var firstOrNull$result;
    firstOrNull$break:
      do {
        var tmp$_2;
        tmp$_2 = data.iterator();
        while (tmp$_2.hasNext()) {
          var element = tmp$_2.next();
          if (element != null) {
            firstOrNull$result = element;
            break firstOrNull$break;
          }
        }
        firstOrNull$result = null;
      } while (false);
    var item = firstOrNull$result;
    return (tmp$ = item != null ? this.listMappers_0.get_11rb$(Kotlin.getKClassFromExpression(item)) : null) != null ? tmp$ : KotlinxSerializer$Companion_getInstance().EMPTY_LIST_SERIALIZER_0;
  }
  var type = Kotlin.getKClassFromExpression(data);
  if ((tmp$_0 = this.mappers_0.get_11rb$(type)) != null) {
    return tmp$_0;
  }
  return (tmp$_1 = defaultSerializer(type)) != null ? tmp$_1 : serializer(type);
};
  KotlinxSerializer.prototype.lookupSerializerByType_0 = function(type) {
  var tmp$, tmp$_0;
  if ((tmp$ = this.mappers_0.get_11rb$(type)) != null) {
    return tmp$;
  }
  return (tmp$_0 = defaultSerializer(type)) != null ? tmp$_0 : serializer(type);
};
  function KotlinxSerializer$Companion() {
    KotlinxSerializer$Companion_instance = this;
    this.EMPTY_LIST_SERIALIZER_0 = get_list(serializer_0(kotlin_js_internal_StringCompanionObject));
  }
  KotlinxSerializer$Companion.$metadata$ = {
  kind: Kind_OBJECT, 
  simpleName: 'Companion', 
  interfaces: []};
  var KotlinxSerializer$Companion_instance = null;
  function KotlinxSerializer$Companion_getInstance() {
    if (KotlinxSerializer$Companion_instance === null) {
      new KotlinxSerializer$Companion();
    }
    return KotlinxSerializer$Companion_instance;
  }
  KotlinxSerializer.$metadata$ = {
  kind: Kind_CLASS, 
  simpleName: 'KotlinxSerializer', 
  interfaces: [JsonSerializer]};
  var initializer;
  function SerializerInitializer() {
    SerializerInitializer_instance = this;
    var $receiver = json.serializersStore;
    var element = new KotlinxSerializer();
    $receiver.add_11rb$(element);
  }
  SerializerInitializer.$metadata$ = {
  kind: Kind_OBJECT, 
  simpleName: 'SerializerInitializer', 
  interfaces: []};
  var SerializerInitializer_instance = null;
  function SerializerInitializer_getInstance() {
    if (SerializerInitializer_instance === null) {
      new SerializerInitializer();
    }
    return SerializerInitializer_instance;
  }
  $$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'] = $module$kotlinx_serialization_kotlinx_serialization_runtime;
  $$importsForInline$$['ktor-ktor-client-serialization'] = _;
  Object.defineProperty(KotlinxSerializer, 'Companion', {
  get: KotlinxSerializer$Companion_getInstance});
  var package$io = _.io || (_.io = {});
  var package$ktor = package$io.ktor || (package$io.ktor = {});
  var package$client = package$ktor.client || (package$ktor.client = {});
  var package$features = package$client.features || (package$client.features = {});
  var package$json = package$features.json || (package$features.json = {});
  var package$serializer = package$json.serializer || (package$json.serializer = {});
  package$serializer.KotlinxSerializer = KotlinxSerializer;
  Object.defineProperty(_, 'initializer', {
  get: function() {
  return initializer;
}});
  Object.defineProperty(_, 'SerializerInitializer', {
  get: SerializerInitializer_getInstance});
  KotlinxSerializer.prototype.write_za3rmp$ = JsonSerializer.prototype.write_za3rmp$;
  initializer = SerializerInitializer_getInstance();
  Kotlin.defineModule('ktor-ktor-client-serialization', _);
  return _;
}));
