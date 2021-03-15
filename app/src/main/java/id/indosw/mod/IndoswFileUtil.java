package id.indosw.mod;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IndoswFileUtil {

    private static void createNewFile(String path) {
        int lastSep = path.lastIndexOf(File.separator);
        if (lastSep > 0) {
            makeDir(path.substring(0, lastSep));
        }
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String path) {
        createNewFile(path);
        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            FileReader fr2 = new FileReader(new File(path));
            char[] buff = new char[1024];
            while (true) {
                int length = fr2.read(buff);
                if (length > 0) {
                    sb.append(new String(buff, 0, length));
                } else {
                    try {
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            fr2.close();
        } catch (IOException e2) {
            e2.printStackTrace();
            if (0 != 0) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    fr.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        return sb.toString();
    }

    public static void writeFile(String path, String str) {
        createNewFile(path);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(path), false);
            fileWriter.write(str);
            fileWriter.flush();
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static void copyFile(String sourcePath, String destPath) {
        if (isExistFile(sourcePath)) {
            createNewFile(destPath);
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                FileInputStream fis2 = new FileInputStream(sourcePath);
                FileOutputStream fos2 = new FileOutputStream(destPath, false);
                byte[] buff = new byte[1024];
                while (true) {
                    int length = fis2.read(buff);
                    if (length > 0) {
                        fos2.write(buff, 0, length);
                    } else {
                        break;
                    }
                }
                fis2.close();
                try {
                    fos2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
                if (0 != 0) {
                    try {
                        fis.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                if (0 != 0) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    try {
                        fis.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (0 != 0) {
                    try {
                        fos.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] fileArr = file.listFiles();
            if (fileArr != null) {
                for (File subFile : fileArr) {
                    if (subFile.isDirectory()) {
                        deleteFile(subFile.getAbsolutePath());
                    }
                    if (subFile.isFile()) {
                        subFile.delete();
                    }
                }
            }
            file.delete();
        }
    }

    public static boolean isExistFile(String path) {
        return new File(path).exists();
    }

    public static void makeDir(String path) {
        if (!isExistFile(path)) {
            new File(path).mkdirs();
        }
    }

    public static void listDir(String path, ArrayList<String> list) {
        File[] listFiles;
        File dir = new File(path);
        if (!(!dir.exists() || dir.isFile() || (listFiles = dir.listFiles()) == null || listFiles.length <= 0 || list == null)) {
            list.clear();
            for (File file : listFiles) {
                list.add(file.getAbsolutePath());
            }
        }
    }

    public static boolean isDirectory(String path) {
        if (!isExistFile(path)) {
            return false;
        }
        return new File(path).isDirectory();
    }

    public static boolean isFile(String path) {
        if (!isExistFile(path)) {
            return false;
        }
        return new File(path).isFile();
    }

    public static long getFileLength(String path) {
        if (!isExistFile(path)) {
            return 0;
        }
        return new File(path).length();
    }

    public static String getExternalStorageDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getPackageDataDir(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    public static String getPublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }

    public static String convertUriToFilePath(Context context, Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                String id2 = DocumentsContract.getDocumentId(uri);
                if (!TextUtils.isEmpty(id2) && id2.startsWith("raw:")) {
                    return id2.replaceFirst("raw:", "");
                }
                path = getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id2).longValue()), null, null);
            } else if (isMediaDocument(uri)) {
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String type = split2[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                path = getDataColumn(context, contentUri, "_id=?", new String[]{split2[1]});
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            path = getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
        if (path == null) {
            return null;
        }
        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor == null) {
                    return null;
                }
                cursor.close();
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            if (cursor != null) {
                cursor.close();
            }
            return string;
        } catch (Exception e) {
            if (0 == 0) {
                return null;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static void saveBitmap(Bitmap bitmap, String destPath) {
        FileOutputStream out = null;
        createNewFile(destPath);
        try {
            out = new FileOutputStream(new File(destPath));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static Bitmap getScaledBitmap(String path, int max) {
        int height;
        int width;
        Bitmap src = BitmapFactory.decodeFile(path);
        int width2 = src.getWidth();
        int height2 = src.getHeight();
        if (width2 > height2) {
            height = (int) (((float) height2) * (((float) max) / ((float) width2)));
            width = max;
        } else {
            width = (int) (((float) width2) * (((float) max) / ((float) height2)));
            height = max;
        }
        return Bitmap.createScaledBitmap(src, width, height, true);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static void resizeBitmapFileRetainRatio(String fromPath, String destPath, int max) {
        if (isExistFile(fromPath)) {
            saveBitmap(getScaledBitmap(fromPath, max), destPath);
        }
    }

    public static void resizeBitmapFileToSquare(String fromPath, String destPath, int max) {
        if (isExistFile(fromPath)) {
            saveBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(fromPath), max, max, true), destPath);
        }
    }

    public static void resizeBitmapFileToCircle(String fromPath, String destPath) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(-12434878);
            canvas.drawCircle((float) (src.getWidth() / 2), (float) (src.getHeight() / 2), (float) (src.getWidth() / 2), paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(src, rect, rect, paint);
            saveBitmap(bitmap, destPath);
        }
    }

    public static void resizeBitmapFileWithRoundedBorder(String fromPath, String destPath, int pixels) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, src.getWidth(), src.getHeight());
            RectF rectF = new RectF(rect);
            float roundPx = (float) pixels;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(-12434878);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(src, rect, rect, paint);
            saveBitmap(bitmap, destPath);
        }
    }

    public static void cropBitmapFileFromCenter(String fromPath, String destPath, int w, int h) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            int width = src.getWidth();
            int height = src.getHeight();
            if (width >= w || height >= h) {
                int x = 0;
                int y = 0;
                if (width > w) {
                    x = (width - w) / 2;
                }
                if (height > h) {
                    y = (height - h) / 2;
                }
                int cw = w;
                int ch = h;
                if (w > width) {
                    cw = width;
                }
                if (h > height) {
                    ch = height;
                }
                saveBitmap(Bitmap.createBitmap(src, x, y, cw, ch), destPath);
            }
        }
    }

    public static void rotateBitmapFile(String fromPath, String destPath, float angle) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            saveBitmap(Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true), destPath);
        }
    }

    public static void scaleBitmapFile(String fromPath, String destPath, float x, float y) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            Matrix matrix = new Matrix();
            matrix.postScale(x, y);
            saveBitmap(Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true), destPath);
        }
    }

    public static void skewBitmapFile(String fromPath, String destPath, float x, float y) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            Matrix matrix = new Matrix();
            matrix.postSkew(x, y);
            saveBitmap(Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true), destPath);
        }
    }

    public static void setBitmapFileColorFilter(String fromPath, String destPath, int color) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            Bitmap bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth() - 1, src.getHeight() - 1);
            Paint p = new Paint();
            p.setColorFilter(new LightingColorFilter(color, 1));
            new Canvas(bitmap).drawBitmap(bitmap, 0.0f, 0.0f, p);
            saveBitmap(bitmap, destPath);
        }
    }

    public static void setBitmapFileBrightness(String fromPath, String destPath, float brightness) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            ColorMatrix cm = new ColorMatrix(new float[]{1.0f, 0.0f, 0.0f, 0.0f, brightness, 0.0f, 1.0f, 0.0f, 0.0f, brightness, 0.0f, 0.0f, 1.0f, 0.0f, brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
            Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            canvas.drawBitmap(src, 0.0f, 0.0f, paint);
            saveBitmap(bitmap, destPath);
        }
    }

    public static void setBitmapFileContrast(String fromPath, String destPath, float contrast) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            ColorMatrix cm = new ColorMatrix(new float[]{contrast, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, contrast, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, contrast, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
            Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            canvas.drawBitmap(src, 0.0f, 0.0f, paint);
            saveBitmap(bitmap, destPath);
        }
    }

    public static int getJpegRotate(String filePath) {
        try {
            int iOrientation = new ExifInterface(filePath).getAttributeInt("Orientation", -1);
            if (iOrientation == 3) {
                return 180;
            }
            if (iOrientation == 6) {
                return 90;
            }
            if (iOrientation != 8) {
                return 0;
            }
            return 270;
        } catch (IOException e) {
            return 0;
        }
    }

    public static File createNewPictureFile(Context context) {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + (new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg"));
    }
}