#!/usr/bin/env bash
# Runs on the emulator (started by reactivecircus/android-emulator-runner).
# Usage: emulator-smoke.sh <path-to-apk>
set -uo pipefail

APK="$1"
PKG="pro.sketchware"
OUT="screenshots"
FINDER=".github/scripts/find_node.py"
mkdir -p "$OUT"
SUMMARY="$OUT/summary.md"

log() { echo "[smoke] $*"; echo "- $*" >> "$SUMMARY"; }

echo "# Emulator run" > "$SUMMARY"

adb wait-for-device
adb shell 'while [ "$(getprop sys.boot_completed | tr -d "\r")" != "1" ]; do sleep 2; done'
adb shell input keyevent 82 >/dev/null 2>&1 || true

log "Installing $APK"
adb install -r "$APK" || { log "install FAILED"; exit 1; }

adb shell pm grant "$PKG" android.permission.READ_EXTERNAL_STORAGE >/dev/null 2>&1 || true
adb shell pm grant "$PKG" android.permission.WRITE_EXTERNAL_STORAGE >/dev/null 2>&1 || true
adb shell pm grant "$PKG" android.permission.POST_NOTIFICATIONS >/dev/null 2>&1 || true

adb logcat -c
adb shell monkey -p "$PKG" -c android.intent.category.LAUNCHER 1 >/dev/null 2>&1
sleep 15

dump_ui() { # $1 = name
  adb shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1
  adb pull /sdcard/ui.xml "$OUT/$1.xml" >/dev/null 2>&1
}

snap() { # $1 = name
  adb exec-out screencap -p > "$OUT/$1.png"
  dump_ui "$1"
  log "Screenshot: $1.png"
}

# Tap the center of a view by resource-id name (e.g. create_new_project). Returns 1 if not found.
tap_id() {
  local coords
  coords=$(python3 "$FINDER" "$OUT/last.xml" "$1" 2>/dev/null) || return 1
  adb shell input tap $coords
  return 0
}

snap "01-projects"
cp "$OUT/01-projects.xml" "$OUT/last.xml" 2>/dev/null

if tap_id "create_new_project"; then
  sleep 2
  snap "02-create-sheet"
else
  log "create_new_project button not found (see 01-projects.xml)"
fi

# Close the sheet with back, then long-press the first project card if one exists.
adb shell input keyevent 4
sleep 1
coords=$(python3 "$FINDER" "$OUT/01-projects.xml" "project_name" 2>/dev/null) || coords=""
if [ -n "$coords" ]; then
  set -- $coords
  adb shell input swipe "$1" "$2" "$1" "$2" 900
  sleep 2
  snap "03-project-long-press"
else
  log "No project card found to long-press"
fi

# Crash check
if adb logcat -d | grep -q "FATAL EXCEPTION" && adb logcat -d | grep -q "$PKG"; then
  log "CRASH detected in logcat"
  adb logcat -d > "$OUT/logcat.txt"
  exit 1
fi
adb logcat -d > "$OUT/logcat.txt"
log "No crash detected"
exit 0
