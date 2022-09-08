package mod.hey.studios.code;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sketchware.remod.R;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import a.a.a.Lx;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import io.github.rosemoe.sora.widget.schemes.SchemeEclipse;
import io.github.rosemoe.sora.widget.schemes.SchemeGitHub;
import io.github.rosemoe.sora.widget.schemes.SchemeNotepadXX;
import io.github.rosemoe.sora.widget.schemes.SchemeVS2019;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;

public class SrcCodeEditor extends AppCompatActivity {

    public static SharedPreferences pref;
    private LinearLayout toolbar;
    private CodeEditor editor;
    private String beforeContent;

    public static void loadCESettings(Context c, CodeEditor ed, String prefix) {
        pref = c.getSharedPreferences("hsce", Activity.MODE_PRIVATE);

        int text_size = pref.getInt(prefix + "_ts", 12);
        int theme = pref.getInt(prefix + "_theme", 3);
        boolean word_wrap = pref.getBoolean(prefix + "_ww", false);
        boolean auto_c = pref.getBoolean(prefix + "_ac", true);
        boolean auto_complete_symbol_pairs = pref.getBoolean(prefix + "_acsp", true);

        selectTheme(ed, theme);

        ed.setTextSize(text_size);
        ed.setWordwrap(word_wrap);
        ed.getProps().symbolPairAutoCompletion = auto_complete_symbol_pairs;
        ed.getComponent(EditorAutoCompletion.class).setEnabled(auto_c);
    }

    public static void selectTheme(CodeEditor ed, int which) {
        EditorColorScheme scheme;

        if (ed.getColorScheme() instanceof TextMateColorScheme) {
            switch (which) {
                case 1:
                    scheme = CodeEditorColorSchemes.GITHUB;
                    break;

                case 3:
                default:
                    scheme = CodeEditorColorSchemes.DRACULA;
            }
        } else {
            switch (which) {
                default:
                case 0:
                    scheme = new EditorColorScheme();
                    break;

                case 1:
                    scheme = new SchemeGitHub();
                    break;

                case 2:
                    scheme = new SchemeEclipse();
                    break;

                case 3:
                    scheme = new SchemeDarcula();
                    break;

                case 4:
                    scheme = new SchemeVS2019();
                    break;

                case 5:
                    scheme = new SchemeNotepadXX();
                    break;
            }
        }

        ed.setColorScheme(scheme);
    }

    public static String prettifyXml(String xml, int indentAmount, Intent extras) {
        try {
            // Turn xml string into a document
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))));

            // Remove whitespaces outside tags
            document.normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList)
                    xPath.evaluate(
                            "//text()[normalize-space()='']",
                            document,
                            XPathConstants.NODESET
                    );

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }

            // Setup pretty print options
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indentAmount));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            if (extras.hasExtra("disableHeader"))
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");


            // Return pretty print xml string
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds a specified amount of tabs.
     */
    public static void a(StringBuilder code, int tabAmount) {
        for (int i = 0; i < tabAmount; ++i) {
            code.append('\t');
        }
    }

    public static String paste(Activity act) {
        ClipboardManager clipboard = (ClipboardManager) act.getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboard.hasPrimaryClip()) {
            ClipDescription desc = clipboard.getPrimaryClipDescription();
            ClipData data = clipboard.getPrimaryClip();

            if (data != null && desc != null && desc.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                return String.valueOf(data.getItemAt(0).getText());
            }
        }

        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_editor_hs);

        initialize();
        initializeLogic();
    }

    private void initialize() {
        toolbar = findViewById(R.id.toolbar);
        editor = findViewById(R.id.editor);
    }

    private void initializeLogic() {
        toolbar.setVisibility(View.GONE);

        String title = getIntent().getStringExtra("title");
        setTitle(title);

        editor.setTypefaceText(Typeface.MONOSPACE);

        beforeContent = FileUtil.readFile(getIntent().getStringExtra("content"));

        editor.setText(beforeContent);

        if (title.endsWith(".java")) {
            editor.setEditorLanguage(new JavaLanguage());
        } else if (title.endsWith(".kt")) {
            editor.setEditorLanguage(CodeEditorLanguages.KOTLIN);
            editor.setColorScheme(CodeEditorColorSchemes.DRACULA);
        } else if (title.endsWith(".xml")) {
            editor.setEditorLanguage(CodeEditorLanguages.XML);
            editor.setColorScheme(CodeEditorColorSchemes.DRACULA);
        }

        loadCESettings(this, editor, "act");
    }

    public void save() {
        beforeContent = editor.getText().toString();
        FileUtil.writeFile(getIntent().getStringExtra("content"), beforeContent);
        SketchwareUtil.toast("Saved");
    }

    @Override
    public void onBackPressed() {
        if (beforeContent.equals(editor.getText().toString())) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("You have unsaved changes. Are you sure you want to exit?")
                    .setPositiveButton(R.string.common_word_exit, (dialog, which) -> finish())
                    .setNegativeButton(R.string.common_word_cancel, null)
                    .create()
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences local_pref = getSharedPreferences("hsce", Activity.MODE_PRIVATE);

        menu.clear();

        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Undo")
                .setIcon(getDrawable(R.drawable.ic_undo_white_48dp))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Redo")
                .setIcon(getDrawable(R.drawable.ic_redo_white_48dp))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Save")
                .setIcon(getDrawable(R.drawable.save_white_48))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Find & Replace");
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Word wrap")
                .setCheckable(true)
                .setChecked(local_pref.getBoolean("act_ww", false));
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Pretty print");
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Switch language");
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Switch theme");

        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Auto complete")
                .setCheckable(true)
                .setChecked(local_pref.getBoolean("act_ac", true));
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Auto complete symbol pair")
                .setCheckable(true)
                .setChecked(local_pref.getBoolean("act_acsp", true));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        switch (title) {
            case "Undo":
                editor.undo();
                break;

            case "Redo":
                editor.redo();
                break;

            case "Save":
                save();
                break;

            case "Pretty print":
                if (getIntent().hasExtra("java")) {
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
                        ss = Lx.j(ss, true);
                    } catch (Exception e) {
                        err = true;
                        SketchwareUtil.toastError("Your code contains incorrectly nested parentheses");
                    }

                    if (!err) editor.setText(ss);

                } else if (getIntent().hasExtra("xml")) {
                    String format = prettifyXml(editor.getText().toString(), 4, getIntent());

                    if (format != null) {
                        editor.setText(format);
                    } else {
                        SketchwareUtil.toastError("Failed to format XML file", Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toast("Only Java and XML files can be formatted");
                }
                break;

            case "Switch language":
                SketchwareUtil.toast("Currently not supported, sorry!");
                break;

            case "Find & Replace":
                editor.getSearcher().stopSearch();
                editor.beginSearchMode();
                break;

            case "Switch theme":
                String[] themes = new String[]{"Default", "GitHub", "Eclipse", "Dracula", "VS2019", "NotepadXX"};
                new AlertDialog.Builder(SrcCodeEditor.this)
                        .setTitle("Switch theme")
                        .setSingleChoiceItems(themes, -1,
                                (dialog, which) -> {
                                    selectTheme(editor, which);
                                    pref.edit().putInt("act_theme", which).apply();
                                    dialog.dismiss();
                                }
                        )
                        .setNegativeButton(R.string.common_word_cancel, null)
                        .show();
                break;

            case "Word wrap":
                item.setChecked(!item.isChecked());
                editor.setWordwrap(item.isChecked());

                pref.edit().putBoolean("act_ww", item.isChecked()).apply();
                break;

            case "Auto complete symbol pair":
                item.setChecked(!item.isChecked());
                editor.getProps().symbolPairAutoCompletion = item.isChecked();

                pref.edit().putBoolean("act_acsp", item.isChecked()).apply();
                break;

            case "Auto complete":
                item.setChecked(!item.isChecked());

                editor.getComponent(EditorAutoCompletion.class).setEnabled(item.isChecked());
                pref.edit().putBoolean("act_ac", item.isChecked()).apply();
                break;

            default:
                return false;
        }

        return true;
    }

    @Override
    public void onStop() {
        super.onStop();

        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        pref.edit().putInt("act_ts", (int) (editor.getTextSizePx() / scaledDensity)).apply();
    }
}
