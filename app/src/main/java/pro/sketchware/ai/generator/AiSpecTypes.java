package pro.sketchware.ai.generator;

import java.util.Map;
import java.util.Set;

/**
 * Maps the type names a model may use (see {@link AiProjectPrompt}) to Sketchware's own.
 */
final class AiSpecTypes {

    /**
     * Variable type name to Sketchware's variable type ID, see <code>a.a.a.mq#c(int)</code>.
     */
    static final Map<String, Integer> VARIABLES = Map.of(
            "boolean", 0,
            "number", 1,
            "String", 2,
            "Map", 3);

    /**
     * List type name to Sketchware's list type ID, see <code>a.a.a.mq#b(int)</code>.
     */
    static final Map<String, Integer> LISTS = Map.of(
            "ListNumber", 1,
            "ListString", 2,
            "ListMap", 3);

    /**
     * Component type name to the type name
     * <code>ComponentBean#getComponentTypeByTypeName(String)</code> understands.
     */
    static final Map<String, String> COMPONENTS = Map.of(
            "Intent", "Intent",
            "SharedPreferences", "File",
            "Calendar", "Calendar",
            "Vibrator", "Vibrator",
            "Dialog", "Dialog",
            "Timer", "Timer");

    /**
     * Method parameter type name to its More Block spec placeholder.
     */
    static final Map<String, String> METHOD_PARAMETERS = Map.of(
            "String", "%s",
            "number", "%d",
            "boolean", "%b");

    /**
     * The Activity lifecycle events a screen can handle, besides onCreate.
     */
    static final Set<String> ACTIVITY_EVENTS = Set.of(
            "onBackPressed", "onStart", "onResume", "onPause", "onStop", "onDestroy");

    private AiSpecTypes() {
    }
}
