package ru.iandreyshev.mymusicapplication.model

interface IPlayer {
    fun onStop()
    fun onPausePlay()
    fun onRestart()
}
