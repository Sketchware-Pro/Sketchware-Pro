package a.a.a

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import java.util.ArrayList
import java.util.HashMap
import mod.agus.jcoderz.editor.event.ManageEvent
import pro.sketchware.R

object xB {

    /**
     * Bridge for precompiled Rs.class which invokes `xB.b().a(...)`.
     */
    @JvmStatic
    fun b(): xB = this

    private val specs = HashMap<String, String>()

    @JvmStatic
    fun getString(context: Context, resId: Int): String = getString(context.resources, resId)

    fun a(context: Context, resId: Int): String = getString(context, resId)

    @JvmStatic
    fun getString(resources: Resources, resId: Int): String {
        return try {
            resources.getString(resId)
        } catch (_: Exception) {
            ""
        }
    }

    fun a(resources: Resources, resId: Int): String = getString(resources, resId)

    @JvmStatic
    fun getSpec(context: Context, key: String): String {
        if (specs.isEmpty()) {
            initializeSpecs(context)
        }
        return specs[key] ?: ""
    }

    /**
     * Invoked by Rs.class (important-classes.jar) via invokevirtual to get built-in block specs and "else" label.
     */
    fun a(context: Context, key: String): String = getSpec(context, key)

    /**
     * Invoked by LogicEditorActivity to get the header title for an event activity.
     */
    @JvmStatic
    fun getEventTitle(context: Context, viewId: String, eventName: String): String {
        if (specs.isEmpty()) {
            initializeSpecs(context)
        }
        return when (eventName) {
            "onClick",
            "onCheckedChange",
            "onItemSelected",
            "onItemClicked",
            "onItemLongClicked",
            "onTextChanged",
            "onPageStarted",
            "onPageFinished",
            "onProgressChanged",
            "onStartTrackingTouch",
            "onStopTrackingTouch",
            "onAnimationStart",
            "onAnimationEnd",
            "onAnimationCancel",
            "onBindCustomView",
            "onDateChange",
            "onChildAdded",
            "onChildChanged",
            "onChildRemoved",
            "onCancelled",
            "onUploadProgress",
            "onDownloadProgress",
            "onUploadSuccess",
            "onDownloadSuccess",
            "onDeleteSuccess",
            "onFailure",
            "onPictureTaken",
            "onPictureTakenCancel",
            "onFilesPicked",
            "onFilesPickedCancel",
            "onResponse",
            "onErrorResponse",
            "onSpeechResult",
            "onSpeechError",
            "onConnected",
            "onDataReceived",
            "onDataSent",
            "onConnectionError",
            "onConnectionStopped",
            "onMapReady",
            "onMarkerClicked",
            "onLocationChanged" -> {
                val eventSpec = specs[eventName] ?: ""
                "${getString(context, R.string.root_spec_common_when)} $viewId $eventSpec"
            }

            else -> {
                specs[eventName] ?: ManageEvent.i(viewId, eventName)
            }
        }
    }

    fun a(context: Context, viewId: String, eventName: String): String =
        getEventTitle(context, viewId, eventName)

    private fun buildBlockSpec(context: Context, prefix: String, parameters: ArrayList<String>): String {
        val parameterCount = if (parameters.size > 1) parameters.size - 1 else 0
        val sb = StringBuilder(1024)
        val head = getResourceString(context, "${prefix}_head")
        sb.append(head)

        var index = 0
        if (parameters.isNotEmpty()) {
            if (sb.isNotEmpty()) sb.append(" ")
            sb.append(parameters[0])
        }

        while (index < parameterCount) {
            index++
            val body = getResourceString(context, "${prefix}_body_$index")
            if (body.isNotEmpty()) {
                if (sb.isNotEmpty()) sb.append(" ")
                sb.append(body)
            }
            if (sb.isNotEmpty()) sb.append(" ")
            sb.append(parameters[index])
        }

        val tail = getResourceString(context, "${prefix}_tail")
        if (tail.isNotEmpty()) {
            if (sb.isNotEmpty()) sb.append(" ")
            sb.append(tail)
        }

        return sb.toString()
    }

    @SuppressLint("DiscouragedApi")
    private fun getResourceString(context: Context, name: String): String {
        val id = context.resources.getIdentifier(name, "string", context.packageName)
        if (id != 0) {
            try {
                return context.getString(id)
            } catch (_: Exception) {
            }
        }
        return ""
    }

    private fun registerBlockSpec(context: Context, opCode: String) {
        specs[opCode] = buildBlockSpec(context, "block_" + lq.d(opCode), lq.a(opCode))
    }

    private fun registerEventSpec(context: Context, eventName: String) {
        specs[eventName] = buildBlockSpec(context, "root_spec_" + lq.c(eventName), lq.b(eventName))
    }

    private fun initializeSpecs(context: Context) {
        specs.clear()

        listOf(
            "initializeLogic",
            "onBackPressed",
            "onPostCreate",
            "onStart",
            "onStop",
            "onDestroy",
            "onResume",
            "onPause",
            "moreBlock",
            "onClick",
            "onCheckedChange",
            "onItemSelected",
            "onItemClicked",
            "onItemLongClicked",
            "onTextChanged",
            "onPageStarted",
            "onPageFinished",
            "onProgressChanged",
            "onStartTrackingTouch",
            "onStopTrackingTouch",
            "onAnimationStart",
            "onAnimationEnd",
            "onAnimationCancel",
            "onBindCustomView",
            "onDateChange",
            "onChildAdded",
            "onChildChanged",
            "onChildRemoved",
            "onCancelled",
            "onSensorChanged",
            "onCreateUserComplete",
            "onSignInUserComplete",
            "onResetPasswordEmailSent",
            "onUploadProgress",
            "onDownloadProgress",
            "onUploadSuccess",
            "onDownloadSuccess",
            "onDeleteSuccess",
            "onFailure",
            "onPictureTaken",
            "onPictureTakenCancel",
            "onFilesPicked",
            "onFilesPickedCancel",
            "onAdLoaded",
            "onAdFailedToLoad",
            "onAdOpened",
            "onAdClosed",
            "onResponse",
            "onErrorResponse",
            "onSpeechResult",
            "onSpeechError",
            "onConnected",
            "onDataReceived",
            "onDataSent",
            "onConnectionError",
            "onConnectionStopped",
            "onMapReady",
            "onMarkerClicked",
            "onLocationChanged",
        ).forEach { registerEventSpec(context, it) }

        listOf(
            "viewOnClick",
            "setVarBoolean",
            "setVarInt",
            "increaseInt",
            "decreaseInt",
            "setVarString",
            "mapCreateNew",
            "mapPut",
            "mapGet",
            "mapContainKey",
            "mapRemoveKey",
            "mapSize",
            "mapClear",
            "mapIsEmpty",
            "mapGetAllKeys",
            "addListInt",
            "insertListInt",
            "deleteList",
            "getAtListInt",
            "indexListInt",
            "lengthList",
            "containListInt",
            "clearList",
            "addListStr",
            "insertListStr",
            "getAtListStr",
            "indexListStr",
            "containListStr",
            "addListMap",
            "insertListMap",
            "getAtListMap",
            "setListMap",
            "containListMap",
            "addMapToList",
            "insertMapToList",
            "getMapInList",
            "repeat",
            "forever",
            "break",
            "if",
            "ifElse",
            "else",
            "true",
            "false",
            "<",
            "=",
            ">",
            "&&",
            "||",
            "not",
            "+",
            "-",
            "*",
            "/",
            "%",
            "random",
            "stringLength",
            "stringJoin",
            "stringIndex",
            "stringLastIndex",
            "stringSub",
            "stringEquals",
            "stringContains",
            "stringReplace",
            "stringReplaceFirst",
            "stringReplaceAll",
            "toNumber",
            "trim",
            "toUpperCase",
            "toLowerCase",
            "toString",
            "toStringWithDecimal",
            "toStringFormat",
            "addSourceDirectly",
            "mapToStr",
            "strToMap",
            "listMapToStr",
            "strToListMap",
            "mathGetDip",
            "mathGetDisplayWidth",
            "mathGetDisplayHeight",
            "mathPi",
            "mathE",
            "mathPow",
            "mathMin",
            "mathMax",
            "mathSqrt",
            "mathAbs",
            "mathRound",
            "mathCeil",
            "mathFloor",
            "mathSin",
            "mathCos",
            "mathTan",
            "mathAsin",
            "mathAcos",
            "mathAtan",
            "mathExp",
            "mathLog",
            "mathLog10",
            "mathToRadian",
            "mathToDegree",
            "isDrawerOpen",
            "openDrawer",
            "closeDrawer",
            "setEnable",
            "getEnable",
            "setVisible",
            "setClickable",
            "setText",
            "setTypeface",
            "getText",
            "setBgColor",
            "setBgResource",
            "setTextColor",
            "setHint",
            "setHintTextColor",
            "setImage",
            "setColorFilter",
            "requestFocus",
            "setRotate",
            "getRotate",
            "setAlpha",
            "getAlpha",
            "setTranslationX",
            "getTranslationX",
            "setTranslationY",
            "getTranslationY",
            "setScaleX",
            "getScaleX",
            "setScaleY",
            "getScaleY",
            "getLocationX",
            "getLocationY",
            "setChecked",
            "getChecked",
            "setThumbResource",
            "setTrackResource",
            "listSetData",
            "listSetCustomViewData",
            "listRefresh",
            "listSetItemChecked",
            "listGetCheckedPosition",
            "listGetCheckedPositions",
            "listGetCheckedCount",
            "listSmoothScrollTo",
            "spnSetData",
            "spnRefresh",
            "spnSetSelection",
            "spnGetSelection",
            "webViewLoadUrl",
            "webViewGetUrl",
            "webViewSetCacheMode",
            "webViewCanGoBack",
            "webViewCanGoForward",
            "webViewGoBack",
            "webViewGoForward",
            "webViewClearCache",
            "webViewClearHistory",
            "webViewStopLoading",
            "webViewZoomIn",
            "webViewZoomOut",
            "calendarViewGetDate",
            "calendarViewSetDate",
            "calendarViewSetMinDate",
            "calnedarViewSetMaxDate",
            "adViewLoadAd",
            "mapViewSetMapType",
            "mapViewMoveCamera",
            "mapViewZoomTo",
            "mapViewZoomIn",
            "mapViewZoomOut",
            "mapViewAddMarker",
            "mapViewSetMarkerInfo",
            "mapViewSetMarkerPosition",
            "mapViewSetMarkerColor",
            "mapViewSetMarkerIcon",
            "mapViewSetMarkerVisible",
            "intentSetAction",
            "intentSetData",
            "intentSetScreen",
            "intentPutExtra",
            "intentSetFlags",
            "startActivity",
            "intentGetString",
            "finishActivity",
            "fileGetData",
            "fileSetData",
            "fileRemoveData",
            "calendarGetNow",
            "calendarAdd",
            "calendarSet",
            "calendarFormat",
            "calendarDiff",
            "calendarGetTime",
            "calendarSetTime",
            "vibratorAction",
            "timerAfter",
            "timerEvery",
            "timerCancel",
            "dialogSetTitle",
            "dialogSetMessage",
            "dialogOkButton",
            "dialogCancelButton",
            "dialogNeutralButton",
            "dialogShow",
            "dialogDismiss",
            "mediaplayerCreate",
            "mediaplayerStart",
            "mediaplayerPause",
            "mediaplayerSeek",
            "mediaplayerGetCurrent",
            "mediaplayerGetDuration",
            "mediaplayerIsPlaying",
            "mediaplayerSetLooping",
            "mediaplayerIsLooping",
            "mediaplayerReset",
            "mediaplayerRelease",
            "soundpoolCreate",
            "soundpoolLoad",
            "soundpoolStreamPlay",
            "soundpoolStreamStop",
            "objectanimatorSetTarget",
            "objectanimatorSetProperty",
            "objectanimatorSetValue",
            "objectanimatorSetFromTo",
            "objectanimatorSetDuration",
            "objectanimatorSetRepeatMode",
            "objectanimatorSetRepeatCount",
            "objectanimatorSetInterpolator",
            "objectanimatorStart",
            "objectanimatorCancel",
            "objectanimatorIsRunning",
            "firebaseAdd",
            "firebasePush",
            "firebaseGetPushKey",
            "firebaseDelete",
            "firebaseGetChildren",
            "firebaseauthCreateUser",
            "firebaseauthSignInUser",
            "firebaseauthSignInAnonymously",
            "firebaseauthIsLoggedIn",
            "firebaseauthGetCurrentUser",
            "firebaseauthGetUid",
            "firebaseauthResetPassword",
            "firebaseauthSignOutUser",
            "firebaseStartListen",
            "firebaseStopListen",
            "gyroscopeStartListen",
            "gyroscopeStopListen",
            "interstitialadCreate",
            "interstitialadLoadAd",
            "interstitialadShow",
            "firebasestorageUploadFile",
            "firebasestorageDownloadFile",
            "firebasestorageDelete",
            "camerastarttakepicture",
            "filepickerstartpickfiles",
            "requestnetworkSetParams",
            "requestnetworkSetHeaders",
            "requestnetworkStartRequestNetwork",
            "fileutildelete",
            "fileutilcopy",
            "fileutilwrite",
            "fileutilread",
            "fileutilmove",
            "fileutilisexist",
            "fileutilmakedir",
            "fileutillistdir",
            "fileutilisdir",
            "fileutilisfile",
            "fileutillength",
            "fileutilStartsWith",
            "fileutilEndsWith",
            "fileutilGetLastSegmentPath",
            "doToast",
            "copyToClipboard",
            "setTitle",
            "seekBarGetMax",
            "seekBarGetProgress",
            "seekBarSetMax",
            "seekBarSetProgress",
            "getExternalStorageDir",
            "getPackageDataDir",
            "getPublicDir",
            "resizeBitmapFileRetainRatio",
            "resizeBitmapFileToSquare",
            "resizeBitmapFileToCircle",
            "resizeBitmapFileWithRoundedBorder",
            "cropBitmapFileFromCenter",
            "rotateBitmapFile",
            "scaleBitmapFile",
            "skewBitmapFile",
            "setBitmapFileColorFilter",
            "setBitmapFileBrightness",
            "setBitmapFileContrast",
            "setImageFilePath",
            "setImageUrl",
            "getJpegRotate",
            "progressBarSetIndeterminate",
            "textToSpeechSetPitch",
            "textToSpeechSetSpeechRate",
            "textToSpeechSpeak",
            "textToSpeechIsSpeaking",
            "textToSpeechStop",
            "textToSpeechShutdown",
            "speechToTextStartListening",
            "speechToTextStopListening",
            "speechToTextShutdown",
            "bluetoothConnectReadyConnection",
            "bluetoothConnectReadyConnectionToUuid",
            "bluetoothConnectStartConnection",
            "bluetoothConnectStartConnectionToUuid",
            "bluetoothConnectStopConnection",
            "bluetoothConnectSendData",
            "bluetoothConnectIsBluetoothEnabled",
            "bluetoothConnectIsBluetoothActivated",
            "bluetoothConnectActivateBluetooth",
            "bluetoothConnectGetPairedDevices",
            "bluetoothConnectGetRandomUuid",
            "locationManagerRequestLocationUpdates",
            "locationManagerRemoveUpdates",
        ).forEach { registerBlockSpec(context, it) }
    }
}
