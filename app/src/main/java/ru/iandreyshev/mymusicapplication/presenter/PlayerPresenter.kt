package ru.iandreyshev.mymusicapplication.presenter

import android.content.res.Resources
import ru.iandreyshev.model.player.IPlayerPresenter
import ru.iandreyshev.model.player.Player
import ru.iandreyshev.model.player.PlayingState
import ru.iandreyshev.model.player.Timeline
import ru.iandreyshev.mymusicapplication.R
import ru.iandreyshev.mymusicapplication.utils.toHumanReadableTime

class PlayerPresenter(
    private val resources: Resources,
    private val player: Player
) : IPlayerPresenter {

    private val mViewMap = mutableMapOf<IView, Boolean>()

    private var mTitle: String = ""
    private var mPoster: Int = R.drawable.icon_unknown_author
    private var mTimeline: Timeline = Timeline(0, 0f)
    private var mPlayingState: PlayingState = PlayingState.Disabled

    fun onPlay() = player.onPlay()
    fun onStop() = player.onStop()
    fun onRestart() = player.onRestart()
    fun onChangeTimePosition(timePercent: Float) = player.onChangeTimelinePosition(timePercent)

    fun onAttach(view: IView) {
        mViewMap[view] = true

        view.updateTitle(mTitle)
        val time = mTimeline.timeInMillis.toHumanReadableTime()
        val progress = mTimeline.percent
        view.updateTimeline(progress, time)
        view.updatePlaying(mPlayingState)
        view.updatePoster(mPoster)
    }

    fun onDetach(view: IView) {
        if (mViewMap.contains(view)) {
            mViewMap[view] = false
        }
    }

    fun onFinish(view: IView) {
        mViewMap.remove(view)
    }

    override fun updateTitle(title: String?) {
        mTitle = title ?: resources.getString(R.string.player_song_not_selected)
        updateView { view ->
            view.updateTitle(mTitle)
        }
    }

    override fun updateTimeline(timeline: Timeline) {
        mTimeline = timeline
        updateView { view ->
            val time = mTimeline.timeInMillis.toHumanReadableTime()
            val progress = mTimeline.percent
            view.updateTimeline(progress, time)
        }
    }

    override fun updatePlaying(state: PlayingState) {
        mPlayingState = state
        updateView { view ->
            view.updatePlaying(mPlayingState)
        }
    }

    override fun updatePoster(resource: Int?) {
        mPoster = resource ?: R.drawable.icon_unknown_author

        updateView { view ->
            view.updatePoster(mPoster)
        }
    }

    private fun updateView(updateCallback: (IView) -> Unit) {
        mViewMap.entries.forEach {
            if (it.value) {
                updateCallback(it.key)
            }
        }
    }

    interface IView {
        fun updateTitle(title: String)
        fun updatePoster(resource: Int)
        fun updateTimeline(progress: Float, currentTime: String)
        fun updatePlaying(state: PlayingState)
    }

}
