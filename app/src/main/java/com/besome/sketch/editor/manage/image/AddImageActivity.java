package com.besome.sketch.editor.manage.image;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
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
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.yy;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageImageAddBinding;

public class AddImageActivity extends BaseDialogActivity implements View.OnClickListener {
    public ManageImageAddBinding binding;
    private ArrayList<ProjectResourceBean> existingImages;
    private ArrayList<Uri> pickedImageUris;
    private PB O;
    private String sc_id;
    private ArrayList<ProjectResourceBean> images;
    private boolean multipleImagesPicked = false;
    private boolean B = false;
    private String imageFilePath = null;
    private int imageRotationDegrees = 0;
    private int imageExifOrientation = 0;
    private int imageScaleY = 1;
    private int imageScaleX = 1;
    private String dir_path = "";
    private boolean editing = false;
    private ProjectResourceBean image = null;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (binding != null && binding.imgSelected != null) {
                    binding.imgSelected.setEnabled(true);
                }
                Intent data = result.getData();
                if (result.getResultCode() == RESULT_OK && data != null) {
                    binding.tvAddPhoto.setVisibility(View.GONE);
                    imageRotationDegrees = 0;
                    imageScaleY = 1;
                    imageScaleX = 1;
                    if (data.getClipData() == null) {
                        B = true;
                        multipleImagesPicked = false;
                        setImageFromUri(data.getData());
                        if (O != null) {
                            O.a(1);
                        }
                    } else {
                        ClipData clipData = data.getClipData();
                        if (clipData.getItemCount() == 1) {
                            B = true;
                            multipleImagesPicked = false;
                            setImageFromUri(clipData.getItemAt(0).getUri());
                            if (O != null) {
                                O.a(1);
                            }
                        } else {
                            handleImagePickClipData(clipData);
                            multipleImagesPicked = true;
                            if (O != null) {
                                O.a(clipData.getItemCount());
                            }
                        }
                    }
                }
            });

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
    public void onClick(View view) {
        int id = view.getId();
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
            pickImages(!editing);
        } else if (id == R.id.img_vertical) {
            flipImageVertically();
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(getString(R.string.design_manager_image_title_add_image));
        d(getString(R.string.common_word_save));
        
        binding = ManageImageAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        existingImages = intent.getParcelableArrayListExtra("images");
        sc_id = intent.getStringExtra("sc_id");
        dir_path = intent.getStringExtra("dir_path");
        image = intent.getParcelableExtra("edit_target");
        
        if (image != null) {
            editing = true;
        }

        binding.chkCollection.setText(R.string.msg_add_to_my_collection);
        binding.tvAddPhoto.setText(R.string.design_manager_image_title_add_image);
        
        binding.imgSelected.setOnClickListener(this);
        binding.imgRotate.setOnClickListener(this);
        binding.imgVertical.setOnClickListener(this);
        binding.imgHorizontal.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);

        O = new PB(this, binding.tiInput, uq.b, getReservedImageNames());
        O.a(1);
        
        B = false;
        imageRotationDegrees = 0;
        imageScaleY = 1;
        imageScaleX = 1;
        new oB().f(dir_path); // java.io.File.mkdirs
        images = new ArrayList<>();
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        if (editing) {
            image.isEdited = true;
            e(getString(R.string.design_manager_image_title_edit_image));
            imageRotationDegrees = image.rotate;
            imageScaleX = image.flipHorizontal;
            imageScaleY = image.flipVertical;
            
            O = new PB(this, binding.tiInput, uq.b, getReservedImageNames(), image.resName);
            O.a(1);
            
            binding.edInput.setText(image.resName);
            binding.edInput.setEnabled(false);
            binding.chkCollection.setEnabled(false);
            binding.tvAddPhoto.setVisibility(View.GONE);
            
            if (image.savedPos == 0) {
                setImageFromFile(a(image));
            } else {
                setImageFromFile(image.resFullName);
            }
        }
    }

    private ArrayList<String> getReservedImageNames() {
        var names = new ArrayList<String>();
        for (var existingImage : existingImages) {
            names.add(existingImage.resName);
        }
        return names;
    }

    private void refreshPreview() {
        binding.imgSelected.setImageBitmap(iB.a(iB.a(iB.a(imageFilePath, 1024, 1024), imageExifOrientation), imageRotationDegrees, imageScaleX, imageScaleY));
    }

    private void save() {
        if (a(O)) {
            new Handler().postDelayed(() -> {
                k();
                new SaveAsyncTask(this).execute();
            }, 500L);
        }
    }

    private void s() {
        if (binding.tvDesc != null) {
            binding.tvDesc.setVisibility(View.INVISIBLE);
        }
        if (binding.layoutImgInform != null && binding.layoutImgModify != null && binding.tvImgcnt != null) {
            binding.layoutImgInform.setVisibility(View.GONE);
            binding.layoutImgModify.setVisibility(View.VISIBLE);
            binding.tvImgcnt.setVisibility(View.GONE);
        }
    }

    private void pickImages(boolean allowMultiple) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (allowMultiple) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
            imagePickerLauncher.launch(Intent.createChooser(intent, getString(R.string.common_word_choose)));
        } catch (ActivityNotFoundException unused) {
            bB.b(this, getString(R.string.common_error_activity_not_found), bB.TOAST_NORMAL).show();
        }
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
        s();
    }

    private void setImageRotation(int i) {
        if (imageFilePath != null && !imageFilePath.isEmpty()) {
            imageRotationDegrees = i;
            if (imageRotationDegrees == 360) {
                imageRotationDegrees = 0;
            }
            refreshPreview();
        }
    }

    private void onMultipleImagesPicked(int count) {
        if (binding.layoutImgInform == null || binding.layoutImgModify == null || binding.tvImgcnt == null) {
            return;
        }
        binding.layoutImgInform.setVisibility(View.VISIBLE);
        binding.layoutImgModify.setVisibility(View.GONE);
        binding.tvImgcnt.setVisibility(View.VISIBLE);
        binding.tvImgcnt.setText("+ " + (count - 1) + " more");
    }

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

    private boolean a(PB pb) {
        if (!pb.b()) {
            return false;
        }
        if (B || imageFilePath != null) {
            return true;
        }
        binding.tvDesc.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }

    private void setImageFromUri(Uri uri) {
        String filePath;
        if (uri != null && (filePath = HB.a(this, uri)) != null) {
            setImageFromFile(filePath);
        }
    }

    private void handleImagePickClipData(ClipData clipData) {
        if (clipData != null) {
            pickedImageUris = new ArrayList<>();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                if (i == 0) {
                    setImageFromUri(clipData.getItemAt(i).getUri());
                }
                pickedImageUris.add(clipData.getItemAt(i).getUri());
            }
            onMultipleImagesPicked(clipData.getItemCount());
        }
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return dir_path + File.separator + projectResourceBean.resFullName;
    }

    private static class SaveAsyncTask extends MA {
        private final WeakReference<AddImageActivity> activity;

        public SaveAsyncTask(AddImageActivity activity) {
            super(activity.getApplicationContext());
            this.activity = new WeakReference<>(activity);
            activity.a(this);
        }

        @Override
        public void a() {
            var activity = this.activity.get();
            activity.h();
            Intent intent = new Intent();
            intent.putExtra("sc_id", activity.sc_id);
            if (activity.editing) {
                intent.putExtra("image", activity.image);
            } else {
                intent.putExtra("images", activity.images);
            }
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }

        @Override
        public void b() throws By {
            var activity = this.activity.get();
            try {
                publishProgress("Now processing..");
                if (!activity.multipleImagesPicked) {
                    if (!activity.editing) {
                        var image = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                                Helper.getText(activity.binding.edInput).trim(), activity.imageFilePath);
                        image.savedPos = 1;
                        image.isNew = true;
                        image.rotate = activity.imageRotationDegrees;
                        image.flipVertical = activity.imageScaleY;
                        image.flipHorizontal = activity.imageScaleX;
                        if (activity.binding.chkCollection.isChecked()) {
                            Op.g().a(activity.sc_id, image);
                        }
                        activity.images.add(image);
                    } else if (!activity.B) {
                        var image = activity.image;
                        image.rotate = activity.imageRotationDegrees;
                        image.flipHorizontal = activity.imageScaleX;
                        image.flipVertical = activity.imageScaleY;
                        image.isEdited = true;
                    } else {
                        var image = activity.image;
                        image.resFullName = activity.imageFilePath;
                        image.savedPos = 1;
                        image.rotate = activity.imageRotationDegrees;
                        image.flipVertical = activity.imageScaleY;
                        image.flipHorizontal = activity.imageScaleX;
                        image.isEdited = true;
                    }
                } else {
                    var toAdd = new ArrayList<ProjectResourceBean>();
                    int i = 0;
                    while (i < activity.pickedImageUris.size()) {
                        var uri = activity.pickedImageUris.get(i);
                        var imageName = Helper.getText(activity.binding.edInput).trim() + "_" + ++i;
                        var imageFilePath = HB.a(activity.getApplicationContext(), uri);
                        if (imageFilePath == null) {
                            return;
                        }
                        var image = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                                imageName, imageFilePath);
                        image.savedPos = 1;
                        image.isNew = true;
                        image.rotate = iB.a(imageFilePath);
                        image.flipVertical = 1;
                        image.flipHorizontal = 1;
                        toAdd.add(image);
                    }
                    if (activity.binding.chkCollection.isChecked()) {
                        Op.g().a(activity.sc_id, toAdd, true);
                    }
                    activity.multipleImagesPicked = false;
                    activity.images.addAll(toAdd);
                }
            } catch (Exception e) {
                // the bytecode's lying
                // noinspection ConstantValue
                if (e instanceof yy yy) {
                    var errorMessage = yy.getMessage();
                    var code = switch (errorMessage) {
                        case "fail_to_copy" -> R.string.collection_failed_to_copy;
                        case "file_no_exist" -> R.string.collection_no_exist_file;
                        case "duplicate_name" -> R.string.collection_duplicated_name;
                        default -> 0;
                    };
                    var message = code != 0 ? activity.getString(code) : null;

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