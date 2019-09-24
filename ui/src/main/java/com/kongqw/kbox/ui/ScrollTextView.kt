package com.kongqw.kbox.ui

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView

class ScrollTextView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {

    init {
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.MARQUEE
        isFocusable = true
        isFocusableInTouchMode = true

        // 无限循环滚动
        marqueeRepeatLimit = -1

        setSingleLine(true)

        maxLines = 1
    }

    override fun isFocused(): Boolean {
        // return super.isFocused()
        return true
    }
}