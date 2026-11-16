package pro.sketchware.ai.generator;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.Fx;
import a.a.a.Ox;
import a.a.a.eC;
import a.a.a.hC;
import a.a.a.iC;
import a.a.a.lC;
import a.a.a.yB;
import a.a.a.yq;
import mod.hey.studios.util.ProjectFile;

/**
 * Reads an existing project into an {@link AiProjectSpec}, so a model can change it.
 * <p>
 * Everything is read from the project's saved files through fresh manager instances, never
 * through the editor's shared ones, so this is safe while another project is open. Layouts and
 * logic come out of Sketchware's own generators, so blocks of any kind are read as the Java they
 * stand for.
 * <p>
 * Whatever the spec can't express is listed in {@link Result#getUnsupported()}: changing the
 * project with AI won't carry it over.
 */
public class AiProjectReader {

    private static final String DRAWER_PREFIX = "_drawer_";
    private static final String FRAGMENT_MARKER = "fragment";
    private static final String ON_CREATE_KEY = "onCreate_initializeLogic";
    private static final String ON_CREATE_EVENT = "initializeLogic";
    private static final String MORE_BLOCK_KEY_SUFFIX = "_moreBlock";
    private static final Pattern MORE_BLOCK_PARAMETER = Pattern.compile("%([sdbm])(?:\\.[\\w.]*)?\\.(\\w+)");

    private final Context context;

    public AiProjectReader(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    public Result read(@NonNull String sc_id) throws AiSpecException {
        HashMap<String, Object> metadata = lC.b(sc_id);
        if (metadata == null) {
            throw new AiSpecException("Project " + sc_id + " doesn't exist anymore.");
        }

        hC fileManager = new hC(sc_id);
        fileManager.i();
        eC dataManager = new eC(sc_id);
        dataManager.g();
        dataManager.e();
        iC libraryManager = new iC(sc_id);
        libraryManager.i();
        yq sourceGenerator = new yq(context, sc_id);
        sourceGenerator.a(libraryManager, fileManager, dataManager);

        AiProjectSpec spec = new AiProjectSpec();
        spec.appName = yB.c(metadata, "my_app_name");
        spec.projectName = yB.c(metadata, "my_ws_name");
        spec.packageName = yB.c(metadata, "my_sc_pkg_name");
        spec.colors = new AiProjectSpec.Colors();
        spec.colors.colorPrimary = hexColor(metadata, ProjectFile.COLOR_PRIMARY);
        spec.colors.colorPrimaryDark = hexColor(metadata, ProjectFile.COLOR_PRIMARY_DARK);
        spec.colors.colorAccent = hexColor(metadata, ProjectFile.COLOR_ACCENT);

        List<String> unsupported = new ArrayList<>();
        for (ProjectFileBean file : fileManager.b()) {
            if (file.fileName.contains(FRAGMENT_MARKER)) {
                // Kept as they are; AI changes only touch Activities
                continue;
            }
            spec.screens.add(readScreen(sourceGenerator, dataManager, file, unsupported));
        }
        for (ProjectFileBean customView : fileManager.c()) {
            if (customView.fileName.startsWith(DRAWER_PREFIX)) {
                continue;
            }
            AiProjectSpec.ItemLayout itemLayout = new AiProjectSpec.ItemLayout();
            itemLayout.name = customView.fileName;
            itemLayout.layout = layoutXml(sourceGenerator, dataManager, customView);
            spec.itemLayouts.add(itemLayout);
        }
        return new Result(sc_id, spec, unsupported);
    }

    private AiProjectSpec.Screen readScreen(yq sourceGenerator, eC dataManager, ProjectFileBean file, List<String> unsupported) {
        String java = file.getJavaName();
        String activity = file.getActivityName();
        AiProjectSpec.Screen screen = new AiProjectSpec.Screen();
        screen.name = file.fileName;
        screen.toolbar = file.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR);
        screen.fab = file.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB);
        screen.layout = layoutXml(sourceGenerator, dataManager, file);
        if (file.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
            unsupported.add("the navigation drawer of " + activity);
        }

        for (Pair<Integer, String> variable : dataManager.k(java)) {
            String type = keyOf(AiSpecTypes.VARIABLES, variable.first);
            if (type == null) {
                unsupported.add("the custom variable \"" + variable.second + "\" of " + activity);
            } else {
                screen.variables.add(field(type, variable.second));
            }
        }
        for (Pair<Integer, String> list : dataManager.j(java)) {
            String type = keyOf(AiSpecTypes.LISTS, list.first);
            if (type == null) {
                unsupported.add("the list \"" + list.second + "\" of " + activity);
            } else {
                screen.lists.add(field(type, list.second));
            }
        }
        for (ComponentBean component : dataManager.e(java)) {
            String type = keyOf(AiSpecTypes.COMPONENTS, ComponentBean.getComponentTypeName(component.type));
            if (type == null) {
                unsupported.add("the " + ComponentBean.getComponentTypeName(component.type) + " component \"" + component.componentId + "\" of " + activity);
                continue;
            }
            AiProjectSpec.Component specComponent = new AiProjectSpec.Component();
            specComponent.type = type;
            specComponent.name = component.componentId;
            specComponent.param = component.param1 == null ? "" : component.param1;
            screen.components.add(specComponent);
        }

        screen.onCreate = code(sourceGenerator, dataManager, activity, java, ON_CREATE_KEY);
        for (EventBean event : dataManager.g(java)) {
            readEvent(sourceGenerator, dataManager, activity, java, event, screen, unsupported);
        }
        for (Pair<String, String> moreBlock : dataManager.i(java)) {
            AiProjectSpec.Method method = readMethod(moreBlock.first, moreBlock.second);
            if (method == null) {
                unsupported.add("the More Block \"" + moreBlock.first + "\" of " + activity);
                continue;
            }
            method.code = code(sourceGenerator, dataManager, activity, java, moreBlock.first + MORE_BLOCK_KEY_SUFFIX);
            screen.methods.add(method);
        }
        return screen;
    }

    private void readEvent(yq sourceGenerator, eC dataManager, String activity, String java, EventBean event,
                           AiProjectSpec.Screen screen, List<String> unsupported) {
        String target;
        if (event.eventType == EventBean.EVENT_TYPE_ACTIVITY) {
            if (ON_CREATE_EVENT.equals(event.eventName)) {
                // Read as the screen's onCreate code
                return;
            }
            if (!AiSpecTypes.ACTIVITY_EVENTS.contains(event.eventName)) {
                unsupported.add("the " + event.eventName + " event of " + activity);
                return;
            }
            target = AiProjectPrompt.TARGET_ACTIVITY;
        } else if (event.eventType == EventBean.EVENT_TYPE_VIEW) {
            target = event.targetId;
        } else {
            unsupported.add("the " + event.eventName + " event of \"" + event.targetId + "\" in " + activity);
            return;
        }

        AiProjectSpec.Event specEvent = new AiProjectSpec.Event();
        specEvent.target = target;
        specEvent.name = event.eventName;
        specEvent.code = code(sourceGenerator, dataManager, activity, java, event.getEventKey());
        screen.events.add(specEvent);
    }

    /**
     * @param spec A More Block spec such as <code>loadNotes %s.filter</code>.
     * @return Null for a More Block the spec can't express, e.g. one that returns a value.
     */
    private AiProjectSpec.Method readMethod(String name, String spec) {
        if (name.contains("[") || !name.matches("[A-Za-z]\\w*")) {
            return null;
        }
        AiProjectSpec.Method method = new AiProjectSpec.Method();
        method.name = name;
        Matcher parameters = MORE_BLOCK_PARAMETER.matcher(spec);
        while (parameters.find()) {
            String type = switch (parameters.group(1)) {
                case "s" -> "String";
                case "d" -> "number";
                case "b" -> "boolean";
                default -> null;
            };
            if (type == null) {
                return null;
            }
            method.params.add(field(type, parameters.group(2)));
        }
        return method;
    }

    private String layoutXml(yq sourceGenerator, eC dataManager, ProjectFileBean file) {
        // A copy without toolbar, floating action button or drawer, so that only the user's own
        // views are written, as the spec expects
        ProjectFileBean plain = new ProjectFileBean(file.fileType, file.fileName);
        plain.setActivityOptions(ProjectFileBean.OPTION_ACTIVITY_SHIFT);
        Ox xmlGenerator = new Ox(sourceGenerator.N, plain);
        // Ox reads the floating action button of every Activity, even one that doesn't show it.
        // This creates it in memory only, the data manager is never saved.
        ViewBean fab = file.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY ? dataManager.h(file.getXmlName()) : null;
        xmlGenerator.a(eC.a(dataManager.d(file.getXmlName())), fab);
        return xmlGenerator.b();
    }

    private String code(yq sourceGenerator, eC dataManager, String activity, String java, String key) {
        ArrayList<BlockBean> blocks = dataManager.a(java, key);
        if (blocks == null || blocks.isEmpty()) {
            return "";
        }
        return new Fx(activity, sourceGenerator.N, blocks, false).a();
    }

    private static AiProjectSpec.Field field(String type, String name) {
        AiProjectSpec.Field field = new AiProjectSpec.Field();
        field.type = type;
        field.name = name;
        return field;
    }

    private static <V> String keyOf(Map<String, V> map, V value) {
        for (Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static String hexColor(HashMap<String, Object> metadata, String key) {
        return String.format("#%06X", 0xFFFFFF & yB.a(metadata, key, ProjectFile.getDefaultColor(key)));
    }

    public static final class Result {
        private final String sc_id;
        private final AiProjectSpec spec;
        private final List<String> unsupported;

        private Result(String sc_id, AiProjectSpec spec, List<String> unsupported) {
            this.sc_id = sc_id;
            this.spec = spec;
            this.unsupported = unsupported;
        }

        @NonNull
        public String getScId() {
            return sc_id;
        }

        @NonNull
        public AiProjectSpec getSpec() {
            return spec;
        }

        /**
         * @return Descriptions of what can't be carried over when the project is changed by AI.
         */
        @NonNull
        public List<String> getUnsupported() {
            return unsupported;
        }
    }
}
