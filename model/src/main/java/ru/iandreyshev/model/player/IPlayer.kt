package ru.iandreyshev.model.player

interface IPlayer {
    /*
    * Вызывается чтобы остановить проигрывание, если трек запущен
    * или чтобы запустить проигрование, если трек не запущен
    * */
    fun onPlay()
    /*
    * Вызывается, чтобы остновить проигрывание и перевести позицию проигрывания в начало
    * */
    fun onStop()
    /*
    * Вызывается чтобы запустить трек заного
    * */
    fun onRestart()
    /*
    * Вызывается, после смены текущей позиции таймлайна
    * */
    fun onChangeTimelinePosition(position: Float)

    fun subscribe(presenter: IPlayerPresenter)
    fun unsubscribe()
}
