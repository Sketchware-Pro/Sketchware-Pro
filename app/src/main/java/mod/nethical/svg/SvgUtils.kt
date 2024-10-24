package mod.nethical.svg

import android.content.Context
import android.util.Log
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File
import java.io.StringReader
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern

class SvgUtils(private val context: Context) {
    private var imageLoader: ImageLoader? = null

    init {
        initImageLoader()
    }

    fun initImageLoader() {
        imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }


    fun loadImage(imageView: ImageView, filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            val request: ImageRequest = ImageRequest.Builder(context)
                .data(file)
                .target(imageView)
                .build()

            imageLoader!!.enqueue(request)
        }
    }


    fun loadWithoutQueue(imageView: ImageView,filePath: String){
        imageView.load(filePath) {
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
        }
    }
    companion object {
        private const val SIZE_MULTIPLIER = 2

        @JvmStatic
        @Throws(Exception::class)
        fun convert(inputFilePath: String, outputDir: String, fillColor: String?) {
            // Read the SVG file
            val data = String(Files.readAllBytes(Paths.get(inputFilePath)))

            // Default values for width, height, viewportWidth, and viewportHeight
            var width = 24
            var height = 24
            var vWidth = 24
            var vHeight = 24

            // Use regex to extract viewBox, width, and height if present
            var pattern = Pattern.compile("viewBox=\"([0-9]+\\s[0-9]+\\s[0-9]+\\s[0-9]+)\"")
            var matcher = pattern.matcher(data)
            if (matcher.find()) {
                val viewBoxValues =
                    matcher.group(1)!!.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                vWidth = viewBoxValues[2].toInt()
                vHeight = viewBoxValues[3].toInt()
            }

            pattern = Pattern.compile("width=\"([0-9]+\\.?[0-9]*)\"")
            matcher = pattern.matcher(data)
            if (matcher.find()) {
                width = matcher.group(1)?.toFloat() as Int
            }

            pattern = Pattern.compile("height=\"([0-9]+\\.?[0-9]*)\"")
            matcher = pattern.matcher(data)
            if (matcher.find()) {
                height = matcher.group(1)?.toFloat() as Int
            }

            // Start creating the XML file using XmlSerializer
            val writer = StringWriter()
            val factory = XmlPullParserFactory.newInstance()
            val serializer = factory.newSerializer()

            serializer.setOutput(writer)
            serializer.startDocument("UTF-8", true)

            // Start the <vector> tag
            serializer.startTag(null, "vector")
            serializer.attribute(
                null,
                "xmlns:android",
                "http://schemas.android.com/apk/res/android"
            )
            serializer.attribute(null, "android:name", "vector")
            serializer.attribute(null, "android:width", (width * SIZE_MULTIPLIER).toString() + "dp")
            serializer.attribute(
                null,
                "android:height",
                (height * SIZE_MULTIPLIER).toString() + "dp"
            )
            serializer.attribute(null, "android:viewportWidth", vWidth.toString())
            serializer.attribute(null, "android:viewportHeight", vHeight.toString())

            // Process the <path> tag(s) in the SVG
            val parser = factory.newPullParser()
            parser.setInput(StringReader(data))
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "path" == parser.name) {
                    // Extract path data and fill color
                    val pathData = parser.getAttributeValue(null, "d")
                    var fill = parser.getAttributeValue(null, "fill")

                    // If a custom fill color is provided, use it; otherwise, use the default fill color
                    if (fillColor != null && !fillColor.isEmpty()) {
                        fill = fillColor
                    } else if (fill == null) {
                        fill = "#000000" // Default fill color
                    }

                    // Write the <path> tag
                    serializer.startTag(null, "path")
                    serializer.attribute(null, "android:name", "path")
                    serializer.attribute(null, "android:pathData", pathData)
                    serializer.attribute(null, "android:fillColor", fill)
                    serializer.endTag(null, "path")
                }
                eventType = parser.next()
            }

            // End the <vector> tag
            serializer.endTag(null, "vector")
            serializer.endDocument()

            // Generate output XML path
            val outputFilePath = outputDir + "/" + File(inputFilePath).name.replace(".svg", ".xml")

            val outputPath = Paths.get(outputFilePath)
            Files.createFile(outputPath)


            Files.write(outputPath, writer.toString().toByteArray())

            Log.d("svgConverter", "Converted file saved to: $outputFilePath")
        }
    }

}
