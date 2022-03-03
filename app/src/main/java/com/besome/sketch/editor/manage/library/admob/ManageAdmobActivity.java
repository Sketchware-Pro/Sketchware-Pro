package com.besome.sketch.editor.manage.library.admob;

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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.AdTestDeviceBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseSessionAppCompatActivity;
import com.sketchware.remod.Resources;

import java.util.ArrayList;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.mB;
import mod.hey.studios.util.Helper;

public class ManageAdmobActivity extends BaseSessionAppCompatActivity implements View.OnClickListener {

    public DB A = null;
    public TestDeviceAdapter testDeviceAdapter;
    public ArrayList<AdTestDeviceBean> testDeviceList = new ArrayList<>();
    public LinearLayout p;
    public LinearLayout switchLayout;
    public Switch libSwitch;
    public TextView tvBannerName;
    public TextView tvBannerId;
    public TextView tvInterName;
    public TextView tvInterId;
    public RecyclerView listTestDevice;
    public Toolbar toolbar;
    public Button btnConsole;
    public ProjectLibraryBean admobLibraryBean;
    private final int n = 8001;
    private final int o = 8002;

    @Override
    public void a(int requestCode, String idk) {
        if (requestCode == 8001 || requestCode == 8002) {
            n(requestCode);
        }
    }

    private void initializeLibrary(ProjectLibraryBean libraryBean) {
        admobLibraryBean = libraryBean;
        configure();
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

    @Override
    public void g(int idk) {
    }

    @Override
    public void h(int idk) {
    }

    @Override
    public void l() {
    }

    @Override
    public void m() {
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
            bB.a(getApplicationContext(), Helper.getResString(Resources.string.common_message_check_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void n(int requestCode) {
        if (requestCode == 8001) {
            libSwitch.setChecked(true);
            admobLibraryBean.useYn = "Y";
        } else {
            toAdmobActivity();
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
                case 8001:
                    libSwitch.setChecked(true);
                    admobLibraryBean.useYn = "Y";
                    break;
                case 8002:
                    toAdmobActivity();
                    break;
            }
        }
    }

    public void onBackPressed() {
        getIntent().putExtra("admob", admobLibraryBean);
        setResult(RESULT_OK, getIntent());
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            switch (v.getId()) {
                case Resources.id.btn_console:
                    n();
                    break;

                case Resources.id.layout_switch:
                    if (!libSwitch.isChecked()) {
                        i(8001);
                    } else {
                        libSwitch.setChecked(!libSwitch.isChecked());
                        if ("Y".equals(admobLibraryBean.useYn) && !libSwitch.isChecked()) {
                            configureLibrary();
                        } else {
                            admobLibraryBean.useYn = "Y";
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.manage_library_manage_admob);
        p = findViewById(Resources.id.layout_content);
        toolbar = findViewById(Resources.id.toolbar);
        a(toolbar);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(Helper.getResString(Resources.string.design_library_admob_title_admob_manager));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        A = new DB(this.getApplicationContext(), "P1");
        admobLibraryBean = getIntent().getParcelableExtra("admob");
        ((TextView) findViewById(Resources.id.tv_enable)).setText(Helper.getResString(Resources.string.design_library_settings_title_enabled));
        ((TextView) findViewById(Resources.id.tv_title_banner)).setText(Helper.getResString(Resources.string.design_library_admob_title_banner));
        ((TextView) findViewById(Resources.id.tv_title_banner_name)).setText(Helper.getResString(Resources.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(Resources.id.tv_title_banner_id)).setText(Helper.getResString(Resources.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(Resources.id.tv_title_inter)).setText(Helper.getResString(Resources.string.design_library_admob_title_interstitial));
        ((TextView) findViewById(Resources.id.tv_title_inter_name)).setText(Helper.getResString(Resources.string.design_library_admob_title_ad_name) + " : ");
        ((TextView) findViewById(Resources.id.tv_title_inter_id)).setText(Helper.getResString(Resources.string.design_library_admob_title_ad_unit_id) + " : ");
        ((TextView) findViewById(Resources.id.tv_title_test_device)).setText(Helper.getResString(Resources.string.design_library_admob_dialog_set_test_device_title));
        tvBannerId = findViewById(Resources.id.tv_banner_id);
        tvBannerName = findViewById(Resources.id.tv_banner_name);
        tvInterId = findViewById(Resources.id.tv_inter_id);
        tvInterName = findViewById(Resources.id.tv_inter_name);
        listTestDevice = findViewById(Resources.id.list_test_device);
        listTestDevice.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        testDeviceAdapter = new TestDeviceAdapter();
        listTestDevice.setAdapter(testDeviceAdapter);
        libSwitch = findViewById(Resources.id.lib_switch);
        switchLayout = findViewById(Resources.id.layout_switch);
        switchLayout.setOnClickListener(this);
        btnConsole = findViewById(Resources.id.btn_console);
        btnConsole.setText(Helper.getResString(Resources.string.design_library_admob_button_goto_console));
        btnConsole.setOnClickListener(this);
        configure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(Resources.menu.manage_admob_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Resources.id.menu_admob_help:
                o();
                break;
            case Resources.id.menu_admob_settings:
                i(8002);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void configureLibrary() {
        final aB dialog = new aB(this);
        dialog.a(Resources.drawable.delete_96);
        dialog.b(Helper.getResString(Resources.string.common_word_warning));
        dialog.a(Helper.getResString(Resources.string.design_library_admob_dialog_description_confirm_uncheck));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(Resources.string.common_word_delete), v -> {
            if (!mB.a()) {
                admobLibraryBean.useYn = "N";
                libSwitch.setChecked(false);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(Resources.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void downloadChromeDialog() {
        final aB dialog = new aB(this);
        dialog.a(Resources.drawable.chrome_96);
        dialog.b(Helper.getResString(Resources.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(Resources.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(Resources.string.common_word_ok), v -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(Resources.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void toAdmobActivity() {
        Intent intent = new Intent(getApplicationContext(), AdmobActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("admob", admobLibraryBean);
        startActivityForResult(intent, 236);
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(admobLibraryBean.useYn));
        setBannerAdUnit(admobLibraryBean.reserved1);
        setInterAdUnit(admobLibraryBean.reserved2);
        testDeviceList = admobLibraryBean.testDevices;
        testDeviceAdapter.c();
    }

    public class TestDeviceAdapter extends RecyclerView.a<TestDeviceAdapter.ViewHolder> {
        public int index = -1;

        @Override
        public int a() {
            return testDeviceList.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int index) {
            viewHolder.tvDeviceId.setText(testDeviceList.get(index).deviceId);
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(Resources.layout.manage_library_setting_admob_test_device_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {
            public TextView tvDeviceId;
            public ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                tvDeviceId = view.findViewById(Resources.id.tv_device_id);
                imageView = view.findViewById(Resources.id.img_delete);
                imageView.setVisibility(View.GONE);
            }
        }
    }
}
