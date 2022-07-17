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

import a.a.a.Qs;
import a.a.a.bB;
import a.a.a.ci;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.oq;
import a.a.a.wB;
import mod.hey.studios.util.Helper;

public class ViewEvents extends LinearLayout {

    private String sc_id;
    private ProjectFileBean projectFileBean;
    private ArrayList<EventBean> eventBeanList;
    private RecyclerView recyclerView;
    private Qs eventClickListener;

    public ViewEvents(Context context) {
        super(context);
        a(context);
    }

    public ViewEvents(Context context, AttributeSet attr) {
        super(context, attr);
        a(context);
    }

    private void selectEvent(int var1) {
        EventBean eventBean = eventBeanList.get(var1);
        if (!eventBean.isSelected) {
            eventBean.isSelected = true;
            jC.a(sc_id).a(projectFileBean.getJavaName(), eventBean);
            recyclerView.getAdapter().c(var1);
            bB.a(getContext(), Helper.getResString(2131625331), 0).show();
        }

        Qs var3 = eventClickListener;
        if (var3 != null) {
            var3.a(eventBean);
        }

    }

    private void a(Context context) {
        wB.a(context, this, R.layout.view_events);
        eventBeanList = new ArrayList<>();
        recyclerView = findViewById(R.id.list_events);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.b(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new EventsAdapter());
        recyclerView.setItemAnimator(new ci());
    }

    public void a(String sc_id, ProjectFileBean projectFileBean, ViewBean viewBean) {
        this.sc_id = sc_id;
        this.projectFileBean = projectFileBean;
        eventBeanList.clear();
        ArrayList<EventBean> eventBeans = jC.a(sc_id).g(projectFileBean.getJavaName());

        for (String eventName : oq.c(viewBean.getClassInfo())) {
            for (EventBean eventBean : eventBeans) {
                if (!eventName.equals("onBindCustomView") || (!viewBean.customView.equals("") && !viewBean.customView.equals("none"))) {
                    EventBean newEvent = new EventBean(EventBean.EVENT_TYPE_VIEW, viewBean.type, viewBean.id, eventName);
                    newEvent.isSelected = (eventBean.eventType == EventBean.EVENT_TYPE_VIEW) && viewBean.id.equals(eventBean.targetId) && eventName.equals(eventBean.eventName);
                    eventBeanList.add(newEvent);
                }
            }
        }

        recyclerView.getAdapter().c();
    }

    public void setOnEventClickListener(Qs onEventClickListener) {
        eventClickListener = onEventClickListener;
    }

    private class EventsAdapter extends RecyclerView.a<EventsAdapter.ViewHolder> {

        @Override
        public int a() {
            return eventBeanList.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            EventBean eventBean = eventBeanList.get(position);
            if (eventBean.isSelected) {
                viewHolder.v.setVisibility(View.GONE);
                mB.a(viewHolder.u, 1);
            } else {
                viewHolder.v.setVisibility(View.VISIBLE);
                mB.a(viewHolder.u, 0);
            }

            viewHolder.u.setImageResource(oq.a(eventBean.eventName));
            viewHolder.w.setText(eventBean.eventName);
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_grid_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {

            public LinearLayout t;
            public ImageView u;
            public ImageView v;
            public TextView w;

            public ViewHolder(View itemVIew) {
                super(itemVIew);
                t = itemVIew.findViewById(R.id.container);
                u = itemVIew.findViewById(R.id.img_icon);
                v = itemVIew.findViewById(R.id.img_used_event);
                w = itemVIew.findViewById(R.id.tv_title);
                itemVIew.setOnClickListener(view -> selectEvent(j()));
            }
        }
    }
}
