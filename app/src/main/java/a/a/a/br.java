package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.besome.sketch.editor.component.ComponentAddActivity;
import com.besome.sketch.editor.component.ComponentEventButton;
import com.besome.sketch.lib.base.CollapsibleViewHolder;
import com.besome.sketch.lib.ui.CollapsibleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import pro.sketchware.R;
import pro.sketchware.databinding.FrComponentListBinding;
import pro.sketchware.databinding.FrLogicListItemComponentBinding;
import pro.sketchware.databinding.FrLogicListItemEventPreviewBinding;

public class br extends qA implements View.OnClickListener {

    private ProjectFileBean projectFile;
    private Adapter adapter;
    private String sc_id;
    private ArrayList<ComponentBean> components = new ArrayList<>();
    private final ActivityResultLauncher<Intent> addComponent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            refreshData();
        }
    });

    private FrComponentListBinding binding;

    public void refreshData() {
        if (projectFile != null && adapter != null) {
            components = jC.a(sc_id).e(projectFile.getJavaName());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && v.getId() == binding.fab.getId()) {
            Intent intent = new Intent(requireContext(), ComponentAddActivity.class);
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("project_file", projectFile);
            addComponent.launch(intent);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrComponentListBinding.inflate(inflater, container, false);
        initialize();
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            sc_id = savedInstanceState.getString("sc_id");
        } else {
            sc_id = requireActivity().getIntent().getStringExtra("sc_id");
        }
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    public void unselectAll() {
        if (projectFile != null) {
            for (ComponentBean component : jC.a(sc_id).e(projectFile.getJavaName())) {
                component.initValue();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void initialize() {
        binding.componentList.setHasFixedSize(true);
        binding.emptyMessage.setVisibility(View.GONE);
        binding.emptyMessage.setText(xB.b().a(requireContext(), R.string.component_message_no_components));
        binding.componentList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        adapter = new Adapter();
        binding.componentList.setAdapter(adapter);
        binding.fab.setOnClickListener(this);
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

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private static final ConcatAdapter.Config EVENTS_ADAPTER_CONFIG = new ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build();
        private final RecyclerView.RecycledViewPool eventViewHolders = new RecyclerView.RecycledViewPool();

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ComponentBean componentBean = components.get(position);
            holder.bind(componentBean);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(FrLogicListItemComponentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public int getItemCount() {
            int size = components.size();
            if (size == 0) {
                binding.emptyMessage.setVisibility(View.VISIBLE);
            } else {
                binding.emptyMessage.setVisibility(View.GONE);
            }
            return size;
        }

        private class ViewHolder extends CollapsibleViewHolder {

            public final FrLogicListItemComponentBinding binding;

            public ViewHolder(FrLogicListItemComponentBinding itemBinding) {
                super(itemBinding.getRoot(), 200);
                binding = itemBinding;
            }

            void bind(ComponentBean componentBean) {
                binding.componentOption
                        .getDeleteButton()
                        .getLabel()
                        .setText(xB.b().a(requireContext(), R.string.component_context_menu_title_delete_component));
                binding.componentOption.setButtonOnClickListener(v -> {
                    int lastSelectedItem = getLayoutPosition();
                    ComponentBean bean =
                            jC.a(sc_id).a(projectFile.getJavaName(), lastSelectedItem);
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
                setOnClickCollapseConfig(v -> v != binding.getRoot());
                binding.componentEvents.setRecycledViewPool(eventViewHolders);
                if (binding.componentEvents.getLayoutManager()
                        instanceof LinearLayoutManager manager) {
                    manager.setRecycleChildrenOnDetach(true);
                }
                binding.componentEvents.setItemAnimator(null);
                AddedEventsAdapter addedEventsAdapter = new AddedEventsAdapter();
                AvailableEventsAdapter availableEventsAdapter = new AvailableEventsAdapter();
                availableEventsAdapter.setOnEventClickListener(event -> {
                    var newAddedEvents = new ArrayList<>(addedEventsAdapter.getCurrentList());
                    newAddedEvents.add(event);
                    addedEventsAdapter.submitList(newAddedEvents);
                    var newAvailableEvents = new ArrayList<>(availableEventsAdapter.getCurrentList());
                    newAvailableEvents.remove(event.eventName);
                    availableEventsAdapter.submitList(newAvailableEvents);
                    openEvent(event.targetId, event.eventName, event.eventName);
                });
                ConcatAdapter componentEventsAdapter = new ConcatAdapter(
                        EVENTS_ADAPTER_CONFIG, addedEventsAdapter, availableEventsAdapter);

                binding.tvComponentType.setText(
                        ComponentBean.getComponentName(requireContext(), componentBean.type));
                binding.imgIcon.setImageResource(
                        ComponentBean.getIconResource(componentBean.type));

                switch (componentBean.type) {
                    case ComponentBean.COMPONENT_TYPE_SHAREDPREF -> binding.tvComponentId
                            .setText(componentBean.componentId + " : " + componentBean.param1);
                    case ComponentBean.COMPONENT_TYPE_FIREBASE,
                         ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE,
                         ComponentBean.COMPONENT_TYPE_FILE_PICKER -> {
                        String path = componentBean.param1;
                        if (path.isEmpty()) {
                            path = "/";
                        }
                        binding.tvComponentId.setText(componentBean.componentId + " : " + path);
                    }
                    default -> binding.tvComponentId.setText(componentBean.componentId);
                }

                ArrayList<EventBean> addedEvents =
                        jC.a(sc_id).a(projectFile.getJavaName(), componentBean);
                ArrayList<String> availableEvents =
                        new ArrayList<>(Arrays.asList(oq.a(componentBean.getClassInfo())));

                binding.eventsPreview.removeAllViews();
                binding.eventsPreview.setAlpha(1.0f);
                binding.eventsPreview.setTranslationX(0.0f);
                if (componentBean.isCollapsed) {
                    binding.componentOptionLayout.setVisibility(View.GONE);
                    binding.imgMenu.setRotation(0.0f);
                } else {
                    binding.componentOptionLayout.setVisibility(View.VISIBLE);
                    binding.imgMenu.setRotation(-180.0f);
                    if (componentBean.isConfirmation) {
                        if (shouldAnimateNextTransformation()) {
                            binding.componentOption.showConfirmation();
                            setAnimateNextTransformation(false);
                        } else {
                            binding.componentOption.showConfirmationWithoutAnimation();
                        }
                    } else {
                        if (shouldAnimateNextTransformation()) {
                            binding.componentOption.hideConfirmation();
                            setAnimateNextTransformation(false);
                        } else {
                            binding.componentOption.hideConfirmationWithoutAnimation();
                        }
                    }
                }
                binding.componentOptionLayout.getLayoutParams().height =
                        ViewGroup.LayoutParams.WRAP_CONTENT;

                if (!addedEvents.isEmpty() || !availableEvents.isEmpty()) {
                    for (EventBean event : addedEvents) {
                        if (availableEvents.contains(event.eventName)) {
                            FrLogicListItemEventPreviewBinding previewBinding =
                                    FrLogicListItemEventPreviewBinding.inflate(LayoutInflater.from(requireContext()));

                            LinearLayout.LayoutParams layoutParams =
                                    new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0, 0, (int) wB.a(requireContext(), 4.0f), 0);
                            previewBinding.getRoot().setLayoutParams(layoutParams);
                            previewBinding.icon.setImageResource(oq.a(event.eventName));
                            previewBinding.iconBg.setBackgroundResource(
                                    R.drawable.circle_bg_surface);
                            binding.eventsPreview.addView(previewBinding.getRoot());
                            availableEvents.remove(event.eventName);
                        }
                    }

                    for (String eventName : availableEvents) {
                        FrLogicListItemEventPreviewBinding previewBinding =
                                FrLogicListItemEventPreviewBinding.inflate(LayoutInflater.from(requireContext()));
                        LinearLayout.LayoutParams layoutParams2 =
                                new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams2.setMargins(0, 0, (int) wB.a(requireContext(), 4.0f), 0);
                        previewBinding.getRoot().setLayoutParams(layoutParams2);
                        previewBinding.icon.setImageResource(oq.a(eventName));
                        ColorMatrix colorMatrix = new ColorMatrix();
                        colorMatrix.setSaturation(0.0f);
                        previewBinding.icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                        binding.eventsPreview.addView(previewBinding.getRoot());
                        previewBinding.getRoot().setScaleX(0.8f);
                        previewBinding.getRoot().setScaleY(0.8f);
                    }

                    binding.componentEvents.setVisibility(View.VISIBLE);
                    if (!componentBean.isCollapsed) {
                        binding.eventsPreview.setTranslationX(
                                binding.eventsPreview.getWidth());
                        binding.eventsPreview.setAlpha(0);
                    }
                    addedEventsAdapter.submitList(addedEvents);
                    availableEventsAdapter.submitList(availableEvents);
                    binding.componentEvents.setAdapter(componentEventsAdapter);
                } else {
                    binding.componentEvents.setVisibility(View.GONE);
                }
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
                return binding.componentOptionLayout;
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnClickCollapseTriggerViews() {
                return Set.of(binding.imgMenu, binding.getRoot());
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnLongClickCollapseTriggerViews() {
                return Set.of(binding.getRoot());
            }

            @Override
            public void collapse() {
                super.collapse();
                binding.eventsPreview.animate().translationX(0.0f).alpha(1.0f).setStartDelay(120L).setDuration(150L).start();
                binding.componentEvents.animate().translationX(-binding.componentEvents.getWidth()).setDuration(150L).alpha(0.0f).start();
            }

            @Override
            public void expand() {
                super.expand();
                binding.eventsPreview.animate().translationX(binding.eventsPreview.getWidth()).alpha(0.0f).setDuration(150L).start();
                binding.componentEvents.setTranslationX(-binding.componentEvents.getWidth());
                binding.componentEvents.setAlpha(0.0f);
                binding.componentEvents.animate().translationX(0.0f).setStartDelay(200L).setDuration(120L).alpha(1.0f).start();
            }

            private interface EventClickListener {
                void onEventClick(EventBean bean);
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
                    holder.button.setClickListener(v -> {
                        if (!mB.a()) {
                            openEvent(event.targetId, event.eventName, event.eventName);
                        }
                    });
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
                private EventClickListener listener;

                public AvailableEventsAdapter() {
                    super(DIFF_CALLBACK);
                }

                public void setOnEventClickListener(EventClickListener listener) {
                    this.listener = listener;
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
                    holder.button.setClickListener(v -> {
                        if (!mB.a()) {
                            var component = components.get(ViewHolder.this.getLayoutPosition());
                            var event = new EventBean(EventBean.EVENT_TYPE_COMPONENT, component.type, component.componentId, eventName);
                            jC.a(sc_id).a(projectFile.getJavaName(), event);
                            bB.a(requireContext(), xB.b().a(requireContext(), R.string.event_message_new_event), 0).show();
                            holder.button.onEventAdded();
                            if (listener != null) {
                                listener.onEventClick(event);
                            }
                        }
                    });
                }
            }

            private class EventViewHolder extends RecyclerView.ViewHolder {
                public final ComponentEventButton button;

                public EventViewHolder(@NonNull ComponentEventButton itemView) {
                    super(itemView);
                    button = itemView;
                }
            }
        }
    }
}
