package org.eteclab.share.ui.share;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

/**
 * Created by json on 2016/3/28.
 */
public abstract class ShareParent {


    public abstract void shareText(String content);


    /**
     * 分享图片
     *
     * @param res
     */
    public void shareImg(@DrawableRes int res){

    }


    /**
     * 分享图片
     *
     * @param ImgPath
     */
    public abstract void shareImg(@Nullable String ImgPath,String description) ;


    /**
     * 分享网页
     *
     * @param webpageUrl  网页url
     * @param title       网页标题
     * @param description 网页描述
     */
    public abstract void shareWeb(String webpageUrl, String title, String description, String thumUrl);

    /**
     * 分享音乐
     *
     * @param musicUrl    网页url
     * @param title       网页标题
     * @param description 网页描述
     */
    public abstract void shareMusic(String musicUrl,String targUrl, String title, String description, String thumUrl);

    /**
     * 分享视频
     *
     * @param musicUrl    网页url
     * @param title       网页标题
     * @param description 网页描述
     */
    public abstract void shareVideo(String musicUrl,String targUrl, String title, String description, String thumUrl);
}
