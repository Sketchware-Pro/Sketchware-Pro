package mod.trindadedev.ui.fragments.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.FragmentEventsManagerDetailsBinding;
import com.sketchware.remod.databinding.LayoutEventItemBinding;

import mod.trindadedev.ui.fragments.BaseFragment;
import mod.jbk.util.OldResourceIdMapper;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

import java.util.HashMap;
import java.util.ArrayList;

public class EventsManagerDetailsFragment extends BaseFragment {
     
     private final ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
     private String listName;
     private FragmentEventsManagerDetailsBinding binding;
     
     public EventsManagerDetailsFragment () {
          listName = "";
     }
     
     public EventsManagerDetailsFragment(String listName) {
          this.listName = listName;
     }

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          binding = FragmentEventsManagerDetailsBinding.inflate(inflater, container, false);
          return binding.getRoot();
     }

     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
          super.onViewCreated(view, savedInstanceState);
          configureToolbar(binding.toolbar);
          refreshList();
     }
     
     private void refreshList() {
          listMap.clear();
          if (FileUtil.isExistFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath())) {
                ArrayList<HashMap<String, Object>> events = new Gson()
                       .fromJson(FileUtil.readFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < events.size(); i++) {
                    if (listName.equals(events.get(i).get("listener"))) {
                        listMap.add(events.get(i));
                    }
                }
                binding.eventsRecyclerView.setAdapter(new EventsAdapter(listMap, requireContext()));
                binding.eventsRecyclerView.getAdapter().notifyDataSetChanged();
          }
    }

    private void deleteItem(int position) {
          listMap.remove(position);
          if (FileUtil.isExistFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath())) {
               ArrayList<HashMap<String, Object>> events = new Gson()
                   .fromJson(FileUtil.readFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
               for (int i = events.size() - 1; i > -1; i--) {
                   if (listName.equals(events.get(i).get("listener"))) {
                        events.remove(i);
                   }
               }
               events.addAll(listMap);
               FileUtil.writeFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath(), new Gson().toJson(events));
               refreshList();
           }
    }
     
    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

         private final ArrayList<HashMap<String, Object>> dataArray;
         private final Context context;

         public EventsAdapter(ArrayList<HashMap<String, Object>> arrayList, Context context) {
              this.dataArray = arrayList;
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

              if (listName.equals("")) {
                  holder.binding.eventIcon.setImageResource(R.drawable.widget_source);
              } else {
                  int imgRes = Integer.parseInt((String) dataArray.get(position).get("icon"));
                  holder.binding.eventIcon.setImageResource(OldResourceIdMapper.getDrawableFromOldResourceId(imgRes));
              }
               
              holder.binding.eventTitle.setText((String) item.get("name"));
              if ("".equals(dataArray.get(position).get("var"))) {
                   holder.binding.eventSubtitle.setText("Activity event");
              } else {
                   holder.binding.eventSubtitle.setText((String) dataArray.get(position).get("var"));
              }
              holder.binding.eventCard.setOnClickListener(v -> {});
         }

         @Override
         public int getItemCount() {
              return dataArray.size();
         }

         public class ViewHolder extends RecyclerView.ViewHolder {
              private final LayoutEventItemBinding binding;

              public ViewHolder(@NonNull LayoutEventItemBinding binding) {
                   super(binding.getRoot());
                   this.binding = binding;
              }
         }
    }
}
