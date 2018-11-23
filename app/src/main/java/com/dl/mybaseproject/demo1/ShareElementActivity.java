package com.dl.mybaseproject.demo1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.dl.common.bean.ShareElementBean;
import com.dl.common.manager.GlideManager;
import com.dl.common.manager.ShareElementManager;
import com.dl.mybaseproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class ShareElementActivity extends AppCompatActivity {

    @BindView(R.id.image)
    PhotoView mImageView;
    private ShareElementManager mShareElement;
    private String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536211148390&di=cd3339fddd0c6caa6532bc4109d7c6b3&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1038755924%2C483820292%26fm%3D214%26gp%3D0.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.demo1_activity_share_element);
        ButterKnife.bind(this);
        GlideManager.loadImage1_1(this,url,mImageView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageView.setTransitionName("image");
        } else {
            ShareElementBean  info= getIntent().getExtras().getParcelable("image");
            mShareElement = new ShareElementManager(info, this, mImageView.getRootView());
            mShareElement.convert(mImageView)
                    .setDuration(1000)
                    .setInterpolator(new LinearInterpolator())
                    .startEnterAnimator();
        }

        mImageView.setOnViewTapListener((view, x, y) -> exit());

        mImageView.postDelayed(() -> getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ShareElementActivity.this,R.color.black))),500);
    }

    private void exit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            mShareElement.convert(mImageView)
                    .setDuration(1000)
                    .setInterpolator(new LinearInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            finish();
                            overridePendingTransition(0, 0);
                        }
                    })
                    .startExitAnimator();
        }
    }


    @Override
    public void onBackPressed() {
        exit();
    }
}
