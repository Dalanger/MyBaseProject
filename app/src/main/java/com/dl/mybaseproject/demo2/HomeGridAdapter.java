package com.dl.mybaseproject.demo2;

import android.support.v7.widget.RecyclerView;

import com.dl.mybaseproject.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * created by dalang at 2018/9/11
 */
public class HomeGridAdapter extends BGARecyclerViewAdapter<GridBean> {
    public HomeGridAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_home_grid);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, GridBean model) {
        helper.setText(R.id.tv_name,model.getName());
    }
}
