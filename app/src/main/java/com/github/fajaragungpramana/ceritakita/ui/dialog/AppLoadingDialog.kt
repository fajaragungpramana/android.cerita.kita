package com.github.fajaragungpramana.ceritakita.ui.dialog

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.github.fajaragungpramana.ceritakita.common.app.AppDialog
import com.github.fajaragungpramana.ceritakita.databinding.DialogLoadingBinding

class AppLoadingDialog(fm: FragmentManager) : AppDialog<DialogLoadingBinding>(fm) {

    override fun onViewBinding(viewGroup: ViewGroup?) =
        DialogLoadingBinding.inflate(layoutInflater, viewGroup, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        isCancelable = false
    }

}