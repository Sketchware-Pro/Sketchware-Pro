package mod.hilal.saif.activities.tools;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.CollapsibleViewHolder;
import com.besome.sketch.lib.ui.CollapsibleButton;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import a.a.a.aB;
import a.a.a.wq;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.elfilibustero.sketch.editor.component.CollapsibleCustomComponentLayout;
import mod.elfilibustero.sketch.lib.ui.SketchFilePickerDialog;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.components.ComponentsHandler;

public class ManageCustomComponentActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, Object>> componentsList = new ArrayList<>();

    private static final String COMPONENT_EXPORT_DIR = wq.getExtraDataExport() + "/components/";
    private static final String COMPONENT_DIR = wq.getCustomComponent();

    private TextView tv_guide;
    private RecyclerView componentView;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.manage_custom_component);
        init();
    }

    private void init() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        tv_guide = findViewById(R.id.tv_guide);
        componentView = findViewById(R.id.componentView);

        findViewById(R.id._fab).setOnClickListener(_view ->
                startActivity(new Intent(getApplicationContext(), AddCustomComponentActivity.class)));
    }

    @Override
    public void onResume() {
        super.onResume();
        readSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Import");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            showFilePickerDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        ComponentsHandler.refreshCachedCustomComponents();
    }

    private void readSettings() {
        if (FileUtil.isExistFile(COMPONENT_DIR)) {
            readComponents(COMPONENT_DIR);
        } else {
            tv_guide.setVisibility(View.VISIBLE);
            componentView.setVisibility(View.GONE);
        }
    }

    private void readComponents(final String _path) {
        componentsList = new Gson().fromJson(FileUtil.readFile(_path), Helper.TYPE_MAP_LIST);
        if (componentsList != null && componentsList.size() > 0) {
            ComponentsAdapter adapter = new ComponentsAdapter(componentsList);
            Parcelable state = componentView.getLayoutManager().onSaveInstanceState();
            componentView.setAdapter(adapter);
            componentView.getLayoutManager().onRestoreInstanceState(state);
            adapter.notifyDataSetChanged();
            componentView.setVisibility(View.VISIBLE);
            tv_guide.setVisibility(View.GONE);
            return;
        }
        tv_guide.setVisibility(View.VISIBLE);
        componentView.setVisibility(View.GONE);
    }

    private void showFilePickerDialog() {
        SketchFilePickerDialog filePickerDialog = new SketchFilePickerDialog(this)
                .allowExtension("json")
                .setFilePath(FileUtil.getExternalStorageDir())
                .setOnFileSelectedListener((SketchFilePickerDialog dialog, File file) -> {
                    try {
                        selectComponentToImport(file.getAbsolutePath());
                    } catch (Exception e) {
                        SketchwareUtil.toastError(Helper.getResString(R.string.publish_message_dialog_invalid_json));
                    }
                    dialog.dismiss();
                });
        filePickerDialog.setTitle(Helper.getResString(R.string.common_word_import));
        filePickerDialog.setIcon(R.drawable.file_48_blue);
        filePickerDialog.show();
    }

    private void selectComponentToImport(String path) {
        String text = FileUtil.readFile(path);
        if (text.isEmpty() || text.equals("[]")) {
            SketchwareUtil.toastError(Helper.getResString(R.string.common_message_selected_file_empty));
            return;
        }

        ArrayList<HashMap<String, Object>> list = new Gson().fromJson(text, Helper.TYPE_MAP_LIST);
        if (list.isEmpty() || !ComponentsHandler.isValidComponentList(list)) {
            SketchwareUtil.toastError(Helper.getResString(R.string.publish_message_dialog_invalid_json));
            return;
        }

        ArrayList<String> componentNames = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            componentNames.add((String) list.get(j).get("name"));
        }

        if (componentNames.size() > 1) {
            var dialog = new aB(this);
            dialog.b(Helper.getResString(R.string.logic_editor_title_select_component));
            ArrayList<Integer> selectedPositions = new ArrayList<>();
            var listView = new ListView(this);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, componentNames);
            listView.setAdapter(arrayAdapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setDivider(null);
            listView.setDividerHeight(0);
            listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
                boolean isChecked = checkedPositions.get(position);
                if (isChecked) {
                    selectedPositions.add(position);
                } else {
                    selectedPositions.remove(Integer.valueOf(position));
                }
            });
            dialog.a(listView);
            dialog.b(Helper.getResString(R.string.common_word_import), view -> {
                for (int position : selectedPositions) {
                    HashMap<String, Object> map = list.get(position);
                    if (position != -1 && ComponentsHandler.isValidComponent(map)) {
                        componentsList.add(map);
                    } else {
                        SketchwareUtil.toastError(Helper.getResString(R.string.invalid_component));
                    }
                }
                FileUtil.writeFile(COMPONENT_DIR, new Gson().toJson(componentsList));
                readSettings();
                dialog.dismiss();
            });
            dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
            dialog.show();
        } else {
            HashMap<String, Object> map = list.get(0);
            if (ComponentsHandler.isValidComponent(map)) {
                componentsList.add(map);
                FileUtil.writeFile(COMPONENT_DIR, new Gson().toJson(componentsList));
                readSettings();
            } else {
                SketchwareUtil.toastError(Helper.getResString(R.string.invalid_component));
            }
        }
    }

    private void save(final HashMap<String, Object> _item) {
        componentsList.remove(_item);
        FileUtil.writeFile(COMPONENT_DIR, new Gson().toJson(componentsList));
    }

    private void export(int position) {
        String componentName = componentsList.get(position).get("name").toString();
        var dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_word_export));
        dialog.a(Helper.getResString(R.string.developer_tools_component_message_export, componentName));
        dialog.a(R.drawable.export_96);
        dialog.b(Helper.getResString(R.string.common_word_yes), view -> {
            String fileName = componentName + ".json";
            String filePath = new File(COMPONENT_EXPORT_DIR, fileName).getAbsolutePath();
            ArrayList<HashMap<String, Object>> list = new ArrayList<>();
            list.add(componentsList.get(position));
            FileUtil.writeFile(filePath, new Gson().toJson(list));
            SketchwareUtil.toast(Helper.getResString(R.string.developer_tools_component_success_message_export, filePath));
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public class ComponentsAdapter extends RecyclerView.Adapter<ComponentsAdapter.ViewHolder> {
        private final ArrayList<HashMap<String, Object>> components;
        private final List<Boolean> collapse;
        private final List<Boolean> confirmation;

        public ComponentsAdapter(ArrayList<HashMap<String, Object>> itemList) {
            this.components = itemList;
            this.collapse = new ArrayList<>(Collections.nCopies(itemList.size(), true));
            this.confirmation = new ArrayList<>(Collections.nCopies(itemList.size(), false));
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.manage_custom_component_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(components.get(position));
            if (holder.isCollapsed()) {
                holder.optionLayout.setVisibility(View.GONE);
                holder.menu.setRotation(0.0f);
            } else {
                holder.optionLayout.setVisibility(View.VISIBLE);
                holder.menu.setRotation(-180.0f);
                if (confirmation.get(position)) {
                    if (holder.shouldAnimateNextTransformation()) {
                        holder.collapsibleComponentLayout.showConfirmation();
                        holder.setAnimateNextTransformation(false);
                    } else {
                        holder.collapsibleComponentLayout.showConfirmationWithoutAnimation();
                    }
                } else {
                    if (holder.shouldAnimateNextTransformation()) {
                        holder.collapsibleComponentLayout.hideConfirmation();
                        holder.setAnimateNextTransformation(false);
                    } else {
                        holder.collapsibleComponentLayout.hideConfirmationWithoutAnimation();
                    }
                }
            }
            holder.optionLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        @Override
        public int getItemCount() {
            return components.size();
        }

        public class ViewHolder extends CollapsibleViewHolder {
            public final LinearLayout root;
            public final LinearLayout optionLayout;
            public final ImageView icon;
            public final TextView type;
            public final TextView id;
            public final ImageView menu;
            public final CollapsibleCustomComponentLayout collapsibleComponentLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView, 200);
                root = (LinearLayout) itemView;
                icon = itemView.findViewById(R.id.img_icon);
                type = itemView.findViewById(R.id.tv_component_type);
                id = itemView.findViewById(R.id.tv_component_id);
                menu = itemView.findViewById(R.id.img_menu);
                optionLayout = itemView.findViewById(R.id.component_option_layout);
                collapsibleComponentLayout = itemView.findViewById(R.id.component_option);
                collapsibleComponentLayout.setButtonOnClickListener(v -> {
                    int lastSelectedItem = getLayoutPosition();
                    if (v instanceof CollapsibleButton button) {
                        int id = button.getButtonId();
                        if (id == 0) {
                            export(lastSelectedItem);
                            confirmation.set(lastSelectedItem, false);
                        } else if (id == 1) {
                            confirmation.set(lastSelectedItem, true);
                        } else {
                            return;
                        }
                        setAnimateNextTransformation(true);
                        notifyItemChanged(lastSelectedItem);
                        return;
                    }
                    int id = v.getId();
                    if (id == R.id.confirm_yes) {
                        save(components.get(lastSelectedItem));
                        confirmation.set(lastSelectedItem, false);
                        notifyItemRemoved(lastSelectedItem);
                        notifyItemRangeChanged(lastSelectedItem, getItemCount());
                    } else if (id == R.id.confirm_no) {
                        confirmation.set(lastSelectedItem, false);
                        setAnimateNextTransformation(true);
                        notifyItemChanged(lastSelectedItem);
                    }
                });
                onDoneInitializingViews();
                root.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), AddCustomComponentActivity.class);
                    intent.putExtra("pos", (int) getLayoutPosition());
                    startActivity(intent);
                });
                setOnClickCollapseConfig(v -> v != root);
            }

            public void bind(HashMap<String, Object> item) {
                type.setText((String) item.get("name"));
                id.setText((String) item.get("id"));
                icon.setImageResource(Integer.parseInt((String) item.get("icon")));
            }

            @Override
            protected boolean isCollapsed() {
                return collapse.get(getLayoutPosition());
            }

            @Override
            protected void setIsCollapsed(boolean isCollapsed) {
                collapse.set(getLayoutPosition(), isCollapsed);
            }

            @NonNull
            @Override
            protected ViewGroup getOptionsLayout() {
                return optionLayout;
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnClickCollapseTriggerViews() {
                return Set.of(menu, root);
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnLongClickCollapseTriggerViews() {
                return Set.of(root);
            }
        }
    }
}
