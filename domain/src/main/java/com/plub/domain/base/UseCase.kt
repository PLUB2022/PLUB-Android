package com.plub.domain.base

abstract class UseCase<PARAM,MODEL> {
    abstract operator fun invoke(request:PARAM): MODEL
}