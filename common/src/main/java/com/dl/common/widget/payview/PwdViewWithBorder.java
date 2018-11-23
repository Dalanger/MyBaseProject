package com.dl.common.widget.payview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dl.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by dalang at 2018/10/25
 */
public class PwdViewWithBorder extends RelativeLayout{
    private Context mContext;
    private EditText mEtPass;//密码输入框
    private LinearLayout mTextViewLayout;//显示多个textview的线性布局
    private List<TextView> textViews;//textview的集合
    private int mPassLength=6;//密码长度
    private GradientDrawable mEtPassDrawable;
    private InputCallback mInputCallback;
    private int mPassTextColor;//密文颜色
    private float mPassTextSize;//密文字体大小
    private int mBorderWidth;//边框及分割线宽度
    private int mBorderColor;//边框颜色
    private String mPassText;//密文字符
    private float mRadius;//边框圆角大小

    public void setInputFinishListener(InputCallback mInputCallback) {
        this.mInputCallback = mInputCallback;
    }

    public int getPassLength() {
        return mPassLength;
    }

    public void setPassLength(int mPassLength) {
        this.mPassLength = mPassLength;
    }

    public PwdViewWithBorder(Context context) {
        this(context,null);
    }
    public PwdViewWithBorder(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public PwdViewWithBorder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initAttrs(attrs);
        initView();
    }
    /**
     * @method  initAttrs
     * @description 加载自定义属性
     * @date: 2018/10/9 15:48
     * @author: LML
     * @param attrs 自定义属性
     * @return void
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PayPasswordView);
        if (typedArray != null) {
            mBorderWidth = (int) typedArray.getDimension(R.styleable.PayPasswordView_borderWidth, 1);
            mBorderColor = typedArray.getColor(R.styleable.PayPasswordView_borderColor, getResources().getColor(R.color.colorPrimary));
            mPassLength = typedArray.getInteger(R.styleable.PayPasswordView_passLength, 6);
            mRadius = typedArray.getDimension(R.styleable.PayPasswordView_radius, 4);
            mPassTextColor = typedArray.getColor(R.styleable.PayPasswordView_passTextColor, getResources().getColor(R.color.colorPrimary));
            mPassTextSize = typedArray.getDimension(R.styleable.PayPasswordView_passTextSize, 16);
            mPassText = typedArray.getString(R.styleable.PayPasswordView_passText);
            mEtPassDrawable=new GradientDrawable();
            mEtPassDrawable.setStroke(mBorderWidth, mBorderColor);
            mEtPassDrawable.setCornerRadius(mRadius);
            typedArray.recycle();
        }
    }

    public void setPassWord(String passWord){
        mEtPass.setText(passWord);
    }
    private void initView() {
        RelativeLayout.LayoutParams contentLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(contentLayoutParams);
        //设置edittext的属性
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mEtPass = new EditText(mContext);
        mEtPass.setLayoutParams(etLayoutParams);
        mEtPass.setTextSize(0);//文字大小设为0 使输入的文字不可见 后面用textview显示
        mEtPass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mPassLength)});//设置过滤器让控件只能输入设定密码长度的字符数
        mEtPass.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
        //外边框背景
        if(mEtPassDrawable!=null){
            mEtPass.setBackground(mEtPassDrawable);
        }else {
            mEtPass.setBackgroundResource(R.drawable.defaut_pay_view_bg);
        }
        mEtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //监听输入完成
                if(s.length()<=mPassLength){
                    initDatas(s);
                    if(s.length()==mPassLength){
                        if(mInputCallback!=null){
                            mInputCallback.onInputFinish(s.toString().trim());
                        }
                    }
                }
            }
        });
        //
        mTextViewLayout = new LinearLayout(mContext);
        mTextViewLayout.setLayoutParams(contentLayoutParams);
        mTextViewLayout.setOrientation(LinearLayout.HORIZONTAL);
        //为父布局relativelayout添加两个子布局
        addView(mEtPass);
        addView(mTextViewLayout);
        //循环添加textview
        initPassView();
    }
    /**
     * @method  initPassView
     * @description 添加显示密文“●”的控件
     * @date: 2018/10/9 15:46
     * @author: LML
     * @return void
     */
    private void initPassView(){
        if(textViews==null){
            textViews=new ArrayList<>();
        }
        TextView textViewPass;
        View view;
        LinearLayout.LayoutParams passParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams spliteParams = new LinearLayout.LayoutParams(mBorderWidth, LayoutParams.MATCH_PARENT);
        passParams.weight = 1;
        for(int x=0;x<mPassLength;x++){
            textViewPass=new TextView(mContext);
            textViewPass.setLayoutParams(passParams);
            textViewPass.setTextSize(mPassTextSize);
            textViewPass.setGravity(Gravity.CENTER);
            textViewPass.setTextColor(mPassTextColor);
            textViews.add(textViewPass);
            mTextViewLayout.addView(textViewPass);
            //添加密文分割线，分割线数量为密码长度-1
            if(x<mPassLength-1){
                view=new View(mContext);
                view.setBackgroundColor(mBorderColor);
                view.setLayoutParams(spliteParams);
                mTextViewLayout.addView(view);
            }
        }
    }
    public void initDatas(Editable s) {
        if(s.length() > 0)
        {
            for(int i = 0; i < mPassLength; i++)
            {
                if(i < s.length())
                {
                    if(TextUtils.isEmpty(mPassText)){
                        textViews.get(i).setText("●");
                    }else {
                        textViews.get(i).setText(mPassText);
                    }
                }
                else
                {
                    textViews.get(i).setText("");
                }
            }
        }
        else
        {
            for(int i = 0; i < mPassLength; i++)
            {
                textViews.get(i).setText("");
            }
        }
    }

    interface InputCallback{
        void onInputFinish(String password);
    }
}
