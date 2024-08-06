package mod.Edward.KOC.ProjectTools;

import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.AutoAnimation;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.DialogFragmentEnd;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.FragmentEnd;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager._addActivity;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager._deleteActivity;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.activitiesList;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.isProjectNotSaved;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.selectedPosition;
import static mod.Edward.KOC.ProjectTools.ProjectToolsManager.activityName;

import static mod.SketchwareUtil.dpToPx;
import static mod.SketchwareUtil.toastError;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.regex.Pattern;

import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class RenameAnActivity {

    public static void StartRenameActivityTool(AlertDialog dialog, Context context, View inflate, String ScId) {
        final LinearLayout MainBg = inflate.findViewById(R.id.main_bg);
        final LinearLayout RenameLayout = inflate.findViewById(R.id.rename_layout);
        final LinearLayout LayoutBtn = inflate.findViewById(R.id.layout_button);
        final TextView title = inflate.findViewById(R.id.dialog_title);
        final MaterialButton BtnConfirmRename = inflate.findViewById(R.id.dialog_btn_confirm_rename);
        final MaterialButton BtnCancel = inflate.findViewById(R.id.dialog_btn_cancel);
        final RecyclerView recyclerView = inflate.findViewById(R.id.recyclerView);
        final TextInputLayout inputLayout = inflate.findViewById(R.id.textInputLayout);
        final TextInputEditText editText = inflate.findViewById(R.id.edittext);

        if (activitiesList.size() < 2) {
            return;
        }

        ProjectToolsManager.MyAdapter adapter = new ProjectToolsManager.MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        selectedPosition = 1;
        activityName = activitiesList.get(1);

        title.setText("Rename An Activity");
        MainBg.setVisibility(View.GONE);
        AutoAnimation(inflate, 400);
        RenameLayout.setVisibility(View.VISIBLE);
        LayoutBtn.setVisibility(View.VISIBLE);
        inputLayout.setVisibility(View.VISIBLE);
        if (activitiesList.size() > 5) {
            recyclerView.getLayoutParams().height = dpToPx(200);
            recyclerView.requestLayout();
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

                if (activitiesList.contains(charSequence2)) {
                    inputLayout.setError("Duplicated name");
                    return;
                }
                inputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        BtnConfirmRename.setOnClickListener(v -> {
            if (inputLayout.getError() != null) return;
            if (isProjectNotSaved(ScId)) {
                toastError("Please save the project first");
                return;
            }
            if (selectedPosition == 0) {
                toastError("you can't rename MainActivity");
                return;
            }
            if (editText.getText().toString().isEmpty()) {
                inputLayout.setError(String.format(Helper.getResString(R.string.invalid_value_min_lenth), 3));
                return;
            }
            _addActivity(ScId, activityName, ScId, editText.getText().toString());
            _deleteActivity(ScId, activityName);
            SketchwareUtil.showMessage(context, "Activity Renamed");
            dialog.dismiss();
        });

        BtnCancel.setOnClickListener(v -> dialog.dismiss());

    }
}
