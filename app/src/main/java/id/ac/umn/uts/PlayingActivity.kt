package id.ac.umn.uts

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import id.ac.umn.uts.ListLaguActivity.Companion.musicFiles
import kotlinx.android.synthetic.main.activity_playing.*
import java.io.File


class PlayingActivity : AppCompatActivity() {
//    var song_name: TextView? = null
//    var artist_name:TextView? = null
//    var duration_played:TextView? = null
//    var duration_total:TextView? = null
//    var nextBtn:ImageView? = null
//    var prevBtn:ImageView? = null
//    var seekBar: SeekBar? = null
    var position = -1

    companion object {
        lateinit var listSongs: ArrayList<MusicFiles>
        lateinit var uri : Uri
        var mediaPlayer: MediaPlayer = MediaPlayer()
    }

    private val handler: Handler = Handler()
    private var playThread: Thread? = null
    private  var prevThread: Thread? = null
    private  var nextThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing)

        getIntentExtra()

        judulLaguMusic.text = listSongs.get(position).title

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        this.runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    // Get seekbar's time and convert into readable format
                    val mCurrentPosition = mediaPlayer.currentPosition.div(1000)
                    seekBar.progress = mCurrentPosition
                    durationPlayed.setText(formattedTime(mCurrentPosition))
                }
                handler.postDelayed(this, 1000)
            }
        })

        judulLaguMusic.isSelected = true
    }

    private fun formattedTime(mCurrentPosition: Int): String {
        var totalout = ""
        var totalNew = ""
        val seconds = (mCurrentPosition % 60).toString()
        val minutes = (mCurrentPosition / 60).toString()
        totalout = "$minutes:$seconds"
        totalNew = "$minutes:0$seconds"
        return if (seconds.length == 1) {
            totalNew
        } else {
            totalout
        }
    }

    private fun getIntentExtra(){
        position = getIntent().getIntExtra("position", -1)
        listSongs = musicFiles

        if(listSongs != null){
            toggleButton.setImageResource(R.drawable.pause)
//            uri = Uri.parse(listSongs.get(position).path)
            uri = Uri.fromFile(File(listSongs.get(position).path))
        }

        Log.i("alah", uri.toString())
        Log.i("alah", "diatas adalah path uri musicny")

//        mediaPlayer = MediaPlayer.create(this, uri)

        if(mediaPlayer != null){
            mediaPlayer.stop()
            mediaPlayer.release()
            mediaPlayer = MediaPlayer.create(this, uri)
            mediaPlayer.start();
        }else{
            mediaPlayer = MediaPlayer.create(this, uri)
            mediaPlayer.start()
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000)
        setDurationTotal()
    }

    fun setDurationTotal(){
        // Set Total Duration of music
        val durationTotalInt: Int = listSongs.get(position).duration!!.toInt() / 1000
        durationTotal.setText(formattedTime(durationTotalInt))
    }

    override fun onResume() {
        playThreadBtn()
        nextThreadBtn()
        prevThreadBtn()
        super.onResume()
    }

    private fun prevThreadBtn() {
        prevThread = object : Thread() {
            override fun run() {
                super.run()
                prevBtn.setOnClickListener { prevBtnClicked() }
            }
        }
        (prevThread as Thread).start()
    }

    private fun prevBtnClicked() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
            position = if (position - 1 < 0) listSongs.size - 1 else position - 1
            uri = Uri.parse(listSongs.get(position).path)
            mediaPlayer = MediaPlayer.create(applicationContext, uri)
            setDurationTotal()
            judulLaguMusic.text = listSongs.get(position).title
            seekBar.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val mCurrentPosition = mediaPlayer.currentPosition / 1000
                        seekBar.progress = mCurrentPosition
                    }
                    handler.postDelayed(this, 1000)
                }
            })
            toggleButton.setImageResource(R.drawable.pause)
            mediaPlayer.start()
        } else {
            mediaPlayer.stop()
            mediaPlayer.release()
            position = if (position - 1 < 0) listSongs.size - 1 else position - 1
            uri = Uri.parse(listSongs.get(position).path)
            mediaPlayer = MediaPlayer.create(applicationContext, uri)
            setDurationTotal()
            judulLaguMusic.text = listSongs.get(position).title
            seekBar.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val mCurrentPosition = mediaPlayer.currentPosition / 1000
                        seekBar.progress = mCurrentPosition
                    }
                    handler.postDelayed(this, 1000)
                }
            })
            toggleButton.setImageResource(R.drawable.play)
        }
    }

    private fun nextThreadBtn() {
        nextThread = object : Thread() {
            override fun run() {
                super.run()
                nextBtn.setOnClickListener { nextBtnClicked() }
            }
        }
        (nextThread as Thread).start()
    }

    private fun nextBtnClicked() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
            position = (position + 1) % listSongs.size
            uri = Uri.parse(listSongs.get(position).path)
            mediaPlayer = MediaPlayer.create(this, uri)
            setDurationTotal()
            judulLaguMusic.text = listSongs.get(position).title
            seekBar.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val mCurrentPosition = mediaPlayer.currentPosition / 1000
                        seekBar.progress = mCurrentPosition
                    }
                    handler.postDelayed(this, 1000)
                }
            })
            toggleButton.setImageResource(R.drawable.pause)
            mediaPlayer.start()
        } else {
            mediaPlayer.stop()
            mediaPlayer.release()
            position = (position + 1) % listSongs.size
            uri = Uri.parse(listSongs.get(position).path)
            mediaPlayer = MediaPlayer.create(this, uri)
            setDurationTotal()
            judulLaguMusic.text = listSongs.get(position).title
            seekBar.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val mCurrentPosition = mediaPlayer.currentPosition / 1000
                        seekBar.progress = mCurrentPosition
                    }
                    handler.postDelayed(this, 1000)
                }
            })
            toggleButton.setImageResource(R.drawable.play)
        }
    }

    private fun playThreadBtn() {
        playThread = object : Thread() {
            override fun run() {
                super.run()
                toggleButton.setOnClickListener(View.OnClickListener { playPauseBtnClicked() })
            }
        }
        (playThread as Thread).start()
    }

    private fun playPauseBtnClicked() {
        if (mediaPlayer.isPlaying) {
            toggleButton.setImageResource(R.drawable.play)
            mediaPlayer.pause()
            seekBar.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val mCurrentPosition = mediaPlayer.currentPosition / 1000
                        seekBar.progress = mCurrentPosition
                    }
                    handler.postDelayed(this, 1000)
                }
            })
        } else {
            toggleButton.setImageResource(R.drawable.pause)
            mediaPlayer.start()
            seekBar.max = mediaPlayer.duration / 1000
            this.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val mCurrentPosition = mediaPlayer.currentPosition / 1000
                        seekBar.progress = mCurrentPosition
                    }
                    handler.postDelayed(this, 1000)
                }
            })
        }
    }

    fun toggleButtonFunc(view: View){
//        when(getDrawable(toggleButton)){
//            R.drawable.play -> toggleButton.setImageResource(R.drawable.pause)
//            R.drawable.pause -> toggleButton.setImageResource(R.drawable.play)
//            else -> toggleButton.setImageResource(R.drawable.pause)
//        }

//        toggleButton.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pause))
    }
}