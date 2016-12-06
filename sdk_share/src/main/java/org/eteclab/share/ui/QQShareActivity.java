package org.eteclab.share.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.eteclab.Constants;
import org.eteclab.share.call.ShareResultCall;

public class QQShareActivity extends Activity {

    public Tencent mTencent;
    BaseUiListener uiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiListener = new BaseUiListener();
        mTencent = Tencent.createInstance(Constants.QQ_APPKEY, this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
        }
        mTencent.shareToQQ(this, bundle, uiListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_QZONE_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, uiListener);
        }
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, uiListener);
        }
    }


    /**
     * 分享结果回调类
     */
    class BaseUiListener implements IUiListener {

        public BaseUiListener() {
            if (ShareResultCall.call == null) {
                new ShareResultCall();
            }
        }

        @Override
        public void onComplete(Object o) {
            ShareResultCall.call.onShareSucess();
//            Toast.makeText(getApplicationContext(), "分享成功", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onError(UiError uiError) {
            ShareResultCall.call.onShareError(uiError.errorDetail + uiError.errorMessage, uiError.errorCode);
            ShareResultCall.call.onShareFailure(uiError.errorDetail + uiError.errorMessage, uiError.errorCode);
//            Toast.makeText(getApplicationContext(), "分享失败 errorCode:" + uiError.errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onCancel() {
            ShareResultCall.call.onShareCancel();
//            Toast.makeText(getApplicationContext(), "用户取消", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
