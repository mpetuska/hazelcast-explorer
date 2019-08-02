(function (_, Kotlin, $module$kotlin_extensions, $module$redux, $module$kotlinx_serialization_kotlinx_serialization_runtime) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var Unit = Kotlin.kotlin.Unit;
  var combineReducers = $module$redux.combineReducers;
  var bindActionCreators = $module$redux.bindActionCreators;
  var assign = $module$kotlin_extensions.kotlinext.js.assign_m9hcte$;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  var clone = $module$kotlin_extensions.kotlinext.js.clone_issdgt$;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var wrapFunction = Kotlin.wrapFunction;
  function get_state($receiver) {
    return $receiver.getState();
  }
  function combineReducers_0(reducers) {
    var obj = {};
    var tmp$;
    tmp$ = reducers.entries.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      obj[element.key] = element.value;
    }
    return combineReducers(obj);
  }
  function bindActionCreators_0(actionCreators, dispatch) {
    var result = LinkedHashMap_init();
    var obj = {};
    var tmp$;
    tmp$ = actionCreators.entries.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      obj[element.key] = element.value;
    }
    var $receiver = bindActionCreators(obj, dispatch);
    var $receiver_0 = Object.keys($receiver);
    var tmp$_0;
    for (tmp$_0 = 0; tmp$_0 !== $receiver_0.length; ++tmp$_0) {
      var element_0 = $receiver_0[tmp$_0];
      var value = $receiver[element_0];
      result.put_xwzc9p$(element_0, value);
    }
    return result;
  }
  function rEnhancer$lambda$lambda$wrapperReducer$lambda(closure$reducer) {
    return function (state, action) {
      return closure$reducer(state, action.action);
    };
  }
  function rEnhancer$lambda$lambda$wrapperReducer(reducer) {
    return rEnhancer$lambda$lambda$wrapperReducer$lambda(reducer);
  }
  function rEnhancer$lambda$lambda$lambda$lambda(closure$store) {
    return function (action) {
      var tmp$ = closure$store;
      var obj = {};
      obj.type = Kotlin.getKClassFromExpression(action).simpleName;
      obj.action = action;
      var result = tmp$.dispatch(obj);
      return result;
    };
  }
  function rEnhancer$lambda$lambda$lambda$lambda_0(closure$store, closure$wrapperReducer) {
    return function (nextReducer) {
      closure$store.replaceReducer(closure$wrapperReducer(nextReducer));
      return Unit;
    };
  }
  function rEnhancer$lambda$lambda(closure$next) {
    return function (reducer, initialState) {
      var wrapperReducer = rEnhancer$lambda$lambda$wrapperReducer;
      var store = closure$next(wrapperReducer(reducer), initialState);
      var obj = {};
      var $receiver = clone(assign(obj, store));
      $receiver.dispatch = rEnhancer$lambda$lambda$lambda$lambda(store);
      $receiver.replaceReducer = rEnhancer$lambda$lambda$lambda$lambda_0(store, wrapperReducer);
      return $receiver;
    };
  }
  function rEnhancer$lambda(next) {
    return rEnhancer$lambda$lambda(next);
  }
  function rEnhancer() {
    return rEnhancer$lambda;
  }
  function WrapperAction() {
  }
  WrapperAction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'WrapperAction',
    interfaces: []
  };
  function RAction() {
  }
  RAction.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'RAction',
    interfaces: []
  };
  var serializeAction = defineInlineFunction('kotlin-redux.redux.serializeAction_t1gu5c$', wrapFunction(function () {
    var getKClass = Kotlin.getKClass;
    var toString = Kotlin.toString;
    var Json = _.$$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'].kotlinx.serialization.json.Json;
    var drop = Kotlin.kotlin.text.drop_6ic1pp$;
    var getContextualOrDefault = _.$$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'].kotlinx.serialization.modules.getContextualOrDefault_6za9kt$;
    return function (A_0, isA, action) {
      var tmp$ = '{' + '"' + 'type' + '"' + ':' + '"' + toString(getKClass(A_0).simpleName) + '"' + ',';
      var $receiver = Json.Companion;
      return tmp$ + drop($receiver.stringify_tf03ej$(getContextualOrDefault($receiver.context, getKClass(A_0)), action), 1);
    };
  }));
  var deserializeAction = defineInlineFunction('kotlin-redux.redux.deserializeAction_g7azbc$', wrapFunction(function () {
    var Json = _.$$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'].kotlinx.serialization.json.Json;
    var internal = _.$$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'].kotlinx.serialization.internal;
    var to = Kotlin.kotlin.to_ujzrz7$;
    var get_map = _.$$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'].kotlinx.serialization.get_map_kgqhr1$;
    var getKClass = Kotlin.getKClass;
    var equals = Kotlin.equals;
    var getContextualOrDefault = _.$$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'].kotlinx.serialization.modules.getContextualOrDefault_6za9kt$;
    return function (A_0, isA, action) {
      var map = Json.Companion.parse_awif5v$(get_map(to(internal.StringSerializer, internal.StringSerializer)), action);
      var tmp$;
      if (equals(map.get_11rb$('type'), getKClass(A_0).simpleName)) {
        var $receiver = Json.Companion.nonstrict;
        tmp$ = $receiver.parse_awif5v$(getContextualOrDefault($receiver.context, getKClass(A_0)), action);
      }
       else
        tmp$ = null;
      return tmp$;
    };
  }));
  var package$redux = _.redux || (_.redux = {});
  package$redux.get_state_rw1iam$ = get_state;
  $$importsForInline$$['kotlin-extensions'] = $module$kotlin_extensions;
  package$redux.combineReducers_ip98tz$ = combineReducers_0;
  package$redux.bindActionCreators_sqanl0$ = bindActionCreators_0;
  package$redux.rEnhancer_287e2$ = rEnhancer;
  package$redux.WrapperAction = WrapperAction;
  package$redux.RAction = RAction;
  $$importsForInline$$['kotlinx-serialization-kotlinx-serialization-runtime'] = $module$kotlinx_serialization_kotlinx_serialization_runtime;
  Kotlin.defineModule('kotlin-redux', _);
  return _;
}(module.exports, require('kotlin'), require('kotlin-extensions'), require('redux'), require('kotlinx-serialization-kotlinx-serialization-runtime')));

//# sourceMappingURL=kotlin-redux.js.map
