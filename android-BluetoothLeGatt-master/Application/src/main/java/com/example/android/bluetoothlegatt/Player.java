package com.example.android.bluetoothlegatt;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

public class Player extends Activity {

    public static final String EXTRAS_MEDIA_FILE_NAME = "MEDIA_FILE";
    public static final String EXTRAS_MEDIA_FILE_TYPE = "MEDIA_TYPE";

    public static final String AUDIO = "AUDIO";
    public static final String VIDEO = "VIDEO";

    MediaPlayer mp;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);

        final Intent intent = getIntent();
        String mediaFile = intent.getStringExtra(EXTRAS_MEDIA_FILE_NAME);
        String mediaType = intent.getStringExtra(EXTRAS_MEDIA_FILE_TYPE);

        if(mediaType.equals(VIDEO))
            playVideo(mediaFile);
        else if(mediaType.equals(AUDIO))
            playAudio();
        else
            finish();

    }

    public void playVideo(String mediaFile)
    {
        videoView =(VideoView)findViewById(R.id.videoView1);

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        Uri uri=Uri.parse(mediaFile);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_video, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mp!=null)
            mp.stop();

        if(videoView!=null)
            videoView.stopPlayback();
    }

    public void playAudio() {

        try {
//            mp=MediaPlayer.create(this, R.raw.god_audio);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
