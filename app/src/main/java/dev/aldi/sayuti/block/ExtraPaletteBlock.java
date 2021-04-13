package dev.aldi.sayuti.block;

import a.a.a.Ss;
import a.a.a.jC;
import a.a.a.kq;
import a.a.a.xq;
import android.app.Activity;
import android.util.Pair;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.LogicEditorActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hilal.saif.blocks.BlocksHandler;

public class ExtraPaletteBlock extends Activity {
    private String eventName;
    private FileResConfig frc;
    private boolean isCustomView;
    public LogicEditorActivity logicEditor;
    private HashMap<String, Object> mapSave = new HashMap<>();
    private ExtraMenuBlock menuBlock;

    public ExtraPaletteBlock(LogicEditorActivity logicEditorActivity) {
        this.logicEditor = logicEditorActivity;
        this.menuBlock = new ExtraMenuBlock(logicEditorActivity);
        this.eventName = logicEditorActivity.D;
        this.frc = new FileResConfig(this.logicEditor.B);
        this.isCustomView = this.logicEditor.D.equals("onBindCustomView");
    }

    private boolean isWidgetUsed(String str) {
        if (this.mapSave.containsKey(str)) {
            return ((Boolean) this.mapSave.get(str)).booleanValue();
        }
        if (this.eventName.equals("onBindCustomView")) {
            String str2 = jC.a(this.logicEditor.B).c(this.logicEditor.M.getXmlName(), this.logicEditor.C).customView;
            if (str2 != null && str2.length() > 0) {
                Iterator<ViewBean> it = jC.a(this.logicEditor.B).d(ProjectFileBean.getXmlName(str2)).iterator();
                while (it.hasNext()) {
                    if (it.next().getClassInfo().a(str)) {
                        this.mapSave.put(str, true);
                        return true;
                    }
                }
            }
        } else if (jC.a(this.logicEditor.B).y(this.logicEditor.M.getXmlName(), str)) {
            this.mapSave.put(str, true);
            return true;
        }
        this.mapSave.put(str, false);
        return false;
    }

    public void f(Ss ss) {
        if (ss.b.equals("m")) {
            if (ss.getMenuName().matches("cardview|collapsingtoolbar|textinputlayout|swiperefreshlayout|radiogroup|lottie|otpview|youtubeview|codeview|recyclerview|datepicker|timepicker")) {
                this.logicEditor.f(ss);
            } else {
                this.logicEditor.g(ss);
            }
        }
    }

    public boolean e(String str, String str2) {
        switch (str.hashCode()) {
            case -1861588048:
                if (str.equals("circleimageview")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 43, str2);
                }
                break;
            case -1787283314:
                if (str.equals("onesignal")) {
                    return jC.a(this.logicEditor.B).d(this.logicEditor.M.getJavaName(), 32, str2);
                }
                break;
            case -1706714623:
                if (str.equals("asynctask")) {
                    return jC.a(this.logicEditor.B).d(this.logicEditor.M.getJavaName(), 36, str2);
                }
                break;
            case -1138271152:
                if (str.equals("otpview")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 46, str2);
                }
                break;
            case -1096937569:
                if (str.equals("lottie")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 44, str2);
                }
                break;
            case -1028606954:
                if (str.equals("phoneauth")) {
                    return jC.a(this.logicEditor.B).d(this.logicEditor.M.getJavaName(), 28, str2);
                }
                break;
            case -897829429:
                if (str.equals("fbadbanner")) {
                    return jC.a(this.logicEditor.B).d(this.logicEditor.M.getJavaName(), 33, str2);
                }
                break;
            case -866964974:
                if (str.equals("codeview")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 47, str2);
                }
                break;
            case -400432284:
                if (str.equals("recyclerview")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 48, str2);
                }
                break;
            case -322859824:
                if (str.equals("googlelogin")) {
                    return jC.a(this.logicEditor.B).d(this.logicEditor.M.getJavaName(), 31, str2);
                }
                break;
            case -257959751:
                if (str.equals("dynamiclink")) {
                    return jC.a(this.logicEditor.B).d(this.logicEditor.M.getJavaName(), 29, str2);
                }
                break;
            case -75883448:
                if (str.equals("youtubeview")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 45, str2);
                }
                break;
            case -7230027:
                if (str.equals("cardview")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 36, str2);
                }
                break;
            case 5318500:
                if (str.equals("radiogroup")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 40, str2);
                }
                break;
            case 710961803:
                if (str.equals("fbadinterstitial")) {
                    return jC.a(this.logicEditor.B).d(this.logicEditor.M.getJavaName(), 34, str2);
                }
                break;
            case 893026343:
                if (str.equals("textinputlayout")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 38, str2);
                }
                break;
            case 1096269585:
                if (str.equals("collapsingtoolbar")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 37, str2);
                }
                break;
            case 1177398322:
                if (str.equals("cloudmessage")) {
                    return jC.a(this.logicEditor.B).d(this.logicEditor.M.getJavaName(), 30, str2);
                }
                break;
            case 1351679420:
                if (str.equals("datepicker")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 27, str2);
                }
                break;
            case 1611547126:
                if (str.equals("customVar")) {
                    return jC.a(this.logicEditor.B).f(this.logicEditor.M.getXmlName(), 5, str2);
                }
                break;
            case 1612926363:
                if (str.equals("timepicker")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 28, str2);
                }
                break;
            case 2107542731:
                if (str.equals("swiperefreshlayout")) {
                    return jC.a(this.logicEditor.B).g(this.logicEditor.M.getXmlName(), 39, str2);
                }
                break;
        }
        return true;
    }

    public final void moreBlocks() {
        ReturnMoreblockManager.listMoreblocks(jC.a(this.logicEditor.B).i(this.logicEditor.M.getJavaName()).iterator(), this.logicEditor);
    }

    public final void var() {
        Iterator<Pair<Integer, String>> it = jC.a(this.logicEditor.B).k(this.logicEditor.M.getJavaName()).iterator();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (it.hasNext()) {
            Pair<Integer, String> next = it.next();
            if (((Integer) next.first).intValue() == 0) {
                this.logicEditor.a((String) next.second, "b", "getVar").setTag(next.second);
                i++;
            } else if (((Integer) next.first).intValue() == 1) {
                this.logicEditor.a((String) next.second, "d", "getVar").setTag(next.second);
                i2++;
            } else if (((Integer) next.first).intValue() == 2) {
                this.logicEditor.a((String) next.second, "s", "getVar").setTag(next.second);
                i3++;
            } else if (((Integer) next.first).intValue() == 3) {
                this.logicEditor.a((String) next.second, "a", kq.b(((Integer) next.first).intValue()), "getVar").setTag(next.second);
                i4++;
            } else if (((Integer) next.first).intValue() == 5) {
                String str = (String) next.second;
                String substring = str.substring(0, str.indexOf(" "));
                this.logicEditor.a(str.substring(str.indexOf(" ") + 1), ExtraBlockClassInfo.getType(substring), substring, "getVar").setTag(next.second);
            }
        }
        BlocksHandler.primaryBlocksA(this.logicEditor, i, i2, i3, i4);
        blockCustomViews();
        blockDrawer();
        blockEvents();
        blockComponents();
    }

    public final void blockComponents() {
        this.logicEditor.a("Components", -11184811);
        Iterator<ComponentBean> it = jC.a(this.logicEditor.B).e(this.logicEditor.M.getJavaName()).iterator();
        while (it.hasNext()) {
            ComponentBean next = it.next();
            if (next.type != 27) {
                this.logicEditor.a(next.componentId, "p", ComponentBean.getComponentTypeName(next.type), "getVar").setTag(next.componentId);
            }
        }
    }

    public final void blockCustomViews() {
        if (this.eventName.equals("onBindCustomView")) {
            String str = jC.a(this.logicEditor.B).c(this.logicEditor.M.getXmlName(), this.logicEditor.C).customView;
            if (str != null && str.length() > 0) {
                this.logicEditor.a("Custom Views", -11184811);
                Iterator<ViewBean> it = jC.a(this.logicEditor.B).d(ProjectFileBean.getXmlName(str)).iterator();
                while (it.hasNext()) {
                    ViewBean next = it.next();
                    this.logicEditor.a(next.id, "v", ViewBean.getViewTypeName(next.type), "getVar").setTag(next.id);
                }
            }
            this.logicEditor.a(" ", "notifyDataSetChanged");
            this.logicEditor.a("c", "viewOnClick");
            this.logicEditor.a("c", "viewOnLongClick");
            this.logicEditor.a("c", "checkboxOnChecked");
            this.logicEditor.a("b", "checkboxIsChecked");
            return;
        }
        this.logicEditor.a("Views", -11184811);
        Iterator<ViewBean> it2 = jC.a(this.logicEditor.B).d(this.logicEditor.M.getXmlName()).iterator();
        while (it2.hasNext()) {
            ViewBean next2 = it2.next();
            this.logicEditor.a(next2.id, "v", ViewBean.getViewTypeName(next2.type), "getVar").setTag(next2.id);
        }
        this.logicEditor.M.hasActivityOption(8);
        this.logicEditor.M.hasActivityOption(4);
    }

    public final void blockDrawer() {
        if (this.logicEditor.M.hasActivityOption(4)) {
            this.logicEditor.a("Drawer Views", -11184811);
            ArrayList<ViewBean> d = jC.a(this.logicEditor.B).d(this.logicEditor.M.getDrawerXmlName());
            if (d != null) {
                Iterator<ViewBean> it = d.iterator();
                while (it.hasNext()) {
                    ViewBean next = it.next();
                    String str = "_drawer_" + next.id;
                    this.logicEditor.a(str, "v", ViewBean.getViewTypeName(next.type), "getVar").setTag(str);
                }
            }
        }
    }

    public final void blockEvents() {
        if (this.eventName.equals("onTabAdded") || this.eventName.equals("onFragmentAdded") || this.eventName.equals("onTabLayoutNewTabAdded")) {
            this.logicEditor.a("Fragment & TabLayout", -11184811);
            if (this.eventName.equals("onTabAdded") || this.eventName.equals("onTabLayoutNewTabAdded")) {
                this.logicEditor.a("f", "returnTitle");
            }
            if (this.eventName.equals("onFragmentAdded")) {
                this.logicEditor.a("f", "returnFragment");
            }
        }
        if (this.eventName.equals("onScrollChanged")) {
            this.logicEditor.a("ListView", -11184811);
            this.logicEditor.a("d", "listscrollparam");
            this.logicEditor.a("d", "getLastVisiblePosition");
        }
        if (this.eventName.equals("onScrollChanged2")) {
            this.logicEditor.a("RecyclerView", -11184811);
            this.logicEditor.a("d", "recyclerscrollparam");
        }
        if (this.eventName.equals("onPageChanged")) {
            this.logicEditor.a("ViewPager", -11184811);
            this.logicEditor.a("d", "pagerscrollparam");
        }
        if (this.eventName.equals("onCreateOptionsMenu")) {
            this.logicEditor.a("Menu", -11184811);
            this.logicEditor.a(" ", "menuInflater");
            this.logicEditor.a(" ", "menuAddItem");
            this.logicEditor.a(" ", "menuAddMenuItem");
            this.logicEditor.a("c", "menuAddSubmenu");
            this.logicEditor.a(" ", "submenuAddItem");
        }
    }

    public final void list() {
        Iterator<Pair<Integer, String>> it = jC.a(this.logicEditor.B).j(this.logicEditor.M.getJavaName()).iterator();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            Pair<Integer, String> next = it.next();
            if (((Integer) next.first).intValue() == 1) {
                i++;
            } else if (((Integer) next.first).intValue() == 2) {
                i2++;
            } else {
                i3++;
            }
            this.logicEditor.a((String) next.second, "l", kq.a(((Integer) next.first).intValue()), "getVar").setTag(next.second);
            i = i;
            i2 = i2;
            i3 = i3;
        }
        BlocksHandler.primaryBlocksB(this.logicEditor, i, i2, i3, this.eventName);
    }

    public void setBlock(int i, int i2) {
        this.logicEditor.m.a();
        if (this.eventName.equals("Import")) {
            this.logicEditor.a("Enter the path without import & semicolon", -11184811);
            this.logicEditor.a(" ", "customImport");
            this.logicEditor.a(" ", "customImport2");
            return;
        }
        switch (i) {
            case 0:
                this.logicEditor.b("Add variable", "variableAdd");
                this.logicEditor.b("Add custom variable", "variableAddNew");
                this.logicEditor.b("Remove variable", "variableRemove");
                var();
                return;
            case 1:
                this.logicEditor.b("Add list", "listAdd");
                this.logicEditor.b("Remove list", "listRemove");
                list();
                return;
            case 2:
                BlocksHandler.primaryBlocksC(this.logicEditor);
                return;
            case 3:
                BlocksHandler.primaryBlocksD(this.logicEditor);
                return;
            case 4:
                this.logicEditor.a("d", "mathGetDip");
                this.logicEditor.a("d", "mathGetDisplayWidth");
                this.logicEditor.a("d", "mathGetDisplayHeight");
                this.logicEditor.a("d", "mathPi");
                this.logicEditor.a("d", "mathE");
                this.logicEditor.a("d", "mathPow");
                this.logicEditor.a("d", "mathMin");
                this.logicEditor.a("d", "mathMax");
                this.logicEditor.a("d", "mathSqrt");
                this.logicEditor.a("d", "mathAbs");
                this.logicEditor.a("d", "mathRound");
                this.logicEditor.a("d", "mathCeil");
                this.logicEditor.a("d", "mathFloor");
                this.logicEditor.a("d", "mathSin");
                this.logicEditor.a("d", "mathCos");
                this.logicEditor.a("d", "mathTan");
                this.logicEditor.a("d", "mathAsin");
                this.logicEditor.a("d", "mathAcos");
                this.logicEditor.a("d", "mathAtan");
                this.logicEditor.a("d", "mathExp");
                this.logicEditor.a("d", "mathLog");
                this.logicEditor.a("d", "mathLog10");
                this.logicEditor.a("d", "mathToRadian");
                this.logicEditor.a("d", "mathToDegree");
                return;
            case 5:
                if (this.frc.getAssetsFile().size() > 0) {
                    this.logicEditor.a(" ", "getAssetFile");
                    this.logicEditor.a("s", "copyAssetFile");
                }
                this.logicEditor.a("s", "fileutilread");
                this.logicEditor.a(" ", "fileutilwrite");
                this.logicEditor.a(" ", "fileutilcopy");
                this.logicEditor.a(" ", "fileutilmove");
                this.logicEditor.a(" ", "fileutildelete");
                this.logicEditor.a(" ", "renameFile");
                this.logicEditor.a("b", "fileutilisexist");
                this.logicEditor.a(" ", "fileutilmakedir");
                this.logicEditor.a(" ", "fileutillistdir");
                this.logicEditor.a("b", "fileutilisdir");
                this.logicEditor.a("b", "fileutilisfile");
                this.logicEditor.a("d", "fileutillength");
                this.logicEditor.a("b", "fileutilStartsWith");
                this.logicEditor.a("b", "fileutilEndsWith");
                this.logicEditor.a("s", "fileutilGetLastSegmentPath");
                this.logicEditor.a("s", "getExternalStorageDir");
                this.logicEditor.a("s", "getPackageDataDir");
                this.logicEditor.a("s", "getPublicDir");
                this.logicEditor.a(" ", "resizeBitmapFileRetainRatio");
                this.logicEditor.a(" ", "resizeBitmapFileToSquare");
                this.logicEditor.a(" ", "resizeBitmapFileToCircle");
                this.logicEditor.a(" ", "resizeBitmapFileWithRoundedBorder");
                this.logicEditor.a(" ", "cropBitmapFileFromCenter");
                this.logicEditor.a(" ", "rotateBitmapFile");
                this.logicEditor.a(" ", "scaleBitmapFile");
                this.logicEditor.a(" ", "skewBitmapFile");
                this.logicEditor.a(" ", "setBitmapFileColorFilter");
                this.logicEditor.a(" ", "setBitmapFileBrightness");
                this.logicEditor.a(" ", "setBitmapFileContrast");
                this.logicEditor.a("d", "getJpegRotate");
                return;
            case 6:
                this.logicEditor.a(" ", "setEnable");
                this.logicEditor.a("b", "getEnable");
                this.logicEditor.a(" ", "setVisible");
                this.logicEditor.a(" ", "setElevation");
                this.logicEditor.a(" ", "setRotate");
                this.logicEditor.a("d", "getRotate");
                this.logicEditor.a(" ", "setAlpha");
                this.logicEditor.a("d", "getAlpha");
                this.logicEditor.a(" ", "setTranslationX");
                this.logicEditor.a("d", "getTranslationX");
                this.logicEditor.a(" ", "setTranslationY");
                this.logicEditor.a("d", "getTranslationY");
                this.logicEditor.a(" ", "setScaleX");
                this.logicEditor.a("d", "getScaleX");
                this.logicEditor.a(" ", "setScaleY");
                this.logicEditor.a("d", "getScaleY");
                this.logicEditor.a("d", "getLocationX");
                this.logicEditor.a("d", "getLocationY");
                this.logicEditor.a("d", "getHeight");
                this.logicEditor.a("d", "getWidth");
                this.logicEditor.a(" ", "requestFocus");
                this.logicEditor.a(" ", "removeView");
                this.logicEditor.a(" ", "removeViews");
                this.logicEditor.a(" ", "addView");
                this.logicEditor.a(" ", "addViews");
                this.logicEditor.a(" ", "setGravity");
                this.logicEditor.a(" ", "setColorFilterView");
                this.logicEditor.a(" ", "setBgColor");
                this.logicEditor.a(" ", "setBgResource");
                this.logicEditor.a(" ", "setBgDrawable");
                this.logicEditor.a(" ", "setStrokeView");
                this.logicEditor.a(" ", "setCornerRadiusView");
                this.logicEditor.a(" ", "setGradientBackground");
                this.logicEditor.a(" ", "setRadiusAndStrokeView");
                this.logicEditor.a("Widgets", -11184811);
                if (isWidgetUsed("TextView") || isWidgetUsed("EditText")) {
                    this.logicEditor.a(" ", "setText");
                    this.logicEditor.a("s", "getText");
                    this.logicEditor.a(" ", "setTypeface");
                    this.logicEditor.a(" ", "setTextColor");
                    this.logicEditor.a(" ", "setTextSize");
                }
                if (isWidgetUsed("EditText")) {
                    this.logicEditor.a(" ", "setHint");
                    this.logicEditor.a(" ", "setHintTextColor");
                    this.logicEditor.a(" ", "EditTextdiableSuggestion");
                    this.logicEditor.a(" ", "EditTextLines");
                    this.logicEditor.a(" ", "EditTextSingleLine");
                    this.logicEditor.a(" ", "EditTextShowError");
                    this.logicEditor.a(" ", "EditTextSelectAll");
                    this.logicEditor.a(" ", "EditTextSetSelection");
                    this.logicEditor.a(" ", "EditTextSetMaxLines");
                    this.logicEditor.a("d", "EdittextGetselectionStart");
                    this.logicEditor.a("d", "EdittextGetselectionEnd");
                }
                if (isWidgetUsed("CompoundButton")) {
                    this.logicEditor.a(" ", "setChecked");
                    this.logicEditor.a("b", "getChecked");
                }
                if (isWidgetUsed("AutoCompleteTextView")) {
                    this.logicEditor.a(" ", "autoComSetData");
                }
                if (isWidgetUsed("MultiAutoCompleteTextView")) {
                    this.logicEditor.a(" ", "multiAutoComSetData");
                    this.logicEditor.a(" ", "setThreshold");
                    this.logicEditor.a(" ", "setTokenizer");
                }
                if (isWidgetUsed("ImageView") || isWidgetUsed("CircleImageView")) {
                    this.logicEditor.a(" ", "setImage");
                    this.logicEditor.a(" ", "setImageCustomRes");
                    this.logicEditor.a(" ", "setImageIdentifier");
                    this.logicEditor.a(" ", "setImageFilePath");
                    this.logicEditor.a(" ", "setImageUrl");
                    this.logicEditor.a(" ", "setColorFilter");
                }
                if (isWidgetUsed("RatingBar")) {
                    this.logicEditor.a("d", "getRating");
                    this.logicEditor.a(" ", "setRating");
                    this.logicEditor.a(" ", "setNumStars");
                    this.logicEditor.a(" ", "setStepSize");
                }
                if (isWidgetUsed("SeekBar")) {
                    this.logicEditor.a(" ", "seekBarSetProgress");
                    this.logicEditor.a("d", "seekBarGetProgress");
                    this.logicEditor.a(" ", "seekBarSetMax");
                    this.logicEditor.a("d", "seekBarGetMax");
                }
                if (isWidgetUsed("ProgressBar")) {
                    this.logicEditor.a(" ", "progressBarSetIndeterminate");
                }
                if (isWidgetUsed("VideoView")) {
                    this.logicEditor.a(" ", "videoviewSetVideoUri");
                    this.logicEditor.a(" ", "videoviewStart");
                    this.logicEditor.a(" ", "videoviewPause");
                    this.logicEditor.a(" ", "videoviewStop");
                    this.logicEditor.a("b", "videoviewIsPlaying");
                    this.logicEditor.a("b", "videoviewCanPause");
                    this.logicEditor.a("b", "videoviewCanSeekForward");
                    this.logicEditor.a("b", "videoviewCanSeekBackward");
                    this.logicEditor.a("d", "videoviewGetCurrentPosition");
                    this.logicEditor.a("d", "videoviewGetDuration");
                }
                if (isWidgetUsed("WebView")) {
                    this.logicEditor.a(" ", "webViewLoadUrl");
                    this.logicEditor.a("s", "webViewGetUrl");
                    this.logicEditor.a(" ", "webViewSetCacheMode");
                    this.logicEditor.a("b", "webViewCanGoBack");
                    this.logicEditor.a("b", "webViewCanGoForward");
                    this.logicEditor.a(" ", "webViewGoBack");
                    this.logicEditor.a(" ", "webViewGoForward");
                    this.logicEditor.a(" ", "webViewClearCache");
                    this.logicEditor.a(" ", "webViewClearHistory");
                    this.logicEditor.a(" ", "webViewStopLoading");
                    this.logicEditor.a(" ", "webViewZoomIn");
                    this.logicEditor.a(" ", "webViewZoomOut");
                }
                this.logicEditor.a("List", -11184811);
                if (isWidgetUsed("Spinner")) {
                    this.logicEditor.a(" ", "spnSetData");
                    this.logicEditor.a(" ", "spnSetCustomViewData");
                    this.logicEditor.a(" ", "spnRefresh");
                    this.logicEditor.a(" ", "spnSetSelection");
                    this.logicEditor.a("d", "spnGetSelection");
                }
                if (!this.eventName.equals("onBindCustomView")) {
                    if (isWidgetUsed("ListView")) {
                        this.logicEditor.a(" ", "listSetData");
                        this.logicEditor.a(" ", "listSetCustomViewData");
                        this.logicEditor.a(" ", "listRefresh");
                        this.logicEditor.a(" ", "refreshingList");
                        this.logicEditor.a(" ", "listSmoothScrollTo");
                        this.logicEditor.a(" ", "listViewSetSelection");
                        this.logicEditor.a(" ", "listSetTranscriptMode");
                        this.logicEditor.a(" ", "listSetStackFromBottom");
                        this.logicEditor.a(" ", "ListViewAddHeader");
                        this.logicEditor.a(" ", "listViewRemoveHeader");
                        this.logicEditor.a(" ", "ListViewAddFooter");
                        this.logicEditor.a(" ", "listViewRemoveFooter");
                    }
                    if (isWidgetUsed("RecyclerView")) {
                        this.logicEditor.a(" ", "recyclerSetCustomViewData");
                        this.logicEditor.a(" ", "recyclerSetLayoutManager");
                        this.logicEditor.a(" ", "recyclerSetLayoutManagerHorizontal");
                        this.logicEditor.a(" ", "recyclerSetHasFixedSize");
                        this.logicEditor.a(" ", "recyclerSmoothScrollToPosition");
                        this.logicEditor.a(" ", "recyclerScrollToPositionWithOffset");
                    }
                    if (isWidgetUsed("GridView")) {
                        this.logicEditor.a(" ", "gridSetCustomViewData");
                        this.logicEditor.a(" ", "gridSetNumColumns");
                        this.logicEditor.a(" ", "gridSetColumnWidth");
                        this.logicEditor.a(" ", "gridSetVerticalSpacing");
                        this.logicEditor.a(" ", "gridSetHorizontalSpacing");
                        this.logicEditor.a(" ", "gridSetStretchMode");
                    }
                    if (isWidgetUsed("ViewPager")) {
                        this.logicEditor.a(" ", "pagerSetCustomViewData");
                        this.logicEditor.a(" ", "pagerSetFragmentAdapter");
                        this.logicEditor.a("d", "pagerGetOffscreenPageLimit");
                        this.logicEditor.a(" ", "pagerSetOffscreenPageLimit");
                        this.logicEditor.a("d", "pagerGetCurrentItem");
                        this.logicEditor.a(" ", "pagerSetCurrentItem");
                        this.logicEditor.a(" ", "ViewPagerNotifyOnDtatChange");
                    }
                } else {
                    this.logicEditor.a(" ", "setRecyclerViewLayoutParams");
                }
                this.logicEditor.a("AndroidX", -11184811);
                if (this.logicEditor.M.hasActivityOption(4)) {
                    this.logicEditor.a("b", "isDrawerOpen");
                    this.logicEditor.a(" ", "openDrawer");
                    this.logicEditor.a(" ", "closeDrawer");
                }
                if (this.logicEditor.M.hasActivityOption(8)) {
                    this.logicEditor.a(" ", "fabIcon");
                    this.logicEditor.a(" ", "fabSize");
                    this.logicEditor.a(" ", "fabVisibility");
                }
                if (isWidgetUsed("BottomNavigationView")) {
                    this.logicEditor.a(" ", "bottomMenuAddItem");
                }
                if (isWidgetUsed("SwipeRefreshLayout")) {
                    this.logicEditor.a("c", "onSwipeRefreshLayout");
                    this.logicEditor.a(" ", "setRefreshing");
                }
                if (isWidgetUsed("CardView")) {
                    this.logicEditor.a(" ", "setCardBackgroundColor");
                    this.logicEditor.a(" ", "setCardRadius");
                    this.logicEditor.a(" ", "setCardElevation");
                    this.logicEditor.a(" ", "setPreventCornerOverlap");
                    this.logicEditor.a(" ", "setUseCompatPadding");
                }
                if (isWidgetUsed("TabLayout")) {
                    this.logicEditor.a(" ", "addTab");
                    this.logicEditor.a(" ", "setupWithViewPager");
                    this.logicEditor.a(" ", "setInlineLabel");
                    this.logicEditor.a(" ", "setTabTextColors");
                    this.logicEditor.a(" ", "setTabRippleColor");
                    this.logicEditor.a(" ", "setSelectedTabIndicatorColor");
                    this.logicEditor.a(" ", "setSelectedTabIndicatorHeight");
                }
                if (isWidgetUsed("TextInputLayout")) {
                    this.logicEditor.a(" ", "tilSetBoxBgColor");
                    this.logicEditor.a(" ", "tilSetBoxStrokeColor");
                    this.logicEditor.a(" ", "tilSetBoxBgMode");
                    this.logicEditor.a(" ", "tilSetBoxCornerRadii");
                    this.logicEditor.a(" ", "tilSetError");
                    this.logicEditor.a(" ", "tilSetErrorEnabled");
                    this.logicEditor.a(" ", "tilSetCounterEnabled");
                    this.logicEditor.a(" ", "tilSetCounterMaxLength");
                    this.logicEditor.a("d", "tilGetCounterMaxLength");
                }
                this.logicEditor.a("Library", -11184811);
                if (isWidgetUsed("WaveSideBar")) {
                    this.logicEditor.a(" ", "setCustomLetter");
                }
                if (isWidgetUsed("BadgeView")) {
                    this.logicEditor.a("d", "getBadgeCount");
                    this.logicEditor.a(" ", "setBadgeNumber");
                    this.logicEditor.a(" ", "setBadgeString");
                    this.logicEditor.a(" ", "setBadgeBackground");
                    this.logicEditor.a(" ", "setBadgeTextColor");
                    this.logicEditor.a(" ", "setBadgeTextSize");
                }
                if (isWidgetUsed("BubbleLayout")) {
                    this.logicEditor.a(" ", "setBubbleColor");
                    this.logicEditor.a(" ", "setBubbleStrokeColor");
                    this.logicEditor.a(" ", "setBubbleStrokeWidth");
                    this.logicEditor.a(" ", "setBubbleCornerRadius");
                    this.logicEditor.a(" ", "setBubbleArrowHeight");
                    this.logicEditor.a(" ", "setBubbleArrowWidth");
                    this.logicEditor.a(" ", "setBubbleArrowPosition");
                }
                if (isWidgetUsed("PatternLockView")) {
                    this.logicEditor.a("s", "patternToString");
                    this.logicEditor.a("s", "patternToMD5");
                    this.logicEditor.a("s", "patternToSha1");
                    this.logicEditor.a(" ", "patternSetDotCount");
                    this.logicEditor.a(" ", "patternSetNormalStateColor");
                    this.logicEditor.a(" ", "patternSetCorrectStateColor");
                    this.logicEditor.a(" ", "patternSetWrongStateColor");
                    this.logicEditor.a(" ", "patternSetViewMode");
                    this.logicEditor.a(" ", "patternLockClear");
                }
                if (isWidgetUsed("CodeView")) {
                    this.logicEditor.a(" ", "codeviewSetCode");
                    this.logicEditor.a(" ", "codeviewSetLanguage");
                    this.logicEditor.a(" ", "codeviewSetTheme");
                    this.logicEditor.a(" ", "codeviewApply");
                }
                if (isWidgetUsed("LottieAnimationView")) {
                    this.logicEditor.a(" ", "lottieSetAnimationFromAsset");
                    this.logicEditor.a(" ", "lottieSetAnimationFromJson");
                    this.logicEditor.a(" ", "lottieSetAnimationFromUrl");
                    this.logicEditor.a(" ", "lottieSetRepeatCount");
                    this.logicEditor.a(" ", "lottieSetSpeed");
                }
                isWidgetUsed("OTPView");
                this.logicEditor.a("Google", -11184811);
                isWidgetUsed("SignInButton");
                if (isWidgetUsed("YoutubePlayerView")) {
                    this.logicEditor.a(" ", "YTPVLifecycle");
                    this.logicEditor.a("c", "YTPVSetListener");
                }
                if (jC.c(this.logicEditor.B).b().useYn.equals("Y")) {
                    this.logicEditor.a(" ", "adViewLoadAd");
                }
                if (isWidgetUsed("MapView")) {
                    this.logicEditor.a(" ", "mapViewSetMapType");
                    this.logicEditor.a(" ", "mapViewMoveCamera");
                    this.logicEditor.a(" ", "mapViewZoomTo");
                    this.logicEditor.a(" ", "mapViewZoomIn");
                    this.logicEditor.a(" ", "mapViewZoomOut");
                    this.logicEditor.a(" ", "mapViewAddMarker");
                    this.logicEditor.a(" ", "mapViewSetMarkerInfo");
                    this.logicEditor.a(" ", "mapViewSetMarkerPosition");
                    this.logicEditor.a(" ", "mapViewSetMarkerColor");
                    this.logicEditor.a(" ", "mapViewSetMarkerIcon");
                    this.logicEditor.a(" ", "mapViewSetMarkerVisible");
                }
                this.logicEditor.a("Date & Time", -11184811);
                if (isWidgetUsed("TimePicker")) {
                    this.logicEditor.a(" ", "timepickerSetHour");
                    this.logicEditor.a(" ", "timepickerSetMinute");
                    this.logicEditor.a(" ", "timepickerSetCurrentHour");
                    this.logicEditor.a(" ", "timepickerSetCurrentMinute");
                    this.logicEditor.a(" ", "timepickerSetIs24Hour");
                }
                if (isWidgetUsed("CalendarView")) {
                    this.logicEditor.a(" ", "calendarViewSetDate");
                    this.logicEditor.a(" ", "calendarViewSetMinDate");
                    this.logicEditor.a(" ", "calnedarViewSetMaxDate");
                }
                this.logicEditor.a("Function", -11184811);
                this.logicEditor.a(" ", "performClick");
                this.logicEditor.a("c", "viewOnClick");
                this.logicEditor.a("c", "viewOnLongClick");
                this.logicEditor.a("c", "viewOnTouch");
                this.logicEditor.a("c", "showSnackbar");
                return;
            case 7:
                this.logicEditor.b("Add component", "componentAdd");
                this.logicEditor.a(" ", "changeStatebarColour");
                this.logicEditor.a(" ", "LightStatusBar");
                this.logicEditor.a(" ", "showKeyboard");
                this.logicEditor.a(" ", "hideKeyboard");
                this.logicEditor.a(" ", "doToast");
                this.logicEditor.a(" ", "copyToClipboard");
                this.logicEditor.a(" ", "setTitle");
                if (xq.a(this.logicEditor.B) || xq.b(this.logicEditor.B)) {
                    this.logicEditor.a("s", "intentGetString");
                    this.logicEditor.a("f", "finishActivity");
                    this.logicEditor.a("f", "finishAffinity");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 1)) {
                    this.logicEditor.a("Intent", -11184811);
                    this.logicEditor.a(" ", "intentSetAction");
                    this.logicEditor.a(" ", "intentSetData");
                    if (xq.a(this.logicEditor.B) || xq.b(this.logicEditor.B)) {
                        this.logicEditor.a(" ", "intentSetScreen");
                        this.logicEditor.a(" ", "launchApp");
                        this.logicEditor.a(" ", "intentPutExtra");
                    }
                    this.logicEditor.a(" ", "intentSetFlags");
                    this.logicEditor.a(" ", "startActivity");
                    this.logicEditor.a(" ", "startActivityWithChooser");
                }
                if (this.frc.getBroadcastFile().size() > 0) {
                    this.logicEditor.a("Broadcast", -11184811);
                    this.logicEditor.a(" ", "sendBroadcast");
                }
                if (this.frc.getServiceFile().size() > 0) {
                    this.logicEditor.a("Service", -11184811);
                    this.logicEditor.a(" ", "startService");
                    this.logicEditor.a(" ", "stopService");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 2)) {
                    this.logicEditor.a("Shared preferences", -11184811);
                    this.logicEditor.a("s", "fileGetData");
                    this.logicEditor.a(" ", "fileSetData");
                    this.logicEditor.a(" ", "fileRemoveData");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 24)) {
                    this.logicEditor.a("Date picker dialog", -11184811);
                    this.logicEditor.a(" ", "datePickerDialogShow");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 25)) {
                    this.logicEditor.a("Time picker dialog", -11184811);
                    this.logicEditor.a(" ", "timePickerDialogShow");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 3)) {
                    this.logicEditor.a("Calendar", -11184811);
                    this.logicEditor.a(" ", "calendarGetNow");
                    this.logicEditor.a(" ", "calendarAdd");
                    this.logicEditor.a(" ", "calendarSet");
                    this.logicEditor.a("s", "calendarFormat");
                    this.logicEditor.a("d", "calendarDiff");
                    this.logicEditor.a("d", "calendarGetTime");
                    this.logicEditor.a(" ", "calendarSetTime");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 4)) {
                    this.logicEditor.a("Vibrator", -11184811);
                    this.logicEditor.a(" ", "vibratorAction");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 5)) {
                    this.logicEditor.a("Timer", -11184811);
                    this.logicEditor.a("c", "timerAfter");
                    this.logicEditor.a("c", "timerEvery");
                    this.logicEditor.a(" ", "timerCancel");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 36)) {
                    this.logicEditor.a("AsyncTask", -11184811);
                    this.logicEditor.a(" ", "AsyncTaskExecute");
                    this.logicEditor.a(" ", "AsyncTaskPublishProgress");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 7)) {
                    this.logicEditor.a("Dialog", -11184811);
                    this.logicEditor.a(" ", "dialogSetTitle");
                    this.logicEditor.a(" ", "Dialog SetIcon");
                    this.logicEditor.a(" ", "dialogSetMessage");
                    this.logicEditor.a("c", "dialogOkButton");
                    this.logicEditor.a("c", "dialogCancelButton");
                    this.logicEditor.a("c", "dialogNeutralButton");
                    this.logicEditor.a(" ", "dialogShow");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 8)) {
                    this.logicEditor.a("Media player", -11184811);
                    this.logicEditor.a(" ", "mediaplayerCreate");
                    this.logicEditor.a(" ", "mediaplayerStart");
                    this.logicEditor.a(" ", "mediaplayerPause");
                    this.logicEditor.a(" ", "mediaplayerSeek");
                    this.logicEditor.a("d", "mediaplayerGetCurrent");
                    this.logicEditor.a("d", "mediaplayerGetDuration");
                    this.logicEditor.a("b", "mediaplayerIsPlaying");
                    this.logicEditor.a(" ", "mediaplayerSetLooping");
                    this.logicEditor.a("b", "mediaplayerIsLooping");
                    this.logicEditor.a(" ", "mediaplayerReset");
                    this.logicEditor.a(" ", "mediaplayerRelease");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 9)) {
                    this.logicEditor.a("SoundPool", -11184811);
                    this.logicEditor.a(" ", "soundpoolCreate");
                    this.logicEditor.a("d", "soundpoolLoad");
                    this.logicEditor.a("d", "soundpoolStreamPlay");
                    this.logicEditor.a(" ", "soundpoolStreamStop");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 10)) {
                    this.logicEditor.a("Object animator", -11184811);
                    this.logicEditor.a(" ", "objectanimatorSetTarget");
                    this.logicEditor.a(" ", "objectanimatorSetProperty");
                    this.logicEditor.a(" ", "objectanimatorSetValue");
                    this.logicEditor.a(" ", "objectanimatorSetFromTo");
                    this.logicEditor.a(" ", "objectanimatorSetDuration");
                    this.logicEditor.a(" ", "objectanimatorSetRepeatMode");
                    this.logicEditor.a(" ", "objectanimatorSetRepeatCount");
                    this.logicEditor.a(" ", "objectanimatorSetInterpolator");
                    this.logicEditor.a(" ", "objectanimatorStart");
                    this.logicEditor.a(" ", "objectanimatorCancel");
                    this.logicEditor.a("b", "objectanimatorIsRunning");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 6)) {
                    this.logicEditor.a("Firebase", -11184811);
                    this.logicEditor.a(" ", "firebaseAdd");
                    this.logicEditor.a(" ", "firebasePush");
                    this.logicEditor.a("s", "firebaseGetPushKey");
                    this.logicEditor.a(" ", "firebaseDelete");
                    this.logicEditor.a("c", "firebaseGetChildren");
                    this.logicEditor.a(" ", "firebaseStartListen");
                    this.logicEditor.a(" ", "firebaseStopListen");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 12)) {
                    this.logicEditor.a("FirebaseAuth", -11184811);
                    this.logicEditor.a("b", "firebaseauthIsLoggedIn");
                    this.logicEditor.a("s", "firebaseauthGetCurrentUser");
                    this.logicEditor.a("s", "firebaseauthGetUid");
                    this.logicEditor.a(" ", "firebaseauthCreateUser");
                    this.logicEditor.a(" ", "firebaseauthSignInUser");
                    this.logicEditor.a(" ", "firebaseauthSignInAnonymously");
                    this.logicEditor.a(" ", "firebaseauthResetPassword");
                    this.logicEditor.a(" ", "firebaseauthSignOutUser");
                }
                jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 28);
                jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 29);
                jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 30);
                jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 31);
                jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 32);
                jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 33);
                jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 34);
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 11)) {
                    this.logicEditor.a("Gyroscope", -11184811);
                    this.logicEditor.a(" ", "gyroscopeStartListen");
                    this.logicEditor.a(" ", "gyroscopeStopListen");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 13)) {
                    this.logicEditor.a("Admob", -11184811);
                    this.logicEditor.a(" ", "interstitialadCreate");
                    this.logicEditor.a(" ", "interstitialadLoadAd");
                    this.logicEditor.a(" ", "interstitialadShow");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 14)) {
                    this.logicEditor.a("Firebase Storage", -11184811);
                    this.logicEditor.a(" ", "firebasestorageUploadFile");
                    this.logicEditor.a(" ", "firebasestorageDownloadFile");
                    this.logicEditor.a(" ", "firebasestorageDelete");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 15)) {
                    this.logicEditor.a("Camera", -11184811);
                    this.logicEditor.a(" ", "camerastarttakepicture");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 16)) {
                    this.logicEditor.a("File picker", -11184811);
                    this.logicEditor.a(" ", "filepickerstartpickfiles");
                    this.logicEditor.a(" ", "imageCrop");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 17)) {
                    this.logicEditor.a("Request network", -11184811);
                    this.logicEditor.a("b", "isConnected");
                    this.logicEditor.a(" ", "requestnetworkSetParams");
                    this.logicEditor.a(" ", "requestnetworkSetHeaders");
                    this.logicEditor.a(" ", "requestnetworkStartRequestNetwork");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 18)) {
                    this.logicEditor.a("Text to speech", -11184811);
                    this.logicEditor.a("b", "textToSpeechIsSpeaking");
                    this.logicEditor.a(" ", "textToSpeechSetPitch");
                    this.logicEditor.a(" ", "textToSpeechSetSpeechRate");
                    this.logicEditor.a(" ", "textToSpeechSpeak");
                    this.logicEditor.a(" ", "textToSpeechStop");
                    this.logicEditor.a(" ", "textToSpeechShutdown");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 19)) {
                    this.logicEditor.a("Speech to text", -11184811);
                    this.logicEditor.a(" ", "speechToTextStartListening");
                    this.logicEditor.a(" ", "speechToTextStopListening");
                    this.logicEditor.a(" ", "speechToTextShutdown");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 20)) {
                    this.logicEditor.a("Bluetooth", -11184811);
                    this.logicEditor.a("b", "bluetoothConnectIsBluetoothEnabled");
                    this.logicEditor.a("b", "bluetoothConnectIsBluetoothActivated");
                    this.logicEditor.a("s", "bluetoothConnectGetRandomUuid");
                    this.logicEditor.a(" ", "bluetoothConnectReadyConnection");
                    this.logicEditor.a(" ", "bluetoothConnectReadyConnectionToUuid");
                    this.logicEditor.a(" ", "bluetoothConnectStartConnection");
                    this.logicEditor.a(" ", "bluetoothConnectStartConnectionToUuid");
                    this.logicEditor.a(" ", "bluetoothConnectStopConnection");
                    this.logicEditor.a(" ", "bluetoothConnectSendData");
                    this.logicEditor.a(" ", "bluetoothConnectActivateBluetooth");
                    this.logicEditor.a(" ", "bluetoothConnectGetPairedDevices");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 21)) {
                    this.logicEditor.a("Location manager", -11184811);
                    this.logicEditor.a(" ", "locationManagerRequestLocationUpdates");
                    this.logicEditor.a(" ", "locationManagerRemoveUpdates");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 22)) {
                    this.logicEditor.a("Video ad", -11184811);
                    this.logicEditor.a(" ", "videoAdCreate");
                    this.logicEditor.a(" ", "videoAdLoad");
                    this.logicEditor.a("b", "videoAdIsLoaded");
                    this.logicEditor.a(" ", "videoAdShow");
                    this.logicEditor.a(" ", "videoAdResume");
                    this.logicEditor.a(" ", "videoAdPause");
                    this.logicEditor.a(" ", "videoAdDestroy");
                }
                if (jC.a(this.logicEditor.B).f(this.logicEditor.M.getJavaName(), 23) || this.eventName.equals("onPreExecute") || this.eventName.equals("onProgressUpdate") || this.eventName.equals("onPostExecute")) {
                    this.logicEditor.a("Progress dialog", -11184811);
                    this.logicEditor.a(" ", "progressdialogCreate");
                    this.logicEditor.a(" ", "progressdialogSetTitle");
                    this.logicEditor.a(" ", "progressdialogSetMessage");
                    this.logicEditor.a(" ", "progressdialogSetMax");
                    this.logicEditor.a(" ", "progressdialogSetProgress");
                    this.logicEditor.a(" ", "progressdialogSetCancelable");
                    this.logicEditor.a(" ", "progressdialogSetCanceledOutside");
                    this.logicEditor.a(" ", "progressdialogSetStyle");
                    this.logicEditor.a(" ", "progressdialogShow");
                    this.logicEditor.a(" ", "progressdialogDismiss");
                    return;
                }
                return;
            case 8:
                this.logicEditor.b("Create", "blockAdd");
                this.logicEditor.b("Import From Collection", "blockImport");
                this.logicEditor.b("Explore Shared Collection", "sharedMoreBlock");
                if (BlocksHandler.config("A")) {
                    this.logicEditor.a(" ", "customToast");
                    this.logicEditor.a(" ", "customToastWithIcon");
                }
                moreBlocks();
                if (BlocksHandler.config("A")) {
                    this.logicEditor.a("Command Blocks", -11184811);
                    this.logicEditor.a("c", "CommandBlockJava");
                    this.logicEditor.a("c", "CommandBlockXML");
                    return;
                }
                return;
            default:
                ArrayList extraBlockData = ExtraBlockFile.getExtraBlockData();
                for (int i3 = 0; i3 < extraBlockData.size(); i3++) {
                    HashMap hashMap = (HashMap) extraBlockData.get(i3);
                    if (hashMap.get("palette").equals(String.valueOf(i))) {
                        if (hashMap.get("type").equals("h")) {
                            this.logicEditor.a(hashMap.get("spec").toString(), -11184811);
                        } else if (hashMap.containsKey("typeName")) {
                            this.logicEditor.a("", hashMap.get("type").toString(), hashMap.get("typeName").toString(), hashMap.get("name").toString());
                        } else {
                            this.logicEditor.a("", hashMap.get("type").toString(), "", hashMap.get("name").toString());
                        }
                    }
                }
                return;
        }
    }
}
