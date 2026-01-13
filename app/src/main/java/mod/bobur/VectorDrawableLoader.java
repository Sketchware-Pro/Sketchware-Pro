package mod.bobur;

import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bobur.androidsvg.SVG;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import a.a.a.wq;
import pro.sketchware.utility.FileUtil;

public class VectorDrawableLoader {
    public ArrayList<String> getVectorDrawables(String sc_id) {
        ArrayList<String> files = new ArrayList<>();
        ArrayList<String> allPaths = new ArrayList<>();

        File resDir = new File(wq.b(sc_id) + "/files/resource/");
        if (resDir.exists() && resDir.isDirectory()) {
            for (File subDir : Objects.requireNonNull(resDir.listFiles())) {
                if (subDir.isDirectory() && subDir.getName().startsWith("drawable")) {
                    FileUtil.listDir(subDir.getAbsolutePath(), allPaths);
                }
            }
        }

        allPaths.sort(Comparator.comparingLong(path -> new File(path).lastModified()));

        for (String vectorPath : allPaths) {
            String fileName = Uri.parse(vectorPath).getLastPathSegment();
            if (fileName != null && fileName.endsWith(".xml")) {
                try {
                    String content = FileUtil.readFile(vectorPath);
                    if (content.contains("<vector")) {
                        files.add(fileName.substring(0, fileName.length() - 4));
                    }
                } catch (Exception ignored) {
                    Log.e("VectorDrawable", "Error reading file: " + vectorPath);
                }
            }
        }

        return files;
    }


    public String getVectorFullPath(String sc_id, String fileName) {
        return wq.b(sc_id) + "/files/resource/drawable/" + fileName + ".xml";
    }

    public void setImageVectorFromFile(ImageView imageView, String filePath) throws Exception {
        VectorDrawableParser attrs = new VectorDrawableParser(FileUtil.readFile(filePath));
        String svg = attrs.toSvg();

        SVG svgObj = SVG.getFromString(svg);
        Picture picture = svgObj.renderToPicture();
        imageView.setImageDrawable(new PictureDrawable(picture));
    }
}