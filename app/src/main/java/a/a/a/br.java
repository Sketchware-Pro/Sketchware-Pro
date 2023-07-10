package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.besome.sketch.editor.component.CollapsibleComponentLayout;
import com.besome.sketch.editor.component.ComponentAddActivity;
import com.besome.sketch.editor.component.ComponentEventButton;
import com.besome.sketch.lib.base.CollapsibleViewHolder;
import com.besome.sketch.lib.ui.CollapsibleButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class br extends qA implements View.OnClickListener {
    private ProjectFileBean projectFile;
    private Adapter adapter;
    private TextView empty;
    private FloatingActionButton fab;
    private String sc_id;
    private ArrayList<ComponentBean> components = new ArrayList<>();
    private final ActivityResultLauncher<Intent> addComponent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            refreshData();
        }
    });

    public void refreshData() {
        if (projectFile != null && adapter != null) {
            components = jC.a(sc_id).e(projectFile.getJavaName());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && v.getId() == R.id.fab) {
            Intent intent = new Intent(requireContext(), ComponentAddActivity.class);
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("project_file", projectFile);
            addComponent.launch(intent);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fr_component_list, container, false);
        initialize(root);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            sc_id = savedInstanceState.getString("sc_id");
        } else {
            sc_id = requireActivity().getIntent().getStringExtra("sc_id");
        }
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private static final ConcatAdapter.Config EVENTS_ADAPTER_CONFIG = new ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build();
        private final RecyclerView.RecycledViewPool eventViewHolders = new RecyclerView.RecycledViewPool();

        private class ViewHolder extends CollapsibleViewHolder {
            public final LinearLayout root;
            public final LinearLayout optionLayout;
            public final RecyclerView componentEvents;
            public final ImageView icon;
            public final TextView type;
            public final TextView id;
            public final ImageView menu;
            public final CollapsibleComponentLayout collapsibleComponentLayout;
            public final LinearLayout eventsPreview;

            public final AddedEventsAdapter addedEventsAdapter;
            public final AvailableEventsAdapter availableEventsAdapter;
            public final ConcatAdapter componentEventsAdapter;

            public ViewHolder(@NonNull View itemView) {
                super(itemView, 200);
                root = (LinearLayout) itemView;
                icon = itemView.findViewById(R.id.img_icon);
                type = itemView.findViewById(R.id.tv_component_type);
                id = itemView.findViewById(R.id.tv_component_id);
                menu = itemView.findViewById(R.id.img_menu);
                eventsPreview = itemView.findViewById(R.id.events_preview);
                optionLayout = itemView.findViewById(R.id.component_option_layout);
                componentEvents = itemView.findViewById(R.id.component_events);
                collapsibleComponentLayout = itemView.findViewById(R.id.component_option);
                collapsibleComponentLayout.getDeleteButton().getLabel().setText(xB.b().a(requireContext(), R.string.component_context_menu_title_delete_component));
                collapsibleComponentLayout.setButtonOnClickListener(v -> {
                    int lastSelectedItem = getLayoutPosition();
                    ComponentBean bean = jC.a(sc_id).a(projectFile.getJavaName(), lastSelectedItem);
                    if (v instanceof CollapsibleButton) {
                        bean.isConfirmation = true;
                        setAnimateNextTransformation(true);
                        notifyItemChanged(lastSelectedItem);
                    } else {
                        int id = v.getId();
                        if (id == R.id.confirm_no) {
                            bean.isConfirmation = false;
                            setAnimateNextTransformation(true);
                            notifyItemChanged(lastSelectedItem);
                        } else if (id == R.id.confirm_yes) {
                            jC.a(sc_id).b(projectFile.getJavaName(), bean);
                            bean.isConfirmation = false;
                            notifyItemRemoved(lastSelectedItem);
                            notifyItemRangeChanged(lastSelectedItem, getItemCount());
                        }
                    }
                });
                onDoneInitializingViews();
                setOnClickCollapseConfig(v -> v != root);
                componentEvents.setRecycledViewPool(eventViewHolders);
                if (componentEvents.getLayoutManager() instanceof LinearLayoutManager manager) {
                    manager.setRecycleChildrenOnDetach(true);
                }
                componentEvents.setItemAnimator(null);
                addedEventsAdapter = new AddedEventsAdapter();
                availableEventsAdapter = new AvailableEventsAdapter();
                componentEventsAdapter = new ConcatAdapter(EVENTS_ADAPTER_CONFIG, addedEventsAdapter, availableEventsAdapter);
            }

            @Override
            protected boolean isCollapsed() {
                return components.get(getLayoutPosition()).isCollapsed;
            }

            @Override
            protected void setIsCollapsed(boolean isCollapsed) {
                components.get(getLayoutPosition()).isCollapsed = isCollapsed;
            }

            @NonNull
            @Override
            protected ViewGroup getOptionsLayout() {
                return optionLayout;
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnClickCollapseTriggerViews() {
                return Set.of(menu, root);
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnLongClickCollapseTriggerViews() {
                return Set.of(root);
            }

            @Override
            public void collapse() {
                super.collapse();
                eventsPreview.animate().translationX(0.0f).alpha(1.0f).setStartDelay(120L).setDuration(150L).start();
                componentEvents.animate().translationX(-componentEvents.getWidth()).setDuration(150L).alpha(0.0f).start();
            }

            @Override
            public void expand() {
                super.expand();
                eventsPreview.animate().translationX(eventsPreview.getWidth()).alpha(0.0f).setDuration(150L).start();
                componentEvents.setTranslationX(-componentEvents.getWidth());
                componentEvents.setAlpha(0.0f);
                componentEvents.animate().translationX(0.0f).setStartDelay(200L).setDuration(120L).alpha(1.0f).start();
            }

            private class AddedEventsAdapter extends ListAdapter<EventBean, EventViewHolder> {
                private static final DiffUtil.ItemCallback<EventBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
                    @Override
                    public boolean areItemsTheSame(@NonNull EventBean oldItem, @NonNull EventBean newItem) {
                        return oldItem.equals(newItem);
                    }

                    @Override
                    public boolean areContentsTheSame(@NonNull EventBean oldItem, @NonNull EventBean newItem) {
                        return true;
                    }
                };

                public AddedEventsAdapter() {
                    super(DIFF_CALLBACK);
                }

                @NonNull
                @Override
                public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    ComponentEventButton button = new ComponentEventButton(parent.getContext());
                    return new EventViewHolder(button);
                }

                @Override
                public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
                    var event = getItem(position);
                    holder.button.onEventAdded();
                    holder.button.getName().setText(event.eventName);
                    holder.button.getIcon().setImageResource(oq.a(event.eventName));
                }
            }

            private class AvailableEventsAdapter extends ListAdapter<String, EventViewHolder> {
                private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
                    @Override
                    public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                        return oldItem.equals(newItem);
                    }

                    @Override
                    public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                        return true;
                    }
                };

                public AvailableEventsAdapter() {
                    super(DIFF_CALLBACK);
                }

                @NonNull
                @Override
                public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    ComponentEventButton button = new ComponentEventButton(parent.getContext());
                    return new EventViewHolder(button);
                }

                @Override
                public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
                    var eventName = getItem(position);
                    holder.button.onEventAvailableToAdd();
                    holder.button.getName().setText(eventName);
                    holder.button.getIcon().setImageResource(oq.a(eventName));
                }
            }

            private class EventViewHolder extends RecyclerView.ViewHolder {
                public final ComponentEventButton button;

                public EventViewHolder(@NonNull ComponentEventButton itemView) {
                    super(itemView);
                    button = itemView;
                    button.setClickListener(v -> {
                        if (!mB.a()) {
                            var bindingAdapter = getBindingAdapter();
                            if (bindingAdapter instanceof AddedEventsAdapter) {
                                var event = addedEventsAdapter.getCurrentList().get(getLayoutPosition());
                                openEvent(event.targetId, event.eventName, event.eventName);
                            } else if (bindingAdapter instanceof AvailableEventsAdapter) {
                                var component = components.get(ViewHolder.this.getLayoutPosition());
                                var availableEventsListIndex = getLayoutPosition() - addedEventsAdapter.getItemCount();
                                var eventName = availableEventsAdapter.getCurrentList().get(availableEventsListIndex);
                                EventBean event = new EventBean(EventBean.EVENT_TYPE_COMPONENT, component.type, component.componentId, eventName);
                                jC.a(sc_id).a(projectFile.getJavaName(), event);
                                bB.a(requireContext(), xB.b().a(requireContext(), R.string.event_message_new_event), 0).show();
                                button.onEventAdded();
                                notifyItemChanged(getLayoutPosition());
                                openEvent(event.targetId, event.eventName, event.eventName);
                            }
                        }
                    });
                }
            }
        }

        public Adapter(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 2) {
                            if (fab.isEnabled()) {
                                fab.hide();
                            }
                        } else if (dy < -2) {
                            if (fab.isEnabled()) {
                                fab.show();
                            }
                        }
                    }
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ComponentBean componentBean = components.get(position);
            holder.type.setText(ComponentBean.getComponentName(requireContext(), componentBean.type));
            holder.icon.setImageResource(ComponentBean.getIconResource(componentBean.type));

            switch (componentBean.type) {
                case ComponentBean.COMPONENT_TYPE_SHAREDPREF ->
                        holder.id.setText(componentBean.componentId + " : " + componentBean.param1);
                case ComponentBean.COMPONENT_TYPE_FIREBASE, ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE, ComponentBean.COMPONENT_TYPE_FILE_PICKER -> {
                    String path = componentBean.param1;
                    if (path.length() == 0) {
                        path = "/";
                    }
                    holder.id.setText(componentBean.componentId + " : " + path);
                }
                default -> holder.id.setText(componentBean.componentId);
            }

            ArrayList<EventBean> addedEvents = jC.a(sc_id).a(projectFile.getJavaName(), componentBean);
            ArrayList<String> availableEvents = new ArrayList<>(Arrays.asList(oq.a(componentBean.getClassInfo())));

            holder.eventsPreview.removeAllViews();
            holder.eventsPreview.setAlpha(1.0f);
            holder.eventsPreview.setTranslationX(0.0f);
            if (componentBean.isCollapsed) {
                holder.optionLayout.setVisibility(View.GONE);
                holder.menu.setRotation(0.0f);
            } else {
                holder.optionLayout.setVisibility(View.VISIBLE);
                holder.menu.setRotation(-180.0f);
                if (componentBean.isConfirmation) {
                    if (holder.shouldAnimateNextTransformation()) {
                        holder.collapsibleComponentLayout.showConfirmation();
                        holder.setAnimateNextTransformation(false);
                    } else {
                        holder.collapsibleComponentLayout.showConfirmationWithoutAnimation();
                    }
                } else {
                    if (holder.shouldAnimateNextTransformation()) {
                        holder.collapsibleComponentLayout.hideConfirmation();
                        holder.setAnimateNextTransformation(false);
                    } else {
                        holder.collapsibleComponentLayout.hideConfirmationWithoutAnimation();
                    }
                }
            }
            holder.optionLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

            if (addedEvents.size() > 0 || availableEvents.size() > 0) {
                for (EventBean event : addedEvents) {
                    if (availableEvents.contains(event.eventName)) {
                        LinearLayout linearLayout = (LinearLayout) wB.a(requireContext(), R.layout.fr_logic_list_item_event_preview);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, (int) wB.a(requireContext(), 4.0f), 0);
                        linearLayout.setLayoutParams(layoutParams);
                        ((ImageView) linearLayout.findViewById(R.id.icon)).setImageResource(oq.a(event.eventName));
                        linearLayout.findViewById(R.id.icon_bg).setBackgroundResource(R.drawable.circle_bg_white_outline_secondary);
                        holder.eventsPreview.addView(linearLayout);
                        availableEvents.remove(event.eventName);
                    }
                }

                for (String eventName : availableEvents) {
                    LinearLayout linearLayout2 = (LinearLayout) wB.a(requireContext(), R.layout.fr_logic_list_item_event_preview);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams2.setMargins(0, 0, (int) wB.a(requireContext(), 4.0f), 0);
                    linearLayout2.setLayoutParams(layoutParams2);
                    ImageView imageView = linearLayout2.findViewById(R.id.icon);
                    imageView.setImageResource(oq.a(eventName));
                    ColorMatrix colorMatrix = new ColorMatrix();
                    colorMatrix.setSaturation(0.0f);
                    imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                    holder.eventsPreview.addView(linearLayout2);
                    linearLayout2.setScaleX(0.8f);
                    linearLayout2.setScaleY(0.8f);
                }

                holder.componentEvents.setVisibility(View.VISIBLE);
                if (!componentBean.isCollapsed) {
                    holder.eventsPreview.setTranslationX(holder.eventsPreview.getWidth());
                    holder.eventsPreview.setAlpha(0);
                }
                holder.addedEventsAdapter.submitList(addedEvents);
                holder.availableEventsAdapter.submitList(availableEvents);
                holder.componentEvents.setAdapter(holder.componentEventsAdapter);
            } else {
                holder.componentEvents.setVisibility(View.GONE);
            }
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fr_logic_list_item_component, parent, false));
        }

        @Override
        public int getItemCount() {
            int size = components.size();
            if (size == 0) {
                empty.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.GONE);
            }
            return size;
        }
    }

    public void unselectAll() {
        if (projectFile != null) {
            for (ComponentBean component : jC.a(sc_id).e(projectFile.getJavaName())) {
                component.initValue();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initialize(ViewGroup viewGroup) {
        empty = viewGroup.findViewById(R.id.empty_message);
        RecyclerView componentList = viewGroup.findViewById(R.id.component_list);
        componentList.setHasFixedSize(true);
        empty.setVisibility(View.GONE);
        empty.setText(xB.b().a(requireContext(), R.string.component_message_no_components));
        componentList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        adapter = new Adapter(componentList);
        componentList.setAdapter(adapter);
        fab = viewGroup.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    public void setProjectFile(ProjectFileBean projectFileBean) {
        projectFile = projectFileBean;
    }

    private void openEvent(String targetId, String eventName, String eventText) {
        Intent intent = new Intent(requireActivity().getApplicationContext(), LogicEditorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("id", targetId);
        intent.putExtra("event", eventName);
        intent.putExtra("project_file", projectFile);
        intent.putExtra("event_text", eventText);
        startActivity(intent);
    }
}
