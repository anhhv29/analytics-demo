package com.example.analytics1.view.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.analytics1.base.fragment.BaseDialogFragment
import com.example.analytics1.databinding.DialogLoadingBinding

class LoadingDialog : BaseDialogFragment<DialogLoadingBinding>() {

    companion object {
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogLoadingBinding = DialogLoadingBinding.inflate(layoutInflater)
}