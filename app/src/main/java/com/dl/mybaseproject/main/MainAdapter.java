package com.dl.mybaseproject.main;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.dl.common.animation.BaseAnimation;
import com.dl.common.animation.ScaleInAnimation;
import com.dl.mybaseproject.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

public class MainAdapter extends BGARecyclerViewAdapter<MainDataBean> {

    private boolean mFirstOnlyEnable=false;
    private boolean mOpenAnimationEnable=true;
    private BaseAnimation mSelectAnimation = new ScaleInAnimation();//由小到大动画
    private int mLastPosition=-1;
    private Interpolator mInterpolator = new LinearInterpolator();

    public MainAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_main);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, MainDataBean model) {
         helper.getImageView(R.id.iv_logo).setImageResource(R.mipmap.home_camera);
         helper.setText(R.id.tv_text,model.getContent());
         helper.setText(R.id.tv_name,"Demo"+(position+1));
    }


    @Override
    public void onViewAttachedToWindow(BGARecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimation(holder);
    }

    /**
     * add animation when you want to show time
     *
     * @param holder
     */
    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;

                animation = mSelectAnimation;

                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }


    /**
     * set anim to start when loading
     *
     * @param anim
     * @param index
     */
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(300).start();
        anim.setInterpolator(mInterpolator);
    }
}
