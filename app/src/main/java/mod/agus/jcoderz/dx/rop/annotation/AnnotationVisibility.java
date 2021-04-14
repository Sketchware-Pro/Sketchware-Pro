package mod.agus.jcoderz.dx.rop.annotation;

import mod.agus.jcoderz.dx.util.ToHuman;

public enum AnnotationVisibility implements ToHuman {
    RUNTIME("runtime"),
    BUILD("build"),
    SYSTEM("system"),
    EMBEDDED("embedded");
    
    private final String human;

    private AnnotationVisibility(String str) {
        this.human = str;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return this.human;
    }
}
