package mod.agus.jcoderz.beans;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;

public class BroadcastBean {
    public FilePathUtil fpu = new FilePathUtil();
    public FileResConfig frc = new FileResConfig(this.numProj);
    public String numProj;

    public BroadcastBean(String str) {
        this.numProj = str;
    }

    public String[] getBroadcastValue() {
        if (FileUtil.isExistFile(this.fpu.getManifestBroadcast(this.numProj))) {
            return (String[]) this.frc.getBroadcastManifestList().toArray(new String[this.frc.getBroadcastManifestList().size()]);
        }
        return new String[0];
    }

    public String getTitleDialog() {
        return "Select Broadcast Receiver";
    }
}
