package mod.agus.jcoderz.beans;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;

public class ServiceBean {
    public FilePathUtil fpu = new FilePathUtil();
    public String numProj;
    public FileResConfig frc = new FileResConfig(numProj);

    public ServiceBean(String str) {
        numProj = str;
    }

    public String[] getServiceValue() {
        if (FileUtil.isExistFile(fpu.getManifestService(numProj))) {
            return frc.getServiceManifestList().toArray(new String[0]);
        }

        return new String[0];
    }

    public String getTitleDialog() {
        return "Select Background Service";
    }
}
