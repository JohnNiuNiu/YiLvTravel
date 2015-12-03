package me.yilv.yilvtravel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liliu on 15/4/2.
 */
public class WebBrowserActivity extends Activity implements View.OnClickListener {
    private String TAG = WebBrowserActivity.class.getSimpleName();

    public static final String KEY_URL = "key_url";
    public static final String KEY_IS_NEED_LOGIN = "key_is_need_login";
    private static Map<String, String> PARAMETER;

    static {
        PARAMETER = new HashMap<>();
        PARAMETER.put("view_from", "app");
    }

    protected WebViewClient mWebViewClient;
    private String mUrl;
    private WebView mWv;
    private ProgressBar mProgressBar;
    private View failedView;

    private long mTempExitTime;
    private Toast mToast;

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100 && mProgressBar.getVisibility() == View.VISIBLE) {
                mProgressBar.setVisibility(View.INVISIBLE);
            } else {
                if (mProgressBar.getVisibility() != View.VISIBLE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //setTopTitle(title);
        }

    };
    private DownloadListener mWvDlListener = new DownloadListener() {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            LogUtil.i(TAG, "url:" + url + "//userAgent:" + userAgent + "//contentDisposition:"
                    + contentDisposition + "//mimetype:" + mimetype + "//contentLength:" + contentLength);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webbrowser);

        //mUrl = getIntent().getStringExtra(KEY_URL);
        mUrl = "http://m.lyjjr.cn:8080";
        LogUtil.d(TAG, "mUrl:" + mUrl);
        if (TextUtils.isEmpty(mUrl)) {
            finish();
        }
        try {
            String urlPath = new URL(mUrl).getPath();
            TAG = urlPath;
            //TODO need to optimize;标示如果是我们的app，浏览活动界面不需要弹出下载app的提示
            if (!TextUtils.isEmpty(urlPath)) {
                //简单判断path是否包含参数
                if (mUrl.contains("?")) {
                    mUrl += "&view_from=app";
                } else {
                    mUrl += "?view_from=app";
                }
                LogUtil.d(TAG, "url has modefied," + mUrl);
            }
        } catch (MalformedURLException e) {
            LogUtil.e(TAG, "onCreate", e);
        }
        if (TextUtils.isEmpty(TAG)) {
            TAG = "WebBrowserActivity";
        }

        mProgressBar = (ProgressBar) findViewById(R.id.web_loading_pb);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mWv = (WebView) findViewById(R.id.browser_wv);
        WebSettings ws = mWv.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDisplayZoomControls(false);

        mWv.setWebChromeClient(mWebChromeClient);
        if (mWebViewClient == null) {
            mWebViewClient = new WebViewClient() {
                private boolean hasError = false;

                @Override
                public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                    return super.shouldOverrideKeyEvent(view, event);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    LogUtil.d(TAG, "override url:" + url);
                    if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:")) {
                        IntentHelper.browserView(WebBrowserActivity.this, url);
                    } /*else if (url.startsWith(Config.SCHEME + ":")) {
                        IntentHelper.parseMySchemeUri(WebBrowserActivity.this, url);
                    } */ else {
                        view.loadUrl(url, PARAMETER);
                    }
                    return true;
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    super.onReceivedSslError(view, handler, error);
                    LogUtil.e(TAG, "onReceivedSslError");
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    LogUtil.i(TAG, "onPageStarted---url:" + url);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    LogUtil.i(TAG, "onPageFinished---url:" + url);
                    if (failedView != null) {
                        if (!hasError && failedView.isShown()) {
                            failedView.setVisibility(View.GONE);
                        }
                    }
                    if (hasError) {
                        hasError = false;
                    }
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    LogUtil.e(TAG, "onReceivedError---errorCode:" + errorCode + "//description:" + description
                            + "//failingUrl:" + failingUrl);
                    hasError = true;
                    if (failedView == null) {
                        failedView = findViewById(R.id.net_error);
                        failedView.setOnClickListener(WebBrowserActivity.this);
                    }
                    failedView.setTag(failingUrl);
                    failedView.setVisibility(View.VISIBLE);
                }
            };
        }
        mWv.setWebViewClient(mWebViewClient);
        mWv.setDownloadListener(mWvDlListener);

        //seted listener must be before loadUrl;
        //getTitleLeftView().setOnClickListener(this);
        //getTitleRightView().setOnClickListener(this);

        //load url
        checkStartLoadUrl();

        new UmengMng(this).forceUpdate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWv != null) {
            mWv.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (mWv != null && mWv.canGoBack()) {
            mWv.goBack();
        } else {
            if ((System.currentTimeMillis() - mTempExitTime) < Config.EXIT_INTERVAL) {
                //you can do something before exit;
            /*Umeng first called before Process.kill,System.exit...*/
                //MobclickAgent.onKillProcess(Context context)
                super.onBackPressed();
            } else {
                mTempExitTime = System.currentTimeMillis();
                if (mToast == null) {
                    mToast = Toast.makeText(this, getString(R.string.exit_toast_msg, getString(R.string.app_name)), Toast.LENGTH_SHORT);
                }
                mToast.show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.net_error:
                mWv.reload();
                break;
        }
    }

    private void checkStartLoadUrl() {
        //set url cookie
        if (getIntent().getBooleanExtra(KEY_IS_NEED_LOGIN, false)) {
            /*if (AppHelper.isLogin(this)) {
                AppHelper.setCookie(mUrl);
                mWv.loadUrl(mUrl, PARAMETER);
            } else {
                LogUtil.w(TAG, "are you logined?");
            }*/
        } else {
            mWv.loadUrl(mUrl, PARAMETER);
        }
    }

}
