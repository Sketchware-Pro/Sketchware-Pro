package pro.sketchware.ai.generator;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Turns a model's raw answer into a validated {@link AiProjectSpec}.
 * <p>
 * Everything that can be repaired without guessing is repaired silently (missing lists, an
 * unusable package name, ...). Everything else is rejected with an {@link AiSpecException} whose
 * message tells the model what to correct.
 */
public class AiResponseParser {

    private static final Pattern RESOURCE_NAME = Pattern.compile("[a-z][a-z0-9_]*");
    private static final Pattern JAVA_IDENTIFIER = Pattern.compile("[A-Za-z][A-Za-z0-9_]*");
    private static final Pattern PACKAGE_NAME = Pattern.compile("[a-z][a-z0-9_]*(\\.[a-z][a-z0-9_]*)+");
    private static final Pattern HEX_COLOR = Pattern.compile("#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})");
    private static final String FALLBACK_APP_NAME = "AI App";
    private static final String FALLBACK_PROJECT_NAME = "AiApp";
    private static final String PACKAGE_PREFIX = "com.ai.";
    private static final int MAX_PROJECT_NAME_LENGTH = 30;

    /**
     * Sketchware generates Fragments instead of Activities for screens with this in their name.
     */
    private static final String FRAGMENT_NAME_MARKER = "fragment";

    private static final Set<String> JAVA_KEYWORDS = Set.of(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile", "while", "true", "false",
            "null");

    private final Gson gson = new Gson();

    /**
     * @return The spec of an app, or one with only {@link AiProjectSpec#reply} set when the
     * model answered in words instead of building something.
     */
    @NonNull
    public AiProjectSpec parse(@NonNull String response) throws AiSpecException {
        if (response.indexOf('{') < 0 && !response.trim().isEmpty()) {
            // Models sometimes answer small talk in plain text, despite being asked for JSON
            AiProjectSpec reply = new AiProjectSpec();
            reply.reply = response.trim();
            return reply;
        }

        AiProjectSpec spec;
        try {
            spec = gson.fromJson(extractJsonObject(response), AiProjectSpec.class);
        } catch (JsonParseException e) {
            throw new AiSpecException("It isn't valid JSON (" + rootMessage(e) + "). Escape quotes, backslashes and line breaks inside JSON strings.", e);
        }
        if (spec == null) {
            throw new AiSpecException("It is empty.");
        }
        if (spec.isReply()) {
            return spec;
        }

        normalizeMetadata(spec);
        spec.permissions = normalizePermissions(spec.permissions);
        spec.screens = nonNull(spec.screens);
        spec.itemLayouts = nonNull(spec.itemLayouts);

        validateScreens(spec);
        validateItemLayouts(spec);
        return spec;
    }

    /**
     * Models like to wrap JSON in markdown fences or a sentence, so only the outermost object is
     * kept.
     */
    private String extractJsonObject(String response) throws AiSpecException {
        int start = response.indexOf('{');
        int end = response.lastIndexOf('}');
        if (start < 0 || end <= start) {
            throw new AiSpecException("It doesn't contain a JSON object.");
        }
        return response.substring(start, end + 1);
    }

    private void normalizeMetadata(AiProjectSpec spec) {
        if (isBlank(spec.appName)) {
            spec.appName = FALLBACK_APP_NAME;
        }
        spec.appName = spec.appName.trim();

        String projectName = (isBlank(spec.projectName) ? spec.appName : spec.projectName).replaceAll("[^A-Za-z0-9]", "");
        if (projectName.isEmpty() || !Character.isLetter(projectName.charAt(0))) {
            projectName = FALLBACK_PROJECT_NAME + projectName;
        }
        if (projectName.length() > MAX_PROJECT_NAME_LENGTH) {
            projectName = projectName.substring(0, MAX_PROJECT_NAME_LENGTH);
        }
        spec.projectName = projectName;

        if (!isValidPackageName(spec.packageName)) {
            spec.packageName = PACKAGE_PREFIX + projectName.toLowerCase(Locale.ROOT);
        }

        if (spec.colors != null) {
            spec.colors.colorPrimary = validColorOrNull(spec.colors.colorPrimary);
            spec.colors.colorPrimaryDark = validColorOrNull(spec.colors.colorPrimaryDark);
            spec.colors.colorAccent = validColorOrNull(spec.colors.colorAccent);
        }
    }

    private boolean isValidPackageName(String packageName) {
        if (packageName == null || !PACKAGE_NAME.matcher(packageName).matches()) {
            return false;
        }
        for (String segment : packageName.split("\\.")) {
            if (JAVA_KEYWORDS.contains(segment)) {
                return false;
            }
        }
        return true;
    }

    private String validColorOrNull(String color) {
        return color != null && HEX_COLOR.matcher(color.trim()).matches() ? color.trim() : null;
    }

    private List<String> normalizePermissions(List<String> permissions) {
        List<String> valid = new ArrayList<>();
        for (String permission : nonNull(permissions)) {
            if (permission != null && permission.trim().contains(".") && !valid.contains(permission.trim())) {
                valid.add(permission.trim());
            }
        }
        return valid;
    }

    private void validateScreens(AiProjectSpec spec) throws AiSpecException {
        if (spec.screens.isEmpty()) {
            throw new AiSpecException("\"screens\" is missing or empty.");
        }

        Set<String> screenNames = new HashSet<>();
        AiProjectSpec.Screen launcher = null;
        for (AiProjectSpec.Screen screen : spec.screens) {
            if (screen == null) {
                throw new AiSpecException("\"screens\" contains null.");
            }
            validateResourceName(screen.name, "screen name");
            if (screen.name.contains(FRAGMENT_NAME_MARKER)) {
                throw new AiSpecException("The screen name \"" + screen.name + "\" must not contain \"" + FRAGMENT_NAME_MARKER + "\".");
            }
            if (!screenNames.add(screen.name)) {
                throw new AiSpecException("The screen name \"" + screen.name + "\" is used twice.");
            }
            if (AiProjectPrompt.LAUNCHER_SCREEN.equals(screen.name)) {
                launcher = screen;
            }
            validateScreen(screen);
        }

        if (launcher == null) {
            throw new AiSpecException("No screen is named \"" + AiProjectPrompt.LAUNCHER_SCREEN + "\". The launcher screen must have that name.");
        }
        spec.screens.remove(launcher);
        spec.screens.add(0, launcher);
    }

    private void validateScreen(AiProjectSpec.Screen screen) throws AiSpecException {
        String where = "Screen \"" + screen.name + "\": ";
        if (isBlank(screen.layout)) {
            throw new AiSpecException(where + "\"layout\" is missing.");
        }
        screen.variables = nonNull(screen.variables);
        screen.lists = nonNull(screen.lists);
        screen.components = nonNull(screen.components);
        screen.events = nonNull(screen.events);
        screen.methods = nonNull(screen.methods);
        screen.onCreate = screen.onCreate == null ? "" : screen.onCreate;

        Set<String> memberNames = new HashSet<>();
        for (AiProjectSpec.Field variable : screen.variables) {
            validateField(variable, AiSpecTypes.VARIABLES, where + "variable", memberNames);
        }
        for (AiProjectSpec.Field list : screen.lists) {
            validateField(list, AiSpecTypes.LISTS, where + "list", memberNames);
        }
        for (AiProjectSpec.Component component : screen.components) {
            if (component == null) {
                throw new AiSpecException(where + "\"components\" contains null.");
            }
            validateMember(component.name, component.type, AiSpecTypes.COMPONENTS, where + "component", memberNames);
            component.param = component.param == null ? "" : component.param.trim();
        }
        for (AiProjectSpec.Method method : screen.methods) {
            validateMethod(method, where, memberNames);
        }
        for (AiProjectSpec.Event event : screen.events) {
            if (event == null || isBlank(event.target) || isBlank(event.name)) {
                throw new AiSpecException(where + "every event needs a \"target\" and a \"name\".");
            }
            event.target = event.target.trim();
            event.name = event.name.trim();
            event.code = event.code == null ? "" : event.code;
        }
        fixMethodCalls(screen);
    }

    /**
     * Adds the underscore Sketchware puts in front of every method name to calls that lack it.
     */
    private void fixMethodCalls(AiProjectSpec.Screen screen) {
        List<String> methodNames = new ArrayList<>();
        for (AiProjectSpec.Method method : screen.methods) {
            methodNames.add(method.name);
        }
        MethodCallFixer fixer = new MethodCallFixer(methodNames);
        screen.onCreate = fixer.fix(screen.onCreate);
        for (AiProjectSpec.Event event : screen.events) {
            event.code = fixer.fix(event.code);
        }
        for (AiProjectSpec.Method method : screen.methods) {
            method.code = fixer.fix(method.code);
        }
    }

    private void validateMethod(AiProjectSpec.Method method, String where, Set<String> memberNames) throws AiSpecException {
        if (method == null) {
            throw new AiSpecException(where + "\"methods\" contains null.");
        }
        validateIdentifier(method.name, where + "method name");
        if (!memberNames.add(method.name)) {
            throw new AiSpecException(where + "the name \"" + method.name + "\" is used twice.");
        }
        method.params = nonNull(method.params);
        method.code = method.code == null ? "" : method.code;

        Set<String> parameterNames = new HashSet<>();
        for (AiProjectSpec.Field parameter : method.params) {
            validateField(parameter, AiSpecTypes.METHOD_PARAMETERS, where + "parameter of method \"" + method.name + "\"", parameterNames);
        }
    }

    private void validateField(AiProjectSpec.Field field, Map<String, ?> allowedTypes, String what, Set<String> usedNames) throws AiSpecException {
        if (field == null) {
            throw new AiSpecException(what + " is null.");
        }
        validateMember(field.name, field.type, allowedTypes, what, usedNames);
    }

    private void validateMember(String name, String type, Map<String, ?> allowedTypes, String what, Set<String> usedNames) throws AiSpecException {
        validateIdentifier(name, what + " name");
        if (type == null || !allowedTypes.containsKey(type)) {
            throw new AiSpecException(what + " \"" + name + "\" has the unsupported type \"" + type + "\". Allowed: " + String.join(", ", sorted(allowedTypes.keySet())) + ".");
        }
        if (!usedNames.add(name)) {
            throw new AiSpecException(what + ": the name \"" + name + "\" is used twice.");
        }
    }

    private void validateItemLayouts(AiProjectSpec spec) throws AiSpecException {
        Set<String> layoutNames = new HashSet<>();
        for (AiProjectSpec.Screen screen : spec.screens) {
            layoutNames.add(screen.name);
        }
        for (AiProjectSpec.ItemLayout itemLayout : spec.itemLayouts) {
            if (itemLayout == null) {
                throw new AiSpecException("\"itemLayouts\" contains null.");
            }
            validateResourceName(itemLayout.name, "item layout name");
            if (!layoutNames.add(itemLayout.name)) {
                throw new AiSpecException("The layout name \"" + itemLayout.name + "\" is used twice.");
            }
            if (isBlank(itemLayout.layout)) {
                throw new AiSpecException("Item layout \"" + itemLayout.name + "\": \"layout\" is missing.");
            }
        }
    }

    private void validateResourceName(String name, String what) throws AiSpecException {
        if (name == null || !RESOURCE_NAME.matcher(name).matches() || JAVA_KEYWORDS.contains(name)) {
            throw new AiSpecException("The " + what + " \"" + name + "\" is invalid. Use lowercase letters, digits and underscores, starting with a letter.");
        }
    }

    private void validateIdentifier(String name, String what) throws AiSpecException {
        if (name == null || !JAVA_IDENTIFIER.matcher(name).matches() || JAVA_KEYWORDS.contains(name)) {
            throw new AiSpecException("The " + what + " \"" + name + "\" is invalid. Use letters, digits and underscores, starting with a letter.");
        }
    }

    private List<String> sorted(Set<String> values) {
        List<String> sorted = new ArrayList<>(values);
        sorted.sort(String::compareTo);
        return sorted;
    }

    private String rootMessage(Throwable throwable) {
        Throwable root = throwable;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root.getMessage();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private <T> List<T> nonNull(List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }
}
