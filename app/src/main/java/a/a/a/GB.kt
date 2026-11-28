package a.a.a

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.util.DisplayMetrics
import com.google.android.gms.common.GoogleApiAvailability
import java.util.Locale

@Suppress("DEPRECATION")
object GB {
    @JvmStatic
    fun a(context: Context): Int = wB.a(context, 48.0f).toInt()

    @JvmStatic
    fun a(): String? {
        return Build.SUPPORTED_ABIS.getOrNull(0)
    }

    @JvmStatic
    fun a(context: Context, i: Int) {
        val className = context.packageManager
            .getLaunchIntentForPackage(context.packageName)!!
            .component!!
            .className
        val intent = Intent("android.intent.action.BADGE_COUNT_UPDATE")
        intent.putExtra("badge_count", i)
        intent.putExtra("badge_count_package_name", context.packageName)
        intent.putExtra("badge_count_class_name", className)
        context.sendBroadcast(intent)
    }

    @JvmStatic
    fun a(activity: Activity): Boolean {
        val api = GoogleApiAvailability.getInstance()
        val status = api.isGooglePlayServicesAvailable(activity)
        if (status == 0) {
            return true
        }
        api.isUserResolvableError(status)
        return false
    }

    @JvmStatic
    fun b(): String {
        for (field in Build.VERSION_CODES::class.java.fields) {
            val name = field.name
            try {
                if (field.getInt(Any()) == Build.VERSION.SDK_INT) {
                    return name
                }
            } catch (_: Exception) {
                return ""
            }
        }
        return ""
    }

    @JvmStatic
    fun b(context: Context): String = ""

    @JvmStatic
    fun b(context: Context, i: Int): Boolean {
        return (context.getSystemService(Context.SENSOR_SERVICE) as SensorManager).getDefaultSensor(i) != null
    }

    @JvmStatic
    fun b(activity: Activity): FloatArray {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return floatArrayOf(metrics.xdpi, metrics.ydpi)
    }

    @JvmStatic
    fun c(): Long {
        return try {
            StatFs(Environment.getExternalStorageDirectory().path).freeBytes / 1048576L
        } catch (_: Exception) {
            -1L
        }
    }

    @JvmStatic
    fun c(context: Context): ArrayList<String> {
        val list = ArrayList<String>()
        val accounts = AccountManager.get(context).getAccountsByType("com.google")
        for (account in accounts) {
            if (account.type == "com.google") {
                list.add(account.name)
            }
        }
        return list
    }

    @JvmStatic
    fun c(activity: Activity): IntArray {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return intArrayOf(metrics.widthPixels, metrics.heightPixels)
    }

    @JvmStatic
    fun d(context: Context): Int {
        var versionCode = 0
        try {
            versionCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCode
    }

    @JvmStatic
    fun e(context: Context): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    @JvmStatic
    fun f(context: Context): Int {
        val id = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (id > 0) context.resources.getDimensionPixelSize(id) else 0
    }

    @JvmStatic
    fun g(context: Context): Locale {
        return context.resources.configuration.locales[0]
    }

    @JvmStatic
    fun h(context: Context): Boolean {
        val types = intArrayOf(0, 1)
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            for (type in types) {
                val info = cm.activeNetworkInfo
                if (info != null && info.type == type) {
                    return true
                }
            }
            false
        } catch (_: Exception) {
            false
        }
    }
}
