package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ViewBean;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyInputItemBinding;
import pro.sketchware.databinding.PropertyPopupParentAttrBinding;
import pro.sketchware.databinding.PropertySwitchItemSinglelineBinding;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.relativelayout.CircularDependencyDetector;

@SuppressLint("ViewConstructor")
public class PropertyAttributesItem extends LinearLayout implements View.OnClickListener {
    private static final String[] PARENT_RELATIVE = {
            "android:layout_centerInParent",
            "android:layout_centerVertical",
            "android:layout_centerHorizontal",
            "android:layout_toStartOf",
            "android:layout_toLeftOf",
            "android:layout_toRightOf",
            "android:layout_toEndOf",
            "android:layout_above",
            "android:layout_below",
            "android:layout_alignStart",
            "android:layout_alignLeft",
            "android:layout_alignTop",
            "android:layout_alignEnd",
            "android:layout_alignRight",
            "android:layout_alignBottom",
            "android:layout_alignParentStart",
            "android:layout_alignParentLeft",
            "android:layout_alignParentTop",
            "android:layout_alignParentEnd",
            "android:layout_alignParentRight",
            "android:layout_alignParentBottom",
            "android:layout_alignBaseline"
    };
    public static List<String> RELATIVE_IDS = Arrays.asList(
            "android:layout_alignStart",
            "android:layout_alignLeft",
            "android:layout_alignTop",
            "android:layout_alignEnd",
            "android:layout_alignBottom",
            "android:layout_alignBaseline",
            "android:layout_toStartOf",
            "android:layout_toLeftOf",
            "android:layout_toEndOf",
            "android:layout_toRightOf",
            "android:layout_above",
            "android:layout_below");
    private final ArrayList<ViewBean> beans = new ArrayList<>();
    private String key = "";
    private HashMap<String, String> value = new HashMap<>();
    private TextView tvName;
    private TextView tvValue;
    private View propertyItem;
    private View propertyMenuItem;
    private ImageView imgLeftIcon;
    private int icon;
    private Kw valueChangeListener;
    private ViewBean bean;
    private List<String> ids = new ArrayList<>();

    public PropertyAttributesItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    private void initialize(Context context, boolean z) {
        wB.a(context, this, R.layout.property_input_item);
        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        mB.a(this);
        this.key = key;
        int identifier = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(Helper.getResString(identifier));
            icon = R.drawable.ic_property_parent_attr;
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
                return;
            }
            tvValue.setText("Configure parent attributes");
            imgLeftIcon.setImageResource(icon);
        }
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            propertyItem.setVisibility(GONE);
            propertyMenuItem.setVisibility(VISIBLE);
        } else {
            propertyItem.setVisibility(VISIBLE);
            propertyMenuItem.setVisibility(GONE);
        }
    }

    public void setBean(ViewBean bean) {
        this.bean = bean.clone();
    }

    public void setBeans(ArrayList<ViewBean> beans) {
        this.beans.addAll(beans);
    }

    public void setAvailableIds(List<String> ids) {
        this.ids = ids;
    }

    public void setOnPropertyValueChangeListener(Kw onPropertyValueChangeListener) {
        valueChangeListener = onPropertyValueChangeListener;
    }

    public HashMap<String, String> getValue() {
        return value;
    }

    public void setValue(HashMap<String, String> value) {
        this.value = new HashMap<>(value);
    }

    @Override
    public void onClick(View v) {
        showParentAttributes();
    }

    private void showParentAttributes() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        var binding = PropertyPopupParentAttrBinding.inflate(LayoutInflater.from(getContext()));
        dialog.setContentView(binding.getRoot());
        dialog.show();

        binding.viewId.setText(bean.id);

        var adapter = new AttributesAdapter();
        binding.recyclerView.setAdapter(adapter);
        var dividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        List<String> keys = new ArrayList<>(value.keySet());
        adapter.submitList(keys);

        binding.add.setOnClickListener(v -> {
            List<String> list = new ArrayList<>();
            for (String attr : PARENT_RELATIVE) {
                if (!value.containsKey(attr)) {
                    list.add(attr);
                }
            }
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Choose an attributes")
                    .setAdapter(
                            new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list), (d, w) -> {
                                var attr = list.get(w);
                                if (RELATIVE_IDS.contains(attr)) {
                                    new MaterialAlertDialogBuilder(getContext())
                                            .setTitle("Choose an id")
                                            .setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ids), (d2, w2) -> {
                                                var id = ids.get(w2);
                                                if (new CircularDependencyDetector(beans, bean).isLegalAttribute(id, attr)) {
                                                    value.put(attr, id);
                                                    if (valueChangeListener != null)
                                                        valueChangeListener.a(key, value);
                                                    adapter.submitList(new ArrayList<>(value.keySet()));
                                                } else {
                                                    SketchwareUtil.toastError("IllegalStateException : Circular dependencies cannot exist in RelativeLayout");
                                                }
                                            })
                                            .setNegativeButton("Cancel", (d2, which) -> d.dismiss())
                                            .show();
                                } else {
                                    value.put(attr, "false");
                                    if (valueChangeListener != null)
                                        valueChangeListener.a(key, value);
                                    adapter.submitList(new ArrayList<>(value.keySet()));
                                }
                            })
                    .setNegativeButton("Cancel", (d, which) -> d.dismiss())
                    .show();
        });
    }

    private class AttributesAdapter extends ListAdapter<String, RecyclerView.ViewHolder> {

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

        public AttributesAdapter() {
            super(DIFF_CALLBACK);
        }

        @Override
        public int getItemViewType(int position) {
            if (RELATIVE_IDS.contains(getItem(position))) {
                return 1;
            } else {
                return 0;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            FrameLayout root = new FrameLayout(parent.getContext());
            if (viewType == 1) {
                return new IdsViewHolder(root);
            } else {
                return new BooleanViewHolder(root);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof IdsViewHolder idsHolder) {
                idsHolder.bind(getItem(position));
            } else if (holder instanceof BooleanViewHolder booleanHolder) {
                booleanHolder.bind(getItem(position));
            }
        }

        private class IdsViewHolder extends RecyclerView.ViewHolder {
            private final PropertyInputItemBinding binding;

            public IdsViewHolder(FrameLayout view) {
                super(view);
                binding = PropertyInputItemBinding.inflate(LayoutInflater.from(view.getContext()), view, true);
            }

            void bind(String attr) {
                binding.tvName.setText(attr);
                binding.tvValue.setText("@id/" + value.get(attr));
                binding.imgLeftIcon.setImageResource(R.drawable.ic_mtrl_code);
                binding.getRoot().findViewById(R.id.property_menu_item).setVisibility(View.GONE);
                itemView.setOnClickListener(v -> {
                    var filteredIds = new ArrayList<>(ids);
                    filteredIds.remove(value.get(attr));
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Choose an id")
                            .setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, filteredIds), (d, w) -> {
                                var id = filteredIds.get(w);
                                value.put(attr, id);
                                binding.tvValue.setText("@id/" + id);
                                if (valueChangeListener != null)
                                    valueChangeListener.a(key, value);
                            })
                            .setNegativeButton("Cancel", (d, which) -> d.dismiss())
                            .show();
                });
                itemView.setOnLongClickListener(v -> {
                    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
                    dialog.setTitle("Delete");
                    dialog.setMessage("Are you sure you want to delete " + attr + "?");
                    dialog.setPositiveButton("Yes", (view, which) -> {
                        value.remove(attr);
                        if (valueChangeListener != null)
                            valueChangeListener.a(key, value);
                        submitList(new ArrayList<>(value.keySet()));
                        view.dismiss();
                    });
                    dialog.setNegativeButton("No", (view, which) -> view.dismiss());
                    dialog.show();
                    return true;
                });
            }
        }

        private class BooleanViewHolder extends RecyclerView.ViewHolder {
            private final PropertySwitchItemSinglelineBinding binding;

            public BooleanViewHolder(FrameLayout view) {
                super(view);
                binding = PropertySwitchItemSinglelineBinding.inflate(LayoutInflater.from(view.getContext()), view, true);
            }

            void bind(String attr) {
                binding.tvName.setText(attr);
                binding.imgLeftIcon.setImageResource(R.drawable.ic_mtrl_code);
                binding.getRoot().findViewById(R.id.property_menu_item).setVisibility(View.GONE);
                binding.switchValue.setChecked(Boolean.parseBoolean(value.get(attr)));
                itemView.setOnClickListener(v -> {
                    binding.switchValue.setChecked(!binding.switchValue.isChecked());
                    value.put(attr, String.valueOf(binding.switchValue.isChecked()));
                    if (valueChangeListener != null) valueChangeListener.a(key, value);
                });
                itemView.setOnLongClickListener(v -> {
                    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
                    dialog.setTitle("Delete");
                    dialog.setMessage("Are you sure you want to delete " + attr + "?");
                    dialog.setPositiveButton("Yes", (view, which) -> {
                        value.remove(attr);
                        if (valueChangeListener != null)
                            valueChangeListener.a(key, value);
                        submitList(new ArrayList<>(value.keySet()));
                        view.dismiss();
                    });

                    dialog.setNegativeButton("No", (view, which) -> view.dismiss());
                    dialog.show();
                    return true;
                });
            }
        }
    }
}
