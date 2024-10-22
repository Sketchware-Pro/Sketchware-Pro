package mod.nethical.svg;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import coil.ComponentRegistry;
import coil.decode.SvgDecoder;
import java.io.File;
import coil.ImageLoader;
import coil.request.ImageRequest;

import java.io.*;
import java.nio.file.*;
import java.util.regex.*;


public class SvgUtils {

    private Context context;
    private ImageLoader imageLoader;
    private static final int SIZE_MULTIPLIER = 2;
    
    public SvgUtils(Context context){
        this.context = context;
        initImageLoader();
        
    }
    public void initImageLoader(){
        imageLoader = new ImageLoader.Builder(context)
                .components(new ComponentRegistry.Builder()
                    .add(new SvgDecoder.Factory())
                    .build())
                .build();
    }
    
    
    public void loadImage(ImageView imageView, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            ImageRequest request = new ImageRequest.Builder(context)
                .data(file)
                .target(imageView)
                .build();
    
            imageLoader.enqueue(request);
        } 
    }

    public static void convert(String inputFilePath, String outputDir, String fillColor) throws Exception {
        // Read the SVG file
        String data = new String(Files.readAllBytes(Paths.get(inputFilePath)));

        // Default values for width, height, viewportWidth, and viewportHeight
        int width = 24, height = 24, vWidth = 24, vHeight = 24;

        // Use regex to extract viewBox, width, and height if present
        Pattern pattern = Pattern.compile("viewBox=\"([0-9]+\\s[0-9]+\\s[0-9]+\\s[0-9]+)\"");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            String[] viewBoxValues = matcher.group(1).split("\\s");
            vWidth = Integer.parseInt(viewBoxValues[2]);
            vHeight = Integer.parseInt(viewBoxValues[3]);
        }

        pattern = Pattern.compile("width=\"([0-9]+\\.?[0-9]*)\"");
        matcher = pattern.matcher(data);
        if (matcher.find()) {
            width = (int) Float.parseFloat(matcher.group(1));
        }

        pattern = Pattern.compile("height=\"([0-9]+\\.?[0-9]*)\"");
        matcher = pattern.matcher(data);
        if (matcher.find()) {
            height = (int) Float.parseFloat(matcher.group(1));
        }

        // Start creating the XML file using XmlSerializer
        StringWriter writer = new StringWriter();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlSerializer serializer = factory.newSerializer();

        serializer.setOutput(writer);
        serializer.startDocument("UTF-8", true);

        // Start the <vector> tag
        serializer.startTag(null, "vector");
        serializer.attribute(null, "xmlns:android", "http://schemas.android.com/apk/res/android");
        serializer.attribute(null, "android:name", "vector");
        serializer.attribute(null, "android:width", (width * SIZE_MULTIPLIER) + "dp");
        serializer.attribute(null, "android:height", (height * SIZE_MULTIPLIER) + "dp");
        serializer.attribute(null, "android:viewportWidth", String.valueOf(vWidth));
        serializer.attribute(null, "android:viewportHeight", String.valueOf(vHeight));

        // Process the <path> tag(s) in the SVG
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(data));
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && "path".equals(parser.getName())) {
                // Extract path data and fill color
                String pathData = parser.getAttributeValue(null, "d");
                String fill = parser.getAttributeValue(null, "fill");

                // If a custom fill color is provided, use it; otherwise, use the default fill color
                if (fillColor != null && !fillColor.isEmpty()) {
                    fill = fillColor;
                } else if (fill == null) {
                    fill = "#000000";  // Default fill color
                }

                // Write the <path> tag
                serializer.startTag(null, "path");
                serializer.attribute(null, "android:name", "path");
                serializer.attribute(null, "android:pathData", pathData);
                serializer.attribute(null, "android:fillColor", fill);
                serializer.endTag(null, "path");
            }
            eventType = parser.next();
        }

        // End the <vector> tag
        serializer.endTag(null, "vector");
        serializer.endDocument();

        // Generate output XML path
        String outputFilePath = outputDir + "/" + new File(inputFilePath).getName().replace(".svg", ".xml");

        Path outputPath = Paths.get(outputFilePath);
        Files.createFile(outputPath);


        Files.write(outputPath, writer.toString().getBytes());

        Log.d("svgConverter","Converted file saved to: " + outputFilePath);
    }

}
