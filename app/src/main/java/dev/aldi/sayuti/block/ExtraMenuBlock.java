package dev.aldi.sayuti.block;

import android.net.Uri;

import com.besome.sketch.editor.LogicEditorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import a.a.a.Ss;
import a.a.a.jC;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hilal.saif.asd.asdforall.AsdAll;

public class ExtraMenuBlock {

    private final LogicEditorActivity a;

    public ExtraMenuBlock(LogicEditorActivity logicEditorActivity) {
        a = logicEditorActivity;
    }

    public void a(Ss ss, AsdAll asdAll, ArrayList<String> arrayList) {
        String menuName = ss.getMenuName();
        if (menuName.equals("LayoutParam")) {
            asdAll.b("LayoutParams");
            arrayList.add("MATCH_PARENT");
            arrayList.add("WRAP_CONTENT");
        }
        if (menuName.equals("Command")) {
            asdAll.b("commands");
            arrayList.add("insert");
            arrayList.add("add");
            arrayList.add("replace");
            arrayList.add("find-replace");
            arrayList.add("find-replace-first");
            arrayList.add("find-replace-all");
        }
        try {
            JSONArray jSONArray = new JSONArray(ExtraBlockFile.getMenuBlockFile());
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (!jSONObject.isNull("id") && menuName.equals(jSONObject.getString("id"))) {
                    arrayList.clear();
                    b(jSONObject, asdAll, arrayList, a.B);
                    try {
                        JSONArray jSONArray2 = new JSONObject(ExtraBlockFile.getMenuDataFile()).getJSONArray(jSONObject.getString("id"));
                        if (jSONArray2.length() > 0) {
                            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                JSONObject jSONObject2 = jSONArray2.getJSONObject(i2);
                                asdAll.b(jSONObject2.getString("title"));
                                ArrayList<String> arrayList2 = new ArrayList<>(Arrays.asList(jSONObject2.getString("value").split("\\+")));
                                arrayList.addAll(arrayList2);
                            }
                        }
                    } catch (JSONException ignored) {
                    }
                }
            }
        } catch (JSONException ignored) {
        }
    }

    public final void b(JSONObject json, AsdAll asdAll, ArrayList<String> selectableItems, String sc_id) {
        String menus = FileUtil.getExternalStorageDir().concat("/.sketchware/data/" + sc_id + "/files/resource/menu/");
        String layouts = FileUtil.getExternalStorageDir().concat("/.sketchware/data/" + sc_id + "/files/resource/layout/");
        String animations = FileUtil.getExternalStorageDir().concat("/.sketchware/data/" + sc_id + "/files/resource/anim/");
        String drawables = FileUtil.getExternalStorageDir().concat("/.sketchware/data/" + sc_id + "/files/resource/drawable/");
        String drawables_xhdpi = FileUtil.getExternalStorageDir().concat("/.sketchware/data/" + sc_id + "/files/resource/drawable-xhdpi/");
        try {
            switch (json.getString("id")) {
                case "menu":
                    asdAll.b("Select a menu");
                    for (String menu : FileUtil.listFiles(menus, ".xml")) {
                        selectableItems.add(Uri.parse(menu).getLastPathSegment().substring(0, Uri.parse(menu).getLastPathSegment().indexOf(".xml")));
                    }
                    break;

                case "layout":
                    asdAll.b("Select a layout");
                    for (String layout : FileUtil.listFiles(layouts, ".xml")) {
                        selectableItems.add(Uri.parse(layout).getLastPathSegment().substring(0, Uri.parse(layout).getLastPathSegment().indexOf(".xml")));
                    }
                    for (String str4 : jC.b(sc_id).e()) {
                        selectableItems.add(str4.substring(0, str4.indexOf(".xml")));
                    }
                    break;

                case "anim":
                    asdAll.b("Select an animation");
                    for (String animation : FileUtil.listFiles(animations, ".xml")) {
                        selectableItems.add(Uri.parse(animation).getLastPathSegment().substring(0, Uri.parse(animation).getLastPathSegment().indexOf(".xml")));
                    }
                    break;

                case "drawable":
                    asdAll.b("Select a drawable");
                    for (String drawable : FileUtil.listFiles(drawables, ".xml")) {
                        selectableItems.add(Uri.parse(drawable).getLastPathSegment().substring(0, Uri.parse(drawable).getLastPathSegment().indexOf(".xml")));
                    }
                    break;

                case "image":
                    asdAll.b("Select an image");
                    for (String drawable_xhdpi : FileUtil.listFiles(drawables_xhdpi, "")) {
                        if (drawable_xhdpi.contains(".png") || drawable_xhdpi.contains(".jpg")) {
                            if (drawable_xhdpi.contains(".png")) {
                                selectableItems.add(Uri.parse(drawable_xhdpi).getLastPathSegment().substring(0, Uri.parse(drawable_xhdpi).getLastPathSegment().indexOf(".png")));
                            } else {
                                selectableItems.add(Uri.parse(drawable_xhdpi).getLastPathSegment().substring(0, Uri.parse(drawable_xhdpi).getLastPathSegment().indexOf(".jpg")));
                            }
                        }
                    }
                    break;
            }
        } catch (JSONException ignored) {
        }
    }
}