package com.dl.mybaseproject.demo5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.dl.common.adapter.TabPageIndicatorAdapter;
import com.dl.common.base.BaseActivity;
import com.dl.common.utils.DisplayUtil;
import com.dl.mybaseproject.R;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * created by dalang at 2018/10/23
 */
public class Demo5Activity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.bg_img)
    View bgImg;
    @BindView(R.id.tab_layout)
    XTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private int lastState = 1;
    private List<String> list_title;
    private List<Integer> list_id;
    private List<Fragment> list_fragment;
    private TabPageIndicatorAdapter indicatorAdapter;
    private NoScrollBehavior myAppBarLayoutBehavoir;
    private Demo5ItemFragment currentFragment;

    @Override
    public int getContentViewId() {
        return R.layout.demo5_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initTab();
        initListener();

    }


    private void initView() {
        StatusBarUtil.setTranslucentForImageView(mActivity, 50, null);
        //防止toolbar顶入状态栏
        CollapsingToolbarLayout.LayoutParams lp2 = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        lp2.topMargin = DisplayUtil.getStatusBarHeight(mActivity);
        toolbar.setLayoutParams(lp2);

        myAppBarLayoutBehavoir = (NoScrollBehavior)
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
    }

    private void initListener() {


        appBar.addOnOffsetChangedListener(this);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //拿到当前fragment
                currentFragment = (Demo5ItemFragment) list_fragment.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initTab() {
        //初始化tab
        list_title = new ArrayList<>();
        list_title.add("军事");
        list_title.add("娱乐");
        list_title.add("萌宠");
        list_title.add("生活");

        list_id = new ArrayList<>();
        list_id.add(1);
        list_id.add(2);
        list_id.add(3);
        list_id.add(4);

        list_fragment = new ArrayList<>();
        for (int i = 0; i < list_id.size(); i++) {
            Demo5ItemFragment orderItemFragment = Demo5ItemFragment.newInstance(list_id.get(i));
            list_fragment.add(orderItemFragment);
        }
        currentFragment = (Demo5ItemFragment) list_fragment.get(0);
        indicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), list_fragment, list_title);
        viewpager.setAdapter(indicatorAdapter);
        tabLayout.setupWithViewPager(viewpager);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appBar.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //顶部渐变 标题栏处理
        float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
        int alpha = (int) (255 * percent);
        titleLayout.setBackgroundColor(Color.argb(alpha, 26, 128, 210));
        tvTitle.setAlpha(alpha);
        bgImg.setBackgroundColor(Color.argb(alpha, 26, 128, 210));
        if (percent < 0.5) {
            rlBack.setBackgroundResource(R.drawable.oval_shadow);
        } else {
            rlBack.setBackground(null);
        }

        //滑动事件处理
        if (percent == 0) {
            //当完全展开时  appbar可滑动  禁止refresh(可根据需求不禁止刷新)
            myAppBarLayoutBehavoir.setNoScroll(false);
            currentFragment.setRefreshState(false);
        } else if (percent == 1) {
            //当完全折叠时  appbar不可滑动使tab吸顶   允许refresh
            currentFragment.setRefreshState(true);
            myAppBarLayoutBehavoir.setNoScroll(true);
        } else {
            //滑动中 appbar可滑动 禁止refresh(建议禁止刷新,否则会appbar影响滑动流畅)
            myAppBarLayoutBehavoir.setNoScroll(false);
            currentFragment.setRefreshState(false);
        }
    }


    @Override
    public void onBackPressed() {

        //返回监听 当appBar处于不可滑动(即完全折叠)时，先释放appBar
        if (myAppBarLayoutBehavoir.isNoScroll()) {
            myAppBarLayoutBehavoir.setNoScroll(false);
            appBar.setExpanded(true, true);
        } else {
            super.onBackPressed();
        }
    }


    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        //返回监听 当appBar处于不可滑动(即完全折叠)时，先释放appBar
        if (myAppBarLayoutBehavoir.isNoScroll()) {
            myAppBarLayoutBehavoir.setNoScroll(false);
            appBar.setExpanded(true, true);
        } else {
            mSwipeBackHelper.backward();
        }
    }
}
