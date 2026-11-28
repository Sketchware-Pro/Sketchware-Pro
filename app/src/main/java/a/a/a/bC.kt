package a.a.a

import com.besome.sketch.beans.BlockBean
import com.besome.sketch.beans.HistoryBlockBean
import java.util.ArrayList
import java.util.HashMap

class bC(@JvmField var d: String) {
    @JvmField
    var c: MutableMap<String, ArrayList<HistoryBlockBean>>? = HashMap()

    @JvmField
    var b: MutableMap<String, Int>? = HashMap()

    companion object {
        @JvmField
        var a: bC? = null

        @JvmStatic
        fun a(str: String, str2: String, str3: String): String = "${str}_${str2}_$str3"

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
        fun d(str: String): bC {
            if (a == null) {
                synchronized(bC::class.java) {
                    if (a == null || a!!.d != str) {
                        a = bC(str)
                    }
                }
            }
            return a!!
        }
    }

    private fun a(str: String) {
        val indexMap = b ?: return
        if (indexMap.containsKey(str)) {
            val list = c?.get(str) ?: return
            val limit = indexMap[str]!!
            for (size in list.size downTo limit + 1) {
                list.removeAt(size - 1)
            }
        }
    }

    fun a(str: String, blockBean: BlockBean, i: Int, i2: Int, blockBean2: BlockBean?, blockBean3: BlockBean?) {
        val list = ArrayList<BlockBean>()
        list.add(blockBean)
        a(str, list, i, i2, blockBean2, blockBean3)
    }

    fun a(str: String, blockBean: BlockBean, blockBean2: BlockBean) {
        if (blockBean.isEqual(blockBean2)) {
            return
        }
        val bean = HistoryBlockBean()
        bean.actionUpdate(blockBean, blockBean2)
        if (c?.containsKey(str) != true) {
            f(str)
        }
        a(str)
        a(str, bean)
    }

    private fun a(str: String, historyBlockBean: HistoryBlockBean) {
        val historyMap = c ?: return
        if (!historyMap.containsKey(str)) {
            f(str)
        }
        val list = historyMap[str]!!
        list.add(historyBlockBean)
        if (list.size > 50) {
            list.removeAt(0)
        } else {
            e(str)
        }
    }

    fun a(str: String, arrayList: ArrayList<BlockBean>, i: Int, i2: Int, blockBean: BlockBean?, blockBean2: BlockBean?) {
        val bean = HistoryBlockBean()
        bean.actionAdd(arrayList, i, i2, blockBean, blockBean2)
        if (c?.containsKey(str) != true) {
            f(str)
        }
        a(str)
        a(str, bean)
    }

    fun a(
        str: String,
        arrayList: ArrayList<BlockBean>,
        arrayList2: ArrayList<BlockBean>,
        i: Int,
        i2: Int,
        i3: Int,
        i4: Int,
        blockBean: BlockBean?,
        blockBean2: BlockBean?,
        blockBean3: BlockBean?,
        blockBean4: BlockBean?
    ) {
        val bean = HistoryBlockBean()
        bean.actionMove(arrayList, arrayList2, i, i2, i3, i4, blockBean, blockBean2, blockBean3, blockBean4)
        if (c?.containsKey(str) != true) {
            f(str)
        }
        a(str)
        a(str, bean)
    }

    fun b(str: String) {
        val historyMap = c ?: return
        if (historyMap.containsKey(str)) {
            historyMap.remove(str)
            b?.remove(str)
        }
    }

    fun b(str: String, arrayList: ArrayList<BlockBean>, i: Int, i2: Int, blockBean: BlockBean?, blockBean2: BlockBean?) {
        val bean = HistoryBlockBean()
        bean.actionRemove(arrayList, i, i2, blockBean, blockBean2)
        if (c?.containsKey(str) != true) {
            f(str)
        }
        a(str)
        a(str, bean)
    }

    private fun c(str: String) {
        val indexMap = b ?: return
        if (!indexMap.containsKey(str)) {
            f(str)
        }
        val current = indexMap[str]!!
        if (current == 0) {
            return
        }
        indexMap[str] = current - 1
    }

    private fun e(str: String) {
        val indexMap = b ?: return
        if (!indexMap.containsKey(str)) {
            f(str)
        }
        indexMap[str] = indexMap[str]!! + 1
    }

    fun f(str: String) {
        c?.put(str, ArrayList())
        b?.put(str, 0)
    }

    fun g(str: String): Boolean {
        val indexMap = b ?: return false
        val historyMap = c ?: return false
        return indexMap.containsKey(str) && indexMap[str]!! < historyMap[str]!!.size
    }

    fun h(str: String): Boolean {
        val indexMap = b ?: return false
        return indexMap.containsKey(str) && indexMap[str]!! > 0
    }

    fun i(str: String): HistoryBlockBean? {
        if (!g(str)) {
            return null
        }
        val index = b!![str]!!
        e(str)
        return c!![str]!![index].clone()
    }

    fun j(str: String): HistoryBlockBean? {
        if (!h(str)) {
            return null
        }
        val index = b!![str]!!
        c(str)
        return c!![str]!![index - 1].clone()
    }
}
