package com.besome.sketch.editor.manage.image;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import a.a.a.By;
import a.a.a.HB;
import a.a.a.MA;
import a.a.a.Op;
import a.a.a.PB;
import a.a.a.bB;
import a.a.a.iB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.yy;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageImageAddBinding;

public class AddImageCollectionActivity extends BaseDialogActivity implements View.OnClickListener {

    public ManageImageAddBinding binding;
    private PB imageNameValidator;
    private String sc_id;
    private ArrayList<ProjectResourceBean> images;
    private boolean z = false;
    private String imageFilePath = null;
    private int imageRotationDegrees = 0;
    private int imageExifOrientation = 0;
    private int imageScaleY = 1;
    private int imageScaleX = 1;
    private boolean editing = false;
    private ProjectResourceBean editTarget = null;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (binding != null && binding.imgSelected != null) {
                    binding.imgSelected.setEnabled(true);
                }
                Intent data = result.getData();
                if (result.getResultCode() == RESULT_OK && data != null) {
                    imageRotationDegrees = 0;
                    imageScaleY = 1;
                    imageScaleX = 1;
                    z = true;
                    setImageFromUri(data.getData());
                    if (imageNameValidator != null) {
                        imageNameValidator.a(1);
                    }
                }
            });

    private void flipImageHorizontally() {
        if (imageFilePath != null && !imageFilePath.isEmpty()) {
            if (imageRotationDegrees != 90 && imageRotationDegrees != 270) {
                imageScaleX *= -1;
            } else {
                imageScaleY *= -1;
            }
            refreshPreview();
        }
    }

    private void flipImageVertically() {
        if (imageFilePath != null && !imageFilePath.isEmpty()) {
            if (imageRotationDegrees != 90 && imageRotationDegrees != 270) {
                imageScaleY *= -1;
            } else {
                imageScaleX *= -1;
            }
            refreshPreview();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();
            if (id == R.id.cancel_button) {
                setResult(RESULT_CANCELED);
                finish();
            } else if (id == R.id.common_dialog_cancel_button) {
                finish();
            } else if (id == R.id.common_dialog_ok_button) {
                save();
            } else if (id == R.id.img_horizontal) {
                flipImageHorizontally();
            } else if (id == R.id.img_rotate) {
                setImageRotation(imageRotationDegrees + 90);
            } else if (id == R.id.img_selected) {
                binding.imgSelected.setEnabled(false);
                if (!editing) {
                    pickImage();
                }
            } else if (id == R.id.img_vertical) {
                flipImageVertically();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        e(getString(R.string.design_manager_image_title_add_image));
        d(getString(R.string.common_word_save));
        
        binding = ManageImageAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        Intent intent = getIntent();
        images = intent.getParcelableArrayListExtra("images");
        sc_id = intent.getStringExtra("sc_id");
        editTarget = intent.getParcelableExtra("edit_target");
        
        if (editTarget != null) {
            editing = true;
        }

        binding.chkCollection.setVisibility(View.GONE);

        imageNameValidator = new PB(this, binding.tiInput, uq.b, getReservedImageNames());
        imageNameValidator.a(1);
        
        binding.tvAddPhoto.setText(R.string.design_manager_image_title_add_image);
        
        binding.imgSelected.setOnClickListener(this);
        binding.imgRotate.setOnClickListener(this);
        binding.imgVertical.setOnClickListener(this);
        binding.imgHorizontal.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        
        z = false;
        imageRotationDegrees = 0;
        imageScaleY = 1;
        imageScaleX = 1;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (editing) {
            editTarget.isEdited = true;
            e(getString(R.string.design_manager_image_title_edit_image_name));
            
            imageNameValidator = new PB(this, binding.tiInput, uq.b, getReservedImageNames(), editTarget.resName);
            imageNameValidator.a(1);
            
            binding.edInput.setText(editTarget.resName);
            binding.chkCollection.setVisibility(View.GONE);
            binding.tvAddPhoto.setVisibility(View.GONE);
            binding.layoutImgModify.setVisibility(View.GONE);
            
            setImageFromFile(a(editTarget));
        }
    }

    private ArrayList<String> getReservedImageNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean image : images) {
            names.add(image.resName);
        }
        return names;
    }

    private void pickImage() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(Intent.createChooser(intent, getString(R.string.common_word_choose)));
        } catch (ActivityNotFoundException unused) {
            bB.b(this, getString(R.string.common_error_activity_not_found), bB.TOAST_NORMAL).show();
        }
    }

    private void refreshPreview() {
        binding.imgSelected.setImageBitmap(iB.a(iB.a(iB.a(imageFilePath, 1024, 1024), imageExifOrientation), imageRotationDegrees, imageScaleX, imageScaleY));
    }

    private void save() {
        if (a(imageNameValidator)) {
            new Handler().postDelayed(() -> {
                k();
                new SaveAsyncTask(this).execute();
            }, 500L);
        }
    }

    private void t() {
        if (binding.tvDesc != null) {
            binding.tvDesc.setVisibility(View.INVISIBLE);
        }
        if (binding.layoutImgInform != null && binding.layoutImgModify != null && binding.tvImgcnt != null) {
            binding.layoutImgInform.setVisibility(View.GONE);
            binding.layoutImgModify.setVisibility(View.VISIBLE);
            binding.tvImgcnt.setVisibility(View.GONE);
        }
    }

    private boolean a(PB validator) {
        if (!validator.b()) {
            return false;
        }
        if (z || imageFilePath != null) {
            return true;
        }
        binding.tvDesc.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }

    private void setImageFromFile(String path) {
        imageFilePath = path;
        binding.imgSelected.setImageBitmap(iB.a(path, 1024, 1024));
        
        int indexOfFilenameExtension = path.lastIndexOf(".");
        if (path.endsWith(".9.png")) {
            indexOfFilenameExtension = path.lastIndexOf(".9.png");
        }
        
        if (binding.edInput != null && (binding.edInput.getText() == null || binding.edInput.getText().length() <= 0)) {
            binding.edInput.setText(path.substring(path.lastIndexOf("/") + 1, indexOfFilenameExtension));
        }
        
        try {
            imageExifOrientation = iB.a(path);
            refreshPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        t();
    }

    private void setImageRotation(int degrees) {
        if (imageFilePath != null && !imageFilePath.isEmpty()) {
            imageRotationDegrees = degrees;
            if (imageRotationDegrees == 360) {
                imageRotationDegrees = 0;
            }
            refreshPreview();
        }
    }

    private void setImageFromUri(Uri uri) {
        String filePath;
        if (uri != null && (filePath = HB.a(this, uri)) != null) {
            setImageFromFile(filePath);
        }
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
    }

    private static class SaveAsyncTask extends MA {
        private final WeakReference<AddImageCollectionActivity> activity;

        public SaveAsyncTask(AddImageCollectionActivity activity) {
            super(activity.getApplicationContext());
            this.activity = new WeakReference<>(activity);
            activity.a(this);
        }

        @Override
        public void a() {
            var activity = this.activity.get();
            bB.a(activity.getApplicationContext(), activity.getString(
                    activity.editing ? R.string.design_manager_message_edit_complete :
                            R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
            activity.h();
            activity.finish();
        }

        @Override
        public void b() throws By {
            var activity = this.activity.get();
            try {
                publishProgress("Now processing..");
                if (!activity.editing) {
                    var image = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                            Helper.getText(activity.binding.edInput).trim(), activity.imageFilePath);
                    image.savedPos = 1;
                    image.isNew = true;
                    image.rotate = activity.imageRotationDegrees;
                    image.flipVertical = activity.imageScaleY;
                    image.flipHorizontal = activity.imageScaleX;
                    Op.g().a(activity.sc_id, image);
                } else {
                    Op.g().a(activity.editTarget, Helper.getText(activity.binding.edInput), false);
                }
            } catch (Exception e) {
                // the bytecode's lying
                // noinspection ConstantValue
                if (e instanceof yy yy) {
                    var messageId = switch (yy.getMessage()) {
                        case "fail_to_copy" -> R.string.collection_failed_to_copy;
                        case "file_no_exist" -> R.string.collection_no_exist_file;
                        case "duplicate_name" -> R.string.collection_duplicated_name;
                        default -> 0;
                    };
                    var message = messageId != 0 ? activity.getString(messageId) : "";

                    var a = yy.a();
                    if (a != null && !a.isEmpty()) {
                        var names = "";
                        for (String name : a) {
                            if (!names.isEmpty()) {
                                names += ", ";
                            }
                            names += name;
                        }
                        message += "[" + names + "]";
                    }
                    throw new By(message);
                }
                e.printStackTrace();
                throw new By(e.getMessage());
            }
        }

        @Override
        public void a(String str) {
            activity.get().h();
        }
    }
}