package a.a.a;

import android.util.Pair;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.Map;

import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.handle.code.CodeResult;
import mod.hey.studios.project.ProjectSettings;
import mod.hilal.saif.android_manifest.AndroidManifestInjector;
import mod.hilal.saif.blocks.CommandBlock;
import mod.hilal.saif.events.LogicHandler;
import mod.jbk.util.LogUtil;
import mod.w3wide.control.logic.PermissionManager;
import mod.w3wide.control.logic.SourceHandler;

public class Jx {

    public static String a = "\r\n";
    private final ProjectSettings settings;
    private final PermissionManager permMan;
    private final SourceHandler sourceHandler;
    /**
     * Currently generating class' package name,
     * e.g. com.jbk.internal.demo
     */
    public String b;
    public ProjectFileBean c;
    public eC d;
    public Hx e;
    public jq f;
    /**
     * Imports to be added to the currently generating class,
     * e.g. {"com.google.firebase.FirebaseApp"}
     */
    public ArrayList<String> g = new ArrayList<>();
    public ArrayList<String> h = new ArrayList<>();
    /**
     * Fields of the currently generating class,
     * e.g. {"private FloatingActionBar _fab;"}
     */
    public ArrayList<String> i = new ArrayList<>();
    public ArrayList<String> j = new ArrayList<>();
    public ArrayList<String> k = new ArrayList<>();
    public ArrayList<String> l = new ArrayList<>();
    /**
     * Statements to be added to initialize(Bundle),
     * e.g. {"_drawer.addDrawerListener(_toggle);"}
     */
    public ArrayList<String> m = new ArrayList<>();
    public ManageLocalLibrary mll;
    public ArrayList<String> n = new ArrayList<>();
    public String o = "";
    public ArrayList<String> p = new ArrayList<>();
    public ArrayList<String> q = new ArrayList<>();
    public ArrayList<String> r = new ArrayList<>();
    public Lx.a s;
    private boolean is = false;

    public Jx(jq jqVar, ProjectFileBean projectFileBean, eC eCVar) {
        this.b = jqVar.a;
        this.f = jqVar;
        this.c = projectFileBean;
        this.d = eCVar;
        this.mll = new ManageLocalLibrary(this.d.a);
        this.settings = new ProjectSettings(this.d.a);
        permMan = new PermissionManager(d.a, c.getJavaName());
        sourceHandler = new SourceHandler(d.a, c.getJavaName());
    }

    private void extraVariables() {
        for (String variable : sourceHandler.customVariables()) {
            i.add(variable);
        }
        for (String binds : sourceHandler.viewBinds()) {
            m.add(binds);
        }
    }

    /**
     * Removes duplicate imports in {@link Jx#g}.
     */
    private void removeExtraImports() {
        ArrayList<String> newImports = new ArrayList<>();
        for (String value : g) {
            if (!newImports.contains(value) && !value.trim().isEmpty()) {
                newImports.add(value);
            }
        }
        g = newImports;
    }

    public String getLauncherActivity(String str) {
        StringBuilder sb = new StringBuilder();
        String activityName = getActivityName(AndroidManifestInjector.getLauncherActivity(this.d.a));
        if (!activityName.equals("MainActivity")) {
            sb.append("import ");
            sb.append(this.b);
            sb.append(".");
            sb.append(activityName);
            sb.append(";");
            sb.append("\r\n");
        }
        return sb.toString();
    }

    public String getActivityName(String str) {
        String str2;
        int i2;
        String lowerCase = str.toLowerCase();
        int i3 = 0;
        String str3 = "";
        while (i3 < lowerCase.length()) {
            char charAt = lowerCase.charAt(i3);
            if (charAt == '_' && i3 < lowerCase.length() - 1) {
                i2 = i3 + 1;
                char charAt2 = lowerCase.charAt(i2);
                if (Character.isLowerCase(charAt2)) {
                    str2 = str3 + Character.toUpperCase(charAt2);
                } else {
                    str2 = str3 + charAt;
                    i2 = i3;
                }
            } else if (i3 == 0) {
                str2 = str3 + Character.toUpperCase(charAt);
                i2 = i3;
            } else {
                str2 = str3 + charAt;
                i2 = i3;
            }
            str3 = str2;
            i3 = i2 + 1;
        }
        return str3 + "Activity";
    }

    public String a() {
        extraVariables();
        handleAppCompat();
        i();
        g();
        e();
        d();
        c();
        h();
        f();
        j();
        StringBuilder sb = new StringBuilder(8192);
        sb.append("package ").append(b).append(";");
        sb.append(a);
        sb.append(a);
        if (this.c.getActivityName().equals("MainActivity")) {
            sb.append(getLauncherActivity(this.b));
        }
        removeExtraImports();
        for (String value : this.g) {
            sb.append("import ").append(value).append(";");
            sb.append(a);
        }
        if (this.f.g) {
            sb.append("import androidx.fragment.app.Fragment;");
            sb.append(a);
            sb.append("import androidx.fragment.app.FragmentManager;");
            sb.append(a);
            sb.append("import androidx.fragment.app.DialogFragment;");
            sb.append(a);
            if (this.c.fileName.contains("_bottomdialog_fragment")) {
                sb.append("import com.google.android.material.bottomsheet.BottomSheetDialogFragment;");
                sb.append(a);
            }
        } else {
            sb.append("import android.app.Fragment;");
            sb.append(a);
            sb.append("import android.app.FragmentManager;");
            sb.append(a);
            sb.append("import android.app.DialogFragment;");
            sb.append(a);
        }
        if (permMan.hasNewPermission() || this.f.a(this.c.getActivityName()).a()) {
            if (this.f.g) {
                sb.append("import androidx.core.content.ContextCompat;");
                sb.append(a);
                sb.append("import androidx.core.app.ActivityCompat;");
                sb.append(a);
            }
            sb.append("import android.Manifest;");
            sb.append(a);
            sb.append("import android.content.pm.PackageManager;");
            sb.append(a);
        }
        sb.append(LogicHandler.imports(this.e.b()));
        sb.append(a);
        sb.append(a);
        if (this.f.g) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("public class ");
            sb2.append(this.c.getActivityName());
            sb2.append(" extends ");
            if (this.c.fileName.contains("_bottomdialog_fragment")) {
                sb2.append("BottomSheetDialogFragment");
            } else if (this.c.fileName.contains("_dialog_fragment")) {
                sb2.append("DialogFragment");
            } else if (this.c.fileName.contains("_fragment")) {
                sb2.append("Fragment");
            } else {
                sb2.append("AppCompatActivity");
            }
            sb2.append(" {");
            sb.append(sb2);
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("public class ");
            sb3.append(this.c.getActivityName());
            sb3.append(" extends ");
            if (this.c.fileName.contains("_bottomdialog_fragment")) {
                sb3.append("/* Enable AppCompat to use it */ ");
            }
            if (this.c.fileName.contains("_dialog_fragment")) {
                sb3.append("DialogFragment");
            } else if (this.c.fileName.contains("_fragment")) {
                sb3.append("Fragment");
            } else {
                sb3.append("Activity");
            }
            sb3.append(" {");
            sb.append(sb3);
        }
        for (String str : this.r) {
            if (str.length() > 0) {
                sb.append(a);
                sb.append(str);
            }
        }
        for (String value : this.h) {
            if (value.length() > 0) {
                sb.append(a);
                sb.append(value);
            }
        }
        if (i.size() > 0) {
            sb.append(a);
        }
        for (String str3 : i) {
            if (str3.length() > 0) {
                sb.append(a);
                sb.append(str3);
            }
        }
        if (j.size() > 0) {
            sb.append(a);
        }
        for (String value : j) {
            if (value.length() > 0) {
                sb.append(a);
                sb.append(value);
            }
        }
        if (k.size() > 0) {
            sb.append(a);
        }
        for (String value : k) {
            if (value.length() > 0) {
                sb.append(a);
                sb.append(value);
            }
        }
        if (l.size() > 0) {
            sb.append(a);
        }
        for (String value : l) {
            if (value.length() > 0) {
                sb.append(a);
                sb.append(value);
            }
        }
        if (this.c.fileName.contains("_fragment")) {
            if (f.g) {
                sb.append(a);
                sb.append(a);
                sb.append("@NonNull");
                sb.append(a);
                sb.append("@Override");
                sb.append(a);
                sb.append("public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {");
                sb.append(a);
            } else {
                sb.append(a);
                sb.append(a);
                sb.append("@Override");
                sb.append(a);
                sb.append("public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {");
                sb.append(a);
            }
            sb.append("View _view = _inflater.inflate(R.layout.");
            sb.append(c.fileName);
            sb.append(", _container, false);");
            sb.append(a);
            sb.append("initialize(_savedInstanceState, _view);");
            sb.append(a);
        } else {
            sb.append(a);
            sb.append(a);
            sb.append("@Override");
            sb.append(a);
            sb.append("protected void onCreate(Bundle _savedInstanceState) {");
            sb.append(a);
            sb.append("super.onCreate(_savedInstanceState);");
            sb.append(a);
            sb.append("setContentView(R.layout.").append(c.fileName).append(");");
            sb.append(a);
            sb.append("initialize(_savedInstanceState);");
            sb.append(a);
        }
        if (f.h) {
            addImport("com.google.firebase.FirebaseApp");
            if (c.fileName.contains("_fragment")) {
                sb.append("com.google.firebase.FirebaseApp.initializeApp(getContext());");
            } else {
                sb.append("com.google.firebase.FirebaseApp.initializeApp(this);");
            }
            sb.append(a);
        }
        sb.append(a);
        if (!c.fileName.contains("_fragment")) {
            sb.append(permMan.writePermission(f.g, f.a(c.getActivityName()).c));
        }
        if (c.fileName.contains("_fragment")) {
            sb.append("initializeLogic();");
            sb.append(a);
            sb.append("return _view;");
            sb.append(a);
        }
        sb.append("}");
        sb.append(a);
        if (permMan.hasPermission && !c.fileName.contains("_fragment")) {
            sb.append(a);
            sb.append("@Override");
            sb.append(a);
            sb.append("public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {");
            sb.append(a);
            sb.append("super.onRequestPermissionsResult(requestCode, permissions, grantResults);");
            sb.append(a);
            sb.append("if (requestCode == 1000) {");
            sb.append(a);
            sb.append("initializeLogic();");
            sb.append(a);
            sb.append("}");
            sb.append(a);
            sb.append("}");
            sb.append(a);
        }
        if (c.fileName.contains("_fragment")) {
            sb.append(a);
            sb.append("private void initialize(Bundle _savedInstanceState, View _view) {");
        } else {
            sb.append(a);
            sb.append("private void initialize(Bundle _savedInstanceState) {");
        }
        sb.append(a);
        sb.append(sourceHandler.initializeLogic(f, c.getActivityName()));
        for (String value : m) {
            if (value.length() > 0) {
                sb.append(a);
                sb.append(value);
            }
        }
        for (String value : n) {
            if (value.length() > 0) {
                sb.append(a);
                sb.append(value);
            }
        }
        if (e.g().length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(e.g());
        }
        if (e.c().length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(e.c());
        }
        if (e.d().length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(e.d());
        }
        if (e.f().length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(e.f());
        }
        sb.append(a);
        sb.append("}");
        sb.append(a);
        sb.append(a);
        sb.append("private void initializeLogic() {");
        sb.append(a);
        if (this.o.length() > 0) {
            sb.append(this.o);
            sb.append(a);
        }
        sb.append("}");
        sb.append(a);
        sb.append(a);
        sb.append("@Override");
        sb.append(a);
        if (c.fileName.contains("_fragment")) {
            sb.append("public ");
        } else {
            sb.append("protected ");
        }
        sb.append("void onActivityResult(int _requestCode, int _resultCode, Intent _data) {");
        sb.append(a);
        sb.append(CodeResult.c(f.x));
        sb.append("super.onActivityResult(_requestCode, _resultCode, _data);");
        sb.append(a);
        sb.append(sourceHandler.activityResult(f, c.getActivityName()));
        sb.append(a);
        sb.append("switch (_requestCode) {");
        sb.append(a);
        sb.append(e.a());
        sb.append(a);
        sb.append("default:");
        sb.append(a);
        sb.append("break;");
        sb.append(a);
        sb.append("}");
        sb.append(a);
        sb.append("}");
        sb.append(a);
        if (c.hasActivityOption(4)) {
            e.a("onBackPressed", "DrawerLayout", "_drawer");
        }
        ArrayList<ViewBean> beans = d.d(c.getXmlName());
        if (beans.size() > 0) {
            for (ViewBean next : beans) {
                if (next.type == 18) {
                    e.a("onStart", "MapView", next.id);
                    e.a("onResume", "MapView", next.id);
                    e.a("onPause", "MapView", next.id);
                    e.a("onStop", "MapView", next.id);
                    e.a("onDestroy", "MapView", next.id);
                }
            }
        }
        if (e.k.length() > 0) {
            sb.append(a);
            sb.append(e.k);
            sb.append(a);
        }
        if (e.l.length() > 0) {
            sb.append(a);
            sb.append(e.l);
            sb.append(a);
        }
        String base = LogicHandler.base(e.b());
        if (base.length() > 0) {
            sb.append(a);
            sb.append(base);
        }
        is = base.contains("public CharSequence onTabLayoutNewTabAdded(int _position) {");
        for (String value : p) {
            sb.append(a);
            sb.append(value);
            sb.append(a);
        }
        for (String value : q) {
            if (is || !value.contains("return onTabLayoutNewTabAdded(pos);")) {
                sb.append(a);
                sb.append(value);
                sb.append(a);
            } else {
                sb.append(a);
                sb.append(value.replace("return onTabLayoutNewTabAdded(pos);", "// Use the Activity Event (onTabLayoutNewTabAdded) in order to use this method\r\nreturn \"page \" + String.valueOf(pos);"));
                sb.append(a);
            }
        }
        if (!this.c.fileName.contains("_fragment") && this.settings.getValue("disable_old_methods", "false").equals("false")) {
            deprecatedMethods(sb);
        }
        sb.append("}");
        sb.append(a);
        String sb4 = sb.toString();
        if (this.c.fileName.contains("_fragment")) {
            sb4 = sb4.replaceAll("getApplicationContext|getBaseContext", "getContext")
                    .replace("(ClipboardManager) getSystemService", "(ClipboardManager) getContext().getSystemService")
                    .replace("(Vibrator) getSystemService", "(Vibrator) getContext().getSystemService")
                    .replace("(SensorManager) getSystemService", "(SensorManager) getContext().getSystemService")
                    .replace("Typeface.createFromAsset(getAssets()", "Typeface.createFromAsset(getContext().getAssets()")
                    .replace("= getAssets().open", "= getContext().getAssets().open")
                    .replace("getSharedPreferences", "getContext().getSharedPreferences")
                    .replace("AlertDialog.Builder(this);", "AlertDialog.Builder(getContext());")
                    .replace("SpeechRecognizer.createSpeechRecognizer(this);", "SpeechRecognizer.createSpeechRecognizer(getContext());")
                    .replace("new RequestNetwork(this);", "new RequestNetwork((Activity)getContext());")
                    .replace("new BluetoothConnect(this);", "new BluetoothConnect((Activity)getContext());")
                    .replace("MobileAds.getRewardedVideoAdInstance(this);", "MobileAds.getRewardedVideoAdInstance(getContext());")
                    .replace("runOnUiThread(new", "getActivity().runOnUiThread(new")
                    .replace(".setLayoutManager(new LinearLayoutManager(this", ".setLayoutManager(new LinearLayoutManager(getContext()");
        }
        if (this.f.g) {
            sb4 = sb4.replaceAll("getFragmentManager", "getSupportFragmentManager");
        }
        LogUtil.dump("Jx", this);
        return CommandBlock.CB(Lx.j(sb4));
    }

    public final String a(int i2, String str) {
        String b2 = mq.b(i2);
        a(mq.c(b2));
        return Lx.a(b2, str, Lx.a.a);
    }

    public final String a(ComponentBean componentBean) {
        String a2 = mq.a(componentBean.type);
        a(mq.c(a2));
        return Lx.a(a2, componentBean.componentId, Lx.a.a, componentBean.param1, componentBean.param2, componentBean.param3);
    }

    public final String a(ViewBean viewBean) {
        String replaceAll = viewBean.convert.replaceAll("\\w*\\..*\\.", "");
        if (replaceAll.equals("")) {
            replaceAll = viewBean.getClassInfo().a();
        }
        a(mq.c(replaceAll));
        return Lx.a(replaceAll, "_drawer_" + viewBean.id, Lx.a.a);
    }

    public final void addImport(String str) {
        if (!this.g.contains(str)) {
            this.g.add(str);
        }
    }

    public final void deprecatedMethods(StringBuilder sb) {
        sb.append(a)
                .append("@Deprecated")
                .append(a)
                .append("public void showMessage(String _s) {")
                .append(a)
                .append("Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();")
                .append(a)
                .append("}")
                .append(a)
                .append(a)
                .append("@Deprecated")
                .append(a)
                .append("public int getLocationX(View _v) {")
                .append(a)
                .append("int _location[] = new int[2];")
                .append(a)
                .append("_v.getLocationInWindow(_location);")
                .append(a)
                .append("return _location[0];")
                .append(a)
                .append("}")
                .append(a)
                .append(a)
                .append("@Deprecated")
                .append(a)
                .append("public int getLocationY(View _v) {")
                .append(a)
                .append("int _location[] = new int[2];")
                .append(a)
                .append("_v.getLocationInWindow(_location);")
                .append(a)
                .append("return _location[1];")
                .append(a)
                .append("}")
                .append(a)
                .append(a)
                .append("@Deprecated")
                .append(a)
                .append("public int getRandom(int _min, int _max) {")
                .append(a)
                .append("Random random = new Random();")
                .append(a)
                .append("return random.nextInt(_max - _min + 1) + _min;")
                .append(a)
                .append("}")
                .append(a)
                .append(a)
                .append("@Deprecated")
                .append(a)
                .append("public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {")
                .append(a)
                .append("ArrayList<Double> _result = new ArrayList<Double>();")
                .append(a)
                .append("SparseBooleanArray _arr = _list.getCheckedItemPositions();")
                .append(a)
                .append("for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {")
                .append(a)
                .append("if (_arr.valueAt(_iIdx))")
                .append(a)
                .append("_result.add((double)_arr.keyAt(_iIdx));")
                .append(a)
                .append("}")
                .append(a)
                .append("return _result;")
                .append(a)
                .append("}")
                .append(a)
                .append(a)
                .append("@Deprecated")
                .append(a)
                .append("public float getDip(int _input) {")
                .append(a)
                .append("return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());")
                .append(a)
                .append("}")
                .append(a)
                .append(a)
                .append("@Deprecated")
                .append(a)
                .append("public int getDisplayWidthPixels() {")
                .append(a)
                .append("return getResources().getDisplayMetrics().widthPixels;")
                .append(a)
                .append("}")
                .append(a)
                .append(a)
                .append("@Deprecated")
                .append(a)
                .append("public int getDisplayHeightPixels() {")
                .append(a)
                .append("return getResources().getDisplayMetrics().heightPixels;")
                .append(a)
                .append("}")
                .append(a);
    }

    public final void a(ArrayList<String> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            for (String value : arrayList) {
                addImport(value);
            }
        }
    }

    public final String b(int i2, String str) {
        String c2 = mq.c(i2);
        a(mq.c(c2));
        return Lx.a(c2, str, Lx.a.a);
    }

    public final String b(ComponentBean componentBean) {
        return Lx.b(mq.a(componentBean.type), componentBean.componentId, componentBean.param1, componentBean.param2, componentBean.param3);
    }

    public final String b(ViewBean viewBean) {
        String replaceAll = viewBean.convert.replaceAll("\\w*\\..*\\.", "");
        if (replaceAll.equals("")) {
            replaceAll = viewBean.getClassInfo().a();
        }
        a(mq.c(replaceAll));
        return Lx.a(replaceAll, viewBean.id, Lx.a.a);
    }

    public final void handleAppCompat() {
        if (f.g) {
            addImport("androidx.appcompat.app.AppCompatActivity");
            addImport("androidx.annotation.*");
        } else {
            addImport("android.app.Activity");
        }
        if (f.g) {
            if (c.hasActivityOption(1) && !c.fileName.contains("_fragment")) {
                StringBuilder sb = new StringBuilder();
                i.add("private Toolbar _toolbar;");
                i.add("private AppBarLayout _app_bar;");
                i.add("private CoordinatorLayout _coordinator;");
                sb.append("_app_bar = (AppBarLayout) findViewById(R.id._app_bar);");
                sb.append(a);
                sb.append("_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);");
                sb.append(a);
                sb.append("_toolbar = (Toolbar) findViewById(R.id._toolbar);");
                sb.append(a);
                sb.append("setSupportActionBar(_toolbar);");
                sb.append(a);
                sb.append("getSupportActionBar().setDisplayHomeAsUpEnabled(true);");
                sb.append(a);
                sb.append("getSupportActionBar().setHomeButtonEnabled(true);");
                sb.append(a);
                sb.append("_toolbar.setNavigationOnClickListener(new View.OnClickListener() {");
                sb.append(a);
                sb.append("@Override");
                sb.append(a);
                sb.append("public void onClick(View _v) {");
                sb.append(a);
                sb.append("onBackPressed();");
                sb.append(a);
                sb.append("}");
                sb.append(a);
                sb.append("});");
                m.add(sb.toString());
                addImport("androidx.appcompat.widget.Toolbar");
                addImport("androidx.coordinatorlayout.widget.CoordinatorLayout");
                addImport("com.google.android.material.appbar.AppBarLayout");
            }
            if (c.hasActivityOption(8)) {
                i.add("private FloatingActionButton _fab;");
                StringBuilder fab = new StringBuilder();
                if (c.fileName.contains("_fragment")) {
                    fab.append("_fab = (FloatingActionButton) _view.findViewById(R.id._fab);");
                } else {
                    fab.append("_fab = (FloatingActionButton) findViewById(R.id._fab);");
                }
                fab.append(a);
                m.add(fab.toString());
                addImport("com.google.android.material.floatingactionbutton.FloatingActionButton");
            }
            if (c.hasActivityOption(4) && !c.fileName.contains("_fragment")) {
                i.add("private DrawerLayout _drawer;");
                String str;
                if (c.hasActivityOption(1)) {
                    str = "_drawer = (DrawerLayout) findViewById(R.id._drawer);" + a + "ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(" + c.getActivityName() + ".this, _drawer, _toolbar, R.string.app_name, R.string.app_name);" + a;
                } else {
                    str = "_drawer = (DrawerLayout) findViewById(R.id._drawer);" + a + "ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(" + c.getActivityName() + ".this, _drawer, R.string.app_name, R.string.app_name);" + a;
                }
                m.add(str + "_drawer.addDrawerListener(_toggle);" + a + "_toggle.syncState();" + a + a + "LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);" + a);
                addImport("androidx.core.view.GravityCompat");
                addImport("androidx.drawerlayout.widget.DrawerLayout");
                addImport("androidx.appcompat.app.ActionBarDrawerToggle");
                a(mq.c("LinearLayout"));
            }
        }
        addImport("android.app.*");
        addImport("android.os.*");
        addImport("android.view.*");
        addImport("android.view.View.*");
        addImport("android.widget.*");
        addImport("android.content.*");
        addImport("android.content.res.*");
        addImport("android.graphics.*");
        addImport("android.graphics.drawable.*");
        addImport("android.media.*");
        addImport("android.net.*");
        addImport("android.text.*");
        addImport("android.text.style.*");
        addImport("android.util.*");
        addImport("android.webkit.*");
        addImport("android.animation.*");
        addImport("android.view.animation.*");
        addImport("java.io.*");
        addImport("java.util.*");
        addImport("java.util.regex.*");
        addImport("java.text.*");
        addImport("org.json.*");
        o = new Fx(c.getActivityName(), f, "onCreate_initializeLogic", d.a(c.getJavaName(), "onCreate_initializeLogic")).a();
    }

    public final String c(ViewBean viewBean) {
        String replaceAll = viewBean.convert.replaceAll("\\w*\\..*\\.", "");
        if (replaceAll.equals("")) {
            replaceAll = viewBean.getClassInfo().a();
        }
        return Lx.c(replaceAll, viewBean.id, "_nav_view");
    }

    public final void c() {
        String a2;
        for (ViewBean next : this.d.f(this.c.getXmlName())) {
            String xmlName = ProjectFileBean.getXmlName(next.customView);
            this.c.getJavaName();
            String str = next.id + "_" + "onBindCustomView";
            String a3 = new Fx(this.c.getActivityName(), this.f, str, this.d.a(this.c.getJavaName(), str)).a();
            if (next.type == 31) {
                a2 = Lx.pagerAdapter(next.id, next.customView, this.d.d(xmlName), a3);
            } else if (next.type == 48) {
                a2 = Lx.recyclerViewAdapter(next.id, next.customView, this.d.d(xmlName), a3);
            } else {
                a2 = Lx.a(next.id, next.customView, this.d.d(xmlName), a3);
            }
            this.q.add(a2);
        }
    }

    public final String d(ViewBean viewBean) {
        String replaceAll = viewBean.convert.replaceAll("\\w*\\..*\\.", "");
        if (replaceAll.equals("")) {
            replaceAll = viewBean.getClassInfo().a();
        }
        if (c.fileName.contains("_fragment")) {
            return Lx.b(replaceAll, viewBean.id, true);
        }
        return Lx.b(replaceAll, viewBean.id, false);
    }

    /**
     * Handles the Activity's More Blocks and adds them to {@link Jx#p}.
     */
    public final void d() {
        String javaName = this.c.getJavaName();
        ArrayList<Pair<String, String>> pairs = d.i(javaName);
        for (int index = 0, pairsSize = pairs.size(); index < pairsSize; index++) {
            Pair<String, String> next = pairs.get(index);
            String name = next.first + "_moreBlock";
            String code = Lx.a(next.first, next.second, new Fx(c.getActivityName(), f, name, d.a(javaName, name)).a());
            if (index < (pairsSize - 1)) {
                p.add(code);
            } else {
                // Removes unnecessary newline at end of More Block code
                p.add(code.substring(0, code.length() - 2));
            }
        }
    }

    public final void e() {
        e = new Hx(f, c, d);
        a(e.e());
    }

    public final void f() {
        for (Map.Entry<String, ArrayList<BlockBean>> entry : d.b(c.getJavaName()).entrySet()) {
            for (BlockBean blockBean : entry.getValue()) {
                String opCode = blockBean.opCode;
                char type = 65535;
                switch (opCode.hashCode()) {
                    case -1975568730:
                        if (opCode.equals("copyToClipboard")) {
                            type = 7;
                            break;
                        }
                        break;
                    case -1435533117:
                        if (opCode.equals("listStrToStr")) {
                            type = 11;
                            break;
                        }
                        break;
                    case -1149848189:
                        if (opCode.equals("toStringFormat")) {
                            type = 1;
                            break;
                        }
                        break;
                    case -733318734:
                        if (opCode.equals("strToListMap")) {
                            type = 3;
                            break;
                        }
                        break;
                    case -733312377:
                        if (opCode.equals("strToListStr")) {
                            type = '\n';
                            break;
                        }
                        break;
                    case -208762465:
                        if (opCode.equals("toStringWithDecimal")) {
                            type = 0;
                            break;
                        }
                        break;
                    case -101305250:
                    case 308049309:
                    case 41072934:
                        type = '\n';
                        break;
                    case 168740282:
                        if (opCode.equals("mapToStr")) {
                            type = 4;
                            break;
                        }
                        break;
                    case 470160234:
                        if (opCode.equals("fileutilGetLastSegmentPath")) {
                            type = '\b';
                            break;
                        }
                        break;
                    case 1129709718:
                        if (opCode.equals("setImageUrl")) {
                            type = '\t';
                            break;
                        }
                        break;
                    case 1252547704:
                        if (opCode.equals("listMapToStr")) {
                            type = 5;
                            break;
                        }
                        break;
                    case 1313527577:
                        if (opCode.equals("setTypeface")) {
                            type = 6;
                            break;
                        }
                        break;
                    case 1775620400:
                        opCode.equals("strToMap");
                        type = '\n';
                        break;
                }
                switch (type) {
                    case 0:
                    case 1:
                        addImport("java.text.DecimalFormat");
                        break;
                    //case 2:
                    case 3:
                    case '\n':
                        addImport("com.google.gson.Gson");
                        addImport("com.google.gson.reflect.TypeToken");
                        break;
                    case 4:
                    case 5:
                    case 11:
                        addImport("com.google.gson.Gson");
                        break;
                    case 6:
                        addImport("android.graphics.Typeface");
                        break;
                    case 7:
                        addImport("android.content.ClipData");
                        addImport("android.content.ClipboardManager");
                        break;
                    case '\b':
                        addImport("android.net.Uri");
                        break;
                    case '\t':
                        addImport("com.bumptech.glide.Glide");
                        break;
                }
            }
        }
    }

    public final void g() {
        ArrayList<ViewBean> viewBeans = d.d(c.getXmlName());
        if (viewBeans.size() > 0) {
            for (ViewBean viewBean : viewBeans) {
                m.add(d(viewBean));
            }
        }
        if (c.hasActivityOption(4)) {
            ArrayList<ViewBean> drawerBeans = d.d(c.getDrawerXmlName());
            if (drawerBeans.size() > 0) {
                for (ViewBean viewBean : drawerBeans) {
                    m.add(c(viewBean));
                }
            }
        }
        ArrayList<ComponentBean> componentBeans = d.e(c.getJavaName());
        if (componentBeans.size() > 0) {
            for (ComponentBean componentBean : componentBeans) {
                n.add(b(componentBean));
            }
        }
    }

    public final void h() {
        int i2 = 100;
        for (ComponentBean next : d.e(c.getJavaName())) {
            if (next.type == 15 || next.type == 31 || next.type == 16) {
                int i4 = i2 + 1;
                r.add(Lx.a(next.componentId, i4));
                i2 = i4;
            }
        }
    }

    public final void i() {
        String javaName = this.c.getJavaName();
        ArrayList<Pair<Integer, String>> k2 = d.k(javaName);
        for (Pair<Integer, String> next : k2) {
            int intValue = next.first;
            String str = next.second;
            if (intValue == 9) {
                addImport(str);
            } else {
                this.i.add(b(intValue, str));
            }
        }
        ArrayList<Pair<Integer, String>> j2 = d.j(javaName);
        for (Pair<Integer, String> next2 : j2) {
            this.j.add(a(next2.first, next2.second));
        }
        ArrayList<ViewBean> d2 = d.d(c.getXmlName());
        for (ViewBean viewBean : d2) {
            this.k.add(b(viewBean));
        }
        if (this.c.hasActivityOption(4)) {
            ArrayList<ViewBean> d3 = d.d(this.c.getDrawerXmlName());
            for (ViewBean viewBean : d3) {
                this.k.add(a(viewBean));
            }
        }
        ArrayList<ComponentBean> componentBeans = d.e(javaName);
        for (ComponentBean bean : componentBeans) {
            l.add(a(bean));
        }
        if (componentBeans.size() > 0) {
            boolean hasTimer = false;
            boolean hasFirebaseDB = false;
            boolean hasFirebaseStorage = false;
            for (ComponentBean bean : componentBeans) {
                if (bean.type == 5) {
                    hasTimer = true;
                }
                if (bean.type == 6) {
                    hasFirebaseDB = true;
                }
                if (bean.type == 14) {
                    hasFirebaseStorage = true;
                }
            }
            if (hasTimer) {
                this.h.add(Lx.d("Timer"));
            }
            if (hasFirebaseDB) {
                this.h.add(Lx.d("FirebaseDB"));
            }
            if (hasFirebaseStorage) {
                this.h.add(Lx.d("FirebaseStorage"));
            }
        }
    }

    public final void j() {
        for (String value : this.mll.getImportLocalLibrary()) {
            addImport(value);
        }
    }
}
