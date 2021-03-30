package mod.agus.jcoderz.lib;

import android.content.ContentResolver;
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

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {

    public static boolean renameFile(String str, String str2) {
        return new File(str).renameTo(new File(str2));
    }

    private static void createNewFile(String path) {
        int lastSep = path.lastIndexOf(File.separator);
        if (lastSep > 0) {
            String dirPath = path.substring(0, lastSep);
            makeDir(dirPath);
        }

        File file = new File(path);

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String path) {
        createNewFile(path);

        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(path);

            char[] buff = new char[1024];
            int length = 0;

            while ((length = fr.read(buff)) > 0) {
                sb.append(new String(buff, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static void writeFile(String path, String str) {
        createNewFile(path);
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(path, false);
            fileWriter.write(str);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(String sourcePath, String destPath) {
        if (!isExistFile(sourcePath)) return;
        createNewFile(destPath);

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(sourcePath);
            fos = new FileOutputStream(destPath, false);

            byte[] buff = new byte[1024];
            int length = 0;

            while ((length = fis.read(buff)) > 0) {
                fos.write(buff, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Copies an entire directory, recursively.
     *
     * @param source   The directory whose contents to copy.
     * @param copyInto The directory to copy files into.
     * @throws IOException Thrown when something goes wrong while copying.
     */
    public static void copyDirectory(File source, File copyInto) throws IOException {
        if (!source.isDirectory()) {
            File parentFile = copyInto.getParentFile();
            if (parentFile == null || parentFile.exists() || parentFile.mkdirs()) {
                FileInputStream fileInputStream = new FileInputStream(source);
                FileOutputStream fileOutputStream = new FileOutputStream(copyInto);
                byte[] bArr = new byte[2048];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        fileInputStream.close();
                        fileOutputStream.close();
                        return;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
            } else {
                throw new IOException("Cannot create dir " + parentFile.getAbsolutePath());
            }
        } else if (copyInto.exists() || copyInto.mkdirs()) {
            String[] list = source.list();
            for (String s : list) {
                copyDirectory(new File(source, s), new File(copyInto, s));
            }
        } else {
            throw new IOException("Cannot create dir " + copyInto.getAbsolutePath());
        }
    }

    public static void extractFileFromZip(InputStream inputStream, File file) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read > 0) {
                    outputStream.write(bArr, 0, read);
                } else {
                    return;
                }
            }
        }
    }

    public static void moveFile(String sourcePath, String destPath) {
        copyFile(sourcePath, destPath);
        deleteFile(sourcePath);
    }

    public static void deleteFile(String path) {
        File file = new File(path);

        if (!file.exists()) return;

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

    public static String convertUriToFilePath(final Context context, final Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);

                if (!TextUtils.isEmpty(id)) {
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                }

                final Uri contentUri = ContentUris
                        .withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                path = getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                path = getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            path = getDataColumn(context, uri, null, null);
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }

        if (path != null) {
            try {
                return URLDecoder.decode(path, "UTF-8");
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;

        final String column = MediaStore.Images.Media.DATA;
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception ignored) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
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
        FileUtil.createNewFile(destPath);
        try {
            out = new FileOutputStream(destPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getScaledBitmap(String str, int i) {
        int i2;
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        int width = decodeFile.getWidth();
        int height = decodeFile.getHeight();
        if (width > height) {
            int i3 = (int) ((((float) i) / ((float) width)) * ((float) height));
            i2 = i;
            i = i3;
        } else {
            i2 = (int) (((float) width) * (((float) i) / ((float) height)));
        }
        return Bitmap.createScaledBitmap(decodeFile, i2, i, true);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
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
            Bitmap decodeFile = BitmapFactory.decodeFile(fromPath);
            Bitmap createBitmap = Bitmap.createBitmap(decodeFile.getWidth(), decodeFile.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, decodeFile.getWidth(), decodeFile.getHeight());
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(-12434878);
            canvas.drawCircle((float) (decodeFile.getWidth() / 2), (float) (decodeFile.getHeight() / 2), (float) (decodeFile.getWidth() / 2), paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(decodeFile, rect, rect, paint);
            saveBitmap(createBitmap, destPath);
        }
    }

    public static void resizeBitmapFileWithRoundedBorder(String fromPath, String destPath, int pixels) {
        if (isExistFile(fromPath)) {
            Bitmap decodeFile = BitmapFactory.decodeFile(fromPath);
            Bitmap createBitmap = Bitmap.createBitmap(decodeFile.getWidth(), decodeFile.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, decodeFile.getWidth(), decodeFile.getHeight());
            RectF rectF = new RectF(rect);
            float f = (float) pixels;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(-12434878);
            canvas.drawRoundRect(rectF, f, f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(decodeFile, rect, rect, paint);
            saveBitmap(createBitmap, destPath);
        }
    }

    public static void cropBitmapFileFromCenter(String fromPath, String destPath, int w, int h) {
        if (!isExistFile(fromPath)) return;
        Bitmap src = BitmapFactory.decodeFile(fromPath);

        int width = src.getWidth();
        int height = src.getHeight();

        if (width < w && height < h)
            return;

        int x = 0;
        int y = 0;

        if (width > w)
            x = (width - w) / 2;

        if (height > h)
            y = (height - h) / 2;

        int cw = w;
        int ch = h;

        if (w > width)
            cw = width;

        if (h > height)
            ch = height;

        Bitmap bitmap = Bitmap.createBitmap(src, x, y, cw, ch);
        saveBitmap(bitmap, destPath);
    }

    public static void rotateBitmapFile(String fromPath, String destPath, float angle) {
        if (isExistFile(fromPath)) {
            Bitmap decodeFile = BitmapFactory.decodeFile(fromPath);
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            saveBitmap(Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true), destPath);
        }
    }

    public static void scaleBitmapFile(String fromPath, String destPath, float x, float y) {
        if (isExistFile(fromPath)) {
            Bitmap decodeFile = BitmapFactory.decodeFile(fromPath);
            Matrix matrix = new Matrix();
            matrix.postScale(x, y);
            saveBitmap(Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true), destPath);
        }
    }

    public static void skewBitmapFile(String fromPath, String destPath, float x, float y) {
        if (isExistFile(fromPath)) {
            Bitmap decodeFile = BitmapFactory.decodeFile(fromPath);
            Matrix matrix = new Matrix();
            matrix.postSkew(x, y);
            saveBitmap(Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true), destPath);
        }
    }

    public static void setBitmapFileColorFilter(String fromPath, String destPath, int color) {
        if (isExistFile(fromPath)) {
            Bitmap decodeFile = BitmapFactory.decodeFile(fromPath);
            Bitmap createBitmap = Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth() - 1, decodeFile.getHeight() - 1);
            Paint paint = new Paint();
            paint.setColorFilter(new LightingColorFilter(color, 1));
            new Canvas(createBitmap).drawBitmap(createBitmap, 0.0f, 0.0f, paint);
            saveBitmap(createBitmap, destPath);
        }
    }

    public static void setBitmapFileBrightness(String fromPath, String destPath, float brightness) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            ColorMatrix cm = new ColorMatrix(new float[]
                    {
                            1, 0, 0, 0, brightness,
                            0, 1, 0, 0, brightness,
                            0, 0, 1, 0, brightness,
                            0, 0, 0, 1, 0
                    });

            Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            canvas.drawBitmap(src, 0, 0, paint);
            saveBitmap(bitmap, destPath);
        }
    }

    public static void setBitmapFileContrast(String fromPath, String destPath, float contrast) {
        if (isExistFile(fromPath)) {
            Bitmap src = BitmapFactory.decodeFile(fromPath);
            ColorMatrix cm = new ColorMatrix(new float[]
                    {
                            contrast, 0, 0, 0, 0,
                            0, contrast, 0, 0, 0,
                            0, 0, contrast, 0, 0,
                            0, 0, 0, 1, 0
                    });

            Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            canvas.drawBitmap(src, 0, 0, paint);

            saveBitmap(bitmap, destPath);
        }
    }

    public static int getJpegRotate(String filePath) {
        int rotate = 0;
        try {
            ExifInterface exif = new ExifInterface(filePath);
            int iOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

            switch (iOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }
        } catch (IOException e) {
            return 0;
        }

        return rotate;
    }

    public static File createNewPictureFile(Context context) {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()) + ".jpg"));
    }

    public static byte[] readFromInputStream(InputStream stream) {
        int available;

        try {
            available = stream.available();
        } catch (IOException e) {
            available = 0;
        }

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[available];

        try {
            for (int len = stream.read(buffer); len != -1; len = stream.read(buffer)) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            return new byte[0];
        }

        return outputStream.toByteArray();
    }

    public static void writeBytes(File target, byte[] data) throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(target));
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

    public static void extractZipTo(ZipInputStream input, String outPath) throws IOException {
        File outDir = new File(outPath);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        ZipEntry entry = input.getNextEntry();
        while (entry != null) {
            String entryPathExtracted = new File(outPath, entry.getName()).getAbsolutePath();

            if (!entry.isDirectory()) {
                new File(entryPathExtracted).getParentFile().mkdirs();
                writeBytes(new File(entryPathExtracted), readFromInputStream(input));
            }
            input.closeEntry();
            entry = input.getNextEntry();
        }
        input.close();
    }
}
