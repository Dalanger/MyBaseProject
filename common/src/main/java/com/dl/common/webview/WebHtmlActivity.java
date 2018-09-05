package com.dl.common.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dl.common.R;
import com.dl.common.base.BaseActivity;


/**
 * Created by dalang on 2018/9/4.
 * webview加载 网页/富文本
 */
public class WebHtmlActivity extends BaseActivity {


    private int type = 0;//0 富文本 1网页
    private String data;
    private String title;
    private TextView titleName;
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    public int getContentViewId() {
        return R.layout.activity_web_html;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", 0);
        data = getIntent().getStringExtra("data");
        title=getIntent().getStringExtra("title");
        initActionBar();
        initWebView();
    }

    private void initActionBar() {
        findViewById(R.id.title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeBackHelper.backward();
            }
        });

        titleName = findViewById(R.id.title_name);
        titleName.setText(title);
    }

    private void initWebView() {
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progress);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        switch (type) {
            case 0:
                String str = "<style>img{max-width:100%;height:auto;}</style>";
                webView.loadData(str+data, "text/html", "UTF-8");
                break;
            case 1:
                webView.loadUrl(data);
                break;
        }
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    progressBar.setProgress(newProgress);
                }

            }
        });


    }


}
