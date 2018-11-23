package com.dl.mybaseproject.demo1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dl.common.address.AddressPickerView;
import com.dl.common.base.BaseActivity;
import com.dl.common.bean.ShareElementBean;
import com.dl.common.manager.GlideManager;
import com.dl.common.utils.DialogUtil;
import com.dl.common.utils.ImgUtil;
import com.dl.common.utils.PhoneUtil;
import com.dl.common.utils.ToastUtil;
import com.dl.common.widget.dialog.DialogInput;
import com.dl.common.widget.dialog.DialogNormal;
import com.dl.common.widget.dialog.DialogVertical;
import com.dl.mybaseproject.R;
import com.jaeger.library.StatusBarUtil;
import com.jph.takephoto.model.TResult;
import com.lzh.easythread.Callback;
import com.lzh.easythread.EasyThread;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


public class Demo1Activity extends BaseActivity {


    @BindView(R.id.status_bar_fix)
    View statusBarFix;
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.user_icon)
    RoundedImageView userIcon;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536211148390&di=cd3339fddd0c6caa6532bc4109d7c6b3&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1038755924%2C483820292%26fm%3D214%26gp%3D0.jpg";
    private EasyThread executor;
    private ThreadCallback threadCallback;
    private Bitmap bitmap;
    private String mProvince = "";
    private String mCity = "";
    private String mDistrict = "";

    private int mYear = 1994;
    private int mMonth = 1;
    private int mDay = 21;


    @Override
    public int getContentViewId() {
        return R.layout.demo1_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initThread();
        initStatus();
        initImg();
    }

    private void initThread() {
        executor = EasyThread.Builder
                .single()
                .name("blur_photo")
                .build();
        threadCallback = new ThreadCallback();
    }

    private void initStatus() {
        StatusBarUtil.setTranslucentForImageView(this, 50, null);
        statusBarFix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PhoneUtil.getStatusBarHeight(mActivity)));
    }

    private void initImg() {
        ViewGroup.LayoutParams layoutParams = rlImg.getLayoutParams();
        layoutParams.height = PhoneUtil.getScreenWidth(mActivity) * 9 / 16;
        rlImg.setLayoutParams(layoutParams);


        GlideManager.loadHead(this, url, userIcon);

        executor.callback(threadCallback).execute(() ->
                bitmap = ImgUtil.getBitmapToBlur(url, 10)
        );

    }

    @OnClick({R.id.user_icon, R.id.title_back, R.id.tv_upload, R.id.rl_nickname,
            R.id.rl_sex, R.id.rl_birthday, R.id.rl_address, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_icon:
                Intent intent = new Intent(mActivity, ShareElementActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    userIcon.setTransitionName("image");
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, userIcon, "image");
                    startActivity(intent, compat.toBundle());
                } else {
                    ShareElementBean info = new ShareElementBean();
                    info.convertOriginalInfo(userIcon);
                    intent.putExtra("image", info);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                break;
            case R.id.title_back:
                mSwipeBackHelper.backward();
                break;
            case R.id.tv_upload:
                DialogUtil.uploadPhoto(mActivity, getTakePhoto(), 1);
                break;
            case R.id.rl_nickname:
                showNameDialog(tvNickname.getText().toString().trim());
                break;
            case R.id.rl_sex:
                showSexDialog();
                break;
            case R.id.rl_birthday:
                showDatePickerDialog(mActivity, tvBirthday);
                break;
            case R.id.rl_address:
                showAddressDialog();
                break;
            case R.id.btn_logout:
                showLogoutDialog();
                break;
        }
    }

    public void showDatePickerDialog(final Activity activity, final TextView tv) {
        // Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(activity, (view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String month = String.format("%02d", monthOfYear + 1);
            String day = String.format("%02d", dayOfMonth);
            tv.setText(year + "/" + month + "/" + day);

        }, mYear, mMonth, mDay);


        dialog.getDatePicker().setMaxDate(new Date().getTime());

        dialog.show();
    }

    private void showLogoutDialog() {
        final DialogNormal dialog = DialogUtil.buildDialogNormal(mActivity, "提示", "确定要退出当前账号?");
        dialog.setSureListener(v ->
                dialog.dismiss()
        );

        dialog.show();
    }

    private void showAddressDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);

        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);

        AddressPickerView addressView = rootView.findViewById(R.id.apvAddress);
        addressView.setPickerType(0);
        if (!TextUtils.isEmpty(mProvince)) {
            addressView.setPreData(mProvince, mCity, mDistrict);
        }

        addressView.setOnAddressPickerSure((province, city, district, provinceCode, cityCode, districtCode) -> {
            tvAddress.setText(province + "-" + city + "-" + district);
            mProvince = province;
            mCity = city;
            mDistrict = district;
            dialog.dismiss();
        });

        dialog.setContentView(rootView);
        dialog.show();
    }

    private void showSexDialog() {
        final DialogVertical dialog = DialogUtil.buildDialogVertical(this, "选择性别", "男", "女");
        dialog.setOnBtn1Listener(v -> {
            tvSex.setText("男");
            dialog.dismiss();
        });
        dialog.setOnBtn2Listener(v -> {
            tvSex.setText("女");
            dialog.dismiss();
        });
        dialog.show();
    }

    private void showNameDialog(String name) {
        final DialogInput dialog = DialogUtil.buildInputDialog(this, "修改昵称", name);
        if (!TextUtils.isEmpty(name)) {
            dialog.getEditText().setSelection(name.length());
        }

        dialog.setSureListener(v -> {
            if (!TextUtils.isEmpty(dialog.getContent())) {
                tvNickname.setText(dialog.getContent());
                dialog.dismiss();
            } else {
                ToastUtil.warn("昵称不能为空");
            }

        });
        dialog.show();
    }


    private class ThreadCallback implements Callback {

        @Override
        public void onError(Thread thread, Throwable t) {

        }

        @Override
        public void onCompleted(Thread thread) {
            //监听线程完毕
            ivBackground.setImageBitmap(bitmap);
        }

        @Override
        public void onStart(Thread thread) {

        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        final String imgResult = result.getImage().getCompressPath();
        File file = new File(imgResult);
        if (file.getName().contains("gif")) {
            ToastUtil.normal("不支持上传动图");
        } else {
            GlideManager.loadHead(this, file, userIcon);

            executor.callback(threadCallback).execute(() -> bitmap = ImgUtil.bitmapToBlur(BitmapFactory.decodeFile(imgResult), 10));
        }

    }
}
