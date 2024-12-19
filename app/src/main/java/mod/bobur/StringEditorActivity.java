package mod.bobur;

import static com.besome.sketch.design.DesignActivity.sc_id;
import static com.besome.sketch.editor.LogicEditorActivity.getAllJavaFileNames;
import static com.besome.sketch.editor.LogicEditorActivity.getAllXmlFileNames;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import a.a.a.aB;
import a.a.a.eC;
import a.a.a.jC;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ViewBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import a.a.a.eC;
import a.a.a.jC;
import mod.bobur.helpers.Translator;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogTranslateBinding;
import pro.sketchware.databinding.ProgressMsgBoxBinding;
import pro.sketchware.databinding.StringEditorBinding;
import pro.sketchware.databinding.StringEditorItemBinding;
import pro.sketchware.databinding.ViewStringEditorAddBinding;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.code.SrcCodeEditorLegacy;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import pro.sketchware.R;
import pro.sketchware.databinding.StringEditorBinding;
import pro.sketchware.databinding.StringEditorItemBinding;
import pro.sketchware.databinding.ViewStringEditorAddBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.XmlUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class StringEditorActivity extends AppCompatActivity {

    private final ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
    private MaterialAlertDialogBuilder dialog;
    private StringEditorBinding binding;
    private RecyclerViewAdapter adapter;
    private boolean isComingFromSrcCodeEditor = true;
    private boolean isRunning;
    private int currentIndex;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StringEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
        path = getIntent().getStringExtra("content");
    }

    private void initialize() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(_v -> onBackPressed());
        dialog = new MaterialAlertDialogBuilder(this);
        binding.addStringButton.setOnClickListener(view -> addStringDialog());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy < 0) {
                    if (!binding.addStringButton.isExtended()) {
                        binding.addStringButton.extend();
                    }
                }
                else if (dy > 0) {
                    if (binding.addStringButton.isExtended()) {
                        binding.addStringButton.shrink();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isComingFromSrcCodeEditor) {
            convertXmlToListMap(FileUtil.readFile(path), listmap);
            adapter = new RecyclerViewAdapter(listmap);
            binding.recyclerView.setAdapter(adapter);
        }
        isComingFromSrcCodeEditor = false;
    }

    @Override
    public void onBackPressed() {
        ArrayList<HashMap<String, Object>> cache = new ArrayList<>();
        convertXmlToListMap(FileUtil.readFile(path), cache);
        String cacheString = new Gson().toJson(cache);
        String cacheListmap = new Gson().toJson(listmap);
        if (cacheListmap.equals(cacheString) || listmap.isEmpty()) {
            setResult(RESULT_OK);
            finish();
        } else {
            dialog.setTitle("Warning")
                    .setMessage("You have unsaved changes. Are you sure you want to exit?")
                    .setPositiveButton("Exit", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }
        if (listmap.isEmpty() && (! FileUtil.readFile(path).contains("</resources>"))) {
            XmlUtil.saveXml(path,convertListMapToXml(listmap));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.string_editor_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText);
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
            });
        }

        menu.findItem(R.id.action_get_default).setVisible(!checkDefaultString(getIntent().getStringExtra("content")));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            XmlUtil.saveXml(getIntent().getStringExtra("content"), convertListMapToXml(listmap));
        } else if (id == R.id.action_get_default) {
            convertXmlToListMap(FileUtil.readFile(getDefaultStringPath(Objects.requireNonNull(getIntent().getStringExtra("content")))), listmap);
            adapter.notifyDataSetChanged();
        } else if (id == R.id.action_open_editor) {
            isComingFromSrcCodeEditor = true;
            XmlUtil.saveXml(getIntent().getStringExtra("content"), convertListMapToXml(listmap));
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ConfigActivity.isLegacyCeEnabled() ? SrcCodeEditorLegacy.class : SrcCodeEditor.class);
            intent.putExtra("title", getIntent().getStringExtra("title"));
            intent.putExtra("content", path);
            intent.putExtra("xml", getIntent().getStringExtra("xml"));
            startActivity(intent);
        } else if (id == 4) {
            autoTranslateDialog();
        } else if (id == 5) {
            createLanguagesDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void convertXmlToListMap(final String xmlString, final ArrayList<HashMap<String, Object>> listmap) {
        try {
            listmap.clear();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(new InputSource(input));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("string");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    HashMap<String, Object> map = getStringHashMap((Element) node);
                    listmap.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createLanguagesDialog() {
        String[] cache = getLanguages();
        ArrayList<String> languagesList = new ArrayList<>(Arrays.asList(cache));
        languagesList.removeAll(getValuesFolders(path));
        String[] languages = languagesList.toArray(new String[0]);
        boolean[] checkedItems = new boolean[languagesList.size()];

        MaterialAlertDialogBuilder dialog2 = new MaterialAlertDialogBuilder(this);
        dialog2.setTitle("Create languages")
                .setMultiChoiceItems(languages, checkedItems, (dialog2_c, which, isChecked) -> {
                    checkedItems[which] = isChecked;
                })
                .setPositiveButton("Create", (dialog1, which) -> {
                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {
                            String defaultString = FileUtil.readFile(getDefaultStringPath(path));
                            FileUtil.writeFile(getResFolder(path) + "/" + languagesList.get(i) + "/strings.xml", defaultString);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public ArrayList<String> getValuesFolders(final String _path) {
        ArrayList<String> cache = new ArrayList<>();
        FileUtil.listDir(_path.substring(0, _path.substring(0, _path.lastIndexOf("/")).lastIndexOf("/")), cache);
        ArrayList<String> real = new ArrayList<>();
        for (int i = 0; i < cache.size(); i++) {
            if (Objects.requireNonNull(Uri.parse(cache.get(i)).getLastPathSegment()).startsWith("values")) {
                real.add(Uri.parse(cache.get(i)).getLastPathSegment());
            }
        }
        return real;
    }

    public String getResFolder(final String _path) {
        return (_path.substring(0, _path.substring(0, _path.lastIndexOf("/")).lastIndexOf("/")));
    private static HashMap<String, Object> getStringHashMap(Element node) {
        HashMap<String, Object> map = new HashMap<>();
        String key = node.getAttribute("name");
        String value = node.getTextContent();
        map.put("key", key);
        map.put("text", value);
        return map;
    }

    public static boolean isXmlStringsContains(ArrayList<HashMap<String, Object>> listMap, String value) {
        for (Map<String, Object> map : listMap) {
            if (map.containsKey("key") && value.equals(map.get("key"))) {
                return true;
            }
        }
        return false;
    }

    public static String convertListMapToXml(final ArrayList<HashMap<String, Object>> listmap) {
        StringBuilder xmlString = new StringBuilder();
        xmlString.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");
        for (HashMap<String, Object> map : listmap) {
            String key = (String) map.get("key");
            Object textObj = map.get("text");
            String text = textObj instanceof String ? (String) textObj : textObj.toString();
            String escapedText = escapeXml(text);
            xmlString.append("    <string name=\"").append(key).append("\"");
            if (map.containsKey("translatable")) {
                String translatable = (String) map.get("translatable");
                xmlString.append(" translatable=\"").append(translatable).append("\"");
            }
            xmlString.append(">").append(escapedText).append("</string>\n");
        }
        xmlString.append("</resources>");
        return xmlString.toString();
    }

    public static String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "\\'")
                .replace("\n", "&#10;")
                .replace("\r", "&#13;");
    }

    public static String[] getLanguages() {
        Locale[] locales = Locale.getAvailableLocales();

        return Arrays.stream(locales)
                .map(locale -> {
                    String language = locale.getLanguage();
                    String country = locale.getCountry();
                    return country.isEmpty() ? "values-" + language : "values-" + language;
                })
                .distinct()
                .toArray(String[]::new);
    }

    private void autoTranslateDialog() {
        DialogTranslateBinding dialogBinding = DialogTranslateBinding.inflate(getLayoutInflater());
        dialogBinding.edToLanguage.setText(getLanguageCode(Objects.requireNonNull(path)));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        adapter.add("Google Translate");
        // adapter.add("Yandex Translate");
        dialogBinding.edSource.setAdapter(adapter);
        if (dialogBinding.edFromLanguage.getText().toString().isEmpty() || dialogBinding.edToLanguage.getText().toString().isEmpty()) {
            SketchwareUtil.toast("Please fill in all fields", Toast.LENGTH_SHORT);
            return;
        }

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Auto Translate")
                .setView(dialogBinding.getRoot())
                .setPositiveButton("Translate", (dialog1, which) -> autoTranslate(dialogBinding.edSource.getText().toString(), dialogBinding.edFromLanguage.getText().toString(), dialogBinding.edToLanguage.getText().toString()))
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void autoTranslate(String source, String fromLanguage, String toLanguage) {
        ProgressMsgBoxBinding loadingDialogBinding = ProgressMsgBoxBinding.inflate(getLayoutInflater());
        loadingDialogBinding.tvProgress.setText("Translating string 1/" + listmap.size());
        var loadingDialog = new MaterialAlertDialogBuilder(this)
                .setTitle("Please wait")
                .setCancelable(false)
                .setView(loadingDialogBinding.getRoot())
                .create();
        loadingDialog.show();

        isRunning = true;
        translateNext(loadingDialog, loadingDialogBinding, source, fromLanguage, toLanguage);
    }

    private void translateNext(AlertDialog loadingDialog, ProgressMsgBoxBinding loadingDialogBinding, String source, String fromLanguage, String toLanguage) {
        if (currentIndex >= listmap.size() || !isRunning) {
            loadingDialog.dismiss();
            adapter.notifyDataSetChanged();
            isRunning = false;
            return;
        }

        String text = (String) listmap.get(currentIndex).get("text");

        Translator.translate(text, fromLanguage, toLanguage, source, new Translator.TranslateListener() {
            @Override
            public void onTranslateSuccess(String result) {
                listmap.get(currentIndex).put("text", result);
                loadingDialogBinding.tvProgress.setText("Translating string " + (currentIndex + 1) + "/" + listmap.size());
                currentIndex++;
                translateNext(loadingDialog, loadingDialogBinding, source, fromLanguage, toLanguage);
            }

            @Override
            public void onTranslateError(String errorMessage) {
                isRunning = false;
                loadingDialog.setMessage("Error: Failed to translate");

                new MaterialAlertDialogBuilder(loadingDialog.getContext()).setTitle("Error").setMessage("Failed to translate").setPositiveButton("Retry", (dialog, which) -> {
                    isRunning = true;
                    translateNext(loadingDialog, loadingDialogBinding, source, fromLanguage, toLanguage);
                }).setNegativeButton("Cancel", (dialog, which) -> {
                    loadingDialog.dismiss();
                }).setCancelable(false).show();
            }
        });
    }

    public void addStringDialog() {
    private void addStringDialog() {
        aB dialog = new aB(this);
        ViewStringEditorAddBinding binding = ViewStringEditorAddBinding.inflate(LayoutInflater.from(this));
        dialog.b("Create new string");
        dialog.b("Create", v1 -> {
            String key = Objects.requireNonNull(binding.stringKeyInput.getText()).toString();
            String value = Objects.requireNonNull(binding.stringValueInput.getText()).toString();

            if (key.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toast("Please fill in all fields", Toast.LENGTH_SHORT);
                return;
            }

            if (isXmlStringsContains(listmap, key)) {
                binding.stringKeyInputLayout.setError("\"" + key + "\" is already exist");
                return;
            }
            addString(key, value);
        });
        dialog.a(getString(R.string.cancel), v1 -> dialog.dismiss());
        dialog.a(binding.getRoot());
        dialog.show();
    }

    public void addString(final String key, final String text) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("text", text);
        if (listmap.isEmpty()) {
            listmap.add(map);
            adapter.notifyItemInserted(0);
            return;
        }
        for (int i = 0; i < listmap.size(); i++) {
            if (Objects.equals(listmap.get(i).get("key"), key)) {
                listmap.set(i, map);
                adapter.notifyItemChanged(i);
                return;
            }
        }
        listmap.add(map);
        adapter.notifyItemInserted(listmap.size() - 1);
    }
    
    public boolean checkDefaultString(final String path) {
        File file = new File(path);
        String parentFolder = Objects.requireNonNull(file.getParentFile()).getName();
        return parentFolder.equals("values");
    }

    public String getLanguageCode(final String path) {
        return path.replaceFirst(".*/values-([a-z]{2}).*", "$1");
    }

    public String getDefaultStringPath(final String path) {
        return path.replaceFirst("/values-[a-z]{2}", "/values");
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> originalData;
        private ArrayList<HashMap<String, Object>> filteredData;

        public RecyclerViewAdapter(ArrayList<HashMap<String, Object>> data) {
            this.originalData = new ArrayList<>(data);
            this.filteredData = data;
        }

        @NonNull
        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(
                @NonNull ViewGroup parent, int viewType) {
            StringEditorItemBinding itemBinding =
                    StringEditorItemBinding.inflate(
                            LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
            HashMap<String, Object> item = filteredData.get(position);
            String key = (String) item.get("key");
            String text = (String) item.get("text");
            holder.binding.textInputLayout.setHint(key);
            holder.binding.editText.setText(text);

            holder.binding.editText.setOnClickListener(
                    v -> {
                        int adapterPosition = holder.getAbsoluteAdapterPosition();
                        HashMap<String, Object> currentItem = filteredData.get(adapterPosition);

                        aB dialog = new aB(StringEditorActivity.this);
                        ViewStringEditorAddBinding dialogBinding =
                                ViewStringEditorAddBinding.inflate(
                                        LayoutInflater.from(StringEditorActivity.this));

                        dialogBinding.stringKeyInput.setText((String) currentItem.get("key"));
                        dialogBinding.stringValueInput.setText((String) currentItem.get("text"));

                        dialog.b("Edit string");
                        dialog.b(
                                "Save",
                                v1 -> {
                                    String keyInput =
                                            Objects.requireNonNull(
                                                            dialogBinding.stringKeyInput.getText())
                                                    .toString();
                                    String valueInput =
                                            Objects.requireNonNull(
                                                            dialogBinding.stringValueInput
                                                                    .getText())
                                                    .toString();
                                    if (keyInput.isEmpty() || valueInput.isEmpty()) {
                                        SketchwareUtil.toast(
                                                "Please fill in all fields", Toast.LENGTH_SHORT);
                                        return;
                                    }
                                    if (keyInput.equals(key) && valueInput.equals(text)) {
                                        return;
                                    }
                                    currentItem.put("key", keyInput);
                                    currentItem.put("text", valueInput);
                                    notifyItemChanged(adapterPosition);
                                });

                        dialog.configureDefaultButton(
                                "Delete",
                                v1 -> {
                                    if (isXmlStringUsed(key)) {
                                        SketchwareUtil.toastError(
                                                Helper.getResString(
                                                        R.string
                                                                .logic_editor_title_remove_xml_string_error));
                                    } else {
                                        filteredData.remove(adapterPosition);
                                        notifyItemRemoved(adapterPosition);
                                    }
                                });
                        dialog.a(getString(R.string.cancel), v1 -> dialog.dismiss());
                        dialog.a(dialogBinding.getRoot());
                        dialog.show();
                    });
        }

        @Override
        public int getItemCount() {
            return filteredData.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            StringEditorItemBinding binding;

            public ViewHolder(StringEditorItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        /**
         * Filters the data based on the query.
         *
         * @param query The search query.
         */
        public void filter(String query) {
            if (query == null || query.isEmpty()) {
                filteredData = new ArrayList<>(originalData);
            } else {
                filteredData = new ArrayList<>();
                for (HashMap<String, Object> item : originalData) {
                    String key = (String) item.get("key");
                    String text = (String) item.get("text");
                    if ((key != null && key.toLowerCase().contains(query.toLowerCase()))
                            || (text != null && text.toLowerCase().contains(query.toLowerCase()))) {
                        filteredData.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public boolean isXmlStringUsed(String key) {
            if ("app_name".equals(key) || sc_id == null) {
                return false;
            }

            String projectScId = sc_id;
            eC projectDataManager = jC.a(projectScId);

            return isStringUsedInJavaFiles(projectScId, projectDataManager, key) || isStringUsedInXmlFiles(projectScId, projectDataManager, key);
        }

        private boolean isStringUsedInJavaFiles(String projectScId, eC projectDataManager, String key) {
            for (String javaFileName : getAllJavaFileNames(projectScId)) {
                for (Map.Entry<String, ArrayList<BlockBean>> entry : projectDataManager.b(javaFileName).entrySet()) {
                    for (BlockBean block : entry.getValue()) {
                        if ((block.opCode.equals("getResStr") && block.spec.equals(key)) ||
                                (block.opCode.equals("getResString") && block.parameters.get(0).equals("R.string." + key))) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private boolean isStringUsedInXmlFiles(String projectScId, eC projectDataManager, String key) {
            for (String xmlFileName : getAllXmlFileNames(projectScId)) {
                for (ViewBean view : projectDataManager.d(xmlFileName)) {
                    if (view.text.text.equals("@string/" + key) || view.text.hint.equals("@string/" + key)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
