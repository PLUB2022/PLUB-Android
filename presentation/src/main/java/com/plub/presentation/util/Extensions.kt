package com.plub.presentation.util

import android.content.res.Resources

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