import requests
import json
from datetime import datetime, timedelta, timezone
import os
import logging

# Logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# Constants
GITHUB_REPO = os.getenv("GITHUB_REPOSITORY", "Sketchware-Pro/Sketchware-Pro")
GITHUB_TOKEN = os.getenv("GH_ABOUT_APP_WORKFLOW_TOKEN")
GITHUB_API_BASE = "https://api.github.com"
GITHUB_ABOUT_APP_FILE = "about.json"

HEADERS = {
    "Accept": "application/vnd.github+json",
    "X-GitHub-Api-Version": "2022-11-28",
}

if GITHUB_TOKEN:
    HEADERS["Authorization"] = f"Bearer {GITHUB_TOKEN}"

def get_collaborators():
    logging.info("Fetching collaborators")
    url = f"{GITHUB_API_BASE}/repos/{GITHUB_REPO}/collaborators"
    response = requests.get(url, headers=HEADERS)
    if response.status_code != 200:
        logging.error(f"Failed to fetch collaborators: {response.status_code}")
        return None
    logging.info("Collaborators fetched successfully")
    return response.json()

def get_contributors():
    logging.info("Fetching contributors")
    url = f"{GITHUB_API_BASE}/repos/{GITHUB_REPO}/contributors"
    response = requests.get(url, headers=HEADERS)
    if response.status_code != 200:
        logging.error(f"Failed to fetch contributors: {response.status_code}")
        return None
    logging.info("Contributors fetched successfully")
    return response.json()

def get_user_bio(username):
    logging.info(f"Fetching bio for user: {username}")
    url = f"{GITHUB_API_BASE}/users/{username}"
    response = requests.get(url, headers=HEADERS)
    bio = None

    if response.status_code == 200:
        logging.info(f"Bio fetched for user: {username}")
        bio = response.json().get("bio")
        if bio is None:
            created_at = datetime.strptime(response.json().get("created_at"), "%Y-%m-%dT%H:%M:%SZ").strftime("%m/%Y")
            bio = f"Joined GitHub on {created_at} with {response.json().get('public_repos')} public repositories, and {response.json().get('followers')} followers."
    else:
        logging.error(f"Failed to fetch bio for user: {username}")

    return bio

def has_recent_activity(username):
    logging.info(f"Checking recent activity for user: {username}")
    url = f"{GITHUB_API_BASE}/repos/{GITHUB_REPO}/commits"
    params = {
        "author": username,
        "since": (datetime.now(timezone.utc) - timedelta(days=30)).isoformat()
    }
    response = requests.get(url, headers=HEADERS, params=params)
    if response.status_code == 200:
        logging.info(f"Recent activity checked for user: {username}")
        return len(response.json()) > 0
    else:
        logging.error(f"Failed to check recent activity for user: {username}")
        return False

def update_team_data(collaborators, contributors):
    try:
        # اگر فایل about.json وجود نداشت، یک ساختار ساده بساز
        if os.path.exists(GITHUB_ABOUT_APP_FILE):
            with open(GITHUB_ABOUT_APP_FILE, "r") as f:
                data = json.load(f)
        else:
            data = {"team": []}

        updated_team = []

        logging.info("Updating team data with collaborators")
        for user in collaborators:
            updated_team.append({
                "user_username": user.get("login"),
                "user_img": user.get("avatar_url"),
                "description": get_user_bio(user.get("login")),
                "is_core_team": True,
                "is_active": has_recent_activity(user.get("login")),
            })

        collaborator_usernames = {user["user_username"] for user in updated_team}

        logging.info("Updating team data with contributors")
        for user in contributors:
            if user.get("login") not in collaborator_usernames:
                updated_team.append({
                    "user_username": user.get("login"),
                    "user_img": user.get("avatar_url"),
                    "description": get_user_bio(user.get("login")),
                    "is_core_team": False,
                    "is_active": has_recent_activity(user.get("login")),
                })

        data["team"] = updated_team

        logging.info("Writing updated team data to file")
        with open(GITHUB_ABOUT_APP_FILE, "w") as file:
            json.dump(data, file, indent=2)
        logging.info("Team data updated successfully")

    except Exception as e:
        logging.error(f"An error occurred while updating team data: {e}")

def main():
    logging.info("Starting update_app_data script")

    if not GITHUB_TOKEN:
        logging.warning("GH_ABOUT_APP_WORKFLOW_TOKEN is not set. Skipping update.")
        return

    collaborators = get_collaborators()
    contributors = get_contributors()

    if collaborators is not None and contributors is not None:
        update_team_data(collaborators, contributors)
    else:
        logging.warning("Could not fetch collaborators or contributors. Skipping update.")

    logging.info("Finished update_app_data script")

if __name__ == "__main__":
    main()
