package com.plub.presentation.util

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.Serializable

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = this / Resources.getSystem().displayMetrics.density

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.px: Float
    get() = this * Resources.getSystem().displayMetrics.density

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

fun TextView.requestAndClearFocus() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    clearFocus()
    isFocusable = false
    isFocusableInTouchMode = false
}

fun List<CreateGatheringQuestion>.deepCopy(): List<CreateGatheringQuestion> {
    val temp = mutableListOf<CreateGatheringQuestion>()
    forEach {
        temp.add(it.copy())
    }
    return temp
}

fun List<CreateGatheringQuestion>.deepCopyAfterUpdateQuestion(key: Int, question: String): List<CreateGatheringQuestion> {
    val temp = mutableListOf<CreateGatheringQuestion>()
    forEach {
        temp.add(it.copy())
    }
    temp.find { it.key == key }?.question = question
    return temp
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

fun String.fromHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun <T> Fragment.getNavigationResult(key: String = "result", singleCall : Boolean = true , result: (T) -> (Unit)) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        ?.observe(viewLifecycleOwner) {
            result(it)
            //if not removed then when click back without set data it will return previous data
            if(singleCall) findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)
        }
}

fun <T>Fragment.setNavigationResult(key: String = "result", result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun RecyclerView.infiniteScrolls(method: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisiblePosition =
                (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            val isBottom = lastVisiblePosition + 1 == adapter?.itemCount
            val isDownScroll = dy > 0

            if(isBottom and isDownScroll)
                method()
        }
    })
}

fun RecyclerView.setOnRecyclerViewClickListener(method: () -> Unit) {
    setOnTouchListener { view, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_UP)
            view.performClick()
        else
            false
    }

    onThrottleClick { _ ->
        method()
    }
}

fun View.setVisibleWithAnimation() {
    val animationDuration = 500L
    val visibleAnimation = AlphaAnimation(0f, 1f)
    visibleAnimation.duration = animationDuration
    visibility = View.VISIBLE
    animation = visibleAnimation
}

fun View.setInVisibleWithAnimation() {
    val animationDuration = 500L
    val invisibleAnimation = AlphaAnimation(1f, 0f)
    invisibleAnimation.duration = animationDuration
    visibility = View.INVISIBLE
    animation = invisibleAnimation
}

fun EditText.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}