package pro.sketchware.activities.main.fragments.ai;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.transition.MaterialFadeThrough;

import java.util.HashMap;
import java.util.List;

import a.a.a.lC;
import a.a.a.yB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.main.activities.MainActivity;
import pro.sketchware.activities.settings.SettingsActivity;
import pro.sketchware.ai.config.AiConfiguration;
import pro.sketchware.ai.config.AiConfigurationRepository;
import pro.sketchware.ai.generator.GeneratedFile;
import pro.sketchware.databinding.FragmentAiBinding;
import pro.sketchware.databinding.ItemAiProjectChoiceBinding;
import pro.sketchware.lib.base.BaseTextWatcher;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.UI;

/**
 * The home screen's AI tab: a chat in which every message either describes a new app or a
 * change to the attached project, and every answer is a finished project together with the
 * files the AI wrote for it.
 */
public class AiFragment extends Fragment implements AiChatAdapter.Listener {

    private static final int MENU_GROUP_MODELS = 1;
    private static final int MENU_ID_MANAGE_MODELS = -1;
    private static final int MENU_ID_CONFIGURATION = 1;
    private static final int MENU_ID_NEW_CHAT = 2;

    private FragmentAiBinding binding;
    private AiChatViewModel viewModel;
    private AiChatAdapter adapter;
    private AiConfigurationRepository repository;
    private AppBarLayout appBar;
    private View bottomNavigation;
    private int previousSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED;
    /**
     * Whether the tab is on screen, so the window's keyboard mode is changed and restored once.
     */
    private boolean shown;
    private final ViewTreeObserver.OnGlobalLayoutListener onLayout = this::updateBottomPadding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialFadeThrough());
        setReturnTransition(new MaterialFadeThrough());
        setExitTransition(new MaterialFadeThrough());
        setReenterTransition(new MaterialFadeThrough());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new AiConfigurationRepository(requireContext());
        viewModel = new ViewModelProvider(this).get(AiChatViewModel.class);
        appBar = requireActivity().findViewById(R.id.appbar);
        bottomNavigation = requireActivity().findViewById(R.id.bottom_nav);

        adapter = new AiChatAdapter(this);
        binding.messages.setAdapter(adapter);
        // The chat scrolls on its own; letting it move the hidden search bar would move the chat
        binding.messages.setNestedScrollingEnabled(false);
        UI.addSystemWindowInsetToPadding(binding.topBar, true, true, true, false);

        setupTopBar();
        setupComposer();
        addSuggestions();
        binding.setup.setOnClickListener(v -> openConfiguration());

        viewModel.getItems().observe(getViewLifecycleOwner(), this::showItems);
        viewModel.isBusy().observe(getViewLifecycleOwner(), busy -> updateComposer());
        viewModel.getAttachedProject().observe(getViewLifecycleOwner(), this::showAttachedProject);
        viewModel.getChangedProject().observe(getViewLifecycleOwner(), sc_id -> mainActivity().refreshProjects());

        keepComposerVisible();
    }

    @Override
    public void onResume() {
        super.onResume();
        // The user may be coming back from the model settings
        bindModel();
        if (!isHidden()) {
            onShown();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onHidden();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (binding == null) {
            return;
        }
        if (hidden) {
            onHidden();
        } else {
            bindModel();
            onShown();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.getRoot().getViewTreeObserver().removeOnGlobalLayoutListener(onLayout);
        binding = null;
    }

    private void onShown() {
        if (shown) {
            return;
        }
        shown = true;
        // The chat has its own top bar
        appBar.setExpanded(false, false);
        // From Android 11 on the keyboard's height is known exactly, so the window can stay as it
        // is and the composer moves above the keyboard alone, covering the bottom navigation.
        // Before that, the window has to shrink for the keyboard.
        previousSoftInputMode = requireActivity().getWindow().getAttributes().softInputMode;
        requireActivity().getWindow().setSoftInputMode(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
                ? WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
                : WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void onHidden() {
        if (!shown) {
            return;
        }
        shown = false;
        requireActivity().getWindow().setSoftInputMode(previousSoftInputMode);
        hideKeyboard();
        setBottomNavigationVisible(true);
    }

    // ---------------------------------------------------------------------------------------
    // Top bar
    // ---------------------------------------------------------------------------------------

    private void setupTopBar() {
        binding.openDrawer.setOnClickListener(v -> mainActivity().openDrawer());
        binding.model.setOnClickListener(v -> showModelMenu());
        binding.newChat.setOnClickListener(v -> viewModel.newChat());
        binding.overflow.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireContext(), binding.overflow);
            popup.getMenu().add(Menu.NONE, MENU_ID_CONFIGURATION, 0, R.string.ai_chat_menu_configuration);
            popup.getMenu().add(Menu.NONE, MENU_ID_NEW_CHAT, 1, R.string.ai_chat_menu_new_chat)
                    .setEnabled(!Boolean.TRUE.equals(viewModel.isBusy().getValue()));
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == MENU_ID_CONFIGURATION) {
                    openConfiguration();
                } else {
                    viewModel.newChat();
                }
                return true;
            });
            popup.show();
        });
    }

    /**
     * Shows the selected model's version, e.g. "gemini-2.5-flash", rather than the name the user
     * gave its configuration.
     */
    private void bindModel() {
        AiConfiguration selected = repository.getSelected();
        binding.model.setText(selected == null ? getString(R.string.ai_chat_no_model) : modelVersion(selected));
        binding.setup.setVisibility(selected == null ? View.VISIBLE : View.GONE);
        binding.welcomeMessage.setText(selected == null ? R.string.ai_no_configuration_message : R.string.ai_chat_welcome_message);
        updateComposer();
    }

    private void showModelMenu() {
        List<AiConfiguration> configurations = repository.getAll();
        AiConfiguration selected = repository.getSelected();

        PopupMenu popup = new PopupMenu(requireContext(), binding.model);
        Menu menu = popup.getMenu();
        for (int i = 0; i < configurations.size(); i++) {
            AiConfiguration configuration = configurations.get(i);
            MenuItem item = menu.add(MENU_GROUP_MODELS, i, i, describeModel(configuration));
            item.setChecked(selected != null && selected.getId().equals(configuration.getId()));
        }
        menu.setGroupCheckable(MENU_GROUP_MODELS, true, true);
        menu.add(Menu.NONE, MENU_ID_MANAGE_MODELS, configurations.size(), R.string.ai_manage_models);

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU_ID_MANAGE_MODELS) {
                openConfiguration();
            } else {
                repository.setSelected(configurations.get(item.getItemId()).getId());
                bindModel();
            }
            return true;
        });
        popup.show();
    }

    /**
     * @return The model's ID without the vendor prefix some providers use, e.g. "nvidia/".
     */
    private static String modelVersion(AiConfiguration configuration) {
        String model = configuration.getModel();
        int slash = model.lastIndexOf('/');
        return slash >= 0 && slash < model.length() - 1 ? model.substring(slash + 1) : model;
    }

    /**
     * @return The version, followed by the configuration's own name if it has one.
     */
    private static String describeModel(AiConfiguration configuration) {
        String version = modelVersion(configuration);
        String name = configuration.getName().trim();
        return name.isEmpty() || name.equals(version)
                ? version + " · " + configuration.getProvider().getDisplayName()
                : version + " · " + name;
    }

    private void openConfiguration() {
        Intent intent = new Intent(requireContext(), SettingsActivity.class);
        intent.putExtra(SettingsActivity.FRAGMENT_TAG_EXTRA, SettingsActivity.AI_CONFIGURATION_FRAGMENT);
        startActivity(intent);
    }

    // ---------------------------------------------------------------------------------------
    // Composer
    // ---------------------------------------------------------------------------------------

    private void setupComposer() {
        binding.prompt.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateComposer();
            }
        });
        binding.send.setOnClickListener(v -> send());
        binding.attachProject.setOnClickListener(v -> chooseProject());
        binding.attachedProject.setOnCloseIconClickListener(v -> viewModel.attach(null));
    }

    private void addSuggestions() {
        for (String suggestion : getResources().getStringArray(R.array.ai_ideas)) {
            Chip chip = new Chip(requireContext());
            chip.setText(suggestion);
            chip.setCheckable(false);
            chip.setOnClickListener(v -> {
                binding.prompt.setText(suggestion);
                binding.prompt.setSelection(suggestion.length());
                binding.prompt.requestFocus();
            });
            binding.suggestions.addView(chip);
        }
    }

    /**
     * The send button only lights up when there's something to send and nothing is running.
     */
    private void updateComposer() {
        if (binding == null) {
            return;
        }
        boolean busy = Boolean.TRUE.equals(viewModel.isBusy().getValue());
        boolean hasText = !Helper.getText(binding.prompt).trim().isEmpty();
        binding.send.setEnabled(!busy && hasText && repository.getSelected() != null);
        binding.model.setEnabled(!busy);
        binding.newChat.setEnabled(!busy);
        binding.attachProject.setEnabled(!busy);
        binding.attachedProject.setCloseIconVisible(!busy);
        // Suggestions start new apps, so they make no sense while a project is attached
        boolean showSuggestions = !busy && viewModel.getAttachedProject().getValue() == null;
        binding.suggestionsScroll.setVisibility(showSuggestions ? View.VISIBLE : View.GONE);
    }

    private void showAttachedProject(AiChatViewModel.AttachedProject project) {
        binding.attachedProject.setVisibility(project == null ? View.GONE : View.VISIBLE);
        binding.attachedProject.setText(project == null ? null : project.name());
        binding.prompt.setHint(project == null ? R.string.ai_chat_hint : R.string.ai_chat_hint_edit);
        updateComposer();
    }

    private void chooseProject() {
        List<HashMap<String, Object>> projects = lC.a();
        if (projects.isEmpty()) {
            SketchwareUtil.toast(getString(R.string.ai_chat_no_projects));
            return;
        }
        // Newest first, like the project list
        projects.sort((first, second) -> Integer.compare(
                parseId(yB.c(second, "sc_id")), parseId(yB.c(first, "sc_id"))));

        ArrayAdapter<HashMap<String, Object>> adapter = new ArrayAdapter<>(requireContext(), 0, projects) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                ItemAiProjectChoiceBinding item = convertView == null
                        ? ItemAiProjectChoiceBinding.inflate(getLayoutInflater(), parent, false)
                        : ItemAiProjectChoiceBinding.bind(convertView);
                HashMap<String, Object> project = projects.get(position);
                item.name.setText(yB.c(project, "my_app_name"));
                item.details.setText(getString(R.string.ai_chat_project_details,
                        yB.c(project, "sc_id"), yB.c(project, "my_sc_pkg_name")));
                return item.getRoot();
            }
        };
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.ai_chat_choose_project)
                .setAdapter(adapter, (dialog, which) -> {
                    HashMap<String, Object> project = projects.get(which);
                    viewModel.attach(new AiChatViewModel.AttachedProject(
                            yB.c(project, "sc_id"), yB.c(project, "my_app_name")));
                    binding.prompt.requestFocus();
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    private static int parseId(String sc_id) {
        try {
            return Integer.parseInt(sc_id);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void send() {
        String prompt = Helper.getText(binding.prompt).trim();
        AiConfiguration configuration = repository.getSelected();
        if (prompt.isEmpty()) {
            return;
        }
        if (configuration == null) {
            openConfiguration();
            return;
        }
        if (!mainActivity().isStoragePermissionGranted()) {
            SketchwareUtil.toastError(getString(R.string.ai_error_storage));
            mainActivity().s();
            return;
        }

        binding.prompt.setText("");
        hideKeyboard();
        viewModel.send(prompt, configuration);
    }

    // ---------------------------------------------------------------------------------------
    // Chat
    // ---------------------------------------------------------------------------------------

    private void showItems(List<ChatItem> items) {
        int previousCount = adapter.getItemCount();
        adapter.submitList(items, () -> {
            if (binding != null && !items.isEmpty() && items.size() != previousCount) {
                binding.messages.smoothScrollToPosition(items.size() - 1);
            }
        });
        binding.welcome.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onOpenProject(@NonNull String sc_id) {
        mainActivity().openProject(sc_id);
    }

    @Override
    public void onEditProject(@NonNull String sc_id) {
        mainActivity().editProject(sc_id);
    }

    @Override
    public void onRetry(@NonNull String prompt) {
        binding.prompt.setText(prompt);
        send();
    }

    @Override
    public void onUndo(@NonNull String sc_id, @NonNull String projectName) {
        viewModel.undo(sc_id, projectName);
    }

    @Override
    public void onCopy(@NonNull GeneratedFile file) {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(file.getName(), file.getContent()));
        SketchwareUtil.toast(getString(R.string.ai_chat_copied, file.getName()));
    }

    // ---------------------------------------------------------------------------------------
    // Layout
    // ---------------------------------------------------------------------------------------

    /**
     * The tab sits behind the bottom navigation, so the composer is moved up by its height. Or
     * by the keyboard's, while it covers the bottom navigation.
     */
    private void keepComposerVisible() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            updateBottomPadding();
            return insets;
        });
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(onLayout);
    }

    private void updateBottomPadding() {
        if (binding == null) {
            return;
        }
        View root = binding.getRoot();
        int windowHeight = root.getRootView().getHeight();
        if (windowHeight == 0) {
            return;
        }
        // Read from the window, because views above this one may have consumed the insets
        WindowInsetsCompat insets = ViewCompat.getRootWindowInsets(root);
        boolean keyboardShown = shown && insets != null && insets.isVisible(WindowInsetsCompat.Type.ime());
        // Like other chat apps, the bottom navigation makes room for the composer while typing
        setBottomNavigationVisible(!keyboardShown);

        int[] location = new int[2];
        root.getLocationInWindow(location);
        int belowWindow = Math.max(0, location[1] + root.getHeight() - windowHeight);

        int coveredByNavigation = 0;
        if (!keyboardShown) {
            bottomNavigation.getLocationInWindow(location);
            coveredByNavigation = Math.max(0, windowHeight - location[1]);
        }

        int coveredByKeyboard = 0;
        if (keyboardShown) {
            // Depending on the Android version, the window may have shrunk for the keyboard
            // already; only the part it still covers counts
            int keyboardHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
            coveredByKeyboard = Math.max(0, keyboardHeight - (fullWindowHeight() - windowHeight));
        }

        int padding = belowWindow + Math.max(coveredByNavigation, coveredByKeyboard);
        if (root.getPaddingBottom() != padding) {
            root.setPadding(root.getPaddingLeft(), root.getPaddingTop(), root.getPaddingRight(), padding);
        }
    }

    /**
     * @return The window's height without any keyboard.
     */
    private int fullWindowHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return requireActivity().getWindowManager().getCurrentWindowMetrics().getBounds().height();
        }
        return binding.getRoot().getRootView().getHeight();
    }

    /**
     * Shows or hides the bottom navigation together with the divider above it.
     */
    private void setBottomNavigationVisible(boolean visible) {
        View container = (View) bottomNavigation.getParent();
        int visibility = visible ? View.VISIBLE : View.GONE;
        if (container.getVisibility() != visibility) {
            container.setVisibility(visibility);
        }
    }

    private void hideKeyboard() {
        if (binding == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.prompt.getWindowToken(), 0);
        binding.prompt.clearFocus();
    }

    private MainActivity mainActivity() {
        return (MainActivity) requireActivity();
    }
}
