(function (win) {
    var hasOwnProperty = Object.prototype.hasOwnProperty;
    var JSBridge = win.JSBridge || (win.JSBridge = {});
    var JSBRIDGE_PROTOCOL = 'JSBridge';
    var Inner = {
        callbacks: {},
        call: function (obj, method, params, callback) {
            console.log(obj+" "+method+" "+params+" "+callback);
            var port = Util.getPort();
            console.log(port);
            this.callbacks[port] = callback;
            var uri=Util.getUri(obj,method,params,port);
            console.log(uri);
            window.prompt(uri, "");
        },
        onFinish: function (port, jsonObj){
            var callback = this.callbacks[port];
            callback && callback(jsonObj);
            delete this.callbacks[port];
        },
        onFailure: function (port, jsonObj) {
            var result = Utils.getResultMsg(101, jsonObj, null);
            if (jsonObj && jsonObj.errorCode !== undefined && jsonObj.errorMsg != undefined
                && jsonObj.data !== undefined) {
                result = jsonObj;
            }
            var callback = callbacks[port];
            callback && callback(result);
            onComplete(port);
        },
    };
    var Util = {
        getPort: function () {
            return Math.floor(Math.random() * (1 << 30));
        },
        getUri:function(obj, method, params, port){
            params = this.getParam(params);
            var uri;
            if(params == null || port == null) {
                 uri = JSBRIDGE_PROTOCOL + '://' + obj + '/' + method + '?' + null + '&callback=' + 0;
            } else {
                 uri = JSBRIDGE_PROTOCOL + '://' + obj + '/' + method + '?' + params + '&callback=' + port;
            }
            return uri;
        },
        getParam:function(obj){
            if (obj && typeof obj === 'object') {
                return JSON.stringify(obj);
            } else {
                return obj || '';
            }
        }
    };
    for (var key in Inner) {
        if (!hasOwnProperty.call(JSBridge, key)) {
            JSBridge[key] = Inner[key];
        }
    }
})(window);