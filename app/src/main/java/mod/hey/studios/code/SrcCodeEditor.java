package mod.hey.studios.code;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sketchware.remod.Resources;

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

import io.github.rosemoe.editor.interfaces.EditorLanguage;
import io.github.rosemoe.editor.langs.EmptyLanguage;
import io.github.rosemoe.editor.langs.desc.CDescription;
import io.github.rosemoe.editor.langs.desc.CppDescription;
import io.github.rosemoe.editor.langs.desc.JavaScriptDescription;
import io.github.rosemoe.editor.langs.java.JavaLanguage;
import io.github.rosemoe.editor.langs.universal.UniversalLanguage;
import io.github.rosemoe.editor.widget.CodeEditor;
import io.github.rosemoe.editor.widget.EditorColorScheme;
import io.github.rosemoe.editor.widget.schemes.SchemeDarcula;
import io.github.rosemoe.editor.widget.schemes.SchemeEclipse;
import io.github.rosemoe.editor.widget.schemes.SchemeGitHub;
import io.github.rosemoe.editor.widget.schemes.SchemeNotepadXX;
import io.github.rosemoe.editor.widget.schemes.SchemeVS2019;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;

///6.3.0

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

        selectTheme(ed, theme);

        ed.setTextSize(text_size);
        ed.setWordwrap(word_wrap);
        ed.setAutoCompletionEnabled(auto_c);
    }

    public static void selectTheme(CodeEditor ed, int which) {
        EditorColorScheme scheme;

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

        ed.setColorScheme(scheme);
    }

    public static String prettifyXml(String xml, int indentAmount) {
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

    public static String j(String codeString) {
        StringBuilder formattedCode = new StringBuilder(4096);
        char[] code = codeString.toCharArray();
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        int var8 = 0;
        boolean var9 = false;

        int var19;
        int index = 0;
        for (boolean var10 = false; index < code.length; index = var19) {
            int var13;
            boolean var14;
            boolean var15;
            boolean var16;
            boolean var17;
            int var18;
            label82:
            {
                char codeBit = code[index];
                if (var5) {
                    if (codeBit == '\n') {
                        formattedCode.append(codeBit);
                        a(formattedCode, var8);
                        var5 = false;
                    } else {
                        formattedCode.append(codeBit);
                    }
                } else if (var6) {
                    label79:
                    {
                        if (codeBit == '*') {
                            int var40 = index + 1;
                            char var41 = code[var40];
                            if (var41 == '/') {
                                formattedCode.append(codeBit);
                                formattedCode.append(var41);
                                index = var40;
                                var6 = false;
                                break label79;
                            }
                        }

                        formattedCode.append(codeBit);

                        // Add indents for commented lines
                        if (codeBit == '\n') {
                            a(formattedCode, var8);
                        }
                    }
                } else if (var7) {
                    formattedCode.append(codeBit);
                    var7 = false;
                } else if (codeBit == '\\') {
                    formattedCode.append(codeBit);
                    var7 = true;
                } else if (var9) {
                    if (codeBit == '\'') {
                        formattedCode.append(codeBit);
                        var9 = false;
                    } else {
                        formattedCode.append(codeBit);
                    }
                } else if (var10) {
                    if (codeBit == '\"') {
                        formattedCode.append(codeBit);
                        var10 = false;
                    } else {
                        formattedCode.append(codeBit);
                    }
                } else {
                    label88:
                    {
                        if (codeBit == '/') {
                            int var27 = index + 1;
                            char var28 = code[var27];
                            if (var28 == '/') {
                                formattedCode.append(codeBit);
                                formattedCode.append(var28);
                                var5 = true;
                                index = var27;
                                break label88;
                            }

                            if (var28 == '*') {
                                formattedCode.append(codeBit);
                                formattedCode.append(var28);
                                var6 = true;
                                index = var27;
                                break label88;
                            }
                        }

                        if (codeBit != '\n') {
                            boolean var20;
                            if (codeBit == '\'') {
                                var20 = true;
                            } else {
                                var20 = var9;
                            }

                            boolean var21;
                            if (codeBit == '\"') {
                                var21 = true;
                            } else {
                                var21 = var10;
                            }

                            int var22;
                            if (codeBit == '{') {
                                var22 = var8 + 1;
                            } else {
                                var22 = var8;
                            }

                            if (codeBit == '}') {
                                var22--;
                                if (formattedCode.charAt(-1 + formattedCode.length()) == '\t') {
                                    formattedCode.deleteCharAt(-1 + formattedCode.length());
                                }
                            }

                            formattedCode.append(codeBit);
                            var18 = var22;
                            var10 = var21;
                            var13 = index;
                            var14 = var5;
                            var15 = var6;
                            var16 = var7;
                            var17 = var20;
                            break label82;
                        }

                        formattedCode.append(codeBit);
                        a(formattedCode, var8);
                    }
                }

                var13 = index;
                var14 = var5;
                var15 = var6;
                var16 = var7;
                var17 = var9;
                var18 = var8;
            }

            var19 = var13 + 1;
            var8 = var18;
            var9 = var17;
            var7 = var16;
            var6 = var15;
            var5 = var14;
        }

        return formattedCode.toString();
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
        setContentView(Resources.layout.code_editor_hs);

        ResHelper.isInASD = false;

        initialize();
        initializeLogic();
    }

    private void initialize() {
        toolbar = findViewById(Resources.id.toolbar);
        editor = findViewById(Resources.id.editor);
    }

    private void initializeLogic() {
        toolbar.setVisibility(View.GONE);

        setTitle(getIntent().getStringExtra("title"));

        editor.setTypefaceText(Typeface.MONOSPACE);
        //6.3.0 fix1
        editor.setOverScrollEnabled(false);

        //Removed temporarily
        //editor.setEdgeEnabled(false);

        beforeContent = FileUtil.readFile(getIntent().getStringExtra("content"));

        editor.setText(beforeContent);
        editor.setEditorLanguage(new JavaLanguage());

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
                    .setPositiveButton(Resources.string.common_word_exit, (dialog, which) -> finish())
                    .setNegativeButton(Resources.string.common_word_cancel, null)
                    .create()
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences local_pref = getSharedPreferences("hsce", Activity.MODE_PRIVATE);

        menu.clear();

        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Undo")
                .setIcon(getDrawable(Resources.drawable.ic_undo_white_48dp))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Redo")
                .setIcon(getDrawable(Resources.drawable.ic_redo_white_48dp))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Save")
                .setIcon(getDrawable(Resources.drawable.save_white_48))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Paste");
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
                        ss = j(ss);
                    } catch (Exception e) {
                        err = true;
                        SketchwareUtil.toastError("Your code contains incorrectly nested parentheses");
                    }

                    if (!err) editor.setText(ss);

                } else if (getIntent().hasExtra("xml")) {
                    String format = prettifyXml(editor.getText().toString(), 4);

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
                new AlertDialog.Builder(SrcCodeEditor.this)
                        .setTitle("Switch language")
                        .setSingleChoiceItems(new String[]{"C", "C++", "Java", "JavaScript", /*"S5droid",*/ "None"}, -1,
                                (dialog, which) -> {
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
                        .setNegativeButton(Resources.string.common_word_cancel, null)
                        .show();
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
                        .setNegativeButton(Resources.string.common_word_cancel, null)
                        .show();
                break;

            case "Word wrap":
                item.setChecked(!item.isChecked());
                editor.setWordwrap(item.isChecked());

                pref.edit().putBoolean("act_ww", item.isChecked()).apply();
                break;

            case "Auto complete":
                item.setChecked(!item.isChecked());

                editor.setAutoCompletionEnabled(item.isChecked());
                pref.edit().putBoolean("act_ac", item.isChecked()).apply();
                break;

            case "Paste":
                editor.setText(paste(this));
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
