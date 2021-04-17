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
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import mod.hilal.saif.lib.FileUtil;

public class BaseLayout {
    public static String defaultValue(String str, String str2, boolean z) {
        if (z) {
            HashMap hashMap = new HashMap();
            hashMap.put("toolbar", "androidx.appcompat.widget.Toolbar");
            hashMap.put("appbarlayout", "com.google.android.material.appbar.AppBarLayout");
            hashMap.put("coordinatorlayout", "androidx.coordinatorlayout.widget.CoordinatorLayout");
            hashMap.put("drawerlayout", "androidx.drawerlayout.widget.DrawerLayout");
            hashMap.put("navigationdrawer", "LinearLayout");
            hashMap.put("floatingactionbutton", "com.google.android.material.floatingactionbutton.FloatingActionButton");
            FileUtil.writeFile(str.concat("-convert"), new Gson().toJson(hashMap));
        }
        switch (str2.hashCode()) {
            case -1140094085:
                if (str2.equals("toolbar")) {
                    return "androidx.appcompat.widget.Toolbar";
                }
                break;
            case -301919654:
                if (str2.equals("coordinatorlayout")) {
                    return "androidx.coordinatorlayout.widget.CoordinatorLayout";
                }
                break;
            case 320322395:
                if (str2.equals("drawerlayout")) {
                    return "androidx.drawerlayout.widget.DrawerLayout";
                }
                break;
            case 1499805692:
                if (str2.equals("appbarlayout")) {
                    return "com.google.android.material.appbar.AppBarLayout";
                }
                break;
            case 1654772718:
                if (str2.equals("floatingactionbutton")) {
                    return "com.google.android.material.floatingactionbutton.FloatingActionButton";
                }
                break;
            case 1676420101:
                if (str2.equals("navigationdrawer")) {
                    return "LinearLayout";
                }
                break;
        }
        return "LinearLayout";
    }

    public static String getPreValue(String str, String str2) {
        String obj;
        if (!FileUtil.isExistFile(String.valueOf(str) + "-convert") || FileUtil.readFile(String.valueOf(str) + "-convert").equals("") || FileUtil.readFile(String.valueOf(str) + "-convert").equals("[]")) {
            return defaultValue(str, str2, true);
        }
        HashMap hashMap = (HashMap) new Gson().fromJson(FileUtil.readFile(String.valueOf(str) + "-convert"), new TypeToken<HashMap<String, Object>>() {
        }.getType());
        switch (str2.hashCode()) {
            case -1140094085:
                if (str2.equals("toolbar")) {
                    obj = hashMap.get("toolbar").toString();
                    break;
                } else {
                    obj = "";
                    break;
                }
            case -301919654:
                if (str2.equals("coordinatorlayout")) {
                    obj = hashMap.get("coordinatorlayout").toString();
                    break;
                } else {
                    obj = "";
                    break;
                }
            case 320322395:
                if (str2.equals("drawerlayout")) {
                    obj = hashMap.get("drawerlayout").toString();
                    break;
                } else {
                    obj = "";
                    break;
                }
            case 1499805692:
                if (str2.equals("appbarlayout")) {
                    obj = hashMap.get("appbarlayout").toString();
                    break;
                } else {
                    obj = "";
                    break;
                }
            case 1654772718:
                if (str2.equals("floatingactionbutton")) {
                    obj = hashMap.get("floatingactionbutton").toString();
                    break;
                } else {
                    obj = "";
                    break;
                }
            case 1676420101:
                if (str2.equals("navigationdrawer")) {
                    obj = hashMap.get("navigationdrawer").toString();
                    break;
                } else {
                    obj = "";
                    break;
                }
            default:
                obj = "";
                break;
        }
        if (obj.equals("")) {
            return defaultValue(str, str2, false);
        }
        return obj;
    }

    public static void showDialog(Context context, String str, final String str2, final String str3) {
        final AlertDialog create = new AlertDialog.Builder(context).create();
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(2131427797, (ViewGroup) null);
        create.setView(inflate);
        create.setCanceledOnTouchOutside(true);
        create.getWindow().setBackgroundDrawableResource(17170445);
        ((EditText) inflate.findViewById(2131232446)).setVisibility(8);
        EditText editText = (EditText) inflate.findViewById(2131232443);
        editText.setVisibility(View.GONE);
        final EditText editText2 = (EditText) inflate.findViewById(2131232447);
        ((TextView) ((ViewGroup) editText.getParent()).getChildAt(0)).setText("Convert");
        editText2.setText(str);
        editText2.setHint("type");
        ((TextView) inflate.findViewById(2131232445)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (editText2.getText().toString().trim().equals("")) {
                    create.dismiss();
                    return;
                }
                ((HashMap) new Gson().fromJson(FileUtil.readFile(String.valueOf(str3) + "-convert"), new TypeToken<HashMap<String, Object>>() {
                }.getType())).put(str2, editText2.getText().toString().trim());
                create.dismiss();
            }
        });
        ((TextView) inflate.findViewById(2131232444)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                create.dismiss();
            }
        });
        create.show();
    }

    public static void visuals(final Context context, ImageView imageView, final String str, String str2, final String str3) {
        final ImageView imageView2 = (ImageView) ((LinearLayout) imageView.getParent()).findViewById(2131232459);
        imageView2.setVisibility(View.VISIBLE);
        imageView2.setImageResource(2131165788);
        imageView2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, imageView2);
                popupMenu.getMenu().add("Convert");
                final Context Context = context;
                final String Str = str;
                final String str2 = str3;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String charSequence = menuItem.getTitle().toString();
                        switch (charSequence.hashCode()) {
                            case -1678723693:
                                if (!charSequence.equals("Convert")) {
                                    return true;
                                }
                                BaseLayout.showDialog(context, BaseLayout.getPreValue(str, str2), str2, str);
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }
}
