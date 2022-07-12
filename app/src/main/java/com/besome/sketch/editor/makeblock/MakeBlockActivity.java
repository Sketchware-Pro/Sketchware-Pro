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

    private void goBackDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(0x7f0e061d));
        dialog.a(2131165604);
        dialog.a(Helper.getResString(2131625500));
        dialog.b(Helper.getResString(2131625000), view -> {
            if (!mB.a()) {
                dialog.dismiss();
                finish();
            }

        });
        dialog.a(Helper.getResString(2131624974), Helper.getDialogDismissListener(dialog));
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

        setContentView(2131427505);
        if (savedInstanceState == null) {
            l = getIntent().getStringExtra("sc_id");
            m = getIntent().getParcelableExtra("project_file");
        } else {
            l = savedInstanceState.getString("sc_id");
            m = savedInstanceState.getParcelable("project_file");
        }

        k = findViewById(2131231847);
        a(k);
        findViewById(2131231370).setVisibility(View.GONE);
        d().a(Helper.getResString(2131625498));
        d().e(true);
        d().d(true);
        k.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        o = new dt(this);
        o.setFuncNameValidator(jC.a(l).a(m));
        n = findViewById(2131231488);
        n.addView(o);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131492883, menu);
        menu.findItem(2131231546).setTitle(Helper.getResString(2131624983));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2131231546) {
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
