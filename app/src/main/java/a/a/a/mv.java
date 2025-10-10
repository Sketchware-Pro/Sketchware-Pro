package a.a.a;

import android.widget.LinearLayout;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.firebase.FirebaseActivity;

import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryFirebaseStorageUrlSettingsBinding;

public class mv extends LinearLayout implements nv {

    private final ManageLibraryFirebaseStorageUrlSettingsBinding binding;

    public mv(FirebaseActivity firebaseActivity) {
        super(firebaseActivity);
        binding = ManageLibraryFirebaseStorageUrlSettingsBinding.inflate(firebaseActivity.getLayoutInflater(), this, true);

        gB.b(this, 600, 200, null);
        binding.tvTitleStorageUrl.setText(R.string.design_library_firebase_title_storage_bucket_url);
    }

    @Override
    public void a() {
        mB.a(getContext(), binding.edInputStorageUrl);
    }

    @Override
    public void a(ProjectLibraryBean libraryBean) {
        String var2 = binding.edInputStorageUrl.getText().toString().trim();
        if (!var2.isEmpty()) {
            libraryBean.reserved3 = var2;
            if (var2.startsWith("gs://")) {
                libraryBean.reserved3 = var2.replaceFirst("gs://", "");
            }

            if (var2.endsWith("/")) {
                libraryBean.reserved3 = var2.substring(0, var2.lastIndexOf("/"));
            }
        }

    }

    //todo: Update docs url
    @Override
    public String getDocUrl() {
        return "https://docs.sketchware.io/docs/firebase-storage.html";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public void setData(ProjectLibraryBean libraryBean) {
        String reserved3 = libraryBean.reserved3;
        if (reserved3 != null && reserved3.length() > 0) {
            binding.edInputStorageUrl.setText(libraryBean.reserved3);
        }
    }
}
