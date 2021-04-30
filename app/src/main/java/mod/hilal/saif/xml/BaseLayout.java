package mod.hilal.saif.xml;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sketchware.remod.Resources;

import java.util.HashMap;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class BaseLayout {

    public static String defaultValue(String path, String type, boolean write) {
        if (write) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("toolbar", "androidx.appcompat.widget.Toolbar");
            hashMap.put("appbarlayout", "com.google.android.material.appbar.AppBarLayout");
            hashMap.put("coordinatorlayout", "androidx.coordinatorlayout.widget.CoordinatorLayout");
            hashMap.put("drawerlayout", "androidx.drawerlayout.widget.DrawerLayout");
            hashMap.put("navigationdrawer", "LinearLayout");
            hashMap.put("floatingactionbutton", "com.google.android.material.floatingactionbutton.FloatingActionButton");
            FileUtil.writeFile(path.concat("-convert"), new Gson().toJson(hashMap));
        }

        switch (type) {
            case "toolbar":
                return "androidx.appcompat.widget.Toolbar";

            case "coordinatorlayout":
                return "androidx.coordinatorlayout.widget.CoordinatorLayout";

            case "drawerlayout":
                return "androidx.drawerlayout.widget.DrawerLayout";

            case "appbarlayout":
                return "com.google.android.material.appbar.AppBarLayout";

            case "floatingactionbutton":
                return "com.google.android.material.floatingactionbutton.FloatingActionButton";

            case "navigationdrawer":
            default:
                return "LinearLayout";
        }
    }

    public static String getPreValue(String path, String type) {
        String value = "";
        if (!FileUtil.isExistFile(path + "-convert") || FileUtil.readFile(path + "-convert").equals("") || FileUtil.readFile(path + "-convert").equals("[]")) {
            return defaultValue(path, type, true);
        }
        HashMap<String, Object> hashMap = (HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(path + "-convert"), Helper.TYPE_MAP_LIST);
        if (hashMap.containsKey(type) && hashMap.get(type) != null) {
            switch (type) {
                case "toolbar":
                    value = hashMap.get("toolbar").toString();
                    break;

                case "coordinatorlayout":
                    value = hashMap.get("coordinatorlayout").toString();
                    break;

                case "drawerlayout":
                    value = hashMap.get("drawerlayout").toString();
                    break;

                case "appbarlayout":
                    value = hashMap.get("appbarlayout").toString();
                    break;

                case "floatingactionbutton":
                    value = hashMap.get("floatingactionbutton").toString();
                    break;

                case "navigationdrawer":
                    value = hashMap.get("navigationdrawer").toString();
                    break;
            }
        }

        if (value.equals("")) {
            return defaultValue(path, type, false);
        }
        return value;
    }

    public static void showDialog(Context context, String pre, final String type, final String path) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View inflate = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(Resources.layout.custom_dialog_attribute, null);
        dialog.setView(inflate);
        dialog.setCanceledOnTouchOutside(true);
        /* what */
        /* dialog.getWindow().setBackgroundDrawableResource(17170445); */
        ((EditText) inflate.findViewById(Resources.id.dialog_input_res)).setVisibility(View.GONE);
        EditText dialog_input_attr = (EditText) inflate.findViewById(Resources.id.dialog_input_attr);
        dialog_input_attr.setVisibility(View.GONE);
        final EditText dialog_input_value = (EditText) inflate.findViewById(Resources.id.dialog_input_value);
        ((TextView) ((ViewGroup) dialog_input_attr.getParent()).getChildAt(0)).setText("Convert");
        dialog_input_value.setText(pre);
        dialog_input_value.setHint("type");
        ((TextView) inflate.findViewById(Resources.id.dialog_btn_save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (dialog_input_value.getText().toString().trim().equals("")) {
                    dialog.dismiss();
                    return;
                }
                ((HashMap<String, Object>) new Gson().fromJson(FileUtil.readFile(path + "-convert"), Helper.TYPE_MAP_LIST))
                        .put(type, dialog_input_value.getText().toString().trim());
                dialog.dismiss();
            }
        });
        ((TextView) inflate.findViewById(Resources.id.dialog_btn_cancel)).setOnClickListener(Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public static void visuals(final Context context, ImageView bc, final String fileName, String sc_id, final String type) {
        final ImageView ig_toolbar_load_file = ((LinearLayout) bc.getParent()).findViewById(Resources.id.ig_toolbar_load_file);
        ig_toolbar_load_file.setVisibility(View.VISIBLE);
        ig_toolbar_load_file.setImageResource(Resources.drawable.ic_menu_white_24dp);
        ig_toolbar_load_file.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, ig_toolbar_load_file);
                popupMenu.getMenu().add("Convert");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equals("Convert")) {
                            showDialog(context, getPreValue(fileName, type), type, fileName);
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
