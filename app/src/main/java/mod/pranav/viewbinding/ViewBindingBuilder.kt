package mod.pranav.viewbinding

import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class ViewBindingBuilder(
    private val inputFiles: List<File>,
    private val outputDir: File,
    private val packageName: String = "dev.pranav.viewbinding"
) {
    fun generateBindings() {
        inputFiles.forEach { generateBindingForLayoutAndWrite(it) }
    }

    /** generate binding and return class code */
    fun generateBindingForLayout(layoutFile: File): String {
        val name = generateFileNameForLayout(layoutFile.nameWithoutExtension)
        val rootView = getTopLevelView(layoutFile)
        val parsed = parseViews(layoutFile)
        val views =
            if (parsed.isNotEmpty() && parsed.first() == rootView) parsed.drop(1) else parsed

        val content = """
// Generated file. Do not modify.
package $packageName;

${generateImports(views, rootView)}

public final class $name {
    public final ${rootView.type} ${rootView.name};
${views.joinToString("\n") { "    public final ${it.type} ${it.name};" }}

    private $name(${rootView.type} ${rootView.name}${
            if (views.isNotEmpty()) views.joinToString(
                prefix = ", "
            ) { "${it.type} ${it.name}" } else ""
        }) {
        this.${rootView.name} = ${rootView.name};
${views.joinToString("\n") { "        this.${it.name} = ${it.name};" }}
    }

    public ${rootView.type} getRoot() {
        return ${rootView.name};
    }

    public static $name inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static $name inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.${layoutFile.nameWithoutExtension}, parent, false);
        if (attachToParent) parent.addView(root);
        return bind(root);
    }

    public static $name bind(View view) {
        ${rootView.type} ${rootView.name} = (${rootView.type}) view;
${
            if (views.isNotEmpty()) {
                """${
                    views.filterNot { it.isInclude }
                        .joinToString("\n") { "        ${it.type} ${it.name} = findChildViewById(view, R.id.${it.id});" }
                }
${
                    views.filter { it.isInclude }
                        .joinToString("\n") { "        ${it.type} ${it.name} = ${it.fullType}.bind(findChildViewById(view, R.id.${it.id}));" }
                }
        if (${views.joinToString(" || ") { "${it.name} == null" }}) {
             throw new IllegalStateException("Required views are missing");
        }"""
            } else ""
        }

        return new $name(${rootView.name}${if (views.isNotEmpty()) ", " + views.joinToString { it.name } else ""});
    }

    private static <T extends View> T findChildViewById(View rootView, int id) {
         if (rootView instanceof ViewGroup) {
              ViewGroup rootViewGroup = (ViewGroup) rootView;
              for (int i = 0; i < rootViewGroup.getChildCount(); i++) {
                   T view = rootViewGroup.getChildAt(i).findViewById(id);
                   if (view != null) return view;
              }
         }
         return null;
    }
}
        """.trimIndent()

        return content
    }

    /** generate view binding and save in output file */
    private fun generateBindingForLayoutAndWrite(layoutFile: File) {
        val name = generateFileNameForLayout(layoutFile.nameWithoutExtension)
        val file = File(outputDir, "$name.java")
        val content = generateBindingForLayout(layoutFile)
        file.writeText(content)
    }

    private fun generateImports(views: List<View>, rootView: View): String {
        val copy = views.toMutableSet().filterNot {
            it.type == "View" || it.type == "ViewGroup"
        }.distinctBy { it.fullType }
        val imports = mutableSetOf(
            "import android.view.View;",
            "import android.view.LayoutInflater;",
            "import android.view.ViewGroup;",
            "import ${rootView.fullType};"
        )

        copy.forEach {
            if (it.fullType == "android.widget.WebView") {
                imports.add("import android.webkit.WebView;")
            } else {
                imports.add("import ${it.fullType};")
            }
        }

        return imports.sorted().joinToString("\n")
    }

    private fun getTopLevelView(layoutFile: File): View {
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(layoutFile)
        val element = document.documentElement
        return View(
            element.nodeName.substringAfterLast("."),
            if (element.nodeName.contains(".")) element.nodeName else "android.widget.${element.nodeName}",
            element.attributes?.getNamedItem("android:id")?.nodeValue?.substringAfter("/")
                ?: "rootView"
        )
    }

    private fun parseViews(layoutFile: File): List<View> {
        val views = mutableListOf<View>()
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(layoutFile)
        parseNode(document.documentElement, views)
        return (views.filterNot { it.isInclude } + views.filter { it.isInclude })
    }

    private fun parseNode(node: Node, views: MutableList<View>) {
        if (node.nodeType == Node.ELEMENT_NODE) {
            val id = node.attributes?.getNamedItem("android:id")
            if (id != null) {
                if (node.nodeName == "include") {
                    val layout =
                        node.attributes?.getNamedItem("layout")?.nodeValue?.substringAfter("/")
                    if (layout != null) {
                        val id = node.attributes?.getNamedItem("android:id")
                        if (id != null) {
                            views.add(
                                View(
                                    generateFileNameForLayout(layout),
                                    packageName + "." + generateFileNameForLayout(layout),
                                    id.nodeValue.substringAfter("/"),
                                    true
                                )
                            )
                        }
                    }
                } else {
                    views.add(
                        View(
                            node.nodeName.substringAfterLast("."),
                            if (node.nodeName.contains(".")) node.nodeName else "android.widget.${node.nodeName}",
                            id.nodeValue.substringAfter("/")
                        )
                    )
                }
            }
            for (i in 0 until node.childNodes.length) {
                parseNode(node.childNodes.item(i), views)
            }
        }
    }

    data class View(
        val type: String,
        val fullType: String,
        val id: String,
        val isInclude: Boolean = false
    ) {
        val name = generateParameterFromId(id)

        override fun toString(): String {
            return "${type}(fullName='$fullType', id='$id', name='$name', isInclude=$isInclude)"
        }
    }

    companion object {
        @JvmStatic
        fun generateParameterFromId(id: String): String {
            return if (id.contains('_')) id.substringBefore('_') + id.substringAfter('_')
                .split('_')
                .joinToString("") { part -> part.replaceFirstChar { it.uppercaseChar() } } else id
        }

        @JvmStatic
        fun generateFileNameForLayout(layoutName: String): String {
            return layoutName.split('_')
                .joinToString("") { part -> part.replaceFirstChar { it.uppercaseChar() } } + "Binding"
        }
    }
}
