package org.eteclab;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.enetic.share.Util;

import org.eteclab.share.call.ShareResultCall;
import org.eteclab.share.ui.ShareDialog;
import org.eteclab.share.ui.share.ShareParent;
import org.eteclab.share.ui.share.ShareQQ;
import org.eteclab.share.ui.share.ShareSina;
import org.eteclab.share.ui.share.ShareWX;

import java.io.File;

/**
 * Created by json on 2016/3/28.
 */
public class OnkeyShare {

    //标题
    private String title = "";
    //描述
    private String description = "";

    //跳转url
    private String targUrl = "";
    //图片 url
    private String imageUrl = "";
    //音乐 url
    private String musicUrl = "";
    //视频 url
    private String videoUrl = "";

    //略缩图 url
    private String thumUrl = "";

    private Context mCtx;

    ShareDialog dialog;

    public OnkeyShare(Context ctx) {
        mCtx = ctx;
        dialog = new ShareDialog(mCtx);
    }

    public void addShareCall(ShareResultCall call) {
    }


    public void show() {
        dialog.setShare(this);
        dialog.show();
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setTargUrl(String targUrl) {
        this.targUrl = targUrl;
    }

    //微博不支持网络图片
    public void setImageUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            this.imageUrl = imageUrl;
        }

    }

    public void setMusicUrl(String musicUrl) {
        if (!TextUtils.isEmpty(musicUrl)) {
            this.musicUrl = musicUrl;
        }
    }

    public void setVideoUrl(String videoUrl) {

        if (!TextUtils.isEmpty(videoUrl)) {
            this.videoUrl = videoUrl;
        }
    }

    public void setThumUrl(String thumUrl) {
        if (!TextUtils.isEmpty(thumUrl)) {
            this.thumUrl = thumUrl;
        }
    }

    public void sendShare(ShareDialog.ShareBean bn) {
        ShareWX shareWX = new ShareWX(mCtx);
        if (bn.getType().equals("share_weChat")) {
            shareWX.setScene(1);
            sendWxShare(shareWX);
        } else if (bn.getType().equals("share_weChatmoments")) {
            shareWX.setScene(2);
            sendWxShare(shareWX);
        } else if (bn.getType().equals("share_weChatfavorite")) {
            shareWX.setScene(3);
            sendWxShare(shareWX);
        } else if (bn.getType().equals("share_QQ")) {
            ShareQQ shareQQ = new ShareQQ(this.mCtx);
//            sendWxShare(shareQQ);
            if (type.equals(SHARE_TYPE.SHARE_MUSIC)) {
                shareQQ.shareMusic(musicUrl, targUrl, title, description, thumUrl);
            } else {
                shareQQ.shareQQDefault(title, description, targUrl, imageUrl, mCtx.getResources().getString(Util.getAppResources(mCtx, "app_name", "string")));
            }
        } else if (bn.getType().equals("share_QZone")) {
            ShareQQ shareQQ = new ShareQQ(this.mCtx);
            shareQQ.setQZone();
//            sendWxShare(shareQQ);

            if (type.equals(SHARE_TYPE.SHARE_MUSIC)) {
                shareQQ.shareMusic(musicUrl, targUrl, title, description, thumUrl);
            } else {
                shareQQ.shareQQDefault(title, description, targUrl, imageUrl, mCtx.getResources().getString(Util.getAppResources(mCtx, "app_name", "string")));
            }
        } else if (bn.getType().equals("share_sinaweibo")) {
            ShareSina shareSina = new ShareSina(mCtx);
            sendWxShare(shareSina);
        } else if (bn.getType().equals("share_more")) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            if (imageUrl == null || imageUrl.equals("")) {
                intent.setType("text/plain"); // 纯文本
            } else {
                File f = new File(imageUrl);
                if (f != null && f.exists() && f.isFile()) {
                    intent.setType("*/*");
                    Uri u = Uri.fromFile(f);
                    intent.putExtra(Intent.EXTRA_STREAM, u);
                }
            }
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
            intent.putExtra(Intent.EXTRA_TEXT, description);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(Intent.createChooser(intent, "分享"));
        }
    }


    private void sendWxShare(ShareParent shares) {
        switch (type) {
            case SHARE_TEXT:
                shares.shareText(description);
                break;
            case SHARE_IMAGE:
                shares.shareImg(imageUrl, description);
                break;
            case SHARE_MUSIC:
                shares.shareMusic(musicUrl, targUrl, title, description, thumUrl);
                break;
            case SHARE_VIDEO:
                shares.shareVideo(videoUrl, targUrl, title, description, thumUrl);
                break;
            case SHARE_WEB:
                shares.shareWeb(targUrl, title, description, thumUrl);
                break;
        }
    }

    SHARE_TYPE type = SHARE_TYPE.SHARE_TEXT;


    public void setShareType(SHARE_TYPE type) {
        this.type = type;
    }

    public enum SHARE_TYPE {
        SHARE_TEXT,
        SHARE_IMAGE,
        SHARE_MUSIC,
        SHARE_VIDEO,
        SHARE_WEB
    }

    public void setPlatform() {

    }
}
