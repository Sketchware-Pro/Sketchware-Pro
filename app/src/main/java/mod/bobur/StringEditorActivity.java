package mod.bobur;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.StringEditorBinding;
import com.sketchware.remod.databinding.StringEditorItemBinding;
import com.sketchware.remod.databinding.ViewStringEditorAddBinding;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import a.a.a.aB;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.FileUtil;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.code.SrcCodeEditorLegacy;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hilal.saif.activities.tools.ConfigActivity;

public class StringEditorActivity extends AppCompatActivity {

    private final ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
    private MaterialAlertDialogBuilder dialog;
    private StringEditorBinding binding;
    private RecyclerViewAdapter adapter;
    private boolean isComingFromSrcCodeEditor = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StringEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(_v -> onBackPressed());
        dialog = new MaterialAlertDialogBuilder(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isComingFromSrcCodeEditor) {
            convertXmlToListMap(FileUtil.readFile(getIntent().getStringExtra("content")), listmap);
            adapter = new RecyclerViewAdapter(listmap);
            binding.recyclerView.setAdapter(adapter);
        }
        isComingFromSrcCodeEditor = false;
    }

    @Override
    public void onBackPressed() {
        if (replaceXml(FileUtil.readFile(getIntent().getStringExtra("content")))
                .equals(replaceXml(convertListMapToXml(listmap))) || listmap.isEmpty()) {
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
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menu.add(0, 0, 0, "Add a new string")
                .setIcon(R.drawable.ic_add_24)
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0, 1, 0, "Save")
                .setIcon(R.drawable.save_icon_24px)
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);

        if (!checkDefaultString(getIntent().getStringExtra("content"))) {
            menu.add(0, 2, 0, "Get default strings")
                    .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_NEVER);
        }

        menu.add(0, 3, 0, "Open in editor")
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_NEVER);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == 1) {
            saveXml();
        } else if (id == 0) {
            addStringDialog();
        } else if (id == 2) {
            convertXmlToListMap(FileUtil.readFile(getDefaultStringPath(Objects.requireNonNull(getIntent().getStringExtra("content")))), listmap);
            adapter.notifyDataSetChanged();
        } else if (id == 3) {
            saveXml();
            isComingFromSrcCodeEditor = true;
            Intent intent = new Intent();
            if (ConfigActivity.isLegacyCeEnabled()) {
                intent.setClass(getApplicationContext(), SrcCodeEditorLegacy.class);
            } else {
                intent.setClass(getApplicationContext(), SrcCodeEditor.class);
            }
            intent.putExtra("title", getIntent().getStringExtra("title"));
            intent.putExtra("content", getIntent().getStringExtra("content"));
            intent.putExtra("xml", getIntent().getStringExtra("xml"));
            startActivity(intent);
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
                    Element element = (Element) node;
                    HashMap<String, Object> map = new HashMap<>();
                    String key = element.getAttribute("name");
                    String value = element.getTextContent();
                    String translatable = element.getAttribute("translatable");
                    if (translatable.isEmpty()) {
                        translatable = "true";
                    }
                    map.put("key", key);
                    map.put("text", value);
                    map.put("translatable", translatable);
                    listmap.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                .replace("'", "&apos;")
                .replace("\n", "&#10;")
                .replace("\r", "&#13;");
    }

    public String replaceXml(final String text) {
        return text.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "")
                .replace("\r", "")
                .replace("\n", "")
                .replace(" ", "")
                .replace("\t", "");
    }

    public void saveXml() {
        FileUtil.writeFile(getIntent().getStringExtra("content"), convertListMapToXml(listmap));
        SketchwareUtil.toast("Save completed", Toast.LENGTH_SHORT);
    }

    public void addStringDialog() {
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

            addString(key, value);
            dialog.dismiss();
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
        String parentFolder = file.getParentFile().getName();
        return parentFolder.equals("values");
    }

    public String getDefaultStringPath(final String path) {
        return path.replaceFirst("/values-[a-z]{2}", "/values");
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> data;

        public RecyclerViewAdapter(ArrayList<HashMap<String, Object>> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            StringEditorItemBinding itemBinding = StringEditorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
            HashMap<String, Object> item = data.get(position);
            String key = (String) item.get("key");
            String text = (String) item.get("text");
            holder.binding.textInputLayout.setHint(key);
            holder.binding.editText.setText(text);

            holder.binding.editText.setOnClickListener(v -> {
                int adapterPosition = holder.getAdapterPosition();
                HashMap<String, Object> currentItem = data.get(adapterPosition);

                aB dialog = new aB(StringEditorActivity.this);
                ViewStringEditorAddBinding dialogBinding = ViewStringEditorAddBinding.inflate(LayoutInflater.from(StringEditorActivity.this));

                dialogBinding.stringKeyInput.setText((String) currentItem.get("key"));
                dialogBinding.stringValueInput.setText((String) currentItem.get("text"));

                dialog.b("Edit string");
                dialog.b("Save", v1 -> {
                    String keyInput = Objects.requireNonNull(dialogBinding.stringKeyInput.getText()).toString();
                    String valueInput = Objects.requireNonNull(dialogBinding.stringValueInput.getText()).toString();
                    if (keyInput.isEmpty() || valueInput.isEmpty()) {
                        SketchwareUtil.toast("Please fill in all fields", Toast.LENGTH_SHORT);
                        return;
                    }
                    if (keyInput.equals(key) && valueInput.equals(text)) {
                        dialog.dismiss();
                        return;
                    }
                    currentItem.put("key", keyInput);
                    currentItem.put("text", valueInput);
                    notifyItemChanged(adapterPosition);
                    dialog.dismiss();
                });

                dialog.configureDefaultButton("Delete", v1 -> {
                    data.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    dialog.dismiss();
                });
                dialog.a(getString(R.string.cancel), v1 -> dialog.dismiss());
                dialog.a(dialogBinding.getRoot());
                dialog.show();
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            StringEditorItemBinding binding;

            public ViewHolder(StringEditorItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
