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
        inputFiles.forEach { generateBindingForLayout(it) }
    }

    private fun generateBindingForLayout(layoutFile: File) {
        val name = generateFileNameForLayout(layoutFile.nameWithoutExtension)
        val rootView = getTopLevelView(layoutFile)
        val views = parseViews(layoutFile)
        val file = File(outputDir, "$name.java")

        val content = """
// Generated file. Do not modify.
package $packageName;

${generateImports(views, rootView)}

public final class $name {
    public final ${rootView.name} rootView;
${views.joinToString("\n") { "    public final ${it.name} ${it.id};" }}

    private $name(${rootView.name} rootView${if (views.isNotEmpty()) views.joinToString(prefix=", ") { "${it.name} ${it.id}" } else ""}) {
        this.rootView = rootView;
${views.joinToString("\n") { "        this.${it.id} = ${it.id};" }}
    }

    public ${rootView.name} getRoot() {
        return rootView;
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
        ${rootView.name} rootView = (${rootView.name}) view;
${views.joinToString("\n") { "        ${it.name} ${it.id} = findChildViewById(view, R.id.${it.id});" }}
        if (${views.joinToString(" || ") { "${it.id} == null" }}) {
             throw new IllegalStateException("Required views are missing");
        }
        return new $name(rootView, ${views.joinToString { it.id }});
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

        file.writeText(content)
    }

    private fun generateImports(views: List<View>, rootView: View): String {
        val copy = views.toMutableSet().filterNot {
            it.name == "View" || it.name == "ViewGroup"
        }.distinctBy { it.fullName }
        val imports = mutableSetOf(
            "import android.view.View;",
            "import android.view.LayoutInflater;",
            "import android.view.ViewGroup;",
            "import ${rootView.fullName};"
        )
        copy.forEach { imports.add("import ${it.fullName};") }
        return imports.sorted().joinToString("\n")
    }

    private fun getTopLevelView(layoutFile: File): View {
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(layoutFile)
        val element = document.documentElement
        return View(
            element.nodeName.substringAfterLast("."),
            if (element.nodeName.contains(".")) element.nodeName else "android.widget.${element.nodeName}",
            element.attributes?.getNamedItem("android:id")?.nodeValue?.substringAfter("/") ?: ""
        )
    }

    private fun parseViews(layoutFile: File): List<View> {
        val views = mutableListOf<View>()
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(layoutFile)
        parseNode(document.documentElement, views)
        return views
    }

    private fun parseNode(node: Node, views: MutableList<View>) {
        if (node.nodeType == Node.ELEMENT_NODE) {
            val id = node.attributes?.getNamedItem("android:id")
            if (id != null) {
                views.add(
                    View(
                        node.nodeName.substringAfterLast("."),
                        if (node.nodeName.contains(".")) node.nodeName else "android.widget.${node.nodeName}",
                        id.nodeValue.substringAfter("/")
                    )
                )
            }
            for (i in 0 until node.childNodes.length) {
                parseNode(node.childNodes.item(i), views)
            }
        }
    }

    data class View(val name: String, val fullName: String, val id: String)

    companion object {
        @JvmStatic
        fun generateFileNameForLayout(layoutName: String): String {
            return layoutName.split("_")
                .joinToString("") { part -> part.replaceFirstChar { it.uppercaseChar() } } + "Binding"
        }
    }
}
