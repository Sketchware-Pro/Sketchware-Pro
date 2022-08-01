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
    public String a;
    public ProjectFileBean b;
    public ViewBean c;
    public ArrayList<EventBean> d;
    public RecyclerView e;
    public Qs f;

    public ViewEvents(Context context) {
        super(context);
        a(context);
    }

    public void setOnEventClickListener(Qs qs) {
        f = qs;
    }

    class a extends RecyclerView.a<a.a2> {

        class a2 extends RecyclerView.v {
            public LinearLayout t;
            public ImageView u;
            public ImageView v;
            public TextView w;

            public a2(View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.container);
                u = itemView.findViewById(R.id.img_icon);
                v = itemView.findViewById(R.id.img_used_event);
                w = itemView.findViewById(R.id.tv_title);
                itemView.setOnClickListener(v -> ViewEvents.this.a(j()));
            }
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(a2 holder, int position) {
            EventBean eventBean = ViewEvents.this.d.get(position);
            if (eventBean.isSelected) {
                holder.v.setVisibility(View.GONE);
                mB.a(holder.u, 1);
            } else {
                holder.v.setVisibility(View.VISIBLE);
                mB.a(holder.u, 0);
            }
            holder.u.setImageResource(oq.a(eventBean.eventName));
            holder.w.setText(eventBean.eventName);
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public a2 b(ViewGroup parent, int viewType) {
            return new a2(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_grid_item, parent, false));
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return ViewEvents.this.d.size();
        }
    }

    public ViewEvents(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, R.layout.view_events);
        d = new ArrayList<>();
        e = findViewById(R.id.list_events);
        e.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.b(0);
        e.setLayoutManager(linearLayoutManager);
        e.setAdapter(new a());
        e.setItemAnimator(new ci());
    }

    public void a(String str, ProjectFileBean projectFileBean, ViewBean viewBean) {
        boolean z;
        a = str;
        b = projectFileBean;
        c = viewBean;
        String[] c = oq.c(viewBean.getClassInfo());
        d.clear();
        if (c != null) {
            ArrayList<EventBean> g = jC.a(str).g(projectFileBean.getJavaName());
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
                    d.add(eventBean);
                }
            }
        }
        e.getAdapter().c();
    }

    public final void a(int i) {
        EventBean eventBean = d.get(i);
        if (!eventBean.isSelected) {
            eventBean.isSelected = true;
            jC.a(a).a(b.getJavaName(), eventBean);
            e.getAdapter().c(i);
            bB.a(getContext(), xB.b().a(getContext(), R.string.event_message_new_event), 0).show();
        }
        if (f != null) {
            f.a(eventBean);
        }
    }
}
