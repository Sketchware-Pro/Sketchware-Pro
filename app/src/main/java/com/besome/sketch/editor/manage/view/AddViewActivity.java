package com.besome.sketch.editor.manage.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.SelectableButtonBar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import a.a.a.YB;
import a.a.a.bB;
import a.a.a.rq;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;

public class AddViewActivity extends BaseDialogActivity {
    public LinearLayout A;
    public RelativeLayout B;
    public ImageView C;
    public ImageView D;
    public TextInputLayout E;
    public EditText F;
    public YB G;
    public TextView H;
    public ArrayList<String> I;
    public boolean J;
    public boolean K;
    public boolean L;
    public boolean M;
    public int N;
    public ProjectFileBean O;
    public String P;
    public SelectableButtonBar add_view_type_selector;
    public LinearLayout add_view_type_selector_layout;
    public ArrayList<a> t;
    public RecyclerView u;
    public b v;
    public SelectableButtonBar w;
    public SelectableButtonBar x;
    public LinearLayout y;
    public LinearLayout z;

    public final void a(View var1) {
        var1.animate().translationX((float) (-var1.getMeasuredWidth())).start();
    }

    public final void a(a var1) {
        int var2 = var1.a;
        if (var2 != 0) {
            if (var2 != 1) {
                if (var2 != 2) {
                    if (var2 == 3) {
                        if (var1.d) {
                            e(C);
                        } else {
                            b(C);
                        }
                    }
                } else if (var1.d) {
                    d(A);
                } else {
                    a(A);
                }
            } else if (var1.d) {
                if (!J) {
                    z.animate().translationY((float) (-y.getMeasuredHeight())).start();
                } else {
                    e(z);
                }
            } else if (!J) {
                n();
            } else {
                c(z);
            }
        } else if (var1.d) {
            e(y);
            if (K) {
                e(z);
            }
        } else {
            c(y);
            if (K) {
                z.animate().translationY((float) (-y.getMeasuredHeight())).start();
            } else {
                n();
            }
        }

    }

    public boolean a(YB var1) {
        boolean var2;
        var2 = var1.b();

        return var2;
    }

    public final void b(View var1) {
        var1.animate().translationY((float) var1.getMeasuredHeight()).start();
    }

    public final void b(boolean var1) {
        for (int var2 = 0; var2 < t.size(); ++var2) {
            a var3 = t.get(var2);
            if (var3.a == 2) {
                var3.d = var1;
                v.notifyItemChanged(var2);
                break;
            }
        }

    }

    public final void c(View var1) {
        var1.animate().translationY((float) (-var1.getMeasuredHeight())).start();
    }

    public final void c(boolean var1) {
        for (int i = 0; i < t.size(); ++i) {
            a var3 = t.get(i);
            if (var3.a == 1) {
                var3.d = var1;
                v.notifyItemChanged(i);
                break;
            }
        }

    }

    public final void d(View var1) {
        var1.animate().translationX(0.0F).start();
    }

    public final void e(View var1) {
        var1.animate().translationY(0.0F).start();
    }

    public final ArrayList<ViewBean> f(String var1) {
        return rq.f(var1);
    }

    public final void g(int var1) {
        K = (var1 & 1) == 1;

        J = (var1 & 2) != 2;

        L = (var1 & 8) == 8;

        M = (var1 & 4) == 4;

    }

    public final void n() {
        z.animate().translationY((float) (-(y.getMeasuredHeight() + z.getMeasuredHeight()))).start();
    }

    public final void o() {
        t = new ArrayList<>();
        t.add(new a(this, 0, 2131165864, "StatusBar", J));
        t.add(new a(this, 1, 2131165872, "Toolbar", K));
        t.add(new a(this, 2, 2131165737, "Drawer", M));
        t.add(new a(this, 3, 2131165608, "FAB", L));
        v.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        if (var1 == 276 && var2 == -1) {
            ProjectFileBean var4 = var3.getParcelableExtra("preset_data");
            P = var4.presetName;
            g(var4.options);
            o();
        }

    }

    @Override
    @SuppressLint("ResourceType")
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(2131427557);
        e(xB.b().a(getApplicationContext(), 2131625299));
        Intent var2 = getIntent();
        I = var2.getStringArrayListExtra("screen_names");
        N = var2.getIntExtra("request_code", 264);
        O = var2.getParcelableExtra("project_file");
        if (O != null) {
            e(xB.b().a(getApplicationContext(), 2131625300));
        }

        add_view_type_selector = findViewById(2131232506);
        add_view_type_selector_layout = findViewById(2131232507);
        w = findViewById(2131230865);
        x = findViewById(2131230864);
        y = findViewById(2131231604);
        z = findViewById(2131231605);
        A = findViewById(2131231601);
        B = findViewById(2131231603);
        C = findViewById(2131231602);
        D = findViewById(2131231154);
        E = findViewById(2131231825);
        F = findViewById(2131231007);
        H = findViewById(2131232285);
        H.setVisibility(8);
        H.setText(xB.b().a(getApplicationContext(), 2131625295));
        E.setHint(xB.b().a(this, 2131625293));
        F.setPrivateImeOptions("defaultInputmode=english;");
        u = findViewById(2131231056);
        v = new b(this);
        u.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        u.setHasFixedSize(true);
        u.setAdapter(v);
        ((TextView) findViewById(2131232145)).setText(xB.b().a(getApplicationContext(), 2131625303));
        ((TextView) findViewById(2131232020)).setText(xB.b().a(getApplicationContext(), 2131625302));
        add_view_type_selector.a(0, "Activity");
        add_view_type_selector.a(1, "Fragment");
        add_view_type_selector.a(2, "DialogFragment");
        add_view_type_selector.a();
        w.a(0, "Portrait");
        w.a(1, "Landscape");
        w.a(2, "Both");
        w.a();
        x.a(0, "Unspecified");
        x.a(1, "Visible");
        x.a(2, "Hidden");
        x.a();
        x.setListener(i -> {
            if (0 == i || 1 == i) {
                e(B);
            } else if (2 == i) {
                B.animate().translationY((float) D.getMeasuredHeight()).start();
            }
        });
        d(xB.b().a(getApplicationContext(), 2131624970));
        b(xB.b().a(getApplicationContext(), 2131624974));

        super.r.setOnClickListener(v -> {
            int options = 1;
            if (265 == N) {
                O.orientation = w.getSelectedItemKey();
                O.keyboardSetting = x.getSelectedItemKey();
                if (!K) {
                    options = 0;
                }
                if (!J) {
                    options = options | 2;
                }
                if (L) {
                    options = options | 8;
                }
                if (M) {
                    options = options | 4;
                }
                O.options = options;
                Intent intent = new Intent();
                intent.putExtra("project_file", O);
                setResult(-1, intent);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625279, new Object[0]), 0).show();
                finish();
            } else if (a(G)) {
                String var4 = F.getText().toString() + getSuffix(add_view_type_selector);
                ProjectFileBean projectFileBean = new ProjectFileBean(0, var4, w.getSelectedItemKey(), x.getSelectedItemKey(), K, !J, L, M);
                Intent intent = new Intent();
                intent.putExtra("project_file", projectFileBean);
                if (P != null) {
                    intent.putExtra("preset_views", f(P));
                }
                setResult(-1, intent);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625276, new Object[0]), 0).show();
                finish();
            }

        });
        super.s.setOnClickListener(v -> {
            setResult(0);
            finish();
        });
        if (N == 265) {
            G = new YB(getApplicationContext(), E, uq.b, new ArrayList<>(), O.fileName);
            F.setText(O.fileName);
            F.setEnabled(false);
            F.setBackgroundResource(2131034318);
            g(O.options);
            add_view_type_selector_layout.setVisibility(8);
            w.setSelectedItemByKey(O.orientation);
            x.setSelectedItemByKey(O.keyboardSetting);
            super.r.setText(xB.b().a(getApplicationContext(), 2131625031).toUpperCase());
        } else {
            K = true;
            J = true;
            G = new YB(getApplicationContext(), E, uq.b, I);
        }

        o();
    }

    private String getSuffix(SelectableButtonBar buttonBar) {
        int selectedItemKey = buttonBar.getSelectedItemKey();
        String suffix;
        if (selectedItemKey == 0) {
            suffix = "";
        } else if (selectedItemKey == 1) {
            suffix = "_fragment";
        } else if (selectedItemKey == 2) {
            suffix = "_dialog_fragment";
        } else {
            suffix = "";
        }
        return suffix;
    }

    private class a {
        public final AddViewActivity e;
        public int a;
        public int b;
        public String c;
        public boolean d;

        public a(AddViewActivity var1, int var2, int var3, String var4, boolean var5) {
            e = var1;
            a = var2;
            b = var3;
            c = var4;
            d = var5;
        }
    }

    public class b extends RecyclerView.Adapter<b.ViewHolder> {
        public final AddViewActivity e;
        public int c;
        public boolean d;

        public b(AddViewActivity var1) {
            e = var1;
            c = -1;
        }

        @Override
        public int getItemCount() {
            return e.t.size();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int var2) {
            d = true;
            a var3 = t.get(var2);
            viewHolder.t.setImageResource(var3.b);
            viewHolder.u.setText(var3.c);
            viewHolder.v.setChecked(var3.d);
            var2 = var3.a;
            if (var2 == 0) {
                e.J = var3.d;
            } else if (var2 == 1) {
                e.K = var3.d;
            } else if (var2 == 3) {
                e.L = var3.d;
            } else if (var2 == 2) {
                e.M = var3.d;
            }

            AddViewActivity var4 = e;
            if (var4.L || var4.M) {
                e.H.setVisibility(View.VISIBLE);
            }

            e.a(var3);
            d = false;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
            @SuppressLint("ResourceType") View var3 = wB.a(var1.getContext(), 2131427556);
            var3.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            return new ViewHolder(this, var3);
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final b w;
            public ImageView t;
            public TextView u;
            public CheckBox v;

            @SuppressLint("ResourceType")
            public ViewHolder(b var1, View var2) {
                super(var2);
                w = var1;
                t = var2.findViewById(2131231151);
                u = var2.findViewById(2131232055);
                v = var2.findViewById(2131230883);
                v.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (!d) {
                        c = getLayoutPosition();
                        a item = AddViewActivity.this.t.get(c);
                        H.setVisibility(8);
                        item.d = isChecked;
                        if (item.a == 2 || item.d) {
                            c(true);
                        } else if (item.a == 1 || !item.d) {
                            b(false);
                        }
                        notifyItemChanged(c);
                    }
                });
            }
        }
    }
}