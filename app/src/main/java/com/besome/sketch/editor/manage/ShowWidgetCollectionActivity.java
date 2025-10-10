package com.besome.sketch.editor.manage;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ItemView;
import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.util.ArrayList;

import a.a.a.GB;
import a.a.a.NB;
import a.a.a.Op;
import a.a.a.Rp;
import a.a.a.bB;
import a.a.a.kC;
import a.a.a.mB;
import a.a.a.wq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageCollectionShowWidgetBinding;

public class ShowWidgetCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String widgetName;
    private EditText widgetNameInput;
    private NB widgetNameValidator;
    private ManageCollectionShowWidgetBinding binding;

    private ItemView loadViews(ArrayList<ViewBean> views) {
        ItemView syVar = null;
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
        binding.layoutButton.measure(0, 0);
        binding.scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDisplayMetrics().heightPixels - GB.a(getApplicationContext()) - GB.f(getApplicationContext()) - binding.layoutButton.getMeasuredHeight()));
        binding.scrollView.requestLayout();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.save_button && widgetNameValidator.b()) {
            Rp.h().a(widgetName, Helper.getText(widgetNameInput), true);
            bB.a(getApplicationContext(), getString(R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
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
        binding = ManageCollectionShowWidgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        widgetName = getIntent().getStringExtra("widget_name");
        String sc_id = getIntent().getStringExtra("sc_id");
        binding.pane.initialize(sc_id, true);
        binding.pane.setVerticalScrollBarEnabled(true);
        kC kCVar = new kC("", wq.a() + "/image/data/", "", "");
        kCVar.b(Op.g().f());
        binding.pane.setResourceManager(kCVar);
        widgetNameInput = binding.edInput.getEditText();
        widgetNameInput.setPrivateImeOptions("defaultInputmode=english;");
        widgetNameInput.setText(widgetName);
        binding.edInput.setHint(getString(R.string.design_manager_widget_hint_enter_widget_name));
        binding.saveButton.setOnClickListener(this);
        widgetNameValidator = new NB(this, binding.edInput.getTextInputLayout(), Rp.h().g());
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadViews(Rp.h().a(widgetName).widgets);
        setActionContainerHeight();
    }

    private ItemView loadView(ViewBean view) {
        View v = binding.pane.createItemView(view);
        binding.pane.addViewAndUpdateIndex(v);
        return (ItemView) v;
    }
}
