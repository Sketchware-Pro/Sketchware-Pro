package com.besome.sketch.editor.manage;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;

import java.util.ArrayList;

import a.a.a.GB;
import a.a.a.NB;
import a.a.a.Op;
import a.a.a.Rp;
import a.a.a.bB;
import a.a.a.kC;
import a.a.a.mB;
import a.a.a.sy;
import a.a.a.wq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class ShowWidgetCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String widgetName;
    private ViewPane viewPane;
    private ScrollView scrollView;
    private EditText widgetNameInput;
    private LinearLayout actionContainer;
    private NB widgetNameValidator;

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

    private void setActionContainerHeight() {
        actionContainer.measure(0, 0);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDisplayMetrics().heightPixels - GB.a(e) - GB.f(e) - actionContainer.getMeasuredHeight()));
        scrollView.requestLayout();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.save_button && widgetNameValidator.b()) {
            Rp.h().a(widgetName, Helper.getText(widgetNameInput), true);
            bB.a(getApplicationContext(), getTranslatedString(R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setActionContainerHeight();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_collection_show_widget);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(getTranslatedString(R.string.design_manager_widget_title_actionbar_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        widgetName = getIntent().getStringExtra("widget_name");
        viewPane = findViewById(R.id.pane);
        viewPane.setVerticalScrollBarEnabled(true);
        kC kCVar = new kC("", wq.a() + "/image/data/", "", "");
        kCVar.b(Op.g().f());
        viewPane.setResourceManager(kCVar);
        EasyDeleteEditText input = findViewById(R.id.ed_input);
        widgetNameInput = input.getEditText();
        widgetNameInput.setPrivateImeOptions("defaultInputmode=english;");
        widgetNameInput.setText(widgetName);
        input.setHint(getTranslatedString(R.string.design_manager_widget_hint_enter_widget_name));
        Button save = findViewById(R.id.save_button);
        save.setText(getTranslatedString(R.string.common_word_save));
        save.setOnClickListener(this);
        widgetNameValidator = new NB(this, input.getTextInputLayout(), Rp.h().g());
        actionContainer = findViewById(R.id.layout_button);
        scrollView = findViewById(R.id.scroll_view);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadViews(Rp.h().a(widgetName).widgets);
        setActionContainerHeight();
    }

    private sy loadView(ViewBean view) {
        View v = viewPane.createItemView(view);
        viewPane.addViewAndUpdateIndex(v);
        return (sy) v;
    }
}
