package com.dl.common.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.dl.common.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2017/6/30.
 */

public class BigPhotoAdapter extends PagerAdapter {
    private VpClickListener vpClickListener;
    private List<String> datas = new ArrayList<String>();
    private Context context;


    public void setDatas(List<String> datas) {
        if (datas != null)
            this.datas = datas;
    }

    public BigPhotoAdapter(Context context, VpClickListener vpClickListener) {
        this.context = context;
        this.vpClickListener = vpClickListener;

    }

    @Override
    public int getCount() {
        if (datas == null) return 0;
        return datas.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pager_image, container, false);
        if (view != null) {
            PhotoView imageView = view.findViewById(R.id.image);
            //loading
            final ProgressBar loading = new ProgressBar(context);
            FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingLayoutParams.gravity = Gravity.CENTER;
            loading.setLayoutParams(loadingLayoutParams);
            ((FrameLayout) view).addView(loading);

            final String imgurl = datas.get(position);

            if (imgurl.contains("http")) {

                Glide.with(context)
                        .load(imgurl)
                        .placeholder(R.mipmap.glide_place_4_3)
                        .error(R.mipmap.glide_error_4_3)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存多个尺寸
                        .thumbnail(0.1f)//先显示缩略图  缩略图为原图的1/10
                        .into(new GlideDrawableImageViewTarget(imageView) {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);

                                loading.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                loading.setVisibility(View.GONE);
                            }

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                                loading.setVisibility(View.GONE);
                            }
                        });

            } else {
                Glide.with(context)
                        .load(new File(imgurl))
                        .placeholder(R.mipmap.glide_place_4_3)
                        .error(R.mipmap.glide_error_4_3)
                        .into(new GlideDrawableImageViewTarget(imageView) {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                loading.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                loading.setVisibility(View.GONE);
                            }

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                                loading.setVisibility(View.GONE);
                            }
                        });
            }
            //图片单击事件的处理,PhotoView的点击监听事件要这样
            imageView.setOnViewTapListener((view1, x, y) -> vpClickListener.vpClickCallback());
            imageView.setOnLongClickListener(v -> {
                vpClickListener.longClickListener(position);
                return false;
            });
            container.addView(view, 0);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public interface VpClickListener {
        void vpClickCallback();

        void longClickListener(int position);
    }
}
