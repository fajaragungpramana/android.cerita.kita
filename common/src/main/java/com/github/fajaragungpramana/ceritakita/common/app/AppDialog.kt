package com.github.fajaragungpramana.ceritakita.common.app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.github.fajaragungpramana.ceritakita.common.contract.AppObserver

abstract class AppDialog<VB : ViewBinding>(private val mFragmentManager: FragmentManager) :
    DialogFragment() {

    private lateinit var mViewBinding: VB
    protected val viewBinding: VB
        get() = mViewBinding

    protected abstract fun onViewBinding(viewGroup: ViewGroup?): VB

    protected abstract fun onCreated(savedInstanceState: Bundle?)

    protected open fun onMargin() = 104

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = onViewBinding(container)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val background = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(background, onMargin())

        dialog?.window?.setBackgroundDrawable(inset)

        onCreated(savedInstanceState)

        if (this is AppObserver) onStateObserver()

    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val fragmentTransaction = manager.beginTransaction()
            fragmentTransaction.add(this, tag)
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun showDialog(tag: String) = show(mFragmentManager, tag)

}