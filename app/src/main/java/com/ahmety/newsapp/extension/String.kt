package com.ahmety.newsapp.extension

import android.text.SpannableString
import android.text.style.UnderlineSpan

fun String.doUnderline() : SpannableString{
    val spannableString = SpannableString(this)
    spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)
    return spannableString
}