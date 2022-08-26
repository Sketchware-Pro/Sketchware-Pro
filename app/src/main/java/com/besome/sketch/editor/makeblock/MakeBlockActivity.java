package com.besome.sketch.editor.makeblock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;

import a.a.a.aB;
import a.a.a.dt;
import a.a.a.jC;
import a.a.a.mB;
import mod.hey.studios.util.Helper;

public class MakeBlockActivity extends BaseAppCompatActivity {

    public Toolbar k;
    public String l;
    public ProjectFileBean m;
    public LinearLayout n;
    public dt o;

    private void goBackDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.logic_editor_more_block_dialog_message_confirm_goback));
        dialog.a(R.drawable.exit_96);
        dialog.a(Helper.getResString(R.string.logic_editor_more_block_dialog_description_goback));
        dialog.b(Helper.getResString(R.string.common_word_goback), view -> {
            if (!mB.a()) {
                dialog.dismiss();
                finish();
            }

        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (o.a()) {
            super.onBackPressed();
        } else {
            goBackDialog();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }

        setContentView(R.layout.make_block);
        if (savedInstanceState == null) {
            l = getIntent().getStringExtra("sc_id");
            m = getIntent().getParcelableExtra("project_file");
        } else {
            l = savedInstanceState.getString("sc_id");
            m = savedInstanceState.getParcelable("project_file");
        }

        k = findViewById(R.id.toolbar);
        a(k);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(Helper.getResString(R.string.logic_editor_more_block_actionbar_title_create_more_block));
        d().e(true);
        d().d(true);
        k.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        o = new dt(this);
        o.setFuncNameValidator(jC.a(l).a(m));
        n = findViewById(R.id.makeblock_view);
        n.addView(o);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moreblock_menu, menu);
        menu.findItem(R.id.moreblock_create).setTitle(Helper.getResString(R.string.common_word_create));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.moreblock_create) {
            if (o.a()) return false;

            if (o.b()) {
                Intent intent = new Intent();
                Pair<String, String> blockInformation = o.getBlockInformation();
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
        if (!super.j()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", l);
        outState.putParcelable("project_file", m);
        super.onSaveInstanceState(outState);
    }
}
