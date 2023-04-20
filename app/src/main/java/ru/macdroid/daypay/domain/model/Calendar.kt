package ru.macdroid.daypay.domain.model

data class Calendar(
    val status: Int = -1,
    val years: List<Int> = listOf()
)