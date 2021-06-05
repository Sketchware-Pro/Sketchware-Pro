package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class EventsMaker extends Activity {

    private ViewGroup base;
    private AlertDialog.Builder dia;
    private FloatingActionButton fab;
    private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427795);
        setToolbar();
        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    public void setupViews() {
        fab = findViewById(2131232439);
        listView = findViewById(2131232438);
        base = (ViewGroup) listView.getParent();
        LinearLayout newLayout = newLayout(-1, -2, 0.0f);
        newLayout.setBackgroundColor(Color.parseColor("#00000000"));
        newLayout.setPadding((int) SketchwareUtil.getDip(8), (int) SketchwareUtil.getDip(8), (int) SketchwareUtil.getDip(8), (int) SketchwareUtil.getDip(8));
        newLayout.setFocusable(false);
        newLayout.setGravity(16);
        newLayout.addView(newText("Listeners:", 16.0f, false, -7829368, -2, -2, 0.0f));
        base.addView(newLayout, 1);
        CardView newCard = newCard(-1, -2, 0.0f);
        LinearLayout newLayout2 = newLayout(-1, -1, 0.0f);
        newCard.addView(newLayout2);
        makeup(newLayout2, 2131166270, "Activity events", getNumOfEvents(""));
        base.addView(newCard, 1);
        newLayout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), EventsMakerDetails.class);
                intent.putExtra("lis_name", "");
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAddDial();
            }
        });
        refreshList();
    }

    public void showAddDial() {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427816, null);
        create.setView(inflate);
        create.setCanceledOnTouchOutside(true);
        create.requestWindowFeature(1);
        create.getWindow().setLayout(-1, -1);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView textView = inflate.findViewById(2131232528);
        TextView textView2 = inflate.findViewById(2131232351);
        final EditText editText = inflate.findViewById(2131232590);
        final EditText editText2 = inflate.findViewById(2131232578);
        final EditText editText3 = inflate.findViewById(2131231561);
        final CheckBox checkBox = inflate.findViewById(2131232620);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!editText3.getText().toString().equals("")) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", editText3.getText().toString());
                    if (checkBox.isChecked()) {
                        hashMap.put("code", "//" + editText3.getText().toString() + "\n" + editText2.getText().toString());
                        hashMap.put("s", "true");
                    } else {
                        hashMap.put("code", editText2.getText().toString());
                        hashMap.put("s", "false");
                    }
                    hashMap.put("imports", editText.getText().toString());
                    listMap.add(hashMap);
                    addItem();
                    create.dismiss();
                    return;
                }
                SketchwareUtil.toastError("Invalid name!");
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                create.dismiss();
            }
        });
        create.show();
    }

    public void a(View view, int i, int i2, boolean z) {
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

    public void overrideEvents(String str, String str2) {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).get("listener").toString().equals(str)) {
                    arrayList.get(i).put("listener", str2);
                }
            }
        }
        FileUtil.writeFile(concat, new Gson().toJson(arrayList));
    }

    public void editItemDialog(final int position) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427816, null);
        create.setView(inflate);
        create.setCanceledOnTouchOutside(true);
        create.requestWindowFeature(1);
        create.getWindow().setLayout(-1, -1);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView textView = inflate.findViewById(2131232528);
        TextView textView2 = inflate.findViewById(2131232351);
        final EditText editText = inflate.findViewById(2131232590);
        final EditText editText2 = inflate.findViewById(2131232578);
        final EditText editText3 = inflate.findViewById(2131231561);
        final CheckBox checkBox = inflate.findViewById(2131232620);
        editText3.setText(listMap.get(position).get("name").toString());
        editText2.setText(listMap.get(position).get("code").toString());
        editText.setText(listMap.get(position).get("imports").toString());
        if (listMap.get(position).containsKey("s") && listMap.get(position).get("s").toString().equals("true")) {
            checkBox.setChecked(true);
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(listMap.get(position).get("code").toString().split("\n")));
            if (arrayList.get(0).contains("//" + editText3.getText().toString())) {
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
            editText2.setText(str);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!editText3.getText().toString().equals("")) {
                    HashMap<String, Object> hashMap = listMap.get(position);
                    overrideEvents((String) hashMap.get("name"), editText3.getText().toString());
                    hashMap.put("name", editText3.getText().toString());
                    if (checkBox.isChecked()) {
                        hashMap.put("code", "//" + editText3.getText().toString() + "\n" + editText2.getText().toString());
                        hashMap.put("s", "true");
                    } else {
                        hashMap.put("code", editText2.getText().toString());
                        hashMap.put("s", "false");
                    }
                    hashMap.put("imports", editText.getText().toString());
                    FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json"), new Gson().toJson(listMap));
                    refreshList();
                    create.dismiss();
                    return;
                }
                SketchwareUtil.toastError("Invalid name!");
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                create.dismiss();
            }
        });
        create.show();
    }

    public void refreshList() {
        listMap.clear();
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json");
        if (FileUtil.isExistFile(concat)) {
            listMap = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            listView.setAdapter(new ListAdapter(listMap));
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    public void deleteItem(int i) {
        listMap.remove(i);
        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json"), new Gson().toJson(listMap));
        refreshList();
    }

    public void deleteRelatedEvents(String str) {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            for (int size = arrayList.size() - 1; size > -1; size--) {
                if (arrayList.get(size).get("listener").toString().equals(str)) {
                    arrayList.remove(size);
                }
            }
        }
        FileUtil.writeFile(concat, new Gson().toJson(arrayList));
    }

    public void addItem() {
        FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json"), new Gson().toJson(listMap));
        refreshList();
    }

    public void openFileExplorerImport() {
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
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] selections) {
                if (FileUtil.readFile(selections[0]).equals("")) {
                    SketchwareUtil.toastError("The selected file is empty!");
                } else if (FileUtil.readFile(selections[0]).equals("[]")) {
                    SketchwareUtil.toastError("The selected file is empty!");
                } else {
                    try {
                        String[] split = FileUtil.readFile(selections[0]).split("\n");
                        importEvents((ArrayList<HashMap<String, Object>>) new Gson().fromJson(split[0], Helper.TYPE_MAP_LIST), (ArrayList<HashMap<String, Object>>) new Gson().fromJson(split[1], Helper.TYPE_MAP_LIST));
                    } catch (Exception e) {
                        SketchwareUtil.toastError("Invalid file");
                    }
                }
            }
        });
        filePickerDialog.show();
    }

    public void importEvents(ArrayList<HashMap<String, Object>> arrayList, ArrayList<HashMap<String, Object>> arrayList2) {
        ArrayList<HashMap<String, Object>> arrayList3;
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        String concat2 = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json");
        ArrayList<HashMap<String, Object>> arrayList4 = new ArrayList<>();
        if (FileUtil.isExistFile(concat)) {
            arrayList3 = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
        } else {
            arrayList3 = arrayList4;
        }
        arrayList3.addAll(arrayList2);
        FileUtil.writeFile(concat, new Gson().toJson(arrayList3));
        listMap.addAll(arrayList);
        FileUtil.writeFile(concat2, new Gson().toJson(listMap));
        refreshList();
        SketchwareUtil.toast("Successfully imported events");
    }

    public void exportAll() {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/export/events/");
        String concat2 = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        if (FileUtil.isExistFile(concat2)) {
            arrayList = new Gson().fromJson(FileUtil.readFile(concat2), Helper.TYPE_MAP_LIST);
        }
        FileUtil.writeFile(concat + "All_Events" + ".txt", new Gson().toJson(listMap) + "\n" + new Gson().toJson(arrayList));
        SketchwareUtil.toast("Successfully exported events to:\n/Internal storage/.sketchware/data/system/export/events");
    }

    public void export(int i) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/export/events/");
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        arrayList.add(listMap.get(i));
        ArrayList<HashMap<String, Object>> arrayList2 = new ArrayList<>();
        String concat2 = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat2)) {
            ArrayList<HashMap<String, Object>> arrayList3 = new Gson().fromJson(FileUtil.readFile(concat2), Helper.TYPE_MAP_LIST);
            for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                if (arrayList3.get(i2).get("listener").toString().equals(listMap.get(i).get("name"))) {
                    arrayList2.add(arrayList3.get(i2));
                }
            }
        }
        FileUtil.writeFile(concat + arrayList.get(0).get("name").toString() + ".txt", new Gson().toJson(arrayList) + "\n" + new Gson().toJson(arrayList2));
        SketchwareUtil.toast("Successfully exported event to:\n/Internal storage/.sketchware/data/system/export/events");
    }

    public String getNumOfEvents(String str) {
        int i;
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            ArrayList<HashMap<String, Object>> arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            i = 0;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (arrayList.get(i2).get("listener").toString().equals(str)) {
                    i++;
                }
            }
        } else {
            i = 0;
        }
        return "Events: ".concat(String.valueOf(i));
    }

    public void makeup(View view, int i, String str, String str2) {
        View inflate = getLayoutInflater().inflate(2131427537, null);
        LinearLayout linearLayout = inflate.findViewById(2131230931);
        ImageView imageView = inflate.findViewById(2131231428);
        inflate.findViewById(2131231965).setVisibility(View.GONE);
        imageView.setImageResource(i);
        ((LinearLayout) imageView.getParent()).setGravity(17);
        ((TextView) inflate.findViewById(2131231430)).setText(str);
        ((TextView) inflate.findViewById(2131231427)).setText(str2);
        ((ViewGroup) view).addView(inflate);
    }

    public TextView getTextClone(String str, float f, String str2) {
        TextView textView = getLayoutInflater().inflate(2131427796, null).findViewById(2131232441);
        textView.setText(str);
        textView.setTextColor(Color.parseColor(str2));
        textView.setTextSize(f);
        ((LinearLayout) textView.getParent()).removeView(textView);
        return textView;
    }

    public CardView newCard(int i, int i2, float f) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i2, f);
        layoutParams.setMargins((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(6), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2));
        cardView.setLayoutParams(layoutParams);
        cardView.setPadding((int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2));
        cardView.setCardBackgroundColor(-1);
        cardView.setRadius(SketchwareUtil.getDip(4));
        return cardView;
    }

    public LinearLayout newLayout(int i, int i2, float f) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(i, i2, f));
        linearLayout.setPadding((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-1);
        linearLayout.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#64B5F6")}), gradientDrawable, null));
        linearLayout.setClickable(true);
        linearLayout.setFocusable(true);
        return linearLayout;
    }

    public TextView newText(String str, float f, boolean z, int i, int i2, int i3, float f2) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(i2, i3, f2));
        textView.setPadding((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4));
        textView.setTextColor(i);
        textView.setText(str);
        textView.setTextSize(f);
        if (z) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        return textView;
    }

    public void setToolbar() {
        ((TextView) findViewById(2131232458)).setText("Event manager");
        ImageView back_icon = findViewById(2131232457);
        back_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back_icon);
        final ImageView more_icon = findViewById(2131232459);
        more_icon.setVisibility(View.VISIBLE);
        more_icon.setImageResource(2131165791);
        more_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(EventsMaker.this, more_icon);
                final Menu menu = popupMenu.getMenu();
                menu.add("Import events");
                menu.add("Export events");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Import events":
                                openFileExplorerImport();
                                break;

                            case "Export events":
                                exportAll();
                                SketchwareUtil.toast("Successfully exported events to:\n/Internal storage/.sketchware/data/system/export/events");
                                break;

                            default:
                                return false;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        Helper.applyRippleToToolbarView(more_icon);
    }

    public class ListAdapter extends BaseAdapter {

        final ArrayList<HashMap<String, Object>> _data;

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
                convertView = getLayoutInflater().inflate(2131427802, null);
            }
            LinearLayout linearLayout = convertView.findViewById(2131232468);
            a(linearLayout, (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), true);
            ImageView imageView = convertView.findViewById(2131232469);
            TextView textView = convertView.findViewById(2131232470);
            imageView.setImageResource(2131165593);
            ((LinearLayout) imageView.getParent()).setGravity(17);
            textView.setText((String) _data.get(position).get("name"));
            ((TextView) convertView.findViewById(2131232471)).setText(getNumOfEvents(textView.getText().toString()));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), EventsMakerDetails.class);
                    intent.putExtra("lis_name", (String) _data.get(position).get("name"));
                    startActivity(intent);
                }
            });
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EventsMaker.this)
                            .setTitle(_data.get(position).get("name").toString())
                            .setItems(new String[]{"Edit", "Export", "Delete"}, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        editItemDialog(position);
                                    }
                                    if (which == 1) {
                                        export(position);
                                    }
                                    if (which == 2) {
                                        deleteRelatedEvents(_data.get(position).get("name").toString());
                                        deleteItem(position);
                                    }
                                }
                            });
                    builder.create().show();
                    return true;
                }
            });
            return convertView;
        }
    }
}