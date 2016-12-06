package org.eteclab.share.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.enetic.share.Util;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;

import org.eteclab.Constants;

import java.util.List;

/**
 * Created by llq on 2016/3/22.
 */
public class ShareSinaWEIBO extends Activity implements IWeiboHandler.Response {
    IWeiboShareAPI mWeiboShareAPI;
    private String type;

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mWeiboShareAPI.handleWeiboResponse(intent, this); //当前应用唤起微博分享后，返回当前应用

    }

    SsoHandler mSsoHandler;
    AuthInfo mAuthInfo;


    public static final String REDIRECT_URL = "http://www.sina.com";// 应用的回调页
    public static final String SCOPE =                               // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");

        init();
//        mAuthInfo = new AuthInfo(this, Constants.SINA_APPKEY,
//                REDIRECT_URL, SCOPE);
//        mSsoHandler = new SsoHandler(this, mAuthInfo);
//        mSsoHandler. authorizeClientSso(new AuthListener());
    }

//    public class AuthListener implements WeiboAuthListener {
//        @Override
//        public void onComplete(Bundle bundle) {
//            init();
//        }
//
//        @Override
//        public void onWeiboException(WeiboException e) {
//            Toast.makeText(getApplicationContext(), "" + e.getMessage(), 0).show();
//
//        }
//
//        @Override
//        public void onCancel() {
//            Toast.makeText(getApplicationContext(), "", 0).show();
//        }
//    }


    public void init() {
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.SINA_APPKEY);
        mWeiboShareAPI.registerApp();

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        // 1. 初始化微博的分享消息


        if (type.equals("text")) {
            weiboMessage.textObject = getIntent().getParcelableExtra("share");
        } else if (type.equals("img")) {
            weiboMessage.imageObject = getIntent().getParcelableExtra("share");
        } else if (type.equals("music")) {
            weiboMessage.mediaObject = getIntent().getParcelableExtra("share");
        } else if (type.equals("video")) {
            weiboMessage.mediaObject = getIntent().getParcelableExtra("share");
        } else if (type.equals("web")) {
            weiboMessage.mediaObject = getIntent().getParcelableExtra("share");
        } else if (type.equals("voice")) {
            weiboMessage.mediaObject = getIntent().getParcelableExtra("share");
        }

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        if (Util.isAvilible(this, "com.sina.weibo")) {
            mWeiboShareAPI.sendRequest(this, request);
        } else {
            Toast.makeText(this, "未安装微博客户端", Toast.LENGTH_SHORT).show();
            finish();
        }

//finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null)
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);

    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        if (baseResponse != null)
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:        //
                    Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL://
                    Toast.makeText(this, "取消分享", Toast.LENGTH_SHORT).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:    //
                    Toast.makeText(this, "分享失败" + baseResponse.errMsg, Toast.LENGTH_SHORT).show();
                    break;
            }
        finish();
    }




}