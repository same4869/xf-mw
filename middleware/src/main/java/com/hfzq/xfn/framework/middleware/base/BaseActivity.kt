package com.hfzq.xfn.framework.middleware.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.hfzq.framework.middleware.R


/**
 *
 * @author: Luffy
 * @date: 2023/6/1
 * @Description
 */
abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    private var _binding: B? = null
    protected val binding: B?
        get() = _binding
    protected var toolbar: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (isDarkTheme()) {
//            setTheme(R.style.BaseAppTheme_Dark)
//        } else {
//            setTheme(R.style.BaseAppTheme_Light)
//        }
        _binding = createBinding()
        setContentView(binding?.root)

        setupToolbar()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (isDarkTheme()) {
//                window.statusBarColor = ContextCompat.getColor(this, R.color.toolbar_color_dark)
//            } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.main_bg_color_white)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //实现状态栏图标和文字颜色为暗色

//            }
        }

        initViewModel()
        initView()
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * 添加toolbar
     */
    private fun setupToolbar() {
        toolbar = binding?.root?.findViewById(R.id.toolbar)
        if (toolbar != null) {
//            setSupportActionBar(toolbar)
//            supportActionBar?.setDisplayShowCustomEnabled(true)
            val titleText = toolbar?.findViewById<TextView>(R.id.toolbar_title)
            val backBtn = toolbar?.findViewById<ImageView>(R.id.tool_nav_back)
            titleText?.text = getToolbarTitle()
            backBtn?.setOnClickListener {
                onBackPressed()
            }
        }
    }

    /**
     * 初始化viewmodel
     */
    open fun initViewModel() {}

    /**
     * 获取头部标题的Title
     */
    abstract fun getToolbarTitle(): String

    /**
     * 初始化界面UI
     */
    abstract fun initView()

    /**
     * 抓取界面所需数据
     */
    abstract fun fetchData()

    abstract fun createBinding(): B

    /**
     * 判断是否展示深色模式 1:浅色，0：深色
     */
    abstract fun isDarkTheme() : Boolean
}