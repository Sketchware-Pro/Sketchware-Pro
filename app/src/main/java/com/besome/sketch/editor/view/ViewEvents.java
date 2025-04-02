package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import a.a.a.Qs;
import a.a.a.bB;
import a.a.a.jC;
import a.a.a.oq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.EventGridItemBinding;

public class ViewEvents extends LinearLayout {
    private final ArrayList<EventBean> events = new ArrayList<>();
    private final EventAdapter eventAdapter = new EventAdapter();

    private String sc_id;
    private ProjectFileBean projectFileBean;
    private Qs onEventClickListener;

    public ViewEvents(Context context) {
        this(context, null);
    }

    public ViewEvents(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.view_events);
        RecyclerView eventsList = findViewById(R.id.list_events);
        eventsList.setHasFixedSize(true);
        eventsList.setAdapter(eventAdapter);
        eventsList.setItemAnimator(new DefaultItemAnimator());
    }

    public void setOnEventClickListener(Qs listener) {
        onEventClickListener = listener;
    }

    void setData(String sc_id, ProjectFileBean projectFileBean, ViewBean viewBean) {
        this.sc_id = sc_id;
        this.projectFileBean = projectFileBean;

        String[] viewEvents = oq.c(viewBean.getClassInfo());
        events.clear();

        ArrayList<EventBean> alreadyAddedEvents = jC.a(sc_id).g(projectFileBean.getJavaName());
        for (String event : viewEvents) {
            boolean eventAlreadyInActivity = false;
            for (EventBean bean : alreadyAddedEvents) {
                if (bean.eventType == EventBean.EVENT_TYPE_VIEW && viewBean.id.equals(bean.targetId) && event.equals(bean.eventName)) {
                    eventAlreadyInActivity = true;
                    break;
                }
            }

            if (!event.equals("onBindCustomView") || (!viewBean.customView.isEmpty() && !viewBean.customView.equals("none"))) {
                EventBean eventBean = new EventBean(EventBean.EVENT_TYPE_VIEW, viewBean.type, viewBean.id, event);
                eventBean.isSelected = eventAlreadyInActivity;
                events.add(eventBean);
            }
        }

        eventAdapter.notifyDataSetChanged();
    }

    private void createEvent(int eventPosition) {
        EventBean eventBean = events.get(eventPosition);
        if (!eventBean.isSelected) {
            eventBean.isSelected = true;
            jC.a(sc_id).a(projectFileBean.getJavaName(), eventBean);
            eventAdapter.notifyItemChanged(eventPosition);
            bB.a(getContext(), xB.b().a(getContext(), R.string.event_message_new_event), 0).show();
        }
        if (onEventClickListener != null) {
            onEventClickListener.a(eventBean);
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            EventBean eventBean = events.get(position);
            holder.bind(eventBean, position);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            EventGridItemBinding binding = EventGridItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final EventGridItemBinding binding;

            public ViewHolder(EventGridItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(EventBean event, int position) {
                binding.container.setOnClickListener(v -> createEvent(getLayoutPosition()));
                binding.imgIcon.setImageResource(oq.a(event.eventName));
                binding.tvTitle.setText(event.eventName);

                if (event.isSelected) {
                    binding.imgIcon.setColorFilter(MaterialColors.getColor(binding.tvTitle, com.google.android.material.R.attr.colorSecondary));
                    binding.imgUsedEvent.setVisibility(View.GONE);
                    binding.container.setOnLongClickListener(v -> {
                        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(itemView.getContext());
                        dialog.setIcon(R.drawable.delete_96);
                        dialog.setTitle("Confirm Delete");
                        dialog.setMessage("Click on Confirm to delete the selected Event.");

                        dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (view, which) -> {
                            view.dismiss();
                            EventBean.deleteEvent(sc_id, event, projectFileBean);
                            bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_delete), 0).show();
                            event.isSelected = false;
                            eventAdapter.notifyItemChanged(position);
                        });
                        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
                        dialog.show();
                        return true;
                    });
                } else {
                    binding.imgUsedEvent.setVisibility(View.VISIBLE);
                    binding.imgIcon.setColorFilter(MaterialColors.getColor(binding.tvTitle, com.google.android.material.R.attr.colorOutlineVariant));
                }
            }
        }
    }
}
