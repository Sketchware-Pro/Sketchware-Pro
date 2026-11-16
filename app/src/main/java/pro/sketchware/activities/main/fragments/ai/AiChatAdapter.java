package pro.sketchware.activities.main.fragments.ai;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import pro.sketchware.R;
import pro.sketchware.ai.generator.AiProjectGenerator;
import pro.sketchware.ai.generator.GeneratedFile;
import pro.sketchware.databinding.ItemAiChatAssistantBinding;
import pro.sketchware.databinding.ItemAiChatFileBinding;
import pro.sketchware.databinding.ItemAiChatUserBinding;
import pro.sketchware.utility.ThemeUtils;

/**
 * Shows the AI chat: the user's prompts on the right, the AI's answers on the left, and the files
 * it created as cards that expand to show their code.
 */
class AiChatAdapter extends ListAdapter<ChatItem, AiChatAdapter.Holder> {

    /**
     * How long a new file card shows its "being created" spinner.
     */
    private static final long FILE_CREATING_MS = 450;
    private static final long TYPING_DOT_DURATION_MS = 420;
    private static final long TYPING_DOT_STAGGER_MS = 140;
    private static final float TYPING_DOT_LIFT_DP = 4;

    private static final DiffUtil.ItemCallback<ChatItem> DIFF = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull ChatItem oldItem, @NonNull ChatItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ChatItem oldItem, @NonNull ChatItem newItem) {
            // Items are immutable, so a different instance means different content
            return oldItem == newItem;
        }
    };

    private final Listener listener;
    private final Set<Long> expandedFiles = new HashSet<>();
    private final Set<Long> createdFiles = new HashSet<>();

    AiChatAdapter(@NonNull Listener listener) {
        super(DIFF);
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().ordinal();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewBinding binding = switch (ChatItem.Type.values()[viewType]) {
            case USER -> ItemAiChatUserBinding.inflate(inflater, parent, false);
            case FILE -> ItemAiChatFileBinding.inflate(inflater, parent, false);
            case PROGRESS, RESULT, ERROR, INFO -> ItemAiChatAssistantBinding.inflate(inflater, parent, false);
        };
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ChatItem item = getItem(position);
        holder.stopTyping();
        if (holder.binding instanceof ItemAiChatUserBinding user) {
            user.message.setText(item.getText());
            user.project.setVisibility(item.getProjectName() == null ? View.GONE : View.VISIBLE);
            user.project.setText(item.getProjectName());
        } else if (holder.binding instanceof ItemAiChatFileBinding file) {
            bindFile(holder, file, item);
        } else {
            bindAssistant(holder, (ItemAiChatAssistantBinding) holder.binding, item);
        }
    }

    @Override
    public void onViewRecycled(@NonNull Holder holder) {
        super.onViewRecycled(holder);
        holder.stopTyping();
    }

    private void bindAssistant(Holder holder, ItemAiChatAssistantBinding binding, ChatItem item) {
        Context context = binding.getRoot().getContext();
        boolean hasActions = item.getType() == ChatItem.Type.RESULT || item.getType() == ChatItem.Type.ERROR;
        binding.typing.setVisibility(item.getType() == ChatItem.Type.PROGRESS ? View.VISIBLE : View.GONE);
        binding.actions.setVisibility(hasActions ? View.VISIBLE : View.GONE);
        binding.message.setTextColor(ThemeUtils.getColor(binding.message,
                item.getType() == ChatItem.Type.ERROR ? R.attr.colorError : R.attr.colorOnSurface));

        switch (item.getType()) {
            case PROGRESS -> {
                binding.message.setText(describeStage(context, item));
                holder.startTyping();
            }
            case RESULT -> bindResult(binding, Objects.requireNonNull(item.getResult()));
            case ERROR -> {
                binding.message.setText(item.getText());
                bindActions(binding, R.string.ai_chat_retry, v -> listener.onRetry(Objects.requireNonNull(item.getFailedPrompt())),
                        0, null);
            }
            case INFO -> binding.message.setText(item.getText());
            default -> throw new IllegalStateException("Not an assistant item: " + item.getType());
        }
    }

    private void bindResult(ItemAiChatAssistantBinding binding, AiProjectGenerator.Result result) {
        Context context = binding.getRoot().getContext();
        String appName = result.getSpec().appName;
        String sc_id = result.getScId();
        if (result.isUpdate()) {
            StringBuilder message = new StringBuilder(context.getString(R.string.ai_chat_updated, appName));
            if (!result.getNotCarriedOver().isEmpty()) {
                message.append("\n\n").append(context.getString(R.string.ai_chat_not_carried_over,
                        String.join(", ", result.getNotCarriedOver())));
            }
            binding.message.setText(message);
            bindActions(binding, R.string.ai_result_open, v -> listener.onOpenProject(sc_id),
                    R.string.ai_chat_undo, v -> listener.onUndo(sc_id, appName));
        } else {
            int screenCount = result.getSpec().screens.size();
            binding.message.setText(context.getString(R.string.ai_chat_result, appName,
                    context.getResources().getQuantityString(R.plurals.ai_result_screens, screenCount, screenCount),
                    sc_id, result.getSpec().packageName));
            bindActions(binding, R.string.ai_result_open, v -> listener.onOpenProject(sc_id),
                    R.string.ai_chat_edit_details, v -> listener.onEditProject(sc_id));
        }
    }

    private String describeStage(Context context, ChatItem item) {
        return switch (Objects.requireNonNull(item.getStage())) {
            case READING -> context.getString(R.string.ai_stage_reading);
            case GENERATING -> context.getString(R.string.ai_stage_generating, item.getText());
            case REPAIRING -> context.getString(R.string.ai_stage_repairing);
            case BUILDING -> context.getString(R.string.ai_stage_building);
        };
    }

    private void bindActions(ItemAiChatAssistantBinding binding, int primaryText, View.OnClickListener primary,
                             int secondaryText, View.OnClickListener secondary) {
        binding.primaryAction.setText(primaryText);
        binding.primaryAction.setOnClickListener(primary);
        binding.secondaryAction.setVisibility(secondary == null ? View.GONE : View.VISIBLE);
        if (secondary != null) {
            binding.secondaryAction.setText(secondaryText);
            binding.secondaryAction.setOnClickListener(secondary);
        }
    }

    private void bindFile(Holder holder, ItemAiChatFileBinding binding, ChatItem item) {
        GeneratedFile file = Objects.requireNonNull(item.getFile());
        long id = item.getId();
        boolean expanded = expandedFiles.contains(id);

        binding.name.setText(file.getName());
        binding.details.setText(binding.getRoot().getContext().getString(R.string.ai_chat_file_details,
                file.getDescription(), file.getLineCount()));
        binding.code.setText(file.getContent());
        binding.body.setVisibility(expanded ? View.VISIBLE : View.GONE);
        binding.chevron.setRotation(expanded ? 180 : 0);

        boolean created = createdFiles.contains(id);
        binding.creating.setVisibility(created ? View.GONE : View.VISIBLE);
        binding.created.setVisibility(created ? View.VISIBLE : View.GONE);
        if (!created) {
            binding.getRoot().postDelayed(() -> {
                createdFiles.add(id);
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && getItem(position).getId() == id) {
                    notifyItemChanged(position);
                }
            }, FILE_CREATING_MS);
        }

        binding.header.setOnClickListener(v -> {
            boolean expand = !expandedFiles.remove(id);
            if (expand) {
                expandedFiles.add(id);
            }
            binding.body.setVisibility(expand ? View.VISIBLE : View.GONE);
            binding.chevron.animate().rotation(expand ? 180 : 0).start();
        });
        binding.copy.setOnClickListener(v -> listener.onCopy(file));
    }

    interface Listener {
        void onOpenProject(@NonNull String sc_id);

        void onEditProject(@NonNull String sc_id);

        void onRetry(@NonNull String prompt);

        void onUndo(@NonNull String sc_id, @NonNull String projectName);

        void onCopy(@NonNull GeneratedFile file);
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final ViewBinding binding;
        private AnimatorSet typingAnimation;

        Holder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Makes the three dots of an assistant row bounce one after another.
         */
        void startTyping() {
            if (!(binding instanceof ItemAiChatAssistantBinding assistant) || typingAnimation != null) {
                return;
            }
            float lift = -TYPING_DOT_LIFT_DP * itemView.getResources().getDisplayMetrics().density;
            View[] dots = {assistant.dot1, assistant.dot2, assistant.dot3};
            typingAnimation = new AnimatorSet();
            AnimatorSet.Builder builder = null;
            for (int i = 0; i < dots.length; i++) {
                ObjectAnimator bounce = ObjectAnimator.ofFloat(dots[i], View.TRANSLATION_Y, 0, lift);
                bounce.setDuration(TYPING_DOT_DURATION_MS);
                bounce.setStartDelay(i * TYPING_DOT_STAGGER_MS);
                bounce.setRepeatMode(ValueAnimator.REVERSE);
                bounce.setRepeatCount(ValueAnimator.INFINITE);
                builder = builder == null ? typingAnimation.play(bounce) : builder.with(bounce);
            }
            typingAnimation.start();
        }

        void stopTyping() {
            if (typingAnimation != null) {
                typingAnimation.cancel();
                typingAnimation = null;
                ItemAiChatAssistantBinding assistant = (ItemAiChatAssistantBinding) binding;
                assistant.dot1.setTranslationY(0);
                assistant.dot2.setTranslationY(0);
                assistant.dot3.setTranslationY(0);
            }
        }
    }
}
