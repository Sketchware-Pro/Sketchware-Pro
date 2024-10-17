package mod.ilyasse.utils

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sketchware.remod.R

object UI {
    @JvmStatic
    fun circularImage(image: ImageView, url: String?) {
        Glide.with(image.context)
            .load(url)
            .placeholder(R.drawable.ic_user)
            .into(image)
    }

    @JvmStatic
    fun advancedCorners(view: View, color: Int) {
        val drawable = GradientDrawable()
        drawable.setColor(color)
        drawable.cornerRadii = floatArrayOf(0f, 0f, 30f, 30f, 30f, 30f, 0f, 0f)
        view.background = drawable
    }

    @JvmStatic
    fun shadAnim(view: View?, propertyName: String, value: Double, duration: Double) {
        val anim = ObjectAnimator()
        anim.target = view
        anim.setPropertyName(propertyName)
        anim.setFloatValues(value.toFloat())
        anim.setDuration(duration.toLong())
        anim.start()
    }

    @JvmStatic
    fun animateLayoutChanges(view: ViewGroup?) {
        val autoTransition = AutoTransition()
        autoTransition.duration = 300
        TransitionManager.beginDelayedTransition(view, autoTransition)
    }

    @JvmStatic
    fun rippleRound(view: View, focus: Int, pressed: Int, round: Double) {
        val drawable = GradientDrawable()
        drawable.setColor(focus)
        drawable.cornerRadius = round.toFloat()
        val rippleDrawable =
            RippleDrawable(ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressed)), drawable, null)
        view.background = rippleDrawable
    }
}
