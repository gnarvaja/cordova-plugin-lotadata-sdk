/*global cordova, module*/

module.exports = {
    initialize: function (apiKey, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "MomentsPlugin", "initialize", [apiKey]);
    },
    recordEvent: function (eventName, eventData, successCallback, errorCallback) {
        if (eventData !== null && eventData !== undefined)
            cordova.exec(successCallback, errorCallback, "MomentsPlugin", "recordEvent", [eventName, eventData]);
        else
            cordova.exec(successCallback, errorCallback, "MomentsPlugin", "recordEvent", [eventName]);
    },
    setFgTrackingMode: function (trackingMode, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "MomentsPlugin", "setFgTrackingMode", [trackingMode]);
    },
    setBgTrackingMode: function (trackingMode, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "MomentsPlugin", "setBgTrackingMode", [trackingMode]);
    },
    bestKnownLocation: function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "MomentsPlugin", "bestKnownLocation", []);
    },
    // TODO: add more moments SDK functions
};
