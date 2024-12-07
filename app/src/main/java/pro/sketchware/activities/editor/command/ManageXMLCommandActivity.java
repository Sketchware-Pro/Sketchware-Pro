package pro.sketchware.activities.editor.command;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;

import a.a.a.Jx;
import a.a.a.hC;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.yq;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.blocks.CommandBlock;

import pro.sketchware.R;
import pro.sketchware.activities.editor.command.adapters.XMLCommandAdapter;
import pro.sketchware.databinding.ManageXmlCommandBinding;
import pro.sketchware.databinding.ManageXmlCommandAddBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Executors;

public class ManageXMLCommandActivity extends BaseAppCompatActivity {

    private ManageXmlCommandBinding binding;
    private String sc_id;

    private String commandPath;

    private XMLCommandAdapter adapter;

    private ProjectSettings settings;

    private static final String[] COMMANDS_ACTION = {
        "insert", "add", "replace", "find-replace", "find-replace-first", "find-replace-all"
    };

    private ArrayList<HashMap<String, Object>> commands = new ArrayList<>();
    private ArrayList<String> xmlFiles;

    private hC projectFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageXmlCommandBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("XML Command Manager");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        UI.addSystemWindowInsetToPadding(binding.list, false, false, false, true);
        binding.toolbar.setNavigationOnClickListener(
                v -> {
                    if (!mB.a()) {
                        onBackPressed();
                    }
                });
        binding.fab.setOnClickListener(v -> dialog(false, 0));
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }
        commandPath = wq.b(sc_id) + "/command";
        projectFile = jC.b(sc_id);
        xmlFiles = new ArrayList<>(projectFile.e());
        xmlFiles.addAll(
                Arrays.asList("string.xml", "colors.xml", "styles.xml", "AndroidManifest.xml"));
        settings = new ProjectSettings(sc_id);
        adapter = new XMLCommandAdapter();
        adapter.setOnItemClickListener(
                item -> {
                    int position = item.second;
                    PopupMenu popupMenu = new PopupMenu(ManageXMLCommandActivity.this, item.first);
                    var menu = popupMenu.getMenu();
                    menu.add(Menu.NONE, 0, Menu.NONE, "Edit");
                    menu.add(Menu.NONE, 1, Menu.NONE, "Delete");
                    if (position != 0) menu.add(Menu.NONE, 2, Menu.NONE, "Move up");
                    if (position != adapter.getItemCount() - 1)
                        menu.add(Menu.NONE, 3, Menu.NONE, "Move down");

                    popupMenu.setOnMenuItemClickListener(
                            itemMenu -> {
                                if (itemMenu.getItemId() == 0) {
                                    dialog(true, position);
                                } else if (itemMenu.getItemId() == 2) {
                                    if (position > 0) {
                                        Collections.swap(commands, position, position - 1);
                                        save();
                                    }
                                } else if (itemMenu.getItemId() == 3) {
                                    if (position < adapter.getItemCount() - 1) {
                                        Collections.swap(commands, position, position + 1);
                                        save();
                                    }
                                } else {
                                    MaterialAlertDialogBuilder dialog =
                                            new MaterialAlertDialogBuilder(this);
                                    dialog.setTitle(R.string.common_word_delete);
                                    dialog.setMessage("Are you sure you want to delete this item?");
                                    dialog.setPositiveButton(
                                            R.string.common_word_yes,
                                            (d, w) -> {
                                                if (position != -1) {
                                                    commands.remove(position);
                                                    FileUtil.writeFile(commandPath, new Gson().toJson(commands));
                                                    adapter.submitList(new ArrayList<>(commands));
                                                }
                                            });
                                    dialog.setNegativeButton(R.string.common_word_no, null);
                                    dialog.show();
                                }
                                return true;
                            });

                    popupMenu.show();
                });
        binding.list.setAdapter(adapter);
        fetchCommand();
        generateCommand();
    }

    private void save() {
        FileUtil.writeFile(commandPath, new Gson().toJson(commands));
        adapter.submitList(null);
        adapter.submitList(commands);
    }

    private void dialog(boolean edit, int position) {
        var dialog = new BottomSheetDialog(this);
        ManageXmlCommandAddBinding binding =
                ManageXmlCommandAddBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        dialog.setOnShowListener(
                d -> {
                    BottomSheetDialog bsd = (BottomSheetDialog) d;
                    View parent =
                            bsd.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                    if (parent != null) {
                        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        behavior.setSkipCollapsed(true);

                        ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
                        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        parent.setLayoutParams(layoutParams);
                    }
                });
        dialog.show();
        binding.title.setText(!edit ? "Add new command" : "Edit command");

        if (edit) {
            var command = commands.get(position);
            binding.xmlName.setText(
                    CommandBlock.getInputName(
                            command.get("input") != null ? command.get("input").toString() : ""));
            binding.reference.setText(command.get("reference").toString());
            binding.command.setText(command.get("command").toString());
            binding.distance.setText(getIntValue(command.get("distance").toString()));
            binding.front.setText(getIntValue(command.get("after").toString()));
            binding.backend.setText(getIntValue(command.get("before").toString()));
            binding.changes.setText(
                    CommandBlock.getExceptFirstLine(
                            command.get("input") != null ? command.get("input").toString() : ""));
        }

        binding.xmlName.setAdapter(
                new ArrayAdapter<String>(
                        this, android.R.layout.simple_dropdown_item_1line, xmlFiles));

        binding.command.setAdapter(
                new ArrayAdapter<String>(
                        this, android.R.layout.simple_dropdown_item_1line, COMMANDS_ACTION));

        binding.positive.setText(R.string.common_word_save);
        binding.positive.setOnClickListener(
                v -> {
                    var xmlName = binding.xmlName.getText().toString();
                    var reference = binding.reference.getText().toString();
                    var command = binding.command.getText().toString();
                    if (TextUtils.isEmpty(xmlName)) {
                        SketchwareUtil.toastError("XML name is required");
                        return;
                    }
                    if (TextUtils.isEmpty(reference)) {
                        SketchwareUtil.toastError("reference is required");
                        return;
                    }
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("reference", reference);
                    map.put("distance", Integer.parseInt(binding.distance.getText().toString()));
                    map.put("after", Integer.parseInt(binding.front.getText().toString()));
                    map.put("before", Integer.parseInt(binding.backend.getText().toString()));
                    map.put("command", binding.command.getText().toString());
                    StringBuilder inputBuilder = new StringBuilder();
                    inputBuilder.append(">").append(xmlName).append("\n");
                    inputBuilder.append(binding.changes.getText().toString());
                    map.put("input", inputBuilder.toString());
                    if (edit) {
                        if (position != -1) {
                            commands.set(position, map);
                        }
                    } else {
                        commands.add(map);
                    }
                    save();
                    dialog.dismiss();
                });
        binding.negative.setOnClickListener(
                v -> {
                    dialog.dismiss();
                });
        binding.xmlName.requestFocus();
    }

    private String getIntValue(String value) {
        return String.valueOf((int) Double.parseDouble(value));
    }

    private void showConfirmationDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Confirmation");
        dialog.setMessage(
                "Would you like to enable the new XML Command? It will speed up XML generation and compilation, but this change cannot be undone. Don’t worry, your previous changes with the XML Command Block will transfered here so it will remain unaffected.");
        dialog.setPositiveButton(
                R.string.common_word_yes,
                (d, w) -> {
                    settings.setValue(
                            ProjectSettings.SETTING_NEW_XML_COMMAND,
                            ProjectSettings.SETTING_GENERIC_VALUE_TRUE);
                    generateCommand();
                });
        dialog.setNegativeButton(R.string.common_word_exit, (d, w) -> finish());
        dialog.setCancelable(false);
        dialog.show();
    }

    private void fetchCommand() {
        if (FileUtil.isExistFile(commandPath)) {
            try {
                commands =
                        new Gson().fromJson(FileUtil.readFile(commandPath), Helper.TYPE_MAP_LIST);
                adapter.submitList(commands);
            } catch (Exception ignored) {
            }
        }
    }

    private void generateCommand() {
        var newXMLCommand =
                Boolean.parseBoolean(
                        settings.getValue(
                                ProjectSettings.SETTING_NEW_XML_COMMAND,
                                ProjectSettings.SETTING_GENERIC_VALUE_FALSE));
        if (newXMLCommand) {
            if (!FileUtil.isExistFile(commandPath)) {
                k();
                Executors.newSingleThreadExecutor()
                        .execute(
                                () -> {
                                    fetchXMLCommand(ManageXMLCommandActivity.this, sc_id);
                                    runOnUiThread(
                                            () -> {
                                                h();
                                                fetchCommand();
                                            });
                                });
            }
        } else {
            showConfirmationDialog();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    public static void fetchXMLCommand(Context context, String sc_id) {
        var path = wq.b(sc_id) + "/command";
        if (FileUtil.isExistFile(path)) {
            return;
        }
        var yq = new yq(context, sc_id);
        var projectLibraryManager = jC.c(sc_id);
        var projectFileManager = jC.b(sc_id);
        var projectDataManager = jC.a(sc_id);
        yq.a(projectLibraryManager, projectFileManager, projectDataManager, false);
        CommandBlock.x();
        ArrayList<ProjectFileBean> files = new ArrayList<>(projectFileManager.b());
        files.addAll(new ArrayList<>(projectFileManager.c()));
        for (ProjectFileBean file : files) {
            CommandBlock.CBForXml(new Jx(yq.N, file, projectDataManager).generateCode());
        }
        String commandPath = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/commands");
        if (FileUtil.isExistFile(commandPath)) {
            FileUtil.copyFile(commandPath, path);
            CommandBlock.x();
        } else {
            FileUtil.writeFile(path, "[]");
        }
    }
}
