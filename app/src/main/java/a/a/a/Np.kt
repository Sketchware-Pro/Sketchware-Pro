package a.a.a

import com.besome.sketch.beans.CollectionBean
import com.besome.sketch.beans.ProjectResourceBean
import java.io.File
import java.io.IOException
import java.util.ArrayList

class Np : Lp() {
    companion object {
        @JvmField
        var f: Np? = null

        @JvmStatic
        fun g(): Np {
            if (f == null) {
                synchronized(Np::class.java) {
                    if (f == null) {
                        f = Np()
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
        var targetName = projectResourceBean.resName
        if (projectResourceBean.resFullName.contains(".")) {
            val fullName = projectResourceBean.resFullName
            targetName += fullName.substring(fullName.lastIndexOf('.'))
        }
        val destPath = "$b${File.separator}$targetName"
        val fileUtil = c!!
        if (projectResourceBean.savedPos == 1) {
            val srcPath = projectResourceBean.resFullName
            if (!fileUtil.e(srcPath)) {
                throw yy("file_no_exist")
            }
            try {
                fileUtil.f(b!!)
                fileUtil.a(srcPath, destPath)
            } catch (_: IOException) {
                throw yy("fail_to_copy")
            }
        } else {
            val srcPath = "${wq.d()}${File.separator}$str${File.separator}${projectResourceBean.resFullName}"
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

    fun a(str: String, z: Boolean) {
        val list = e ?: return
        for (i in list.size - 1 downTo 0) {
            val bean = list[i]
            if (bean.name == str) {
                list.removeAt(i)
                c?.c("$b${File.separator}${bean.data}")
                break
            }
        }
        if (z) {
            e()
        }
    }

    override fun b() {
        a = "${wq.a()}${File.separator}font${File.separator}list"
        b = "${wq.a()}${File.separator}font${File.separator}data"
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
