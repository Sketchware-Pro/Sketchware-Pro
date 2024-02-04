package com.besome.sketch.editor.manage.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.SelectableButtonBar;
import com.sketchware.remod.databinding.ManageScreenActivityAddTempBinding;

import java.util.ArrayList;

import a.a.a.YB;
import a.a.a.bB;
import a.a.a.rq;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;

public class AddViewActivity extends BaseDialogActivity {

    public YB G;
    public ArrayList<String> I;
    public boolean J;
    public boolean K;
    public boolean L;
    public boolean M;
    public int N;
    public ProjectFileBean O;
    public String P;
    public ArrayList<a> t;
    public b v;

    private ManageScreenActivityAddTempBinding binding;

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
                            e(binding.previewFab);
                        } else {
                            b(binding.previewFab);
                        }
                    }
                } else if (var1.d) {
                    d(binding.previewDrawer);
                } else {
                    a(binding.previewDrawer);
                }
            } else if (var1.d) {
                if (!J) {
                    binding.previewToolbar.animate().translationY((float) (-binding.previewStatusbar.getMeasuredHeight())).start();
                } else {
                    e(binding.previewToolbar);
                }
            } else if (!J) {
                n();
            } else {
                c(binding.previewToolbar);
            }
        } else if (var1.d) {
            e(binding.previewStatusbar);
            if (K) {
                e(binding.previewToolbar);
            }
        } else {
            c(binding.previewStatusbar);
            if (K) {
                binding.previewToolbar.animate().translationY((float) (-binding.previewStatusbar.getMeasuredHeight())).start();
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
        binding.previewToolbar.animate().translationY((float) (-(binding.previewStatusbar.getMeasuredHeight() + binding.previewToolbar.getMeasuredHeight()))).start();
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
        binding = ManageScreenActivityAddTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        e(xB.b().a(getApplicationContext(), 2131625299));
        Intent var2 = getIntent();
        I = var2.getStringArrayListExtra("screen_names");
        N = var2.getIntExtra("request_code", 264);
        O = var2.getParcelableExtra("project_file");
        if (O != null) {
            e(xB.b().a(getApplicationContext(), 2131625300));
        }

        binding.tvWarning.setVisibility(8);
        binding.tvWarning.setText(xB.b().a(getApplicationContext(), 2131625295));
        binding.tiName.setHint(xB.b().a(this, 2131625293));
        binding.edName.setPrivateImeOptions("defaultInputmode=english;");
        v = new b(this);
        binding.featureTypes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        binding.featureTypes.setHasFixedSize(true);
        binding.featureTypes.setAdapter(v);
        binding.tvScreenOrientation.setText(xB.b().a(getApplicationContext(), 2131625303));
        binding.tvKeyboard.setText(xB.b().a(getApplicationContext(), 2131625302));
        binding.addViewTypeSelector.a(0, "Activity");
        binding.addViewTypeSelector.a(1, "Fragment");
        binding.addViewTypeSelector.a(2, "DialogFragment");
        binding.addViewTypeSelector.a();
        binding.btnbarOrientation.a(0, "Portrait");
        binding.btnbarOrientation.a(1, "Landscape");
        binding.btnbarOrientation.a(2, "Both");
        binding.btnbarOrientation.a();
        binding.btnbarKeyboard.a(0, "Unspecified");
        binding.btnbarKeyboard.a(1, "Visible");
        binding.btnbarKeyboard.a(2, "Hidden");
        binding.btnbarKeyboard.a();
        binding.btnbarKeyboard.setListener(i -> {
            if (0 == i || 1 == i) {
                e(binding.activityPreview);
            } else if (2 == i) {
                binding.activityPreview.animate().translationY((float) binding.imgKeyboard.getMeasuredHeight()).start();
            }
        });
        d(xB.b().a(getApplicationContext(), 2131624970));
        b(xB.b().a(getApplicationContext(), 2131624974));

        super.r.setOnClickListener(v -> {
            int options = 1;
            if (265 == N) {
                O.orientation = binding.btnbarOrientation.getSelectedItemKey();
                O.keyboardSetting = binding.btnbarKeyboard.getSelectedItemKey();
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
                String var4 = binding.edName.getText().toString() + getSuffix(binding.addViewTypeSelector);
                ProjectFileBean projectFileBean = new ProjectFileBean(0, var4, binding.btnbarOrientation.getSelectedItemKey(), binding.btnbarKeyboard.getSelectedItemKey(), K, !J, L, M);
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
            G = new YB(getApplicationContext(), binding.tiName, uq.b, new ArrayList<>(), O.fileName);
            binding.edName.setText(O.fileName);
            binding.edName.setEnabled(false);
            binding.edName.setBackgroundResource(2131034318);
            g(O.options);
            binding.addViewTypeSelectorLayout.setVisibility(8);
            binding.btnbarOrientation.setSelectedItemByKey(O.orientation);
            binding.btnbarKeyboard.setSelectedItemByKey(O.keyboardSetting);
            super.r.setText(xB.b().a(getApplicationContext(), 2131625031).toUpperCase());
        } else {
            K = true;
            J = true;
            G = new YB(getApplicationContext(), binding.tiName, uq.b, I);
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
                binding.tvWarning.setVisibility(View.VISIBLE);
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
                        binding.tvWarning.setVisibility(8);
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