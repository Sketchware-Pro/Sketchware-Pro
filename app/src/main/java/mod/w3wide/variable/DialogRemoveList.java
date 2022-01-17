package mod.w3wide.variable;

import a.a.a.*;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.besome.sketch.editor.LogicEditorActivity;

public class DialogRemoveList implements OnClickListener {

    public final ViewGroup viewGroup;
    public final aB dialog;
    public final LogicEditorActivity logicEditor;

    public DialogRemoveList(LogicEditorActivity logicEditorActivity, ViewGroup viewGroup, aB aBVar) {
        logicEditor = logicEditorActivity;
        this.viewGroup = viewGroup;
        dialog = aBVar;
    }

    public void onClick(View view) {
        int childCount = viewGroup.getChildCount();
        String javaName = logicEditor.M.getJavaName();
        String eventName = logicEditor.C + "_" + logicEditor.D;

        for (int i = 0; i < childCount; i++) {
            if (viewGroup.getChildAt(i) instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                String list = radioButton.getText().toString();

                if (radioButton.isChecked()) {
                    if (!logicEditor.o.b(list)) {
                        if (!jC.a(logicEditor.B).b(javaName, list, eventName)) {
                            logicEditor.l(list);
                            dialog.dismiss();
                        }
                    } else {
                        Toast.makeText(logicEditor.getApplicationContext(), xB.b().a(logicEditor.getApplicationContext(), 2131625492), 0).show();
                    }
                    return;
                }
            }
        }
        dialog.dismiss();
    }
}
