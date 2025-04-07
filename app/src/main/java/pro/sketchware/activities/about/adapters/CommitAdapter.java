package pro.sketchware.activities.about.adapters;

import static pro.sketchware.utility.UI.advancedCorners;
import static pro.sketchware.utility.UI.loadImageFromUrl;
import static pro.sketchware.utility.UI.rippleRound;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;

import io.noties.markwon.Markwon;
import pro.sketchware.BuildConfig;
import pro.sketchware.R;
import pro.sketchware.activities.about.models.AboutAppViewModel;
import pro.sketchware.activities.about.models.AboutResponseModel;
import pro.sketchware.databinding.AboutBetaChangesBinding;
import pro.sketchware.databinding.AboutCommitAdapterItemProgressBinding;

public class CommitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADING = 1;
    private final ArrayList<AboutResponseModel.CommitDetails> commitDetailsList = new ArrayList<>();
    private final AboutAppViewModel aboutAppViewModel;
    public int shaPosition = -1;
    private boolean isLoadingVisible = false;

    public CommitAdapter(AboutAppViewModel aboutAppViewModel) {
        this.aboutAppViewModel = aboutAppViewModel;
    }

    @Override
    public int getItemViewType(int position) {
        return isLoadingVisible && position == commitDetailsList.size() ? TYPE_LOADING : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            AboutCommitAdapterItemProgressBinding binding = AboutCommitAdapterItemProgressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new LoadingViewHolder(binding);
        } else {
            AboutBetaChangesBinding binding = AboutBetaChangesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder itemHolder) {
            AboutResponseModel.CommitDetails commitDetails = commitDetailsList.get(position);

            String date = commitDetails.getCommitDate();
            if (position != 0 && commitDetailsList.get(position - 1).getCommitDate().equals(date)) {
                itemHolder.binding.tvDateHeader.setVisibility(View.GONE);
            } else {
                itemHolder.binding.tvDateHeader.setText(date);
                itemHolder.binding.tvDateHeader.setVisibility(View.VISIBLE);
            }

            loadImageFromUrl(itemHolder.binding.imgUserIcon, commitDetails.getCommitterImage());
            itemHolder.binding.tvUserName.setText(commitDetails.getCommitterName());
            markwon(itemHolder.binding.tvDescription, commitDetails.getDescription());

            int activeBackgroundColor;
            int activeBackgroundTextColor;
            if (isCoreTeamMember(commitDetails.getCommitterName())) {
                itemHolder.binding.tvStatus.setText("Core Team");
                activeBackgroundColor = MaterialColors.getColor(itemHolder.binding.tvStatus, R.attr.colorCoolGreenContainer);
                activeBackgroundTextColor = MaterialColors.getColor(itemHolder.binding.tvStatus, R.attr.colorOnCoolGreenContainer);
            } else {
                itemHolder.binding.tvStatus.setText("Contributor");
                activeBackgroundColor = MaterialColors.getColor(itemHolder.binding.tvStatus, R.attr.colorAmberContainer);
                activeBackgroundTextColor = MaterialColors.getColor(itemHolder.binding.tvStatus, R.attr.colorOnAmberContainer);
            }
            rippleRound(itemHolder.binding.tvStatus, activeBackgroundColor, activeBackgroundColor, 100);
            itemHolder.binding.tvStatus.setTextColor(activeBackgroundTextColor);

            if (shaPosition > position || shaPosition == -1) {
                itemHolder.binding.updateStatus.setText("New Update");
                activeBackgroundColor = MaterialColors.getColor(itemHolder.binding.updateStatus, R.attr.colorCoolGreenContainer);
                activeBackgroundTextColor = MaterialColors.getColor(itemHolder.binding.updateStatus, R.attr.colorOnCoolGreenContainer);
                advancedCorners(itemHolder.binding.viewLeftline, MaterialColors.getColor(itemHolder.binding.viewLeftline, R.attr.colorGreen));
            } else if (shaPosition < position) {
                itemHolder.binding.updateStatus.setText("Old Version");
                activeBackgroundColor = MaterialColors.getColor(itemHolder.binding.updateStatus, R.attr.colorVioletContainer);
                activeBackgroundTextColor = MaterialColors.getColor(itemHolder.binding.updateStatus, R.attr.colorViolet);
                advancedCorners(itemHolder.binding.viewLeftline, MaterialColors.getColor(itemHolder.binding.viewLeftline, R.attr.colorViolet));
            } else {
                itemHolder.binding.updateStatus.setText("Current Version");
                activeBackgroundColor = MaterialColors.getColor(itemHolder.binding.updateStatus, R.attr.colorAmberContainer);
                activeBackgroundTextColor = MaterialColors.getColor(itemHolder.binding.updateStatus, R.attr.colorOnAmberContainer);
                advancedCorners(itemHolder.binding.viewLeftline, MaterialColors.getColor(itemHolder.binding.viewLeftline, R.attr.colorAmber));
            }
            rippleRound(itemHolder.binding.updateStatus, activeBackgroundColor, activeBackgroundColor, 100);
            itemHolder.binding.updateStatus.setTextColor(activeBackgroundTextColor);
        }
    }

    @Override
    public int getItemCount() {
        return commitDetailsList.size() + (isLoadingVisible ? 1 : 0);
    }

    public void addItems(ArrayList<AboutResponseModel.CommitDetails> newItems) {
        int startPosition = commitDetailsList.size();
        commitDetailsList.addAll(newItems);
        if (shaPosition == -1) {
            updateShaPosition();
        }
        notifyItemRangeInserted(startPosition, newItems.size());
    }

    private boolean isCoreTeamMember(String userName) {
        ArrayList<String> coreTeam = aboutAppViewModel.getCoreTeamMembers();
        return coreTeam.contains(userName);
    }

    public void showLoading() {
        if (!isLoadingVisible) {
            isLoadingVisible = true;
            notifyItemInserted(commitDetailsList.size());
        }
    }

    public void hideLoading() {
        if (isLoadingVisible) {
            isLoadingVisible = false;
            notifyItemRemoved(commitDetailsList.size());
        }
    }

    private void markwon(TextView textView, String input) {
        Markwon markwon = Markwon.create(textView.getContext());
        markwon.setMarkdown(textView, input);
    }

    public void updateShaPosition() {
        int i = 0;
        for (AboutResponseModel.CommitDetails commitDetails : commitDetailsList) {
            if (commitDetails.getSha().equals(BuildConfig.GIT_HASH)) {
                shaPosition = i;
            }
            i++;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final AboutBetaChangesBinding binding;

        public ItemViewHolder(AboutBetaChangesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(AboutCommitAdapterItemProgressBinding binding) {
            super(binding.getRoot());
        }
    }
}
