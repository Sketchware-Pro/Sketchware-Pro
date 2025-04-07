package pro.sketchware.activities.appcompat.adapters;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import pro.sketchware.R;
import pro.sketchware.databinding.CustomViewAttributeBinding;
import pro.sketchware.listeners.ItemClickListener;
import pro.sketchware.utility.ThemeUtils;

public class AppCompatAdapter
        extends ListAdapter<HashMap<String, Object>, AppCompatAdapter.ViewHolder> {
    private static final DiffUtil.ItemCallback<HashMap<String, Object>> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull HashMap<String, Object> oldItem,
                        @NonNull HashMap<String, Object> newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull HashMap<String, Object> oldItem,
                        @NonNull HashMap<String, Object> newItem) {
                    return true;
                }
            };
    private ItemClickListener<Pair<View, HashMap<String, Object>>> listener;

    public AppCompatAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOnItemClickListener(
            ItemClickListener<Pair<View, HashMap<String, Object>>> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomViewAttributeBinding binding =
                CustomViewAttributeBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomViewAttributeBinding binding;

        public ViewHolder(CustomViewAttributeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HashMap<String, Object> item) {
            int violet = ThemeUtils.getColor(itemView, R.attr.colorViolet);
            int onSurface = ThemeUtils.getColor(itemView, R.attr.colorOnSurface);
            int green = ThemeUtils.getColor(itemView, R.attr.colorGreen);

            String value = item.get("value").toString();
            SpannableString spannableString = new SpannableString(value);
            spannableString.setSpan(
                    new ForegroundColorSpan(violet),
                    0,
                    value.indexOf(":"),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(
                    new ForegroundColorSpan(onSurface),
                    value.indexOf(":"),
                    value.indexOf("=") + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(
                    new ForegroundColorSpan(green),
                    value.indexOf("\""),
                    value.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            binding.cusAttrText.setText(spannableString);
            binding.cusAttrBtn.setVisibility(View.INVISIBLE);
            binding.cusAttrLayout.setOnClickListener(
                    v -> {
                        if (listener != null) {
                            listener.onItemClick(Pair.create(binding.cusAttrBtn, item));
                        }
                    });
        }
    }
}
