package a.a.a

import java.lang.reflect.Modifier

open class nA {
    fun toString(nAVar: nA): String {
        val sb = StringBuffer()
        sb.append("[")
        for (field in nAVar.javaClass.fields) {
            if (!Modifier.isStatic(field.modifiers) && Modifier.isPublic(field.modifiers)) {
                try {
                    sb.append(field.name)
                    sb.append("=")
                    sb.append(field.get(nAVar))
                    sb.append(",")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        sb.append("]")
        return sb.toString()
    }
}
