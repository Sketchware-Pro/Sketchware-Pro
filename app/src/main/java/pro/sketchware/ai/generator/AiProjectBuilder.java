package pro.sketchware.ai.generator;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.beans.ViewBean;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import a.a.a.GB;
import a.a.a.eC;
import a.a.a.hC;
import a.a.a.iC;
import a.a.a.lC;
import a.a.a.nB;
import a.a.a.oB;
import a.a.a.oq;
import a.a.a.wq;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.util.Helper;
import mod.hey.studios.util.ProjectFile;
import pro.sketchware.managers.inject.InjectRootLayoutManager;
import pro.sketchware.tools.ViewBeanParser;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileUtil;

/**
 * Writes an {@link AiProjectSpec} to storage as a regular Sketchware project, the same way the
 * editor itself would save it.
 * <p>
 * Layouts become real views that can be edited in the view editor, because they go through the
 * same {@link ViewBeanParser} the XML editor uses. Variables, lists, components, events and More
 * Blocks become the real thing as well; only the code inside events is stored as one
 * "add source directly" block each.
 * <p>
 * Building happens in two phases: everything is resolved and validated in memory first, so that a
 * rejected answer never leaves a half-written project behind.
 */
public class AiProjectBuilder {

    private static final String SOURCE_BLOCK_OPCODE = "addSourceDirectly";
    private static final String SOURCE_BLOCK_SPEC = "add source directly %s.inputOnly";
    private static final String SOURCE_BLOCK_TYPE = " ";
    /**
     * The color <code>a.a.a.kq</code> gives blocks of the operator palette.
     */
    private static final int SOURCE_BLOCK_COLOR = 0xff5cb722;
    private static final String FIRST_BLOCK_ID = "10";

    private static final String ON_CREATE_KEY = "onCreate_initializeLogic";
    private static final String MORE_BLOCK_KEY_SUFFIX = "_moreBlock";
    private static final String EVENT_ON_CLICK = "onClick";
    private static final String EVENT_ON_BIND_CUSTOM_VIEW = "onBindCustomView";
    private static final String NO_CUSTOM_VIEW = "none";
    private static final String COMPONENT_VIBRATOR = "Vibrator";
    private static final String PERMISSION_VIBRATE = "android.permission.VIBRATE";

    private static final String SETUP_NO_BACK_ARROW = "getSupportActionBar().setDisplayHomeAsUpEnabled(false);";
    private static final String SETUP_FAB_ICON_CALL = "_fab.setImageResource(";
    private static final String SETUP_FAB_ICON = SETUP_FAB_ICON_CALL + "android.R.drawable.ic_input_add);";

    /**
     * File names Sketchware treats as Fragments, and the prefix of drawer layouts. Specs describe
     * neither, so updates keep them.
     */
    private static final String FRAGMENT_MARKER = "fragment";
    private static final String DRAWER_PREFIX = "_drawer_";

    private static final String INITIAL_VERSION_CODE = "1";
    private static final String INITIAL_VERSION_NAME = "1.0";
    private static final String REGISTRATION_DATE_FORMAT = "yyyyMMddHHmmss";

    private final Context context;

    public AiProjectBuilder(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * @return The <code>sc_id</code> of the created project.
     * @throws AiSpecException If the spec can't be built. Nothing was written in that case.
     */
    @NonNull
    public String build(@NonNull AiProjectSpec spec) throws AiSpecException {
        Resolved resolved = resolve(spec);
        String sc_id = lC.b();
        try {
            writeNewMetadata(sc_id, spec);
            writeProject(sc_id, spec, resolved);
        } catch (RuntimeException e) {
            lC.a(context, sc_id);
            throw e;
        }
        return sc_id;
    }

    /**
     * Replaces an existing project's screens, item layouts and logic with the spec's. Fragments,
     * drawers, resources, enabled libraries, the app's name and its package are kept. The project
     * is backed up first, see {@link AiProjectBackup}.
     *
     * @throws AiSpecException If the spec can't be built. Nothing was changed in that case.
     */
    public void update(@NonNull String sc_id, @NonNull AiProjectSpec spec) throws AiSpecException {
        Resolved resolved = resolve(spec);
        AiProjectBackup backup = new AiProjectBackup(sc_id);
        backup.create();
        try {
            writeUpdatedMetadata(sc_id, spec);
            writeProject(sc_id, spec, resolved);
        } catch (RuntimeException e) {
            backup.restore();
            throw e;
        }
    }

    private Resolved resolve(AiProjectSpec spec) throws AiSpecException {
        Map<String, ParsedLayout> itemLayouts = new LinkedHashMap<>();
        for (AiProjectSpec.ItemLayout itemLayout : spec.itemLayouts) {
            itemLayouts.put(itemLayout.name, parseLayout(itemLayout.layout, "Item layout \"" + itemLayout.name + "\""));
        }

        List<ResolvedScreen> screens = new ArrayList<>();
        for (AiProjectSpec.Screen screen : spec.screens) {
            screens.add(resolveScreen(screen, itemLayouts));
        }
        return new Resolved(screens, itemLayouts);
    }

    // ---------------------------------------------------------------------------------------
    // Phase 1: resolve and validate
    // ---------------------------------------------------------------------------------------

    private ResolvedScreen resolveScreen(AiProjectSpec.Screen screen, Map<String, ParsedLayout> itemLayouts) throws AiSpecException {
        String where = "Screen \"" + screen.name + "\"";
        ParsedLayout layout = parseLayout(screen.layout, where);

        Map<String, ViewBean> viewsById = new HashMap<>();
        for (ViewBean view : layout.views) {
            viewsById.put(view.id, view);
            if (hasCustomView(view) && !itemLayouts.containsKey(view.customView)) {
                throw new AiSpecException(where + ": the view \"" + view.id + "\" uses the item layout \"" + view.customView + "\", which isn't defined in \"itemLayouts\".");
            }
        }

        // Keyed by event key, so that a model declaring the same event twice doesn't end up with
        // two listeners
        Map<String, ResolvedEvent> events = new LinkedHashMap<>();
        for (AiProjectSpec.Event event : screen.events) {
            ResolvedEvent resolvedEvent = resolveEvent(event, screen, viewsById, where);
            events.put(resolvedEvent.bean.getEventKey(), resolvedEvent);
        }

        // Created last, because resolving events can still turn on the floating action button
        ProjectFileBean file = new ProjectFileBean(ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY, screen.name,
                ProjectFileBean.ORIENTATION_PORTRAIT, ProjectFileBean.KEYBOARD_STATE_UNSPECIFIED,
                screen.toolbar, false, screen.fab, false);
        return new ResolvedScreen(screen, layout, events, file);
    }

    private ResolvedEvent resolveEvent(AiProjectSpec.Event event, AiProjectSpec.Screen screen, Map<String, ViewBean> viewsById, String where) throws AiSpecException {
        if (AiProjectPrompt.TARGET_ACTIVITY.equals(event.target)) {
            if (!AiSpecTypes.ACTIVITY_EVENTS.contains(event.name)) {
                throw new AiSpecException(where + ": \"" + event.name + "\" isn't an activity event. Allowed: " + join(AiSpecTypes.ACTIVITY_EVENTS) + ". Put start-up code in \"onCreate\".");
            }
            return new ResolvedEvent(new EventBean(EventBean.EVENT_TYPE_ACTIVITY, 0, event.name, event.name), event.code);
        }

        if (AiProjectPrompt.TARGET_FAB.equals(event.target)) {
            if (!EVENT_ON_CLICK.equals(event.name)) {
                throw new AiSpecException(where + ": the floating action button only supports the event " + EVENT_ON_CLICK + ".");
            }
            // An event on the button can only mean that the screen is supposed to have one
            screen.fab = true;
            return new ResolvedEvent(new EventBean(EventBean.EVENT_TYPE_VIEW, ViewBean.VIEW_TYPE_WIDGET_FAB, AiProjectPrompt.TARGET_FAB, event.name), event.code);
        }

        ViewBean view = viewsById.get(event.target);
        if (view == null) {
            throw new AiSpecException(where + ": an event targets the view \"" + event.target + "\", but the layout has no view with that ID. IDs in the layout: " + join(viewsById.keySet()) + ".");
        }
        List<String> supportedEvents = Arrays.asList(oq.getEventsForClass(view.getClassInfo()));
        if (!supportedEvents.contains(event.name)) {
            throw new AiSpecException(where + ": the view \"" + view.id + "\" doesn't support the event \"" + event.name + "\". Supported: " + join(supportedEvents) + ". Set other listeners yourself in \"onCreate\".");
        }
        if (EVENT_ON_BIND_CUSTOM_VIEW.equals(event.name) && !hasCustomView(view)) {
            throw new AiSpecException(where + ": the view \"" + view.id + "\" has an " + EVENT_ON_BIND_CUSTOM_VIEW + " event, but no tools:listitem attribute that names its item layout.");
        }
        return new ResolvedEvent(new EventBean(EventBean.EVENT_TYPE_VIEW, view.type, view.id, event.name), event.code);
    }

    private boolean hasCustomView(ViewBean view) {
        return view.customView != null && !view.customView.isEmpty() && !NO_CUSTOM_VIEW.equals(view.customView);
    }

    private ParsedLayout parseLayout(String xml, String where) throws AiSpecException {
        String cleanXml = xml.replaceFirst("^\\s*<\\?xml[^>]*\\?>", "").trim();
        // ViewBeanParser instantiates views to find out what they are, which needs a Looper
        FutureTask<ParsedLayout> task = new FutureTask<>(() -> {
            ViewBeanParser parser = new ViewBeanParser(cleanXml);
            parser.setSkipRoot(true);
            ArrayList<ViewBean> views = parser.parse();
            return new ParsedLayout(views, parser.getRootAttributes());
        });
        if (Looper.myLooper() == Looper.getMainLooper()) {
            task.run();
        } else {
            new Handler(Looper.getMainLooper()).post(task);
        }

        try {
            ParsedLayout layout = task.get();
            if (layout.root == null) {
                throw new AiSpecException(where + ": the layout is empty.");
            }
            return layout;
        } catch (ExecutionException e) {
            throw new AiSpecException(where + ": the layout isn't valid (" + e.getCause().getMessage() + ").", e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AiSpecException(where + ": parsing the layout was interrupted.", e);
        }
    }

    // ---------------------------------------------------------------------------------------
    // Phase 2: write
    // ---------------------------------------------------------------------------------------

    /**
     * Writes everything but the metadata. Works on whatever the project already has saved, which
     * is nothing for a new one, so the same code creates and updates projects.
     */
    private void writeProject(String sc_id, AiProjectSpec spec, Resolved resolved) {
        List<ResolvedScreen> screens = resolved.screens;
        Map<String, ParsedLayout> itemLayouts = resolved.itemLayouts;

        writeSettings(sc_id);
        List<String> removedFiles = writeFiles(sc_id, screens, itemLayouts);
        writeLibraries(sc_id);
        writeViewsAndLogic(sc_id, screens, itemLayouts, removedFiles);
        writeRootLayouts(sc_id, screens, itemLayouts);
        writePermissions(sc_id, spec, screens);

        // Sketchware offers to restore unsaved editor state from here, which would undo this
        new oB().b(wq.a(sc_id));
    }

    /**
     * Mirrors what <code>MyProjectSettingActivity</code> does when the user creates a project.
     */
    private void writeNewMetadata(String sc_id, AiProjectSpec spec) {
        oB fileUtil = new oB();
        fileUtil.f(wq.e() + File.separator + sc_id);
        fileUtil.f(wq.g() + File.separator + sc_id);
        fileUtil.f(wq.t() + File.separator + sc_id);
        fileUtil.f(wq.d() + File.separator + sc_id);

        AiProjectSpec.Colors colors = spec.colors == null ? new AiProjectSpec.Colors() : spec.colors;
        HashMap<String, Object> metadata = new HashMap<>();
        metadata.put("sc_id", sc_id);
        metadata.put("my_sc_pkg_name", spec.packageName);
        metadata.put("my_ws_name", spec.projectName);
        metadata.put("my_app_name", spec.appName);
        metadata.put("my_sc_reg_dt", new nB().a(REGISTRATION_DATE_FORMAT));
        metadata.put("custom_icon", false);
        metadata.put("isIconAdaptive", false);
        metadata.put("sc_ver_code", INITIAL_VERSION_CODE);
        metadata.put("sc_ver_name", INITIAL_VERSION_NAME);
        metadata.put("sketchware_ver", GB.d(context));
        metadata.put(ProjectFile.COLOR_PRIMARY, colorOrDefault(colors.colorPrimary, ProjectFile.COLOR_PRIMARY));
        metadata.put(ProjectFile.COLOR_PRIMARY_DARK, colorOrDefault(colors.colorPrimaryDark, ProjectFile.COLOR_PRIMARY_DARK));
        metadata.put(ProjectFile.COLOR_ACCENT, colorOrDefault(colors.colorAccent, ProjectFile.COLOR_ACCENT));
        metadata.put(ProjectFile.COLOR_CONTROL_HIGHLIGHT, ProjectFile.getDefaultColor(ProjectFile.COLOR_CONTROL_HIGHLIGHT));
        metadata.put(ProjectFile.COLOR_CONTROL_NORMAL, ProjectFile.getDefaultColor(ProjectFile.COLOR_CONTROL_NORMAL));
        lC.a(sc_id, metadata);

        // Clear whatever an earlier project with this ID may have left behind
        wq.a(context, sc_id);
        fileUtil.b(wq.b(sc_id));
    }

    /**
     * Applies the spec's colors. Name and package stay as they are: the user changes those in the
     * project's settings.
     */
    private void writeUpdatedMetadata(String sc_id, AiProjectSpec spec) {
        if (spec.colors == null) {
            return;
        }
        HashMap<String, Object> metadata = lC.b(sc_id);
        if (metadata == null) {
            return;
        }
        metadata.put(ProjectFile.COLOR_PRIMARY, colorOrDefault(spec.colors.colorPrimary, ProjectFile.COLOR_PRIMARY));
        metadata.put(ProjectFile.COLOR_PRIMARY_DARK, colorOrDefault(spec.colors.colorPrimaryDark, ProjectFile.COLOR_PRIMARY_DARK));
        metadata.put(ProjectFile.COLOR_ACCENT, colorOrDefault(spec.colors.colorAccent, ProjectFile.COLOR_ACCENT));
        lC.b(sc_id, metadata);
    }

    private void writeSettings(String sc_id) {
        // The generated code refers to views by their field, not through view binding
        ProjectSettings projectSettings = new ProjectSettings(sc_id);
        projectSettings.setValue(ProjectSettings.SETTING_NEW_XML_COMMAND, ProjectSettings.SETTING_GENERIC_VALUE_TRUE);
        projectSettings.setValue(ProjectSettings.SETTING_ENABLE_VIEWBINDING, ProjectSettings.SETTING_GENERIC_VALUE_FALSE);

        // Models write lambdas no matter what they're told, and those need Java 8 and D8
        BuildSettings buildSettings = new BuildSettings(sc_id);
        buildSettings.setValue(BuildSettings.SETTING_JAVA_VERSION, BuildSettings.SETTING_JAVA_VERSION_1_8);
        buildSettings.setValue(BuildSettings.SETTING_DEXER, BuildSettings.SETTING_DEXER_D8);
    }

    private int colorOrDefault(String hexColor, String colorKey) {
        if (hexColor != null) {
            try {
                return Color.parseColor(hexColor);
            } catch (IllegalArgumentException ignored) {
                // Fall through to the default
            }
        }
        return ProjectFile.getDefaultColor(colorKey);
    }

    /**
     * Makes the spec's screens and item layouts the project's Activities and custom views.
     * Fragments and drawers aren't described by specs, so they're kept.
     *
     * @return The XML and Java names of files that existed before but don't anymore.
     */
    private List<String> writeFiles(String sc_id, List<ResolvedScreen> screens, Map<String, ParsedLayout> itemLayouts) {
        hC fileManager = new hC(sc_id);
        fileManager.i();
        List<String> removedFiles = new ArrayList<>();

        ArrayList<ProjectFileBean> activities = new ArrayList<>();
        for (ResolvedScreen screen : screens) {
            activities.add(screen.file);
        }
        for (ProjectFileBean existing : fileManager.b()) {
            if (existing.fileName.contains(FRAGMENT_MARKER)) {
                activities.add(existing);
            } else {
                ResolvedScreen replacement = findScreen(screens, existing.fileName);
                if (replacement == null) {
                    removedFiles.add(existing.getXmlName());
                    removedFiles.add(existing.getJavaName());
                } else {
                    // Keep what the user set up that specs don't describe
                    replacement.file.orientation = existing.orientation;
                    replacement.file.keyboardSetting = existing.keyboardSetting;
                    replacement.file.theme = existing.theme;
                    if (existing.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                        replacement.file.setActivityOptions(replacement.file.getActivityOptions() | ProjectFileBean.OPTION_ACTIVITY_DRAWER);
                    }
                }
            }
        }

        ArrayList<ProjectFileBean> customViews = new ArrayList<>();
        for (String name : itemLayouts.keySet()) {
            customViews.add(new ProjectFileBean(ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW, name));
        }
        for (ProjectFileBean existing : fileManager.c()) {
            if (existing.fileName.startsWith(DRAWER_PREFIX)) {
                customViews.add(existing);
            } else if (!itemLayouts.containsKey(existing.fileName)) {
                removedFiles.add(existing.getXmlName());
            }
        }

        fileManager.a(activities);
        fileManager.b(customViews);
        fileManager.m();
        return removedFiles;
    }

    private ResolvedScreen findScreen(List<ResolvedScreen> screens, String name) {
        for (ResolvedScreen screen : screens) {
            if (screen.spec.name.equals(name)) {
                return screen;
            }
        }
        return null;
    }

    /**
     * Toolbars, floating action buttons and card views all come from AppCompat and Material.
     * Libraries the user enabled stay enabled.
     */
    private void writeLibraries(String sc_id) {
        iC libraryManager = new iC(sc_id);
        libraryManager.i();
        ProjectLibraryBean appCompat = libraryManager.c();
        appCompat.useYn = ProjectLibraryBean.LIB_USE_Y;
        libraryManager.b(appCompat);
        libraryManager.l();
    }

    private void writeViewsAndLogic(String sc_id, List<ResolvedScreen> screens, Map<String, ParsedLayout> itemLayouts, List<String> removedFiles) {
        eC dataManager = new eC(sc_id);
        dataManager.g();
        dataManager.e();

        for (String removedFile : removedFiles) {
            clearFileData(dataManager, removedFile);
        }
        for (Map.Entry<String, ParsedLayout> itemLayout : itemLayouts.entrySet()) {
            dataManager.c.put(ProjectFileBean.getXmlName(itemLayout.getKey()), itemLayout.getValue().views);
        }

        for (ResolvedScreen screen : screens) {
            String xmlName = screen.file.getXmlName();
            String javaName = screen.file.getJavaName();
            clearFileData(dataManager, xmlName);
            clearFileData(dataManager, javaName);

            dataManager.c.put(xmlName, screen.layout.views);
            if (screen.spec.fab) {
                // Creates the default floating action button of this layout
                dataManager.h(xmlName);
            }

            for (AiProjectSpec.Field variable : screen.spec.variables) {
                dataManager.c(javaName, AiSpecTypes.VARIABLES.get(variable.type), variable.name);
            }
            for (AiProjectSpec.Field list : screen.spec.lists) {
                dataManager.b(javaName, AiSpecTypes.LISTS.get(list.type), list.name);
            }
            for (AiProjectSpec.Component component : screen.spec.components) {
                int type = ComponentBean.getComponentTypeByTypeName(AiSpecTypes.COMPONENTS.get(component.type));
                dataManager.a(javaName, type, component.name, component.param.isEmpty() ? component.name : component.param);
            }
            for (AiProjectSpec.Method method : screen.spec.methods) {
                dataManager.a(javaName, method.name, toMoreBlockSpec(method));
                putCode(dataManager, javaName, method.name + MORE_BLOCK_KEY_SUFFIX, method.code);
            }
            for (ResolvedEvent event : screen.events.values()) {
                dataManager.a(javaName, event.bean);
                putCode(dataManager, javaName, event.bean.getEventKey(), event.code);
            }
            putCode(dataManager, javaName, ON_CREATE_KEY, defaultSetup(screen, screen == screens.get(0)) + screen.spec.onCreate);
        }

        dataManager.j();
    }

    /**
     * Removes everything stored for one file: views and the floating action button for a layout,
     * fields, components, events, More Blocks and blocks for an Activity.
     */
    private void clearFileData(eC dataManager, String fileName) {
        dataManager.c.remove(fileName);
        dataManager.j.remove(fileName);
        dataManager.d.remove(fileName);
        dataManager.e.remove(fileName);
        dataManager.f.remove(fileName);
        dataManager.g.remove(fileName);
        dataManager.h.remove(fileName);
        dataManager.i.remove(fileName);
    }

    /**
     * Fixes what Sketchware's own defaults get wrong for a generated app. Runs before the model's
     * code, so that the model can still override it.
     */
    private String defaultSetup(ResolvedScreen screen, boolean isLauncher) {
        StringBuilder setup = new StringBuilder();
        String existingCode = screen.spec.onCreate;
        if (isLauncher && screen.spec.toolbar && !existingCode.contains(SETUP_NO_BACK_ARROW)) {
            // Sketchware gives every toolbar a back arrow, which closes the app on the first screen
            setup.append(SETUP_NO_BACK_ARROW).append('\n');
        }
        if (screen.spec.fab && !existingCode.contains(SETUP_FAB_ICON_CALL)) {
            // Sketchware's floating action button has no icon
            setup.append(SETUP_FAB_ICON).append('\n');
        }
        return setup.toString();
    }

    /**
     * @return A More Block spec such as <code>loadNotes %s.filter %d.limit</code>.
     */
    private String toMoreBlockSpec(AiProjectSpec.Method method) {
        StringBuilder spec = new StringBuilder(method.name);
        for (AiProjectSpec.Field parameter : method.params) {
            spec.append(' ').append(AiSpecTypes.METHOD_PARAMETERS.get(parameter.type)).append('.').append(parameter.name);
        }
        return spec.toString();
    }

    private void putCode(eC dataManager, String javaName, String key, String code) {
        String trimmedCode = code.replace("\r\n", "\n").trim();
        if (trimmedCode.isEmpty()) {
            return;
        }
        BlockBean block = new BlockBean(FIRST_BLOCK_ID, SOURCE_BLOCK_SPEC, SOURCE_BLOCK_TYPE, SOURCE_BLOCK_OPCODE);
        block.color = SOURCE_BLOCK_COLOR;
        block.parameters.add(trimmedCode);

        ArrayList<BlockBean> blocks = new ArrayList<>();
        blocks.add(block);
        dataManager.a(javaName, key, blocks);
    }

    private void writeRootLayouts(String sc_id, List<ResolvedScreen> screens, Map<String, ParsedLayout> itemLayouts) {
        InjectRootLayoutManager rootLayoutManager = new InjectRootLayoutManager(sc_id);
        for (ResolvedScreen screen : screens) {
            rootLayoutManager.set(screen.file.getXmlName(), InjectRootLayoutManager.toRoot(screen.layout.root));
        }
        for (Map.Entry<String, ParsedLayout> itemLayout : itemLayouts.entrySet()) {
            rootLayoutManager.set(ProjectFileBean.getXmlName(itemLayout.getKey()), InjectRootLayoutManager.toRoot(itemLayout.getValue().root));
        }
    }

    /**
     * Sketchware only detects the permissions its own blocks need, so the ones the generated code
     * needs are declared through the project's permission file.
     */
    private void writePermissions(String sc_id, AiProjectSpec spec, List<ResolvedScreen> screens) {
        String path = new FilePathUtil().getPathPermission(sc_id);
        // Permissions the user added stay
        ArrayList<String> permissions = new ArrayList<>(readPermissions(path));
        List<String> needed = new ArrayList<>(spec.permissions);
        for (ResolvedScreen screen : screens) {
            for (AiProjectSpec.Component component : screen.spec.components) {
                if (COMPONENT_VIBRATOR.equals(component.type)) {
                    needed.add(PERMISSION_VIBRATE);
                }
            }
        }
        boolean changed = false;
        for (String permission : needed) {
            if (!permissions.contains(permission)) {
                permissions.add(permission);
                changed = true;
            }
        }
        if (changed) {
            FileUtil.writeFile(path, new Gson().toJson(permissions));
        }
    }

    private List<String> readPermissions(String path) {
        if (!FileUtil.isExistFile(path)) {
            return new ArrayList<>();
        }
        try {
            List<String> permissions = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_STRING);
            return permissions == null ? new ArrayList<>() : permissions;
        } catch (RuntimeException e) {
            return new ArrayList<>();
        }
    }

    private String join(Iterable<String> values) {
        return String.join(", ", values);
    }

    /**
     * A spec after phase 1: everything checked and parsed, nothing written yet.
     */
    private static class Resolved {
        private final List<ResolvedScreen> screens;
        private final Map<String, ParsedLayout> itemLayouts;

        private Resolved(List<ResolvedScreen> screens, Map<String, ParsedLayout> itemLayouts) {
            this.screens = screens;
            this.itemLayouts = itemLayouts;
        }
    }

    /**
     * A layout's views, and the tag and attributes of its root element.
     */
    private static class ParsedLayout {
        private final ArrayList<ViewBean> views;
        private final Pair<String, Map<String, String>> root;

        private ParsedLayout(ArrayList<ViewBean> views, Pair<String, Map<String, String>> root) {
            this.views = views;
            this.root = root;
        }
    }

    private static class ResolvedEvent {
        private final EventBean bean;
        private final String code;

        private ResolvedEvent(EventBean bean, String code) {
            this.bean = bean;
            this.code = code;
        }
    }

    private static class ResolvedScreen {
        private final AiProjectSpec.Screen spec;
        private final ParsedLayout layout;
        private final Map<String, ResolvedEvent> events;
        private final ProjectFileBean file;

        private ResolvedScreen(AiProjectSpec.Screen spec, ParsedLayout layout, Map<String, ResolvedEvent> events, ProjectFileBean file) {
            this.spec = spec;
            this.layout = layout;
            this.events = events;
            this.file = file;
        }
    }
}
