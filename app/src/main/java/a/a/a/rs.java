package a.a.a;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.besome.sketch.editor.event.AddEventActivity;
import com.besome.sketch.editor.event.CollapsibleEventLayout;
import com.besome.sketch.lib.base.CollapsibleViewHolder;
import com.besome.sketch.lib.ui.CollapsibleButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.moreblock.importer.MoreblockImporterDialog;
import mod.jbk.editor.manage.MoreblockImporter;

public class rs extends qA implements View.OnClickListener, MoreblockImporterDialog.CallBack {
    private ProjectFileBean currentActivity;
    private CategoryAdapter categoryAdapter;
    private EventAdapter eventAdapter;
    private FloatingActionButton fab;
    private final ActivityResultLauncher<Intent> addEventLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> refreshEvents());
    private final ActivityResultLauncher<Intent> openEvent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        // in case any Events were added, e.g. a new MoreBlock
        refreshEvents();
    });

    private HashMap<Integer, ArrayList<EventBean>> events;
    private ArrayList<EventBean> moreBlocks;
    private ArrayList<EventBean> viewEvents;
    private ArrayList<EventBean> componentEvents;
    private ArrayList<EventBean> activityEvents;
    private ArrayList<EventBean> drawerViewEvents;
    private TextView noEvents;
    private TextView importMoreBlockFromCollection;
    private String sc_id;

    public static int a(int i) {
        if (i == 4) {
            return R.drawable.more_block_96dp;
        }
        if (i == 1) {
            return R.drawable.multiple_devices_48;
        }
        if (i == 0) {
            return R.drawable.ic_cycle_color_48dp;
        }
        if (i == 3) {
            return R.drawable.ic_drawer_color_48dp;
        }
        return i == 2 ? R.drawable.component_96 : 0;
    }

    public static String a(Context context, int i) {
        if (i == 4) {
            return xB.b().a(context, R.string.common_word_moreblock);
        }
        if (i == 1) {
            return xB.b().a(context, R.string.common_word_view);
        }
        if (i == 0) {
            return xB.b().a(context, R.string.common_word_activity);
        }
        if (i == 3) {
            return xB.b().a(context, R.string.common_word_drawer);
        }
        return i == 2 ? xB.b().a(context, R.string.common_word_component) : "";
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && v.getId() == R.id.fab) {
            Intent intent = new Intent(requireActivity().getApplicationContext(), AddEventActivity.class);
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("project_file", currentActivity);
            intent.putExtra("category_index", categoryAdapter.index);
            addEventLauncher.launch(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fr_logic_list, container, false);
        initialize(view);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            sc_id = savedInstanceState.getString("sc_id");
        } else {
            sc_id = requireActivity().getIntent().getStringExtra("sc_id");
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    public ProjectFileBean getCurrentActivity() {
        return currentActivity;
    }

    public void refreshEvents() {
        if (currentActivity != null) {
            moreBlocks.clear();
            viewEvents.clear();
            componentEvents.clear();
            activityEvents.clear();
            drawerViewEvents.clear();
            for (Pair<String, String> moreBlock : jC.a(sc_id).i(currentActivity.getJavaName())) {
                EventBean eventBean = new EventBean(EventBean.EVENT_TYPE_ETC, -1, moreBlock.first, "moreBlock");
                eventBean.initValue();
                moreBlocks.add(eventBean);
            }
            EventBean eventBean2 = new EventBean(EventBean.EVENT_TYPE_ACTIVITY, -1, "onCreate", "initializeLogic");
            eventBean2.initValue();
            activityEvents.add(eventBean2);
            for (EventBean eventBean : jC.a(sc_id).g(currentActivity.getJavaName())) {
                eventBean.initValue();
                int i = eventBean.eventType;
                if (i == EventBean.EVENT_TYPE_VIEW) {
                    viewEvents.add(eventBean);
                } else if (i == EventBean.EVENT_TYPE_COMPONENT) {
                    componentEvents.add(eventBean);
                } else if (i == EventBean.EVENT_TYPE_ACTIVITY) {
                    activityEvents.add(eventBean);
                } else if (i == EventBean.EVENT_TYPE_DRAWER_VIEW) {
                    drawerViewEvents.add(eventBean);
                }
            }
            if (categoryAdapter.index == -1) {
                eventAdapter.a(events.get(0));
                categoryAdapter.index = 0;
                categoryAdapter.notifyDataSetChanged();
            }
            if (categoryAdapter.index == 4) {
                importMoreBlockFromCollection.setVisibility(View.VISIBLE);
            } else {
                importMoreBlockFromCollection.setVisibility(View.GONE);
            }
            if (eventAdapter != null) {
                if (categoryAdapter != null) {
                    categoryAdapter.notifyDataSetChanged();
                }
                eventAdapter.a(events.get(categoryAdapter.index));
                eventAdapter.notifyDataSetChanged();
            }
        }
    }

    private void deleteMoreBlock(EventBean moreBlock, int position) {
        if (jC.a(sc_id).f(currentActivity.getJavaName(), moreBlock.targetId)) {
            bB.b(requireContext(), xB.b().a(requireContext(), R.string.logic_editor_message_currently_used_block), 0).show();
        } else {
            jC.a(sc_id).n(currentActivity.getJavaName(), moreBlock.targetId);
            bB.a(requireContext(), xB.b().a(requireContext(), R.string.common_message_complete_delete), 0).show();
            events.get(categoryAdapter.index).remove(position);
            eventAdapter.notifyItemRemoved(position);
            eventAdapter.notifyItemRangeChanged(position, eventAdapter.getItemCount());
        }
    }

    public void c() {
        if (currentActivity != null) {
            for (Map.Entry<Integer, ArrayList<EventBean>> entry : events.entrySet()) {
                for (EventBean bean : entry.getValue()) {
                    bean.initValue();
                }
            }
            eventAdapter.notifyDataSetChanged();
        }
    }

    private void initialize(ViewGroup parent) {
        noEvents = parent.findViewById(R.id.tv_no_events);
        RecyclerView eventList = parent.findViewById(R.id.event_list);
        RecyclerView categoryList = parent.findViewById(R.id.category_list);
        fab = parent.findViewById(R.id.fab);
        noEvents.setVisibility(View.GONE);
        noEvents.setText(xB.b().a(requireContext(), R.string.event_message_no_events));
        eventList.setLayoutManager(new LinearLayoutManager(null, RecyclerView.VERTICAL, false));
        categoryList.setLayoutManager(new LinearLayoutManager(null, RecyclerView.VERTICAL, false));
        ((SimpleItemAnimator) categoryList.getItemAnimator()).setSupportsChangeAnimations(false);
        categoryAdapter = new CategoryAdapter();
        categoryList.setAdapter(categoryAdapter);
        eventAdapter = new EventAdapter();
        eventList.setAdapter(eventAdapter);
        fab.setOnClickListener(this);
        eventList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        events = new HashMap<>();
        moreBlocks = new ArrayList<>();
        viewEvents = new ArrayList<>();
        componentEvents = new ArrayList<>();
        activityEvents = new ArrayList<>();
        drawerViewEvents = new ArrayList<>();
        events.put(0, activityEvents);
        events.put(1, viewEvents);
        events.put(2, componentEvents);
        events.put(3, drawerViewEvents);
        events.put(4, moreBlocks);
        importMoreBlockFromCollection = parent.findViewById(R.id.tv_import);
        importMoreBlockFromCollection.setText(xB.b().a(requireContext(), R.string.logic_button_import_more_block));
        importMoreBlockFromCollection.setOnClickListener(v -> showImportMoreBlockFromCollectionsDialog());
    }

    private void showSaveMoreBlockToCollectionsDialog(int moreBlockPosition) {
        aB aBVar = new aB(requireActivity());
        aBVar.b(xB.b().a(requireContext(), R.string.logic_more_block_favorites_save_title));
        aBVar.a(R.drawable.ic_bookmark_red_48dp);
        View a2 = wB.a(requireContext(), R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(requireContext(), R.string.logic_more_block_favorites_save_guide));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        NB nb = new NB(requireContext(), a2.findViewById(R.id.ti_input), Pp.h().g());
        aBVar.a(a2);
        aBVar.b(xB.b().a(requireContext(), R.string.common_word_save), v -> {
            if (nb.b()) {
                saveMoreBlockToCollection(editText.getText().toString(), moreBlocks.get(moreBlockPosition));
                mB.a(requireContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(requireContext(), R.string.common_word_cancel), v -> {
            mB.a(requireContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    private void resetEvent(EventBean event) {
        eC a2 = jC.a(sc_id);
        String javaName = currentActivity.getJavaName();
        a2.a(javaName, event.targetId + "_" + event.eventName, new ArrayList<>());
        bB.a(requireContext(), xB.b().a(requireContext(), R.string.common_message_complete_reset), 0).show();
    }

    @Override
    public void onSelected(MoreBlockCollectionBean bean) {
        new MoreblockImporter(requireActivity(), sc_id, currentActivity).importMoreblock(bean, this::refreshEvents);
    }

    private void showImportMoreBlockFromCollectionsDialog() {
        ArrayList<MoreBlockCollectionBean> moreBlocksInCollections = Pp.h().f();
        new MoreblockImporterDialog(requireActivity(), moreBlocksInCollections, this).show();
    }

    public void setCurrentActivity(ProjectFileBean projectFileBean) {
        currentActivity = projectFileBean;
    }

    private void deleteEvent(EventBean event, int position) {
        EventBean.deleteEvent(sc_id, event, currentActivity);
        bB.a(requireContext(), xB.b().a(requireContext(), R.string.common_message_complete_delete), 0).show();
        events.get(categoryAdapter.index).remove(position);
        eventAdapter.notifyItemRemoved(position);
        eventAdapter.notifyItemRangeChanged(position, eventAdapter.getItemCount());
    }

    private void initializeEvents(ArrayList<EventBean> events) {
        for (EventBean bean : events) {
            bean.initValue();
        }
    }

    private void openEvent(String targetId, String eventId, String description) {
        Intent intent = new Intent(requireActivity(), LogicEditorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("id", targetId);
        intent.putExtra("event", eventId);
        intent.putExtra("project_file", currentActivity);
        intent.putExtra("event_text", description);
        openEvent.launch(intent);
    }

    private void saveMoreBlockToCollection(String moreBlockName, EventBean moreBlock) {
        String b2 = jC.a(sc_id).b(currentActivity.getJavaName(), moreBlock.targetId);
        eC a2 = jC.a(sc_id);
        String javaName = currentActivity.getJavaName();
        ArrayList<BlockBean> moreBlockBlocks = a2.a(javaName, moreBlock.targetId + "_" + moreBlock.eventName);

        boolean hasAnyBlocks = false;
        boolean failedToAddResourceToCollections = false;
        for (BlockBean next : moreBlockBlocks) {
            ArrayList<Gx> paramClassInfo = next.getParamClassInfo();
            if (paramClassInfo.size() > 0) {
                for (int i = 0; i < paramClassInfo.size(); i++) {
                    Gx gx = paramClassInfo.get(i);
                    String parameter = next.parameters.get(i);

                    if (gx.b("resource") || gx.b("resource_bg")) {
                        if (jC.d(sc_id).l(parameter) && !Op.g().b(parameter)) {
                            Op.g().a(sc_id, jC.d(sc_id).g(parameter));
                        }
                    } else if (gx.b("sound")) {
                        if (jC.d(sc_id).m(parameter) && !Qp.g().b(parameter)) {
                            try {
                                Qp.g().a(sc_id, jC.d(sc_id).j(parameter));
                            } catch (Exception unused) {
                                failedToAddResourceToCollections = true;
                            }
                        }
                    } else if (gx.b("font")) {
                        if (jC.d(sc_id).k(parameter) && !Np.g().b(parameter)) {
                            Np.g().a(sc_id, jC.d(sc_id).e(parameter));
                        }
                    }
                }
                hasAnyBlocks = true;
            }
        }
        if (hasAnyBlocks) {
            if (failedToAddResourceToCollections) {
                bB.b(requireContext(), xB.b().a(requireContext(), R.string.logic_more_block_message_missed_resource_exist), 0).show();
            } else {
                bB.a(requireContext(), xB.b().a(requireContext(), R.string.logic_more_block_message_resource_added), 0).show();
            }
        }
        try {
            Pp.h().a(moreBlockName, b2, moreBlockBlocks, true);
        } catch (Exception unused2) {
            bB.b(requireContext(), xB.b().a(requireContext(), R.string.common_error_failed_to_save), 0).show();
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
        private int index = -1;

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.name.setText(rs.a(requireContext(), position));
            holder.icon.setImageResource(rs.a(position));
            if (index == position) {
                ViewPropertyAnimatorCompat animator1 = ViewCompat.animate(holder.icon);
                animator1.scaleX(1);
                animator1.scaleY(1);
                animator1.setDuration(300);
                animator1.setInterpolator(new AccelerateInterpolator());
                animator1.start();
                ViewPropertyAnimatorCompat animator2 = ViewCompat.animate(holder.icon);
                animator2.scaleX(1);
                animator2.scaleY(1);
                animator2.setDuration(300);
                animator2.setInterpolator(new AccelerateInterpolator());
                animator2.start();
                holder.pointerLeft.setVisibility(View.VISIBLE);
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(1);
                holder.icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            } else {
                ViewPropertyAnimatorCompat animator1 = ViewCompat.animate(holder.icon);
                animator1.scaleX(0.8f);
                animator1.scaleY(0.8f);
                animator1.setDuration(300);
                animator1.setInterpolator(new DecelerateInterpolator());
                animator1.start();
                ViewPropertyAnimatorCompat animator2 = ViewCompat.animate(holder.icon);
                animator2.scaleX(0.8f);
                animator2.scaleY(0.8f);
                animator2.setDuration(300);
                animator2.setInterpolator(new DecelerateInterpolator());
                animator2.start();
                holder.pointerLeft.setVisibility(View.GONE);
                ColorMatrix colorMatrix2 = new ColorMatrix();
                colorMatrix2.setSaturation(0);
                holder.icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
            }
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.common_category_triangle_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public final ImageView icon;
            public final TextView name;
            public final View pointerLeft;

            public ViewHolder(View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.img_icon);
                name = itemView.findViewById(R.id.tv_name);
                pointerLeft = itemView.findViewById(R.id.pointer_left);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                notifyItemChanged(index);
                index = getLayoutPosition();
                notifyItemChanged(index);
                initializeEvents(events.get(index));
                if (index == 4) {
                    importMoreBlockFromCollection.setVisibility(View.VISIBLE);
                } else {
                    importMoreBlockFromCollection.setVisibility(View.GONE);
                }
                eventAdapter.a(events.get(index));
                eventAdapter.notifyDataSetChanged();
            }
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
        private ArrayList<EventBean> currentCategoryEvents = new ArrayList<>();

        @Override
        public int getItemCount() {
            return currentCategoryEvents.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            EventBean eventBean = currentCategoryEvents.get(position);
            holder.targetType.setVisibility(View.VISIBLE);
            holder.previewContainer.setVisibility(View.VISIBLE);
            holder.preview.setVisibility(View.VISIBLE);
            holder.preview.setImageResource(oq.a(eventBean.eventName));
            holder.optionsLayout.showDelete();
            if (eventBean.eventType == EventBean.EVENT_TYPE_ETC) {
                holder.optionsLayout.showAddToCollection();
            } else {
                holder.optionsLayout.hideAddToCollection();
            }
            if (eventBean.eventType == EventBean.EVENT_TYPE_ACTIVITY) {
                if (eventBean.eventName.equals("initializeLogic")) {
                    holder.optionsLayout.hideDelete();
                }
                holder.targetId.setText(eventBean.targetId);
                holder.type.setBackgroundResource(oq.a(eventBean.eventName));
                holder.name.setText(eventBean.eventName);
                holder.description.setText(oq.a(eventBean.eventName, requireContext()));
                holder.icon.setImageResource(R.drawable.widget_source);
                holder.preview.setVisibility(View.GONE);
                holder.targetType.setVisibility(View.GONE);
            } else {
                holder.icon.setImageResource(EventBean.getEventIconResource(eventBean.eventType, eventBean.targetType));
                if (eventBean.eventType == EventBean.EVENT_TYPE_VIEW) {
                    holder.targetType.setText(ViewBean.getViewTypeName(eventBean.targetType));
                } else if (eventBean.eventType == EventBean.EVENT_TYPE_DRAWER_VIEW) {
                    holder.targetType.setText(ViewBean.getViewTypeName(eventBean.targetType));
                } else if (eventBean.eventType == EventBean.EVENT_TYPE_COMPONENT) {
                    holder.targetType.setText(ComponentBean.getComponentName(requireContext(), eventBean.targetType));
                } else if (eventBean.eventType == EventBean.EVENT_TYPE_ETC) {
                    holder.icon.setImageResource(R.drawable.widget_source);
                    holder.targetType.setVisibility(View.GONE);
                    holder.preview.setVisibility(View.GONE);
                }
                if (eventBean.targetId.equals("_fab")) {
                    holder.targetId.setText("fab");
                } else {
                    holder.targetId.setText(ReturnMoreblockManager.getMbName(eventBean.targetId));
                }
                holder.type.setText(EventBean.getEventTypeName(eventBean.eventType));
                holder.type.setBackgroundResource(EventBean.getEventTypeBgRes(eventBean.eventType));
                holder.name.setText(eventBean.eventName);
                holder.description.setText(oq.a(eventBean.eventName, requireContext()));
                if (eventBean.eventType == EventBean.EVENT_TYPE_ETC) {
                    holder.description.setText(ReturnMoreblockManager.getMbTypeList(eventBean.targetId));
                }
            }
            if (eventBean.isCollapsed) {
                holder.optionContainer.setVisibility(View.GONE);
                holder.menu.setRotation(0);
            } else {
                holder.optionContainer.setVisibility(View.VISIBLE);
                holder.menu.setRotation(-180);
            }
            if (eventBean.isConfirmation) {
                if (holder.shouldAnimateNextTransformation()) {
                    holder.optionsLayout.showConfirmation();
                    holder.setAnimateNextTransformation(false);
                } else {
                    holder.optionsLayout.showConfirmationWithoutAnimation();
                }
            } else {
                if (holder.shouldAnimateNextTransformation()) {
                    holder.optionsLayout.hideConfirmation();
                } else {
                    holder.optionsLayout.hideConfirmationWithoutAnimation();
                }
            }
            holder.optionContainer.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        public void a(ArrayList<EventBean> arrayList) {
            if (arrayList.size() == 0) {
                noEvents.setVisibility(View.VISIBLE);
            } else {
                noEvents.setVisibility(View.GONE);
            }
            currentCategoryEvents = arrayList;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fr_logic_list_item, parent, false));
        }

        private class ViewHolder extends CollapsibleViewHolder {
            public final LinearLayout root;
            public final ImageView menu;
            public final ImageView preview;
            public final LinearLayout previewContainer;
            public final LinearLayout optionContainer;
            public final CollapsibleEventLayout optionsLayout;
            public final ImageView icon;
            public final TextView targetType;
            public final TextView targetId;
            public final TextView type;
            public final TextView name;
            public final TextView description;

            public ViewHolder(View itemView) {
                super(itemView, 200);
                root = (LinearLayout) itemView;
                icon = itemView.findViewById(R.id.img_icon);
                targetType = itemView.findViewById(R.id.tv_target_type);
                targetId = itemView.findViewById(R.id.tv_target_id);
                type = itemView.findViewById(R.id.tv_event_type);
                name = itemView.findViewById(R.id.tv_event_name);
                description = itemView.findViewById(R.id.tv_event_text);
                menu = itemView.findViewById(R.id.img_menu);
                preview = itemView.findViewById(R.id.img_preview);
                previewContainer = itemView.findViewById(R.id.ll_preview);
                optionContainer = itemView.findViewById(R.id.event_option_layout);
                optionsLayout = itemView.findViewById(R.id.event_option);
                optionsLayout.setButtonOnClickListener(v -> {
                    if (!mB.a()) {
                        EventBean eventBean = (events.get(categoryAdapter.index)).get(getLayoutPosition());
                        if (v instanceof CollapsibleButton button) {
                            setAnimateNextTransformation(true);
                            int id = button.getButtonId();
                            if (id == 2) {
                                eventBean.buttonPressed = id;
                                eventBean.isConfirmation = false;
                                eventBean.isCollapsed = false;
                                notifyItemChanged(getLayoutPosition());
                                showSaveMoreBlockToCollectionsDialog(getLayoutPosition());
                            } else {
                                eventBean.buttonPressed = id;
                                eventBean.isConfirmation = true;
                                notifyItemChanged(getLayoutPosition());
                            }
                        } else {
                            if (v.getId() == R.id.confirm_no) {
                                eventBean.isConfirmation = false;
                                setAnimateNextTransformation(true);
                                notifyItemChanged(getLayoutPosition());
                            } else if (v.getId() == R.id.confirm_yes) {
                                if (eventBean.buttonPressed == 0) {
                                    eventBean.isConfirmation = false;
                                    eventBean.isCollapsed = true;
                                    setAnimateNextTransformation(true);
                                    resetEvent(eventBean);
                                    notifyItemChanged(getLayoutPosition());
                                } else if (eventBean.buttonPressed == 1) {
                                    eventBean.isConfirmation = false;
                                    if (categoryAdapter.index != 4) {
                                        deleteEvent(eventBean, getLayoutPosition());
                                    } else {
                                        deleteMoreBlock(eventBean, getLayoutPosition());
                                    }
                                }
                                fab.show();
                            }
                        }
                    }
                });
                onDoneInitializingViews();
                root.setOnClickListener(v -> {
                    if (!mB.a()) {
                        EventBean eventBean = events.get(categoryAdapter.index).get(getLayoutPosition());
                        openEvent(eventBean.targetId, eventBean.eventName, description.getText().toString());
                    }
                });
            }

            @Override
            protected boolean isCollapsed() {
                return events.get(categoryAdapter.index).get(getLayoutPosition()).isCollapsed;
            }

            @Override
            protected void setIsCollapsed(boolean isCollapsed) {
                events.get(categoryAdapter.index).get(getLayoutPosition()).isCollapsed = isCollapsed;
            }

            @NonNull
            @Override
            protected ViewGroup getOptionsLayout() {
                return optionContainer;
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnClickCollapseTriggerViews() {
                return Set.of(menu);
            }

            @NonNull
            @Override
            protected Set<? extends View> getOnLongClickCollapseTriggerViews() {
                return Set.of(root);
            }
        }
    }
}
