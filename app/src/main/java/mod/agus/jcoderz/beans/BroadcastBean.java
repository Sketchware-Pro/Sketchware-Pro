package mod.agus.jcoderz.beans;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;

public class BroadcastBean {
    public FilePathUtil fpu = new FilePathUtil();
    public String numProj;
    public FileResConfig frc = new FileResConfig(numProj);

    public BroadcastBean(String str) {
        numProj = str;
    }

    public String[] getBroadcastValue() {
        if (FileUtil.isExistFile(fpu.getManifestBroadcast(numProj))) {
            return frc.getBroadcastManifestList().toArray(new String[0]);
        }

        return new String[0];
    }

    public String getTitleDialog() {
        return "Select Broadcast Receiver";
    }
}
