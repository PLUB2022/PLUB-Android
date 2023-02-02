package com.plub.presentation.ui.main.plubing

import androidx.lifecycle.viewModelScope
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class PlubingMainViewModel @Inject constructor(
) : BaseViewModel<PlubingMainPageState>(PlubingMainPageState()) {

    fun onAppBarOffsetChanged(height:Float, totalScrollRange:Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val offsetAlpha: Float = height / totalScrollRange
            val headerAlpha = abs(offsetAlpha)
            updateUiState {
                it.copy(
                    headerAlpha = headerAlpha
                )
            }
        }
    }
}
