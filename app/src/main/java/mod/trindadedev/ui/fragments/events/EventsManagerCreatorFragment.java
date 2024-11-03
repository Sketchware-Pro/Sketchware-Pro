package mod.trindadedev.ui.fragments.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import pro.sketchware.R;
import pro.sketchware.databinding.FragmentEventsManagerCreatorBinding;

import java.util.ArrayList;
import java.util.HashMap;

import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.IconSelectorDialog;
import mod.jbk.util.OldResourceIdMapper;

public class EventsManagerCreatorFragment extends Fragment {

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
            return !binding.eventsCreatorEventname.getText().toString().isEmpty()
                    && !binding.eventsCreatorSpec.getText().toString().isEmpty()
                    && !binding.eventsCreatorCode.getText().toString().isEmpty();
        } else {
            return !binding.eventsCreatorEventname.getText().toString().isEmpty()
                    && !binding.eventsCreatorVarname.getText().toString().isEmpty()
                    && !binding.eventsCreatorIcon.getText().toString().isEmpty()
                    && !binding.eventsCreatorSpec.getText().toString().isEmpty()
                    && !binding.eventsCreatorCode.getText().toString().isEmpty();
        }
    }

    private void getViewsById() {
        ((View) binding.eventsCreatorListenercode.getParent().getParent()).setVisibility(View.GONE);
        binding.eventsCreatorChooseicon.setImageResource(R.drawable.add_96_blue);
        binding.eventsCreatorCheckbox.setVisibility(View.GONE);
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
        if (!OldResourceIdMapper.isValidIconId(binding.eventsCreatorIcon.getText().toString())) {
            binding.eventsCreatorIconTil.setError("Invalid icon ID");
            binding.eventsCreatorIcon.requestFocus();
            return;
        }
        ArrayList<HashMap<String, Object>> arrayList;
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
        } else {
            arrayList = new ArrayList<>();
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        if (isEdit) {
            hashMap = arrayList.get(figureP(_name));
        }
        hashMap.put("name", binding.eventsCreatorEventname.getText().toString());
        hashMap.put("var", binding.eventsCreatorVarname.getText().toString());
        if (isActivityEvent) {
            hashMap.put("listener", "");
        } else {
            hashMap.put("listener", lisName);
        }
        hashMap.put("icon", binding.eventsCreatorIcon.getText().toString());
        hashMap.put("description", binding.eventsCreatorDesc.getText().toString());
        hashMap.put("parameters", binding.eventsCreatorParams.getText().toString());
        hashMap.put("code", binding.eventsCreatorCode.getText().toString());
        hashMap.put("headerSpec", binding.eventsCreatorSpec.getText().toString());
        if (!isEdit) {
            arrayList.add(hashMap);
        }
        FileUtil.writeFile(concat, new Gson().toJson(arrayList));
        SketchwareUtil.toast("Saved");
        getParentFragmentManager().popBackStack();
    }

    private int figureP(String str) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            ArrayList<HashMap<String, Object>> arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
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
