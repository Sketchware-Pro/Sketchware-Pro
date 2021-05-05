package a.a.a;

import android.util.Pair;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.handle.code.CodeResult;
import mod.hey.studios.project.ProjectSettings;
import mod.hilal.saif.android_manifest.AndroidManifestInjector;
import mod.hilal.saif.blocks.CommandBlock;
import mod.hilal.saif.events.LogicHandler;

public class Jx {

    public static String a = "\r\n";
    private final ProjectSettings settings;
    public String b;
    public ProjectFileBean c;
    public eC d;
    public Hx e;
    public jq f;
    public ArrayList<String> g = new ArrayList<>();
    public ArrayList<String> h = new ArrayList<>();
    public ArrayList<String> i = new ArrayList<>();
    public ArrayList<String> j = new ArrayList<>();
    public ArrayList<String> k = new ArrayList<>();
    public ArrayList<String> l = new ArrayList<>();
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
    }

    public void removeExtraImports() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Object value : this.g) {
            String str = (String) value;
            if (!arrayList.contains(str)) {
                arrayList.add(str);
            }
        }
        this.g = arrayList;
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
        sb.append("package ").append(this.b).append(";");
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
        if (this.f.a(this.c.getActivityName()).a()) {
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
            sb.append(sb2.toString());
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
            sb.append(sb3.toString());
        }
        for (String str : this.r) {
            if (str.length() > 0) {
                sb.append(a);
                sb.append(str);
            }
        }
        for (Object value : this.h) {
            String str2 = (String) value;
            if (str2.length() > 0) {
                sb.append(a);
                sb.append(str2);
            }
        }
        if (this.i.size() > 0) {
            sb.append(a);
        }
        for (String str3 : this.i) {
            if (str3.length() > 0) {
                sb.append(a);
                sb.append(str3);
            }
        }
        if (this.j.size() > 0) {
            sb.append(a);
        }
        for (Object value : this.j) {
            String str4 = (String) value;
            if (str4.length() > 0) {
                sb.append(a);
                sb.append(str4);
            }
        }
        if (this.k.size() > 0) {
            sb.append(a);
        }
        for (Object value : this.k) {
            String str5 = (String) value;
            if (str5.length() > 0) {
                sb.append(a);
                sb.append(str5);
            }
        }
        if (this.l.size() > 0) {
            sb.append(a);
        }
        for (Object value : this.l) {
            String str6 = (String) value;
            if (str6.length() > 0) {
                sb.append(a);
                sb.append(str6);
            }
        }
        if (this.c.fileName.contains("_fragment")) {
            if (this.f.g) {
                String str7 = a;
                sb.append(str7);
                sb.append(str7);
                sb.append("@NonNull");
                sb.append(a);
                sb.append("@Override");
                sb.append(a);
                sb.append("public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {");
                sb.append(a);
            } else {
                String str8 = a;
                sb.append(str8);
                sb.append(str8);
                sb.append("@Override");
                sb.append(a);
                sb.append("public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {");
                sb.append(a);
            }
            sb.append("View _view = _inflater.inflate(R.layout.");
            sb.append(this.c.fileName);
            sb.append(", _container, false);");
            sb.append(a);
            sb.append("initialize(_savedInstanceState, _view);");
            sb.append(a);
        } else {
            String str9 = a;
            sb.append(str9);
            sb.append(str9);
            sb.append("@Override");
            sb.append(a);
            sb.append("protected void onCreate(Bundle _savedInstanceState) {");
            sb.append(a);
            sb.append("super.onCreate(_savedInstanceState);");
            sb.append(a);
            sb.append("setContentView(R.layout.").append(this.c.fileName).append(");");
            sb.append(a);
            sb.append("initialize(_savedInstanceState);");
            sb.append(a);
        }
        if (this.f.h) {
            addImport("com.google.firebase.FirebaseApp");
            if (this.c.fileName.contains("_fragment")) {
                sb.append("com.google.firebase.FirebaseApp.initializeApp(getContext());");
            } else {
                sb.append("com.google.firebase.FirebaseApp.initializeApp(this);");
            }
            sb.append(a);
        }
        if (!this.f.a(this.c.getActivityName()).a() || this.c.fileName.contains("_fragment")) {
            sb.append("initializeLogic();");
            sb.append(a);
        } else {
            jq jqVar = this.f;
            sb.append(Lx.a(jqVar.g, jqVar.a(this.c.getActivityName()).c));
        }
        if (this.c.fileName.contains("_fragment")) {
            sb.append("return _view;");
            sb.append(a);
        }
        sb.append("}");
        sb.append(a);
        if (this.f.a(this.c.getActivityName()).a() && !this.c.fileName.contains("_fragment")) {
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
        if (this.c.fileName.contains("_fragment")) {
            sb.append(a);
            sb.append("private void initialize(Bundle _savedInstanceState, View _view) {");
        } else {
            sb.append(a);
            sb.append("private void initialize(Bundle _savedInstanceState) {");
        }
        for (Object value : this.m) {
            String str10 = (String) value;
            if (str10.length() > 0) {
                sb.append(a);
                sb.append(str10);
            }
        }
        for (Object value : this.n) {
            String str11 = (String) value;
            if (str11.length() > 0) {
                sb.append(a);
                sb.append(str11);
            }
        }
        String g2 = this.e.g();
        if (g2.length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(g2);
        }
        String c2 = this.e.c();
        if (c2.length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(c2);
        }
        String d2 = this.e.d();
        if (d2.length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(d2);
        }
        String f2 = this.e.f();
        if (f2.length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(f2);
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
        if (this.c.fileName.contains("_fragment")) {
            sb.append("public ");
        } else {
            sb.append("protected ");
        }
        sb.append("void onActivityResult(int _requestCode, int _resultCode, Intent _data) {");
        sb.append(a);
        sb.append(CodeResult.c(this.f.x));
        sb.append("super.onActivityResult(_requestCode, _resultCode, _data);");
        sb.append(a);
        sb.append("switch (_requestCode) {");
        sb.append(a);
        sb.append(this.e.a());
        sb.append(a);
        sb.append("default:");
        sb.append(a);
        sb.append("break;");
        sb.append(a);
        sb.append("}");
        sb.append(a);
        sb.append("}");
        sb.append(a);
        if (this.c.hasActivityOption(4)) {
            this.e.a("onBackPressed", "DrawerLayout", "_drawer");
        }
        ArrayList<ViewBean> d3 = this.d.d(this.c.getXmlName());
        if (d3.size() > 0) {
            for (ViewBean next : d3) {
                if (next.type == 18) {
                    this.e.a("onStart", "MapView", next.id);
                    this.e.a("onResume", "MapView", next.id);
                    this.e.a("onPause", "MapView", next.id);
                    this.e.a("onStop", "MapView", next.id);
                    this.e.a("onDestroy", "MapView", next.id);
                }
            }
        }
        String str12 = this.e.k;
        if (str12.length() > 0) {
            sb.append(a);
            sb.append(str12);
            sb.append(a);
        }
        String str13 = this.e.l;
        if (str13.length() > 0) {
            sb.append(a);
            sb.append(str13);
            sb.append(a);
        }
        String base = LogicHandler.base(this.e.b());
        if (base.length() > 0) {
            sb.append(a);
            sb.append(base);
        }
        this.is = base.contains("public CharSequence onTabLayoutNewTabAdded(int _position) {");
        for (Object value : this.p) {
            sb.append(a);
            sb.append((String) value);
            sb.append(a);
        }
        for (Object value : this.q) {
            String str14 = (String) value;
            if (this.is || !str14.contains("return onTabLayoutNewTabAdded(pos);")) {
                sb.append(a);
                sb.append(str14);
                sb.append(a);
            } else {
                sb.append(a);
                sb.append(str14.replace("return onTabLayoutNewTabAdded(pos);", "// use the activitiy event (onTabLayoutNewTabAdded) in order to use this method\r\nreturn \"page \" + String.valueOf(pos);"));
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
            sb4 = sb4.replaceAll("getApplicationContext|getBaseContext", "getContext").replace("(ClipboardManager) getSystemService", "(ClipboardManager) getContext().getSystemService").replace("(Vibrator) getSystemService", "(Vibrator) getContext().getSystemService").replace("(SensorManager) getSystemService", "(SensorManager) getContext().getSystemService").replace("Typeface.createFromAsset(getAssets()", "Typeface.createFromAsset(getContext().getAssets()").replace("= getAssets().open", "= getContext().getAssets().open").replace("getSharedPreferences", "getContext().getSharedPreferences").replace("AlertDialog.Builder(this);", "AlertDialog.Builder(getContext());").replace("SpeechRecognizer.createSpeechRecognizer(this);", "SpeechRecognizer.createSpeechRecognizer(getContext());").replace("new RequestNetwork(this);", "new RequestNetwork((Activity)getContext());").replace("new BluetoothConnect(this);", "new BluetoothConnect((Activity)getContext());").replace("MobileAds.getRewardedVideoAdInstance(this);", "MobileAds.getRewardedVideoAdInstance(getContext());").replace("runOnUiThread(new", "getActivity().runOnUiThread(new").replace(".setLayoutManager(new LinearLayoutManager(this", ".setLayoutManager(new LinearLayoutManager(getContext()");
        }
        if (this.f.g) {
            sb4 = sb4.replaceAll("getFragmentManager", "getSupportFragmentManager");
        }
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
        sb.append(a).append("@Deprecated").append(a).append("public void showMessage(String _s) {").append(a).append("Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();").append(a).append("}").append(a).append(a).append("@Deprecated").append(a).append("public int getLocationX(View _v) {").append(a).append("int _location[] = new int[2];").append(a).append("_v.getLocationInWindow(_location);").append(a).append("return _location[0];").append(a).append("}").append(a).append(a).append("@Deprecated").append(a).append("public int getLocationY(View _v) {").append(a).append("int _location[] = new int[2];").append(a).append("_v.getLocationInWindow(_location);").append(a).append("return _location[1];").append(a).append("}").append(a).append(a).append("@Deprecated").append(a).append("public int getRandom(int _min, int _max) {").append(a).append("Random random = new Random();").append(a).append("return random.nextInt(_max - _min + 1) + _min;").append(a).append("}").append(a).append(a).append("@Deprecated").append(a).append("public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {").append(a).append("ArrayList<Double> _result = new ArrayList<Double>();").append(a).append("SparseBooleanArray _arr = _list.getCheckedItemPositions();").append(a).append("for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {").append(a).append("if (_arr.valueAt(_iIdx))").append(a).append("_result.add((double)_arr.keyAt(_iIdx));").append(a).append("}").append(a).append("return _result;").append(a).append("}").append(a).append(a).append("@Deprecated").append(a).append("public float getDip(int _input) {").append(a).append("return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());").append(a).append("}").append(a).append(a).append("@Deprecated").append(a).append("public int getDisplayWidthPixels() {").append(a).append("return getResources().getDisplayMetrics().widthPixels;").append(a).append("}").append(a).append(a).append("@Deprecated").append(a).append("public int getDisplayHeightPixels() {").append(a).append("return getResources().getDisplayMetrics().heightPixels;").append(a).append("}").append(a);
    }

    public final void a(ArrayList arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            for (Object value : arrayList) {
                addImport(value.toString());
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
        String str;
        if (this.f.g) {
            addImport("androidx.appcompat.app.AppCompatActivity");
            addImport("androidx.annotation.*");
        } else {
            addImport("android.app.Activity");
        }
        if (this.f.g) {
            if (this.c.hasActivityOption(1) && !this.c.fileName.contains("_fragment")) {
                StringBuilder sb = new StringBuilder();
                this.i.add("private Toolbar _toolbar;");
                this.i.add("private AppBarLayout _app_bar;");
                this.i.add("private CoordinatorLayout _coordinator;");
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
                this.m.add(sb.toString());
                addImport("androidx.appcompat.widget.Toolbar");
                addImport("androidx.coordinatorlayout.widget.CoordinatorLayout");
                addImport("com.google.android.material.appbar.AppBarLayout");
            }
            if (this.c.hasActivityOption(8)) {
                this.i.add("private FloatingActionButton _fab;");
                StringBuilder sb2 = new StringBuilder();
                if (this.c.fileName.contains("_fragment")) {
                    sb2.append("_fab = (FloatingActionButton) _view.findViewById(R.id._fab);");
                } else {
                    sb2.append("_fab = (FloatingActionButton) findViewById(R.id._fab);");
                }
                sb2.append(a);
                this.m.add(sb2.toString());
                addImport("com.google.android.material.floatingactionbutton.FloatingActionButton");
            }
            if (this.c.hasActivityOption(4) && !this.c.fileName.contains("_fragment")) {
                this.i.add("private DrawerLayout _drawer;");
                if (this.c.hasActivityOption(1)) {
                    str = "_drawer = (DrawerLayout) findViewById(R.id._drawer);" + a + "ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(" + this.c.getActivityName() + ".this, _drawer, _toolbar, R.string.app_name, R.string.app_name);" + a;
                } else {
                    str = "_drawer = (DrawerLayout) findViewById(R.id._drawer);" + a + "ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(" + this.c.getActivityName() + ".this, _drawer, R.string.app_name, R.string.app_name);" + a;
                }
                this.m.add(str + "_drawer.addDrawerListener(_toggle);" + a + "_toggle.syncState();" + a + a + "LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);" + a);
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
        addImport("java.io*");
        addImport("java.util.*");
        addImport("java.util.regex.*");
        addImport("java.text.*");
        addImport("org.json.*");
        this.o = new Fx(this.c.getActivityName(), this.f, "onCreate_initializeLogic", this.d.a(this.c.getJavaName(), "onCreate_initializeLogic")).a();
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
        if (this.c.fileName.contains("_fragment")) {
            return Lx.b(replaceAll, viewBean.id, true);
        }
        return Lx.b(replaceAll, viewBean.id, false);
    }

    public final void d() {
        String javaName = this.c.getJavaName();
        for (Pair<String, String> next : this.d.i(javaName)) {
            String str = next.first + "_" + "moreBlock";
            this.c.hasActivityOption(4);
            this.p.add(Lx.a(next.first, next.second, new Fx(this.c.getActivityName(), this.f, str, this.d.a(javaName, str)).a()));
        }
    }

    public final void e() {
        this.e = new Hx(this.f, this.c, this.d);
        a(this.e.e());
    }

    public final void f() {
        for (Map.Entry<String, ArrayList<BlockBean>> entry : this.d.b(this.c.getJavaName()).entrySet()) {
            for (BlockBean blockBean : entry.getValue()) {
                String str = blockBean.opCode;
                char c2 = 65535;
                switch (str.hashCode()) {
                    case -1975568730:
                        if (str.equals("copyToClipboard")) {
                            c2 = 7;
                            break;
                        }
                        break;
                    case -1435533117:
                        if (str.equals("listStrToStr")) {
                            c2 = 11;
                            break;
                        }
                        break;
                    case -1149848189:
                        if (str.equals("toStringFormat")) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case -733318734:
                        if (str.equals("strToListMap")) {
                            c2 = 3;
                            break;
                        }
                        break;
                    case -733312377:
                        if (str.equals("strToListStr")) {
                            c2 = '\n';
                            break;
                        }
                        break;
                    case -208762465:
                        if (str.equals("toStringWithDecimal")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case -101305250:
                    case 308049309:
                    case 41072934:
                        c2 = '\n';
                        break;
                    case 168740282:
                        if (str.equals("mapToStr")) {
                            c2 = 4;
                            break;
                        }
                        break;
                    case 470160234:
                        if (str.equals("fileutilGetLastSegmentPath")) {
                            c2 = '\b';
                            break;
                        }
                        break;
                    case 1129709718:
                        if (str.equals("setImageUrl")) {
                            c2 = '\t';
                            break;
                        }
                        break;
                    case 1252547704:
                        if (str.equals("listMapToStr")) {
                            c2 = 5;
                            break;
                        }
                        break;
                    case 1313527577:
                        if (str.equals("setTypeface")) {
                            c2 = 6;
                            break;
                        }
                        break;
                    case 1775620400:
                        str.equals("strToMap");
                        c2 = '\n';
                        break;
                }
                switch (c2) {
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
        String xmlName = this.c.getXmlName();
        String javaName = this.c.getJavaName();
        ArrayList<ViewBean> d2 = this.d.d(xmlName);
        if (d2.size() > 0) {
            for (ViewBean viewBean : d2) {
                this.m.add(d(viewBean));
            }
        }
        if (this.c.hasActivityOption(4)) {
            ArrayList<ViewBean> d3 = this.d.d(this.c.getDrawerXmlName());
            if (d3.size() > 0) {
                for (ViewBean viewBean : d3) {
                    this.m.add(c(viewBean));
                }
            }
        }
        ArrayList<ComponentBean> e2 = this.d.e(javaName);
        if (e2.size() > 0) {
            for (ComponentBean componentBean : e2) {
                this.n.add(b(componentBean));
            }
        }
    }

    public final void h() {
        Iterator<ComponentBean> it = this.d.e(this.c.getJavaName()).iterator();
        int i2 = 100;
        while (it.hasNext()) {
            ComponentBean next = it.next();
            int i3 = next.type;
            if (i3 == 15 || i3 == 31 || i3 == 16) {
                ArrayList<String> arrayList = this.r;
                String str = next.componentId;
                int i4 = i2 + 1;
                arrayList.add(Lx.a(str, i4));
                i2 = i4;
            }
        }
    }

    public final void i() {
        boolean z = false;
        String xmlName = this.c.getXmlName();
        String javaName = this.c.getJavaName();
        ArrayList<Pair<Integer, String>> k2 = this.d.k(javaName);
        if (k2.size() > 0) {
            for (Pair<Integer, String> next : k2) {
                int intValue = next.first;
                String str = next.second;
                if (intValue == 9) {
                    addImport(str);
                } else {
                    this.i.add(b(intValue, str));
                }
            }
        }
        ArrayList<Pair<Integer, String>> j2 = this.d.j(javaName);
        if (j2.size() > 0) {
            for (Pair<Integer, String> next2 : j2) {
                this.j.add(a(next2.first, next2.second));
            }
        }
        ArrayList<ViewBean> d2 = this.d.d(xmlName);
        if (d2.size() > 0) {
            for (ViewBean viewBean : d2) {
                this.k.add(b(viewBean));
            }
        }
        if (this.c.hasActivityOption(4)) {
            ArrayList<ViewBean> d3 = this.d.d(this.c.getDrawerXmlName());
            if (d3.size() > 0) {
                for (ViewBean viewBean : d3) {
                    this.k.add(a(viewBean));
                }
            }
        }
        ArrayList<ComponentBean> e2 = this.d.e(javaName);
        if (e2.size() > 0) {
            for (ComponentBean componentBean : e2) {
                this.l.add(a(componentBean));
            }
        }
        if (e2.size() > 0) {
            Iterator<ComponentBean> it6 = e2.iterator();
            boolean z2 = false;
            boolean z3 = false;
            while (it6.hasNext()) {
                ComponentBean next3 = it6.next();
                if (next3.type == 5) {
                    z2 = true;
                }
                if (next3.type == 6) {
                    z3 = true;
                }
                if (next3.type == 14) {
                    z = true;
                }
            }
            if (z2) {
                this.h.add(Lx.d("Timer"));
            }
            if (z3) {
                this.h.add(Lx.d("FirebaseDB"));
            }
            if (z) {
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
