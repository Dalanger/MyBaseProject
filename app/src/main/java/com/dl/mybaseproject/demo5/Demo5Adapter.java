package com.dl.mybaseproject.demo5;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.dl.common.animation.AlphaInAnimation;
import com.dl.common.animation.BaseAnimation;
import com.dl.mybaseproject.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * created by dalang at 2018/10/24
 */
public class Demo5Adapter extends BGARecyclerViewAdapter<String> {

    private boolean mFirstOnlyEnable=true;
    private boolean mOpenAnimationEnable=true;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();//透明度渐变动画
    private int mLastPosition=-1;
    private Interpolator mInterpolator = new LinearInterpolator();

    public Demo5Adapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_demo5_list);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.setText(R.id.tv_title,model);
        helper.setText(R.id.tv_content,"第"+(position+1)+"条数据");
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
        anim.setDuration(800).start();
        anim.setInterpolator(mInterpolator);
    }
}
