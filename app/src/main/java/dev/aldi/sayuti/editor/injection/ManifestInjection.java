package dev.aldi.sayuti.editor.injection;

import java.util.ArrayList;

import com.sketchware.remod.xml.XmlBuilder;
import a.a.a.jq;
import mod.agus.jcoderz.lib.FileUtil;

public class ManifestInjection {

    public ArrayList arr;
    public jq jq;
    public String path;
    public String replace;
    public String value;

    public ManifestInjection(jq jqVar, ArrayList arrayList) {
        jq = jqVar;
        arr = arrayList;
    }

    public void b(XmlBuilder nx, String str, String str2) {
        path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + jq.sc_id + "/injection/manifest/" + str;
        if (FileUtil.isExistFile(path)) {
            FileUtil.readFile(path).equals("");
        }
    }
}