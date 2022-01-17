package mod.w3wide.variable;

import static com.besome.sketch.SketchApplication.getContext;

import a.a.a.eC;
import a.a.a.jC;
import a.a.a.xB;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.besome.sketch.editor.LogicEditorActivity;

import mod.w3wide.dialog.SketchDialog;

public class DialogRemoveVariable implements OnClickListener {
    public final ViewGroup viewGroup;
    public final SketchDialog dialog;
    public final LogicEditorActivity logicEditor;

    public DialogRemoveVariable(LogicEditorActivity logicEditorActivity, ViewGroup viewGroup, SketchDialog dialog) {
        logicEditor = logicEditorActivity;
        this.viewGroup = viewGroup;
        this.dialog = dialog;
    }

    public void onClick(View view) {
        int childCount = viewGroup.getChildCount();
        String eventName = logicEditor.C + "_" + logicEditor.D;
        String javaName = logicEditor.M.getJavaName();

        for (int i = 0; i < childCount; i++) {
            if (viewGroup.getChildAt(i) instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                String variable = radioButton.getText().toString();
                if (radioButton.isChecked()) {
                    if (!logicEditor.o.c(variable)) {
                        if (!jC.a(logicEditor.B).c(javaName, variable, eventName)) {
                            logicEditor.m(variable);
                            dialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getContext(), xB.b().a(getContext(), 2131625493), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        }
        dialog.dismiss();
    }
}
