package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.besome.sketch.beans.AdUnitBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.sketchware.remod.R;

import java.util.ArrayList;

import mod.hey.studios.util.Helper;

public class Nu extends LinearLayout implements Uu, OnClickListener {
    public String a = "";
    public String b = "";
    public ArrayList<String> c = new ArrayList<>();
    public TextView d;
    public TextView e;
    public TextView f;
    public TextView g;
    private String rewardAdUnit = "";
    private TextView tvRewardName, tvRewardNameTitle;

    public Nu(Context context) {
        super(context);
        initialize(context);
    }

    private RadioButton addRadioButton(String text) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(text);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.topMargin = (int) wB.a(getContext(), 4.0F);
        layoutParams.bottomMargin = (int) wB.a(getContext(), 4.0F);
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private void setAdUnit(int position) {
        aB dialog = new aB((Activity) getContext());
        View rootView = wB.a(getContext(), R.layout.property_popup_selector_single);
        ViewGroup viewGroup = rootView.findViewById(R.id.rg_content);
        dialog.b(Helper.getResString(R.string.design_library_admob_dialog_select_unit_title));

        for (String adUnit : c) {
            viewGroup.addView(addRadioButton(adUnit));
        }

        dialog.a(rootView);
        dialog.b(Helper.getResString(R.string.common_word_select), view -> {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    if (position == 0) {
                        b(radioButton.getText().toString());
                    } else if (position == 1) {
                        c(radioButton.getText().toString());
                    } else {
                        setRewardAdUnit(radioButton.getText().toString());
                    }
                    break;
                }
            }
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), view -> dialog.dismiss());
        dialog.show();
    }

    private void initialize(Context var1) {
        wB.a(var1, this, R.layout.manage_library_admob_set_unit);
        gB.b(this, 600, 200, null);
        d = findViewById(R.id.tv_banner_name);
        e = findViewById(R.id.tv_banner_id);
        f = findViewById(R.id.tv_inter_name);
        g = findViewById(R.id.tv_inter_id);
        tvRewardName = findViewById(R.id.tv_reward_name);
        tvRewardNameTitle = findViewById(R.id.tv_reward_name_title);
        ((TextView) findViewById(R.id.tv_banner_title)).setText(Helper.getResString(R.string.design_library_admob_title_banner));
        ((TextView) findViewById(R.id.tv_banner_name_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_banner_id_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_inter_title)).setText(Helper.getResString(R.string.design_library_admob_title_interstitial));
        ((TextView) findViewById(R.id.tv_inter_name_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_inter_id_title)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");

        findViewById(R.id.img_select_banner).setOnClickListener(this);
        findViewById(R.id.img_select_inter).setOnClickListener(this);
        findViewById(R.id.img_select_reward).setOnClickListener(this);
        findViewById(R.id.tv_banner_edit).setOnClickListener(this);
        findViewById(R.id.tv_inter_edit).setOnClickListener(this);
        findViewById(R.id.tv_reward_edit).setOnClickListener(this);
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        projectLibraryBean.reserved1 = a;
        projectLibraryBean.reserved2 = b;
        projectLibraryBean.reserved3 = rewardAdUnit;
    }

    private void setRewardAdUnit(String adUnit) {
        if (!adUnit.isEmpty()) {
            rewardAdUnit = adUnit;
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                tvRewardNameTitle.setText(adUnit.substring(0, indexOfColon));
                tvRewardName.setText(adUnit.substring(indexOfColon + 3));
            } else {
                tvRewardNameTitle.setText("");
                tvRewardName.setText(adUnit);
            }
        }
    }

    private void b(String adUnit) {
        if (!adUnit.isEmpty()) {
            a = adUnit;
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                d.setText(adUnit.substring(0, indexOfColon));
                e.setText(adUnit.substring(indexOfColon + 3));
            } else {
                d.setText("");
                e.setText(adUnit);
            }
        }
    }

    private void c(String adUnit) {
        if (!adUnit.isEmpty()) {
            b = adUnit;
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                f.setText(adUnit.substring(0, indexOfColon));
                g.setText(adUnit.substring(indexOfColon + 3));
            } else {
                f.setText("");
                g.setText(adUnit);
            }
        }
    }

    @Override
    public String getDocUrl() {
        return "";
    }

    @Override
    public boolean isValid() {
        if (!a.isEmpty() && !b.isEmpty()) {
            return true;
        } else {
            bB.a(getContext(), Helper.getResString(R.string.design_library_admob_message_select_ad_unit), 1).show();
            return false;
        }
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.img_select_banner:
            case R.id.tv_banner_edit:
                setAdUnit(0);
                break;

            case R.id.img_select_inter:
            case R.id.tv_inter_edit:
                setAdUnit(1);
                break;

            case R.id.img_select_reward:
            case R.id.tv_reward_edit:
                setAdUnit(2);
                break;
        }
    }

    @Override
    public void setData(ProjectLibraryBean projectLibraryBean) {
        if (projectLibraryBean.adUnits.size() > 0) {
            for (AdUnitBean adUnitBean : projectLibraryBean.adUnits) {
                c.add(adUnitBean.name + " : " + adUnitBean.id);
            }
        }

        b(projectLibraryBean.reserved1);
        c(projectLibraryBean.reserved2);
        setRewardAdUnit(projectLibraryBean.reserved3);
    }
}
