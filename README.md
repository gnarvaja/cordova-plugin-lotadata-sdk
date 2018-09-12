# Cordova Plugin for LotaData SDK

https://docs.lotadata.com/mobile-sdk/cordova.html

## Installation

```
$ cordova plugin add https://github.com/LotaData-Inc/cordova-plugin-lotadata-sdk.git
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
    * [.initialize(successCallback, errorCallback)](#module_moments.initialize)
    * [.recordEvent(eventName, eventData, successCallback, errorCallback)](#module_moments.recordEvent)
    * [.onError](#module_moments.onError) : <code>function</code>
    * [.onSuccess](#module_moments.onSuccess) : <code>function</code>

<a name="module_moments.initialize"></a>

### moments.initialize(successCallback, errorCallback)
Initializes the sdk. Recommended to ask for permissions 
(if needed) before doing so.

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
An in-app event represents an event or action performed 
by the end user within your mobile app. LotaData's SDK enables you to 
define, tag, record, and analyze in-app events for your mobile users. 
Example of commonly tagged events are: “app launched”, “app moved to foreground”, 
“app moved to background”, “start button clicked", "game level complete",
"transaction start", "payment complete", "packaged picked up", "delivery complete".

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