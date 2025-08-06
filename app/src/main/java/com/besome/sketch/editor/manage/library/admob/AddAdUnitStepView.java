package com.besome.sketch.editor.manage.library.admob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.AdUnitBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import a.a.a.SB;
import a.a.a.Uu;
import a.a.a.bB;
import a.a.a.gB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class AddAdUnitStepView extends LinearLayout implements Uu, OnClickListener {
    private AdUnitsAdapter adUnitsAdapter;
    private ArrayList<AdUnitBean> adUnitBeanArrayList = new ArrayList<>();
    private TextView tvWarning;

    public AddAdUnitStepView(AdmobActivity activity) {
        this(activity, null);
    }

    public AddAdUnitStepView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void createAdUnit() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(Helper.getResString(R.string.design_library_admob_dialog_add_adunit_title));
        dialog.setIcon(R.drawable.ic_mtrl_add);
        View rootView = wB.a(getContext(), R.layout.manage_library_setting_admob_adunit_add);

        TextInputLayout tiName = rootView.findViewById(R.id.ti_name);
        tiName.setHint(Helper.getResString(R.string.design_library_admob_dialog_add_adunit_hint_adunit_name));
        EditText edName = rootView.findViewById(R.id.ed_name);
        SB nameValidator = new SB(getContext(), tiName, 1, 50);

        TextInputLayout tiAdUnitId = rootView.findViewById(R.id.ti_adunit_id);
        tiAdUnitId.setHint(Helper.getResString(R.string.design_library_admob_dialog_add_adunit_hint_adunit_id));
        EditText edAdUnitId = rootView.findViewById(R.id.ed_adunit_id);
        SB adUnitValidator = new SB(getContext(), tiAdUnitId, 1, 100);

        edName.setPrivateImeOptions("defaultInputmode=english;");
        dialog.setView(rootView);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_add), (v, which) -> {
            if (!nameValidator.b()) {
                edName.requestFocus();
            } else if (!adUnitValidator.b()) {
                edAdUnitId.requestFocus();
            } else {
                String name = Helper.getText(edName);
                String id = Helper.getText(edAdUnitId);
                adUnitBeanArrayList.add(new AdUnitBean(id, name));
                adUnitsAdapter.notifyItemInserted(adUnitBeanArrayList.size() - 1);
                v.dismiss();
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private void deleteAdUnit(int position) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(Helper.getResString(R.string.design_library_admob_dialog_delete_adunit_title));
        dialog.setIcon(R.drawable.delete_96);
        dialog.setMessage(Helper.getResString(R.string.design_library_admob_dialog_confirm_delete_adunit));
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (v, which) -> {
            adUnitBeanArrayList.remove(position);
            adUnitsAdapter.notifyItemRemoved(position);
            bB.a(getContext(), Helper.getResString(R.string.common_message_complete_delete), 0).show();
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.manage_library_admob_listing);
        gB.b(this, 600, 200, null);
        findViewById(R.id.layout_manual_add_ad_unit).setOnClickListener(this);
        tvWarning = findViewById(R.id.tv_warning);

        TextView addManually = findViewById(R.id.tv_manual_add_ad_unit);
        addManually.setText(Helper.getResString(R.string.design_library_admob_button_manual_add_to_adunit));

        RecyclerView listAdUnit = findViewById(R.id.list_ad_unit);
        listAdUnit.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
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
        if (!adUnitBeanArrayList.isEmpty()) {
            return true;
        } else {
            bB.a(getContext(), Helper.getResString(R.string.design_library_admob_setting_message_add_ad_unit), 1).show();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_manual_add_ad_unit) {
            createAdUnit();
        }
    }

    @Override
    public void setData(ProjectLibraryBean projectLibraryBean) {
        adUnitBeanArrayList = projectLibraryBean.adUnits;
        adUnitsAdapter.notifyDataSetChanged();
    }

    private class AdUnitsAdapter extends RecyclerView.Adapter<AdUnitsAdapter.ViewHolder> {
        @Override
        public int getItemCount() {
            if (adUnitBeanArrayList.isEmpty()) {
                tvWarning.setVisibility(View.VISIBLE);
            } else {
                tvWarning.setVisibility(View.GONE);
            }

            return adUnitBeanArrayList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            AdUnitBean adUnitBean = adUnitBeanArrayList.get(position);
            viewHolder.tvName.setText(adUnitBean.name);
            viewHolder.tvUnitId.setText(adUnitBean.id);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_setting_admob_adunit_item, parent, false));
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView tvName;
            public final TextView tvUnitId;
            public final ImageView imgDelete;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_name);
                tvUnitId = itemView.findViewById(R.id.tv_unit_id);
                imgDelete = itemView.findViewById(R.id.img_delete);
                imgDelete.setOnClickListener(view -> deleteAdUnit(getLayoutPosition()));
            }
        }
    }
}
