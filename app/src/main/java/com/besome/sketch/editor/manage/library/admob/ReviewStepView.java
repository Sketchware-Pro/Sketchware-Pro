package com.besome.sketch.editor.manage.library.admob;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;

import a.a.a.Uu;
import a.a.a.gB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryAdmobPreviewBinding;
import pro.sketchware.databinding.ManageLibrarySettingAdmobTestDeviceItemBinding;
import pro.sketchware.lib.base.BaseViewBindingAdapter;

public class ReviewStepView extends LinearLayout implements Uu {

    private TestDevicesAdapter adapter;
    private ManageLibraryAdmobPreviewBinding binding;

    public ReviewStepView(Context context) {
        super(context);
        initialize(context);
    }

    @SuppressLint("SetTextI18n")
    private void initialize(Context context) {
        binding = ManageLibraryAdmobPreviewBinding.inflate(LayoutInflater.from(context), this, true);
        gB.b(this, 600, 200, null);
        binding.tvEnable.setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        binding.tvTitleBanner.setText(Helper.getResString(R.string.design_library_admob_title_banner));

        binding.tvTitleBannerName.setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        binding.tvTitleBannerId.setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        binding.tvTitleInter.setText(Helper.getResString(R.string.design_library_admob_title_interstitial));
        binding.tvTitleInterName.setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        binding.tvTitleInterId.setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        binding.tvTitleTestDevice.setText(Helper.getResString(R.string.design_library_admob_dialog_set_test_device_title));

        binding.listTestDevice.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new TestDevicesAdapter();
        binding.listTestDevice.setAdapter(adapter);
        binding.layoutSwitch.setOnClickListener(view -> binding.libSwitch.setChecked(!binding.libSwitch.isChecked()));
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        if (binding.libSwitch.isChecked()) {
            projectLibraryBean.useYn = "Y";
        } else {
            projectLibraryBean.useYn = "N";
        }
    }

    private void setAppId(String appId) {
        if (!isEmpty(appId)) {
            binding.tvAppId.setText(appId);
        }
    }

    private void setBannerDetails(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                binding.tvBannerName.setText(adUnit.substring(0, indexOfColon));
                binding.tvBannerId.setText(adUnit.substring(indexOfColon + 3));
            } else {
                binding.tvBannerName.setText("");
                binding.tvBannerId.setText(adUnit);
            }
        }
    }

    private void setInterstitialDetails(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                binding.tvInterName.setText(adUnit.substring(0, indexOfColon));
                binding.tvInterId.setText(adUnit.substring(indexOfColon + 3));
            } else {
                binding.tvInterName.setText("");
                binding.tvInterId.setText(adUnit);
            }
        }
    }

    private void setRewardedAdDetails(String adUnit) {
        if (!adUnit.isEmpty()) {
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                binding.tvRewardName.setText(adUnit.substring(0, indexOfColon));
                binding.tvRewardId.setText(adUnit.substring(indexOfColon + 3));
            } else {
                binding.tvRewardName.setText("");
                binding.tvRewardId.setText(adUnit);
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
        binding.libSwitch.setChecked(true);
        setAppId(projectLibraryBean.appId);
        setBannerDetails(projectLibraryBean.reserved1);
        setInterstitialDetails(projectLibraryBean.reserved2);
        setRewardedAdDetails(projectLibraryBean.reserved3);
        adapter.setItems(projectLibraryBean.testDevices);
    }

    public static class TestDevicesAdapter extends BaseViewBindingAdapter<AdTestDeviceBean> {

        @NonNull
        @Override
        public ViewBinding getViewBinding(LayoutInflater inflater, ViewGroup parent) {
            return ManageLibrarySettingAdmobTestDeviceItemBinding.inflate(inflater, parent, false);
        }

        @Override
        public void onBindView(@NonNull ViewBinding binding, int position) {
            if (binding instanceof ManageLibrarySettingAdmobTestDeviceItemBinding testDeviceBinding) {
                testDeviceBinding.tvDeviceId.setText(getItem(position).deviceId);
                testDeviceBinding.imgDelete.setVisibility(View.GONE);
            }
        }
    }
}
