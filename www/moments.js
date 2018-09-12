/**
 * @exports moments
 */
var moments = {};

/**
 * Callback function that provides an error message.
 * @callback module:moments.onError
 * @param {string} message - The message is provided by the device's native code.
 */

/**
 * Callback function that provides a successful message.
 * @callback module:moments.onSuccess
 * @param {string} message - The message is provided by the device's native code.
 */


/**
 * @description Initializes the sdk. Recommended to ask for permissions 
 * (if needed) before doing so.
 * @example
 * plugins.moments.initialize(success, error);
 * @param {module:moments.onSuccess} successCallback
 * @param {module:moments.onError} errorCallback
 */
moments.initialize = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "initialize", []);
};


/**
 * @description An in-app event represents an event or action performed 
 * by the end user within your mobile app. LotaData's SDK enables you to 
 * define, tag, record, and analyze in-app events for your mobile users. 
 * Example of commonly tagged events are: “app launched”, “app moved to foreground”, 
 * “app moved to background”, “start button clicked", "game level complete",
 * "transaction start", "payment complete", "packaged picked up", "delivery complete".
 * @example
 * // Sending a record event with no data binded to it
 * plugins.moments.recordEvent("app launched", null, success, error);

 * // Sending a record event with a numerical value associated to it
 * plugins.moments.recordEvent("level completed", 10, success, error);
 * @param {string} eventName
 * @param {number} eventData
 * @param {module:moments.onSuccess} successCallback
 * @param {module:moments.onError} errorCallback
 */
moments.recordEvent = function (eventName, eventData, successCallback, errorCallback) {
    if (eventData !== null && eventData !== undefined)
        cordova.exec(successCallback, errorCallback, "MomentsPlugin", "recordEvent", [eventName, eventData]);
    else
        cordova.exec(successCallback, errorCallback, "MomentsPlugin", "recordEvent", [eventName]);
};

moments.setFgTrackingMode = function (trackingMode, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "setFgTrackingMode", [trackingMode]);
};

moments.setBgTrackingMode = function (trackingMode, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "setBgTrackingMode", [trackingMode]);
};

moments.bestKnownLocation = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "bestKnownLocation", []);
};

module.exports = moments;