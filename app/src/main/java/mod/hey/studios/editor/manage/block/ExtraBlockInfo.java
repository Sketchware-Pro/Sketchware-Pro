package mod.hey.studios.editor.manage.block;

public class ExtraBlockInfo {
    private String code = "";
    private int color = 0;
    public transient boolean isMissing;
    private String name = "";
    private int paletteColor = 0;
    private String spec = "";
    private String spec2 = "";

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public int getColor() {
        return this.color;
    }

    public int getPaletteColor() {
        return this.paletteColor;
    }

    public String getSpec() {
        return this.spec;
    }

    public String getSpec2() {
        return this.spec2;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public void setPaletteColor(int i) {
        this.paletteColor = i;
    }

    public void setSpec(String str) {
        this.spec = str;
    }

    public void setSpec2(String str) {
        this.spec2 = str;
    }
}
