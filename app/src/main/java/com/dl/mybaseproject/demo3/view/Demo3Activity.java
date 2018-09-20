package com.dl.mybaseproject.demo3.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dl.common.base.BaseMvpActivity;
import com.dl.common.uitils.DialogUtil;
import com.dl.common.uitils.ToastUtil;
import com.dl.mybaseproject.R;
import com.dl.mybaseproject.demo3.bean.TestBean2;
import com.dl.mybaseproject.demo3.bean.TestBean5;
import com.dl.mybaseproject.demo3.contract.IDemo3Contract;
import com.dl.mybaseproject.demo3.presenter.Demo3Presenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description :
 *
 * @author dalang
 * @date 2018/9/17  16:59
 * - generate by MvpAutoCodePlus plugin.
 */

public class Demo3Activity extends BaseMvpActivity<IDemo3Contract.View, IDemo3Contract.Presenter> implements IDemo3Contract.View {

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_result_1)
    TextView tvResult1;
    @BindView(R.id.tv_result_2)
    TextView tvResult2;
    @BindView(R.id.tv_result_3)
    TextView tvResult3;
    @BindView(R.id.tv_result_4)
    TextView tvResult4;


    @Override
    public int getContentViewId() {
        return R.layout.activity_demo3;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        titleName.setText("RxJava常用操作符");
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.warn(msg);
    }

    @Override
    public void showDialogLoading() {
        DialogUtil.buildLoading(mActivity);
    }

    @Override
    public void dismissDialogLoading() {
        DialogUtil.dismiss();
    }

    @Override
    public void showUILoading() {

    }

    @Override
    public void dismissUILoading() {

    }

    @Override
    public void errorUI() {

    }

    @Override
    public void errorDialog() {
        ToastUtil.error();
    }

    @Override
    public IDemo3Contract.Presenter createPresenter() {
        return new Demo3Presenter();
    }

    @Override
    public IDemo3Contract.View createView() {
        return this;
    }


    @OnClick({R.id.title_back, R.id.btn_1, R.id.btn_2, R.id.btn_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                mSwipeBackHelper.backward();
                break;
            case R.id.btn_1:
                mPresenter.getMergeData();
                break;
            case R.id.btn_2:
                mPresenter.getFlatMapData("员工须知");
                break;
            case R.id.btn_3:
                mPresenter.getZipData();
                break;
        }
    }

    @Override
    public void setData1(String rate) {
        tvResult1.setText("请求结果1---" + rate);
    }

    @Override
    public void setData2(List<TestBean2.DateBean> dataList) {
        tvResult2.setText("请求结果2---" + dataList.toString());
    }

    @Override
    public void setData3(String data3) {
        tvResult3.setText("嵌套请求结果---" + data3);
    }

    @Override
    public void setData4(List<TestBean5> dataList) {
        tvResult4.setText("合并请求结果---" + dataList.toString());
    }

}

