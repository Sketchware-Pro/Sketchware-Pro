package com.besome.sketch.editor;

import static mod.SketchwareUtil.getDip;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.BlockCollectionBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.HistoryBlockBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.component.ComponentAddActivity;
import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.editor.logic.LogicTopMenu;
import com.besome.sketch.editor.logic.PaletteBlock;
import com.besome.sketch.editor.logic.PaletteSelector;
import com.besome.sketch.editor.makeblock.MakeBlockActivity;
import com.besome.sketch.editor.manage.ShowBlockCollectionActivity;
import com.besome.sketch.editor.view.ViewDummy;
import com.besome.sketch.editor.view.ViewLogicEditor;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import a.a.a.DB;
import a.a.a.FB;
import a.a.a.Fx;
import a.a.a.GB;
import a.a.a.Lx;
import a.a.a.MA;
import a.a.a.Mp;
import a.a.a.NB;
import a.a.a.Ox;
import a.a.a.Pp;
import a.a.a.Rs;
import a.a.a.Ss;
import a.a.a.Ts;
import a.a.a.Us;
import a.a.a.Vs;
import a.a.a.ZB;
import a.a.a.Zx;
import a.a.a.aB;
import a.a.a.bC;
import a.a.a.eC;
import a.a.a.jC;
import a.a.a.jq;
import a.a.a.kC;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;
import a.a.a.xq;
import a.a.a.yq;
import a.a.a.yy;
import dev.aldi.sayuti.block.ExtraPaletteBlock;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import mod.codeware.HiveAI;
import mod.hasrat.menu.ExtraMenuBean;
import mod.hey.studios.editor.view.IdGenerator;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.moreblock.importer.MoreblockImporterDialog;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.asd.asdforall.AsdAllEditor;
import mod.jbk.editor.manage.MoreblockImporter;
import mod.jbk.util.BlockUtil;

@SuppressLint({"ClickableViewAccessibility", "RtlHardcoded", "SetTextI18n", "DefaultLocale"})
public class LogicEditorActivity extends BaseAppCompatActivity implements View.OnClickListener, Vs, View.OnTouchListener, MoreblockImporterDialog.CallBack {

    private final Handler Z = new Handler();
    private final int[] v = new int[2];
    public ProjectFileBean M;
    public PaletteBlock m;
    public BlockPane o;
    public String B = "";
    public String C = "";
    public String D = "";
    private Vibrator F;
    private LinearLayout J, K;
    private FloatingActionButton L;
    private LogicTopMenu N;
    private LogicEditorDrawer O;
    private ObjectAnimator U, V, ba, ca, fa, ga;
    private ExtraPaletteBlock extraPaletteBlock;
    private ViewLogicEditor n;
    private ViewDummy p;
    private Rs w;
    private float r, q, s, t;
    private int A, S, e, x, y;
    private int T = -30;
    private View Y;
    private boolean G, u, W, X, da, ea, ha, ia;
    private final Runnable aa = this::r;

    private void loadEventBlocks() {
        ArrayList<BlockBean> eventBlocks = jC.a(B).a(M.getJavaName(), C + "_" + D);
        if (eventBlocks != null) {
            if (eventBlocks.size() == 0) {
                e(X);
            }

            boolean needToFindRoot = true;
            HashMap<Integer, Rs> blockIdsAndBlocks = new HashMap<>();
            for (BlockBean next : eventBlocks) {
                if (D.equals("onTextChanged") && next.opCode.equals("getArg") && next.spec.equals("text")) {
                    next.spec = "charSeq";
                }
                Rs b2 = b(next);
                blockIdsAndBlocks.put((Integer) b2.getTag(), b2);
                o.g = Math.max(o.g, (Integer) b2.getTag() + 1);
                o.a(b2, 0, 0);
                b2.setOnTouchListener(this);
                if (needToFindRoot) {
                    o.getRoot().b(b2);
                    needToFindRoot = false;
                }
            }
            for (BlockBean next2 : eventBlocks) {
                Rs block = blockIdsAndBlocks.get(Integer.valueOf(next2.id));
                if (block != null) {
                    Rs subStack1RootBlock;
                    if (next2.subStack1 >= 0 && (subStack1RootBlock = blockIdsAndBlocks.get(next2.subStack1)) != null) {
                        block.e(subStack1RootBlock);
                    }
                    Rs subStack2RootBlock;
                    if (next2.subStack2 >= 0 && (subStack2RootBlock = blockIdsAndBlocks.get(next2.subStack2)) != null) {
                        block.f(subStack2RootBlock);
                    }
                    Rs nextBlock;
                    if (next2.nextBlock >= 0 && (nextBlock = blockIdsAndBlocks.get(next2.nextBlock)) != null) {
                        block.b(nextBlock);
                    }
                    for (int i = 0; i < next2.parameters.size(); i++) {
                        String parameter = next2.parameters.get(i);
                        if (parameter != null && parameter.length() > 0) {
                            if (parameter.charAt(0) == '@') {
                                Rs parameterBlock = blockIdsAndBlocks.get(Integer.valueOf(parameter.substring(1)));
                                if (parameterBlock != null) {
                                    block.a((Ts) block.V.get(i), parameterBlock);
                                }
                            } else {
                                ((Ss) block.V.get(i)).setArgValue(parameter);
                                block.m();
                            }
                        }
                    }
                }
            }
            o.getRoot().k();
            o.b();
        }
    }

    private void redo() {
        if (!u) {
            HistoryBlockBean historyBlockBean = bC.d(B).i(s());
            if (historyBlockBean != null) {
                int actionType = historyBlockBean.getActionType();
                if (actionType == HistoryBlockBean.ACTION_TYPE_ADD) {
                    int[] locationOnScreen = new int[2];
                    o.getLocationOnScreen(locationOnScreen);
                    a(historyBlockBean.getAddedData(), historyBlockBean.getCurrentX() + locationOnScreen[0], historyBlockBean.getCurrentY() + locationOnScreen[1], true);
                    if (historyBlockBean.getCurrentParentData() != null) {
                        a(historyBlockBean.getCurrentParentData(), true);
                    }
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_UPDATE) {
                    a(historyBlockBean.getCurrentUpdateData(), true);
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_REMOVE) {
                    ArrayList<BlockBean> removedData = historyBlockBean.getRemovedData();

                    for (int i = removedData.size() - 1; i >= 0; i--) {
                        o.a(removedData.get(i), false);
                    }
                    if (historyBlockBean.getCurrentParentData() != null) {
                        a(historyBlockBean.getCurrentParentData(), true);
                    }
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_MOVE) {
                    for (BlockBean afterMoveData : historyBlockBean.getAfterMoveData()) {
                        o.a(afterMoveData, true);
                    }

                    int[] locationOnScreen = new int[2];
                    o.getLocationOnScreen(locationOnScreen);
                    a(historyBlockBean.getAfterMoveData(), historyBlockBean.getCurrentX() + locationOnScreen[0], historyBlockBean.getCurrentY() + locationOnScreen[1], true);
                    if (historyBlockBean.getCurrentParentData() != null) {
                        a(historyBlockBean.getCurrentParentData(), true);
                    }

                    if (historyBlockBean.getCurrentOriginalParent() != null) {
                        a(historyBlockBean.getCurrentOriginalParent(), true);
                    }
                }
            }
            invalidateOptionsMenu();
        }
    }

    public void C() {
        invalidateOptionsMenu();
    }

    public final void E() {
        eC a2 = jC.a(B);
        String javaName = M.getJavaName();
        a2.a(javaName, C + "_" + D, o.getBlocks());
    }

    public final void G() {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(R.string.logic_editor_title_add_new_list));
        aBVar.a(R.drawable.add_96_blue);
        View a2 = wB.a(this, R.layout.logic_popup_add_list);
        RadioGroup radioGroup = a2.findViewById(R.id.rg_type);
        EditText editText = a2.findViewById(R.id.ed_input);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(getTranslatedString(R.string.logic_editor_hint_enter_variable_name));
        ((TextView) a2.findViewById(R.id.rb_int)).setText(getTranslatedString(R.string.logic_variable_type_number));
        ((TextView) a2.findViewById(R.id.rb_string)).setText(getTranslatedString(R.string.logic_variable_type_string));
        ((TextView) a2.findViewById(R.id.rb_map)).setText(getTranslatedString(R.string.logic_variable_type_map));
        ZB zb = new ZB(getContext(), a2.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(B).a(M));
        editText.setPrivateImeOptions("defaultInputmode=english;");
        aBVar.a(a2);
        aBVar.b(getTranslatedString(R.string.common_word_add), v -> {
            if (zb.b()) {
                int i = 1;
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId != R.id.rb_int) {
                    if (checkedRadioButtonId == R.id.rb_string) {
                        i = 2;
                    } else if (checkedRadioButtonId == R.id.rb_map) {
                        i = 3;
                    }
                }

                a(i, editText.getText().toString());
                aBVar.dismiss();
            }
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    private void showAddNewVariableDialog() {
        aB dialog = new aB(this);
        dialog.b(getTranslatedString(R.string.logic_editor_title_add_new_variable));
        dialog.a(R.drawable.add_96_blue);

        View customView = wB.a(this, R.layout.logic_popup_add_variable);
        RadioGroup radioGroup = customView.findViewById(R.id.rg_type);
        EditText editText = customView.findViewById(R.id.ed_input);
        ((TextInputLayout) customView.findViewById(R.id.ti_input)).setHint(getTranslatedString(R.string.logic_editor_hint_enter_variable_name));
        editText.setPrivateImeOptions("defaultInputmode=english;");
        ((TextView) customView.findViewById(R.id.rb_boolean)).setText(getTranslatedString(R.string.logic_variable_type_boolean));
        ((TextView) customView.findViewById(R.id.rb_int)).setText(getTranslatedString(R.string.logic_variable_type_number));
        ((TextView) customView.findViewById(R.id.rb_string)).setText(getTranslatedString(R.string.logic_variable_type_string));
        ((TextView) customView.findViewById(R.id.rb_map)).setText(getTranslatedString(R.string.logic_variable_type_map));
        ZB nameValidator = new ZB(getContext(), customView.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(B).a(M));
        dialog.a(customView);
        dialog.b(getTranslatedString(R.string.common_word_add), v -> {
            int variableType = 1;
            if (radioGroup.getCheckedRadioButtonId() == R.id.rb_boolean) {
                variableType = 0;
            } else if (radioGroup.getCheckedRadioButtonId() != R.id.rb_int) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_string) {
                    variableType = 2;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_map) {
                    variableType = 3;
                }
            }

            if (nameValidator.b()) {
                b(variableType, editText.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public final void I() {
        ArrayList<MoreBlockCollectionBean> pa = Pp.h().f();
        new MoreblockImporterDialog(this, pa, this).show();
    }

    public final void J() {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(R.string.logic_editor_title_remove_list));
        aBVar.a(R.drawable.delete_96);
        View a2 = wB.a(this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = a2.findViewById(R.id.rg_content);
        for (Pair<Integer, String> list : jC.a(B).j(M.getJavaName())) {
            viewGroup.addView(e(list.second));
        }
        aBVar.a(a2);
        aBVar.b(getTranslatedString(R.string.common_word_remove), v -> {
            int childCount = viewGroup.getChildCount();
            int i = 0;
            while (i < childCount) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    if (!o.b(radioButton.getText().toString())) {
                        if (!jC.a(B).b(M.getJavaName(), radioButton.getText().toString(), C + "_" + D)) {
                            l(radioButton.getText().toString());
                        }
                    }
                    Toast.makeText(getContext(), getTranslatedString(R.string.logic_editor_message_currently_used_list), Toast.LENGTH_SHORT).show();
                    return;
                }
                i++;
            }
            aBVar.dismiss();
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void K() {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(R.string.logic_editor_title_remove_variable));
        aBVar.a(R.drawable.delete_96);
        View a2 = wB.a(this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = a2.findViewById(R.id.rg_content);
        for (Pair<Integer, String> next : jC.a(B).k(M.getJavaName())) {
            RadioButton e = e(next.second);
            e.setTag(next.first);
            viewGroup.addView(e);
        }
        aBVar.a(a2);
        aBVar.b(getTranslatedString(R.string.common_word_remove), v -> {
            int childCount = viewGroup.getChildCount();
            int i = 0;
            while (i < childCount) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    if (!o.c(radioButton.getText().toString())) {
                        if (!jC.a(B).c(M.getJavaName(), radioButton.getText().toString(), C + "_" + D)) {
                            m(radioButton.getText().toString());
                        }
                    }
                    Toast.makeText(getContext(), getTranslatedString(R.string.logic_editor_message_currently_used_variable), Toast.LENGTH_SHORT).show();
                    return;
                }
                i++;
            }
            aBVar.dismiss();
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public void L() {
        try {
            new Handler().postDelayed(() -> new ProjectSaver(this).execute(), 500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void undo() {
        if (!u) {
            HistoryBlockBean history = bC.d(B).j(s());
            if (history != null) {
                int actionType = history.getActionType();
                if (actionType == HistoryBlockBean.ACTION_TYPE_ADD) {
                    ArrayList<BlockBean> addedData = history.getAddedData();
                    for (int i = addedData.size() - 1; i >= 0; i--) {
                        o.a(addedData.get(i), false);
                    }

                    if (history.getPrevParentData() != null) {
                        history.getPrevParentData().print();
                        a(history.getPrevParentData(), true);
                    }
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_UPDATE) {
                    a(history.getPrevUpdateData(), true);
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_REMOVE) {
                    int[] oLocationOnScreen = new int[2];
                    o.getLocationOnScreen(oLocationOnScreen);
                    a(history.getRemovedData(), history.getCurrentX() + oLocationOnScreen[0], history.getCurrentY() + oLocationOnScreen[1], true);

                    if (history.getPrevParentData() != null) {
                        a(history.getPrevParentData(), true);
                    }
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_MOVE) {
                    for (BlockBean beforeMoveBlock : history.getBeforeMoveData()) {
                        o.a(beforeMoveBlock, true);
                    }

                    int[] oLocationOnScreen = new int[2];
                    o.getLocationOnScreen(oLocationOnScreen);
                    a(history.getBeforeMoveData(), history.getPrevX() + oLocationOnScreen[0], history.getPrevY() + oLocationOnScreen[1], true);

                    if (history.getPrevParentData() != null) {
                        a(history.getPrevParentData(), true);
                    }
                    if (history.getPrevOriginalParent() != null) {
                        a(history.getPrevOriginalParent(), true);
                    }
                }
            }

            invalidateOptionsMenu();
        }
    }

    public Rs a(Rs rs, int i, int i2, boolean z) {
        Rs a2 = o.a(rs, i, i2, z);
        if (!z) {
            a2.setOnTouchListener(this);
        }
        return a2;
    }

    public View a(String str, String str2) {
        Ts a2 = m.a("", str, str2);
        a2.setTag(str2);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
        return a2;
    }

    public final View a(String str, String str2, String str3) {
        Ts a2 = m.a(str, str2, str3);
        a2.setTag(str3);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
        return a2;
    }

    public final View a(String str, String str2, String str3, String str4) {
        Ts a2 = m.a(str, str2, str3, str4);
        a2.setTag(str4);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
        return a2;
    }

    public final LinearLayout a(String str, boolean z) {
        Uri fromFile;
        float a2 = wB.a(this, 1.0f);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (60.0f * a2)));
        linearLayout.setGravity(Gravity.CENTER | Gravity.LEFT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;
        textView.setLayoutParams(layoutParams);
        textView.setText(str);
        linearLayout.addView(textView);
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        int i = (int) (a2 * 48.0f);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        if (!"NONE".equals(str)) {
            if (z) {
                imageView.setImageResource(getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
            } else {
                if (Build.VERSION.SDK_INT >= 24) {
                    fromFile = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", new File(jC.d(B).f(str)));
                } else {
                    fromFile = Uri.fromFile(new File(jC.d(B).f(str)));
                }
                Glide.with(this).load(fromFile).signature(kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(imageView);
            }
            imageView.setBackgroundColor(0xffbdbdbd);
        } else {
            imageView.setBackgroundColor(Color.WHITE);
        }
        linearLayout.addView(imageView);
        return linearLayout;
    }

    public final ArrayList<BlockBean> a(ArrayList<BlockBean> arrayList, int i, int i2, boolean z) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        ArrayList<BlockBean> arrayList2 = new ArrayList<>();
        for (BlockBean next : arrayList) {
            if (next.id != null && !next.id.equals("")) {
                arrayList2.add(next.clone());
            }
        }
        for (BlockBean next2 : arrayList2) {
            if (Integer.parseInt(next2.id) >= 99000000) {
                hashMap.put(Integer.valueOf(next2.id), o.g);
                o.g = o.g + 1;
            } else {
                hashMap.put(Integer.valueOf(next2.id), Integer.valueOf(next2.id));
            }
        }
        int size = arrayList2.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            BlockBean blockBean = arrayList2.get(size);
            if (!a(blockBean)) {
                arrayList2.remove(size);
                hashMap.remove(Integer.valueOf(blockBean.id));
            }
        }
        for (BlockBean block : arrayList2) {
            if (hashMap.containsKey(Integer.valueOf(block.id))) {
                block.id = String.valueOf(hashMap.get(Integer.valueOf(block.id)));
            } else {
                block.id = "";
            }
            for (int j = 0; j < block.parameters.size(); j++) {
                String parameter = block.parameters.get(j);
                if (parameter != null && parameter.length() > 0 && parameter.charAt(0) == '@') {
                    int parameterId = Integer.parseInt(parameter.substring(1));
                    int parameterAsBlockId = hashMap.containsKey(parameterId) ? hashMap.get(parameterId) : 0;
                    if (parameterAsBlockId >= 0) {
                        block.parameters.set(j, '@' + String.valueOf(parameterAsBlockId));
                    } else {
                        block.parameters.set(j, "");
                    }
                }
            }
            if (block.subStack1 >= 0 && hashMap.containsKey(block.subStack1)) {
                block.subStack1 = hashMap.get(block.subStack1);
            }
            if (block.subStack2 >= 0 && hashMap.containsKey(block.subStack2)) {
                block.subStack2 = hashMap.get(block.subStack2);
            }
            if (block.nextBlock >= 0 && hashMap.containsKey(block.nextBlock)) {
                block.nextBlock = hashMap.get(block.nextBlock);
            }
        }
        Rs firstBlock = null;
        for (int j = 0; j < arrayList2.size(); j++) {
            BlockBean blockBean = arrayList2.get(j);
            if (blockBean.id != null && !blockBean.id.equals("")) {
                Rs block = b(blockBean);
                if (j == 0) {
                    firstBlock = block;
                }
                o.a(block, i, i2);
                block.setOnTouchListener(this);
            }
        }
        for (BlockBean block : arrayList2) {
            if (block.id != null && !block.id.equals("")) {
                a(block, false);
            }
        }
        if (firstBlock != null && z) {
            firstBlock.p().k();
            o.b();
        }
        return arrayList2;
    }

    @Override
    public void a(int i, int i2) {
        extraPaletteBlock.setBlock(i, i2);
    }

    public void a(int i, String str) {
        jC.a(B).b(M.getJavaName(), i, str);
        a(1, 0xffcc5b22);
    }

    public final void a(Rs rs) {
        w = null;
        y = -1;
        x = 0;
        int[] iArr = new int[2];
        rs.getLocationOnScreen(iArr);
        Rs rs2 = rs.E;
        if (rs2 != null) {
            w = rs2;
        }
        Rs rs3 = w;
        if (rs3 == null) {
            return;
        }
        if (rs3.ha == (Integer) rs.getTag()) {
            x = 0;
        } else if (w.ia == (Integer) rs.getTag()) {
            x = 2;
        } else if (w.ja == (Integer) rs.getTag()) {
            x = 3;
        } else if (w.V.contains(rs)) {
            x = 5;
            y = w.V.indexOf(rs);
        }
    }

    public void a(Rs rs, float f, float f2) {
        for (View next : rs.V) {
            if ((next instanceof Ss) && next.getX() < f && next.getX() + next.getWidth() > f && next.getY() < f2 && next.getY() + next.getHeight() > f2) {
                new ExtraMenuBean(this).defineMenuSelector((Ss) next);
                return;
            }
        }
    }

    public void a(Ss ss, Object obj) {
        BlockBean clone = ss.E.getBean().clone();
        ss.setArgValue(obj);
        ss.E.m();
        ss.E.p().k();
        ss.E.pa.b();
        bC.d(B).a(s(), clone, ss.E.getBean().clone());
        C();
    }

    public final void pickImage(Ss ss, String str) {
        boolean selectingBackgroundImage = "property_background_resource".equals(str);
        boolean selectingImage = !selectingBackgroundImage && "property_image".equals(str);
        aB dialog = new aB(this);
        if (selectingImage) {
            dialog.b(getTranslatedString(R.string.logic_editor_title_select_image));
        } else if (selectingBackgroundImage) {
            dialog.b(getTranslatedString(R.string.logic_editor_title_select_image_background));
        }

        dialog.a(R.drawable.ic_picture_48dp);
        View customView = wB.a(this, R.layout.property_popup_selector_color);
        RadioGroup radioGroup = customView.findViewById(R.id.rg);
        LinearLayout content = customView.findViewById(R.id.content);
        ArrayList<String> images = jC.d(B).m();
        if (xq.a(B) || xq.b(B)) {
            if (selectingImage) {
                images.add(0, "default_image");
            } else if (selectingBackgroundImage) {
                images.add(0, "NONE");
            }
        }

        for (String image : images) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText("");
            radioButton.setTag(image);
            radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
            radioButton.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    (int) (wB.a(this, 1f) * 60)));

            radioGroup.addView(radioButton);
            if (image.equals(ss.getArgValue())) {
                radioButton.setChecked(true);
            }

            LinearLayout imageLinear = a(image, !((xq.a(B) || xq.b(B)) && !image.equals("default_image") && !"NONE".equals(image)));
            imageLinear.setOnClickListener(v -> {
                RadioButton button = (RadioButton) radioGroup.getChildAt(content.indexOfChild(v));
                button.setChecked(true);
            });
            content.addView(imageLinear);
        }

        dialog.a(customView);
        dialog.b(getTranslatedString(R.string.common_word_save), v -> {
            int childCount = radioGroup.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    a(ss, radioButton.getTag());
                    break;
                }
                i++;
            }
            dialog.dismiss();
        });
        dialog.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public void a(Ss ss, boolean z) {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(z ? R.string.logic_editor_title_enter_number_value : R.string.logic_editor_title_enter_string_value));
        aBVar.a(R.drawable.rename_96_blue);
        View a2 = wB.a(this, R.layout.property_popup_input_text);
        EditText editText = a2.findViewById(R.id.ed_input);
        if (z) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editText.setMaxLines(1);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            editText.setImeOptions(EditorInfo.IME_ACTION_NONE);
        }
        editText.setText(ss.getArgValue().toString());
        aBVar.a(a2);
        aBVar.b(getTranslatedString(R.string.common_word_save), v -> {
            String text = editText.getText().toString();
            emptyStringSetter:
            {
                if (z) {
                    try {
                        double d = Double.parseDouble(text);
                        if (!Double.isNaN(d) && !Double.isInfinite(d)) {
                            break emptyStringSetter;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if (text.length() > 0) {
                    if (text.charAt(0) == '@') {
                        text = " " + text;
                        break emptyStringSetter;
                    }
                }

                text = "";
            }

            a(ss, text);
            aBVar.dismiss();
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void a(BlockBean blockBean, boolean z) {
        Rs block = o.a(blockBean.id);
        if (block != null) {
            block.ia = -1;
            block.ja = -1;
            block.ha = -1;

            for (int i = 0; i < blockBean.parameters.size(); i++) {
                String parameter = blockBean.parameters.get(i);
                if (parameter != null) {
                    if (parameter.length() > 0 && parameter.charAt(0) == '@') {
                        int blockId = Integer.parseInt(parameter.substring(1));
                        if (blockId > 0) {
                            Rs parameterBlock = o.a(blockId);
                            if (parameterBlock != null) {
                                block.a((Ts) block.V.get(i), parameterBlock);
                            }
                        }
                    } else {
                        if (block.V.get(i) instanceof Ss) {
                            Ss ss = (Ss) block.V.get(i);
                            String javaName = M.getJavaName();
                            String xmlName = M.getXmlName();
                            if (D.equals("onBindCustomView")) {
                                var eC = jC.a(B);
                                var view = eC.c(xmlName, C);
                                if (view == null) {
                                    // Event is of a Drawer View
                                    view = eC.c("_drawer_" + xmlName, C);
                                }
                                String customView = view.customView;
                                if (customView != null) {
                                    xmlName = ProjectFileBean.getXmlName(customView);
                                }
                            }

                            if (parameter.length() > 0) {
                                if (ss.b.equals("m")) {
                                    switch (ss.c) {
                                        case "varInt":
                                            jC.a(B).f(javaName, ExtraMenuBean.VARIABLE_TYPE_NUMBER, parameter);
                                            break;

                                        case "varBool":
                                            jC.a(B).f(javaName, ExtraMenuBean.VARIABLE_TYPE_BOOLEAN, parameter);
                                            break;

                                        case "varStr":
                                            jC.a(B).f(javaName, ExtraMenuBean.VARIABLE_TYPE_STRING, parameter);
                                            break;

                                        case "listInt":
                                            jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_NUMBER, parameter);
                                            break;

                                        case "listStr":
                                            jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_STRING, parameter);
                                            break;

                                        case "listMap":
                                            jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_MAP, parameter);
                                            break;

                                        case "list":
                                            boolean b = jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_NUMBER, parameter);
                                            if (!b) {
                                                b = jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_STRING, parameter);
                                            }

                                            if (!b) {
                                                jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_MAP, parameter);
                                            }
                                            break;

                                        case "view":
                                            jC.a(B).h(xmlName, parameter);
                                            break;

                                        case "textview":
                                            jC.a(B).g(xmlName, parameter);
                                            break;

                                        case "checkbox":
                                            jC.a(B).e(xmlName, parameter);
                                            break;

                                        case "imageview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_IMAGEVIEW, parameter);
                                            break;

                                        case "seekbar":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_SEEKBAR, parameter);
                                            break;

                                        case "calendarview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW, parameter);
                                            break;

                                        case "adview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_ADVIEW, parameter);
                                            break;

                                        case "listview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_LISTVIEW, parameter);
                                            break;

                                        case "spinner":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_SPINNER, parameter);
                                            break;

                                        case "webview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_WEBVIEW, parameter);
                                            break;

                                        case "switch":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_SWITCH, parameter);
                                            break;

                                        case "progressbar":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR, parameter);
                                            break;

                                        case "mapview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_MAPVIEW, parameter);
                                            break;

                                        case "intent":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_INTENT, parameter);
                                            break;

                                        case "file":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_SHAREDPREF, parameter);
                                            break;

                                        case "calendar":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_CALENDAR, parameter);
                                            break;

                                        case "timer":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_TIMERTASK, parameter);
                                            break;

                                        case "vibrator":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_VIBRATOR, parameter);
                                            break;

                                        case "dialog":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_DIALOG, parameter);
                                            break;

                                        case "mediaplayer":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_MEDIAPLAYER, parameter);
                                            break;

                                        case "soundpool":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_SOUNDPOOL, parameter);
                                            break;

                                        case "objectanimator":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_OBJECTANIMATOR, parameter);
                                            break;

                                        case "firebase":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_FIREBASE, parameter);
                                            break;

                                        case "firebaseauth":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH, parameter);
                                            break;

                                        case "firebasestorage":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE, parameter);
                                            break;

                                        case "gyroscope":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_GYROSCOPE, parameter);
                                            break;

                                        case "interstitialad":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD, parameter);
                                            break;

                                        case "requestnetwork":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK, parameter);
                                            break;

                                        case "texttospeech":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_TEXT_TO_SPEECH, parameter);
                                            break;

                                        case "speechtotext":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT, parameter);
                                            break;

                                        case "bluetoothconnect":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT, parameter);
                                            break;

                                        case "locationmanager":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER, parameter);
                                            break;

                                        case "resource_bg":
                                        case "resource":
                                            for (String str : jC.d(B).m()) {
                                                // Like this in vanilla Sketchware. Don't ask me why.
                                                //noinspection StatementWithEmptyBody
                                                if (parameter.equals(str)) {
                                                }
                                            }
                                            break;

                                        case "activity":
                                            for (String str : jC.b(B).d()) {
                                                // Like this in vanilla Sketchware. Don't ask me why.
                                                //noinspection StatementWithEmptyBody
                                                if (parameter.equals(str.substring(str.indexOf(".java")))) {
                                                }
                                            }
                                            break;

                                        case "sound":
                                            for (String str : jC.d(B).p()) {
                                                // Like this in vanilla Sketchware. Don't ask me why.
                                                //noinspection StatementWithEmptyBody
                                                if (parameter.equals(str)) {
                                                }
                                            }
                                            break;

                                        case "videoad":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_REWARDED_VIDEO_AD, parameter);
                                            break;

                                        case "progressdialog":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_PROGRESS_DIALOG, parameter);
                                            break;

                                        case "datepickerdialog":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_DATE_PICKER_DIALOG, parameter);
                                            break;

                                        case "timepickerdialog":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_TIME_PICKER_DIALOG, parameter);
                                            break;

                                        case "notification":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_NOTIFICATION, parameter);
                                            break;

                                        case "radiobutton":
                                            jC.a(B).g(xmlName, 19, parameter);
                                            break;

                                        case "ratingbar":
                                            jC.a(B).g(xmlName, 20, parameter);
                                            break;

                                        case "videoview":
                                            jC.a(B).g(xmlName, 21, parameter);
                                            break;

                                        case "searchview":
                                            jC.a(B).g(xmlName, 22, parameter);
                                            break;

                                        case "actv":
                                            jC.a(B).g(xmlName, 23, parameter);
                                            break;

                                        case "mactv":
                                            jC.a(B).g(xmlName, 24, parameter);
                                            break;

                                        case "gridview":
                                            jC.a(B).g(xmlName, 25, parameter);
                                            break;

                                        case "tablayout":
                                            jC.a(B).g(xmlName, 30, parameter);
                                            break;

                                        case "viewpager":
                                            jC.a(B).g(xmlName, 31, parameter);
                                            break;

                                        case "bottomnavigation":
                                            jC.a(B).g(xmlName, 32, parameter);
                                            break;

                                        case "badgeview":
                                            jC.a(B).g(xmlName, 33, parameter);
                                            break;

                                        case "patternview":
                                            jC.a(B).g(xmlName, 34, parameter);
                                            break;

                                        case "sidebar":
                                            jC.a(B).g(xmlName, 35, parameter);
                                            break;

                                        default:
                                            extraPaletteBlock.e(ss.c, parameter);
                                    }
                                }
                            }

                            ss.setArgValue(parameter);
                            block.m();
                        }
                    }
                }
            }

            int subStack1RootBlockId = blockBean.subStack1;
            if (subStack1RootBlockId >= 0) {
                Rs subStack1RootBlock = o.a(subStack1RootBlockId);
                if (subStack1RootBlock != null) {
                    block.e(subStack1RootBlock);
                }
            }

            int subStack2RootBlockId = blockBean.subStack2;
            if (subStack2RootBlockId >= 0) {
                Rs subStack2RootBlock = o.a(subStack2RootBlockId);
                if (subStack2RootBlock != null) {
                    block.f(subStack2RootBlock);
                }
            }

            int nextBlockId = blockBean.nextBlock;
            if (nextBlockId >= 0) {
                Rs nextBlock = o.a(nextBlockId);
                if (nextBlock != null) {
                    block.b(nextBlock);
                }
            }

            block.m();
            if (z) {
                block.p().k();
                o.b();
            }
        }
    }

    public final void a(String str, int i) {
        m.a(str, i);
    }

    public final void a(String str, Rs rs) {
        ArrayList<String> arrayList;
        ArrayList<Rs> allChildren = rs.getAllChildren();
        ArrayList<BlockBean> arrayList2 = new ArrayList<>();
        for (Rs child : allChildren) {
            BlockBean blockBean = new BlockBean();
            BlockBean bean = child.getBean();
            blockBean.copy(bean);
            blockBean.id = String.format("99%06d", Integer.valueOf(bean.id));
            int i = bean.subStack1;
            if (i > 0) {
                blockBean.subStack1 = i + 99000000;
            }
            int i2 = bean.subStack2;
            if (i2 > 0) {
                blockBean.subStack2 = i2 + 99000000;
            }
            int i3 = bean.nextBlock;
            if (i3 > 0) {
                blockBean.nextBlock = i3 + 99000000;
            }
            blockBean.parameters.clear();
            for (String next : bean.parameters) {
                if (next.length() <= 1 || next.charAt(0) != '@') {
                    arrayList = blockBean.parameters;
                } else {
                    String format = String.format("99%06d", Integer.valueOf(next.substring(1)));
                    arrayList = blockBean.parameters;
                    next = '@' + format;
                }
                arrayList.add(next);
            }
            arrayList2.add(blockBean);
        }
        try {
            Mp.h().a(str, arrayList2, true);
            O.a(str, arrayList2).setOnTouchListener(this);
        } catch (Exception e) {
            // The bytecode is lying. Checked exceptions suck.
            //noinspection ConstantConditions
            if (e instanceof yy) {
                e.printStackTrace();
            } else {
                throw e;
            }
        }
    }

    public final void a(boolean z) {
        N.a(z);
    }

    public final boolean a(float f, float f2) {
        return N.a(f, f2);
    }

    public final boolean a(BlockBean blockBean) {
        if (blockBean.opCode.equals("getArg")) {
            return true;
        }
        if (blockBean.opCode.equals("definedFunc")) {
            Iterator<Pair<String, String>> it = jC.a(B).i(M.getJavaName()).iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (blockBean.spec.equals(ReturnMoreblockManager.getMbName(it.next().second))) {
                    z = true;
                }
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public Rs b(BlockBean blockBean) {
        return new Rs(this, Integer.parseInt(blockBean.id), blockBean.spec, blockBean.type, blockBean.typeName, blockBean.opCode);
    }

    private RadioButton getFontRadioButton(String fontName) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText("");
        radioButton.setTag(fontName);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (wB.a(this, 1.0f) * 60.0f));
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public void b(int i, String str) {
        jC.a(B).c(M.getJavaName(), i, str);
        a(0, 0xffee7d16);
    }

    public void b(Rs rs) {
        o.b(rs);
    }

    public final void b(Ss ss) {
        View a2 = wB.a(this, R.layout.color_picker);
        a2.setAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in));
        Zx zx = new Zx(a2, this, (ss.getArgValue() == null || ss.getArgValue().toString().length() <= 0 || ss.getArgValue().toString().indexOf("0xFF") != 0) ? 0 : Color.parseColor(ss.getArgValue().toString().replace("0xFF", "#")), true, false);
        zx.a(i -> {
            if (i == 0) {
                a(ss, "Color.TRANSPARENT");
            } else {
                a(ss, String.format("0x%08X", i & (Color.WHITE)));
            }
        });
        zx.setAnimationStyle(R.anim.abc_fade_in);
        zx.showAtLocation(a2, Gravity.CENTER, 0, 0);
    }

    public final void b(String str, String str2) {
        TextView a2 = m.a(str);
        a2.setTag(str2);
        a2.setSoundEffectsEnabled(true);
        a2.setOnClickListener(this);
    }

    public final void b(String str, String str2, View.OnClickListener onClickListener) {
        TextView a2 = m.a(str);
        a2.setTag(str2);
        a2.setSoundEffectsEnabled(true);
        a2.setOnClickListener(onClickListener);
    }

    public final void b(boolean z) {
        N.b(z);
    }

    public final boolean b(float f, float f2) {
        return N.b(f, f2);
    }

    public final void c(Rs rs) {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(R.string.logic_block_favorites_save_title));
        aBVar.a(R.drawable.ic_bookmark_red_48dp);
        View a2 = wB.a(this, R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(getTranslatedString(R.string.logic_block_favorites_save_guide));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        NB nb = new NB(this, a2.findViewById(R.id.ti_input), Mp.h().g());
        aBVar.a(a2);
        aBVar.b(getTranslatedString(R.string.common_word_save), v -> {
            if (nb.b()) {
                a(editText.getText().toString(), rs);
                aBVar.dismiss();
            }
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public void c(Ss ss) {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(R.string.logic_editor_title_enter_string_value));
        aBVar.a(R.drawable.rename_96_blue);
        View a2 = wB.a(this, R.layout.property_popup_input_text);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(getTranslatedString(R.string.property_hint_enter_value));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setSingleLine(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setText(ss.getArgValue().toString());
        aBVar.a(a2);
        aBVar.b(getTranslatedString(R.string.common_word_save), v -> {
            a(ss, editText.getText().toString());
            aBVar.dismiss();
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public void c(String str, String str2) {
        jC.a(B).a(M.getJavaName(), str, str2);
        a(8, 0xff8a55d7);
    }

    public final void c(boolean z) {
        N.c(z);
    }

    public final boolean c(float f, float f2) {
        return N.c(f, f2);
    }

    private LinearLayout getFontPreview(String fontName) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (wB.a(this, 1.0f) * 60.0f)));
        linearLayout.setGravity(Gravity.CENTER | Gravity.LEFT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView name = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;
        name.setLayoutParams(layoutParams);
        name.setText(fontName);
        linearLayout.addView(name);
        TextView preview = new TextView(this);
        preview.setLayoutParams(layoutParams);
        preview.setText("Preview");

        Typeface typeface;
        if (fontName.equalsIgnoreCase("default_font")) {
            typeface = Typeface.DEFAULT;
        } else {
            try {
                typeface = Typeface.createFromFile(jC.d(B).d(fontName));
            } catch (RuntimeException e) {
                typeface = Typeface.DEFAULT;
                preview.setText("Couldn't load font");
            }
        }

        preview.setTypeface(typeface);
        linearLayout.addView(preview);
        return linearLayout;
    }

    public final RadioButton d(String str, String str2) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(str + " : " + str2);
        radioButton.setTag(str2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (wB.a(this, 1.0f) * 40.0f));
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public final void d(Ss ss) {
        aB dialog = new aB(this);
        dialog.b(getTranslatedString(R.string.logic_editor_title_select_font));
        dialog.a(R.drawable.abc_96_color);

        View customView = wB.a(this, R.layout.property_popup_selector_color);
        RadioGroup radioGroup = customView.findViewById(R.id.rg);
        LinearLayout linearLayout = customView.findViewById(R.id.content);
        ArrayList<String> fontNames = jC.d(B).k();
        if (xq.a(B) || xq.b(B)) {
            fontNames.add(0, "default_font");
        }
        for (String fontName : fontNames) {
            RadioButton font = getFontRadioButton(fontName);
            radioGroup.addView(font);
            if (fontName.equals(ss.getArgValue())) {
                font.setChecked(true);
            }
            LinearLayout fontPreview = getFontPreview(fontName);
            fontPreview.setOnClickListener(v -> font.setChecked(true));
            linearLayout.addView(fontPreview);
        }

        dialog.a(customView);
        dialog.b(getTranslatedString(R.string.common_word_select), v -> {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    a(ss, radioButton.getTag());
                    break;
                }
            }
            dialog.dismiss();
        });
        dialog.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public final void d(boolean z) {
        N.d(z);
    }

    public final boolean d(float f, float f2) {
        return N.d(f, f2);
    }

    public final RadioButton e(String str) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) wB.a(getContext(), 4.0f);
        layoutParams.bottomMargin = (int) wB.a(getContext(), 4.0f);
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public void e(Ss ss) {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(R.string.logic_editor_title_enter_data_value));
        aBVar.a(R.drawable.rename_96_blue);
        View a2 = wB.a(this, R.layout.property_popup_input_intent_data);
        ((TextView) a2.findViewById(R.id.tv_desc_intent_usage)).setText(getTranslatedString(R.string.property_description_component_intent_usage));
        EditText editText = a2.findViewById(R.id.ed_input);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(getTranslatedString(R.string.property_hint_enter_value));
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setText(ss.getArgValue().toString());
        aBVar.a(a2);
        aBVar.b(getTranslatedString(R.string.common_word_save), v -> {
            a(ss, editText.getText().toString());
            aBVar.dismiss();
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public void e(boolean z) {
        ObjectAnimator objectAnimator;
        if (!W) {
            h(getResources().getConfiguration().orientation);
        }
        if (X == z) {
            return;
        }
        X = z;
        n();
        if (z) {
            g(false);
            objectAnimator = U;
        } else {
            objectAnimator = V;
        }
        objectAnimator.start();
        f(getResources().getConfiguration().orientation);
    }

    public final void f(int i) {
        LinearLayout.LayoutParams layoutParams;
        int a2;
        int i2 = ViewGroup.LayoutParams.MATCH_PARENT;
        if (X) {
            int i3 = getResources().getDisplayMetrics().widthPixels;
            int i4 = getResources().getDisplayMetrics().heightPixels;
            if (i3 <= i4) {
                i3 = i4;
            }
            if (2 == i) {
                i2 = i3 - ((int) wB.a(this, 320.0f));
                a2 = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                a2 = ((i3 - GB.a(getContext())) - GB.f(getContext())) - ((int) wB.a(this, 240.0f));
            }
            layoutParams = new LinearLayout.LayoutParams(i2, a2);
        } else {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        n.setLayoutParams(layoutParams);
        n.requestLayout();
    }

    public void f(Ss ss) {
        aB dialog = new aB(this);
        View a2 = wB.a(this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = a2.findViewById(R.id.rg_content);
        String xmlName = M.getXmlName();
        String customViewName;
        if (D.equals("onBindCustomView")) {
            var eC = jC.a(B);
            var view = eC.c(xmlName, C);
            if (view == null) {
                // Event is of a Drawer View
                view = eC.c("_drawer_" + xmlName, C);
            }
            if ((customViewName = view.customView) != null) {
                xmlName = ProjectFileBean.getXmlName(customViewName);
            }
        }
        dialog.b(getTranslatedString(R.string.logic_editor_title_select_view));
        ArrayList<ViewBean> views = jC.a(B).d(xmlName);
        for (int i = 0, viewsSize = views.size(); i < viewsSize; i++) {
            ViewBean viewBean = views.get(i);
            String convert = viewBean.convert;
            String typeName = convert.isEmpty() ? ViewBean.getViewTypeName(viewBean.type) : IdGenerator.getLastPath(convert);
            if (!convert.equals("include")) {
                Set<String> toNotAdd = new Ox(new jq(), M).readAttributesToReplace(viewBean);
                if (!toNotAdd.contains("android:id")) {
                    viewGroup.addView(d(typeName, viewBean.id));
                }
            }
        }
        int childCount = viewGroup.getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
            if (ss.getArgValue().toString().equals(radioButton.getTag().toString())) {
                radioButton.setChecked(true);
                break;
            }
            i++;
        }
        dialog.a(a2);
        dialog.configureDefaultButton("Code Editor", v -> {
            AsdAllEditor editor = new AsdAllEditor(this);
            editor.setCon(ss.getArgValue().toString());
            editor.show();
            editor.saveLis(this, ss, editor);
            editor.cancelLis(this, editor);
            dialog.dismiss();
        });
        dialog.b(getTranslatedString(R.string.common_word_select), v -> {
            int childCount2 = viewGroup.getChildCount();
            int j = 0;
            while (true) {
                if (j >= childCount2) {
                    break;
                }
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(j);
                if (radioButton.isChecked()) {
                    a(ss, radioButton.getTag());
                    break;
                }
                j++;
            }
            dialog.dismiss();
        });
        dialog.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public final void f(boolean z) {
        N.e(z);
    }

    @Override
    public void finish() {
        bC.d(B).b(s());
        super.finish();
    }

    private Context getContext() {
        return getApplicationContext();
    }

    public final void g(int i) {
        RelativeLayout.LayoutParams layoutParams;
        int i2;
        if (2 == i) {
            K.setLayoutParams(new LinearLayout.LayoutParams((int) wB.a(this, 320.0f), ViewGroup.LayoutParams.MATCH_PARENT));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.gravity = Gravity.CENTER | Gravity.BOTTOM;
            int dimension = (int) getResources().getDimension(R.dimen.action_button_margin);
            layoutParams2.setMargins(dimension, dimension, dimension, dimension);
            L.setLayoutParams(layoutParams2);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.topMargin = GB.a(getContext());
            i2 = LinearLayout.HORIZONTAL;
        } else {
            K.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) wB.a(this, 240.0f)));
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams3.gravity = Gravity.CENTER | Gravity.RIGHT;
            int dimension2 = (int) getResources().getDimension(R.dimen.action_button_margin);
            layoutParams3.setMargins(dimension2, dimension2, dimension2, dimension2);
            L.setLayoutParams(layoutParams3);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            i2 = LinearLayout.VERTICAL;
        }
        J.setOrientation(i2);
        J.setLayoutParams(layoutParams);
        h(i);
        f(i);
    }

    public final void g(boolean z) {
        if (!ha) {
            t();
        }
        if (ia != z) {
            ia = z;
            l();
            (z ? fa : ga).start();
        }
    }

    public final void h(int i) {
        label24:
        {
            label23:
            {
                boolean var2 = X;
                if (2 == i) {
                    if (!var2) {
                        J.setTranslationX((float) ((int) wB.a(this, 320.0F)));
                        break label23;
                    }
                } else if (!var2) {
                    J.setTranslationX(0.0F);
                    J.setTranslationY((float) ((int) wB.a(this, 240.0F)));
                    break label24;
                }

                J.setTranslationX(0.0F);
            }

            J.setTranslationY(0.0F);
        }

        ObjectAnimator var3;
        if (2 == i) {
            U = ObjectAnimator.ofFloat(J, "TranslationX", 0.0F);
            var3 = ObjectAnimator.ofFloat(J, "TranslationX", (float) ((int) wB.a(this, 320.0F)));
        } else {
            U = ObjectAnimator.ofFloat(J, "TranslationY", 0.0F);
            var3 = ObjectAnimator.ofFloat(J, "TranslationY", (float) ((int) wB.a(this, 240.0F)));
        }

        V = var3;
        U.setDuration(500L);
        U.setInterpolator(new DecelerateInterpolator());
        V.setDuration(300L);
        V.setInterpolator(new DecelerateInterpolator());
        W = true;
    }

    public final void h(Ss ss) {
        aB dialog = new aB(this);
        dialog.b(getTranslatedString(R.string.logic_editor_title_select_sound));
        dialog.a(R.drawable.music_48);

        View customView = wB.a(this, R.layout.property_popup_selector_single);
        RadioGroup radioGroup = customView.findViewById(R.id.rg_content);
        SoundPool soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .build();
        soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> {
            if (soundPool1 != null) {
                soundPool1.play(sampleId, 1, 1, 1, 0, 1);
            }
        });

        for (String soundName : jC.d(B).p()) {
            RadioButton sound = e(soundName);
            radioGroup.addView(sound);
            if (soundName.equals(ss.getArgValue())) {
                sound.setChecked(true);
            }
            sound.setOnClickListener(v -> soundPool.load(jC.d(B).i(sound.getText().toString()), 1));
        }
        dialog.a(customView);
        dialog.b(getTranslatedString(R.string.common_word_select), v -> {
            RadioButton checkedRadioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            a(ss, checkedRadioButton.getText().toString());
            dialog.dismiss();
        });
        dialog.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public final void h(boolean z) {
        N.b(false);
        N.a(false);
        N.d(false);
        N.c(false);
        if (!da) {
            x();
        }
        if (ea == z) {
            return;
        }
        ea = z;
        m();
        (z ? ba : ca).start();
    }

    public final void i(Ss ss) {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(R.string.logic_editor_title_select_typeface));
        aBVar.a(R.drawable.abc_96_color);
        View a3 = wB.a(this, R.layout.property_popup_selector_single);
        RadioGroup radioGroup = a3.findViewById(R.id.rg_content);
        for (Pair<Integer, String> pair : sq.a("property_text_style")) {
            RadioButton e = e(pair.second);
            radioGroup.addView(e);
            if (pair.second.equals(ss.getArgValue())) {
                e.setChecked(true);
            }
        }
        aBVar.a(a3);
        aBVar.b(getTranslatedString(R.string.common_word_save), v -> {
            int childCount = radioGroup.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    a(ss, radioButton.getText().toString());
                    break;
                }
                i++;
            }
            aBVar.dismiss();
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void l() {
        if (fa.isRunning()) {
            fa.cancel();
        }
        if (ga.isRunning()) {
            ga.cancel();
        }
    }

    public void l(String str) {
        jC.a(B).o(M.getJavaName(), str);
        a(1, 0xffcc5b22);
    }

    public final void m() {
        if (ba.isRunning()) {
            ba.cancel();
        }
        if (ca.isRunning()) {
            ca.cancel();
        }
    }

    public void m(String str) {
        jC.a(B).p(M.getJavaName(), str);
        a(0, 0xffee7d16);
    }

    public final void n() {
        if (U.isRunning()) {
            U.cancel();
        }
        if (V.isRunning()) {
            V.cancel();
        }
    }

    public final void n(String str) {
        aB aBVar = new aB(this);
        aBVar.b(getTranslatedString(R.string.logic_block_favorites_delete_title));
        aBVar.a(R.drawable.high_priority_96_red);
        aBVar.a(getTranslatedString(R.string.logic_block_favorites_delete_message));
        aBVar.b(getTranslatedString(R.string.common_word_delete), v -> {
            Mp.h().a(str, true);
            O.a(str);
            aBVar.dismiss();
        });
        aBVar.a(getTranslatedString(R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public void o(String str) {
        Intent intent = new Intent(getContext(), ShowBlockCollectionActivity.class);
        intent.putExtra("block_name", str);
        startActivity(intent);
    }

    public boolean o() {
        int childCount = o.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = o.getChildAt(i);
            if (childAt instanceof Rs) {
                ((Rs) childAt).U.equals("Forever");
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 222) {
                c(data.getStringExtra("block_name"), data.getStringExtra("block_spec"));
            } else if (requestCode == 224) {
                a(7, 0xff2ca5e2);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (ia) {
            g(false);
            return;
        }
        if (X) {
            e(false);
            return;
        }
        k();
        if (!o() || !p()) {
            return;
        }
        L();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            Object tag = v.getTag();
            if (tag != null) {
                if (tag.equals("variableAdd")) {
                    showAddNewVariableDialog();
                } else if (tag.equals("variableRemove")) {
                    K();
                } else if (tag.equals("listAdd")) {
                    G();
                } else if (tag.equals("listRemove")) {
                    J();
                } else if (tag.equals("blockAdd")) {
                    Intent intent = new Intent(getContext(), MakeBlockActivity.class);
                    intent.putExtra("sc_id", B);
                    intent.putExtra("project_file", M);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, 222);
                } else if (tag.equals("componentAdd")) {
                    Intent intent = new Intent(getContext(), ComponentAddActivity.class);
                    intent.putExtra("sc_id", B);
                    intent.putExtra("project_file", M);
                    intent.putExtra("filename", M.getJavaName());
                    startActivityForResult(intent, 224);
                } else if (tag.equals("blockImport")) {
                    I();
                }
            }
            int id = v.getId();
            if (id == R.id.btn_accept) {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            } else if (id == R.id.btn_cancel) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        g(configuration.orientation);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic_editor);
        if (!super.j()) {
            finish();
        }
        Parcelable parcelable;
        if (savedInstanceState == null) {
            B = getIntent().getStringExtra("sc_id");
            C = getIntent().getStringExtra("id");
            D = getIntent().getStringExtra("event");
            parcelable = getIntent().getParcelableExtra("project_file");
        } else {
            B = savedInstanceState.getString("sc_id");
            C = savedInstanceState.getString("id");
            D = savedInstanceState.getString("event");
            parcelable = savedInstanceState.getParcelable("project_file");
        }
        M = (ProjectFileBean) parcelable;
        DB h = new DB(this, "P1");
        T = (int) wB.a(getBaseContext(), (float) T);
        Toolbar k = findViewById(R.id.toolbar);
        setSupportActionBar(k);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        k.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        k.setPopupTheme(R.style.ThemeOverlay_ToolbarMenu);
        G = new DB(getContext(), "P12").a("P12I0", true);
        A = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        F = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        String stringExtra = getIntent().getStringExtra("event_text");
        ActionBar d;
        if (C.equals("onCreate")) {
            d = getSupportActionBar();
        } else if (C.equals("_fab")) {
            d = getSupportActionBar();
            stringExtra = "fab : " + stringExtra;
        } else {
            d = getSupportActionBar();
            stringExtra = ReturnMoreblockManager.getMbName(C) + " : " + stringExtra;
        }
        d.setTitle(stringExtra);
        PaletteSelector l = findViewById(R.id.palette_selector);
        l.setOnBlockCategorySelectListener(this);
        m = findViewById(R.id.palette_block);
        p = findViewById(R.id.dummy);
        n = findViewById(R.id.editor);
        o = n.getBlockPane();
        J = findViewById(R.id.layout_palette);
        K = findViewById(R.id.area_palette);
        L = findViewById(R.id.fab_toggle_palette);
        L.setOnClickListener(v -> e(!X));
        N = findViewById(R.id.top_menu);
        O = findViewById(R.id.right_drawer);
        extraPaletteBlock = new ExtraPaletteBlock(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logic_menu, menu);
        menu.findItem(R.id.menu_logic_redo).setEnabled(false);
        menu.findItem(R.id.menu_logic_undo).setEnabled(false);
        if (M == null) {
            return true;
        }
        if (bC.d(B).g(s())) {
            menu.findItem(R.id.menu_logic_redo).setIcon(R.drawable.ic_redo_white_48dp);
            menu.findItem(R.id.menu_logic_redo).setEnabled(true);
        } else {
            menu.findItem(R.id.menu_logic_redo).setIcon(R.drawable.ic_redo_grey_48dp);
            menu.findItem(R.id.menu_logic_redo).setEnabled(false);
        }
        if (bC.d(B).h(s())) {
            menu.findItem(R.id.menu_logic_undo).setIcon(R.drawable.ic_undo_white_48dp);
            menu.findItem(R.id.menu_logic_undo).setEnabled(true);
        } else {
            menu.findItem(R.id.menu_logic_undo).setIcon(R.drawable.ic_undo_grey_48dp);
            menu.findItem(R.id.menu_logic_undo).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.menu_block_helper) {
            e(false);
            g(!ia);
        } else if (itemId == R.id.menu_logic_redo) {
            redo();
        } else if (itemId == R.id.menu_logic_undo) {
            undo();
        } else if (itemId == R.id.menu_logic_showsource) {
            showSourceCode();
        } else if(itemId == R.id.hive_generate_code){
            showGenerateCodeDialog();
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void showGenerateCodeDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Generate code")
                .setPositiveButton(R.string.common_word_close, null)
                .create();

        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.hive_generate_code_layout,null);

        final ImageButton generateButton = dialogView.findViewById(R.id.generateButton);
        final ProgressBar progressBar = dialogView.findViewById(R.id.progressBar);
        final EditText question = dialogView.findViewById(R.id.question);

        dialog.setView(dialogView,
                (int) getDip(24),
                (int) getDip(8),
                (int) getDip(24),
                (int) getDip(8));

        final CodeEditor codeEditor = dialogView.findViewById(R.id.codeview);
        codeEditor.setColorScheme(new EditorColorScheme());
        codeEditor.setEditable(false);
        codeEditor.setEditorLanguage(new JavaLanguage());
        codeEditor.setText(Lx.j("code", false));
        codeEditor.setTextSize(12);
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setWordwrap(false);
        codeEditor.getComponent(Magnifier.class).setWithinEditorForcibly(true);

        dialogView.findViewById(R.id.generateButton).setOnClickListener(v -> {
            if (question.getText().toString().trim().isEmpty()){
                question.setError("Please enter question");
            }else{
                generateButton.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                HiveAI hiveAI = new HiveAI();
                hiveAI.generate(question.getText().toString(), resultText -> {
                    runOnUiThread(() -> {
                        codeEditor.setText(Lx.j(resultText, false));
                        codeEditor.setVisibility(View.VISIBLE);
                        generateButton.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    });
                });
            }

        });

        dialog.show();
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);

        String title;
        if (D.equals("moreBlock")) {
            title = getTranslatedString(R.string.root_spec_common_define) + " " + ReturnMoreblockManager.getLogicEditorTitle(jC.a(B).b(M.getJavaName(), C));
        } else if (C.equals("_fab")) {
            title = xB.b().a(getContext(), "fab", D);
        } else {
            title = xB.b().a(getContext(), C, D);
        }
        String e1 = title;

        o.a(e1, D);

        ArrayList<String> spec = FB.c(e1);
        int blockId = 0;
        for (int i = 0; i < spec.size(); i++) {
            String specBit = spec.get(i);
            if (specBit.charAt(0) == '%') {
                Rs block = BlockUtil.getVariableBlock(getContext(), blockId + 1, specBit, "getArg");
                if (block != null) {
                    block.setBlockType(1);
                    o.addView(block);
                    o.getRoot().a((Ts) o.getRoot().V.get(blockId), block);
                    block.setOnTouchListener(this);
                    blockId++;
                }
            }
        }

        o.getRoot().k();
        g(getResources().getConfiguration().orientation);
        a(0, 0xffee7d16);
        loadEventBlocks();
        z();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", B);
        bundle.putString("id", C);
        bundle.putString("event", D);
        bundle.putParcelable("project_file", M);
        super.onSaveInstanceState(bundle);
        ArrayList<BlockBean> blocks = o.getBlocks();
        eC a2 = jC.a(B);
        String javaName = M.getJavaName();
        a2.a(javaName, C + "_" + D, blocks);
        jC.a(B).k();
    }

    @Override
    public void onSelected(MoreBlockCollectionBean moreBlockCollectionBean) {
        new MoreblockImporter(this, B, M).importMoreblock(moreBlockCollectionBean, () -> a(8, 0xff8a55d7));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (event.getPointerId(event.getActionIndex()) > 0) {
            return true;
        }
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            u = false;
            Z.postDelayed(aa, ViewConfiguration.getLongPressTimeout() / 2);
            int[] locationOnScreen = new int[2];
            v.getLocationOnScreen(locationOnScreen);
            s = locationOnScreen[0];
            t = locationOnScreen[1];
            q = event.getRawX();
            r = event.getRawY();
            Y = v;
            return true;
        }
        if (actionMasked == MotionEvent.ACTION_MOVE) {
            if (!u) {
                if (Math.abs(q - s - event.getX()) >= A || Math.abs(r - t - event.getY()) >= A) {
                    Y = null;
                    Z.removeCallbacks(aa);
                }
                return false;
            }
            Z.removeCallbacks(aa);
            float rawX = event.getRawX();
            float rawY = event.getRawY();
            p.a(v, rawX - s, rawY - t, q - s, r - t, S, T);
            if (b(event.getRawX(), event.getRawY())) {
                p.setAllow(true);
                b(true);
                a(false);
                d(false);
                c(false);
                return true;
            }
            b(false);
            if (a(event.getRawX(), event.getRawY())) {
                p.setAllow(true);
                a(true);
                d(false);
                c(false);
                return true;
            }
            a(false);
            if (d(event.getRawX(), event.getRawY())) {
                p.setAllow(true);
                d(true);
                c(false);
                return true;
            }
            d(false);
            if (c(event.getRawX(), event.getRawY())) {
                p.setAllow(true);
                c(true);
                return true;
            }
            c(false);
            p.a(this.v);
            if (n.a(this.v[0], this.v[1])) {
                p.setAllow(true);
                o.c((Rs) v, this.v[0], this.v[1]);
            } else {
                p.setAllow(false);
                o.d();
            }
            return true;
        } else if (actionMasked == MotionEvent.ACTION_UP) {
            Y = null;
            Z.removeCallbacks(aa);
            if (!u) {
                if (v instanceof Rs) {
                    Rs rs = (Rs) v;
                    if (rs.getBlockType() == 0) {
                        a(rs, event.getX(), event.getY());
                    }
                }
                return false;
            }
            m.setDragEnabled(true);
            n.setScrollEnabled(true);
            O.setDragEnabled(true);
            p.setDummyVisibility(View.GONE);
            if (!p.getAllow()) {
                Rs rs2 = (Rs) v;
                if (rs2.getBlockType() == 0) {
                    o.a(rs2, 0);
                    if (w != null) {
                        if (x == 0) {
                            w.ha = (Integer) v.getTag();
                        }
                        if (x == 2) {
                            w.ia = (Integer) v.getTag();
                        }
                        if (x == 3) {
                            w.ja = (Integer) v.getTag();
                        }
                        if (x == 5) {
                            w.a((Ts) w.V.get(y), rs2);
                        }
                        rs2.E = w;
                        w.p().k();
                    } else {
                        rs2.p().k();
                    }
                }
                q();
            } else if (N.b()) {
                Rs rs5 = (Rs) v;
                if (rs5.getBlockType() == 2) {
                    g(true);
                    n(rs5.T);
                } else {
                    b(false);
                    int id = Integer.parseInt(rs5.getBean().id);
                    BlockBean blockBean2;
                    if (w != null) {
                        BlockBean clone = w.getBean().clone();
                        if (x == 0) {
                            clone.nextBlock = id;
                        } else if (x == 2) {
                            clone.subStack1 = id;
                        } else if (x == 3) {
                            clone.subStack2 = id;
                        } else if (x == 5) {
                            clone.parameters.set(y, "@" + id);
                        }
                        blockBean2 = clone;
                    } else {
                        blockBean2 = null;
                    }
                    ArrayList<BlockBean> arrayList = new ArrayList<>();
                    for (Rs allChild : rs5.getAllChildren()) {
                        arrayList.add(allChild.getBean().clone());
                    }
                    b(rs5);
                    BlockBean blockBean3 = null;
                    if (w != null) {
                        blockBean3 = w.getBean().clone();
                    }
                    int[] oLocationOnScreen = new int[2];
                    o.getLocationOnScreen(oLocationOnScreen);
                    bC.d(B).b(s(), arrayList, ((int) s) - oLocationOnScreen[0], ((int) t) - oLocationOnScreen[1], blockBean2, blockBean3);
                    C();
                }
            } else if (N.d()) {
                d(false);
                Rs rs7 = (Rs) v;
                o.a(rs7, 0);
                if (w != null) {
                    if (x == 0) {
                        w.ha = (Integer) v.getTag();
                    }
                    if (x == 2) {
                        w.ia = (Integer) v.getTag();
                    }
                    if (x == 3) {
                        w.ja = (Integer) v.getTag();
                    }
                    if (x == 5) {
                        w.a((Ts) w.V.get(y), rs7);
                    }
                    rs7.E = w;
                    w.p().k();
                } else {
                    rs7.p().k();
                }
                c(rs7);
            } else if (N.c()) {
                c(false);
                if (v instanceof Us) {
                    o(((Us) v).T);
                }
            } else if (N.a()) {
                a(false);
                Rs rs10 = (Rs) v;
                o.a(rs10, 0);
                if (w != null) {
                    if (x == 0) {
                        w.ha = (Integer) v.getTag();
                    }
                    if (x == 2) {
                        w.ia = (Integer) v.getTag();
                    }
                    if (x == 3) {
                        w.ja = (Integer) v.getTag();
                    }
                    if (x == 5) {
                        w.a((Ts) w.V.get(y), rs10);
                    }
                    rs10.E = w;
                    w.p().k();
                } else {
                    rs10.p().k();
                }
                ArrayList<BlockBean> arrayList2 = new ArrayList<>();
                for (Rs rs : rs10.getAllChildren()) {
                    BlockBean clone2 = rs.getBean().clone();
                    clone2.id = String.valueOf(Integer.parseInt(clone2.id) + 99000000);
                    if (clone2.nextBlock > 0) {
                        clone2.nextBlock = clone2.nextBlock + 99000000;
                    }
                    if (clone2.subStack1 > 0) {
                        clone2.subStack1 = clone2.subStack1 + 99000000;
                    }
                    if (clone2.subStack2 > 0) {
                        clone2.subStack2 = clone2.subStack2 + 99000000;
                    }
                    for (int i = 0; i < clone2.parameters.size(); i++) {
                        String parameter = clone2.parameters.get(i);
                        if (parameter != null && parameter.length() > 0 && parameter.charAt(0) == '@') {
                            clone2.parameters.set(i, "@" + (Integer.parseInt(parameter.substring(1)) + 99000000));
                        }
                    }
                    arrayList2.add(clone2);
                }
                int[] nLocationOnScreen = new int[2];
                n.getLocationOnScreen(nLocationOnScreen);
                int width = nLocationOnScreen[0] + (n.getWidth() / 2);
                int a2 = nLocationOnScreen[1] + ((int) wB.a(getContext(), 4.0f));
                ArrayList<BlockBean> a3 = a(arrayList2, width, a2, true);
                int[] oLocationOnScreen = new int[2];
                o.getLocationOnScreen(oLocationOnScreen);
                bC.d(B).a(s(), a3, width - oLocationOnScreen[0], a2 - oLocationOnScreen[1], null, null);
                C();
            } else if (v instanceof Rs) {
                p.a(this.v);
                Rs rs13 = (Rs) v;
                if (rs13.getBlockType() == 1) {
                    int addTargetId = o.getAddTargetId();
                    BlockBean clone3 = addTargetId >= 0 ? o.a(addTargetId).getBean().clone() : null;
                    Rs a4 = a(rs13, this.v[0], this.v[1], false);
                    BlockBean blockBean3 = null;
                    if (addTargetId >= 0) {
                        blockBean3 = o.a(addTargetId).getBean().clone();
                    }
                    int[] locationOnScreen = new int[2];
                    o.getLocationOnScreen(locationOnScreen);
                    bC.d(B).a(s(), a4.getBean().clone(), this.v[0] - locationOnScreen[0], this.v[1] - locationOnScreen[1], clone3, blockBean3);
                    if (clone3 != null) {
                        clone3.print();
                    }
                    if (blockBean3 != null) {
                        blockBean3.print();
                    }
                } else if (rs13.getBlockType() == 2) {
                    int addTargetId2 = o.getAddTargetId();
                    BlockBean clone5 = addTargetId2 >= 0 ? o.a(addTargetId2).getBean().clone() : null;
                    ArrayList<BlockBean> data = ((Us) v).getData();
                    ArrayList<BlockBean> a5 = a(data, this.v[0], this.v[1], true);
                    if (a5.size() > 0) {
                        Rs a6 = o.a(a5.get(0).id);
                        a(a6, this.v[0], this.v[1], true);
                        BlockBean blockBean3 = null;
                        if (addTargetId2 >= 0) {
                            blockBean3 = o.a(addTargetId2).getBean().clone();
                        }
                        int[] locationOnScreen = new int[2];
                        o.getLocationOnScreen(locationOnScreen);
                        bC.d(B).a(s(), a5, this.v[0] - locationOnScreen[0], this.v[1] - locationOnScreen[1], clone5, blockBean3);
                    }
                    o.c();
                } else {
                    o.a(rs13, 0);
                    int id = Integer.parseInt(rs13.getBean().id);
                    BlockBean blockBean;
                    if (w != null) {
                        blockBean = w.getBean().clone();
                        if (x == 0) {
                            blockBean.nextBlock = id;
                        } else if (x == 2) {
                            blockBean.subStack1 = id;
                        } else if (x == 3) {
                            blockBean.subStack2 = id;
                        } else if (x == 5) {
                            blockBean.parameters.set(y, "@" + id);
                        }
                    } else {
                        blockBean = null;
                    }
                    Rs a7 = o.a(o.getAddTargetId());
                    BlockBean clone6 = a7 != null ? a7.getBean().clone() : null;
                    ArrayList<Rs> allChildren3 = rs13.getAllChildren();
                    ArrayList<BlockBean> arrayList3 = new ArrayList<>();
                    for (Rs rs : allChildren3) {
                        arrayList3.add(rs.getBean().clone());
                    }
                    a(rs13, this.v[0], this.v[1], true);
                    ArrayList<BlockBean> arrayList4 = new ArrayList<>();
                    for (Rs rs : allChildren3) {
                        arrayList4.add(rs.getBean().clone());
                    }
                    BlockBean clone7 = w != null ? w.getBean().clone() : null;
                    BlockBean blockBean3 = null;
                    if (a7 != null) {
                        blockBean3 = a7.getBean().clone();
                    }
                    if (blockBean == null || clone7 == null || !blockBean.isEqual(clone7)) {
                        int[] locationOnScreen = new int[2];
                        o.getLocationOnScreen(locationOnScreen);
                        int x = locationOnScreen[0];
                        int y = locationOnScreen[1];
                        bC.d(B).a(s(), arrayList3, arrayList4, ((int) s) - x, ((int) t) - y, this.v[0] - x, this.v[1] - y, blockBean, clone7, clone6, blockBean3);
                    }
                    o.c();
                }
                C();
                o.c();
            }
            p.setAllow(false);
            h(false);
            u = false;
            return true;
        } else if (actionMasked == MotionEvent.ACTION_CANCEL) {
            Z.removeCallbacks(aa);
            u = false;
            return false;
        } else if (actionMasked == MotionEvent.ACTION_SCROLL) {
            Z.removeCallbacks(aa);
            u = false;
            return false;
        } else {
            return true;
        }
    }

    public boolean p() {
        return true;
    }

    public void q() {
    }

    private void r() {
        if (Y != null) {
            m.setDragEnabled(false);
            n.setScrollEnabled(false);
            O.setDragEnabled(false);
            if (ia) {
                g(false);
            }

            if (G) {
                F.vibrate(100L);
            }

            u = true;
            if (((Rs) Y).getBlockType() == 0) {
                a((Rs) Y);
                f(true);
                h(true);
                p.a((Rs) Y);
                o.a((Rs) Y, 8);
                o.c((Rs) Y);
                o.a((Rs) Y);
            } else if (((Rs) Y).getBlockType() == 2) {
                f(false);
                h(true);
                p.a((Rs) Y);
                o.a((Rs) Y, ((Us) Y).getData());
            } else {
                p.a((Rs) Y);
                o.a((Rs) Y);
            }

            float a = q - s;
            float b = r - t;
            p.a(Y, a, b, a, b, S, T);
            p.a(v);
            if (n.a(v[0], v[1])) {
                p.setAllow(true);
                o.c((Rs) Y, v[0], v[1]);
            } else {
                p.setAllow(false);
                o.d();
            }
        }
    }

    public final String s() {
        return bC.a(M.getJavaName(), C, D);
    }

    public void showSourceCode() {
        yq yq = new yq(this, B);
        yq.a(jC.c(B), jC.b(B), jC.a(B), false);
        String code = new Fx(M.getActivityName(), yq.N, "", o.getBlocks()).a();

        CodeEditor codeEditor = new CodeEditor(this);
        codeEditor.setColorScheme(new EditorColorScheme());
        codeEditor.setEditable(false);
        codeEditor.setEditorLanguage(new JavaLanguage());
        codeEditor.setText(Lx.j(code, false));
        codeEditor.setTextSize(12);
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setWordwrap(false);
        codeEditor.getComponent(Magnifier.class).setWithinEditorForcibly(true);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Source code")
                .setIcon(R.drawable.code_icon)
                .setPositiveButton(R.string.common_word_close, null)
                .create();

        dialog.setView(codeEditor,
                (int) getDip(24),
                (int) getDip(8),
                (int) getDip(24),
                (int) getDip(8));
        dialog.show();
    }

    public final void t() {
        fa = ObjectAnimator.ofFloat(O, "TranslationX", 0.0f);
        fa.setDuration(500L);
        fa.setInterpolator(new DecelerateInterpolator());
        ga = ObjectAnimator.ofFloat(O, "TranslationX", O.getHeight());
        ga.setDuration(300L);
        ga.setInterpolator(new DecelerateInterpolator());
        ha = true;
    }

    public final void x() {
        ba = ObjectAnimator.ofFloat(N, "TranslationY", 0.0f);
        ba.setDuration(500L);
        ba.setInterpolator(new DecelerateInterpolator());
        ca = ObjectAnimator.ofFloat(N, "TranslationY", N.getHeight() * (-1));
        ca.setDuration(300L);
        ca.setInterpolator(new DecelerateInterpolator());
        da = true;
    }

    public final void z() {
        O.a();
        for (BlockCollectionBean next : Mp.h().f()) {
            O.a(next.name, next.blocks).setOnTouchListener(this);
        }
    }

    private static class ProjectSaver extends MA {
        private final WeakReference<LogicEditorActivity> activity;

        public ProjectSaver(LogicEditorActivity logicEditorActivity) {
            super(logicEditorActivity);
            activity = new WeakReference<>(logicEditorActivity);
            logicEditorActivity.addTask(this);
        }

        @Override
        public void a() {
            activity.get().h();
            activity.get().finish();
        }

        @Override
        public void a(String str) {
            Toast.makeText(a, xB.b().a(activity.get().getContext(), R.string.common_error_failed_to_save), Toast.LENGTH_SHORT).show();
            activity.get().h();
        }

        @Override
        public void b() {
            publishProgress("Now saving..");
            activity.get().E();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
