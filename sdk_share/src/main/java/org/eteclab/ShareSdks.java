package org.eteclab;

import android.content.Context;

/**
 * Created by json on 2016/3/25.
 */
public class ShareSdks {


    private static Boolean mShowShareMore = false;

    public static void initShare(Context ctx) {
        Constants.init(ctx);
    }


    public static Boolean isShowMore() {
        return mShowShareMore;
    }

}
