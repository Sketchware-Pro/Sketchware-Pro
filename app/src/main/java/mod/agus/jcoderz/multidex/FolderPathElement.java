package mod.agus.jcoderz.multidex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

class FolderPathElement implements ClassPathElement {
    private File baseFolder;

    public FolderPathElement(File file) {
        this.baseFolder = file;
    }

    @Override // mod.agus.jcoderz.multidex.ClassPathElement
    public InputStream open(String str) throws FileNotFoundException {
        return new FileInputStream(new File(this.baseFolder, str.replace(ClassPathElement.SEPARATOR_CHAR, File.separatorChar)));
    }

    @Override // mod.agus.jcoderz.multidex.ClassPathElement
    public void close() {
    }

    @Override // mod.agus.jcoderz.multidex.ClassPathElement
    public Iterable<String> list() {
        ArrayList<String> arrayList = new ArrayList<>();
        collect(this.baseFolder, "", arrayList);
        return arrayList;
    }

    private void collect(File file, String str, ArrayList<String> arrayList) {
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                collect(file2, String.valueOf(str) + ClassPathElement.SEPARATOR_CHAR + file2.getName(), arrayList);
            } else {
                arrayList.add(String.valueOf(str) + ClassPathElement.SEPARATOR_CHAR + file2.getName());
            }
        }
    }
}
