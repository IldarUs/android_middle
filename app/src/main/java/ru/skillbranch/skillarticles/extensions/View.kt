package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

fun View.setMarginOptionally(
        left: Int = marginLeft,
        top: Int = marginTop,
        right: Int = marginRight,
        bottom: Int = marginBottom
) {
    if (layoutParams is MarginLayoutParams) {
        (layoutParams as MarginLayoutParams).setMargins(left, top, right, bottom)
        requestLayout()
    }
}