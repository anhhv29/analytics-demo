package com.example.analytics1.view.dialog

import com.example.analytics1.base.fragment.BaseDialogFragment
import com.example.analytics1.databinding.DialogLoadingBinding

class LoadingDialog : BaseDialogFragment<DialogLoadingBinding>() {

    override fun getFragmentBinding() = DialogLoadingBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }
}