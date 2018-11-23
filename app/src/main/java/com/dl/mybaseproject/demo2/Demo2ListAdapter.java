package com.dl.mybaseproject.demo2;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dl.mybaseproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * created by dalang at 2018/9/11
 */
public class Demo2ListAdapter extends BGARecyclerViewAdapter<String>{


    private List<Integer> heightList;//随机数集合

    public Demo2ListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.demo2_item_list);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void setData(List<String> data) {
        super.setData(data);
        heightList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int height = new Random().nextInt(300) + 650;//随机数
            heightList.add(height);
        }
        
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {
        CardView layout=helper.getView(R.id.layout);
        ViewGroup.LayoutParams layoutParams=layout.getLayoutParams();
        layoutParams.height=heightList.get(position);
        layout.setLayoutParams(layoutParams);
    }

}
