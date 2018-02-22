package com.example.apple.playaudiotest;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;





public class MainActivity extends AppCompatActivity implements View.OnClickListener{




    private MediaPlayer mediaPlayer=new MediaPlayer();


    //这里是尝试插入的干预音乐播放代码
   // AlarmManager manager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
   // long triggerAtTime= SystemClock.elapsedRealtime()+1000*1000;
    //  manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime, PendingIntent);
   //到时候必须代替for循环变成alarm机制


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play=(Button) findViewById(R.id.play);
        Button pause=(Button) findViewById(R.id.pause);
        Button stop=(Button)  findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.
           WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE },1);          //提示的其实不一定是错的地方，只是机械显示而已
            }else {
            initMediaPlayer();//初始化MediaPlayer
        }
    }

    private void initMediaPlayer(){
        try{
            File file= new File(Environment.getExternalStorageDirectory(),
                    "music.mp3");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件路径
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

 //   @Override            //这个地方为什么和书上不一样
    public void onRequestRemissionsResult(int requestCode,String[] permissions,
        int[] grantResults){
                switch (requestCode){
                    case 1:
                        if(grantResults.length>0 && grantResults[0]==PackageManager.
                                PERMISSION_GRANTED){
                            initMediaPlayer();
                        }else{
                            Toast.makeText(this, "拒绝权限者不可使用程序",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                    default:
                }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                        mediaPlayer.start();//begin to play
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();//pause of play
                }
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();//stop of playing
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer !=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}
