package mod.trindadedev.ui.fragments.events;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.DialogAddNewListenerBinding;
import com.sketchware.remod.databinding.FragmentEventsManagerBinding;

import mod.trindadedev.ui.fragments.events.adapters.ListenersAdapter;
import mod.trindadedev.ui.fragments.BaseFragment;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;

public class EventsManagerFragment extends BaseFragment {

     private FragmentEventsManagerBinding binding;
     
     public static final File EVENT_EXPORT_LOCATION = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/data/system/export/events/");
     public static final File EVENTS_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/data/system/events.json");
     public static final File LISTENERS_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/data/system/listeners.json");
     private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          binding = FragmentEventsManagerBinding.inflate(inflater, container, false);
          return binding.getRoot();
     }

     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
          super.onViewCreated(view, savedInstanceState);
          configureToolbar(binding.toolbar);
          binding.activityEventsCard.setOnClickListener(v -> openFragment(new EventsManagerFragment()));
          binding.fabNewListener.setOnClickListener(v -> showAddNewListenerDialog());
     }
     
     private void showAddNewListenerDialog() {
          var listenerBinding = DialogAddNewListenerBinding.inflate(LayoutInflater.from(requireContext()));
          var dialog = new MaterialAlertDialogBuilder(requireContext())
               .setTitle("New Listener")
               .setMessage("Type info of new listener")
               .setView(listenerBinding.getRoot())
               .setPositiveButton("Save", (di, i) -> {
                     if (!listenerBinding.listenerName.getText().toString().equals("")) {
                          HashMap<String, Object> hashMap = new HashMap<>();
                          hashMap.put("name", listenerBinding.listenerName.getText().toString());
                          if (listenerBinding.listenerIsIndependentClassOrMethod.isChecked()) {
                               hashMap.put("code", "//" + listenerBinding.listenerName.getText().toString() + "\n" + listenerBinding.listenerCode.getText().toString());
                               hashMap.put("s", "true");
                          } else {
                              hashMap.put("code", listenerBinding.listenerCode.getText().toString());
                              hashMap.put("s", "false");
                          }
                          hashMap.put("imports", listenerBinding.listenerCustomImport.getText().toString());
                          listMap.add(hashMap);
                     }                     
                     /* TO-DO: addItem(); */
                     di.dismiss();
                     return;
               })
               .setNegativeButton("Cancel", (di, i) -> di.dismiss()).create();
          dialog.show();
          SketchwareUtil.toastError("Invalid name!");
     }
     
     private void refreshList() {
          listMap.clear();
          if (FileUtil.isExistFile(LISTENERS_FILE.getAbsolutePath())) {
               listMap = new Gson().fromJson(FileUtil.readFile(LISTENERS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
               binding.listenersList.setAdapter(new ListenersAdapter(listMap, requireContext()));
               binding.listenersList.getAdapter().notifyDataSetChanged();
          }
     }
     
     private void addListenerItem() {
        FileUtil.writeFile(LISTENERS_FILE.getAbsolutePath(), new Gson().toJson(listMap));
        refreshList();
     }
}