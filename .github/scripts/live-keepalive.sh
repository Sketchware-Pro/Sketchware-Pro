#!/usr/bin/env bash
# Runs on the emulator for the live-link workflow. Installs the APK, opens the app,
# then keeps the job (and the emulator) alive for about 4h50m.
# Usage: live-keepalive.sh <path-to-apk>
set -uo pipefail

APK="$1"
PKG="pro.sketchware"

adb wait-for-device
adb shell 'while [ "$(getprop sys.boot_completed | tr -d "\r")" != "1" ]; do sleep 2; done'
adb install -r "$APK" || echo "install failed"
adb shell pm grant "$PKG" android.permission.READ_EXTERNAL_STORAGE >/dev/null 2>&1 || true
adb shell pm grant "$PKG" android.permission.WRITE_EXTERNAL_STORAGE >/dev/null 2>&1 || true
adb shell monkey -p "$PKG" -c android.intent.category.LAUNCHER 1 >/dev/null 2>&1 || true

END=$((SECONDS + 17400))
while [ "$SECONDS" -lt "$END" ]; do
  sleep 60
done
echo "Live window finished."
