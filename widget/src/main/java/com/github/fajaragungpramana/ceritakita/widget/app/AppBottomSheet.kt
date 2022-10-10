package com.github.fajaragungpramana.ceritakita.widget.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class AppBottomSheet<VB : ViewBinding> : BottomSheetDialogFragment() {

    private lateinit var mViewBinding: VB
    protected val viewBinding: VB
        get() = mViewBinding

    protected abstract fun onViewBinding(viewGroup: ViewGroup?): VB

    protected abstract fun onCreated(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = onViewBinding(container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onCreated(savedInstanceState)

        if (this is AppObserver) onStateObserver()
    }

    interface AppObserver {
        fun onStateObserver()
    }

}