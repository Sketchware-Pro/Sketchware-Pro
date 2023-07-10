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

import androidx.annotation.NonNull;
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

    private static final int ICON_COLOR_BLACK = 0;
    private static final int ICON_COLOR_GREY = 1;
    private static final int ICON_COLOR_WHITE = 2;
    
    private RecyclerView iconsList;
    private Button showBlackIcons;
    private Button showGreyIcons;
    private Button showWhiteIcons;
    private EditText iconName;
    private WB iconNameValidator;
    private SearchView searchView;
    private IconAdapter adapter = null;
    /**
     * Current icons' color, where 0 stands for black, 1 for grey, and 2 for white.
     */
    private int iconType = -1;
    private ArrayList<Pair<String, String>> icons = new ArrayList<>();
    private ArrayList<Pair<String, String>> filteredIcons = new ArrayList<>();

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
                    intent.putExtra("iconPath", filteredIcons.get(adapter.selectedIconPosition).second);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            } else if (id == R.id.btn_black) {
                setIconColor(ICON_COLOR_BLACK);
            } else if (id == R.id.btn_cancel) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            } else if (id == R.id.btn_grey) {
                setIconColor(ICON_COLOR_GREY);
            } else if (id == R.id.btn_white) {
                setIconColor(ICON_COLOR_WHITE);
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((GridLayoutManager) iconsList.getLayoutManager()).setSpanCount(getGridLayoutColumnCount());
        iconsList.requestLayout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_icon);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_manager_icon_actionbar_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contents_search_menu, menu);
        MenuItem mSearch = menu.findItem(R.id.menu_find);
        searchView = (SearchView) mSearch.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterIcons(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setIconName(int iconPosition) {
        iconName.setText(filteredIcons.get(iconPosition).first);
    }

    private void setIconColor(int colorType) {
        if (iconType != colorType) {
            iconType = colorType;
            if (colorType == ICON_COLOR_BLACK) {
                showBlackIcons.setBackgroundColor(0xff33b8f5);
                showGreyIcons.setBackgroundColor(0xffe5e5e5);
                showWhiteIcons.setBackgroundColor(0xffe5e5e5);
            } else if (colorType == ICON_COLOR_GREY) {
                showBlackIcons.setBackgroundColor(0xffe5e5e5);
                showGreyIcons.setBackgroundColor(0xff33b8f5);
                showWhiteIcons.setBackgroundColor(0xffe5e5e5);
            } else if (colorType == ICON_COLOR_WHITE) {
                showBlackIcons.setBackgroundColor(0xffe5e5e5);
                showGreyIcons.setBackgroundColor(0xffe5e5e5);
                showWhiteIcons.setBackgroundColor(0xff33b8f5);
            }
            new IconColorChangedIconLoader(getApplicationContext()).execute();
        }
    }

    private void listIcons() {
        icons = new ArrayList<>();
        String color = switch (iconType) {
            case ICON_COLOR_BLACK -> "black";
            case ICON_COLOR_GREY -> "grey";
            case ICON_COLOR_WHITE -> "white";
            default -> "black";
        };
        String iconFolderName = "icon_" + color;
        String iconPackStoreLocation = wq.getExtractedIconPackStoreLocation();
        try (Stream<Path> iconFiles = Files.list(Paths.get(iconPackStoreLocation, iconFolderName))) {
            iconFiles.map(Path::getFileName)
                .map(Path::toString)
                .forEach(iconName -> icons.add(new Pair<>(
                    iconName.substring(0, iconName.indexOf("_" + color)) + "_" + color,
                    Paths.get(iconPackStoreLocation, iconFolderName, iconName).toString()
                )));
        } catch (IOException e) {
            e.printStackTrace();
        }
        filteredIcons = new ArrayList<>(icons);
    }
    
    private void filterIcons(String query) {
        filteredIcons.clear();
        for (Pair<String, String> icon : icons) {
            if (icon.first.toLowerCase().contains(query.toLowerCase())) {
                filteredIcons.add(icon);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {

        private int selectedIconPosition = -1;

        private class ViewHolder extends RecyclerView.ViewHolder {

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
                        selectedIconPosition = getLayoutPosition();
                        notifyItemChanged(selectedIconPosition);
                        notifyItemChanged(lastSelectedPosition);
                        setIconName(selectedIconPosition);
                    }
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (position != selectedIconPosition) {
                if (iconType == ICON_COLOR_WHITE) {
                    holder.background.setBackgroundColor(0xffbdbdbd);
                } else {
                    holder.background.setBackgroundColor(Color.WHITE);
                }
            } else {
                holder.background.setBackgroundColor(0xffffccbc);
            }
            holder.name.setText(filteredIcons.get(position).first);
            try {
                holder.icon.setImageBitmap(iB.a(filteredIcons.get(position).second, 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.import_icon_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return filteredIcons.size();
        }
    }

    private class InitialIconLoader extends MA {
        public InitialIconLoader(Context context) {
            super(context);
            addTask(this);
        }

        @Override
        public void a() {
            h();
            setIconColor(ICON_COLOR_BLACK);
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
            addTask(this);
            k();
        }

        @Override
        public void a() {
            h();
            iconName.setText("");
            adapter.selectedIconPosition = -1;
            adapter.notifyDataSetChanged();
        }

        @Override
        public void b() {
            listIcons();
            runOnUiThread(() -> {
                if (searchView != null) {
                    searchView.setQuery("", false);
                }
            });
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
