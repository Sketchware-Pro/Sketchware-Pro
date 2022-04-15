package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import mod.hey.studios.moreblock.MoreblockValidator;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hilal.saif.moreblock.MoreBlockCustomParams;

@SuppressLint("ViewConstructor")
public class dt extends LinearLayout {
    public Activity a;
    public RelativeLayout b;
    public LinearLayout c;
    public TextInputLayout d;
    public TextInputLayout e;
    public TextInputLayout f;
    public EditText g;
    public EditText h;
    public EditText i;
    public LinearLayout j;
    public gt k;
    public ArrayList<Pair<String, String>> l = new ArrayList<>();
    public ZB m;
    public ZB n;
    public MoreblockValidator o;
    public Rs p;
    public RadioGroup radio_mb_type;
    public RadioButton radio_mb_type_boolean;
    public RadioButton radio_mb_type_number;
    public RadioButton radio_mb_type_string;
    public RadioButton radio_mb_type_void;

    public dt(Activity activity) {
        super(activity);
        initialize(activity);
    }

    public final int a(TextView textView) {
        Rect rect = new Rect();
        textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
        return rect.width();
    }

    @SuppressLint("ResourceType")
    private void initialize(Activity activity) {
        a = activity;
        LayoutInflater.from(activity).inflate(2131427506, this);
        radio_mb_type = findViewById(2131232432);
        initRefresh();
        radio_mb_type_void = findViewById(2131232433);
        radio_mb_type_string = findViewById(2131232434);
        radio_mb_type_number = findViewById(2131232435);
        radio_mb_type_boolean = findViewById(2131232436);
        j = findViewById(2131232305);
        b = findViewById(2131230793);
        c = findViewById(2131231664);
        k = new gt(activity);
        j.addView(k);
        d = findViewById(2131231825);
        e = findViewById(2131231823);
        f = findViewById(2131231833);
        ((TextView) findViewById(2131232204)).setText(xB.b().a(activity, 2131625508));
        ((TextView) findViewById(2131232196)).setText(xB.b().a(activity, 2131625506));
        o = new MoreblockValidator(activity, d, uq.b, uq.a(), new ArrayList<>());
        n = new ZB(activity, e, uq.b, uq.a(), new ArrayList<>());
        m = new ZB(activity, f, uq.b, uq.a(), new ArrayList<>());
        g = findViewById(2131231007);
        h = findViewById(2131231006);
        i = findViewById(2131231011);
        d.setHint(xB.b().a(activity, 2131625503));
        f.setHint(xB.b().a(activity, 2131625504));
        e.setHint(xB.b().a(activity, 2131625502));
        g.setPrivateImeOptions("defaultInputmode=english;");
        h.setPrivateImeOptions("defaultInputmode=english;");
        i.setPrivateImeOptions("defaultInputmode=english;");
        g.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable var1) {
                if (var1.toString().equals("") || o.b()) {
                    a(b, c, p, var1.toString(), l);
                }
            }
        });
        Button var4 = findViewById(2131230758);
        var4.setText(xB.b().a(activity, 2131625499));
        var4.setOnClickListener(view -> {
            if (!mB.a()) {
                if (m.b() && o.b()) {
                    Pair<String, String> var21 = k.getSelectedItem();
                    String var3 = var21.first;
                    String var41 = var3;
                    if (var21.second.length() > 0) {
                        var41 = var3 + "." + var21.second;
                    }

                    l.add(new Pair<>(var41, i.getText().toString()));
                    a(b, c, p, g.getText().toString(), l);
                    ArrayList<String> var6 = new ArrayList<>(Arrays.asList(uq.a()));

                    for (Pair<String, String> stringStringPair : l) {
                        if (!stringStringPair.first.equals("t")) {
                            var6.add(stringStringPair.second);
                        }
                    }

                    m.a(var6.toArray(new String[0]));
                    i.setText("");
                }

            }
        });
        var4 = findViewById(2131230757);
        var4.setText(xB.b().a(activity, 2131625499));
        MoreBlockCustomParams.customParams(this);
        var4.setOnClickListener(view -> {
            if (!mB.a()) {
                if (n.b() && o.b()) {
                    l.add(new Pair<>("t", h.getText().toString()));
                    a(b, c, p, g.getText().toString(), l);
                    h.setText("");
                }
            }
        });
        p = new Rs(activity, 0, "", " ", "definedFunc");
        b.addView(p);
    }

    public final void a(ViewGroup blockArea, ViewGroup removeArea, Rs var3, String var4, ArrayList<Pair<String, String>> var5) {
        blockArea.removeAllViews();
        blockArea.addView(var3);
        Iterator<Pair<String, String>> var6 = var5.iterator();

        String var7;
        String var10;
        StringBuilder var15;
        for (var7 = var4; var6.hasNext(); var7 = var15.toString()) {
            Pair<String, String> var8;
            idk:
            {
                var8 = var6.next();
                StringBuilder var9;
                switch (var8.first) {
                    case "b":
                        var9 = new StringBuilder();
                        var9.append(var7);
                        var10 = " %b.";
                        var15 = var9;
                        break;
                    case "d":
                        var9 = new StringBuilder();
                        var9.append(var7);
                        var10 = " %d.";
                        var15 = var9;
                        break;
                    case "s":
                        var9 = new StringBuilder();
                        var9.append(var7);
                        var10 = " %s.";
                        var15 = var9;
                        break;
                    default:
                        if (var8.first.length() > 2 && var8.first.contains(".")) {
                            StringBuilder var19 = new StringBuilder();
                            var19.append(var7);
                            var19.append(" %");
                            var19.append(var8.first);
                            var19.append(".");
                            var15 = var19;
                            break idk;
                        }

                        var9 = new StringBuilder();
                        var9.append(var7);
                        var10 = " ";
                        var15 = var9;
                        break;
                }

                var15.append(var10);
            }

            var15.append(var8.second);
        }

        var3.setSpec(var7);
        int var11 = var5.size();
        int var12 = 0;

        int var13;
        int var14;
        for (var13 = var12; var12 < var11; var13 = var14) {
            lol:
            {
                Pair<String, String> var16 = var5.get(var12);
                Rs var17;
                if (var16.first.equals("b")) {
                    var17 = new Rs(a, var5.indexOf(var16) + 1, var16.second, "b", "getArg");
                } else if (var16.first.equals("d")) {
                    var17 = new Rs(a, var5.indexOf(var16) + 1, var16.second, "d", "getArg");
                } else {
                    if (!var16.first.equals("s")) {
                        var14 = var13;
                        if (var16.first.length() > 2) {
                            var10 = var16.first;
                            String var18 = var10.substring(var10.indexOf(".") + 1);
                            var10 = kq.a(var18);
                            var17 = new Rs(a, var5.indexOf(var16) + 1, var16.second, var10, kq.b(var18), "getArg");
                            blockArea.addView(var17);
                            var3.a((Ts) var3.V.get(var13), var17);
                            var14 = var13 + 1;
                        }
                        break lol;
                    }

                    var17 = new Rs(a, var5.indexOf(var16) + 1, var16.second, "s", "getArg");
                }

                blockArea.addView(var17);
                var3.a((Ts) var3.V.get(var13), var17);
                var14 = var13 + 1;
            }

            ++var12;
        }

        var3.k();
        removeArea.removeAllViews();
        var13 = var3.ka.size();

        for (var12 = 0; var12 < var13; ++var12) {
            View var20 = var3.ka.get(var12);
            if (var3.la.get(var12).equals("label")) {
                var14 = a((TextView) var20);
            } else {
                var14 = 0;
            }

            if (var20 instanceof Rs) {
                var14 = ((Rs) var20).getWidthSum();
            }

            var14 = (int) ((float) var14 + wB.a(a, 4.0F));
            ImageView removeIcon = new ImageView(a);
            removeIcon.setImageResource(R.drawable.ic_remove_grey600_24dp);
            removeIcon.setScaleType(ScaleType.CENTER_INSIDE);
            removeIcon.setPadding(0, (int) wB.a(a, 4.0F), 0, (int) wB.a(a, 4.0F));
            removeIcon.setLayoutParams(new LayoutParams(var14, -1));
            removeArea.addView(removeIcon);
            if (var12 == 0 && g.getText().length() > 0) {
                removeIcon.setVisibility(View.INVISIBLE);
                removeIcon.setEnabled(false);
            } else {
                removeIcon.setOnClickListener(view -> {
                    int indexOfChild;
                    if (g.getText().length() > 0) {
                        indexOfChild = removeArea.indexOfChild(view) - 1;
                    } else {
                        indexOfChild = removeArea.indexOfChild(view);
                    }

                    var5.remove(indexOfChild);
                    ArrayList<String> reservedTypes = new ArrayList<>(Arrays.asList(uq.a()));

                    for (Pair<String, String> stringStringPair : var5) {
                        if (!stringStringPair.first.equals("t")) {
                            reservedTypes.add(stringStringPair.second);
                        }
                    }

                    m.a(reservedTypes.toArray(new String[0]));
                    a(blockArea, removeArea, var3, var4, var5);
                });
            }
        }

    }

    public boolean a() {
        return g.getText().toString().isEmpty() && l.size() == 0;
    }

    public boolean b() {
        if (!g.getText().toString().isEmpty() && o.b()) {
            return true;
        } else {
            bB.b(getContext(), xB.b().a(getContext(), 2131625494), 0).show();
            return false;
        }
    }

    public Pair<String, String> getBlockInformation() {
        String var1 = g.getText().toString().trim();
        return new Pair<>(ReturnMoreblockManager.injectMbType(var1, var1, getType()), ReturnMoreblockManager.injectMbType(p.T, var1, getType()));
    }

    public String getType() {
        return ReturnMoreblockManager.getMbTypeFromRadioButton(radio_mb_type);
    }

    public void initRefresh() {
        radio_mb_type.setOnCheckedChangeListener((radioGroup, i) -> refresh(getType()));
    }

    public void refresh(String var1) {
        var1 = ReturnMoreblockManager.getPreviewType(var1);
        Rs var2 = new Rs(a, 0, "", var1, "definedFunc");
        p = var2;
        a(b, c, var2, g.getText().toString(), l);
    }

    public void setFuncNameValidator(ArrayList<String> var1) {
        o = new MoreblockValidator(a, d, uq.b, uq.a(), var1);
    }

}
