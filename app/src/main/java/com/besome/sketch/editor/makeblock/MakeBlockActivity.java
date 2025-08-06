package com.besome.sketch.editor.makeblock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.jC;
import a.a.a.mB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class MakeBlockActivity extends BaseAppCompatActivity {

    private String sc_id;
    private ProjectFileBean project;
    private MoreBlockBuilderView makeBlock;

    private void goBackDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(Helper.getResString(R.string.logic_editor_more_block_dialog_message_confirm_goback));
        dialog.setIcon(R.drawable.exit_96);
        dialog.setMessage(Helper.getResString(R.string.logic_editor_more_block_dialog_description_goback));
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_goback), (v, which) -> {
            if (!mB.a()) {
                v.dismiss();
                finish();
            }

        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (makeBlock.a()) {
            super.onBackPressed();
        } else {
            goBackDialog();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isStoragePermissionGranted()) {
            finish();
        }

        setContentView(R.layout.make_block);
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
            project = getIntent().getParcelableExtra("project_file");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            project = savedInstanceState.getParcelable("project_file");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.logic_editor_more_block_actionbar_title_create_more_block));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        makeBlock = new MoreBlockBuilderView(this);
        makeBlock.setFuncNameValidator(jC.a(sc_id).a(project));
        LinearLayout makeBlock = findViewById(R.id.makeblock_view);
        makeBlock.addView(this.makeBlock);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moreblock_menu, menu);
        menu.findItem(R.id.moreblock_create).setTitle(Helper.getResString(R.string.common_word_create));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.moreblock_create) {
            if (makeBlock.a()) return false;

            if (makeBlock.b()) {
                Intent intent = new Intent();
                Pair<String, String> blockInformation = makeBlock.getBlockInformation();
                intent.putExtra("block_name", blockInformation.first);
                intent.putExtra("block_spec", blockInformation.second);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isStoragePermissionGranted()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        outState.putParcelable("project_file", project);
        super.onSaveInstanceState(outState);
    }
}