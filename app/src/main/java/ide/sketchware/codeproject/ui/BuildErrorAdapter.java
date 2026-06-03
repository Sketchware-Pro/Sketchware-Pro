package ide.sketchware.codeproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ide.sketchware.R;
import mod.hey.studios.util.CompileLogHelper;

public class BuildErrorAdapter extends RecyclerView.Adapter<BuildErrorAdapter.ViewHolder> {

    private static final Pattern FILE_LINE_PATTERN =
            Pattern.compile("(.*\\.(?:java|kt|xml)):(\\d+):");

    private final List<String> errors = new ArrayList<>();
    private OnErrorClickListener clickListener;

    public interface OnErrorClickListener {
        void onErrorClick(String filePath, int lineNumber);
    }

    public void setOnErrorClickListener(OnErrorClickListener listener) {
        this.clickListener = listener;
    }

    public void setErrors(List<String> newErrors) {
        errors.clear();
        errors.addAll(newErrors);
        notifyDataSetChanged();
    }

    public void clear() {
        errors.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_build_error, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String errorLine = errors.get(position);
        holder.textView.setText(CompileLogHelper.getColoredLogs(holder.itemView.getContext(), errorLine));
        holder.textView.setTextIsSelectable(true);

        Matcher matcher = FILE_LINE_PATTERN.matcher(errorLine);
        if (matcher.find()) {
            holder.itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    String filePath = matcher.group(1);
                    int lineNumber = Integer.parseInt(matcher.group(2));
                    clickListener.onErrorClick(filePath, lineNumber);
                }
            });
        } else {
            holder.itemView.setOnClickListener(null);
            holder.itemView.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return errors.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.error_text);
        }
    }
}
