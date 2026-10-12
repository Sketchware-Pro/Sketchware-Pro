package pro.sketchware.activities.main.fragments.projects;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDragHandleView;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.function.Consumer;

import pro.sketchware.R;

/**
 * Bottom sheets used by the Projects manager (create, import, search scope, selection, folder picker...).
 */
public final class ProjectsSheets {

    private static final int DANGER_COLOR = 0xFFBA1A1A;

    private ProjectsSheets() {
    }

    /* ---------- Sheets ---------- */

    public static void showCreateSheet(ProjectsFragment fragment) {
        Context context = fragment.requireContext();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        LinearLayout content = newSheetContent(dialog, "Create", "Create a folder or a new project here");

        addRow(content, R.drawable.ic_mtrl_folder, "Create folder", "Organize your projects", false, true, () -> {
            dialog.dismiss();
            fragment.promptCreateFolder();
        });
        addRow(content, R.drawable.ic_mtrl_add, "Create new project", "Start a new Android project", false, true, () -> {
            dialog.dismiss();
            fragment.toProjectSettingsActivity();
        });
        dialog.show();
    }

    public static void showImportSheet(ProjectsFragment fragment) {
        Context context = fragment.requireContext();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        LinearLayout content = newSheetContent(dialog, "Import", null);

        addRow(content, R.drawable.ic_mtrl_folder, "Import folder", "Coming soon", false, false, null);
        addRow(content, R.drawable.ic_mtrl_file, "Import zip file", "Coming soon", false, false, null);
        addRow(content, R.drawable.ic_mtrl_history, "Import .swb file", "Restore a project from a SketchwareX backup", false, true, () -> {
            dialog.dismiss();
            fragment.restoreProject();
        });
        dialog.show();
    }

    public static void showSearchScopeSheet(ProjectsFragment fragment) {
        Context context = fragment.requireContext();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        ProjectFolderStore store = fragment.getFolderStore();
        ProjectFolderStore.Folder scope = store.getFolder(fragment.getSearchScopeFolderId());
        String subtitle = scope == null ? "Currently searching all folders" : "Currently searching in \"" + scope.name + "\"";
        LinearLayout content = newSheetContent(dialog, "Search scope", subtitle);

        addRow(content, R.drawable.ic_mtrl_search, "Search in all folders", "Search every project", false, true, () -> {
            dialog.dismiss();
            fragment.setSearchScope(null);
        });
        addRow(content, R.drawable.ic_mtrl_folder, "Search in a specific folder", "Pick a folder, then press Select", false, true, () -> {
            dialog.dismiss();
            showFolderPicker(fragment, "Search in folder", "Select", fragment::setSearchScope, null);
        });
        dialog.show();
    }

    public static void showProjectLongPressSheet(ProjectsFragment fragment, String scId) {
        Context context = fragment.requireContext();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        LinearLayout content = newSheetContent(dialog, "Project", scId);

        addRow(content, R.drawable.ic_mtrl_checkbox, "Select", "Select several projects to move or delete them", false, true, () -> {
            dialog.dismiss();
            fragment.startSelection(scId);
        });
        dialog.show();
    }

    public static void showSelectionSheet(ProjectsFragment fragment) {
        Context context = fragment.requireContext();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        int count = fragment.getSelectedIds().size();
        LinearLayout content = newSheetContent(dialog, count + " selected", null);

        addRow(content, R.drawable.ic_mtrl_move_x, "Move projects", "Choose the folder to move them to", false, true, () -> {
            dialog.dismiss();
            showMovePicker(fragment);
        });
        addRow(content, R.drawable.ic_mtrl_close, "Cancel selection", null, false, true, () -> {
            dialog.dismiss();
            fragment.exitSelection();
        });
        dialog.show();
    }

    public static void showFolderSheet(ProjectsFragment fragment, String folderId, String folderName) {
        Context context = fragment.requireContext();
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        LinearLayout content = newSheetContent(dialog, folderName, null);

        addRow(content, R.drawable.ic_mtrl_edit, "Rename folder", null, false, true, () -> {
            dialog.dismiss();
            fragment.promptRenameFolder(folderId, folderName);
        });
        addRow(content, R.drawable.icon_delete, "Delete folder", "Deletes everything inside it too", true, true, () -> {
            dialog.dismiss();
            fragment.confirmDeleteFolder(folderId, folderName);
        });
        dialog.show();
    }

    /**
     * Opens the folder picker used to move the selected projects.
     */
    public static void showMovePicker(ProjectsFragment fragment) {
        List<Action> bottomActions = new java.util.ArrayList<>();
        bottomActions.add(new Action(R.drawable.icon_delete, "Delete selected", true, () ->
                fragment.confirmDeleteProjects(fragment.getSelectedIds(), "")));
        bottomActions.add(new Action(R.drawable.ic_mtrl_close, "Cancel selection", false, fragment::exitSelection));

        showFolderPicker(fragment, "Move projects", "Move", fragment::moveSelectedTo, bottomActions);
    }

    /**
     * A folder browser in a bottom sheet. Tapping a folder's name opens it, the button on its right
     * ({@code actionLabel}) picks it. The header row is the folder you are currently browsing.
     *
     * @param onPick       receives the picked folder id, or null for the root
     * @param bottomActions optional extra actions shown under the list
     */
    public static void showFolderPicker(ProjectsFragment fragment, String title, String actionLabel,
                                        Consumer<String> onPick, @Nullable List<Action> bottomActions) {
        Context context = fragment.requireContext();
        ProjectFolderStore store = fragment.getFolderStore();
        BottomSheetDialog dialog = new BottomSheetDialog(context);

        LinearLayout root = newSheetContent(dialog, title, null);

        LinearLayout list = new LinearLayout(context);
        list.setOrientation(LinearLayout.VERTICAL);
        root.addView(list);

        final String[] browsing = {null};

        Runnable[] render = new Runnable[1];
        render[0] = () -> {
            list.removeAllViews();
            String current = browsing[0];
            ProjectFolderStore.Folder currentFolder = store.getFolder(current);

            if (currentFolder == null) {
                addRowWithAction(context, list, R.drawable.ic_mtrl_folder, "Projects (root)", null, actionLabel, () -> {
                    dialog.dismiss();
                    onPick.accept(null);
                }, null);
            } else {
                addRowWithAction(context, list, R.drawable.ic_mtrl_arrow_left, currentFolder.name, "Tap to go back", actionLabel, () -> {
                    dialog.dismiss();
                    onPick.accept(current);
                }, () -> {
                    browsing[0] = currentFolder.parentId;
                    render[0].run();
                });
            }

            List<ProjectFolderStore.Folder> children = store.getChildFolders(current);
            if (children.isEmpty()) {
                TextView empty = new TextView(context);
                empty.setText("No folders here");
                empty.setPadding(dp(context, 16), dp(context, 12), dp(context, 16), dp(context, 12));
                empty.setTextAppearance(context, attrResId(context, com.google.android.material.R.attr.textAppearanceBodyMedium));
                list.addView(empty);
            }
            for (ProjectFolderStore.Folder child : children) {
                addRowWithAction(context, list, R.drawable.ic_mtrl_folder, child.name, null, actionLabel, () -> {
                    dialog.dismiss();
                    onPick.accept(child.id);
                }, () -> {
                    browsing[0] = child.id;
                    render[0].run();
                });
            }
        };
        render[0].run();

        if (bottomActions != null) {
            for (Action action : bottomActions) {
                addRow(root, action.iconRes, action.title, null, action.danger, true, () -> {
                    dialog.dismiss();
                    action.run.run();
                });
            }
        }
        dialog.show();
    }

    /* ---------- Building blocks ---------- */

    private static LinearLayout newSheetContent(BottomSheetDialog dialog, String title, @Nullable String subtitle) {
        Context context = dialog.getContext();
        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(context, 16), 0, dp(context, 16), dp(context, 24));

        // The dialog must get a content view, otherwise the sheet opens empty.
        ScrollView scroll = new ScrollView(context);
        scroll.addView(content);
        dialog.setContentView(scroll);

        content.addView(new BottomSheetDragHandleView(context));

        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextAppearance(context, attrResId(context, com.google.android.material.R.attr.textAppearanceTitleLarge));
        titleView.setPadding(dp(context, 8), dp(context, 4), dp(context, 8), 0);
        content.addView(titleView);

        if (subtitle != null) {
            TextView subtitleView = new TextView(context);
            subtitleView.setText(subtitle);
            subtitleView.setTextAppearance(context, attrResId(context, com.google.android.material.R.attr.textAppearanceBodyMedium));
            subtitleView.setTextColor(attrColor(context, android.R.attr.textColorSecondary));
            subtitleView.setPadding(dp(context, 8), dp(context, 4), dp(context, 8), dp(context, 12));
            content.addView(subtitleView);
        }
        return content;
    }

    private static View addRow(LinearLayout parent, @DrawableRes int iconRes, String title, @Nullable String subtitle,
                               boolean danger, boolean enabled, @Nullable Runnable onClick) {
        Context context = parent.getContext();
        View row = buildRow(context, iconRes, title, subtitle, danger, enabled, onClick, null);
        parent.addView(row);
        return row;
    }

    private static void addRowWithAction(Context context, LinearLayout parent, @DrawableRes int iconRes, String title,
                                         @Nullable String subtitle, String actionLabel, Runnable onAction,
                                         @Nullable Runnable onBodyClick) {
        MaterialButton button = new MaterialButton(context);
        button.setText(actionLabel);
        button.setAllCaps(false);
        button.setOnClickListener(v -> onAction.run());

        View row = buildRow(context, iconRes, title, subtitle, false, true, onBodyClick, button);
        parent.addView(row);
    }

    private static View buildRow(Context context, @DrawableRes int iconRes, String title, @Nullable String subtitle,
                                 boolean danger, boolean enabled, @Nullable Runnable onClick, @Nullable View trailing) {
        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setBackgroundResource(R.drawable.project_item_shape_alone);
        row.setPadding(dp(context, 16), dp(context, 14), dp(context, 12), dp(context, 14));

        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rowParams.topMargin = dp(context, 6);
        row.setLayoutParams(rowParams);

        int color = danger ? DANGER_COLOR : attrColor(context, android.R.attr.textColorPrimary);

        ImageView icon = new ImageView(context);
        icon.setImageResource(iconRes);
        ImageViewCompat.setImageTintList(icon, android.content.res.ColorStateList.valueOf(color));
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dp(context, 24), dp(context, 24));
        icon.setLayoutParams(iconParams);
        row.addView(icon);

        LinearLayout texts = new LinearLayout(context);
        texts.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textsParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textsParams.setMarginStart(dp(context, 16));
        texts.setLayoutParams(textsParams);

        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextAppearance(context, attrResId(context, com.google.android.material.R.attr.textAppearanceBodyLarge));
        titleView.setTextColor(color);
        texts.addView(titleView);

        if (subtitle != null) {
            TextView subtitleView = new TextView(context);
            subtitleView.setText(subtitle);
            subtitleView.setTextAppearance(context, attrResId(context, com.google.android.material.R.attr.textAppearanceBodySmall));
            subtitleView.setTextColor(attrColor(context, android.R.attr.textColorSecondary));
            texts.addView(subtitleView);
        }
        row.addView(texts);

        if (trailing != null) {
            LinearLayout.LayoutParams trailingParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            trailingParams.setMarginStart(dp(context, 8));
            trailing.setLayoutParams(trailingParams);
            row.addView(trailing);
        }

        if (!enabled) {
            row.setAlpha(0.45f);
        } else if (onClick != null) {
            row.setClickable(true);
            row.setFocusable(true);
            TypedValue ripple = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, ripple, true);
            row.setForeground(context.getDrawable(ripple.resourceId));
            row.setOnClickListener(v -> onClick.run());
        }
        return row;
    }

    private static int dp(Context context, int value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }

    private static int attrResId(Context context, int attr) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.resourceId;
    }

    private static int attrColor(Context context, int attr) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.data;
    }

    public static class Action {
        final int iconRes;
        final String title;
        final boolean danger;
        final Runnable run;

        public Action(int iconRes, String title, boolean danger, Runnable run) {
            this.iconRes = iconRes;
            this.title = title;
            this.danger = danger;
            this.run = run;
        }
    }
}
