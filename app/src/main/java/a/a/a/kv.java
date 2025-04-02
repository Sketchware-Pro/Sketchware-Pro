package a.a.a;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.firebase.FirebaseActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.utility.SketchwareUtil;

public class kv extends LinearLayout implements nv {

    public FirebaseActivity a;
    private ProjectLibraryBean firebaseLibraryBean;
    private TextView tv_enable;
    private TextView tv_app_id;
    private TextView tv_api_key;
    private TextView tv_project_id;
    private TextView tv_storage_url;
    private TextView tv_title_app_id;
    private TextView tv_title_api_key;
    private LinearLayout layout_switch;
    private TextView tv_title_project_id;
    private TextView tv_title_storage_url;
    private MaterialSwitch lib_switch;

    public kv(FirebaseActivity var1) {
        super(var1);
        this.a = var1;
        this.a(var1);
    }

    public void a() {

    }

    public final void a(Context var1) {
        wB.a(var1, this, R.layout.manage_library_firebase_preview);
        gB.b(this, 600, 200, null);

        tv_enable = findViewById(R.id.tv_enable);
        tv_app_id = findViewById(R.id.tv_app_id);
        lib_switch = findViewById(R.id.lib_switch);
        tv_api_key = findViewById(R.id.tv_api_key);
        layout_switch = findViewById(R.id.layout_switch);
        tv_project_id = findViewById(R.id.tv_project_id);
        tv_storage_url = findViewById(R.id.tv_storage_url);
        tv_title_app_id = findViewById(R.id.tv_title_app_id);
        tv_title_api_key = findViewById(R.id.tv_title_api_key);
        tv_title_project_id = findViewById(R.id.tv_title_project_id);
        tv_title_storage_url = findViewById(R.id.tv_title_storage_url);

        layout_switch.setOnClickListener(v -> {
            if (lib_switch.isChecked() || !firebaseLibraryBean.data.isEmpty()) {
                lib_switch.setChecked(!lib_switch.isChecked());
                if ("Y".equals(firebaseLibraryBean.useYn) && !lib_switch.isChecked()) {
                    configureLibraryDialog();
                } else {
                    firebaseLibraryBean.useYn = "Y";
                }
            } else {
                SketchwareUtil.toast("Configure Firebase settings first, either by importing google-services.json, " +
                        "or by manually entering the project's details.", Toast.LENGTH_LONG);
            }
        });
    }

    private void configureLibraryDialog() {
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(a);
        dialog.setTitle(Helper.getResString(R.string.common_word_warning));
        dialog.setIcon(R.drawable.delete_96);
        dialog.setMessage(Helper.getResString(R.string.design_library_firebase_dialog_description_confirm_uncheck_firebase));
        dialog.setCancelable(false);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (v, which) -> {
            firebaseLibraryBean.useYn = "N";
            lib_switch.setChecked(false);
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), (v, which) -> {
            lib_switch.setChecked(true);
            v.dismiss();
        });
        dialog.show();
    }

    public void a(ProjectLibraryBean mProjectLibraryBean) {
        firebaseLibraryBean = mProjectLibraryBean;
        if (lib_switch.isChecked()) {
            firebaseLibraryBean.useYn = "Y";
        } else {
            firebaseLibraryBean.useYn = "N";
        }
    }

    public String getDocUrl() {
        return "";
    }

    public boolean isValid() {
        return true;
    }

    public void setData(ProjectLibraryBean mProjectLibraryBean) {
        firebaseLibraryBean = mProjectLibraryBean;
        tv_project_id.setText(firebaseLibraryBean.data);
        tv_app_id.setText(firebaseLibraryBean.reserved1);
        tv_api_key.setText(firebaseLibraryBean.reserved2);
        tv_storage_url.setText(firebaseLibraryBean.reserved3);
        lib_switch.setChecked(firebaseLibraryBean.useYn.equals("Y"));
    }
}