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
import com.sketchware.remod.databinding.CustomViewProBinding;

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
        CustomViewProBinding binding = CustomViewProBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, Object> item = dataArray.get(position);
        String name = (String) item.get("name");

        holder.binding.customViewProImg.setImageResource(R.drawable.event_on_response_48dp);
        ((LinearLayout) holder.binding.customViewProImg.getParent()).setGravity(Gravity.CENTER);

        holder.binding.customViewProTitle.setText(name);
        holder.binding.customViewProSubtitle.setText(getNumOfEvents(name));

        holder.linearLayout.setOnClickListener(v -> {});

        holder.linearLayout.setOnLongClickListener(v -> {
            new MaterialAlertDialogBuilder(context)
                .setTitle(name)
                .setItems(new String[]{"Edit", "Export", "Delete"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            // TO-DO editItemDialog(position);
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
        private final CustomViewProBinding binding;
        private final LinearLayout linearLayout;

        public ViewHolder(@NonNull CustomViewProBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.linearLayout = binding.customViewProBackground;
        }
    }

    private String getNumOfEvents(String name) {
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
        return context.getResources().getString(R.string.tools_events_);
    }
}
