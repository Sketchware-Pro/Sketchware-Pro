package a.a.a;

import android.animation.Animator;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.besome.sketch.editor.event.AddEventActivity;
import com.besome.sketch.editor.event.CollapsibleButton;
import com.besome.sketch.editor.event.CollapsibleEventLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.moreblock.importer.MoreblockImporterDialog;
import mod.hey.studios.util.Helper;

public class rs extends qA implements View.OnClickListener, MoreblockImporterDialog.CallBack {
    private static final int REQUEST_CODE_ADD_EVENT = 223;

    private ProjectFileBean currentActivity;
    private CategoryAdapter categoryAdapter;
    private EventAdapter eventAdapter;
    private FloatingActionButton fab;
    private HashMap<Integer, ArrayList<EventBean>> events;
    private ArrayList<EventBean> moreBlocks;
    private ArrayList<EventBean> viewEvents;
    private ArrayList<EventBean> componentEvents;
    private ArrayList<EventBean> activityEvents;
    private ArrayList<EventBean> drawerViewEvents;
    private TextView noEvents;
    private TextView importMoreBlockFromCollection;
    private String sc_id;
    private ArrayList<Pair<Integer, String>> toBeAddedVariables;
    private ArrayList<Pair<Integer, String>> toBeAddedLists;
    private ArrayList<ProjectResourceBean> toBeAddedImages;
    private ArrayList<ProjectResourceBean> toBeAddedSounds;
    private ArrayList<ProjectResourceBean> toBeAddedFonts;
    private final oB fileUtil = new oB();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_EVENT) {
            refreshEvents();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && v.getId() == R.id.fab) {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddEventActivity.class);
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("project_file", currentActivity);
            intent.putExtra("category_index", categoryAdapter.index);
            startActivityForResult(intent, REQUEST_CODE_ADD_EVENT);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
            sc_id = getActivity().getIntent().getStringExtra("sc_id");
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

    private void maybeAddImageToListOfToBeAddedImages(String imageName) {
        if (toBeAddedImages == null) {
            toBeAddedImages = new ArrayList<>();
        }
        for (String imageInProjectName : jC.d(sc_id).m()) {
            if (imageInProjectName.equals(imageName)) {
                return;
            }
        }
        ProjectResourceBean image = Op.g().a(imageName);
        if (image != null) {
            boolean alreadyToBeAdded = false;
            for (ProjectResourceBean toBeAddedImage : toBeAddedImages) {
                if (toBeAddedImage.resName.equals(imageName)) {
                    alreadyToBeAdded = true;
                    break;
                }
            }
            if (!alreadyToBeAdded) {
                toBeAddedImages.add(image);
            }
        }
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
                categoryAdapter.c();
            }
            if (categoryAdapter.index == 4) {
                importMoreBlockFromCollection.setVisibility(View.VISIBLE);
            } else {
                importMoreBlockFromCollection.setVisibility(View.GONE);
            }
            if (eventAdapter != null) {
                if (categoryAdapter != null) {
                    categoryAdapter.c();
                }
                eventAdapter.a(events.get(categoryAdapter.index));
                eventAdapter.c();
            }
        }
    }

    private void maybeAddFontToListOfToBeAddedFonts(String fontName) {
        if (toBeAddedFonts == null) {
            toBeAddedFonts = new ArrayList<>();
        }
        for (String fontInProjectName : jC.d(sc_id).k()) {
            if (fontInProjectName.equals(fontName)) {
                return;
            }
        }
        ProjectResourceBean font = Np.g().a(fontName);
        if (font != null) {
            boolean alreadyToBeAdded = false;
            for (ProjectResourceBean toBeAddedFont : toBeAddedFonts) {
                if (toBeAddedFont.resName.equals(fontName)) {
                    alreadyToBeAdded = true;
                    break;
                }
            }
            if (!alreadyToBeAdded) {
                toBeAddedFonts.add(font);
            }
        }
    }

    private void deleteMoreBlock(EventBean moreBlock) {
        if (jC.a(sc_id).f(currentActivity.getJavaName(), moreBlock.targetId)) {
            bB.b(getContext(), xB.b().a(getContext(), R.string.logic_editor_message_currently_used_block), 0).show();
        } else {
            jC.a(sc_id).n(currentActivity.getJavaName(), moreBlock.targetId);
            bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_delete), 0).show();
            events.get(categoryAdapter.index).remove(eventAdapter.lastSelectedItem);
            eventAdapter.e(eventAdapter.lastSelectedItem);
            eventAdapter.a(eventAdapter.lastSelectedItem, eventAdapter.a());
        }
    }

    public void c() {
        if (currentActivity != null) {
            for (Map.Entry<Integer, ArrayList<EventBean>> entry : events.entrySet()) {
                for (EventBean bean : entry.getValue()) {
                    bean.initValue();
                }
            }
            eventAdapter.c();
        }
    }

    private void initialize(ViewGroup parent) {
        noEvents = parent.findViewById(R.id.tv_no_events);
        RecyclerView eventList = parent.findViewById(R.id.event_list);
        RecyclerView categoryList = parent.findViewById(R.id.category_list);
        fab = parent.findViewById(R.id.fab);
        noEvents.setVisibility(View.GONE);
        noEvents.setText(xB.b().a(getContext(), R.string.event_message_no_events));
        eventList.setHasFixedSize(true);
        eventList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        categoryList.setHasFixedSize(true);
        categoryList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        ((Bi) categoryList.getItemAnimator()).a(false);
        categoryAdapter = new CategoryAdapter();
        categoryList.setAdapter(categoryAdapter);
        eventAdapter = new EventAdapter();
        eventList.setAdapter(eventAdapter);
        fab.setOnClickListener(this);
        // RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)
        eventList.a(new RecyclerView.m() {
            @Override
            // RecyclerView.OnScrollListener#onScrolled(RecyclerView, int, int)
            public void a(RecyclerView recyclerView, int dx, int dy) {
                super.a(recyclerView, dx, dy);
                if (dy > 2) {
                    if (fab.isEnabled()) {
                        // FloatingActionButton#hide()
                        fab.c();
                    }
                } else if (dy < -2) {
                    if (fab.isEnabled()) {
                        // FloatingActionButton#show()
                        fab.f();
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
        importMoreBlockFromCollection.setText(xB.b().a(getContext(), R.string.logic_button_import_more_block));
        importMoreBlockFromCollection.setOnClickListener(v -> showImportMoreBlockFromCollectionsDialog());
    }

    private void addMoreBlockFromCollectionsHandleVariables(MoreBlockCollectionBean moreBlock) {
        toBeAddedVariables = new ArrayList<>();
        toBeAddedLists = new ArrayList<>();
        toBeAddedImages = new ArrayList<>();
        toBeAddedSounds = new ArrayList<>();
        toBeAddedFonts = new ArrayList<>();
        for (BlockBean next : moreBlock.blocks) {
            if (next.opCode.equals("getVar")) {
                if (next.type.equals("b")) {
                    maybeAddVariableToListOfToBeAddedVariables(0, next.spec);
                } else if (next.type.equals("d")) {
                    maybeAddVariableToListOfToBeAddedVariables(1, next.spec);
                } else if (next.type.equals("s")) {
                    maybeAddVariableToListOfToBeAddedVariables(2, next.spec);
                } else if (next.type.equals("a")) {
                    maybeAddVariableToListOfToBeAddedVariables(3, next.spec);
                } else if (next.type.equals("l")) {
                    if (next.typeName.equals("List Number")) {
                        maybeAddListToListOfToBeAddedLists(1, next.spec);
                    } else if (next.typeName.equals("List String")) {
                        maybeAddListToListOfToBeAddedLists(2, next.spec);
                    } else if (next.typeName.equals("List Map")) {
                        maybeAddListToListOfToBeAddedLists(3, next.spec);
                    }
                }
            }
            ArrayList<Gx> paramClassInfo = next.getParamClassInfo();
            if (paramClassInfo.size() > 0) {
                for (int i = 0; i < paramClassInfo.size(); i++) {
                    Gx gx = paramClassInfo.get(i);
                    String str = next.parameters.get(i);
                    if (str.length() > 0 && str.charAt(0) != '@') {
                        if (gx.b("boolean.SelectBoolean")) {
                            maybeAddVariableToListOfToBeAddedVariables(0, str);
                        } else if (gx.b("double.SelectDouble")) {
                            maybeAddVariableToListOfToBeAddedVariables(1, str);
                        } else if (gx.b("String.SelectString")) {
                            maybeAddVariableToListOfToBeAddedVariables(2, str);
                        } else if (gx.b("Map")) {
                            maybeAddVariableToListOfToBeAddedVariables(3, str);
                        } else if (gx.b("ListInt")) {
                            maybeAddListToListOfToBeAddedLists(1, str);
                        } else if (gx.b("ListString")) {
                            maybeAddListToListOfToBeAddedLists(2, str);
                        } else if (gx.b("ListMap")) {
                            maybeAddListToListOfToBeAddedLists(3, str);
                        } else if (!gx.b("resource_bg") && !gx.b("resource")) {
                            if (gx.b("sound")) {
                                maybeAddSoundToListOfToBeAddedSounds(str);
                            } else if (gx.b("font")) {
                                maybeAddFontToListOfToBeAddedFonts(str);
                            }
                        } else {
                            maybeAddImageToListOfToBeAddedImages(str);
                        }
                    }
                }
            }
        }
        if (toBeAddedVariables.size() <= 0 && toBeAddedLists.size() <= 0 && toBeAddedImages.size() <= 0 && toBeAddedSounds.size() <= 0 && toBeAddedFonts.size() <= 0) {
            addMoreBlockFromCollectionsCreateEvent(moreBlock);
        } else {
            showMoreBlockAutoAddDialog(moreBlock);
        }
    }

    private void showSaveMoreBlockToCollectionsDialog(int moreBlockPosition) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), R.string.logic_more_block_favorites_save_title));
        aBVar.a(R.drawable.ic_bookmark_red_48dp);
        View a2 = wB.a(getContext(), R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(getContext(), R.string.logic_more_block_favorites_save_guide));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        NB nb = new NB(getContext(), a2.findViewById(R.id.ti_input), Pp.h().g());
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), R.string.common_word_save), v -> {
            if (nb.b()) {
                saveMoreBlockToCollection(editText.getText().toString(), moreBlocks.get(moreBlockPosition));
                mB.a(getContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), v -> {
            mB.a(getContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    private void resetEvent(EventBean event) {
        eC a2 = jC.a(sc_id);
        String javaName = currentActivity.getJavaName();
        a2.a(javaName, event.targetId + "_" + event.eventName, new ArrayList<>());
        bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_reset), 0).show();
    }

    private void addMoreBlockFromCollections(MoreBlockCollectionBean moreBlock) {
        String blockName = moreBlock.spec;

        if (blockName.contains(" ")) {
            blockName = blockName.substring(0, blockName.indexOf(' '));
        }
        boolean duplicateNameFound = false;
        for (Pair<String, String> projectMoreBlock : jC.a(sc_id).i(currentActivity.getJavaName())) {
            if (projectMoreBlock.first.equals(blockName)) {
                duplicateNameFound = true;
                break;
            }
        }
        if (!duplicateNameFound) {
            addMoreBlockFromCollectionsHandleVariables(moreBlock);
        } else {
            showEditMoreBlockNameDialog(moreBlock);
        }
    }

    @Override
    public void onSelected(MoreBlockCollectionBean bean) {
        addMoreBlockFromCollections(bean);
    }

    private void copySoundFromCollectionsToProject(String soundName) {
        if (Qp.g().b(soundName)) {
            ProjectResourceBean a2 = Qp.g().a(soundName);
            try {
                fileUtil.a(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + a2.resFullName, wq.t() + File.separator + sc_id + File.separator + a2.resFullName);
                jC.d(sc_id).c.add(a2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showImportMoreBlockFromCollectionsDialog() {
        ArrayList<MoreBlockCollectionBean> moreBlocksInCollections = Pp.h().f();
        new MoreblockImporterDialog(getActivity(), moreBlocksInCollections, this).show();
    }

    private void maybeAddVariableToListOfToBeAddedVariables(int variableType, String variableName) {
        if (toBeAddedVariables == null) {
            toBeAddedVariables = new ArrayList<>();
        }
        for (Pair<Integer, String> variable : jC.a(sc_id).k(currentActivity.getJavaName())) {
            if (variable.first == variableType && variable.second.equals(variableName)) {
                return;
            }
        }
        boolean alreadyToBeAdded = false;
        for (Pair<Integer, String> toBeAddedVariable : toBeAddedVariables) {
            if (toBeAddedVariable.first == variableType && toBeAddedVariable.second.equals(variableName)) {
                alreadyToBeAdded = true;
                break;
            }
        }
        if (!alreadyToBeAdded) {
            toBeAddedVariables.add(new Pair<>(variableType, variableName));
        }
    }

    private void showEditMoreBlockNameDialog(MoreBlockCollectionBean moreBlock) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), R.string.logic_more_block_title_change_block_name));
        aBVar.a(R.drawable.more_block_96dp);
        View a2 = wB.a(getContext(), R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(getContext(), R.string.logic_more_block_desc_change_block_name));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        ZB zb = new ZB(getContext(), a2.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(sc_id).a(currentActivity));
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), R.string.common_word_save), v -> {
            if (zb.b()) {
                moreBlock.spec = editText.getText().toString() + (moreBlock.spec.contains(" ") ?
                        moreBlock.spec.substring(moreBlock.spec.indexOf(" ")) : "");
                addMoreBlockFromCollectionsHandleVariables(moreBlock);
                mB.a(getContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), v -> {
            mB.a(getContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    private void maybeAddSoundToListOfToBeAddedSounds(String soundName) {
        if (toBeAddedSounds == null) {
            toBeAddedSounds = new ArrayList<>();
        }
        for (String soundInProjectName : jC.d(sc_id).p()) {
            if (soundInProjectName.equals(soundName)) {
                return;
            }
        }
        ProjectResourceBean sound = Qp.g().a(soundName);
        if (sound != null) {
            boolean alreadyToBeAdded = false;
            for (ProjectResourceBean toBeAddedSound : toBeAddedSounds) {
                if (toBeAddedSound.resName.equals(soundName)) {
                    alreadyToBeAdded = true;
                    break;
                }
            }
            if (!alreadyToBeAdded) {
                toBeAddedSounds.add(sound);
            }
        }
    }

    public void setCurrentActivity(ProjectFileBean projectFileBean) {
        currentActivity = projectFileBean;
    }

    private void showMoreBlockAutoAddDialog(MoreBlockCollectionBean moreBlock) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), R.string.logic_more_block_title_add_variable_resource));
        aBVar.a(R.drawable.break_warning_96_red);
        aBVar.a(xB.b().a(getContext(), R.string.logic_more_block_desc_add_variable_resource));
        aBVar.b(xB.b().a(getContext(), R.string.common_word_continue), v -> {
            for (Pair<Integer, String> pair : toBeAddedVariables) {
                eC eC = jC.a(sc_id);
                eC.c(currentActivity.getJavaName(), pair.first, pair.second);
            }
            for (Pair<Integer, String> pair : toBeAddedLists) {
                eC eC = jC.a(sc_id);
                eC.b(currentActivity.getJavaName(), pair.first, pair.second);
            }
            for (ProjectResourceBean bean : toBeAddedImages) {
                copyImageFromCollectionsToProject(bean.resName);
            }
            for (ProjectResourceBean bean : toBeAddedSounds) {
                copySoundFromCollectionsToProject(bean.resName);
            }
            for (ProjectResourceBean bean : toBeAddedFonts) {
                copyFontFromCollectionsToProject(bean.resName);
            }
            addMoreBlockFromCollectionsCreateEvent(moreBlock);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    private void deleteEvent(EventBean event) {
        jC.a(sc_id).d(currentActivity.getJavaName(), event.targetId, event.eventName);
        eC a2 = jC.a(sc_id);
        String javaName = currentActivity.getJavaName();
        a2.k(javaName, event.targetId + "_" + event.eventName);
        bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_delete), 0).show();
        events.get(categoryAdapter.index).remove(eventAdapter.lastSelectedItem);
        eventAdapter.e(eventAdapter.lastSelectedItem);
        eventAdapter.a(eventAdapter.lastSelectedItem, eventAdapter.a());
    }

    private void copyImageFromCollectionsToProject(String imageName) {
        if (Op.g().b(imageName)) {
            ProjectResourceBean image = Op.g().a(imageName);
            try {
                fileUtil.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + image.resFullName, wq.g() + File.separator + sc_id + File.separator + image.resFullName);
                jC.d(sc_id).b.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeEvents(ArrayList<EventBean> events) {
        for (EventBean bean : events) {
            bean.initValue();
        }
    }

    private void openEvent(String targetId, String eventId, String description) {
        Intent intent = new Intent(getActivity(), LogicEditorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("id", targetId);
        intent.putExtra("event", eventId);
        intent.putExtra("project_file", currentActivity);
        intent.putExtra("event_text", description);
        startActivity(intent);
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
                bB.b(getContext(), xB.b().a(getContext(), R.string.logic_more_block_message_missed_resource_exist), 0).show();
            } else {
                bB.a(getContext(), xB.b().a(getContext(), R.string.logic_more_block_message_resource_added), 0).show();
            }
        }
        try {
            Pp.h().a(moreBlockName, b2, moreBlockBlocks, true);
        } catch (Exception unused2) {
            bB.b(getContext(), xB.b().a(getContext(), R.string.common_error_failed_to_save), 0).show();
        }
    }

    private void maybeAddListToListOfToBeAddedLists(int listType, String listName) {
        if (toBeAddedLists == null) {
            toBeAddedLists = new ArrayList<>();
        }
        for (Pair<Integer, String> list : jC.a(sc_id).j(currentActivity.getJavaName())) {
            if (list.first == listType && list.second.equals(listName)) {
                return;
            }
        }

        boolean alreadyToBeAdded = false;
        for (Pair<Integer, String> toBeAddedList : toBeAddedLists) {
            if (toBeAddedList.first == listType && toBeAddedList.second.equals(listName)) {
                alreadyToBeAdded = true;
                break;
            }
        }
        if (!alreadyToBeAdded) {
            toBeAddedLists.add(new Pair<>(listType, listName));
        }
    }

    private void copyFontFromCollectionsToProject(String fontName) {
        if (Np.g().b(fontName)) {
            ProjectResourceBean font = Np.g().a(fontName);
            try {
                fileUtil.a(wq.a() + File.separator + "font" + File.separator + "data" + File.separator + font.resFullName, wq.d() + File.separator + sc_id + File.separator + font.resFullName);
                jC.d(sc_id).d.add(font);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addMoreBlockFromCollectionsCreateEvent(MoreBlockCollectionBean moreBlock) {
        String spec = moreBlock.spec;
        String moreBlockName = spec.contains(" ") ? spec.substring(0, spec.indexOf(' ')) : spec;
        jC.a(sc_id).a(currentActivity.getJavaName(), moreBlockName, spec);
        eC a2 = jC.a(sc_id);
        String javaName = currentActivity.getJavaName();
        a2.a(javaName, moreBlockName + "_moreBlock", moreBlock.blocks);
        bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_save), 0).show();
        refreshEvents();
    }

    private class CategoryAdapter extends RecyclerView.a<CategoryAdapter.ViewHolder> {
        private int index = -1;

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(ViewHolder holder, int position) {
            holder.name.setText(rs.a(getContext(), position));
            holder.icon.setImageResource(rs.a(position));
            if (index == position) {
                ef a2 = Ze.a(holder.icon);
                a2.c(1);
                a2.d(1);
                a2.a(300);
                a2.a(new AccelerateInterpolator());
                a2.c();
                ef a3 = Ze.a(holder.icon);
                a3.c(1);
                a3.d(1);
                a3.a(300);
                a3.a(new AccelerateInterpolator());
                a3.c();
                holder.pointerLeft.setVisibility(View.VISIBLE);
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(1);
                holder.icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            } else {
                ef a4 = Ze.a(holder.icon);
                a4.c(0.8f);
                a4.d(0.8f);
                a4.a(300);
                a4.a(new DecelerateInterpolator());
                a4.c();
                ef a5 = Ze.a(holder.icon);
                a5.c(0.8f);
                a5.d(0.8f);
                a5.a(300);
                a5.a(new DecelerateInterpolator());
                a5.c();
                holder.pointerLeft.setVisibility(View.GONE);
                ColorMatrix colorMatrix2 = new ColorMatrix();
                colorMatrix2.setSaturation(0);
                holder.icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
            }
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.common_category_triangle_item, parent, false));
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return events.size();
        }

        private class ViewHolder extends RecyclerView.v implements View.OnClickListener {
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
                CategoryAdapter.this.c(index);
                index = j();
                CategoryAdapter.this.c(index);
                initializeEvents(events.get(index));
                if (index == 4) {
                    importMoreBlockFromCollection.setVisibility(View.VISIBLE);
                } else {
                    importMoreBlockFromCollection.setVisibility(View.GONE);
                }
                eventAdapter.a(events.get(index));
                eventAdapter.c();
            }
        }
    }

    private class EventAdapter extends RecyclerView.a<EventAdapter.ViewHolder> {
        private int lastSelectedItem = -1;
        private ArrayList<EventBean> currentCategoryEvents = new ArrayList<>();

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return currentCategoryEvents.size();
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(ViewHolder holder, int position) {
            EventBean eventBean = currentCategoryEvents.get(position);
            holder.targetType.setVisibility(View.VISIBLE);
            holder.previewContainer.setVisibility(View.VISIBLE);
            holder.preview.setVisibility(View.VISIBLE);
            holder.preview.setImageResource(oq.a(eventBean.eventName));
            holder.optionsLayout.e();
            if (eventBean.eventType == EventBean.EVENT_TYPE_ETC) {
                // Show Add to Collection
                holder.optionsLayout.f();
            } else {
                // Hide Add to Collection
                holder.optionsLayout.c();
            }
            if (eventBean.eventType == EventBean.EVENT_TYPE_ACTIVITY) {
                if (eventBean.eventName.equals("initializeLogic")) {
                    // Hide Delete
                    holder.optionsLayout.b();
                }
                holder.targetId.setText(eventBean.targetId);
                holder.type.setBackgroundResource(oq.a(eventBean.eventName));
                holder.name.setText(eventBean.eventName);
                holder.description.setText(oq.a(eventBean.eventName, getContext()));
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
                    holder.targetType.setText(ComponentBean.getComponentName(getContext(), eventBean.targetType));
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
                holder.description.setText(oq.a(eventBean.eventName, getContext()));
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
                holder.optionsLayout.d();
            } else {
                holder.optionsLayout.a();
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
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fr_logic_list_item, parent, false));
        }

        private class ViewHolder extends RecyclerView.v {
            public final ImageView menu;
            public final ImageView preview;
            public final LinearLayout previewContainer;
            public final LinearLayout optionContainer;
            public final LinearLayout options;
            public final CollapsibleEventLayout optionsLayout;
            public final ImageView icon;
            public final TextView targetType;
            public final TextView targetId;
            public final TextView type;
            public final TextView name;
            public final TextView description;

            public ViewHolder(View itemView) {
                super(itemView);
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
                options = itemView.findViewById(R.id.event_option);
                optionsLayout = new CollapsibleEventLayout(getContext());
                optionsLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                options.addView(optionsLayout);
                optionsLayout.setButtonOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedItem = j();
                        EventBean eventBean = (events.get(categoryAdapter.index)).get(lastSelectedItem);
                        if (v instanceof CollapsibleButton) {
                            int i = ((CollapsibleButton) v).b;
                            if (i == 2) {
                                eventBean.buttonPressed = i;
                                eventBean.isConfirmation = false;
                                eventBean.isCollapsed = false;
                                EventAdapter.this.c(lastSelectedItem);
                                showSaveMoreBlockToCollectionsDialog(lastSelectedItem);
                            } else {
                                eventBean.buttonPressed = i;
                                eventBean.isConfirmation = true;
                                EventAdapter.this.c(lastSelectedItem);
                            }
                        } else {
                            if (v.getId() == R.id.confirm_no) {
                                eventBean.isConfirmation = false;
                                EventAdapter.this.c(lastSelectedItem);
                            } else if (v.getId() == R.id.confirm_yes) {
                                if (eventBean.buttonPressed == 0) {
                                    eventBean.isConfirmation = false;
                                    eventBean.isCollapsed = true;
                                    resetEvent(eventBean);
                                    EventAdapter.this.c(lastSelectedItem);
                                } else if (eventBean.buttonPressed == 1) {
                                    eventBean.isConfirmation = false;
                                    if (categoryAdapter.index != 4) {
                                        deleteEvent(eventBean);
                                    } else {
                                        deleteMoreBlock(eventBean);
                                    }
                                }
                                fab.f();
                            }
                        }
                    }
                });
                menu.setOnClickListener(v -> {
                    lastSelectedItem = j();
                    EventBean eventBean = events.get(categoryAdapter.index).get(lastSelectedItem);
                    if (eventBean.isCollapsed) {
                        eventBean.isCollapsed = false;
                        showOptions();
                    } else {
                        eventBean.isCollapsed = true;
                        hideOptions();
                    }
                });
                itemView.setOnLongClickListener(v -> {
                    lastSelectedItem = j();
                    EventBean eventBean = events.get(categoryAdapter.index).get(lastSelectedItem);
                    if (eventBean.isCollapsed) {
                        eventBean.isCollapsed = false;
                        showOptions();
                    } else {
                        eventBean.isCollapsed = true;
                        hideOptions();
                    }
                    return true;
                });
                itemView.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedItem = j();
                        EventBean eventBean = events.get(categoryAdapter.index).get(lastSelectedItem);
                        openEvent(eventBean.targetId, eventBean.eventName, description.getText().toString());
                    }
                });
            }

            private void hideOptions() {
                gB.a(menu, 0, null);
                gB.a(optionContainer, 200, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        optionContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            }

            private void showOptions() {
                optionContainer.setVisibility(View.VISIBLE);
                gB.a(menu, -180, null);
                gB.b(optionContainer, 200, null);
            }
        }
    }
}
