package com.dl.mybaseproject.demo6;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dl.common.base.BaseActivity;
import com.dl.common.utils.LogUtil;
import com.dl.common.utils.PhoneUtil;
import com.dl.common.utils.ToastUtil;
import com.dl.common.widget.drawerlayout.DrawerMenu;
import com.dl.mybaseproject.R;
import com.jaeger.library.StatusBarUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static q.rorbin.badgeview.Badge.OnDragStateChangedListener.STATE_SUCCEED;

public class Demo6Activity extends BaseActivity {


    @BindView(R.id.status_bar_fix_drawer)
    View statusBarFixDrawer;
    @BindView(R.id.status_bar_fix_main)
    View statusBarFixMain;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.menu)
    DrawerMenu mMenu;
    @BindView(R.id.drawer_layout)
    LinearLayout drawerLayout;
    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.iv_contact)
    ImageView ivContact;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.iv_news)
    ImageView ivNews;
    @BindView(R.id.tv_news)
    TextView tvNews;
    @BindView(R.id.iv_discover)
    ImageView ivDiscover;
    @BindView(R.id.tv_discover)
    TextView tvDiscover;
    @BindView(R.id.shadow_layout)
    View shadowLayout;

    private Demo6Fragment demo6Fragment;
    private Badge badge_head;
    private Badge badge_msg;
    private Badge badge_contact;
    private Badge badge_news;
    private Badge badge_discover;
    private PopupWindow popWnd;
    private boolean isPopShow;

    @Override
    public int getContentViewId() {
        return R.layout.demo6_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initStatus();
        initView();
        initFragment();
        initList();
        listener();

    }

    private void initView() {
        badge_head = new QBadgeView(mActivity).bindTarget(ivHead);
        badge_head.setBadgeGravity(Gravity.TOP | Gravity.END);
        badge_head.setGravityOffset(1, 6, true);
        badge_head.setBadgePadding(3, true);
        badge_head.setBadgeText("");


        badge_msg = new QBadgeView(mActivity).bindTarget(ivMsg);
        badge_msg.setBadgeGravity(Gravity.TOP | Gravity.END);
        badge_msg.setBadgeNumber(1314);
        badge_msg.setShowShadow(false);
        //打开拖拽消除模式并设置监听
        badge_msg.setOnDragStateChangedListener((dragState, badge, targetView) -> {
            if (dragState == STATE_SUCCEED) {
                ToastUtil.normal("TODO-消息已读");
            }
        });

        badge_contact = new QBadgeView(mActivity).bindTarget(ivContact);
        badge_contact.setBadgeGravity(Gravity.TOP | Gravity.END);
        badge_contact.setGravityOffset(5, true);
        badge_contact.setBadgeText("");
        badge_contact.setShowShadow(false);

        badge_news = new QBadgeView(mActivity).bindTarget(ivNews);
        badge_news.setBadgeGravity(Gravity.TOP | Gravity.END);
        badge_news.setGravityOffset(4, true);
        badge_news.setBadgeText("");
        badge_news.setShowShadow(false);

        badge_discover = new QBadgeView(mActivity).bindTarget(ivDiscover);
        badge_discover.setBadgeGravity(Gravity.TOP | Gravity.END);
        badge_discover.setGravityOffset(4, true);
        badge_discover.setBadgeText("");
        badge_discover.setShowShadow(false);

    }

    private void initList() {

    }

    private void initFragment() {
        switchFragment(0);
        setTabState(ivMsg, R.mipmap.demo6_bottom_icon_1_check, tvMsg, R.color.blue_light);
    }

    private void listener() {

    }

    private void initStatus() {
        StatusBarUtil.setTranslucentForImageView(this, 100, null);
        statusBarFixMain.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PhoneUtil.getStatusBarHeight(mActivity)));
        statusBarFixDrawer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PhoneUtil.getStatusBarHeight(mActivity)));
        //此处0.8是由xml中设置而来 指drawer宽度占屏幕的百分比
        drawerLayout.setLayoutParams(new FrameLayout.LayoutParams((int) (PhoneUtil.getScreenWidth(mActivity) * 0.8), ViewGroup.LayoutParams.MATCH_PARENT));
    }


    private void switchFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String title = "";
        switch (position) {
            case 0:
                title = "消息";
                badge_msg.setBadgeNumber(1314);
                break;
            case 1:
                title = "联系人";
                badge_msg.hide(true);
                break;
            case 2:
                title = "看点";
                badge_msg.hide(true);
                break;
            case 3:
                title = "动态";
                badge_msg.hide(true);
                break;

        }
        titleName.setText(title);

        //demo方便共用一个fragment 理应四个
        if (demo6Fragment == null) {
            demo6Fragment = new Demo6Fragment();
            transaction.add(R.id.fragment, demo6Fragment, "tag" + position);
        } else {
            transaction.show(demo6Fragment);
        }


        transaction.commit();
    }


    private void resetTabState() {
        setTabState(ivMsg, R.mipmap.demo6_bottom_icon_1, tvMsg, R.color.grayB6);
        setTabState(ivContact, R.mipmap.demo6_bottom_icon_2, tvContact, R.color.grayB6);
        setTabState(ivNews, R.mipmap.demo6_bottom_icon_3, tvNews, R.color.grayB6);
        setTabState(ivDiscover, R.mipmap.demo6_bottom_icon_4, tvDiscover, R.color.grayB6);

    }


    private void setTabState(ImageView imageView, int imageResources, TextView textView, int textColor) {
        imageView.setImageResource(imageResources);
        textView.setTextColor(getResources().getColor(textColor));
    }

    //关闭侧滑返回
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }


    @Override
    public void onBackPressed() {
        if (mMenu.isOpened()) {
            mMenu.closeMenu();
        } else {
            if (isPopShow) {
                popWnd.dismiss();
            } else {
                super.onBackPressed();
            }

        }
    }


    @OnClick({R.id.iv_head, R.id.iv_plus, R.id.ll_msg, R.id.ll_contact, R.id.ll_news, R.id.ll_discover})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                if (mMenu.isOpened()) {
                    mMenu.closeMenu();
                } else {
                    mMenu.openMenu();
                }
                break;
            case R.id.iv_plus:

                if (isPopShow) {
                    popWnd.dismiss();
                    LogUtil.d(isPopShow+"1");
                } else {
                    LogUtil.d(isPopShow+"2");
                    showPop(view);
                }

                break;
            case R.id.ll_msg:
                resetTabState();
                setTabState(ivMsg, R.mipmap.demo6_bottom_icon_1_check, tvMsg, R.color.blue_light);
                switchFragment(0);
                break;
            case R.id.ll_contact:
                resetTabState();
                setTabState(ivContact, R.mipmap.demo6_bottom_icon_2_check, tvContact, R.color.blue_light);
                switchFragment(1);
                break;
            case R.id.ll_news:
                resetTabState();
                setTabState(ivNews, R.mipmap.demo6_bottom_icon_3_check, tvNews, R.color.blue_light);
                switchFragment(2);
                break;
            case R.id.ll_discover:
                resetTabState();
                setTabState(ivDiscover, R.mipmap.demo6_bottom_icon_4_check, tvDiscover, R.color.blue_light);
                switchFragment(3);
                break;
        }
    }


    private void showPop(View view) {
        isPopShow = true;
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.demo6_pop_layout, null, false);
        popWnd = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();

        int[] location = new int[2];
        popWnd.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popWnd.setOutsideTouchable(false);
        popWnd.setContentView(contentView);
        view.getLocationOnScreen(location);
        popWnd.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth + PhoneUtil.dp2px(mActivity, 15), location[1] + view.getHeight());

        shadowLayout.setVisibility(View.VISIBLE);
        popWnd.setOnDismissListener(() -> {
                    isPopShow = false;
                    shadowLayout.setVisibility(View.GONE);
                }
        );

        shadowLayout.setOnClickListener(v -> {
            popWnd.dismiss();
        });
    }

}
