package com.besome.sketch.editor.manage.library.material3;

import static pro.sketchware.SketchApplication.getContext;

import android.content.Context;

import androidx.annotation.StyleRes;

import com.besome.sketch.beans.ProjectLibraryBean;

import a.a.a.jC;
import pro.sketchware.R;
import pro.sketchware.utility.ThemeUtils;

public class Material3LibraryManager {

    private final boolean isEditingState;
    private final boolean isAppCompatEnabled;
    private final ProjectLibraryBean appCombatLibraryBean;
    private final Context context;

    public Material3LibraryManager(String sc_id) {
        this.isEditingState = false;
        this.appCombatLibraryBean = jC.c(sc_id).c();
        this.isAppCompatEnabled = appCombatLibraryBean.isEnabled();
        this.context = getContext();
    }

    public Material3LibraryManager(Context context, String sc_id) {
        this.context = context;
        this.isEditingState = false;
        this.appCombatLibraryBean = jC.c(sc_id).c();
        this.isAppCompatEnabled = appCombatLibraryBean.isEnabled();
    }

    public Material3LibraryManager(ProjectLibraryBean projectLibraryBean) {
        this.context = getContext();
        this.isEditingState = true;
        this.appCombatLibraryBean = projectLibraryBean;
        this.isAppCompatEnabled = projectLibraryBean.isEnabled();
    }

    public boolean isAppCompatEnabled() {
        return isAppCompatEnabled;
    }

    public boolean isMaterial3Enabled() {
        return isAppCompatEnabled && safeGetBoolean("material3");
    }

    public boolean isDynamicColorsEnabled() {
        return (isMaterial3Enabled() || isEditingState) && safeGetBoolean("dynamic_colors");
    }

    public String getTheme() {
        return (isMaterial3Enabled() || isEditingState) ? safeGetString("theme") : "DayNight";
    }

    private String safeGetString(String key) {
        if (appCombatLibraryBean.configurations != null &&
                appCombatLibraryBean.configurations.containsKey(key) &&
                appCombatLibraryBean.configurations.get(key) instanceof String keyValue) {
            return keyValue;
        }
        return "";
    }

    private boolean safeGetBoolean(String key) {
        if (appCombatLibraryBean.configurations != null &&
                appCombatLibraryBean.configurations.containsKey(key) &&
                appCombatLibraryBean.configurations.get(key) instanceof Boolean keyValue) {
            return keyValue;
        }
        return false;
    }

    @StyleRes
    public int getViewEditorThemeOverlay() {
        if (!isMaterial3Enabled()) {
            return R.style.ThemeOverlay_SketchwarePro_ViewEditor;
        }
        boolean isDark = isDarkVariant();

        if (isDynamicColorsEnabled()) {
            return isDark
                    ? R.style.ThemeOverlay_SketchwarePro_ViewEditor_Material3_Dark
                    : R.style.ThemeOverlay_SketchwarePro_ViewEditor_Material3_Light;
        } else {
            return isDark
                    ? R.style.ThemeOverlay_SketchwarePro_ViewEditor_Material3_NON_DYNAMIC_Dark
                    : R.style.ThemeOverlay_SketchwarePro_ViewEditor_Material3_NON_DYNAMIC_Light;
        }
    }

    public boolean isDarkVariant() {
        return getTheme().equals("Dark") ||
                (!getTheme().equals("Light") && ThemeUtils.isDarkThemeEnabled(context));
    }

    public boolean canUseNightVariantColors() {
        if (!isMaterial3Enabled()) {
            return false;
        }

        return getTheme().equals("Dark") ||
                (!getTheme().equals("Light") && ThemeUtils.isDarkThemeEnabled(context));
    }

    public ProjectLibraryBean getAppCombatLibraryBean() {
        return appCombatLibraryBean;
    }

}