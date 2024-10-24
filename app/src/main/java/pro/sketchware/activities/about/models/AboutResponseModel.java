package pro.sketchware.activities.about.models;

import java.util.ArrayList;

public class AboutResponseModel {
    private String discordInviteLink;
    private ArrayList<TeamMember> moddersteam;
    private ArrayList<ChangeLogs> changelog;

    // Getters
    public String getDiscordInviteLink() {
        return discordInviteLink;
    }

    public ArrayList<TeamMember> getTeam() {
        return moddersteam;
    }

    public ArrayList<ChangeLogs> getChangelog() {
        return changelog;
    }

    public static class TeamMember {
        private String modder_username;
        private String modder_description;
        private String modder_img;
        private boolean isTitled;
        private boolean isMainModder;
        private boolean isActive;
        private String title;

        public String getMemberUsername() {
            return modder_username;
        }

        public String getMemberDescription() {
            return modder_description;
        }

        public String getMemberImg() {
            return modder_img;
        }

        public boolean isTitled() {
            return isTitled;
        }

        public boolean isMainModder() {
            return isMainModder;
        }

        public boolean isActive() {
            return isActive;
        }

        public String getTitle() {
            return title;
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
}
