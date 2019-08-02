/** @license react-json-editor-ajrm v2.5.9
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
"use strict";

var _interopRequireWildcard = require("@babel/runtime/helpers/interopRequireWildcard");

var _interopRequireDefault = require("@babel/runtime/helpers/interopRequireDefault");

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _typeof2 = _interopRequireDefault(require("@babel/runtime/helpers/typeof"));

var _objectSpread2 = _interopRequireDefault(require("@babel/runtime/helpers/objectSpread"));

var _classCallCheck2 = _interopRequireDefault(require("@babel/runtime/helpers/classCallCheck"));

var _createClass2 = _interopRequireDefault(require("@babel/runtime/helpers/createClass"));

var _possibleConstructorReturn2 = _interopRequireDefault(require("@babel/runtime/helpers/possibleConstructorReturn"));

var _getPrototypeOf2 = _interopRequireDefault(require("@babel/runtime/helpers/getPrototypeOf"));

var _inherits2 = _interopRequireDefault(require("@babel/runtime/helpers/inherits"));

var _assertThisInitialized2 = _interopRequireDefault(require("@babel/runtime/helpers/assertThisInitialized"));

var _react = _interopRequireWildcard(require("react"));

var _themes = _interopRequireDefault(require("./themes"));

var _mitsuketa = require("./mitsuketa");

var _err = _interopRequireDefault(require("./err"));

var _locale = require("./locale");

var _en = _interopRequireDefault(require("./locale/en"));

var JSONInput =
/*#__PURE__*/
function (_Component) {
  (0, _inherits2.default)(JSONInput, _Component);

  function JSONInput(props) {
    var _this;

    (0, _classCallCheck2.default)(this, JSONInput);
    _this = (0, _possibleConstructorReturn2.default)(this, (0, _getPrototypeOf2.default)(JSONInput).call(this, props));
    _this.updateInternalProps = _this.updateInternalProps.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.createMarkup = _this.createMarkup.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.onClick = _this.onClick.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.onBlur = _this.onBlur.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.update = _this.update.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.getCursorPosition = _this.getCursorPosition.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.setCursorPosition = _this.setCursorPosition.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.scheduledUpdate = _this.scheduledUpdate.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.setUpdateTime = _this.setUpdateTime.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.renderLabels = _this.renderLabels.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.newSpan = _this.newSpan.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.renderErrorMessage = _this.renderErrorMessage.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.onScroll = _this.onScroll.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.showPlaceholder = _this.showPlaceholder.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.tokenize = _this.tokenize.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.onKeyPress = _this.onKeyPress.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.onKeyDown = _this.onKeyDown.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.onPaste = _this.onPaste.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.stopEvent = _this.stopEvent.bind((0, _assertThisInitialized2.default)((0, _assertThisInitialized2.default)(_this)));
    _this.refContent = null;
    _this.refLabels = null;

    _this.updateInternalProps();

    _this.renderCount = 1;
    _this.state = {
      prevPlaceholder: '',
      markupText: '',
      plainText: '',
      json: '',
      jsObject: undefined,
      lines: false,
      error: false
    };

    if (!_this.props.locale) {
      console.warn("[react-json-editor-ajrm - Deprecation Warning] You did not provide a 'locale' prop for your JSON input - This will be required in a future version. English has been set as a default.");
    }

    return _this;
  }

  (0, _createClass2.default)(JSONInput, [{
    key: "updateInternalProps",
    value: function updateInternalProps() {
      var colors = {},
          style = {},
          theme = _themes.default.dark_vscode_tribute;
      if ('theme' in this.props) if (typeof this.props.theme === 'string') if (this.props.theme in _themes.default) theme = _themes.default[this.props.theme];
      colors = theme;
      if ('colors' in this.props) colors = {
        default: 'default' in this.props.colors ? this.props.colors.default : colors.default,
        string: 'string' in this.props.colors ? this.props.colors.string : colors.string,
        number: 'number' in this.props.colors ? this.props.colors.number : colors.number,
        colon: 'colon' in this.props.colors ? this.props.colors.colon : colors.colon,
        keys: 'keys' in this.props.colors ? this.props.colors.keys : colors.keys,
        keys_whiteSpace: 'keys_whiteSpace' in this.props.colors ? this.props.colors.keys_whiteSpace : colors.keys_whiteSpace,
        primitive: 'primitive' in this.props.colors ? this.props.colors.primitive : colors.primitive,
        error: 'error' in this.props.colors ? this.props.colors.error : colors.error,
        background: 'background' in this.props.colors ? this.props.colors.background : colors.background,
        background_warning: 'background_warning' in this.props.colors ? this.props.colors.background_warning : colors.background_warning
      };
      this.colors = colors;
      if ('style' in this.props) style = {
        outerBox: 'outerBox' in this.props.style ? this.props.style.outerBox : {},
        container: 'container' in this.props.style ? this.props.style.container : {},
        warningBox: 'warningBox' in this.props.style ? this.props.style.warningBox : {},
        errorMessage: 'errorMessage' in this.props.style ? this.props.style.errorMessage : {},
        body: 'body' in this.props.style ? this.props.style.body : {},
        labelColumn: 'labelColumn' in this.props.style ? this.props.style.labelColumn : {},
        labels: 'labels' in this.props.style ? this.props.style.labels : {},
        contentBox: 'contentBox' in this.props.style ? this.props.style.contentBox : {}
      };else style = {
        outerBox: {},
        container: {},
        warningBox: {},
        errorMessage: {},
        body: {},
        labelColumn: {},
        labels: {},
        contentBox: {}
      };
      this.style = style;
      this.confirmGood = 'confirmGood' in this.props ? this.props.confirmGood : true;
      var totalHeight = this.props.height || '610px',
          totalWidth = this.props.width || '479px';
      this.totalHeight = totalHeight;
      this.totalWidth = totalWidth;

      if (!('onKeyPressUpdate' in this.props) || this.props.onKeyPressUpdate) {
        if (!this.timer) this.timer = setInterval(this.scheduledUpdate, 100);
      } else if (this.timer) {
        clearInterval(this.timer);
        this.timer = false;
      }

      this.updateTime = false;
      this.waitAfterKeyPress = 'waitAfterKeyPress' in this.props ? this.props.waitAfterKeyPress : 1000;
      this.resetConfiguration = 'reset' in this.props ? this.props.reset : false;
    }
  }, {
    key: "render",
    value: function render() {
      var _this2 = this;

      var id = this.props.id,
          markupText = this.state.markupText,
          error = this.state.error,
          colors = this.colors,
          style = this.style,
          confirmGood = this.confirmGood,
          totalHeight = this.totalHeight,
          totalWidth = this.totalWidth,
          hasError = error ? 'token' in error : false;
      this.renderCount++;
      return _react.default.createElement("div", {
        name: "outer-box",
        id: id && id + '-outer-box',
        style: (0, _objectSpread2.default)({
          display: 'block',
          overflow: 'none',
          height: totalHeight,
          width: totalWidth,
          margin: 0,
          boxSizing: 'border-box',
          position: 'relative'
        }, style.outerBox)
      }, confirmGood ? _react.default.createElement("div", {
        style: {
          opacity: hasError ? 0 : 1,
          height: '30px',
          width: '30px',
          position: 'absolute',
          top: 0,
          right: 0,
          transform: 'translate(-25%,25%)',
          pointerEvents: 'none',
          transitionDuration: '0.2s',
          transitionTimingFunction: 'cubic-bezier(0, 1, 0.5, 1)'
        }
      }, _react.default.createElement("svg", {
        height: "30px",
        width: "30px",
        viewBox: "0 0 100 100"
      }, _react.default.createElement("path", {
        fillRule: "evenodd",
        clipRule: "evenodd",
        fill: "green",
        opacity: "0.85",
        d: "M39.363,79L16,55.49l11.347-11.419L39.694,56.49L72.983,23L84,34.085L39.363,79z"
      }))) : void 0, _react.default.createElement("div", {
        name: "container",
        id: id && id + '-container',
        style: (0, _objectSpread2.default)({
          display: 'block',
          height: totalHeight,
          width: totalWidth,
          margin: 0,
          boxSizing: 'border-box',
          overflow: 'hidden',
          fontFamily: 'Roboto, sans-serif'
        }, style.container),
        onClick: this.onClick
      }, _react.default.createElement("div", {
        name: "warning-box",
        id: id && id + '-warning-box',
        style: (0, _objectSpread2.default)({
          display: 'block',
          overflow: 'hidden',
          height: hasError ? '60px' : '0px',
          width: '100%',
          margin: 0,
          backgroundColor: colors.background_warning,
          transitionDuration: '0.2s',
          transitionTimingFunction: 'cubic-bezier(0, 1, 0.5, 1)'
        }, style.warningBox),
        onClick: this.onClick
      }, _react.default.createElement("span", {
        style: {
          display: 'inline-block',
          height: '60px',
          width: '60px',
          margin: 0,
          boxSizing: 'border-box',
          overflow: 'hidden',
          verticalAlign: 'top',
          pointerEvents: 'none'
        },
        onClick: this.onClick
      }, _react.default.createElement("div", {
        style: {
          position: 'relative',
          top: 0,
          left: 0,
          height: '60px',
          width: '60px',
          margin: 0,
          pointerEvents: 'none'
        },
        onClick: this.onClick
      }, _react.default.createElement("div", {
        style: {
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          pointerEvents: 'none'
        },
        onClick: this.onClick
      }, _react.default.createElement("svg", {
        height: "25px",
        width: "25px",
        viewBox: "0 0 100 100"
      }, _react.default.createElement("path", {
        fillRule: "evenodd",
        clipRule: "evenodd",
        fill: "red",
        d: "M73.9,5.75c0.467-0.467,1.067-0.7,1.8-0.7c0.7,0,1.283,0.233,1.75,0.7l16.8,16.8  c0.467,0.5,0.7,1.084,0.7,1.75c0,0.733-0.233,1.334-0.7,1.801L70.35,50l23.9,23.95c0.5,0.467,0.75,1.066,0.75,1.8  c0,0.667-0.25,1.25-0.75,1.75l-16.8,16.75c-0.534,0.467-1.117,0.7-1.75,0.7s-1.233-0.233-1.8-0.7L50,70.351L26.1,94.25  c-0.567,0.467-1.167,0.7-1.8,0.7c-0.667,0-1.283-0.233-1.85-0.7L5.75,77.5C5.25,77,5,76.417,5,75.75c0-0.733,0.25-1.333,0.75-1.8  L29.65,50L5.75,26.101C5.25,25.667,5,25.066,5,24.3c0-0.666,0.25-1.25,0.75-1.75l16.8-16.8c0.467-0.467,1.05-0.7,1.75-0.7  c0.733,0,1.333,0.233,1.8,0.7L50,29.65L73.9,5.75z"
      }))))), _react.default.createElement("span", {
        style: {
          display: 'inline-block',
          height: '60px',
          width: 'calc(100% - 60px)',
          margin: 0,
          overflow: 'hidden',
          verticalAlign: 'top',
          position: 'absolute',
          pointerEvents: 'none'
        },
        onClick: this.onClick
      }, this.renderErrorMessage())), _react.default.createElement("div", {
        name: "body",
        id: id && id + '-body',
        style: (0, _objectSpread2.default)({
          display: 'flex',
          overflow: 'none',
          height: hasError ? 'calc(100% - 60px)' : '100%',
          width: '',
          margin: 0,
          resize: 'none',
          fontFamily: 'Roboto Mono, Monaco, monospace',
          fontSize: '11px',
          backgroundColor: colors.background,
          transitionDuration: '0.2s',
          transitionTimingFunction: 'cubic-bezier(0, 1, 0.5, 1)'
        }, style.body),
        onClick: this.onClick
      }, _react.default.createElement("span", {
        name: "labels",
        id: id && id + '-labels',
        ref: function ref(_ref) {
          return _this2.refLabels = _ref;
        },
        style: (0, _objectSpread2.default)({
          display: 'inline-block',
          boxSizing: 'border-box',
          verticalAlign: 'top',
          height: '100%',
          width: '44px',
          margin: 0,
          padding: '5px 0px 5px 10px',
          overflow: 'hidden',
          color: '#D4D4D4'
        }, style.labelColumn),
        onClick: this.onClick
      }, this.renderLabels()), _react.default.createElement("span", {
        id: id,
        ref: function ref(_ref2) {
          return _this2.refContent = _ref2;
        },
        contentEditable: true,
        style: (0, _objectSpread2.default)({
          display: 'inline-block',
          boxSizing: 'border-box',
          verticalAlign: 'top',
          height: '100%',
          width: '',
          flex: 1,
          margin: 0,
          padding: '5px',
          overflowX: 'hidden',
          overflowY: 'auto',
          wordWrap: 'break-word',
          whiteSpace: 'pre-line',
          color: '#D4D4D4',
          outline: 'none'
        }, style.contentBox),
        dangerouslySetInnerHTML: this.createMarkup(markupText),
        onKeyPress: this.onKeyPress,
        onKeyDown: this.onKeyDown,
        onClick: this.onClick,
        onBlur: this.onBlur,
        onScroll: this.onScroll,
        onPaste: this.onPaste,
        autoComplete: "off",
        autoCorrect: "off",
        autoCapitalize: "off",
        spellCheck: false
      }))));
    }
  }, {
    key: "renderErrorMessage",
    value: function renderErrorMessage() {
      var locale = this.props.locale || _en.default,
          error = this.state.error,
          style = this.style;
      if (!error) return void 0;
      return _react.default.createElement("p", {
        style: (0, _objectSpread2.default)({
          color: 'red',
          fontSize: '12px',
          position: 'absolute',
          width: 'calc(100% - 60px)',
          height: '60px',
          boxSizing: 'border-box',
          margin: 0,
          padding: 0,
          paddingRight: '10px',
          overflowWrap: 'break-word',
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center'
        }, style.errorMessage)
      }, (0, _locale.format)(locale.format, error));
    }
  }, {
    key: "renderLabels",
    value: function renderLabels() {
      var colors = this.colors,
          style = this.style,
          errorLine = this.state.error ? this.state.error.line : -1,
          lines = this.state.lines ? this.state.lines : 1;
      var labels = new Array(lines);

      for (var i = 0; i < lines - 1; i++) {
        labels[i] = i + 1;
      }

      return labels.map(function (number) {
        var color = number !== errorLine ? colors.default : 'red';
        return _react.default.createElement("div", {
          key: number,
          style: (0, _objectSpread2.default)({}, style.labels, {
            color: color
          })
        }, number);
      });
    }
  }, {
    key: "createMarkup",
    value: function createMarkup(markupText) {
      if (markupText === undefined) return {
        __html: ''
      };
      return {
        __html: '' + markupText
      };
    }
  }, {
    key: "newSpan",
    value: function newSpan(i, token, depth) {
      var colors = this.colors,
          type = token.type,
          string = token.string;
      var color = '';

      switch (type) {
        case 'string':
        case 'number':
        case 'primitive':
        case 'error':
          color = colors[token.type];
          break;

        case 'key':
          if (string === ' ') color = colors.keys_whiteSpace;else color = colors.keys;
          break;

        case 'symbol':
          if (string === ':') color = colors.colon;else color = colors.default;
          break;

        default:
          color = colors.default;
          break;
      }

      if (string.length !== string.replace(/</g, '').replace(/>/g, '').length) string = '<xmp style=display:inline;>' + string + '</xmp>';
      return '<span' + ' type="' + type + '"' + ' value="' + string + '"' + ' depth="' + depth + '"' + ' style="color:' + color + '"' + '>' + string + '</span>';
    }
  }, {
    key: "getCursorPosition",
    value: function getCursorPosition(countBR) {
      var _this3 = this;

      /**
       * Need to deprecate countBR
       * It is used to differenciate between good markup render, and aux render when error found
       * Adjustments based on coundBR account for usage of <br> instead of <span> for linebreaks to determine acurate cursor position
       * Find a way to consolidate render styles
       */
      var isChildOf = function isChildOf(node) {
        while (node !== null) {
          if (node === _this3.refContent) return true;
          node = node.parentNode;
        }

        return false;
      };

      var selection = window.getSelection(),
          charCount = -1,
          linebreakCount = 0,
          node;

      if (selection.focusNode && isChildOf(selection.focusNode)) {
        node = selection.focusNode;
        charCount = selection.focusOffset;

        while (node) {
          if (node === this.refContent) break;

          if (node.previousSibling) {
            node = node.previousSibling;
            if (countBR) if (node.nodeName === 'BR') linebreakCount++;
            charCount += node.textContent.length;
          } else {
            node = node.parentNode;
            if (node === null) break;
          }
        }
      }

      return charCount + linebreakCount;
    }
  }, {
    key: "setCursorPosition",
    value: function setCursorPosition(nextPosition) {
      var _this4 = this;

      if ([false, null, undefined].indexOf(nextPosition) > -1) return;

      var createRange = function createRange(node, chars, range) {
        if (!range) {
          range = document.createRange();
          range.selectNode(node);
          range.setStart(node, 0);
        }

        if (chars.count === 0) {
          range.setEnd(node, chars.count);
        } else if (node && chars.count > 0) {
          if (node.nodeType === Node.TEXT_NODE) {
            if (node.textContent.length < chars.count) chars.count -= node.textContent.length;else {
              range.setEnd(node, chars.count);
              chars.count = 0;
            }
          } else for (var lp = 0; lp < node.childNodes.length; lp++) {
            range = createRange(node.childNodes[lp], chars, range);
            if (chars.count === 0) break;
          }
        }

        return range;
      };

      var setPosition = function setPosition(chars) {
        if (chars < 0) return;
        var selection = window.getSelection(),
            range = createRange(_this4.refContent, {
          count: chars
        });
        if (!range) return;
        range.collapse(false);
        selection.removeAllRanges();
        selection.addRange(range);
      };

      if (nextPosition > 0) setPosition(nextPosition);else this.refContent.focus();
    }
  }, {
    key: "update",
    value: function update() {
      var cursorOffset = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : 0;
      var updateCursorPosition = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : true;
      var container = this.refContent,
          data = this.tokenize(container);
      if ('onChange' in this.props) this.props.onChange({
        plainText: data.indented,
        markupText: data.markup,
        json: data.json,
        jsObject: data.jsObject,
        lines: data.lines,
        error: data.error
      });
      var cursorPosition = this.getCursorPosition(data.error) + cursorOffset;
      this.setState({
        plainText: data.indented,
        markupText: data.markup,
        json: data.json,
        jsObject: data.jsObject,
        lines: data.lines,
        error: data.error
      });
      this.updateTime = false;
      if (updateCursorPosition) this.setCursorPosition(cursorPosition);
    }
  }, {
    key: "scheduledUpdate",
    value: function scheduledUpdate() {
      if ('onKeyPressUpdate' in this.props) if (this.props.onKeyPressUpdate === false) return;
      var updateTime = this.updateTime;
      if (updateTime === false) return;
      if (updateTime > new Date().getTime()) return;
      this.update();
    }
  }, {
    key: "setUpdateTime",
    value: function setUpdateTime() {
      if ('onKeyPressUpdate' in this.props) if (this.props.onKeyPressUpdate === false) return;
      this.updateTime = new Date().getTime() + this.waitAfterKeyPress;
    }
  }, {
    key: "stopEvent",
    value: function stopEvent(event) {
      if (!event) return;
      event.preventDefault();
      event.stopPropagation();
    }
  }, {
    key: "onKeyPress",
    value: function onKeyPress(event) {
      var ctrlOrMetaIsPressed = event.ctrlKey || event.metaKey;
      if (this.props.viewOnly && !ctrlOrMetaIsPressed) this.stopEvent(event);
      if (!ctrlOrMetaIsPressed) this.setUpdateTime();
    }
  }, {
    key: "onKeyDown",
    value: function onKeyDown(event) {
      var viewOnly = !!this.props.viewOnly;
      var ctrlOrMetaIsPressed = event.ctrlKey || event.metaKey;

      switch (event.key) {
        case 'Tab':
          this.stopEvent(event);
          if (viewOnly) break;
          document.execCommand("insertText", false, "  ");
          this.setUpdateTime();
          break;

        case 'Backspace':
        case 'Delete':
          if (viewOnly) this.stopEvent(event);
          this.setUpdateTime();
          break;

        case 'ArrowLeft':
        case 'ArrowRight':
        case 'ArrowUp':
        case 'ArrowDown':
          this.setUpdateTime();
          break;

        case 'a':
        case 'c':
          if (viewOnly && !ctrlOrMetaIsPressed) this.stopEvent(event);
          break;

        default:
          if (viewOnly) this.stopEvent(event);
          break;
      }
    }
  }, {
    key: "onPaste",
    value: function onPaste(event) {
      if (this.props.viewOnly) {
        this.stopEvent(event);
      } else {
        event.preventDefault();
        var text = event.clipboardData.getData('text/plain');
        document.execCommand('insertHTML', false, text);
      }

      this.update();
    }
  }, {
    key: "onClick",
    value: function onClick() {
      if ('viewOnly' in this.props) if (this.props.viewOnly) return;
    }
  }, {
    key: "onBlur",
    value: function onBlur() {
      if ('viewOnly' in this.props) if (this.props.viewOnly) return;
      this.update(0, false);
    }
  }, {
    key: "onScroll",
    value: function onScroll(event) {
      this.refLabels.scrollTop = event.target.scrollTop;
    }
  }, {
    key: "componentDidUpdate",
    value: function componentDidUpdate() {
      this.updateInternalProps();
      this.showPlaceholder();
    }
  }, {
    key: "componentDidMount",
    value: function componentDidMount() {
      this.showPlaceholder();
    }
  }, {
    key: "componentWillUnmount",
    value: function componentWillUnmount() {
      if (this.timer) clearInterval(this.timer);
    }
  }, {
    key: "showPlaceholder",
    value: function showPlaceholder() {
      var placeholderDoesNotExist = !('placeholder' in this.props);
      if (placeholderDoesNotExist) return;
      var placeholder = this.props.placeholder;
      var placeholderHasEmptyValues = [undefined, null].indexOf(placeholder) > -1;
      if (placeholderHasEmptyValues) return;
      var _this$state = this.state,
          prevPlaceholder = _this$state.prevPlaceholder,
          jsObject = _this$state.jsObject;
      var resetConfiguration = this.resetConfiguration;
      var placeholderDataType = (0, _mitsuketa.getType)(placeholder);
      var unexpectedDataType = ['object', 'array'].indexOf(placeholderDataType) === -1;
      if (unexpectedDataType) _err.default.throwError('showPlaceholder', 'placeholder', 'either an object or an array');
      var samePlaceholderValues = (0, _mitsuketa.identical)(placeholder, prevPlaceholder); // Component will always re-render when new placeholder value is any different from previous placeholder value. 

      var componentShouldUpdate = !samePlaceholderValues;

      if (!componentShouldUpdate) {
        if (resetConfiguration) {
          /**
           * If 'reset' property is set true or is truthy,
           * any difference between placeholder and current value
           * should trigger component re-render
           */
          if (jsObject !== undefined) componentShouldUpdate = !(0, _mitsuketa.identical)(placeholder, jsObject);
        }
      }

      if (!componentShouldUpdate) return;
      var data = this.tokenize(placeholder);
      this.setState({
        prevPlaceholder: placeholder,
        plainText: data.indentation,
        markupText: data.markup,
        lines: data.lines,
        error: data.error
      });
    }
  }, {
    key: "tokenize",
    value: function tokenize(something) {
      if ((0, _typeof2.default)(something) !== 'object') return console.error('tokenize() expects object type properties only. Got \'' + (0, _typeof2.default)(something) + '\' type instead.');
      var locale = this.props.locale || _en.default;
      var newSpan = this.newSpan;
      /**
       *     DOM NODE || ONBLUR OR UPDATE
       */

      if ('nodeType' in something) {
        var quarkize = function quarkize(text) {
          var prefix = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : '';
          var buffer = {
            active: false,
            string: '',
            number: '',
            symbol: '',
            space: '',
            delimiter: '',
            quarks: []
          };

          function pushAndStore(char, type) {
            switch (type) {
              case 'symbol':
              case 'delimiter':
                if (buffer.active) buffer.quarks.push({
                  string: buffer[buffer.active],
                  type: prefix + '-' + buffer.active
                });
                buffer[buffer.active] = '';
                buffer.active = type;
                buffer[buffer.active] = char;
                break;

              default:
                if (type !== buffer.active || [buffer.string, char].indexOf('\n') > -1) {
                  if (buffer.active) buffer.quarks.push({
                    string: buffer[buffer.active],
                    type: prefix + '-' + buffer.active
                  });
                  buffer[buffer.active] = '';
                  buffer.active = type;
                  buffer[buffer.active] = char;
                } else buffer[type] += char;

                break;
            }
          }

          function finalPush() {
            if (buffer.active) {
              buffer.quarks.push({
                string: buffer[buffer.active],
                type: prefix + '-' + buffer.active
              });
              buffer[buffer.active] = '';
              buffer.active = false;
            }
          }

          for (var i = 0; i < text.length; i++) {
            var char = text.charAt(i);

            switch (char) {
              case '"':
              case "'":
                pushAndStore(char, 'delimiter');
                break;

              case ' ':
              case "\xA0":
                pushAndStore(char, 'space');
                break;

              case '{':
              case '}':
              case '[':
              case ']':
              case ':':
              case ',':
                pushAndStore(char, 'symbol');
                break;

              case '0':
              case '1':
              case '2':
              case '3':
              case '4':
              case '5':
              case '6':
              case '7':
              case '8':
              case '9':
                if (buffer.active === 'string') pushAndStore(char, 'string');else pushAndStore(char, 'number');
                break;

              case '-':
                if (i < text.length - 1) if ('0123456789'.indexOf(text.charAt(i + 1)) > -1) {
                  pushAndStore(char, 'number');
                  break;
                }

              case '.':
                if (i < text.length - 1 && i > 0) if ('0123456789'.indexOf(text.charAt(i + 1)) > -1 && '0123456789'.indexOf(text.charAt(i - 1)) > -1) {
                  pushAndStore(char, 'number');
                  break;
                }

              default:
                pushAndStore(char, 'string');
                break;
            }
          }

          finalPush();
          return buffer.quarks;
        };

        var validToken = function validToken(string, type) {
          var quotes = '\'"';
          var firstChar = '',
              lastChar = '',
              quoteType = false;

          switch (type) {
            case 'primitive':
              if (['true', 'false', 'null', 'undefined'].indexOf(string) === -1) return false;
              break;

            case 'string':
              if (string.length < 2) return false;
              firstChar = string.charAt(0), lastChar = string.charAt(string.length - 1), quoteType = quotes.indexOf(firstChar);
              if (quoteType === -1) return false;
              if (firstChar !== lastChar) return false;

              for (var i = 0; i < string.length; i++) {
                if (i > 0 && i < string.length - 1) if (string.charAt(i) === quotes[quoteType]) if (string.charAt(i - 1) !== '\\') return false;
              }

              break;

            case 'key':
              if (string.length === 0) return false;
              firstChar = string.charAt(0), lastChar = string.charAt(string.length - 1), quoteType = quotes.indexOf(firstChar);

              if (quoteType > -1) {
                if (string.length === 1) return false;
                if (firstChar !== lastChar) return false;

                for (var i = 0; i < string.length; i++) {
                  if (i > 0 && i < string.length - 1) if (string.charAt(i) === quotes[quoteType]) if (string.charAt(i - 1) !== '\\') return false;
                }
              } else {
                var nonAlphanumeric = '\'"`.,:;{}[]&<>=~*%\\|/-+!?@^ \xa0';

                for (var i = 0; i < nonAlphanumeric.length; i++) {
                  var nonAlpha = nonAlphanumeric.charAt(i);
                  if (string.indexOf(nonAlpha) > -1) return false;
                }
              }

              break;

            case 'number':
              for (var i = 0; i < string.length; i++) {
                if ('0123456789'.indexOf(string.charAt(i)) === -1) if (i === 0) {
                  if ('-' !== string.charAt(0)) return false;
                } else if ('.' !== string.charAt(i)) return false;
              }

              break;

            case 'symbol':
              if (string.length > 1) return false;
              if ('{[:]},'.indexOf(string) === -1) return false;
              break;

            case 'colon':
              if (string.length > 1) return false;
              if (':' !== string) return false;
              break;

            default:
              return true;
              break;
          }

          return true;
        };

        var tokenFollowed = function tokenFollowed() {
          var last = buffer.tokens_normalize.length - 1;
          if (last < 1) return false;

          for (var i = last; i >= 0; i--) {
            var previousToken = buffer.tokens_normalize[i];

            switch (previousToken.type) {
              case 'space':
              case 'linebreak':
                break;

              default:
                return previousToken;
                break;
            }
          }

          return false;
        };

        var setError = function setError(tokenID, reason) {
          var offset = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 0;
          error = {
            token: tokenID,
            line: line,
            reason: reason
          };
          buffer.tokens_merge[tokenID + offset].type = 'error';
        };

        var followedBySymbol = function followedBySymbol(tokenID, options) {
          if (tokenID === undefined) console.error('tokenID argument must be an integer.');
          if (options === undefined) console.error('options argument must be an array.');
          if (tokenID === buffer.tokens_merge.length - 1) return false;

          for (var i = tokenID + 1; i < buffer.tokens_merge.length; i++) {
            var _nextToken = buffer.tokens_merge[i];

            switch (_nextToken.type) {
              case 'space':
              case 'linebreak':
                break;

              case 'symbol':
              case 'colon':
                if (options.indexOf(_nextToken.string) > -1) return i;else return false;
                break;

              default:
                return false;
                break;
            }
          }

          return false;
        };

        var followsSymbol = function followsSymbol(tokenID, options) {
          if (tokenID === undefined) console.error('tokenID argument must be an integer.');
          if (options === undefined) console.error('options argument must be an array.');
          if (tokenID === 0) return false;

          for (var i = tokenID - 1; i >= 0; i--) {
            var _previousToken = buffer.tokens_merge[i];

            switch (_previousToken.type) {
              case 'space':
              case 'linebreak':
                break;

              case 'symbol':
              case 'colon':
                if (options.indexOf(_previousToken.string) > -1) return true;
                return false;
                break;

              default:
                return false;
                break;
            }
          }

          return false;
        };

        var typeFollowed = function typeFollowed(tokenID) {
          if (tokenID === undefined) console.error('tokenID argument must be an integer.');
          if (tokenID === 0) return false;

          for (var i = tokenID - 1; i >= 0; i--) {
            var _previousToken2 = buffer.tokens_merge[i];

            switch (_previousToken2.type) {
              case 'space':
              case 'linebreak':
                break;

              default:
                return _previousToken2.type;
                break;
            }
          }

          return false;
        };

        var newIndent = function newIndent() {
          var space = [];

          for (var i = 0; i < _depth * 2; i++) {
            space.push('&nbsp;');
          }

          return space.join('');
        };

        var newLineBreak = function newLineBreak() {
          var byPass = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;
          _line++;

          if (_depth > 0 || byPass) {
            return '<br>';
          }

          return '';
        };

        var newLineBreakAndIndent = function newLineBreakAndIndent() {
          var byPass = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;
          return newLineBreak(byPass) + newIndent();
        };

        var containerNode = something.cloneNode(true),
            hasChildren = containerNode.hasChildNodes();
        if (!hasChildren) return '';
        var children = containerNode.childNodes;
        var buffer = {
          tokens_unknown: [],
          tokens_proto: [],
          tokens_split: [],
          tokens_fallback: [],
          tokens_normalize: [],
          tokens_merge: [],
          tokens_plainText: '',
          indented: '',
          json: '',
          jsObject: undefined,
          markup: ''
        };

        for (var i = 0; i < children.length; i++) {
          var child = children[i];
          var info = {};

          switch (child.nodeName) {
            case 'SPAN':
              info = {
                string: child.textContent,
                type: child.attributes.type.textContent
              };
              buffer.tokens_unknown.push(info);
              break;

            case 'DIV':
              buffer.tokens_unknown.push({
                string: child.textContent,
                type: 'unknown'
              });
              break;

            case 'BR':
              if (child.textContent === '') buffer.tokens_unknown.push({
                string: '\n',
                type: 'unknown'
              });
              break;

            case '#text':
              buffer.tokens_unknown.push({
                string: child.wholeText,
                type: 'unknown'
              });
              break;

            case 'FONT':
              buffer.tokens_unknown.push({
                string: child.textContent,
                type: 'unknown'
              });
              break;

            default:
              console.error('Unrecognized node:', {
                child: child
              });
              break;
          }
        }

        for (var i = 0; i < buffer.tokens_unknown.length; i++) {
          var token = buffer.tokens_unknown[i];
          buffer.tokens_proto = buffer.tokens_proto.concat(quarkize(token.string, 'proto'));
        }

        for (var i = 0; i < buffer.tokens_proto.length; i++) {
          var _token = buffer.tokens_proto[i];

          if (_token.type.indexOf('proto') === -1) {
            if (!validToken(_token.string, _token.type)) {
              buffer.tokens_split = buffer.tokens_split.concat(quarkize(_token.string, 'split'));
            } else buffer.tokens_split.push(_token);
          } else buffer.tokens_split.push(_token);
        }

        for (var i = 0; i < buffer.tokens_split.length; i++) {
          var _token2 = buffer.tokens_split[i];
          var type = _token2.type,
              string = _token2.string,
              length = string.length,
              fallback = [];

          if (type.indexOf('-') > -1) {
            type = type.slice(type.indexOf('-') + 1);
            if (type !== 'string') fallback.push('string');
            fallback.push('key');
            fallback.push('error');
          }

          var tokul = {
            string: string,
            length: length,
            type: type,
            fallback: fallback
          };
          buffer.tokens_fallback.push(tokul);
        }

        var buffer2 = {
          brackets: [],
          stringOpen: false,
          isValue: false
        };

        for (var i = 0; i < buffer.tokens_fallback.length; i++) {
          var _token3 = buffer.tokens_fallback[i];
          var _type2 = _token3.type,
              _string2 = _token3.string;
          var normalToken = {
            type: _type2,
            string: _string2
          };

          switch (_type2) {
            case 'symbol':
            case 'colon':
              if (buffer2.stringOpen) {
                if (buffer2.isValue) normalToken.type = 'string';else normalToken.type = 'key';
                break;
              }

              switch (_string2) {
                case '[':
                case '{':
                  buffer2.brackets.push(_string2);
                  buffer2.isValue = buffer2.brackets[buffer2.brackets.length - 1] === '[';
                  break;

                case ']':
                case '}':
                  buffer2.brackets.pop();
                  buffer2.isValue = buffer2.brackets[buffer2.brackets.length - 1] === '[';
                  break;

                case ',':
                  if (tokenFollowed().type === 'colon') break;
                  buffer2.isValue = buffer2.brackets[buffer2.brackets.length - 1] === '[';
                  break;

                case ':':
                  normalToken.type = 'colon';
                  buffer2.isValue = true;
                  break;
              }

              break;

            case 'delimiter':
              if (buffer2.isValue) normalToken.type = 'string';else normalToken.type = 'key';

              if (!buffer2.stringOpen) {
                buffer2.stringOpen = _string2;
                break;
              }

              if (i > 0) {
                var previousToken = buffer.tokens_fallback[i - 1],
                    _string = previousToken.string,
                    _type = previousToken.type,
                    _char = _string.charAt(_string.length - 1);

                if (_type === 'string' && _char === '\\') break;
              }

              if (buffer2.stringOpen === _string2) {
                buffer2.stringOpen = false;
                break;
              }

              break;

            case 'primitive':
            case 'string':
              if (['false', 'true', 'null', 'undefined'].indexOf(_string2) > -1) {
                var lastIndex = buffer.tokens_normalize.length - 1;

                if (lastIndex >= 0) {
                  if (buffer.tokens_normalize[lastIndex].type !== 'string') {
                    normalToken.type = 'primitive';
                    break;
                  }

                  normalToken.type = 'string';
                  break;
                }

                normalToken.type = 'primitive';
                break;
              }

              if (_string2 === '\n') if (!buffer2.stringOpen) {
                normalToken.type = 'linebreak';
                break;
              }
              if (buffer2.isValue) normalToken.type = 'string';else normalToken.type = 'key';
              break;

            case 'space':
              if (buffer2.stringOpen) if (buffer2.isValue) normalToken.type = 'string';else normalToken.type = 'key';
              break;

            case 'number':
              if (buffer2.stringOpen) if (buffer2.isValue) normalToken.type = 'string';else normalToken.type = 'key';
              break;

            default:
              break;
          }

          buffer.tokens_normalize.push(normalToken);
        }

        for (var i = 0; i < buffer.tokens_normalize.length; i++) {
          var _token4 = buffer.tokens_normalize[i];
          var mergedToken = {
            string: _token4.string,
            type: _token4.type,
            tokens: [i]
          };
          if (['symbol', 'colon'].indexOf(_token4.type) === -1) if (i + 1 < buffer.tokens_normalize.length) {
            var count = 0;

            for (var u = i + 1; u < buffer.tokens_normalize.length; u++) {
              var nextToken = buffer.tokens_normalize[u];
              if (_token4.type !== nextToken.type) break;
              mergedToken.string += nextToken.string;
              mergedToken.tokens.push(u);
              count++;
            }

            i += count;
          }
          buffer.tokens_merge.push(mergedToken);
        }

        var quotes = '\'"',
            alphanumeric = 'abcdefghijklmnopqrstuvwxyz' + 'ABCDEFGHIJKLMNOPQRSTUVWXYZ' + '0123456789' + '_$';
        var error = false,
            line = buffer.tokens_merge.length > 0 ? 1 : 0;
        buffer2 = {
          brackets: [],
          stringOpen: false,
          isValue: false
        };
        var bracketList = [];

        for (var i = 0; i < buffer.tokens_merge.length; i++) {
          if (error) break;
          var _token5 = buffer.tokens_merge[i],
              _string3 = _token5.string,
              _type3 = _token5.type,
              found = false;

          switch (_type3) {
            case 'space':
              break;

            case 'linebreak':
              line++;
              break;

            case 'symbol':
              switch (_string3) {
                case '{':
                case '[':
                  found = followsSymbol(i, ['}', ']']);

                  if (found) {
                    setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.prohibited, {
                      firstToken: buffer.tokens_merge[found].string,
                      secondToken: _string3
                    }));
                    break;
                  }

                  if (_string3 === '[' && i > 0) if (!followsSymbol(i, [':', '[', ','])) {
                    setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.permitted, {
                      firstToken: "[",
                      secondToken: [":", "[", ","]
                    }));
                    break;
                  }
                  if (_string3 === '{') if (followsSymbol(i, ['{'])) {
                    setError(i, (0, _locale.format)(locale.invalidToken.double, {
                      token: "{"
                    }));
                    break;
                  }
                  buffer2.brackets.push(_string3);
                  buffer2.isValue = buffer2.brackets[buffer2.brackets.length - 1] === '[';
                  bracketList.push({
                    i: i,
                    line: line,
                    string: _string3
                  });
                  break;

                case '}':
                case ']':
                  if (_string3 === '}') if (buffer2.brackets[buffer2.brackets.length - 1] !== '{') {
                    setError(i, (0, _locale.format)(locale.brace.curly.missingOpen));
                    break;
                  }
                  if (_string3 === '}') if (followsSymbol(i, [','])) {
                    setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.prohibited, {
                      firstToken: ",",
                      secondToken: "}"
                    }));
                    break;
                  }
                  if (_string3 === ']') if (buffer2.brackets[buffer2.brackets.length - 1] !== '[') {
                    setError(i, (0, _locale.format)(locale.brace.square.missingOpen));
                    break;
                  }
                  if (_string3 === ']') if (followsSymbol(i, [':'])) {
                    setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.prohibited, {
                      firstToken: ":",
                      secondToken: "]"
                    }));
                    break;
                  }
                  buffer2.brackets.pop();
                  buffer2.isValue = buffer2.brackets[buffer2.brackets.length - 1] === '[';
                  bracketList.push({
                    i: i,
                    line: line,
                    string: _string3
                  });
                  break;

                case ',':
                  found = followsSymbol(i, ['{']);

                  if (found) {
                    if (followedBySymbol(i, ['}'])) {
                      setError(i, (0, _locale.format)(locale.brace.curly.cannotWrap, {
                        token: ","
                      }));
                      break;
                    }

                    setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.prohibited, {
                      firstToken: "{",
                      secondToken: ","
                    }));
                    break;
                  }

                  if (followedBySymbol(i, ['}', ',', ']'])) {
                    setError(i, (0, _locale.format)(locale.noTrailingOrLeadingComma));
                    break;
                  }

                  found = typeFollowed(i);

                  switch (found) {
                    case 'key':
                    case 'colon':
                      setError(i, (0, _locale.format)(locale.invalidToken.termSequence.prohibited, {
                        firstTerm: found === 'key' ? locale.types.key : locale.symbols.colon,
                        secondTerm: locale.symbols.comma
                      }));
                      break;

                    case 'symbol':
                      if (followsSymbol(i, ['{'])) {
                        setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.prohibited, {
                          firstToken: "{",
                          secondToken: ","
                        }));
                        break;
                      }

                      break;

                    default:
                      break;
                  }

                  buffer2.isValue = buffer2.brackets[buffer2.brackets.length - 1] === '[';
                  break;

                default:
                  break;
              }

              buffer.json += _string3;
              break;

            case 'colon':
              found = followsSymbol(i, ['[']);

              if (found && followedBySymbol(i, [']'])) {
                setError(i, (0, _locale.format)(locale.brace.square.cannotWrap, {
                  token: ":"
                }));
                break;
              }

              if (found) {
                setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.prohibited, {
                  firstToken: "[",
                  secondToken: ":"
                }));
                break;
              }

              if (typeFollowed(i) !== 'key') {
                setError(i, (0, _locale.format)(locale.invalidToken.termSequence.permitted, {
                  firstTerm: locale.symbols.colon,
                  secondTerm: locale.types.key
                }));
                break;
              }

              if (followedBySymbol(i, ['}', ']'])) {
                setError(i, (0, _locale.format)(locale.invalidToken.termSequence.permitted, {
                  firstTerm: locale.symbols.colon,
                  secondTerm: locale.types.value
                }));
                break;
              }

              buffer2.isValue = true;
              buffer.json += _string3;
              break;

            case 'key':
            case 'string':
              var firstChar = _string3.charAt(0),
                  lastChar = _string3.charAt(_string3.length - 1),
                  quote_primary = quotes.indexOf(firstChar);

              if (quotes.indexOf(firstChar) === -1) if (quotes.indexOf(lastChar) !== -1) {
                setError(i, (0, _locale.format)(locale.string.missingOpen, {
                  quote: firstChar
                }));
                break;
              }
              if (quotes.indexOf(lastChar) === -1) if (quotes.indexOf(firstChar) !== -1) {
                setError(i, (0, _locale.format)(locale.string.missingClose, {
                  quote: firstChar
                }));
                break;
              }
              if (quotes.indexOf(firstChar) > -1) if (firstChar !== lastChar) {
                setError(i, (0, _locale.format)(locale.string.missingClose, {
                  quote: firstChar
                }));
                break;
              }
              if ('string' === _type3) if (quotes.indexOf(firstChar) === -1 && quotes.indexOf(lastChar) === -1) {
                setError(i, (0, _locale.format)(locale.string.mustBeWrappedByQuotes));
                break;
              }
              if ('key' === _type3) if (followedBySymbol(i, ['}', ']'])) {
                setError(i, (0, _locale.format)(locale.invalidToken.termSequence.permitted, {
                  firstTerm: locale.types.key,
                  secondTerm: locale.symbols.colon
                }));
              }
              if (quotes.indexOf(firstChar) === -1 && quotes.indexOf(lastChar) === -1) for (var h = 0; h < _string3.length; h++) {
                if (error) break;

                var c = _string3.charAt(h);

                if (alphanumeric.indexOf(c) === -1) {
                  setError(i, (0, _locale.format)(locale.string.nonAlphanumeric, {
                    token: c
                  }));
                  break;
                }
              }
              if (firstChar === "'") _string3 = '"' + _string3.slice(1, -1) + '"';else if (firstChar !== '"') _string3 = '"' + _string3 + '"';
              if ('key' === _type3) if ('key' === typeFollowed(i)) {
                if (i > 0) if (!isNaN(buffer.tokens_merge[i - 1])) {
                  buffer.tokens_merge[i - 1] += buffer.tokens_merge[i];
                  setError(i, (0, _locale.format)(locale.key.numberAndLetterMissingQuotes));
                  break;
                }
                setError(i, (0, _locale.format)(locale.key.spaceMissingQuotes));
                break;
              }
              if ('key' === _type3) if (!followsSymbol(i, ['{', ','])) {
                setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.permitted, {
                  firstToken: _type3,
                  secondToken: ["{", ","]
                }));
                break;
              }
              if ('string' === _type3) if (!followsSymbol(i, ['[', ':', ','])) {
                setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.permitted, {
                  firstToken: _type3,
                  secondToken: ["[", ":", ","]
                }));
                break;
              }
              if ('key' === _type3) if (buffer2.isValue) {
                setError(i, (0, _locale.format)(locale.string.unexpectedKey));
                break;
              }
              if ('string' === _type3) if (!buffer2.isValue) {
                setError(i, (0, _locale.format)(locale.key.unexpectedString));
                break;
              }
              buffer.json += _string3;
              break;

            case 'number':
            case 'primitive':
              if (followsSymbol(i, ['{'])) {
                buffer.tokens_merge[i].type = 'key';
                _type3 = buffer.tokens_merge[i].type;
                _string3 = '"' + _string3 + '"';
              } else if (typeFollowed(i) === 'key') {
                buffer.tokens_merge[i].type = 'key';
                _type3 = buffer.tokens_merge[i].type;
              } else if (!followsSymbol(i, ['[', ':', ','])) {
                setError(i, (0, _locale.format)(locale.invalidToken.tokenSequence.permitted, {
                  firstToken: _type3,
                  secondToken: ["[", ":", ","]
                }));
                break;
              }

              if (_type3 !== 'key') if (!buffer2.isValue) {
                buffer.tokens_merge[i].type = 'key';
                _type3 = buffer.tokens_merge[i].type;
                _string3 = '"' + _string3 + '"';
              }
              if (_type3 === 'primitive') if (_string3 === 'undefined') setError(i, (0, _locale.format)(locale.invalidToken.useInstead, {
                badToken: "undefined",
                goodToken: "null"
              }));
              buffer.json += _string3;
              break;
          }
        }

        var noEscapedSingleQuote = '';

        for (var i = 0; i < buffer.json.length; i++) {
          var current = buffer.json.charAt(i),
              next = '';

          if (i + 1 < buffer.json.length) {
            next = buffer.json.charAt(i + 1);

            if (current === '\\' && next === "'") {
              noEscapedSingleQuote += next;
              i++;
              continue;
            }
          }

          noEscapedSingleQuote += current;
        }

        buffer.json = noEscapedSingleQuote;

        if (!error) {
          var removePair = function removePair(index) {
            bracketList.splice(index + 1, 1);
            bracketList.splice(index, 1);
            if (!delta) delta = true;
          };

          var maxIterations = Math.ceil(bracketList.length / 2);
          var round = 0,
              delta = false;

          while (bracketList.length > 0) {
            delta = false;

            for (var tokenCount = 0; tokenCount < bracketList.length - 1; tokenCount++) {
              var pair = bracketList[tokenCount].string + bracketList[tokenCount + 1].string;
              if (['[]', '{}'].indexOf(pair) > -1) removePair(tokenCount);
            }

            round++;
            if (!delta) break;
            if (round >= maxIterations) break;
          }

          if (bracketList.length > 0) {
            var _tokenString = bracketList[0].string,
                _tokenPosition = bracketList[0].i,
                _closingBracketType = _tokenString === '[' ? ']' : '}';

            line = bracketList[0].line;
            setError(_tokenPosition, (0, _locale.format)(locale.brace[_closingBracketType === ']' ? 'square' : 'curly'].missingClose));
          }
        }

        if (!error) if ([undefined, ''].indexOf(buffer.json) === -1) try {
          buffer.jsObject = JSON.parse(buffer.json);
        } catch (err) {
          var errorMessage = err.message,
              subsMark = errorMessage.indexOf('position');
          if (subsMark === -1) throw new Error('Error parsing failed');
          var errPositionStr = errorMessage.substring(subsMark + 9, errorMessage.length),
              errPosition = parseInt(errPositionStr);
          var charTotal = 0,
              tokenIndex = 0,
              _token6 = false,
              _line2 = 1,
              exitWhile = false;

          while (charTotal < errPosition && !exitWhile) {
            _token6 = buffer.tokens_merge[tokenIndex];
            if ('linebreak' === _token6.type) _line2++;
            if (['space', 'linebreak'].indexOf(_token6.type) === -1) charTotal += _token6.string.length;
            if (charTotal >= errPosition) break;
            tokenIndex++;
            if (!buffer.tokens_merge[tokenIndex + 1]) exitWhile = true;
          }

          line = _line2;
          var backslashCount = 0;

          for (var _i = 0; _i < _token6.string.length; _i++) {
            var char = _token6.string.charAt(_i);

            if (char === '\\') backslashCount = backslashCount > 0 ? backslashCount + 1 : 1;else {
              if (backslashCount % 2 !== 0 || backslashCount === 0) if ('\'"bfnrt'.indexOf(char) === -1) {
                setError(tokenIndex, (0, _locale.format)(locale.invalidToken.unexpected, {
                  token: '\\'
                }));
              }
              backslashCount = 0;
            }
          }

          if (!error) setError(tokenIndex, (0, _locale.format)(locale.invalidToken.unexpected, {
            token: _token6.string
          }));
        }
        var _line = 1,
            _depth = 0;
        ;
        if (!error) for (var i = 0; i < buffer.tokens_merge.length; i++) {
          var _token7 = buffer.tokens_merge[i],
              _string4 = _token7.string,
              _type4 = _token7.type;

          switch (_type4) {
            case 'space':
            case 'linebreak':
              break;

            case 'string':
            case 'number':
            case 'primitive':
            case 'error':
              buffer.markup += (followsSymbol(i, [',', '[']) ? newLineBreakAndIndent() : '') + newSpan(i, _token7, _depth);
              break;

            case 'key':
              buffer.markup += newLineBreakAndIndent() + newSpan(i, _token7, _depth);
              break;

            case 'colon':
              buffer.markup += newSpan(i, _token7, _depth) + '&nbsp;';
              break;

            case 'symbol':
              switch (_string4) {
                case '[':
                case '{':
                  buffer.markup += (!followsSymbol(i, [':']) ? newLineBreakAndIndent() : '') + newSpan(i, _token7, _depth);
                  _depth++;
                  break;

                case ']':
                case '}':
                  _depth--;

                  var islastToken = i === buffer.tokens_merge.length - 1,
                      _adjustment = i > 0 ? ['[', '{'].indexOf(buffer.tokens_merge[i - 1].string) > -1 ? '' : newLineBreakAndIndent(islastToken) : '';

                  buffer.markup += _adjustment + newSpan(i, _token7, _depth);
                  break;

                case ',':
                  buffer.markup += newSpan(i, _token7, _depth);
                  break;
              }

              break;
          }
        }

        if (error) {
          var countCarrigeReturn = function countCarrigeReturn(string) {
            var count = 0;

            for (var i = 0; i < string.length; i++) {
              if (['\n', '\r'].indexOf(string[i]) > -1) count++;
            }

            return count;
          };

          var _line_fallback = 1;
          _line = 1;

          for (var i = 0; i < buffer.tokens_merge.length; i++) {
            var _token8 = buffer.tokens_merge[i],
                _type5 = _token8.type,
                _string5 = _token8.string;
            if (_type5 === 'linebreak') _line++;
            buffer.markup += newSpan(i, _token8, _depth);
            _line_fallback += countCarrigeReturn(_string5);
          }

          _line++;
          _line_fallback++;
          if (_line < _line_fallback) _line = _line_fallback;
        }

        for (var i = 0; i < buffer.tokens_merge.length; i++) {
          var _token9 = buffer.tokens_merge[i];
          buffer.indented += _token9.string;
          if (['space', 'linebreak'].indexOf(_token9.type) === -1) buffer.tokens_plainText += _token9.string;
        }

        if (error) {
          var isFunction = function isFunction(functionToCheck) {
            return functionToCheck && {}.toString.call(functionToCheck) === '[object Function]';
          };

          if ('modifyErrorText' in this.props) if (isFunction(this.props.modifyErrorText)) error.reason = this.props.modifyErrorText(error.reason);
        }

        return {
          tokens: buffer.tokens_merge,
          noSpaces: buffer.tokens_plainText,
          indented: buffer.indented,
          json: buffer.json,
          jsObject: buffer.jsObject,
          markup: buffer.markup,
          lines: _line,
          error: error
        };
      }

      ;
      /**
       *     JS OBJECTS || PLACEHOLDER
       */

      if (!('nodeType' in something)) {
        var escape_character = function escape_character() {
          if (_buffer.currentChar !== '\\') return false;
          _buffer.inputText = deleteCharAt(_buffer.inputText, _buffer.position);
          return true;
        };

        var deleteCharAt = function deleteCharAt(string, position) {
          return string.slice(0, position) + string.slice(position + 1);
        };

        var determine_string = function determine_string() {
          if ('\'"'.indexOf(_buffer.currentChar) === -1) return false;

          if (!_buffer.stringOpen) {
            add_tokenSecondary();
            _buffer.stringStart = _buffer.position;
            _buffer.stringOpen = _buffer.currentChar;
            return true;
          }

          if (_buffer.stringOpen === _buffer.currentChar) {
            add_tokenSecondary();

            var stringToken = _buffer.inputText.substring(_buffer.stringStart, _buffer.position + 1);

            add_tokenPrimary(stringToken);
            _buffer.stringOpen = false;
            return true;
          }

          return false;
        };

        var determine_value = function determine_value() {
          if (':,{}[]'.indexOf(_buffer.currentChar) === -1) return false;
          if (_buffer.stringOpen) return false;
          add_tokenSecondary();
          add_tokenPrimary(_buffer.currentChar);

          switch (_buffer.currentChar) {
            case ':':
              _buffer.isValue = true;
              return true;
              break;

            case '{':
            case '[':
              _buffer.brackets.push(_buffer.currentChar);

              break;

            case '}':
            case ']':
              _buffer.brackets.pop();

              break;
          }

          if (_buffer.currentChar !== ':') _buffer.isValue = _buffer.brackets[_buffer.brackets.length - 1] === '[';
          return true;
        };

        var add_tokenSecondary = function add_tokenSecondary() {
          if (_buffer.tokenSecondary.length === 0) return false;

          _buffer.tokens.push(_buffer.tokenSecondary);

          _buffer.tokenSecondary = '';
          return true;
        };

        var add_tokenPrimary = function add_tokenPrimary(value) {
          if (value.length === 0) return false;

          _buffer.tokens.push(value);

          return true;
        };

        var indent = function indent(number) {
          var space = [];

          for (var i = 0; i < number * 2; i++) {
            space.push(' ');
          }

          return (number > 0 ? '\n' : '') + space.join('');
        };

        var indentII = function indentII(number) {
          var space = [];
          if (number > 0) lines++;

          for (var i = 0; i < number * 2; i++) {
            space.push('&nbsp;');
          }

          return (number > 0 ? '<br>' : '') + space.join('');
        };

        var _buffer = {
          inputText: JSON.stringify(something),
          position: 0,
          currentChar: '',
          tokenPrimary: '',
          tokenSecondary: '',
          brackets: [],
          isValue: false,
          stringOpen: false,
          stringStart: 0,
          tokens: []
        };

        for (var i = 0; i < _buffer.inputText.length; i++) {
          _buffer.position = i;
          _buffer.currentChar = _buffer.inputText.charAt(_buffer.position);

          var a = determine_value(),
              b = determine_string(),
              _c = escape_character();

          if (!a && !b && !_c) if (!_buffer.stringOpen) _buffer.tokenSecondary += _buffer.currentChar;
        }

        var _buffer2 = {
          brackets: [],
          isValue: false,
          tokens: []
        };
        _buffer2.tokens = _buffer.tokens.map(function (token) {
          var type = '',
              string = '',
              value = '';

          switch (token) {
            case ',':
              type = 'symbol';
              string = token;
              value = token;
              _buffer2.isValue = _buffer2.brackets[_buffer2.brackets.length - 1] === '[';
              break;

            case ':':
              type = 'symbol';
              string = token;
              value = token;
              _buffer2.isValue = true;
              break;

            case '{':
            case '[':
              type = 'symbol';
              string = token;
              value = token;

              _buffer2.brackets.push(token);

              _buffer2.isValue = _buffer2.brackets[_buffer2.brackets.length - 1] === '[';
              break;

            case '}':
            case ']':
              type = 'symbol';
              string = token;
              value = token;

              _buffer2.brackets.pop();

              _buffer2.isValue = _buffer2.brackets[_buffer2.brackets.length - 1] === '[';
              break;

            case 'undefined':
              type = 'primitive';
              string = token;
              value = undefined;
              break;

            case 'null':
              type = 'primitive';
              string = token;
              value = null;
              break;

            case 'false':
              type = 'primitive';
              string = token;
              value = false;
              break;

            case 'true':
              type = 'primitive';
              string = token;
              value = true;
              break;

            default:
              var C = token.charAt(0);

              var stripQuotesFromKey = function stripQuotesFromKey(text) {
                if (text.length === 0) return text;
                if (['""', "''"].indexOf(text) > -1) return "''";
                var wrappedInQuotes = false;

                for (var i = 0; i < 2; i++) {
                  if ([text.charAt(0), text.charAt(text.length - 1)].indexOf(['"', "'"][i]) > -1) {
                    wrappedInQuotes = true;
                    break;
                  }
                }

                if (wrappedInQuotes && text.length >= 2) text = text.slice(1, -1);

                var nonAlphaNumeric = text.replace(/\w/g, ''),
                    alphaNumeric = text.replace(/\W+/g, ''),
                    mayRemoveQuotes = function (nonAlphaNumeric, text) {
                  var numberAndLetter = false;

                  for (var i = 0; i < text.length; i++) {
                    if (i === 0) if (isNaN(text.charAt(i))) break;

                    if (isNaN(text.charAt(i))) {
                      numberAndLetter = true;
                      break;
                    }
                  }

                  return !(nonAlphaNumeric.length > 0 || numberAndLetter);
                }(nonAlphaNumeric, text),
                    hasQuotes = function (string) {
                  for (var i = 0; i < string.length; i++) {
                    if (["'", '"'].indexOf(string.charAt(i)) > -1) return true;
                  }

                  return false;
                }(nonAlphaNumeric);

                if (hasQuotes) {
                  var newText = '';
                  var charList = text.split('');

                  for (var ii = 0; ii < charList.length; ii++) {
                    var _char2 = charList[ii];
                    if (["'", '"'].indexOf(_char2) > -1) _char2 = '\\' + _char2;
                    newText += _char2;
                  }

                  text = newText;
                }

                if (!mayRemoveQuotes) return "'" + text + "'";else return text;
              };

              if ('\'"'.indexOf(C) > -1) {
                if (_buffer2.isValue) type = 'string';else type = 'key';
                if (type === 'key') string = stripQuotesFromKey(token);

                if (type === 'string') {
                  string = '';
                  var charList2 = token.slice(1, -1).split('');

                  for (var ii = 0; ii < charList2.length; ii++) {
                    var _char3 = charList2[ii];
                    if ('\'\"'.indexOf(_char3) > -1) _char3 = '\\' + _char3;
                    string += _char3;
                  }

                  string = "'" + string + "'";
                }

                value = string;
                break;
              }

              if (!isNaN(token)) {
                type = 'number';
                string = token;
                value = Number(token);
                break;
              }

              if (token.length > 0) if (!_buffer2.isValue) {
                type = 'key';
                string = token;
                if (string.indexOf(' ') > -1) string = "'" + string + "'";
                value = string;
                break;
              }
          }

          return {
            type: type,
            string: string,
            value: value,
            depth: _buffer2.brackets.length
          };
        });
        var clean = '';

        for (var i = 0; i < _buffer2.tokens.length; i++) {
          var _token10 = _buffer2.tokens[i];
          clean += _token10.string;
        }

        ;
        var indentation = '';

        for (var i = 0; i < _buffer2.tokens.length; i++) {
          var _token11 = _buffer2.tokens[i];

          switch (_token11.string) {
            case '[':
            case '{':
              var _nextToken2 = i < _buffer2.tokens.length - 1 - 1 ? _buffer2.tokens[i + 1] : '';

              if ('}]'.indexOf(_nextToken2.string) === -1) indentation += _token11.string + indent(_token11.depth);else indentation += _token11.string;
              break;

            case ']':
            case '}':
              var prevToken = i > 0 ? _buffer2.tokens[i - 1] : '';
              if ('[{'.indexOf(prevToken.string) === -1) indentation += indent(_token11.depth) + _token11.string;else indentation += _token11.string;
              break;

            case ':':
              indentation += _token11.string + ' ';
              break;

            case ',':
              indentation += _token11.string + indent(_token11.depth);
              break;

            default:
              indentation += _token11.string;
              break;
          }
        }

        var lines = 1;
        ;
        var markup = '';

        var _lastIndex = _buffer2.tokens.length - 1;

        for (var i = 0; i < _buffer2.tokens.length; i++) {
          var _token12 = _buffer2.tokens[i];
          var span = newSpan(i, _token12, _token12.depth);

          switch (_token12.string) {
            case '{':
            case '[':
              var _nextToken3 = i < _buffer2.tokens.length - 1 - 1 ? _buffer2.tokens[i + 1] : '';

              if ('}]'.indexOf(_nextToken3.string) === -1) markup += span + indentII(_token12.depth);else markup += span;
              break;

            case '}':
            case ']':
              var _prevToken = i > 0 ? _buffer2.tokens[i - 1] : '';

              if ('[{'.indexOf(_prevToken.string) === -1) markup += indentII(_token12.depth) + (_lastIndex === i ? '<br>' : '') + span;else markup += span;
              break;

            case ':':
              markup += span + ' ';
              break;

            case ',':
              markup += span + indentII(_token12.depth);
              break;

            default:
              markup += span;
              break;
          }
        }

        lines += 2;
        return {
          tokens: _buffer2.tokens,
          noSpaces: clean,
          indented: indentation,
          json: JSON.stringify(something),
          jsObject: something,
          markup: markup,
          lines: lines
        };
      }
    }
  }]);
  return JSONInput;
}(_react.Component);

var _default = JSONInput;
exports.default = _default;