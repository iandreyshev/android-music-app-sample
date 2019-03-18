package ru.iandreyshev.model.repository

import ru.iandreyshev.model.R

class Repository : IRepository {

    private val mSongs: List<Song> = listOf(
            Song(0, "Grand Theft Auto Vice City - Mission completed", R.raw.mission_completed),
            Song(1, "Bomfunk Mc's - Freestyler", R.raw.freestyler)
    )

    override fun getAllSongs(): List<ISong> {
        return mSongs
    }

    override fun getSongById(id: Long): ISong? {
        return mSongs.first { song -> song.id == id }
    }

}
