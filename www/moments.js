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
 * @example
 * plugins.moments.initialize(success, error);
 * @param {module:moments.onSuccess} successCallback
 * @param {module:moments.onError} errorCallback
 */
moments.initialize = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "initialize", []);
};


/**
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


/**
 * @example
 * plugins.moments.setFgTrackingMode("Route", success, error);
 * @param {string} trackingMode - Check [SDK Tracking Modes](https://docs.lotadata.com/android-sdk/overview.html#sdk-tracking-modes)
 * @param {module:moments.onSuccess} successCallback
 * @param {module:moments.onError} errorCallback
 */
moments.setFgTrackingMode = function (trackingMode, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "setFgTrackingMode", [trackingMode]);
};

/**
 * @example
 * plugins.moments.setBgTrackingMode("MINIMAL_POWER", success, error);
 * @param {string} trackingMode - Check [SDK Tracking Modes](https://docs.lotadata.com/android-sdk/overview.html#sdk-tracking-modes)
 * @param {module:moments.onSuccess} successCallback
 * @param {module:moments.onError} errorCallback
 */
moments.setBgTrackingMode = function (trackingMode, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "setBgTrackingMode", [trackingMode]);
};

/**
 * @example
 * plugins.moments.bestKnownLocation(success, error);
 * @param {module:moments.onSuccess} successCallback
 * @param {module:moments.onError} errorCallback
 */
moments.bestKnownLocation = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "bestKnownLocation", []);
};

module.exports = moments;