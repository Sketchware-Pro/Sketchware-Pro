package com.besome.sketch.editor;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.besome.sketch.editor.property.ViewPropertyItems;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CustomScrollView;

import java.util.ArrayList;
import java.util.Map;

import a.a.a.Kw;
import a.a.a.cC;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.ro;
import com.besome.sketch.editor.property.PropertyResourceItem;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class PropertyActivity extends BaseAppCompatActivity implements Kw {

    private final ArrayList<Integer> propertyGroups = new ArrayList<>();
    private ProjectFileBean projectFileBean;
    private ViewBean viewBean;
    private ViewPropertyItems propertyItems;
    private boolean p;
    private String sc_id;
    private int layoutPosition;

    private LinearLayout content;
    private LinearLayout layoutAds;
    private CustomScrollView scrollView;
    private RecyclerView propertyGroupList;

    @Override
    public void a(String var1, Object var2) {
    }

    public void l() {
        propertyItems.setProjectFileBean(projectFileBean);
        propertyItems.a(sc_id, viewBean);
        content.addView(propertyItems);
    }

    private void m() {
        ArrayList<String> var1 = jC.d(sc_id).m();
        PropertyResourceItem resourceProperty;
        if (!var1.contains(viewBean.layout.backgroundResource)) {
            viewBean.layout.backgroundResource = null;
            resourceProperty = content.findViewWithTag("property_background_resource");
            if (resourceProperty != null) {
                resourceProperty.setValue(viewBean.layout.backgroundResource);
            }
        }

        if (viewBean.type == 6 && !var1.contains(viewBean.image.resName)) {
            viewBean.image.resName = "default_image";
            resourceProperty = content.findViewWithTag("property_image");
            if (resourceProperty != null) {
                resourceProperty.setValue(viewBean.image.resName);
            }
        }

        for (String fileName : jC.b(sc_id).e()) {
            for (ViewBean bean : jC.a(sc_id).d(fileName)) {
                if (bean.type == 6 && !var1.contains(bean.image.resName)) {
                    bean.image.resName = "default_image";
                    p = true;
                }

                if (!var1.contains(bean.layout.backgroundResource)) {
                    bean.layout.backgroundResource = null;
                    p = true;
                }
            }
        }

        for (String fileName : jC.b(sc_id).d()) {
            for (Map.Entry<String, ArrayList<BlockBean>> var4 : jC.a(sc_id).b(fileName).entrySet()) {
                for (BlockBean bean : var4.getValue()) {
                    if ("setImage".equals(bean.opCode)) {
                        if (!var1.contains(bean.parameters.get(1))) {
                            bean.parameters.set(1, "default_image");
                        }
                    } else if ("setBgResource".equals(bean.opCode) && !var1.contains(bean.parameters.get(1))) {
                        bean.parameters.set(1, "NONE");
                    }
                }
            }
        }
    }

    public void n() {
        layoutAds.setVisibility(View.GONE);
    }

    public void o() {
        ViewBean viewBean;
        if (this.viewBean.id.equals("_fab")) {
            viewBean = jC.a(sc_id).h(projectFileBean.getXmlName());
        } else {
            viewBean = jC.a(sc_id).c(projectFileBean.getXmlName(), this.viewBean.preId);
        }

        cC.c(sc_id).a(projectFileBean.getXmlName(), viewBean.clone(), this.viewBean);
        viewBean.copy(this.viewBean);
        Intent var2 = new Intent();
        var2.putExtra("view_id", this.viewBean.id);
        var2.putExtra("is_edit_image", p);
        var2.putExtra("bean", viewBean);
        setResult(RESULT_OK, var2);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 209 && resultCode == -1 && jC.d(sc_id) != null && data != null) {
            String var4 = data.getStringExtra("sc_id");
            ArrayList<ProjectResourceBean> result = data.getParcelableArrayListExtra("result");
            if (jC.d(var4) != null) {
                jC.d(var4).b(result);
                m();
            }
        }

    }

    @Override
    public void onBackPressed() {
        propertyItems.i(viewBean);
        o();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property);

        content = findViewById(R.id.content);
        layoutAds = findViewById(R.id.layout_ads);
        scrollView = findViewById(R.id.scroll_view);
        propertyGroupList = findViewById(R.id.property_group_list);

        if (!j()) {
            finish();
        }

        ro w = new ro(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.edit_view_properties_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        propertyGroups.add(1);
        propertyGroups.add(2);
        propertyGroups.add(3);
        propertyGroups.add(4);

        propertyGroupList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        propertyGroupList.setAdapter(new ItemAdapter());

        if (savedInstanceState != null) {
            sc_id = savedInstanceState.getString("sc_id");
            projectFileBean = savedInstanceState.getParcelable("project_file");
            viewBean = savedInstanceState.getParcelable("bean");
        } else {
            sc_id = getIntent().getStringExtra("sc_id");
            projectFileBean = getIntent().getParcelableExtra("project_file");
            viewBean = getIntent().getParcelableExtra("bean");
        }

        String viewId;
        if (viewBean.id.charAt(0) == '_') {
            viewId = viewBean.id.substring(1);
        } else {
            viewId = viewBean.id;
        }

        toolbar.setSubtitle(viewId);
        layoutAds.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.property_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_add_image_res) {
            p();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        propertyItems = new ViewPropertyItems(this);
        propertyItems.setProjectSettings(new ProjectSettings(sc_id));
        propertyItems.setOrientation(LinearLayout.VERTICAL);
        l();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!j()) {
            finish();
        }
        n();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        outState.putParcelable("project_file", projectFileBean);
        outState.putParcelable("bean", viewBean);
        super.onSaveInstanceState(outState);
    }

    public void p() {
        Intent var1 = new Intent(getApplicationContext(), ManageImageActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", sc_id);
        startActivityForResult(var1, 209);
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        @Override
        public int getItemCount() {
            return propertyGroups.size();
        }

        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ViewPropertyAnimatorCompat propertyAnimator;
            ColorMatrix colorMatrix;
            ColorMatrixColorFilter colorFilter;
            if (layoutPosition == position) {
                propertyAnimator = ViewCompat.animate(holder.imgIcon);
                propertyAnimator.scaleX(1.1F);
                propertyAnimator.scaleY(1.1F);
                propertyAnimator.setDuration(300L);
                propertyAnimator.setInterpolator(new AccelerateInterpolator());
                propertyAnimator.start();
                propertyAnimator = ViewCompat.animate(holder.imgIcon);
                propertyAnimator.scaleX(1.1F);
                propertyAnimator.scaleY(1.1F);
                propertyAnimator.setDuration(300L);
                propertyAnimator.setInterpolator(new AccelerateInterpolator());
                propertyAnimator.start();
                holder.pointerLeft.setVisibility(View.VISIBLE);
                colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(1.0F);
                colorFilter = new ColorMatrixColorFilter(colorMatrix);
            } else {
                propertyAnimator = ViewCompat.animate(holder.imgIcon);
                propertyAnimator.scaleX(1.0F);
                propertyAnimator.scaleY(1.0F);
                propertyAnimator.setDuration(300L);
                propertyAnimator.setInterpolator(new DecelerateInterpolator());
                propertyAnimator.start();
                propertyAnimator = ViewCompat.animate(holder.imgIcon);
                propertyAnimator.scaleX(1.0F);
                propertyAnimator.scaleY(1.0F);
                propertyAnimator.setDuration(300L);
                propertyAnimator.setInterpolator(new DecelerateInterpolator());
                propertyAnimator.start();
                holder.pointerLeft.setVisibility(View.GONE);
                colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(0.0F);
                colorFilter = new ColorMatrixColorFilter(colorMatrix);
            }

            holder.imgIcon.setColorFilter(colorFilter);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.common_category_triangle_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView imgIcon;
            public TextView tvName;
            public View pointerLeft;

            public ViewHolder(View itemView) {
                super(itemView);
                imgIcon = itemView.findViewById(R.id.img_icon);
                tvName = itemView.findViewById(R.id.tv_name);
                pointerLeft = itemView.findViewById(R.id.pointer_left);
                pointerLeft.setBackgroundResource(R.drawable.triangle_point_left_primary);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View var1) {
                if (!mB.a()) {
                    if (getLayoutPosition() != -1) {
                        if (getLayoutPosition() != layoutPosition) {
                            notifyItemChanged(layoutPosition);
                            layoutPosition = getLayoutPosition();
                            notifyItemChanged(layoutPosition);
                            switch (propertyGroups.get(layoutPosition)) {
                                case 1 -> propertyItems.d(viewBean);
                                case 2 -> propertyItems.g(viewBean);
                                case 3 -> propertyItems.h(viewBean);
                                case 4 -> propertyItems.f(viewBean);
                            }
                        }
                    }

                }
            }
        }
    }
}
