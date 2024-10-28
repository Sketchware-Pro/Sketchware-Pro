package mod.nethical.svg

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import org.xmlpull.v1.XmlSerializer
import java.io.File
import java.io.StringReader
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern
import kotlin.math.roundToInt


const val SIZE_MULTIPLIER = 2
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

    fun loadImage(imageView: ImageView, filePath: String,width: Int, height: Int) {
        val file = File(filePath)
        if (file.exists()) {
            val request: ImageRequest = ImageRequest.Builder(context)
                .allowConversionToBitmap(true)
                .data(file)
                .target(imageView)
                .build()

            imageLoader!!.enqueue(request)
        }
    }

    fun loadScaledSvgIntoImageView(
        imageView: ImageView,
        svgPath: String,
        scaleFactor: Float = 0.5f // Default scaling factor if none is provided
    ) {
        // Create an ImageLoader with SVG support
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        // Load the SVG image and apply scaling
        val request = ImageRequest.Builder(context)
            .data(svgPath)
            .target { drawable ->
                drawable.let {
                    // Get density scaling
                    val densityScale = (context.resources.displayMetrics.density * scaleFactor).roundToInt()
                    val bitmap = drawable.toBitmap()
                    // Apply scaling to width and height
                    val scaledBitmap = Bitmap.createScaledBitmap(
                        bitmap,
                        48 * densityScale,
                        48 * densityScale,
                        true
                    )
                    // Set the scaled image on the ImageView
                    imageView.setImageBitmap(scaledBitmap)
                }
            }
            .build()

        imageLoader.enqueue(request)
    }

    fun loadWithoutQueue(imageView: ImageView,filePath: String){
        imageView.load(filePath) {
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
        }
    }

    fun convert(inputFilePath: String, outputDir: String, fillColor: String?) {
        // Read the SVG file
        val data = String(Files.readAllBytes(Paths.get(inputFilePath)))

        // Create XML parser factory
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true

        // Parse the input SVG
        val inputParser = factory.newPullParser()
        inputParser.setInput(StringReader(data))

        // Extract SVG attributes
        val svgAttributes = extractSvgAttributes(inputParser)

        // Create output XML
        val writer = StringWriter()
        val serializer = factory.newSerializer()
        serializer.setOutput(writer)

        // Write the vector drawable
        writeVectorDrawable(serializer, svgAttributes, inputParser, fillColor)

        // Save the output file
        val outputFilePath = outputDir + "/" + File(inputFilePath).name.replace(".svg", ".xml")
        val outputPath = Paths.get(outputFilePath)
        Files.createDirectories(outputPath.parent)
        Files.write(outputPath, writer.toString().toByteArray())

        Log.d("svgConverter", "Converted file saved to: $outputFilePath")
    }

    data class SvgAttributes(
        val width: Int = 24,
        val height: Int = 24,
        val viewportWidth: Float = 24f,
        val viewportHeight: Float = 24f
    )

    private fun extractSvgAttributes(parser: XmlPullParser): SvgAttributes {
        while (parser.eventType != XmlPullParser.START_TAG || parser.name != "svg") {
            parser.next()
        }

        // Get viewBox attributes
        val viewBox = parser.getAttributeValue(null, "viewBox")?.split(" ")
        val viewportWidth = viewBox?.getOrNull(2)?.toFloatOrNull() ?: 24f
        val viewportHeight = viewBox?.getOrNull(3)?.toFloatOrNull() ?: 24f

        // Get width and height
        val width = parser.getAttributeValue(null, "width")?.removeSuffix("px")?.toIntOrNull() ?: 24
        val height = parser.getAttributeValue(null, "height")?.removeSuffix("px")?.toIntOrNull() ?: 24

        return SvgAttributes(width, height, viewportWidth, viewportHeight)
    }

    private fun writeVectorDrawable(
        serializer: XmlSerializer,
        attributes: SvgAttributes,
        parser: XmlPullParser,
        customFillColor: String?
    ) {

        serializer.startDocument("UTF-8", true)

        // Write vector tag
        serializer.startTag(null, "vector")
        serializer.attribute(null, "xmlns:android", "http://schemas.android.com/apk/res/android")
        serializer.attribute(null, "android:width", "${attributes.width * SIZE_MULTIPLIER}dp")
        serializer.attribute(null, "android:height", "${attributes.height * SIZE_MULTIPLIER}dp")
        serializer.attribute(null, "android:viewportWidth", attributes.viewportWidth.toString())
        serializer.attribute(null, "android:viewportHeight", attributes.viewportHeight.toString())

        // Process all SVG elements
        var depth = 0
        var groupFillColor: String? = null

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    depth++
                    when (parser.name) {
                        "g" -> {
                            groupFillColor = parser.getAttributeValue(null, "fill")
                        }
                        "path" -> {
                            writePath(serializer, parser, customFillColor ?: groupFillColor)
                        }
                        "circle", "rect", "ellipse" -> {
                            val pathData = convertShapeToPath(parser)
                            writePathFromData(serializer, pathData, customFillColor ?: groupFillColor)
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    depth--
                    if (parser.name == "g") {
                        groupFillColor = null
                    }
                }
            }
            parser.next()
        }

        serializer.endTag(null, "vector")
        serializer.endDocument()
    }

    private fun writePath(serializer: XmlSerializer, parser: XmlPullParser, inheritedFill: String?) {
        val pathData = parser.getAttributeValue(null, "d")
        val fill = parser.getAttributeValue(null, "fill") ?: inheritedFill ?: "#000000"

        if (pathData != null) {
            serializer.startTag(null, "path")
            serializer.attribute(null, "android:pathData", pathData)
            serializer.attribute(null, "android:fillColor", fill)

            // Handle opacity if present
            parser.getAttributeValue(null, "opacity")?.toFloatOrNull()?.let { opacity ->
                if (opacity < 1.0f) {
                    serializer.attribute(null, "android:fillAlpha", opacity.toString())
                }
            }

            serializer.endTag(null, "path")
        }
    }

    private fun writePathFromData(serializer: XmlSerializer, pathData: String, fill: String?) {
        serializer.startTag(null, "path")
        serializer.attribute(null, "android:pathData", pathData)
        serializer.attribute(null, "android:fillColor", fill ?: "#000000")
        serializer.endTag(null, "path")
    }

    private fun convertShapeToPath(parser: XmlPullParser): String {
        return when (parser.name) {
            "circle" -> {
                val cx = parser.getAttributeValue(null, "cx")?.toFloatOrNull() ?: 0f
                val cy = parser.getAttributeValue(null, "cy")?.toFloatOrNull() ?: 0f
                val r = parser.getAttributeValue(null, "r")?.toFloatOrNull() ?: 0f
                createCirclePath(cx, cy, r)
            }
            "rect" -> {
                val x = parser.getAttributeValue(null, "x")?.toFloatOrNull() ?: 0f
                val y = parser.getAttributeValue(null, "y")?.toFloatOrNull() ?: 0f
                val width = parser.getAttributeValue(null, "width")?.toFloatOrNull() ?: 0f
                val height = parser.getAttributeValue(null, "height")?.toFloatOrNull() ?: 0f
                val rx = parser.getAttributeValue(null, "rx")?.toFloatOrNull() ?: 0f
                val ry = parser.getAttributeValue(null, "ry")?.toFloatOrNull() ?: 0f
                createRectPath(x, y, width, height, rx, ry)
            }
            "ellipse" -> {
                val cx = parser.getAttributeValue(null, "cx")?.toFloatOrNull() ?: 0f
                val cy = parser.getAttributeValue(null, "cy")?.toFloatOrNull() ?: 0f
                val rx = parser.getAttributeValue(null, "rx")?.toFloatOrNull() ?: 0f
                val ry = parser.getAttributeValue(null, "ry")?.toFloatOrNull() ?: 0f
                createEllipsePath(cx, cy, rx, ry)
            }
            else -> ""
        }
    }

    private fun createCirclePath(cx: Float, cy: Float, r: Float): String {
        return "M ${cx - r},$cy " +
                "A $r,$r 0 0 1 ${cx + r},$cy " +
                "A $r,$r 0 0 1 ${cx - r},$cy Z"
    }

    private fun createRectPath(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        rx: Float,
        ry: Float
    ): String {
        return if (rx <= 0f && ry <= 0f) {
            "M $x,$y h $width v $height h ${-width} Z"
        } else {
            val effectiveRx = rx.coerceAtMost(width / 2)
            val effectiveRy = ry.coerceAtMost(height / 2)
            "M ${x + effectiveRx},$y " +
                    "h ${width - 2 * effectiveRx} " +
                    "a $effectiveRx,$effectiveRy 0 0 1 $effectiveRx,$effectiveRy " +
                    "v ${height - 2 * effectiveRy} " +
                    "a $effectiveRx,$effectiveRy 0 0 1 ${-effectiveRx},$effectiveRy " +
                    "h ${-(width - 2 * effectiveRx)} " +
                    "a $effectiveRx,$effectiveRy 0 0 1 ${-effectiveRx},${-effectiveRy} " +
                    "v ${-(height - 2 * effectiveRy)} " +
                    "a $effectiveRx,$effectiveRy 0 0 1 $effectiveRx,${-effectiveRy} Z"
        }
    }

    private fun createEllipsePath(cx: Float, cy: Float, rx: Float, ry: Float): String {
        return "M ${cx - rx},$cy " +
                "A $rx,$ry 0 0 1 ${cx + rx},$cy " +
                "A $rx,$ry 0 0 1 ${cx - rx},$cy Z"
    }

}
