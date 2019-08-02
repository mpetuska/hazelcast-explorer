"use strict";

var _interopRequireDefault = require("@babel/runtime/helpers/interopRequireDefault");

var _typeof2 = _interopRequireDefault(require("@babel/runtime/helpers/typeof"));

/**
 * @author Andrew Redican <andrew.redican.mejia@gmail.com>
 */

/**
 * Performs deep search on object tree, removes all properties with matching key, returns a new identity without the specified property
 * @param {Any} identity
 * @param {string} keyName
 * @param {Optional Number} maxDepth
 * @return {Any} identity
 */
function _deepRemoveAll_Key(identity, keyName, maxDepth) {
  if (_getType(keyName) !== 'string') return undefined;
  if (keyName === '') return undefined;

  var clonedIdentity = _deepClone2(identity);

  var paths = _locateAll_Key2(clonedIdentity, keyName, maxDepth);

  if (paths === [] || paths === false) return clonedIdentity;
  paths.forEach(function (path) {
    if (path === '') path = keyName;else path += '.' + keyName;
    path = path.split('.');
    var ref = clonedIdentity;
    if (!Array.isArray(path)) delete ref[path];

    for (var i = 0; i < path.length; i++) {
      var key = path[i];

      if (key in ref) {
        if (i < path.length - 1) ref = ref[key];else delete ref[key];
      } else break;
    }
  });
  return clonedIdentity;
}
/**
 * Performs deep search on object tree, removes the first property with matching key, returns a new identity without the specified property
 * @param {Any} identity
 * @param {string} keyName
 * @param {Optional Number} maxDepth
 * @return {Any} identity
 */


function _deepRemove_Key(identity, keyName, maxDepth) {
  if (_getType(keyName) !== 'string') return undefined;
  if (keyName === '') return undefined;

  var clonedIdentity = _deepClone2(identity);

  var path = _locate_Key2(clonedIdentity, keyName, maxDepth);

  if (path === false) return clonedIdentity;
  if (path === '') path = keyName;else path += '.' + keyName;
  path = path.split('.');
  var ref = clonedIdentity;
  if (!Array.isArray(path)) delete ref[path];
  path.forEach(function (key, i) {
    if (i < path.length - 1) ref = ref[key];else delete ref[key];
  });
  return clonedIdentity;
}
/**
 * Performs deep search on object tree, and renames the all matching keys
 * @param {Any} identity
 * @param {string} keyName
 * @param {string} newKeyName
 * @param {Optional Number} maxDepth
 * @return {Any} identity
 */


function _renameKeys2(identity, keyName, newKeyName) {
  var maxDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : null;
  if (_getType(keyName) !== 'string') return undefined;
  if (_getType(newKeyName) !== 'string') return undefined;
  if (keyName === '') return undefined;
  if (newKeyName === '') return undefined;

  function _renameKeys(identity, keyName, newKeyName, maxDepth) {
    var currentDepth = arguments.length > 4 && arguments[4] !== undefined ? arguments[4] : 0;
    var keys;

    switch (_getType(identity)) {
      case 'array':
        var Arr = [];
        keys = Object.keys(identity);

        for (var i = 0, l = keys.length; i < l; i++) {
          var key = keys[i],
              subIdentity = identity[key];
          Arr[key] = _renameKeys(subIdentity, keyName, newKeyName, maxDepth, currentDepth + 1);
        }

        return Arr;

      case 'object':
        var Obj = {};
        keys = Object.keys(identity);

        for (var i = 0, l = keys.length; i < l; i++) {
          var _key = keys[i],
              _subIdentity = identity[_key];
          if (maxDepth !== null ? currentDepth < maxDepth : true) if (_key === keyName) _key = newKeyName;
          Obj[_key] = _renameKeys(_subIdentity, keyName, newKeyName, maxDepth, currentDepth + 1);
        }

        return Obj;

      case 'string':
        return '' + identity;

      case 'number':
        return 0 + identity;

      case 'boolean':
        if (identity) return true;
        return false;

      case 'null':
        return null;

      case 'undefined':
        return undefined;
    }
  }

  return _renameKeys(identity, keyName, newKeyName, maxDepth, 0);
}
/**
 * Performs deep search on object tree, then renames the first matching key
 * @param {Any} identity
 * @param {string} keyName
 * @param {string} newKeyName
 * @param {Optional Number} maxDepth
 * @return {Any} identity
 */


function _renameKey2(identity, keyName, newKeyName) {
  var maxDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : null;
  if (_getType(keyName) !== 'string') return undefined;
  if (_getType(newKeyName) !== 'string') return undefined;
  if (keyName === '') return undefined;
  if (newKeyName === '') return undefined;
  var applied = false;

  function _renameKey(identity, keyName, newKeyName, maxDepth) {
    var currentDepth = arguments.length > 4 && arguments[4] !== undefined ? arguments[4] : 0;
    var keys;

    switch (_getType(identity)) {
      case 'array':
        var Arr = [];
        keys = Object.keys(identity);

        for (var i = 0, l = keys.length; i < l; i++) {
          var key = keys[i],
              subIdentity = identity[key];
          Arr[key] = _renameKey(subIdentity, keyName, newKeyName, maxDepth, currentDepth + 1);
        }

        return Arr;

      case 'object':
        var Obj = {};
        keys = Object.keys(identity);

        for (var i = 0, l = keys.length; i < l; i++) {
          var _key2 = keys[i],
              _subIdentity2 = identity[_key2];
          if (maxDepth !== null ? currentDepth < maxDepth : true) if (!applied) if (_key2 === keyName) {
            _key2 = newKeyName;
            applied = true;
          }
          Obj[_key2] = _renameKey(_subIdentity2, keyName, newKeyName, maxDepth, currentDepth + 1);
        }

        return Obj;

      case 'string':
        return '' + identity;

      case 'number':
        return 0 + identity;

      case 'boolean':
        if (identity) return true;
        return false;

      case 'null':
        return null;

      case 'undefined':
        return undefined;
    }
  }

  return _renameKey(identity, keyName, newKeyName, maxDepth, 0);
}
/**
 * Creates a non-reference clone that is an exact copy to the identity provided.
 * @param {Any} identity
 * @param {Optional Number} maxDepth
 * @param {Optional Number} startDepth
 * @return {Any} identity
 */


function _deepClone2(identity) {
  var maxDepth = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : null;
  var startDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  var R = [];

  function _deepClone(identity, maxDepth, startDepth) {
    var currentDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : 0;
    var keys;

    if (startDepth !== null ? currentDepth < startDepth : false) {
      if (_isIterable(identity)) {
        keys = Object.keys(identity);
        keys.forEach(function (key) {
          _deepClone(identity[key], maxDepth, startDepth, currentDepth + 1);
        });
      }

      return;
    }

    if (startDepth !== null ? currentDepth == startDepth : false) {
      if (startDepth == 0) {
        R = _deepClone(identity, maxDepth, null, currentDepth);
        return;
      }

      if (_isIterable(identity)) R.push(_deepClone(identity, maxDepth, startDepth, currentDepth + 1));
      return;
    }

    switch (_getType(identity)) {
      case 'array':
        var Arr = [];
        keys = Object.keys(identity);
        if (maxDepth !== null ? currentDepth < maxDepth : true) for (var i = 0, l = keys.length; i < l; i++) {
          var key = keys[i],
              subIdentity = identity[key];
          Arr[key] = _deepClone(subIdentity, maxDepth, startDepth, currentDepth + 1);
        }
        return Arr;

      case 'object':
        var Obj = {};
        keys = Object.keys(identity);
        if (maxDepth !== null ? currentDepth < maxDepth : true) for (var i = 0, l = keys.length; i < l; i++) {
          var _key3 = keys[i],
              _subIdentity3 = identity[_key3];
          Obj[_key3] = _deepClone(_subIdentity3, maxDepth, startDepth, currentDepth + 1);
        }
        return Obj;

      case 'string':
        return '' + identity;

      case 'number':
        return 0 + identity;

      case 'boolean':
        if (identity) return true;
        return false;

      case 'null':
        return null;

      case 'undefined':
        return undefined;
    }
  }

  if (startDepth === null) return _deepClone(identity, maxDepth, startDepth, 0);

  _deepClone(identity, maxDepth, startDepth, 0);

  return R;
}
/**
 * Performs deep search on collection to find all matches to the key name, and returns a list of identities containing the matched instances. If no matches found, it returns `undefined`.
 * @param {Any} collection
 * @param {Any} keyName
 * @param {Optional Number} maxDepth
 * @return {Array || undefined} Identities
 */


function _deepFilter_Key(collection, keyName) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  if (_getType(keyName) !== 'string') return undefined;
  if (keyName === '') return undefined;

  var paths = _locateAll_Key2(collection, keyName, maxDepth);

  if (paths === false) return undefined;
  var results = paths.map(function (path) {
    if (path === false) return undefined;
    if (path === '') path = keyName;else path += '.' + keyName;
    path = path.split('.');
    var result = collection;
    if (!Array.isArray(path)) return result[path];
    path.forEach(function (key) {
      result = result[key];
    });
    return result;
  });
  return results;
}
/**
 * Performs deep search on collection to find all matches to the key name, returns the location of each match in a string array. If no matches found, it returns `false`.
 * @param {Any} collection
 * @param {Any} keyName
 * @param {Optional Number} maxDepth
 * @return {Array || false} Paths
 */


function _locateAll_Key2(collection, keyName) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  if (_getType(keyName) !== 'string') return undefined;
  if (keyName === '') return undefined;
  var R = [];

  function _locateAll_Key(collection, keyName) {
    var xKey = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : '';
    var path = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : '';
    var maxDepth = arguments.length > 4 && arguments[4] !== undefined ? arguments[4] : null;
    var currentDepth = arguments.length > 5 && arguments[5] !== undefined ? arguments[5] : 0;
    if (xKey === keyName) R[R.length] = path;
    var result = false;
    if (maxDepth !== null) if (currentDepth >= maxDepth) return result;
    if (_isIterable(collection)) for (var i = 0, keys = Object.keys(collection), l = keys.length; i < l; i++) {
      var key = keys[i],
          subcollection = collection[key];

      _locateAll_Key(subcollection, keyName, key, (path === '' ? path : path + '.') + key, maxDepth, currentDepth + 1);
    }
  }

  _locateAll_Key(collection, keyName, '', '', maxDepth);

  R = R.map(function (path) {
    if (_getType(path) === 'boolean') return path;
    if (path === '') return path;
    path = path.split('.');
    path.pop();
    path = path.join('.');
    return path;
  });
  return R.length === 0 ? false : R;
}
/**
 * Performs deep search on collection to find a match to the key name, and returns the first identity containing the match. If no match found, it returns `undefined`.
 * @param {Any} collection
 * @param {Any} keyName
 * @param {Optional number} maxDepth
 * @return {Identity || undefined} identity
 */


function _deepGet_Key(collection, keyName) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  if (_getType(keyName) !== 'string') return undefined;
  if (keyName === '') return undefined;

  var path = _locate_Key2(collection, keyName, maxDepth);

  if (path === false) return undefined;
  if (path === '') path = keyName;else path += '.' + keyName;
  path = path.split('.');
  var result = collection;
  if (!Array.isArray(path)) return result[path];
  path.forEach(function (key) {
    result = result[key];
  });
  return result;
}
/**
 * Performs deep search on collection to find a match to the key name, will return the path of the first instance matched. If no match found, it returns `false`.
 * @param {Any} collection
 * @param {Any} keyName
 * @param {Optional number} maxDepth
 * @return {String || false} Path
 */


function _locate_Key2(collection, keyName) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  if (_getType(keyName) !== 'string') return undefined;
  if (keyName === '') return undefined;

  function _locate_Key(collection, keyName) {
    var path = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : '';
    var maxDepth = arguments.length > 3 ? arguments[3] : undefined;
    var currentDepth = arguments.length > 4 && arguments[4] !== undefined ? arguments[4] : 0;
    if (path === keyName) return path;
    var result = false;
    if (maxDepth !== null) if (currentDepth >= maxDepth) return result;
    if (_isIterable(collection)) for (var i = 0, keys = Object.keys(collection), l = keys.length; i < l; i++) {
      var key = keys[i],
          subcollection = collection[key],
          res = _locate_Key(subcollection, keyName, key, maxDepth, currentDepth + 1);

      if (res) {
        path = path === '' ? path : path + '.';
        result = path + res;
        break;
      }
    }
    return result;
  }

  var path = _locate_Key(collection, keyName, '', maxDepth, 0);

  if (_getType(path) === 'boolean') return path;
  if (path === '') return path;
  path = path.split('.');
  path.pop();
  path = path.join('.');
  return path;
}
/**
 * Performs deep search for identity on collection to return the location's depth of the first match. If no match found, it returns `false`.
 * @param {Any} collection
 * @param {Any} identity
 * @param {Optional Number} maxDepth
 * @return {boolean}
 */


function _matchDepth(collection, identity) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;

  var path = _locate2(collection, identity, maxDepth);

  if (path === false) return false;
  if (path === '') return 0;
  path = path.split('.');
  return path.length;
}
/**
 * Walks through the entire object tree to return the maximum number of layers it contains.
 * @param {Any} identity
 * @param {Optional Number} maxDepth
 */


function _maxDepth2(identity) {
  var maxLayer = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : null;
  var R = 0;

  function _maxDepth(identity, maxLayer) {
    var currentDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 0;
    if (R < currentDepth) R = currentDepth;
    if (maxLayer !== null) if (currentDepth >= maxLayer) return;

    if (_isIterable(identity)) {
      var keys = Object.keys(identity);
      keys.forEach(function (key) {
        var subIdentity = identity[key];

        _maxDepth(subIdentity, maxLayer, currentDepth + 1);
      });
    }
  }

  _maxDepth(identity, maxLayer);

  return R;
}
/**
 * Performs deep search for identity on collection, returns the number of matches found.
 * @param {Any} collection
 * @param {Any} identity
 * @param {Number} nthDepth
 * @param {Optional Number} maxDepth
 * @return {Any} Returns number of matches found.
 */


function _countMatches(collection, identity) {
  var nthDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  var maxDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : null;
  var depth,
      nthDepth_isNull = nthDepth === null,
      maxDepth_isNull = maxDepth === null;
  if (nthDepth_isNull && maxDepth_isNull) depth = null;else if (!nthDepth_isNull && !maxDepth_isNull) {
    if (nthDepth < maxDepth) depth = nthDepth;else depth = maxDepth;
  } else if (nthDepth) depth = nthDepth;else depth = maxDepth;

  var paths = _locateAll2(collection, identity, depth);

  if (paths === false) return 0;
  if (nthDepth === null) return paths.length;

  if (_getType(nthDepth) === 'number') {
    var count = 0;
    paths.forEach(function (path) {
      path = path.split('.');
      if (path.length === nthDepth) count++;
    });
    return count;
  }

  return undefined;
}
/**
* Performs deep search for each identity on collection, to shorten the identities to those that meets the match criteria
* @param {Any} collection
* @param {Any} identities
* @param {Any} property
* @param {Optional Number} maxDepth
* @return {Any} Returns a collection of the same type as the 'identities' parameter provided with only the identities that matched.
*/


function _onlyFalsy(collection, identities, property) {
  var maxDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : null;

  if (_getType(identities) === 'array') {
    var result = [];
    identities.forEach(function (identity) {
      var subCollection = _deepFilter(collection, identity);

      if (_isTruthy(subCollection)) if (_foundFalsy2(subCollection, property, maxDepth)) result.push(identity);
    });
    return result;
  }

  if (_getType(identities) === 'object') {
    var _result = {};
    Object.keys(identities).forEach(function (key) {
      var identity = identities[key],
          subCollection = _deepFilter(collection, identity);

      if (_isTruthy(subCollection)) if (_foundFalsy2(subCollection, property, maxDepth)) _result[key] = identity;
    });
    return _result;
  }

  if (_foundFalsy2(collection, property, maxDepth)) return identities;
}
/**
 * Performs deep search on collection to find any match to the property and evalutates if truthy
 * @param {Any} collection
 * @param {Property} identity
 * @param {Optional Number} maxDepth
 * @return {boolean} If match confirmed and truthy will return true, otherwise false
 */


function _foundFalsy2(collection, identity) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  identity = singleProperty(identity);
  if (_isFalsy(identity)) return undefined;

  function _foundFalsy(collection, identity, maxDepth) {
    var currentDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : 0;
    if (_containsKeys(collection, [identity])) return _isFalsy(collection[identity]);
    if (maxDepth !== null) if (currentDepth >= maxDepth) return false;
    if (_isIterable(collection)) for (var i = 0, keys = Object.keys(collection), l = keys.length; i < l; i++) {
      var key = keys[i],
          subcollection = collection[key],
          res = _foundFalsy(subcollection, identity, maxDepth, currentDepth + 1);

      if (res) return true;
    }
    return false;
  }

  return _foundFalsy(collection, identity, maxDepth);
}
/**
 * Performs deep search for each identity on collection, to shorten the identities to those that meets the match criteria
 * @param {Any} collection
 * @param {Any} identities
 * @param {Any} property
 * @param {Optional Number} maxDepth
 * @return {Any} Returns a collection of the same type as the 'identities' parameter provided with only the identities that matched.
 */


function _onlyTruthy(collection, identities, property) {
  var maxDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : null;

  if (_getType(identities) === 'array') {
    var result = [];
    identities.forEach(function (identity) {
      var subCollection = _deepFilter(collection, identity);

      if (_isTruthy(subCollection)) if (_foundTruthy2(subCollection, property, maxDepth)) result.push(identity);
    });
    return result;
  }

  if (_getType(identities) === 'object') {
    var _result2 = {};
    Object.keys(identities).forEach(function (key) {
      var identity = identities[key],
          subCollection = _deepFilter(collection, identity);

      if (_isTruthy(subCollection)) if (_foundTruthy2(subCollection, property, maxDepth)) _result2[key] = identity;
    });
    return _result2;
  }

  if (_foundTruthy2(collection, property, maxDepth)) return identities;
}
/**
 * Performs deep search on collection to find any match to the property and evalutates if truthy
 * @param {Any} collection
 * @param {Property} identity
 * @param {Optional Number} maxDepth
 * @return {boolean} If match confirmed and truthy will return true, otherwise false
 */


function _foundTruthy2(collection, identity) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  identity = singleProperty(identity);
  if (_isFalsy(identity)) return undefined;

  function _foundTruthy(collection, identity, maxDepth) {
    var currentDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : 0;
    if (_containsKeys(collection, [identity])) return _isTruthy(collection[identity]);
    if (maxDepth !== null) if (currentDepth >= maxDepth) return false;
    if (_isIterable(collection)) for (var i = 0, keys = Object.keys(collection), l = keys.length; i < l; i++) {
      var key = keys[i],
          subcollection = collection[key],
          res = _foundTruthy(subcollection, identity, maxDepth, currentDepth + 1);

      if (res) return true;
    }
    return false;
  }

  return _foundTruthy(collection, identity, maxDepth, 0);
}
/**
 * Validates if identity is equal to a property definition or contains a single property key.
 * @param {Property} identity
 * @return {String || boolean} If criteria matched will return property name as string, otherwise false
 */


function singleProperty(identity) {
  var propCount = _length(identity);

  if (propCount > 1) return false;
  if (propCount === 1) return Object.keys(identity)[0];
  if (propCount === 0) if (['string', 'number'].indexOf(_getType(identity)) > -1) return identity;
  return false;
}
/**
 * Determines if identity is non-falsy
 * @param {Any} identity
 * @return {boolean} Returns true if criteria matched, otherwise false.
 */


function _isTruthy(identity) {
  return !_isFalsy(identity);
}
/**
 * Determines if identity is falsy
 * @param {Any} identity
 * @return {boolean} Returns true if criteria matched, otherwise false.
 */


function _isFalsy(identity) {
  if (falser(identity) === false) return true;
  return false;
}
/**
 * Converts false-like values into actual boolean value of false
 * @param {Any} identity
 * @return {Any || boolean} Returns false is value is falsy, otherwise returns original value.
 */


function falser(identity) {
  if (_isIterable(identity)) return identity;
  if (['null', 'undefined'].indexOf(_getType(identity)) > -1) return false;
  if (['', 0, false].indexOf(identity) > -1) return false;
  return identity;
}
/**
 * Check the length of the top-most depth of the identity
 * @param {Any} identity
 * @return {integer} Greater than or equal to 0.
 */


function _length(identity) {
  if (['array', 'object'].indexOf(_getType(identity)) === -1) return 0;
  return Object.keys(identity).length;
}
/**
 * Performs deep search for each identity on collection, to shorten the identities to those that does meets the match criteria
 * @param {Any} collection
 * @param {Any} identities
 * @param {Optional Number} maxDepth
 * @return {Any} Returns a collection of the same type as the 'identities' parameter provided with only the identities that were not matched.
 */


function _onlyMissing(collection, identities) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;

  if (_getType(identities) === 'array') {
    var result = [];
    identities.forEach(function (identity) {
      if (!_exists(collection, identity, maxDepth)) result.push(identity);
    });
    return result;
  }

  if (_getType(identities) === 'object') {
    var _result3 = {};
    Object.keys(identities).forEach(function (key) {
      var identity = identities[key];
      if (!_exists(collection, identity, maxDepth)) _result3[key] = identity;
    });
    return _result3;
  }

  if (!_exists(collection, identities, maxDepth)) return identities;
}
/**
 * Performs deep search for each identity on collection, to shorten the identities to those that meets the match criteria
 * @param {Any} collection
 * @param {Any} identities
 * @param {Optional Number} maxDepth
 * @return {Any} Returns a collection of the same type as the 'identities' parameter provided with only the identities that matched.
 */


function _onlyExisting(collection, identities) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;

  if (_getType(identities) === 'array') {
    var result = [];
    identities.forEach(function (identity) {
      if (_exists(collection, identity, maxDepth)) result.push(identity);
    });
    return result;
  }

  if (_getType(identities) === 'object') {
    var _result4 = {};
    Object.keys(identities).forEach(function (key) {
      var identity = identities[key];
      if (_exists(collection, identity, maxDepth)) _result4[key] = identity;
    });
    return _result4;
  }

  if (_exists(collection, identities, maxDepth)) return identities;
}
/**
 * Performs deep search on collection to find any match to the identity
 * @param {Any} collection
 * @param {Any} identity
 * @param {Optional Number} maxDepth
 * @return {boolean} If a match is confirmed will return true, otherwise false
 */


function _exists(collection, identity) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  var currentDepth = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : 0;
  if (_identical(collection, identity)) return true;
  if (_isIterable(identity)) if (_sameType(collection, identity)) if (_containsKeys(collection, Object.keys(identity))) {
    var trimmed = _trim(collection, Object.keys(identity));

    if (_identical(trimmed, identity)) return true;
  }
  if (maxDepth === null ? true : currentDepth < maxDepth) if (_isIterable(collection)) for (var i = 0, keys = Object.keys(collection), l = keys.length; i < l; i++) {
    var key = keys[i],
        subcollection = collection[key],
        res = _exists(subcollection, identity, maxDepth, currentDepth + 1);

    if (res) return true;
  }
  return false;
}
/**
 * Performs deep search on collection to find all matches to the identity, will return a list of identities containing the match. If no matches found, it returns `undefined`.
 * @param {Any} collection
 * @param {Any} identity
 * @param {Optional Number} maxDepth
 * @return {Array || undefined} identities
 */


function _deepFilter(collection, identity) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;

  var paths = _locateAll2(collection, identity, maxDepth);

  if (paths === false) return undefined;
  var results = paths.map(function (path) {
    if (path === '') return collection;
    path = path.split('.');
    if (['array', 'object'].indexOf(_getType(identity)) === -1) path.splice(-1, 1);
    var result = collection;
    if (!Array.isArray(path)) return result[path];
    path.forEach(function (key) {
      result = result[key];
    });
    return result;
  });
  return results;
}
/**
 * Performs deep search on collection to find all matches to the identity, returns a string array containing the location of all matches. If no matches found, it returns `false`.
 * @param {Any} collection
 * @param {Any} identity
 * @param {Optional Number} maxDepth
 * @return {Array || false} Paths
 */


function _locateAll2(collection, identity) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  var R = [];

  function _locateAll(collection, identity) {
    var path = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : '';
    var maxDepth = arguments.length > 3 ? arguments[3] : undefined;
    var currentDepth = arguments.length > 4 ? arguments[4] : undefined;
    if (_isIterable(identity)) if (_sameType(collection, identity)) if (_containsKeys(collection, Object.keys(identity))) {
      var trimmed = _trim(collection, Object.keys(identity));

      if (_identical(trimmed, identity)) R[R.length] = path;
    }
    if (_identical(collection, identity)) R[R.length] = path;
    var result = false;
    if (maxDepth !== null) if (currentDepth >= maxDepth) return result;
    if (_isIterable(collection)) for (var i = 0, keys = Object.keys(collection), l = keys.length; i < l; i++) {
      var key = keys[i],
          subcollection = collection[key];

      _locateAll(subcollection, identity, (path === '' ? path : path + '.') + key, maxDepth, currentDepth + 1);
    }
  }

  _locateAll(collection, identity, '', maxDepth, 0);

  return R.length === 0 ? false : R;
}
/**
 * Performs deep search on collection to find a match to the identity, will return the identity containing of the first instance matched. If no matches found, it returns `undefined`.
 * @param {Any} collection
 * @param {Any} identity
 * @param {Optional Number} maxDepth
 * @return {identity || undefined} identity
 */


function _deepGet(collection, identity) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;

  var path = _locate2(collection, identity, maxDepth);

  if (path === false) return undefined;
  if (path === '') return collection;
  path = path.split('.');
  if (['array', 'object'].indexOf(_getType(identity)) === -1) path.splice(-1, 1);
  var result = collection;
  if (!Array.isArray(path)) return result[path];
  path.forEach(function (key) {
    result = result[key];
  });
  return result;
}
/**
 * Performs deep search on collection to find a match to the identity, will return the path of the first instance matched as string. If no matches found, returns `false`.
 * @param {Any} collection
 * @param {Any} identity
 * @param {Optional number} maxDepth
 * @return {string || false} path
 */


function _locate2(collection, identity) {
  var maxDepth = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;

  function _locate(collection, identity) {
    var path = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : '';
    var maxDepth = arguments.length > 3 ? arguments[3] : undefined;
    var currentDepth = arguments.length > 4 ? arguments[4] : undefined;
    if (_isIterable(identity)) if (_sameType(collection, identity)) if (_containsKeys(collection, Object.keys(identity))) {
      var trimmed = _trim(collection, Object.keys(identity));

      if (_identical(trimmed, identity)) return path;
    }
    if (_identical(collection, identity)) return path;
    var result = false;
    if (maxDepth !== null) if (currentDepth >= maxDepth) return result;
    if (_isIterable(collection)) for (var i = 0, keys = Object.keys(collection), l = keys.length; i < l; i++) {
      var key = keys[i],
          subcollection = collection[key],
          res = _locate(subcollection, identity, key, maxDepth, currentDepth + 1);

      if (res) {
        path = path === '' ? path : path + '.';
        result = path + res;
        break;
      }
    }
    return result;
  }

  return _locate(collection, identity, '', maxDepth, 0);
}
/**
 * Trims an identity to only contain the specified properties.
 * @param {Any} identity
 * @param {Any} keyList
 * @return {Object or Array} Returns , otherwise false
 */


function _trim(identity, keyList) {
  var identityType = _getType(identity);

  if (['array', 'object'].indexOf(identityType) === -1) return undefined;
  var keyCount = keyList.length;
  if (keyCount === 0) return undefined;
  var newIdentity;

  switch (identityType) {
    case 'object':
      newIdentity = {};
      keyList.forEach(function (key) {
        if (key in identity) newIdentity[key] = identity[key];
      });
      break;

    case 'array':
      newIdentity = [];
      keyList.forEach(function (key) {
        if (key in identity) newIdentity.push(identity[key]);
      });
      break;
  }

  return newIdentity;
}
/**
 * Check if identity contains all of the specified keys
 * @param {Any} identity
 * @param {Array} keyList
 * @return {boolean} true || false
 */


function _containsKeys(identity, keyList) {
  var keyCount = keyList.length;
  if (keyCount === 0 || !_isIterable(identity)) return false;
  var identitykeys = Object.keys(identity);
  var result = true;

  for (var i = 0; i < keyCount; i++) {
    var key = '' + keyList[i];

    if (identitykeys.indexOf(key) === -1) {
      result = false;
      break;
    }
  }

  return result;
}
/**
 * Check if identity has one or more keys to iterate
 * @param {Any} identity
 * @return {boolean} true || false
 */


function _isIterable(identity) {
  if (['array', 'object'].indexOf(_getType(identity)) === -1) return false;
  if (Object.keys(identity).length === 0) return false;
  return true;
}
/**
 * Compares two identities, will return either true if identical, otherwise false.
 * @param {Any} identityA
 * @param {Any} identityB
 * @return {boolean} true || false
 */


function _identical(identityA, identityB) {
  var structureMatch = _sameStructure(identityA, identityB);

  if (structureMatch === false) return structureMatch;
  if (['array', 'object'].indexOf(structureMatch) === -1) return identityA === identityB;
  var Keys = Object.keys(identityA),
      KeyCount = Keys.length;
  var childMatch = true;

  for (var i = 0; i < KeyCount; i++) {
    var Key = Keys[i],
        identicalMatch = _identical(identityA[Key], identityB[Key]);

    if (identicalMatch === false) {
      childMatch = identicalMatch;
      break;
    }

    ;
  }

  return childMatch;
}
/**
 * Compares data structure of two identities, will return either the dataType or true/false.
 * @param {Any} identityA
 * @param {Any} identityB
 * @return {String || False} DataType as string for positive match, otherwise false
 */


function _sameStructure(identityA, identityB) {
  var typeMatch = _sameType(identityA, identityB);

  if (typeMatch === false) return false;

  if (['array', 'object'].indexOf(typeMatch) > -1) {
    var AKeys = Object.keys(identityA),
        BKeys = Object.keys(identityB),
        AKeyCount = AKeys.length,
        BKeyCount = BKeys.length;
    if (!(AKeyCount === BKeyCount)) return false;
    if (AKeyCount === 0) return true;

    for (var i = 0; i < AKeyCount; i++) {
      if (AKeys[i] !== BKeys[i]) return false;
    }
  }

  return typeMatch;
}
/**
 * Compares data type of two identities, will dataType if true.
 * @param {Any} identityA
 * @param {Any} identityB
 * @return {boolean} true || false
 */


function _sameType(identityA, identityB) {
  var typeA = _getType(identityA);

  return typeA === _getType(identityB) ? typeA : false;
}
/**
 * Gets data type; makes distintion between object, array, and null.
 * @param {Any} identity
 * @return {String} dataType
 */


function _getType(identity) {
  if (identity === null) return 'null';
  var it = (0, _typeof2.default)(identity);
  if (it === 'object') if (Array.isArray(identity)) return 'array';
  return it;
}

var mitsuketa = {
  getType: function getType(identity) {
    return _getType(identity);
  },
  sameType: function sameType(identityA, identityB) {
    return _sameType(identityA, identityB);
  },
  sameStructure: function sameStructure(identityA, identityB) {
    return _sameStructure(identityA, identityB);
  },
  identical: function identical(identityA, identityB) {
    return _identical(identityA, identityB);
  },
  isIterable: function isIterable(identity) {
    return _isIterable(identity);
  },
  containsKeys: function containsKeys(identity, keyList) {
    return _containsKeys(identity, keyList);
  },
  trim: function trim(identity, keyList) {
    return _trim(identity, keyList);
  },
  locate: function locate(collection, identity, maxDepth) {
    return _locate2(collection, identity, maxDepth);
  },
  deepGet: function deepGet(collection, identity, maxDepth) {
    return _deepGet(collection, identity, maxDepth);
  },
  locateAll: function locateAll(collection, identity, maxDepth) {
    return _locateAll2(collection, identity, maxDepth);
  },
  deepFilter: function deepFilter(collection, identity, maxDepth) {
    return _deepFilter(collection, identity, maxDepth);
  },
  exists: function exists(collection, identity, maxDepth) {
    return _exists(collection, identity, maxDepth);
  },
  onlyExisting: function onlyExisting(collection, identities, maxDepth) {
    return _onlyExisting(collection, identities, maxDepth);
  },
  onlyMissing: function onlyMissing(collection, identities, maxDepth) {
    return _onlyMissing(collection, identities, maxDepth);
  },
  length: function length(identity) {
    return _length(identity);
  },
  isFalsy: function isFalsy(identity) {
    return _isFalsy(identity);
  },
  isTruthy: function isTruthy(identity) {
    return _isTruthy(identity);
  },
  foundTruthy: function foundTruthy(collection, identity, maxDepth) {
    return _foundTruthy2(collection, identity, maxDepth);
  },
  onlyTruthy: function onlyTruthy(collection, identities, property, maxDepth) {
    return _onlyTruthy(collection, identities, property, maxDepth);
  },
  foundFalsy: function foundFalsy(collection, identity, maxDepth) {
    return _foundFalsy2(collection, identity, maxDepth);
  },
  onlyFalsy: function onlyFalsy(collection, identities, property, maxDepth) {
    return _onlyFalsy(collection, identities, property, maxDepth);
  },
  countMatches: function countMatches(collection, identity, nthDepth, maxDepth) {
    return _countMatches(collection, identity, nthDepth, maxDepth);
  },
  matchDepth: function matchDepth(collection, identity, maxDepth) {
    return _matchDepth(collection, identity, maxDepth);
  },
  maxDepth: function maxDepth(identity, maxLayer) {
    return _maxDepth2(identity, maxLayer);
  },
  locate_Key: function locate_Key(collection, keyName, maxDepth) {
    return _locate_Key2(collection, keyName, maxDepth);
  },
  deepGet_Key: function deepGet_Key(collection, keyName, maxDepth) {
    return _deepGet_Key(collection, keyName, maxDepth);
  },
  locateAll_Key: function locateAll_Key(collection, keyName, maxDepth) {
    return _locateAll_Key2(collection, keyName, maxDepth);
  },
  deepFilter_Key: function deepFilter_Key(collection, keyName, maxDepth) {
    return _deepFilter_Key(collection, keyName, maxDepth);
  },
  deepClone: function deepClone(identity, maxDepth, startDepth) {
    return _deepClone2(identity, maxDepth, startDepth);
  },
  renameKey: function renameKey(identity, keyName, newKeyName, maxDepth) {
    return _renameKey2(identity, keyName, newKeyName, maxDepth);
  },
  renameKeys: function renameKeys(identity, keyName, newKeyName, maxDepth) {
    return _renameKeys2(identity, keyName, newKeyName, maxDepth);
  },
  deepRemove_Key: function deepRemove_Key(identity, keyName, maxDepth) {
    return _deepRemove_Key(identity, keyName, maxDepth);
  },
  deepRemoveAll_Key: function deepRemoveAll_Key(identity, keyName, maxDepth) {
    return _deepRemoveAll_Key(identity, keyName, maxDepth);
  }
};
module.exports = exports = mitsuketa;