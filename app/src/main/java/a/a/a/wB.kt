package a.a.a

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

object wB {

    @JvmStatic
    fun a(context: Context, value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            context.resources.displayMetrics
        )
    }

    @JvmStatic
    fun a(context: Context, @LayoutRes layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, null)
    }

    @JvmStatic
    fun a(
        context: Context,
        parent: ViewGroup,
        @LayoutRes layoutRes: Int
    ): View {
        return LayoutInflater.from(context).inflate(layoutRes, parent, true)
    }
}