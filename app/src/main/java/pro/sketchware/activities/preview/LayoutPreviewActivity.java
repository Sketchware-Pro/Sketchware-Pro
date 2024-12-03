package pro.sketchware.activities.preview;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;

import a.a.a.Op;
import a.a.a.Rp;
import a.a.a.kC;
import a.a.a.mB;
import a.a.a.sy;
import a.a.a.wq;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;

import pro.sketchware.databinding.ActivityLayoutPreviewBinding;
import pro.sketchware.tools.ViewBeanParser;
import pro.sketchware.utility.SketchwareUtil;

import java.io.File;
import java.util.ArrayList;

public class LayoutPreviewActivity extends BaseAppCompatActivity {

    private ActivityLayoutPreviewBinding binding;

    private ViewPane pane;
    
    private String content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityLayoutPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        var toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Layout Preview");
        getSupportActionBar().setSubtitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        content = getIntent().getStringExtra("content");
        var sc_id = getIntent().getStringExtra("sc_id");
        pane = binding.pane;
        pane.setScId(sc_id);
        pane.setVerticalScrollBarEnabled(true);
        kC kCVar = new kC("", wq.a() + "/image/data/", "", "");
        kCVar.b(Op.g().f());
        pane.setResourceManager(kCVar);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (content != null) {
            try {
                var parser = new ViewBeanParser(new File(content));
                loadViews(parser.parse());
            } catch (Exception e) {
                SketchwareUtil.toastError(e.toString());
            }
        } else {
            SketchwareUtil.toastError("content is null");
        }
    }

    private sy loadView(ViewBean view) {
        var itemView = pane.createItemView(view);
        pane.addViewAndUpdateIndex(itemView);
        return (sy) itemView;
    }

    private sy loadViews(ArrayList<ViewBean> views) {
        sy itemView = null;
        for (ViewBean view : views) {
            if (views.indexOf(view) == 0) {
                view.parent = "root";
                view.parentType = 0;
                view.preParent = null;
                view.preParentType = -1;
                itemView = loadView(view);
            } else {
                loadView(view);
            }
        }
        return itemView;
    }
}
