package a.a.a;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.firebase.FirebaseActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryFirebasePreviewBinding;
import pro.sketchware.utility.SketchwareUtil;

public class kv extends LinearLayout implements nv {

    private final FirebaseActivity activity;
    private final ManageLibraryFirebasePreviewBinding binding;
    private ProjectLibraryBean firebaseLibraryBean;

    public kv(FirebaseActivity firebaseActivity) {
        super(firebaseActivity);
        binding = ManageLibraryFirebasePreviewBinding.inflate(firebaseActivity.getLayoutInflater(), this, true);
        activity = firebaseActivity;
        initialize();
    }

    @Override
    public void a() {
    }

    private void initialize() {
        gB.b(this, 600, 200, null);

        binding.layoutSwitch.setOnClickListener(v -> {
            if (binding.libSwitch.isChecked() || !firebaseLibraryBean.data.isEmpty()) {
                binding.libSwitch.setChecked(!binding.libSwitch.isChecked());
                if ("Y".equals(firebaseLibraryBean.useYn) && !binding.libSwitch.isChecked()) {
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
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        dialog.setTitle(Helper.getResString(R.string.common_word_warning));
        dialog.setIcon(R.drawable.delete_96);
        dialog.setMessage(Helper.getResString(R.string.design_library_firebase_dialog_description_confirm_uncheck_firebase));
        dialog.setCancelable(false);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (v, which) -> {
            firebaseLibraryBean.useYn = "N";
            binding.libSwitch.setChecked(false);
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), (v, which) -> {
            binding.libSwitch.setChecked(true);
            v.dismiss();
        });
        dialog.show();
    }

    @Override
    public void a(ProjectLibraryBean mProjectLibraryBean) {
        firebaseLibraryBean = mProjectLibraryBean;
        if (binding.libSwitch.isChecked()) {
            firebaseLibraryBean.useYn = "Y";
        } else {
            firebaseLibraryBean.useYn = "N";
        }
    }

    @Override
    public String getDocUrl() {
        return "";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public void setData(ProjectLibraryBean mProjectLibraryBean) {
        firebaseLibraryBean = mProjectLibraryBean;
        binding.tvProjectId.setText(firebaseLibraryBean.data);
        binding.tvAppId.setText(firebaseLibraryBean.reserved1);
        binding.tvApiKey.setText(firebaseLibraryBean.reserved2);
        binding.tvStorageUrl.setText(firebaseLibraryBean.reserved3);
        binding.libSwitch.setChecked(firebaseLibraryBean.useYn.equals("Y"));
    }
}