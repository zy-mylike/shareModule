package org.eteclab.share.ui.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.enetic.share.Util;
import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.utils.Utility;

import org.eteclab.share.ui.ShareSinaWEIBO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by llq on 2016/3/22.
 */
public class ShareSina extends ShareParent {


    private Context ctx;


    public ShareSina(Context ctx) {
        this.ctx = ctx;
    }

    private void sendToShare(final BaseMediaObject params, String type) {
        this.ctx.startActivity(new Intent(this.ctx, ShareSinaWEIBO.class).putExtra("share", params).putExtra("type", type));
    }


    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    @Override
    public void shareText(String content) {

        TextObject textObject = new TextObject();
        textObject.text = content;
        sendToShare(textObject, "text");
    }


    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    @Override
    public void shareImg(String imgUrl, String description) {
        ImageObject imageObject = new ImageObject();
        if (imgUrl.startsWith("http://")) {
            imageObject.imagePath = imgUrl;
        } else {
            if (!new File(imgUrl).exists()) {
                Toast.makeText(ctx, "文件不存在", Toast.LENGTH_SHORT).show();
                return;
            } else {
                imageObject.setImageObject(Util.revitionImageSize(imgUrl));
                imageObject.setThumbImage(compressImage(BitmapFactory.decodeFile(imgUrl)));
            }
        }
        imageObject.description = description;


//        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        sendToShare(imageObject, "img");
    }


    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    @Override
    public void shareWeb(String actionUrl, String title, String description, String thumild) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = description;

        Bitmap bitmap = BitmapFactory.decodeFile(thumild);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        if (bitmap != null)
            mediaObject.setThumbImage(compressImage(bitmap));
        mediaObject.actionUrl = actionUrl;
        mediaObject.defaultText = description;
        sendToShare(mediaObject, "web");
    }

    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    @Override
    public void shareMusic(String actionUrl, String targUrl, String title, String description, String thumild) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = title;
        musicObject.description = description;

        Bitmap bitmap = BitmapFactory.decodeFile(thumild);


        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        musicObject.setThumbImage(compressImage(bitmap));
        musicObject.actionUrl = actionUrl;

//        musicObject.dataUrl = "www.weibo.com";
//        musicObject.dataHdUrl = "www.weibo.com";
//        musicObject.duration = 10;
//        musicObject.defaultText = "Music 默认文案";
        sendToShare(musicObject, "music");
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    @Override
    public void shareVideo(String actionUrl, String targUrl, String title, String description, String thumild) {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = title;
        videoObject.description = description;
        Bitmap bitmap = BitmapFactory.decodeFile(thumild);
        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。


        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
            System.out.println("kkkkkkk    size  " + os.toByteArray().length);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        videoObject.setThumbImage(compressImage(bitmap));
        videoObject.actionUrl = actionUrl;
//        videoObject.dataUrl = "www.weibo.com";
//        videoObject.dataHdUrl = "www.weibo.com";
//        videoObject.duration = 10;
//        videoObject.defaultText = "Vedio 默认文案";
        sendToShare(videoObject, "video");
    }

    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private void shareVoice(String title, String description, String actionUrl, String thumild) {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = title;
        voiceObject.description = description;
        Bitmap bitmap = BitmapFactory.decodeFile(thumild);
        // 设置 Bitmap 类型的图片到视频对象里      设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        voiceObject.setThumbImage(compressImage(bitmap));
        voiceObject.actionUrl = actionUrl;
//        voiceObject.dataUrl = "www.weibo.com";
//        voiceObject.dataHdUrl = "www.weibo.com";
//        voiceObject.duration = 10;
//        voiceObject.defaultText = "Voice 默认文案";
        sendToShare(voiceObject, "voice");
    }


    private Bitmap compressImage(Bitmap image) {

        if (image == null) {
            return image;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100,
                baos);
        int options = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 32) {
            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;//每次都减少10
        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
}