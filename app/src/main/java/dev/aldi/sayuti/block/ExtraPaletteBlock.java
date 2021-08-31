package dev.aldi.sayuti.block;

import android.app.Activity;
import android.util.Pair;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.LogicEditorActivity;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Ss;
import a.a.a.jC;
import a.a.a.kq;
import a.a.a.xq;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.hey.studios.editor.view.IdGenerator;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.hilal.saif.blocks.BlocksHandler;
import mod.w3wide.blocks.ExtraBlocks;
import mod.w3wide.control.logic.LogicClickListener;

public class ExtraPaletteBlock extends Activity {

    private final String eventName;
    private final String javaName;
    private final String xmlName;
    private final String sc_id;

    private final LogicClickListener clickListener;
    private final ExtraBlocks extraBlocks;
    private final FileResConfig frc;
    private final HashMap<String, Object> mapSave = new HashMap<>();
    private final ProjectFileBean projectFile;
    public LogicEditorActivity logicEditor;

    public ExtraPaletteBlock(LogicEditorActivity logicEditorActivity) {
        logicEditor = logicEditorActivity;
        eventName = logicEditorActivity.D;

        projectFile = logicEditor.M;
        javaName = projectFile.getJavaName();
        xmlName = projectFile.getXmlName();
        sc_id = logicEditor.B;

        frc = new FileResConfig(sc_id);
        extraBlocks = new ExtraBlocks(logicEditor);
        clickListener = new LogicClickListener(logicEditor);
    }

    private boolean isWidgetUsed(String str) {
        if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_ALWAYS_SHOW_BLOCKS)) {
            return true;
        }

        if (mapSave.containsKey(str)) {
            Object strValueInMapSave = mapSave.get(str);
            if (strValueInMapSave instanceof Boolean) {
                return (boolean) strValueInMapSave;
            } else {
                return false;
            }
        }
        if (eventName.equals("onBindCustomView")) {
            String str2 = jC.a(sc_id).c(xmlName, logicEditor.C).customView;
            if (str2 != null && str2.length() > 0) {
                for (ViewBean viewBean : jC.a(sc_id).d(ProjectFileBean.getXmlName(str2))) {
                    if (viewBean.getClassInfo().a(str)) {
                        mapSave.put(str, true);
                        return true;
                    }
                }
            }
        } else if (jC.a(sc_id).y(xmlName, str)) {
            mapSave.put(str, true);
            return true;
        }
        mapSave.put(str, false);
        return false;
    }

    /*
    * ExtraPaletteBlock#f(Ss) moved to mod.w3wide.menu.ExtraMenuBean#defineMenuSelector(Ss)
    * for better block menu selections and to add new stuff easily.
    */

    public boolean e(String str, String str2) {
        switch (str) {
            case "circleimageview":
                return jC.a(sc_id).g(xmlName, 43, str2);

            case "onesignal":
                return jC.a(sc_id).d(javaName, 32, str2);

            case "asynctask":
                return jC.a(sc_id).d(javaName, 36, str2);

            case "otpview":
                return jC.a(sc_id).g(xmlName, 46, str2);

            case "lottie":
                return jC.a(sc_id).g(xmlName, 44, str2);

            case "phoneauth":
                return jC.a(sc_id).d(javaName, 28, str2);

            case "fbadbanner":
                return jC.a(sc_id).d(javaName, 33, str2);

            case "codeview":
                return jC.a(sc_id).g(xmlName, 47, str2);

            case "recyclerview":
                return jC.a(sc_id).g(xmlName, 48, str2);

            case "googlelogin":
                return jC.a(sc_id).d(javaName, 31, str2);

            case "dynamiclink":
                return jC.a(sc_id).d(javaName, 29, str2);

            case "youtubeview":
                return jC.a(sc_id).g(xmlName, 45, str2);

            case "signinbutton":
                return jC.a(sc_id).g(xmlName, 42, str2);

            case "cardview":
                return jC.a(sc_id).g(xmlName, 36, str2);

            case "radiogroup":
                return jC.a(sc_id).g(xmlName, 40, str2);

            case "fbadinterstitial":
                return jC.a(sc_id).d(javaName, 34, str2);

            case "textinputlayout":
                return jC.a(sc_id).g(xmlName, 38, str2);

            case "collapsingtoolbar":
                return jC.a(sc_id).g(xmlName, 37, str2);

            case "cloudmessage":
                return jC.a(sc_id).d(javaName, 30, str2);

            case "datepicker":
                return jC.a(sc_id).g(xmlName, 27, str2);

            case "customVar":
                return jC.a(sc_id).f(xmlName, 5, str2);

            case "timepicker":
                return jC.a(sc_id).g(xmlName, 28, str2);

            case "swiperefreshlayout":
                return jC.a(sc_id).g(xmlName, 39, str2);
        }
        return true;
    }

    public final void moreBlocks() {
        ReturnMoreblockManager.listMoreblocks(jC.a(sc_id).i(javaName).iterator(), logicEditor);
    }

    private void variables() {
        ArrayList<String> varBools = jC.a(sc_id).e(javaName, 0);
        for (int i = 0; i < varBools.size(); i++) {
        	if (i == 0) logicEditor.a("Boolean", 0xff555555);
        	logicEditor.a(varBools.get(i), "b", "getVar").setTag(varBools.get(i));
        }

        ArrayList<String> varNums = jC.a(sc_id).e(javaName, 1);
        for (int i = 0; i < varNums.size(); i++) {
        	if (i == 0) logicEditor.a("Number", 0xff555555);
        	logicEditor.a(varNums.get(i), "d", "getVar").setTag(varNums.get(i));
        }

        ArrayList<String> varStrs = jC.a(sc_id).e(javaName, 2);
        for (int i = 0; i < varStrs.size(); i++) {
        	if (i == 0) logicEditor.a("String", 0xff555555);
        	logicEditor.a(varStrs.get(i), "s", "getVar").setTag(varStrs.get(i));
        }

        ArrayList<String> varMaps = jC.a(sc_id).e(javaName, 3);
        for (int i = 0; i < varMaps.size(); i++) {
        	if (i == 0) logicEditor.a("Map", 0xff555555);
        	logicEditor.a(varMaps.get(i), "a", "getVar").setTag(varMaps.get(i));
        }

        ArrayList<String> varCustoms = jC.a(sc_id).e(javaName, 5);
        for (int i = 0; i < varCustoms.size(); i++) {
	    	if (i == 0) logicEditor.a("Custom Variable", 0xff555555);
	        String[] split = varCustoms.get(i).split(" ");
            if (split.length > 1) {
                logicEditor.a(split[1], "v", split[0], "getVar").setTag(varCustoms.get(i));
            } else {
                SketchwareUtil.toastError("Received invalid data, content: {" + i + ":\"" + varCustoms.get(i) + "\"}");
            }
        }
        BlocksHandler.primaryBlocksA(
            logicEditor,
            extraBlocks.isVariableUsed(0),
            extraBlocks.isVariableUsed(1),
            extraBlocks.isVariableUsed(2),
            extraBlocks.isVariableUsed(3)
        );
        blockCustomViews();
        blockDrawer();
        blockEvents();
        extraBlocks.eventBlocks();
        blockComponents();
    }

    public final void blockComponents() {
        ArrayList<ComponentBean> components = jC.a(sc_id).e(javaName);
        for (int i = 0, componentsSize = components.size(); i < componentsSize; i++) {
            ComponentBean component = components.get(i);

            if (i == 0) {
                logicEditor.a("Components", 0xff555555);
            }

            if (component.type != 27) {
                logicEditor.a(component.componentId, "p", ComponentBean.getComponentTypeName(component.type), "getVar").setTag(component.componentId);
            }
        }
    }

    public final void blockCustomViews() {
        if (eventName.equals("onBindCustomView")) {
            String viewBeanCustomView = jC.a(sc_id).c(xmlName, logicEditor.C).customView;
            if (viewBeanCustomView != null && viewBeanCustomView.length() > 0) {
                ArrayList<ViewBean> customViews = jC.a(sc_id).d(ProjectFileBean.getXmlName(viewBeanCustomView));
                for (int i = 0, customViewsSize = customViews.size(); i < customViewsSize; i++) {
                    ViewBean customView = customViews.get(i);

                    if (i == 0) {
                        logicEditor.a("Custom Views", 0xff555555);
                    }

                    if (!customView.convert.equals("include")) {
                        String typeName = customView.convert.isEmpty() ? ViewBean.getViewTypeName(customView.type) : IdGenerator.getLastPath(customView.convert);
                        logicEditor.a(customView.id, "v", typeName, "getVar").setTag(customView.id);
                    }
                }
            }
            logicEditor.a(" ", "notifyDataSetChanged");
            logicEditor.a("c", "viewOnClick");
            logicEditor.a("c", "viewOnLongClick");
            logicEditor.a("c", "checkboxOnChecked");
            logicEditor.a("b", "checkboxIsChecked");
            return;
        }
        ArrayList<ViewBean> views = jC.a(sc_id).d(xmlName);
        for (int i = 0, viewsSize = views.size(); i < viewsSize; i++) {
            ViewBean view = views.get(i);

            if (i == 0) {
                logicEditor.a("Views", 0xff555555);
            }

            if (!view.convert.equals("include")) {
                String typeName = view.convert.isEmpty() ? ViewBean.getViewTypeName(view.type) : IdGenerator.getLastPath(view.convert);
                logicEditor.a(view.id, "v", typeName, "getVar").setTag(view.id);
            }
        }
    }

    public final void blockDrawer() {
        if (projectFile.hasActivityOption(4)) {
            ArrayList<ViewBean> drawerViews = jC.a(sc_id).d(projectFile.getDrawerXmlName());
            if (drawerViews != null) {
                for (int i = 0, drawerViewsSize = drawerViews.size(); i < drawerViewsSize; i++) {
                    ViewBean drawerView = drawerViews.get(i);

                    if (i == 0) {
                        logicEditor.a("Drawer Views", 0xff555555);
                    }

                    if (!drawerView.convert.equals("include")) {
                        String id = "_drawer_" + drawerView.id;
                        String typeName = drawerView.convert.isEmpty() ? ViewBean.getViewTypeName(drawerView.type) : IdGenerator.getLastPath(drawerView.convert);
                        logicEditor.a(id, "v", typeName, "getVar").setTag(id);
                    }
                }
            }
        }
    }

    public final void blockEvents() {
        if (eventName.equals("onTabAdded") || eventName.equals("onFragmentAdded") || eventName.equals("onTabLayoutNewTabAdded")) {
            logicEditor.a("Fragment & TabLayout", 0xff555555);
            if (eventName.equals("onTabAdded") || eventName.equals("onTabLayoutNewTabAdded")) {
                logicEditor.a("f", "returnTitle");
            }
            if (eventName.equals("onFragmentAdded")) {
                logicEditor.a("f", "returnFragment");
            }
        }
        if (eventName.equals("onScrollChanged")) {
            logicEditor.a("ListView", 0xff555555);
            logicEditor.a("d", "listscrollparam");
            logicEditor.a("d", "getLastVisiblePosition");
        }
        if (eventName.equals("onScrollChanged2")) {
            logicEditor.a("RecyclerView", 0xff555555);
            logicEditor.a("d", "recyclerscrollparam");
        }
        if (eventName.equals("onPageChanged")) {
            logicEditor.a("ViewPager", 0xff555555);
            logicEditor.a("d", "pagerscrollparam");
        }
        if (eventName.equals("onCreateOptionsMenu")) {
            logicEditor.a("Menu", 0xff555555);
            logicEditor.a(" ", "menuInflater");
            logicEditor.a(" ", "menuAddItem");
            logicEditor.a(" ", "menuAddMenuItem");
            logicEditor.a("c", "menuAddSubmenu");
            logicEditor.a(" ", "submenuAddItem");
        }
    }

    public final void list() {
        for (Pair<Integer, String> intStrPair : jC.a(sc_id).j(javaName)) {
            switch (intStrPair.first) {
                case 1:
                case 2:
                case 3:
                    logicEditor.a(intStrPair.second, "l", kq.a(intStrPair.first), "getVar").setTag(intStrPair.second);

                default:
                    String[] split = intStrPair.second.split(" ");
                    if (split.length > 1) {
                        logicEditor.a(split[1], "l", "List", "getVar").setTag(intStrPair.second);
                    } else {
                        SketchwareUtil.toastError("Received invalid data, content: {" + intStrPair.first + ":\"" + intStrPair.second + "\"}");
                    }
            }
        }
        BlocksHandler.primaryBlocksB(
            logicEditor,
            extraBlocks.isListUsed(1),
            extraBlocks.isListUsed(2),
            extraBlocks.isListUsed(3),
            eventName
        );
    }

    public void setBlock(int i, int i2) {
        logicEditor.m.a();
        if (eventName.equals("Import")) {
            logicEditor.a("Enter the path without import & semicolon", 0xff555555);
            logicEditor.a(" ", "customImport");
            logicEditor.a(" ", "customImport2");
            return;
        }

        switch (i) {
            case 0:
                logicEditor.b("Add variable", "variableAdd");
                logicEditor.b("Add custom variable", "variableAddNew", clickListener);
                logicEditor.b("Remove variable", "variableRemove");
                variables();
                return;

            case 1:
                logicEditor.b("Add list", "listAdd");
                logicEditor.b("Add custom List", "listAddCustom", clickListener);
                logicEditor.b("Remove list", "listRemove");
                list();
                return;

            case 2:
                BlocksHandler.primaryBlocksC(logicEditor);
                return;

            case 3:
                BlocksHandler.primaryBlocksD(logicEditor);
                return;

            case 4:
                logicEditor.a("d", "mathGetDip");
                logicEditor.a("d", "mathGetDisplayWidth");
                logicEditor.a("d", "mathGetDisplayHeight");
                logicEditor.a("d", "mathPi");
                logicEditor.a("d", "mathE");
                logicEditor.a("d", "mathPow");
                logicEditor.a("d", "mathMin");
                logicEditor.a("d", "mathMax");
                logicEditor.a("d", "mathSqrt");
                logicEditor.a("d", "mathAbs");
                logicEditor.a("d", "mathRound");
                logicEditor.a("d", "mathCeil");
                logicEditor.a("d", "mathFloor");
                logicEditor.a("d", "mathSin");
                logicEditor.a("d", "mathCos");
                logicEditor.a("d", "mathTan");
                logicEditor.a("d", "mathAsin");
                logicEditor.a("d", "mathAcos");
                logicEditor.a("d", "mathAtan");
                logicEditor.a("d", "mathExp");
                logicEditor.a("d", "mathLog");
                logicEditor.a("d", "mathLog10");
                logicEditor.a("d", "mathToRadian");
                logicEditor.a("d", "mathToDegree");
                return;

            case 5:
                extraBlocks.fileBlocks();
                logicEditor.a("FileUtil Blocks", 0xff555555);
                if (frc.getAssetsFile().size() > 0) {
                    logicEditor.a(" ", "getAssetFile");
                    logicEditor.a("s", "copyAssetFile");
                }
                logicEditor.a("s", "fileutilread");
                logicEditor.a(" ", "fileutilwrite");
                logicEditor.a(" ", "fileutilcopy");
                logicEditor.a(" ", "fileutilcopydir");
                logicEditor.a(" ", "fileutilmove");
                logicEditor.a(" ", "fileutildelete");
                logicEditor.a(" ", "renameFile");
                logicEditor.a("b", "fileutilisexist");
                logicEditor.a(" ", "fileutilmakedir");
                logicEditor.a(" ", "fileutillistdir");
                logicEditor.a("b", "fileutilisdir");
                logicEditor.a("b", "fileutilisfile");
                logicEditor.a("d", "fileutillength");
                logicEditor.a("b", "fileutilStartsWith");
                logicEditor.a("b", "fileutilEndsWith");
                logicEditor.a("s", "fileutilGetLastSegmentPath");
                logicEditor.a("s", "getExternalStorageDir");
                logicEditor.a("s", "getPackageDataDir");
                logicEditor.a("s", "getPublicDir");
                logicEditor.a(" ", "resizeBitmapFileRetainRatio");
                logicEditor.a(" ", "resizeBitmapFileToSquare");
                logicEditor.a(" ", "resizeBitmapFileToCircle");
                logicEditor.a(" ", "resizeBitmapFileWithRoundedBorder");
                logicEditor.a(" ", "cropBitmapFileFromCenter");
                logicEditor.a(" ", "rotateBitmapFile");
                logicEditor.a(" ", "scaleBitmapFile");
                logicEditor.a(" ", "skewBitmapFile");
                logicEditor.a(" ", "setBitmapFileColorFilter");
                logicEditor.a(" ", "setBitmapFileBrightness");
                logicEditor.a(" ", "setBitmapFileContrast");
                logicEditor.a("d", "getJpegRotate");
                return;

            case 6:
                logicEditor.a(" ", "setEnable");
                logicEditor.a("b", "getEnable");
                logicEditor.a(" ", "setVisible");
                logicEditor.a("b", "checkViewVisibility");
                logicEditor.a(" ", "setElevation");
                logicEditor.a(" ", "setRotate");
                logicEditor.a("d", "getRotate");
                logicEditor.a(" ", "setAlpha");
                logicEditor.a("d", "getAlpha");
                logicEditor.a(" ", "setTranslationX");
                logicEditor.a("d", "getTranslationX");
                logicEditor.a(" ", "setTranslationY");
                logicEditor.a("d", "getTranslationY");
                logicEditor.a(" ", "setScaleX");
                logicEditor.a("d", "getScaleX");
                logicEditor.a(" ", "setScaleY");
                logicEditor.a("d", "getScaleY");
                logicEditor.a("d", "getLocationX");
                logicEditor.a("d", "getLocationY");
                logicEditor.a("d", "getHeight");
                logicEditor.a("d", "getWidth");
                logicEditor.a(" ", "requestFocus");
                logicEditor.a(" ", "removeView");
                logicEditor.a(" ", "removeViews");
                logicEditor.a(" ", "addView");
                logicEditor.a("v", "viewGetChildAt");
                logicEditor.a(" ", "addViews");
                logicEditor.a(" ", "setGravity");
                logicEditor.a(" ", "setColorFilterView");
                logicEditor.a(" ", "setBgColor");
                logicEditor.a(" ", "setBgResource");
                logicEditor.a(" ", "setBgDrawable");
                logicEditor.a(" ", "setStrokeView");
                logicEditor.a(" ", "setCornerRadiusView");
                logicEditor.a(" ", "setGradientBackground");
                logicEditor.a(" ", "setRadiusAndStrokeView");
            {
                boolean editTextUsed = isWidgetUsed("EditText")
                        || extraBlocks.isCustomVarUsed("EditText");
                boolean textViewUsed = isWidgetUsed("TextView")
                        || extraBlocks.isCustomVarUsed("TextView") || editTextUsed;
                boolean compoundButtonUsed = isWidgetUsed("CompoundButton")
                        || extraBlocks.isCustomVarUsed("CompoundButton")
                        || extraBlocks.isCustomVarUsed("CheckBox")
                        || extraBlocks.isCustomVarUsed("RadioButton")
                        || extraBlocks.isCustomVarUsed("Switch")
                        || extraBlocks.isCustomVarUsed("ToggleButton");
                boolean autoCompleteTextViewUsed = isWidgetUsed("AutoCompleteTextView")
                        || extraBlocks.isCustomVarUsed("AutoCompleteTextView");
                boolean multiAutoCompleteTextViewUsed = isWidgetUsed("MultiAutoCompleteTextView")
                        || extraBlocks.isCustomVarUsed("MultiAutoCompleteTextView");
                boolean imageViewUsed = isWidgetUsed("ImageView") || isWidgetUsed("CircleImageView")
                        || extraBlocks.isCustomVarUsed("ImageView");
                boolean ratingBarUsed = isWidgetUsed("RatingBar")
                        || extraBlocks.isCustomVarUsed("RatingBar");
                boolean seekBarUsed = isWidgetUsed("SeekBar")
                        || extraBlocks.isCustomVarUsed("SeekBar");
                boolean progressBarUsed = isWidgetUsed("ProgressBar")
                        || extraBlocks.isCustomVarUsed("ProgressBar");
                boolean videoViewUsed = isWidgetUsed("VideoView")
                        || extraBlocks.isCustomVarUsed("VideoView");
                boolean webViewUsed = isWidgetUsed("WebView")
                        || extraBlocks.isCustomVarUsed("WebView");

                if (textViewUsed || compoundButtonUsed || autoCompleteTextViewUsed
                        || multiAutoCompleteTextViewUsed || imageViewUsed || ratingBarUsed
                        || seekBarUsed || progressBarUsed || videoViewUsed || webViewUsed) {
                    logicEditor.a("Widgets", 0xff555555);
                }

                if (textViewUsed) {
                    logicEditor.a(" ", "setText");
                    logicEditor.a("s", "getText");
                    logicEditor.a(" ", "setTypeface");
                    logicEditor.a(" ", "setTextColor");
                    logicEditor.a(" ", "setTextSize");
                }

                if (editTextUsed) {
                    logicEditor.a(" ", "setHint");
                    logicEditor.a(" ", "setHintTextColor");
                    logicEditor.a(" ", "EditTextdiableSuggestion");
                    logicEditor.a(" ", "EditTextLines");
                    logicEditor.a(" ", "EditTextSingleLine");
                    logicEditor.a(" ", "EditTextShowError");
                    logicEditor.a(" ", "EditTextSelectAll");
                    logicEditor.a(" ", "EditTextSetSelection");
                    logicEditor.a(" ", "EditTextSetMaxLines");
                    logicEditor.a("d", "EdittextGetselectionStart");
                    logicEditor.a("d", "EdittextGetselectionEnd");
                }

                if (compoundButtonUsed) {
                    logicEditor.a(" ", "setChecked");
                    logicEditor.a("b", "getChecked");
                }

                if (autoCompleteTextViewUsed) {
                    logicEditor.a(" ", "autoComSetData");
                }

                if (multiAutoCompleteTextViewUsed) {
                    logicEditor.a(" ", "multiAutoComSetData");
                    logicEditor.a(" ", "setThreshold");
                    logicEditor.a(" ", "setTokenizer");
                }

                if (imageViewUsed) {
                    logicEditor.a(" ", "setImage");
                    logicEditor.a(" ", "setImageCustomRes");
                    logicEditor.a(" ", "setImageIdentifier");
                    logicEditor.a(" ", "setImageFilePath");
                    logicEditor.a(" ", "setImageUrl");
                    logicEditor.a(" ", "setColorFilter");
                }

                if (ratingBarUsed) {
                    logicEditor.a("d", "getRating");
                    logicEditor.a(" ", "setRating");
                    logicEditor.a(" ", "setNumStars");
                    logicEditor.a(" ", "setStepSize");
                }

                if (seekBarUsed) {
                    logicEditor.a(" ", "seekBarSetProgress");
                    logicEditor.a("d", "seekBarGetProgress");
                    logicEditor.a(" ", "seekBarSetMax");
                    logicEditor.a("d", "seekBarGetMax");
                }

                if (progressBarUsed) {
                    logicEditor.a(" ", "progressBarSetIndeterminate");
                }

                if (videoViewUsed) {
                    logicEditor.a(" ", "videoviewSetVideoUri");
                    logicEditor.a(" ", "videoviewStart");
                    logicEditor.a(" ", "videoviewPause");
                    logicEditor.a(" ", "videoviewStop");
                    logicEditor.a("b", "videoviewIsPlaying");
                    logicEditor.a("b", "videoviewCanPause");
                    logicEditor.a("b", "videoviewCanSeekForward");
                    logicEditor.a("b", "videoviewCanSeekBackward");
                    logicEditor.a("d", "videoviewGetCurrentPosition");
                    logicEditor.a("d", "videoviewGetDuration");
                }

                if (webViewUsed) {
                    logicEditor.a(" ", "webViewLoadUrl");
                    logicEditor.a("s", "webViewGetUrl");
                    logicEditor.a("d", "webviewGetProgress");
                    logicEditor.a(" ", "webViewSetCacheMode");
                    logicEditor.a("b", "webViewCanGoBack");
                    logicEditor.a("b", "webViewCanGoForward");
                    logicEditor.a(" ", "webViewGoBack");
                    logicEditor.a(" ", "webViewGoForward");
                    logicEditor.a(" ", "webViewClearCache");
                    logicEditor.a(" ", "webViewClearHistory");
                    logicEditor.a(" ", "webViewStopLoading");
                    logicEditor.a(" ", "webViewZoomIn");
                    logicEditor.a(" ", "webViewZoomOut");
                }
            }
            {
                boolean inOnBindCustomView = eventName.equals("onBindCustomView");
                boolean spinnerUsed = isWidgetUsed("Spinner");
                boolean listViewUsed = isWidgetUsed("ListView");
                boolean recyclerViewUsed = isWidgetUsed("RecyclerView");
                boolean gridViewUsed = isWidgetUsed("GridView") || extraBlocks.isCustomVarUsed("GridView");
                boolean viewPagerUsed = isWidgetUsed("ViewPager");

                if (spinnerUsed || listViewUsed || recyclerViewUsed || gridViewUsed || viewPagerUsed) {
                    logicEditor.a("List", 0xff555555);
                }

                if (spinnerUsed) {
                    logicEditor.a(" ", "spnSetData");
                    logicEditor.a(" ", "spnSetCustomViewData");
                    logicEditor.a(" ", "spnRefresh");
                    logicEditor.a(" ", "spnSetSelection");
                    logicEditor.a("d", "spnGetSelection");
                }

                if (!inOnBindCustomView) {
                    if (listViewUsed) {
                        logicEditor.a(" ", "listSetData");
                        logicEditor.a(" ", "listSetCustomViewData");
                        logicEditor.a(" ", "listRefresh");
                        logicEditor.a(" ", "refreshingList");
                        logicEditor.a(" ", "listSmoothScrollTo");
                        logicEditor.a(" ", "listViewSetSelection");
                        logicEditor.a(" ", "listSetTranscriptMode");
                        logicEditor.a(" ", "listSetStackFromBottom");
                        logicEditor.a(" ", "ListViewAddHeader");
                        logicEditor.a(" ", "listViewRemoveHeader");
                        logicEditor.a(" ", "ListViewAddFooter");
                        logicEditor.a(" ", "listViewRemoveFooter");
                    }

                    if (recyclerViewUsed) {
                        logicEditor.a(" ", "recyclerSetCustomViewData");
                        logicEditor.a(" ", "recyclerSetLayoutManager");
                        logicEditor.a(" ", "recyclerSetLayoutManagerHorizontal");
                        logicEditor.a(" ", "recyclerSetHasFixedSize");
                        logicEditor.a(" ", "recyclerSmoothScrollToPosition");
                        logicEditor.a(" ", "recyclerScrollToPositionWithOffset");
                    }

                    if (gridViewUsed) {
                        logicEditor.a(" ", "gridSetCustomViewData");
                        logicEditor.a(" ", "gridSetNumColumns");
                        logicEditor.a(" ", "gridSetColumnWidth");
                        logicEditor.a(" ", "gridSetVerticalSpacing");
                        logicEditor.a(" ", "gridSetHorizontalSpacing");
                        logicEditor.a(" ", "gridSetStretchMode");
                    }

                    if (viewPagerUsed) {
                        logicEditor.a(" ", "pagerSetCustomViewData");
                        logicEditor.a(" ", "pagerSetFragmentAdapter");
                        logicEditor.a("d", "pagerGetOffscreenPageLimit");
                        logicEditor.a(" ", "pagerSetOffscreenPageLimit");
                        logicEditor.a("d", "pagerGetCurrentItem");
                        logicEditor.a(" ", "pagerSetCurrentItem");
                        logicEditor.a(" ", "ViewPagerNotifyOnDtatChange");
                    }
                } else {
                    logicEditor.a(" ", "setRecyclerViewLayoutParams");
                }
            }
            {
                boolean drawerUsed = projectFile.hasActivityOption(4);
                boolean fabUsed = projectFile.hasActivityOption(8);
                boolean bottomNavigationViewUsed = isWidgetUsed("BottomNavigationView");
                boolean swipeRefreshLayoutUsed = isWidgetUsed("SwipeRefreshLayout");
                boolean cardViewUsed = isWidgetUsed("CardView");
                boolean tabLayoutUsed = isWidgetUsed("TabLayout");
                boolean textInputLayoutUsed = isWidgetUsed("TextInputLayout") || extraBlocks.isCustomVarUsed("TextInputLayout");

                if (drawerUsed || fabUsed || bottomNavigationViewUsed || swipeRefreshLayoutUsed || cardViewUsed || tabLayoutUsed || textInputLayoutUsed) {
                    logicEditor.a("AndroidX components", 0xff555555);
                }

                if (drawerUsed) {
                    logicEditor.a("b", "isDrawerOpen");
                    logicEditor.a(" ", "openDrawer");
                    logicEditor.a(" ", "closeDrawer");
                }

                if (fabUsed) {
                    logicEditor.a(" ", "fabIcon");
                    logicEditor.a(" ", "fabSize");
                    logicEditor.a(" ", "fabVisibility");
                }

                if (bottomNavigationViewUsed) {
                    logicEditor.a(" ", "bottomMenuAddItem");
                }

                if (swipeRefreshLayoutUsed) {
                    logicEditor.a("c", "onSwipeRefreshLayout");
                    logicEditor.a(" ", "setRefreshing");
                }

                if (cardViewUsed) {
                    logicEditor.a(" ", "setCardBackgroundColor");
                    logicEditor.a(" ", "setCardRadius");
                    logicEditor.a(" ", "setCardElevation");
                    logicEditor.a(" ", "setPreventCornerOverlap");
                    logicEditor.a(" ", "setUseCompatPadding");
                }

                if (tabLayoutUsed) {
                    logicEditor.a(" ", "addTab");
                    logicEditor.a(" ", "setupWithViewPager");
                    logicEditor.a(" ", "setInlineLabel");
                    logicEditor.a(" ", "setTabTextColors");
                    logicEditor.a(" ", "setTabRippleColor");
                    logicEditor.a(" ", "setSelectedTabIndicatorColor");
                    logicEditor.a(" ", "setSelectedTabIndicatorHeight");
                }

                if (textInputLayoutUsed) {
                    logicEditor.a(" ", "tilSetBoxBgColor");
                    logicEditor.a(" ", "tilSetBoxStrokeColor");
                    logicEditor.a(" ", "tilSetBoxBgMode");
                    logicEditor.a(" ", "tilSetBoxCornerRadii");
                    logicEditor.a(" ", "tilSetError");
                    logicEditor.a(" ", "tilSetErrorEnabled");
                    logicEditor.a(" ", "tilSetCounterEnabled");
                    logicEditor.a(" ", "tilSetCounterMaxLength");
                    logicEditor.a("d", "tilGetCounterMaxLength");
                }
            }
            {
                boolean waveSideBarUsed = isWidgetUsed("WaveSideBar");
                boolean badgeViewUsed = isWidgetUsed("BadgeView");
                boolean bubbleLayoutUsed = isWidgetUsed("BubbleLayout");
                boolean patternLockViewUsed = isWidgetUsed("PatternLockView");
                boolean codeViewUsed = isWidgetUsed("CodeView");
                boolean lottieAnimationViewUsed = isWidgetUsed("LottieAnimationView");
                boolean otpViewUsed = isWidgetUsed("OTPView");

                if (waveSideBarUsed || badgeViewUsed || bubbleLayoutUsed || patternLockViewUsed || codeViewUsed || lottieAnimationViewUsed) {
                    logicEditor.a("Library", 0xff555555);
                }

                if (otpViewUsed) {
                    logicEditor.a(" ", "otpViewSetFieldCount");
                    logicEditor.a(" ", "otpViewSetOTPText");
                    logicEditor.a("s", "otpViewGetOTPText");
                    logicEditor.a("c", "otpViewSetOTPListener");
                }

                if (waveSideBarUsed) {
                    logicEditor.a(" ", "setCustomLetter");
                }

                if (badgeViewUsed) {
                    logicEditor.a("d", "getBadgeCount");
                    logicEditor.a(" ", "setBadgeNumber");
                    logicEditor.a(" ", "setBadgeString");
                    logicEditor.a(" ", "setBadgeBackground");
                    logicEditor.a(" ", "setBadgeTextColor");
                    logicEditor.a(" ", "setBadgeTextSize");
                }

                if (bubbleLayoutUsed) {
                    logicEditor.a(" ", "setBubbleColor");
                    logicEditor.a(" ", "setBubbleStrokeColor");
                    logicEditor.a(" ", "setBubbleStrokeWidth");
                    logicEditor.a(" ", "setBubbleCornerRadius");
                    logicEditor.a(" ", "setBubbleArrowHeight");
                    logicEditor.a(" ", "setBubbleArrowWidth");
                    logicEditor.a(" ", "setBubbleArrowPosition");
                }

                if (patternLockViewUsed) {
                    logicEditor.a("s", "patternToString");
                    logicEditor.a("s", "patternToMD5");
                    logicEditor.a("s", "patternToSha1");
                    logicEditor.a(" ", "patternSetDotCount");
                    logicEditor.a(" ", "patternSetNormalStateColor");
                    logicEditor.a(" ", "patternSetCorrectStateColor");
                    logicEditor.a(" ", "patternSetWrongStateColor");
                    logicEditor.a(" ", "patternSetViewMode");
                    logicEditor.a(" ", "patternLockClear");
                }

                if (codeViewUsed) {
                    logicEditor.a(" ", "codeviewSetCode");
                    logicEditor.a(" ", "codeviewSetLanguage");
                    logicEditor.a(" ", "codeviewSetTheme");
                    logicEditor.a(" ", "codeviewApply");
                }

                if (lottieAnimationViewUsed) {
                    logicEditor.a(" ", "lottieSetAnimationFromAsset");
                    logicEditor.a(" ", "lottieSetAnimationFromJson");
                    logicEditor.a(" ", "lottieSetAnimationFromUrl");
                    logicEditor.a(" ", "lottieSetRepeatCount");
                    logicEditor.a(" ", "lottieSetSpeed");
                }
            }
            {
                boolean signInButtonUsed = isWidgetUsed("SignInButton");
                boolean youtubePlayerViewUsed = isWidgetUsed("YoutubePlayerView");
                boolean adMobUsed = jC.c(sc_id).b().useYn.equals("Y");
                boolean mapViewUsed = isWidgetUsed("MapView");

                if (signInButtonUsed || youtubePlayerViewUsed || adMobUsed || mapViewUsed) {
                    logicEditor.a("Google", 0xff555555);
                }

                if (signInButtonUsed) {
                    logicEditor.a(" ", "signInButtonSetColorScheme");
                    logicEditor.a(" ", "signInButtonSetSize");
                }

                if (youtubePlayerViewUsed) {
                    logicEditor.a(" ", "YTPVLifecycle");
                    logicEditor.a("c", "YTPVSetListener");
                }

                if (adMobUsed) {
                    logicEditor.a(" ", "adViewLoadAd");
                }

                if (mapViewUsed) {
                    logicEditor.a(" ", "mapViewSetMapType");
                    logicEditor.a(" ", "mapViewMoveCamera");
                    logicEditor.a(" ", "mapViewZoomTo");
                    logicEditor.a(" ", "mapViewZoomIn");
                    logicEditor.a(" ", "mapViewZoomOut");
                    logicEditor.a(" ", "mapViewAddMarker");
                    logicEditor.a(" ", "mapViewSetMarkerInfo");
                    logicEditor.a(" ", "mapViewSetMarkerPosition");
                    logicEditor.a(" ", "mapViewSetMarkerColor");
                    logicEditor.a(" ", "mapViewSetMarkerIcon");
                    logicEditor.a(" ", "mapViewSetMarkerVisible");
                }
            }
            {
                boolean timePickerUsed = isWidgetUsed("TimePicker");
                boolean calendarViewUsed = isWidgetUsed("CalendarView");

                if (timePickerUsed || calendarViewUsed) {
                    logicEditor.a("Date & Time", 0xff555555);
                }

                if (timePickerUsed) {
                    logicEditor.a(" ", "timepickerSetHour");
                    logicEditor.a(" ", "timepickerSetMinute");
                    logicEditor.a(" ", "timepickerSetCurrentHour");
                    logicEditor.a(" ", "timepickerSetCurrentMinute");
                    logicEditor.a(" ", "timepickerSetIs24Hour");
                }

                if (calendarViewUsed) {
                    logicEditor.a(" ", "calendarViewSetDate");
                    logicEditor.a(" ", "calendarViewSetMinDate");
                    logicEditor.a(" ", "calnedarViewSetMaxDate");
                }
            }
            logicEditor.a("Function", 0xff555555);
            logicEditor.a(" ", "performClick");
            logicEditor.a("c", "viewOnClick");
            logicEditor.a("c", "viewOnLongClick");
            logicEditor.a("c", "viewOnTouch");
            logicEditor.a("c", "showSnackbar");
            return;

            case 7:
                logicEditor.b("Add component", "componentAdd");
                logicEditor.a(" ", "changeStatebarColour");
                logicEditor.a(" ", "LightStatusBar");
                logicEditor.a(" ", "showKeyboard");
                logicEditor.a(" ", "hideKeyboard");
                logicEditor.a(" ", "doToast");
                logicEditor.a(" ", "copyToClipboard");
                logicEditor.a(" ", "setTitle");
                if (xq.a(sc_id) || xq.b(sc_id)) {
                    logicEditor.a("b", "intentHasExtra");
                    logicEditor.a("s", "intentGetString");
                    logicEditor.a("f", "finishActivity");
                    logicEditor.a("f", "finishAffinity");
                }
                if (extraBlocks.isComponentUsed(1) || extraBlocks.isCustomVarUsed("Intent")) {
                    logicEditor.a("Intent", 0xff555555);
                    logicEditor.a(" ", "intentSetAction");
                    logicEditor.a(" ", "intentSetData");
                    logicEditor.a(" ", "intentSetType");
                    if (xq.a(sc_id) || xq.b(sc_id)) {
                        logicEditor.a(" ", "intentSetScreen");
                        logicEditor.a(" ", "launchApp");
                        logicEditor.a(" ", "intentPutExtra");
                        logicEditor.a(" ", "intentRemoveExtra");
                    }
                    logicEditor.a(" ", "intentSetFlags");
                    logicEditor.a(" ", "startActivity");
                    logicEditor.a(" ", "startActivityWithChooser");
                }
                if (frc.getBroadcastFile().size() > 0) {
                    logicEditor.a("Broadcast", 0xff555555);
                    logicEditor.a(" ", "sendBroadcast");
                }
                if (frc.getServiceFile().size() > 0) {
                    logicEditor.a("Service", 0xff555555);
                    logicEditor.a(" ", "startService");
                    logicEditor.a(" ", "stopService");
                }
                if (extraBlocks.isComponentUsed(2)) {
                    logicEditor.a("SharedPreferences", 0xff555555);
                    logicEditor.a("b", "fileContainsData");
                    logicEditor.a("s", "fileGetData");
                    logicEditor.a(" ", "fileSetData");
                    logicEditor.a(" ", "fileRemoveData");
                }
                if (extraBlocks.isComponentUsed(24)) {
                    logicEditor.a("DatePickerDialog", 0xff555555);
                    logicEditor.a(" ", "datePickerDialogShow");
                }
                if (extraBlocks.isComponentUsed(25)) {
                    logicEditor.a("TimePickerDialog", 0xff555555);
                    logicEditor.a(" ", "timePickerDialogShow");
                }
                if (extraBlocks.isComponentUsed(3)) {
                    logicEditor.a("Calendar", 0xff555555);
                    logicEditor.a(" ", "calendarGetNow");
                    logicEditor.a(" ", "calendarAdd");
                    logicEditor.a(" ", "calendarSet");
                    logicEditor.a("s", "calendarFormat");
                    logicEditor.a("d", "calendarDiff");
                    logicEditor.a("d", "calendarGetTime");
                    logicEditor.a(" ", "calendarSetTime");
                }
                if (extraBlocks.isComponentUsed(4)) {
                    logicEditor.a("Vibrator", 0xff555555);
                    logicEditor.a(" ", "vibratorAction");
                }
                if (extraBlocks.isComponentUsed(5) || extraBlocks.isCustomVarUsed("Timer")) {
                    logicEditor.a("Timer", 0xff555555);
                    logicEditor.a("c", "timerAfter");
                    logicEditor.a("c", "timerEvery");
                    logicEditor.a(" ", "timerCancel");
                }
                if (extraBlocks.isComponentUsed(36)) {
                    logicEditor.a("AsyncTask", 0xff555555);
                    logicEditor.a(" ", "AsyncTaskExecute");
                    logicEditor.a(" ", "AsyncTaskPublishProgress");
                }
                if (extraBlocks.isComponentUsed(7) || extraBlocks.isCustomVarUsed("Dialog")) {
                    logicEditor.a("Dialog", 0xff555555);
                    logicEditor.a(" ", "dialogSetTitle");
                    logicEditor.a(" ", "Dialog SetIcon");
                    logicEditor.a(" ", "dialogSetMessage");
                    logicEditor.a("c", "dialogOkButton");
                    logicEditor.a("c", "dialogCancelButton");
                    logicEditor.a("c", "dialogNeutralButton");
                    logicEditor.a(" ", "dialogShow");
                }
                if (extraBlocks.isComponentUsed(8)) {
                    logicEditor.a("MediaPlayer", 0xff555555);
                    logicEditor.a(" ", "mediaplayerCreate");
                    logicEditor.a(" ", "mediaplayerStart");
                    logicEditor.a(" ", "mediaplayerPause");
                    logicEditor.a(" ", "mediaplayerSeek");
                    logicEditor.a("d", "mediaplayerGetCurrent");
                    logicEditor.a("d", "mediaplayerGetDuration");
                    logicEditor.a("b", "mediaplayerIsPlaying");
                    logicEditor.a(" ", "mediaplayerSetLooping");
                    logicEditor.a("b", "mediaplayerIsLooping");
                    logicEditor.a(" ", "mediaplayerReset");
                    logicEditor.a(" ", "mediaplayerRelease");
                }
                if (extraBlocks.isComponentUsed(9)) {
                    logicEditor.a("SoundPool", 0xff555555);
                    logicEditor.a(" ", "soundpoolCreate");
                    logicEditor.a("d", "soundpoolLoad");
                    logicEditor.a("d", "soundpoolStreamPlay");
                    logicEditor.a(" ", "soundpoolStreamStop");
                }
                if (extraBlocks.isComponentUsed(10)) {
                    logicEditor.a("ObjectAnimator", 0xff555555);
                    logicEditor.a(" ", "objectanimatorSetTarget");
                    logicEditor.a(" ", "objectanimatorSetProperty");
                    logicEditor.a(" ", "objectanimatorSetValue");
                    logicEditor.a(" ", "objectanimatorSetFromTo");
                    logicEditor.a(" ", "objectanimatorSetDuration");
                    logicEditor.a(" ", "objectanimatorSetRepeatMode");
                    logicEditor.a(" ", "objectanimatorSetRepeatCount");
                    logicEditor.a(" ", "objectanimatorSetInterpolator");
                    logicEditor.a(" ", "objectanimatorStart");
                    logicEditor.a(" ", "objectanimatorCancel");
                    logicEditor.a("b", "objectanimatorIsRunning");
                }
                if (extraBlocks.isComponentUsed(6)) {
                    logicEditor.a("Firebase", 0xff555555);
                    logicEditor.a(" ", "firebaseAdd");
                    logicEditor.a(" ", "firebasePush");
                    logicEditor.a("s", "firebaseGetPushKey");
                    logicEditor.a(" ", "firebaseDelete");
                    logicEditor.a("c", "firebaseGetChildren");
                    logicEditor.a(" ", "firebaseStartListen");
                    logicEditor.a(" ", "firebaseStopListen");
                }
                if (extraBlocks.isComponentUsed(12)) {
                    logicEditor.a("FirebaseAuth", 0xff555555);
                    logicEditor.a("b", "firebaseauthIsLoggedIn");
                    logicEditor.a("s", "firebaseauthGetCurrentUser");
                    logicEditor.a("s", "firebaseauthGetUid");
                    logicEditor.a(" ", "firebaseauthCreateUser");
                    logicEditor.a(" ", "firebaseauthSignInUser");
                    logicEditor.a(" ", "firebaseauthSignInAnonymously");
                    logicEditor.a(" ", "firebaseauthResetPassword");
                    logicEditor.a(" ", "firebaseauthSignOutUser");
                }
                jC.a(sc_id).f(javaName, 28);
                jC.a(sc_id).f(javaName, 29);
                jC.a(sc_id).f(javaName, 30);
                jC.a(sc_id).f(javaName, 31);
                jC.a(sc_id).f(javaName, 32);
                jC.a(sc_id).f(javaName, 33);
                jC.a(sc_id).f(javaName, 34);
                if (extraBlocks.isComponentUsed(11)) {
                    logicEditor.a("Gyroscope", 0xff555555);
                    logicEditor.a(" ", "gyroscopeStartListen");
                    logicEditor.a(" ", "gyroscopeStopListen");
                }
                if (extraBlocks.isComponentUsed(13)) {
                    logicEditor.a("AdMob", 0xff555555);
                    logicEditor.a(" ", "interstitialadCreate");
                    logicEditor.a(" ", "interstitialadLoadAd");
                    logicEditor.a(" ", "interstitialadShow");
                }
                if (extraBlocks.isComponentUsed(14)) {
                    logicEditor.a("Firebase Storage", 0xff555555);
                    logicEditor.a(" ", "firebasestorageUploadFile");
                    logicEditor.a(" ", "firebasestorageDownloadFile");
                    logicEditor.a(" ", "firebasestorageDelete");
                }
                if (extraBlocks.isComponentUsed(15)) {
                    logicEditor.a("Camera", 0xff555555);
                    logicEditor.a(" ", "camerastarttakepicture");
                }
                if (extraBlocks.isComponentUsed(16)) {
                    logicEditor.a("FilePicker", 0xff555555);
                    logicEditor.a(" ", "filepickerstartpickfiles");
                    logicEditor.a(" ", "imageCrop");
                }
                if (extraBlocks.isComponentUsed(17)) {
                    logicEditor.a("RequestNetwork", 0xff555555);
                    logicEditor.a("b", "isConnected");
                    logicEditor.a(" ", "requestnetworkSetParams");
                    logicEditor.a(" ", "requestnetworkSetHeaders");
                    logicEditor.a(" ", "requestnetworkStartRequestNetwork");
                }
                if (extraBlocks.isComponentUsed(18)) {
                    logicEditor.a("TextToSpeech", 0xff555555);
                    logicEditor.a("b", "textToSpeechIsSpeaking");
                    logicEditor.a(" ", "textToSpeechSetPitch");
                    logicEditor.a(" ", "textToSpeechSetSpeechRate");
                    logicEditor.a(" ", "textToSpeechSpeak");
                    logicEditor.a(" ", "textToSpeechStop");
                    logicEditor.a(" ", "textToSpeechShutdown");
                }
                if (extraBlocks.isComponentUsed(19)) {
                    logicEditor.a("SpeechToText", 0xff555555);
                    logicEditor.a(" ", "speechToTextStartListening");
                    logicEditor.a(" ", "speechToTextStopListening");
                    logicEditor.a(" ", "speechToTextShutdown");
                }
                if (extraBlocks.isComponentUsed(20)) {
                    logicEditor.a("Bluetooth", 0xff555555);
                    logicEditor.a("b", "bluetoothConnectIsBluetoothEnabled");
                    logicEditor.a("b", "bluetoothConnectIsBluetoothActivated");
                    logicEditor.a("s", "bluetoothConnectGetRandomUuid");
                    logicEditor.a(" ", "bluetoothConnectReadyConnection");
                    logicEditor.a(" ", "bluetoothConnectReadyConnectionToUuid");
                    logicEditor.a(" ", "bluetoothConnectStartConnection");
                    logicEditor.a(" ", "bluetoothConnectStartConnectionToUuid");
                    logicEditor.a(" ", "bluetoothConnectStopConnection");
                    logicEditor.a(" ", "bluetoothConnectSendData");
                    logicEditor.a(" ", "bluetoothConnectActivateBluetooth");
                    logicEditor.a(" ", "bluetoothConnectGetPairedDevices");
                }
                if (extraBlocks.isComponentUsed(21)) {
                    logicEditor.a("LocationManager", 0xff555555);
                    logicEditor.a(" ", "locationManagerRequestLocationUpdates");
                    logicEditor.a(" ", "locationManagerRemoveUpdates");
                }
                if (extraBlocks.isComponentUsed(22)) {
                    logicEditor.a("Video Ad", 0xff555555);
                    logicEditor.a(" ", "videoAdCreate");
                    logicEditor.a(" ", "videoAdLoad");
                    logicEditor.a("b", "videoAdIsLoaded");
                    logicEditor.a(" ", "videoAdShow");
                    logicEditor.a(" ", "videoAdResume");
                    logicEditor.a(" ", "videoAdPause");
                    logicEditor.a(" ", "videoAdDestroy");
                }
                if (extraBlocks.isComponentUsed(23) || extraBlocks.isCustomVarUsed("ProgressDialog") || eventName.equals("onPreExecute") || eventName.equals("onProgressUpdate") || eventName.equals("onPostExecute")) {
                    logicEditor.a("ProgressDialog", 0xff555555);
                    logicEditor.a(" ", "progressdialogCreate");
                    logicEditor.a(" ", "progressdialogSetTitle");
                    logicEditor.a(" ", "progressdialogSetMessage");
                    logicEditor.a(" ", "progressdialogSetMax");
                    logicEditor.a(" ", "progressdialogSetProgress");
                    logicEditor.a(" ", "progressdialogSetCancelable");
                    logicEditor.a(" ", "progressdialogSetCanceledOutside");
                    logicEditor.a(" ", "progressdialogSetStyle");
                    logicEditor.a(" ", "progressdialogShow");
                    logicEditor.a(" ", "progressdialogDismiss");
                    return;
                }
                return;

            case 8:
                logicEditor.b("Create", "blockAdd");
                logicEditor.b("Import From Collection", "blockImport");
                logicEditor.b("Explore Shared Collection", "sharedMoreBlock");
                if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_SHOW_BUILT_IN_BLOCKS)) {
                    logicEditor.a(" ", "customToast");
                    logicEditor.a(" ", "customToastWithIcon");
                }
                moreBlocks();
                if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_SHOW_BUILT_IN_BLOCKS)) {
                    logicEditor.a("Command Blocks", 0xff555555);
                    logicEditor.a("c", "CommandBlockJava");
                    logicEditor.a("c", "CommandBlockXML");
                    logicEditor.a("Permission Command Blocks", 0xff555555);
                    logicEditor.a(" ", "addPermission");
                    logicEditor.a(" ", "removePermission");
                    logicEditor.a("Other Command Blocks", 0xff555555);
                    logicEditor.a(" ", "addCustomVariable");
                    logicEditor.a(" ", "addInitializer");
                    return;
                }
                return;

            default:
                ArrayList<HashMap<String, Object>> extraBlockData = ExtraBlockFile.getExtraBlockData();
                for (HashMap<String, Object> map : extraBlockData) {
                    Object palette = map.get("palette");
                    if (palette instanceof String) {
                        String paletteString = (String) palette;

                        if (paletteString.equals(String.valueOf(i))) {
                            Object type = map.get("type");
                            if (type instanceof String) {
                                String typeString = (String) type;

                                if (typeString.equals("h")) {
                                    Object spec = map.get("spec");
                                    if (spec instanceof String) {
                                        String specString = (String) spec;

                                        logicEditor.a(specString, 0xff555555);
                                    }
                                } else {
                                    Object name = map.get("name");
                                    if (name instanceof String) {
                                        String nameString = (String) name;

                                        Object typeName = map.get("typeName");
                                        if (typeName instanceof String) {
                                            String typeNameString = (String) typeName;

                                            logicEditor.a("", typeString, typeNameString, nameString);
                                        } else {
                                            logicEditor.a("", typeString, "", nameString);
                                        }
                                    } else {
                                        SketchwareUtil.toastError("Detected invalid Custom Block! Check the \"name\" key's type/value");
                                    }
                                }
                            } else {
                                SketchwareUtil.toastError("Detected invalid Custom Block! Check the \"type\" key's type/value");
                            }
                        }
                    } else {
                        SketchwareUtil.toastError("Detected invalid Custom Block! Check the \"palette\" key's type/value");
                    }
                }
        }
    }
}
