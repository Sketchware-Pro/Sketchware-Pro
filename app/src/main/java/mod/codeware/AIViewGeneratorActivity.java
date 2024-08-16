package mod.codeware;

import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.android.tools.r8.internal.Qu;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Arrays;

import a.a.a.Lx;
import a.a.a.Op;
import a.a.a.kC;
import a.a.a.sy;
import a.a.a.wq;

public class AIViewGeneratorActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private String question;
    private ViewPane viewPane;
    private EditText questionInput;

    private ProgressBar progressBar;
    private Button generateButton;

    @SuppressLint("CutPasteId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiview_generator);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle("Ai view generator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        question = getIntent().getStringExtra("question");
        viewPane = findViewById(R.id.pane);
        viewPane.setVerticalScrollBarEnabled(true);
        kC kCVar = new kC("", wq.a() + "/image/data/", "", "");
        kCVar.b(Op.g().f());
        viewPane.setResourceManager(kCVar);
        EasyDeleteEditText input = findViewById(R.id.ed_input);
        questionInput = input.getEditText();
        questionInput.setPrivateImeOptions("defaultInputmode=english;");

        input.setHint("Enter your question");
        Button save = findViewById(R.id.generateButton);
        save.setText("Generate");
        save.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);
        generateButton = findViewById(R.id.generateButton);
    }

    public void loadView(String json){

        viewPane.d();

        ArrayList<ViewBean> viewBeans = new ArrayList<>();
        ArrayList<String> viewJsonList = new ArrayList<>(Arrays.asList(json.split("\n")));

        for(String item : viewJsonList){
            Log.d("TAG", "loadView: " + item.trim());
            ViewBean viewBean = new Gson().fromJson(item.trim(), ViewBean.class);
            viewBeans.add(viewBean);
        }

        loadViews(viewBeans);
        viewPane.invalidate();
    }

    private sy loadViews(ArrayList<ViewBean> views) {
        sy syVar = null;
        for (ViewBean view : views) {
            if (views.indexOf(view) == 0) {
                view.parent = "root";
                view.parentType = 0;
                view.preParent = null;
                view.preParentType = -1;
                syVar = loadView(view);
            } else {
                loadView(view);
            }
        }
        return syVar;
    }

    private sy loadView(ViewBean view) {
        View v = viewPane.b(view);
        viewPane.a(v);
        return (sy) v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.generateButton) {
           question =  questionInput.getText().toString().trim();
           loadAi(question);
        }
    }

    private void loadAi(String question) {

        progressBar.setVisibility(View.VISIBLE);
        generateButton.setVisibility(View.GONE);

        HiveAI hiveAI = new HiveAI();
        hiveAI.generateView(question, resultText -> {
            Log.d("TAG", "loadAi: " + resultText);
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                generateButton.setVisibility(View.VISIBLE);
                loadView(resultText);
            });
        });
    }
}