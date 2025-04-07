package pro.sketchware.beans;

public class ResourceBean {
    private final String resPath;
    private final String resName;
    private final String resFullName;

    public ResourceBean(String resPath, String resName, String resFullName) {
        this.resPath = resPath;
        this.resName = resName;
        this.resFullName = resFullName;
    }

    public String getResPath() {
        return resPath;
    }

    public String getResName() {
        return resName;
    }

    public String getResFullName() {
        return resFullName;
    }
}