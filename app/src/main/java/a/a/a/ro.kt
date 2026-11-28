package a.a.a

import android.content.Context

class ro(context: Context) {
    @JvmField
    var a: DB = DB(context, "U1")

    @JvmField
    var b: Int = a.d("U1I0")

    @JvmField
    var c: Int = (if (b > 3) 3 else b) * 20
}
