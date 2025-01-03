package pro.sketchware.activities.resources.editors;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.aB;
import a.a.a.wq;

import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ResourcesEditorsActivityBinding;
import pro.sketchware.activities.resources.editors.adapters.EditorsAdapter;
import pro.sketchware.activities.resources.editors.fragments.ColorsEditor;
import pro.sketchware.activities.resources.editors.fragments.StringsEditor;
import pro.sketchware.activities.resources.editors.fragments.StylesEditor;
import pro.sketchware.activities.resources.editors.fragments.ThemesEditor;
import pro.sketchware.databinding.ResourcesVariantSelectorDialogBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ResourcesEditorActivity extends AppCompatActivity {

    private ResourcesEditorsActivityBinding binding;

    private MaterialAlertDialogBuilder builder;

    public boolean isComingFromSrcCodeEditor;

    public String sc_id;
    public String variant;
    private final String variantFullNameStarts = "values-";
    public String stringsFilePath;
    public String colorsFilePath;
    public String stylesFilePath;
    public String themesFilePath;

    public StringsEditor stringsEditor;
    public ColorsEditor colorsEditor;
    public StylesEditor stylesEditor;
    public ThemesEditor themesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ResourcesEditorsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.topAppBar);

        sc_id = getIntent().getStringExtra("sc_id");
        if (getIntent().hasExtra("variant")) {
            initialize(getIntent().getStringExtra("variant"));
        } else {
            initialize("");
        }
    }

    private void initialize(String variant) {
        this.variant = variant;

        stringsEditor = new StringsEditor();
        colorsEditor = new ColorsEditor();
        stylesEditor = new StylesEditor();
        themesEditor = new ThemesEditor();

        String projectResourcesDirectory =
                String.format(
                        "%s/files/resource/values%s/",
                        wq.b(sc_id),
                        variant
                );

        stringsFilePath = projectResourcesDirectory + "strings.xml";
        colorsFilePath = projectResourcesDirectory + "colors.xml";
        stylesFilePath = projectResourcesDirectory + "styles.xml";
        themesFilePath = projectResourcesDirectory + "themes.xml";

        binding.viewPager.setOffscreenPageLimit(4);

        setupViewPager();

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("strings" + variant + ".xml");
                    break;
                case 1:
                    tab.setText("colors" + variant + ".xml");
                    break;
                case 2:
                    tab.setText("styles" + variant + ".xml");
                    break;
                case 3:
                    tab.setText("themes" + variant + ".xml");
                    break;
            }
        }).attach();

        binding.fab.setOnClickListener(view -> {
            int currentItem = binding.viewPager.getCurrentItem();
            if (currentItem == 0) {
                stringsEditor.addStringDialog();
            } else if (currentItem == 1) {
                colorsEditor.showColorEditDialog(null, -1);
            } else if (currentItem == 2) {
                stylesEditor.showAddStyleDialog();
            } else if (currentItem == 3) {
                themesEditor.showAddThemeDialog();
            }
        });
    }

    public void checkForInvalidResources() {
        if (stringsEditor.stringsEditorManager.isDataLoadingFailed) {
            showLoadFailedDialog("strings.xml", stringsFilePath);
            return;
        }
        if (colorsEditor.colorsEditorManager.isDataLoadingFailed) {
            showLoadFailedDialog("colors.xml", colorsFilePath);
            return;
        }
        if (stylesEditor.stylesEditorManager.isDataLoadingFailed) {
            showLoadFailedDialog("styles.xml", stylesFilePath);
            return;
        }
        if (themesEditor.themesEditorManager.isDataLoadingFailed) {
            showLoadFailedDialog("themes.xml", themesFilePath);
        }
    }

    private void goToCodeEditor(String title, String contentPath) {
        builder = null;
        isComingFromSrcCodeEditor = true;
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), SrcCodeEditor.class);
        intent.putExtra("title", title);
        intent.putExtra("content", contentPath);
        startActivity(intent);
    }

    private void showLoadFailedDialog(String title, String contentPath) {
        if (builder != null) return;
        builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(Helper.getResString(R.string.resources_manager_xml_load_failed_title))
                .setMessage(String.format(Helper.getResString(R.string.resources_manager_xml_load_failed_message), title))
                .setPositiveButton("Open code editor", (dialog, which) -> goToCodeEditor(title, contentPath))
                .setNegativeButton(Helper.getResString(R.string.common_word_exit), ((dialogInterface, i) -> super.onBackPressed()))
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        if (stringsEditor.checkForUnsavedChanges()
                || colorsEditor.checkForUnsavedChanges()
                || stylesEditor.checkForUnsavedChanges()
                || themesEditor.checkForUnsavedChanges()
        ) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Warning")
                    .setMessage("You have unsaved changes. Are you sure you want to exit?")
                    .setPositiveButton("Exit", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
            return;
        }

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onResume() {
        if (isComingFromSrcCodeEditor) {
            int currentItem = binding.viewPager.getCurrentItem();

            if (currentItem == 0 || stringsEditor.stringsEditorManager.isDataLoadingFailed) {
                stringsEditor.updateStringsList(stringsFilePath);
            }

            if (currentItem == 1 || colorsEditor.colorsEditorManager.isDataLoadingFailed) {
                colorsEditor.updateColorsList(colorsFilePath);
            }

            if (currentItem == 2 || stylesEditor.stylesEditorManager.isDataLoadingFailed) {
                stylesEditor.updateStylesList(stylesFilePath);
            }

            if (currentItem == 3 || themesEditor.themesEditorManager.isDataLoadingFailed) {
                themesEditor.updateThemesList(themesFilePath);
            }
            checkForInvalidResources();
        }
        isComingFromSrcCodeEditor = false;
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.resources_editor_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.toLowerCase().trim();
                    int currentItem = binding.viewPager.getCurrentItem();
                    if (currentItem == 0) {
                        stringsEditor.adapter.filter(newText);
                    } else if (currentItem == 1) {
                        colorsEditor.adapter.filter(newText);
                    } else if (currentItem == 2) {
                        stylesEditor.adapter.filter(newText);
                    } else if (currentItem == 3) {
                        themesEditor.adapter.filter(newText);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
            });
        }
        MenuItem getFromVariantItem = menu.findItem(R.id.action_get_from_variant);
        if (getFromVariantItem != null) {
            getFromVariantItem.setVisible(!variant.isEmpty());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            stringsEditor.saveStringsFile();
            colorsEditor.saveColorsFile();
            stylesEditor.saveStylesFile();
            themesEditor.saveThemesFile();
            SketchwareUtil.toast("Save completed");
        } else if (id == R.id.action_select_variant) {
            changeTheVariantDialog();
        } else if (id == R.id.action_get_from_variant) {
            importFromDefaultVariant();
        } else if (id != R.id.action_search) {
            int currentItem = binding.viewPager.getCurrentItem();
            if (currentItem == 0) {
                stringsEditor.saveStringsFile();
                goToCodeEditor("strings.xml", stringsFilePath);
            } else if (currentItem == 1) {
                colorsEditor.saveColorsFile();
                goToCodeEditor("colors.xml", colorsFilePath);
            } else if (currentItem == 2) {
                stylesEditor.saveStylesFile();
                goToCodeEditor("styles.xml", stylesFilePath);
            } else if (currentItem == 3) {
                themesEditor.saveThemesFile();
                goToCodeEditor("themes.xml", themesFilePath);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager() {
        EditorsAdapter adapter = new EditorsAdapter(this);
        binding.viewPager.setAdapter(adapter);
    }

    private void changeTheVariantDialog() {
        ArrayList<String> resourcesDir = new ArrayList<>();
        FileUtil.listDir(wq.b(sc_id) + "/files/resource/", resourcesDir);

        ArrayList<String> variants = extractVariants(resourcesDir);
        AtomicInteger selectedChoice = new AtomicInteger(variants.indexOf("values" + variant));

        ResourcesVariantSelectorDialogBinding binding = ResourcesVariantSelectorDialogBinding.inflate(getLayoutInflater());
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_word_select_variant));

        setupDialog(binding, dialog, variants, selectedChoice);

        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.a(binding.getRoot());
        dialog.show();
    }

    private void setupDialog(ResourcesVariantSelectorDialogBinding binding, aB dialog, ArrayList<String> variants, AtomicInteger selectedChoice) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, variants);
        binding.listView.setAdapter(adapter);
        binding.listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        binding.listView.setItemChecked(selectedChoice.get(), true);

        binding.listView.setOnItemClickListener((parent, view, which, id) -> {
            selectedChoice.set(which);
            binding.input.setText("");
            binding.input.clearFocus();
        });

        if (!(selectedChoice.get() >= 0 && selectedChoice.get() < variants.size() && variants.get(selectedChoice.get()).equals("values" + variant))) {
            binding.input.setText("values" + variant);
        }
        binding.input.setText(variantFullNameStarts);

        binding.input.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return;

                String inputText = s.toString();
                if (!inputText.startsWith(variantFullNameStarts)) {
                    isUpdating = true;
                    binding.input.setText(variantFullNameStarts);
                    binding.input.setSelection(variantFullNameStarts.length());
                    isUpdating = false;
                } else {
                    String newText = inputText.trim();
                    if (!newText.equals(variantFullNameStarts)) {
                        for (int i = 0; i < binding.listView.getCount(); i++) {
                            binding.listView.setItemChecked(i, false);
                        }
                    } else {
                        if (selectedChoice.get() >= 0 && selectedChoice.get() < binding.listView.getCount()) {
                            binding.listView.setItemChecked(selectedChoice.get(), true);
                        }
                    }
                }
            }
        });

        dialog.b(Helper.getResString(R.string.common_word_save), v -> saveVariant(binding, variants, selectedChoice));
    }

    private void saveVariant(ResourcesVariantSelectorDialogBinding binding, ArrayList<String> variants, AtomicInteger selectedChoice) {
        String newVariant = Objects.requireNonNull(binding.input.getText()).toString().trim();
        if (!newVariant.equals(variantFullNameStarts)) {
            if (newVariant.startsWith(variantFullNameStarts)) {
                initialize(newVariant.replace("values", ""));
            } else {
                SketchwareUtil.toastError("Invalid variant input");
            }
        } else {
            initialize(variants.get(selectedChoice.get()).replace("values", ""));
        }
    }

    public ArrayList<String> extractVariants(ArrayList<String> resourcesDir) {
        ArrayList<String> variants = new ArrayList<>();
        for (String dir :  resourcesDir) {
            String regex = "(values(-[^/]+)*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(dir);

            if (matcher.find()) {
                variants.add(matcher.group(1));
            }
        }
        return variants;
    }

    private void importFromDefaultVariant() {
        ArrayList<String> resourcesFileNames = new ArrayList<>(
                List.of("strings.xml",
                        "colors.xml",
                        "styles.xml",
                        "themes.xml"
                ));
        boolean[] selectedItems = new boolean[resourcesFileNames.size()];

        new MaterialAlertDialogBuilder(this)
                .setTitle(Helper.getResString(R.string.common_word_import_variant))
                .setMultiChoiceItems(
                        resourcesFileNames.toArray(new String[0]),
                        selectedItems,
                        (dialog, which, isChecked) -> selectedItems[which] = isChecked
                )
                .setPositiveButton(R.string.common_word_save, (dialog2, which) -> {
                    for (int i = 0; i < resourcesFileNames.size(); i++) {
                        if (selectedItems[i]) {
                            if (i == 0) stringsEditor.updateStringsList(stringsFilePath.replace(variant, ""));
                            if (i == 1) colorsEditor.updateColorsList(colorsFilePath.replace(variant, ""));
                            if (i == 2) stylesEditor.updateStylesList(stylesFilePath.replace(variant, ""));
                            if (i == 3) themesEditor.updateThemesList(themesFilePath.replace(variant, ""));
                        }
                    }
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .create()
                .show();
    }

}
