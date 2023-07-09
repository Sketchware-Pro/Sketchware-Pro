package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.android.material.textfield.TextInputLayout;

import java.security.MessageDigest;
import java.util.ArrayList;

import mod.hey.studios.util.Helper;

public class Tu extends LinearLayout implements Uu, View.OnClickListener {
    public RecyclerView a;
    public a b;
    public ArrayList<AdTestDeviceBean> c;

    public Tu(Context context) {
        super(context);
        this.c = new ArrayList<>();
        a(context);
    }

    private String getCurrentDeviceId() {
        return a(Settings.Secure.getString(getContext().getContentResolver(), "android_id")).toUpperCase();
    }

    @Override
    public String getDocUrl() {
        return "https://docs.sketchware.io/docs/admob-adding-test-device.html";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != 2131231403) {
            return;
        }
        a();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override
    public void setData(ProjectLibraryBean projectLibraryBean) {
        this.c = projectLibraryBean.testDevices;
        this.b.c();
    }

    class a extends RecyclerView.a<a> {
        public int c = -1;

        class a extends RecyclerView.v {
            public TextView t;
            public ImageView u;

            public a(View view) {
                super(view);
                this.t = (TextView) view.findViewById(2131231956);
                this.u = (ImageView) view.findViewById(2131231132);
                this.u.setOnClickListener(v -> {
                    Tu.this.a(j());
                });
            }
        }

        public a() {
        }

        @Override
        public void b(a aVar, int i) {
            aVar.t.setText(Tu.this.c.get(i).deviceId);
        }

        @Override
        public int a() {
            return Tu.this.c.size();
        }

        @Override
        public a b(ViewGroup viewGroup, int i) {
            return new a(LayoutInflater.from(viewGroup.getContext()).inflate(2131427555, viewGroup, false));
        }
    }

    public final void a(Context context) {
        wB.a(context, this, 2131427536);
        gB.b(this, 600, 200, null);
        ((TextView) findViewById(2131232154)).setText(xB.b().a(getContext(), 2131625164));
        findViewById(2131231403).setOnClickListener(this);
        this.a = (RecyclerView) findViewById(2131231468);
        this.a.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        this.b = new a();
        this.a.setAdapter(this.b);
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        projectLibraryBean.testDevices = this.c;
    }

    public final String a(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                stringBuffer.append(String.format("%02X", Integer.valueOf(digest[i] & 255)));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public final void a() {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(xB.b().a(getContext(), 2131625177));
        aBVar.a(2131165298);
        View a2 = wB.a(getContext(), 2131427554);
        EditText editText = (EditText) a2.findViewById(2131230989);
        ((TextInputLayout) a2.findViewById(2131231809)).setHint(xB.b().a(getContext(), 2131625176));
        SB sb = new SB(getContext(), (TextInputLayout) a2.findViewById(2131231809), 1, 100);
        editText.setText(getCurrentDeviceId());
        editText.setPrivateImeOptions("defaultInputmode=english;");
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131624970), v -> {
            if (sb.b()) {
                String text = editText.getText().toString();
                for (AdTestDeviceBean device : c) {
                    if (device.deviceId.equals(text)) {
                        bB.a(getContext(), xB.b().a(getContext(), 2131625178), 0).show();
                        return;
                    }
                }
                c.add(new AdTestDeviceBean(text));
                b.d(c.size() - 1);
                aBVar.dismiss();
            } else {
                editText.requestFocus();
            }
        });
        aBVar.a(xB.b().a(getContext(), 2131624974), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void a(int i) {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(xB.b().a(getContext(), 2131625173));
        aBVar.a(2131165524);
        aBVar.a(xB.b().a(getContext(), 2131625171));
        aBVar.b(xB.b().a(getContext(), 2131624986), v -> {
            c.remove(i);
            b.e(i);
            bB.a(getContext(), xB.b().a(getContext(), 2131624935), 0).show();
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getContext(), 2131624974), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }
}
