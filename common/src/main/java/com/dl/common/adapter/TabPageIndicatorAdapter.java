package com.dl.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dalang on 2018/9/3
 *
 */
public class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> list_fragment;                         //fragment列表
    private List<String> list_Title;                              //tab名的列表


    public TabPageIndicatorAdapter(FragmentManager fm,List<Fragment> fragments,List<String> list_Title) {
        super(fm);
        list_fragment=fragments;
        this.list_Title=list_Title;
    }



    private void setFragmentList(List<Fragment> fragmentList) {
        if (this.list_fragment != null) {
            list_fragment.clear();
        }
        this.list_fragment = fragmentList;
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

}
