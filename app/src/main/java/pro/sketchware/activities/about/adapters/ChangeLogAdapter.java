package pro.sketchware.activities.about.adapters;

import static pro.sketchware.utility.UI.advancedCorners;
import static pro.sketchware.utility.UI.animateLayoutChanges;
import static pro.sketchware.utility.UI.shadAnim;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import pro.sketchware.activities.about.models.AboutResponseModel;
import pro.sketchware.databinding.AboutChangelogBinding;

public class ChangeLogAdapter extends RecyclerView.Adapter<ChangeLogAdapter.ViewHolder> {

    private final ArrayList<AboutResponseModel.ChangeLogs> changelog;
    private final Set<Integer> showingAdditionalInfoPositions = new HashSet<>();

    public ChangeLogAdapter(ArrayList<AboutResponseModel.ChangeLogs> data) {
        changelog = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AboutChangelogBinding binding = AboutChangelogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AboutResponseModel.ChangeLogs release = changelog.get(position);

        boolean isTitled = release.isTitled();
        if (isTitled) {
            String titleText = release.getTitle();
            if (titleText != null) {
                holder.binding.tvTitle.setText(titleText);
                holder.binding.tvTitle.setVisibility(View.VISIBLE);
            } else {
                holder.binding.tvTitle.setText("We've messed something up, sorry for the inconvenience!\n" +
                        "(Details: Invalid data type of \"title\")");
                holder.binding.tvTitle.setVisibility(View.VISIBLE);
            }
        } else {
            holder.binding.tvTitle.setVisibility(View.GONE);
        }

        boolean isBetaVersion = release.isBeta();
        boolean previousIsBetaValueDiffers = true;
        if (position != 0) {
            boolean previousIsBeta = changelog.get(position - 1).isBeta();
            previousIsBetaValueDiffers = previousIsBeta != isBetaVersion;
        }

        holder.binding.tvVariant.setVisibility(previousIsBetaValueDiffers ? View.VISIBLE : View.GONE);
        if (previousIsBetaValueDiffers) {
            holder.binding.tvVariant.setText(isBetaVersion ? "Beta" : "Official");
        }

        long releaseDate = release.getReleaseDate();
        if (releaseDate > 0) {
            holder.binding.tvReleaseNote.setVisibility(View.VISIBLE);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            holder.binding.tvReleaseNote.setText("Released on: " + formatter.format(new Date(releaseDate)));
        } else {
            holder.binding.tvReleaseNote.setVisibility(View.GONE);
        }

        String description = release.getDescription();
        if (description != null) {
            holder.binding.tvSubTitle.setText(description);
            Linkify.addLinks(holder.binding.tvSubTitle, Linkify.WEB_URLS);
        } else {
            holder.binding.tvSubTitle.setText("We've messed something up, sorry for the inconvenience!\n" +
                    "(Details: Invalid data type of \"description\")");
        }

        boolean showingAdditionalInfo = showingAdditionalInfoPositions.contains(position);

        holder.binding.viewAdditionalInfo.setVisibility(showingAdditionalInfo ? View.VISIBLE : View.GONE);
        holder.binding.icArrow.setRotation(showingAdditionalInfo ? 0 : 180);

        holder.binding.icArrow.setOnClickListener(v -> holder.binding.logBackground.performClick());

        holder.binding.logBackground.setOnClickListener(v -> {
            if (showingAdditionalInfoPositions.contains(position)) {
                shadAnim(holder.binding.icArrow, "rotation", 180, 220);
                holder.binding.viewAdditionalInfo.setVisibility(View.GONE);
                showingAdditionalInfoPositions.remove(position);
            } else {
                shadAnim(holder.binding.icArrow, "rotation", 0, 220);
                holder.binding.viewAdditionalInfo.setVisibility(View.VISIBLE);
                showingAdditionalInfoPositions.add(position);
            }
            animateLayoutChanges(holder.binding.logBackground);
        });

        if (position == 0) {
            advancedCorners(holder.binding.viewLeftline, Color.parseColor("#2f55ed"));
        } else {
            holder.binding.viewLeftline.setBackground(null);
        }
    }

    @Override
    public int getItemCount() {
        return changelog.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final AboutChangelogBinding binding;

        public ViewHolder(@NonNull AboutChangelogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
