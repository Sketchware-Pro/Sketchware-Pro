package com.besome.sketch.editor.manage.common;

import android.content.Intent;

/**
 * Defines the supported resource types handled by AddResourceActivity.
 */
public enum ResourceType {
    IMAGE,
    SOUND,
    FONT;

    public static final String EXTRA_RESOURCE_TYPE = "resource_type";
    public static final String EXTRA_COLLECTION_MODE = "is_collection_mode";

    /**
     * Resolves the resource type from an explicit type extra or legacy extras.
     */
    public static ResourceType fromIntent(Intent intent) {
        if (intent == null) {
            return IMAGE;
        }

        String explicitType = intent.getStringExtra(EXTRA_RESOURCE_TYPE);
        if (explicitType != null) {
            try {
                return ResourceType.valueOf(explicitType);
            } catch (IllegalArgumentException ignored) {
                // Continue with legacy extra detection.
            }
        }

        if (intent.hasExtra("project_fonts")
                || intent.hasExtra("fonts")
                || intent.hasExtra("font_names")
                || intent.getIntExtra("request_code", -1) == 272) {
            return FONT;
        }

        if (intent.hasExtra("project_sounds")
                || intent.hasExtra("sounds")
                || intent.hasExtra("sound_names")
                || intent.getIntExtra("request_code", -1) == 270) {
            return SOUND;
        }

        return IMAGE;
    }
}
