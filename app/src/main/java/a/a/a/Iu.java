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
import com.sketchware.remod.R;

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
        initialize(activity);
    }

    private void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(xB.b().a(getContext(), R.string.design_library_admob_dialog_add_adunit_title));
        dialog.a(R.drawable.add_96_blue);
        View var2 = wB.a(getContext(), R.layout.manage_library_setting_admob_adunit_add);
        EditText var3 = var2.findViewById(R.id.ed_name);
        ((TextInputLayout) var2.findViewById(R.id.ti_name)).setHint(xB.b().a(getContext(), R.string.design_library_admob_dialog_add_adunit_hint_adunit_name));
        SB var4 = new SB(getContext(), var2.findViewById(R.id.ti_name), 1, 50);
        EditText var5 = var2.findViewById(R.id.ed_adunit_id);
        ((TextInputLayout) var2.findViewById(R.id.ti_adunit_id)).setHint(xB.b().a(getContext(), R.string.design_library_admob_dialog_add_adunit_hint_adunit_id));
        SB var6 = new SB(getContext(), var2.findViewById(R.string.design_library_admob_dialog_add_adunit_hint_adunit_id), 1, 100);
        var3.setPrivateImeOptions("defaultInputmode=english;");
        dialog.a(var2);
        dialog.b(xB.b().a(getContext(), R.string.common_word_add), view -> {
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
        dialog.a(xB.b().a(getContext(), R.string.common_word_cancel), view -> dialog.dismiss());
        dialog.show();
    }

    private void a(int position) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(xB.b().a(getContext(), R.string.design_library_admob_dialog_delete_adunit_title));
        dialog.a(R.drawable.delete_96);
        dialog.a(xB.b().a(getContext(), R.string.design_library_admob_dialog_confirm_delete_adunit));
        dialog.b(xB.b().a(getContext(), R.string.common_word_delete), view -> {
            adUnitBeanArrayList.remove(position);
            b.e(position);
            bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_delete), 0).show();
            dialog.dismiss();
        });
        dialog.a(xB.b().a(getContext(), R.string.common_word_cancel), view -> dialog.dismiss());
        dialog.show();
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.manage_library_admob_listing);
        gB.b(this, 600, 200, null);
        ((TextView) findViewById(R.id.tv_manual_add_ad_unit)).setText(xB.b().a(getContext(), R.string.design_library_admob_button_manual_add_to_adunit));
        findViewById(R.id.layout_manual_add_ad_unit).setOnClickListener(this);
        e = findViewById(R.id.tv_warning);

        a = findViewById(R.id.list_ad_unit);
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
            bB.a(getContext(), xB.b().a(getContext(), R.string.design_library_admob_setting_message_add_ad_unit), 1).show();
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.layout_manual_add_ad_unit) {
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
            return new ViewHolder(LayoutInflater.from(var1.getContext()).inflate(R.layout.manage_library_setting_admob_adunit_item, var1, false));
        }

        public class ViewHolder extends v {
            public TextView t;
            public TextView u;
            public ImageView v;

            public ViewHolder(View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.tv_name);
                u = itemView.findViewById(R.id.tv_unit_id);
                v = itemView.findViewById(R.id.img_delete);
                v.setOnClickListener(view -> {
                    AdUnitsAdapter.this.c = j();
                    Iu.this.a(j());
                });
            }
        }
    }
}
