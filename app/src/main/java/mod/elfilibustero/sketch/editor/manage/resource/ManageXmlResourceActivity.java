package mod.elfilibustero.sketch.editor.manage.resource;

import a.a.a.aB;
import a.a.a.wB;
import a.a.a.Zx;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.CollapsibleViewHolder;
import com.besome.sketch.lib.ui.CollapsibleButton;
import com.google.gson.Gson;
import com.sketchware.remod.databinding.ColorPickerBinding;
import com.sketchware.remod.databinding.ManageXmlResourceBinding;
import com.sketchware.remod.databinding.ManageXmlResourceAddBinding;
import com.sketchware.remod.databinding.ManageXmlResourceListItemBinding;
import com.sketchware.remod.R;

import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import mod.agus.jcoderz.lib.FileUtil;
import mod.elfilibustero.sketch.beans.ResourceXmlBean;
import mod.elfilibustero.sketch.editor.xml.CollapsibleXmlLayout;
import mod.elfilibustero.sketch.lib.utils.ResourceHandler;
import mod.elfilibustero.sketch.lib.utils.StringEscapeUtil;
import mod.elfilibustero.sketch.lib.valid.ResNameValidator;
import mod.hey.studios.util.Helper;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;

public class ManageXmlResourceActivity extends AppCompatActivity {
    private ResourceHandler resourceHandler;
    private String sc_id;
    private int resType = 0;
    private String fileName = "";
    private String resName = "";
    private String resourcePath = "";

    private boolean isResTypeStyle = false;
    private boolean isResTypeStyleItem = false;
    private boolean isResTypeString = false;
    private boolean isResTypeColor = false;

    private int itemPosition = 0;

    private List<Map<String, Object>> resources = new ArrayList<>();
    private List<Map<String, Object>> items = new ArrayList<>();
    private Map<String, Object> style = new HashMap<>();
    private List<String> alreadyUsed = new ArrayList<>();

    private ManageXmlResourceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageXmlResourceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        sc_id = getIntent().getStringExtra("sc_id");
        resType = getIntent().getIntExtra("type", 0);
        fileName = ResourceXmlBean.getResFileName(resType);
        resName = Helper.getResString(ResourceXmlBean.getXmlResName(resType));
        resourcePath = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/injection/resource/" + fileName;
        resourceHandler = new ResourceHandler(this, sc_id);
        switch (resType) {
            case ResourceXmlBean.RES_TYPE_STRING -> {
                isResTypeString = true;
            }
            case ResourceXmlBean.RES_TYPE_COLOR -> {
                isResTypeColor = true;
            }
            case ResourceXmlBean.RES_TYPE_STYLE -> {
                isResTypeStyle = true;
            }
            case ResourceXmlBean.RES_TYPE_STYLE_ITEM -> {
                isResTypeStyleItem = true;
                itemPosition = getIntent().getIntExtra("position", 0);
                resName = "item";
            }
        }

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(ResourceXmlBean.getActivityTitle(resType));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        binding.fab.setOnClickListener(_view -> {
            if (isResTypeString) {
                showStringDialog(null, null, true, null, null, false);
            } else if (isResTypeColor) {
                showColorDialog(null, null, null, null, false);
            } else {
                showStyleDialog(null, null, null, null, false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        readSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        var item = menu.add(Menu.NONE, 0, Menu.NONE, Helper.getResString(R.string.design_drawer_menu_title_source_code));
        item.setIcon(R.drawable.code_white_48);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            showCurrentActivitySrcCode();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save(String content, String path) {
        FileUtil.writeFile(path, content);
        readSettings();
    }

    private void delete(Map<String, Object> _item) {
        if (isResTypeStyleItem) {
            items.remove(_item);
            style.put("items", items);
            resources.set(itemPosition, style);
        } else {
            resources.remove(_item);
        }
        save(new Gson().toJson(resources), resourcePath);
    }

    private void readSettings() {
        if (FileUtil.isExistFile(resourcePath)) {
            resources = resourceHandler.parseResourceFile(fileName);
        }

        if (resources == null || resources.isEmpty()) {
            resources = switch (resType) {
                case ResourceXmlBean.RES_TYPE_STRING -> resourceHandler.getDefaultString();
                case ResourceXmlBean.RES_TYPE_COLOR -> resourceHandler.getDefaultColor();
                case ResourceXmlBean.RES_TYPE_STYLE, ResourceXmlBean.RES_TYPE_STYLE_ITEM -> resourceHandler.getDefaultStyle();
                default -> new ArrayList<>();
            };
        }

        if (resType == ResourceXmlBean.RES_TYPE_STYLE_ITEM) {
            style = resources.get(itemPosition);
            if (style.isEmpty() || style.containsKey("items")) {
                items = (List<Map<String, Object>>) style.get("items");
            }
            getSupportActionBar().setTitle((String) style.get("name"));
        }
        var recyclerView = binding.recyclerView;
        var adapter = new Adapter(isResTypeStyleItem ? items : resources);
        var state = recyclerView.getLayoutManager().onSaveInstanceState();
        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutManager().onRestoreInstanceState(state);
        adapter.notifyDataSetChanged();
    }

    private List<String> getNames(String name) {
        List<Map<String, Object>> lists = isResTypeStyleItem ? items : resources;
        List<String> names = resources.stream()
            .map(resource -> (String) resource.get("name"))
            .collect(Collectors.toList());
        if (name != null || names.contains(name)) {
            names.remove(name);
        }
        return names;
    }

    private void showCurrentActivitySrcCode() {
        String source = switch (resType) {
            case ResourceXmlBean.RES_TYPE_STRING ->
                resourceHandler.getXmlString();
            case ResourceXmlBean.RES_TYPE_COLOR ->
                resourceHandler.getXmlColor();
            case ResourceXmlBean.RES_TYPE_STYLE, ResourceXmlBean.RES_TYPE_STYLE_ITEM ->
                resourceHandler.getXmlStyle();
            default ->
                Helper.getResString(R.string.invalid_generated_source);
        };

        var dialog = new aB(this);
        dialog.b(fileName + ".xml");
        dialog.a(ResourceXmlBean.getXmlIcon(resType));
        dialog.b(Helper.getResString(R.string.common_word_close), Helper.getDialogDismissListener(dialog));
        var editor = new CodeEditor(this);
        editor.setTypefaceText(Typeface.MONOSPACE);
        editor.setEditable(false);
        editor.setTextSize(14);
        editor.setText(source);
        editor.getComponent(Magnifier.class).setWithinEditorForcibly(true);
        editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
        editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
        dialog.a(editor);
        dialog.show();
    }

    private void showStringDialog(String _name, String _value, boolean _translatable, Integer _position, Integer _insertPosition, boolean _edit) {
        showDialog(_edit, _name, _value, _position, _insertPosition, _translatable);
    }

    private void showColorDialog(String _name, String _value, Integer _position, Integer _insertPosition, boolean _edit) {
        showDialog(_edit, _name, _value, _position, _insertPosition, null);
    }

    private void showStyleDialog(String _name, String _value, Integer _position, Integer _insertPosition, boolean _edit) {
        showDialog(_edit, _name, _value, _position, _insertPosition, null);
    }

    private void showDialog(boolean editMode, String _name, String _value, Integer _position, Integer _insertPosition, Boolean _translatable) {
        var dialog = new aB(this);
        var prefix = editMode ? "Edit " : "Add new ";
        dialog.b(prefix + resName.toLowerCase());
        dialog.a(ResourceXmlBean.getXmlIcon(resType));
        var binding = ManageXmlResourceAddBinding.inflate(getLayoutInflater());
        var name = binding.name;
        var value = binding.value;
        var picker = binding.picker;
        var check = binding.check;
        name.setHint("Enter name");
        value.setHint("Enter " + (isResTypeStyle ? "parent" : "value"));
        ResNameValidator validator = new ResNameValidator(this, binding.tiName, getNames(_name), resType);
        name.setText(_name);
        value.setText(isResTypeString ? StringEscapeUtil.unescapeXML(_value) : _value);
        if (isResTypeString) binding.translatableLayout.setVisibility(View.VISIBLE);
        if (isResTypeColor) {
            picker.setVisibility(View.VISIBLE);
            picker.setOnClickListener(showColorPickerDialog(dialog, value));
        }
        if (_translatable != null) check.setChecked(!_translatable);

        dialog.a(binding.getRoot());
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            String inputName = name.getText().toString();
            String inputValue = value.getText().toString();
            boolean translatable = !check.isChecked();

            if (inputName.isEmpty()) {
                validator.b.requestFocus();
                validator.b.setError(Helper.getResString(R.string.invalid_name_cannot_be_empty));
                return;
            }

            if (!validator.isValid()) {
                validator.b.requestFocus();
                return;
            }

            if (!editMode) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", inputName);
                if (isResTypeStyle) {
                    map.put("parent", inputValue);
                    map.put("items", new ArrayList<>());
                } else {
                    map.put("value", isResTypeString ? StringEscapeUtil.escapeXML(inputValue) : inputValue);
                    if (isResTypeString) {
                        map.put("translatable", translatable);
                    }
                }
                if (_insertPosition == null) {
                    if (isResTypeStyleItem) {
                        items.add(map);
                    } else {
                        resources.add(map);
                    }
                } else {
                    if (isResTypeStyleItem) {
                        items.add(_insertPosition, map);
                    } else {
                        resources.add(_insertPosition, map);
                    }
                    
                }
                if (isResTypeStyleItem) resources.get(itemPosition).put("items", items);
            } else {
                if (isResTypeStyleItem) {
                    items.get(_position).put("name", inputName);
                    items.get(_position).put("value", inputValue);
                    style.put("items", items);
                    resources.set(itemPosition, style);
                } else {
                    resources.get(_position).put("name", inputName);
                    resources.get(_position).put(isResTypeStyle ? "parent" : "value", inputValue);
                    if (isResTypeString) {
                        resources.get(_position).put("translatable", translatable);
                    }
                }

            }
            save(new Gson().toJson(resources), resourcePath);
            dialog.dismiss();
        });

        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private View.OnClickListener showColorPickerDialog(Dialog dialog, EditText value) {
        return v -> {
            var binding = ColorPickerBinding.inflate(getLayoutInflater());
            var view = binding.getRoot();
            var zx = new Zx(view, this, 0, true, false);
            zx.a(color -> {
                if (dialog != null) dialog.show();
                value.setText(String.format("#%08X", color));
            });
            zx.setAnimationStyle(R.anim.abc_fade_in);
            zx.showAtLocation(view, Gravity.CENTER, 0, 0);
            dialog.hide();
        };
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private List<Map<String, Object>> data;
        private List<Boolean> collapse;
        private List<Boolean> confirmation;

        public Adapter(List<Map<String, Object>> itemList) {
            data = itemList;
            collapse = new ArrayList<>(Collections.nCopies(itemList.size(), true));
            confirmation = new ArrayList<>(Collections.nCopies(itemList.size(), false));
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ManageXmlResourceListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            var binding = holder.binding;
            if (data.get(position).containsKey("name")) {
                binding.title.setText((String) data.get(position).get("name"));
            }
            if (resType == ResourceXmlBean.RES_TYPE_STYLE) {
                if (data.get(position).containsKey("parent")) {
                    binding.desc.setText((String) data.get(position).get("parent"));
                }
            } else {
                if (data.get(position).containsKey("value")) {
                    String value = (String) data.get(position).get("value");
                    binding.desc.setText(isResTypeString ? StringEscapeUtil.unescapeXML(value) : value);
                }
            }

            if (holder.isCollapsed()) {
                binding.optionLayout.setVisibility(View.GONE);
                binding.menu.setRotation(0.0f);
            } else {
                binding.optionLayout.setVisibility(View.VISIBLE);
                binding.menu.setRotation(-180.0f);
                if (confirmation.get(position)) {
                    if (holder.shouldAnimateNextTransformation()) {
                        binding.collapsible.showConfirmation();
                        holder.setAnimateNextTransformation(false);
                    } else {
                        binding.collapsible.showConfirmationWithoutAnimation();
                    }
                } else {
                    if (holder.shouldAnimateNextTransformation()) {
                        binding.collapsible.hideConfirmation();
                        holder.setAnimateNextTransformation(false);
                    } else {
                        binding.collapsible.hideConfirmationWithoutAnimation();
                    }
                }
            }
            binding.optionLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends CollapsibleViewHolder {
            private final ManageXmlResourceListItemBinding binding;
            private final LinearLayout root;

            public ViewHolder(@NonNull ManageXmlResourceListItemBinding binding) {
                super(binding.getRoot(), 200);
                this.binding = binding;
                this.root = binding.getRoot();

                if (!isResTypeStyle) binding.collapsible.getEditButton().setVisibility(View.GONE);

                binding.collapsible.setButtonOnClickListener(v -> {
                    int lastSelectedItem = getLayoutPosition();
                    if (v instanceof CollapsibleButton) {
                        switch (((CollapsibleButton) v).getButtonId()) {
                            case 0:
                                String resName = (String) data.get(lastSelectedItem).get("name");
                                String resParent = (String) data.get(lastSelectedItem).get("parent");
                                if (isResTypeStyle) {
                                    showStyleDialog(resName, resParent, lastSelectedItem, null, true);
                                }
                                break;
                            case 1:
                                if (isResTypeStyle || isResTypeStyleItem) {
                                    showStyleDialog(null, null, null, lastSelectedItem, false);
                                } else if (isResTypeColor) {
                                    showColorDialog(null, null, null, lastSelectedItem, false);
                                } else if (isResTypeString) {
                                    showStringDialog(null, null, true, null, lastSelectedItem, false);
                                }
                                break;
                            case 2:
                                confirmation.set(lastSelectedItem, true);
                                setAnimateNextTransformation(true);
                                notifyItemChanged(lastSelectedItem);
                                break;
                        }
                        return;
                    }
                    int id = v.getId();
                    if (id == R.id.confirm_yes) {
                        delete(data.get(lastSelectedItem));
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
                root.setOnClickListener(_v -> {
                    int lastSelectedItem = getLayoutPosition();
                    if (isResTypeStyle) {
                        Intent i = new Intent(getApplicationContext(), ManageXmlResourceActivity.class);
                        i.putExtra("sc_id", sc_id);
                        i.putExtra("type", ResourceXmlBean.RES_TYPE_STYLE_ITEM);
                        i.putExtra("position", lastSelectedItem);
                        startActivity(i);
                    } else {
                        String resName = (String) data.get(lastSelectedItem).get("name");
                        String resValue = (String) data.get(lastSelectedItem).get("value");
                        if (isResTypeColor) {
                            showColorDialog(resName, resValue, lastSelectedItem, null, true);
                        } else if (isResTypeString) {
                            showStringDialog(resName, resValue, (boolean) data.get(lastSelectedItem).get("translatable"), lastSelectedItem, null, true);
                        } else if (isResTypeStyleItem) {
                            showStyleDialog(resName, resValue, lastSelectedItem, null, true);
                        }
                    }
                });
                setOnClickCollapseConfig(v -> v != root);
                binding.icon.setImageResource(ResourceXmlBean.getXmlIcon(resType));
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
                return binding.optionLayout;
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnClickCollapseTriggerViews() {
                return Set.of(binding.menu, root);
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnLongClickCollapseTriggerViews() {
                return Set.of(root);
            }
        }
    }
}
