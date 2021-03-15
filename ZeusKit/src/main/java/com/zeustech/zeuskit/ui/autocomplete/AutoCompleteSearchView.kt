package com.zeustech.zeuskit.ui.autocomplete

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.zeustech.zeuskit.ui.other.KeyboardManager
import com.zeustech.zeuskit.ui.views.TextFieldListener

open class AutoCompleteSearchView : AppCompatAutoCompleteTextView,
    TextWatcher,
    OnEditorActionListener,
    OnFocusChangeListener {

    var textFieldListener: TextFieldListener? = null

    constructor(context: Context) : super(context) { setUpListeners() }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { setUpListeners() }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { setUpListeners() }

    private fun setUpListeners() {
        onFocusChangeListener = this
        addTextChangedListener(this)
        setOnEditorActionListener(this)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) { }

    override fun afterTextChanged(s: Editable?) {
        textFieldListener?.onTextChanged(this, s.toString())
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        val v = view ?: return
        if (hasFocus) KeyboardManager().showKeyboard(v)
        else KeyboardManager().hideKeyboard(v)
        textFieldListener?.onFocusChanged(
            this,
            text.toString(),
            hasFocus
        )
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        val v = view ?: return false
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val text = v.text.toString()
            if (text.isNotEmpty()) {
                textFieldListener?.onActionChanged(this, text, actionId)
                KeyboardManager().hideKeyboard(v)
                return true
            }
        }
        return false
    }

}