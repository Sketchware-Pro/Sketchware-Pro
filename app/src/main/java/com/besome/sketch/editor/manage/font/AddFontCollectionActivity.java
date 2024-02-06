package com.besome.sketch.editor.manage.font;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.SelectableBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;

import a.a.a.HB;
import a.a.a.Np;
import a.a.a.Nt;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yy;

public class AddFontCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    private TextView fontPreviewText;
    private CheckBox addToCollectionCheckBox;
    private boolean isFontLoaded;
    private ArrayList<ProjectResourceBean> projectResourceBeanArrayList;
    private ProjectResourceBean projectResourceBean;
    private String projectId;
    public int requestCode;
    private TextInputEditText inputEditText;
    private TextInputLayout inputLayout;
    private WB fontValidator;
    private ImageView addNewFontBtn;
    private boolean isEditMode = false;
    private Uri selectedFontUri = null;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.manage_font_add);

        e(getTranslatedString(R.string.design_manager_font_title_add_font));
        d(getTranslatedString(R.string.common_word_save));
        b(getTranslatedString(R.string.common_word_cancel));
        Intent intent = getIntent();
        projectId = intent.getStringExtra("sc_id");
        projectResourceBeanArrayList = intent.getParcelableArrayListExtra("fonts");
        requestCode = intent.getIntExtra("request_code", -1);
        projectResourceBean = intent.getParcelableExtra("edit_target");
        if (projectResourceBean != null) {
            isEditMode = true;
        }
        addToCollectionCheckBox = findViewById(R.id.add_to_collection_checkbox);
        addToCollectionCheckBox.setVisibility(View.GONE);
        inputEditText = findViewById(R.id.ed_input);
        inputLayout = findViewById(R.id.ti_input);
        addNewFontBtn = (ImageView) findViewById(R.id.select_file);
        fontPreviewText = (TextView) findViewById(R.id.font_preview);
        fontValidator = new WB(this, inputLayout, uq.b, getExistingFontNames());
        addNewFontBtn.setOnClickListener(new Nt(this));
        ((BaseDialogActivity) this).r.setOnClickListener(this);
        ((BaseDialogActivity) this).s.setOnClickListener(this);
        if (isEditMode) {
            e(getTranslatedString(R.string.design_manager_font_title_edit_font_name));
            fontValidator = new WB(this, inputLayout, uq.b, getExistingFontNames(), projectResourceBean.resName);
            inputEditText.setText(projectResourceBean.resName);
            fontPreviewText.setTypeface(Typeface.createFromFile(getFontFilePath(projectResourceBean)));
        }
    }

    public final ArrayList<String> getExistingFontNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : projectResourceBeanArrayList) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    public final void selectFontFile() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, getTranslatedString(R.string.common_word_choose)), 229);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Uri fontUri;
        super.onActivityResult(i, i2, intent);
        if (i == 229 && addNewFontBtn != null && i2 == -1 && (fontUri = intent.getData()) != null) {
            selectedFontUri = fontUri;
            try {
                String fontFilePath = HB.a(this, selectedFontUri);
                if (fontFilePath == null) {
                    return;
                }
                fontFilePath.substring(fontFilePath.lastIndexOf("."));
                isFontLoaded = true;
                fontPreviewText.setTypeface(Typeface.createFromFile(fontFilePath));
                if (inputEditText.getText() == null || inputEditText.getText().length() <= 0) {
                    int lastIndexOf = fontFilePath.lastIndexOf("/");
                    int lastIndexOf2 = fontFilePath.lastIndexOf(".");
                    if (lastIndexOf2 <= 0) {
                        lastIndexOf2 = fontFilePath.length();
                    }
                    inputEditText.setText(fontFilePath.substring(lastIndexOf + 1, lastIndexOf2));
                }
                fontPreviewText.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                isFontLoaded = false;
                fontPreviewText.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            saveFont();
        }
    }

    public void onResume() {
        super.onResume();
        ((BaseAppCompatActivity) this).d.setScreenName(AddFontCollectionActivity.class.getSimpleName().toString());
        ((BaseAppCompatActivity) this).d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final void saveFont() {
        Log.d("AddFontCollectionActivity", "saving font");
        if (validateFont(fontValidator) && !isEditMode) {
            Log.d("AddFontCollectionActivity", "saveFont: " + selectedFontUri);
            String fontName = inputEditText.getText().toString();
            String fontFilePath = HB.a(this, selectedFontUri);
            if (fontFilePath == null) {
                return;
            }
            ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, fontName, fontFilePath);
            ((SelectableBean) projectResourceBean).savedPos = 1;
            ((SelectableBean) projectResourceBean).isNew = true;
            try {
                Np.g().a(projectId, projectResourceBean);
                bB.a(this, getTranslatedString(R.string.design_manager_message_add_complete), 1).show();
            } catch (Exception e) {
                int errorCode = -1;
                // Well, (parts of) the bytecode's lying, yy can be thrown.
                //noinspection ConstantConditions
                if (e instanceof yy) {
                    String message = e.getMessage();
                    switch (message) {
                        case "fail_to_copy" -> errorCode = 2;
                        case "file_no_exist" -> errorCode = 1;
                        case "duplicate_name" -> errorCode = 0;
                    }
                    switch (errorCode) {
                        case 0 ->
                                bB.a(this, getTranslatedString(R.string.collection_duplicated_name), 1).show();
                        case 1 ->
                                bB.a(this, getTranslatedString(R.string.collection_no_exist_file), 1).show();
                        case 2 ->
                                bB.a(this, getTranslatedString(R.string.collection_failed_to_copy), 1).show();
                    }
                }
            }
        } else {
            Log.e("AddFontCollectionActivity", "saveFont: " + selectedFontUri);
            Np.g().a(projectResourceBean, inputEditText.getText().toString(), true);
            bB.a(this, getTranslatedString(R.string.design_manager_message_edit_complete), 1).show();
        }
        finish();
    }

    public static void startFontSelection(AddFontCollectionActivity addFontCollectionActivity) {
        addFontCollectionActivity.selectFontFile();
    }

    public boolean validateFont(WB wb) {
        if (wb.b()) {
            if ((!isFontLoaded || selectedFontUri == null) && !isEditMode) {
                addNewFontBtn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
                return false;
            }
            return true;
        }
        return false;
    }

    public final String getFontFilePath(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "font" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
    }
}
