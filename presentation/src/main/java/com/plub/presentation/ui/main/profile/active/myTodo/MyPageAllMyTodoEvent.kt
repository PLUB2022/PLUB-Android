package com.plub.presentation.ui.main.profile.active.myTodo

import com.plub.presentation.ui.Event

sealed class MyPageAllMyTodoEvent : Event{
    object GoToBack : MyPageAllMyTodoEvent()
}