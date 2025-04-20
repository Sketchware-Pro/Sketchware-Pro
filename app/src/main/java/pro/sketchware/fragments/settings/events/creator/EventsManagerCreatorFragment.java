package pro.sketchware.fragments.settings.events.creator;

import static pro.sketchware.utility.GsonUtils.getGson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.qA;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.IconSelectorDialog;
import mod.jbk.util.OldResourceIdMapper;
import pro.sketchware.R;
import pro.sketchware.databinding.FragmentEventsManagerCreatorBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class EventsManagerCreatorFragment extends qA {

    private String _code;
    private String _desc;
    private String _icon;
    private String _name;
    private String _par;
    private String _spec;
    private String _var;
    private String event_name = "";
    private boolean isActivityEvent;
    private boolean isEdit;
    private String lisName;

    private FragmentEventsManagerCreatorBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("lis_name")) {
            lisName = getArguments().getString("lis_name");
            isActivityEvent = lisName.isEmpty();
        }
        if (getArguments() != null && getArguments().containsKey("event")) {
            event_name = getArguments().getString("event");
            isEdit = true;
            _name = getArguments().getString("_name");
            _var = getArguments().getString("_var");
            _icon = getArguments().getString("_icon");
            _desc = getArguments().getString("_desc");
            _par = getArguments().getString("_par");
            _spec = getArguments().getString("_spec");
            _code = getArguments().getString("_code");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEventsManagerCreatorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setToolbar();
        getViewsById();
        setupViews();

        if (isEdit) {
            fillUp();
        }
        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        return view;
    }

    private void fillUp() {
        binding.eventsCreatorEventname.setText(_name);
        binding.eventsCreatorVarname.setText(_var);
        binding.eventsCreatorIcon.setText(_icon);
        binding.eventsCreatorDesc.setText(_desc);
        binding.eventsCreatorParams.setText(_par);
        binding.eventsCreatorSpec.setText(_spec);
        binding.eventsCreatorCode.setText(_code);
    }

    private boolean filledIn() {
        if (isActivityEvent) {
            return !Helper.getText(binding.eventsCreatorEventname).isEmpty()
                    && !Helper.getText(binding.eventsCreatorSpec).isEmpty()
                    && !Helper.getText(binding.eventsCreatorCode).isEmpty();
        } else {
            return !Helper.getText(binding.eventsCreatorEventname).isEmpty()
                    && !Helper.getText(binding.eventsCreatorVarname).isEmpty()
                    && !Helper.getText(binding.eventsCreatorIcon).isEmpty()
                    && !Helper.getText(binding.eventsCreatorSpec).isEmpty()
                    && !Helper.getText(binding.eventsCreatorCode).isEmpty();
        }
    }

    private void getViewsById() {
        ((View) binding.eventsCreatorListenercode.getParent().getParent()).setVisibility(View.GONE);
        binding.eventsCreatorChooseicon.setImageResource(R.drawable.ic_mtrl_add);
        if (isActivityEvent) {
            binding.eventsCreatorVarname.setText("");
            ((View) binding.eventsCreatorVarname.getParent().getParent()).setVisibility(View.GONE);
            binding.eventsCreatorIcon.setText("2131165298");
            ((View) binding.eventsCreatorIcon.getParent().getParent().getParent()).setVisibility(View.GONE);
        }
        Helper.addClearErrorOnTextChangeTextWatcher(binding.eventsCreatorIconTil);
    }

    private void setupViews() {
        binding.eventsCreatorCancel.setOnClickListener(Helper.getBackPressedClickListener(requireActivity()));
        binding.eventsCreatorSave.setOnClickListener(v -> save());
        binding.eventsCreatorChooseicon.setOnClickListener(v -> showIconSelectorDialog());
    }

    private void showIconSelectorDialog() {
        new IconSelectorDialog(requireActivity(), binding.eventsCreatorIcon).show();
    }

    private void save() {
        if (!filledIn()) {
            SketchwareUtil.toast("Some required fields are empty!");
            return;
        }
        if (!OldResourceIdMapper.isValidIconId(Helper.getText(binding.eventsCreatorIcon))) {
            binding.eventsCreatorIconTil.setError("Invalid icon ID");
            binding.eventsCreatorIcon.requestFocus();
            return;
        }
        ArrayList<HashMap<String, Object>> arrayList;
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            arrayList = getGson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
        } else {
            arrayList = new ArrayList<>();
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        if (isEdit) {
            hashMap = arrayList.get(figureP(_name));
        }
        hashMap.put("name", Helper.getText(binding.eventsCreatorEventname));
        hashMap.put("var", Helper.getText(binding.eventsCreatorVarname));
        if (isActivityEvent) {
            hashMap.put("listener", "");
        } else {
            hashMap.put("listener", lisName);
        }
        hashMap.put("icon", Helper.getText(binding.eventsCreatorIcon));
        hashMap.put("description", Helper.getText(binding.eventsCreatorDesc));
        hashMap.put("parameters", Helper.getText(binding.eventsCreatorParams));
        hashMap.put("code", Helper.getText(binding.eventsCreatorCode));
        hashMap.put("headerSpec", Helper.getText(binding.eventsCreatorSpec));
        if (!isEdit) {
            arrayList.add(hashMap);
        }
        FileUtil.writeFile(concat, getGson().toJson(arrayList));
        SketchwareUtil.toast("Saved");
        getParentFragmentManager().popBackStack();
    }

    private int figureP(String str) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            ArrayList<HashMap<String, Object>> arrayList = getGson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < arrayList.size(); i++) {
                if (str.equals(arrayList.get(i).get("name"))) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void setToolbar() {
        if (isEdit) {
            binding.toolbar.setTitle(event_name);
        } else if (isActivityEvent) {
            binding.toolbar.setTitle("Create a new activity event");
        } else {
            binding.toolbar.setTitle(lisName + "Create a new event");
        }
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(requireActivity()));
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
    }
}
