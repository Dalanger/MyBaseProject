package com.dl.mybaseproject.main;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.dl.common.base.BaseActivity;
import com.dl.common.recyclerview.RecyItemTouchHelperCallback;
import com.dl.common.utils.ToastUtil;
import com.dl.mybaseproject.R;
import com.dl.mybaseproject.demo1.Demo1Activity;
import com.dl.mybaseproject.demo2.Demo2Activity;
import com.dl.mybaseproject.demo3.view.Demo3Activity;
import com.dl.mybaseproject.demo4.Demo4Activity;
import com.dl.mybaseproject.demo5.Demo5Activity;
import com.dl.mybaseproject.demo6.Demo6Activity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_list)
    RecyclerView mainList;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;

    private List<MainDataBean> data;
    private long mBackPressed;
    private static final int TIME_INTERVAL = 2000;
    private MainAdapter mainAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.main_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initView();

        initData();
        initList();

    }



    private void initList() {
        mainAdapter = new MainAdapter(mainList);
        mainList.setAdapter(mainAdapter);
        mainAdapter.setData(data);
        RecyItemTouchHelperCallback itemTouchHelperCallback = new RecyItemTouchHelperCallback(mainAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mainList);

        mainAdapter.setOnRVItemLongClickListener((parent, itemView, position) -> {
            ToastUtil.normal(position + "");
            return false;
        });

        mainAdapter.setOnRVItemClickListener((parent, itemView, position) -> mSwipeBackHelper.forward(data.get(position).getActivity()));





    }


    private void initView() {

        titleName.setText("Dalanger's  Demos");

        data = new ArrayList<>();
    }


    private void initData() {

        data.add(new MainDataBean(R.mipmap.home_camera, "毛玻璃效果/照片选择/头像放大动画/时间选择/地址选择/dialog展示", Demo1Activity.class));
        data.add(new MainDataBean(R.mipmap.home_demo2, "仿京东顶部渐变/自定义指示器/viewpager3D效果/瀑布流", Demo2Activity.class));
        data.add(new MainDataBean(R.mipmap.home_demo3, "结合MVP使用RxJava常用操作符merge/flatMap/zip/filter", Demo3Activity.class));
        data.add(new MainDataBean(R.mipmap.home_demo4, "仿支付宝首页顶部伸缩滑动/中间层下拉刷新", Demo4Activity.class));
        data.add(new MainDataBean(R.mipmap.home_demo5, "TabLayout+ViewPager吸顶及刷新数量动画", Demo5Activity.class));
        data.add(new MainDataBean(R.mipmap.home_demo6, "仿QQ首页drawer/侧滑删除/浮动imgaeView/角标拖拽", Demo6Activity.class));

        for (int i = 0; i < 20; i++) {
            data.add(new MainDataBean(R.mipmap.home_more, "待续", Demo1Activity.class));
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
            ToastUtil.snack(rootLayout, "再按一次退出");
        }
        mBackPressed = System.currentTimeMillis();
    }


}
