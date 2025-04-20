package pro.sketchware.fragments.settings.events.details;

import static pro.sketchware.utility.GsonUtils.getGson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.qA;
import mod.hey.studios.util.Helper;
import mod.jbk.util.OldResourceIdMapper;
import pro.sketchware.R;
import pro.sketchware.databinding.FragmentEventsManagerDetailsBinding;
import pro.sketchware.databinding.LayoutEventItemBinding;
import pro.sketchware.fragments.settings.events.EventsManagerConstants;
import pro.sketchware.fragments.settings.events.creator.EventsManagerCreatorFragment;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.UI;

public class EventsManagerDetailsFragment extends qA {

    private final ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    private final String listName;
    private FragmentEventsManagerDetailsBinding binding;

    public EventsManagerDetailsFragment() {
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
        binding.fabNewEvent.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("lis_name", listName);
            EventsManagerCreatorFragment fragment = new EventsManagerCreatorFragment();
            fragment.setArguments(args);
            openFragment(fragment);
        });
        refreshList();
    }

    private void refreshList() {
        listMap.clear();
        if (FileUtil.isExistFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath())) {
            ArrayList<HashMap<String, Object>> events = getGson()
                    .fromJson(FileUtil.readFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < events.size(); i++) {
                if (listName.equals(events.get(i).get("listener"))) {
                    listMap.add(events.get(i));
                }
            }
            binding.eventsRecyclerView.setAdapter(new EventsAdapter(listMap));
            binding.eventsRecyclerView.getAdapter().notifyDataSetChanged();
        }
        Collections.reverse(listMap);
        if (listMap.isEmpty()) {
            binding.noEventsLayout.setVisibility(View.VISIBLE);
        }
    }

    private void deleteItem(int position) {
        listMap.remove(position);
        if (FileUtil.isExistFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath())) {
            ArrayList<HashMap<String, Object>> events = getGson()
                    .fromJson(FileUtil.readFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            for (int i = events.size() - 1; i > -1; i--) {
                if (listName.equals(events.get(i).get("listener"))) {
                    events.remove(i);
                }
            }
            events.addAll(listMap);
            FileUtil.writeFile(EventsManagerConstants.EVENTS_FILE.getAbsolutePath(), getGson().toJson(events));
            refreshList();
        }
    }

    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> dataArray;

        public EventsAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            dataArray = arrayList;
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

            holder.itemView.setBackgroundResource(UI.getShapedBackgroundForList(dataArray, position));

            if (listName.isEmpty()) {
                holder.binding.eventIcon.setImageResource(R.drawable.ic_mtrl_code);
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
            holder.itemView.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putString("lis_name", listName);
                args.putString("event", (String) dataArray.get(position).get("name"));
                args.putString("_pos", String.valueOf(position));
                args.putString("_name", (String) dataArray.get(position).get("name"));
                args.putString("_var", (String) dataArray.get(position).get("var"));
                args.putString("_lis", (String) dataArray.get(position).get("listener"));
                args.putString("_icon", (String) dataArray.get(position).get("icon"));
                args.putString("_desc", (String) dataArray.get(position).get("description"));
                args.putString("_par", (String) dataArray.get(position).get("parameters"));
                args.putString("_spec", (String) dataArray.get(position).get("headerSpec"));
                args.putString("_code", (String) dataArray.get(position).get("code"));
                EventsManagerCreatorFragment fragment = new EventsManagerCreatorFragment();
                fragment.setArguments(args);
                openFragment(fragment);
            });
            holder.itemView.setOnLongClickListener(v -> {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle((String) dataArray.get(position).get("name"))
                        .setMessage("Delete this event?")
                        .setPositiveButton("Delete", (dialog, i) -> deleteItem(position))
                        .setNeutralButton("Edit", (dialog, i) -> {
                            Bundle args = new Bundle();
                            args.putString("lis_name", listName);
                            args.putString("event", (String) dataArray.get(position).get("name"));
                            args.putString("_pos", String.valueOf(position));
                            args.putString("_name", (String) dataArray.get(position).get("name"));
                            args.putString("_var", (String) dataArray.get(position).get("var"));
                            args.putString("_lis", (String) dataArray.get(position).get("listener"));
                            args.putString("_icon", (String) dataArray.get(position).get("icon"));
                            args.putString("_desc", (String) dataArray.get(position).get("description"));
                            args.putString("_par", (String) dataArray.get(position).get("parameters"));
                            args.putString("_spec", (String) dataArray.get(position).get("headerSpec"));
                            args.putString("_code", (String) dataArray.get(position).get("code"));
                            EventsManagerCreatorFragment fragment = new EventsManagerCreatorFragment();
                            fragment.setArguments(args);
                            openFragment(fragment);
                        })
                        .setNegativeButton("Cancel", (di, i) -> di.dismiss())
                        .show();
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
    }
}
