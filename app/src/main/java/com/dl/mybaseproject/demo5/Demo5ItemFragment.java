package com.dl.mybaseproject.demo5;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dl.common.base.BaseFragment;
import com.dl.mybaseproject.R;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * created by dalang at 2018/10/23
 */
public class Demo5ItemFragment extends BaseFragment {

    @BindView(R.id.pull_to_refresh)
    PtrClassicFrameLayout pullToRefresh;
    @BindView(R.id.demo_list)
    RecyclerView demoList;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    Unbinder unbinder;
    private int mType;
    private List<String> data;
    private Demo5Adapter demo5Adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_demo5;
    }


    /**
     * 传入需要的参数，设置给mType
     *
     * @param mType
     * @return
     */
    public static Demo5ItemFragment newInstance(int mType) {
        Bundle bundle = new Bundle();
        bundle.putInt("mType", mType);
        Demo5ItemFragment fragment = new Demo5ItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        initList();
        initRefresh();
    }

    private void initRefresh() {
        pullToRefresh.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                //延迟2s模拟数据请求
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //模拟数据
                        for (int i = 0; i < 10; i++) {
                            data.add("我是第"+mType+"页(加载)");
                        }
                        demo5Adapter.notifyDataSetChanged();
                        pullToRefresh.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int num=10;
                        for (int i = 0; i < num; i++) {
                            data.add(i,"我是第"+mType+"页(刷新)");
                        }
                        demo5Adapter.notifyDataSetChanged();
                        pullToRefresh.refreshComplete();

                        //顶部刷新条数动画显示
                        tvRefresh.setVisibility(View.VISIBLE);
                        tvRefresh.setText("已更新"+num+"条内容");
                        //借鉴第三方库viewAnimator 动画可自行更改
                        ViewAnimator
                                .animate(tvRefresh)
                                .duration(500)
                                .zoomIn()
                                .onStop(new AnimationListener.Stop() {
                                    @Override
                                    public void onStop() {
                                        ViewAnimator
                                                .animate(tvRefresh)
                                                .fadeOut()
                                                .duration(500)
                                                .onStop(new AnimationListener.Stop() {
                                                    @Override
                                                    public void onStop() {
                                                        tvRefresh.setVisibility(View.GONE);
                                                    }
                                                })
                                                .start();
                                    }
                                })
                                .start();
                    }
                }, 2000);
            }
        });
    }

    private void initList() {
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("我是第"+mType+"页");
        }

        demo5Adapter = new Demo5Adapter(demoList);
        demoList.setAdapter(demo5Adapter);
        demo5Adapter.setData(data);
    }

    @Override
    protected void managerArguments() {
        Bundle bundle = getArguments();
        if (bundle != null)
            mType = bundle.getInt("mType");
    }

    public void setRefreshState(boolean isEnable) {
        pullToRefresh.setEnabled(isEnable);
    }

}
