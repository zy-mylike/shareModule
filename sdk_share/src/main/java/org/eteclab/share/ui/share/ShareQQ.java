package org.eteclab.share.ui.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.eteclab.share.ui.QQShareActivity;

import com.enetic.share.Util;
import com.tencent.connect.share.QQShare;

/**
 * Created by llq on 2016/3/22.
 */
public class ShareQQ extends ShareParent {

    private int SHARE_TYPE = 0x00;

    /**
     * 调用本方法则 分享至手机QQ好友
     */
    public void setQQLine() {
        this.SHARE_TYPE |= QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE;
    }

    public void setQQDefault() {
        this.SHARE_TYPE = 0x00;
    }

    /**
     * 调用本方法则 分享至手机QQ空间
     */
    public void setQZone() {
        this.SHARE_TYPE |= QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN;
    }

    private Context ctx;


    public ShareQQ(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * 分享图文消息
     *
     * @param shareType 分享类型 QQShare.SHARE_TO_QQ_TYPE_DEFAULT：默认 ,QQShare.SHARE_TO_QQ_TYPE_IMAGE：图片,QQShare.SHARE_TO_QQ_TYPE_APP：应用,QQShare.SHARE_TO_QQ_TYPE_AUDIO：音频
     * @param title     分享标题
     * @param des       分享摘要
     * @param tarUrl    这条分享消息被好友点击后的跳转URL。
     * @param imgUrl    分享图片的URL或者本地路径
     */
    public void shareQQDefault(String title, String des, String tarUrl, String imgUrl, String appName) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, des);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, tarUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, SHARE_TYPE);
//        mTencent.shareToQQ(this, params, uiListener);
        sendToShare(params);
    }

    /***
     * 分享音乐
     *
     * @param title    分享标题
     * @param des      分享摘要
     * @param tarUrl   这条分享消息被好友点击后的跳转URL。
     * @param musicUrl 音乐链接
     * @param imageUrl 本地或者网络图片
     */
    public void shareQQMusic(String title, String des, String tarUrl, String musicUrl, String imageUrl, String appName) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, des);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, tarUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, musicUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, SHARE_TYPE);
//        mTencent.shareToQQ(this, params, uiListener);
        sendToShare(params);
    }
//
//    /**
//     * 分享应用
//     */
//
//    public void shareQQApp(String title, String des, String imageUrl, String appName) {
//        final Bundle params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
//        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
//        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, des);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
////        mTencent.shareToQQ(this, params, uiListener);\
//        sendToShare(params);
//    }


    private void sendToShare(final Bundle params) {
        if (Util.isAvilible(ctx, "com.tencent.mobileqq")) {
            this.ctx.startActivity(new Intent(this.ctx, QQShareActivity.class).putExtras(params));
        } else {
            Toast.makeText(ctx, "客户端未安装", Toast.LENGTH_SHORT).show();
        }

    }

//    @Override
//    public void shareText(String content) {
//
//    }

    @Override
    public void shareText(String content) {
        final Bundle params = new Bundle();
//        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
//        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
//        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, tarUrl);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, SHARE_TYPE);
////        mTencent.shareToQQ(this, params, uiListener);
//        sendToShare(params);
    }

    /**
     * 分享纯图片（必须为本地图片）
     */
    @Override
    public void shareImg(@Nullable String ImgPath, String description) {
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, ImgPath);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, SHARE_TYPE);
        if (ImgPath.startsWith("http://")) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        }
//        mTencent.shareToQQ(this, params, uiListener);
        sendToShare(params);
    }

    @Override
    public void shareWeb(String webpageUrl, String title, String description, String thumUrl) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, webpageUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, thumUrl);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, SHARE_TYPE);
//        mTencent.shareToQQ(this, params, uiListener);
        sendToShare(params);
    }

    @Override
    public void shareMusic(String musicUrl, String targUrl, String title, String description, String thumUrl) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, thumUrl);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, musicUrl);
//        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, SHARE_TYPE);
//        mTencent.shareToQQ(this, params, uiListener);
        sendToShare(params);
    }

    @Override
    public void shareVideo(String musicUrl, String targUrl, String title, String description, String thumUrl) {
        shareMusic(musicUrl, targUrl, title, description, thumUrl);
    }
}