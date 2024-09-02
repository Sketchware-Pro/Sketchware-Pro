package com.besome.sketch.editor.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.view.AddCustomViewActivity;
import com.besome.sketch.editor.manage.view.AddViewActivity;
import com.besome.sketch.editor.manage.view.PresetSettingActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.SelectableButtonBar;
import com.google.android.gms.analytics.HitBuilders;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.eC;
import a.a.a.hC;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.rq;
import a.a.a.wq;
import a.a.a.xB;

public class ViewSelectorActivity extends BaseAppCompatActivity {
    private Adapter adapter;
    private String sc_id;
    private ProjectFileBean projectFile;
    private TextView empty_message;
    private String currentXml;
    private int selectedTab;
    private boolean isCustomView = false;
    private final int[] x = new int[19];

    private final int TAB_ACTIVITY = ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY;
    private final int TAB_CUSTOM_VIEW = ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW;

    private int getViewIcon(int i) {
        String replace = String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0');
        return getApplicationContext().getResources().getIdentifier("activity_" + replace, "drawable", getApplicationContext().getPackageName());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
    }

    private ArrayList<String> getScreenNames() {
        ArrayList<String> screenNames = new ArrayList<>();
        ArrayList<ProjectFileBean> b = jC.b(this.sc_id).b();
        if (b != null) {
            Iterator<ProjectFileBean> it = b.iterator();
            while (it.hasNext()) {
                screenNames.add(it.next().fileName);
            }
        }
        ArrayList<ProjectFileBean> c = jC.b(this.sc_id).c();
        if (c != null) {
            Iterator<ProjectFileBean> it2 = c.iterator();
            while (it2.hasNext()) {
                screenNames.add(it2.next().fileName);
            }
        }
        return screenNames;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 264:
                if (resultCode == RESULT_OK) {
                    ProjectFileBean projectFileBean = (ProjectFileBean) data.getParcelableExtra("project_file");
                    jC.b(this.sc_id).a(projectFileBean);
                    if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                        jC.b(this.sc_id).a(2, projectFileBean.getDrawerName());
                    }
                    if (data.hasExtra("preset_views")) {
                        a(projectFileBean, data.getParcelableArrayListExtra("preset_views"));
                    }
                    jC.b(this.sc_id).j();
                    jC.b(this.sc_id).l();
                    this.adapter.notifyDataSetChanged();
                    return;
                }
                return;
            case 265:
                if (resultCode == RESULT_OK) {
                    ProjectFileBean projectFileBean2 = (ProjectFileBean) data.getParcelableExtra("project_file");
                    ProjectFileBean projectFileBean3 = jC.b(this.sc_id).b().get(this.adapter.selectedItem);
                    projectFileBean3.keyboardSetting = projectFileBean2.keyboardSetting;
                    projectFileBean3.orientation = projectFileBean2.orientation;
                    projectFileBean3.options = projectFileBean2.options;
                    if (projectFileBean2.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                        jC.b(this.sc_id).a(2, projectFileBean2.getDrawerName());
                    } else {
                        jC.b(this.sc_id).b(2, projectFileBean2.getDrawerName());
                    }
                    if (projectFileBean2.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)
                            || projectFileBean2.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                        jC.c(this.sc_id).c().useYn = "Y";
                    }
                    Adapter adapter = this.adapter;
                    adapter.notifyItemChanged(adapter.selectedItem);
                    Intent intent = new Intent();
                    intent.putExtra("project_file", projectFileBean2);
                    setResult(RESULT_OK, intent);
                    return;
                }
                return;
            case 266:
                if (resultCode == RESULT_OK) {
                    ProjectFileBean projectFileBean4 = (ProjectFileBean) data.getParcelableExtra("project_file");
                    jC.b(this.sc_id).a(projectFileBean4);
                    if (data.hasExtra("preset_views")) {
                        a(projectFileBean4, data.getParcelableArrayListExtra("preset_views"));
                    }
                    jC.b(this.sc_id).j();
                    jC.b(this.sc_id).l();
                    this.adapter.notifyDataSetChanged();
                    return;
                }
                return;
            default:
                switch (requestCode) {
                    case 276:
                        if (resultCode == RESULT_OK) {
                            ProjectFileBean projectFileBean5 = (ProjectFileBean) data.getParcelableExtra("preset_data");
                            ProjectFileBean projectFileBean6 = jC.b(this.sc_id).b().get(this.adapter.selectedItem);
                            projectFileBean6.keyboardSetting = projectFileBean5.keyboardSetting;
                            projectFileBean6.orientation = projectFileBean5.orientation;
                            projectFileBean6.options = projectFileBean5.options;
                            if (projectFileBean5.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)
                                    || projectFileBean5.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                                jC.c(this.sc_id).c().useYn = "Y";
                            }
                            a(projectFileBean5, projectFileBean6, requestCode);
                            jC.b(this.sc_id).j();
                            this.adapter.notifyDataSetChanged();
                            Intent intent2 = new Intent();
                            intent2.putExtra("project_file", projectFileBean6);
                            setResult(RESULT_OK, intent2);
                            return;
                        }
                        return;
                    case 277:
                    case 278:
                        if (resultCode == RESULT_OK) {
                            ProjectFileBean projectFileBean7 = (ProjectFileBean) data.getParcelableExtra("preset_data");
                            ProjectFileBean projectFileBean8 = jC.b(this.sc_id).c().get(this.adapter.selectedItem);
                            a(projectFileBean7, projectFileBean8, requestCode);
                            jC.b(this.sc_id).j();
                            this.adapter.notifyDataSetChanged();
                            Intent intent3 = new Intent();
                            intent3.putExtra("project_file", projectFileBean8);
                            setResult(RESULT_OK, intent3);
                            return;
                        }
                        return;
                    default:
                        return;
                }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_selector_popup_select_xml);
        Intent intent = getIntent();
        if (savedInstanceState == null) {
            this.sc_id = intent.getStringExtra("sc_id");
            this.currentXml = intent.getStringExtra("current_xml");
            this.isCustomView = intent.getBooleanExtra("is_custom_view", false);
        } else {
            this.sc_id = savedInstanceState.getString("sc_id");
            this.currentXml = savedInstanceState.getString("current_xml");
            this.isCustomView = savedInstanceState.getBoolean("is_custom_view");
        }
        if (this.isCustomView) {
            this.selectedTab = TAB_CUSTOM_VIEW;
        } else {
            this.selectedTab = TAB_ACTIVITY;
        }
        SelectableButtonBar button_bar = (SelectableButtonBar) findViewById(R.id.button_bar);
        this.empty_message = (TextView) findViewById(R.id.empty_message);
        RecyclerView list_xml = (RecyclerView) findViewById(R.id.list_xml);
        ImageView add_button = (ImageView) findViewById(R.id.add_button);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        button_bar.a(TAB_ACTIVITY, xB.b().a(this, R.string.common_word_view).toUpperCase());
        button_bar.a(TAB_CUSTOM_VIEW, xB.b().a(this, R.string.common_word_custom_view).toUpperCase());
        button_bar.setSelectedItemByIndex(this.selectedTab);
        button_bar.a();
        this.adapter = new Adapter();
        list_xml.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        list_xml.setHasFixedSize(true);
        list_xml.setAdapter(this.adapter);
        button_bar.setListener(selectedItemKey -> {
            if (selectedItemKey != selectedTab) {
                selectedTab = selectedItemKey;
                adapter.notifyDataSetChanged();
                empty_message.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }
        });
        this.empty_message.setText(xB.b().a(this, R.string.design_manager_view_message_no_view));
        add_button.setOnClickListener(v -> {
            if (!mB.a()) {
                if (selectedTab == TAB_ACTIVITY) {
                    Intent i = new Intent(getApplicationContext(), AddViewActivity.class);
                    i.putStringArrayListExtra("screen_names", getScreenNames());
                    i.putExtra("request_code", 264);
                    startActivityForResult(i, 264);
                } else if (selectedTab == TAB_CUSTOM_VIEW) {
                    Intent i = new Intent(getApplicationContext(), AddCustomViewActivity.class);
                    i.putStringArrayListExtra("screen_names", getScreenNames());
                    startActivityForResult(i, 266);
                }
            }
        });
        container.setOnClickListener(v -> finish());
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.d.setScreenName(ViewSelectorActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", this.sc_id);
        outState.putString("current_xml", this.currentXml);
        outState.putBoolean("is_custom_view", this.isCustomView);
        super.onSaveInstanceState(outState);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private int selectedItem = -1;

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final LinearLayout container;
            public final ImageView img_edit;
            public final ImageView img_view;
            public final TextView tv_filename;
            public final TextView tv_linked_filename;
            public final ImageView img_preset_setting;

            public ViewHolder(View itemView) {
                super(itemView);
                this.container = (LinearLayout) itemView.findViewById(R.id.container);
                this.img_edit = (ImageView) itemView.findViewById(R.id.img_edit);
                this.img_view = (ImageView) itemView.findViewById(R.id.img_view);
                this.tv_filename = (TextView) itemView.findViewById(R.id.tv_filename);
                this.tv_linked_filename = (TextView) itemView.findViewById(R.id.tv_linked_filename);
                this.img_preset_setting = (ImageView) itemView.findViewById(R.id.img_preset_setting);
                itemView.setOnClickListener(v -> {
                    if (!mB.a()) {
                        selectedItem = getLayoutPosition();
                        hC hC = jC.b(sc_id);
                        ArrayList<ProjectFileBean> list = switch (selectedTab) {
                            case TAB_ACTIVITY -> hC.b();
                            case TAB_CUSTOM_VIEW -> hC.c();
                            default -> null;
                        };
                        if (list != null) {
                            projectFile = list.get(getLayoutPosition());
                        }
                        Intent intent = new Intent();
                        intent.putExtra("project_file", projectFile);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                this.img_view.setOnClickListener(v -> {
                    if (selectedTab == TAB_ACTIVITY && !mB.a()) {
                        selectedItem = getLayoutPosition();
                        Intent intent = new Intent(getApplicationContext(), AddViewActivity.class);
                        intent.putExtra("project_file", jC.b(sc_id).b().get(getLayoutPosition()));
                        intent.putExtra("request_code", 265);
                        startActivityForResult(intent, 265);
                    }
                });
                this.img_preset_setting.setOnClickListener(v -> {
                    if (!mB.a()) {
                        selectedItem = getLayoutPosition();
                        int requestCode = a(jC.b(sc_id).b().get(getLayoutPosition()));
                        Intent intent = new Intent(getApplicationContext(), PresetSettingActivity.class);
                        intent.putExtra("request_code", requestCode);
                        intent.putExtra("edit_mode", true);
                        startActivityForResult(intent, requestCode);
                    }
                });
            }
        }

        public Adapter() {
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.container.setBackgroundColor(ViewSelectorActivity.this.getResources().getColor(R.color.transparent));
            int i = ViewSelectorActivity.this.selectedTab;
            if (i == TAB_ACTIVITY) {
                viewHolder.tv_filename.setVisibility(View.VISIBLE);
                viewHolder.tv_linked_filename.setVisibility(View.VISIBLE);
                ProjectFileBean projectFileBean = jC.b(ViewSelectorActivity.this.sc_id).b().get(position);
                String xmlName = projectFileBean.getXmlName();
                if (ViewSelectorActivity.this.currentXml.equals(xmlName)) {
                    viewHolder.container.setBackgroundColor(ViewSelectorActivity.this.getResources().getColor(R.color.scolor_dark_yellow_01));
                }
                String javaName = projectFileBean.getJavaName();
                viewHolder.img_edit.setVisibility(View.VISIBLE);
                viewHolder.img_view.setImageResource(ViewSelectorActivity.this.getViewIcon(projectFileBean.options));
                viewHolder.tv_filename.setText(xmlName);
                viewHolder.tv_linked_filename.setVisibility(View.VISIBLE);
                viewHolder.tv_linked_filename.setText(javaName);
                viewHolder.tv_filename.setTextColor(0xff404040);
                return;
            }
            if (i == TAB_CUSTOM_VIEW) {
                viewHolder.img_edit.setVisibility(View.GONE);
                viewHolder.tv_linked_filename.setVisibility(View.GONE);
                ProjectFileBean projectFileBean2 = jC.b(ViewSelectorActivity.this.sc_id).c().get(position);
                if (ViewSelectorActivity.this.currentXml.equals(projectFileBean2.getXmlName())) {
                    viewHolder.container.setBackgroundColor(ViewSelectorActivity.this.getResources().getColor(R.color.scolor_dark_yellow_01));
                }
                if (projectFileBean2.fileType == ProjectFileBean.PROJECT_FILE_TYPE_DRAWER) {
                    viewHolder.img_view.setImageResource(ViewSelectorActivity.this.getViewIcon(4));
                    viewHolder.tv_filename.setText(projectFileBean2.fileName.substring(1));
                    viewHolder.tv_filename.setTextColor(0xffff0000);
                } else {
                    viewHolder.img_view.setImageResource(ViewSelectorActivity.this.getViewIcon(3));
                    viewHolder.tv_filename.setText(projectFileBean2.getXmlName());
                    viewHolder.tv_filename.setTextColor(0xff000000);
                }
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.file_selector_popup_select_xml_activity_item, parent, false));
        }

        @Override
        public int getItemCount() {
            int size;
            ViewSelectorActivity.this.empty_message.setVisibility(View.GONE);
            ViewSelectorActivity viewSelectorActivity = ViewSelectorActivity.this;
            int i = viewSelectorActivity.selectedTab;
            if (i == TAB_ACTIVITY) {
                size = jC.b(viewSelectorActivity.sc_id).b().size();
            } else {
                size = i == TAB_CUSTOM_VIEW ? jC.b(viewSelectorActivity.sc_id).c().size() : 0;
            }
            if (size == 0) {
                ViewSelectorActivity.this.empty_message.setVisibility(View.VISIBLE);
            }
            return size;
        }
    }

    private void a(ProjectFileBean projectFileBean, ArrayList<ViewBean> arrayList) {
        jC.a(this.sc_id);
        Iterator<ViewBean> it = eC.a(arrayList).iterator();
        while (it.hasNext()) {
            ViewBean next = it.next();
            next.id = a(next.type, projectFileBean.getXmlName());
            jC.a(this.sc_id).a(projectFileBean.getXmlName(), next);
            if (next.type == ViewBean.VIEW_TYPE_WIDGET_BUTTON
                    && projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                jC.a(this.sc_id).a(projectFileBean.getJavaName(), 1, next.type, next.id, "onClick");
            }
        }
    }

    private void a(ProjectFileBean projectFileBean, ProjectFileBean projectFileBean2, int i) {
        ArrayList<ViewBean> d = jC.a(this.sc_id).d(projectFileBean2.getXmlName());
        for (int size = d.size() - 1; size >= 0; size--) {
            jC.a(this.sc_id).a(projectFileBean2, d.get(size));
        }
        ArrayList<ViewBean> a = a(projectFileBean.presetName, i);
        jC.a(this.sc_id);
        Iterator<ViewBean> it = eC.a(a).iterator();
        while (it.hasNext()) {
            ViewBean next = it.next();
            next.id = a(next.type, projectFileBean2.getXmlName());
            jC.a(this.sc_id).a(projectFileBean2.getXmlName(), next);
            if (next.type == ViewBean.VIEW_TYPE_WIDGET_BUTTON
                    && projectFileBean2.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                jC.a(this.sc_id).a(projectFileBean2.getJavaName(), 1, next.type, next.id, "onClick");
            }
        }
    }

    private ArrayList<ViewBean> a(String str, int i) {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        switch (i) {
            case 276:
                return rq.f(str);
            case 277:
                return rq.b(str);
            case 278:
                return rq.d(str);
            default:
                return arrayList;
        }
    }

    private String a(int i, String str) {
        String b = wq.b(i);
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        int[] iArr = this.x;
        int i2 = iArr[i] + 1;
        iArr[i] = i2;
        sb.append(i2);
        String sb2 = sb.toString();
        ArrayList<ViewBean> d = jC.a(this.sc_id).d(str);
        while (true) {
            boolean z = false;
            Iterator<ViewBean> it = d.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (sb2.equals(it.next().id)) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(b);
            int[] iArr2 = this.x;
            int i3 = iArr2[i] + 1;
            iArr2[i] = i3;
            sb3.append(i3);
            sb2 = sb3.toString();
        }
    }

    private int a(ProjectFileBean projectFileBean) {
        if (this.selectedTab == 0) {
            return 276;
        }
        return projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW ? 277 : 278;
    }
}
