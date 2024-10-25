package pro.sketchware.lib.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;

public abstract class BaseViewBindingAdapter<T> extends RecyclerView.Adapter<BaseViewBindingAdapter.ViewHolder> {

    private ArrayList<T> items = new ArrayList<>();

    @NonNull
    public abstract ViewBinding getViewBinding(LayoutInflater inflater, ViewGroup parent);

    public abstract void onBindView(@NonNull ViewBinding binding, int position);

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getViewBinding(LayoutInflater.from(parent.getContext()), parent));
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        onBindView(holder.binding, position);
    }

    @Override
    public final int getItemCount() {
        return items.size();
    }

    public void setItems(@NonNull ArrayList<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
