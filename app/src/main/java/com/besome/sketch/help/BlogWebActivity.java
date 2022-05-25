package com.besome.sketch.help;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import a.a.a.xB;

public class BlogWebActivity extends BaseAppCompatActivity {
    public WebView k;
    public ProgressBar l;

    @Override
    public void onBackPressed() {
        if (k.canGoBack()) {
            k.goBack();
        } else {
            finish();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(2131427369);
        k = findViewById(2131232331);
        l = findViewById(2131231608);
        l.setMax(100);
        k.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100 && l.getVisibility() != View.VISIBLE) {
                    l.setVisibility(View.VISIBLE);
                }

                if (newProgress >= 100 && l.getVisibility() == View.VISIBLE) {
                    l.setVisibility(View.GONE);
                }

                l.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        k.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        WebSettings webSettings = k.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        k.loadUrl(xB.b().a(getApplicationContext(), 2131625628));
    }

}
