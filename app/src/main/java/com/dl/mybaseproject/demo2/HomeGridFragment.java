package com.dl.mybaseproject.demo2;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.dl.common.base.BaseFragment;
import com.dl.mybaseproject.R;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * created by dalang at 2018/9/11
 */
public class HomeGridFragment extends BaseFragment {

    @BindView(R.id.grid_list)
    RecyclerView gridList;
    Unbinder unbinder;
    private List<GridBean> data;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_grid;
    }

    /**
     * 传入需要的参数，dataList
     *
     * @param dataList
     * @return
     */
    public static HomeGridFragment newInstance(List<GridBean> dataList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) dataList);
        HomeGridFragment fragment = new HomeGridFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        HomeGridAdapter gridAdapter=new HomeGridAdapter(gridList);
        gridList.setAdapter(gridAdapter);
        gridAdapter.setData(data);
    }

    @Override
    protected void managerArguments() {
        Bundle bundle = getArguments();
        if (bundle != null)
            data = (List<GridBean>) bundle.getSerializable("list");
    }

}
