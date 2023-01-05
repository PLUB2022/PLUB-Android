package com.plub.presentation.util

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun <T> HashSet<T>.removeElementAfterReturnNewHashSet(element: T): HashSet<T> {
    val tempHashSet = this.toHashSet()
    tempHashSet.remove(element)
    return tempHashSet
}

fun <T> HashSet<T>.addElementAfterReturnNewHashSet(element: T): HashSet<T> {
    val tempHashSet = this.toHashSet()
    tempHashSet.add(element)
    return tempHashSet
}

/**
 *  element가 존재하는 경우 remove, 존재하지 않는 경우 add
 */
fun <T> HashSet<T>.addOrRemoveElementAfterReturnNewHashSet(element: T): HashSet<T> {
    return if (element in this)
        removeElementAfterReturnNewHashSet(element)
    else
        addElementAfterReturnNewHashSet(element)
}

fun EditText.afterTextChanged(method: (editable: Editable?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            method(p0)
        }
    })
}