package a.a.a;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Iterator;
import mod.hey.studios.moreblock.MoreblockValidator;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hilal.saif.moreblock.MoreBlockCustomParams;

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
    public ArrayList<Pair<String, String>> l = new ArrayList();
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
        a(activity);
    }

    public final int a(TextView textView) {
        Rect rect = new Rect();
        textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
        return rect.width();
    }

    public final void a(Activity activity) {
        this.a = activity;
        LayoutInflater.from(activity).inflate(2131427506, this);
        this.radio_mb_type = (RadioGroup) findViewById(2131232432);
        initRefresh();
        this.radio_mb_type_void = (RadioButton) findViewById(2131232433);
        this.radio_mb_type_string = (RadioButton) findViewById(2131232434);
        this.radio_mb_type_number = (RadioButton) findViewById(2131232435);
        this.radio_mb_type_boolean = (RadioButton) findViewById(2131232436);
        this.j = (LinearLayout) findViewById(2131232305);
        this.b = (RelativeLayout) findViewById(2131230793);
        this.c = (LinearLayout) findViewById(2131231664);
        this.k = new gt(activity);
        this.j.addView(this.k);
        this.d = (TextInputLayout) findViewById(2131231825);
        this.e = (TextInputLayout) findViewById(2131231823);
        this.f = (TextInputLayout) findViewById(2131231833);
        ((TextView) findViewById(2131232204)).setText(xB.b().a(activity, 2131625508));
        ((TextView) findViewById(2131232196)).setText(xB.b().a(activity, 2131625506));
        this.o = new MoreblockValidator(activity, this.d, uq.b, uq.a(), new ArrayList());
        this.n = new ZB(activity, this.e, uq.b, uq.a(), new ArrayList());
        this.m = new ZB(activity, this.f, uq.b, uq.a(), new ArrayList());
        this.g = (EditText) findViewById(2131231007);
        this.h = (EditText) findViewById(2131231006);
        this.i = (EditText) findViewById(2131231011);
        this.d.setHint(xB.b().a(activity, 2131625503));
        this.f.setHint(xB.b().a(activity, 2131625504));
        this.e.setHint(xB.b().a(activity, 2131625502));
        this.g.setPrivateImeOptions("defaultInputmode=english;");
        this.h.setPrivateImeOptions("defaultInputmode=english;");
        this.i.setPrivateImeOptions("defaultInputmode=english;");
        this.g.addTextChangedListener(new _s(this));
        Button button = (Button) findViewById(2131230758);
        button.setText(xB.b().a(activity, 2131625499));
        button.setOnClickListener(new at(this));
        button = (Button) findViewById(2131230757);
        button.setText(xB.b().a(activity, 2131625499));
        MoreBlockCustomParams.customParams(this);
        button.setOnClickListener(new bt(this));
        this.p = new Rs(activity, 0, "", " ", "definedFunc");
        this.b.addView(this.p);
    }

    public final void a(ViewGroup viewGroup, ViewGroup viewGroup2, Rs rs, String str, ArrayList<Pair<String, String>> arrayList) {
        viewGroup.removeAllViews();
        viewGroup.addView(rs);
        Iterator it = arrayList.iterator();
        String str2 = str;
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            StringBuilder stringBuilder;
            if (((String) pair.first).equals("b")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(" %b.");
                stringBuilder.append((String) pair.second);
                str2 = stringBuilder.toString();
            } else if (((String) pair.first).equals("d")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(" %d.");
                stringBuilder.append((String) pair.second);
                str2 = stringBuilder.toString();
            } else if (((String) pair.first).equals("s")) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(" %s.");
                stringBuilder.append((String) pair.second);
                str2 = stringBuilder.toString();
            } else if (((String) pair.first).length() <= 2 || ((String) pair.first).indexOf(".") < 0) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str2);
                stringBuilder.append(" ");
                stringBuilder.append((String) pair.second);
                str2 = stringBuilder.toString();
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str2);
                stringBuilder2.append(" %");
                stringBuilder2.append((String) pair.first);
                stringBuilder2.append(".");
                stringBuilder2.append((String) pair.second);
                str2 = stringBuilder2.toString();
            }
        }
        rs.setSpec(str2);
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            int i3;
            Pair pair2 = (Pair) arrayList.get(i2);
            Rs rs2;
            int i4;
            if (((String) pair2.first).equals("b")) {
                rs2 = new Rs(this.a, arrayList.indexOf(pair2) + 1, (String) pair2.second, "b", "getArg");
                viewGroup.addView(rs2);
                i4 = i + 1;
                rs.a((Ts) rs.V.get(i), rs2);
                i3 = i4;
            } else if (((String) pair2.first).equals("d")) {
                rs2 = new Rs(this.a, arrayList.indexOf(pair2) + 1, (String) pair2.second, "d", "getArg");
                viewGroup.addView(rs2);
                i4 = i + 1;
                rs.a((Ts) rs.V.get(i), rs2);
                i3 = i4;
            } else if (((String) pair2.first).equals("s")) {
                rs2 = new Rs(this.a, arrayList.indexOf(pair2) + 1, (String) pair2.second, "s", "getArg");
                viewGroup.addView(rs2);
                i4 = i + 1;
                rs.a((Ts) rs.V.get(i), rs2);
                i3 = i4;
            } else if (((String) pair2.first).length() > 2) {
                Object obj = pair2.first;
                String substring = ((String) obj).substring(((String) obj).indexOf(".") + 1);
                rs2 = new Rs(this.a, arrayList.indexOf(pair2) + 1, (String) pair2.second, kq.a(substring), kq.b(substring), "getArg");
                viewGroup.addView(rs2);
                rs.a((Ts) rs.V.get(i), rs2);
                i3 = i + 1;
            } else {
                i3 = i;
            }
            i2++;
            i = i3;
        }
        rs.k();
        viewGroup2.removeAllViews();
        i2 = rs.ka.size();
        for (i = 0; i < i2; i++) {
            View view = (View) rs.ka.get(i);
            int a = ((String) rs.la.get(i)).equals("label") ? a((TextView) view) : 0;
            if (view instanceof Rs) {
                a = ((Rs) view).getWidthSum();
            }
            i3 = (int) (((float) a) + wB.a(this.a, 4.0f));
            View imageView = new ImageView(this.a);
            imageView.setImageResource(2131165831);
            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            imageView.setPadding(0, (int) wB.a(this.a, 4.0f), 0, (int) wB.a(this.a, 4.0f));
            imageView.setLayoutParams(new LayoutParams(i3, -1));
            viewGroup2.addView(imageView);
            if (i != 0 || this.g.getText().length() <= 0) {
                imageView.setOnClickListener(new ct(this, arrayList, viewGroup2, viewGroup, rs, str));
            } else {
                imageView.setVisibility(4);
                imageView.setEnabled(false);
            }
        }
    }

    public boolean a() {
        return this.g.getText().toString().isEmpty() && this.l.size() == 0;
    }

    public boolean b() {
        if (this.g.getText().toString().isEmpty()) {
            bB.b(getContext(), xB.b().a(getContext(), 2131625494), 0).show();
            return false;
        } else if (this.o.b()) {
            return true;
        } else {
            bB.b(getContext(), xB.b().a(getContext(), 2131625494), 0).show();
            return false;
        }
    }

    public Pair<String, String> getBlockInformation() {
        String trim = this.g.getText().toString().trim();
        return new Pair(ReturnMoreblockManager.injectMbType(trim, trim, getType()), ReturnMoreblockManager.injectMbType(this.p.T, trim, getType()));
    }

    public String getType() {
        return ReturnMoreblockManager.getMbTypeFromRadioButton(this.radio_mb_type);
    }

    public void initRefresh() {
        this.radio_mb_type.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				refresh(getType());
			}
		});
    }

    public void refresh(String str) {
        this.p = new Rs(this.a, 0, "", ReturnMoreblockManager.getPreviewType(str), "definedFunc");
        a(this.b, this.c, this.p, this.g.getText().toString(), this.l);
    }

    public void setFuncNameValidator(ArrayList<String> arrayList) {
        this.o = new MoreblockValidator(this.a, this.d, uq.b, uq.a(), arrayList);
    }
}
