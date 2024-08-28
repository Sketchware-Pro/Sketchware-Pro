package com.besome.sketch.editor.manage.image;

import android.content.ActivityNotFoundException;
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
import com.google.android.gms.analytics.HitBuilders;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

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
import a.a.a.xB;
import a.a.a.yy;

public class AddImageCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    private TextView tv_collection;
    private TextView tv_add_photo;
    private ImageView preview;
    private PB imageNameValidator;
    private EditText ed_input_edittext;
    private EasyDeleteEditText ed_input;
    private TextView tv_desc;
    private CheckBox chk_collection;
    private String sc_id;
    private ArrayList<ProjectResourceBean> images;
    private LinearLayout layout_img_inform = null;
    private LinearLayout layout_img_modify = null;
    private TextView tv_imgcnt = null;
    private boolean z = false;
    private String imageFilePath = null;
    private int imageRotationDegrees = 0;
    private int imageExifOrientation = 0;
    private int imageScaleY = 1;
    private int imageScaleX = 1;
    private boolean editing = false;
    private ProjectResourceBean editTarget = null;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 215 && preview != null) {
            preview.setEnabled(true);
            if (resultCode == RESULT_OK) {
                imageRotationDegrees = 0;
                imageScaleY = 1;
                imageScaleX = 1;
                z = true;
                setImageFromUri(data.getData());
                if (imageNameValidator != null) {
                    imageNameValidator.a(1);
                }
            }
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
                preview.setEnabled(false);
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
        e(xB.b().a(this, R.string.design_manager_image_title_add_image));
        d(xB.b().a(getApplicationContext(), R.string.common_word_save));
        setContentView(R.layout.manage_image_add);
        Intent intent = getIntent();
        images = intent.getParcelableArrayListExtra("images");
        sc_id = intent.getStringExtra("sc_id");
        editTarget = intent.getParcelableExtra("edit_target");
        if (editTarget != null) {
            editing = true;
        }
        layout_img_inform = findViewById(R.id.layout_img_inform);
        layout_img_modify = findViewById(R.id.layout_img_modify);
        chk_collection = findViewById(R.id.chk_collection);
        chk_collection.setVisibility(View.GONE);
        tv_desc = findViewById(R.id.tv_desc);
        tv_imgcnt = findViewById(R.id.tv_imgcnt);
        tv_collection = findViewById(R.id.tv_collection);
        tv_collection.setVisibility(View.GONE);
        tv_add_photo = findViewById(R.id.tv_add_photo);
        preview = findViewById(R.id.img_selected);
        ImageView img_rotate = findViewById(R.id.img_rotate);
        ImageView img_vertical = findViewById(R.id.img_vertical);
        ImageView img_horizontal = findViewById(R.id.img_horizontal);
        ed_input = findViewById(R.id.ed_input);
        ed_input_edittext = ed_input.getEditText();
        ed_input_edittext.setPrivateImeOptions("defaultInputmode=english;");
        ed_input.setHint(xB.b().a(this, R.string.design_manager_image_hint_enter_image_name));
        imageNameValidator = new PB(this, ed_input.getTextInputLayout(), uq.b, getReservedImageNames());
        imageNameValidator.a(1);
        tv_add_photo.setText(xB.b().a(this, R.string.design_manager_image_title_add_image));
        preview.setOnClickListener(this);
        img_rotate.setOnClickListener(this);
        img_vertical.setOnClickListener(this);
        img_horizontal.setOnClickListener(this);
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
            e(xB.b().a(this, R.string.design_manager_image_title_edit_image_name));
            imageNameValidator = new PB(this, ed_input.getTextInputLayout(), uq.b, getReservedImageNames(), editTarget.resName);
            imageNameValidator.a(1);
            ed_input_edittext.setText(editTarget.resName);
            chk_collection.setVisibility(View.GONE);
            tv_collection.setVisibility(View.GONE);
            tv_add_photo.setVisibility(View.GONE);
            setImageFromFile(a(editTarget));
            layout_img_modify.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        d.setScreenName(AddImageCollectionActivity.class.getSimpleName());
        d.send(new HitBuilders.ScreenViewBuilder().build());
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
            startActivityForResult(Intent.createChooser(intent, xB.b().a(this, R.string.common_word_choose)), 215);
        } catch (ActivityNotFoundException unused) {
            bB.b(this, xB.b().a(this, R.string.common_error_activity_not_found), bB.TOAST_NORMAL).show();
        }
    }

    private void refreshPreview() {
        preview.setImageBitmap(iB.a(iB.a(iB.a(imageFilePath, 1024, 1024), imageExifOrientation), imageRotationDegrees, imageScaleX, imageScaleY));
    }

    private void save() {
        if (a(imageNameValidator)) {
            new Handler().postDelayed(() -> {
                k();
                new SaveAsyncTask(getApplicationContext()).execute();
            }, 500L);
        }
    }

    private void t() {
        if (tv_desc != null) {
            tv_desc.setVisibility(View.INVISIBLE);
        }
        if (layout_img_inform != null && layout_img_modify != null && tv_imgcnt != null) {
            layout_img_inform.setVisibility(View.GONE);
            layout_img_modify.setVisibility(View.VISIBLE);
            tv_imgcnt.setVisibility(View.GONE);
        }
    }

    private boolean a(PB validator) {
        if (!validator.b()) {
            return false;
        }
        if (z || imageFilePath != null) {
            return true;
        }
        tv_desc.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
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

    private class SaveAsyncTask extends MA {
        public SaveAsyncTask(Context context) {
            super(context);
            AddImageCollectionActivity.this.a(this);
        }

        @Override
        public void a() {
            if (editing) {
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
            } else {
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
            }
            h();
            finish();
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x00c4  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0105 A[Catch: all -> 0x0075, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0012, B:7:0x004b, B:12:0x005a, B:13:0x005b, B:18:0x0079, B:19:0x0085, B:21:0x0087, B:34:0x00ff, B:36:0x0105, B:38:0x010b, B:39:0x010f, B:41:0x0115, B:43:0x0121, B:45:0x0132, B:48:0x0142, B:49:0x015b, B:50:0x0160, B:51:0x00ca, B:52:0x00dc, B:53:0x00ee, B:54:0x00a1, B:57:0x00ab, B:60:0x00b5), top: B:2:0x0001, inners: #1, #3 }] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x0115 A[Catch: all -> 0x0075, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0012, B:7:0x004b, B:12:0x005a, B:13:0x005b, B:18:0x0079, B:19:0x0085, B:21:0x0087, B:34:0x00ff, B:36:0x0105, B:38:0x010b, B:39:0x010f, B:41:0x0115, B:43:0x0121, B:45:0x0132, B:48:0x0142, B:49:0x015b, B:50:0x0160, B:51:0x00ca, B:52:0x00dc, B:53:0x00ee, B:54:0x00a1, B:57:0x00ab, B:60:0x00b5), top: B:2:0x0001, inners: #1, #3 }] */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00ee A[Catch: all -> 0x0075, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0012, B:7:0x004b, B:12:0x005a, B:13:0x005b, B:18:0x0079, B:19:0x0085, B:21:0x0087, B:34:0x00ff, B:36:0x0105, B:38:0x010b, B:39:0x010f, B:41:0x0115, B:43:0x0121, B:45:0x0132, B:48:0x0142, B:49:0x015b, B:50:0x0160, B:51:0x00ca, B:52:0x00dc, B:53:0x00ee, B:54:0x00a1, B:57:0x00ab, B:60:0x00b5), top: B:2:0x0001, inners: #1, #3 }] */
        @Override
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void b() {
            char c;
            String str;
            String a;
            ArrayList<String> a2;
            Iterator<String> it;
            try {
                try {
                    publishProgress("Now processing..");
                    if (editing) {
                        Op.g().a(editTarget, ed_input_edittext.getText().toString(), true);
                        return;
                    }
                    ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, ed_input_edittext.getText().toString().trim(), imageFilePath);
                    projectResourceBean.savedPos = 1;
                    projectResourceBean.isNew = true;
                    projectResourceBean.rotate = imageRotationDegrees;
                    projectResourceBean.flipVertical = imageScaleY;
                    projectResourceBean.flipHorizontal = imageScaleX;
                    try {
                        Op.g().a(sc_id, projectResourceBean);
                    } catch (yy e) {
                        throw e;
                    }
                } catch (yy e2) {
                    String message = e2.getMessage();
                    int hashCode = message.hashCode();
                    if (hashCode == -2111590760) {
                        if (message.equals("fail_to_copy")) {
                            c = 2;
                            str = "";
                            if (c != 0) {
                            }
                            a2 = e2.a();
                            if (a2 != null) {
                            }
                            throw new By(a);
                        }
                        c = 65535;
                        str = "";
                        if (c != 0) {
                        }
                        a2 = e2.a();
                        if (a2 != null) {
                        }
                        throw new By(a);
                    }
                    if (hashCode != -1587253668) {
                        if (hashCode == -105163457 && message.equals("duplicate_name")) {
                            c = 0;
                            str = "";
                            if (c != 0) {
                                a = xB.b().a(getApplicationContext(), R.string.collection_duplicated_name);
                            } else if (c != 1) {
                                a = c != 2 ? "" : xB.b().a(getApplicationContext(), R.string.collection_failed_to_copy);
                            } else {
                                a = xB.b().a(getApplicationContext(), R.string.collection_no_exist_file);
                            }
                            a2 = e2.a();
                            if (a2 != null && a2.size() > 0) {
                                it = a2.iterator();
                                while (it.hasNext()) {
                                    String next = it.next();
                                    if (str.length() > 0) {
                                        str = str + ", ";
                                    }
                                    str = str + next;
                                }
                                a = a + "[" + str + "]";
                            }
                            throw new By(a);
                        }
                        c = 65535;
                        str = "";
                        if (c != 0) {
                        }
                        a2 = e2.a();
                        if (a2 != null) {
                            it = a2.iterator();
                            while (it.hasNext()) {
                            }
                            a = a + "[" + str + "]";
                        }
                        throw new By(a);
                    }
                    if (message.equals("file_no_exist")) {
                        c = 1;
                        str = "";
                        if (c != 0) {
                        }
                        a2 = e2.a();
                        if (a2 != null) {
                        }
                        throw new By(a);
                    }
                    c = 65535;
                    str = "";
                    if (c != 0) {
                    }
                    a2 = e2.a();
                    if (a2 != null) {
                    }
                    throw new By(a);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                throw new By(e3.getMessage());
            }
        }

        @Override
        public void a(String str) {
            h();
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
}
