package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.Qs;
import a.a.a.bB;
import a.a.a.ci;
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
        a(context);
    }

    public void setOnEventClickListener(Qs listener) {
        eventClickListener = listener;
    }

    private class EventAdapter extends RecyclerView.a<EventAdapter.ViewHolder> {

        private class ViewHolder extends RecyclerView.v {
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
                itemView.setOnClickListener(v -> ViewEvents.this.a(j()));
            }
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(ViewHolder holder, int position) {
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
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_grid_item, parent, false));
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return events.size();
        }
    }

    public ViewEvents(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, R.layout.view_events);
        events = new ArrayList<>();
        eventsList = findViewById(R.id.list_events);
        eventsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.b(0);
        eventsList.setLayoutManager(linearLayoutManager);
        eventsList.setAdapter(new EventAdapter());
        eventsList.setItemAnimator(new ci());
    }

    public void a(String sc_id, ProjectFileBean projectFileBean, ViewBean viewBean) {
        boolean z;
        this.sc_id = sc_id;
        this.projectFileBean = projectFileBean;
        String[] c = oq.c(viewBean.getClassInfo());
        events.clear();
        if (c != null) {
            ArrayList<EventBean> g = jC.a(sc_id).g(projectFileBean.getJavaName());
            for (String str2 : c) {
                Iterator<EventBean> it = g.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    EventBean next = it.next();
                    if (next.eventType == 1 && viewBean.id.equals(next.targetId) && str2.equals(next.eventName)) {
                        z = true;
                        break;
                    }
                }
                if (!str2.equals("onBindCustomView") || (!viewBean.customView.equals("") && !viewBean.customView.equals("none"))) {
                    EventBean eventBean = new EventBean(EventBean.EVENT_TYPE_VIEW, viewBean.type, viewBean.id, str2);
                    eventBean.isSelected = z;
                    events.add(eventBean);
                }
            }
        }
        eventsList.getAdapter().c();
    }

    public final void a(int i) {
        EventBean eventBean = events.get(i);
        if (!eventBean.isSelected) {
            eventBean.isSelected = true;
            jC.a(sc_id).a(projectFileBean.getJavaName(), eventBean);
            eventsList.getAdapter().c(i);
            bB.a(getContext(), xB.b().a(getContext(), R.string.event_message_new_event), 0).show();
        }
        if (eventClickListener != null) {
            eventClickListener.a(eventBean);
        }
    }
}
