package pro.sketchware.ai.generator;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * The app description a model answers with, as documented to it in {@link AiProjectPrompt}.
 * <p>
 * Instances come out of {@link AiResponseParser}, which guarantees that no list is null, that all
 * names are valid identifiers, and that the first screen is the launcher screen.
 */
public class AiProjectSpec {

    /**
     * Set instead of everything else when the message wasn't a request for an app, e.g. a
     * greeting: the model's answer to show in the chat.
     */
    @SerializedName("reply")
    public String reply;

    @SerializedName("appName")
    public String appName;

    @SerializedName("projectName")
    public String projectName;

    @SerializedName("packageName")
    public String packageName;

    @SerializedName("colors")
    public Colors colors;

    @SerializedName("permissions")
    public List<String> permissions = new ArrayList<>();

    @SerializedName("screens")
    public List<Screen> screens = new ArrayList<>();

    @SerializedName("itemLayouts")
    public List<ItemLayout> itemLayouts = new ArrayList<>();

    /**
     * @return Whether this is an answer in words rather than an app.
     */
    public boolean isReply() {
        return reply != null && !reply.trim().isEmpty() && (screens == null || screens.isEmpty());
    }

    public static class Colors {
        @SerializedName("colorPrimary")
        public String colorPrimary;

        @SerializedName("colorPrimaryDark")
        public String colorPrimaryDark;

        @SerializedName("colorAccent")
        public String colorAccent;
    }

    /**
     * One Activity: its layout, its fields and the code of its events.
     */
    public static class Screen {
        @SerializedName("name")
        public String name;

        @SerializedName("toolbar")
        public boolean toolbar;

        @SerializedName("fab")
        public boolean fab;

        @SerializedName("layout")
        public String layout;

        @SerializedName("variables")
        public List<Field> variables = new ArrayList<>();

        @SerializedName("lists")
        public List<Field> lists = new ArrayList<>();

        @SerializedName("components")
        public List<Component> components = new ArrayList<>();

        @SerializedName("onCreate")
        public String onCreate;

        @SerializedName("events")
        public List<Event> events = new ArrayList<>();

        @SerializedName("methods")
        public List<Method> methods = new ArrayList<>();
    }

    /**
     * A typed name: a variable, a list, or a method parameter.
     */
    public static class Field {
        @SerializedName("type")
        public String type;

        @SerializedName("name")
        public String name;
    }

    public static class Component {
        @SerializedName("type")
        public String type;

        @SerializedName("name")
        public String name;

        @SerializedName("param")
        public String param;
    }

    public static class Event {
        /**
         * A view ID, {@link AiProjectPrompt#TARGET_FAB} or {@link AiProjectPrompt#TARGET_ACTIVITY}.
         */
        @SerializedName("target")
        public String target;

        @SerializedName("name")
        public String name;

        @SerializedName("code")
        public String code;
    }

    public static class Method {
        @SerializedName("name")
        public String name;

        @SerializedName("params")
        public List<Field> params = new ArrayList<>();

        @SerializedName("code")
        public String code;
    }

    /**
     * The layout of one row of a list.
     */
    public static class ItemLayout {
        @SerializedName("name")
        public String name;

        @SerializedName("layout")
        public String layout;
    }
}
