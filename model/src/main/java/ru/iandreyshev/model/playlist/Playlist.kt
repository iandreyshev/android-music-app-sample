package ru.iandreyshev.model.playlist

import ru.iandreyshev.model.repository.ISong

class Playlist(
    songs: List<ISong>,
    private val onSelectSong: (id: Long) -> Unit
) : IPlaylist {

    private var mPresenter: IPlaylistPresenter? = null
    private val mTracks: List<ITrack>

    init {
        /*
        * Преобразуем объекты репозитория в объекты модели используя функцию map
        * Функция map трансформирует каждый объект коллекции используя лямбду
        * */
        mTracks = songs.map { song ->
            /*
            * Внутри каждая песня приобразуется в объект Track, который будет вызывать лямбду,
            * переданную в конструкторе, когда трек будет выбран из списка
            * */
            Track(title = song.title, durationInMillis = 0) {
                onSelectSong(song.id)
            }
        }
    }

    /*
    * Подписавшийся на список презентер должен сразу получить информацию,
    * которая может быть отображена
    * */
    override fun subscribe(presenter: IPlaylistPresenter) {
        mPresenter = presenter
        mPresenter?.updatePlaylist(mTracks)
    }

    /*
    * Презентер может отписаться от модели. Например, если его жизненный цикл короче,
    * чем жизненный цикл модели.
    * */
    override fun unsubscribe() {
        mPresenter = null
    }

}
