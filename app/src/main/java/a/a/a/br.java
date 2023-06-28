package a.a.a;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.besome.sketch.editor.component.CollapsibleComponentLayout;
import com.besome.sketch.editor.component.ComponentAddActivity;
import com.besome.sketch.editor.component.ComponentEventButton;
import com.besome.sketch.editor.event.CollapsibleButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import mod.hey.studios.util.Helper;

public class br extends qA implements View.OnClickListener {
    public ProjectFileBean f;
    public a h;
    public TextView i;
    public RecyclerView k;
    public FloatingActionButton n;
    public String o;
    public ArrayList<ComponentBean> g = new ArrayList<>();
    public boolean j = false;
    private final ActivityResultLauncher<Intent> addComponent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            d();
        }
    });
    public final String l = "component";
    public final int m = 4;

    public void d() {
        if (this.f == null || this.h == null) {
            return;
        }
        this.g = jC.a(this.o).e(this.f.getJavaName());
        this.h.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (!mB.a() && view.getId() == R.id.fab) {
            Intent intent = new Intent(getContext(), ComponentAddActivity.class);
            intent.putExtra("sc_id", this.o);
            intent.putExtra("project_file", this.f);
            addComponent.launch(intent);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        if (getUserVisibleHint()) {
            if (menuItem.getItemId() != 4) {
                return true;
            }
            a(this.h.c);
            return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        if (view.getTag().equals("component")) {
            contextMenu.setHeaderTitle(xB.b().a(getContext(), R.string.component_context_menu_title));
            contextMenu.add(0, 4, 0, xB.b().a(getContext(), R.string.component_context_menu_title_delete_component));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_component_list, viewGroup, false);
        a(viewGroup2);
        setHasOptionsMenu(true);
        if (bundle != null) {
            this.o = bundle.getString("sc_id");
        } else {
            this.o = getActivity().getIntent().getStringExtra("sc_id");
        }
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.o);
        super.onSaveInstanceState(bundle);
    }

    class a extends RecyclerView.Adapter<br.a.ViewHolder> {
        public int c = -1;

        class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout A;
            public LinearLayout B;
            public ImageView t;
            public TextView u;
            public TextView v;
            public ImageView w;
            public CollapsibleComponentLayout x;
            public LinearLayout y;
            public LinearLayout z;

            public ViewHolder(View view) {
                super(view);
                this.t = (ImageView) view.findViewById(R.id.img_icon);
                this.u = (TextView) view.findViewById(R.id.tv_component_type);
                this.v = (TextView) view.findViewById(R.id.tv_component_id);
                this.w = (ImageView) view.findViewById(R.id.img_menu);
                this.y = (LinearLayout) view.findViewById(R.id.events_preview);
                this.A = (LinearLayout) view.findViewById(R.id.component_option_layout);
                this.z = (LinearLayout) view.findViewById(R.id.component_option);
                this.B = (LinearLayout) view.findViewById(R.id.component_events);
                this.x = new CollapsibleComponentLayout(br.this.getContext());
                this.x.setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                this.x.n.e.setText(xB.b().a(br.this.getContext(), R.string.component_context_menu_title_delete_component));
                this.z.addView(this.x);
                this.x.setButtonOnClickListener(v -> {
                    c = getLayoutPosition();
                    ComponentBean bean = jC.a(o).a(f.getJavaName(), c);
                    if (v instanceof CollapsibleButton) {
                        bean.isConfirmation = true;
                        notifyItemChanged(c);
                    } else {
                        int id = v.getId();
                        if (id == R.id.confirm_no) {
                            bean.isConfirmation = false;
                            notifyItemChanged(c);
                        } else if (id == R.id.confirm_yes) {
                            jC.a(o).b(f.getJavaName(), bean);
                            bean.isConfirmation = false;
                            notifyItemRemoved(c);
                            notifyItemRangeChanged(c, getItemCount());
                        }
                    }
                });
                view.setOnClickListener(v -> {
                    c = getLayoutPosition();
                    ComponentBean bean = jC.a(o).a(f.getJavaName(), getLayoutPosition());
                    if (bean.isCollapsed) {
                        bean.isCollapsed = false;
                        E();
                    } else {
                        bean.isCollapsed = true;
                        D();
                    }
                });
                this.w.setOnClickListener(v -> {
                    c = getLayoutPosition();
                    ComponentBean bean = g.get(c);
                    if (bean.isCollapsed) {
                        bean.isCollapsed = false;
                        E();
                    } else {
                        bean.isCollapsed = true;
                        D();
                    }
                });
                view.setOnLongClickListener(v -> {
                    c = getLayoutPosition();
                    ComponentBean bean = g.get(c);
                    if (bean.isCollapsed) {
                        bean.isCollapsed = false;
                        E();
                    } else {
                        bean.isCollapsed = true;
                        D();
                    }
                    return true;
                });
            }

            public void D() {
                gB.a(this.w, 0.0f, (Animator.AnimatorListener) null);
                gB.a((ViewGroup) this.A, 200, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        A.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                    }
                });
                this.y.animate().translationX(0.0f).alpha(1.0f).setStartDelay(120L).setDuration(150L).start();
                this.B.animate().translationX(-this.B.getWidth()).setDuration(150L).alpha(0.0f).start();
            }

            public void E() {
                this.A.setVisibility(View.VISIBLE);
                gB.a(this.w, -180.0f, (Animator.AnimatorListener) null);
                gB.b(this.A, 200, null);
                this.y.animate().translationX(this.y.getWidth()).alpha(0.0f).setDuration(150L).start();
                LinearLayout linearLayout = this.B;
                linearLayout.setTranslationX(-linearLayout.getWidth());
                this.B.setAlpha(0.0f);
                this.B.animate().translationX(0.0f).setStartDelay(200L).setDuration(120L).alpha(1.0f).start();
            }
        }

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 2) {
                            if (n.isEnabled()) {
                                n.hide();
                            }
                        } else if (dy < -2) {
                            if (n.isEnabled()) {
                                n.show();
                            }
                        }
                    }
                });
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ComponentBean componentBean = br.this.g.get(position);
            holder.u.setText(ComponentBean.getComponentName(br.this.getContext(), componentBean.type));
            holder.t.setImageResource(ComponentBean.getIconResource(componentBean.type));
            int i2 = componentBean.type;
            if (i2 == 2) {
                TextView textView = holder.v;
                textView.setText(componentBean.componentId + " : " + componentBean.param1);
            } else if (i2 != 6 && i2 != 14 && i2 != 16) {
                holder.v.setText(componentBean.componentId);
            } else {
                String str = componentBean.param1;
                if (str.length() <= 0) {
                    str = "/";
                }
                TextView textView2 = holder.v;
                textView2.setText(componentBean.componentId + " : " + str);
            }
            ArrayList<EventBean> a2 = jC.a(br.this.o).a(br.this.f.getJavaName(), componentBean);
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(Arrays.asList(oq.a(componentBean.getClassInfo())));
            holder.y.removeAllViews();
            holder.B.removeAllViews();
            holder.y.setAlpha(1.0f);
            holder.y.setTranslationX(0.0f);
            if (componentBean.isCollapsed) {
                holder.A.setVisibility(View.GONE);
                holder.w.setRotation(0.0f);
            } else {
                holder.A.setVisibility(View.VISIBLE);
                holder.w.setRotation(-180.0f);
                if (componentBean.isConfirmation) {
                    holder.x.b();
                } else {
                    holder.x.a();
                }
            }
            holder.A.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            Iterator<EventBean> it = a2.iterator();
            while (it.hasNext()) {
                EventBean next = it.next();
                if (arrayList.contains(next.eventName)) {
                    LinearLayout linearLayout = (LinearLayout) wB.a(br.this.getContext(), R.layout.fr_logic_list_item_event_preview);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, (int) wB.a(br.this.getContext(), 4.0f), 0);
                    linearLayout.setLayoutParams(layoutParams);
                    ((ImageView) linearLayout.findViewById(R.id.icon)).setImageResource(oq.a(next.eventName));
                    ((LinearLayout) linearLayout.findViewById(R.id.icon_bg)).setBackgroundResource(R.drawable.circle_bg_white_outline_secondary);
                    ComponentEventButton componentEventButton = new ComponentEventButton(br.this.getContext());
                    componentEventButton.e.setText(next.eventName);
                    componentEventButton.c.setImageResource(oq.a(next.eventName));
                    componentEventButton.setClickListener(v -> {
                        if (!mB.a()) {
                            a(next.targetId, next.eventName, next.eventName);
                        }
                    });
                    holder.y.addView(linearLayout);
                    holder.B.addView(componentEventButton);
                    arrayList.remove(next.eventName);
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                LinearLayout linearLayout2 = (LinearLayout) wB.a(br.this.getContext(), R.layout.fr_logic_list_item_event_preview);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams2.setMargins(0, 0, (int) wB.a(br.this.getContext(), 4.0f), 0);
                linearLayout2.setLayoutParams(layoutParams2);
                ImageView imageView = (ImageView) linearLayout2.findViewById(R.id.icon);
                imageView.setImageResource(oq.a(str2));
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(0.0f);
                imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                holder.y.addView(linearLayout2);
                linearLayout2.setScaleX(0.8f);
                linearLayout2.setScaleY(0.8f);
                ComponentEventButton componentEventButton2 = new ComponentEventButton(br.this.getContext());
                componentEventButton2.a();
                componentEventButton2.e.setText(str2);
                componentEventButton2.c.setImageResource(oq.a(str2));
                componentEventButton2.setClickListener(v -> {
                    if (!mB.a()) {
                        EventBean event = new EventBean(EventBean.EVENT_TYPE_COMPONENT, componentBean.type, componentBean.componentId, str2);
                        jC.a(o).a(f.getJavaName(), event);
                        bB.a(getContext(), xB.b().a(getContext(), R.string.event_message_new_event), 0).show();
                        componentEventButton2.b();
                        notifyItemChanged(c);
                        a(event.targetId, event.eventName, event.eventName);
                    }
                });
                holder.B.addView(componentEventButton2);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fr_logic_list_item_component, parent, false));
        }

        @Override
        public int getItemCount() {
            int size = br.this.g.size();
            if (size == 0) {
                br.this.i.setVisibility(View.VISIBLE);
            } else {
                br.this.i.setVisibility(View.GONE);
            }
            return size;
        }
    }

    public void c() {
        if (this.f == null) {
            return;
        }
        Iterator<ComponentBean> it = jC.a(this.o).e(this.f.getJavaName()).iterator();
        while (it.hasNext()) {
            it.next().initValue();
        }
        this.h.notifyDataSetChanged();
    }

    public final void a(ViewGroup viewGroup) {
        this.i = (TextView) viewGroup.findViewById(R.id.empty_message);
        this.k = (RecyclerView) viewGroup.findViewById(R.id.component_list);
        this.k.setHasFixedSize(true);
        this.i.setVisibility(View.GONE);
        this.i.setText(xB.b().a(getContext(), R.string.component_message_no_components));
        this.k.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        this.h = new a(this.k);
        this.k.setAdapter(this.h);
        this.n = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        this.n.setOnClickListener(this);
    }

    public void a(ProjectFileBean projectFileBean) {
        this.f = projectFileBean;
    }

    public final void a(int i) {
        aB aBVar = new aB(this.a);
        aBVar.b(xB.b().a(getContext(), R.string.component_context_menu_title_delete_component));
        aBVar.a(R.drawable.delete_96);
        aBVar.a(xB.b().a(getContext(), R.string.event_dialog_confirm_delete_component));
        aBVar.b(xB.b().a(getContext(), R.string.common_word_delete), v -> {
            ComponentBean component = jC.a(o).a(f.getJavaName(), i);
            jC.a(o).b(f.getJavaName(), component);
            d();
            bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_delete), 0).show();
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void a(String str, String str2, String str3) {
        Intent intent = new Intent(getActivity(), LogicEditorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", this.o);
        intent.putExtra("id", str);
        intent.putExtra("event", str2);
        intent.putExtra("project_file", this.f);
        intent.putExtra("event_text", str3);
        startActivity(intent);
    }
}
