package mod.bobur;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import android.text.SpannableStringBuilder;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.code.SrcCodeEditorLegacy;
import mod.hilal.saif.activities.tools.ConfigActivity;


public class StringEditorActivity extends AppCompatActivity {

    private Toolbar _toolbar;
    private HashMap<String, Object> map = new HashMap<>();

    private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();

    private ListView listview;
    private MaterialAlertDialogBuilder dialog;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.string_editor);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                onBackPressed();
            }
        });
        listview = findViewById(R.id.listview);
        dialog = new MaterialAlertDialogBuilder(this);

    }

    private void initializeLogic() {
        _convertXmlToListMap(FileUtil.readFile(getIntent().getStringExtra("content")), listmap);
        listview.setAdapter(new ListviewAdapter(listmap));
    }


    @Override
    public void onBackPressed() {
        if (_replaceXml(FileUtil.readFile(getIntent().getStringExtra("content"))).equals(_replaceXml(_convertListMapToXml(listmap)))) {
            finish();
        } else if (listmap.size() == 0) {
            finish();
        } else {
            dialog.setTitle("Warning");
            dialog.setMessage("You have unsaved changes. Are you sure you want to exit?");
            dialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface _dialog, int _which) {
                    finish();
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface _dialog, int _which) {

                }
            });
            dialog.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuitem2 = menu.add(Menu.NONE, 0, Menu.NONE, "Add a new string");
        menuitem2.setIcon(R.drawable.ic_add_24);
        menuitem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuItem menuitem1 = menu.add(Menu.NONE, 1, Menu.NONE, "Save");
        menuitem1.setIcon(R.drawable.save_icon_24px);
        menuitem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        if (!_checkDefaultString(getIntent().getStringExtra("content"))) {
            MenuItem menuitem3 = menu.add(Menu.NONE, 2, Menu.NONE, "Get default strings");
            menuitem3.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        }
        MenuItem menuitem4 = menu.add(Menu.NONE, 3, Menu.NONE, "Open in editor");
        menuitem4.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int _id = item.getItemId();
        final String _title = (String) item.getTitle();
        // save
        if (_id == 1) {
            FileUtil.writeFile(getIntent().getStringExtra("content"), _convertListMapToXml(listmap));
            SketchwareUtil.toast("Save completed", Toast.LENGTH_SHORT);
        }
        // add string
        if (_id == 0) {
            _addStringDialog();
        }
        // get default strings
        if (_id == 2) {
            listmap.clear();
            _convertXmlToListMap(FileUtil.readFile(_getDefaultStringPath(getIntent().getStringExtra("content"))), listmap);
            listview.setAdapter(new ListviewAdapter(listmap));
        }
        if (_id == 3) {
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void _convertXmlToListMap(final String _xmlString, final ArrayList<HashMap<String, Object>> _listmap) {
        try {
            // Initialize the XML parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(_xmlString.getBytes("UTF-8"));
            Document doc = builder.parse(input);

            // Normalize the XML structure
            doc.getDocumentElement().normalize();

            // Get the list of all <string> elements
            NodeList nodeList = doc.getElementsByTagName("string");

            // Loop through each <string> element
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Create a new HashMap to store the key, text, and translatable
                    HashMap<String, Object> map = new HashMap<>();

                    // Extract the key (from the name attribute) and value (text content)
                    String key = element.getAttribute("name");
                    String value = element.getTextContent();

                    // Extract the translatable attribute, default to "true" if not present
                    String translatable = element.getAttribute("translatable");
                    if (translatable.isEmpty()) {
                        translatable = "true"; // Default value
                    }

                    // Add the key-value-translatable pair to the HashMap
                    map.put("key", key);
                    map.put("text", value);
                    map.put("translatable", translatable);

                    // Add the HashMap to the provided list
                    _listmap.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String _convertListMapToXml(final ArrayList<HashMap<String, Object>> _listmap) {
        StringBuilder xmlString = new StringBuilder();

        // Start building the XML
        xmlString.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        xmlString.append("<resources>\n");

        // Iterate over the list and extract key-value pairs
        for (HashMap<String, Object> map : _listmap) {
            String key = (String) map.get("key");

            // Handle different types for text
            Object textObj = map.get("text");
            String text;

            if (textObj instanceof String) {
                text = (String) textObj;
            } else if (textObj instanceof SpannableStringBuilder) {
                // Convert SpannableStringBuilder to String
                text = textObj.toString(); // You might want to format it properly
            } else {
                text = ""; // Handle unexpected types
            }

            // Escape the text to handle special characters
            String escapedText = escapeXml(text);

            // Start the <string> element
            xmlString.append("    <string name=\"").append(key).append("\"");

            // Check if the translatable attribute exists
            if (map.containsKey("translatable")) {
                String translatable = (String) map.get("translatable");
                xmlString.append(" translatable=\"").append(translatable).append("\"");
            }

            // Close the <string> tag and add the escaped text
            xmlString.append(">").append(escapedText).append("</string>\n");
        }

        // Close the XML
        xmlString.append("</resources>");

        // Convert StringBuilder to a string and return it
        return xmlString.toString();
    }

    // Helper method to escape XML special characters
    private String escapeXml(String text) {
        if (text == null) {
            return "";
        }
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;")
                .replace("\n", "&#10;") // Preserve new lines in XML
                .replace("\r", "&#13;"); // Preserve carriage returns in XML
    }


    public String _replaceXml(final String _text) {
        return (_text.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("\r", "").replace("\n", "").replace(" ", "").replace("	", ""));
    }


    public void _addStringDialog() {
        LayoutInflater inflater = LayoutInflater.from(StringEditorActivity.this);
        View view = inflater.inflate(R.layout.property_popup_input_text, null);
        view.setPadding(50, 40, 50, 0);
        EditText edittext_key = (EditText) view.findViewById(R.id.ed_input);
        TextInputLayout ti_input = (TextInputLayout) view.findViewById(R.id.ti_input);
        ti_input.setHint("Enter key");
        // Set input filter to allow only letters, numbers, and underscores
        InputFilter nameFilter = (source, start, end, dest, dstart, dend) -> {
            if (source.toString().matches("[a-zA-Z0-9_]+")) {
                return source; // Allow valid characters (letters, numbers, underscores)
            }
            return ""; // Reject invalid characters
        };
        edittext_key.setFilters(new InputFilter[]{nameFilter});
        // Create and show the AlertDialog
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Enter Data").setIcon(R.drawable.ic_add_24).setView(view) // Set the custom layout
                .setPositiveButton("Create", (dialogInterface, i) -> {
                    // Retrieve the input values
                    String key = edittext_key.getText().toString();
                    _addString(key, "");
                }).setNegativeButton("Cancel", null).show();
    }


    public void _addString(final String _key, final String _text) {
        map = new HashMap<>();
        map.put("key", _key);
        map.put("text", _text);
        listmap.add(map);
        // Update the ListView with the new data
        if (listmap.get(0) == null) {
            listview.setAdapter(new ListviewAdapter(listmap));
        } else {
            ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
        }
    }


    public boolean _checkDefaultString(final String _path) {
        // Create a File object from the provided path
        File file = new File(_path);

        // Get the parent folder of the file
        String parentFolder = file.getParentFile().getName();

        // Check if the parent folder is "values"
        if (parentFolder.equals("values")) {
            return true;
        } else {
            return false;
        }
    }


    public String _getDefaultStringPath(final String _path) {
        return _path.replaceFirst("/values-[a-z]{2}", "/values");
    }

    public class ListviewAdapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public ListviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _v, ViewGroup _container) {
            LayoutInflater _inflater = getLayoutInflater();
            View _view = _v;
            if (_view == null) {
                _view = _inflater.inflate(R.layout.string_editor_item, null);
            }

            final com.google.android.material.textfield.TextInputLayout text_input_layout = _view.findViewById(R.id.text_input_layout);
            final EditText edit_text = _view.findViewById(R.id.edit_text);

            edit_text.setOnClickListener(v -> {
                LayoutInflater inflater2 = LayoutInflater.from(StringEditorActivity.this);
                View view = inflater2.inflate(R.layout.property_popup_input_text, null);
                view.setPadding(50, 40, 50, 0);
                EditText edittext_key = (EditText) view.findViewById(R.id.ed_input);
                TextInputLayout ti_input = (TextInputLayout) view.findViewById(R.id.ti_input);
                ti_input.setHint("Enter value");
                edittext_key.setText(_data.get((int) _position).get("text").toString());
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(StringEditorActivity.this);
                builder.setTitle("Edit value");
                builder.setIcon(R.drawable.ic_edit_grey600_24dp);
                builder.setPositiveButton("Save", (dialog, which) -> {
                    // Update the item in the list
                    _data.get((int) _position).put("text", edittext_key.getText());
                    notifyDataSetChanged();
                });
                builder.setNegativeButton("Cancel", null);
                builder.setNeutralButton("Delete", (dialog, which) -> {
                    // Remove the item from the list
                    _data.remove((int) _position);
                    notifyDataSetChanged();
                });
                builder.setView(view);
                builder.show();
            });
            edit_text.setOnFocusChangeListener((v, hasFocus) -> edit_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    _data.get((int) _position).put("text", s.toString());
                }
            }));

            edit_text.setText(_data.get((int) _position).get("text").toString());
            text_input_layout.setHint(_data.get((int) _position).get("key").toString());

            return _view;
        }
    }
}
