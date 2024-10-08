package mod.ilyasse.activities.about.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.text.util.Linkify;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.color.MaterialColors;
import com.sketchware.remod.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import mod.ilyasse.activities.about.models.AboutResponseModel;

public class AboutAdapters {

    public static class TeamRecyclerAdapter extends RecyclerView.Adapter<TeamRecyclerAdapter.ViewHolder> {

        private final ArrayList<AboutResponseModel.TeamMember> team;

        public TeamRecyclerAdapter(ArrayList<AboutResponseModel.TeamMember> data) {
            team = data;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            var view = inflater.inflate(R.layout.about_teamview, null);
            var layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AboutResponseModel.TeamMember member = team.get(position);

            // Load image
            String contributorImg = member.getMemberImg();
            if (contributorImg != null) {
                circularImage(holder.icon, contributorImg);
            }

            // Set username
            String contributorUsername = member.getMemberUsername();
            if (contributorUsername != null) {
                holder.username.setText(contributorUsername);
            }

            // Set description
            String contributorDescription = member.getMemberDescription();
            if (contributorDescription != null) {
                holder.description.setText(contributorDescription);
            }

            // Handle title
            boolean isTitled = member.isTitled();
            if (isTitled) {
                String titleText = member.getTitle();
                if (titleText != null) {
                    holder.title.setText(titleText);
                    holder.title.setVisibility(View.VISIBLE);
                } else {
                    holder.title.setVisibility(View.GONE);
                }
            } else {
                holder.title.setVisibility(View.GONE);
            }

            // Handle sidebar color and status
            boolean isMainModder = member.isMainModder();
            if (isMainModder) {
                advancedCorners(holder.sidebar, MaterialColors.getColor(holder.sidebar, com.google.android.material.R.attr.colorPrimary));
            } else {
                advancedCorners(holder.sidebar, MaterialColors.getColor(holder.sidebar, R.attr.colorGreen));
            }

            if (isMainModder) {
                holder.status.setVisibility(View.VISIBLE);
                boolean isActive = member.isActive();
                int activeBackgroundColor;
                int activeBackgroundTextColor;
                if (isActive) {
                    holder.status.setText("Active");
                    activeBackgroundColor = MaterialColors.getColor(holder.status, R.attr.colorCoolGreenContainer);
                    activeBackgroundTextColor = MaterialColors.getColor(holder.status, R.attr.colorOnCoolGreenContainer);
                } else {
                    holder.status.setText("Inactive");
                    activeBackgroundColor = MaterialColors.getColor(holder.status, R.attr.colorAmberContainer);
                    activeBackgroundTextColor = MaterialColors.getColor(holder.status, R.attr.colorOnAmberContainer);
                }
                rippleRound(holder.status, activeBackgroundColor, activeBackgroundColor, 100);
                holder.status.setTextColor(activeBackgroundTextColor);
            } else {
                holder.status.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return team != null ? team.size() : 0;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView title;
            public final LinearLayout sidebar;
            public final ImageView icon;
            public final TextView username;
            public final TextView description;
            public final TextView status;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
                sidebar = itemView.findViewById(R.id.view_leftline);
                icon = itemView.findViewById(R.id.img_user_icon);
                username = itemView.findViewById(R.id.tv_user_name);
                status = itemView.findViewById(R.id.tv_status);
                description = itemView.findViewById(R.id.tv_description);
            }
        }
    }

    public static class ChangelogRecyclerAdapter extends RecyclerView.Adapter<ChangelogRecyclerAdapter.ViewHolder> {

        private final ArrayList<AboutResponseModel.ChangeLogs> changelog;
        private final Set<Integer> showingAdditionalInfoPositions = new HashSet<>();

        public ChangelogRecyclerAdapter(ArrayList<AboutResponseModel.ChangeLogs> data) {
            changelog = data;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            var view = inflater.inflate(R.layout.about_changelog, null);
            var layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AboutResponseModel.ChangeLogs release = changelog.get(position);

            // Handle title
            boolean isTitled = release.isTitled();
            if (isTitled) {
                String titleText = release.getTitle();
                if (titleText != null) {
                    holder.title.setText(titleText);
                    holder.title.setVisibility(View.VISIBLE);
                } else {
                    holder.title.setText("We've messed something up, sorry for the inconvenience!\n" +
                            "(Details: Invalid data type of \"title\")");
                    holder.title.setVisibility(View.VISIBLE);
                }
            } else {
                holder.title.setVisibility(View.GONE);
            }

            // Handle variant
            boolean isBetaVersion = release.isBeta();
            boolean previousIsBetaValueDiffers = true;
            if (position != 0) {
                boolean previousIsBeta = changelog.get(position - 1).isBeta();
                previousIsBetaValueDiffers = previousIsBeta != isBetaVersion;
            }

            holder.variant.setVisibility(previousIsBetaValueDiffers ? View.VISIBLE : View.GONE);
            if (previousIsBetaValueDiffers) {
                holder.variant.setText(isBetaVersion ? "Beta" : "Official");
            }

            // Handle release date
            long releaseDate = release.getReleaseDate();
            if (releaseDate > 0) {
                holder.releasedOn.setVisibility(View.VISIBLE);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                holder.releasedOn.setText("Released on: " + formatter.format(new Date(releaseDate)));
            } else {
                holder.releasedOn.setVisibility(View.GONE);
            }

            // Handle description
            String description = release.getDescription();
            if (description != null) {
                holder.subtitle.setText(description);
                Linkify.addLinks(holder.subtitle, Linkify.WEB_URLS);
            } else {
                holder.subtitle.setText("We've messed something up, sorry for the inconvenience!\n" +
                        "(Details: Invalid data type of \"description\")");
            }

            // Handle additional info visibility
            boolean showingAdditionalInfo = showingAdditionalInfoPositions.contains(position);

            holder.viewAdditionalInfo.setVisibility(showingAdditionalInfo ? View.VISIBLE : View.GONE);
            holder.arrow.setRotation(showingAdditionalInfo ? 0 : 180);

            holder.arrow.setOnClickListener(v -> holder.logBackground.performClick());

            holder.logBackground.setOnClickListener(v -> {
                if (showingAdditionalInfoPositions.contains(position)) {
                    shadAnim(holder.arrow, "rotation", 180, 220);
                    holder.viewAdditionalInfo.setVisibility(View.GONE);
                    showingAdditionalInfoPositions.remove(position);
                } else {
                    shadAnim(holder.arrow, "rotation", 0, 220);
                    holder.viewAdditionalInfo.setVisibility(View.VISIBLE);
                    showingAdditionalInfoPositions.add(position);
                }
                animateLayoutChanges(holder.logBackground);
            });

            if (position == 0) {
                advancedCorners(holder.leftLine, Color.parseColor("#2f55ed"));
            } else {
                holder.leftLine.setBackground(null);
            }
        }

        @Override
        public int getItemCount() {
            return changelog.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView variant;
            public final LinearLayout leftLine;
            public final TextView title;
            public final TextView releasedOn;
            public final TextView subtitle;
            public final LinearLayout logBackground;
            public final LinearLayout viewAdditionalInfo;
            public final ImageButton arrow;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                variant = itemView.findViewWithTag("tv_variant");
                leftLine = itemView.findViewById(R.id.view_leftline);
                title = itemView.findViewById(R.id.tv_title);
                releasedOn = itemView.findViewById(R.id.tv_release_note);
                subtitle = itemView.findViewById(R.id.tv_sub_title);
                logBackground = itemView.findViewWithTag("log_background");
                viewAdditionalInfo = itemView.findViewWithTag("view_additional_info");
                arrow = itemView.findViewWithTag("ic_arrow");
            }
        }
    }

    private static void circularImage(ImageView image, String url) {
        Glide.with(image.getContext())
                .load(url)
                .placeholder(R.drawable.ic_user)
                .into(image);
    }

    private static void advancedCorners(View view, int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadii(new float[]{0, 0, 30, 30, 30, 30, 0, 0});
        view.setBackground(gd);
    }

    private static void shadAnim(View view, String propertyName, double value, double duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(view);
        anim.setPropertyName(propertyName);
        anim.setFloatValues((float) value);
        anim.setDuration((long) duration);
        anim.start();
    }

    private static void animateLayoutChanges(LinearLayout view) {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration((short) 300);
        TransitionManager.beginDelayedTransition(view, autoTransition);
    }

    private static void rippleRound(View view, int focus, int pressed, double round) {
        GradientDrawable GG = new GradientDrawable();
        GG.setColor(focus);
        GG.setCornerRadius((float) round);
        RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{pressed}), GG, null);
        view.setBackground(RE);
    }
}
