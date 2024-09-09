package com.example.analytics1.base.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.analytics1.R

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {
    protected lateinit var viewBinding: VB
    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBackgroundTransparent()
        viewBinding = inflateViewBinding(inflater, container)
        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog?.window?.setDecorFitsSystemWindows(false)
            dialog?.window?.insetsController?.hide(WindowInsets.Type.navigationBars())
            dialog?.window?.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            dialog?.window?.decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickView()
    }

    open fun clickView() {}

    override fun onStart() {
        super.onStart()
        dialog?.window
            ?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
        dialog?.window?.setBackgroundDrawableResource(R.color.white)
    }

    private fun setBackgroundTransparent() {
        dialog?.window?.setBackgroundDrawableResource(R.color.black50)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }
}
