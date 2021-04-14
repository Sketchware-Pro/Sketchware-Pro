package mod.agus.jcoderz.beans;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;

public class ServiceBean {
    public FilePathUtil fpu = new FilePathUtil();
    public FileResConfig frc = new FileResConfig(this.numProj);
    public String numProj;

    public ServiceBean(String str) {
        this.numProj = str;
    }

    public String[] getServiceValue() {
        if (FileUtil.isExistFile(this.fpu.getManifestService(this.numProj))) {
            return (String[]) this.frc.getServiceManifestList().toArray(new String[this.frc.getServiceManifestList().size()]);
        }
        return new String[0];
    }

    public String getTitleDialog() {
        return "Select Background Service";
    }
}
