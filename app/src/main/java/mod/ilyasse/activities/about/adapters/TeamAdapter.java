package mod.ilyasse.activities.about.adapters;

import static mod.ilyasse.utils.UI.advancedCorners;
import static mod.ilyasse.utils.UI.circularImage;
import static mod.ilyasse.utils.UI.rippleRound;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.AboutTeamviewBinding;

import java.util.ArrayList;

import mod.ilyasse.activities.about.models.AboutResponseModel;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private final ArrayList<AboutResponseModel.TeamMember> team;

    public TeamAdapter(ArrayList<AboutResponseModel.TeamMember> data) {
        team = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AboutTeamviewBinding binding = AboutTeamviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AboutResponseModel.TeamMember member = team.get(position);

        String contributorImg = member.getMemberImg();
        if (contributorImg != null) {
            circularImage(holder.binding.imgUserIcon, contributorImg);
        }

        String contributorUsername = member.getMemberUsername();
        if (contributorUsername != null) {
            holder.binding.tvUserName.setText(contributorUsername);
        }

        String contributorDescription = member.getMemberDescription();
        if (contributorDescription != null) {
            holder.binding.tvDescription.setText(contributorDescription);
        }

        boolean isTitled = member.isTitled();
        if (isTitled) {
            String titleText = member.getTitle();
            if (titleText != null) {
                holder.binding.tvTitle.setText(titleText);
                holder.binding.tvTitle.setVisibility(View.VISIBLE);
            } else {
                holder.binding.tvTitle.setVisibility(View.GONE);
            }
        } else {
            holder.binding.tvTitle.setVisibility(View.GONE);
        }

        boolean isMainModder = member.isMainModder();
        if (isMainModder) {
            advancedCorners(holder.binding.viewLeftline, MaterialColors.getColor(holder.binding.viewLeftline, com.google.android.material.R.attr.colorPrimary));
        } else {
            advancedCorners(holder.binding.viewLeftline, MaterialColors.getColor(holder.binding.viewLeftline, R.attr.colorGreen));
        }

        if (isMainModder) {
            holder.binding.tvStatus.setVisibility(View.VISIBLE);
            boolean isActive = member.isActive();
            int activeBackgroundColor;
            int activeBackgroundTextColor;
            if (isActive) {
                holder.binding.tvStatus.setText("Active");
                activeBackgroundColor = MaterialColors.getColor(holder.binding.tvStatus, R.attr.colorCoolGreenContainer);
                activeBackgroundTextColor = MaterialColors.getColor(holder.binding.tvStatus, R.attr.colorOnCoolGreenContainer);
            } else {
                holder.binding.tvStatus.setText("Inactive");
                activeBackgroundColor = MaterialColors.getColor(holder.binding.tvStatus, R.attr.colorAmberContainer);
                activeBackgroundTextColor = MaterialColors.getColor(holder.binding.tvStatus, R.attr.colorOnAmberContainer);
            }
            rippleRound(holder.binding.tvStatus, activeBackgroundColor, activeBackgroundColor, 100);
            holder.binding.tvStatus.setTextColor(activeBackgroundTextColor);
        } else {
            holder.binding.tvStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return team != null ? team.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final AboutTeamviewBinding binding;

        public ViewHolder(@NonNull AboutTeamviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
