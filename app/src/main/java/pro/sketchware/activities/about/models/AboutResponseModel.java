package pro.sketchware.activities.about.models;

import com.google.gson.internal.LinkedTreeMap;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class AboutResponseModel {
    private String discordInviteLink;
    private ArrayList<TeamMember> team;
    private ArrayList<ChangeLogs> changelog;

    // Getters
    public String getDiscordInviteLink() {
        return discordInviteLink;
    }

    public ArrayList<TeamMember> getTeam() {
        return team;
    }

    public ArrayList<ChangeLogs> getChangelog() {
        return changelog;
    }

    public static class TeamMember {
        private String user_username;
        private String description;
        private String user_img;
        private boolean is_core_team;
        private boolean is_active;

        public String getMemberUsername() {
            return user_username;
        }

        public String getMemberImg() {
            return user_img;
        }

        public boolean isCoreTeamMember() {
            return is_core_team;
        }

        public boolean isActive() {
            return is_active;
        }

        public String getTitle() {
            return is_core_team ? "Core Team Members" : "Contributors";
        }

        public String getDescription() {
            return description.trim();
        }
    }

    public static class ChangeLogs {
        private String title;
        private String description;
        private long releaseDate;
        private boolean isBeta;
        private boolean isTitled;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public long getReleaseDate() {
            return releaseDate;
        }

        public boolean isBeta() {
            return isBeta;
        }

        public boolean isTitled() {
            return isTitled;
        }
    }

    public static class CommitDetails {

        private final LinkedTreeMap<String, Object> commit = new LinkedTreeMap<>();
        private final LinkedTreeMap<String, Object> author = new LinkedTreeMap<>();
        private String sha;

        public String getCommitterName() {
            String commiterName = safeGetValueFromMap(author, "login");
            return commiterName.isEmpty() ? "Deleted Account" : commiterName;
        }

        public String getCommitterImage() {
            return safeGetValueFromMap(author, "avatar_url");
        }

        public String getDescription() {
            return safeGetValueFromMap(commit, "message");
        }

        public String getSha() {
            return sha != null ? sha : "";
        }

        public String getCommitDate() {
            try {
                LinkedTreeMap<?, ?> committerDetails = (LinkedTreeMap<?, ?>) commit.get("committer");
                if (committerDetails == null) {
                    return Helper.getResString(R.string.github_api_date_unavailable);
                }

                String commitDateString = (String) committerDetails.get("date");
                if (commitDateString == null || commitDateString.isEmpty()) {
                    return Helper.getResString(R.string.github_api_date_unavailable);
                }

                OffsetDateTime commitDateTime = OffsetDateTime.parse(commitDateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                LocalDate commitDate = commitDateTime.toLocalDate();
                return formatCommitDate(commitDate);
            } catch (Exception e) {
                return Helper.getResString(R.string.github_api_date_unavailable);
            }
        }

        private String safeGetValueFromMap(LinkedTreeMap<String, Object> map, String key) {
            if (map == null || !map.containsKey(key)) {
                return "";
            }
            Object value = map.get(key);
            return value != null ? value.toString() : "";
        }

        private String formatCommitDate(LocalDate commitDate) {
            LocalDate today = LocalDate.now(ZoneOffset.UTC);

            if (commitDate.equals(today)) {
                return "Today";
            } else if (commitDate.equals(today.minusDays(1))) {
                return "Yesterday";
            } else if (commitDate.isAfter(today.minusWeeks(1))) {
                return "This week";
            } else if (commitDate.isAfter(today.minusWeeks(2))) {
                return "Last week";
            } else if (commitDate.isAfter(today.minusMonths(1))) {
                return "Last month";
            } else {
                long monthsAgo = ChronoUnit.MONTHS.between(commitDate.withDayOfMonth(1), today.withDayOfMonth(1));
                if (monthsAgo <= 12) {
                    return monthsAgo + (monthsAgo == 1 ? " month ago" : " months ago");
                }

                long yearsAgo = ChronoUnit.YEARS.between(commitDate, today);
                return yearsAgo + (yearsAgo == 1 ? " year ago" : " years ago");
            }
        }

    }

}
