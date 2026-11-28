package a.a.a

import com.besome.sketch.beans.CollectionBean
import com.besome.sketch.beans.ViewBean
import com.besome.sketch.beans.WidgetCollectionBean
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.StringReader
import java.util.ArrayList

class Rp : Lp() {
    @JvmField
    var g: Gson? = null

    init {
        i()
    }

    companion object {
        @JvmField
        var f: Rp? = null

        @JvmStatic
        fun h(): Rp {
            if (f == null) {
                synchronized(Rp::class.java) {
                    if (f == null) {
                        f = Rp()
                    }
                }
            }
            return f!!
        }
    }

    fun a(str: String): WidgetCollectionBean? {
        for (bean in e ?: return null) {
            if (bean.name == str) {
                return WidgetCollectionBean(bean.name, gC.b(g, bean.data))
            }
        }
        return null
    }

    fun a(str: String, str2: String, z: Boolean) {
        val list = e ?: return
        for (bean in list) {
            if (bean.name == str) {
                bean.name = str2
                break
            }
        }
        if (z) {
            e()
        }
    }

    fun a(str: String, arrayList: ArrayList<ViewBean>, z: Boolean) {
        if (e == null) {
            a()
        }
        if (g == null) {
            i()
        }
        val list = e!!
        for (bean in list) {
            if (bean.name == str) {
                throw yy("duplicate_name")
            }
        }
        val sb = StringBuilder()
        for (viewBean in arrayList) {
            sb.append(g!!.toJson(viewBean))
            sb.append("\n")
        }
        list.add(CollectionBean(str, sb.toString()))
        if (z) {
            e()
        }
    }

    fun a(str: String, z: Boolean) {
        val list = e ?: return
        for (i in list.size - 1 downTo 0) {
            if (list[i].name == str) {
                list.removeAt(i)
                break
            }
        }
        if (z) {
            e()
        }
    }

    override fun b() {
        a = "${wq.a()}${File.separator}widget${File.separator}list"
        b = "${wq.a()}${File.separator}widget${File.separator}data"
    }

    override fun c() {
        val list = ArrayList<CollectionBean>()
        e = list
        var reader: BufferedReader? = null
        try {
            val fileUtil = c ?: return
            val path = a ?: return
            if (fileUtil.e(path)) {
                reader = BufferedReader(StringReader(fileUtil.g(path)))
                while (true) {
                    val line = reader.readLine() ?: break
                    if (line.isNotEmpty()) {
                        val bean = d?.fromJson(line, CollectionBean::class.java)
                        if (bean != null) {
                            list.add(bean)
                        }
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

    fun f(): ArrayList<WidgetCollectionBean> {
        if (e == null) {
            a()
        }
        if (g == null) {
            i()
        }
        val result = ArrayList<WidgetCollectionBean>()
        for (bean in e!!) {
            result.add(WidgetCollectionBean(bean.name, gC.b(g, bean.data)))
        }
        return result
    }

    fun g(): ArrayList<String> {
        if (e == null) {
            a()
        }
        val result = ArrayList<String>()
        for (bean in e!!) {
            result.add(bean.name)
        }
        return result
    }

    private fun i() {
        g = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }
}
