package com.besome.sketch.editor.manage.library.admob;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.mB;
import mod.hey.studios.util.Helper;

public class ManageAdmobActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_ENABLE_ADMOB = 8001;
    private static final int REQUEST_CODE_ADMOB_SETTINGS = 8002;
    private DB A = null;
    private TestDeviceAdapter testDeviceAdapter;
    private ArrayList<AdTestDeviceBean> testDeviceList = new ArrayList<>();
    private Switch libSwitch;
    private TextView tvBannerName;
    private TextView tvBannerId;
    private TextView tvInterName;
    private TextView tvInterId;
    private ProjectLibraryBean admobLibraryBean;
    private String sc_id;
    private TextView tvRewardId;
    private TextView tvRewardName;

    private void initializeLibrary(ProjectLibraryBean libraryBean) {
        admobLibraryBean = libraryBean;
        configure();
    }

    private void setAppId(String appId) {
        if (!isEmpty(appId)) {
            ((TextView) findViewById(R.id.tv_app_id)).setText(appId);
        }
    }

    private void setBannerAdUnit(String adId) {
        if (!adId.isEmpty()) {
            if (adId.contains(" : ")) {
                int indexOfSemicolon = adId.indexOf(" : ");
                tvBannerName.setText(adId.substring(0, indexOfSemicolon));
                tvBannerId.setText(adId.substring(indexOfSemicolon + 3));
            } else {
                tvBannerName.setText("");
                tvBannerId.setText(adId);
            }
        }
    }

    private void setInterAdUnit(String adId) {
        if (!adId.isEmpty()) {
            if (adId.contains(" : ")) {
                int indexOfSemicolon = adId.indexOf(" : ");
                tvInterName.setText(adId.substring(0, indexOfSemicolon));
                tvInterId.setText(adId.substring(indexOfSemicolon + 3));
            } else {
                tvInterName.setText("");
                tvInterId.setText(adId);
            }
        }
    }

    private void setRewardAdUnit(String adId) {
        if (!adId.isEmpty()) {
            if (adId.contains(" : ")) {
                int indexOfSemicolon = adId.indexOf(" : ");
                tvRewardName.setText(adId.substring(0, indexOfSemicolon));
                tvRewardId.setText(adId.substring(indexOfSemicolon + 3));
            } else {
                tvRewardName.setText("");
                tvRewardId.setText(adId);
            }
        }
    }

    private void n() {
        if (GB.h(getApplicationContext())) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("https://apps.admob.com/v2/home"));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                downloadChromeDialog();
            }
        } else {
            bB.a(getApplicationContext(), Helper.getResString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
        }
    }

    private void o() {
        try {
            A.a("P1I16", true);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("https://docs.sketchware.io/docs/admob-getting-started.html"));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception e) {
            downloadChromeDialog();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 236:
                    initializeLibrary(data.getParcelableExtra("admob"));
                    break;

                case REQUEST_CODE_ENABLE_ADMOB:
                    libSwitch.setChecked(true);
                    admobLibraryBean.useYn = "Y";
                    break;

                case REQUEST_CODE_ADMOB_SETTINGS:
                    toAdmobActivity();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        getIntent().putExtra("admob", admobLibraryBean);
        setResult(RESULT_OK, getIntent());
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();
            if (id == R.id.btn_console) {
                n();
            } else if (id == R.id.layout_switch) {
                if (!libSwitch.isChecked()) {
                    libSwitch.setChecked(true);
                    admobLibraryBean.useYn = "Y";
                } else {
                    libSwitch.setChecked(!libSwitch.isChecked());
                    if ("Y".equals(admobLibraryBean.useYn) && !libSwitch.isChecked()) {
                        configureLibrary();
                    } else {
                        admobLibraryBean.useYn = "Y";
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_library_manage_admob);

        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.design_library_admob_title_admob_manager));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        A = new DB(getApplicationContext(), "P1");
        admobLibraryBean = getIntent().getParcelableExtra("admob");
        ((TextView) findViewById(R.id.tv_enable)).setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        ((TextView) findViewById(R.id.tv_title_banner)).setText(Helper.getResString(R.string.design_library_admob_title_banner));
        ((TextView) findViewById(R.id.tv_title_banner_name)).setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_title_banner_id)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_title_inter)).setText(Helper.getResString(R.string.design_library_admob_title_interstitial));
        ((TextView) findViewById(R.id.tv_title_inter_name)).setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_title_inter_id)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_title_reward_name)).setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(R.id.tv_title_reward_id)).setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(R.id.tv_title_test_device)).setText(Helper.getResString(R.string.design_library_admob_dialog_set_test_device_title));
        tvBannerId = findViewById(R.id.tv_banner_id);
        tvBannerName = findViewById(R.id.tv_banner_name);
        tvInterId = findViewById(R.id.tv_inter_id);
        tvInterName = findViewById(R.id.tv_inter_name);
        tvRewardId = findViewById(R.id.tv_reward_id);
        tvRewardName = findViewById(R.id.tv_reward_name);

        RecyclerView listTestDevice = findViewById(R.id.list_test_device);
        listTestDevice.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        testDeviceAdapter = new TestDeviceAdapter();
        listTestDevice.setAdapter(testDeviceAdapter);

        libSwitch = findViewById(R.id.lib_switch);
        LinearLayout switchLayout = findViewById(R.id.layout_switch);
        switchLayout.setOnClickListener(this);

        Button btnConsole = findViewById(R.id.btn_console);
        btnConsole.setText(Helper.getResString(R.string.design_library_admob_button_goto_console));
        btnConsole.setOnClickListener(this);

        configure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_admob_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_admob_help) {
            o();
        } else if (itemId == R.id.menu_admob_settings) {
            toAdmobActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private void configureLibrary() {
        final aB dialog = new aB(this);
        dialog.a(R.drawable.delete_96);
        dialog.b(Helper.getResString(R.string.common_word_warning));
        dialog.a(Helper.getResString(R.string.design_library_admob_dialog_description_confirm_uncheck));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(R.string.common_word_delete), (d, which) -> {
            if (!mB.a()) {
                admobLibraryBean.useYn = "N";
                libSwitch.setChecked(false);
                d.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), (d, which) -> Helper.getDialogDismissListener(d));
        dialog.show();
    }

    private void downloadChromeDialog() {
        final aB dialog = new aB(this);
        dialog.a(R.drawable.chrome_96);
        dialog.b(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(R.string.common_word_ok), (d, which) -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                d.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), (d, which) -> Helper.getDialogDismissListener(d));
        dialog.show();
    }

    private void toAdmobActivity() {
        Intent intent = new Intent(getApplicationContext(), AdmobActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("admob", admobLibraryBean);
        startActivityForResult(intent, 236);
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(admobLibraryBean.useYn));
        setBannerAdUnit(admobLibraryBean.reserved1);
        setInterAdUnit(admobLibraryBean.reserved2);
        setRewardAdUnit(admobLibraryBean.reserved3);
        setAppId(admobLibraryBean.appId);
        testDeviceList = admobLibraryBean.testDevices;
        testDeviceAdapter.notifyDataSetChanged();
    }

    private class TestDeviceAdapter extends RecyclerView.Adapter<TestDeviceAdapter.ViewHolder> {

        @Override
        public int getItemCount() {
            return testDeviceList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvDeviceId.setText(testDeviceList.get(position).deviceId);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_setting_admob_test_device_item, parent, false));
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView tvDeviceId;

            public ViewHolder(View itemView) {
                super(itemView);
                tvDeviceId = itemView.findViewById(R.id.tv_device_id);
                ImageView imgDelete = itemView.findViewById(R.id.img_delete);

                imgDelete.setVisibility(View.GONE);
            }
        }
    }
}
