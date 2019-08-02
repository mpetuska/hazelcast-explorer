"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _mitsuketa = require("./mitsuketa");

var err = {
  getCaller: function getCaller() {
    var skip = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : 1;
    // A somewhat hacky solution that will yield different results in different JS engines. 
    // Since we only call this function when an error will actually be thrown we typically don't 
    // rally mind the performance impact this might have if called too often.
    // Lucky for us we use nodeJS and thus only V8.
    var stackTrace = new Error().stack;
    var callerName = stackTrace.replace(/^Error\s+/, '');
    callerName = callerName.split("\n")[skip];
    callerName = callerName.replace(/^\s+at Object./, '').replace(/^\s+at /, '').replace(/ \(.+\)$/, '');
    return callerName;
  },
  throwError: function throwError() {
    var fxName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : 'unknown function';
    var paramName = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 'unknown parameter';
    var expectation = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 'to be defined';
    throw ['@', fxName, '(): Expected parameter \'', paramName, '\' ', expectation].join('');
  },
  isUndefined: function isUndefined() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    if ([null, undefined].indexOf(param) > -1) err.throwError(err.getCaller(2), paramName);
  },
  isFalsy: function isFalsy() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    if (!param) err.throwError(err.getCaller(2), paramName);
  },
  isNoneOf: function isNoneOf() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    var contains = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : [];
    if (contains.indexOf(param) === -1) err.throwError(err.getCaller(2), paramName, 'to be any of' + JSON.stringify(contains));
  },
  isAnyOf: function isAnyOf() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    var contains = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : [];
    if (contains.indexOf(param) > -1) err.throwError(err.getCaller(2), paramName, 'not to be any of' + JSON.stringify(contains));
  },
  isNotType: function isNotType() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    var type = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : '';
    if ((0, _mitsuketa.getType)(param) !== type.toLowerCase()) err.throwError(err.getCaller(2), paramName, 'to be type ' + type.toLowerCase());
  },
  isAnyTypeOf: function isAnyTypeOf() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    var types = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : [];
    types.forEach(function (type) {
      if ((0, _mitsuketa.getType)(param) === type) err.throwError(err.getCaller(2), paramName, 'not to be type of ' + type.toLowerCase());
    });
  },
  missingKey: function missingKey() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    var keyName = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : '';
    err.isUndefined(paramName, param);
    if (Object.keys(param).indexOf(keyName) === -1) err.throwError(err.getCaller(2), paramName, 'to contain \'' + keyName + '\' key');
  },
  missingAnyKeys: function missingAnyKeys() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    var keyNames = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : [''];
    err.isUndefined(paramName, param);
    var keyList = Object.keys(param);
    keyNames.forEach(function (keyName) {
      if (keyList.indexOf(keyName) === -1) err.throwError(err.getCaller(2), paramName, 'to contain \'' + keyName + '\' key');
    });
  },
  containsUndefined: function containsUndefined() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    [undefined, null].forEach(function (value) {
      var location = (0, _mitsuketa.locate)(param, value);
      if (location) err.throwError(err.getCaller(2), paramName, 'not to contain \'' + JSON.stringify(value) + '\' at ' + location);
    });
  },
  isInvalidPath: function isInvalidPath() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    err.isUndefined(paramName, param);
    err.isNotType(paramName, param, 'string');
    err.isAnyOf(paramName, param, ['', '/']);
    '.$[]#'.split().forEach(function (invalidChar) {
      if (param.indexOf(invalidChar) > -1) err.throwError(err.getCaller(2), paramName, 'not to contain invalid character \'' + invalidChar + '\'');
    });
    if (param.match(/\/{2,}/g)) err.throwError(err.getCaller(2), paramName, 'not to contain consecutive forward slash characters');
  },
  isInvalidWriteData: function isInvalidWriteData() {
    var paramName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : '<unknown parameter>';
    var param = arguments.length > 1 ? arguments[1] : undefined;
    err.isUndefined(paramName, param);
    err.containsUndefined(paramName, param);
  }
};
var _default = err;
exports.default = _default;