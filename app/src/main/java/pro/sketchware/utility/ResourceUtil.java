package pro.sketchware.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pro.sketchware.beans.ResourceBean;

public class ResourceUtil {
    private final String sc_id;
    private final String resFolder;

    public ResourceUtil(String sc_id, String resFolder) {
        this.sc_id = sc_id;
        this.resFolder = resFolder;
    }

    public List<ResourceBean> getResource() {
        ArrayList<String> fileList = new ArrayList<>();
        FileUtil.listDir(new FilePathUtil().getPathResource(sc_id) + File.separator + resFolder, fileList);
        return getResourceFiles(fileList);
    }

    public String getResourcePathFromName(String resName) {
        return getResource().stream()
                .filter(bean -> bean.getResName().equals(resName))
                .findFirst()
                .map(ResourceBean::getResPath)
                .orElse("");
    }

    private ArrayList<ResourceBean> getResourceFiles(ArrayList<String> resPaths) {
        return resPaths.stream()
                .map(resPath -> {
                    File resFile = new File(resPath);
                    String resFullName = resFile.getName();
                    String resName = resFullName.substring(0, resFullName.lastIndexOf('.'));
                    return new ResourceBean(resPath, resName, resFullName);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}