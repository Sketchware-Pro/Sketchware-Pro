package pro.sketchware.activities.about.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AboutAppViewModel extends ViewModel {
    private final MutableLiveData<String> discordInviteLink = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<AboutResponseModel.TeamMember>> teamMembers = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<AboutResponseModel.ChangeLogs>> changelog = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<AboutResponseModel.CommitDetails>> commitDetailsList = new MutableLiveData<>();

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

    public ArrayList<String> getCoreTeamMembers() {
        ArrayList<String> coreTeam = new ArrayList<>();
        if (teamMembers.getValue() != null) {
            for (AboutResponseModel.TeamMember teamMember : teamMembers.getValue()) {
                if (teamMember.isCoreTeamMember()) {
                    coreTeam.add(teamMember.getMemberUsername().toLowerCase());
                }
            }
        }
        return coreTeam;
    }

    public LiveData<ArrayList<AboutResponseModel.ChangeLogs>> getChangelog() {
        return changelog;
    }

    public void setChangelog(ArrayList<AboutResponseModel.ChangeLogs> changelog) {
        this.changelog.setValue(changelog);
    }

    public LiveData<ArrayList<AboutResponseModel.CommitDetails>> getCommitDetailsList() {
        return commitDetailsList;
    }

    public void setCommitDetailsList(ArrayList<AboutResponseModel.CommitDetails> newCommitDetails) {
        commitDetailsList.setValue(newCommitDetails);
    }
}
