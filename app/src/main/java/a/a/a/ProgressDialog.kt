package a.a.a

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.TextView
import com.sketchware.remod.R

class ProgressDialog(var1: Context) : Dialog(var1) {
    private val tvProgress: TextView
    private var isCancelable = false

    init {
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_msg_box)
        setTitle(xB.b().a(var1, R.string.common_message_progress))
        tvProgress = findViewById<View>(R.id.tv_progress) as TextView
        tvProgress.text = xB.b().a(var1, R.string.common_message_loading)
        super.setCanceledOnTouchOutside(false)
        super.setCancelable(true)
    }

    fun setProgress(progress: String?) {
        tvProgress.text = progress
    }

    fun setIsCancelable(cancelable: Boolean) {
        isCancelable = cancelable
    }

    fun isDialogCancelable(): Boolean {
        return isCancelable
    }

    override fun onBackPressed() {
        if (!isCancelable) {
            super.onBackPressed()
        }
    }
}
