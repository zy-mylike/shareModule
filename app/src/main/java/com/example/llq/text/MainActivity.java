package com.example.llq.text;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.eteclab.OnkeyShare;
import org.eteclab.share.call.ShareResultCall;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.share_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnkeyShare share = new OnkeyShare(MainActivity.this);
                share.addShareCall(new ShareResultCall(){
                    @Override
                    public void onShareSucess() {
                        super.onShareSucess();
                    }

                    @Override
                    public void onShareFailure(String errStr, int errCode) {
                        super.onShareFailure(errStr, errCode);
                    }

                    @Override
                    public void onShareError(String errStr, int errCode) {
                        super.onShareError(errStr, errCode);
                    }

                    @Override
                    public void onShareCancel() {
                        super.onShareCancel();
                    }
                });
                share.setTitle("分享文本标题");
                share.setDescription("分享文本内容*******");
                share.setTargUrl("http://www.baidu.com");
                //微博只支持本地图片，其他的本地
                share.setImageUrl("http://static.test.mobioa.cn/findme/head/20160309/25/25975702.png");
//                share.setImageUrl("/storage/sdcard1/1.png");
                share.setShareType(OnkeyShare.SHARE_TYPE.SHARE_TEXT);
                share.show();
            }
        });
        findViewById(R.id.share_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnkeyShare share = new OnkeyShare(MainActivity.this);
                share.setTitle("图片标题");
                share.setDescription("分享图片描述*******");
                share.setTargUrl("http://www.baidu.com");
                //微博只支持本地图片，其他的本地
//                share.setImageUrl("http://static.test.mobioa.cn/findme/head/20160309/25/25975702.png");
                share.setImageUrl("/storage/sdcard1/1.png");
                share.setShareType(OnkeyShare.SHARE_TYPE.SHARE_IMAGE);
                share.show();
            }
        });
        findViewById(R.id.share_image_url).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信暂不支持网络图片
                OnkeyShare share = new OnkeyShare(MainActivity.this);
                share.setTitle("图片标题");
                share.setDescription("分享图片描述*******");
                share.setTargUrl("http://www.baidu.com");
                //微博只支持本地图片，其他的本地
                share.setImageUrl("http://static.test.mobioa.cn/findme/head/20160309/25/25975702.png");
//                share.setImageUrl("/storage/sdcard1/1.png");
                share.setShareType(OnkeyShare.SHARE_TYPE.SHARE_IMAGE);
                share.show();
            }
        });
        findViewById(R.id.share_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnkeyShare share = new OnkeyShare(MainActivity.this);
                share.setTitle("音乐标题");
                share.setDescription("分享音乐描述*******");
                //  QQ和QQ空间使用
                share.setTargUrl("http://www.baidu.com");

                share.setMusicUrl("http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3");
                //微博只支持本地图片，其他的本地
//                share.setImageUrl("http://static.test.mobioa.cn/findme/head/20160309/25/25975702.png");
                share.setImageUrl("/storage/sdcard1/1.png");
                share.setShareType(OnkeyShare.SHARE_TYPE.SHARE_MUSIC);
                share.show();
            }
        });
        findViewById(R.id.share_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnkeyShare share = new OnkeyShare(MainActivity.this);
                share.setTitle("视频标题");
                share.setDescription("分享视频描述*******");
                //  QQ和QQ空间使用
                share.setTargUrl("http://www.iqiyi.com/v_19rrolm54g.html#vfrm=19-9-0-1");
                share.setVideoUrl("http://static.test.mobioa.cn/wodm/writer.3gp");
                //略缩图
                share.setImageUrl("/storage/sdcard1/1.png");
                share.setShareType(OnkeyShare.SHARE_TYPE.SHARE_VIDEO);
                share.show();
            }
        });
        findViewById(R.id.share_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnkeyShare share = new OnkeyShare(MainActivity.this);
                share.setTitle("网页标题");
                share.setDescription("网页描述————————————————————————");
                share.setTargUrl("http://www.baidu.com");
                //微博只支持本地图片，其他的本地
//                share.setImageUrl("http://static.test.mobioa.cn/findme/head/20160309/25/25975702.png");
                share.setImageUrl("/storage/sdcard1/1.png");
                share.setShareType(OnkeyShare.SHARE_TYPE.SHARE_WEB);
                share.show();
            }
        });
    }
}