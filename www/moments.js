var moments = {};

moments.initialize = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MomentsPlugin", "initialize", []);
};

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