package ru.iandreyshev.mymusicapplication.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_player.*
import ru.iandreyshev.model.player.PlayingState
import ru.iandreyshev.mymusicapplication.R
import ru.iandreyshev.mymusicapplication.application.MusicApplication
import ru.iandreyshev.mymusicapplication.presenter.PlayerPresenter
import ru.iandreyshev.mymusicapplication.utils.disable
import ru.iandreyshev.mymusicapplication.utils.enable

class PlayerActivity : AppCompatActivity(), PlayerPresenter.IView {

    private val mPlayerPresenter = MusicApplication.getPlayerPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        btnStop.setBackgroundResource(R.drawable.icon_stop)
        btnStop.setOnClickListener { mPlayerPresenter.onStop() }

        btnPlay.setBackgroundResource(R.drawable.icon_play)
        btnPlay.setOnClickListener { mPlayerPresenter.onPlay() }

        btnRestart.setBackgroundResource(R.drawable.icon_restart)
        btnRestart.setOnClickListener { mPlayerPresenter.onRestart() }

        sbTimeLine.progress = TIMELINE_MIN
        sbTimeLine.max = TIMELINE_MAX
        sbTimeLine.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar?.progress?.toFloat() ?: return
                mPlayerPresenter.onChangeTimePosition(progress / TIMELINE_MAX)
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mPlayerPresenter.onAttach(this)
    }

    override fun onPause() {
        super.onPause()
        mPlayerPresenter.onDetach(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing) {
            mPlayerPresenter.onFinish(this)
        }
    }

    override fun updateTitle(title: String) {
        tvTitle.text = title
    }

    override fun updateTimeline(progress: Float, currentTime: String) {
        tvTime.text = currentTime
        sbTimeLine.progress = (TIMELINE_MAX * progress).toInt()
    }

    override fun updatePlaying(state: PlayingState) {
        when (state) {
            PlayingState.Disabled -> {
                btnStop.disable()
                btnPlay.disable()
                btnRestart.disable()
                sbTimeLine.disable()

                btnPlay.setBackgroundResource(R.drawable.icon_play)
            }
            PlayingState.Idle -> {
                btnStop.disable()
                btnPlay.enable()
                btnRestart.disable()
                sbTimeLine.disable()

                btnPlay.setBackgroundResource(R.drawable.icon_play)
            }
            PlayingState.Playing -> {
                btnStop.enable()
                btnPlay.enable()
                btnRestart.enable()
                sbTimeLine.enable()

                btnPlay.setBackgroundResource(R.drawable.icon_pause)
            }
            PlayingState.Paused -> {
                btnStop.enable()
                btnPlay.enable()
                btnRestart.enable()
                sbTimeLine.disable()

                btnPlay.setBackgroundResource(R.drawable.icon_play)
            }
        }
    }

    companion object {
        private const val TIMELINE_MIN = 0
        private const val TIMELINE_MAX = 100
    }

}
