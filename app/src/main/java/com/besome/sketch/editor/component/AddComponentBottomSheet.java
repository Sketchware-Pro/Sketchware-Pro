package com.besome.sketch.editor.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import mod.hey.studios.util.Helper;
import mod.hilal.saif.components.ComponentsHandler;
import pro.sketchware.R;
import pro.sketchware.databinding.ComponentAddItemBinding;
import pro.sketchware.databinding.LogicAddComponentBinding;
import pro.sketchware.dialogs.InnerAddComponentBottomSheet;

public class AddComponentBottomSheet extends BottomSheetDialogFragment {
    private String sc_id;
    private ProjectFileBean projectFileBean;

    private ArrayList<ComponentBean> componentList;
    private LogicAddComponentBinding binding;
    private OnComponentCreateListener onComponentCreateListener;

    public static AddComponentBottomSheet newInstance(String scId, ProjectFileBean projectFileBean, OnComponentCreateListener onComponentCreateListener) {
        AddComponentBottomSheet addComponentBottomSheet = new AddComponentBottomSheet();
        addComponentBottomSheet.setOnComponentCreateListener(onComponentCreateListener);
        Bundle args = new Bundle();
        args.putString("sc_id", scId);
        args.putParcelable("project_file_bean", projectFileBean);
        addComponentBottomSheet.setArguments(args);
        return addComponentBottomSheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        sc_id = args.getString("sc_id");
        projectFileBean = args.getParcelable("project_file_bean");

        initializeComponentBeans();
    }

    private void initializeComponentBeans() {
        componentList = new ArrayList<>();
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_INTENT));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SHAREDPREF));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FILE_PICKER));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_CALENDAR));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_VIBRATOR));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_TIMERTASK));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_DIALOG));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_MEDIAPLAYER));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SOUNDPOOL));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_OBJECTANIMATOR));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_CAMERA));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_GYROSCOPE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_TEXT_TO_SPEECH));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_PROGRESS_DIALOG));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_DATE_PICKER_DIALOG));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_TIME_PICKER_DIALOG));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_NOTIFICATION));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FRAGMENT_ADAPTER));

        // Ads
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_REWARDED_VIDEO_AD));

        // Firebase
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH_PHONE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE));
        componentList.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN));

        ComponentsHandler.add(componentList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LogicAddComponentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext(), FlexDirection.ROW, FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);

        binding.title.setText(Helper.getResString(R.string.component_title_add_component));
        binding.componentList.setHasFixedSize(true);
        binding.componentList.setAdapter(new ComponentsAdapter());
        binding.componentList.setLayoutManager(flexboxLayoutManager);

        binding.componentList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                FlexboxLayoutManager lm = (FlexboxLayoutManager) recyclerView.getLayoutManager();
                if (lm == null) return;
                int first = lm.findFirstCompletelyVisibleItemPosition();
                int last = lm.findLastCompletelyVisibleItemPosition();
                int total = binding.componentList.getAdapter().getItemCount();
                binding.dividerTop.setVisibility(first > 0 ? View.VISIBLE : View.GONE);
                binding.dividerBottom.setVisibility(last < total - 1 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        onComponentCreateListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sc_id", sc_id);
        savedInstanceState.putParcelable("project_file", projectFileBean);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setOnComponentCreateListener(OnComponentCreateListener onComponentCreateListener) {
        this.onComponentCreateListener = onComponentCreateListener;
    }

    private void showAddComponentDialog(ComponentBean componentBean) {
        InnerAddComponentBottomSheet innerAddComponentBottomSheet = InnerAddComponentBottomSheet.newInstance(sc_id, projectFileBean, componentBean, (sheet) -> {
            sheet.dismiss();
            dismiss();
            onComponentCreateListener.invoke();
        });
        innerAddComponentBottomSheet.show(getParentFragmentManager(), null);
    }

    public interface OnComponentCreateListener {
        void invoke();
    }

    private class ComponentsAdapter extends RecyclerView.Adapter<ComponentsAdapter.ComponentBeanViewHolder> {
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(@NonNull ComponentBeanViewHolder holder, int position) {
            var componentBean = componentList.get(position);
            holder.bind(componentBean);
        }

        @Override
        @NonNull
        public ComponentBeanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ComponentAddItemBinding binding = ComponentAddItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ComponentBeanViewHolder(binding);
        }

        @Override
        public int getItemCount() {
            return componentList.size();
        }

        private class ComponentBeanViewHolder extends RecyclerView.ViewHolder {
            private final ComponentAddItemBinding binding;

            public ComponentBeanViewHolder(ComponentAddItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(ComponentBean componentBean) {
                String componentName = ComponentBean.getComponentName(itemView.getContext(), componentBean.type);
                binding.name.setText(componentName);
                binding.icon.setImageResource(ComponentBean.getIconResource(componentBean.type));
                binding.getRoot().setOnClickListener(v -> showAddComponentDialog(componentBean));
            }
        }
    }
}
