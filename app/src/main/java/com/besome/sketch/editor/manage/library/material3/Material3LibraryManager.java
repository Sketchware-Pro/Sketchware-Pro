package com.besome.sketch.editor.manage.library.material3;

import com.besome.sketch.beans.ProjectLibraryBean;

import a.a.a.jC;

public class Material3LibraryManager {

    private final boolean isEditingState;
    private final boolean isAppCompatEnabled;
    private final ProjectLibraryBean appCombatLibraryBean;

    public Material3LibraryManager(String sc_id) {
        this.isEditingState = false;
        this.appCombatLibraryBean = jC.c(sc_id).c();
        this.isAppCompatEnabled = appCombatLibraryBean.isEnabled();
    }

    public Material3LibraryManager(ProjectLibraryBean projectLibraryBean) {
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

    public ProjectLibraryBean getAppCombatLibraryBean() {
        return appCombatLibraryBean;
    }

}