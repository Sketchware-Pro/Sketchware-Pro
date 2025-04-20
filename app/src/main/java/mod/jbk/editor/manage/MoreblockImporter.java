package mod.jbk.editor.manage;

import android.app.Activity;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import a.a.a.Gx;
import a.a.a.Np;
import a.a.a.Op;
import a.a.a.Qp;
import a.a.a.ZB;
import a.a.a.bB;
import a.a.a.eC;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class MoreblockImporter {
    private final Activity activity;
    private final String sc_id;
    private final ProjectFileBean projectActivity;
    private final String activityJavaName;

    private final oB fileUtil = new oB();

    private ArrayList<Pair<Integer, String>> toBeAddedVariables;
    private ArrayList<Pair<Integer, String>> toBeAddedLists;
    private ArrayList<ProjectResourceBean> toBeAddedImages;
    private ArrayList<ProjectResourceBean> toBeAddedSounds;
    private ArrayList<ProjectResourceBean> toBeAddedFonts;

    private Callback callback;

    public MoreblockImporter(Activity activity, String sc_id, ProjectFileBean projectActivity) {
        this.activity = activity;
        this.sc_id = sc_id;
        this.projectActivity = projectActivity;
        this.activityJavaName = projectActivity.getJavaName();
    }

    public void importMoreblock(MoreBlockCollectionBean moreblock, Callback callback) {
        this.callback = callback;
        String blockName = ReturnMoreblockManager.getMbName(ReturnMoreblockManager.getMbNameWithTypeFromSpec(moreblock.spec));

        boolean duplicateNameFound = false;
        for (Pair<String, String> projectMoreBlock : jC.a(sc_id).i(activityJavaName)) {
            if (ReturnMoreblockManager.getMbName(projectMoreBlock.first).equals(blockName)) {
                duplicateNameFound = true;
                break;
            }
        }
        if (!duplicateNameFound) {
            handleVariables(moreblock);
        } else {
            showEditMoreBlockNameDialog(moreblock);
        }
    }

    private void handleVariables(MoreBlockCollectionBean moreBlock) {
        toBeAddedVariables = new ArrayList<>();
        toBeAddedLists = new ArrayList<>();
        toBeAddedImages = new ArrayList<>();
        toBeAddedSounds = new ArrayList<>();
        toBeAddedFonts = new ArrayList<>();
        for (BlockBean next : moreBlock.blocks) {
            if (next.opCode.equals("getVar")) {
                switch (next.type) {
                    case "b":
                        maybeAddVariable(0, next.spec);
                        break;
                    case "d":
                        maybeAddVariable(1, next.spec);
                        break;
                    case "s":
                        maybeAddVariable(2, next.spec);
                        break;
                    case "a":
                        maybeAddVariable(3, next.spec);
                        break;
                    case "l":
                        switch (next.typeName) {
                            case "List Number" -> maybeAddList(1, next.spec);
                            case "List String" -> maybeAddList(2, next.spec);
                            case "List Map" -> maybeAddList(3, next.spec);
                        }
                        break;
                }
            }
            ArrayList<Gx> paramClassInfo = next.getParamClassInfo();
            if (!paramClassInfo.isEmpty()) {
                for (int i = 0; i < paramClassInfo.size(); i++) {
                    Gx gx = paramClassInfo.get(i);
                    String str = next.parameters.get(i);
                    if (!str.isEmpty() && str.charAt(0) != '@') {
                        if (gx.b("boolean.SelectBoolean")) {
                            maybeAddVariable(0, str);
                        } else if (gx.b("double.SelectDouble")) {
                            maybeAddVariable(1, str);
                        } else if (gx.b("String.SelectString")) {
                            maybeAddVariable(2, str);
                        } else if (gx.b("Map")) {
                            maybeAddVariable(3, str);
                        } else if (gx.b("ListInt")) {
                            maybeAddList(1, str);
                        } else if (gx.b("ListString")) {
                            maybeAddList(2, str);
                        } else if (gx.b("ListMap")) {
                            maybeAddList(3, str);
                        } else if (!gx.b("resource_bg") && !gx.b("resource")) {
                            if (gx.b("sound")) {
                                maybeAddSound(str);
                            } else if (gx.b("font")) {
                                maybeAddFont(str);
                            }
                        } else {
                            maybeAddImage(str);
                        }
                    }
                }
            }
        }
        if (toBeAddedVariables.isEmpty() && toBeAddedLists.isEmpty() && toBeAddedImages.isEmpty() && toBeAddedSounds.isEmpty() && toBeAddedFonts.isEmpty()) {
            createEvent(moreBlock);
        } else {
            showAutoAddDialog(moreBlock);
        }
    }

    private void createEvent(MoreBlockCollectionBean moreBlock) {
        String moreBlockName = ReturnMoreblockManager.getMbNameWithTypeFromSpec(moreBlock.spec);

        jC.a(sc_id).a(activityJavaName, moreBlockName, moreBlock.spec);
        jC.a(sc_id).a(activityJavaName, moreBlockName + "_moreBlock", moreBlock.blocks);
        bB.a(activity, xB.b().a(activity, R.string.common_message_complete_save), 0).show();
        callback.onImportComplete();
    }

    private void showAutoAddDialog(MoreBlockCollectionBean moreBlock) {
        MaterialAlertDialogBuilder aBVar = new MaterialAlertDialogBuilder(activity);
        aBVar.setTitle(xB.b().a(activity, R.string.logic_more_block_title_add_variable_resource));
        aBVar.setIcon(R.drawable.break_warning_96_red);
        aBVar.setMessage(xB.b().a(activity, R.string.logic_more_block_desc_add_variable_resource));
        aBVar.setPositiveButton(xB.b().a(activity, R.string.common_word_continue), (v, which) -> {
            for (Pair<Integer, String> pair : toBeAddedVariables) {
                eC eC = jC.a(sc_id);
                eC.c(activityJavaName, pair.first, pair.second);
            }
            for (Pair<Integer, String> pair : toBeAddedLists) {
                eC eC = jC.a(sc_id);
                eC.b(activityJavaName, pair.first, pair.second);
            }
            for (ProjectResourceBean bean : toBeAddedImages) {
                copyImageFromCollectionsToProject(bean.resName);
            }
            for (ProjectResourceBean bean : toBeAddedSounds) {
                copySoundFromCollectionsToProject(bean.resName);
            }
            for (ProjectResourceBean bean : toBeAddedFonts) {
                copyFontFromCollectionsToProject(bean.resName);
            }
            createEvent(moreBlock);
            v.dismiss();
        });
        aBVar.setNegativeButton(xB.b().a(activity, R.string.common_word_cancel), null);
        aBVar.show();
    }

    private void showEditMoreBlockNameDialog(MoreBlockCollectionBean moreBlock) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        dialog.setTitle(xB.b().a(activity, R.string.logic_more_block_title_change_block_name));
        dialog.setIcon(R.drawable.more_block_96dp);

        View customView = wB.a(activity, R.layout.property_popup_save_to_favorite);
        ((TextView) customView.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(activity, R.string.logic_more_block_desc_change_block_name));
        EditText newName = customView.findViewById(R.id.ed_input);
        newName.setPrivateImeOptions("defaultInputmode=english;");
        newName.setLines(1);
        newName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        newName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        List<String> moreBlockNamesWithoutReturnTypes = new LinkedList<>();
        for (String moreBlockName : jC.a(sc_id).a(projectActivity)) {
            moreBlockNamesWithoutReturnTypes.add(ReturnMoreblockManager.getMbName(moreBlockName));
        }

        ZB validator = new ZB(activity, customView.findViewById(R.id.ti_input), uq.b, uq.a(), new ArrayList<>(moreBlockNamesWithoutReturnTypes));
        dialog.setView(customView);
        dialog.setPositiveButton(xB.b().a(activity, R.string.common_word_save), (v, which) -> {
            if (validator.b()) {
                String moreBlockName = ReturnMoreblockManager.getMbName(ReturnMoreblockManager.getMbNameWithTypeFromSpec(moreBlock.spec));
                moreBlock.spec = Helper.getText(newName) + moreBlock.spec.substring(moreBlockName.length());

                handleVariables(moreBlock);
                mB.a(activity, newName);
                v.dismiss();
            }
        });
        dialog.setNegativeButton(xB.b().a(activity, R.string.common_word_cancel), (v, which) -> {
            mB.a(activity, newName);
            v.dismiss();
        });
        dialog.show();
    }

    private void copyImageFromCollectionsToProject(String imageName) {
        if (Op.g().b(imageName)) {
            ProjectResourceBean image = Op.g().a(imageName);
            try {
                fileUtil.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + image.resFullName, wq.g() + File.separator + sc_id + File.separator + image.resFullName);
                jC.d(sc_id).b.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void copySoundFromCollectionsToProject(String soundName) {
        if (Qp.g().b(soundName)) {
            ProjectResourceBean a2 = Qp.g().a(soundName);
            try {
                fileUtil.a(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + a2.resFullName, wq.t() + File.separator + sc_id + File.separator + a2.resFullName);
                jC.d(sc_id).c.add(a2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void copyFontFromCollectionsToProject(String fontName) {
        if (Np.g().b(fontName)) {
            ProjectResourceBean font = Np.g().a(fontName);
            try {
                fileUtil.a(wq.a() + File.separator + "font" + File.separator + "data" + File.separator + font.resFullName, wq.d() + File.separator + sc_id + File.separator + font.resFullName);
                jC.d(sc_id).d.add(font);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void maybeAddVariable(int variableType, String variableName) {
        if (toBeAddedVariables == null) {
            toBeAddedVariables = new ArrayList<>();
        }
        for (Pair<Integer, String> variable : jC.a(sc_id).k(activityJavaName)) {
            if (variable.first == variableType && variable.second.equals(variableName)) {
                return;
            }
        }
        boolean alreadyToBeAdded = false;
        for (Pair<Integer, String> toBeAddedVariable : toBeAddedVariables) {
            if (toBeAddedVariable.first == variableType && toBeAddedVariable.second.equals(variableName)) {
                alreadyToBeAdded = true;
                break;
            }
        }
        if (!alreadyToBeAdded) {
            toBeAddedVariables.add(new Pair<>(variableType, variableName));
        }
    }

    private void maybeAddList(int listType, String listName) {
        if (toBeAddedLists == null) {
            toBeAddedLists = new ArrayList<>();
        }
        for (Pair<Integer, String> list : jC.a(sc_id).j(activityJavaName)) {
            if (list.first == listType && list.second.equals(listName)) {
                return;
            }
        }

        boolean alreadyToBeAdded = false;
        for (Pair<Integer, String> toBeAddedList : toBeAddedLists) {
            if (toBeAddedList.first == listType && toBeAddedList.second.equals(listName)) {
                alreadyToBeAdded = true;
                break;
            }
        }
        if (!alreadyToBeAdded) {
            toBeAddedLists.add(new Pair<>(listType, listName));
        }
    }

    private void maybeAddSound(String soundName) {
        if (toBeAddedSounds == null) {
            toBeAddedSounds = new ArrayList<>();
        }
        for (String soundInProjectName : jC.d(sc_id).p()) {
            if (soundInProjectName.equals(soundName)) {
                return;
            }
        }
        ProjectResourceBean sound = Qp.g().a(soundName);
        if (sound != null) {
            boolean alreadyToBeAdded = false;
            for (ProjectResourceBean toBeAddedSound : toBeAddedSounds) {
                if (toBeAddedSound.resName.equals(soundName)) {
                    alreadyToBeAdded = true;
                    break;
                }
            }
            if (!alreadyToBeAdded) {
                toBeAddedSounds.add(sound);
            }
        }
    }

    private void maybeAddFont(String fontName) {
        if (toBeAddedFonts == null) {
            toBeAddedFonts = new ArrayList<>();
        }
        for (String fontInProjectName : jC.d(sc_id).k()) {
            if (fontInProjectName.equals(fontName)) {
                return;
            }
        }
        ProjectResourceBean font = Np.g().a(fontName);
        if (font != null) {
            boolean alreadyToBeAdded = false;
            for (ProjectResourceBean toBeAddedFont : toBeAddedFonts) {
                if (toBeAddedFont.resName.equals(fontName)) {
                    alreadyToBeAdded = true;
                    break;
                }
            }
            if (!alreadyToBeAdded) {
                toBeAddedFonts.add(font);
            }
        }
    }

    private void maybeAddImage(String imageName) {
        if (toBeAddedImages == null) {
            toBeAddedImages = new ArrayList<>();
        }
        for (String imageInProjectName : jC.d(sc_id).m()) {
            if (imageInProjectName.equals(imageName)) {
                return;
            }
        }
        ProjectResourceBean image = Op.g().a(imageName);
        if (image != null) {
            boolean alreadyToBeAdded = false;
            for (ProjectResourceBean toBeAddedImage : toBeAddedImages) {
                if (toBeAddedImage.resName.equals(imageName)) {
                    alreadyToBeAdded = true;
                    break;
                }
            }
            if (!alreadyToBeAdded) {
                toBeAddedImages.add(image);
            }
        }
    }

    public interface Callback {
        void onImportComplete();
    }
}
