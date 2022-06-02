package a.a.a;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;

@SuppressLint("ResourceType")
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

    public Ku(Context var1) {
        super(var1);
        a(var1);
    }


    private void a(Context context) {
        wB.a(context, this, 2131427534);
        gB.b(this, 600, 200, null);
        c = findViewById(2131231889);
        d = findViewById(2131231887);
        e = findViewById(2131232014);
        f = findViewById(2131232012);
        ((TextView) findViewById(2131231965)).setText(xB.b().a(context, 2131625249));
        ((TextView) findViewById(2131232201)).setText(xB.b().a(getContext(), 2131625196));
        TextView var3 = findViewById(2131232203);
        StringBuilder var2 = new StringBuilder();
        var2.append(xB.b().a(getContext(), 2131625192));
        var2.append(" : ");
        var3.setText(var2.toString());
        TextView var5 = findViewById(2131232202);
        var5.setText(xB.b().a(getContext(), 2131625193) + " : ");
        ((TextView) findViewById(2131232220)).setText(xB.b().a(getContext(), 2131625197));
        var3 = findViewById(2131232222);
        var2 = new StringBuilder();
        var2.append(xB.b().a(getContext(), 2131625192));
        var2.append(" : ");
        var3.setText(var2.toString());
        ((TextView) findViewById(2131232221)).setText(xB.b().a(getContext(), 2131625193) + " : ");
        ((TextView) findViewById(2131232246)).setText(xB.b().a(getContext(), 2131625177));
        g = findViewById(2131231468);
        g.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        h = new TestDevicesAdapter();
        g.setAdapter(h);
        b = findViewById(2131231429);
        a = findViewById(2131231408);
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
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(2131427555, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {
            public TextView t;
            public ImageView u;

            public ViewHolder(View itemView) {
                super(itemView);
                t = itemView.findViewById(2131231956);
                u = itemView.findViewById(2131231132);
                u.setVisibility(View.GONE);
            }
        }
    }
}
