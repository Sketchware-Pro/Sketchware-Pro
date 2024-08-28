package com.besome.sketch.editor.manage.image;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.xB;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.analytics.HitBuilders;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Iterator;

public class ManageImageImportActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public ImageView img;

    public ImageView img_backbtn;

    public TextView tv_currentnum;

    public TextView tv_totalnum;

    public TextView tv_sendbtn;

    public TextView tv_samename;

    public RecyclerView recycler_list;

    public EasyDeleteEditText ed_input;

    public EditText ed_input_edittext;

    public CheckBox chk_samename;

    public ItemAdapter adapter;

    public ArrayList<ProjectResourceBean> projectImages;

    public ArrayList<ProjectResourceBean> selectedCollections;

    public int selectedCollectionsSize = 0;

    public int selectedItem = 0;

    public Button btn_decide;

    public QB nameValidator;

    public static ArrayList a(ManageImageImportActivity manageImageImportActivity) {
        return manageImageImportActivity.selectedCollections;
    }

    public static int b(ManageImageImportActivity manageImageImportActivity) {
        return manageImageImportActivity.selectedItem;
    }

    public static TextView c(ManageImageImportActivity manageImageImportActivity) {
        return manageImageImportActivity.tv_currentnum;
    }

    public static EditText d(ManageImageImportActivity manageImageImportActivity) {
        return manageImageImportActivity.ed_input_edittext;
    }

    public static CheckBox e(ManageImageImportActivity manageImageImportActivity) {
        return manageImageImportActivity.chk_samename;
    }

    public static ItemAdapter f(ManageImageImportActivity manageImageImportActivity) {
        return manageImageImportActivity.adapter;
    }

    public final ArrayList<String> getReservedSelectedCollectionNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        Iterator<ProjectResourceBean> it = this.selectedCollections.iterator();
        while (it.hasNext()) {
            names.add(it.next().resName);
        }
        return names;
    }

    public final ArrayList<String> getReservedProjectImageNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        Iterator<ProjectResourceBean> it = this.projectImages.iterator();
        while (it.hasNext()) {
            names.add(it.next().resName);
        }
        return names;
    }

    public final boolean n() {
        ArrayList arrayList = new ArrayList();
        Iterator<ProjectResourceBean> it = this.selectedCollections.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (next.isDuplicateCollection) {
                arrayList.add(next.resName);
            }
        }
        if (arrayList.size() <= 0) {
            return false;
        }
        String a = xB.b().a(getApplicationContext(), R.string.common_message_name_unavailable);
        Iterator it2 = arrayList.iterator();
        String str = "";
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            if (str.length() > 0) {
                str = str + ", ";
            }
            str = str + str2;
        }
        bB.a(getApplicationContext(), a + "\n[" + str + "]", bB.TOAST_WARNING).show();
        return true;
    }

    public final boolean isNameValid() {
        return this.nameValidator.b();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (mB.a()) {
            return;
        }
        int id = v.getId();
        if (id != R.id.btn_decide) {
            if (id != R.id.img_backbtn) {
                if (id == R.id.tv_sendbtn && !n()) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("results", this.selectedCollections);
                    setResult(RESULT_OK, intent);
                    finish();
                    return;
                }
                return;
            }
            onBackPressed();
            return;
        }
        String name = this.ed_input_edittext.getText().toString();
        if (!isNameValid()) {
            this.ed_input_edittext.setText(this.selectedCollections.get(this.selectedItem).resName);
            return;
        }
        if (!this.chk_samename.isChecked()) {
            ProjectResourceBean projectResourceBean = this.selectedCollections.get(this.selectedItem);
            projectResourceBean.resName = name;
            projectResourceBean.isDuplicateCollection = false;
            this.nameValidator.a(getReservedSelectedCollectionNames());
            this.adapter.notifyDataSetChanged();
            return;
        }
        int i = 0;
        while (i < this.selectedCollections.size()) {
            ProjectResourceBean projectResourceBean2 = this.selectedCollections.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append("_");
            i++;
            sb.append(i);
            projectResourceBean2.resName = sb.toString();
            projectResourceBean2.isDuplicateCollection = false;
        }
        this.nameValidator.a(getReservedSelectedCollectionNames());
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }
        setContentView(R.layout.manage_image_import);
        this.img_backbtn = (ImageView) findViewById(R.id.img_backbtn);
        this.img_backbtn.setOnClickListener(this);
        this.tv_currentnum = (TextView) findViewById(R.id.tv_currentnum);
        this.tv_totalnum = (TextView) findViewById(R.id.tv_totalnum);
        this.tv_sendbtn = (TextView) findViewById(R.id.tv_sendbtn);
        this.tv_sendbtn.setText(xB.b().a(getApplicationContext(), R.string.common_word_import).toUpperCase());
        this.tv_sendbtn.setOnClickListener(this);
        this.tv_samename = (TextView) findViewById(R.id.tv_samename);
        this.tv_samename.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_title_apply_same_naming));
        this.adapter = new ItemAdapter();
        this.recycler_list = (RecyclerView) findViewById(R.id.recycler_list);
        this.recycler_list.setHasFixedSize(true);
        this.recycler_list.setAdapter(this.adapter);
        this.recycler_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        this.projectImages = getIntent().getParcelableArrayListExtra("project_images");
        this.selectedCollections = getIntent().getParcelableArrayListExtra("selected_collections");
        this.selectedCollectionsSize = this.selectedCollections.size();
        this.tv_currentnum.setText(String.valueOf(1));
        this.tv_totalnum.setText(String.valueOf(this.selectedCollectionsSize));
        this.ed_input = (EasyDeleteEditText) findViewById(R.id.ed_input);
        this.ed_input_edittext = this.ed_input.getEditText();
        this.ed_input_edittext.setText(this.selectedCollections.get(0).resName);
        this.ed_input_edittext.setPrivateImeOptions("defaultInputmode=english;");
        this.ed_input.setHint(xB.b().a(this, R.string.design_manager_image_hint_enter_image_name));
        this.nameValidator = new QB(getApplicationContext(), this.ed_input.getTextInputLayout(), uq.b, getReservedProjectImageNames(), getReservedSelectedCollectionNames());
        this.chk_samename = (CheckBox) findViewById(R.id.chk_samename);
        this.chk_samename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameValidator.c(null);
                nameValidator.a(selectedCollections.size());
            } else {
                nameValidator.c(selectedCollections.get(selectedItem).resName);
                nameValidator.a(1);
            }
        });
        this.btn_decide = (Button) findViewById(R.id.btn_decide);
        this.btn_decide.setText(xB.b().a(getApplicationContext(), R.string.design_manager_change_name_button));
        this.btn_decide.setOnClickListener(this);
        this.img = (ImageView) findViewById(R.id.img);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initializeLogic();
        showPreview(0);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        this.d.setScreenName(ManageImageImportActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final void initializeLogic() {
        ArrayList duplicateCollections = new ArrayList();
        ArrayList notDuplicateCollections = new ArrayList();
        Iterator<ProjectResourceBean> it = this.selectedCollections.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (isNameInUseByProjectImage(next.resName)) {
                next.isDuplicateCollection = true;
                duplicateCollections.add(next);
            } else {
                next.isDuplicateCollection = false;
                notDuplicateCollections.add(next);
            }
        }
        if (duplicateCollections.size() > 0) {
            bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_conflict), bB.TOAST_WARNING).show();
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_no_conflict), bB.TOAST_NORMAL).show();
        }
        this.selectedCollections = new ArrayList<>();
        Iterator it2 = duplicateCollections.iterator();
        while (it2.hasNext()) {
            this.selectedCollections.add((ProjectResourceBean) it2.next());
        }
        Iterator it3 = notDuplicateCollections.iterator();
        while (it3.hasNext()) {
            this.selectedCollections.add((ProjectResourceBean) it3.next());
        }
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            public LinearLayout layout_item;

            public ImageView img_conflict;

            public ImageView img;

            public TextView tv_name;

            public ViewHolder(View itemView) {
                super(itemView);
                this.layout_item = (LinearLayout) itemView.findViewById(R.id.layout_item);
                this.img_conflict = (ImageView) itemView.findViewById(R.id.img_conflict);
                this.img = (ImageView) itemView.findViewById(R.id.img);
                this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                this.img.setOnClickListener(v -> {
                    if (!mB.a()) {
                        selectedItem = getLayoutPosition();
                        showPreview(selectedItem);
                        tv_currentnum.setText(String.valueOf(selectedItem + 1));
                        ed_input_edittext.setText(selectedCollections.get(selectedItem).resName);
                        if (chk_samename.isChecked()) {
                            nameValidator.c(null);
                            nameValidator.a(selectedCollections.size());
                        } else {
                            nameValidator.c(selectedCollections.get(selectedItem).resName);
                            nameValidator.a(1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }

        public ItemAdapter() {
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) ManageImageImportActivity.this.selectedCollections.get(position);
            String str = projectResourceBean.resFullName;
            if (projectResourceBean.isDuplicateCollection) {
                viewHolder.img_conflict.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                viewHolder.img_conflict.setImageResource(R.drawable.ic_ok_48dp);
            }
            if (position == ManageImageImportActivity.this.selectedItem) {
                viewHolder.img.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                viewHolder.img.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            Glide.with(ManageImageImportActivity.this.getApplicationContext()).load(str).asBitmap().centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(viewHolder.img));
            viewHolder.tv_name.setText(((ProjectResourceBean) ManageImageImportActivity.this.selectedCollections.get(position)).resName);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_import_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return ManageImageImportActivity.this.selectedCollections.size();
        }
    }

    public static int a(ManageImageImportActivity manageImageImportActivity, int i) {
        manageImageImportActivity.selectedItem = i;
        return i;
    }

    public static void b(ManageImageImportActivity manageImageImportActivity, int i) {
        manageImageImportActivity.showPreview(i);
    }

    public final void showPreview(int index) {
        Glide.with(getApplicationContext()).load(this.selectedCollections.get(index).resFullName).asBitmap().centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(img));
    }

    public final boolean isNameInUseByProjectImage(String name) {
        Iterator<ProjectResourceBean> it = this.projectImages.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
