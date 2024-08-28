package com.besome.sketch.editor.manage.image;

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
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class AddImageCollectionActivity extends BaseDialogActivity implements View.OnClickListener {

    public TextView tv_collection;

    public TextView tv_add_photo;

    public ImageView img_rotate;

    public ImageView img_vertical;

    public ImageView img_horizontal;

    public ImageView preview;

    public PB imageNameValidator;

    public EditText ed_input_edittext;

    public EasyDeleteEditText ed_input;
    public int Q;
    public int R;

    public TextView tv_desc;

    public CheckBox chk_collection;

    public String sc_id;

    public ArrayList<ProjectResourceBean> images;
    public boolean t = false;

    public LinearLayout layout_img_inform = null;

    public LinearLayout layout_img_modify = null;

    public TextView tv_imgcnt = null;
    public boolean z = false;

    public String imageFilePath = null;

    public int imageRotationDegrees = 0;

    public int imageExifOrientation = 0;

    public int imageScaleY = 1;

    public int imageScaleX = 1;

    public boolean editing = false;

    public ProjectResourceBean editTarget = null;

    public final void flipImageHorizontally() {
        String imageFilePath = this.imageFilePath;
        if (imageFilePath == null || imageFilePath.length() <= 0) {
            return;
        }
        int imageRotationDegrees = this.imageRotationDegrees;
        if (imageRotationDegrees != 90 && imageRotationDegrees != 270) {
            this.imageScaleX *= -1;
        } else {
            this.imageScaleY *= -1;
        }
        refreshPreview();
    }

    public final void flipImageVertically() {
        String imageFilePath = this.imageFilePath;
        if (imageFilePath == null || imageFilePath.length() <= 0) {
            return;
        }
        int imageRotationDegrees = this.imageRotationDegrees;
        if (imageRotationDegrees != 90 && imageRotationDegrees != 270) {
            this.imageScaleY *= -1;
        } else {
            this.imageScaleX *= -1;
        }
        refreshPreview();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView preview;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 215 && (preview = this.preview) != null) {
            preview.setEnabled(true);
            if (resultCode == -1) {
                this.imageRotationDegrees = 0;
                this.imageScaleY = 1;
                this.imageScaleX = 1;
                this.z = true;
                setImageFromUri(data.getData());
                PB imageNameValidator = this.imageNameValidator;
                if (imageNameValidator != null) {
                    imageNameValidator.a(1);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mB.a()) {
            return;
        }
        switch (v.getId()) {
            case 2131230869:
                setResult(0);
                finish();
                return;
            case 2131230909:
                finish();
                return;
            case 2131230914:
                save();
                return;
            case 2131231150:
                flipImageHorizontally(); // R.id.img_horizontal
                return;
            case 2131231176:
                setImageRotation(this.imageRotationDegrees + 90);
                return;
            case 2131231181:
                this.preview.setEnabled(false);
                if (this.editing) {
                    return;
                }
                pickImage();
                return;
            case 2131231203:
                flipImageVertically(); // R.id.img_vertical
                return;
            default:
                return;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        e(xB.b().a(this, 2131625272));
        d(xB.b().a(getApplicationContext(), 2131625031));
        setContentView(2131427526);
        Intent intent = getIntent();
        this.images = intent.getParcelableArrayListExtra("images");
        this.sc_id = intent.getStringExtra("sc_id");
        this.editTarget = (ProjectResourceBean) intent.getParcelableExtra("edit_target");
        if (this.editTarget != null) {
            this.editing = true;
        }
        this.layout_img_inform = (LinearLayout) findViewById(2131231354);
        this.layout_img_modify = (LinearLayout) findViewById(2131231355);
        this.chk_collection = (CheckBox) findViewById(2131230887);
        this.chk_collection.setVisibility(8);
        this.tv_desc = (TextView) findViewById(2131231944);
        this.tv_imgcnt = (TextView) findViewById(2131232004);
        this.tv_collection = (TextView) findViewById(2131231913);
        this.tv_collection.setVisibility(8);
        this.tv_add_photo = (TextView) findViewById(2131231865);
        this.preview = (ImageView) findViewById(2131231181);
        this.img_rotate = (ImageView) findViewById(2131231176);
        this.img_vertical = (ImageView) findViewById(2131231203);
        this.img_horizontal = (ImageView) findViewById(2131231150);
        this.ed_input = (EasyDeleteEditText) findViewById(2131230990);
        this.ed_input_edittext = this.ed_input.getEditText();
        this.ed_input_edittext.setPrivateImeOptions("defaultInputmode=english;");
        this.ed_input.setHint(xB.b().a(this, 2131625268));
        this.imageNameValidator = new PB(this, this.ed_input.getTextInputLayout(), uq.b, getReservedImageNames());
        this.imageNameValidator.a(1);
        this.tv_add_photo.setText(xB.b().a(this, 2131625272));
        this.preview.setOnClickListener(this);
        this.img_rotate.setOnClickListener(this);
        this.img_vertical.setOnClickListener(this);
        this.img_horizontal.setOnClickListener(this);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        this.z = false;
        this.imageRotationDegrees = 0;
        this.imageScaleY = 1;
        this.imageScaleX = 1;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (this.editing) {
            this.editTarget.isEdited = true;
            e(xB.b().a(this, 2131625275));
            this.imageNameValidator = new PB(this, this.ed_input.getTextInputLayout(), uq.b, getReservedImageNames(), this.editTarget.resName);
            this.imageNameValidator.a(1);
            this.ed_input_edittext.setText(this.editTarget.resName);
            this.chk_collection.setVisibility(8);
            this.tv_collection.setVisibility(8);
            this.tv_add_photo.setVisibility(8);
            setImageFromFile(a(this.editTarget));
            this.layout_img_modify.setVisibility(8);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.d.setScreenName(AddImageCollectionActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final ArrayList<String> getReservedImageNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        Iterator<ProjectResourceBean> it = this.images.iterator();
        while (it.hasNext()) {
            names.add(it.next().resName);
        }
        return names;
    }

    public final void pickImage() {
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 2131624976)), 215);
        } catch (ActivityNotFoundException unused) {
            bB.b(this, xB.b().a(this, 2131624907), 0).show();
        }
    }

    public final void refreshPreview() {
        this.preview.setImageBitmap(iB.a(iB.a(iB.a(this.imageFilePath, 1024, 1024), this.imageExifOrientation), this.imageRotationDegrees, this.imageScaleX, this.imageScaleY));
    }

    public final void save() {
        if (a(this.imageNameValidator)) {
            new Handler().postDelayed(() -> {
                k();
                new SaveAsyncTask(getApplicationContext()).execute();
            }, 500L);
        }
    }

    public final void t() {
        TextView tv_desc = this.tv_desc;
        if (tv_desc != null) {
            tv_desc.setVisibility(4);
        }
        LinearLayout layout_img_inform = this.layout_img_inform;
        if (layout_img_inform == null || this.layout_img_modify == null || this.tv_imgcnt == null) {
            return;
        }
        layout_img_inform.setVisibility(8);
        this.layout_img_modify.setVisibility(0);
        this.tv_imgcnt.setVisibility(8);
    }

    public boolean a(PB validator) {
        if (!validator.b()) {
            return false;
        }
        if (this.z || this.imageFilePath != null) {
            return true;
        }
        this.tv_desc.startAnimation(AnimationUtils.loadAnimation(this, 2130771980));
        return false;
    }

    public final void setImageFromFile(String path) {
        this.imageFilePath = path;
        this.preview.setImageBitmap(iB.a(path, 1024, 1024));
        this.Q = path.lastIndexOf("/");
        this.R = path.lastIndexOf(".");
        if (path.endsWith(".9.png")) {
            this.R = path.lastIndexOf(".9.png");
        }
        EditText ed_input_edittext = this.ed_input_edittext;
        if (ed_input_edittext != null && (ed_input_edittext.getText() == null || this.ed_input_edittext.getText().length() <= 0)) {
            this.ed_input_edittext.setText(path.substring(this.Q + 1, this.R));
        }
        try {
            this.imageExifOrientation = iB.a(path);
            refreshPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        t();
    }

    public final void setImageRotation(int degrees) {
        String imageFilePath = this.imageFilePath;
        if (imageFilePath == null || imageFilePath.length() <= 0) {
            return;
        }
        this.imageRotationDegrees = degrees;
        if (this.imageRotationDegrees == 360) {
            this.imageRotationDegrees = 0;
        }
        refreshPreview();
    }

    class SaveAsyncTask extends MA {
        public SaveAsyncTask(Context context) {
            super(context);
            AddImageCollectionActivity.this.a(this);
        }

        @Override
        public void a() {
            if (AddImageCollectionActivity.this.editing) {
                bB.a(AddImageCollectionActivity.this.getApplicationContext(), xB.b().a(AddImageCollectionActivity.this.getApplicationContext(), 2131625279), 0).show();
            } else {
                bB.a(AddImageCollectionActivity.this.getApplicationContext(), xB.b().a(AddImageCollectionActivity.this.getApplicationContext(), 2131625276), 0).show();
            }
            AddImageCollectionActivity.this.h();
            AddImageCollectionActivity.this.finish();
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
                    if (AddImageCollectionActivity.this.editing) {
                        Op.g().a(AddImageCollectionActivity.this.editTarget, AddImageCollectionActivity.this.ed_input_edittext.getText().toString(), true);
                        return;
                    }
                    ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, AddImageCollectionActivity.this.ed_input_edittext.getText().toString().trim(), AddImageCollectionActivity.this.imageFilePath);
                    projectResourceBean.savedPos = 1;
                    projectResourceBean.isNew = true;
                    projectResourceBean.rotate = AddImageCollectionActivity.this.imageRotationDegrees;
                    projectResourceBean.flipVertical = AddImageCollectionActivity.this.imageScaleY;
                    projectResourceBean.flipHorizontal = AddImageCollectionActivity.this.imageScaleX;
                    try {
                        Op.g().a(AddImageCollectionActivity.this.sc_id, projectResourceBean);
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
                                a = xB.b().a(AddImageCollectionActivity.this.getApplicationContext(), 2131624903);
                            } else if (c != 1) {
                                a = c != 2 ? "" : xB.b().a(AddImageCollectionActivity.this.getApplicationContext(), 2131624904);
                            } else {
                                a = xB.b().a(AddImageCollectionActivity.this.getApplicationContext(), 2131624905);
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
            AddImageCollectionActivity.this.h();
        }
    }

    public final void setImageFromUri(Uri uri) {
        String filePath;
        if (uri == null || (filePath = HB.a(this, uri)) == null) {
            return;
        }
        setImageFromFile(filePath);
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
    }
}
