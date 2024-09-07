package com.besome.sketch.editor.event;

import a.a.a.bB;
import a.a.a.dt;
import a.a.a.gB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.oq;
import a.a.a.rs;
import a.a.a.wB;
import a.a.a.xB;
import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AddEventActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public ArrayList<EventBean> addableDrawerViewEvents;

    public ArrayList<EventBean> eventsToAdd;
    public boolean C;

    public Button add_button;

    public Button cancel_button;

    public int categoryIndex;

    public TextView empty_message;

    public dt moreBlockView;

    public String sc_id;

    public ProjectFileBean projectFile;

    public CategoryAdapter categoryAdapter;

    public EventAdapter eventAdapter;

    public EventsToAddAdapter eventsToAddAdapter;

    public TextView tv_category;

    public RecyclerView category_list;

    public RecyclerView event_list;

    public RecyclerView events_preview;

    public LinearLayout container;

    public ScrollView moreblock_layout;

    public HashMap<Integer, ArrayList<EventBean>> categories;
    public ArrayList<EventBean> w;

    public ArrayList<EventBean> addableViewEvents;

    public ArrayList<EventBean> addableComponentEvents;

    public ArrayList<EventBean> addableActivityEvents;

    public static HashMap a(AddEventActivity addEventActivity) {
        return addEventActivity.categories;
    }

    public static ArrayList b(AddEventActivity addEventActivity) {
        return addEventActivity.eventsToAdd;
    }

    public static void c(AddEventActivity addEventActivity) {
        addEventActivity.l();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(2130771982, 2130771983); // R.anim.ani_fade_in, R.anim.ani_fade_out
    }

    public final void l() {
        if (this.eventsToAdd.size() == 0 && !this.C) {
            this.C = true;
            gB.a((ViewGroup) this.events_preview, 300, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {
                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    events_preview.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {
                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {
                }
            });
        } else {
            if (this.eventsToAdd.size() <= 0 || !this.C) {
                return;
            }
            this.C = false;
            this.events_preview.setVisibility(0);
            gB.b(this.events_preview, 300, null);
        }
    }

    public final void m() {
        boolean drawerViewEventExists;
        ViewBean fab;
        boolean fabEventExists;
        boolean componentEventExists;
        boolean viewEventExists;
        this.w.clear();
        this.addableActivityEvents.clear();
        this.addableComponentEvents.clear();
        this.addableDrawerViewEvents.clear();
        this.w.clear();
        this.eventsToAdd.clear();
        String[] activityEvents = oq.a();
        int length = activityEvents.length;
        int i = 0;
        while (true) {
            boolean exists = true;
            if (i >= length) {
                break;
            }
            String activityEvent = activityEvents[i];
            Iterator<EventBean> it = jC.a(this.sc_id).g(this.projectFile.getJavaName()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    exists = false;
                    break;
                }
                EventBean next = it.next();
                if (next.eventType == 3 && activityEvent.equals(next.eventName)) {
                    break;
                }
            }
            if (!exists) {
                this.addableActivityEvents.add(new EventBean(3, 0, activityEvent, activityEvent));
            }
            i++;
        }
        ArrayList<ViewBean> views = jC.a(this.sc_id).d(this.projectFile.getXmlName());
        ArrayList<ComponentBean> components = jC.a(this.sc_id).e(this.projectFile.getJavaName());
        if (views != null) {
            Iterator<ViewBean> it2 = views.iterator();
            while (it2.hasNext()) {
                ViewBean view = it2.next();
                String[] viewEvents = oq.c(view.getClassInfo());
                if (viewEvents != null) {
                    for (String viewEvent : viewEvents) {
                        Iterator<EventBean> existingEvents = jC.a(this.sc_id).g(this.projectFile.getJavaName()).iterator();
                        while (true) {
                            if (!existingEvents.hasNext()) {
                                viewEventExists = false;
                                break;
                            }
                            EventBean existingEvent = existingEvents.next();
                            if (existingEvent.eventType == 1 && view.id.equals(existingEvent.targetId) && viewEvent.equals(existingEvent.eventName)) {
                                viewEventExists = true;
                                break;
                            }
                        }
                        if (viewEvent.equals("onBindCustomView") && (view.customView.equals("") || view.customView.equals("none"))) {
                            viewEventExists = true;
                        }
                        if (!viewEventExists) {
                            this.addableViewEvents.add(new EventBean(1, view.type, view.id, viewEvent));
                        }
                    }
                }
            }
        }
        if (components != null) {
            Iterator<ComponentBean> it3 = components.iterator();
            while (it3.hasNext()) {
                ComponentBean component = it3.next();
                String[] componentEvents = oq.a(component.getClassInfo());
                if (componentEvents != null) {
                    for (String componentEvent : componentEvents) {
                        Iterator<EventBean> it4 = jC.a(this.sc_id).g(this.projectFile.getJavaName()).iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                componentEventExists = false;
                                break;
                            }
                            EventBean existingComponentEvent = it4.next();
                            if (existingComponentEvent.eventType == 2 && component.componentId.equals(existingComponentEvent.targetId) && componentEvent.equals(existingComponentEvent.eventName)) {
                                componentEventExists = true;
                                break;
                            }
                        }
                        if (!componentEventExists) {
                            this.addableComponentEvents.add(new EventBean(2, component.type, component.componentId, componentEvent));
                        }
                    }
                }
            }
        }
        if (this.projectFile.hasActivityOption(8) && (fab = jC.a(this.sc_id).h(this.projectFile.getXmlName())) != null) {
            for (String fabEvent : oq.c(fab.getClassInfo())) {
                Iterator<EventBean> it5 = jC.a(this.sc_id).g(this.projectFile.getJavaName()).iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        fabEventExists = false;
                        break;
                    }
                    EventBean existingFabEvent = it5.next();
                    if (existingFabEvent.eventType == 1 && fab.id.equals(existingFabEvent.targetId) && fabEvent.equals(existingFabEvent.eventName)) {
                        fabEventExists = true;
                        break;
                    }
                }
                if (!fabEventExists) {
                    this.addableViewEvents.add(new EventBean(1, fab.type, fab.id, fabEvent));
                }
            }
        }
        if (this.projectFile.hasActivityOption(4)) {
            ArrayList<ViewBean> drawerViews = jC.a(this.sc_id).d(this.projectFile.getDrawerXmlName());
            if (drawerViews != null) {
                Iterator<ViewBean> it6 = drawerViews.iterator();
                while (it6.hasNext()) {
                    ViewBean drawerView = it6.next();
                    for (String drawerViewEvent : oq.c(drawerView.getClassInfo())) {
                        Iterator<EventBean> it7 = jC.a(this.sc_id).g(this.projectFile.getJavaName()).iterator();
                        while (true) {
                            if (!it7.hasNext()) {
                                drawerViewEventExists = false;
                                break;
                            }
                            EventBean existingDrawerViewEvent = it7.next();
                            if (existingDrawerViewEvent.eventType == 4 && drawerView.id.equals(existingDrawerViewEvent.targetId) && drawerViewEvent.equals(existingDrawerViewEvent.eventName)) {
                                drawerViewEventExists = true;
                                break;
                            }
                        }
                        if (!drawerViewEventExists) {
                            this.addableDrawerViewEvents.add(new EventBean(4, drawerView.type, drawerView.id, drawerViewEvent));
                        }
                    }
                }
            }
        }
        if (this.categoryAdapter.lastSelectedCategory == -1) {
            this.eventAdapter.setEvents(this.categories.get(Integer.valueOf(this.categoryIndex)));
            this.categoryAdapter.lastSelectedCategory = this.categoryIndex;
            this.tv_category.setText(rs.a(getApplicationContext(), this.categoryIndex));
            CategoryAdapter categoryAdapter = this.categoryAdapter;
            if (categoryAdapter != null) {
                categoryAdapter.notifyItemChanged(this.categoryIndex);
            }
            if (this.categoryIndex == 4) {
                this.moreblock_layout.setVisibility(0);
                this.empty_message.setVisibility(8);
                this.event_list.setVisibility(8);
            } else {
                this.moreblock_layout.setVisibility(8);
                this.event_list.setVisibility(0);
            }
        }
        EventAdapter eventAdapter = this.eventAdapter;
        if (eventAdapter != null) {
            eventAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (mB.a()) {
            return;
        }
        int id = v.getId();
        if (id != 2131230755) { // R.id.add_button
            if (id != 2131230869) { // R.id.cancel_button
                return;
            }
            setResult(0);
            finish();
            return;
        }
        if (this.eventsToAdd.size() == 0 && this.moreBlockView.a()) {
            return;
        }
        if (!this.moreBlockView.a()) {
            if (!this.moreBlockView.b()) {
                this.eventAdapter.setEvents(this.categories.get(4));
                this.categoryAdapter.lastSelectedCategory = 4;
                this.tv_category.setText(rs.a(getApplicationContext(), 4));
                this.empty_message.setVisibility(8);
                this.moreblock_layout.setVisibility(0);
                this.categoryAdapter.notifyDataSetChanged();
                return;
            }
            Pair<String, String> blockInformation = this.moreBlockView.getBlockInformation();
            jC.a(this.sc_id).a(this.projectFile.getJavaName(), (String) blockInformation.first, (String) blockInformation.second);
        }
        Iterator<EventBean> it = this.eventsToAdd.iterator();
        while (it.hasNext()) {
            jC.a(this.sc_id).a(this.projectFile.getJavaName(), it.next());
        }
        if (this.eventsToAdd.size() == 1) {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625331), 0).show(); // R.string.event_message_new_event
        } else if (this.eventsToAdd.size() > 1) {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625332), 0).show(); // R.string.event_message_new_events
        }
        jC.a(this.sc_id).k();
        setResult(-1);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427496); // R.layout.logic_popup_add_event
        Intent intent = getIntent();
        if (savedInstanceState == null) {
            this.sc_id = intent.getStringExtra("sc_id");
            this.projectFile = (ProjectFileBean) intent.getParcelableExtra("project_file");
            this.categoryIndex = intent.getIntExtra("category_index", 0);
        } else {
            this.sc_id = savedInstanceState.getString("sc_id");
            this.projectFile = (ProjectFileBean) savedInstanceState.getParcelable("project_file");
            this.categoryIndex = savedInstanceState.getInt("category_index");
        }
        this.event_list = (RecyclerView) findViewById(2131231046);
        this.tv_category = (TextView) findViewById(2131231894);
        this.category_list = (RecyclerView) findViewById(2131230876);
        this.events_preview = (RecyclerView) findViewById(2131231049);
        this.container = (LinearLayout) findViewById(2131230931);
        this.add_button = (Button) findViewById(2131230755);
        this.cancel_button = (Button) findViewById(2131230869);
        this.empty_message = (TextView) findViewById(2131231017);
        this.moreblock_layout = (ScrollView) findViewById(2131231547);
        this.moreBlockView = new dt(this);
        this.moreblock_layout.addView(this.moreBlockView);
        this.moreblock_layout.setVisibility(8);
        this.add_button.setOnClickListener(this);
        this.cancel_button.setOnClickListener(this);
        this.categories = new HashMap<>();
        this.w = new ArrayList<>();
        this.addableViewEvents = new ArrayList<>();
        this.addableComponentEvents = new ArrayList<>();
        this.addableActivityEvents = new ArrayList<>();
        this.addableDrawerViewEvents = new ArrayList<>();
        this.categories.put(0, this.addableActivityEvents);
        this.categories.put(1, this.addableViewEvents);
        this.categories.put(2, this.addableComponentEvents);
        this.categories.put(3, this.addableDrawerViewEvents);
        this.categories.put(4, this.w);
        this.event_list.setHasFixedSize(true);
        this.event_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        this.eventAdapter = new EventAdapter();
        this.event_list.setAdapter(this.eventAdapter);
        this.category_list.setHasFixedSize(true);
        this.categoryAdapter = new CategoryAdapter();
        this.category_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.category_list.setAdapter(this.categoryAdapter);
        ((SimpleItemAnimator) this.category_list.getItemAnimator()).setSupportsChangeAnimations(false);
        this.events_preview.setHasFixedSize(true);
        this.eventsToAddAdapter = new EventsToAddAdapter();
        this.events_preview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.events_preview.setAdapter(this.eventsToAddAdapter);
        this.C = true;
        this.events_preview.setVisibility(8);
        this.empty_message.setVisibility(8);
        this.eventsToAdd = new ArrayList<>();
        this.event_list.bringToFront();
        overridePendingTransition(2130771982, 2130771983); // R.anim.ani_fade_in, R.anim.ani_fade_out
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.add_button.setText(xB.b().a(this, 2131624970)); // R.string.common_word_add
        this.cancel_button.setText(xB.b().a(this, 2131624974)); // R.string.common_word_cancel
        this.empty_message.setText(xB.b().a(this, 2131625333)); // R.string.event_message_no_avail_events
        this.moreBlockView.setFuncNameValidator(jC.a(this.sc_id).a(this.projectFile));
    }

    @Override
    public void onResume() {
        super.onResume();
        gB.a(this.container, 500);
        if (this.projectFile != null) {
            m();
        }
        this.d.setScreenName(AddEventActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle newState) {
        newState.putString("sc_id", this.sc_id);
        newState.putParcelable("project_file", this.projectFile);
        newState.putInt("category_index", this.categoryIndex);
        super.onSaveInstanceState(newState);
    }

    class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

        public int lastSelectedEvent = -1;

        public ArrayList<EventBean> events = new ArrayList<>();
        public boolean e;

        class ViewHolder extends RecyclerView.ViewHolder {

            public LinearLayout events_preview;

            public ImageView img_icon;

            public TextView tv_target_type;

            public TextView tv_sep;

            public TextView tv_target_id;

            public TextView tv_event_name;

            public CheckBox checkbox;

            public ViewHolder(View itemView) {
                super(itemView);
                this.events_preview = (LinearLayout) itemView.findViewById(2131231049);
                this.img_icon = (ImageView) itemView.findViewById(2131231151);
                this.tv_target_type = (TextView) itemView.findViewById(2131232193);
                this.tv_sep = (TextView) itemView.findViewById(2131232152);
                this.tv_target_id = (TextView) itemView.findViewById(2131232192);
                this.tv_event_name = (TextView) itemView.findViewById(2131231970);
                this.checkbox = (CheckBox) itemView.findViewById(2131230883);
                itemView.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedEvent = getLayoutPosition();
                        EventBean event = categories.get(categoryAdapter.lastSelectedCategory).get(lastSelectedEvent);
                        if (event.isSelected) {
                            event.isSelected = false;
                            eventsToAdd.remove(event);
                            l();
                            eventsToAddAdapter.notifyItemRemoved(eventsToAddAdapter.getItemCount());
                        } else {
                            event.isSelected = true;
                            eventsToAdd.add(event);
                            l();
                            eventsToAddAdapter.notifyItemInserted(eventsToAddAdapter.getItemCount());
                        }
                        if (!e) {
                            notifyItemChanged(lastSelectedEvent);
                        }
                    }
                });
                this.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    lastSelectedEvent = getLayoutPosition();
                    EventBean event = categories.get(categoryAdapter.lastSelectedCategory).get(lastSelectedEvent);
                    if (!event.isSelected && isChecked) {
                        event.isSelected = true;
                        eventsToAdd.add(event);
                        l();
                        eventsToAddAdapter.notifyItemInserted(eventsToAddAdapter.getItemCount());
                    } else if (event.isSelected && !isChecked) {
                        event.isSelected = false;
                        eventsToAdd.remove(event);
                        eventsToAddAdapter.notifyItemRemoved(eventsToAddAdapter.getItemCount());
                        l();
                    }
                    if (!e) {
                        notifyItemChanged(lastSelectedEvent);
                    }
                });
            }
        }

        public EventAdapter() {
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            this.e = true;
            holder.events_preview.removeAllViews();
            holder.events_preview.setVisibility(0);
            EventBean event = (EventBean) ((ArrayList) AddEventActivity.this.categories.get(Integer.valueOf(AddEventActivity.this.categoryAdapter.lastSelectedCategory))).get(position);
            ImageView imageView = new ImageView(AddEventActivity.this.getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(0, 0, (int) wB.a(AddEventActivity.this.getApplicationContext(), 2.0f), 0);
            int a = (int) wB.a(AddEventActivity.this.getApplicationContext(), 16.0f);
            layoutParams.width = a;
            layoutParams.height = a;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(oq.a(event.eventName));
            holder.events_preview.addView(imageView);
            holder.img_icon.setImageResource(EventBean.getEventIconResource(event.eventType, event.targetType));
            int eventType = event.eventType;
            if (eventType == 3) {
                holder.tv_target_type.setText("Activity");
                holder.events_preview.setVisibility(8);
            } else if (eventType == 1) {
                holder.tv_target_type.setText(ViewBean.getViewTypeName(event.targetType));
            } else if (eventType == 4) {
                holder.tv_target_type.setText(ViewBean.getViewTypeName(event.targetType));
            } else if (eventType == 2) {
                holder.tv_target_type.setText(ComponentBean.getComponentName(AddEventActivity.this.getApplicationContext(), event.targetType));
            } else if (eventType == 5) {
                holder.events_preview.setVisibility(8);
            }
            holder.tv_sep.setText(" : ");
            if (event.targetId.equals("_fab")) {
                holder.tv_target_id.setText("fab");
            } else {
                holder.tv_target_id.setText(event.targetId);
            }
            holder.tv_event_name.setText(oq.a(event.eventName, AddEventActivity.this.getApplicationContext()));
            if (event.isSelected) {
                holder.checkbox.setChecked(true);
            } else {
                holder.checkbox.setChecked(false);
            }
            this.e = false;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(2131427430, parent, false));
        }

        @Override
        public int getItemCount() {
            return this.events.size();
        }

        public void setEvents(ArrayList<EventBean> events) {
            if (events.size() == 0) {
                AddEventActivity.this.empty_message.setVisibility(0);
            } else {
                AddEventActivity.this.empty_message.setVisibility(8);
                AddEventActivity.this.event_list.setVisibility(0);
            }
            this.events = events;
        }
    }

    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

        public int lastSelectedCategory = -1;

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public LinearLayout container;

            public ImageView img_icon;

            public ViewHolder(View itemView) {
                super(itemView);
                this.img_icon = (ImageView) itemView.findViewById(2131231151);
                this.container = (LinearLayout) itemView.findViewById(2131230931);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (getLayoutPosition() != -1) {
                    int layoutPosition = getLayoutPosition();
                    CategoryAdapter categoryAdapter = CategoryAdapter.this;
                    if (layoutPosition == categoryAdapter.lastSelectedCategory) {
                        return;
                    }
                    categoryAdapter.lastSelectedCategory = getLayoutPosition();
                    CategoryAdapter.this.notifyDataSetChanged();
                    AddEventActivity addEventActivity = AddEventActivity.this;
                    addEventActivity.tv_category.setText(rs.a(addEventActivity.getApplicationContext(), CategoryAdapter.this.lastSelectedCategory));
                    CategoryAdapter categoryAdapter2 = CategoryAdapter.this;
                    if (categoryAdapter2.lastSelectedCategory == 4) {
                        AddEventActivity.this.moreblock_layout.setVisibility(0);
                        AddEventActivity.this.empty_message.setVisibility(8);
                    } else {
                        AddEventActivity.this.moreblock_layout.setVisibility(8);
                        AddEventActivity addEventActivity2 = AddEventActivity.this;
                        addEventActivity2.eventAdapter.setEvents((ArrayList<EventBean>) addEventActivity2.categories.get(Integer.valueOf(CategoryAdapter.this.lastSelectedCategory)));
                        AddEventActivity.this.eventAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

        public CategoryAdapter() {
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.img_icon.setImageResource(rs.a(position));
            if (this.lastSelectedCategory == position) {
                holder.container.setBackgroundResource(2131165390);
                holder.img_icon.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
                holder.container.animate().translationY(0.0f).start();
            } else {
                holder.container.setBackgroundResource(2131165387);
                holder.img_icon.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f).start();
                holder.container.setTranslationY(wB.a(AddEventActivity.this.getApplicationContext(), 12.0f));
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(2131427428, parent, false);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(parent.getMeasuredWidth() / getItemCount(), (int) wB.a(AddEventActivity.this.getApplicationContext(), 44.0f)));
            inflate.setTranslationY(wB.a(AddEventActivity.this.getApplicationContext(), 12.0f));
            inflate.findViewById(2131231151).setAlpha(0.6f);
            inflate.findViewById(2131231151).setScaleX(0.8f);
            inflate.findViewById(2131231151).setScaleY(0.8f);
            return new ViewHolder(inflate);
        }

        @Override
        public int getItemCount() {
            return AddEventActivity.this.categories.size();
        }
    }

    class EventsToAddAdapter extends RecyclerView.Adapter<EventsToAddAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            public LinearLayout ll_img_event;

            public RelativeLayout container;

            public ImageView img_icon;

            public ImageView img_event;

            public ViewHolder(View itemView) {
                super(itemView);
                this.container = (RelativeLayout) itemView.findViewById(2131230931);
                this.img_icon = (ImageView) itemView.findViewById(2131231151);
                this.img_event = (ImageView) itemView.findViewById(2131231139);
                this.ll_img_event = (LinearLayout) itemView.findViewById(2131231475);
            }
        }

        public EventsToAddAdapter() {
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.ll_img_event.setVisibility(0);
            EventBean event = (EventBean) AddEventActivity.this.eventsToAdd.get(position);
            int eventType = event.eventType;
            if (eventType == 3) {
                holder.ll_img_event.setVisibility(8);
            } else if (eventType == 5) {
                holder.ll_img_event.setVisibility(8);
            }
            holder.img_icon.setImageResource(EventBean.getEventIconResource(event.eventType, event.targetType));
            holder.img_event.setImageResource(oq.a(event.eventName));
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(2131427436, parent, false));
        }

        @Override
        public int getItemCount() {
            return AddEventActivity.this.eventsToAdd.size();
        }
    }
}
