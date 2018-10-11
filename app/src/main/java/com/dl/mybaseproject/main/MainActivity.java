package com.dl.mybaseproject.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.common.base.BaseActivity;
import com.dl.common.uitils.ToastUtil;
import com.dl.mybaseproject.R;
import com.dl.mybaseproject.demo1.Demo1Activity;
import com.dl.mybaseproject.demo2.Demo2Activity;
import com.dl.mybaseproject.demo3.view.Demo3Activity;
import com.dl.mybaseproject.demo4.Demo4Activity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_list)
    RecyclerView mainList;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private List<MainDataBean> data;
    private long mBackPressed;
    private static final int TIME_INTERVAL = 2000;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initView();

        initData();
        initList();

    }

    private void initList() {
        MainAdapter mainAdapter = new MainAdapter(mainList);
        mainList.setAdapter(mainAdapter);
        mainAdapter.setData(data);
        mainAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                mSwipeBackHelper.forward(data.get(position).getActivity());
            }
        });
    }

    private void initView() {
        titleBack.setVisibility(View.GONE);
        titleName.setText("Dalanger's  Demos");
        data=new ArrayList<>();
    }


    private void initData() {

        data.add(new MainDataBean(R.mipmap.home_camera,"毛玻璃效果/照片选择/头像放大动画/时间选择/地址选择/dialog展示", Demo1Activity.class));
        data.add(new MainDataBean(R.mipmap.home_demo2,"仿京东顶部渐变/自定义指示器/viewpager3D效果/瀑布流", Demo2Activity.class));
        data.add(new MainDataBean(R.mipmap.home_demo3,"结合MVP使用RxJava常用操作符merge/flatMap/zip/filter", Demo3Activity.class));
        data.add(new MainDataBean(R.mipmap.home_demo4,"仿支付宝首页顶部伸缩滑动/中间层下拉刷新", Demo4Activity.class));

        for (int i = 0; i < 30; i++) {
            data.add(new MainDataBean(R.mipmap.home_more,"待续", Demo1Activity.class));
        }



    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }



    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            ToastUtil.normal("再按一次退出");
        }
        mBackPressed = System.currentTimeMillis();
    }


}
