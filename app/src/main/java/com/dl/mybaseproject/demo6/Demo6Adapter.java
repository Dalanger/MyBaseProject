package com.dl.mybaseproject.demo6;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.abbott.mutiimgloader.call.MergeCallBack;
import com.abbott.mutiimgloader.qq.QqMerge;
import com.abbott.mutiimgloader.util.JImageLoader;
import com.dl.common.widget.SwipeMenuLayout;
import com.dl.mybaseproject.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * created by dalang at 2018/11/21
 */
public class Demo6Adapter extends BGARecyclerViewAdapter<Demo6Bean> {
    JImageLoader imageLoader;
    MergeCallBack mergeCallBack;

    public Demo6Adapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.demo6_item_list);
        imageLoader = new JImageLoader(mContext);

//        mergeCallBack = new WeixinMerge();//开启微信讨论组模式  请把xml里riv_oval=true去掉
        mergeCallBack = new QqMerge();
        imageLoader.configDefaultPic(R.mipmap.dl_logo);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Demo6Bean model) {


        helper.setText(R.id.tv_name,"消息"+model.getCount());
        helper.setText(R.id.tv_text,"今晚约不约?------"+(position%2==0?"约":"不约"));

        helper.setItemChildClickListener(R.id.layout);
        helper.setItemChildClickListener(R.id.tv_delete);
        helper.setItemChildClickListener(R.id.tv_up);
        RelativeLayout rootLayout = helper.getView(R.id.layout);

        //角标
        Badge badge = new QBadgeView(mContext).bindTarget(rootLayout);
        badge.setBadgeGravity(Gravity.BOTTOM | Gravity.END);
        badge.setGravityOffset(15, true);
        badge.setBadgeTextSize(14, true);
        badge.setBadgePadding(6, true);
        badge.setOnDragStateChangedListener((dragState, badge1, targetView) -> {
            if (dragState == Badge.OnDragStateChangedListener.STATE_SUCCEED) {
                ((SwipeMenuLayout) helper.getRecyclerViewHolder().itemView).smoothClose();
                ((SwipeMenuLayout) helper.getRecyclerViewHolder().itemView).setIos(true).setSwipeEnable(true);
            } else if (dragState == Badge.OnDragStateChangedListener.STATE_CANCELED) {
                ((SwipeMenuLayout) helper.getRecyclerViewHolder().itemView).smoothClose();
                ((SwipeMenuLayout) helper.getRecyclerViewHolder().itemView).setIos(true).setSwipeEnable(true);

            } else {
                ((SwipeMenuLayout) helper.getRecyclerViewHolder().itemView).smoothClose();
                ((SwipeMenuLayout) helper.getRecyclerViewHolder().itemView).setIos(false).setSwipeEnable(false);
            }
        });
        badge.setBadgeNumber(model.getCount());

        //讨论组头像加载
        if (model.getImgUrls() != null) {
            imageLoader.displayImages(model.getImgUrls(), helper.getImageView(R.id.iv_logo), mergeCallBack);
        } else {
            helper.getImageView(R.id.iv_logo).setImageResource(R.mipmap.dl_logo);
        }

    }


}
