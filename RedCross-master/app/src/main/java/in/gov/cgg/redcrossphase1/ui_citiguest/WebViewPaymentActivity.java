package in.gov.cgg.redcrossphase1.ui_citiguest;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class WebViewPaymentActivity extends AppCompatActivity {
    String postData = null;
    WebView mWebView;

    String FinalURL;
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
                FinalURL = url;
                Log.e("TAG", "==== " + url);
                if (url.contains("  ")) {

                    //flag=true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //reDirect to membership id card class
                        }
                    }, 1000);

                }

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

            }
        });

        GlobalDeclaration.encrpyt = GlobalDeclaration.encrpyt.replace("|", "!");

        String value = GlobalDeclaration.encrpyt;
        String url = GlobalDeclaration.Paymenturl + "?msg=" + value;
        Log.e("TAG", "==== " + url);// http://uat2.cgg.gov.in:8081/redcrosspayment/proceedMob?msg=IRCSTS!7507!NA!1!NA!NA!NA!INR!NA!R!ircsts!NA!NA!F!9554949495!fmmhfhtddhy!11-12-2019!11-12-2019!AMR20197507!NA!NA!http://uat2.cgg.gov.in:8081/redcross/getPaymentResponse
        mWebView.loadUrl(url);

        WebSettings webSettings1 = mWebView.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {

                mWebView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onBackPressed() {


        if (FinalURL != " ") {
            final PrettyDialog dialog = new PrettyDialog(this);
            dialog
                    .setTitle("Red cross")
                    .setMessage("Do you want to cancel payment process?")
                    .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                    .addButton("Yes", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                        @Override
                        public void onClick() {
                            dialog.dismiss();
                            startActivity(new Intent(WebViewPaymentActivity.this, MembershipRegFormActivity.class));
                            finish();
                        }
                    })
                    .addButton("No", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                        @Override
                        public void onClick() {
                            dialog.dismiss();
                            // Toast.makeText(OfficerMainActivity.this, "Cancel selected", Toast.LENGTH_SHORT).show();
                        }
                    });

            dialog.show();
        } else {
            //reDirect to membership id card class
        }
    }

}