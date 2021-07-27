package mod.hilal.saif.activities.android_manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.android_manifest.AndroidManifestInjector;
import mod.hilal.saif.android_manifest.AppComponentsDialog;
import mod.hilal.saif.asd.DialogButtonGradientDrawable;

import static mod.SketchwareUtil.getDip;

public class AndroidManifestInjection extends Activity {

    private final ArrayList<HashMap<String, Object>> list_map = new ArrayList<>();
    private ViewGroup base;
    private ListView act_list;
    private String src_id;
    private String activityName;
    private AlertDialog.Builder dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.view_events);
        RecyclerView dump = findViewById(Resources.id.list_events);
        base = (ViewGroup) dump.getParent();
        base.removeView(dump);
        if (getIntent().hasExtra("sc_id") && getIntent().hasExtra("file_name")) {
            src_id = getIntent().getStringExtra("sc_id");
            activityName = getIntent().getStringExtra("file_name").replaceAll(".java", "");
        }
        newToolbar(base);
        checkAttrs();
        setupViews();
        refreshList();
        checkAttrs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAttrs();
        refreshList();
    }

    private void checkAttrs() {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(src_id).concat("/Injection/androidmanifest/attributes.json");
        if (FileUtil.isExistFile(path)) {
            ArrayList<HashMap<String, Object>> data = new Gson().fromJson(FileUtil.readFile(path),
                    Helper.TYPE_MAP_LIST);
            for (int i = 0; i < data.size(); i++) {
                String str = (String) data.get(i).get("name");
                if (str.equals("_application_attrs")) {
                    String str2 = (String) data.get(i).get("value");
                    if (str2.contains("android:theme")) {
                        return;

                    }
                }
            }
            {
                HashMap<String, Object> _item = new HashMap<>();
                _item.put("name", "_application_attrs");
                _item.put("value", "android:theme=\"@style/AppTheme\"");
                data.add(_item);
            }
            FileUtil.writeFile(path, new Gson().toJson(data));
        }
    }

    private void setupViews() {

        CardView application_card = newCard(-1, -2, 0);
        LinearLayout application_skin = newLayout(-1, -1, 0);
        application_card.addView(application_skin);
        makeup(application_skin, 2131166366, "Application", "Default properties for the app");
        base.addView(application_card);
        //application pressed
        application_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AndroidManifestInjectionDetails.class);
                intent.putExtra("sc_id", src_id);
                intent.putExtra("file_name", activityName);
                intent.putExtra("type", "application");
                startActivity(intent);
            }
        });

        {
            CardView permission_card = newCard(-1, -2, 0);
            LinearLayout permission_skin = newLayout(-1, -1, 0);
            permission_card.addView(permission_skin);
            makeup(permission_skin, 0x7f07019b, "Permissions", "Add custom Permissions to the app");
            base.addView(permission_card);
            //application pressed
            permission_skin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    Intent inta = new Intent();
                    inta.setClass(getApplicationContext(), AndroidManifestInjectionDetails.class);
                    inta.putExtra("sc_id", src_id);
                    inta.putExtra("file_name", activityName);
                    inta.putExtra("type", "permission");
                    startActivity(inta);
                }
            });
        }

        {
            CardView permission_card = newCard(-1, -2, 0);
            LinearLayout permission_skin = newLayout(-1, -1, 0);
            permission_card.addView(permission_skin);
            makeup(permission_skin, 0x7f07035b, "Launcher Activity", "Change the default Launcher Activity");
            base.addView(permission_card);
            //that pressed
            permission_skin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLauncherActDialog(AndroidManifestInjector.getLauncherActivity(src_id));
                }
            });
        }

        CardView allAct_card = newCard(-1, -2, 0);
        LinearLayout allAct_skin = newLayout(-1, -1, 0);
        allAct_card.addView(allAct_skin);
        makeup(allAct_skin, 0x7f07049d, "All Activities", "Add attributes for all Activities");
        base.addView(allAct_card);
        /// all activities pressed
        allAct_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inta = new Intent();
                inta.setClass(getApplicationContext(), AndroidManifestInjectionDetails.class);
                inta.putExtra("sc_id", src_id);
                inta.putExtra("file_name", activityName);
                inta.putExtra("type", "all");
                startActivity(inta);
            }
        });

        CardView appCom_card = newCard(-1, -2, 0);
        LinearLayout appCom_skin = newLayout(-1, -1, 0);
        appCom_card.addView(appCom_skin);
        makeup(appCom_skin, 0x7f07049f, "App Components", "Add extra components");
        base.addView(appCom_card);
        ///app component pressed
        appCom_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppComponentDialog();
            }
        });

        LinearLayout sub_skin = newLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0);
        sub_skin.setBackgroundColor(0x00000000);
        sub_skin.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        sub_skin.setFocusable(false);
        sub_skin.setGravity(Gravity.CENTER_VERTICAL);
        TextView sub = newText("Activities:", 16, false, Color.GRAY, -2, -2, 0);
        sub_skin.addView(sub);
        base.addView(sub_skin);


        LinearLayout bottom = new LinearLayout(this);
        bottom.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f));
        bottom.setPadding(
                (int) getDip(1),
                0,
                (int) getDip(1),
                (int) getDip(1));
        act_list = new ListView(this);
        act_list.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));
        act_list.setDividerHeight(0);
        bottom.addView(act_list);
        base.addView(bottom);

        TextView addnew = new TextView(getApplicationContext());
        addnew.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getDip(45),
                0.0f));
        ((LinearLayout.LayoutParams) addnew.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        addnew.setText("Add Activity");
        addnew.setTextColor(Color.WHITE);
        addnew.setPadding(
                (int) getDip(50),
                (int) getDip(8),
                (int) getDip(50),
                (int) getDip(8));
        addnew.setGravity(Gravity.CENTER);
        addnew.setBackgroundColor(0xff008dcd);
        addnew.setTextSize(15);
        //save.setTypeface(save.getTypeface(), Typeface.BOLD);
        addnew.setBackground(new DialogButtonGradientDrawable()
                .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
        addnew.setElevation((int) getDip(1));
        base.addView(addnew);
        /// add new clicked
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddActivityDialog();
            }
        });
    }

    private void showAppComponentDialog() {
        new AppComponentsDialog(this, src_id).show();
    }

    private void showLauncherActDialog(String actnamr) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(Resources.layout.custom_dialog_attribute, null);
        create.setView(inflate);
        create.setCanceledOnTouchOutside(true);
        ///create.setCancelable(true);
        // ???
        create.getWindow().setBackgroundDrawableResource(0x106000d);
        final TextView btnSave = inflate.findViewById(Resources.id.dialog_btn_save);
        final TextView btnCancel = inflate.findViewById(Resources.id.dialog_btn_cancel);

        final EditText inputRes = inflate.findViewById(Resources.id.dialog_input_res);
        inputRes.setVisibility(View.GONE);
        final EditText inputAttr = inflate.findViewById(Resources.id.dialog_input_attr);
        inputAttr.setVisibility(View.GONE);
        final EditText inputValue = inflate.findViewById(Resources.id.dialog_input_value);
        final TextView textView = (TextView) ((ViewGroup) inputAttr.getParent()).getChildAt(0);
        textView.setText("Launcher Activity (e.g. main)");
        inputValue.setText(actnamr);
        inputValue.setHint("Activity name");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.dismiss();

                AndroidManifestInjector.setLauncherActivity(src_id, inputValue.getText().toString());

                SketchwareUtil.toast("Saved");
            }
        });

        btnCancel.setOnClickListener(Helper.getDialogDismissListener(create));

        create.show();
    }

    private void showAddActivityDialog() {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(Resources.layout.custom_dialog_attribute, null);
        create.setView(inflate);
        create.setCanceledOnTouchOutside(true);
        ///create.setCancelable(true);
        // ???
        create.getWindow().setBackgroundDrawableResource(0x106000d);
        final TextView btnSave = inflate.findViewById(Resources.id.dialog_btn_save);
        final TextView btnCancel = inflate.findViewById(Resources.id.dialog_btn_cancel);

        final EditText inputRes = inflate.findViewById(Resources.id.dialog_input_res);
        inputRes.setVisibility(View.GONE);
        final EditText inputAttr = inflate.findViewById(Resources.id.dialog_input_attr);
        inputAttr.setVisibility(View.GONE);
        final EditText inputValue = inflate.findViewById(Resources.id.dialog_input_value);
        final TextView textView = (TextView) ((ViewGroup) inputAttr.getParent()).getChildAt(0);
        textView.setText("Activity name");
        inputValue.setText(activityName);
        inputValue.setHint("Activity name");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewActivity(inputValue.getText().toString());

                create.dismiss();
                SketchwareUtil.toast("New Activity added");
            }
        });

        btnCancel.setOnClickListener(Helper.getDialogDismissListener(create));

        create.show();
    }

    private void addNewActivity(String componentName) {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(src_id).concat("/Injection/androidmanifest/attributes.json");
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        if (FileUtil.isExistFile(path)) {
            data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:configChanges=\"orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout\"");

            data.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:hardwareAccelerated=\"true\"");

            data.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:supportsPictureInPicture=\"true\"");

            data.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:screenOrientation=\"portrait\"");

            data.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:theme=\"@style/AppTheme\"");

            data.add(_item);
        }

        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("name", componentName);
            _item.put("value", "android:windowSoftInputMode=\"stateHidden\"");

            data.add(_item);
        }


        FileUtil.writeFile(path, new Gson().toJson(data));
        refreshList();

    }

    private void a(View view, int i2, int i3) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{(float) i2, (float) i2, (float) i2 / 2, (float) i2 / 2, (float) i2, (float) i2, (float) i2 / 2, (float) i2 / 2});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins((int) getDip(4), (int) getDip(2), (int) getDip(4), (int) getDip(2));
        view.setElevation((float) i3);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void refreshList() {
        list_map.clear();
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(src_id).concat("/Injection/androidmanifest/attributes.json");
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<HashMap<String, Object>> data;
        if (FileUtil.isExistFile(path)) {
            data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < data.size(); i++) {
                if (!temp.contains(data.get(i).get("name"))) {
                    if (!data.get(i).get("name").equals("_application_attrs") && !data.get(i).get("name").equals("_apply_for_all_activities") && !data.get(i).get("name").equals("_application_permissions")) {
                        temp.add((String) data.get(i).get("name"));
                    }

                }
            }
            for (int i = 0; i < temp.size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("act_name", temp.get(i));
                list_map.add(map);
            }
            this.act_list.setAdapter(new ListAdapter(this.list_map));
            ((BaseAdapter) this.act_list.getAdapter()).notifyDataSetChanged();
            ///FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(src_id).concat("/Injection/androidmanifest/log"), new Gson().toJson(list_map));
        } else {
            ///warn("file not exist");
        }
    }

    private void deleteActivity(int pos) {

        String activity_name = (String) list_map.get(pos).get("act_name");
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(src_id).concat("/Injection/androidmanifest/attributes.json");
        ArrayList<HashMap<String, Object>> data;
        data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
        ///int _int = data.size()-1;
        for (int i = data.size() - 1; i > -1; i--) {
            String temp = (String) data.get(i).get("name");
            if (temp.equals(activity_name)) {
                data.remove(i);
            }
            ///_int--;
        }
        FileUtil.writeFile(path, new Gson().toJson(data));
        refreshList();
        removeComponents(activity_name);
        SketchwareUtil.toast("activity removed");
    }

    private void removeComponents(String str) {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(src_id).concat("/Injection/androidmanifest/activities_components.json");
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        if (FileUtil.isExistFile(path)) {
            data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = data.size() - 1; i > -1; i--) {
                String name = (String) data.get(i).get("name");
                if (name.equals(str)) {
                    data.remove(i);
                    break;
                }
            }
            FileUtil.writeFile(path, new Gson().toJson(data));
        }
    }

    private CardView newCard(int width, int height, float weight) {
        CardView temp_card = new CardView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height, weight);
        lp.setMargins((int) getDip(4), (int) getDip(2), (int) getDip(4), (int) getDip(2));
        temp_card.setLayoutParams(lp);
        temp_card.setPadding((int) getDip(2), (int) getDip(2), (int) getDip(2), (int) getDip(2));
        temp_card.setCardBackgroundColor(0xFFFFFFFF);
        //android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        //gd.setColor(Color.WHITE);
        //android.graphics.drawable.RippleDrawable rpl = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE}),gd , null);
        //temp_card.setBackground(rpl);
        //temp_card.setClickable(true);
        //temp_card.setFocusable(true);


        temp_card.setRadius(getDip(4));
        //temp_card.setCardElevation((float));
        return temp_card;
    }

    private LinearLayout newLayout(int width, int height, float weight) {
        LinearLayout temp_card = new LinearLayout(this);
        temp_card.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
        temp_card.setPadding((int) getDip(1), (int) getDip(1), (int) getDip(1), (int) getDip(1));
        //temp_card.setBackgroundColor(0x00ffffff);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        RippleDrawable rpl = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#64B5F6")}), gd, null);
        temp_card.setBackground(rpl);
        temp_card.setClickable(true);
        temp_card.setFocusable(true);
        return temp_card;
    }

    private TextView newText(String str, float size, boolean is, int color, int width, int height, float weight) {
        TextView temp_card = new TextView(this);
        temp_card.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
        temp_card.setPadding((int) getDip(4), (int) getDip(4), (int) getDip(4), (int) getDip(4));
        temp_card.setTextColor(color);
        temp_card.setText(str);
        temp_card.setTextSize(size);
        if (is) {
            temp_card.setTypeface(Typeface.DEFAULT_BOLD);
        }
        //temp_card.setTypeface(Typeface.ITALIC);
        return temp_card;
    }

    private void newToolbar(View v) {
        View toolbar = getLayoutInflater().inflate(Resources.layout.toolbar_improved, null);
        ImageView _img = toolbar.findViewById(Resources.id.ig_toolbar_back);
        TextView _title = toolbar.findViewById(Resources.id.tx_toolbar_title);
        _title.setText("AndroidManifest Manager");
        _img.setOnClickListener(Helper.getBackPressedClickListener(this));
        v.setPadding(0, 0, 0, 0);
        ((ViewGroup) v).addView(toolbar, 0);
    }

    private void makeup(View v, int icon, String title, String desc) {
        View _view = getLayoutInflater().inflate(Resources.layout.manage_library_base_item, null);
        LinearLayout _back = _view.findViewById(Resources.id.container);
        ImageView _img = _view.findViewById(Resources.id.lib_icon);
        TextView _title = _view.findViewById(Resources.id.lib_title);
        TextView _desc = _view.findViewById(Resources.id.lib_desc);
        TextView _un = _view.findViewById(Resources.id.tv_enable);
		
		/*android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.WHITE);
		android.graphics.drawable.RippleDrawable rpl = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#64B5F6")}),gd , null);
		_back.setBackground(rpl);
		_back.setClickable(true);*/

        _un.setVisibility(View.GONE);
        _img.setImageResource(icon);
        ((LinearLayout) _img.getParent()).setGravity(Gravity.CENTER);
        _title.setText(title);
        //_title.setGravity(Gravity.CENTER);
        _desc.setText(desc);

        ((ViewGroup) v).addView(_view);
    }

    private class ListAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

        public ListAdapter(ArrayList<HashMap<String, Object>> data) {
            _data = data;
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
                convertView = getLayoutInflater().inflate(Resources.layout.custom_view_attribute, null);
            }
            LinearLayout linearLayout = convertView.findViewById(Resources.id.cus_attr_layout);
            TextView textView = convertView.findViewById(Resources.id.cus_attr_text);
            final ImageView imageView = convertView.findViewById(Resources.id.cus_attr_btn);
            imageView.setVisibility(View.GONE);
            a(linearLayout, (int) getDip(4), (int) getDip(2));
            textView.setText((String) list_map.get(position).get("act_name"));
            textView.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
            textView.setTextSize(15);
            textView.setTypeface(Typeface.DEFAULT);
            linearLayout.setVisibility(View.VISIBLE);
            ///activity pressed
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), AndroidManifestInjectionDetails.class);
                    intent.putExtra("sc_id", src_id);
                    intent.putExtra("file_name", (String) _data.get(position).get("act_name"));
                    intent.putExtra("type", "activity");
                    startActivity(intent);
                }
            });
            ////activity long pressed
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dia = new AlertDialog.Builder(AndroidManifestInjection.this);

                    dia.setTitle((String) _data.get(position).get("act_name"));

                    dia.setMessage("do you want to delete all attributes related to this activity?");

                    dia.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteActivity(position);
                        }
                    });

                    dia.setNegativeButton("No", null);
                    dia.show();

                    return true;
                }
            });

            ///end
            return convertView;
        }
    }
}





