package com.besome.sketch.editor.manage.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.By;
import a.a.a.Fw;
import a.a.a.MA;
import a.a.a.bB;
import a.a.a.eC;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.xw;

public class ManageViewActivity extends BaseAppCompatActivity implements OnClickListener, ViewPager.OnPageChangeListener {
    private static final int TAB_COUNT = 2;
    private static final int REQUEST_CODE_ADD_ACTIVITY = 264;
    private static final int REQUEST_CODE_ADD_CUSTOM_VIEW = 266;

    private LinearLayout actionButtonsContainer;
    private boolean selecting = false;
    private String isAppCompatEnabled = "N";
    // signature mustn't be changed: used in La/a/a/Bw;->a(Landroidx/recyclerview/widget/RecyclerView;II)V, La/a/a/tw;->a(Landroidx/recyclerview/widget/RecyclerView;II)V
    public FloatingActionButton s;
    private Fw activitiesFragment;
    private xw customViewsFragment;
    private ViewPager viewPager;
    private final int[] x = new int[19];
    private String sc_id;

    public final String a(int var1, String var2) {
        String var3 = wq.b(var1);
        StringBuilder var4 = new StringBuilder();
        var4.append(var3);
        int[] var5 = x;
        int var6 = var5[var1] + 1;
        var5[var1] = var6;
        var4.append(var6);
        String var9 = var4.toString();
        ArrayList<ViewBean> var12 = jC.a(sc_id).d(var2);
        var2 = var9;

        while (true) {
            boolean var7 = false;
            Iterator<ViewBean> var10 = var12.iterator();

            boolean var13;
            while (true) {
                var13 = var7;
                if (!var10.hasNext()) {
                    break;
                }

                if (var2.equals(var10.next().id)) {
                    var13 = true;
                    break;
                }
            }

            if (!var13) {
                return var2;
            }

            StringBuilder var8 = new StringBuilder();
            var8.append(var3);
            int[] var11 = x;
            var6 = var11[var1] + 1;
            var11[var1] = var6;
            var8.append(var6);
            var2 = var8.toString();
        }
    }

    @Override
    public void onPageScrollStateChanged(int var1) {
    }

    @Override
    public void onPageScrolled(int var1, float var2, int var3) {
    }

    public final void a(ProjectFileBean var1, ArrayList<ViewBean> var2) {
        jC.a(sc_id);
        for (ViewBean viewBean : eC.a(var2)) {
            viewBean.id = a(viewBean.type, var1.getXmlName());
            jC.a(sc_id).a(var1.getXmlName(), viewBean);
            if (viewBean.type == ViewBean.VIEW_TYPE_WIDGET_BUTTON && var1.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                jC.a(sc_id).a(var1.getJavaName(), EventBean.EVENT_TYPE_VIEW, viewBean.type, viewBean.id, "onClick");
            }
        }
    }

    // signature mustn't be changed: used in La/a/a/Dw;->onLongClick(Landroid/view/View;)Z, La/a/a/vw;->onLongClick(Landroid/view/View;)Z
    public void a(boolean var1) {
        selecting = var1;
        invalidateOptionsMenu();
        if (selecting) {
            actionButtonsContainer.setVisibility(View.VISIBLE);
        } else {
            actionButtonsContainer.setVisibility(View.GONE);
        }

        activitiesFragment.a(selecting);
        customViewsFragment.a(selecting);
    }

    @Override
    public void onPageSelected(int var1) {
        s.show();
    }

    // signature mustn't be changed: used in La/a/a/Fw;->b(Lcom/besome/sketch/beans/ProjectFileBean;)V
    public void b(String var1) {
        customViewsFragment.a(var1);
        customViewsFragment.g();
    }

    // signature mustn't be changed: used in La/a/a/Fw;->b(Lcom/besome/sketch/beans/ProjectFileBean;)V, La/a/a/Fw;->f()V
    public void c(String var1) {
        customViewsFragment.b(var1);
        customViewsFragment.g();
    }

    public ArrayList<String> l() {
        ArrayList<String> projectLayoutFiles = new ArrayList<>();
        projectLayoutFiles.add("debug");
        ArrayList<ProjectFileBean> activitiesFiles = activitiesFragment.c();
        ArrayList<ProjectFileBean> customViewsFiles = customViewsFragment.c();

        for (ProjectFileBean projectFileBean : activitiesFiles) {
            projectLayoutFiles.add(projectFileBean.fileName);
        }

        for (ProjectFileBean projectFileBean : customViewsFiles) {
            projectLayoutFiles.add(projectFileBean.fileName);
        }

        return projectLayoutFiles;
    }

    public final void m() {
        jC.b(sc_id).a(activitiesFragment.c());
        jC.b(sc_id).b(customViewsFragment.c());
        jC.b(sc_id).l();
        jC.b(sc_id).j();
        jC.a(sc_id).a(jC.b(sc_id));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProjectFileBean projectFileBean;
        if (requestCode == REQUEST_CODE_ADD_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                projectFileBean = data.getParcelableExtra("project_file");
                activitiesFragment.a(projectFileBean);
                if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                    b(projectFileBean.getDrawerName());
                }

                if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER) || projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                    jC.c(sc_id).c().useYn = "Y";
                }

                if (data.hasExtra("preset_views")) {
                    a(projectFileBean, data.getParcelableArrayListExtra("preset_views"));
                }
            }
        } else if (requestCode == REQUEST_CODE_ADD_CUSTOM_VIEW && resultCode == RESULT_OK) {
            projectFileBean = data.getParcelableExtra("project_file");
            customViewsFragment.a(projectFileBean);
            customViewsFragment.g();
            if (data.hasExtra("preset_views")) {
                a(projectFileBean, data.getParcelableArrayListExtra("preset_views"));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (selecting) {
            a(false);
        } else {
            k();

            try {
                new Handler().postDelayed(() -> (new a(getApplicationContext())).execute(), 500L);
            } catch (Exception e) {
                e.printStackTrace();
                h();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int viewId = v.getId();
            if (viewId == R.id.btn_cancel) {
                if (selecting) {
                    a(false);
                }
            } else if (viewId == R.id.btn_delete) {
                if (selecting) {
                    activitiesFragment.f();
                    customViewsFragment.f();
                    a(false);
                    activitiesFragment.g();
                    customViewsFragment.g();
                    bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_complete_delete), bB.TOAST_WARNING).show();
                    s.show();
                }
            } else if (viewId == R.id.fab) {
                a(false);

                boolean isActivitiesTab = viewPager.getCurrentItem() == 0;
                Intent intent = new Intent(this, isActivitiesTab ? AddViewActivity.class : AddCustomViewActivity.class);
                intent.putStringArrayListExtra("screen_names", l());
                if (isActivitiesTab) {
                    intent.putExtra("request_code", REQUEST_CODE_ADD_ACTIVITY);
                }
                startActivityForResult(intent, isActivitiesTab ? REQUEST_CODE_ADD_ACTIVITY : REQUEST_CODE_ADD_CUSTOM_VIEW);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }

        setContentView(R.layout.manage_view);
        Toolbar m = findViewById(R.id.toolbar);
        setSupportActionBar(m);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_view));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        m.setNavigationOnClickListener(view -> {
            if (!mB.a()) onBackPressed();
        });

        actionButtonsContainer = findViewById(R.id.layout_btn_group);
        Button delete = findViewById(R.id.btn_delete);
        Button cancel = findViewById(R.id.btn_cancel);
        delete.setText(xB.b().a(getApplicationContext(), R.string.common_word_delete));
        cancel.setText(xB.b().a(getApplicationContext(), R.string.common_word_cancel));
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);

        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
            isAppCompatEnabled = getIntent().getStringExtra("compatUseYn");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            isAppCompatEnabled = savedInstanceState.getString("compatUseYn");
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new ManageViewActivity.b(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(TAB_COUNT);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
        s = findViewById(R.id.fab);
        s.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_screen_menu, menu);
        menu.findItem(R.id.menu_screen_delete).setVisible(!selecting);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_screen_delete) {
            a(!selecting);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle newState) {
        newState.putString("sc_id", sc_id);
        newState.putString("compatUseYn", isAppCompatEnabled);
        super.onSaveInstanceState(newState);
    }

    public class a extends MA {
        public a(Context var2) {
            super(var2);
            addTask(this);
        }

        @Override
        public void a() {
            h();
            setResult(RESULT_OK);
            finish();
        }

        @Override
        public void a(String var1) {
            h();
        }

        @Override
        public void b() {
            try {
                publishProgress(xB.b().a(getApplicationContext(), R.string.common_message_progress));
                m();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    throw new By(xB.b().a(super.a, R.string.common_error_unknown));
                } catch (By ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public class b extends FragmentPagerAdapter {
        public String[] f;

        public b(FragmentManager fragmentManager) {
            super(fragmentManager);
            f = new String[]{xB.b().a(getApplicationContext(), R.string.common_word_view).toUpperCase(), xB.b().a(getApplicationContext(), R.string.common_word_custom_view).toUpperCase()};
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return f[position];
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Fragment var3 = (Fragment) super.instantiateItem(container, position);
            if (position != 0) {
                customViewsFragment = (xw) var3;
            } else {
                activitiesFragment = (Fw) var3;
            }

            return var3;
        }

        @Override
        @NonNull
        public Fragment getItem(int position) {
            return position != 0 ? new xw() : new Fw();
        }
    }
}
