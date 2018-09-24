# Cordova Plugin for LotaData SDK

Check the [SDK Documentation](https://docs.lotadata.com/) for more instructions and our [demo repository](https://github.com/LotaData-Inc/cordova-sample-app) for an example of installing and using the plugin along with a Cordova app.

## Installation

```
$ cordova plugin add https://github.com/LotaData-Inc/cordova-plugin-lotadata-sdk.git#<version>
```

---

#### Questions?

We are eager to hear from you and happy to share best practices for integrating the SDK. There are many ways for you to reach us:
* Email [support@lotadata.com](mailto:support@lotadata.com)
* [Telegram @lotadata_sdk](http://t.me/lotadata_sdk)
* Slack developer community

But first, please [sign up for an account with LotaData](http://platform.lotadata.com/?signup=true) so that we may send you the link to download our SDK.

---

## API Reference

<a name="module_moments"></a>

* [moments](#module_moments)
    * [.TrackingMode](#module_moments.TrackingMode) : <code>enum</code>
    * [.initialize(successCallback, errorCallback)](#module_moments.initialize)
    * [.recordEvent(eventName, eventData, successCallback, errorCallback)](#module_moments.recordEvent)
    * [.setFgTrackingMode(trackingMode, successCallback, errorCallback)](#module_moments.setFgTrackingMode)
    * [.setBgTrackingMode(trackingMode, successCallback, errorCallback)](#module_moments.setBgTrackingMode)
    * [.bestKnownLocation(successCallback, errorCallback)](#module_moments.bestKnownLocation)
    * [.onError](#module_moments.onError) : <code>function</code>
    * [.onSuccess](#module_moments.onSuccess) : <code>function</code>

<a name="module_moments.TrackingMode"></a>

### moments.TrackingMode : <code>enum</code>
**Kind**: static enum of [<code>moments</code>](#module_moments)  
**Properties**

| Name | Type | Default |
| --- | --- | --- |
| MANUAL | <code>string</code> | <code>&quot;MANUAL&quot;</code> | 
| MINIMAL_POWER | <code>string</code> | <code>&quot;MINIMAL_POWER&quot;</code> | 
| STAY_DETECTION | <code>string</code> | <code>&quot;STAY_DETECTION&quot;</code> | 
| ROUTE | <code>string</code> | <code>&quot;ROUTE&quot;</code> | 
| HAWK_EYE | <code>string</code> | <code>&quot;HAWK_EYE&quot;</code> | 

<a name="module_moments.initialize"></a>

### moments.initialize(successCallback, errorCallback)
**Kind**: static method of [<code>moments</code>](#module_moments)  

| Param | Type |
| --- | --- |
| successCallback | [<code>onSuccess</code>](#module_moments.onSuccess) | 
| errorCallback | [<code>onError</code>](#module_moments.onError) | 

**Example**  
```js
plugins.moments.initialize(success, error);
```
<a name="module_moments.recordEvent"></a>

### moments.recordEvent(eventName, eventData, successCallback, errorCallback)
**Kind**: static method of [<code>moments</code>](#module_moments)  

| Param | Type |
| --- | --- |
| eventName | <code>string</code> | 
| eventData | <code>number</code> | 
| successCallback | [<code>onSuccess</code>](#module_moments.onSuccess) | 
| errorCallback | [<code>onError</code>](#module_moments.onError) | 

**Example**  
```js
// Sending a record event with no data binded to it
plugins.moments.recordEvent("app launched", null, success, error);
// Sending a record event with a numerical value associated to it
plugins.moments.recordEvent("level completed", 10, success, error);
```
<a name="module_moments.setFgTrackingMode"></a>

### moments.setFgTrackingMode(trackingMode, successCallback, errorCallback)
**Kind**: static method of [<code>moments</code>](#module_moments)  

| Param | Type |
| --- | --- |
| trackingMode | [<code>TrackingMode</code>](#module_moments.TrackingMode) | 
| successCallback | [<code>onSuccess</code>](#module_moments.onSuccess) | 
| errorCallback | [<code>onError</code>](#module_moments.onError) | 

**Example**  
```js
plugins.moments.setFgTrackingMode(plugins.moments.TrackingMode.ROUTE, success, error);
```
<a name="module_moments.setBgTrackingMode"></a>

### moments.setBgTrackingMode(trackingMode, successCallback, errorCallback)
**Kind**: static method of [<code>moments</code>](#module_moments)  

| Param | Type |
| --- | --- |
| trackingMode | [<code>TrackingMode</code>](#module_moments.TrackingMode) | 
| successCallback | [<code>onSuccess</code>](#module_moments.onSuccess) | 
| errorCallback | [<code>onError</code>](#module_moments.onError) | 

**Example**  
```js
plugins.moments.setBgTrackingMode(plugins.moments.TrackingMode.MINIMAL_POWER, success, error);
```
<a name="module_moments.bestKnownLocation"></a>

### moments.bestKnownLocation(successCallback, errorCallback)
**Kind**: static method of [<code>moments</code>](#module_moments)  

| Param | Type |
| --- | --- |
| successCallback | [<code>onSuccess</code>](#module_moments.onSuccess) | 
| errorCallback | [<code>onError</code>](#module_moments.onError) | 

**Example**  
```js
plugins.moments.bestKnownLocation(success, error);
```
<a name="module_moments.onError"></a>

### moments.onError : <code>function</code>
Callback function that provides an error message.

**Kind**: static typedef of [<code>moments</code>](#module_moments)  

| Param | Type | Description |
| --- | --- | --- |
| message | <code>string</code> | The message is provided by the device's native code. |

<a name="module_moments.onSuccess"></a>

### moments.onSuccess : <code>function</code>
Callback function that provides a successful message.

**Kind**: static typedef of [<code>moments</code>](#module_moments)  

| Param | Type | Description |
| --- | --- | --- |
| message | <code>string</code> | The message is provided by the device's native code. |

---
