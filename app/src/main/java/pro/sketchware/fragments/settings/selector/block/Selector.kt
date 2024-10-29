package pro.sketchware.fragments.settings.selector.block

data class Selector(
    val data: MutableList<String> = mutableListOf(),
    val name: String = "",
    val title: String = ""
)