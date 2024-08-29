package mod.trindadedev.ui.fragments.events.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.LayoutEventItemBinding;
import com.sketchware.remod.databinding.DialogAddNewListenerBinding;

import mod.trindadedev.ui.fragments.events.EventsManagerFragment;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

import java.util.ArrayList;
import java.util.HashMap;

public class ListenersAdapter extends RecyclerView.Adapter<ListenersAdapter.ViewHolder> {

     private final ArrayList<HashMap<String, Object>> dataArray;
     private final Context context;

     public ListenersAdapter(ArrayList<HashMap<String, Object>> arrayList, Context context) {
          dataArray = arrayList;
          this.context = context;
     }
 
     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          LayoutEventItemBinding binding = LayoutEventItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
          return new ViewHolder(binding);
     }

     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          HashMap<String, Object> item = dataArray.get(position);
          String name = (String) item.get("name");
 
          holder.binding.eventIcon.setImageResource(R.drawable.event_on_response_48dp);
          ((LinearLayout) holder.binding.eventIcon.getParent()).setGravity(Gravity.CENTER);
 
          holder.binding.eventTitle.setText(name);
          holder.binding.eventSubtitle.setText(getNumOfEvents(name));

          holder.binding.eventCard.setOnClickListener(v -> {});

          holder.binding.eventCard.setOnLongClickListener(v -> {
              new MaterialAlertDialogBuilder(context)
                  .setTitle(name)
                  .setItems(new String[]{"Edit", "Export", "Delete"}, (dialog, which) -> {
                      switch (which) {
                          case 0:
                              editItemDialog(position);
                              break;
                          case 1:
                              // TO-DO: export(position);
                              break;
                          case 2:
                              // TO-DO deleteRelatedEvents(name);
                              // TO-DO deleteItem(position);
                              break;
                          default:
                      }
                  }).show();
              return true;
          });
     }

     @Override
     public int getItemCount() {
          return dataArray.size();
     }

     public static class ViewHolder extends RecyclerView.ViewHolder {
          private final LayoutEventItemBinding binding;
        
          public ViewHolder(@NonNull LayoutEventItemBinding binding) {
               super(binding.getRoot());
               this.binding = binding;
          }
     }

     public static String getNumOfEvents(String name) {
          int eventAmount;
          if (FileUtil.isExistFile(EventsManagerFragment.EVENTS_FILE.getAbsolutePath())) {
              ArrayList<HashMap<String, Object>> events = new Gson()
                      .fromJson(FileUtil.readFile(EventsManagerFragment.EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
              eventAmount = 0;
              for (int i = 0; i < events.size(); i++) {
                  if (events.get(i).get("listener").toString().equals(name)) {
                       eventAmount++;
                  }
              }
          } else {
              eventAmount = 0;
          }
          return "Events: " + eventAmount;
     }
    
     private void showEditListenerDialog() {
          var listenerBinding = DialogAddNewListenerBinding.inflate(LayoutInflater.from(context));
          listenerBinding.listenerName.setText(dataArray.get(position).get("name").toString());
          listenerBinding.listenerCode.setText(dataArray.get(position).get("code").toString());
          listenerBinding.listenerCustomImport.setText(dataArray.get(position).get("imports").toString());
          if (dataArray.get(position).containsKey("s") && dataArray.get(position).get("s").toString().equals("true")) {
               listenerBinding.listenerIsIndependentClassOrMethod.setChecked(true);
               ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(dataArray.get(position).get("code").toString().split("\n")));
               if (arrayList.get(0).contains("//" + listenerBinding.listenerName.getText().toString())) {
                    arrayList.remove(0);
               }
              String value = "";
               for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (value.equals("")) {
                        value = arrayList.get(i2);
                    } else {
                        value = value.concat("\n").concat(arrayList.get(i2));
                    }
               }
               listenerBinding.listenerCode.setText(value);
          }
          var dialog = new MaterialAlertDialogBuilder(context)
               .setTitle("New Listener")
               .setMessage("Type info of new listener")
               .setView(listenerBinding.getRoot())
               .setPositiveButton("Save", (di, i) -> { 
                    if (!listenerBinding.listenerName.getText().toString().equals("")) {
                         HashMap<String, Object> hashMap = dataArray.get(position);
                         //TO-DO: overrideEvents((String) hashMap.get("name"), listenerBinding.listenerName.getText().toString());
                         hashMap.put("name", listenerBinding.listenerName.getText().toString());
                         if (listenerBinding.listenerIsIndependentClassOrMethod.isChecked()) {
                               hashMap.put("code", "//" + listenerBinding.listenerName.getText().toString() + "\n" + listenerBinding.listenerCode.getText().toString());
                               hashMap.put("s", "true");
                         } else {
                               hashMap.put("code", listenerBinding.listenerCode.getText().toString());
                               hashMap.put("s", "false");
                         }
                         hashMap.put("imports", listenerBinding.listenerCustomImport.getText().toString());
                         FileUtil.writeFile(EventsManagerFragment.LISTENERS_FILE.getAbsolutePath(), new Gson().toJson(dataArray));
                         EventsManagerFragment.refreshList(dataArray);
                         dialog.dismiss();
                         return;
                    }
                    SketchwareUtil.toastError("Invalid name!");
               })
               .setNegativeButton("Cancel", (di, i) -> di.dismiss()).create();
          dialog.show();
     }
     
     private void overrideEvents(String before, String after) {
          ArrayList<HashMap<String, Object>> events = new ArrayList<>();
          if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
              events = new Gson()
                    .fromJson(FileUtil.readFile(EventsManagerFragment.EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
              for (int i = 0; i < events.size(); i++) {
                   if (events.get(i).get("listener").toString().equals(before)) {
                       events.get(i).put("listener", after);
                   }
              }
          }
          FileUtil.writeFile(EventsManagerFragment.EVENTS_FILE.getAbsolutePath(), new Gson().toJson(events));
     }
}
