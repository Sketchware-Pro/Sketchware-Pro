package pro.sketchware.activities.about.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AboutAppViewModel extends ViewModel {
    private final MutableLiveData<String> discordInviteLink = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<AboutResponseModel.TeamMember>> teamMembers = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<AboutResponseModel.ChangeLogs>> changelog = new MutableLiveData<>();

    public LiveData<String> getDiscordInviteLink() {
        return discordInviteLink;
    }

    public void setDiscordInviteLink(String discordInviteLink) {
        this.discordInviteLink.setValue(discordInviteLink);
    }

    public LiveData<ArrayList<AboutResponseModel.TeamMember>> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<AboutResponseModel.TeamMember> teamMembers) {
        this.teamMembers.setValue(teamMembers);
    }

    public LiveData<ArrayList<AboutResponseModel.ChangeLogs>> getChangelog() {
        return changelog;
    }

    public void setChangelog(ArrayList<AboutResponseModel.ChangeLogs> changelog) {
        this.changelog.setValue(changelog);
    }
}