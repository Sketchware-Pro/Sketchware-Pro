package a.a.a

import com.besome.sketch.beans.CollectionBean
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.StringReader
import java.util.ArrayList

abstract class Lp {
    @JvmField
    var a: String? = null

    @JvmField
    var b: String? = null

    @JvmField
    var c: oB? = null

    @JvmField
    var d: Gson? = null

    @JvmField
    var e: ArrayList<CollectionBean>? = null

    init {
        a()
    }

    open fun a() {
        b()
        c = oB()
        d = GsonBuilder().create()
        c()
    }

    abstract fun b()

    open fun c() {
        val list = ArrayList<CollectionBean>()
        e = list
        var reader: BufferedReader? = null
        try {
            val fileUtil = c ?: return
            val path = a ?: return
            reader = BufferedReader(StringReader(fileUtil.g(path)))
            while (true) {
                val line = reader.readLine() ?: break
                if (line.isNotEmpty()) {
                    val bean = d?.fromJson(line, CollectionBean::class.java)
                    if (bean != null && fileUtil.e("$b${File.separator}${bean.data}")) {
                        list.add(bean)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (_: Exception) {
            }
        }
    }

    open fun d() {
        e?.clear()
        e = null
    }

    open fun e() {
        val list = e ?: return
        val sb = StringBuilder(1024)
        for (bean in list) {
            sb.append(d?.toJson(bean))
            sb.append("\n")
        }
        try {
            val path = a ?: return
            c?.b(path, sb.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
