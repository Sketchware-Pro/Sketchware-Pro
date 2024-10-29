package pro.sketchware.activities.coloreditor;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ColorEditorActivityBinding;
import com.sketchware.remod.databinding.ColorEditorAddBinding;
import com.sketchware.remod.databinding.PalletCustomviewBinding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.XB;
import a.a.a.aB;
import a.a.a.xB;

import mod.elfilibustero.sketch.lib.utils.PropertiesUtil;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.code.SrcCodeEditorLegacy;
import mod.hilal.saif.activities.tools.ConfigActivity;

import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.XmlUtil;

public class ColorEditorActivity extends AppCompatActivity {

    private final ArrayList<HashMap<String, Object>> color_list = new ArrayList<>();
    boolean isGoingToEditor;
    private ColorEditorActivityBinding binding;
    private RecyclerViewAdapter adapter;
    private Activity activity;

    public static String convertListmapToXml(ArrayList<HashMap<String, Object>> colorList) {
        try {
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter stringWriter = new StringWriter();

            xmlSerializer.setOutput(stringWriter);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.text("\n");
            xmlSerializer.startTag(null, "resources");
            xmlSerializer.text("\n");

            for (int i = 0; i < colorList.size(); i++) {
                String colorName = Objects.requireNonNull(colorList.get(i).get("colorName")).toString();
                String colorValue = Objects.requireNonNull(colorList.get(i).get("colorValue")).toString();
                xmlSerializer.startTag(null, "color");
                xmlSerializer.attribute(null, "name", colorName);
                xmlSerializer.text(colorValue);
                xmlSerializer.endTag(null, "color");
                xmlSerializer.text("\n");
            }

            xmlSerializer.endTag(null, "resources");
            xmlSerializer.text("\n");
            xmlSerializer.endDocument();

            return stringWriter.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isValidHexColor(String colorStr) {
        if (colorStr == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^#([a-fA-F0-9]{1,8})");
        Matcher matcher = pattern.matcher(colorStr);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ColorEditorActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.toolbar.setNavigationOnClickListener(_v -> onBackPressed());
        RecyclerView recyclerview_colors = findViewById(R.id.recyclerview_colors);
        activity = this;

        parseColorsXML(FileUtil.readFile(getIntent().getStringExtra("content")));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview_colors.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(color_list);
        recyclerview_colors.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGoingToEditor) {
            parseColorsXML(FileUtil.readFile(getIntent().getStringExtra("content")));
            adapter = new ColorEditorActivity.RecyclerViewAdapter(color_list);
            binding.recyclerviewColors.setAdapter(adapter);
        }
        isGoingToEditor = false;
    }

    @Override
    public void onBackPressed() {
        if (!Objects.equals(XmlUtil.replaceXml(Objects.requireNonNull(convertListmapToXml(color_list))), XmlUtil.replaceXml(FileUtil.readFile(getIntent().getStringExtra("content"))))) {
            showExitDialog();
        }else{
            super.onBackPressed();
        }
        if (color_list.isEmpty() && (!FileUtil.readFile(getIntent().getStringExtra("content")).contains("</resources>"))) {
            XmlUtil.saveXml(getIntent().getStringExtra("content"), convertListmapToXml(color_list));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menu.add(0, 0, 0, "Add a new color")
                .setIcon(R.drawable.ic_add_24)
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0, 1, 0, "Save")
                .setIcon(R.drawable.save_icon_24px)
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0, 3, 0, "Open in editor")
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_NEVER);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 1) {
            XmlUtil.saveXml(getIntent().getStringExtra("content"), convertListmapToXml(color_list));
        }else if (id == 0) {
            addColorDialog();
        }else if (id == 2) {
            parseColorsXML(FileUtil.readFile(getDefaultResPath(Objects.requireNonNull(getIntent().getStringExtra("content")))));
            adapter.notifyDataSetChanged();
        }else if (id == 3) {
            XmlUtil.saveXml(getIntent().getStringExtra("content"), convertListmapToXml(color_list));
            Intent intent = new Intent();
            if (ConfigActivity.isLegacyCeEnabled()) {
                intent.setClass(getApplicationContext(), SrcCodeEditorLegacy.class);
            }else{
                intent.setClass(getApplicationContext(), SrcCodeEditor.class);
            }
            intent.putExtra("title", getIntent().getStringExtra("title"));
            intent.putExtra("content", getIntent().getStringExtra("content"));
            intent.putExtra("xml", getIntent().getStringExtra("xml"));
            isGoingToEditor = true;
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showExitDialog() {
        aB dialog = new aB(activity);
        dialog.b(xB.b().a(activity, R.string.common_word_warning));
        dialog.a(xB.b().a(activity, R.string.src_code_editor_unsaved_changes_dialog_warning_message));
        dialog.b(xB.b().a(activity, R.string.common_word_save), v -> {
            XmlUtil.saveXml(getIntent().getStringExtra("content"), convertListmapToXml(color_list));
            dialog.dismiss();
            finish();
        });
        dialog.a(xB.b().a(activity, R.string.common_word_exit), v -> {
            dialog.dismiss();
            finish();
        });
        dialog.show();
    }

    private void showDeleteDialog(RecyclerViewAdapter adapter, int position, ArrayList<HashMap<String, Object>> data) {
        aB dialog = new aB(activity);
        dialog.a(R.drawable.delete_96);
        dialog.b(xB.b().a(activity, R.string.color_editor_delete_color));
        dialog.a(xB.b().a(activity, R.string.picker_color_message_delete_all_custom_color));
        dialog.b(xB.b().a(activity, R.string.common_word_delete), v -> {
            data.remove(position);
            if (data.size() - 1 >= 0) {
                adapter.notifyDataSetChanged();
            }else{
                adapter.notifyItemRemoved(position);
            }
            dialog.dismiss();
        });
        dialog.a(xB.b().a(activity, R.string.common_word_cancel), v -> dialog.dismiss());
        dialog.show();
    }

    private void parseColorsXML(String colorXml) {
        try {
            color_list.clear();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(colorXml));

            int eventType = parser.getEventType();
            String colorName = null;
            String colorValue = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equals("color")) {
                            colorName = parser.getAttributeValue(null, "name");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        colorValue = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals("color")) {
                            if (colorName != null && (isValidHexColor(colorValue))) {
                                HashMap<String, Object> colors = new HashMap<>();
                                colors.put("colorName", colorName);
                                colors.put("colorValue", colorValue);
                                color_list.add(colors);
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addColorDialog() {
        aB dialog = new aB(this);
        ColorEditorAddBinding binding = ColorEditorAddBinding.inflate(LayoutInflater.from(this));
        XB colorValidator = new XB(ColorEditorActivity.this, binding.colorValueInputLayout, binding.colorPreview);

        dialog.b("Create new color");
        dialog.b("Create", v1 -> {
            String key = Objects.requireNonNull(binding.colorKeyInput.getText()).toString();
            String value = Objects.requireNonNull(binding.colorValueInput.getText()).toString();

            if (key.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toast("Please fill in all fields", Toast.LENGTH_SHORT);
                return;
            }
            if (!colorValidator.b()) {
                SketchwareUtil.toast("Please enter a valid HEX color", Toast.LENGTH_SHORT);
                return;
            }
            addColor(key, value);
            dialog.dismiss();
        });
        dialog.a(getString(R.string.cancel), v1 -> dialog.dismiss());
        binding.colorPreview.setBackgroundColor(0xffffff);
        dialog.a(binding.getRoot());
        dialog.show();
    }

    public void addColor(final String name, final String value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("colorName", name);
        map.put("colorValue", "#" + value);
        if (color_list.isEmpty()) {
            color_list.add(map);
            adapter.notifyItemInserted(0);
            return;
        }
        for (int i = 0; i < color_list.size(); i++) {
            if (Objects.equals(color_list.get(i).get("colorName"), name)) {
                color_list.set(i, map);
                adapter.notifyItemChanged(i);
                return;
            }
        }
        color_list.add(map);
        adapter.notifyItemInserted(color_list.size() - 1);

    }

    public String getDefaultResPath(final String path) {
        return path.replaceFirst("/values-[a-z]{2}", "/values");
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> data;

        public RecyclerViewAdapter(ArrayList<HashMap<String, Object>> _arr) {
            data = _arr;
        }

        @NonNull
        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PalletCustomviewBinding itemBinding = PalletCustomviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String colorName = Objects.requireNonNull(data.get(position).get("colorName")).toString();
            String colorValue = Objects.requireNonNull(data.get(position).get("colorValue")).toString();

            if (isValidHexColor(colorValue)) {
                holder.itemBinding.title.setHint(colorName);
                holder.itemBinding.sub.setText(colorValue);
                holder.itemBinding.color.setBackgroundColor(PropertiesUtil.parseColor(colorValue));
            }else{
                data.remove(position);
                if (data.size() - 1 >= 0) {
                    notifyDataSetChanged();
                }else{
                    notifyItemRemoved(position);
                }
            }

            holder.itemBinding.backgroundCard.setOnClickListener(v -> {
                HashMap<String, Object> currentItem = data.get(position);
                aB dialog = new aB(ColorEditorActivity.this);
                ColorEditorAddBinding dialogBinding = ColorEditorAddBinding.inflate(LayoutInflater.from(ColorEditorActivity.this));
                XB colorValidator2 = new XB(ColorEditorActivity.this, dialogBinding.colorValueInputLayout, dialogBinding.colorPreview);

                dialogBinding.colorKeyInput.setText(colorName);
                dialogBinding.colorValueInput.setText(colorValue.replace("#", ""));
                dialogBinding.colorPreview.setBackgroundColor(PropertiesUtil.parseColor(colorValue));

                dialogBinding.colorKeyInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0 && Character.isDigit(s.charAt(start))) {
                            dialogBinding.colorKeyInputLayout.setError("Color's name should be starting with a letter");
                        }else{
                            dialogBinding.colorKeyInputLayout.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                dialog.b("Edit color");
                dialog.b("Save", v1 -> {
                    String name = Objects.requireNonNull(dialogBinding.colorKeyInput.getText()).toString();
                    String colorHex = (Objects.requireNonNull(dialogBinding.colorValueInput.getText()).toString()).replace("#", "");
                    if (name.isEmpty() || colorHex.isEmpty()) {
                        SketchwareUtil.toast("Please fill in all fields", Toast.LENGTH_SHORT);
                        return;
                    }
                    if (name.equals(colorName) && colorHex.equals(colorValue)) {
                        dialog.dismiss();
                        return;
                    }
                    if (colorValidator2.b()) {
                        currentItem.put("colorName", name);
                        currentItem.put("colorValue", "#" + colorHex);
                        notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });

                dialog.configureDefaultButton("Delete", v1 -> {
                    data.remove(position);
                    if (data.size() - 1 >= 0) {
                        notifyDataSetChanged();
                    }else{
                        notifyItemRemoved(position);
                    }
                    dialog.dismiss();
                });
                dialog.a(getString(R.string.cancel), v1 -> dialog.dismiss());
                dialog.a(dialogBinding.getRoot());
                dialog.show();
            });

            holder.itemBinding.backgroundCard.setOnLongClickListener(v -> {
                showDeleteDialog(adapter, position, data);
                return false;
            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public PalletCustomviewBinding itemBinding;

            public ViewHolder(PalletCustomviewBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }
        }
    }

}
