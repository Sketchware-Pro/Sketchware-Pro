package a.a.a;

import android.annotation.SuppressLint;
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

@SuppressLint("ResourceType")
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
        View rootView = wB.a(getContext(), 2131427643);
        ViewGroup viewGroup = rootView.findViewById(2131231668);
        dialog.b(xB.b().a(getContext(), 2131625175));

        for (String adUnit : c) {
            viewGroup.addView(addRadioButton(adUnit));
        }

        dialog.a(rootView);
        dialog.b(xB.b().a(getContext(), 2131625035), view -> {
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
        dialog.a(xB.b().a(getContext(), 2131624974), view -> dialog.dismiss());
        dialog.show();
    }

    private void initialize(Context var1) {
        wB.a(var1, this, 2131427535);
        gB.b(this, 600, 200, null);
        d = findViewById(2131231889);
        e = findViewById(2131231887);
        f = findViewById(2131232014);
        g = findViewById(2131232012);
        tvRewardName = findViewById(R.id.tv_reward_name);
        tvRewardNameTitle = findViewById(R.id.tv_reward_name_title);
        ((TextView) findViewById(2131231891)).setText(xB.b().a(getContext(), 2131625196));
        ((TextView) findViewById(2131231890)).setText(xB.b().a(getContext(), 2131625192) + " : ");
        ((TextView) findViewById(2131231888)).setText(xB.b().a(getContext(), 2131625193) + " : ");
        ((TextView) findViewById(2131232016)).setText(xB.b().a(getContext(), 2131625197));
        ((TextView) findViewById(2131232015)).setText(xB.b().a(getContext(), 2131625192) + " : ");
        ((TextView) findViewById(2131232013)).setText(xB.b().a(getContext(), 2131625193) + " : ");
        findViewById(2131231179).setOnClickListener(this);
        findViewById(2131231180).setOnClickListener(this);
        findViewById(2131231886).setOnClickListener(this);
        findViewById(2131232011).setOnClickListener(this);
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        projectLibraryBean.reserved1 = a;
        projectLibraryBean.reserved2 = b;
    }

    private void setRewardAdUnit(String adUnit) {
        if (!adUnit.isEmpty()) {
            rewardAdUnit = adUnit;
            if (adUnit.contains(" : ")) {
                int indexOfColon = adUnit.indexOf(" : ");
                tvRewardNameTitle.setText(adUnit.substring(0, indexOfColon));
                tvRewardName.setText(indexOfColon + 3);
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
            bB.a(getContext(), xB.b().a(getContext(), 2131625182), 1).show();
            return false;
        }
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case 2131231179:
            case 2131231886:
                setAdUnit(0);
                break;

            case 2131231180:
            case 2131232011:
                setAdUnit(1);
                break;

            case R.id.tv_reward_edit:
                setAdUnit(2);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
