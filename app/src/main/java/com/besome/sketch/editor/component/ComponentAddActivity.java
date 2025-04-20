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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.FlexboxLayoutManager.LayoutParams;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import a.a.a.GB;
import a.a.a.SB;
import a.a.a.ZB;
import a.a.a.bB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.components.ComponentsHandler;
import pro.sketchware.R;
import pro.sketchware.databinding.LogicPopupAddComponentTempBinding;
import pro.sketchware.lib.DebouncedClickListener;

public class ComponentAddActivity extends BaseDialogActivity implements View.OnClickListener {

    private final AtomicInteger selectedMime = new AtomicInteger(0);
    private String sc_id;
    private ProjectFileBean projectFileBean;
    private ZB componentNameValidator;
    private SB componentFileNameValidator;
    private SB componentFirebasePathValidator;
    private SB componentMimeTypeValidator;
    private ComponentsAdapter componentsAdapter;
    private ArrayList<ComponentBean> componentList;
    private HashMap<Integer, Pair<Integer, Integer>> w;
    private boolean x;
    private boolean y;
    private LogicPopupAddComponentTempBinding binding;

    private boolean checks() {
        int componentType = componentList.get(componentsAdapter.layoutPosition).type;
        String componentId = Helper.getText(binding.edInput);
        if (!componentNameValidator.b()) {
            return false;
        }
        switch (componentType) {
            case ComponentBean.COMPONENT_TYPE_SHAREDPREF:
                if (!componentFileNameValidator.b()) {
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId, Helper.getText(binding.edInputFilename));
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE:
            case ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE:
                if (!componentFirebasePathValidator.b()) {
                    return false;
                }
                if (jC.c(sc_id).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(R.string.design_library_guide_setup_first), bB.TOAST_WARNING).show();
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId, Helper.getText(binding.edInputFirebasePath));
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH:
                if (jC.c(sc_id).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(R.string.design_library_guide_setup_first), bB.TOAST_WARNING).show();
                    return false;
                } else if (jC.c(sc_id).d().reserved2.trim().isEmpty()) {
                    bB.b(this, Helper.getResString(R.string.design_library_firebase_guide_setup_first), bB.TOAST_WARNING).show();
                    return false;
                } else {
                    jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId, Helper.getText(binding.edInputFirebasePath));
                }
                break;

            case ComponentBean.COMPONENT_TYPE_FRAGMENT_ADAPTER:
                if (jC.c(sc_id).c().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(R.string.design_library_guide_setup_first), bB.TOAST_WARNING).show();
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId);
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                if (jC.c(sc_id).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(R.string.design_library_guide_setup_first), bB.TOAST_WARNING).show();
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId);
                break;

            case ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD:
            case ComponentBean.COMPONENT_TYPE_REWARDED_VIDEO_AD:
                if (jC.c(sc_id).b().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                    bB.b(this, Helper.getResString(R.string.design_library_admob_component_setup_first), bB.TOAST_WARNING).show();
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId);
                break;

            case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                if (Helper.getText(binding.edInputFilePicker).isEmpty() || !componentMimeTypeValidator.b()) {
                    return false;
                }
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId, Helper.getText(binding.edInputFilePicker));
                break;

            default:
                jC.a(sc_id).a(projectFileBean.getJavaName(), componentType, componentId);
        }
        jC.a(sc_id).k();
        return true;
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
        binding = LogicPopupAddComponentTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        binding.imgBack.setVisibility(View.GONE);
        binding.edInput.setPrivateImeOptions("defaultInputmode=english;");
        binding.addButton.setText(Helper.getResString(R.string.common_word_add));
        binding.docsButton.setText(Helper.getResString(R.string.component_add_docs_button_title_go_to_docs));
        binding.tvComponentTitle.setText(Helper.getResString(R.string.component_title_add_component));
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);
        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
        binding.componentList.setLayoutManager(flexboxLayoutManager);
        componentsAdapter = new ComponentsAdapter();
        binding.componentList.setHasFixedSize(true);
        binding.componentList.setAdapter(componentsAdapter);
        binding.layoutDescription.setVisibility(View.GONE);
        componentNameValidator = new ZB(
                this,
                binding.tiInput,
                uq.b,
                uq.a(),
                jC.a(sc_id).a(projectFileBean)
        );
        componentFileNameValidator = new SB(
                this,
                binding.tiInputFilename,
                1 /* minimum amount of characters in input */,
                20 /* maximum amount of characters in input */
        );
        componentFirebasePathValidator = new SB(
                this,
                binding.tiInputFirebasePath,
                0 /* minimum amount of characters in input */,
                100 /* maximum amount of characters in input */
        );
        componentMimeTypeValidator = new SB(
                this,
                binding.tiInputFilePicker,
                1 /* minimum amount of characters in input */,
                50 /* maximum amount of characters in input */
        );
        binding.tvDescFirebasePath.setText(Helper.getResString(R.string.design_library_firebase_guide_path_example));
        binding.tvDescFilePicker.setText(Helper.getResString(R.string.component_description_file_picker_guide_mime_type_example));
        binding.tiInput.setHint(Helper.getResString(R.string.component_hint_enter_name));
        binding.tiInputFilename.setHint(Helper.getResString(R.string.component_file_hint_enter_file_name));
        binding.tiInputFirebasePath.setHint(Helper.getResString(R.string.design_library_firebase_hint_enter_data_location));
        binding.tiInputFilePicker.setHint(Helper.getResString(R.string.component_file_picker_hint_mime_type));
        w = new HashMap<>();
        binding.imgBack.setOnClickListener(this);
        binding.addButton.setOnClickListener(new DebouncedClickListener() {
            @Override
            protected void onDebouncedClick(View v) {
                if (checks()) {
                    bB.a(ComponentAddActivity.this, Helper.getResString(R.string.component_message_component_block_added), bB.TOAST_WARNING).show();
                    mB.a(getApplicationContext(), binding.edInput);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        binding.docsButton.setOnClickListener(this);
        binding.tiInputFilePicker.setEndIconOnClickListener(v -> showFilePickerMimeTypeSelectionDialog());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_back) {
            if (!mB.a()) {
                onBackPressed();
            }
        } else if (id == R.id.docs_button) {
            if (!mB.a()) {
                String componentDocsUrlByTypeName = ComponentBean.getComponentDocsUrlByTypeName(componentList.get(componentsAdapter.layoutPosition).type);
                if (componentDocsUrlByTypeName.isEmpty()) {
                    bB.a(getApplicationContext(), Helper.getResString(R.string.component_add_message_docs_updated_soon), bB.TOAST_NORMAL).show();
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
        }
    }

    private void showFilePickerMimeTypeSelectionDialog() {
        AtomicInteger selectedChoice = new AtomicInteger(selectedMime.get());

        List<String> mimeTypes = Arrays.asList("*/*", "image/*", "audio/*", "text/*");
        List<String> mimeTypeLabels = Arrays.asList(
                getTranslatedString(R.string.component_file_picker_title_select_mime_type_all_files),
                getTranslatedString(R.string.component_file_picker_title_select_mime_type_image_files),
                getTranslatedString(R.string.component_file_picker_title_select_mime_type_audio_files),
                getTranslatedString(R.string.component_file_picker_title_select_mime_type_text_files)
        );

        new MaterialAlertDialogBuilder(this)
                .setTitle(getTranslatedString(R.string.component_file_picker_title_select_mime_type))
                .setSingleChoiceItems(mimeTypeLabels.toArray(new String[0]), selectedMime.get(), (dialog, which) -> selectedChoice.set(which))
                .setPositiveButton(R.string.common_word_select, (dialog, which) -> {
                    String selectedMimeType = mimeTypes.get(selectedChoice.get());
                    selectedMime.set(selectedChoice.get());
                    binding.edInputFilePicker.setText(selectedMimeType);
                })
                .setNegativeButton(R.string.common_word_cancel, (dialog, which) -> dialog.dismiss())
                .show();
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
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_REWARDED_VIDEO_AD));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_PROGRESS_DIALOG));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_DATE_PICKER_DIALOG));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_TIME_PICKER_DIALOG));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_NOTIFICATION));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FRAGMENT_ADAPTER));
        ComponentsHandler.add(componentList);
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH_PHONE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_ONESIGNAL));
        componentsAdapter.notifyDataSetChanged();
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
            binding.tvDescription.animate().alpha(LayoutParams.FLEX_GROW_DEFAULT).start();
            binding.layoutInputs.animate().alpha(LayoutParams.FLEX_GROW_DEFAULT).start();
            binding.addButton.animate().alpha(LayoutParams.FLEX_GROW_DEFAULT).start();
            binding.docsButton.animate().alpha(LayoutParams.FLEX_GROW_DEFAULT).start();
            Pair<Integer, Integer> pair = w.get(componentsAdapter.layoutPosition);
            if (pair == null) return;
            binding.layoutImgIcon.animate()
                    .translationX((float) pair.first)
                    .translationY((float) pair.second)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            y = false;
                            x = false;
                            Helper.setViewsVisibility(true, binding.layoutDescription, binding.tvDescription, binding.imgBack, binding.layoutImgIcon);
                            binding.componentList.setVisibility(View.VISIBLE);
                            binding.tvComponentTitle.setText(Helper.getResString(R.string.component_title_add_component));
                            componentsAdapter.notifyDataSetChanged();
                        }
                    }).start();
        }
    }

    private void s() {
        Helper.setViewsVisibility(false, binding.imgIcon, binding.layoutDescription, binding.layoutInputs, binding.layoutImgIcon, binding.tvDescription, binding.imgBack);
        binding.componentList.setVisibility(View.GONE);
        binding.layoutImgIcon.setTranslationX(LayoutParams.FLEX_GROW_DEFAULT);
        binding.layoutImgIcon.setTranslationY(LayoutParams.FLEX_GROW_DEFAULT);
        ComponentBean componentBean = componentList.get(componentsAdapter.layoutPosition);
        Helper.setViewsVisibility(true, binding.tvWarning, binding.tiInputFilename, binding.tvDescFirebasePath, binding.tvDescFilePicker, binding.tiInputFirebasePath, binding.tiInputFilePicker);
        switch (componentBean.type) {
            case ComponentBean.COMPONENT_TYPE_SHAREDPREF:
                binding.tiInputFilename.setVisibility(View.VISIBLE);
                break;

            case ComponentBean.COMPONENT_TYPE_FIREBASE:
            case ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE:
                Helper.setViewsVisibility(false, binding.tvDescFirebasePath, binding.tiInputFirebasePath);
                break;

            case ComponentBean.COMPONENT_TYPE_GYROSCOPE:
                if (!GB.b(this, Sensor.TYPE_GYROSCOPE)) {
                    binding.tvWarning.setVisibility(View.VISIBLE);
                    binding.tvWarning.setText(Helper.getResString(R.string.message_device_not_support));
                }
                break;

            case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                Helper.setViewsVisibility(false, binding.tvDescFilePicker, binding.tiInputFilePicker);
                break;
        }
        binding.imgIcon.setImageResource(ComponentBean.getIconResource(componentBean.type));
        binding.tvComponentTitle.setText(ComponentBean.getComponentName(getApplicationContext(), componentBean.type));
        binding.tvDescription.setText(ComponentsHandler.description(componentBean.type));
        binding.tvDescription.setAlpha(LayoutParams.FLEX_GROW_DEFAULT);
        binding.layoutInputs.setAlpha(LayoutParams.FLEX_GROW_DEFAULT);
        binding.addButton.setAlpha(LayoutParams.FLEX_GROW_DEFAULT);
        binding.docsButton.setAlpha(LayoutParams.FLEX_GROW_DEFAULT);
        binding.layoutInputs.setTranslationY(300.0f);
        binding.tvDescription.animate().alpha(1.0f).start();
        binding.imgBack.animate().alpha(1.0f).start();
        binding.layoutInputs.animate().alpha(1.0f).translationY(LayoutParams.FLEX_GROW_DEFAULT).start();
        binding.addButton.animate().setStartDelay(150).alpha(1.0f).start();
        binding.docsButton.animate().setStartDelay(150).alpha(1.0f).start();
    }

    /**
     * Show dialog about installing Google Chrome.
     */
    public final void t() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setIcon(R.drawable.chrome_96);
        dialog.setTitle(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.setMessage(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_ok), (v, which) -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                v.dismiss();
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private class ComponentsAdapter extends RecyclerView.Adapter<ComponentsAdapter.ViewHolder> {

        private int layoutPosition = -1;
        private RecyclerView recyclerView;

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            this.recyclerView = recyclerView;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String componentName = ComponentBean.getComponentName(getApplicationContext(), componentList.get(position).type);
            holder.itemView.setAlpha(1.0f);
            holder.itemView.setTranslationX(LayoutParams.FLEX_GROW_DEFAULT);
            holder.itemView.setTranslationY(LayoutParams.FLEX_GROW_DEFAULT);
            holder.itemName.setAlpha(1.0f);
            holder.itemName.setText(componentName);
            holder.itemIcon.setImageResource(ComponentBean.getIconResource(componentList.get(position).type));
            if (!x) {
                return;
            }
            if (position == layoutPosition) {
                Pair<Integer, Integer> pair = w.get(position);
                if (pair == null) return;
                holder.itemName.animate()
                        .setDuration(100)
                        .alpha(LayoutParams.FLEX_GROW_DEFAULT)
                        .start();
                holder.itemView.animate()
                        .setStartDelay(300)
                        .translationX((float) (-pair.first))
                        .translationY((float) (-pair.second))
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                binding.tvName.setText(componentName);
                                s();
                                y = false;
                            }
                        }).start();
                return;
            }
            holder.itemView.animate()
                    .alpha(LayoutParams.FLEX_GROW_DEFAULT)
                    .start();
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = wB.a(parent.getContext(), R.layout.component_add_item);
            int lengthAndWidth = (int) wB.a(parent.getContext(), 76.0f);
            itemView.setLayoutParams(new FlexboxLayoutManager.LayoutParams(lengthAndWidth, lengthAndWidth));
            return new ViewHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return componentList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final ImageView itemIcon;
            private final TextView itemName;

            public ViewHolder(View itemView) {
                super(itemView);
                itemIcon = itemView.findViewById(R.id.icon);
                itemName = itemView.findViewById(R.id.name);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!y) {
                    y = true;
                    layoutPosition = getLayoutPosition();
                    x = true;
                    int[] itemViewLocationInWindow = new int[2];
                    v.getLocationInWindow(itemViewLocationInWindow);
                    int[] recyclerViewLocationInWindow = new int[2];
                    recyclerView.getLocationInWindow(recyclerViewLocationInWindow);
                    int i = itemViewLocationInWindow[0] - recyclerViewLocationInWindow[0];
                    w.put(layoutPosition, new Pair<>(i, (int) (((float) (itemViewLocationInWindow[1] - recyclerViewLocationInWindow[1])) - wB.a(getApplicationContext(), 16.0f))));
                    notifyDataSetChanged();
                }
            }
        }
    }
}
