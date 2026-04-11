package a.a.a;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.view.ManageViewActivity;
import com.besome.sketch.editor.manage.view.PresetSettingActivity;

import java.util.ArrayList;
import java.util.Iterator;

import pro.sketchware.R;
import pro.sketchware.databinding.FrManageViewListBinding;
import pro.sketchware.databinding.ManageViewCustomListItemBinding;

public class xw extends qA {

    public String g;
    public ArrayList<ProjectFileBean> h = new ArrayList<>();
    public int[] l = new int[19];
    private Adapter adapter;
    private boolean hasSelection = false;

    private FrManageViewListBinding binding;

    private String a(int beanType, String xmlName) {
        String baseName = wq.b(beanType);
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(baseName);
        int[] nameCounters = l;
        int counter = nameCounters[beanType] + 1;
        nameCounters[beanType] = counter;
        nameBuilder.append(counter);
        String newName = nameBuilder.toString();
        ArrayList<ViewBean> viewBeans = jC.a(g).d(xmlName);
        xmlName = newName;

        while (true) {
            boolean nameExists = false;
            for (ViewBean viewBean : viewBeans) {
                if (xmlName.equals(viewBean.id)) {
                    nameExists = true;
                    break;
                }
            }

            if (!nameExists) {
                return xmlName;
            }

            nameBuilder = new StringBuilder();
            nameBuilder.append(baseName);
            counter = nameCounters[beanType] + 1;
            nameCounters[beanType] = counter;
            nameBuilder.append(counter);
            xmlName = nameBuilder.toString();
        }
    }

    private ArrayList<ViewBean> a(String var1, int var2) {
        ArrayList<ViewBean> var3 = new ArrayList<>();
        ArrayList<ViewBean> var4;
        if (var2 != 277) {
            if (var2 != 278) {
                var4 = var3;
            } else {
                var4 = rq.d(var1);
            }
        } else {
            var4 = rq.b(var1);
        }

        return var4;
    }

    public void a(ProjectFileBean projectFileBean) {
        h.add(projectFileBean);
        adapter.notifyDataSetChanged();
    }

    public void a(String fileName) {
        Iterator<ProjectFileBean> projectFiles = h.iterator();

        boolean fileAlreadyExists;
        while (true) {
            if (projectFiles.hasNext()) {
                ProjectFileBean fileBean = projectFiles.next();
                if (fileBean.fileType != 2 || !fileBean.fileName.equals(fileName)) {
                    continue;
                }
                fileAlreadyExists = true;
                break;
            }
            fileAlreadyExists = false;
            break;
        }

        if (!fileAlreadyExists) {
            h.add(new ProjectFileBean(2, fileName));
            adapter.notifyDataSetChanged();
        }

    }

    public void a(boolean var1) {
        hasSelection = var1;
        e();
        adapter.notifyDataSetChanged();
    }

    public void b(String var1) {
        for (ProjectFileBean var3 : h) {
            if (var3.fileType == 2 && var3.fileName.equals(var1)) {
                h.remove(var3);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public ArrayList<ProjectFileBean> c() {
        return h;
    }

    public void d() {
        ArrayList<ProjectFileBean> files = jC.b(g).c();
        if (files != null) {
            h.addAll(files);
        }
    }

    public final void e() {
        for (ProjectFileBean projectFileBean : h) {
            projectFileBean.isSelected = false;
        }
    }

    public void f() {
        int var1 = h.size();

        while (true) {
            int var2 = var1 - 1;
            if (var2 < 0) {
                adapter.notifyDataSetChanged();
                return;
            }

            var1 = var2;
            if (h.get(var2).isSelected) {
                h.remove(var2);
                var1 = var2;
            }
        }
    }

    public void g() {
        if (h != null) {
            if (h.isEmpty()) {
                binding.tvGuide.setVisibility(View.VISIBLE);
                binding.listActivities.setVisibility(View.GONE);
            } else {
                binding.tvGuide.setVisibility(View.GONE);
                binding.listActivities.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            d();
        } else {
            g = savedInstanceState.getString("sc_id");
            h = savedInstanceState.getParcelableArrayList("custom_views");
        }

        binding.listActivities.getAdapter().notifyDataSetChanged();
        g();
    }

    @Override
    public void onActivityResult(int var1, int var2, Intent var3) {
        if ((var1 == 277 || var1 == 278) && var2 == -1) {
            ProjectFileBean var4 = h.get(adapter.layoutPosition);
            ArrayList<ViewBean> var5 = jC.a(g).d(var4.getXmlName());

            for (int var7 = var5.size() - 1; var7 >= 0; --var7) {
                ViewBean var6 = var5.get(var7);
                jC.a(g).a(var4, var6);
            }

            ArrayList<ViewBean> var8 = a(((ProjectFileBean) var3.getParcelableExtra("preset_data")).presetName, var1);
            jC.a(g);

            for (ViewBean viewBean : eC.a(var8)) {
                viewBean.id = a(viewBean.type, var4.getXmlName());
                jC.a(g).a(var4.getXmlName(), viewBean);
                if (viewBean.type == 3 && var4.fileType == 0) {
                    jC.a(g).a(var4.getJavaName(), 1, viewBean.type, viewBean.id, "onClick");
                }
            }

            adapter.notifyItemChanged(adapter.layoutPosition);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FrManageViewListBinding.inflate(inflater, container, false);

        binding.listActivities.setHasFixedSize(true);
        binding.listActivities.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(binding.listActivities);
        binding.listActivities.setAdapter(adapter);
        if (savedInstanceState == null) {
            g = getActivity().getIntent().getStringExtra("sc_id");
        } else {
            g = savedInstanceState.getString("sc_id");
        }

        binding.tvGuide.setText(xB.b().a(getActivity(), R.string.design_manager_view_description_guide_create_custom_view));
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", g);
        var1.putParcelableArrayList("custom_views", h);
        super.onSaveInstanceState(var1);
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        public int layoutPosition;

        public Adapter(RecyclerView recyclerView) {
            layoutPosition = -1;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                        super.onScrolled(rv, dx, dy);
                        if (dy > 2) {
                            if (((ManageViewActivity) getActivity()).s.isEnabled()) {
                                ((ManageViewActivity) getActivity()).s.hide();
                            }
                        } else if (dy < -2 && ((ManageViewActivity) getActivity()).s.isEnabled()) {
                            ((ManageViewActivity) getActivity()).s.show();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return h != null ? h.size() : 0;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ManageViewCustomListItemBinding itemBinding = holder.itemBinding;
            if (hasSelection) {
                itemBinding.deleteImgContainer.setVisibility(View.VISIBLE);
                itemBinding.imgActivity.setVisibility(View.GONE);
            } else {
                itemBinding.deleteImgContainer.setVisibility(View.GONE);
                itemBinding.imgActivity.setVisibility(View.VISIBLE);
            }

            ProjectFileBean fileBean = h.get(position);
            itemBinding.imgActivity.setImageResource(R.drawable.activity_preset_1);
            itemBinding.chkSelect.setChecked(fileBean.isSelected);
            if (fileBean.fileType == 1) {
                itemBinding.tvScreenName.setText(fileBean.getXmlName());
            } else if (fileBean.fileType == 2) {
                itemBinding.chkSelect.setVisibility(View.GONE);
                itemBinding.imgActivity.setImageResource(R.drawable.activity_0110);
                itemBinding.tvScreenName.setText(fileBean.fileName.substring(1));
            }

            if (fileBean.isSelected) {
                itemBinding.imgDelete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                itemBinding.imgDelete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ManageViewCustomListItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ManageViewCustomListItemBinding itemBinding;

            public ViewHolder(ManageViewCustomListItemBinding binding) {
                super(binding.getRoot());
                itemBinding = binding;

                binding.chkSelect.setVisibility(View.GONE);
                itemView.setOnClickListener(view -> {
                    layoutPosition = getLayoutPosition();
                    if (hasSelection) {
                        binding.chkSelect.setChecked(!binding.chkSelect.isChecked());
                        h.get(layoutPosition).isSelected = binding.chkSelect.isChecked();
                        notifyItemChanged(layoutPosition);
                    }
                });
                itemView.setOnLongClickListener(v -> {
                    ((ManageViewActivity) getActivity()).a(true);
                    layoutPosition = getLayoutPosition();
                    binding.chkSelect.setChecked(!binding.chkSelect.isChecked());
                    h.get(layoutPosition).isSelected = binding.chkSelect.isChecked();
                    return true;
                });

                binding.imgPresetSetting.setOnClickListener(view -> {
                    if (!mB.a()) {
                        layoutPosition = getLayoutPosition();
                        Intent intent = new Intent(getActivity(), PresetSettingActivity.class);
                        int requestCode;
                        if (h.get(layoutPosition).fileType == 1) {
                            requestCode = 277;
                        } else {
                            requestCode = 278;
                        }

                        intent.putExtra("request_code", requestCode);
                        intent.putExtra("edit_mode", true);
                        startActivityForResult(intent, requestCode);
                    }
                });
            }
        }
    }
}
