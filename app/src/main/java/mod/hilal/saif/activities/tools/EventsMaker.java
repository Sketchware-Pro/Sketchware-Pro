package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.events.EventsHandler;

public class EventsMaker extends Activity {

    public static final File EVENT_EXPORT_LOCATION = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/data/system/export/events/");
    public static final File EVENTS_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/data/system/events.json");
    public static final File LISTENERS_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/data/system/listeners.json");
    private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_attribute);
        setToolbar();
        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventsHandler.refreshCachedCustomEvents();
        EventsHandler.refreshCachedCustomListeners();
    }

    private void setupViews() {
        FloatingActionButton fab = findViewById(R.id.add_attr_fab);
        listView = findViewById(R.id.add_attr_listview);
        ViewGroup base = (ViewGroup) listView.getParent();
        LinearLayout newLayout = newLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0);
        newLayout.setBackgroundColor(Color.parseColor("#00000000"));
        newLayout.setPadding(
                (int) SketchwareUtil.getDip(8),
                (int) SketchwareUtil.getDip(8),
                (int) SketchwareUtil.getDip(8),
                (int) SketchwareUtil.getDip(8)
        );
        newLayout.setFocusable(false);
        newLayout.setGravity(16);
        newLayout.addView(newText("Listeners:", 16.0f, false, 0xff888888,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        base.addView(newLayout, 1);
        CardView newCard = newCard(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0);
        LinearLayout newLayout2 = newLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0);
        newCard.addView(newLayout2);
        makeup(newLayout2, 0x7f07043e, "Activity events", getNumOfEvents(""));
        base.addView(newCard, 1);
        newLayout2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), EventsMakerDetails.class);
            intent.putExtra("lis_name", "");
            startActivity(intent);
        });
        fab.setOnClickListener(v -> showAddDial());
        refreshList();
    }

    private void showAddDial() {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.add_new_listener, null);
        create.setView(inflate);
        create.setCanceledOnTouchOutside(true);
        create.requestWindowFeature(Window.FEATURE_NO_TITLE);
        create.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView save = inflate.findViewById(R.id.save);
        TextView cancel = inflate.findViewById(R.id.cancel);
        final EditText customImport = inflate.findViewById(R.id.customimport);
        final EditText code = inflate.findViewById(R.id.code);
        final EditText name = inflate.findViewById(R.id.name);
        final CheckBox separate = inflate.findViewById(R.id.separate);
        save.setOnClickListener(v -> {
            if (!name.getText().toString().equals("")) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", name.getText().toString());
                if (separate.isChecked()) {
                    hashMap.put("code", "//" + name.getText().toString() + "\n" + code.getText().toString());
                    hashMap.put("s", "true");
                } else {
                    hashMap.put("code", code.getText().toString());
                    hashMap.put("s", "false");
                }
                hashMap.put("imports", customImport.getText().toString());
                listMap.add(hashMap);
                addItem();
                create.dismiss();
                return;
            }
            SketchwareUtil.toastError("Invalid name!");
        });
        cancel.setOnClickListener(Helper.getDialogDismissListener(create));
        create.show();
    }

    private void a(View view, int i, int i2, boolean z) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{(float) i, (float) i, ((float) i) / 2.0f, ((float) i) / 2.0f, (float) i, (float) i, ((float) i) / 2.0f, ((float) i) / 2.0f});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        view.setElevation((float) i2);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void overrideEvents(String before, String after) {
        ArrayList<HashMap<String, Object>> events = new ArrayList<>();
        if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
            events = new Gson()
                    .fromJson(FileUtil.readFile(EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).get("listener").toString().equals(before)) {
                    events.get(i).put("listener", after);
                }
            }
        }
        FileUtil.writeFile(EVENTS_FILE.getAbsolutePath(), new Gson().toJson(events));
    }

    private void editItemDialog(final int position) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.add_new_listener, null);
        create.setView(inflate);
        create.setCanceledOnTouchOutside(true);
        create.requestWindowFeature(Window.FEATURE_NO_TITLE);
        create.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView save = inflate.findViewById(R.id.save);
        TextView cancel = inflate.findViewById(R.id.cancel);
        final EditText customImport = inflate.findViewById(R.id.customimport);
        final EditText code = inflate.findViewById(R.id.code);
        final EditText name = inflate.findViewById(R.id.name);
        final CheckBox separate = inflate.findViewById(R.id.separate);
        name.setText(listMap.get(position).get("name").toString());
        code.setText(listMap.get(position).get("code").toString());
        customImport.setText(listMap.get(position).get("imports").toString());
        if (listMap.get(position).containsKey("s") && listMap.get(position).get("s").toString().equals("true")) {
            separate.setChecked(true);
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(listMap.get(position).get("code").toString().split("\n")));
            if (arrayList.get(0).contains("//" + name.getText().toString())) {
                arrayList.remove(0);
            }
            String str = "";
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (str.equals("")) {
                    str = arrayList.get(i2);
                } else {
                    str = str.concat("\n").concat(arrayList.get(i2));
                }
            }
            code.setText(str);
        }
        save.setOnClickListener(v -> {
            if (!name.getText().toString().equals("")) {
                HashMap<String, Object> hashMap = listMap.get(position);
                overrideEvents((String) hashMap.get("name"), name.getText().toString());
                hashMap.put("name", name.getText().toString());
                if (separate.isChecked()) {
                    hashMap.put("code", "//" + name.getText().toString() + "\n" + code.getText().toString());
                    hashMap.put("s", "true");
                } else {
                    hashMap.put("code", code.getText().toString());
                    hashMap.put("s", "false");
                }
                hashMap.put("imports", customImport.getText().toString());
                FileUtil.writeFile(LISTENERS_FILE.getAbsolutePath(), new Gson().toJson(listMap));
                refreshList();
                create.dismiss();
                return;
            }
            SketchwareUtil.toastError("Invalid name!");
        });
        cancel.setOnClickListener(Helper.getDialogDismissListener(create));
        create.show();
    }

    private void refreshList() {
        listMap.clear();
        if (FileUtil.isExistFile(LISTENERS_FILE.getAbsolutePath())) {
            listMap = new Gson().fromJson(FileUtil.readFile(LISTENERS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            listView.setAdapter(new ListAdapter(listMap));
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    private void deleteItem(int position) {
        listMap.remove(position);
        FileUtil.writeFile(LISTENERS_FILE.getAbsolutePath(), new Gson().toJson(listMap));
        refreshList();
    }

    private void deleteRelatedEvents(String name) {
        ArrayList<HashMap<String, Object>> events = new ArrayList<>();
        if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
            events = new Gson()
                    .fromJson(FileUtil.readFile(EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            for (int i = events.size() - 1; i > -1; i--) {
                if (events.get(i).get("listener").toString().equals(name)) {
                    events.remove(i);
                }
            }
        }
        FileUtil.writeFile(EVENTS_FILE.getAbsolutePath(), new Gson().toJson(events));
    }

    private void addItem() {
        FileUtil.writeFile(LISTENERS_FILE.getAbsolutePath(), new Gson().toJson(listMap));
        refreshList();
    }

    private void openFileExplorerImport() {
        DialogProperties dialogProperties = new DialogProperties();
        dialogProperties.selection_mode = 0;
        dialogProperties.selection_type = 0;
        File file = new File(FileUtil.getExternalStorageDir());
        dialogProperties.root = file;
        dialogProperties.error_dir = file;
        dialogProperties.offset = file;
        dialogProperties.extensions = null;
        FilePickerDialog filePickerDialog = new FilePickerDialog(this, dialogProperties);
        filePickerDialog.setTitle("Select a .txt file");
        filePickerDialog.setDialogSelectionListener(selections -> {
            if (FileUtil.readFile(selections[0]).equals("")) {
                SketchwareUtil.toastError("The selected file is empty!");
            } else if (FileUtil.readFile(selections[0]).equals("[]")) {
                SketchwareUtil.toastError("The selected file is empty!");
            } else {
                try {
                    String[] split = FileUtil.readFile(selections[0]).split("\n");
                    importEvents(new Gson().fromJson(split[0], Helper.TYPE_MAP_LIST),
                            new Gson().fromJson(split[1], Helper.TYPE_MAP_LIST));
                } catch (Exception e) {
                    SketchwareUtil.toastError("Invalid file");
                }
            }
        });
        filePickerDialog.show();
    }

    private void importEvents(ArrayList<HashMap<String, Object>> data, ArrayList<HashMap<String, Object>> data2) {
        ArrayList<HashMap<String, Object>> events = new ArrayList<>();
        if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
            events = new Gson()
                    .fromJson(FileUtil.readFile(EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
        }
        events.addAll(data2);
        FileUtil.writeFile(EVENTS_FILE.getAbsolutePath(), new Gson().toJson(events));
        listMap.addAll(data);
        FileUtil.writeFile(LISTENERS_FILE.getAbsolutePath(), new Gson().toJson(listMap));
        refreshList();
        SketchwareUtil.toast("Successfully imported events");
    }

    private void exportAll() {
        ArrayList<HashMap<String, Object>> events = new ArrayList<>();
        if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
            events = new Gson().fromJson(FileUtil.readFile(EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
        }
        FileUtil.writeFile(new File(EVENT_EXPORT_LOCATION, "All_Events.txt").getAbsolutePath(),
                new Gson().toJson(listMap) + "\n" + new Gson().toJson(events));
        SketchwareUtil.toast("Successfully exported events to:\n" +
                "/Internal storage/.sketchware/data/system/export/events", Toast.LENGTH_LONG);
    }

    private void export(int p) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/export/events/");
        ArrayList<HashMap<String, Object>> ex = new ArrayList<>();
        ex.add(listMap.get(p));
        ArrayList<HashMap<String, Object>> ex2 = new ArrayList<>();
        if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
            ArrayList<HashMap<String, Object>> events = new Gson()
                    .fromJson(FileUtil.readFile(EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).get("listener").toString().equals(listMap.get(p).get("name"))) {
                    ex2.add(events.get(i));
                }
            }
        }
        FileUtil.writeFile(concat + ex.get(0).get("name").toString() + ".txt", new Gson().toJson(ex) + "\n" + new Gson().toJson(ex2));
        SketchwareUtil.toast("Successfully exported event to:\n" +
                "/Internal storage/.sketchware/data/system/export/events", Toast.LENGTH_LONG);
    }

    private String getNumOfEvents(String str) {
        int eventAmount;
        if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
            ArrayList<HashMap<String, Object>> events = new Gson()
                    .fromJson(FileUtil.readFile(EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            eventAmount = 0;
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).get("listener").toString().equals(str)) {
                    eventAmount++;
                }
            }
        } else {
            eventAmount = 0;
        }
        return "Events: " + eventAmount;
    }

    private void makeup(View view, int resIcon, String title, String description) {
        View inflate = getLayoutInflater().inflate(R.layout.manage_library_base_item, null);
        ImageView icon = inflate.findViewById(R.id.lib_icon);
        inflate.findViewById(R.id.tv_enable).setVisibility(View.GONE);
        icon.setImageResource(resIcon);
        ((LinearLayout) icon.getParent()).setGravity(Gravity.CENTER);
        ((TextView) inflate.findViewById(R.id.lib_title)).setText(title);
        ((TextView) inflate.findViewById(R.id.lib_desc)).setText(description);
        ((ViewGroup) view).addView(inflate);
    }

    private CardView newCard(int width, int height, float weight) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height, weight);
        layoutParams.setMargins(
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(6),
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(2)
        );
        cardView.setLayoutParams(layoutParams);
        cardView.setPadding(
                (int) SketchwareUtil.getDip(2),
                (int) SketchwareUtil.getDip(2),
                (int) SketchwareUtil.getDip(2),
                (int) SketchwareUtil.getDip(2)
        );
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setRadius(SketchwareUtil.getDip(4));
        return cardView;
    }

    private LinearLayout newLayout(int width, int height, float weight) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
        linearLayout.setPadding(
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(4)
        );
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);
        linearLayout.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#64B5F6")}), gradientDrawable, null));
        linearLayout.setClickable(true);
        linearLayout.setFocusable(true);
        return linearLayout;
    }

    private TextView newText(String str, float size, boolean is, int color, int width, int length, float weight) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(width, length, weight));
        textView.setPadding(
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(4)
        );
        textView.setTextColor(color);
        textView.setText(str);
        textView.setTextSize(size);
        if (is) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        return textView;
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("Event manager");
        ImageView back_icon = findViewById(R.id.ig_toolbar_back);
        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back_icon);
        final ImageView more_icon = findViewById(R.id.ig_toolbar_load_file);
        more_icon.setVisibility(View.VISIBLE);
        more_icon.setImageResource(R.drawable.ic_more_vert_white_24dp);
        more_icon.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, more_icon);
            final Menu menu = popupMenu.getMenu();
            menu.add("Import events");
            menu.add("Export events");
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getTitle().toString()) {
                    case "Import events":
                        openFileExplorerImport();
                        break;

                    case "Export events":
                        exportAll();
                        SketchwareUtil.toast("Successfully exported events to:\n" +
                                "/Internal storage/.sketchware/data/system/export/events", Toast.LENGTH_LONG);
                        break;

                    default:
                        return false;
                }
                return true;
            });
            popupMenu.show();
        });
        Helper.applyRippleToToolbarView(more_icon);
    }

    private class ListAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

        public ListAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return _data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_view_pro, parent, false);
            }
            LinearLayout linearLayout = convertView.findViewById(R.id.custom_view_pro_background);
            a(linearLayout, (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), true);
            ImageView imageView = convertView.findViewById(R.id.custom_view_pro_img);
            TextView textView = convertView.findViewById(R.id.custom_view_pro_title);
            imageView.setImageResource(R.drawable.event_on_response_48dp);
            ((LinearLayout) imageView.getParent()).setGravity(Gravity.CENTER);
            textView.setText((String) _data.get(position).get("name"));
            ((TextView) convertView.findViewById(R.id.custom_view_pro_subtitle)).setText(getNumOfEvents(textView.getText().toString()));
            linearLayout.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), EventsMakerDetails.class);
                intent.putExtra("lis_name", (String) _data.get(position).get("name"));
                startActivity(intent);
            });
            linearLayout.setOnLongClickListener(v -> {
                new AlertDialog.Builder(EventsMaker.this)
                        .setTitle(_data.get(position).get("name").toString())
                        .setItems(new String[]{"Edit", "Export", "Delete"}, (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    editItemDialog(position);
                                    break;

                                case 1:
                                    export(1);
                                    break;

                                case 2:
                                    deleteRelatedEvents(_data.get(position).get("name").toString());
                                    deleteItem(position);
                                    break;

                                default:
                            }
                        })
                        .show();

                return true;
            });

            return convertView;
        }
    }
}
