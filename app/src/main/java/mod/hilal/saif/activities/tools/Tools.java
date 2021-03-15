package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;

import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import mod.SketchwareUtil;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.lib.FileUtil;

public class Tools extends Activity {

    private ViewGroup base;
    private RecyclerView dump;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427775);
        dump = findViewById(2131231449);
        base = (ViewGroup) dump.getParent();
        base.removeView(dump);
        newToolbar(base);
        setupViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/local_library"))) {
            FileUtil.deleteFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/local_library"));
        }
    }

    private void makeup(View parent, int iconResourceId, String title, String subtitle) {
        View inflate = getLayoutInflater().inflate(2131427537, null);
        ImageView imageView = inflate.findViewById(2131231428);
        inflate.findViewById(2131231965).setVisibility(View.GONE);
        ((LinearLayout) imageView.getParent()).setGravity(17);
        imageView.setImageResource(iconResourceId);
        ((TextView) inflate.findViewById(2131231430)).setText(title);
        ((TextView) inflate.findViewById(2131231427)).setText(subtitle);
        ((ViewGroup) parent).addView(inflate);
    }

    private CardView newCard(int width, int height, float weight) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height, weight);
        layoutParams.setMargins((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2));
        cardView.setLayoutParams(layoutParams);
        cardView.setPadding((int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2));
        cardView.setCardBackgroundColor(-1);
        cardView.setRadius(SketchwareUtil.getDip(4));
        return cardView;
    }

    private LinearLayout newLayout(int width, int height, float weight) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
        linearLayout.setPadding((int) SketchwareUtil.getDip(1), (int) SketchwareUtil.getDip(1), (int) SketchwareUtil.getDip(1), (int) SketchwareUtil.getDip(1));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);
        linearLayout.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#64B5F6")}), gradientDrawable, null));
        linearLayout.setClickable(true);
        linearLayout.setFocusable(true);
        return linearLayout;
    }

    private void newToolbar(View parent) {
        View inflate = getLayoutInflater().inflate(2131427799, null);
        ((TextView) inflate.findViewById(2131232458)).setText("Tools");
        ImageView back = inflate.findViewById(2131232457);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back);
        parent.setPadding(0, 0, 0, 0);
        ((ViewGroup) parent).addView(inflate, 0);
    }

    public void openWorkingDirectory() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        properties.root = getFilesDir().getParentFile();
        properties.error_dir = getExternalCacheDir();
        properties.extensions = null;
        FilePickerDialog dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select ");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(final String[] files) {
                if (files.length > 1 || new File(files[0]).isDirectory()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tools.this)
                            .setTitle("Select an action")
                            .setSingleChoiceItems(new String[]{"Delete"}, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (String file : files) {
                                        FileUtil.deleteFile(file);
                                    }
                                }
                            });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tools.this)
                            .setTitle("Select an action")
                            .setSingleChoiceItems(new String[]{"Edit", "Delete"}, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Intent intent = new Intent();
                                            if (ConfigActivity.isLegacyCeEnabled()) {
                                                intent.setClass(getApplicationContext(), mod.hey.studios.activity.SrcCodeEditor.class);
                                            } else {
                                                intent.setClass(getApplicationContext(), SrcCodeEditor.class);
                                            }
                                            intent.putExtra("title", Uri.parse(files[0]).getLastPathSegment());
                                            intent.putExtra("content", files[0]);
                                            intent.putExtra("xml", "");
                                            startActivity(intent);
                                            break;

                                        case 1:
                                            FileUtil.deleteFile(files[0]);
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
            }
        });
        dialog.show();
    }

    private void setupViews() {
        CardView blockManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout newLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        blockManager.addView(newLayout);
        makeup(newLayout, 2131165374, "Block manager", "Manage your own blocks to use in Logic Editor");
        base.addView(blockManager);
        newLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), BlocksManager.class);
                startActivity(intent);
            }
        });
        CardView blockSelectorManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout blockSelectorManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        blockSelectorManager.addView(blockSelectorManagerLayout);
        makeup(blockSelectorManagerLayout, 2131166037, "Block selector menu manager", "Manage your own block selector menus");
        base.addView(blockSelectorManager);
        blockSelectorManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), BlockSelectorActivity.class);
                startActivity(intent);
            }
        });
        CardView componentManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout componentManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        componentManager.addView(componentManagerLayout);
        makeup(componentManagerLayout, 2131165449, "Component manager", "Manage your own components");
        base.addView(componentManager);
        componentManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ComponentsMaker.class);
                startActivity(intent);
            }
        });
        CardView eventManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout eventManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        eventManager.addView(eventManagerLayout);
        makeup(eventManagerLayout, 2131165580, "Event manager", "Manage your own events");
        base.addView(eventManager);
        eventManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), EventsMaker.class);
                startActivity(intent);
            }
        });
        CardView localLibraryManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout localLibraryManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        localLibraryManager.addView(localLibraryManagerLayout);
        makeup(localLibraryManagerLayout, 2131165477, "Local library manager", "Manage and download local libraries");
        base.addView(localLibraryManager);
        localLibraryManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/local_library"), "[]");
                Intent intent = new Intent();
                intent.putExtra("sc_id", "system");
                intent.setClass(getApplicationContext(), ManageLocalLibraryActivity.class);
                startActivity(intent);
            }
        });
        CardView modSettings = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout modSettingsLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        modSettings.addView(modSettingsLayout);
        makeup(modSettingsLayout, 2131165546, "Mod settings", "Change general mod settings");
        base.addView(modSettings);
        modSettingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ConfigActivity.class);
                startActivity(intent);
            }
        });
        CardView openWorkingDirectory = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout openWorkingDirectoryLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        openWorkingDirectory.addView(openWorkingDirectoryLayout);
        makeup(openWorkingDirectoryLayout, 2131558403, "Open working directory", "Open Sketchware Pro's directory and edit files in it");
        base.addView(openWorkingDirectory);
        openWorkingDirectoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWorkingDirectory();
            }
        });
    }
}