package mod.Edward.KOC.ProjectTools;

import static com.besome.sketch.SketchApplication.getContext;

import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.AutoAnimation;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.DialogFragmentEnd;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.FragmentEnd;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager._addActivity;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager._loadViews;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.activitiesList;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.activityName;
import static mod.SketchwareUtil.dpToPx;
import static mod.SketchwareUtil.toastError;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.besome.sketch.editor.manage.library.ProjectComparator;
import com.besome.sketch.lib.ui.CircleImageView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import a.a.a.DB;
import a.a.a.lC;
import a.a.a.wq;
import a.a.a.yB;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class ImportAnActivity {

    public static final ArrayList<HashMap<String, Object>> projectsList = new ArrayList<>();
    public static DB preference;
    public static final ArrayList<String> activitiesListCompare = new ArrayList<>();
    public static String activityName = "main";

    public static void StartImportActivityTool(AlertDialog dialog, Context context, View inflate, String ScId) {
        preference = new DB(context, "project");
        ProjectPikerDialog(dialog, context, inflate, ScId);
    }

    public static void ProjectPikerDialog(AlertDialog dialog, Context context, View inflate, String ScId) {
        _loadViews(ScId);
        activitiesListCompare.clear();
        activitiesListCompare.addAll(activitiesList);
        final LottieAnimationView[] loading = {inflate.findViewById(R.id.loading_3balls)};
        final RecyclerView myProjects = inflate.findViewById(R.id.recyclerView);
        final LinearLayout MainBg = inflate.findViewById(R.id.main_bg);
        final TextView title = inflate.findViewById(R.id.dialog_title);
        final TextView title3 = inflate.findViewById(R.id.dialog_title3);
        final LinearLayout RenameLayout = inflate.findViewById(R.id.rename_layout);
        final LinearLayout LayoutBtn = inflate.findViewById(R.id.layout_button);
        final LinearLayout bg = inflate.findViewById(R.id.bg);
        final TextInputLayout inputLayout = inflate.findViewById(R.id.textInputLayout);
        final TextInputEditText editText = inflate.findViewById(R.id.edittext);
        final MaterialButton BtnConfirm = inflate.findViewById(R.id.dialog_btn_confirm_rename);
        final MaterialButton BtnCancel = inflate.findViewById(R.id.dialog_btn_cancel);

        AutoAnimation(inflate, 100);
        bg.setBackgroundColor(ContextCompat.getColor(context, R.color.tools_dialog_background));
        title.setText("Import An Activity");
        title3.setText("Import An Activity from :");
        MainBg.setVisibility(View.GONE);
        myProjects.setVisibility(View.GONE);
        RenameLayout.setVisibility(View.VISIBLE);
        loading[0].setVisibility(View.VISIBLE);
        new Thread(() -> {
            synchronized (projectsList) {
                projectsList.clear();
                projectsList.addAll(lC.a());
                projectsList.sort(new ProjectComparator(preference.d("sortBy")));
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                if (loading[0] != null) {
                    ((ViewGroup) loading[0].getParent()).removeView(loading[0]);
                    myProjects.setVisibility(View.VISIBLE);
                    loading[0] = null;
                }
                ProjectsPikerAdapter adapter = new ProjectsPikerAdapter(projectsList);
                myProjects.setAdapter(adapter);
                myProjects.setLayoutManager(new LinearLayoutManager(context));

                adapter.setOnItemClickListener(ItemScId -> {
                    AutoAnimation(inflate, 400);
                    _loadViews(ItemScId);
                    ActivitistAdapter adapter2 = new ActivitistAdapter(activitiesList);
                    myProjects.setAdapter(adapter2);
                    myProjects.setLayoutManager(new LinearLayoutManager(context));

                    adapter2.setOnItemClickListener(editText::setText);
                    bg.setBackgroundColor(Color.TRANSPARENT);
                    title3.setText("Select An Activity");
                    LayoutBtn.setVisibility(View.VISIBLE);
                    inputLayout.setVisibility(View.VISIBLE);
                    inputLayout.setHint("Activity Name for Import");

                    BtnConfirm.setText("Import");

                    if (activitiesList.size() > 5) {
                        myProjects.getLayoutParams().height = dpToPx(200);
                        myProjects.requestLayout();
                    }
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String charSequence2 = s.toString();

                            if (charSequence2.length() < 3) {
                                inputLayout.setError(String.format(Helper.getResString(R.string.invalid_value_min_lenth), 3));
                                return;
                            }

                            if (!Pattern.matches("[a-z]", charSequence2.substring(0, 1))) {
                                inputLayout.setError("Must start with letter");
                                return;
                            }
                            if (activityName.endsWith(DialogFragmentEnd) && !charSequence2.endsWith(DialogFragmentEnd)) {
                                inputLayout.setError("Must ends with \"" + DialogFragmentEnd + "\",\nto protect extend type");
                                return;
                            }
                            if (activityName.endsWith(FragmentEnd) && !charSequence2.endsWith(FragmentEnd)) {
                                inputLayout.setError("Must ends with \"" + FragmentEnd + "\",\nto protect extend type");
                                return;
                            }


                            if (!Pattern.matches("[a-z0-9_]+", charSequence2)) {
                                inputLayout.setError(Helper.getResString(R.string.invalid_value_rule_7));
                                return;
                            }

                            if (activitiesListCompare.contains(charSequence2)) {
                                inputLayout.setError("Duplicated name");
                                return;
                            }
                            inputLayout.setError(null);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    });

                    BtnConfirm.setOnClickListener(view -> {
                        if (inputLayout.getError() != null) {
                            return;
                        }
                        if (editText.getText().toString().isEmpty()) {
                            toastError("Select an Activity to import");
                        }
                        _addActivity(ItemScId, activityName, ScId, editText.getText().toString());
                        SketchwareUtil.showMessage(context, "Activity Imported");
                        dialog.dismiss();
                    });

                    BtnCancel.setOnClickListener(v -> dialog.dismiss());

                });
                AutoAnimation(inflate, 200);
            });
        }).start();
    }

    public interface OnItemClickListener {
        void onItemClick(String scId);
    }
    public interface ActivityItemClickListener {
        void onItemClick(String itemText);
    }


    public static class ProjectsPikerAdapter extends RecyclerView.Adapter<ProjectsPikerAdapter.ViewHolder> {

        ArrayList<HashMap<String, Object>> _data;
        private OnItemClickListener mListener;

        public ProjectsPikerAdapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mListener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_projects_item_import, parent, false);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;

            final TextView tvPublished = _view.findViewById(R.id.tv_published);
            final CircleImageView imgIcon = _view.findViewById(R.id.img_icon);
            final TextView projectName = _view.findViewById(R.id.project_name);
            final TextView appName = _view.findViewById(R.id.app_name);
            final TextView packageName = _view.findViewById(R.id.package_name);
            final TextView projectVersion = _view.findViewById(R.id.project_version);

            HashMap<String, Object> projectMap = _data.get(_position);
            String scId = yB.c(projectMap, "sc_id");

            imgIcon.setImageResource(R.drawable.default_icon);
            if (yB.c(projectMap, "sc_ver_code").isEmpty()) {
                projectMap.put("sc_ver_code", "1");
                projectMap.put("sc_ver_name", "1.0");
                lC.b(scId, projectMap);
            }

            if (yB.b(projectMap, "sketchware_ver") <= 0) {
                projectMap.put("sketchware_ver", 61);
                lC.b(scId, projectMap);
            }

            if (yB.a(projectMap, "custom_icon")) {
                Uri uri;
                String iconFolder = wq.e() + File.separator + scId;
                if (Build.VERSION.SDK_INT >= 24) {
                    String providerPath = getContext().getPackageName() + ".provider";
                    uri = FileProvider.getUriForFile(getContext(), providerPath, new File(iconFolder, "icon.png"));
                } else {
                    uri = Uri.fromFile(new File(iconFolder, "icon.png"));
                }

                imgIcon.setImageURI(uri);
            }

            appName.setText(yB.c(projectMap, "my_ws_name"));
            projectName.setText(yB.c(projectMap, "my_app_name"));
            packageName.setText(yB.c(projectMap, "my_sc_pkg_name"));
            String version = yB.c(projectMap, "sc_ver_name") + "(" + yB.c(projectMap, "sc_ver_code") + ")";
            projectVersion.setText(version);
            tvPublished.setVisibility(View.VISIBLE);
            tvPublished.setText(yB.c(projectMap, "sc_id"));

            _view.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClick(scId);
                }
            });
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }

    public static class ActivitistAdapter extends RecyclerView.Adapter<ActivitistAdapter.MyViewHolder> {

        private ActivityItemClickListener mListener;
        private final ArrayList<String> activitiesList;
        private int selectedPosition = -1;

        public ActivitistAdapter(ArrayList<String> activitiesList) {
            this.activitiesList = activitiesList;
        }

        public void setOnItemClickListener(ActivityItemClickListener listener) {
            this.mListener = listener;
        }

        @NonNull
        @Override
        public ActivitistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rename_item_layout, parent, false);
            return new ActivitistAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivitistAdapter.MyViewHolder holder, int position) {
            String itemText = activitiesList.get(position);
            holder.radioButton.setText(itemText);
            holder.radioButton.setChecked(position == selectedPosition);

            holder.radioButton.setOnClickListener(v -> {
                selectedPosition = holder.getAdapterPosition();
                activityName = itemText;
                notifyDataSetChanged();


                if (mListener != null) {
                    String newText = itemText;
                    int counter = 2;

                    while (activitiesListCompare.contains(newText)) {
                        newText = itemText + counter;
                        counter++;
                    }
                    if (activityName.endsWith(DialogFragmentEnd)) {
                        newText = itemText + (counter - 1) + DialogFragmentEnd;
                    } else if (activityName.endsWith(FragmentEnd)) {
                        newText = itemText + (counter - 1) + FragmentEnd;
                    }
                    mListener.onItemClick(newText);
                }
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
