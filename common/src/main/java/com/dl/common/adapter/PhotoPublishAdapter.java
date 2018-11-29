package com.dl.common.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dl.common.R;
import com.dl.common.utils.DisplayUtil;
import com.dl.common.utils.LogUtil;

import java.io.File;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * created by dalang at 2018/11/26
 */
public class PhotoPublishAdapter extends BGARecyclerViewAdapter<String> {

    private int limit;
    private int size_initial;//判断图片集合的初始数量 用于再次编辑又发布的情况


    public PhotoPublishAdapter(RecyclerView recyclerView, int limit) {
        super(recyclerView, R.layout.item_photo);
        this.limit = limit;
    }

    public int getSize_initial() {
        return size_initial;
    }

    public void setSize_initial(int size_initial) {
        this.size_initial = size_initial;
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {

        ImageView mImageView = helper.getImageView(R.id.img_upload);
        int width = DisplayUtil.getScreenWidth((Activity) mContext);
        LinearLayout.LayoutParams prm = (LinearLayout.LayoutParams) mImageView.getLayoutParams();//获取按钮的布局
        prm.width = (int) (width - mContext.getResources().getDimension(R.dimen.dimen_60)) / 3;
        prm.height = (int) (width - mContext.getResources().getDimension(R.dimen.dimen_60)) / 3;

        mImageView.setLayoutParams(prm);
        if (position == mData.size()) {
            mImageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_add_photo));
            //大于limit则隐藏+号图片
            if (position > limit) {
                mImageView.setVisibility(View.GONE);
            } else {
                mImageView.setVisibility(View.VISIBLE);
            }
        } else {
            if (position < size_initial) {
                //网络图片加载
                Glide.with(mContext).load(mData.get(position).trim())
                        .asBitmap()
                        .placeholder(R.mipmap.glide_error_1_1)
                        .error(R.mipmap.glide_error_1_1)
                        .into(mImageView);
            } else {
                //从相册选择的
                Glide.with(mContext).load(new File(mData.get(position))).asBitmap().into(mImageView);
            }

        }
    }


    @Override
    public int getItemCount() {
        if (mData.size() == limit) {
            return limit;
        } else {
            return mData.size() + 1;
        }
    }


    @Override
    public String getItem(int position) {
        //必须重写 否则+号布局的position会报空指针
        if (position != mData.size()) {
            return super.getItem(position);
        }
        return "";
    }
}
