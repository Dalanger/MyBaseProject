package com.dl.mybaseproject.demo7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dl.common.activity.BigPhotoActivity;
import com.dl.common.adapter.PhotoPublishAdapter;
import com.dl.common.base.BaseActivity;
import com.dl.common.recyclerview.OnRecyclerItemClickListener;
import com.dl.common.recyclerview.WXTouchHelper;
import com.dl.common.utils.DialogUtil;
import com.dl.common.utils.DisplayUtil;
import com.dl.common.utils.ToastUtil;
import com.dl.mybaseproject.R;
import com.jaeger.library.StatusBarUtil;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;

public class Demo7Activity extends BaseActivity {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.iv_qzone)
    ImageView ivQzone;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.recycler_photo)
    RecyclerView recyclerPhoto;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    private int limit = 9;//限制上传多少张图拍呢
    private List<String> imgSelected = new ArrayList<>();
    private PhotoPublishAdapter photoPublishAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public int getContentViewId() {
        return R.layout.demo7_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        StatusBarUtil.setColorForSwipeBack(mActivity,color(R.color.white),50);
        initList();
        fixBottom();

    }

    private void initList() {
        photoPublishAdapter = new PhotoPublishAdapter(recyclerPhoto, limit);
        /**
         * 如果没有scrollView包裹 则不用传入scrollView
         */
        WXTouchHelper myCallBack = new WXTouchHelper(photoPublishAdapter, imgSelected,scrollView);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerPhoto);
        recyclerPhoto.setAdapter(photoPublishAdapter);
        photoPublishAdapter.setData(imgSelected);

        recyclerPhoto.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerPhoto) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == imgSelected.size()) {
                    DialogUtil.uploadMultiplePhoto(mActivity, getTakePhoto(), limit);
                } else {
                if (imgSelected.size() != 0) {
                    Intent intent = new Intent(mActivity, BigPhotoActivity.class);
                    intent.putStringArrayListExtra("imgUrls", (ArrayList<String>) imgSelected);
                    intent.putExtra("position", viewHolder.getAdapterPosition());
                    mSwipeBackHelper.forward(intent);
                }
                }
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() != imgSelected.size()) {
                    BGAKeyboardUtil.closeKeyboard(mActivity);
                    itemTouchHelper.startDrag(viewHolder);
                }
            }
        });


        myCallBack.setDragListener(new WXTouchHelper.DragListener() {
            @Override
            public void deleteState(boolean delete) {
                if (delete) {
                    tvDelete.setAlpha(0.8f);
                    tvDelete.setText("松手即可删除");
                } else {
                    tvDelete.setAlpha(0.5f);
                    tvDelete.setText("拖到此处删除");
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start) {
                    tvDelete.setVisibility(View.VISIBLE);
                } else {
                    tvDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void clearView() {

                fixBottom();

            }

            @Override
            public void deleteOk() {
                //删除后重新计算图片选择数量
                limit = 9 - imgSelected.size();

            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        for (int i = 0; i < result.getImages().size(); i++) {
            File file = new File(result.getImages().get(i).getCompressPath());
            if (file.getName().contains("gif")) {
                ToastUtil.warn("不支持上传动图");
            } else {
                imgSelected.add(result.getImages().get(i).getCompressPath());
            }
        }
        //添加后计算图片选择数量
        limit = 9 - imgSelected.size();
        photoPublishAdapter.notifyDataSetChanged();
        fixBottom();
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        mSwipeBackHelper.backward();
    }

    /**
     * 处理recyclerView下面的布局
     */
    private void fixBottom() {

        int row = photoPublishAdapter.getItemCount() / 3;
        row = (0 == photoPublishAdapter.getItemCount() % 3) ? row : row + 1;//少于3为1行
        row = (4 == row) ? 3 : row;//最多为三行

        int width = DisplayUtil.getScreenWidth(mActivity);
        int itemWidth = (int) (width - getResources().getDimension(R.dimen.dimen_60)) / 3;//item宽高
        int itemSpace=(int) getResources().getDimension(R.dimen.dimen_10);//item间隔
        int marginTop = (getResources().getDimensionPixelSize(R.dimen.recycle_margin_top)
                + itemWidth * row
                +itemSpace*(row-1)
                + getResources().getDimensionPixelSize(R.dimen.bottom_margin_top));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llBottom.getLayoutParams();
        params.setMargins(0, marginTop, 0, 0);
        llBottom.setLayoutParams(params);

    }



}
