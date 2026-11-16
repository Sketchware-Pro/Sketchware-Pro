package pro.sketchware.ai.generator;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pro.sketchware.ai.api.AiClient;
import pro.sketchware.ai.api.AiClientFactory;
import pro.sketchware.ai.api.AiException;
import pro.sketchware.ai.api.AiMessage;
import pro.sketchware.ai.config.AiConfiguration;

/**
 * Generates a whole Sketchware project from a description: asks the model, turns its answer into
 * a project, and lets the model correct itself when the answer can't be used.
 * <p>
 * {@link #generate(String, ProgressListener)} blocks for as long as the model takes, so run it on
 * a background thread.
 */
public class AiProjectGenerator {

    /**
     * How many times the model gets to correct an unusable answer before giving up.
     */
    private static final int MAX_REPAIR_ATTEMPTS = 2;

    private final AiClient client;
    private final AiResponseParser parser = new AiResponseParser();
    private final AiProjectBuilder builder;
    private final AiProjectReader reader;
    private final Gson gson = new Gson();

    public AiProjectGenerator(@NonNull Context context, @NonNull AiConfiguration configuration) {
        client = AiClientFactory.create(configuration);
        builder = new AiProjectBuilder(context);
        reader = new AiProjectReader(context);
    }

    /**
     * @param description What the user wants, in their own words.
     * @return The created project.
     * @throws AiException     If the provider couldn't be reached or refused the request.
     * @throws AiSpecException If the model kept answering with something that can't be built.
     */
    @NonNull
    public Result generate(@NonNull String description, @NonNull ProgressListener listener) throws AiException, AiSpecException {
        return run(description, null, List.of(), listener);
    }

    /**
     * Changes an existing project as described. The project is backed up first, see
     * {@link AiProjectBackup}.
     *
     * @param request What to change, in the user's own words.
     * @return The changed project.
     * @throws AiException     If the provider couldn't be reached or refused the request.
     * @throws AiSpecException If the project can't be read, or the model kept answering with
     *                         something that can't be built. The project is unchanged then.
     */
    @NonNull
    public Result edit(@NonNull String sc_id, @NonNull String request, @NonNull ProgressListener listener) throws AiException, AiSpecException {
        listener.onProgress(Stage.READING);
        AiProjectReader.Result current = reader.read(sc_id);
        return run(AiProjectPrompt.edit(gson.toJson(current.getSpec()), request), sc_id, current.getUnsupported(), listener);
    }

    /**
     * @param sc_id The project to update, or null to create a new one.
     */
    private Result run(String firstMessage, String sc_id, List<String> unsupported, ProgressListener listener) throws AiException, AiSpecException {
        List<AiMessage> conversation = new ArrayList<>();
        conversation.add(AiMessage.user(firstMessage));

        AiSpecException lastProblem = null;
        for (int attempt = 0; attempt <= MAX_REPAIR_ATTEMPTS; attempt++) {
            listener.onProgress(attempt == 0 ? Stage.GENERATING : Stage.REPAIRING);
            String answer = client.complete(AiProjectPrompt.system(), conversation);

            try {
                listener.onProgress(Stage.BUILDING);
                AiProjectSpec spec = parser.parse(answer);
                if (spec.isReply()) {
                    return Result.reply(spec.reply.trim());
                }
                if (sc_id == null) {
                    return new Result(builder.build(spec), false, spec, AiProjectFiles.describe(spec), unsupported);
                }
                builder.update(sc_id, spec);
                return new Result(sc_id, true, spec, AiProjectFiles.describe(spec), unsupported);
            } catch (AiSpecException e) {
                lastProblem = e;
                conversation.add(AiMessage.assistant(answer));
                conversation.add(AiMessage.user(AiProjectPrompt.repair(e.getMessage())));
            }
        }
        throw lastProblem;
    }

    public enum Stage {
        /**
         * Reading the project that is to be changed.
         */
        READING,
        /**
         * Waiting for the model's first answer.
         */
        GENERATING,
        /**
         * The last answer was unusable, waiting for a corrected one.
         */
        REPAIRING,
        /**
         * Turning the answer into a project.
         */
        BUILDING
    }

    /**
     * Called on the thread {@link #generate(String, ProgressListener)} runs on.
     */
    public interface ProgressListener {
        void onProgress(@NonNull Stage stage);
    }

    public static final class Result {
        private final String sc_id;
        private final boolean updated;
        private final AiProjectSpec spec;
        private final List<GeneratedFile> files;
        private final List<String> notCarriedOver;
        private final String reply;

        private Result(String sc_id, boolean updated, AiProjectSpec spec, List<GeneratedFile> files, List<String> notCarriedOver) {
            this(sc_id, updated, spec, files, notCarriedOver, null);
        }

        private Result(String sc_id, boolean updated, AiProjectSpec spec, List<GeneratedFile> files,
                       List<String> notCarriedOver, String reply) {
            this.sc_id = sc_id;
            this.updated = updated;
            this.spec = spec;
            this.files = files;
            this.notCarriedOver = notCarriedOver;
            this.reply = reply;
        }

        private static Result reply(String reply) {
            return new Result("", false, new AiProjectSpec(), List.of(), List.of(), reply);
        }

        /**
         * @return Whether the model answered in words, without creating or changing a project.
         */
        public boolean isReply() {
            return reply != null;
        }

        /**
         * @return The model's answer, for {@link #isReply()} results.
         */
        @NonNull
        public String getReply() {
            return reply == null ? "" : reply;
        }

        /**
         * @return Whether an existing project was changed, rather than a new one created.
         */
        public boolean isUpdate() {
            return updated;
        }

        /**
         * @return For updates: what the old project had that the AI couldn't keep.
         */
        @NonNull
        public List<String> getNotCarriedOver() {
            return notCarriedOver;
        }

        @NonNull
        public List<GeneratedFile> getFiles() {
            return files;
        }

        @NonNull
        public String getScId() {
            return sc_id;
        }

        @NonNull
        public AiProjectSpec getSpec() {
            return spec;
        }
    }
}
