package com.dl.common.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.common.R;
import com.dl.common.adapter.BigPhotoAdapter;
import com.dl.common.base.BaseActivity;
import com.dl.common.manager.ClickManager;
import com.dl.common.utils.DialogUtil;
import com.dl.common.utils.ImgUtil;
import com.dl.common.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.lzh.easythread.Callback;
import com.lzh.easythread.EasyThread;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.ArrayList;
import java.util.List;

public class BigPhotoActivity extends BaseActivity implements BigPhotoAdapter.VpClickListener {


    private ViewPager viewPager;
    private TextView index;
    private ImageView ivDownload;

    private ArrayList<String> imgUrls;
    private int startPosition;//从哪张图片开始
    private BigPhotoAdapter adapter;

    private EasyThread executor;
    private ThreadCallback threadCallback;
    private int currentPosition;


    @Override
    public int getContentViewId() {
        return R.layout.activity_big_photo;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        StatusBarUtil.setColorForSwipeBack(this, getResources().getColor(R.color.black), 50);
        initData();
        initThread();
        initAdapter();

    }

    private void initThread() {
        executor = EasyThread.Builder
                .single()
                .name("save_photo")
                .build();
        threadCallback = new ThreadCallback();
        ClickManager.getInstance().singleClick(ivDownload, () ->
                save()
        );
    }


    private void initData() {
        viewPager = findViewById(R.id.pager);
        index = findViewById(R.id.tv_index);
        ivDownload = findViewById(R.id.iv_download);
        startPosition = getIntent().getIntExtra("position", 0);
        imgUrls = getIntent().getStringArrayListExtra("imgUrls");
        if (imgUrls.get(startPosition).contains("http")) {
            ivDownload.setVisibility(View.VISIBLE);
        } else {
            ivDownload.setVisibility(View.GONE);
        }
        index.setText((startPosition + 1) + "/" + imgUrls.size());
    }


    private void initAdapter() {
        adapter = new BigPhotoAdapter(this, this);
        adapter.setDatas(imgUrls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startPosition);
        currentPosition = startPosition;
        viewPager.addOnPageChangeListener(pageChangeListener);

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
            if (imgUrls.get(position).contains("http")) {
                ivDownload.setVisibility(View.VISIBLE);
            } else {
                ivDownload.setVisibility(View.GONE);
            }


            index.setText((position + 1) + "/" + imgUrls.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class ThreadCallback implements Callback {

        @Override
        public void onError(Thread thread, Throwable t) {

        }

        @Override
        public void onCompleted(Thread thread) {
            //监听线程完毕
            DialogUtil.dismiss();
            ToastUtil.success("图片已保存");
        }

        @Override
        public void onStart(Thread thread) {

        }
    }

    @Override
    public void vpClickCallback() {
        //mSwipeBackHelper.backward();
    }

    @Override
    public void longClickListener(int position) {
        if (imgUrls.get(position).contains("http")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setItems(new String[]{"保存图片"}, (dialogInterface, i) -> {
                switch (i) {
                    case 0:
                        save();
                        break;
                }
            }).create().show();
        }


    }

    private void save() {
        Acp.getInstance(mActivity).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {


                        DialogUtil.buildLoading(mActivity);

                        executor.callback(threadCallback).execute(() -> {
                                    Bitmap bitmap = ImgUtil.url2Bitmap(imgUrls.get(currentPosition));
                                    String path = ImgUtil.saveImage(mActivity, bitmap, "图片" + System.currentTimeMillis());
                                }
                        );

                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });
    }
}
