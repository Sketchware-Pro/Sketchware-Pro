package pro.sketchware.activities.resources.editors.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import a.a.a.aB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.resources.editors.ResourcesEditorActivity;
import pro.sketchware.activities.resources.editors.utils.StringsEditorManager;
import pro.sketchware.databinding.ResourcesEditorFragmentBinding;
import pro.sketchware.databinding.ViewStringEditorAddBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.XmlUtil;
import pro.sketchware.activities.resources.editors.adapters.StringsAdapter;

public class StringsEditor extends Fragment {

    private final ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
    private ResourcesEditorFragmentBinding binding;
    public StringsAdapter adapter;
    public String filePath;

    public final StringsEditorManager stringsEditorManager = new StringsEditorManager();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResourcesEditorFragmentBinding.inflate(inflater, container, false);
        initialize();
        updateStringsList(filePath);
        ((ResourcesEditorActivity) requireActivity()).checkForInvalidResources();
        return binding.getRoot();
    }

    private void initialize() {
        filePath = ((ResourcesEditorActivity) requireActivity()).stringsFilePath;
    }

    public void updateStringsList(String filePath) {
        stringsEditorManager.convertXmlStringsToListMap(FileUtil.readFileIfExist(filePath), listmap);
        adapter = new StringsAdapter(((ResourcesEditorActivity) requireActivity()), listmap);
        binding.recyclerView.setAdapter(adapter);
        updateNoContentLayout();
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

    public boolean checkForUnsavedChanges() {
        if (!FileUtil.isExistFile(filePath) && listmap.isEmpty()) {
            return false;
        }
        ArrayList<HashMap<String, Object>> cache = new ArrayList<>();
        stringsEditorManager.convertXmlStringsToListMap(FileUtil.readFileIfExist(filePath), cache);
        String cacheString = new Gson().toJson(cache);
        String cacheListmap = new Gson().toJson(listmap);
        return !cacheListmap.equals(cacheString) && !listmap.isEmpty();
    }

    public void addStringDialog() {
        aB dialog = new aB(requireActivity());
        ViewStringEditorAddBinding binding = ViewStringEditorAddBinding.inflate(getLayoutInflater());
        dialog.b("Create new string");
        dialog.b("Create", v1 -> {
            String key = Objects.requireNonNull(binding.stringKeyInput.getText()).toString();
            String value = Objects.requireNonNull(binding.stringValueInput.getText()).toString();

            if (key.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toast("Please fill in all fields", Toast.LENGTH_SHORT);
                return;
            }

            if (stringsEditorManager.isXmlStringsExist(listmap, key)) {
                binding.stringKeyInputLayout.setError("\"" + key + "\" is already exist");
                return;
            }
            addString(key, value);
        });
        dialog.a(getString(R.string.cancel), v1 -> dialog.dismiss());
        dialog.a(binding.getRoot());
        dialog.show();
    }

    public void addString(final String key, final String text) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("text", text);
        if (listmap.isEmpty()) {
            listmap.add(map);
            adapter.notifyItemInserted(0);
            return;
        }
        for (int i = 0; i < listmap.size(); i++) {
            if (Objects.equals(listmap.get(i).get("key"), key)) {
                listmap.set(i, map);
                adapter.notifyItemChanged(i);
                return;
            }
        }
        listmap.add(map);
        adapter.notifyItemInserted(listmap.size() - 1);
        updateNoContentLayout();
    }

    public void saveStringsFile() {
        if (FileUtil.isExistFile(filePath) || !listmap.isEmpty()) {
            XmlUtil.saveXml(filePath, stringsEditorManager.convertListMapToXmlStrings(listmap));
        }
    }
}
