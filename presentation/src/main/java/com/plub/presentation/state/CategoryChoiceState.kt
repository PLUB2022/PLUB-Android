package com.plub.presentation.state

data class CategoryChoiceState(
    val listOrGrid : Boolean = false
) : PageState
