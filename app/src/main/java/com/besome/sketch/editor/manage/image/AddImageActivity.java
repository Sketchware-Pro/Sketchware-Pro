package com.besome.sketch.editor.manage.image;

import a.a.a.By;
import a.a.a.HB;
import a.a.a.MA;
import a.a.a.Op;
import a.a.a.PB;
import a.a.a._t;
import a.a.a.bB;
import a.a.a.iB;
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.xB;
import a.a.a.yy;
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
import com.google.android.gms.analytics.HitBuilders;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class AddImageActivity extends BaseDialogActivity implements View.OnClickListener {

    public ArrayList<ProjectResourceBean> existingImages;

    public TextView tv_collection;

    public TextView tv_add_photo;

    public ImageView img_rotate;

    public ImageView img_vertical;

    public ImageView img_horizontal;

    public ImageView preview;

    public ArrayList<Uri> pickedImageUris;
    public PB O;

    public EditText ed_input_edittext;

    public EasyDeleteEditText ed_input;

    public int imageFilePathLastIndexOfSlash;

    public int imageFilePathIndexOfFilenameExtension;

    public oB fileUtil;

    public TextView tv_desc;

    public CheckBox chk_collection;

    public String sc_id;

    public ArrayList<ProjectResourceBean> images;

    public boolean multipleImagesPicked = false;
    public boolean u = false;

    public LinearLayout layout_img_inform = null;

    public LinearLayout layout_img_modify = null;

    public TextView tv_imgcnt = null;
    public boolean B = false;

    public String imageFilePath = null;

    public int imageRotationDegrees = 0;

    public int imageExifOrientation = 0;

    public int imageScaleY = 1;

    public int imageScaleX = 1;

    public String dir_path = "";

    public boolean editing = false;

    public ProjectResourceBean image = null;

    public static void a(AddImageActivity addImageActivity) {
        addImageActivity.k();
    }

    public final void flipImageVertically() {
        String str = this.imageFilePath;
        if (str == null || str.length() <= 0) {
            return;
        }
        int i = this.imageRotationDegrees;
        if (i != 90 && i != 270) {
            this.imageScaleY *= -1;
        } else {
            this.imageScaleX *= -1;
        }
        refreshPreview();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 215 && (imageView = this.preview) != null) {
            imageView.setEnabled(true);
            if (resultCode == -1) {
                this.tv_add_photo.setVisibility(8);
                this.imageRotationDegrees = 0;
                this.imageScaleY = 1;
                this.imageScaleX = 1;
                if (data.getClipData() == null) {
                    this.B = true;
                    this.multipleImagesPicked = false;
                    setImageFromUri(data.getData());
                    PB pb = this.O;
                    if (pb != null) {
                        pb.a(1);
                        return;
                    }
                    return;
                }
                ClipData clipData = data.getClipData();
                if (clipData.getItemCount() == 1) {
                    this.B = true;
                    this.multipleImagesPicked = false;
                    setImageFromUri(clipData.getItemAt(0).getUri());
                    PB pb2 = this.O;
                    if (pb2 != null) {
                        pb2.a(1);
                        return;
                    }
                    return;
                }
                handleImagePickClipData(clipData);
                this.multipleImagesPicked = true;
                PB pb3 = this.O;
                if (pb3 != null) {
                    pb3.a(clipData.getItemCount());
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case 2131230869:
                setResult(0); // R.id.cancel_button
                finish();
                return;
            case 2131230909:
                finish(); // R.id.common_dialog_cancel_button
                return;
            case 2131230914:
                save(); // R.id.common_dialog_ok_button
                return;
            case 2131231150:
                flipImageHorizontally(); // R.id.img_horizontal
                return;
            case 2131231176:
                setImageRotation(this.imageRotationDegrees + 90); // R.id.img_rotate
                return;
            case 2131231181:
                this.preview.setEnabled(false); // R.id.img_selected
                if (this.editing) {
                    pickImages(false);
                    return;
                } else {
                    pickImages(true);
                    return;
                }
            case 2131231203:
                flipImageVertically(); // R.id.img_vertical
                return;
            default:
                return;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, 2131625272));
        d(xB.b().a(getApplicationContext(), 2131625031));
        setContentView(2131427526);
        Intent intent = getIntent();
        this.existingImages = intent.getParcelableArrayListExtra("images");
        this.sc_id = intent.getStringExtra("sc_id");
        this.dir_path = intent.getStringExtra("dir_path");
        this.image = (ProjectResourceBean) intent.getParcelableExtra("edit_target");
        if (this.image != null) {
            this.editing = true;
        }
        this.layout_img_inform = (LinearLayout) findViewById(2131231354);
        this.layout_img_modify = (LinearLayout) findViewById(2131231355);
        this.chk_collection = (CheckBox) findViewById(2131230887);
        this.tv_desc = (TextView) findViewById(2131231944);
        this.tv_imgcnt = (TextView) findViewById(2131232004);
        this.tv_collection = (TextView) findViewById(2131231913);
        this.tv_add_photo = (TextView) findViewById(2131231865);
        this.preview = (ImageView) findViewById(2131231181);
        this.img_rotate = (ImageView) findViewById(2131231176);
        this.img_vertical = (ImageView) findViewById(2131231203);
        this.img_horizontal = (ImageView) findViewById(2131231150);
        this.ed_input = (EasyDeleteEditText) findViewById(2131230990);
        this.ed_input_edittext = this.ed_input.getEditText();
        this.ed_input_edittext.setPrivateImeOptions("defaultInputmode=english;");
        this.ed_input.setHint(xB.b().a(this, 2131625268)); // R.string.design_manager_image_hint_enter_image_name
        this.O = new PB(this, this.ed_input.getTextInputLayout(), uq.b, getReservedImageNames());
        this.O.a(1);
        this.tv_collection.setText(xB.b().a(getApplicationContext(), 2131625289)); // R.string.design_manager_title_add_to_collection
        this.tv_add_photo.setText(xB.b().a(getApplicationContext(), 2131625272)); // R.string.design_manager_image_title_add_image
        this.preview.setOnClickListener(this);
        this.img_rotate.setOnClickListener(this);
        this.img_vertical.setOnClickListener(this);
        this.img_horizontal.setOnClickListener(this);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        this.B = false;
        this.imageRotationDegrees = 0;
        this.imageScaleY = 1;
        this.imageScaleX = 1;
        this.fileUtil = new oB();
        this.fileUtil.f(this.dir_path); // java.io.File.mkdirs
        this.images = new ArrayList<>();
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        if (this.editing) {
            this.image.isEdited = true;
            e(xB.b().a(this, 2131625274));
            ProjectResourceBean projectResourceBean = this.image;
            this.imageRotationDegrees = projectResourceBean.rotate;
            this.imageScaleX = projectResourceBean.flipHorizontal;
            this.imageScaleY = projectResourceBean.flipVertical;
            this.O = new PB(this, this.ed_input.getTextInputLayout(), uq.b, getReservedImageNames(), this.image.resName);
            this.O.a(1);
            this.ed_input_edittext.setText(this.image.resName);
            this.ed_input_edittext.setEnabled(false);
            this.chk_collection.setEnabled(false);
            this.tv_add_photo.setVisibility(8);
            ProjectResourceBean projectResourceBean2 = this.image;
            if (projectResourceBean2.savedPos == 0) {
                setImageFromFile(a(projectResourceBean2));
            } else {
                setImageFromFile(projectResourceBean2.resFullName);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.d.setScreenName(AddImageActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final ArrayList<String> getReservedImageNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.existingImages.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public final void refreshPreview() {
        this.preview.setImageBitmap(iB.a(iB.a(iB.a(this.imageFilePath, 1024, 1024), this.imageExifOrientation), this.imageRotationDegrees, this.imageScaleX, this.imageScaleY));
    }

    public final void save() {
        if (a(this.O)) {
            new Handler().postDelayed(new _t(this), 500L);
        }
    }

    public final void s() {
        TextView textView = this.tv_desc;
        if (textView != null) {
            textView.setVisibility(4);
        }
        LinearLayout linearLayout = this.layout_img_inform;
        if (linearLayout == null || this.layout_img_modify == null || this.tv_imgcnt == null) {
            return;
        }
        linearLayout.setVisibility(8);
        this.layout_img_modify.setVisibility(0);
        this.tv_imgcnt.setVisibility(8);
    }

    public final void pickImages(boolean allowMultiple) {
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (allowMultiple) {
                intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
            }
            startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 2131624976)), 215);
        } catch (ActivityNotFoundException unused) {
            bB.b(this, xB.b().a(this, 2131624907), 0).show();
        }
    }

    public final void setImageFromFile(String path) {
        this.imageFilePath = path;
        this.preview.setImageBitmap(iB.a(path, 1024, 1024));
        this.imageFilePathLastIndexOfSlash = path.lastIndexOf("/");
        this.imageFilePathIndexOfFilenameExtension = path.lastIndexOf(".");
        if (path.endsWith(".9.png")) {
            this.imageFilePathIndexOfFilenameExtension = path.lastIndexOf(".9.png");
        }
        EditText editText = this.ed_input_edittext;
        if (editText != null && (editText.getText() == null || this.ed_input_edittext.getText().length() <= 0)) {
            this.ed_input_edittext.setText(path.substring(this.imageFilePathLastIndexOfSlash + 1, this.imageFilePathIndexOfFilenameExtension));
        }
        try {
            this.imageExifOrientation = iB.a(path);
            refreshPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        s();
    }

    public final void setImageRotation(int i) {
        String str = this.imageFilePath;
        if (str == null || str.length() <= 0) {
            return;
        }
        this.imageRotationDegrees = i;
        if (this.imageRotationDegrees == 360) {
            this.imageRotationDegrees = 0;
        }
        refreshPreview();
    }

    public final void onMultipleImagesPicked(int count) {
        LinearLayout linearLayout = this.layout_img_inform;
        if (linearLayout == null || this.layout_img_modify == null || this.tv_imgcnt == null) {
            return;
        }
        linearLayout.setVisibility(0);
        this.layout_img_modify.setVisibility(8);
        this.tv_imgcnt.setVisibility(0);
        TextView textView = this.tv_imgcnt;
        StringBuilder sb = new StringBuilder();
        sb.append("+ ");
        sb.append(count - 1);
        sb.append(" more");
        textView.setText(sb.toString());
    }

    public final void flipImageHorizontally() {
        String str = this.imageFilePath;
        if (str == null || str.length() <= 0) {
            return;
        }
        int i = this.imageRotationDegrees;
        if (i != 90 && i != 270) {
            this.imageScaleX *= -1;
        } else {
            this.imageScaleY *= -1;
        }
        refreshPreview();
    }

    public boolean a(PB pb) {
        if (!pb.b()) {
            return false;
        }
        if (this.B || this.imageFilePath != null) {
            return true;
        }
        this.tv_desc.startAnimation(AnimationUtils.loadAnimation(this, 2130771980));
        return false;
    }

    public final void setImageFromUri(Uri uri) {
        String a;
        if (uri == null || (a = HB.a(this, uri)) == null) {
            return;
        }
        setImageFromFile(a);
    }

    class SaveAsyncTask extends MA {
        public SaveAsyncTask(Context context) {
            super(context);
            AddImageActivity.this.a(this);
        }

        @Override
        public void a() {
            AddImageActivity.this.h();
            Intent intent = new Intent();
            intent.putExtra("sc_id", AddImageActivity.this.sc_id);
            if (AddImageActivity.this.editing) {
                intent.putExtra("image", AddImageActivity.this.image);
            } else {
                intent.putExtra("images", AddImageActivity.this.images);
            }
            AddImageActivity.this.setResult(-1, intent);
            AddImageActivity.this.finish();
        }

        /* JADX WARN: Removed duplicated region for block: B:57:0x01f1  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0232 A[Catch: all -> 0x01a3, TryCatch #2 {, blocks: (B:4:0x0002, B:6:0x0013, B:8:0x001b, B:14:0x005e, B:10:0x006e, B:17:0x006d, B:18:0x0079, B:20:0x0081, B:22:0x00b5, B:24:0x00ff, B:25:0x0105, B:27:0x0111, B:29:0x0150, B:33:0x0169, B:42:0x0173, B:35:0x0183, B:36:0x018c, B:38:0x0192, B:45:0x0182, B:90:0x01a7, B:91:0x01b3, B:47:0x01b5, B:60:0x022c, B:62:0x0232, B:64:0x0238, B:65:0x023c, B:67:0x0242, B:69:0x024e, B:71:0x025f, B:74:0x026f, B:75:0x0288, B:76:0x028d, B:77:0x01f7, B:78:0x0209, B:79:0x021b, B:80:0x01cf, B:83:0x01d8, B:86:0x01e2), top: B:2:0x0002, inners: #1, #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0242 A[Catch: all -> 0x01a3, TryCatch #2 {, blocks: (B:4:0x0002, B:6:0x0013, B:8:0x001b, B:14:0x005e, B:10:0x006e, B:17:0x006d, B:18:0x0079, B:20:0x0081, B:22:0x00b5, B:24:0x00ff, B:25:0x0105, B:27:0x0111, B:29:0x0150, B:33:0x0169, B:42:0x0173, B:35:0x0183, B:36:0x018c, B:38:0x0192, B:45:0x0182, B:90:0x01a7, B:91:0x01b3, B:47:0x01b5, B:60:0x022c, B:62:0x0232, B:64:0x0238, B:65:0x023c, B:67:0x0242, B:69:0x024e, B:71:0x025f, B:74:0x026f, B:75:0x0288, B:76:0x028d, B:77:0x01f7, B:78:0x0209, B:79:0x021b, B:80:0x01cf, B:83:0x01d8, B:86:0x01e2), top: B:2:0x0002, inners: #1, #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:79:0x021b A[Catch: all -> 0x01a3, TryCatch #2 {, blocks: (B:4:0x0002, B:6:0x0013, B:8:0x001b, B:14:0x005e, B:10:0x006e, B:17:0x006d, B:18:0x0079, B:20:0x0081, B:22:0x00b5, B:24:0x00ff, B:25:0x0105, B:27:0x0111, B:29:0x0150, B:33:0x0169, B:42:0x0173, B:35:0x0183, B:36:0x018c, B:38:0x0192, B:45:0x0182, B:90:0x01a7, B:91:0x01b3, B:47:0x01b5, B:60:0x022c, B:62:0x0232, B:64:0x0238, B:65:0x023c, B:67:0x0242, B:69:0x024e, B:71:0x025f, B:74:0x026f, B:75:0x0288, B:76:0x028d, B:77:0x01f7, B:78:0x0209, B:79:0x021b, B:80:0x01cf, B:83:0x01d8, B:86:0x01e2), top: B:2:0x0002, inners: #1, #4 }] */
        @Override
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void b() {
            String str;
            String a;
            ArrayList<String> a2;
            Iterator<String> it;
            char c = 0;
            try {
                try {
                    publishProgress("Now processing..");
                    if (!AddImageActivity.this.multipleImagesPicked) {
                        if (AddImageActivity.this.editing) {
                            if (!AddImageActivity.this.B) {
                                AddImageActivity.this.image.rotate = AddImageActivity.this.imageRotationDegrees;
                                AddImageActivity.this.image.flipHorizontal = AddImageActivity.this.imageScaleX;
                                AddImageActivity.this.image.flipVertical = AddImageActivity.this.imageScaleY;
                                AddImageActivity.this.image.isEdited = true;
                                return;
                            }
                            AddImageActivity.this.image.resFullName = AddImageActivity.this.imageFilePath;
                            AddImageActivity.this.image.savedPos = 1;
                            AddImageActivity.this.image.rotate = AddImageActivity.this.imageRotationDegrees;
                            AddImageActivity.this.image.flipVertical = AddImageActivity.this.imageScaleY;
                            AddImageActivity.this.image.flipHorizontal = AddImageActivity.this.imageScaleX;
                            AddImageActivity.this.image.isEdited = true;
                            return;
                        }
                        ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, AddImageActivity.this.ed_input_edittext.getText().toString().trim(), AddImageActivity.this.imageFilePath);
                        projectResourceBean.savedPos = 1;
                        projectResourceBean.isNew = true;
                        projectResourceBean.rotate = AddImageActivity.this.imageRotationDegrees;
                        projectResourceBean.flipVertical = AddImageActivity.this.imageScaleY;
                        projectResourceBean.flipHorizontal = AddImageActivity.this.imageScaleX;
                        if (AddImageActivity.this.chk_collection.isChecked()) {
                            try {
                                Op.g().a(AddImageActivity.this.sc_id, projectResourceBean);
                            } catch (yy e) {
                                throw e;
                            }
                        }
                        AddImageActivity.this.images.add(projectResourceBean);
                        return;
                    }
                    ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
                    int i = 0;
                    while (i < AddImageActivity.this.pickedImageUris.size()) {
                        Uri uri = (Uri) AddImageActivity.this.pickedImageUris.get(i);
                        StringBuilder sb = new StringBuilder();
                        sb.append(AddImageActivity.this.ed_input_edittext.getText().toString().trim());
                        sb.append("_");
                        i++;
                        sb.append(i);
                        String sb2 = sb.toString();
                        String a3 = HB.a(AddImageActivity.this.getApplicationContext(), uri);
                        if (a3 == null) {
                            return;
                        }
                        ProjectResourceBean projectResourceBean2 = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, sb2, a3);
                        projectResourceBean2.savedPos = 1;
                        projectResourceBean2.isNew = true;
                        projectResourceBean2.rotate = iB.a(a3);
                        projectResourceBean2.flipVertical = 1;
                        projectResourceBean2.flipHorizontal = 1;
                        arrayList.add(projectResourceBean2);
                    }
                    if (AddImageActivity.this.chk_collection.isChecked()) {
                        try {
                            Op.g().a(AddImageActivity.this.sc_id, arrayList, true);
                        } catch (yy e2) {
                            throw e2;
                        }
                    }
                    AddImageActivity.this.multipleImagesPicked = false;
                    Iterator<ProjectResourceBean> it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        AddImageActivity.this.images.add(it2.next());
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    throw new By(e3.getMessage());
                }
            } catch (yy e4) {
                String message = e4.getMessage();
                int hashCode = message.hashCode();
                if (hashCode == -2111590760) {
                    if (message.equals("fail_to_copy")) {
                        c = 2;
                        str = "";
                        if (c != 0) {
                        }
                        a2 = e4.a();
                        if (a2 != null) {
                        }
                        throw new By(a);
                    }
                    c = 65535;
                    str = "";
                    if (c != 0) {
                    }
                    a2 = e4.a();
                    if (a2 != null) {
                    }
                    throw new By(a);
                }
                if (hashCode != -1587253668) {
                    if (hashCode == -105163457 && message.equals("duplicate_name")) {
                        str = "";
                        if (c != 0) {
                            a = xB.b().a(AddImageActivity.this.getApplicationContext(), 2131624903);
                        } else if (c != 1) {
                            a = c != 2 ? "" : xB.b().a(AddImageActivity.this.getApplicationContext(), 2131624904);
                        } else {
                            a = xB.b().a(AddImageActivity.this.getApplicationContext(), 2131624905);
                        }
                        a2 = e4.a();
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
                    a2 = e4.a();
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
                    a2 = e4.a();
                    if (a2 != null) {
                    }
                    throw new By(a);
                }
                c = 65535;
                str = "";
                if (c != 0) {
                }
                a2 = e4.a();
                if (a2 != null) {
                }
                throw new By(a);
            }
        }

        @Override
        public void a(String str) {
            AddImageActivity.this.h();
        }
    }

    public final void handleImagePickClipData(ClipData clipData) {
        if (clipData != null) {
            this.pickedImageUris = new ArrayList<>();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                if (i == 0) {
                    setImageFromUri(clipData.getItemAt(i).getUri());
                }
                this.pickedImageUris.add(clipData.getItemAt(i).getUri());
            }
            onMultipleImagesPicked(clipData.getItemCount());
        }
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return this.dir_path + File.separator + projectResourceBean.resFullName;
    }
}
