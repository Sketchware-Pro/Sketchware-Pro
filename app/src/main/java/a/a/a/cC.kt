package a.a.a

import com.besome.sketch.beans.HistoryViewBean
import com.besome.sketch.beans.ViewBean
import java.util.ArrayList
import java.util.HashMap

class cC(@JvmField var d: String) {
    @JvmField
    var c: MutableMap<String, ArrayList<HistoryViewBean>>? = HashMap()

    @JvmField
    var b: MutableMap<String, Int>? = HashMap()

    companion object {
        @JvmField
        var a: cC? = null

        @JvmStatic
        fun a() {
            val instance = a
            if (instance != null) {
                instance.d = ""
                instance.c = null
                instance.b = null
            }
            a = null
        }

        @JvmStatic
        fun c(str: String): cC {
            if (a == null) {
                synchronized(cC::class.java) {
                    if (a == null || a!!.d != str) {
                        a = cC(str)
                    }
                }
            }
            return a!!
        }
    }

    fun a(str: String) {
        val indexMap = b ?: return
        if (indexMap.containsKey(str)) {
            val list = c?.get(str) ?: return
            val limit = indexMap[str]!!
            for (size in list.size downTo limit + 1) {
                list.removeAt(size - 1)
            }
        }
    }

    fun a(str: String, historyViewBean: HistoryViewBean) {
        val historyMap = c ?: return
        if (!historyMap.containsKey(str)) {
            e(str)
        }
        val list = historyMap[str]!!
        list.add(historyViewBean)
        if (list.size > 50) {
            list.removeAt(0)
        } else {
            d(str)
        }
    }

    fun a(str: String, viewBean: ViewBean) {
        val list = ArrayList<ViewBean>()
        list.add(viewBean)
        a(str, list)
    }

    fun a(str: String, viewBean: ViewBean, viewBean2: ViewBean) {
        if (viewBean.isEqual(viewBean2)) {
            return
        }
        val bean = HistoryViewBean()
        bean.actionUpdate(viewBean, viewBean2)
        if (c?.containsKey(str) != true) {
            e(str)
        }
        a(str)
        a(str, bean)
    }

    fun a(str: String, arrayList: ArrayList<ViewBean>) {
        val bean = HistoryViewBean()
        bean.actionAdd(arrayList)
        if (c?.containsKey(str) != true) {
            e(str)
        }
        a(str)
        a(str, bean)
    }

    private fun b(str: String) {
        val indexMap = b ?: return
        if (!indexMap.containsKey(str)) {
            e(str)
        }
        val current = indexMap[str]!!
        if (current == 0) {
            return
        }
        indexMap[str] = current - 1
    }

    fun b(str: String, viewBean: ViewBean) {
        val bean = HistoryViewBean()
        bean.actionMove(viewBean)
        if (c?.containsKey(str) != true) {
            e(str)
        }
        a(str)
        a(str, bean)
    }

    fun b(str: String, arrayList: ArrayList<ViewBean>) {
        val bean = HistoryViewBean()
        bean.actionRemove(arrayList)
        if (c?.containsKey(str) != true) {
            e(str)
        }
        a(str)
        a(str, bean)
    }

    private fun d(str: String) {
        val indexMap = b ?: return
        if (!indexMap.containsKey(str)) {
            e(str)
        }
        indexMap[str] = indexMap[str]!! + 1
    }

    fun e(str: String) {
        c?.put(str, ArrayList())
        b?.put(str, 0)
    }

    fun f(str: String): Boolean {
        val indexMap = b ?: return false
        val historyMap = c ?: return false
        return indexMap.containsKey(str) && indexMap[str]!! < historyMap[str]!!.size
    }

    fun g(str: String): Boolean {
        val indexMap = b ?: return false
        return indexMap.containsKey(str) && indexMap[str]!! > 0
    }

    fun h(str: String): HistoryViewBean? {
        if (!f(str)) {
            return null
        }
        val index = b!![str]!!
        d(str)
        return c!![str]!![index].clone()
    }

    fun i(str: String): HistoryViewBean? {
        if (!g(str)) {
            return null
        }
        val index = b!![str]!!
        b(str)
        return c!![str]!![index - 1].clone()
    }
}
