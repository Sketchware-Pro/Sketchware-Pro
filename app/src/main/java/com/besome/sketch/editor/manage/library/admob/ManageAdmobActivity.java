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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.bB;
import a.a.a.mB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryManageAdmobBinding;

public class ManageAdmobActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_ENABLE_ADMOB = 8001;
    private static final int REQUEST_CODE_ADMOB_SETTINGS = 8002;
    private DB A;
    private TestDeviceAdapter testDeviceAdapter;
    private ArrayList<AdTestDeviceBean> testDeviceList = new ArrayList<>();
    private ProjectLibraryBean admobLibraryBean;
    private String sc_id;
    private ManageLibraryManageAdmobBinding binding;

    private void initializeLibrary(ProjectLibraryBean libraryBean) {
        admobLibraryBean = libraryBean;
        configure();
    }

    private void setAppId(String appId) {
        if (!isEmpty(appId)) {
            binding.tvAppId.setText(appId);
        }
    }

    private void setBannerAdUnit(String adId) {
        if (!adId.isEmpty()) {
            if (adId.contains(" : ")) {
                int indexOfSemicolon = adId.indexOf(" : ");
                binding.tvBannerName.setText(adId.substring(0, indexOfSemicolon));
                binding.tvBannerId.setText(adId.substring(indexOfSemicolon + 3));
            } else {
                binding.tvBannerName.setText("");
                binding.tvBannerId.setText(adId);
            }
        }
    }

    private void setInterAdUnit(String adId) {
        if (!adId.isEmpty()) {
            if (adId.contains(" : ")) {
                int indexOfSemicolon = adId.indexOf(" : ");
                binding.tvInterName.setText(adId.substring(0, indexOfSemicolon));
                binding.tvInterId.setText(adId.substring(indexOfSemicolon + 3));
            } else {
                binding.tvInterName.setText("");
                binding.tvInterId.setText(adId);
            }
        }
    }

    private void setRewardAdUnit(String adId) {
        if (!adId.isEmpty()) {
            if (adId.contains(" : ")) {
                int indexOfSemicolon = adId.indexOf(" : ");
                binding.tvRewardName.setText(adId.substring(0, indexOfSemicolon));
                binding.tvRewardId.setText(adId.substring(indexOfSemicolon + 3));
            } else {
                binding.tvRewardName.setText("");
                binding.tvRewardId.setText(adId);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 236:
                    initializeLibrary(data.getParcelableExtra("admob"));
                    break;

                case REQUEST_CODE_ENABLE_ADMOB:
                    binding.libSwitch.setChecked(true);
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
            if (id == binding.btnConsole.getId()) {
                n();
            } else if (id == binding.layoutSwitch.getId()) {
                if (!binding.libSwitch.isChecked()) {
                    binding.libSwitch.setChecked(true);
                    admobLibraryBean.useYn = "Y";
                } else {
                    binding.libSwitch.setChecked(!binding.libSwitch.isChecked());
                    if ("Y".equals(admobLibraryBean.useYn) && !binding.libSwitch.isChecked()) {
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
        binding = ManageLibraryManageAdmobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(Helper.getResString(R.string.design_library_admob_title_admob_manager));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        A = new DB(getApplicationContext(), "P1");
        admobLibraryBean = getIntent().getParcelableExtra("admob");
        binding.tvEnable.setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        binding.tvTitleBanner.setText(Helper.getResString(R.string.design_library_admob_title_banner));
        binding.tvTitleBannerName.setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        binding.tvTitleBannerId.setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        binding.tvTitleInter.setText(Helper.getResString(R.string.design_library_admob_title_interstitial));
        binding.tvTitleInterName.setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        binding.tvTitleInterId.setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        binding.tvTitleRewardName.setText(Helper.getResString(R.string.design_library_admob_title_ad_name) + " : ");
        binding.tvTitleRewardId.setText(Helper.getResString(R.string.design_library_admob_title_ad_unit_id) + " : ");
        binding.tvTitleTestDevice.setText(Helper.getResString(R.string.design_library_admob_dialog_set_test_device_title));

        binding.listTestDevice.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        testDeviceAdapter = new TestDeviceAdapter();
        binding.listTestDevice.setAdapter(testDeviceAdapter);

        binding.layoutSwitch.setOnClickListener(this);

        binding.btnConsole.setText(Helper.getResString(R.string.design_library_admob_button_goto_console));
        binding.btnConsole.setOnClickListener(this);

        configure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_admob_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setIcon(R.drawable.delete_96);
        dialog.setTitle(Helper.getResString(R.string.common_word_warning));
        dialog.setMessage(Helper.getResString(R.string.design_library_admob_dialog_description_confirm_uncheck));
        dialog.setCancelable(false);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (v, which) -> {
            if (!mB.a()) {
                admobLibraryBean.useYn = "N";
                binding.libSwitch.setChecked(false);
                v.dismiss();
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private void downloadChromeDialog() {
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setIcon(R.drawable.chrome_96);
        dialog.setTitle(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.setMessage(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_ok), (v, which) -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                v.dismiss();
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
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
        binding.libSwitch.setChecked("Y".equals(admobLibraryBean.useYn));
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