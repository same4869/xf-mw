package com.hfzq.xfn.framework.middleware.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.hfzq.framework.middleware.databinding.ViewGlobalLoadingViewBinding
import com.hfzq.xfn.framework.commlib.dp2px

class GlobalLoadingView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    private var binding: ViewGlobalLoadingViewBinding? = null

    init {
        binding = ViewGlobalLoadingViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var autoAttachHost = false
    fun show(
        activity: Activity,
        autoAttachHost: Boolean = false,
    ) {
        if (isInEditMode || activity.isFinishing || activity.isDestroyed) return
        this.autoAttachHost = autoAttachHost
        if (autoAttachHost) {
            attachLoadingViewAndShowAnimation(activity)
        } else {
            if (parent != null) {    //这个 view 已经被加入到当前 view tree中
                visibility = View.VISIBLE
                innerShow()
            }
        }
    }

    private fun attachLoadingViewAndShowAnimation(
        activity: Activity
    ) {
        val activityContent = activity.findViewById<ViewGroup>(android.R.id.content) ?: return
        if (parent != null) return
        activityContent.addView(this)
        innerShow()
    }

    private fun innerShow() {
        try {
//            val view = SoraStatusGroup.defaultStatusViewProvider?.createStatusView(
//                context,
//                SoraStatusGroup.LOADING
//            )?.view
            binding?.globalLoadingParentLayout?.addView(ProgressBar(context).apply {
                layoutParams = LinearLayout.LayoutParams(20.dp2px,20.dp2px)
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 隐藏全局 loading,
     *
     * autoAttachHost 设置为true 会尝试 detach from  android.R.content 上。 否则会设置为GONE，并停止动画
     * */
    fun hide(
        activity: Activity
    ) {
        if (isInEditMode || activity.isFinishing || activity.isDestroyed) return

        if (autoAttachHost) {
            detachLoadingViewAndHideAnimation(activity)
        } else {
            if (parent != null) {
                visibility = View.GONE
                innerHide()
            }
        }
    }

    private fun detachLoadingViewAndHideAnimation(
        activity: Activity
    ) {
        val activityContent = activity.findViewById<ViewGroup>(android.R.id.content) ?: return
        if (parent == null) return
        activityContent.removeView(this)
        innerHide()
    }

    private fun innerHide() {
        try {
            binding?.globalLoadingParentLayout?.removeAllViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
