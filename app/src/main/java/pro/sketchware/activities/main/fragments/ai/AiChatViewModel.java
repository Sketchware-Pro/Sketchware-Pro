package pro.sketchware.activities.main.fragments.ai;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pro.sketchware.R;
import pro.sketchware.ai.AiTaskRunner;
import pro.sketchware.ai.config.AiConfiguration;
import pro.sketchware.ai.generator.AiProjectBackup;
import pro.sketchware.ai.generator.AiProjectGenerator;
import pro.sketchware.ai.generator.AiSpecException;
import pro.sketchware.ai.generator.GeneratedFile;

/**
 * Holds the AI chat and runs what it asks for, so both outlive the tab's views.
 * <p>
 * A message either creates a new project, or changes the attached one. Every created project is
 * attached right away, so follow-up messages refine it, like in any chat.
 */
public class AiChatViewModel extends AndroidViewModel {

    /**
     * Delay between showing one created file and the next, to let the user follow along.
     */
    private static final long FILE_REVEAL_DELAY_MS = 180;

    private final AiTaskRunner taskRunner = new AiTaskRunner();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<ChatItem> items = new ArrayList<>();
    private final MutableLiveData<List<ChatItem>> itemsData = new MutableLiveData<>(Collections.emptyList());
    private final MutableLiveData<Boolean> busy = new MutableLiveData<>(false);
    private final MutableLiveData<AttachedProject> attachedProject = new MutableLiveData<>();
    /**
     * Fires once per created or changed project, for refreshing the project list.
     */
    private final MutableLiveData<String> changedProject = new MutableLiveData<>();
    private long nextId;

    public AiChatViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public LiveData<List<ChatItem>> getItems() {
        return itemsData;
    }

    /**
     * @return Whether the AI is working, during which no new message can be sent.
     */
    @NonNull
    public LiveData<Boolean> isBusy() {
        return busy;
    }

    /**
     * @return The project the next message changes, or null for a new project.
     */
    @NonNull
    public LiveData<AttachedProject> getAttachedProject() {
        return attachedProject;
    }

    @NonNull
    public LiveData<String> getChangedProject() {
        return changedProject;
    }

    public void attach(@Nullable AttachedProject project) {
        attachedProject.setValue(project);
    }

    /**
     * Clears the chat and detaches the project. The projects themselves stay.
     */
    public void newChat() {
        if (isWorking()) {
            return;
        }
        handler.removeCallbacksAndMessages(null);
        items.clear();
        attachedProject.setValue(null);
        publish();
    }

    public void send(@NonNull String prompt, @NonNull AiConfiguration configuration) {
        if (isWorking()) {
            return;
        }
        busy.setValue(true);
        AttachedProject target = attachedProject.getValue();
        add(ChatItem.user(nextId++, prompt, target == null ? null : target.name()));

        long progressId = nextId++;
        String modelName = configuration.getDisplayName();
        AiProjectGenerator.Stage firstStage = target == null ? AiProjectGenerator.Stage.GENERATING : AiProjectGenerator.Stage.READING;
        add(ChatItem.progress(progressId, firstStage, modelName));

        Application application = getApplication();
        taskRunner.run(mainThread -> {
                    AiProjectGenerator generator = new AiProjectGenerator(application, configuration);
                    AiProjectGenerator.ProgressListener listener = stage -> mainThread.post(() ->
                            replace(progressId, ChatItem.progress(progressId, stage, modelName)));
                    return target == null
                            ? generator.generate(prompt, listener)
                            : generator.edit(target.sc_id(), prompt, listener);
                },
                new AiTaskRunner.Callback<>() {
                    @Override
                    public void onSuccess(AiProjectGenerator.Result result) {
                        if (result.isReply()) {
                            // Small talk: answer in the chat, nothing was created or changed
                            replace(progressId, ChatItem.info(progressId, result.getReply()));
                            busy.setValue(false);
                            return;
                        }
                        replace(progressId, ChatItem.result(progressId, result));
                        changedProject.setValue(result.getScId());
                        if (!result.isUpdate()) {
                            attachedProject.setValue(new AttachedProject(result.getScId(), result.getSpec().appName));
                        }
                        revealFiles(result.getFiles(), 0);
                    }

                    @Override
                    public void onError(@NonNull Exception e) {
                        String message = e instanceof AiSpecException
                                ? application.getString(R.string.ai_error_unusable_answer, e.getMessage())
                                : String.valueOf(e.getMessage());
                        replace(progressId, ChatItem.error(progressId, message, prompt));
                        busy.setValue(false);
                    }
                });
    }

    /**
     * Puts a project back the way it was before the AI last changed it.
     */
    public void undo(@NonNull String sc_id, @NonNull String projectName) {
        if (isWorking()) {
            return;
        }
        busy.setValue(true);
        Application application = getApplication();
        taskRunner.run(mainThread -> {
            new AiProjectBackup(sc_id).restore();
            return sc_id;
        }, new AiTaskRunner.Callback<>() {
            @Override
            public void onSuccess(String restored) {
                add(ChatItem.info(nextId++, application.getString(R.string.ai_chat_undone, projectName)));
                changedProject.setValue(restored);
                busy.setValue(false);
            }

            @Override
            public void onError(@NonNull Exception e) {
                add(ChatItem.info(nextId++, application.getString(R.string.ai_chat_undo_failed, e.getMessage())));
                busy.setValue(false);
            }
        });
    }

    private boolean isWorking() {
        return Boolean.TRUE.equals(busy.getValue());
    }

    /**
     * Shows the files one after another; the chat is ready for the next message after the last.
     */
    private void revealFiles(List<GeneratedFile> files, int index) {
        if (index >= files.size()) {
            busy.setValue(false);
            return;
        }
        add(ChatItem.file(nextId++, files.get(index)));
        handler.postDelayed(() -> revealFiles(files, index + 1), FILE_REVEAL_DELAY_MS);
    }

    private void add(ChatItem item) {
        items.add(item);
        publish();
    }

    private void replace(long id, ChatItem item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.set(i, item);
                publish();
                return;
            }
        }
    }

    private void publish() {
        itemsData.setValue(new ArrayList<>(items));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        taskRunner.cancel();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * A project the chat changes, with the name to show for it.
     */
    public static final class AttachedProject {
        private final String sc_id;
        private final String name;

        public AttachedProject(@NonNull String sc_id, @NonNull String name) {
            this.sc_id = sc_id;
            this.name = name;
        }

        @NonNull
        public String sc_id() {
            return sc_id;
        }

        @NonNull
        public String name() {
            return name;
        }
    }
}
