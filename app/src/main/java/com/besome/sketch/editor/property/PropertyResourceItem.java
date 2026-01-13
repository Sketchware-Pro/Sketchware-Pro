package com.besome.sketch.editor.property;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.design.DesignActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import a.a.a.Kw;
import a.a.a.jC;
import a.a.a.kC;
import a.a.a.mB;
import a.a.a.wB;
import mod.bobur.VectorDrawableLoader;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ImagePickerItemBinding;
import pro.sketchware.databinding.SearchWithRecyclerViewBinding;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.SvgUtils;

public class PropertyResourceItem extends RelativeLayout implements View.OnClickListener {

    private final SvgUtils svgUtils;
    private final FilePathUtil fpu = new FilePathUtil();
    private final Map<String, View> imageCache = new HashMap<>();

    public String a;
    public String b;
    public String c;
    public boolean d;
    public TextView e;
    public TextView f;
    public ImageView g;
    public ImageView h;
    public RadioGroup i;
    public LinearLayout j;
    public View k;
    public View l;
    public int m;
    public Kw n;

    public PropertyResourceItem(Context context, boolean z, String str, boolean z2) {
        super(context);
        d = false;
        a = str;
        svgUtils = new SvgUtils(context);
        svgUtils.initImageLoader();
        a(context, z, z2);
    }

    public String getKey() {
        return b;
    }

    public void setKey(String str) {
        b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            e.setText(getResources().getString(identifier));
            if ("property_image".equals(b)) {
                m = R.drawable.ic_mtrl_image;
            } else if ("property_background_resource".equals(b)) {
                m = R.drawable.ic_mtrl_background_dots;
            }
            if (l.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(m);
                ((TextView) findViewById(R.id.tv_title)).setText(getContext().getString(identifier));
            } else {
                h.setImageResource(m);
            }
        }
    }

    public String getValue() {
        return c;
    }

    public void setValue(String str) {
        Uri fromFile;
        if (str != null && !str.equalsIgnoreCase("NONE")) {
            c = str;
            f.setText(str);
            if (jC.d(a).h(str) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                g.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
                return;
            } else if (str.equals("default_image")) {
                g.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
                return;
            } else {
                File file = new File(jC.d(a).f(str));
                if (file.exists()) {
                    Context context = getContext();
                    fromFile = FileProvider.getUriForFile(context, getContext().getPackageName() + ".provider", file);
                    if (file.getAbsolutePath().endsWith(".xml")) {
                        svgUtils.loadImage(g, fpu.getSvgFullPath(a, str));
                        return;
                    }
                    Glide.with(getContext()).load(fromFile).signature(kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(g);
                    return;
                }
                g.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
                return;
            }
        }
        c = str;
        f.setText("NONE");
        g.setImageDrawable(null);
        g.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        a();
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        n = kw;
    }

    public void setOrientationItem(int i) {
        if (i == 0) {
            k.setVisibility(GONE);
            l.setVisibility(VISIBLE);
            k.setOnClickListener(null);
            l.setOnClickListener(this);
        } else {
            k.setVisibility(VISIBLE);
            l.setVisibility(GONE);
            k.setOnClickListener(this);
            l.setOnClickListener(null);
        }
    }

    public final void a(Context context, boolean z, boolean z2) {
        wB.a(context, this, R.layout.property_resource_item);
        e = findViewById(R.id.tv_name);
        f = findViewById(R.id.tv_value);
        g = findViewById(R.id.view_image);
        h = findViewById(R.id.img_left_icon);
        k = findViewById(R.id.property_item);
        l = findViewById(R.id.property_menu_item);
        d = z2;
    }

    public final void a() {
        SearchWithRecyclerViewBinding binding = SearchWithRecyclerViewBinding.inflate(LayoutInflater.from(getContext()));

        ArrayList<String> images = jC.d(a).m();
        images.addAll(new VectorDrawableLoader().getVectorDrawables(DesignActivity.sc_id));
        images.add(0, d ? "default_image" : "NONE");

        ImagePickerAdapter adapter = new ImagePickerAdapter(images, c);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.searchInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {
                adapter.filter(s.toString().toLowerCase());
            }
        });

        new MaterialAlertDialogBuilder(getContext())
                .setTitle(Helper.getText(e))
                .setIcon(m)
                .setView(binding.getRoot())
                .setPositiveButton(R.string.common_word_select, (v, which) -> {
                    String selected = adapter.getSelected();
                    if (!selected.isEmpty()) {
                        setValue(selected);
                        if (n != null) {
                            n.a(b, selected);
                        }
                    }
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    private class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.ViewHolder> {

        private final ArrayList<String> allImages;
        private final ArrayList<String> filteredImages = new ArrayList<>();
        private String selectedImage;

        ImagePickerAdapter(ArrayList<String> images, String selectedImage) {
            this.allImages = images;
            this.selectedImage = selectedImage;
            this.filteredImages.addAll(images);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImagePickerItemBinding binding = ImagePickerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String image = filteredImages.get(position);
            holder.binding.textView.setText(image);
            holder.binding.radioButton.setChecked(image.equals(selectedImage));

            View imageView = imageCache.get(image);
            if (imageView == null) {
                imageView = setImageViewContent(image);
                imageCache.put(image, imageView);
            }

            if (imageView.getParent() != null) {
                ((ViewGroup) imageView.getParent()).removeView(imageView);
            }

            holder.binding.layoutImg.removeAllViews();
            holder.binding.layoutImg.addView(imageView);

            holder.binding.transparentOverlay.setOnClickListener(v -> {
                selectedImage = image;
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return filteredImages.size();
        }

        public void filter(String query) {
            filteredImages.clear();
            for (String s : allImages) {
                if (s.toLowerCase().contains(query)) {
                    filteredImages.add(s);
                }
            }
            notifyDataSetChanged();
        }

        public String getSelected() {
            return selectedImage;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImagePickerItemBinding binding;
            ViewHolder(ImagePickerItemBinding b) {
                super(b.getRoot());
                this.binding = b;
            }
        }
    }

    private View setImageViewContent(String image) {
        ImageView imageView = new ImageView(getContext());
        int size = (int) (48 * wB.a(getContext(), 1f));
        imageView.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundResource(R.drawable.bg_outline);

        try {
            if ("default_image".equals(image)) {
                imageView.setImageResource(getResources().getIdentifier(image, "drawable", getContext().getPackageName()));
            } else {
                File file = new File(jC.d(a).f(image));
                if (file.exists()) {
                    Uri uri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", file);
                    if (file.getAbsolutePath().endsWith(".xml")) {
                        svgUtils.loadImage(imageView, fpu.getSvgFullPath(a, image));
                    } else {
                        Glide.with(getContext())
                                .load(uri)
                                .signature(kC.n())
                                .error(R.drawable.ic_remove_grey600_24dp)
                                .into(imageView);
                    }
                } else {
                    VectorDrawableLoader vectorDrawableLoader = new VectorDrawableLoader();
                    vectorDrawableLoader.setImageVectorFromFile(imageView, vectorDrawableLoader.getVectorFullPath(DesignActivity.sc_id, image));
                }
            }
        } catch (Exception e) {
            imageView.setImageResource(R.drawable.ic_remove_grey600_24dp);
        }

        return imageView;
    }
}
