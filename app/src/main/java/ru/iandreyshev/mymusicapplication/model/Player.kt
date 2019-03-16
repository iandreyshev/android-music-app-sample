package ru.iandreyshev.mymusicapplication.model

class Player : IPlayer {

    interface IPlayerPresenter {
        fun updateTrack()
        fun updatePlaying()
        fun updateTimeLine()
    }

    fun setTrack() {
    }

    override fun onStop() {
    }

    override fun onPausePlay() {
    }

    override fun onRestart() {
    }

}
