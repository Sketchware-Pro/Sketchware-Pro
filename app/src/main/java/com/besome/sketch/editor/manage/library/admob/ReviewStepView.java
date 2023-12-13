package com.besome.sketch.editor.manage.library.admob;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.Uu;
import a.a.a.gB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;

public class ReviewStepView extends LinearLayout implements Uu {

    private Switch adMobToggle;
    private TextView bannerName;
    private TextView bannerId;
    private TextView interstitialName;
    private TextView interstitialId;
    private TestDevicesAdapter adapter;
    private ArrayList<AdTestDeviceBean> testDevices = new ArrayList<>();
    private TextView rewardedAdName;
    private TextView rewardedAdId;

    public ReviewStepView(Context context) {
        super(context);
        initialize(context);
    }

    @SuppressLint("SetTextI18n")
    private void initialize(Context context) {
        wB.a(context, this, R.layout.manage_library_admob_preview);
        gB.b(this, 600, 200, null);
        bannerName = findViewById(R.id.tv_banner_name);
        bannerId = findViewById(R.id.tv_banner_id);
        interstitialName = findViewById(R.id.tv_inter_name);
        interstitialId = findViewById(R.id.tv_inter_id);
        rewardedAdName = findViewById(R.id.tv_reward_name);
        rewardedAdId = findViewById(R.id.tv_reward_id);
        ((TextView) findViewById(R.id.tv_enable)).setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        ((TextView) findViewById(R.id.tv_title_banner)).setText(Helper.getResString(R.string.design_library_admob_title_banner));

        TextView bannerName = findViewById(R.id.tv_title_banner_name);
        bannerName.setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        TextView bannerId = findViewById(R.id.tv_title_banner_id);
        bannerId.setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_title_inter)).setText(Helper.getResString(R.string.design_library_admob_title_interstitial));
        TextView interstitialName = findViewById(R.id.tv_title_inter_name);
        interstitialName.setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_title_inter_id)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_title_test_device)).setText(Helper.getResString(R.string.design_library_admob_dialog_set_test_device_title));

        RecyclerView testDevices = findViewById(R.id.list_test_device);
        testDevices.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new TestDevicesAdapter();
        testDevices.setAdapter(adapter);
        adMobToggle = findViewById(R.id.lib_switch);
        LinearLayout toggleContainer = findViewById(R.id.layout_switch);
        toggleContainer.setOnClickListener(view -> adMobToggle.setChecked(!adMobToggle.isChecked()));
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        if (adMobToggle.isChecked()) {
            projectLibraryBean.useYn = "Y";
        } else {
            projectLibraryBean.useYn = "N";
        }
    }

    private void setAppId(String appId) {
        if (!isEmpty(appId)) {
            ((TextView) findViewById(R.id.tv_app_id)).setText(appId);
        }
    }

    private void setBannerDetails(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                bannerName.setText(adUnit.substring(0, indexOfColon));
                bannerId.setText(adUnit.substring(indexOfColon + 3));
            } else {
                bannerName.setText("");
                bannerId.setText(adUnit);
            }
        }
    }

    private void setInterstitialDetails(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                interstitialName.setText(adUnit.substring(0, indexOfColon));
                interstitialId.setText(adUnit.substring(indexOfColon + 3));
            } else {
                interstitialName.setText("");
                interstitialId.setText(adUnit);
            }
        }
    }

    private void setRewardedAdDetails(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                rewardedAdName.setText(adUnit.substring(0, indexOfColon));
                rewardedAdId.setText(adUnit.substring(indexOfColon + 3));
            } else {
                rewardedAdName.setText("");
                rewardedAdId.setText(adUnit);
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
    public void setData(ProjectLibraryBean projectLibraryBean) {
        adMobToggle.setChecked(true);
        setAppId(projectLibraryBean.appId);
        setBannerDetails(projectLibraryBean.reserved1);
        setInterstitialDetails(projectLibraryBean.reserved2);
        setRewardedAdDetails(projectLibraryBean.reserved3);
        testDevices = projectLibraryBean.testDevices;
        adapter.notifyDataSetChanged();
    }

    public class TestDevicesAdapter extends RecyclerView.Adapter<TestDevicesAdapter.ViewHolder> {

        @Override
        public int getItemCount() {
            return testDevices.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.deviceId.setText(testDevices.get(position).deviceId);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_setting_admob_test_device_item, parent, false));
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView deviceId;
            public final ImageView delete;

            public ViewHolder(View itemView) {
                super(itemView);
                deviceId = itemView.findViewById(R.id.tv_device_id);
                delete = itemView.findViewById(R.id.img_delete);
                delete.setVisibility(View.GONE);
            }
        }
    }
}
