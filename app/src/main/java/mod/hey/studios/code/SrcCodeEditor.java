package mod.hey.studios.code;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import mod.hey.studios.util.Helper;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;
import pro.sketchware.R;
import pro.sketchware.activities.preview.LayoutPreviewActivity;
import pro.sketchware.utility.EditorUtils;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.ThemeUtils;

public class SrcCodeEditor extends BaseAppCompatActivity {
    public static final List<Pair<String, Class<? extends EditorColorScheme>>> KNOWN_COLOR_SCHEMES = List.of(
            new Pair<>("Default", EditorColorScheme.class),
            new Pair<>("GitHub", SchemeGitHub.class),
            new Pair<>("Eclipse", SchemeEclipse.class),
            new Pair<>("Darcula", SchemeDarcula.class),
            new Pair<>("VS2019", SchemeVS2019.class),
            new Pair<>("NotepadXX", SchemeNotepadXX.class)
    );
    public static SharedPreferences pref;
    public static int languageId;
    private String beforeContent;
    private ImageView save;
    private ImageView more;
    private TextView file_title;
    private ImageView menu_view_undo;
    private ImageView menu_view_redo;
    private CodeEditor editor;
    private MaterialToolbar toolbar;

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
        if (!(ed.getColorScheme() instanceof TextMateColorScheme)) {
            EditorColorScheme scheme = switch (which) {
                case 1 -> new SchemeGitHub();
                case 2 -> new SchemeEclipse();
                case 3 -> new SchemeDarcula();
                case 4 -> new SchemeVS2019();
                case 5 -> new SchemeNotepadXX();
                default -> new EditorColorScheme();
            };

            ed.setColorScheme(scheme);
        }
    }

    public static void selectLanguage(CodeEditor ed, int which) {
        switch (which) {
            default:
            case 0:
                ed.setEditorLanguage(new JavaLanguage());
                languageId = 0;
                break;

            case 1:
                ed.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_KOTLIN));
                languageId = 1;
                break;

            case 2:
                ed.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
                languageId = 2;
                break;
        }

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

    public static void showSwitchThemeDialog(Activity activity, CodeEditor codeEditor, DialogInterface.OnClickListener listener) {
        EditorColorScheme currentScheme = codeEditor.getColorScheme();
        var knownColorSchemesProperlyOrdered = new ArrayList<>(KNOWN_COLOR_SCHEMES);
        Collections.reverse(knownColorSchemesProperlyOrdered);
        int selectedThemeIndex = knownColorSchemesProperlyOrdered.stream()
                .filter(pair -> pair.second.equals(currentScheme.getClass()))
                .map(KNOWN_COLOR_SCHEMES::indexOf)
                .findFirst()
                .orElse(-1);
        String[] themeItems = KNOWN_COLOR_SCHEMES.stream()
                .map(pair -> pair.first)
                .toArray(String[]::new);
        new AlertDialog.Builder(activity)
                .setTitle("Select Theme")
                .setSingleChoiceItems(themeItems, selectedThemeIndex, listener)
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    public static void showSwitchLanguageDialog(Activity activity, CodeEditor codeEditor, DialogInterface.OnClickListener listener) {
        CharSequence[] languagesList = {
                "Java",
                "Kotlin",
                "XML"
        };

        new AlertDialog.Builder(activity)
                .setTitle("Select Language")
                .setSingleChoiceItems(languagesList, languageId, listener)
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    public static boolean isDarkModeEnabled(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_editor_hs);

        editor = findViewById(R.id.editor);
        toolbar = findViewById(R.id.toolbar);

        initialize();
    }

    private void initialize() {
        String title = getIntent().getStringExtra("title");

        editor.setTypefaceText(EditorUtils.getTypeface(this));
        editor.setTextSize(16);

        beforeContent = FileUtil.readFile(getIntent().getStringExtra("content"));

        editor.setText(beforeContent);

        if (title.endsWith(".java")) {
            editor.setEditorLanguage(new JavaLanguage());
            languageId = 0;
        } else if (title.endsWith(".kt")) {
            editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_KOTLIN));
            editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
            languageId = 1;
        } else if (title.endsWith(".xml")) {
            editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
            if (ThemeUtils.isDarkThemeEnabled(getApplicationContext())) {
                editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
            } else {
                editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
            }
            languageId = 2;
        }

        loadCESettings(this, editor, "act");
        loadToolbar();
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
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
            dialog.setIcon(R.drawable.ic_warning_96dp);
            dialog.setTitle(Helper.getResString(R.string.common_word_warning));
            dialog.setMessage(Helper.getResString(R.string.src_code_editor_unsaved_changes_dialog_warning_message));

            dialog.setPositiveButton(Helper.getResString(R.string.common_word_exit), (v, which) -> {
                v.dismiss();
                finish();
            });
            dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
            dialog.show();
        }
    }

    private void loadToolbar() {
        {
            String title = getIntent().getStringExtra("title");
            toolbar.setTitle(title);
            SharedPreferences local_pref = getSharedPreferences("hsce", Activity.MODE_PRIVATE);
            Menu toolbarMenu = toolbar.getMenu();
            toolbarMenu.clear();
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Undo").setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_undo)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Redo").setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_redo)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Save").setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_save)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            if (isFileInLayoutFolder() && getIntent().hasExtra("sc_id")) {
                toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Layout Preview");
            }
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Find & Replace");
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Word wrap").setCheckable(true).setChecked(local_pref.getBoolean("act_ww", false));
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Pretty print");
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Select language");
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Select theme");
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Auto complete").setCheckable(true).setChecked(local_pref.getBoolean("act_ac", true));
            toolbarMenu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Auto complete symbol pair").setCheckable(true).setChecked(local_pref.getBoolean("act_acsp", true));

            toolbar.setOnMenuItemClickListener(item -> {
                String title1 = item.getTitle().toString();
                switch (title1) {
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

                    case "Select language":
                        showSwitchLanguageDialog(this, editor, (dialog, which) -> {
                            selectLanguage(editor, which);
                            dialog.dismiss();
                        });
                        break;

                    case "Find & Replace":
                        editor.getSearcher().stopSearch();
                        editor.beginSearchMode();
                        break;

                    case "Select theme":
                        showSwitchThemeDialog(this, editor, (dialog, which) -> {
                            selectTheme(editor, which);
                            pref.edit().putInt("act_theme", which).apply();
                            dialog.dismiss();
                        });
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

                    case "Layout Preview":
                        toLayoutPreview();
                        break;

                    default:
                        return false;
                }
                return true;
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        pref.edit().putInt("act_ts", (int) (editor.getTextSizePx() / scaledDensity)).apply();
    }

    private boolean isFileInLayoutFolder() {
        String content = getIntent().getStringExtra("content");
        if (content != null) {
            File file = new File(content);
            if (content.contains("/resource/layout/")) {
                String layoutFolder = file.getParent();
                return layoutFolder != null && layoutFolder.endsWith("/resource/layout");
            }
        }
        return false;
    }

    private void toLayoutPreview() {
        Intent intent = new Intent(getApplicationContext(), LayoutPreviewActivity.class);
        intent.putExtras(getIntent());
        intent.putExtra("xml", editor.getText().toString());
        startActivity(intent);
    }
}