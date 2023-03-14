package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.Qs;
import a.a.a.bB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.oq;
import a.a.a.wB;
import a.a.a.xB;

public class ViewEvents extends LinearLayout {
    private String sc_id;
    private ProjectFileBean projectFileBean;
    private ArrayList<EventBean> events;
    private RecyclerView eventsList;
    private Qs eventClickListener;

    public ViewEvents(Context context) {
        super(context);
        initialize(context);
    }

    public ViewEvents(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.view_events);
        events = new ArrayList<>();
        eventsList = findViewById(R.id.list_events);
        eventsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        eventsList.setLayoutManager(linearLayoutManager);
        eventsList.setAdapter(new EventAdapter());
        eventsList.setItemAnimator(new DefaultItemAnimator());
    }

    public void setOnEventClickListener(Qs listener) {
        eventClickListener = listener;
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

            if (!event.equals("onBindCustomView") || (!viewBean.customView.equals("") && !viewBean.customView.equals("none"))) {
                EventBean eventBean = new EventBean(EventBean.EVENT_TYPE_VIEW, viewBean.type, viewBean.id, event);
                eventBean.isSelected = eventAlreadyInActivity;
                events.add(eventBean);
            }
        }
        eventsList.getAdapter().notifyDataSetChanged();
    }

    private void createEvent(int eventPosition) {
        EventBean eventBean = events.get(eventPosition);
        if (!eventBean.isSelected) {
            eventBean.isSelected = true;
            jC.a(sc_id).a(projectFileBean.getJavaName(), eventBean);
            eventsList.getAdapter().notifyItemChanged(eventPosition);
            bB.a(getContext(), xB.b().a(getContext(), R.string.event_message_new_event), 0).show();
        }
        if (eventClickListener != null) {
            eventClickListener.a(eventBean);
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final LinearLayout container;
            public final ImageView icon;
            public final ImageView addAvailableIcon;
            public final TextView name;

            public ViewHolder(View itemView) {
                super(itemView);
                container = itemView.findViewById(R.id.container);
                icon = itemView.findViewById(R.id.img_icon);
                addAvailableIcon = itemView.findViewById(R.id.img_used_event);
                name = itemView.findViewById(R.id.tv_title);
                itemView.setOnClickListener(v -> createEvent(getLayoutPosition()));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            EventBean eventBean = events.get(position);
            if (eventBean.isSelected) {
                holder.addAvailableIcon.setVisibility(View.GONE);
                mB.a(holder.icon, 1);
            } else {
                holder.addAvailableIcon.setVisibility(View.VISIBLE);
                mB.a(holder.icon, 0);
            }
            holder.icon.setImageResource(oq.a(eventBean.eventName));
            holder.name.setText(eventBean.eventName);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_grid_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return events.size();
        }
    }
}
