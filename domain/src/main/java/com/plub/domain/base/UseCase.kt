package com.plub.domain.base

abstract class UseCase<PARAM,MODEL> {
    abstract suspend operator fun invoke(request:PARAM): MODEL
}