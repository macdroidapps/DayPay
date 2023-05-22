package ru.macdroid.daypay.domain.reduce

interface IEffHandler<Ef, Ev> {

    /**
     * Интерфейс для обработки эффектов
     * handle - грязная функция, side effect, для походов в сеть, в базу и за пивком
     * На вход принимает эффект и функцию commit, которая будет вызвана с событием, не возвращает ничего,
     * это нужно, чтобы мы могли внутри нашей Feature скомпоновать все события и стйты с эффектами для описания UI
     */

    suspend fun handle(effect: Ef, commit: (Ev) -> Unit)

}
