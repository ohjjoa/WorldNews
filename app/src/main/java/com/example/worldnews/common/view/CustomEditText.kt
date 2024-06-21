package com.example.worldnews.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.worldnews.R

class CustomEditText : AppCompatEditText, TextWatcher, OnTouchListener, OnFocusChangeListener {
    private var clearDrawable: Drawable? = null
    //private var searchDrawable: Drawable? = null
    private var onFocusChangeListener: Nothing? = null
    private var onTouchListener: OnTouchListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener?) {
        this.onFocusChangeListener = onFocusChangeListener as Nothing?
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener?) {
        this.onTouchListener = onTouchListener
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        val clearIconDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_highlight_off_black_24dp)
        clearDrawable = clearIconDrawable?.let { DrawableCompat.wrap(it) }
        clearDrawable?.let { DrawableCompat.setTintList(it, hintTextColors) }
        clearDrawable!!.setBounds(0, 0, clearDrawable!!.intrinsicWidth, clearDrawable!!.intrinsicHeight)

//        val searchIconDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_search_black_24dp)
//        searchDrawable = searchIconDrawable?.let { DrawableCompat.wrap(it) }
//        searchDrawable?.let { DrawableCompat.setTintList(it, hintTextColors) }
//        searchDrawable!!.setBounds(searchDrawable!!.intrinsicWidth, 0, 0, searchDrawable!!.intrinsicHeight)

        setClearIconVisible(false)
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
    }

    override fun onFocusChange(
        view: View,
        hasFocus: Boolean
    ) {
        if (hasFocus) {
            text?.isNotEmpty()?.let { setClearIconVisible(it) }
        } else {
            setClearIconVisible(false)
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.x.toInt()
        if (clearDrawable!!.isVisible && x > width - paddingRight - clearDrawable!!.intrinsicWidth) {
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                error = null
                text = null
            }
            return true
        }
        return if (onTouchListener != null) {
            onTouchListener!!.onTouch(view, motionEvent)
        } else {
            false
        }
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (isFocused) {
            setClearIconVisible(s.isNotEmpty())
        }
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}

    private fun setClearIconVisible(visible: Boolean) {
        clearDrawable!!.setVisible(visible, false)
        setCompoundDrawables(null, null, if (visible) clearDrawable else null, null)
    }
}