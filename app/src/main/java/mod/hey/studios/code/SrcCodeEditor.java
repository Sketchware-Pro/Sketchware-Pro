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

import a.a.a.bB;
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
import mod.agus.jcoderz.lib.FileUtil;

///6.3.0

public class SrcCodeEditor extends Activity {
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
        EditorColorScheme scheme = new EditorColorScheme();

        switch (which) {
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

    // xml formatter
    public static String prettifyXml(String xml, int indent) {
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
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Return pretty print xml string
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();

        } catch (Exception e) {
            return null;
        }
    }

    // java formatter
    public static void a(StringBuilder var0, int var1) {
        for (int var2 = 0; var2 < var1; ++var2) {
            var0.append('\t');
        }
    }

    public static String j(String var0) {
        StringBuilder var1 = new StringBuilder(4096);
        char[] var2 = var0.toCharArray();
        int var3 = var2.length;
        int var4 = 0;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        int var8 = 0;
        boolean var9 = false;

        int var19;
        for (boolean var10 = false; var4 < var3; var4 = var19) {
            int var13;
            boolean var14;
            boolean var15;
            boolean var16;
            boolean var17;
            int var18;
            label82:
            {
                char var11 = var2[var4];
                if (var5) {
                    if (var11 == 10) {
                        var1.append(var11);
                        a(var1, var8);
                        var5 = false;
                    } else {
                        var1.append(var11);
                    }
                } else if (var6) {
                    label79:
                    {
                        if (var11 == 42) {
                            int var40 = var4 + 1;
                            char var41 = var2[var40];
                            if (var41 == 47) {
                                var1.append(var11);
                                var1.append(var41);
                                var4 = var40;
                                var6 = false;
                                break label79;
                            }
                        }

                        var1.append(var11);
                        //add indents for commented lines
                        if(var11==10) a(var1,var8);
                    }
                } else if (var7) {
                    var1.append(var11);
                    var7 = false;
                } else if (var11 == 92) {
                    var1.append(var11);
                    var7 = true;
                } else if (var9) {
                    if (var11 == 39) {
                        var1.append(var11);
                        var9 = false;
                    } else {
                        var1.append(var11);
                    }
                } else if (var10) {
                    if (var11 == 34) {
                        var1.append(var11);
                        var10 = false;
                    } else {
                        var1.append(var11);
                    }
                } else {
                    label88:
                    {
                        if (var11 == 47) {
                            int var27 = var4 + 1;
                            char var28 = var2[var27];
                            if (var28 == 47) {
                                var1.append(var11);
                                var1.append(var28);
                                var5 = true;
                                var4 = var27;
                                break label88;
                            }

                            if (var28 == 42) {
                                var1.append(var11);
                                var1.append(var28);
                                var6 = true;
                                var4 = var27;
                                break label88;
                            }
                        }

                        if (var11 != 10) {
                            boolean var20;
                            if (var11 == 39) {
                                var20 = true;
                            } else {
                                var20 = var9;
                            }

                            boolean var21;
                            if (var11 == 34) {
                                var21 = true;
                            } else {
                                var21 = var10;
                            }

                            int var22;
                            if (var11 == 123) {
                                var22 = var8 + 1;
                            } else {
                                var22 = var8;
                            }

                            if (var11 == 125) {
                                --var22;
                                if (var1.charAt(-1 + var1.length()) == 9) {
                                    var1.deleteCharAt(-1 + var1.length());
                                }
                            }

                            var1.append(var11);
                            var18 = var22;
                            var10 = var21;
                            var13 = var4;
                            var14 = var5;
                            var15 = var6;
                            var16 = var7;
                            var17 = var20;
                            break label82;
                        }

                        var1.append(var11);
                        a(var1, var8);
                    }
                }

                var13 = var4;
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

        return var1.toString();
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
    protected void onCreate(Bundle savedInstanceState) {
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
        getActionBar().setSubtitle("UTF-8");

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
        bB.a(getApplicationContext(), "Saved", 0).show();
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

    public void populateMenu(Menu menu) {

        SharedPreferences local_pref = getSharedPreferences("hsce", Activity.MODE_PRIVATE);

        //6.3.0 fix1
        menu.clear();

        menu.add(0, 9, 0, "Paste");
        menu.add(0, 0, 0, "Undo")
                .setIcon(getDrawable(Resources.drawable.ic_undo_white_48dp))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 1, 0, "Redo")
                .setIcon(getDrawable(Resources.drawable.ic_redo_white_48dp))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 2, 0, "Save")
                .setIcon(getDrawable(Resources.drawable.save_white_48))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        
        menu.add(0, 3, 0, "Find & Replace");
        menu.add(0, 4, 0, "Word wrap")
                .setCheckable(true)
                .setChecked(local_pref.getBoolean("act_ww", false));
        menu.add(0, 5, 0, "Pretty print");
        menu.add(0, 6, 0, "Switch language");
        menu.add(0, 7, 0, "Switch theme");

        //6.3.0 fix1
        menu.add(0, 8, 0, "Auto complete")
                .setCheckable(true)
                .setChecked(local_pref.getBoolean("act_ac", true));
    }

    public void onMenuItemClick(MenuItem item) {

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
                //editor.formatCodeAsync();

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
                        bB.a(SrcCodeEditor.this, "Error: Your code contains incorrectly nested parentheses", 0).show();
                    }
                    
                    if (!err) editor.setText(ss);

                } else if (getIntent().hasExtra("xml")) {
                    String format = prettifyXml(editor.getText().toString(), 4);

                    if (format != null) {
                        editor.setText(format);
                    } else {
                        bB.a(getApplicationContext(), "Couldn't format xml file (maybe check if it follows proper xml syntax?)", 1).show();
                    }
                } else {
                    bB.a(getApplicationContext(), "Only Java and XML files can be formatted.", 0).show();
                }

                break;

            case "Switch language":
                new AlertDialog.Builder(SrcCodeEditor.this)
                        .setTitle("Switch language")
                        .setSingleChoiceItems(new String[] {"C", "C++", "Java", "JavaScript", /*"S5droid",*/ "None"}, -1,
                                (dialog, which) -> {
                                    EditorLanguage editorLanguage = null;

                                    switch (which) {
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
                String[] themes = new String[] {"Default", "GitHub", "Eclipse", "Dracula", "VS2019", "NotepadXX"};
                new AlertDialog.Builder(SrcCodeEditor.this)
                        .setTitle("Switch theme")
                        .setSingleChoiceItems(themes, -1,
                                (dialog, which) -> {
                                    selectTheme(editor, which);
                                    pref.edit().putInt("act_theme", which).apply();
                                    dialog.dismiss();
                                }
                        )
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            case "Word wrap":
                item.setChecked(!item.isChecked());
                editor.setWordwrap(item.isChecked());

                pref.edit().putBoolean("act_ww", item.isChecked()).apply();
                break;

            case "Auto complete":
                item.setChecked(!item.isChecked());

                //new
                editor.setAutoCompletionEnabled(item.isChecked());
                pref.edit().putBoolean("act_ac", item.isChecked()).apply();
                break;

            case "Paste":
                editor.setText(paste(this));
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        populateMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        pref.edit().putInt("act_ts", (int) (editor.getTextSizePx() / scaledDensity)).apply();
    }
}
