package a.a.a

import com.besome.sketch.beans.CollectionBean
import com.besome.sketch.beans.ProjectResourceBean
import java.io.File
import java.io.IOException
import java.util.ArrayList

class Op : Lp() {
    companion object {
        @JvmField
        var f: Op? = null

        @JvmStatic
        fun g(): Op {
            if (f == null) {
                synchronized(Op::class.java) {
                    if (f == null) {
                        f = Op()
                    }
                }
            }
            return f!!
        }
    }

    fun a(str: String): ProjectResourceBean? {
        for (bean in f()) {
            if (bean.resName == str) {
                return bean
            }
        }
        return null
    }

    fun a(projectResourceBean: ProjectResourceBean, str: String, z: Boolean) {
        val list = e ?: return
        for (i in list.size - 1 downTo 0) {
            val bean = list[i]
            if (bean.name == projectResourceBean.resName) {
                bean.name = str
                break
            }
        }
        if (z) {
            e()
        }
    }

    fun a(str: String, projectResourceBean: ProjectResourceBean) {
        a(str, projectResourceBean, true)
    }

    fun a(str: String, projectResourceBean: ProjectResourceBean, z: Boolean) {
        if (e == null) {
            a()
        }
        val list = e!!
        for (bean in list) {
            if (bean.name == projectResourceBean.resName) {
                throw yy("duplicate_name")
            }
        }
        val baseName = projectResourceBean.resName
        val targetName = if (projectResourceBean.isNinePatch) "$baseName.9.png" else "$baseName.png"
        val destPath = "$b${File.separator}$targetName"
        val fileUtil = c!!
        if (projectResourceBean.savedPos == 1) {
            val srcPath = projectResourceBean.resFullName
            if (!fileUtil.e(srcPath)) {
                throw yy("file_no_exist")
            }
            try {
                fileUtil.f(b!!)
                iB.a(srcPath, destPath, projectResourceBean.rotate, projectResourceBean.flipHorizontal, projectResourceBean.flipVertical)
            } catch (_: IOException) {
                throw yy("fail_to_copy")
            }
        } else {
            val srcPath = "${wq.g()}${File.separator}$str${File.separator}${projectResourceBean.resFullName}"
            if (!fileUtil.e(srcPath)) {
                throw yy("file_no_exist")
            }
            try {
                fileUtil.f(b!!)
                fileUtil.a(srcPath, destPath)
            } catch (_: IOException) {
                throw yy("fail_to_copy")
            }
        }
        list.add(CollectionBean(projectResourceBean.resName, targetName))
        if (z) {
            e()
        }
    }

    fun a(str: String, arrayList: ArrayList<ProjectResourceBean>, z: Boolean) {
        if (e == null) {
            a()
        }
        val list = e!!
        val duplicates = ArrayList<String>()
        for (bean in list) {
            for (req in arrayList) {
                if (bean.name == req.resName) {
                    duplicates.add(bean.name)
                }
            }
        }
        if (duplicates.isNotEmpty()) {
            val err = yy("duplicate_name")
            err.a(duplicates)
            throw err
        }
        val fileUtil = c!!
        val missing = ArrayList<String>()
        for (req in arrayList) {
            val srcPath = if (req.savedPos == 0) {
                "${wq.g()}${File.separator}$str${File.separator}${req.resFullName}"
            } else {
                req.resFullName
            }
            if (!fileUtil.e(srcPath)) {
                missing.add(req.resName)
            }
        }
        if (missing.isNotEmpty()) {
            val err = yy("file_no_exist")
            err.a(missing)
            throw err
        }
        val failed = ArrayList<String>()
        val copied = ArrayList<String>()
        val pendingBeans = ArrayList<CollectionBean>()
        for (req in arrayList) {
            val baseName = req.resName
            val targetName = if (req.isNinePatch) "$baseName.9.png" else "$baseName.png"
            val srcPath = if (req.savedPos == 0) {
                "${wq.g()}${File.separator}$str${File.separator}${req.resFullName}"
            } else {
                req.resFullName
            }
            val destPath = "$b${File.separator}$targetName"
            try {
                fileUtil.f(b!!)
                iB.a(srcPath, destPath, req.rotate, req.flipHorizontal, req.flipVertical)
                pendingBeans.add(CollectionBean(req.resName, targetName))
                copied.add(destPath)
            } catch (_: IOException) {
                failed.add(req.resName)
            }
        }
        if (failed.isEmpty()) {
            list.addAll(pendingBeans)
            if (z) {
                e()
            }
            return
        }
        val err = yy("fail_to_copy")
        err.a(failed)
        for (path in copied) {
            fileUtil.c(path)
        }
        throw err
    }

    fun a(str: String, z: Boolean) {
        val list = e ?: return
        for (i in list.size - 1 downTo 0) {
            val bean = list[i]
            if (bean.name == str) {
                c?.c("$b${File.separator}${bean.data}")
                list.removeAt(i)
            }
        }
        if (z) {
            e()
        }
    }

    override fun b() {
        a = "${wq.a()}${File.separator}image${File.separator}list"
        b = "${wq.a()}${File.separator}image${File.separator}data"
    }

    fun b(str: String): Boolean {
        for (bean in f()) {
            if (bean.resName == str) {
                return true
            }
        }
        return false
    }

    override fun d() {
        super.d()
        f = null
    }

    fun f(): ArrayList<ProjectResourceBean> {
        if (e == null) {
            a()
        }
        val result = ArrayList<ProjectResourceBean>()
        for (bean in e!!) {
            result.add(ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, bean.name, bean.data))
        }
        return result
    }
}
