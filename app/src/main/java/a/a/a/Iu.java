package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.v;

import com.besome.sketch.beans.AdUnitBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.admob.AdmobActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

@SuppressLint({"ResourceType", "ViewConstructor"})
public class Iu extends LinearLayout implements Uu, OnClickListener {

    public RecyclerView a;
    public AdUnitsAdapter b;
    public ArrayList<AdUnitBean> adUnitBeanArrayList = new ArrayList<>();
    public AdmobActivity d;
    public TextView e;

    public Iu(AdmobActivity activity) {
        super(activity);
        d = activity;
        a(activity);
    }

    private void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(xB.b().a(getContext(), 2131625169));
        dialog.a(2131165298);
        View var2 = wB.a(getContext(), 2131427551);
        EditText var3 = var2.findViewById(2131231007);
        ((TextInputLayout) var2.findViewById(2131231825)).setHint(xB.b().a(getContext(), 2131625168));
        SB var4 = new SB(getContext(), var2.findViewById(2131231825), 1, 50);
        EditText var5 = var2.findViewById(2131230985);
        ((TextInputLayout) var2.findViewById(2131231801)).setHint(xB.b().a(getContext(), 2131625167));
        SB var6 = new SB(getContext(), var2.findViewById(2131231801), 1, 100);
        var3.setPrivateImeOptions("defaultInputmode=english;");
        dialog.a(var2);
        dialog.b(xB.b().a(getContext(), 2131624970), view -> {
            if (var4.b()) {
                var3.requestFocus();
            } else if (var6.b()) {
                var5.requestFocus();
            } else {
                String name = var3.getText().toString();
                String id = var5.getText().toString();
                adUnitBeanArrayList.add(new AdUnitBean(id, name));
                b.d(adUnitBeanArrayList.size() - 1);
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getContext(), 2131624974), view -> dialog.dismiss());
        dialog.show();
    }

    private void a(int position) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(xB.b().a(getContext(), 2131625172));
        dialog.a(2131165524);
        dialog.a(xB.b().a(getContext(), 2131625170));
        dialog.b(xB.b().a(getContext(), 2131624986), view -> {
            adUnitBeanArrayList.remove(position);
            b.e(position);
            bB.a(getContext(), xB.b().a(getContext(), 2131624935), 0).show();
            dialog.dismiss();
        });
        dialog.a(xB.b().a(getContext(), 2131624974), view -> dialog.dismiss());
        dialog.show();
    }

    private void a(Context var1) {
        wB.a(var1, this, 0x7f0b00cd);
        gB.b(this, 600, 200, null);
        ((TextView) findViewById(2131232042)).setText(xB.b().a(getContext(), 2131625162));
        findViewById(2131231372).setOnClickListener(this);
        e = findViewById(2131232285);

        a = findViewById(2131231443);
        a.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        b = new AdUnitsAdapter();
        a.setAdapter(b);
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        projectLibraryBean.adUnits = adUnitBeanArrayList;
    }

    @Override
    public String getDocUrl() {
        return "https://docs.sketchware.io/docs/admob-creating-ad-unit.html";
    }

    @Override
    public boolean isValid() {
        if (adUnitBeanArrayList.size() > 0) {
            return true;
        } else {
            bB.a(getContext(), xB.b().a(getContext(), 2131625183), 1).show();
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == 2131231372) {
            a();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void setData(ProjectLibraryBean var1) {
        adUnitBeanArrayList = var1.adUnits;
        b.c();
    }

    public class AdUnitsAdapter extends androidx.recyclerview.widget.RecyclerView.a<AdUnitsAdapter.ViewHolder> {

        public int c;

        public AdUnitsAdapter() {
            c = -1;
        }

        @Override
        public int a() {
            if (adUnitBeanArrayList.size() == 0) {
                e.setVisibility(View.VISIBLE);
            } else {
                e.setVisibility(View.GONE);
            }

            return adUnitBeanArrayList.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            AdUnitBean adUnitBean = adUnitBeanArrayList.get(position);
            viewHolder.t.setText(adUnitBean.name);
            viewHolder.u.setText(adUnitBean.id);
        }

        @Override
        public ViewHolder b(ViewGroup var1, int var2) {
            return new ViewHolder(LayoutInflater.from(var1.getContext()).inflate(2131427552, var1, false));
        }

        public class ViewHolder extends v {
            public TextView t;
            public TextView u;
            public ImageView v;

            public ViewHolder(View itemView) {
                super(itemView);
                t = itemView.findViewById(2131232055);
                u = itemView.findViewById(2131232261);
                v = itemView.findViewById(2131231132);
                v.setOnClickListener(view -> {
                    AdUnitsAdapter.this.c = j();
                    Iu.this.a(j());
                });
            }
        }
    }
}
