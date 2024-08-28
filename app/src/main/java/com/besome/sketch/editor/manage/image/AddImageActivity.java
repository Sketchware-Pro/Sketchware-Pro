package com.besome.sketch.editor.manage.image;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.sketchware.remod.R;

import java.io.File;
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
import a.a.a.xB;
import a.a.a.yy;

public class AddImageActivity extends BaseDialogActivity implements View.OnClickListener {
    private ArrayList<ProjectResourceBean> existingImages;
    private TextView tv_add_photo;
    private ImageView preview;
    private ArrayList<Uri> pickedImageUris;
    private PB O;
    private EditText ed_input_edittext;
    private EasyDeleteEditText ed_input;
    private TextView tv_desc;
    private CheckBox chk_collection;
    private String sc_id;
    private ArrayList<ProjectResourceBean> images;
    private boolean multipleImagesPicked = false;
    private LinearLayout layout_img_inform = null;
    private LinearLayout layout_img_modify = null;
    private TextView tv_imgcnt = null;
    private boolean B = false;
    private String imageFilePath = null;
    private int imageRotationDegrees = 0;
    private int imageExifOrientation = 0;
    private int imageScaleY = 1;
    private int imageScaleX = 1;
    private String dir_path = "";
    private boolean editing = false;
    private ProjectResourceBean image = null;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 215 && preview != null) {
            preview.setEnabled(true);
            if (resultCode == RESULT_OK) {
                tv_add_photo.setVisibility(View.GONE);
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
            preview.setEnabled(false);
            pickImages(!editing);
        } else if (id == R.id.img_vertical) {
            flipImageVertically();
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, R.string.design_manager_image_title_add_image));
        d(xB.b().a(getApplicationContext(), R.string.common_word_save));
        setContentView(R.layout.manage_image_add);
        Intent intent = getIntent();
        existingImages = intent.getParcelableArrayListExtra("images");
        sc_id = intent.getStringExtra("sc_id");
        dir_path = intent.getStringExtra("dir_path");
        image = intent.getParcelableExtra("edit_target");
        if (image != null) {
            editing = true;
        }
        layout_img_inform = findViewById(R.id.layout_img_inform);
        layout_img_modify = findViewById(R.id.layout_img_modify);
        chk_collection = findViewById(R.id.chk_collection);
        tv_desc = findViewById(R.id.tv_desc);
        tv_imgcnt = findViewById(R.id.tv_imgcnt);
        TextView tv_collection = findViewById(R.id.tv_collection);
        tv_add_photo = findViewById(R.id.tv_add_photo);
        preview = findViewById(R.id.img_selected);
        ImageView img_rotate = findViewById(R.id.img_rotate);
        ImageView img_vertical = findViewById(R.id.img_vertical);
        ImageView img_horizontal = findViewById(R.id.img_horizontal);
        ed_input = findViewById(R.id.ed_input);
        ed_input_edittext = ed_input.getEditText();
        ed_input_edittext.setPrivateImeOptions("defaultInputmode=english;");
        ed_input.setHint(xB.b().a(this, R.string.design_manager_image_hint_enter_image_name));
        O = new PB(this, ed_input.getTextInputLayout(), uq.b, getReservedImageNames());
        O.a(1);
        tv_collection.setText(xB.b().a(getApplicationContext(), R.string.design_manager_title_add_to_collection));
        tv_add_photo.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_title_add_image));
        preview.setOnClickListener(this);
        img_rotate.setOnClickListener(this);
        img_vertical.setOnClickListener(this);
        img_horizontal.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
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
            e(xB.b().a(this, R.string.design_manager_image_title_edit_image));
            imageRotationDegrees = image.rotate;
            imageScaleX = image.flipHorizontal;
            imageScaleY = image.flipVertical;
            O = new PB(this, ed_input.getTextInputLayout(), uq.b, getReservedImageNames(), image.resName);
            O.a(1);
            ed_input_edittext.setText(image.resName);
            ed_input_edittext.setEnabled(false);
            chk_collection.setEnabled(false);
            tv_add_photo.setVisibility(View.GONE);
            if (image.savedPos == 0) {
                setImageFromFile(a(image));
            } else {
                setImageFromFile(image.resFullName);
            }
        }
    }

    private ArrayList<String> getReservedImageNames() {
        var names = new ArrayList<String>();
        names.add("app_icon");
        for (var existingImage : existingImages) {
            names.add(existingImage.resName);
        }
        return names;
    }

    private void refreshPreview() {
        preview.setImageBitmap(iB.a(iB.a(iB.a(imageFilePath, 1024, 1024), imageExifOrientation), imageRotationDegrees, imageScaleX, imageScaleY));
    }

    private void save() {
        if (a(O)) {
            new Handler().postDelayed(() -> new SaveAsyncTask(getApplicationContext()).execute(), 500L);
        }
    }

    private void s() {
        if (tv_desc != null) {
            tv_desc.setVisibility(View.INVISIBLE);
        }
        if (layout_img_inform != null && layout_img_modify != null && tv_imgcnt != null) {
            layout_img_inform.setVisibility(View.GONE);
            layout_img_modify.setVisibility(View.VISIBLE);
            tv_imgcnt.setVisibility(View.GONE);
        }
    }

    private void pickImages(boolean allowMultiple) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (allowMultiple) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
            startActivityForResult(Intent.createChooser(intent, xB.b().a(this, R.string.common_word_choose)), 215);
        } catch (ActivityNotFoundException unused) {
            bB.b(this, xB.b().a(this, R.string.common_error_activity_not_found), bB.TOAST_NORMAL).show();
        }
    }

    private void setImageFromFile(String path) {
        imageFilePath = path;
        preview.setImageBitmap(iB.a(path, 1024, 1024));
        int indexOfFilenameExtension = path.lastIndexOf(".");
        if (path.endsWith(".9.png")) {
            indexOfFilenameExtension = path.lastIndexOf(".9.png");
        }
        if (ed_input_edittext != null && (ed_input_edittext.getText() == null || ed_input_edittext.getText().length() <= 0)) {
            ed_input_edittext.setText(path.substring(path.lastIndexOf("/") + 1, indexOfFilenameExtension));
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
        if (layout_img_inform == null || layout_img_modify == null || tv_imgcnt == null) {
            return;
        }
        layout_img_inform.setVisibility(View.VISIBLE);
        layout_img_modify.setVisibility(View.GONE);
        tv_imgcnt.setVisibility(View.VISIBLE);
        tv_imgcnt.setText("+ " + (count - 1) + " more");
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
        tv_desc.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }

    private void setImageFromUri(Uri uri) {
        String filePath;
        if (uri != null && (filePath = HB.a(this, uri)) != null) {
            setImageFromFile(filePath);
        }
    }

    private class SaveAsyncTask extends MA {
        public SaveAsyncTask(Context context) {
            super(context);
            AddImageActivity.this.a(this);
        }

        @Override
        public void a() {
            h();
            Intent intent = new Intent();
            intent.putExtra("sc_id", sc_id);
            if (editing) {
                intent.putExtra("image", image);
            } else {
                intent.putExtra("images", images);
            }
            setResult(RESULT_OK, intent);
            finish();
        }

        @Override
        public void b() {
            try {
                publishProgress("Now processing..");
                if (!multipleImagesPicked) {
                    if (!editing) {
                        var image = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                                ed_input_edittext.getText().toString().trim(), imageFilePath);
                        image.savedPos = 1;
                        image.isNew = true;
                        image.rotate = imageRotationDegrees;
                        image.flipVertical = imageScaleY;
                        image.flipHorizontal = imageScaleX;
                        if (chk_collection.isChecked()) {
                            Op.g().a(sc_id, image);
                        }
                        images.add(image);
                    } else if (!B) {
                        image.rotate = imageRotationDegrees;
                        image.flipHorizontal = imageScaleX;
                        image.flipVertical = imageScaleY;
                        image.isEdited = true;
                    } else {
                        image.resFullName = imageFilePath;
                        image.savedPos = 1;
                        image.rotate = imageRotationDegrees;
                        image.flipVertical = imageScaleY;
                        image.flipHorizontal = imageScaleX;
                        image.isEdited = true;
                    }
                } else {
                    var toAdd = new ArrayList<ProjectResourceBean>();
                    int i = 0;
                    while (i < pickedImageUris.size()) {
                        var uri = pickedImageUris.get(i);
                        var imageName = ed_input_edittext.getText().toString().trim() + "_" + ++i;
                        var imageFilePath = HB.a(getApplicationContext(), uri);
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
                    if (chk_collection.isChecked()) {
                        Op.g().a(sc_id, toAdd, true);
                    }
                    multipleImagesPicked = false;
                    images.addAll(toAdd);
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
                    var message = code != 0 ? xB.b().a(getApplicationContext(), code) : null;

                    var a = yy.a();
                    if (a == null && !a.isEmpty()) {
                        var names = "";
                        for (String name : a) {
                            if (!names.isEmpty()) {
                                names += ", ";
                            }
                            names += name;
                        }
                        message += "[" + names + "]";
                    }
                    throw new RuntimeException(new By(message));
                }
                e.printStackTrace();
                throw new RuntimeException(new By(e.getMessage()));
            }
        }

        @Override
        public void a(String str) {
            h();
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
}
