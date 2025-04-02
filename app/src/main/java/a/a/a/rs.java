package a.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigationrail.NavigationRailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.moreblock.importer.MoreblockImporterDialog;
import mod.hey.studios.util.Helper;
import mod.jbk.editor.manage.MoreblockImporter;
import pro.sketchware.R;

public class rs extends qA implements View.OnClickListener, MoreblockImporterDialog.CallBack {

    private ProjectFileBean currentActivity;
    private NavigationRailView paletteView;
    private EventAdapter eventAdapter;
    private FloatingActionButton fab;
    private HashMap<Integer, ArrayList<EventBean>> events;
    private ArrayList<EventBean> moreBlocks;
    private ArrayList<EventBean> viewEvents;
    private ArrayList<EventBean> componentEvents;
    private ArrayList<EventBean> activityEvents;
    private ArrayList<EventBean> drawerViewEvents;
    private TextView noEvents;
    private MaterialButton importMoreBlockFromCollection;
    private String sc_id;
    private final ActivityResultLauncher<Intent> addEventLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> refreshEvents());
    private final ActivityResultLauncher<Intent> openEvent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        // in case any Events were added, e.g. a new MoreBlock
        refreshEvents();
    });

    public static int a(int i) {
        return switch (i) {
            case 0 -> R.drawable.ic_mtrl_lifecycle;
            case 1 -> R.drawable.ic_mtrl_devices;
            case 2 -> R.drawable.ic_mtrl_component;
            case 3 -> R.drawable.ic_drawer;
            case 4 -> R.drawable.ic_mtrl_block;
            default -> 0;
        };
    }

    public static String a(Context context, int i) {
        return switch (i) {
            case 0 -> xB.b().a(context, R.string.common_word_activity);
            case 1 -> xB.b().a(context, R.string.common_word_view);
            case 2 -> xB.b().a(context, R.string.common_word_component);
            case 3 -> xB.b().a(context, R.string.common_word_drawer);
            case 4 -> xB.b().a(context, R.string.common_word_moreblock);
            default -> "";
        };
    }

    private int getPaletteIndex(int id) {
        if (id == R.id.activity) {
            return 0;
        } else if (id == R.id.view) {
            return 1;
        } else if (id == R.id.component) {
            return 2;
        } else if (id == R.id.drawer) {
            return 3;
        } else if (id == R.id.moreblock) {
            return 4;
        }
        return -1;
    }

    private int getPaletteIndex() {
        return getPaletteIndex(paletteView.getSelectedItemId());
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && v.getId() == R.id.fab) {
            Intent intent = new Intent(requireActivity().getApplicationContext(), AddEventActivity.class);
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("project_file", currentActivity);
            intent.putExtra("category_index", getPaletteIndex());
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

    public void setCurrentActivity(ProjectFileBean projectFileBean) {
        currentActivity = projectFileBean;
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
            if (getPaletteIndex() == -1) {
                eventAdapter.a(events.get(0));
                paletteView.setSelectedItemId(R.id.activity);
            }
            if (getPaletteIndex() == 4) {
                importMoreBlockFromCollection.setVisibility(View.VISIBLE);
            } else {
                importMoreBlockFromCollection.setVisibility(View.GONE);
            }
            if (eventAdapter != null) {
                eventAdapter.a(events.get(getPaletteIndex()));
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
            events.get(getPaletteIndex()).remove(position);
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
        paletteView = parent.findViewById(R.id.palette);
        paletteView.setOnItemSelectedListener(
                item -> {
                    initializeEvents(events.get(getPaletteIndex(item.getItemId())));
                    if (getPaletteIndex(item.getItemId()) == 4) {
                        importMoreBlockFromCollection.setVisibility(View.VISIBLE);
                    } else {
                        importMoreBlockFromCollection.setVisibility(View.GONE);
                    }
                    eventAdapter.a(events.get(getPaletteIndex(item.getItemId())));
                    eventAdapter.notifyDataSetChanged();
                    return true;
                });
        fab = parent.findViewById(R.id.fab);
        noEvents.setVisibility(View.GONE);
        noEvents.setText(xB.b().a(requireContext(), R.string.event_message_no_events));
        eventList.setLayoutManager(new LinearLayoutManager(null, RecyclerView.VERTICAL, false));
        eventAdapter = new EventAdapter();
        eventList.setAdapter(eventAdapter);
        fab.setOnClickListener(this);
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
        MaterialAlertDialogBuilder aBVar = new MaterialAlertDialogBuilder(requireActivity());
        aBVar.setTitle(xB.b().a(requireContext(), R.string.logic_more_block_favorites_save_title));
        aBVar.setIcon(R.drawable.ic_bookmark_red_48dp);
        View a2 = wB.a(requireContext(), R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(requireContext(), R.string.logic_more_block_favorites_save_guide));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        NB nb = new NB(requireContext(), a2.findViewById(R.id.ti_input), Pp.h().g());
        aBVar.setView(a2);
        aBVar.setPositiveButton(xB.b().a(requireContext(), R.string.common_word_save), (v, which) -> {
            if (nb.b()) {
                saveMoreBlockToCollection(Helper.getText(editText), moreBlocks.get(moreBlockPosition));
                mB.a(requireContext(), editText);
                v.dismiss();
            }
        });
        aBVar.setNegativeButton(xB.b().a(requireContext(), R.string.common_word_cancel), (v, which) -> {
            mB.a(requireContext(), editText);
            v.dismiss();
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

    private void deleteEvent(EventBean event, int position) {
        EventBean.deleteEvent(sc_id, event, currentActivity);
        bB.a(requireContext(), xB.b().a(requireContext(), R.string.common_message_complete_delete), 0).show();
        events.get(getPaletteIndex()).remove(position);
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
            if (!paramClassInfo.isEmpty()) {
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
                holder.icon.setImageResource(R.drawable.ic_mtrl_code);
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
                    holder.icon.setImageResource(R.drawable.ic_mtrl_code);
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
            if (arrayList.isEmpty()) {
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
            public final MaterialCardView root;
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
                root = (MaterialCardView) itemView;
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
                        EventBean eventBean = (events.get(getPaletteIndex())).get(getLayoutPosition());
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
                                    if (getPaletteIndex() != 4) {
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
                        EventBean eventBean = events.get(getPaletteIndex()).get(getLayoutPosition());
                        openEvent(eventBean.targetId, eventBean.eventName, Helper.getText(description));
                    }
                });
            }

            @Override
            protected boolean isCollapsed() {
                return events.get(getPaletteIndex()).get(getLayoutPosition()).isCollapsed;
            }

            @Override
            protected void setIsCollapsed(boolean isCollapsed) {
                events.get(getPaletteIndex()).get(getLayoutPosition()).isCollapsed = isCollapsed;
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
