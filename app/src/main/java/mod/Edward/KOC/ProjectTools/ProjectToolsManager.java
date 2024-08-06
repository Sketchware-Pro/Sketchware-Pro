package mod.Edward.KOC.ProjectTools;

import static mod.Edward.KOC.ProjectTools.ImportAnActivity.StartImportActivityTool;
import static mod.Edward.KOC.ProjectTools.RenameAnActivity.StartRenameActivityTool;
import static mod.SketchwareUtil.toastError;

import android.app.AlertDialog;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sketchware.remod.R;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import a.a.a.yB;
import mod.agus.jcoderz.lib.FileUtil;

public class ProjectToolsManager {

    public static final ArrayList<String> activitiesList = new ArrayList<>();
    public static final String swDir = "/storage/emulated/0/.sketchware/";
    public static String tempDecrypted = "";
    public static int selectedPosition = 0;
    public static String activityInfo = "";
    public static String linesStartingWith = "";
    public static String activityName = "";
    public static final String FragmentEnd = "_fragment";
    public static final String DialogFragmentEnd = "_dialog_fragment";

    public static void showProjectSettingV2Dialog(Context context, HashMap<String, Object> project) {
        String ScId = yB.c(project, "sc_id");
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(R.layout.settings_v2_dialog)
                .setCancelable(true)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View inflate = LayoutInflater.from(context).inflate(R.layout.settings_v2_dialog, null);
        dialog.setView(inflate);
        final MaterialButton BtnRenameActivity = inflate.findViewById(R.id.dialog_btn_rename);
        final MaterialButton BtnImportActivity = inflate.findViewById(R.id.dialog_btn_import_activity);

        BtnRenameActivity.setOnClickListener(view -> {
            if (isProjectNotSaved(ScId)) {
                toastError("Please save the project first");
                return;
            }
            _loadViews(ScId);
            if (activitiesList.size() == 1) {
                toastError("No activities found,\nYou can't rename MainActivity");
                return;
            }
            StartRenameActivityTool(dialog, view.getContext(), inflate, ScId);

        });

        BtnImportActivity.setOnClickListener(view -> {
            if (isProjectNotSaved(ScId)) {
                toastError("Please save the project first");
                return;
            }
            StartImportActivityTool(dialog, view.getContext(), inflate, ScId);
        });

        dialog.show();
    }

    public static void _loadViews(String str) {
        activitiesList.clear();
        _decrypt(swDir.concat("/data/".concat(str.concat("/file"))));

        String decryptedData = tempDecrypted;
        int startIndex = decryptedData.indexOf("@activity");
        int endIndex = decryptedData.lastIndexOf("@customview");

        if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex) {
            toastError("Invalid data format.");
            return;
        }

        startIndex += "@activity".length();

        tempDecrypted = decryptedData.substring(startIndex, endIndex).trim();

        try (Scanner scanner = new Scanner(tempDecrypted)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine().trim();
                if (!nextLine.isEmpty()) {
                    try {
                        HashMap<String, Object> map = new Gson().fromJson(nextLine, new TypeToken<HashMap<String, Object>>() {}.getType());
                        activitiesList.add(map.get("fileName").toString());
                    } catch (Exception e) {
                        toastError("Error parsing JSON: " + e);
                    }
                }
            }
        } catch (Exception e) {
            toastError("Error reading file: " + e);
        }
    }

    public static void _decrypt(String str) {
        tempDecrypted = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwaresecure".getBytes();
            cipher.init(2, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
            byte[] bArr = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(bArr);
            tempDecrypted = new String(cipher.doFinal(bArr));
        } catch (Exception e) {
            tempDecrypted = "";
            toastError(e.toString());
        }
    }

    public static void _addActivity(String str, String str2, String str3, String str4) {
        _getActivityInfo(str, str2);
        activityInfo = activityInfo.replace(str2, str4);
        String concat = swDir.concat("/data/".concat(str3.concat("/file")));
        _decrypt(concat);
        int indexOf = tempDecrypted.indexOf("@customview");
        tempDecrypted = tempDecrypted.substring(0, indexOf).concat(activityInfo.concat("\n").concat(tempDecrypted.substring(indexOf)));
        _encrypt(tempDecrypted.replace("}{", "}\n{"), concat);
       
        String str5 = getJavaName(str2);
        String str6 = getJavaName(str4);
        _getLinesStartingWith(str, "logic", "@" + str5);
        linesStartingWith = linesStartingWith.replace("@" + str5, "@" + str6);
        String concat1 = swDir.concat("/data/".concat(str3.concat("/logic")));
        _decrypt(concat1);
        tempDecrypted = tempDecrypted.concat(linesStartingWith);
        _encrypt(tempDecrypted, concat1);
        _getLinesStartingWith(str, "view", "@" + str2 + ".xml");
        _addResources(str, str3, linesStartingWith);
        linesStartingWith = linesStartingWith.replace("@" + str2, "@" + str4);
        String concat2 = swDir.concat("/data/".concat(str3.concat("/view")));
        _decrypt(concat2);
        tempDecrypted = tempDecrypted.concat(linesStartingWith);
        _encrypt(tempDecrypted, concat2);
        FileUtil.deleteFile(swDir.concat("/bak/".concat(str3.concat("/logic"))));
        FileUtil.deleteFile(swDir.concat("/bak/".concat(str3.concat("/view"))));
    }

    public static void _addResources(String str, String str2, String str3) {
        int i = 0;
        if (str.equals(str2)) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(str3.split("\n")));
        ArrayList<String> arrayList2 = new ArrayList<>();
        int i2 = 0;
        int i3 = 0;
        while (i3 < arrayList.size()) {
            String str4 = arrayList.get(i2);
            int indexOf = str4.indexOf(",\"backgroundResource\":\"");
            if (indexOf != -1) {
                String substring = str4.substring(",\"backgroundResource\":\"".length() + indexOf, str4.indexOf("\"", indexOf + ",\"backgroundResource\":\"".length()));
                if (!substring.equals("NONE") && !arrayList2.contains(substring)) {
                    arrayList2.add(substring);
                }
            }
            int indexOf2 = str4.indexOf("{\"resName\":\"");
            if (indexOf2 != -1) {
                String substring2 = str4.substring("{\"resName\":\"".length() + indexOf2, str4.indexOf("\"", indexOf2 + "{\"resName\":\"".length()));
                if (!substring2.equals("default_image") && !arrayList2.contains(substring2)) {
                    arrayList2.add(substring2);
                }
            }
            i3++;
            i2++;
        }
        if (!arrayList2.isEmpty()) {
            _decrypt(swDir.concat("/data/".concat(str.concat("/resource"))));
            tempDecrypted.substring(tempDecrypted.indexOf("\n"), tempDecrypted.indexOf("@sounds"));
            String concat = swDir.concat("/data/".concat(str2.concat("/resource")));
            _decrypt(concat);
            try {
                String substring3 = tempDecrypted.substring(tempDecrypted.indexOf("\n"), tempDecrypted.indexOf("@sounds"));
                String str5 = "";
                int i4 = 0;
                while (i < arrayList2.size()) {
                    String str6 = arrayList2.get(i4);
                    if (!substring3.contains(str6)) {
                        str5 = str5.concat("\n").concat("{\"resFullName\":\"_resName.png\",\"resName\":\"_resName\",\"resType\":1}").replace("_resName", str6);
                        FileUtil.copyFile(swDir + "/resources/images/" + str + "/" + str6 + ".png", swDir + "/resources/images/" + str2 + "/" + str6 + ".png");
                    }
                    i++;
                    i4++;
                }
                int indexOf3 = tempDecrypted.indexOf("@sounds");
                tempDecrypted = tempDecrypted.substring(0, indexOf3).concat(str5.trim().concat("\n").concat(tempDecrypted.substring(indexOf3)));
                _encrypt(tempDecrypted.trim(), concat);
            } catch (Exception e) {
                toastError(e.toString());
            }
        }
    }


    public static void _getLinesStartingWith(String str, String str2, String str3) {
        linesStartingWith = "\n";
        _decrypt(swDir.concat("/data/".concat(str.concat("/".concat(str2)))));
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(tempDecrypted.split("\n\n")));
        int i = 0;
        int i2 = 0;
        while (i2 < arrayList.size()) {
            if (arrayList.get(i).startsWith(str3)) {
                linesStartingWith = linesStartingWith.trim().concat("\n\n".concat(arrayList.get(i).trim()));
            }
            i2++;
            i++;
        }
    }


    public static void _getActivityInfo(String str, String str2) {
        _decrypt(swDir.concat("/data/".concat(str.concat("/file"))));
        activityInfo = "";

        String decryptedData = tempDecrypted;

        int startIndex = decryptedData.indexOf("@activity") + "@activity".length();
        int endIndex = decryptedData.indexOf("@customview");

        if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex) {
            toastError("Invalid data format.");
            return;
        }

        tempDecrypted = decryptedData.substring(startIndex, endIndex).trim();
        try (Scanner scanner = new Scanner(tempDecrypted)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine().trim();
                if (!nextLine.isEmpty()) {
                    try {
                        HashMap<String, Object> map = new Gson().fromJson(nextLine, new TypeToken<HashMap<String, Object>>() {}.getType());
                        if (map.get("fileName").toString().equals(str2)) {
                            activityInfo = nextLine;
                            return;
                        }
                    } catch (Exception e) {
                        toastError("Error parsing JSON: " + e);
                    }
                }
            }
        } catch (Exception e) {
            toastError("Error reading file: " + e);
        }
    }

    public static void _deleteActivity(String str, String str2) {
        int i = 0;
        _getActivityInfo(str, str2);
        String concat = swDir.concat("/data/".concat(str.concat("/file")));
        _decrypt(concat);
        tempDecrypted = tempDecrypted.replace(activityInfo.concat("\n"), "");
        _encrypt(tempDecrypted, concat);

        String concat1 = swDir.concat("/data/".concat(str.concat("/view")));
        _decrypt(concat1);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(tempDecrypted.split("\n\n")));
        String str3 = "";
        int i2 = 0;
        int i3 = 0;
        while (i3 < arrayList.size()) {
            String str4 = (((String) arrayList.get(i2)).trim().startsWith("@" + str2.trim()) || ((String) arrayList.get(i2)).trim().isEmpty()) ? str3 : str3.trim() + "\n\n" + ((String) arrayList.get(i2)).trim();
            i3++;
            i2++;
            str3 = str4;
        }
        _encrypt(str3, concat1);

        String concat2 = swDir.concat("/data/".concat(str.concat("/logic")));
        _decrypt(concat2);
        ArrayList<String> arrayList2 = new ArrayList<>(Arrays.asList(tempDecrypted.split("\n\n")));
        String str5 = "";

        int i4 = 0;
        while (i < arrayList2.size()) {
            String str6 = (((String) arrayList2.get(i4)).trim().startsWith("@" + getJavaName(str2).trim()) || ((String) arrayList2.get(i4)).trim().isEmpty()) ? str5 : str5.trim() + "\n\n" + ((String) arrayList2.get(i4)).trim();
            i++;
            i4++;
            str5 = str6;
        }
        _encrypt(str5, concat2);

        FileUtil.deleteFile(swDir.concat("/bak/".concat(str.concat("/logic"))));
        FileUtil.deleteFile(swDir.concat("/bak/".concat(str.concat("/view"))));
    }


    public static String getJavaName(String str) {
        try {
            String javaName = "_".concat(str.concat("Activity.java"));
            while (true) {
                int indexOf = javaName.indexOf("_");
                if (!javaName.contains("_")) {
                    return javaName;
                } else {
                    javaName = javaName.substring(0, indexOf).concat(javaName.substring(indexOf + 1, indexOf + 2).toUpperCase().concat(javaName.substring(indexOf + 2)));
                }
            }
        } catch (Exception e) {
            toastError(e.toString());
            return "";
        }
    }

    public static void _encrypt(String str, String str2) {
        String readFile = FileUtil.readFile(str2);
        FileUtil.writeFile(str2, "");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwaresecure".getBytes();
            cipher.init(1, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            new RandomAccessFile(str2, "rw").write(cipher.doFinal(str.getBytes()));
        } catch (Exception e) {
            FileUtil.writeFile(str2, readFile);
            throw new RuntimeException("dummy");
        }
    }

    public static boolean isProjectNotSaved(String str) {
        if (!FileUtil.readFile(swDir.concat("/bak/".concat(str.concat("/view")))).isEmpty()) {
            return true;
        } else return !FileUtil.readFile(swDir.concat("/bak/".concat(str.concat("/logic")))).isEmpty();
    }

    public static void AutoAnimation(View _view, long duration) {
        LinearLayout viewGroup = (LinearLayout) _view;
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration( duration);
        TransitionManager.beginDelayedTransition(viewGroup, autoTransition);
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        public MyAdapter() {
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rename_item_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String itemText = activitiesList.get(position);
            holder.radioButton.setText(itemText);
            holder.radioButton.setChecked(position == selectedPosition);

            holder.radioButton.setOnClickListener(v -> {
                selectedPosition = holder.getAdapterPosition();
                activityName = itemText;
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return activitiesList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            RadioButton radioButton;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                radioButton = itemView.findViewById(R.id.radio_b);
            }
        }
    }

}