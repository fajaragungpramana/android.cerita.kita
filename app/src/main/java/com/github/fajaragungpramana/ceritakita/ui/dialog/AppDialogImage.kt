package com.github.fajaragungpramana.ceritakita.ui.dialog

import android.os.Bundle
import android.view.ViewGroup
import com.github.fajaragungpramana.ceritakita.databinding.DialogImageBinding
import com.github.fajaragungpramana.ceritakita.widget.app.AppBottomSheet

class AppDialogImage : AppBottomSheet<DialogImageBinding>() {

    override fun onViewBinding(viewGroup: ViewGroup?) =
        DialogImageBinding.inflate(layoutInflater, viewGroup, false)

    override fun onCreated(savedInstanceState: Bundle?) {

    }

}