package com.besome.sketch.editor.manage.library.material3;

import com.besome.sketch.beans.ProjectLibraryBean;

import a.a.a.jC;

public class Material3LibraryManager {

    private final boolean isAppCompatEnabled;
    private final ProjectLibraryBean appCombatLibraryBean;

    public Material3LibraryManager(String sc_id) {
        this.appCombatLibraryBean = jC.c(sc_id).c();
        this.isAppCompatEnabled = appCombatLibraryBean.isEnabled();
    }

    public Material3LibraryManager(ProjectLibraryBean projectLibraryBean) {
        this.appCombatLibraryBean = projectLibraryBean;
        this.isAppCompatEnabled = projectLibraryBean.isEnabled();
    }

    public boolean isAppCompatEnabled() {
        return isAppCompatEnabled;
    }

    public boolean isMaterial3Enabled() {
        return isAppCompatEnabled && safeGet("material3");
    }

    public boolean isDynamicColorsEnabled() {
        return isMaterial3Enabled() && safeGet("dynamic_colors");
    }

    public boolean safeGet(String key) {
        if (appCombatLibraryBean.material3Configurations != null &&
                appCombatLibraryBean.material3Configurations.containsKey(key) &&
                appCombatLibraryBean.material3Configurations.get(key) instanceof Boolean keyValue) {
            return keyValue;
        }
        return false;
    }

    public ProjectLibraryBean getAppCombatLibraryBean() {
        return appCombatLibraryBean;
    }

}