package com.ornettech.qcandbirthdaywishes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class AudioPlayerActivity extends AppCompatActivity  implements View.OnClickListener, View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private ImageButton bt_next,bt_prev;
    private SeekBar seekBarProgress;
    private FloatingActionButton bt_play;
    private TextView tv_song_current_duration,tv_song_total_duration,callername,calledby;
    private CircleImageView image;
    private String audiofileURL,caller,dialer,status;

    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ SharedPrefManager.getInstance(AudioPlayerActivity.this).getElectionName()+" Audio Recording</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        initView();
    }

    private void initView() {
        audiofileURL = getIntent().getStringExtra("url");
        caller = getIntent().getStringExtra("calledto");
        dialer = getIntent().getStringExtra("dialer");
        status = getIntent().getStringExtra("status");
        callername = findViewById(R.id.callername);
        callername.setText(dialer +" -> "+ caller);
        calledby = findViewById(R.id.calledby);
        calledby.setText(status);
        bt_play = findViewById(R.id.bt_play);
        tv_song_current_duration = findViewById(R.id.tv_song_current_duration);
        tv_song_current_duration.setText("0.00");
        tv_song_total_duration = findViewById(R.id.tv_song_total_duration);
        tv_song_total_duration.setText("0.00");
        bt_play.setOnClickListener(this);

        image =  findViewById(R.id.image);
        seekBarProgress = findViewById(R.id.SeekBarTestPlay);
        seekBarProgress.setMax(99); // It means 100% .0-99
        seekBarProgress.setOnTouchListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    private void primarySeekBarProgressUpdater() {
        long minutes = (mediaPlayer.getCurrentPosition() / 1000) / 60;
        long seconds = (mediaPlayer.getCurrentPosition() / 1000) % 60;
        tv_song_current_duration.setText(minutes+" : "+seconds);

        seekBarProgress.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100));
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_play) {
            /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
            try {
                mediaPlayer.setDataSource(audiofileURL);
                mediaPlayer.prepare();


            mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
            long minutes = (mediaFileLengthInMilliseconds / 1000) / 60;
            long seconds = (mediaFileLengthInMilliseconds / 1000) % 60;
            tv_song_total_duration.setText(minutes+" : "+seconds);
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                bt_play.setImageResource(R.drawable.ic_pause);
            } else {
                mediaPlayer.pause();
                bt_play.setImageResource(R.drawable.ic_play_arrow);
            }

            primarySeekBarProgressUpdater();
            rotateImageAlbum();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error occured while playing audio file !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.SeekBarTestPlay) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if (mediaPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/
        bt_play.setImageResource(R.drawable.ic_play_arrow);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        /** Method which updates the SeekBar secondary progress by current song loading from URL position*/
        seekBarProgress.setSecondaryProgress(percent);
    }

    private void rotateImageAlbum() {
        if (!mediaPlayer.isPlaying()) return;
        image.animate().setDuration(100).rotation(image.getRotation() + 2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rotateImageAlbum();
                super.onAnimationEnd(animation);
            }
        });
    }
}
