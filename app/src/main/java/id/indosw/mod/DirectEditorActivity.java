package id.indosw.mod;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
impoet android.widget.EditText.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import id.indosw.mod.codeviewrevo.CodeViewRevo;
import id.indosw.mod.codeviewrevo.Language;
import id.indosw.mod.codeviewrevo.SyntaxManager;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class DirectEditorActivity extends AppCompatActivity {

    private final Timer _timer = new Timer();
    private final Intent i = new Intent();
    private final ArrayList<HashMap<String, Object>> listMapJavaFile = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> listMapResFile = new ArrayList<>();
    private final ArrayList<String> list_file_java = new ArrayList<>();
    private final ArrayList<String> list_file_res = new ArrayList<>();
    private Button btnSaveCodeEditor;
    private LinearLayout buttonManifest;
    private CodeViewRevo codeEditorLayout;
    private TextView currentValue;
    private AlertDialog.Builder dialog_deleteTask;
    private Button go_to_original;
    private String[] languageKeywords;
    private LinearLayout linJAVA;
    private LinearLayout linRES;
    private ArrayList<HashMap<String, Object>> listMapEditedCode = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> listMapReplace = new ArrayList<>();
    private Language mCurrentLanguage;
    private HashMap<String, Object> mapReplace = new HashMap<>();
    private String nameAPK = "";
    private String path_SourceEdited = "";
    private String path_bin = "";
    private String path_manifest = "";
    private String pkg_to_path = "";
    private double repeatJava = 0.0d;
    private double repeatReplace = 0.0d;
    private double repeatRes = 0.0d;
    private Switch replace_active;
    private String sc_id_string = "";
    private Button show_list_editedcode;
    private Spinner spinnerJAVA;
    private Spinner spinnerRES;
    private Spinner spinner_edited;
    private String tempName = "";
    private String tempPath = "";
    private TimerTask tmrCheck;

    static /* synthetic */ double access$1908(DirectEditorActivity directEditorActivity) {
        double d = directEditorActivity.repeatReplace;
        directEditorActivity.repeatReplace = 1.0d + d;
        return d;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427819);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        codeEditorLayout = findViewById(2131232624);
        linRES = findViewById(2131232628);
        buttonManifest = findViewById(2131232629);
        linJAVA = findViewById(2131232630);
        spinnerRES = findViewById(2131232633);
        spinnerJAVA = findViewById(2131232634);
        go_to_original = findViewById(2131232639);
        show_list_editedcode = findViewById(2131232640);
        spinner_edited = findViewById(2131232641);
        btnSaveCodeEditor = findViewById(2131232625);
        currentValue = findViewById(2131231837);
        replace_active = findViewById(2131232627);
        dialog_deleteTask = new AlertDialog.Builder(this);
        buttonManifest.setOnClickListener(new View.OnClickListener() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass1 */

            public void onClick(View view) {
                DirectEditorActivity directEditorActivity = DirectEditorActivity.this;
                directEditorActivity._UpdateView(directEditorActivity.path_manifest, Uri.parse(path_manifest).getLastPathSegment());
            }
        });
        findViewById(2131232631).setOnClickListener(new View.OnClickListener() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass2 */

            public void onClick(View view) {
                spinnerRES.performClick();
            }
        });
        findViewById(2131232632).setOnClickListener(new View.OnClickListener() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass3 */

            public void onClick(View view) {
                spinnerJAVA.performClick();
            }
        });
        go_to_original.setOnClickListener(new View.OnClickListener() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass4 */

            public void onClick(View view) {
                i.setClass(getApplicationContext(), DirectEditorActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("sc_id", sc_id_string);
                i.putExtra("current", "main.xml");
                DirectEditorActivity directEditorActivity = DirectEditorActivity.this;
                directEditorActivity.startActivity(directEditorActivity.i);
            }
        });

        show_list_editedcode.setOnClickListener(new View.OnClickListener() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass5 */

            public void onClick(View view) {
                if (FileUtil.isExistFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"))) {
                    spinner_edited.performClick();
                } else {
                    SketchwareUtil.showMessage(getApplicationContext(), "Nothing You Edited Yet");
                }
            }
        });

        findViewById(2131232642).setOnClickListener(new View.OnClickListener() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass6 */

            public void onClick(View view) {
                if (FileUtil.isExistFile(path_SourceEdited.concat("/" + sc_id_string))) {
                    dialog_deleteTask.setTitle("Restore");
                    dialog_deleteTask.setMessage("Are you sure you want to restore everything? All your edited code will be lost.");
                    dialog_deleteTask.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        /* class id.indosw.mod.DirectEditorActivity.AnonymousClass6.AnonymousClass1 */

                        public void onClick(DialogInterface dialogInterface, int i) {
                            FileUtil.deleteFile(path_SourceEdited.concat("/" + sc_id_string));
                            SketchwareUtil.showMessage(getApplicationContext(), "Successfully restored original files");
                        }
                    });
                    dialog_deleteTask.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        /* class id.indosw.mod.DirectEditorActivity.AnonymousClass6.AnonymousClass2 */

                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }); 

                    dialog_deleteTask.create().show();
                    return;
                }
                SketchwareUtil.showMessage(getApplicationContext(), "Nothing needs to be restored");
            }
        });
        btnSaveCodeEditor.setOnClickListener(new View.OnClickListener() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass7 */

            public void onClick(View view) {
                if (!FileUtil.isExistFile(path_SourceEdited.concat("/" + sc_id_string))) {
                    FileUtil.makeDir(path_SourceEdited.concat("/" + sc_id_string));
                }
                FileUtil.writeFile(path_SourceEdited.concat("/" + sc_id_string + "/" + tempName), codeEditorLayout.getText().toString());
                if (FileUtil.isExistFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"))) {
                    listMapReplace = new Gson().fromJson(FileUtil.readFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json")), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                        /* class id.indosw.mod.DirectEditorActivity.AnonymousClass7.AnonymousClass1 */
                    }.getType());
                    if (!FileUtil.readFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json")).contains(tempPath)) {
                        mapReplace = new HashMap<>();
                        mapReplace.put("target", tempPath);
                        mapReplace.put("edited", path_SourceEdited.concat("/" + sc_id_string + "/" + tempName));
                        listMapReplace.add(mapReplace);
                        FileUtil.writeFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"), new Gson().toJson(listMapReplace));
                        listMapEditedCode.clear();
                        if (FileUtil.isExistFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"))) {
                            listMapEditedCode = new Gson().fromJson(FileUtil.readFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json")), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                                /* class id.indosw.mod.DirectEditorActivity.AnonymousClass7.AnonymousClass2 */
                            }.getType());
                            Spinner spinner = spinner_edited;
                            DirectEditorActivity directEditorActivity = DirectEditorActivity.this;
                            spinner.setAdapter(new Spinner_editedAdapter(directEditorActivity.listMapEditedCode));
                        } else {
                            SketchwareUtil.showMessage(getApplicationContext(), "Nothing You Edited Yet");
                        }
                    }
                } else {
                    mapReplace = new HashMap<>();
                    mapReplace.put("target", tempPath);
                    mapReplace.put("edited", path_SourceEdited.concat("/" + sc_id_string + "/" + tempName));
                    listMapReplace.add(mapReplace);
                    FileUtil.writeFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"), new Gson().toJson(listMapReplace));
                    listMapEditedCode.clear();
                    if (FileUtil.isExistFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"))) {
                        listMapEditedCode = new Gson().fromJson(FileUtil.readFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json")), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass7.AnonymousClass3 */
                        }.getType());
                        Spinner spinner2 = spinner_edited;
                        DirectEditorActivity directEditorActivity2 = DirectEditorActivity.this;
                        spinner2.setAdapter(new Spinner_editedAdapter(directEditorActivity2.listMapEditedCode));
                    } else {
                        SketchwareUtil.showMessage(getApplicationContext(), "Nothing You Edited Yet");
                    }
                }
                btnSaveCodeEditor.setVisibility(View.GONE);
            }
        });
        replace_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass8 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (!z) {
                    return;
                }
                if (!FileUtil.isExistFile(path_SourceEdited + "/" + sc_id_string + "/Task.json")) {
                    replace_active.setChecked(false);
                    SketchwareUtil.showMessage(getApplicationContext(), "Task Null !!!");
                    return;
                }
                listMapReplace = new Gson().fromJson(FileUtil.readFile(path_SourceEdited + "/" + sc_id_string + "/Task.json"), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    /* class id.indosw.mod.DirectEditorActivity.AnonymousClass8.AnonymousClass1 */
                }.getType());
                if (FileUtil.isExistFile(path_bin + "/" + nameAPK)) {
                    FileUtil.deleteFile(path_bin + "/" + nameAPK);
                    tmrCheck = new TimerTask() {
                        /* class id.indosw.mod.DirectEditorActivity.AnonymousClass8.AnonymousClass2 */

                        public void run() {
                            runOnUiThread(new Runnable() {
                                /* class id.indosw.mod.DirectEditorActivity.AnonymousClass8.AnonymousClass2.AnonymousClass1 */

                                public void run() {
                                    if (FileUtil.isExistFile(path_bin + "/" + nameAPK)) {
                                        replace_active.setChecked(false);
                                    }
                                    repeatReplace = 0.0d;
                                    for (int i = 0; i < listMapReplace.size(); i++) {
                                        FileUtil.copyFile(Objects.requireNonNull(listMapReplace.get((int) repeatReplace).get("edited")).toString(), Objects.requireNonNull(listMapReplace.get((int) repeatReplace).get("target")).toString());
                                        DirectEditorActivity.access$1908(DirectEditorActivity.this);
                                    }
                                    if (!(replace_active.isChecked() || tmrCheck == null)) {
                                        SketchwareUtil.showMessage(getApplicationContext(), "Task Completed");
                                        tmrCheck.cancel();
                                    }
                                }
                            });
                        }
                    };
                } else {
                    tmrCheck = new TimerTask() {
                        /* class id.indosw.mod.DirectEditorActivity.AnonymousClass8.AnonymousClass3 */

                        public void run() {
                            runOnUiThread(new Runnable() {
                                /* class id.indosw.mod.DirectEditorActivity.AnonymousClass8.AnonymousClass3.AnonymousClass1 */

                                public void run() {
                                    if (FileUtil.isExistFile(path_bin + "/" + nameAPK)) {
                                        replace_active.setChecked(false);
                                    }
                                    repeatReplace = 0.0d;
                                    for (int i = 0; i < listMapReplace.size(); i++) {
                                        FileUtil.copyFile(Objects.requireNonNull(listMapReplace.get((int) repeatReplace).get("edited")).toString(), Objects.requireNonNull(listMapReplace.get((int) repeatReplace).get("target")).toString());
                                        DirectEditorActivity.access$1908(DirectEditorActivity.this);
                                    }
                                    if (!(replace_active.isChecked() || tmrCheck == null)) {
                                        SketchwareUtil.showMessage(getApplicationContext(), "Task Completed");
                                        tmrCheck.cancel();
                                    }
                                }
                            });
                        }
                    };
                }
                _timer.scheduleAtFixedRate(tmrCheck, 0, 250);
            }
        });
    }

    private void initializeLogic() {
        listMapEditedCode.clear();
        mCurrentLanguage = Language.JAVA;
        configLanguageAutoComplete();
        SyntaxManager.applyMonokaiTheme(this, codeEditorLayout, mCurrentLanguage);
        codeEditorLayout.setTextSize(12.0f);
        codeEditorLayout.setEnabled(false);
        currentValue.setText(getIntent().getStringExtra("current"));
        sc_id_string = getIntent().getStringExtra("sc_id");
        String absolutePath = new File("/storage/emulated/0/.sketchware/mysc/").getAbsolutePath();
        String absolutePath2 = new File("/storage/emulated/0/.sketchware/mysc/list/").getAbsolutePath();
        String str = new File("/storage/emulated/0/.sketchware/data").getAbsolutePath() + "/SourceEdited";
        path_SourceEdited = str;
        if (!FileUtil.isExistFile(str)) {
            FileUtil.makeDir(path_SourceEdited);
        }
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwaresecure".getBytes();
            instance.init(2, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            RandomAccessFile randomAccessFile = new RandomAccessFile(absolutePath2 + "/" + sc_id_string + "/project", "r");
            byte[] bArr = new byte[((int) randomAccessFile.length())];
            randomAccessFile.readFully(bArr);
            HashMap<String, Object> hashMap = new Gson().fromJson(new String(instance.doFinal(bArr)), new TypeToken<HashMap<String, Object>>() {
                /* class id.indosw.mod.DirectEditorActivity.AnonymousClass9 */
            }.getType());
            pkg_to_path = Objects.requireNonNull(hashMap.get("my_sc_pkg_name")).toString().replace(".", "/");
            nameAPK = Objects.requireNonNull(hashMap.get("my_ws_name")).toString() + ".apk";
        } catch (Exception e) {
            codeEditorLayout.setText(e.toString());
        }
        String str2 = absolutePath + "/" + sc_id_string + "/app/src/main/java/" + pkg_to_path + "/";
        String str3 = absolutePath + "/" + sc_id_string + "/app/src/main/res/layout/";
        path_manifest = absolutePath + "/" + sc_id_string + "/app/src/main/AndroidManifest.xml";
        path_bin = absolutePath + "/" + sc_id_string + "/bin";
        if (FileUtil.isExistFile(str2)) {
            FileUtil.listDir(str2, list_file_java);
            for (int i2 = 0; i2 < list_file_java.size(); i2++) {
                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put("filePath", list_file_java.get((int) repeatJava));
                hashMap2.put("fileName", Uri.parse(list_file_java.get((int) repeatJava)).getLastPathSegment());
                listMapJavaFile.add(hashMap2);
                spinnerJAVA.setAdapter(new SpinnerJAVAAdapter(listMapJavaFile));
                repeatJava += 1.0d;
            }
        }
        if (FileUtil.isExistFile(str3)) {
            FileUtil.listDir(str3, list_file_res);
            for (int i3 = 0; i3 < list_file_res.size(); i3++) {
                HashMap<String, Object> hashMap3 = new HashMap<>();
                hashMap3.put("filePath", list_file_res.get((int) repeatRes));
                hashMap3.put("fileName", Uri.parse(list_file_res.get((int) repeatRes)).getLastPathSegment());
                listMapResFile.add(hashMap3);
                spinnerRES.setAdapter(new SpinnerRESAdapter(listMapResFile));
                repeatRes += 1.0d;
            }
        }
        buttonManifest.setBackground(new GradientDrawable() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass10 */

            public GradientDrawable getIns(int i, int i2) {
                setCornerRadius((float) i);
                setColor(i2);
                return this;
            }
        }.getIns(7, -16740915));
        buttonManifest.setElevation(11.0f);
        linRES.setBackground(new GradientDrawable() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass11 */

            public GradientDrawable getIns(int i, int i2) {
                setCornerRadius((float) i);
                setColor(i2);
                return this;
            }
        }.getIns(7, -16740915));
        linRES.setElevation(11.0f);
        linJAVA.setBackground(new GradientDrawable() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass12 */

            public GradientDrawable getIns(int i, int i2) {
                setCornerRadius((float) i);
                setColor(i2);
                return this;
            }
        }.getIns(7, -16740915));
        linJAVA.setElevation(11.0f);
        btnSaveCodeEditor.setBackground(new GradientDrawable() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass13 */

            public GradientDrawable getIns(int i, int i2) {
                setCornerRadius((float) i);
                setColor(i2);
                return this;
            }
        }.getIns(17, -12434878));
        btnSaveCodeEditor.setElevation(17.0f);
        go_to_original.setBackground(new GradientDrawable() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass14 */

            public GradientDrawable getIns(int i, int i2) {
                setCornerRadius((float) i);
                setColor(i2);
                return this;
            }
        }.getIns(17, -16777216));
        go_to_original.setElevation(17.0f);
        show_list_editedcode.setBackground(new GradientDrawable() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass15 */

            public GradientDrawable getIns(int i, int i2) {
                setCornerRadius((float) i);
                setColor(i2);
                return this;
            }
        }.getIns(17, -16777216));
        show_list_editedcode.setElevation(17.0f);
        codeEditorLayout.addTextChangedListener(new TextWatcher() {
            /* class id.indosw.mod.DirectEditorActivity.AnonymousClass16 */

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                btnSaveCodeEditor.setVisibility(View.VISIBLE);
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, 2131034337));
        }
        if (FileUtil.isExistFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"))) {
            listMapEditedCode = new Gson().fromJson(FileUtil.readFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json")), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                /* class id.indosw.mod.DirectEditorActivity.AnonymousClass17 */
            }.getType());
            spinner_edited.setAdapter(new Spinner_editedAdapter(listMapEditedCode));
        } else {
            SketchwareUtil.showMessage(getApplicationContext(), "You haven't edited anything yet");
        }
        codeEditorLayout.setText("//This menu reads files inside /Internal Storage/.sketchware/mysc/.\n//Make sure you build your project first, or else no files will be shown!\n\n\n//Select a class, layout or the manifest file\n//by selecting it in the dropdown menu above\n\n//then --> EDIT\n//then --> SAVE\n//and --> activate the switch\n//to run the command\n//to replace the default code\n//with the code you edited");
    }

    private void configLanguageAutoComplete() {
        if (mCurrentLanguage == Language.JAVA) {
            languageKeywords = getResources().getStringArray(2131951616);
        }
        codeEditorLayout.setAdapter(new ArrayAdapter<>(this, 2131427821, 2131232635, languageKeywords));
    }

    @Override
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
    }

    public void _UpdateView(String str, String str2) {
        currentValue.setText(str2);
        codeEditorLayout.setText(FileUtil.readFile(str));
        codeEditorLayout.setEnabled(true);
        tempName = str2;
        tempPath = str;
    }

    public class SpinnerRESAdapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public SpinnerRESAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int i) {
            return _data.get(i);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(2131427820, null);
            }
            LinearLayout linearLayout = convertView.findViewById(2131232636);
            ImageView imageView = convertView.findViewById(2131232637);
            TextView textView = convertView.findViewById(2131232638);
            if (Objects.requireNonNull(_data.get(position).get("fileName")).toString().contains(".java")) {
                imageView.setImageResource(2131166378);
            } else if (Objects.requireNonNull(_data.get(position).get("fileName")).toString().contains(".xml")) {
                imageView.setImageResource(2131166379);
            }
            textView.setText(Objects.requireNonNull(_data.get(position).get("fileName")).toString());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                /* class id.indosw.mod.DirectEditorActivity.SpinnerRESAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    _UpdateView(Objects.requireNonNull(_data.get(position).get("filePath")).toString(), Objects.requireNonNull(_data.get(position).get("fileName")).toString());
                }
            });
            linearLayout.setBackgroundColor(-16746571);
            textView.setTextColor(-1);
            return convertView;
        }
    }

    public class SpinnerJAVAAdapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public SpinnerJAVAAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int i) {
            return _data.get(i);
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(2131427820, null);
            }
            LinearLayout linearLayout = convertView.findViewById(2131232636);
            ImageView imageView = convertView.findViewById(2131232637);
            TextView textView = convertView.findViewById(2131232638);
            if (Objects.requireNonNull(_data.get(i).get("fileName")).toString().contains(".java")) {
                imageView.setImageResource(2131166378);
            } else if (Objects.requireNonNull(_data.get(i).get("fileName")).toString().contains(".xml")) {
                imageView.setImageResource(2131166379);
            }
            textView.setText(Objects.requireNonNull(_data.get(i).get("fileName")).toString());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                /* class id.indosw.mod.DirectEditorActivity.SpinnerJAVAAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    _UpdateView(Objects.requireNonNull(_data.get(i).get("filePath")).toString(), Objects.requireNonNull(_data.get(i).get("fileName")).toString());
                }
            });
            linearLayout.setBackgroundColor(-16746571);
            textView.setTextColor(-1);
            return convertView;
        }
    }

    public class Spinner_editedAdapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public Spinner_editedAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int i) {
            return _data.get(i);
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(2131427820, null);
            }
            LinearLayout linearLayout = convertView.findViewById(2131232636);
            ImageView imageView = convertView.findViewById(2131232637);
            TextView textView = convertView.findViewById(2131232638);
            if (Uri.parse(Objects.requireNonNull(_data.get(i).get("edited")).toString()).getLastPathSegment().contains(".java")) {
                imageView.setImageResource(2131166378);
            } else if (Uri.parse(Objects.requireNonNull(_data.get(i).get("edited")).toString()).getLastPathSegment().contains(".xml")) {
                imageView.setImageResource(2131166379);
            }
            textView.setText(Uri.parse(Objects.requireNonNull(_data.get(i).get("edited")).toString()).getLastPathSegment());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                /* class id.indosw.mod.DirectEditorActivity.Spinner_editedAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    _UpdateView(Objects.requireNonNull(_data.get(i).get("edited")).toString(), Uri.parse(Objects.requireNonNull(_data.get(i).get("edited")).toString()).getLastPathSegment());
                }
            });
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                /* class id.indosw.mod.DirectEditorActivity.Spinner_editedAdapter.AnonymousClass2 */

                public boolean onLongClick(View view) {
                    dialog_deleteTask.setTitle("Remove Edited Code");
                    dialog_deleteTask.setMessage("Are you sure you want to remove this from the list of edited codes?");
                    dialog_deleteTask.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        /* class id.indosw.mod.DirectEditorActivity.Spinner_editedAdapter.AnonymousClass2.AnonymousClass1 */

                        public void onClick(DialogInterface dialogInterface, int i) {
                            FileUtil.deleteFile(Objects.requireNonNull(_data.get(i).get("edited")).toString());
                            _data.remove(i);
                            SketchwareUtil.showMessage(getApplicationContext(), "Successfully deleted");
                            FileUtil.writeFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"), new Gson().toJson(_data));
                            listMapEditedCode.clear();
                            if (FileUtil.isExistFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json"))) {
                                listMapEditedCode = new Gson().fromJson(FileUtil.readFile(path_SourceEdited.concat("/" + sc_id_string + "/Task.json")), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                                    /* class id.indosw.mod.DirectEditorActivity.Spinner_editedAdapter.AnonymousClass2.AnonymousClass1.AnonymousClass1 */
                                }.getType());
                                spinner_edited.setAdapter(new Spinner_editedAdapter(listMapEditedCode));
                                return;
                            }
                            SketchwareUtil.showMessage(getApplicationContext(), "Nothing You Edited Yet");
                        }
                    });
                    dialog_deleteTask.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        /* class id.indosw.mod.DirectEditorActivity.Spinner_editedAdapter.AnonymousClass2.AnonymousClass2 */

                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog_deleteTask.create().show();
                    return true;
                }
            });
            linearLayout.setBackgroundColor(-16746571);
            textView.setTextColor(-1);
            return convertView;
        }
    }
}
Fial New Speed Mod For Editext Mod By Ninja Coder = Edittext.setsizeText=("12sp");
MOD.MORGE.IN.SIZEEDITTEXT.IN.VERTICAL();
FILEIS.MORE.IS.MAINNEW();
GETLINE(0~999999999);
LINESTART(0)
LINEFINALEND(999999999)
if0=0
{
get.text(ninjacoder);
set Speed Is String New = New
}
   }
      };

////Mod By Is Ninja Coder
Code Is Java J2 And C#
