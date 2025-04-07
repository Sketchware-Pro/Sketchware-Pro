package com.besome.sketch.editor.manage.font;

import static mod.hey.studios.util.Helper.addBasicTextChangedListener;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;

import a.a.a.Np;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.yy;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageFontAddBinding;
import pro.sketchware.lib.validator.FontNameValidator;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class AddFontActivity extends BaseDialogActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_FONT_PICKER = 229;

    private Uri fontUri = null;
    private boolean validFontPicked;
    private boolean fromCollectionPage;
    private String sc_id;
    private FontNameValidator fontNameValidator;

    private ManageFontAddBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageFontAddBinding.inflate(getLayoutInflater());
        e(Helper.getResString(R.string.design_manager_font_title_add_font));
        d(Helper.getResString(R.string.common_word_save));
        b(Helper.getResString(R.string.common_word_cancel));
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        sc_id = intent.getStringExtra("sc_id");
        if (intent.getBooleanExtra("add_to_collection", false)) {
            fromCollectionPage = true;
            binding.addToCollectionCheckbox.setChecked(true);
            binding.addToCollectionCheckbox.setEnabled(false);
        }
        binding.selectFile.setOnClickListener(this);
        binding.clearInput.setOnClickListener(v -> binding.edInput.setText(""));
        r.setOnClickListener(this);
        s.setOnClickListener(this);

        fontNameValidator = new FontNameValidator(this, binding.tiInput, uq.b, intent.getStringArrayListExtra("font_names"));
        if (intent.getIntExtra("request_code", -1) == 272) {
            e(Helper.getResString(R.string.design_manager_font_title_edit_font));
            binding.edInput.setText(((ProjectResourceBean) intent.getParcelableExtra("resource_bean")).resName);
            binding.addToCollectionCheckbox.setEnabled(false);
        }
        addBasicTextChangedListener(binding.edInput, str -> {
            binding.buttonsHolder.animate().translationY(binding.tiInput.getError() != null ? 40 : 0);
            binding.clearInput.setVisibility(str.isEmpty() ? View.GONE : View.VISIBLE);
        });
        binding.selectFile.setOnClickListener(view -> {
            if (!mB.a()) {
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("*/*");
                startActivityForResult(Intent.createChooser(intent1, Helper.getResString(R.string.common_word_choose)), REQUEST_CODE_FONT_PICKER);
            }
        });
    }

    private void saveFont() {
        if (isFontValid(fontNameValidator)) {
            String fontName = Helper.getText(binding.edInput);
            String pickedFontFilePath = fontUri.getPath();
            ProjectResourceBean resourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, fontName, pickedFontFilePath);
            resourceBean.savedPos = 1;
            resourceBean.isNew = true;

            if (binding.addToCollectionCheckbox.isChecked()) {
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
            }
            if (!fromCollectionPage) {
                Intent intent = new Intent();
                intent.putExtra("resource_bean", resourceBean);
                setResult(RESULT_OK, intent);
            }
            finish();
        } else {
            bB.b(this, Helper.getResString(R.string.design_manager_message_no_font_selected), Toast.LENGTH_LONG).show();
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

                    binding.edInput.requestFocus();
                    binding.fontPreviewView.setVisibility(View.VISIBLE);
                    binding.fontPreviewTxt.setTypeface(typeface);
                    binding.edInput.setText(extractedFontName);
                } catch (Exception e) {
                    e.printStackTrace();
                    validFontPicked = false;
                    binding.fontPreviewView.setVisibility(View.GONE);
                    SketchwareUtil.toast("Couldn't load font: " + e.getMessage());
                    LogUtil.e("AddFontActivity", "Failed to load font", e);
                }
            }, e -> {
                SketchwareUtil.toastError("Error while loading font: " + e.getMessage());
                e.printStackTrace();
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
        }
    }

    private boolean isFontValid(FontNameValidator wb) {
        if (wb != null && !wb.b()) {
            return false;
        }
        return validFontPicked && fontUri != null;
    }
}
