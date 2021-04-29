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
import com.sketchware.remod.Resources;

import a.a.a.aB;
import a.a.a.dt;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class MakeBlockActivity extends BaseAppCompatActivity {

    public Toolbar k;
    public String l;
    public ProjectFileBean m;
    public LinearLayout n;
    public dt o;

    public final void l() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), Resources.string.logic_editor_more_block_dialog_message_confirm_goback));
        aBVar.a(Resources.drawable.exit_96);
        aBVar.a(xB.b().a(getApplicationContext(), Resources.string.logic_editor_more_block_dialog_description_goback));
        aBVar.b(xB.b().a(getApplicationContext(), Resources.string.common_word_goback), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    aBVar.dismiss();
                    finish();
                }
            }
        });
        aBVar.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    @Override
    public void onBackPressed() {
        if (o.a()) {
            super.onBackPressed();
        } else {
            l();
        }
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!super.j()) {
            finish();
        }
        setContentView(Resources.layout.make_block);
        if (bundle == null) {
            l = getIntent().getStringExtra("sc_id");
            m = getIntent().getParcelableExtra("project_file");
        } else {
            l = bundle.getString("sc_id");
            m = bundle.getParcelable("project_file");
        }
        k = findViewById(Resources.id.toolbar);
        a(k);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(),
                Resources.string.logic_editor_more_block_actionbar_title_create_more_block));
        d().e(true);
        d().d(true);
        /* To save an anonymous class */
        k.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        o = new dt(this);
        o.setFuncNameValidator(jC.a(l).a(m));
        n = findViewById(Resources.id.makeblock_view);
        n.addView(o);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(Resources.menu.moreblock_menu, menu);
        menu.findItem(Resources.id.moreblock_create)
                .setTitle(xB.b().a(getApplicationContext(), Resources.string.common_word_create));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == Resources.id.moreblock_create) {
            if (o.a()) {
                return false;
            }
            if (o.b()) {
                Intent intent = new Intent();
                Pair<String, String> blockInformation = o.getBlockInformation();
                intent.putExtra("block_name", blockInformation.first);
                intent.putExtra("block_spec", blockInformation.second);
                setResult(-1, intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sc_id", l);
        savedInstanceState.putParcelable("project_file", m);
        super.onSaveInstanceState(savedInstanceState);
    }
}
