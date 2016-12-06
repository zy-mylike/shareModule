package org.eteclab.share.call;

/**
 * Created by json on 2016/6/22.
 */
public class ShareResultCall {

    public static ShareResultCall call;

    public ShareResultCall() {
        call = this;
    }


    public void onShareSucess() {
        call = null;
    }

    public void onShareFailure(String errStr, int errCode) {
        call = null;
    }

    public void onShareCancel() {
        call = null;
    }

    public void onShareError(String errStr, int errCode) {
    }
}
