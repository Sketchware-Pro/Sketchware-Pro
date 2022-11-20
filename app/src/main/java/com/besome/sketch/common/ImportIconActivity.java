package com.besome.sketch.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;

import a.a.a.KB;
import a.a.a.MA;
import a.a.a.WB;
import a.a.a.iB;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.xB;

public class ImportIconActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private RecyclerView iconsList;
    private Button showBlackIcons;
    private Button showGreyIcons;
    private Button showWhiteIcons;
    private EditText iconName;
    private WB iconNameValidator;
    private IconAdapter adapter = null;
    /**
     * Current icons' color, where 0 stands for black, 1 for grey, and 2 for white.
     */
    private int iconType = -1;
    private ArrayList<Pair<String, String>> icons = new ArrayList<>();

    private int getGridLayoutColumnCount() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 60;
    }

    private boolean doExtractedIconsExist() {
        return new oB().e(wq.getExtractedIconPackStoreLocation());
    }

    private void extractIcons() {
        KB.a(this, "icons" + File.separator + "icon_pack.zip", wq.getExtractedIconPackStoreLocation());
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();

            if (id == R.id.btn_accept) {
                if (iconNameValidator.b() && adapter.selectedIconPosition >= 0) {
                    Intent intent = new Intent();
                    intent.putExtra("iconName", iconName.getText().toString());
                    intent.putExtra("iconPath", icons.get(adapter.selectedIconPosition).second);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            } else if (id == R.id.btn_black) {
                setIconColor(0);
            } else if (id == R.id.btn_cancel) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            } else if (id == R.id.btn_grey) {
                setIconColor(1);
            } else if (id == R.id.btn_white) {
                setIconColor(2);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((GridLayoutManager) iconsList.getLayoutManager()).d(getGridLayoutColumnCount());
        iconsList.requestLayout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_icon);

        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), R.string.design_manager_icon_actionbar_title));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        ArrayList<String> alreadyAddedImageNames = getIntent().getStringArrayListExtra("imageNames");
        Button save = findViewById(R.id.btn_accept);
        save.setText(xB.b().a(getApplicationContext(), R.string.common_word_accept));
        Button cancel = findViewById(R.id.btn_cancel);
        cancel.setText(xB.b().a(getApplicationContext(), R.string.common_word_cancel));
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        iconsList = findViewById(R.id.image_list);
        iconsList.setHasFixedSize(true);
        iconsList.setLayoutManager(new GridLayoutManager(getBaseContext(), getGridLayoutColumnCount()));
        adapter = new IconAdapter();
        iconsList.setAdapter(adapter);
        showBlackIcons = findViewById(R.id.btn_black);
        showGreyIcons = findViewById(R.id.btn_grey);
        showWhiteIcons = findViewById(R.id.btn_white);
        showBlackIcons.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_import_icon_button_black));
        showGreyIcons.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_import_icon_button_grey));
        showWhiteIcons.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_import_icon_button_white));
        showBlackIcons.setOnClickListener(this);
        showGreyIcons.setOnClickListener(this);
        showWhiteIcons.setOnClickListener(this);
        iconName = findViewById(R.id.ed_input);
        ((TextInputLayout) findViewById(R.id.ti_input)).setHint(xB.b().a(getApplicationContext(), R.string.design_manager_icon_hint_enter_icon_name));
        iconNameValidator = new WB(getApplicationContext(), findViewById(R.id.ti_input), uq.b, alreadyAddedImageNames);
        iconName.setPrivateImeOptions("defaultInputmode=english;");
        k();
        new Handler().postDelayed(() -> new InitialIconLoader(getApplicationContext()).execute(), 300L);
    }

    private void setIconName(int iconPosition) {
        iconName.setText(icons.get(iconPosition).first);
    }

    private void setIconColor(int colorType) {
        if (iconType != colorType) {
            iconType = colorType;
            if (colorType == 0) {
                showBlackIcons.setBackgroundColor(0xff33b8f5);
                showGreyIcons.setBackgroundColor(0xffe5e5e5);
                showWhiteIcons.setBackgroundColor(0xffe5e5e5);
            } else if (colorType == 1) {
                showBlackIcons.setBackgroundColor(0xffe5e5e5);
                showGreyIcons.setBackgroundColor(0xff33b8f5);
                showWhiteIcons.setBackgroundColor(0xffe5e5e5);
            } else if (colorType == 2) {
                showBlackIcons.setBackgroundColor(0xffe5e5e5);
                showGreyIcons.setBackgroundColor(0xffe5e5e5);
                showWhiteIcons.setBackgroundColor(0xff33b8f5);
            }
            new IconColorChangedIconLoader(getApplicationContext()).execute();
        }
    }

    private void listIcons() {
        icons = new ArrayList<>();
        String color = "black";
        if (iconType != 0) {
            if (iconType == 1) {
                color = "grey";
            } else if (iconType == 2) {
                color = "white";
            }
        }
        String iconFolderName = "icon_" + color;
        for (String iconName : new File(wq.getExtractedIconPackStoreLocation() + File.separator + iconFolderName).list()) {
            icons.add(new Pair<>(
                    iconName.substring(0, iconName.indexOf("_" + color)) + "_" + color,
                    wq.getExtractedIconPackStoreLocation() + File.separator + iconFolderName + File.separator + iconName
            ));
        }
    }

    private class IconAdapter extends RecyclerView.a<IconAdapter.ViewHolder> {

        private int selectedIconPosition = -1;

        private class ViewHolder extends RecyclerView.v {

            public final RelativeLayout background;
            public final TextView name;
            public final ImageView icon;

            public ViewHolder(View itemView) {
                super(itemView);
                background = itemView.findViewById(R.id.icon_bg);
                name = itemView.findViewById(R.id.tv_icon_name);
                icon = itemView.findViewById(R.id.img);
                icon.setOnClickListener(v -> {
                    if (!mB.a()) {
                        int lastSelectedPosition = selectedIconPosition;
                        selectedIconPosition = j();
                        // RecyclerView.Adapter<VH extends ViewHolder>#notifyItemChanged(int)
                        IconAdapter.this.c(selectedIconPosition);
                        // RecyclerView.Adapter<VH extends ViewHolder>#notifyItemChanged(int)
                        IconAdapter.this.c(lastSelectedPosition);
                        setIconName(selectedIconPosition);
                    }
                });
            }
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(ViewHolder holder, int position) {
            if (position != selectedIconPosition) {
                if (iconType == 2) {
                    holder.background.setBackgroundColor(0xffbdbdbd);
                } else {
                    holder.background.setBackgroundColor(Color.WHITE);
                }
            } else {
                holder.background.setBackgroundColor(0xffffccbc);
            }
            holder.name.setText(icons.get(position).first);
            try {
                holder.icon.setImageBitmap(iB.a(icons.get(position).second, 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.import_icon_list_item, parent, false));
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return icons.size();
        }
    }

    private class InitialIconLoader extends MA {
        public InitialIconLoader(Context context) {
            super(context);
            ImportIconActivity.this.a(this);
        }

        @Override
        public void a() {
            h();
            setIconColor(0);
        }

        @Override
        public void b() {
            if (!doExtractedIconsExist()) {
                extractIcons();
            }
        }

        @Override
        public void a(String str) {
            h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    private class IconColorChangedIconLoader extends MA {
        public IconColorChangedIconLoader(Context context) {
            super(context);
            ImportIconActivity.this.a(this);
            k();
        }

        @Override
        public void a() {
            h();
            iconName.setText("");
            adapter.selectedIconPosition = -1;
            // RecyclerView.Adapter<VH extends ViewHolder>#notifyDataSetChanged()
            adapter.c();
        }

        @Override
        public void b() {
            listIcons();
        }

        @Override
        public void a(String str) {
            h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
