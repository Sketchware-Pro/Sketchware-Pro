package pro.sketchware.activities.about.adapters;

import static pro.sketchware.utility.UI.advancedCorners;
import static pro.sketchware.utility.UI.loadImageFromUrl;
import static pro.sketchware.utility.UI.rippleRound;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import a.a.a.mB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.about.models.AboutResponseModel;
import pro.sketchware.databinding.AboutTeamviewBinding;

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
            loadImageFromUrl(holder.binding.imgUserIcon, contributorImg);
        }

        String contributorUsername = member.getMemberUsername();
        if (contributorUsername != null) {
            holder.binding.tvUserName.setText(contributorUsername);
        }

        holder.binding.tvDescription.setText(member.getDescription());

        boolean isTitled = position == 0 || !(member.getTitle().equals(team.get(position - 1).getTitle()));
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

        if (member.isCoreTeamMember()) {
            advancedCorners(holder.binding.viewLeftline, MaterialColors.getColor(holder.binding.viewLeftline, R.attr.colorPrimary));
        } else {
            advancedCorners(holder.binding.viewLeftline, MaterialColors.getColor(holder.binding.viewLeftline, R.attr.colorGreen));
        }

        holder.binding.tvStatus.setVisibility(View.VISIBLE);
        int activeBackgroundColor;
        int activeBackgroundTextColor;
        if (member.isActive()) {
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

        holder.binding.memberLayout.setOnClickListener(view -> openMemberGithubProfile(view.getContext(), member.getMemberUsername()));
    }

    private void openMemberGithubProfile(Context context, String username) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(String.format("https://github.com/%s", username)));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            context.startActivity(intent);
        } catch (Exception e) {
            showGoogleChromeNotice(context);
        }
    }

    private void showGoogleChromeNotice(Context context) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setIcon(R.drawable.chrome_96);
        dialog.setTitle(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.setMessage(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_ok), (v, which) -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                context.startActivity(intent);
                v.dismiss();
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
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
