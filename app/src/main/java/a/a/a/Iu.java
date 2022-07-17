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

import com.besome.sketch.beans.AdUnitBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.admob.AdmobActivity;
import com.sketchware.remod.R;

import java.util.ArrayList;

import mod.hey.studios.util.Helper;

@SuppressLint("ViewConstructor")
public class Iu extends LinearLayout implements Uu, OnClickListener {

    private AdUnitsAdapter adUnitsAdapter;
    private ArrayList<AdUnitBean> adUnitBeanArrayList = new ArrayList<>();
    private TextView tvWarning;

    public Iu(AdmobActivity activity) {
        super(activity);
        initialize(activity);
    }

    private void createAdUnit() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(Helper.getResString(R.string.design_library_admob_dialog_add_adunit_title));
        dialog.a(R.drawable.add_96_blue);
        View rootView = wB.a(getContext(), R.layout.manage_library_setting_admob_adunit_add);
        EditText edName = rootView.findViewById(R.id.ed_name);
        SB nameValidator = new SB(getContext(), rootView.findViewById(R.id.ti_name), 1, 50);
        EditText edAdUnitId = rootView.findViewById(R.id.ed_adunit_id);
        SB adUnitValidator = new SB(getContext(), rootView.findViewById(R.id.ti_adunit_id), 1, 100);
        edName.setPrivateImeOptions("defaultInputmode=english;");
        dialog.a(rootView);
        dialog.b(Helper.getResString(R.string.common_word_add), view -> {
            if (!nameValidator.b()) {
                edName.requestFocus();
            } else if (!adUnitValidator.b()) {
                edAdUnitId.requestFocus();
            } else {
                String name = edName.getText().toString();
                String id = edAdUnitId.getText().toString();
                adUnitBeanArrayList.add(new AdUnitBean(id, name));
                adUnitsAdapter.d(adUnitBeanArrayList.size() - 1);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), view -> dialog.dismiss());
        dialog.show();
    }

    private void deleteADUnit(int position) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(Helper.getResString(R.string.design_library_admob_dialog_delete_adunit_title));
        dialog.a(R.drawable.delete_96);
        dialog.a(Helper.getResString(R.string.design_library_admob_dialog_confirm_delete_adunit));
        dialog.b(Helper.getResString(R.string.common_word_delete), view -> {
            adUnitBeanArrayList.remove(position);
            adUnitsAdapter.e(position);
            bB.a(getContext(), Helper.getResString(R.string.common_message_complete_delete), 0).show();
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), view -> dialog.dismiss());
        dialog.show();
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.manage_library_admob_listing);
        gB.b(this, 600, 200, null);
        findViewById(R.id.layout_manual_add_ad_unit).setOnClickListener(this);
        tvWarning = findViewById(R.id.tv_warning);

        RecyclerView listAdUnit = findViewById(R.id.list_ad_unit);
        listAdUnit.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        adUnitsAdapter = new AdUnitsAdapter();
        listAdUnit.setAdapter(adUnitsAdapter);
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
            bB.a(getContext(), Helper.getResString(R.string.design_library_admob_setting_message_add_ad_unit), 1).show();
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.layout_manual_add_ad_unit) {
            createAdUnit();
        }
    }

    @Override
    public void setData(ProjectLibraryBean projectLibraryBean) {
        adUnitBeanArrayList = projectLibraryBean.adUnits;
        adUnitsAdapter.c();
    }

    public class AdUnitsAdapter extends androidx.recyclerview.widget.RecyclerView.a<AdUnitsAdapter.ViewHolder> {

        public int layoutPosition;

        public AdUnitsAdapter() {
            layoutPosition = -1;
        }

        @Override
        public int a() {
            if (adUnitBeanArrayList.size() == 0) {
                tvWarning.setVisibility(View.VISIBLE);
            } else {
                tvWarning.setVisibility(View.GONE);
            }

            return adUnitBeanArrayList.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            AdUnitBean adUnitBean = adUnitBeanArrayList.get(position);
            viewHolder.tvName.setText(adUnitBean.name);
            viewHolder.tvUnitId.setText(adUnitBean.id);
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_setting_admob_adunit_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {
            public TextView tvName;
            public TextView tvUnitId;
            public ImageView imgDelete;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_name);
                tvUnitId = itemView.findViewById(R.id.tv_unit_id);
                imgDelete = itemView.findViewById(R.id.img_delete);
                imgDelete.setOnClickListener(view -> {
                    layoutPosition = j();
                    deleteADUnit(j());
                });
            }
        }
    }
}
