package com.besome.sketch.editor.manage.library.admob;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.besome.sketch.beans.AdUnitBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import a.a.a.Uu;
import a.a.a.bB;
import a.a.a.gB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class AssignAdUnitStepView extends LinearLayout implements Uu, OnClickListener {
    private final ArrayList<String> adUnits = new ArrayList<>();
    private String bannerAdUnitId = "";
    private TextView bannerAdName;
    private TextView bannerAdUnitIdView;
    private String interstitialAdUnitId = "";
    private TextView interstitialAdName;
    private TextView interstitialAdUnitIdView;
    private String rewardedAdUnitId = "";
    private TextView rewardedAdName;
    private TextView rewardedAdUnitIdView;

    public AssignAdUnitStepView(Context context) {
        super(context);
        initialize(context);
    }

    private RadioButton addRadioButton(String text) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(text);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) wB.a(getContext(), 4F);
        layoutParams.bottomMargin = (int) wB.a(getContext(), 4F);
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private void setAdUnit(int position) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        View rootView = wB.a(getContext(), R.layout.property_popup_selector_single);
        ViewGroup viewGroup = rootView.findViewById(R.id.rg_content);
        dialog.setTitle(Helper.getResString(R.string.design_library_admob_dialog_select_unit_title));

        for (String adUnit : adUnits) {
            viewGroup.addView(addRadioButton(adUnit));
        }

        dialog.setView(rootView);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_select), (v, which) -> {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    if (position == 0) {
                        setBannerAdUnit(Helper.getText(radioButton));
                    } else if (position == 1) {
                        setInterstitialAdUnit(Helper.getText(radioButton));
                    } else {
                        setRewardedAdUnitId(Helper.getText(radioButton));
                    }
                    break;
                }
            }
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void initialize(Context context) {
        wB.a(context, this, R.layout.manage_library_admob_set_unit);
        gB.b(this, 600, 200, null);
        bannerAdName = findViewById(R.id.tv_banner_name);
        bannerAdUnitIdView = findViewById(R.id.tv_banner_id);
        interstitialAdName = findViewById(R.id.tv_inter_name);
        interstitialAdUnitIdView = findViewById(R.id.tv_inter_id);
        rewardedAdName = findViewById(R.id.tv_reward_name);
        rewardedAdUnitIdView = findViewById(R.id.tv_reward_id);
        ((TextView) findViewById(R.id.tv_banner_title)).setText(Helper.getResString(R.string.design_library_admob_title_banner));
        ((TextView) findViewById(R.id.tv_banner_name_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_banner_id_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_inter_title)).setText(Helper.getResString(R.string.design_library_admob_title_interstitial));
        ((TextView) findViewById(R.id.tv_inter_name_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_inter_id_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_reward_name_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_reward_id_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");

        findViewById(R.id.img_select_banner).setOnClickListener(this);
        findViewById(R.id.img_select_inter).setOnClickListener(this);
        findViewById(R.id.img_select_reward).setOnClickListener(this);
        findViewById(R.id.tv_banner_edit).setOnClickListener(this);
        findViewById(R.id.tv_inter_edit).setOnClickListener(this);
        findViewById(R.id.tv_reward_edit).setOnClickListener(this);
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        projectLibraryBean.reserved1 = bannerAdUnitId;
        projectLibraryBean.reserved2 = interstitialAdUnitId;
        projectLibraryBean.reserved3 = rewardedAdUnitId;
    }

    private void setBannerAdUnit(String adUnit) {
        if (!adUnit.isEmpty()) {
            bannerAdUnitId = adUnit;
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                bannerAdName.setText(adUnit.substring(0, indexOfColon));
                bannerAdUnitIdView.setText(adUnit.substring(indexOfColon + 3));
            } else {
                bannerAdName.setText("");
                bannerAdUnitIdView.setText(adUnit);
            }
        }
    }

    private void setInterstitialAdUnit(String adUnit) {
        if (!adUnit.isEmpty()) {
            interstitialAdUnitId = adUnit;
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                interstitialAdName.setText(adUnit.substring(0, indexOfColon));
                interstitialAdUnitIdView.setText(adUnit.substring(indexOfColon + 3));
            } else {
                interstitialAdName.setText("");
                interstitialAdUnitIdView.setText(adUnit);
            }
        }
    }

    private void setRewardedAdUnitId(String adUnit) {
        if (!adUnit.isEmpty()) {
            rewardedAdUnitId = adUnit;
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                rewardedAdName.setText(adUnit.substring(0, indexOfColon));
                rewardedAdUnitIdView.setText(adUnit.substring(indexOfColon + 3));
            } else {
                rewardedAdName.setText("");
                rewardedAdUnitIdView.setText(adUnit);
            }
        }
    }

    @Override
    public String getDocUrl() {
        return "";
    }

    @Override
    public boolean isValid() {
        if (!bannerAdUnitId.isEmpty() && !interstitialAdUnitId.isEmpty()) {
            return true;
        } else {
            bB.a(getContext(), Helper.getResString(R.string.design_library_admob_message_select_ad_unit), 1).show();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.img_select_banner || id == R.id.tv_banner_edit) {
            setAdUnit(0);
        } else if (id == R.id.img_select_inter || id == R.id.tv_inter_edit) {
            setAdUnit(1);
        } else if (id == R.id.img_select_reward || id == R.id.tv_reward_edit) {
            setAdUnit(2);
        }
    }

    @Override
    public void setData(ProjectLibraryBean projectLibraryBean) {
        if (!projectLibraryBean.adUnits.isEmpty()) {
            for (AdUnitBean adUnitBean : projectLibraryBean.adUnits) {
                adUnits.add(adUnitBean.name + " : " + adUnitBean.id);
            }
        }

        setBannerAdUnit(projectLibraryBean.reserved1);
        setInterstitialAdUnit(projectLibraryBean.reserved2);
        setRewardedAdUnitId(projectLibraryBean.reserved3);
    }
}
