import requests
import json
from datetime import datetime, timedelta, timezone
import os

# Constants
GITHUB_REPO = "Sketchware-Pro/Sketchware-Pro"
GITHUB_TOKEN = os.getenv("GH_ABOUT_APP_WORKFLOW_TOKEN")
GITHUB_API_BASE = "https://api.github.com"
GITHUB_ABOUT_APP_FILE = "about.json"
GITHUB_ABOUT_APP_URL = f"https://raw.githubusercontent.com/Sketchware-Pro/Sketchware-Pro/refs/heads/host/{GITHUB_ABOUT_APP_FILE}"

HEADERS = {
  "Accept": "application/vnd.github+json",
  "Authorization": f"Bearer {GITHUB_TOKEN}",
  "X-GitHub-Api-Version": "2022-11-28",
}

def get_collaborators():
  url = f"{GITHUB_API_BASE}/repos/{GITHUB_REPO}/collaborators"
  response = requests.get(url, headers=HEADERS)
  if response.status_code != 200:
    return None
  return response.json()

def get_contributors():
  url = f"{GITHUB_API_BASE}/repos/{GITHUB_REPO}/contributors"
  response = requests.get(url, headers=HEADERS)
  if response.status_code != 200:
    return None
  return response.json()

def has_recent_activity(username):
  url = f"{GITHUB_API_BASE}/repos/{GITHUB_REPO}/commits"
  params = {"author": username, "since": (datetime.now(timezone.utc) - timedelta(days=30)).isoformat() + "Z"}
  response = requests.get(url, headers=HEADERS, params=params)
  if response.status_code == 200:
    return len(response.json()) > 0
  else:
    return False

def update_team_data(collaborators, contributors):
  try:
    response = requests.get(GITHUB_ABOUT_APP_URL)
    if response.status_code != 200:
      return

    data = response.json()
    updated_team = []

    for user in collaborators:
      updated_team.append({
        "user_username": user.get("login"),
        "user_img": user.get("avatar_url"),
        "is_core_team": True,
        "is_active": has_recent_activity(user.get("login")),
      })

    collaborator_usernames = {user["user_username"] for user in updated_team}
    for user in contributors:
      if user.get("login") not in collaborator_usernames:
        updated_team.append({
          "user_username": user.get("login"),
          "user_img": user.get("avatar_url"),
          "is_core_team": False,
          "is_active": has_recent_activity(user.get("login")),
        })

    data["team"] = updated_team

    with open(GITHUB_ABOUT_APP_FILE, "w") as file:
      json.dump(data, file, indent=2)

  except Exception as e:
    pass

def main():
  collaborators = get_collaborators()
  contributors = get_contributors()
  if collaborators is not None and contributors is not None:
    update_team_data(collaborators, contributors)


if __name__ == "__main__":
  main()
