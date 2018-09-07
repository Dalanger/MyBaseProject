package com.dl.common.address;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.common.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dalang on 2018/8/30.
 * 自定义地址选择器
 */

public class AddressPickerView extends RelativeLayout implements View.OnClickListener {
    // recyclerView 选中Item 的颜色
    private int defaultSelectedColor = getResources().getColor(R.color.addressRecycleViewSelectedColor);
    // recyclerView 未选中Item 的颜色
    private int defaultUnSelectedColor =getResources().getColor(R.color.addressRecycleViewUnSelectedColor);
    // 确定字体不可以点击时候的颜色
    private int defaultSureUnClickColor =getResources().getColor(R.color.addressSureUnClickColor);
    // 确定字体可以点击时候的颜色
    private int defaultSureCanClickColor = getResources().getColor(R.color.addressSureCanClickColor);

    private Context mContext;
    private int defaultTabCount = 3; //tab 的数量
    private TabLayout mTabLayout; // tabLayout
    private RecyclerView mRvList; // 显示数据的RecyclerView
    private String defaultProvince = "省份"; //显示在上面tab中的省份
    private String defaultCity = "城市"; //显示在上面tab中的城市
    private String defaultDistrict = "区县"; //显示在上面tab中的区县

    private List<AddressBean.AddressItemBean> mRvData; // 用来在recyclerview显示的数据
    private AddressAdapter mAdapter;   // recyclerview 的 adapter

    private AddressBean mYwpAddressBean; // 总数据
    private AddressBean.AddressItemBean mSelectProvice; //选中 省份 bean
    private AddressBean.AddressItemBean mSelectCity;//选中 城市  bean
    private AddressBean.AddressItemBean mSelectDistrict;//选中 区县  bean
    private int mSelectProvicePosition = 0; //选中 省份 位置
    private int mSelectCityPosition = 0;//选中 城市  位置
    private int mSelectDistrictPosition = 0;//选中 区县  位置

    private OnAddressPickerSureListener mOnAddressPickerSureListener;
    private TextView mTvSure; //确定

    private int pickerType;

    public AddressPickerView(Context context) {
        super(context);
        init(context);
    }

    public AddressPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddressPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    //新增 ------   pickerType 0原始的三种选择 1只选择省市 根据需求变动
    public void setPickerType(int pickerType) {
        this.pickerType = pickerType;
        //  原tablayout初始化在init

        mTabLayout.addTab(mTabLayout.newTab().setText(defaultProvince));
        mTabLayout.addTab(mTabLayout.newTab().setText(defaultCity));
        if (pickerType==0) {
            mTabLayout.addTab(mTabLayout.newTab().setText(defaultDistrict));
        }


        mTabLayout.addOnTabSelectedListener(tabSelectedListener);
    }



    /**
     * 初始化
     */
    private void init(Context context) {
        mContext = context;
        mRvData = new ArrayList<>();
        // UI
        View rootView = inflate(mContext, R.layout.address_picker_view, this);
        // 确定
        mTvSure = rootView.findViewById(R.id.tvSure);
        mTvSure.setTextColor(defaultSureUnClickColor);
        mTvSure.setOnClickListener(this);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tlTabLayout);

        // recyclerview adapter的绑定
        mRvList = (RecyclerView) rootView.findViewById(R.id.rvList);
        mRvList.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new AddressAdapter();
        mRvList.setAdapter(mAdapter);
        // 初始化默认的本地数据  也提供了方法接收外面数据
        mRvList.post(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }


    /**
     * 初始化数据
     * 拿assets下的json文件
     */
    private void initData() {
        StringBuilder jsonSB = new StringBuilder();

        try {
            BufferedReader addressJsonStream = new BufferedReader(new InputStreamReader(mContext.getAssets().open("address.json")));
            String line;
            while ((line = addressJsonStream.readLine()) != null) {
                jsonSB.append(line);
            }
            Log.e("dalang",jsonSB.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson=new Gson();
        // 将数据转换为对象  手动加入try catch 捕捉异常
        try {
            mYwpAddressBean = gson.fromJson(jsonSB.toString(),AddressBean.class);
        }catch (Exception e){
            Log.e("dalang","转化异常"+e.getMessage());
        }

        if (mYwpAddressBean != null) {
            mRvData.clear();
            mRvData.addAll(mYwpAddressBean.getProvince());
            mAdapter.notifyDataSetChanged();
        }
    }

    //原始依赖没有 增加引入先前选项
    public void setPreData(final String prov, final String city, final String district) {
        mRvList.post(new Runnable() {
            @Override
            public void run() {
                List<AddressBean.AddressItemBean> provBean=mYwpAddressBean.getProvince();
                List<AddressBean.AddressItemBean> cityBean=new ArrayList<>();
                List<AddressBean.AddressItemBean> districtBean=new ArrayList<>();
                mTabLayout.getTabAt(0).setText(prov);
                mTabLayout.getTabAt(1).setText(city);
                if (pickerType==0) {
                    mTabLayout.getTabAt(2).setText(district);
                }


                for (int i = 0; i < provBean.size(); i++) {
                    if (provBean.get(i).getN().equals(prov)) {
                        mSelectProvice=provBean.get(i);
                        mSelectProvicePosition=i;
                        break;
                    }
                }
                for (AddressBean.AddressItemBean itemBean : mYwpAddressBean.getCity()) {
                    if (itemBean.getP().equals(mSelectProvice.getI()))
                        cityBean.add(itemBean);
                }
                for (int i = 0; i < cityBean.size(); i++) {
                    if (cityBean.get(i).getN().equals(city)) {
                        mSelectCity=cityBean.get(i);
                        mSelectCityPosition=i;
                        break;
                    }
                }

                if (pickerType == 0) {
                    for (AddressBean.AddressItemBean itemBean : mYwpAddressBean.getDistrict()) {
                        if (itemBean.getP().equals(mSelectCity.getI()))
                            districtBean.add(itemBean);
                    }

                    for (int i = 0; i < districtBean.size(); i++) {
                        if (districtBean.get(i).getN().equals(district)) {
                            mSelectDistrict = districtBean.get(i);
                            mSelectDistrictPosition = i;
                            break;
                        }
                    }
                    mTabLayout.getTabAt(2).select();
                    mRvData.clear();
                    mRvData.addAll(districtBean);
                    mAdapter.notifyDataSetChanged();
                    mRvList.scrollToPosition(mSelectDistrictPosition);
                } else {
                    mTabLayout.getTabAt(1).select();
                    mRvData.clear();
                    mRvData.addAll(cityBean);
                    mAdapter.notifyDataSetChanged();
                    mRvList.scrollToPosition(mSelectCityPosition);
                }



                // 确定按钮变亮
                mTvSure.setTextColor(defaultSureCanClickColor);
            }
        });


    }

    /**
     * 开放给外部传入数据
     * 暂时就用这个Bean模型，如果数据不一致就需要各自根据数据来生成这个bean了
     */
    public void initData(AddressBean bean) {
        if (bean != null) {
            mSelectDistrict = null;
            mSelectCity = null;
            mSelectProvice = null;
            mTabLayout.getTabAt(0).select();

            mYwpAddressBean = bean;
            mRvData.clear();
            mRvData.addAll(mYwpAddressBean.getProvince());

            mAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvSure) {
            sure();
        }
    }

    //点确定
    private void sure() {
        if (pickerType == 0) {
            if (mSelectProvice != null &&
                    mSelectCity != null &&
                    mSelectDistrict != null) {
                //   回调接口
                if (mOnAddressPickerSureListener != null) {
                    mOnAddressPickerSureListener.onSureClick(mSelectProvice.getN(),  mSelectCity.getN(),mSelectDistrict.getN(),
                            mSelectProvice.getI(), mSelectCity.getI(), mSelectDistrict.getI());
                }
            } else {
                Toast.makeText(mContext, "地址还没有选完整哦", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (mSelectProvice != null &&
                    mSelectCity != null ) {
                //   回调接口
                if (mOnAddressPickerSureListener != null) {
                    mOnAddressPickerSureListener.onSureClick(mSelectProvice.getN(),  mSelectCity.getN(),"",
                            mSelectProvice.getI(), mSelectCity.getI(),"");
                }
            } else {
                Toast.makeText(mContext, "地址还没有选完整哦", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mYwpAddressBean = null;
    }

    /**
     * TabLayout 切换事件
     */
    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mRvData.clear();
            switch (tab.getPosition()) {
                case 0:
                    mRvData.addAll(mYwpAddressBean.getProvince());
                    mAdapter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectProvicePosition);
                    break;
                case 1:
                    // 点到城市的时候要判断有没有选择省份
                    if (mSelectProvice != null) {
                        for (AddressBean.AddressItemBean itemBean : mYwpAddressBean.getCity()) {
                            if (itemBean.getP().equals(mSelectProvice.getI()))
                                mRvData.add(itemBean);
                        }
                    } else {
                        Toast.makeText(mContext, "请您先选择省份", Toast.LENGTH_SHORT).show();
                    }
                    mAdapter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectCityPosition);

                    break;
                case 2:
                    // 点到区的时候要判断有没有选择省份与城市
                    if (mSelectProvice != null && mSelectCity != null) {
                        for (AddressBean.AddressItemBean itemBean : mYwpAddressBean.getDistrict()) {
                            if (itemBean.getP().equals(mSelectCity.getI()))
                                mRvData.add(itemBean);
                        }
                    } else {
                        Toast.makeText(mContext, "请您先选择省份与城市", Toast.LENGTH_SHORT).show();
                    }
                    mAdapter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectDistrictPosition);
                    break;
            }


        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };


    /**
     * 下面显示数据的adapter
     */
    class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address_text, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final int tabSelectPosition = mTabLayout.getSelectedTabPosition();
            holder.mTitle.setText(mRvData.get(position).getN());
            holder.mTitle.setTextColor(defaultUnSelectedColor);
            // 设置选中效果的颜色
            switch (tabSelectPosition) {
                case 0:
                    if (mRvData.get(position) != null &&
                            mSelectProvice != null &&
                            mRvData.get(position).getI().equals(mSelectProvice.getI())) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    break;
                case 1:
                    if (mRvData.get(position) != null &&
                            mSelectCity != null &&
                            mRvData.get(position).getI().equals(mSelectCity.getI())) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    break;
                case 2:
                    if (mRvData.get(position) != null &&
                            mSelectDistrict != null &&
                            mRvData.get(position).getI().equals(mSelectDistrict.getI())) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    break;
            }
            // 设置点击之后的事件
            holder.mTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击 分类别
                    switch (tabSelectPosition) {
                        case 0:
                            mSelectProvice = mRvData.get(position);
                            // 清空后面两个的数据
                            mSelectCity = null;
                            mSelectDistrict = null;
                            mSelectCityPosition = 0;
                            mSelectDistrictPosition = 0;
                            mTabLayout.getTabAt(1).setText(defaultCity);
                            if (pickerType==0) {
                                mTabLayout.getTabAt(2).setText(defaultDistrict);
                            }

                            // 设置这个对应的标题
                            mTabLayout.getTabAt(0).setText(mSelectProvice.getN());
                            // 跳到下一个选择
                            mTabLayout.getTabAt(1).select();
                            // 灰掉确定按钮
                            mTvSure.setTextColor(defaultSureUnClickColor);
                            mSelectProvicePosition = position;
                            break;
                        case 1:
                            mSelectCity = mRvData.get(position);
                            // 清空后面一个的数据
                            mSelectDistrict = null;
                            mSelectDistrictPosition = 0;
                            if (pickerType==0) {
                                mTabLayout.getTabAt(2).setText(defaultDistrict);
                            }
                            // 设置这个对应的标题
                            mTabLayout.getTabAt(1).setText(mSelectCity.getN());
                            if (pickerType == 0) {
                                // 跳到下一个选择
                                mTabLayout.getTabAt(2).select();
                                // 灰掉确定按钮
                                mTvSure.setTextColor(defaultSureUnClickColor);
                            } else {
                                mTvSure.setTextColor(defaultSureCanClickColor);
                            }

                            mSelectCityPosition = position;
                            break;
                        case 2:
                            mSelectDistrict = mRvData.get(position);
                            // 没了，选完了，这个时候可以点确定了
                            mTabLayout.getTabAt(2).setText(mSelectDistrict.getN());
                            notifyDataSetChanged();
                            // 确定按钮变亮
                            mTvSure.setTextColor(defaultSureCanClickColor);
                            mSelectDistrictPosition = position;
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mRvData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTitle;

            ViewHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.itemTvTitle);
            }

        }
    }


    /**
     * 点确定回调这个接口
     */
    public interface OnAddressPickerSureListener {
        void onSureClick(String province, String city, String district,String provinceCode, String cityCode, String districtCode);
    }

    public void setOnAddressPickerSure(OnAddressPickerSureListener listener) {
        this.mOnAddressPickerSureListener = listener;
    }


//
//    //外部调用示例
//
//    private void showAddressPop() {
//
//        final BottomSheetDialog dialog = new BottomSheetDialog(this);
//
//        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
//
//        AddressPickerView addressView = rootView.findViewById(R.id.apvAddress);
//        addressView.setPickerType(1);
//
//        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
//
//            @Override
//
//            public void onSureClick(String province, String city, String district,String provinceCode, String cityCode, String districtCode) {
//
//
//                dialog.dismiss();
//
//            }
//
//        });
//
//        dialog.setContentView(rootView);
//        dialog.show();
//    }


}
