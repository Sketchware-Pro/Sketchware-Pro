package mod.hasrat.lib;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;

public abstract class CommonViewBindingAdapter<T, V extends ViewBinding>
        extends RecyclerView.Adapter<CommonViewBindingAdapter.BindingViewHolder<V>> {

    private ArrayList<T> items = new ArrayList<>();

    @NonNull
    public abstract V createBinding(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    public abstract void bind(@NonNull V binding, int position);

    @NonNull
    @Override
    public final BindingViewHolder<V> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindingViewHolder<>(createBinding(LayoutInflater.from(parent.getContext()), parent));
    }

    @Override
    public final void onBindViewHolder(@NonNull BindingViewHolder<V> holder, int position) {
        bind(holder.binding, position);
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

    public static class BindingViewHolder<V extends ViewBinding> extends RecyclerView.ViewHolder {
        public V binding;

        public BindingViewHolder(@NonNull V binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
