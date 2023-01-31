package com.plub.presentation.ui.common.bindingAdapter

import androidx.databinding.BindingAdapter
import com.plub.domain.model.enums.DaysType
import com.plub.presentation.ui.common.custom.FixedTextWidthCheckBox

/**
 * https://stackoverflow.com/questions/60005152/cannot-use-same-bindingadapter-on-two-different-views
 * Unit으로 할 경우 DataBinding Complier가 올바르지 않은 java code를 생성함.
 * 따라서 Void를 사용
 */
@BindingAdapter("android:onClick")
fun FixedTextWidthCheckBox.bindFixedTextWidthCheckboxClickEvent(method: () -> Void) {
    checkBoxClickEvent = method
}

@BindingAdapter("dayHashSet", "day")
fun FixedTextWidthCheckBox.verifyIsChecked(
    dayHashSet: HashSet<DaysType>,
    day: DaysType
) {
    isChecked = day in dayHashSet
}