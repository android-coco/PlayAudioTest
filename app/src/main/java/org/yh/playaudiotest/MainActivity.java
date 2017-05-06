package org.yh.playaudiotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1);
        }
        else
        {
            initMediaPlayer(); // 初始化MediaPlayer
        }
    }

    private void initView()
    {
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!mediaPlayer.isPlaying())
                {
                    mediaPlayer.start();//开始播放
                }
            }
        });
        findViewById(R.id.puse).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();//暂停播放
                }
            }
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.reset();//停止播放
                    initMediaPlayer();
                }
            }
        });
    }

    private void initMediaPlayer()
    {
        //.flac .mp3
        File file = new File(Environment.getExternalStorageDirectory(), "muic.flac");
        try
        {
            mediaPlayer.setDataSource(file.getPath());// 指定视频文件的路径
            mediaPlayer.prepare();// 让MediaPlayer进入准备状态
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    initMediaPlayer();
                }
                else
                {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //释放资源
        if(null != mediaPlayer)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
