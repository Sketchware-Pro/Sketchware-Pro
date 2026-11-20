package com.besome.sketch.lib.ui;

import android.content.Context;
import java.util.ArrayList;
import a.a.a.DB;
import a.a.a.wq;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.GsonUtils;

/**
 * TEMPORARY MIGRATION UTILITY
 *
 * Migrates legacy custom colors & Material 3 attribute names from
 * SharedPreferences (DB "P24" / keys "P24I1", "P24I2") to the new
 * file-based storage at {@link wq#getCustomColors()}.
 *
 * Why: older versions stored these in SharedPreferences, which get wiped
 * when the app is uninstalled. The new storage lives in
 * .sketchware/data/system/ and survives uninstall.
 *
 * HOW TO REMOVE LATER
 * -------------------
 * Once enough time has passed that everyone is on the new version:
 *   1. Delete this file (LegacyCustomColorsMigrator.java).
 *   2. Delete the single line in ColorPickerDialog.initialize():
 *          LegacyCustomColorsMigrator.migrateIfNeeded(activity);
 *   Nothing else depends on this class.
 */
final class LegacyCustomColorsMigrator {

    private static final String PREF_NAME = "P24";
    private static final String KEY_COLORS = "P24I1";
    private static final String KEY_ATTRS = "P24I2";

    private LegacyCustomColorsMigrator() {}

    static void migrateIfNeeded(Context context) {
        DB pref = new DB(context, PREF_NAME);

        String rawColors = pref.f(KEY_COLORS);
        String rawAttrs = pref.f(KEY_ATTRS);

        // Nothing to migrate
        if ((rawColors == null || rawColors.isEmpty()) && (rawAttrs == null || rawAttrs.isEmpty())) {
            return;
        }

        Data data = readExistingFile();

        merge(rawColors, data.colors);
        merge(rawAttrs, data.attrs);

        FileUtil.writeFile(wq.getCustomColors(), GsonUtils.getGson().toJson(data));

        // Remove keys after successful write
        pref.a(KEY_COLORS);
        pref.a(KEY_ATTRS);
    }

    private static void merge(String raw, ArrayList<String> target) {
        if (raw == null || raw.isEmpty()) return;
        for (String item : raw.split(",")) {
            String val = item.trim();
            if (!val.isEmpty() && !target.contains(val)) {
                target.add(val);
            }
        }
    }

    private static Data readExistingFile() {
        Data data = new Data();
        try {
            if (FileUtil.isExistFile(wq.getCustomColors())) {
                Data existing = GsonUtils.getGson().fromJson(
                        FileUtil.readFile(wq.getCustomColors()), Data.class);
                if (existing != null) {
                    if (existing.colors != null) data.colors = existing.colors;
                    if (existing.attrs != null) data.attrs = existing.attrs;
                }
            }
        } catch (Exception ignored) {
        }
        return data;
    }

    private static class Data {
        ArrayList<String> colors = new ArrayList<>();
        ArrayList<String> attrs = new ArrayList<>();
    }
}