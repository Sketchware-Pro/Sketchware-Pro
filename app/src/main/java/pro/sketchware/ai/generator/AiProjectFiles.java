package pro.sketchware.ai.generator;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.ProjectFileBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Lists what the AI created for a project as files: one layout per screen and item layout, and
 * one file with the logic of each screen.
 * <p>
 * The logic files show what the screen's blocks hold, not the class Sketchware generates from
 * them when building. Generating that class needs the editor's shared project state, which must
 * not be touched while the user may have another project open.
 */
public final class AiProjectFiles {

    private static final Map<String, String> JAVA_VARIABLE_TYPES = Map.of(
            "boolean", "boolean",
            "number", "double",
            "String", "String",
            "Map", "HashMap<String, Object>");

    private static final Map<String, String> JAVA_LIST_TYPES = Map.of(
            "ListNumber", "ArrayList<Double>",
            "ListString", "ArrayList<String>",
            "ListMap", "ArrayList<HashMap<String, Object>>");

    private static final Map<String, String> JAVA_PARAMETER_TYPES = Map.of(
            "String", "String",
            "number", "double",
            "boolean", "boolean");

    private AiProjectFiles() {
    }

    @NonNull
    public static List<GeneratedFile> describe(@NonNull AiProjectSpec spec) {
        List<GeneratedFile> files = new ArrayList<>();
        for (AiProjectSpec.Screen screen : spec.screens) {
            String activityName = ProjectFileBean.getActivityName(screen.name);
            files.add(new GeneratedFile(ProjectFileBean.getXmlName(screen.name),
                    "Layout of " + activityName, CodeFormatter.formatXml(screen.layout)));
            files.add(new GeneratedFile(ProjectFileBean.getJavaName(screen.name),
                    "Logic of " + activityName, describeLogic(screen)));
        }
        for (AiProjectSpec.ItemLayout itemLayout : spec.itemLayouts) {
            files.add(new GeneratedFile(ProjectFileBean.getXmlName(itemLayout.name),
                    "List item layout", CodeFormatter.formatXml(itemLayout.layout)));
        }
        return files;
    }

    private static String describeLogic(AiProjectSpec.Screen screen) {
        StringBuilder logic = new StringBuilder();

        if (!screen.variables.isEmpty() || !screen.lists.isEmpty() || !screen.components.isEmpty()) {
            logic.append("// Fields\n");
            for (AiProjectSpec.Field variable : screen.variables) {
                logic.append(JAVA_VARIABLE_TYPES.get(variable.type)).append(' ').append(variable.name).append(";\n");
            }
            for (AiProjectSpec.Field list : screen.lists) {
                logic.append(JAVA_LIST_TYPES.get(list.type)).append(' ').append(list.name).append(";\n");
            }
            for (AiProjectSpec.Component component : screen.components) {
                logic.append(component.type).append(' ').append(component.name).append(";\n");
            }
            logic.append('\n');
        }

        appendBlock(logic, "onCreate", screen.onCreate);
        for (AiProjectSpec.Event event : screen.events) {
            String title = AiProjectPrompt.TARGET_ACTIVITY.equals(event.target)
                    ? event.name
                    : event.target + " " + event.name;
            appendBlock(logic, title, event.code);
        }
        for (AiProjectSpec.Method method : screen.methods) {
            List<String> parameters = new ArrayList<>();
            for (AiProjectSpec.Field parameter : method.params) {
                parameters.add(JAVA_PARAMETER_TYPES.get(parameter.type) + " _" + parameter.name);
            }
            appendBlock(logic, "void _" + method.name + "(" + String.join(", ", parameters) + ")", method.code);
        }
        return logic.toString().trim();
    }

    private static void appendBlock(StringBuilder logic, String title, String code) {
        if (code.trim().isEmpty()) {
            return;
        }
        logic.append("// ").append(title).append('\n')
                .append(CodeFormatter.formatJava(code)).append("\n\n");
    }
}
