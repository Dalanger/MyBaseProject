package com.dl.common.recyclerview;

import android.graphics.Canvas;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.dl.common.R;
import com.dl.common.utils.ToastUtil;

import java.util.Collections;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;

/**
 * created by dalang at 2018/11/26
 * 微信拖拽排序删除
 */
public class WXTouchHelper extends ItemTouchHelper.Callback {

    private int dragFlags;
    private int swipeFlags;
    private BGARecyclerViewAdapter adapter;
    private List<String> imagesList;//图片的顺序与拖拽顺序保持一致
    private boolean up;//手指抬起标记位
    private NestedScrollView scrollView;

    public WXTouchHelper(BGARecyclerViewAdapter adapter, List<String> imagesList, NestedScrollView scrollView) {
        this.adapter = adapter;
        this.imagesList = imagesList;
        this.scrollView=scrollView;
    }

    /**
     * 设置item是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //判断 recyclerView的布局管理器数据
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {//设置能拖拽的方向

            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

            swipeFlags = 0;//0则不响应事件
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 当用户从item原来的位置拖动可以拖动的item到新位置的过程中调用
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//得到item原来的position
        int toPosition = target.getAdapterPosition();//得到目标position
        //因为没有将 +号的图片 加入imageList,所以不用imageList.size-1 此处限制不能移动到recyclerView最后一位
        if (toPosition == imagesList.size()  || imagesList.size()  == fromPosition) {
            return false;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {

                Collections.swap(imagesList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {

                Collections.swap(imagesList, i, i - 1);
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * 设置是否支持长按拖拽
     * 此处必须返回false
     * 需要在recyclerView长按事件里限制,否则最后+号长按后扔可拖拽
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 当用户与item的交互结束并且item也完成了动画时调用
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        adapter.notifyDataSetChanged();
        initData();
        if (dragListener != null) {
            dragListener.clearView();
        }
    }

    /**
     * 重置
     */
    private void initData() {
        if (dragListener != null) {
            dragListener.deleteState(false);
            dragListener.dragState(false);
        }
        up = false;
    }

    /**
     * 自定义拖动与滑动交互
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (null == dragListener) {
            return;
        }

        int editTextHeight=ToastUtil.getContext().getResources().getDimensionPixelSize(R.dimen.dimen_100);//recyclerview上面的editText的高度为100
        int buttonHeight=ToastUtil.getContext().getResources().getDimensionPixelSize(R.dimen.dimen_50);//删除按钮高度
        /**
         * item间隔10dp,因为item的xml布局中有个10dp的空白，
         * 拖拽时是拖拽整个itemView所有导致底部10dp空白先接触删除按钮所以在判断阈值时应该考虑此10dp,
         * 如果是采用addItemDecoration添加分割线就不用考虑这10dp，但是拖拽时会出现分割线遮挡的情况,具体效果可以自己实验一下
         */
        int spaceHeight=ToastUtil.getContext().getResources().getDimensionPixelSize(R.dimen.dimen_10);//
        /**
         * scrollView.getHeight()-editTextHeight 为recyclerview的高度
         * 此处不用onChildDraw里的参数recyclerView.getHeight来计算，因为当添加图片至超出屏幕高度
         * 即scrollView可以滑动后获取的recyclerview不准确,亲测。
         */
        if (dY>=(scrollView.getHeight()-editTextHeight)
                - viewHolder.itemView.getBottom()//item底部距离recyclerView顶部高度
                -buttonHeight
                +scrollView.getScrollY()
                +spaceHeight) {//拖到删除处
            dragListener.deleteState(true);
            if (up) {//在删除处放手，则删除item
                viewHolder.itemView.setVisibility(View.INVISIBLE);//先设置不可见，如果不设置的话，会看到viewHolder返回到原位置时才消失，因为remove会在viewHolder动画执行完成后才将viewHolder删除
                imagesList.remove(viewHolder.getAdapterPosition());
                dragListener.deleteOk();
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                initData();
                return;
            }
        } else {//没有到删除处
            if (View.INVISIBLE == viewHolder.itemView.getVisibility()) {//如果viewHolder不可见，则表示用户放手，重置删除区域状态
                dragListener.dragState(false);
            }
            dragListener.deleteState(false);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     * 当长按选中item的时候（拖拽开始的时候）调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && dragListener != null) {
            dragListener.dragState(true);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 设置手指离开后ViewHolder的动画时间，在用户手指离开后调用
     *
     * @param recyclerView
     * @param animationType
     * @param animateDx
     * @param animateDy
     * @return
     */
    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        //手指放开
        up = true;
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    public interface DragListener {
        /**
         * 用户是否将 item拖动到删除处，根据状态改变颜色
         *
         * @param delete
         */
        void deleteState(boolean delete);

        /**
         * 是否于拖拽状态
         *
         * @param start
         */
        void dragState(boolean start);

        /**
         * 当用户与item的交互结束并且item也完成了动画时调用
         */
        void clearView();


        /**
         * 当删除完成后调用
         */
        void deleteOk();
    }

    private DragListener dragListener;

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

}
