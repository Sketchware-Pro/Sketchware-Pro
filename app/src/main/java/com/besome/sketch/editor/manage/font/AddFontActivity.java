package com.besome.sketch.editor.manage.font;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.Np;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.yy;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;

public class AddFontActivity extends BaseDialogActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_FONT_PICKER = 229;

    private MaterialCardView fontPreviewView;
    private TextView fontPreviewText;
    private CheckBox addOrAddedToCollection;
    private Uri fontUri = null;
    private boolean validFontPicked;
    private String sc_id;
    private TextInputEditText fontName;
    private TextInputLayout fontNameInputLayout;
    private WB fontNameValidator;
    private MaterialButton selectFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        e(Helper.getResString(R.string.design_manager_font_title_add_font));
        d(Helper.getResString(R.string.common_word_save));
        b(Helper.getResString(R.string.common_word_cancel));
        setContentView(R.layout.manage_font_add);

        Intent intent = getIntent();
        sc_id = intent.getStringExtra("sc_id");
        addOrAddedToCollection = findViewById(R.id.add_to_collection_checkbox);
        selectFile = findViewById(R.id.select_file);
        fontPreviewText = findViewById(R.id.font_preview_txt);
        fontPreviewView = findViewById(R.id.font_preview_view);
        fontName = findViewById(R.id.ed_input);
        fontNameInputLayout = findViewById(R.id.ti_input);
        fontNameValidator = new WB(this, fontNameInputLayout, uq.b, intent.getStringArrayListExtra("font_names"));
        selectFile.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        if (intent.getIntExtra("request_code", -1) == 272) {
            e(Helper.getResString(R.string.design_manager_font_title_edit_font));
            fontNameValidator = new WB(this, fontNameInputLayout, uq.b, new ArrayList<>());
            fontName.setText(((ProjectResourceBean) intent.getParcelableExtra("resource_bean")).resName);
            fontName.setEnabled(false);
            addOrAddedToCollection.setEnabled(false);
        }
    }


    private void saveFont() {
        if (isFontValid(fontNameValidator)) {
            String fontName = this.fontName.getText().toString();
            String pickedFontFilePath = fontUri.getPath();
            ProjectResourceBean resourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, fontName, pickedFontFilePath);
            resourceBean.savedPos = 1;
            resourceBean.isNew = true;

            if (addOrAddedToCollection.isChecked()) {
                try {
                    Np.g().a(sc_id, resourceBean);
                } catch (Exception e) {
                    Log.e("AddFontActivity", "Failed to add font to collection", e);
                    // Well, (parts of) the bytecode's lying, yy can be thrown.
                    //noinspection ConstantConditions
                    if (e instanceof yy) {
                        switch (e.getMessage()) {
                            case "duplicate_name" ->
                                    bB.b(this, Helper.getResString(R.string.collection_duplicated_name), Toast.LENGTH_LONG).show();
                            case "file_no_exist" ->
                                    bB.b(this, Helper.getResString(R.string.collection_no_exist_file), Toast.LENGTH_LONG).show();
                            case "fail_to_copy" ->
                                    bB.b(this, Helper.getResString(R.string.collection_failed_to_copy), Toast.LENGTH_LONG).show();
                            default -> {
                            }
                        }
                    } else {
                        throw e;
                    }
                }
            } else {
                Intent intent = new Intent();
                intent.putExtra("resource_bean", resourceBean);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FONT_PICKER && resultCode == RESULT_OK) {
            Uri intentData = data.getData();

            String filenameExtension = FileUtil.getFileExtension(SketchwareUtil.getSafDocumentDisplayName(intentData).orElse(".ttf"));
            SketchwareUtil.copySafDocumentToTempFile(intentData, this, filenameExtension, tempFontFile -> {
                fontUri = Uri.fromFile(tempFontFile);
                try {
                    Typeface typeface = Typeface.createFromFile(tempFontFile);
                    if (typeface.equals(Typeface.DEFAULT)) {
                        SketchwareUtil.toastError("Warning: Font doesn't seem to be valid");
                        return;
                    }
                    validFontPicked = true;
                    String extractedFontName = SketchwareUtil.getSafDocumentDisplayName(intentData).orElse("invalid.tff").toLowerCase();
                    extractedFontName = extractedFontName.replaceAll("[^a-z0-9]", "").replace("ttf", "");

                    fontPreviewText.setTypeface(typeface);
                    fontName.requestFocus();
                    fontPreviewView.setVisibility(View.VISIBLE);
                    fontName.setText(extractedFontName);
                } catch (Exception e) {
                    validFontPicked = false;
                    fontPreviewView.setVisibility(View.GONE);
                    SketchwareUtil.toast("Couldn't load font: " + e.getMessage());
                    LogUtil.e("AddFontActivity", "Failed to load font", e);
                }
            }, e -> {
                SketchwareUtil.toastError("Error while loading font: " + e.getMessage());
                LogUtil.e("AddFontActivity", "Failed to load font", e);
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            saveFont();
        } else if (id == R.id.select_file) {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, Helper.getResString(R.string.common_word_choose)), REQUEST_CODE_FONT_PICKER);
            }
        }
    }


    private boolean isFontValid(WB wb) {
        if (!wb.b()) {
            return false;
        }
        if (validFontPicked && fontUri != null) {
            return true;
        }
        selectFile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
        return false;
    }
}
