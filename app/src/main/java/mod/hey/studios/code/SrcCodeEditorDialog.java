package mod.hey.studios.code;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.sketchware.remod.Resources;

import a.a.a.bB;
import io.github.rosemoe.editor.interfaces.EditorLanguage;
import io.github.rosemoe.editor.langs.EmptyLanguage;
import io.github.rosemoe.editor.langs.desc.CDescription;
import io.github.rosemoe.editor.langs.desc.CppDescription;
import io.github.rosemoe.editor.langs.desc.JavaScriptDescription;
import io.github.rosemoe.editor.langs.java.JavaLanguage;
import io.github.rosemoe.editor.langs.universal.UniversalLanguage;
import io.github.rosemoe.editor.widget.CodeEditor;
import mod.hey.studios.util.Helper;

//6.3.0

public class SrcCodeEditorDialog {

    Context c;
    CodeEditor editor;

    SharedPreferences pref;

    public SrcCodeEditorDialog(Context c) {
        this.c = c;
    }

    public void show() {
        View ed = LayoutInflater.from(c)
                .inflate(Resources.layout.code_editor_hs, null);

        editor = ed.findViewById(Resources.id.editor);
        editor.setTypefaceText(Typeface.MONOSPACE);
        editor.setOverScrollEnabled(true);

        // Temporarily removed
        //editor.setEdgeEnabled(false);

        editor.setEditorLanguage(new JavaLanguage());
        editor.setText("");

        SrcCodeEditor.loadCESettings(c, editor, "dlg");

        pref = SrcCodeEditor.pref;
        final ImageView ig_undo, ig_redo, ig_save, ig_more;

        ig_undo = ed.findViewById(Resources.id.menu_view_undo);
        ig_redo = ed.findViewById(Resources.id.menu_view_redo);
        ig_save = ed.findViewById(Resources.id.save);
        ig_more = ed.findViewById(Resources.id.more);

        Helper.applyRipple(c, ig_undo);
        Helper.applyRipple(c, ig_redo);
        Helper.applyRipple(c, ig_save);
        Helper.applyRipple(c, ig_more);

        ig_undo.setOnClickListener(v -> editor.undo());
        ig_redo.setOnClickListener(v -> editor.redo());
        ig_save.setOnClickListener(v -> bB.a(c, "Saved i guess.", 0));
        ig_more.setOnClickListener(v -> {
            PopupMenu pm = new PopupMenu(c, v);
            //pm.inflate(R.menu.menu_main);
            populateMenu(pm.getMenu());

            pm.setOnMenuItemClickListener(item -> {
                _onMenuItemClick(item);
                return false;
            });

            pm.show();
        });

        AlertDialog dlg = new AlertDialog.Builder(c)
                .setView(ed)
                .create();

        dlg.setOnDismissListener(dialog -> {
            float scaledDensity = c.getResources().getDisplayMetrics().scaledDensity;
            pref.edit().putInt("dlg_ts", (int) (editor.getTextSizePx() / scaledDensity)).apply();
        });

        dlg.show();
    }

    public void _onMenuItemClick(MenuItem item) {

        String title = item.getTitle().toString();

        switch (title) {
            case "Pretty print":
                //editor.formatCodeAsync();

                StringBuilder b = new StringBuilder();

                for (String line : editor.getText().toString().split("\n")) {
                    String trims = (line + "X").trim();
                    trims = trims.substring(0, trims.length() - 1);

                    b.append(trims);
                    b.append("\n");
                }

                boolean err = false;
                String ss = b.toString();

                try {
                    ss = SrcCodeEditor.j(ss);
                } catch (Exception e) {
                    err = true;
                    bB.a(c, "Error: Your code contains incorrectly nested parentheses", 0).show();
                }

                if (!err) editor.setText(ss);
                break;

            case "Switch language":
                new AlertDialog.Builder(c)
                        .setTitle("Switch language")
                        .setSingleChoiceItems(new String[] {"C", "C++", "Java", "JavaScript", /*"S5droid",*/ "None"}, -1, (dialog, which) -> {
                                    EditorLanguage editorLanguage;

                                    switch (which) {
                                        default:
                                        case 0:
                                            editorLanguage = new UniversalLanguage(new CDescription());
                                            break;

                                        case 1:
                                            editorLanguage = new UniversalLanguage(new CppDescription());
                                            break;

                                        case 2:
                                            editorLanguage = new JavaLanguage();
                                            break;

                                        case 3:
                                            editorLanguage = new UniversalLanguage(new JavaScriptDescription());
                                            break;

                                        case 4:
                                            editorLanguage = new EmptyLanguage();
                                            break;
                                    }

                                    editor.setEditorLanguage(editorLanguage);
                                    dialog.dismiss();
                                }
                        )
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            case "Find & Replace":
                editor.getSearcher().stopSearch();
                editor.beginSearchMode();
                break;

            case "Switch theme":
                String[] themes = new String[]{"Default", "GitHub", "Eclipse", "Darcula", "VS2019", "NotepadXX"};
                new AlertDialog.Builder(c)
                        .setTitle("Switch theme")
                        .setSingleChoiceItems(themes, -1, (dialog, which) -> {
                                    SrcCodeEditor.selectTheme(editor, which);

                                    pref.edit().putInt("dlg_theme", which).apply();
                                    dialog.dismiss();
                                }
                        )
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            case "Word wrap":
                item.setChecked(!item.isChecked());
                editor.setWordwrap(item.isChecked());

                pref.edit().putBoolean("dlg_ww", item.isChecked()).apply();
                break;
        }
    }


    public void populateMenu(Menu menu) {
        menu.add(0, 3, 0, "Find & Replace");
        menu.add(0, 4, 0, "Word wrap").setCheckable(true).setChecked(pref.getBoolean("dlg_ww", false));
        menu.add(0, 5, 0, "Pretty print");
        menu.add(0, 6, 0, "Switch language");
        menu.add(0, 7, 0, "Switch theme");
    }
}
