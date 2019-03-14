package com.example.navyasree.exoplayerdemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
        SimpleExoPlayerView simpleExoPlayerView;
        SimpleExoPlayer exoplayer;
        long currentposition;
        boolean playwhenready=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleExoPlayerView=findViewById(R.id.simpleexoplayer);
        if (savedInstanceState==null){
            startPlayer();
        }
       /* else {
            currentposition=savedInstanceState.getLong("currentpos");
            playwhenready=savedInstanceState.getBoolean("playwhenready");
            Log.d("currentpos",""+currentposition);
            exoplayer.seekTo(currentposition);
            exoplayer.setPlayWhenReady(playwhenready);
        }*/
    
        }
    public void startPlayer(){
        exoplayer= ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        Uri videoUrl=Uri.parse(" http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        String useragent= Util.getUserAgent(this,"ExoPlayerDemo");
        MediaSource mediaSource = new ExtractorMediaSource(videoUrl,new DefaultDataSourceFactory(this,useragent),new DefaultExtractorsFactory(),null,null);
        exoplayer.prepare(mediaSource);
        exoplayer.setPlayWhenReady(playwhenready);
        exoplayer.seekTo(currentposition);
        simpleExoPlayerView.setPlayer(exoplayer);
    }
    public  void stopPlayer()
    {
        if (exoplayer!=null){
            currentposition=exoplayer.getCurrentPosition();
            exoplayer.release();
            exoplayer.stop();
            exoplayer=null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT>23)
        {
            Toast.makeText(this, "onstart", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT<=23) {
            stopPlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT>23) {
            stopPlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT<=23 && currentposition!=0) {
            startPlayer();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoplayer!=null) {
            outState.putLong("currentpos", exoplayer.getCurrentPosition());
            outState.putBoolean("playwhenready", exoplayer.getPlayWhenReady());
            Log.d("currentpos", "" + exoplayer.getCurrentPosition());
        }
    }*/
}
