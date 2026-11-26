package a.a.a

import com.besome.sketch.beans.BlockBean
import com.besome.sketch.beans.CollectionBean
import com.besome.sketch.beans.MoreBlockCollectionBean
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.StringReader
import java.util.ArrayList

class Pp : Lp() {
    @JvmField
    var g: Gson? = null

    init {
        i()
    }

    companion object {
        @JvmField
        var f: Pp? = null

        @JvmStatic
        fun h(): Pp {
            if (f == null) {
                synchronized(Pp::class.java) {
                    if (f == null) {
                        f = Pp()
                    }
                }
            }
            return f!!
        }
    }

    fun a(str: String): MoreBlockCollectionBean? {
        for (bean in e ?: return null) {
            if (bean.name == str) {
                return MoreBlockCollectionBean(bean.name, bean.reserved1, gC.a(g, bean.data))
            }
        }
        return null
    }

    fun a(str: String, str2: String, arrayList: ArrayList<BlockBean>, z: Boolean) {
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
        for (blockBean in arrayList) {
            sb.append(g!!.toJson(blockBean))
            sb.append("\n")
        }
        list.add(CollectionBean(str, sb.toString(), str2))
        if (z) {
            e()
        }
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
        a = "${wq.a()}${File.separator}more_block${File.separator}list"
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

    fun f(): ArrayList<MoreBlockCollectionBean> {
        if (e == null) {
            a()
        }
        if (g == null) {
            i()
        }
        val result = ArrayList<MoreBlockCollectionBean>()
        for (bean in e!!) {
            result.add(MoreBlockCollectionBean(bean.name, bean.reserved1, gC.a(g, bean.data)))
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
