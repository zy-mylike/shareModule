package org.eteclab.share.ui.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;


import com.enetic.share.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.eteclab.Constants;

import java.io.File;

//import com.tencent.mm.sdk.modelmsg.*;

/**
 * Created by llq on 2016/3/22.
 */
public class ShareWX extends ShareParent {

    private Context ctx;
    private static final int THUMB_SIZE = 150;
    private int imageRes = 0;
    private String imagePath;
    private int scene = SendMessageToWX.Req.WXSceneTimeline;

    /**
     * @param scene 1微信好友，2微信朋友圈，3收藏
     */
    public void setScene(int scene) {
        switch (scene) {
            case 1:
                this.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case 2:
                this.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case 3:
//                this.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;

        }
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
        imagePath = "";
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        imageRes = 0;
    }


    IWXAPI api;

    public ShareWX(Context ctx) {
        this.ctx = ctx;
        init();
    }

    public void init() {
        api = WXAPIFactory.createWXAPI(this.ctx, Constants.WX_APPKEY);
        api.registerApp(Constants.WX_APPKEY);
    }

    /**
     * 分享文本
     *
     * @param content
     */
@Override
    public void shareText(String content) {
        if (!idShare()) {
            return;
        }

        if (content == null || content.length() == 0) {
            return;
        }

        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = content;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;

        // 调用api接口发送数据到微信
        api.sendReq(req);
    }


    /**
     * 分享图片
     *
     * @param res
     */
    @Override
    public void shareImg(@DrawableRes int res) {

        if (!idShare()) {
            return;
        }
        setImageRes(res);
        Bitmap bmp = getThumBitMap();
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = getThumBitMap();
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }


    /**
     * 分享图片
     *
     * @param ImgPath
     */
    @Override
    public void shareImg(@Nullable String ImgPath,String description) {
        if (!idShare()) {
            return;
        }

        File file = new File(ImgPath);
        if (!file.exists()) {
            String tip = "文件不存在";
            return;
        }
        setImagePath(ImgPath);
        WXImageObject imgObj = new WXImageObject();
        if (ImgPath.startsWith("http://")) {
            imgObj.imagePath = ImgPath;
        } else if (ImgPath.startsWith("file:///") || new File(ImgPath).exists()) {
            imgObj.setImagePath(ImgPath);
        } else {
        }


        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.description = description;
        Bitmap bmp = getThumBitMap();
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }


    /**
     * 分享网页
     *
     * @param webpageUrl  网页url
     * @param title       网页标题
     * @param description 网页描述
     */
    @Override
    public void shareWeb(String webpageUrl, String title, String description, String thumUrl) {
        if (!idShare()) {
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webpageUrl;
        shareToWchat(title, description, webpage, thumUrl);
    }

    /**
     * 分享音乐
     *
     * @param musicUrl    网页url
     * @param title       网页标题
     * @param description 网页描述
     */
    @Override
    public void shareMusic(String musicUrl,String targUrl, String title, String description, String thumUrl) {
        WXMusicObject webpage = new WXMusicObject();
//        webpage.musicLowBandUrl = "http://www.baidu.com";
        webpage.musicUrl = musicUrl;
        shareToWchat(title, description, webpage, thumUrl);
    }

    /**
     * 分享视频
     *
     * @param musicUrl    网页url
     * @param title       网页标题
     * @param description 网页描述
     */
    @Override
    public void shareVideo(String musicUrl, String targUrl,String title, String description, String thumUrl) {
        WXVideoObject webpage = new WXVideoObject();
        webpage.videoUrl = musicUrl;
        shareToWchat(title, description, webpage, thumUrl);
    }

    private void shareToWchat(String title, String description, WXMediaMessage.IMediaObject obj, String thumUrl) {

        if (!idShare()) {
            return;
        }
        WXMediaMessage msg = new WXMediaMessage(obj);
        msg.title = title;
        msg.description = description;
        //
        if (!new File(thumUrl).exists()) {
            //本地图片不存在
        } else {
            Bitmap thumb = BitmapFactory.decodeFile(thumUrl);
            msg.thumbData = Util.bmpToByteArray(thumb, true);
        }


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 检查是否安装微信客户端
     *
     * @return
     */
    private Boolean idShare() {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(this.ctx, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public Bitmap getThumBitMap() {
        if (TextUtils.isEmpty(this.imagePath) && this.imageRes == 0) {
            return null;
        }
        if (!TextUtils.isEmpty(this.imagePath)) {
            if (!new File(this.imagePath).exists()) {
                Toast.makeText(this.ctx, "文件不存在", Toast.LENGTH_SHORT).show();
            }
            return BitmapFactory.decodeFile(this.imagePath);
        }
        return BitmapFactory.decodeResource(this.ctx.getResources(), this.imageRes);
    }
}