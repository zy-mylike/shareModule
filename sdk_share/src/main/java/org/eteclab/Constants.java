package org.eteclab;

import android.content.Context;

import com.enetic.share.Util;

/**
 * Created by llq on 2016/3/22.
 */
public class Constants {

    /**
     * 微信分享，收藏 appkey
     */
    public static String WX_APPKEY = "wxd17bfb0508e2250d";

    /**
     * QQ 分享，收藏 appkey
     */
    public static String QQ_APPKEY = "222222";


    /**
     * 新浪微博 appkey
     */
    public static String SINA_APPKEY = "317879547";


    public static void init(Context ctx) {
        QQ_APPKEY = Util.getAppkey(ctx, "QQ_Share_APPKEY", "1222222");
        WX_APPKEY = Util.getAppkey(ctx, "WChat_Share_APPKEY", "21222222");
        SINA_APPKEY = Util.getAppkey(ctx, "WEIBO_Share_APPKEY", "317879547");
    }

}
