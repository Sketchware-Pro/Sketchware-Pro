package a.a.a;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.sketchware.remod.R;

import java.util.ArrayList;

public class Ku extends LinearLayout implements Uu {

    public LinearLayout a;
    public Switch b;
    public TextView c;
    public TextView d;
    public TextView e;
    public TextView f;
    public RecyclerView g;
    public TestDevicesAdapter h;
    public ArrayList<AdTestDeviceBean> i = new ArrayList<>();

    private TextView tvRewardName, tvRewardId;

    public Ku(Context var1) {
        super(var1);
        a(var1);
    }

    private void a(Context context) {
        wB.a(context, this, R.layout.manage_library_admob_preview);
        gB.b(this, 600, 200, null);
        c = findViewById(R.id.tv_banner_name);
        d = findViewById(R.id.tv_banner_id);
        e = findViewById(R.id.tv_inter_name);
        f = findViewById(R.id.tv_inter_id);
        tvRewardName = findViewById(R.id.tv_reward_name);
        tvRewardId = findViewById(R.id.tv_reward_id);
        ((TextView) findViewById(R.id.tv_enable)).setText(xB.b().a(context, R.string.design_library_settings_title_enabled));
        ((TextView) findViewById(R.id.tv_title_banner)).setText(xB.b().a(getContext(), R.string.design_library_admob_title_banner));
        TextView var3 = findViewById(R.id.tv_title_banner_name);
        StringBuilder var2 = new StringBuilder();
        var2.append(xB.b().a(getContext(), R.string.design_library_admob_title_ad_name));
        var2.append(" : ");
        var3.setText(var2.toString());
        TextView var5 = findViewById(R.id.tv_title_banner_id);
        var5.setText(xB.b().a(getContext(), R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_title_inter)).setText(xB.b().a(getContext(), R.string.design_library_admob_title_interstitial));
        var3 = findViewById(R.id.tv_title_inter_name);
        var2 = new StringBuilder();
        var2.append(xB.b().a(getContext(), R.string.design_library_admob_title_ad_name));
        var2.append(" : ");
        var3.setText(var2.toString());
        ((TextView) findViewById(R.id.tv_title_inter_id)).setText(xB.b().a(getContext(), R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_title_test_device)).setText(xB.b().a(getContext(), R.string.design_library_admob_dialog_set_test_device_title));
        g = findViewById(R.id.list_test_device);
        g.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        h = new TestDevicesAdapter();
        g.setAdapter(h);
        b = findViewById(R.id.lib_switch);
        a = findViewById(R.id.layout_switch);
        a.setOnClickListener(view -> b.setChecked(!b.isChecked()));
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        if (b.isChecked()) {
            projectLibraryBean.useYn = "Y";
        } else {
            projectLibraryBean.useYn = "N";
        }
    }

    private void a(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                c.setText(adUnit.substring(0, indexOfColon));
                d.setText(adUnit.substring(indexOfColon + 3));
            } else {
                c.setText("");
                d.setText(adUnit);
            }
        }
    }

    private void b(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                e.setText(adUnit.substring(0, indexOfColon));
                f.setText(adUnit.substring(indexOfColon + 3));
            } else {
                e.setText("");
                f.setText(adUnit);
            }
        }
    }

    private void setRewardAdUnit(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                tvRewardName.setText(adUnit.substring(0, indexOfColon));
                tvRewardId.setText(adUnit.substring(indexOfColon + 3));
            } else {
                tvRewardName.setText("");
                tvRewardId.setText(adUnit);
            }
        }
    }

    @Override
    public String getDocUrl() {
        return "";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void setData(ProjectLibraryBean projectLibraryBean) {
        b.setChecked(true);
        a(projectLibraryBean.reserved1);
        b(projectLibraryBean.reserved2);
        setRewardAdUnit(projectLibraryBean.reserved3);
        i = projectLibraryBean.testDevices;
        h.c();
    }

    public class TestDevicesAdapter extends RecyclerView.a<TestDevicesAdapter.ViewHolder> {
        public int c;

        public TestDevicesAdapter() {
            c = -1;
        }

        @Override
        public int a() {
            return i.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            AdTestDeviceBean var3 = i.get(position);
            viewHolder.t.setText(var3.deviceId);
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_setting_admob_test_device_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {
            public TextView t;
            public ImageView u;

            public ViewHolder(View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.tv_device_id);
                u = itemView.findViewById(R.id.img_delete);
                u.setVisibility(View.GONE);
            }
        }
    }
}
