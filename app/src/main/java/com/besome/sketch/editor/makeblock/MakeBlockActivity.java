package com.besome.sketch.editor.makeblock;

import a.a.a.Xs;
import a.a.a.Ys;
import a.a.a.Zs;
import a.a.a.aB;
import a.a.a.dt;
import a.a.a.jC;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;

public class MakeBlockActivity extends BaseAppCompatActivity {
    public Toolbar k;
    public String l;
    public ProjectFileBean m;
    public LinearLayout n;
    public dt o;

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.besome.sketch.editor.makeblock.MakeBlockActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public final void l() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), 2131625501));
        aBVar.a(2131165604);
        aBVar.a(xB.b().a(getApplicationContext(), 2131625500));
        aBVar.b(xB.b().a(getApplicationContext(), 2131625000), new Ys(this, aBVar));
        aBVar.a(xB.b().a(getApplicationContext(), 2131624974), new Zs(this, aBVar));
        aBVar.show();
    }

    public void onBackPressed() {
        if (this.o.a()) {
            MakeBlockActivity.super.onBackPressed();
        } else {
            l();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.besome.sketch.editor.makeblock.MakeBlockActivity */
    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint("ResourceType")
    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!super.j()) {
            finish();
        }
        setContentView(2131427505);
        if (bundle == null) {
            this.l = getIntent().getStringExtra("sc_id");
            this.m = (ProjectFileBean) getIntent().getParcelableExtra("project_file");
        } else {
            this.l = bundle.getString("sc_id");
            this.m = (ProjectFileBean) bundle.getParcelable("project_file");
        }
        this.k = findViewById(2131231847);
        a(this.k);
        findViewById(2131231370).setVisibility(8);
        d().a(xB.b().a(getApplicationContext(), 2131625498));
        d().e(true);
        d().d(true);
        this.k.setNavigationOnClickListener(new Xs(this));
        this.o = new dt(this);
        this.o.setFuncNameValidator(jC.a(this.l).a(this.m));
        this.n = (LinearLayout) findViewById(2131231488);
        this.n.addView(this.o);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.besome.sketch.editor.makeblock.MakeBlockActivity */
    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint("ResourceType")
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131492883, menu);
        menu.findItem(2131231546).setTitle(xB.b().a(getApplicationContext(), 2131624983));
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.besome.sketch.editor.makeblock.MakeBlockActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2131231546) {
            if (this.o.a()) {
                return false;
            }
            if (this.o.b()) {
                Intent intent = new Intent();
                Pair blockInformation = this.o.getBlockInformation();
                intent.putExtra("block_name", (String) blockInformation.first);
                intent.putExtra("block_spec", (String) blockInformation.second);
                setResult(-1, intent);
                finish();
                return true;
            }
        }
        return MakeBlockActivity.super.onOptionsItemSelected(menuItem);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.besome.sketch.editor.makeblock.MakeBlockActivity */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.l);
        bundle.putParcelable("project_file", this.m);
        MakeBlockActivity.super.onSaveInstanceState(bundle);
    }
}
