package pro.sketchware.activities.resourceseditor.components.fragments;

import static pro.sketchware.utility.UI.animateLayoutChanges;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.resourceseditor.ResourcesEditorActivity;
import pro.sketchware.activities.resourceseditor.components.adapters.StringsAdapter;
import pro.sketchware.activities.resourceseditor.components.utils.StringsEditorManager;
import pro.sketchware.databinding.ResourcesEditorFragmentBinding;
import pro.sketchware.databinding.ViewStringEditorAddBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.XmlUtil;

public class StringsEditor extends Fragment {

    private ResourcesEditorFragmentBinding binding;

    private final ResourcesEditorActivity activity;

    public StringsAdapter adapter;

    public final ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
    private HashMap<Integer, String> notesMap = new HashMap<>();

    public boolean hasUnsavedChanges;
    public String filePath;

    public final StringsEditorManager stringsEditorManager = new StringsEditorManager();

    public StringsEditor(ResourcesEditorActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResourcesEditorFragmentBinding.inflate(inflater, container, false);
        stringsEditorManager.sc_id = activity.sc_id;
        return binding.getRoot();
    }

    public void updateStringsList(String filePath, int updateMode, boolean hasUnsavedChangesStatus) {
        this.filePath = filePath;
        hasUnsavedChanges = hasUnsavedChangesStatus;

        boolean isSkippingMode = updateMode == 1;
        boolean isMergeAndReplace = updateMode == 2;
        stringsEditorManager.isDefaultVariant = activity.variant.isEmpty();

        ArrayList<HashMap<String, Object>> defaultStrings = new ArrayList<>();
        if ((activity.variant.isEmpty() || hasUnsavedChanges) && !FileUtil.isExistFile(filePath)) {
            String generatedContent = activity.yq.getXMLString();
            stringsEditorManager.convertXmlStringsToListMap(generatedContent, defaultStrings);
        } else {
            stringsEditorManager.convertXmlStringsToListMap(FileUtil.readFileIfExist(filePath), defaultStrings);
        }
        notesMap = new HashMap<>(stringsEditorManager.notesMap);

        if (isSkippingMode) {
            HashSet<String> existingKeys = new HashSet<>();
            for (HashMap<String, Object> existingMap : listmap) {
                existingKeys.add((String) existingMap.get("key"));
            }

            for (HashMap<String, Object> stringMap : defaultStrings) {
                String key = (String) stringMap.get("key");
                if (existingKeys.add(key)) {
                    listmap.add(stringMap);
                }
            }
        } else {
            if (isMergeAndReplace) {
                HashSet<String> newKeys = new HashSet<>();
                for (HashMap<String, Object> stringMap : defaultStrings) {
                    newKeys.add((String) stringMap.get("key"));
                }

                listmap.removeIf(existingMap -> newKeys.contains((String) existingMap.get("key")));
            } else {
                listmap.clear();
            }
            listmap.addAll(defaultStrings);
        }

        activity.runOnUiThread(() -> {
            adapter = new StringsAdapter(activity, listmap, notesMap);
            binding.recyclerView.setAdapter(adapter);
            activity.checkForInvalidResources();
            updateNoContentLayout();
            if (hasUnsavedChanges) {
                this.filePath = activity.stringsFilePath;
            }
        });
    }

    public void updateNoContentLayout() {
        if (listmap.isEmpty()) {
            binding.noContentLayout.setVisibility(View.VISIBLE);
            binding.noContentTitle.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_title), "Strings"));
            binding.noContentBody.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_body), "string"));
        } else {
            binding.noContentLayout.setVisibility(View.GONE);
        }
    }

    public void showAddStringDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        ViewStringEditorAddBinding binding = ViewStringEditorAddBinding.inflate(getLayoutInflater());
        dialog.setTitle("Create new string");
        binding.stringKeyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                animateLayoutChanges(binding.getRoot());
                binding.importantNote.setVisibility(s.toString().equals("app_name") ? View.VISIBLE : View.GONE);
            }
        });
        dialog.setPositiveButton("Create", (d, which) -> {
            String key = Objects.requireNonNull(binding.stringKeyInput.getText()).toString();
            String value = Objects.requireNonNull(binding.stringValueInput.getText()).toString();

            if (key.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toastError("Please fill in all fields");
                return;
            }

            if (stringsEditorManager.isXmlStringsExist(listmap, key)) {
                SketchwareUtil.toastError("\"" + key + "\" is already exist");
                return;
            }
            addString(key, value, Objects.requireNonNull(binding.stringHeaderInput.getText()).toString().trim());
            updateNoContentLayout();
        });
        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    public void addString(final String key, final String text, String note) {
        hasUnsavedChanges = true;
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("text", text);
        if (listmap.isEmpty()) {
            listmap.add(map);
            adapter.notifyItemInserted(0);
            if (!note.isEmpty()) {
                notesMap.put(0, note);
            }
            return;
        }
        listmap.add(map);
        int notifyPosition = listmap.size() - 1;
        if (!note.isEmpty()) {
            notesMap.put(notifyPosition, note);
        }
        adapter.notifyItemInserted(notifyPosition);
    }

    public void saveStringsFile() {
        if (hasUnsavedChanges) {
            XmlUtil.saveXml(filePath, stringsEditorManager.convertListMapToXmlStrings(listmap, notesMap));
            hasUnsavedChanges = false;
        }
    }
}
