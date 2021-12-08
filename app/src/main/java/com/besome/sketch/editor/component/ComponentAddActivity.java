package com.besome.sketch.editor.component;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexItem;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.SB;
import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.components.ComponentsHandler;

public class ComponentAddActivity extends BaseDialogActivity implements View.OnClickListener {

    public TextView tvDescription;
    public TextView tvName;
    public ImageView imgIcon;
    public ImageView imgBack;
    public ImageView imgFilePicker;
    /**
     * The sc_id of the currently open project
     */
    public String sc_id;
    public ProjectFileBean projectFileBean;
    public LinearLayout inputFilePickerLayout;
    public TextView tvWarnning;
    public EditText edInput;
    public EditText edInputFilename;
    public EditText edInputFirebasePath;
    public EditText edInputFilePicker;
    public TextInputLayout tiInput;
    public TextInputLayout tiInputFilename;
    public TextInputLayout tiInputFirebasePath;
    public TextInputLayout tiInputFilePicker;
    public TextView tvDescFirebasePath;
    public TextView tvDescFilePicker;
    public ZB T;
    public SB U;
    public SB V;
    public SB W;
    public LinearLayout inputsLayout;
    public LinearLayout imgIconLayout;
    public RelativeLayout descriptionLayout;
    public Button addButton;
    public Button docsButton;
    public RecyclerView componentsList;
    public ComponentsAdapter componentsAdapter;
    public ArrayList<ComponentBean> componentList;
    public HashMap<Integer, Pair<Integer, Integer>> w;
    public boolean x;
    public boolean y;
    public TextView tvComponentTitle;

    private boolean checks() {
        int componentType = componentList.get(componentsAdapter.layoutPosition).type;
        String componentId = edInput.getText().toString();
        if (!T.b()) {
            return false;
        }
        switch (componentType) {
            case ComponentBean.COMPONENT_TYPE_SHAREDPREF:
                if (!U.b()) {
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId, edInputFilename.getText().toString());
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE:
            case ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE:
                if (!V.b()) {
                    return false;
                }
                if (jC.c(sc_id).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(Resources.string.design_library_guide_setup_first), 1).show();
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId, edInputFirebasePath.getText().toString());
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH:
                if (jC.c(sc_id).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(Resources.string.design_library_guide_setup_first), 1).show();
                    return false;
                } else if (jC.c(sc_id).d().reserved2.trim().length() == 0) {
                    bB.b(this, Helper.getResString(Resources.string.design_library_firebase_guide_setup_first), 1).show();
                    return false;
                } else {
                    jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId, edInputFirebasePath.getText().toString());
                }
                break;

            case 27:
                if (jC.c(sc_id).c().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(Resources.string.design_library_guide_setup_first), 1).show();
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId);
                break;

            case ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD:
                if (jC.c(sc_id).b().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(Resources.string.design_library_admob_component_setup_first), 1).show();
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId);
                break;

            case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                if (edInputFilePicker.getText().toString().length() == 0 || !W.b()) {
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId, edInputFilePicker.getText().toString());
                break;

            default:
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId);
        }
        jC.a(sc_id).k();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 275 && resultCode == RESULT_OK) {
            edInputFilePicker.setText(data.getStringExtra("mime_type"));
        }
    }

    @Override
    public void onBackPressed() {
        if (x) {
            p();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.logic_popup_add_component_temp);
        l();
        m();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            sc_id = intent.getStringExtra("sc_id");
            projectFileBean = intent.getParcelableExtra("project_file");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            projectFileBean = savedInstanceState.getParcelable("project_file");
        }
        tvComponentTitle = findViewById(Resources.id.tv_component_title);
        tvDescription = findViewById(Resources.id.tv_description);
        tvName = findViewById(Resources.id.tv_name);
        tvWarnning = findViewById(Resources.id.tv_warning);
        tvDescFirebasePath = findViewById(Resources.id.tv_desc_firebase_path);
        tvDescFilePicker = findViewById(Resources.id.tv_desc_file_picker);
        edInput = findViewById(Resources.id.ed_input);
        edInputFirebasePath = findViewById(Resources.id.ed_input_firebase_path);
        edInputFilename = findViewById(Resources.id.ed_input_filename);
        edInputFilePicker = findViewById(Resources.id.ed_input_file_picker);
        inputsLayout = findViewById(Resources.id.layout_inputs);
        imgBack = findViewById(Resources.id.img_back);
        imgBack.setVisibility(View.GONE);
        imgFilePicker = findViewById(Resources.id.img_file_picker);
        tiInput = findViewById(Resources.id.ti_input);
        tiInputFilename = findViewById(Resources.id.ti_input_filename);
        tiInputFirebasePath = findViewById(Resources.id.ti_input_firebase_path);
        tiInputFilePicker = findViewById(Resources.id.ti_input_file_picker);
        imgIconLayout = findViewById(Resources.id.layout_img_icon);
        descriptionLayout = findViewById(Resources.id.layout_description);
        inputFilePickerLayout = findViewById(Resources.id.layout_input_file_picker);
        edInput.setPrivateImeOptions("defaultInputmode=english;");
        addButton = findViewById(Resources.id.add_button);
        addButton.setText(Helper.getResString(Resources.string.common_word_add));
        docsButton = findViewById(Resources.id.docs_button);
        docsButton.setText(Helper.getResString(Resources.string.component_add_docs_button_title_go_to_docs));
        tvComponentTitle.setText(Helper.getResString(Resources.string.component_title_add_component));
        componentsList = findViewById(Resources.id.components_list);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);
        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
        componentsList.setLayoutManager(flexboxLayoutManager);
        componentsAdapter = new ComponentsAdapter();
        componentsList.setHasFixedSize(true);
        componentsList.setAdapter(componentsAdapter);
        descriptionLayout.setVisibility(View.GONE);
        imgIcon = findViewById(Resources.id.img_icon);
        T = new ZB(
                this,
                tiInput,
                uq.b,
                uq.a(),
                jC.a(sc_id).a(projectFileBean)
        );
        U = new SB(
                this,
                tiInputFilename,
                1,
                20
        );
        V = new SB(
                this,
                tiInputFirebasePath,
                0,
                100
        );
        W = new SB(
                this,
                tiInputFilePicker,
                1,
                50
        );
        tvDescFirebasePath.setText(Helper.getResString(Resources.string.design_library_firebase_guide_path_example));
        tvDescFilePicker.setText(Helper.getResString(Resources.string.component_description_file_picker_guide_mime_type_example));
        tiInput.setHint(Helper.getResString(Resources.string.component_hint_enter_name));
        tiInputFilename.setHint(Helper.getResString(Resources.string.component_file_hint_enter_file_name));
        tiInputFirebasePath.setHint(Helper.getResString(Resources.string.design_library_firebase_hint_enter_data_location));
        tiInputFilePicker.setHint(Helper.getResString(Resources.string.component_file_picker_hint_mime_type));
        w = new HashMap<>();
        imgBack.setOnClickListener(this);
        addButton.setOnClickListener(this);
        docsButton.setOnClickListener(this);
        imgFilePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case Resources.id.add_button:
                if (!mB.a() && checks()) {
                    bB.a(this, Helper.getResString(Resources.string.component_message_component_block_added), 1).show();
                    mB.a(getApplicationContext(), edInput);
                    setResult(RESULT_OK);
                    finish();
                }
                break;

            case Resources.id.img_back:
                if (!mB.a()) {
                    onBackPressed();
                }
                break;

            case Resources.id.docs_button:
                if (!mB.a()) {
                    String componentDocsUrlByTypeName = ComponentBean.getComponentDocsUrlByTypeName(componentList.get(componentsAdapter.layoutPosition).type);
                    if (componentDocsUrlByTypeName.equals("")) {
                        bB.a(getApplicationContext(), Helper.getResString(Resources.string.component_add_message_docs_updated_soon), 0).show();
                        return;
                    }
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse(componentDocsUrlByTypeName));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        startActivity(intent);
                    } catch (Exception unused) {
                        t();
                    }
                }
                break;

            case Resources.id.img_file_picker:
                startActivityForResult(new Intent(this, ShowFilePickerTypesActivity.class), 275);
                break;
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        componentList = new ArrayList<>();
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_INTENT));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SHAREDPREF));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_CALENDAR));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_VIBRATOR));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_TIMERTASK));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_DIALOG));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_MEDIAPLAYER));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SOUNDPOOL));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_OBJECTANIMATOR));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_CAMERA));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FILE_PICKER));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_GYROSCOPE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_TEXT_TO_SPEECH));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER));
        componentList.add(new ComponentBean(22));
        componentList.add(new ComponentBean(23));
        componentList.add(new ComponentBean(24));
        componentList.add(new ComponentBean(25));
        componentList.add(new ComponentBean(26));
        componentList.add(new ComponentBean(27));
        ComponentsHandler.add(componentList);
        componentList.add(new ComponentBean(28));
        componentList.add(new ComponentBean(29));
        componentList.add(new ComponentBean(30));
        componentList.add(new ComponentBean(31));
        componentList.add(new ComponentBean(32));
        componentList.add(new ComponentBean(33));
        componentList.add(new ComponentBean(34));
        componentsAdapter.c();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sc_id", sc_id);
        savedInstanceState.putParcelable("project_file", projectFileBean);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void p() {
        if (!y) {
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
            y = true;
            tvDescription.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            inputsLayout.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            addButton.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            docsButton.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            Pair<Integer, Integer> pair = w.get(componentsAdapter.layoutPosition);
            imgIconLayout.animate()
                    .translationX((float) pair.first)
                    .translationY((float) pair.second)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            y = false;
                            x = false;
                            Helper.setViewsVisibility(true, descriptionLayout, tvDescription, imgBack, imgIconLayout);
                            componentsList.setVisibility(View.VISIBLE);
                            tvComponentTitle.setText(Helper.getResString(Resources.string.component_title_add_component));
                            componentsAdapter.c();
                        }
                    }).start();
        }
    }

    private void s() {
        Helper.setViewsVisibility(false, imgIcon, descriptionLayout, inputsLayout, imgIconLayout, tvDescription, imgBack);
        componentsList.setVisibility(View.GONE);
        imgIconLayout.setTranslationX(FlexItem.FLEX_GROW_DEFAULT);
        imgIconLayout.setTranslationY(FlexItem.FLEX_GROW_DEFAULT);
        ComponentBean componentBean = componentList.get(componentsAdapter.layoutPosition);
        Helper.setViewsVisibility(true, tvWarnning, tiInputFilename, tvDescFirebasePath, tvDescFilePicker, tiInputFirebasePath, inputFilePickerLayout);
        switch (componentBean.type) {
            case ComponentBean.COMPONENT_TYPE_SHAREDPREF:
                tiInputFilename.setVisibility(View.VISIBLE);
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE:
                Helper.setViewsVisibility(false, tvDescFirebasePath, tiInputFirebasePath);
                break;

            case ComponentBean.COMPONENT_TYPE_GYROSCOPE:
                if (!GB.b(this, Sensor.TYPE_GYROSCOPE)) {
                    tvWarnning.setVisibility(View.VISIBLE);
                    tvWarnning.setText(Helper.getResString(Resources.string.message_device_not_support));
                }
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE:
                Helper.setViewsVisibility(false, tvDescFirebasePath, tiInputFirebasePath);
                break;

            case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                Helper.setViewsVisibility(false, tvDescFilePicker, inputFilePickerLayout);
                break;
        }
        imgIcon.setImageResource(ComponentBean.getIconResource(componentBean.type));
        tvComponentTitle.setText(ComponentBean.getComponentName(getApplicationContext(), componentBean.type));
        tvDescription.setText(ComponentsHandler.description(componentBean.type));
        tvDescription.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        inputsLayout.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        addButton.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        docsButton.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        inputsLayout.setTranslationY(300.0f);
        tvDescription.animate().alpha(1.0f).start();
        imgBack.animate().alpha(1.0f).start();
        inputsLayout.animate().alpha(1.0f).translationY(FlexItem.FLEX_GROW_DEFAULT).start();
        addButton.animate().setStartDelay(150).alpha(1.0f).start();
        docsButton.animate().setStartDelay(150).alpha(1.0f).start();
    }

    /**
     * Show dialog about installing Google Chrome.
     */
    public final void t() {
        aB dialog = new aB(this);
        dialog.a(Resources.drawable.chrome_96);
        dialog.b(Helper.getResString(Resources.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(Resources.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(Resources.string.common_word_ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(Helper.getResString(Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public class ComponentsAdapter extends RecyclerView.a<ComponentsAdapter.ViewHolder> {

        public int layoutPosition = -1;
        public RecyclerView recyclerView;

        public ComponentsAdapter() {
        }

        @Override
        public long a(int i) {
            return i;
        }

        @Override
        public void a(RecyclerView recyclerView) {
            super.a(recyclerView);
            this.recyclerView = recyclerView;
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            String componentName = ComponentBean.getComponentName(getApplicationContext(), componentList.get(position).type);
            viewHolder.b.setAlpha(1.0f);
            viewHolder.b.setTranslationX(FlexItem.FLEX_GROW_DEFAULT);
            viewHolder.b.setTranslationY(FlexItem.FLEX_GROW_DEFAULT);
            viewHolder.itemName.setAlpha(1.0f);
            viewHolder.itemName.setText(componentName);
            viewHolder.itemIcon.setImageResource(ComponentBean.getIconResource(componentList.get(position).type));
            if (!x) {
                return;
            }
            if (position == this.layoutPosition) {
                Pair<Integer, Integer> pair = w.get(position);
                viewHolder.itemName.animate()
                        .setDuration(100)
                        .alpha(FlexItem.FLEX_GROW_DEFAULT)
                        .start();
                viewHolder.b.animate()
                        .setStartDelay(300)
                        .translationX((float) (-pair.first))
                        .translationY((float) (-pair.second))
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                tvName.setText(componentName);
                                s();
                                y = false;
                            }
                        }).start();
                return;
            }
            viewHolder.b.animate()
                    .alpha(FlexItem.FLEX_GROW_DEFAULT)
                    .start();
        }

        @Override
        public ViewHolder b(ViewGroup viewGroup, int i) {
            View itemView = wB.a(viewGroup.getContext(), Resources.layout.component_add_item);
            int lengthAndWidth = (int) wB.a(viewGroup.getContext(), 76.0f);
            itemView.setLayoutParams(new FlexboxLayoutManager.LayoutParams(lengthAndWidth, lengthAndWidth));
            return new ViewHolder(itemView);
        }

        @Override
        public int a() {
            return componentList.size();
        }

        public class ViewHolder extends RecyclerView.v implements View.OnClickListener {

            public ImageView itemIcon;
            public TextView itemName;

            public ViewHolder(View view) {
                super(view);
                itemIcon = view.findViewById(Resources.id.icon);
                itemName = view.findViewById(Resources.id.name);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (!y) {
                    y = true;
                    ComponentsAdapter.this.layoutPosition = j();
                    x = true;
                    int[] itemViewLocationInWindow = new int[2];
                    view.getLocationInWindow(itemViewLocationInWindow);
                    int[] recyclerViewLocationInWindow = new int[2];
                    ComponentsAdapter.this.recyclerView.getLocationInWindow(recyclerViewLocationInWindow);
                    int i = itemViewLocationInWindow[0] - recyclerViewLocationInWindow[0];
                    w.put(ComponentsAdapter.this.layoutPosition, new Pair<>(i, (int) (((float) (itemViewLocationInWindow[1] - recyclerViewLocationInWindow[1])) - wB.a(getApplicationContext(), 16.0f))));
                    ComponentsAdapter.this.c();
                }
            }
        }
    }
}
