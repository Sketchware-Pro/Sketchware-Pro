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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

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
        if (view.getId() != R.id.layout_set_test_device) {
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
        this.b.notifyDataSetChanged();
    }

    class a extends RecyclerView.Adapter<a.ViewHolder> {
        public int c = -1;

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView t;
            public ImageView u;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.t = (TextView) itemView.findViewById(R.id.tv_device_id);
                this.u = (ImageView) itemView.findViewById(R.id.img_delete);
                this.u.setOnClickListener(v -> {
                    Tu.this.a(getLayoutPosition());
                });
            }
        }

        public a() {
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.t.setText(Tu.this.c.get(position).deviceId);
        }

        @Override
        public int getItemCount() {
            return Tu.this.c.size();
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_setting_admob_test_device_item, parent, false));
        }
    }

    public final void a(Context context) {
        wB.a(context, this, R.layout.manage_library_admob_test_device);
        gB.b(this, 600, 200, null);
        ((TextView) findViewById(R.id.tv_set_test_device)).setText(xB.b().a(getContext(), R.string.design_library_admob_button_set_test_device));
        findViewById(R.id.layout_set_test_device).setOnClickListener(this);
        this.a = (RecyclerView) findViewById(R.id.list_test_device);
        this.a.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
        aBVar.b(xB.b().a(getContext(), R.string.design_library_admob_dialog_set_test_device_title));
        aBVar.a(R.drawable.add_96_blue);
        View a2 = wB.a(getContext(), R.layout.manage_library_setting_admob_test_device_add);
        EditText editText = (EditText) a2.findViewById(R.id.ed_device_id);
        ((TextInputLayout) a2.findViewById(R.id.ti_device_id)).setHint(xB.b().a(getContext(), R.string.design_library_admob_dialog_set_test_device_hint_device_id));
        SB sb = new SB(getContext(), (TextInputLayout) a2.findViewById(R.id.ti_device_id), 1, 100);
        editText.setText(getCurrentDeviceId());
        editText.setPrivateImeOptions("defaultInputmode=english;");
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), R.string.common_word_add), v -> {
            if (sb.b()) {
                String text = editText.getText().toString();
                for (AdTestDeviceBean device : c) {
                    if (device.deviceId.equals(text)) {
                        bB.a(getContext(), xB.b().a(getContext(), R.string.design_library_admob_dialog_set_test_device_warning_duplicated), 0).show();
                        return;
                    }
                }
                c.add(new AdTestDeviceBean(text));
                b.notifyItemInserted(c.size() - 1);
                aBVar.dismiss();
            } else {
                editText.requestFocus();
            }
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void a(int i) {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(xB.b().a(getContext(), R.string.design_library_admob_dialog_delete_test_device_title));
        aBVar.a(R.drawable.delete_96);
        aBVar.a(xB.b().a(getContext(), R.string.design_library_admob_dialog_confirm_delete_test_device));
        aBVar.b(xB.b().a(getContext(), R.string.common_word_delete), v -> {
            c.remove(i);
            b.notifyItemRemoved(i);
            bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_delete), 0).show();
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }
}
