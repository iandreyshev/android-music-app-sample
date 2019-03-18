package ru.iandreyshev.model.playlist

interface IPlaylist {
    fun subscribe(presenter: IPlaylistPresenter)
    fun unsubscribe()
}
