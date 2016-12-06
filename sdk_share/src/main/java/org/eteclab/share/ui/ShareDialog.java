package org.eteclab.share.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import com.enetic.share.Util;

import org.eteclab.Constants;
import org.eteclab.OnkeyShare;
import org.eteclab.ShareSdks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by json on 2016/3/25.
 */
public class ShareDialog extends Dialog {
    Context ctx;
    List<ShareBean> shareBeans;
    private GridView mGridViews;
    OnkeyShare share;

    public void setShare(OnkeyShare share) {
        this.share = share;
    }

    public ShareDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(Util.getAppResources(context, "share_enetic_ui", "layout"));
        ctx = context;
        initResid();
        mGridViews = (GridView) findViewById(Util.getAppResources(context, "share_gird", "id"));
        mGridViews.setAdapter(new GridAdapter());
        findViewById(Util.getAppResources(context, "cancel_action", "id")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        mGridViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cancel();
                ShareBean bn = (ShareBean) parent.getItemAtPosition(position);
                if (share != null) {
                    share.sendShare(bn);
                }
            }
        });
    }

    HashMap<String, Integer> mString = new HashMap<>();

    private void initResid() {
//        mDrawables.clear();
        shareBeans = new ArrayList<>();
        Resources res = ctx.getResources();
        mString.put("share_weChat", res.getIdentifier("ssdk_wechat", "string", this.ctx.getPackageName()));
        mString.put("share_weChatmoments", res.getIdentifier("ssdk_wechatmoments", "string", this.ctx.getPackageName()));//微信朋友圈
//        mString.put("share_weChatfavorite", res.getIdentifier("ssdk_wechatfavorite", "string", this.ctx.getPackageName()));//微信收藏
        mString.put("share_QQ", res.getIdentifier("ssdk_qq", "string", this.ctx.getPackageName()));
        mString.put("share_QZone", res.getIdentifier("ssdk_qzone", "string", this.ctx.getPackageName()));
        mString.put("share_sinaweibo", res.getIdentifier("ssdk_sinaweibo", "string", this.ctx.getPackageName()));
        mString.put("share_more", res.getIdentifier("ssdk_more", "string", this.ctx.getPackageName()));


        shareBeans.add(new ShareBean("share_weChat", res.getIdentifier("ssdk_oks_classic_wechat", "mipmap", this.ctx.getPackageName()), 1));
        shareBeans.add(new ShareBean("share_weChatmoments", res.getIdentifier("ssdk_oks_classic_wechatmoments", "mipmap", this.ctx.getPackageName()), 2));
//        shareBeans.add(new ShareBean("share_QQ", res.getIdentifier("ssdk_oks_classic_qq", "mipmap", this.ctx.getPackageName()), 4));
//        shareBeans.add(new ShareBean("share_QZone", res.getIdentifier("ssdk_oks_classic_qzone", "mipmap", this.ctx.getPackageName()), 5));
//        shareBeans.add(new ShareBean("share_sinaweibo", res.getIdentifier("ssdk_oks_classic_sinaweibo", "mipmap", this.ctx.getPackageName()), 3));
        if (ShareSdks.isShowMore())
            shareBeans.add(new ShareBean("share_more", res.getIdentifier("ssdk_oks_classic_more", "mipmap", this.ctx.getPackageName()), 1000));
        Collections.sort(shareBeans, new ComparatorByDate());
    }

    public class ShareBean {
        String type;
        //        int shareName;
        int resId;
        int order;

        public ShareBean(String type, int resId, int order) {
            setType(type);
            setOrder(order);
            setResId(resId);
        }

        public int getShareName() {
            return mString.get(getType());
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }
    }


    private class ComparatorByDate implements Comparator<ShareBean> {

        @Override
        public int compare(ShareBean lhs, ShareBean rhs) {
            if (lhs.getOrder() > rhs.getOrder()) {
                return 1;
            } else {
                return -1;
            }

        }
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
//         window.setWindowAnimations(R.style.dialogAnim);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = LayoutParams.MATCH_PARENT; // 宽度
        lp.height = LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        getWindow().setGravity(Gravity.CENTER);
    }


    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (shareBeans != null)
                return shareBeans.size();
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (shareBeans != null)
                return shareBeans.get(position);
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getContext(), Util.getAppResources(ctx, "share_enetic_adapter_itme", "layout"), null);


            ImageView iv = (ImageView) convertView.findViewById(Util.getAppResources(getContext(), "share_img", "id"));
            ShareDialog.ShareBean bn = (ShareDialog.ShareBean) getItem(position);
            iv.setImageResource(bn.getResId());


            TextView tv = (TextView) convertView.findViewById(Util.getAppResources(getContext(), "share_tv", "id"));
            tv.setText(bn.getShareName());

            return convertView;
        }
    }
}
