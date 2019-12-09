package in.gov.cgg.redcrossphase1.ui_citiguest;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.gov.cgg.redcrossphase1.R;

public class WebViewPaymentActivity extends AppCompatActivity {
    String postData = null;
    WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_webview);
        mWebView = findViewById(R.id.webviewid);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //setProgressBarVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("paymentResponsePage")) {

                    //flag=true;
                  /*  new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Paymentgateway.this, SuccessPayment.class));
                        }
                    }, 1000);*/

                }

                //setProgressBarVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //setProgressBarVisibility(View.GONE);
            }
        });
        try {
            String value = "IRCSTS|67766777|NA|1.00|NA|NA|NA|INR|NA|R|ircsts|NA|NA|F|9999999999|Test Name|11-11-1990|11-11-2019|JRC2019112|txtadditional6|txtadditional7|https://10.2.9.85:2019/redcross/getPaymentResponse";
            String url = "http://10.2.9.95:4455/redcrosspayment/proceed?msg=" + URLEncoder.encode(value, "UTF-8");
            mWebView.loadUrl(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        WebSettings webSettings1 = mWebView.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        // mywebview.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {

                mWebView.setVisibility(View.VISIBLE);
            }
        });

    }
}