package mod.ilyasse.activities.about;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.sketchware.remod.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AboutAdapters {
    public static class TeamRecyclerAdapter extends RecyclerView.Adapter<TeamRecyclerAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> team;
        private final Context context;

        public TeamRecyclerAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
            team = data;
            this.context = context;
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
            Object contributorImg = team.get(position).get("modder_img");
            if (contributorImg instanceof String) {
                circularImage(holder.icon, (String) contributorImg);
            }

            Object contributorUsername = team.get(position).get("modder_username");
            if (contributorUsername instanceof String) {
                holder.username.setText((String) contributorUsername);
            }

            Object contributorDescription = team.get(position).get("modder_description");
            if (contributorDescription instanceof String) {
                holder.description.setText((String) contributorDescription);
            }

            Object isTitled = team.get(position).get("isTitled");
            boolean isTitle = false;
            if (isTitled instanceof String) {
                isTitle = Boolean.parseBoolean((String) isTitled);
            } else if (isTitled instanceof Boolean) {
                isTitle = (boolean) isTitled;
            }

            Object titleText = team.get(position).get("title");
            if (isTitle) {
                if (titleText instanceof String) {
                    holder.title.setText((String) titleText);
                    holder.title.setVisibility(View.VISIBLE);
                } else {
                    holder.title.setVisibility(View.GONE);
                }
            } else {
                holder.title.setVisibility(View.GONE);
            }


            Object isPartofTeam = team.get(position).get("isMainModder");
            boolean isPartofTeamBool = false;
            if (isPartofTeam instanceof String) {
                isPartofTeamBool = Boolean.parseBoolean((String) isPartofTeam);
            } else if (isPartofTeam instanceof Boolean) {
                isPartofTeamBool = (boolean) isPartofTeam;
            }

            if (isPartofTeamBool) {
                advancedCorners(holder.sidebar, "#008DCD");
            } else {
                advancedCorners(holder.sidebar, "#00CDAB");
            }

            if (isPartofTeamBool) {
                holder.status.setVisibility(View.VISIBLE);
                Object isActive = team.get(position).get("isActive");
                boolean isActiveBool = false;
                if (isActive instanceof String) {
                    isActiveBool = Boolean.parseBoolean((String) isActive);
                } else if (isActive instanceof Boolean) {
                    isActiveBool = (boolean) isActive;
                }
                if (isActiveBool) {
                    holder.status.setText("Active");
                    rippleRound(holder.status, "#13cc9d", "#13cc9d", 100);
                } else {
                    holder.status.setText("Inactive");
                    rippleRound(holder.status, "#676767", "#676767", 100);
                }
            } else {
                holder.status.setVisibility(View.GONE);
            }
            
            Object githubUrl = team.get(position).get("modder_github");
            if (githubUrl != null) {
                holder.content.setOnClickListener(v -> {
                   Intent intent = new Intent(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse((String) githubUrl));
                   ((Activity) context).startActivity(intent);
                });
            }
        }

        @Override
        public int getItemCount() {
            if (team != null)
                return team.size();
            return 0;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final LinearLayout content;
            public final TextView title;
            public final LinearLayout sidebar;
            public final ImageView icon;
            public final TextView username;
            public final TextView description;
            public final TextView status;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                content = itemView.findViewById(R.id.member_content);
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

        private static final String CHANGELOG_KEY_SHOWING_ADDITIONAL_INFO = "showingAdditionalInfo";
        private final ArrayList<HashMap<String, Object>> changelog;

        public ChangelogRecyclerAdapter(ArrayList<HashMap<String, Object>> data) {
            changelog = data;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            var view = inflater.inflate(R.layout.about_changelog, null);
            var layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            return new ChangelogRecyclerAdapter.ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HashMap<String, Object> release = changelog.get(position);

            Object isTitled = release.get("isTitled");
            boolean isTitle = false;
            if (isTitled instanceof String) {
                isTitle = Boolean.parseBoolean((String) isTitled);
            } else if (isTitled instanceof Boolean) {
                isTitle = (boolean) isTitled;
            }

            if (isTitle) {
                holder.title.setVisibility(View.VISIBLE);
                Object titleText = release.get("title");

                if (titleText instanceof String) {
                    holder.title.setText((String) titleText);
                } else {
                    holder.title.setText("We've messed something up, sorry for the inconvenience!\n" +
                            "(Details: Invalid data type of \"title\")");
                }
            } else {
                holder.title.setVisibility(View.GONE);
            }


            boolean isBetaVersion = false;
            Object isBeta = release.get("isBeta");
            if (isBeta instanceof String) {
                isBetaVersion = Boolean.parseBoolean((String) isBeta);
            } else if (isBeta instanceof Boolean) {
                isBetaVersion = (boolean) isBeta;
            }

            boolean previousIsBetaValueDiffers = true;
            if (position != 0) {
                HashMap<String, Object> previousChangelog = changelog.get(position - 1);

                Object previousIsBeta = previousChangelog.get("isBeta");

                if (previousIsBeta instanceof String) {
                    previousIsBetaValueDiffers = Boolean.parseBoolean((String) previousIsBeta) != isBetaVersion;
                } else if (previousIsBeta instanceof Boolean) {
                    previousIsBetaValueDiffers = ((boolean) previousIsBeta) != isBetaVersion;
                }
            }

            holder.variant.setVisibility(previousIsBetaValueDiffers ? View.VISIBLE : View.GONE);
            if (previousIsBetaValueDiffers) {
                holder.variant.setText(isBetaVersion ? "Beta" : "Official");
            }


            Object releaseDate = release.get("releaseDate");

            if (releaseDate instanceof Double) {
                holder.releasedOn.setVisibility(View.VISIBLE);
                long timestamp = ((Double) releaseDate).longValue();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                holder.releasedOn.setText("Released on: " + formatter.format(new Date(timestamp)));
            } else {
                holder.releasedOn.setVisibility(View.GONE);
            }

            Object description = release.get("description");

            if (description instanceof String) {
                holder.subtitle.setText((String) description);
                Linkify.addLinks(holder.subtitle, Linkify.WEB_URLS);
            } else {
                holder.subtitle.setText("We've messed something up, sorry for the inconvenience!\n" +
                        "(Details: Invalid data type of \"description\")");
            }

            boolean showingAdditionalInfo = false;
            Object showingAdditionalInfoObject;
            if (release.containsKey(CHANGELOG_KEY_SHOWING_ADDITIONAL_INFO) &&
                    (showingAdditionalInfoObject = release.get(CHANGELOG_KEY_SHOWING_ADDITIONAL_INFO)) instanceof Boolean &&
                    ((Boolean) showingAdditionalInfoObject)) {
                showingAdditionalInfo = true;
            }

            holder.viewAdditionalInfo.setVisibility(showingAdditionalInfo ? View.VISIBLE : View.GONE);
            holder.arrow.setRotation(showingAdditionalInfo ? 0 : 180);

            rippleRound(holder.logBackground, "#ffffff", "#1F000000", 0);
            rippleRound(holder.arrow, "#ffffff", "#1F000000", 90);
            holder.arrow.setOnClickListener(v -> holder.logBackground.performClick());

            holder.logBackground.setOnClickListener(v -> {
                if (holder.viewAdditionalInfo.getVisibility() == View.VISIBLE) {
                    shadAnim(holder.arrow, "rotation", 180, 220);
                    holder.viewAdditionalInfo.setVisibility(View.GONE);
                    release.put(CHANGELOG_KEY_SHOWING_ADDITIONAL_INFO, false);
                } else {
                    shadAnim(holder.arrow, "rotation", 0, 220);
                    holder.viewAdditionalInfo.setVisibility(View.VISIBLE);
                    release.put(CHANGELOG_KEY_SHOWING_ADDITIONAL_INFO, true);
                }
                animateLayoutChanges(holder.logBackground);

                notifyItemChanged(position);
            });
            if (0 == position) {
                advancedCorners(holder.leftLine, "#2f55ed");
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

    private static void advancedCorners(View view, String color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(color));
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
        //i used this instead of the xml attribute because this one looks better and smoother.
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration((short) 300);
        TransitionManager.beginDelayedTransition(view, autoTransition);
    }

    private static void rippleRound(View view, String focus, String pressed, double round) {
        GradientDrawable GG = new GradientDrawable();
        GG.setColor(Color.parseColor(focus));
        GG.setCornerRadius((float) round);
        RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(pressed)}), GG, null);
        view.setBackground(RE);
    }
}
