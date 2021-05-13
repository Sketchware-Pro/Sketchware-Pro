package mod.tyron;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

import a.a.a.yq;
import mod.agus.jcoderz.lib.FileUtil;
import mod.tyron.compiler.Compiler;
import mod.tyron.compiler.IncrementalD8Compiler;
import mod.tyron.compiler.IncrementalJavaCompiler;

public class TestActivity extends Activity {

    private List<File> changedList = new ArrayList<>();

    //activity for testing file changes

    IncrementalJavaCompiler compiler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout rootView = new LinearLayout(this);
        rootView.setOrientation(LinearLayout.VERTICAL);

        setContentView(rootView);

       // if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        //}


        //simulate your project here
        HashMap<String, Object> testProject = new HashMap<>();
        testProject.put("sc_id", "748");
        testProject.put("my_sc_pkg_name", "in.sketchub.ex");
        testProject.put("my_ws_name", "Sketchub");
        testProject.put("my_app_name", "Sketchub");
        testProject.put("sc_ver_code", "1.0");
        testProject.put("sc_ver_name", "1.0");
        String path = FileUtil.getExternalStorageDir() + "/.sketchware/mysc/748/";

        yq projectConfig = new yq(this, path, testProject);

        compiler = new IncrementalJavaCompiler(projectConfig);

        compiler.setOnResultListener(new Compiler.Result() {
            @Override
            public void onResult(boolean success, int type, Object... args) {
                Log.d("COMPILER TEST", "is successful: " + success + " message: " + args[0]);
            }
        });

        //listview, idk how to use recyclerview here
        ListView listView = new ListView(this);

        rootView.addView(listView);
        ((LinearLayout.LayoutParams) listView.getLayoutParams()).weight = 1;

        Button button = new Button(this);
        button.setText("COMPILE");
        button.setOnClickListener((v) -> {
//            changedList = compiler.getSourceFiles();
//            listView.setAdapter(new ArrayAdapter<File>(this, android.R.layout.simple_list_item_1, changedList));
//            compiler.mergeClasses(new ArrayList<>(changedList));

           // compiler.compile();
            testAsync async = new testAsync();
            async.execute();

        });
        rootView.addView(button);

        Button button1 = new Button(this);
        button1.setText("RUN D8");
        button1.setOnClickListener((v) -> {
            IncrementalD8Compiler d8Compiler = new IncrementalD8Compiler(projectConfig);
            d8Compiler.compile();
        });
        rootView.addView(button1);

    }

    private class testAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            compiler.compile();
            return null;
        }
    }

}
