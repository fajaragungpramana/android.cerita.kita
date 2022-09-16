package com.github.fajaragungpramana.ceritakita.widget.view

import android.content.Context
import android.content.res.ColorStateList
import android.text.InputFilter
import android.text.InputType
import android.text.method.KeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.github.fajaragungpramana.ceritakita.widget.R
import com.github.fajaragungpramana.ceritakita.widget.databinding.AppEditTextBinding
import com.github.fajaragungpramana.ceritakita.widget.extension.getApplicationColor
import com.github.fajaragungpramana.ceritakita.widget.extension.isValidEmail
import com.github.fajaragungpramana.ceritakita.widget.extension.isValidPassword
import com.github.fajaragungpramana.ceritakita.widget.extension.visible
import com.google.android.material.textfield.TextInputEditText

class AppEditText(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var mViewBinding: AppEditTextBinding

    val field: TextInputEditText
        get() = mViewBinding.tieInput

    var hint: String?
        set(value) {
            mViewBinding.tilInput.hint = value
        }
        get() = mViewBinding.tilInput.hint.toString()

    var text: String?
        set(value) {
            mViewBinding.tieInput.setText(value)
        }
        get() = mViewBinding.tieInput.text.toString()

    var isEnabled: Boolean?
        set(value) {
            mViewBinding.tieInput.isEnabled = value ?: true
        }
        get() = mViewBinding.tieInput.isEnabled

    var inputType: Int?
        set(value) {
            mViewBinding.tieInput.inputType = value ?: InputType.TYPE_CLASS_TEXT
        }
        get() = mViewBinding.tieInput.inputType

    var maxLines: Int?
        set(value) {
            mViewBinding.tieInput.maxLines = value ?: 10
        }
        get() = mViewBinding.tieInput.maxLines

    var length: Int?
        set(value) {
            mViewBinding.tieInput.filters = arrayOf(InputFilter.LengthFilter(value ?: 100))
        }
        get() = mViewBinding.tieInput.length()

    var keyListener: KeyListener?
        set(value) {
            mViewBinding.tieInput.keyListener = value
        }
        get() = mViewBinding.tieInput.keyListener

    var errorMessage: String?
        set(value) {
            mViewBinding.mtvErrorInput.text = value
        }
        get() = mViewBinding.mtvErrorInput.text.toString()

    var isErrorEnabled: Boolean?
        set(value) {
            val isError = value ?: false
            val color =
                context.getApplicationColor(if (isError) R.color.color_primary_red else R.color.color_primary)

            mViewBinding.mtvErrorInput.visible(isError)

            mViewBinding.tilInput.hintTextColor = ColorStateList.valueOf(color)
            mViewBinding.tilInput.boxStrokeColor = color

        }
        get() = mViewBinding.mtvErrorInput.isVisible

    init {
        mViewBinding = AppEditTextBinding.inflate(LayoutInflater.from(context), this, true)

        context.obtainStyledAttributes(attrs, R.styleable.AppEditText, 0, 0).apply {
            hint = getString(R.styleable.AppEditText_appEditTextHint)
            text = getString(R.styleable.AppEditText_appEditTextValue)
            isEnabled = getBoolean(R.styleable.AppEditText_appEditTextEnable, true)
            maxLines = getInteger(R.styleable.AppEditText_appEditTextMaxLines, 10)
            length = getInteger(R.styleable.AppEditText_appEditTextMaxLength, 100)
            inputType = getInteger(
                R.styleable.AppEditText_appEditTextInputType,
                InputType.TYPE_CLASS_TEXT
            )
            errorMessage = getString(R.styleable.AppEditText_appEditTextErrorMessage)
            isErrorEnabled = getBoolean(R.styleable.AppEditText_appEditTextErrorEnable, false)
        }.recycle()

        initTextErrorListener()
    }

    private fun initTextErrorListener() {
        mViewBinding.tieInput.addTextChangedListener {
            val text = it.toString().trim()

            when (inputType?.minus(1)) {
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {
                    isErrorEnabled = !text.isValidEmail()
                }
                InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
                    isErrorEnabled = !text.isValidPassword()
                }
            }
        }
    }

    fun addTextChangedListener(invoke: (String?) -> Unit) {
        mViewBinding.tieInput.addTextChangedListener { invoke(it.toString().trim()) }
    }

}