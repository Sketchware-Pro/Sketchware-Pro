package a.a.a

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.HashMap

object vB {
    @JvmStatic
    fun a(hashMap: HashMap<String, Any?>?): String {
        return Gson().toJson(hashMap)
    }

    @JvmStatic
    fun a(str: String?): HashMap<String, Any?>? {
        return Gson().fromJson(str, object : TypeToken<HashMap<String, Any?>>() {}.type)
    }
}
