package a.a.a;

import android.util.Pair;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import mod.agus.jcoderz.beans.ViewBeans;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.handle.code.CodeResult;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hilal.saif.android_manifest.AndroidManifestInjector;
import mod.hilal.saif.blocks.CommandBlock;
import mod.hilal.saif.events.LogicHandler;
import mod.w3wide.control.logic.PermissionManager;
import mod.w3wide.control.logic.SourceHandler;

public class Jx {

    public static String a = "\r\n";
    private final ProjectSettings settings;
    private final PermissionManager permissionManager;
    private final SourceHandler sourceHandler;
    /**
     * Currently generating class' package name,
     * e.g. com.jbk.internal.demo
     */
    public String packageName;
    public ProjectFileBean projectFileBean;
    public eC projectDataManager;
    public Hx e;
    public jq f;
    /**
     * Imports to be added to the currently generating class,
     * e.g. {"com.google.firebase.FirebaseApp"}
     */
    public ArrayList<String> g = new ArrayList<>();
    /**
     * Fields with static initializer that added Components need,
     * e.g. {"private Timer _timer = new Timer();"}
     */
    public ArrayList<String> h = new ArrayList<>();
    /**
     * Fields of the currently generating class,
     * e.g. {"private FloatingActionBar _fab;"}
     */
    public ArrayList<String> i = new ArrayList<>();
    public ArrayList<String> j = new ArrayList<>();
    /**
     * Views declared in this generated class
     */
    public ArrayList<String> k = new ArrayList<>();
    /**
     * Field declarations from components. Can include static initializer, but doesn't have to.
     */
    public ArrayList<String> l = new ArrayList<>();
    /**
     * Statements to be added to initialize(Bundle),
     * e.g. {"_drawer.addDrawerListener(_toggle);"}
     */
    public ArrayList<String> m = new ArrayList<>();
    public ManageLocalLibrary mll;
    /**
     * Component initializer lines which get added to <code>_initialize(Bundle)</code>
     */
    public ArrayList<String> n = new ArrayList<>();
    /**
     * Content of <code>_initializeLogic()</code>
     */
    public String o = "";
    /**
     * Code of More Blocks that have been created
     */
    public ArrayList<String> p = new ArrayList<>();
    /**
     * Code of inner Adapter classes, used for Widgets like ListView or RecyclerView
     */
    public ArrayList<String> q = new ArrayList<>();
    /**
     * (Currently) filled with request code constants for FilePicker components
     */
    public ArrayList<String> r = new ArrayList<>();

    public Jx(jq jqVar, ProjectFileBean projectFileBean, eC eCVar) {
        packageName = jqVar.a;
        this.projectFileBean = projectFileBean;
        projectDataManager = eCVar;
        f = jqVar;
        mll = new ManageLocalLibrary(eCVar.a);
        settings = new ProjectSettings(eCVar.a);
        permissionManager = new PermissionManager(eCVar.a, projectFileBean.getJavaName());
        sourceHandler = new SourceHandler(eCVar.a, projectFileBean.getJavaName());
    }

    private void extraVariables() {
        i.addAll(sourceHandler.customVariables());
        m.addAll(sourceHandler.viewBinds());
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

    /**
     * @return Import to be added to the currently generating class
     * (includes import of default launcher activity)
     */
    private String getLauncherActivity(String packageName) {
        String theImport = "";

        String activityName = ProjectFileBean.getActivityName(AndroidManifestInjector.getLauncherActivity(projectDataManager.a));
        if (!activityName.equals("MainActivity")) {
            theImport = "import " + packageName + "." + activityName + ";" + a;
        }

        return theImport;
    }

    /**
     * @return Generated Java code of the current View (not Widget)
     */
    public String a() {
        boolean isDialogFragment = projectFileBean.fileName.contains("_dialog_fragment");
        boolean isBottomDialogFragment = projectFileBean.fileName.contains("_bottomdialog_fragment");
        boolean isFragment = projectFileBean.fileName.contains("_fragment");

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
        sb.append("package ").append(packageName).append(";").append(a)
                .append(a);
        if (projectFileBean.getActivityName().equals("MainActivity")) {
            sb.append(getLauncherActivity(packageName));
        }

        if (f.h) addImport("com.google.firebase.FirebaseApp");

        if (f.l) {
            addImport("com.google.android.gms.ads.MobileAds");

            if (f.f) addImport("com.google.android.gms.ads.RequestConfiguration");
        }

        removeExtraImports();
        Collections.sort(g);//just
        for (String anImport : g) {
            sb.append("import ").append(anImport).append(";").append(a);
        }
        if (f.g) {
            sb.append("import androidx.fragment.app.Fragment;").append(a);
            sb.append("import androidx.fragment.app.FragmentManager;").append(a);
            sb.append("import androidx.fragment.app.DialogFragment;").append(a);
            if (isBottomDialogFragment) {
                sb.append("import com.google.android.material.bottomsheet.BottomSheetDialogFragment;").append(a);
            }
        } else {
            sb.append("import android.app.Fragment;").append(a);
            sb.append("import android.app.FragmentManager;").append(a);
            sb.append("import android.app.DialogFragment;").append(a);
        }
        if (permissionManager.hasNewPermission() || f.a(projectFileBean.getActivityName()).a()) {
            if (f.g) {
                sb.append("import androidx.core.content.ContextCompat;").append(a);
                sb.append("import androidx.core.app.ActivityCompat;").append(a);
            }
            sb.append("import android.Manifest;").append(a);
            sb.append("import android.content.pm.PackageManager;").append(a);
        }
        String importsAddedByImportBlocks = LogicHandler.imports(e.b());
        if (!importsAddedByImportBlocks.isEmpty()) {
            sb.append(importsAddedByImportBlocks).append(a);
        }
        sb.append(a);

        sb.append("public class ").append(projectFileBean.getActivityName()).append(" extends ");
        if (f.g) {
            if (isBottomDialogFragment) {
                sb.append("BottomSheetDialogFragment");
            } else if (isDialogFragment) {
                sb.append("DialogFragment");
            } else if (isFragment) {
                sb.append("Fragment");
            } else {
                sb.append("AppCompatActivity");
            }
        } else {
            if (isBottomDialogFragment) {
                sb.append("/* Enable AppCompat to use it */");
            } else if (isDialogFragment) {
                sb.append("DialogFragment");
            } else if (isFragment) {
                sb.append("Fragment");
            } else {
                sb.append("Activity");
            }
        }
        sb.append(" {").append(a);

        boolean activityHasFields = false;

        for (String constant : r) {
            if (constant.length() > 0) {
                activityHasFields = true;
                sb.append(a);
                sb.append(constant);
            }
        }

        if (h.size() > 0) {
            if (activityHasFields) sb.append(a);
            activityHasFields = true;

            for (String componentFieldDeclaration : h) {
                if (componentFieldDeclaration.length() > 0) {
                    sb.append(a);
                    sb.append(componentFieldDeclaration);
                }
            }
        }

        if (i.size() > 0) {
            if (activityHasFields) sb.append(a);
            activityHasFields = true;

            for (String field : i) {
                if (field.length() > 0) {
                    sb.append(a);
                    sb.append(field);
                }
            }
        }

        if (j.size() > 0) {
            if (activityHasFields) sb.append(a);
            activityHasFields = true;

            for (String value : j) {
                if (value.length() > 0) {
                    sb.append(a);
                    sb.append(value);
                }
            }
        }

        if (k.size() > 0) {
            if (activityHasFields) sb.append(a);
            activityHasFields = true;

            for (String viewDeclaration : k) {
                if (viewDeclaration.length() > 0) {
                    sb.append(a);
                    sb.append(viewDeclaration);
                }
            }
        }

        if (l.size() > 0) {
            if (activityHasFields) sb.append(a);
            activityHasFields = true;

            for (String componentFieldDeclaration : l) {
                if (componentFieldDeclaration.length() > 0) {
                    sb.append(a);
                    sb.append(componentFieldDeclaration);
                }
            }
        }

        if (activityHasFields) sb.append(a);

        sb.append(a);
        if (isFragment) {
            if (f.g) {
                sb.append("@NonNull").append(a);
                sb.append("@Override").append(a);
                sb.append("public View onCreateView(@NonNull LayoutInflater _inflater, " +
                        "@Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {").append(a);
            } else {
                sb.append("@Override").append(a);
                sb.append("public View onCreateView(LayoutInflater _inflater, ViewGroup _container, " +
                        "Bundle _savedInstanceState) {").append(a);
            }
            sb.append("View _view = _inflater.inflate(R.layout.").append(projectFileBean.fileName).append(", _container, false);").append(a);
            sb.append("initialize(_savedInstanceState, _view);");
        } else {
            sb.append("@Override").append(a);
            sb.append("protected void onCreate(Bundle _savedInstanceState) {").append(a);
            sb.append("super.onCreate(_savedInstanceState);").append(a);
            sb.append("setContentView(R.layout.").append(projectFileBean.fileName).append(");").append(a);
            sb.append("initialize(_savedInstanceState);");
        }
        sb.append(a);
        if (f.h) {
            if (isFragment) {
                sb.append("FirebaseApp.initializeApp(getContext());");
            } else {
                sb.append("FirebaseApp.initializeApp(this);");
            }
            sb.append(a);
        }

        if (f.l && !isFragment) {
            if (!f.h) {
                sb.append(a);
            }
            sb.append("MobileAds.initialize(this);");
            sb.append(a);
            if (h.contains(Lx.d("InterstitialAd"))) {
                sb.append("_ad_unit_id = \"").append(f.f ? "ca-app-pub-3940256099942544/1033173712" : f.s).append("\";");
            }

            if (f.f) {
                StringBuilder testDevicesListCode = new StringBuilder("List<String> testDeviceIds = Arrays.asList(");
                ArrayList<String> testDevices = f.t;
                for (int j = 0, testDevicesSize = testDevices.size(); j < testDevicesSize; j++) {
                    String testDeviceId = testDevices.get(j);

                    testDevicesListCode.append("\"").append(testDeviceId).append("\"");
                    if (j != testDevicesSize - 1) {
                        testDevicesListCode.append(", ");
                    }
                }
                testDevicesListCode.append(");").append(a);

                sb.append(a);
                sb.append(testDevicesListCode);
                sb.append("MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build());");
            }

            sb.append(a);
        }

        if (!isFragment) {
            // Adds initializeLogic() call too, don't worry
            sb.append(permissionManager.writePermission(f.g, f.a(projectFileBean.getActivityName()).c));
        } else {
            sb.append("initializeLogic();").append(a)
                    .append("return _view;").append(a);
        }
        sb.append("}").append(a);

        if (permissionManager.hasPermission && !isFragment) {
            sb.append(a);
            sb.append("@Override").append(a);
            sb.append("public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {").append(a);
            sb.append("super.onRequestPermissionsResult(requestCode, permissions, grantResults);").append(a);
            sb.append("if (requestCode == 1000) {").append(a);
            sb.append("initializeLogic();").append(a);
            sb.append("}").append(a);
            sb.append("}").append(a);
        }
        sb.append(a);
        if (isFragment) {
            sb.append("private void initialize(Bundle _savedInstanceState, View _view) {");
        } else {
            sb.append("private void initialize(Bundle _savedInstanceState) {");
        }
        sb.append(sourceHandler.initializeLogic(f, projectFileBean.getActivityName()));

        for (String value : m) {
            if (value.length() > 0) {
                sb.append(a);
                sb.append(value);
            }
        }

        for (String componentInitializer : n) {
            if (componentInitializer.length() > 0) {
                sb.append(a);
                sb.append(componentInitializer);
            }
        }

        String hxG = e.g();
        if (hxG.length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(hxG);
        }

        String hxC = e.c();
        if (hxC.length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(hxC);
        }

        String hxD = e.d();
        if (hxD.length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(hxD);
        }

        String hxF = e.f();
        if (hxF.length() > 0) {
            sb.append(a);
            sb.append(a);
            sb.append(hxF);
        }

        sb.append(a);
        sb.append("}").append(a);
        sb.append(a);
        sb.append("private void initializeLogic() {").append(a);
        if (o.length() > 0) {
            sb.append(o).append(a);
        }
        sb.append("}").append(a);

        String agusComponentsOnActivityResultCode = CodeResult.c(f.x);
        String onActivityResultLogic = sourceHandler.activityResult(f, projectFileBean.getActivityName());
        String onActivityResultSwitchLogic = e.a();
        if (!agusComponentsOnActivityResultCode.isEmpty() || !onActivityResultLogic.isEmpty() || !onActivityResultSwitchLogic.isEmpty()) {
            sb.append(a);
            sb.append("@Override").append(a);
            if (isFragment) {
                sb.append("public");
            } else {
                sb.append("protected");
            }
            sb.append(" void onActivityResult(int _requestCode, int _resultCode, Intent _data) {").append(a);
            sb.append(agusComponentsOnActivityResultCode);
            sb.append("super.onActivityResult(_requestCode, _resultCode, _data);").append(a);
            sb.append(onActivityResultLogic).append(a);
            sb.append("switch (_requestCode) {").append(a);
            sb.append(onActivityResultSwitchLogic).append(a);
            sb.append("default:").append(a);
            sb.append("break;").append(a);
            sb.append("}").append(a);
            sb.append("}").append(a);
        }

        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
            e.a("onBackPressed", "DrawerLayout", "_drawer");
        }

        ArrayList<ViewBean> beans = projectDataManager.d(projectFileBean.getXmlName());
        for (ViewBean next : beans) {
            if (next.type == ViewBean.VIEW_TYPE_WIDGET_MAPVIEW) {
                e.a("onStart", "MapView", next.id);
                e.a("onResume", "MapView", next.id);
                e.a("onPause", "MapView", next.id);
                e.a("onStop", "MapView", next.id);
                e.a("onDestroy", "MapView", next.id);
            }
            if (next.type == ViewBean.VIEW_TYPE_WIDGET_ADVIEW) {
                e.a("onResume", "AdView", next.id);
                e.a("onPause", "AdView", next.id);
                e.a("onDestroy", "AdView", next.id);
            }
        }
        if (e.k.length() > 0) {
            sb.append(a);
            sb.append(e.k).append(a);
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

        for (String moreBlocksCode : p) {
            sb.append(a);
            sb.append(moreBlocksCode).append(a);
        }

        sb.append(a);
        for (int j = 0, qSize = q.size(); j < qSize; j++) {
            String adapterCode = q.get(j);

            if (base.contains("public CharSequence onTabLayoutNewTabAdded(int _position) {")
                    || !adapterCode.contains("return onTabLayoutNewTabAdded(pos);")) {
                sb.append(adapterCode);
            } else {
                sb.append(adapterCode.replace("return onTabLayoutNewTabAdded(pos);",
                        "// Use the Activity Event (onTabLayoutNewTabAdded) in order to use this method\r\n" +
                                "return \"page \" + String.valueOf(pos);"));
            }
            if (j != qSize - 1) {
                sb.append(a);
            }
        }
        if (!isFragment && !settings.getValue(ProjectSettings.SETTING_DISABLE_OLD_METHODS, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            sb.append(getDeprecatedMethodsCode());
        }
        sb.append("}").append(a);
        String code = sb.toString();

        if (isFragment) {
            code = code.replaceAll("getApplicationContext\\(\\)", "getContext().getApplicationContext()")
                    .replaceAll("getBaseContext\\(\\)", "getActivity().getBaseContext()")
                    .replaceAll("\\(ClipboardManager\\) getSystemService", "(ClipboardManager) getContext().getSystemService")
                    .replaceAll("\\(Vibrator\\) getSystemService", "(Vibrator) getContext().getSystemService")
                    .replaceAll("\\(SensorManager\\) getSystemService", "(SensorManager) getContext().getSystemService")
                    .replaceAll("Typeface.createFromAsset\\(getAssets\\(\\)", "Typeface.createFromAsset(getContext().getAssets()")
                    .replaceAll("= getAssets\\(\\).open", "= getContext().getAssets().open")
                    .replaceAll("getSharedPreferences", "getContext().getSharedPreferences")
                    .replaceAll("AlertDialog.Builder\\(this\\);", "AlertDialog.Builder(getActivity());")
                    .replaceAll("SpeechRecognizer.createSpeechRecognizer\\(this\\);", "SpeechRecognizer.createSpeechRecognizer(getContext());")
                    .replaceAll("new RequestNetwork\\(this\\);", "new RequestNetwork((Activity) getContext());")
                    .replaceAll("new BluetoothConnect\\(this\\);", "new BluetoothConnect((Activity) getContext());")
                    .replaceAll("MobileAds.getRewardedVideoAdInstance\\(this\\);", "MobileAds.getRewardedVideoAdInstance(getContext());")
                    .replaceAll("runOnUiThread\\(new", "getActivity().runOnUiThread(new")
                    .replaceAll(".setLayoutManager\\(new LinearLayoutManager\\(this", ".setLayoutManager(new LinearLayoutManager(getContext()")
                    .replaceAll("getLayoutInflater\\(\\)", "getActivity().getLayoutInflater()");
        }
        if (f.g) {
            code = code.replaceAll("getFragmentManager", "getSupportFragmentManager");
        }

        return CommandBlock.CB(Lx.j(code));
    }

    private String getListDeclarationAndAddImports(int listType, String listName) {
        String typeName = mq.b(listType);
        addImports(mq.getImportsByTypeName(typeName));
        return Lx.a(typeName, listName, Lx.AccessModifier.PRIVATE);
    }

    private String getComponentDeclarationAndAddImports(ComponentBean componentBean) {
        String typeName = mq.a(componentBean.type);
        addImports(mq.getImportsByTypeName(typeName));
        return Lx.a(typeName, componentBean.componentId, Lx.AccessModifier.PRIVATE, componentBean.param1, componentBean.param2, componentBean.param3);
    }

    private String getDrawerViewDeclarationAndAddImports(ViewBean viewBean) {
        String viewType = viewBean.convert.replaceAll("\\w*\\..*\\.", "");
        if (viewType.equals("")) {
            viewType = viewBean.getClassInfo().a();
        }
        addImports(mq.getImportsByTypeName(viewType));
        return Lx.a(viewType, "_drawer_" + viewBean.id, Lx.AccessModifier.PRIVATE);
    }

    /**
     * @return Definition line for a Variable
     */
    private String getVariableDeclarationAndAddImports(int variableType, String name) {
        String variableTypeName = mq.c(variableType);
        addImports(mq.getImportsByTypeName(variableTypeName));
        return Lx.a(variableTypeName, name, Lx.AccessModifier.PRIVATE);
    }

    private String getViewDeclarationAndAddImports(ViewBean viewBean) {
        String viewType = viewBean.convert.replaceAll("\\w*\\..*\\.", "");
        if (viewType.equals("")) {
            viewType = viewBean.getClassInfo().a();
        }
        addImports(mq.getImportsByTypeName(viewType));
        return Lx.a(viewType, viewBean.id, Lx.AccessModifier.PRIVATE);
    }

    private String getDeprecatedMethodsCode() {
        return a +
                "@Deprecated" + a +
                "public void showMessage(String _s) {" + a +
                "Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();" + a +
                "}" + a +
                a +
                "@Deprecated" + a +
                "public int getLocationX(View _v) {" + a +
                "int _location[] = new int[2];" + a +
                "_v.getLocationInWindow(_location);" + a +
                "return _location[0];" + a +
                "}" + a +
                a +
                "@Deprecated" + a +
                "public int getLocationY(View _v) {" + a +
                "int _location[] = new int[2];" + a +
                "_v.getLocationInWindow(_location);" + a +
                "return _location[1];" + a +
                "}" + a +
                a +
                "@Deprecated" + a +
                "public int getRandom(int _min, int _max) {" + a +
                "Random random = new Random();" + a +
                "return random.nextInt(_max - _min + 1) + _min;" + a +
                "}" + a +
                a +
                "@Deprecated" + a +
                "public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {" + a +
                "ArrayList<Double> _result = new ArrayList<Double>();" + a +
                "SparseBooleanArray _arr = _list.getCheckedItemPositions();" + a +
                "for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {" + a +
                "if (_arr.valueAt(_iIdx))" + a +
                "_result.add((double)_arr.keyAt(_iIdx));" + a +
                "}" + a +
                "return _result;" + a +
                "}" + a +
                a +
                "@Deprecated" + a +
                "public float getDip(int _input) {" + a +
                "return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());" + a +
                "}" + a +
                a +
                "@Deprecated" + a +
                "public int getDisplayWidthPixels() {" + a +
                "return getResources().getDisplayMetrics().widthPixels;" + a +
                "}" + a +
                a +
                "@Deprecated" + a +
                "public int getDisplayHeightPixels() {" + a +
                "return getResources().getDisplayMetrics().heightPixels;" + a +
                "}" + a;
    }

    private void addImport(String classToImport) {
        if (!g.contains(classToImport)) {
            g.add(classToImport);
        }
    }

    private void addImports(ArrayList<String> imports) {
        if (imports != null) {
            for (String value : imports) {
                addImport(value);
            }
        }
    }

    /**
     * @see Lx#b(String, String, String...)
     */
    private String getComponentBeanInitializer(ComponentBean componentBean) {
        return Lx.b(mq.a(componentBean.type), componentBean.componentId, componentBean.param1, componentBean.param2, componentBean.param3);
    }

    private void handleAppCompat() {
        if (f.g) {
            addImport("androidx.appcompat.app.AppCompatActivity");
            addImport("androidx.annotation.*");
        } else {
            addImport("android.app.Activity");
        }
        if (f.g) {
            if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR) && !projectFileBean.fileName.contains("_fragment")) {
                addImport("androidx.appcompat.widget.Toolbar");
                addImport("androidx.coordinatorlayout.widget.CoordinatorLayout");
                addImport("com.google.android.material.appbar.AppBarLayout");

                i.add("private Toolbar _toolbar;");
                i.add("private AppBarLayout _app_bar;");
                i.add("private CoordinatorLayout _coordinator;");
                m.add(
                        "_app_bar = findViewById(R.id._app_bar);" + a +
                                "_coordinator = findViewById(R.id._coordinator);" + a +
                                "_toolbar = findViewById(R.id._toolbar);" + a +
                                "setSupportActionBar(_toolbar);" + a +
                                "getSupportActionBar().setDisplayHomeAsUpEnabled(true);" + a +
                                "getSupportActionBar().setHomeButtonEnabled(true);" + a +
                                "_toolbar.setNavigationOnClickListener(new View.OnClickListener() {" + a +
                                "@Override" + a +
                                "public void onClick(View _v) {" + a +
                                "onBackPressed();" + a +
                                "}" + a +
                                "});"
                );
            }
            if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                addImport("com.google.android.material.floatingactionbutton.FloatingActionButton");

                i.add("private FloatingActionButton _fab;");
                m.add(
                        (projectFileBean.fileName.contains("_fragment") ?
                                "_fab = _view.findViewById(R.id._fab);" :
                                "_fab = findViewById(R.id._fab);") + a
                );
            }
            if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER) && !projectFileBean.fileName.contains("_fragment")) {
                addImport("androidx.core.view.GravityCompat");
                addImport("androidx.drawerlayout.widget.DrawerLayout");
                addImport("androidx.appcompat.app.ActionBarDrawerToggle");

                i.add("private DrawerLayout _drawer;");
                m.add("_drawer = findViewById(R.id._drawer);" + a +
                        "ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(" +
                        projectFileBean.getActivityName() + ".this, _drawer, " +

                        (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR) ?
                                "_toolbar, " : "") +

                        "R.string.app_name, R.string.app_name);" + a +
                        "_drawer.addDrawerListener(_toggle);" + a +
                        "_toggle.syncState();" + a +
                        a +
                        "LinearLayout _nav_view = findViewById(R.id._nav_view);" + a
                );
                addImports(mq.getImportsByTypeName("LinearLayout"));
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
        o = new Fx(projectFileBean.getActivityName(), f, "onCreate_initializeLogic", projectDataManager.a(projectFileBean.getJavaName(), "onCreate_initializeLogic")).a();
    }

    private String getDrawerViewInitializer(ViewBean viewBean) {
        String replaceAll = viewBean.convert.replaceAll("\\w*\\..*\\.", "");
        if (replaceAll.equals("")) {
            replaceAll = viewBean.getClassInfo().a();
        }
        return Lx.getDrawerViewInitializer(replaceAll, viewBean.id, "_nav_view");
    }

    public final void c() {
        for (ViewBean viewBean : projectDataManager.f(projectFileBean.getXmlName())) {
            String xmlName = ProjectFileBean.getXmlName(viewBean.customView);
            this.projectFileBean.getJavaName();
            String eventName = viewBean.id + "_onBindCustomView";
            String adapterLogic = new Fx(projectFileBean.getActivityName(), f, eventName, projectDataManager.a(projectFileBean.getJavaName(), eventName)).a();
            String adapterCode;
            if (viewBean.type == ViewBeans.VIEW_TYPE_LAYOUT_VIEWPAGER) {
                adapterCode = Lx.pagerAdapter(viewBean.id, viewBean.customView, projectDataManager.d(xmlName), adapterLogic);
            } else if (viewBean.type == ViewBeans.VIEW_TYPE_WIDGET_RECYCLERVIEW) {
                adapterCode = Lx.recyclerViewAdapter(viewBean.id, viewBean.customView, projectDataManager.d(xmlName), adapterLogic);
            } else {
                adapterCode = Lx.a(viewBean.id, viewBean.customView, projectDataManager.d(xmlName), adapterLogic);
            }
            q.add(adapterCode);
        }
    }

    private String getViewInitializer(ViewBean viewBean) {
        String replaceAll = viewBean.convert.replaceAll("\\w*\\..*\\.", "");
        if (replaceAll.equals("")) {
            replaceAll = viewBean.getClassInfo().a();
        }
        if (projectFileBean.fileName.contains("_fragment")) {
            return Lx.getViewInitializer(replaceAll, viewBean.id, true);
        }
        return Lx.getViewInitializer(replaceAll, viewBean.id, false);
    }

    /**
     * Handles the Activity's More Blocks and adds them to {@link Jx#p}.
     */
    public final void d() {
        String javaName = this.projectFileBean.getJavaName();
        ArrayList<Pair<String, String>> pairs = projectDataManager.i(javaName);
        for (int index = 0, pairsSize = pairs.size(); index < pairsSize; index++) {
            Pair<String, String> next = pairs.get(index);
            String name = next.first + "_moreBlock";
            String code = Lx.getMoreBlockCode(next.first, next.second, new Fx(projectFileBean.getActivityName(), f, name, projectDataManager.a(javaName, name)).a());
            if (index < (pairsSize - 1)) {
                p.add(code);
            } else {
                // Removes unnecessary newline at end of More Block code
                p.add(code.substring(0, code.length() - 2));
            }
        }
    }

    public final void e() {
        e = new Hx(f, projectFileBean, projectDataManager);
        addImports(e.e());
    }

    /**
     * Adds imports for blocks used in the currently generated Activity.
     */
    public final void f() {
        for (Map.Entry<String, ArrayList<BlockBean>> entry : projectDataManager.b(projectFileBean.getJavaName()).entrySet()) {
            for (BlockBean blockBean : entry.getValue()) {
                switch (blockBean.opCode) {
                    case "toStringWithDecimal":
                    case "toStringFormat":
                        addImport("java.text.DecimalFormat");
                        break;

                    case "strToListMap":
                    case "strToListStr":
                    case "strToMap":
                    case "GsonStringToListString":
                    case "GsonStringToListNumber":
                        addImport("com.google.gson.Gson");
                        addImport("com.google.gson.reflect.TypeToken");
                        break;

                    case "mapToStr":
                    case "listMapToStr":
                    case "GsonListTojsonString":
                        addImport("com.google.gson.Gson");
                        break;

                    case "setTypeface":
                        addImport("android.graphics.Typeface");
                        break;

                    case "copyToClipboard":
                        addImport("android.content.ClipData");
                        addImport("android.content.ClipboardManager");
                        break;

                    case "fileutilGetLastSegmentPath":
                        addImport("android.net.Uri");
                        break;

                    case "setImageUrl":
                        addImport("com.bumptech.glide.Glide");
                        break;

                    case "interstitialAdLoad":
                        addImport("com.google.android.gms.ads.AdRequest");
                        addImport("com.google.android.gms.ads.LoadAdError");
                        break;
                }
            }
        }
    }

    /**
     * Handles the Activity's Drawer Views and Components
     */
    public final void g() {
        ArrayList<ViewBean> viewBeans = projectDataManager.d(projectFileBean.getXmlName());
        for (ViewBean viewBean : viewBeans) {
            m.add(getViewInitializer(viewBean));
        }
        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
            ArrayList<ViewBean> drawerBeans = projectDataManager.d(projectFileBean.getDrawerXmlName());
            for (ViewBean viewBean : drawerBeans) {
                m.add(getDrawerViewInitializer(viewBean));
            }
        }
        ArrayList<ComponentBean> componentBeans = projectDataManager.e(projectFileBean.getJavaName());
        for (ComponentBean componentBean : componentBeans) {
            n.add(getComponentBeanInitializer(componentBean));
        }
    }

    /**
     * Handles the file's request code constants.
     */
    public final void h() {
        int startValue = 100;
        for (ComponentBean next : projectDataManager.e(projectFileBean.getJavaName())) {
            switch (next.type) {
                case ComponentBean.COMPONENT_TYPE_CAMERA:
                case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                case 31:
                    int incrementedValue = startValue + 1;
                    r.add(Lx.a(next.componentId, incrementedValue));
                    startValue = incrementedValue;
                    break;
            }
        }
    }

    public final void i() {
        String javaName = projectFileBean.getJavaName();
        for (Pair<Integer, String> next : projectDataManager.k(javaName)) {
            int intValue = next.first;
            String str = next.second;
            if (intValue == 9) {
                addImport(str);
            } else {
                i.add(getVariableDeclarationAndAddImports(intValue, str));
            }
        }
        for (Pair<Integer, String> next2 : projectDataManager.j(javaName)) {
            j.add(getListDeclarationAndAddImports(next2.first, next2.second));
        }
        for (ViewBean viewBean : projectDataManager.d(projectFileBean.getXmlName())) {
            k.add(getViewDeclarationAndAddImports(viewBean));
        }
        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
            for (ViewBean viewBean : projectDataManager.d(projectFileBean.getDrawerXmlName())) {
                k.add(getDrawerViewDeclarationAndAddImports(viewBean));
            }
        }
        ArrayList<ComponentBean> componentBeans = projectDataManager.e(javaName);
        for (ComponentBean bean : componentBeans) {
            l.add(getComponentDeclarationAndAddImports(bean));
        }
        if (componentBeans.size() > 0) {
            boolean hasTimer = false;
            boolean hasFirebaseDB = false;
            boolean hasFirebaseStorage = false;
            boolean hasInterstitialAd = false;
            for (ComponentBean bean : componentBeans) {
                if (bean.type == ComponentBean.COMPONENT_TYPE_TIMERTASK) {
                    hasTimer = true;
                }
                if (bean.type == ComponentBean.COMPONENT_TYPE_FIREBASE) {
                    hasFirebaseDB = true;
                }
                if (bean.type == ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE) {
                    hasFirebaseStorage = true;
                }
                if (bean.type == ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD) {
                    hasInterstitialAd = true;
                }
            }
            if (hasTimer) {
                h.add(Lx.d("Timer"));
            }
            if (hasFirebaseDB) {
                h.add(Lx.d("FirebaseDB"));
            }
            if (hasFirebaseStorage) {
                h.add(Lx.d("FirebaseStorage"));
            }
            if (hasInterstitialAd) {
                h.add(Lx.d("InterstitialAd"));
            }
        }
    }

    /**
     * Adds Local libraries' imports
     */
    public final void j() {
        for (String value : mll.getImportLocalLibrary()) {
            addImport(value);
        }
    }
}
