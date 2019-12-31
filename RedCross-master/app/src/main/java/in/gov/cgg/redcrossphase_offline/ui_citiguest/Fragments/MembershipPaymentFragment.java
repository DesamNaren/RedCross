package in.gov.cgg.redcrossphase_offline.ui_citiguest.Fragments;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.Objects;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.utils.CustomProgressDialog;

public class MembershipPaymentFragment extends Fragment {

    CustomProgressDialog progressDialog;
    LinearLayout mainLayoutPayment;
    private FragmentActivity c;
    WebView mWebView;
    String FinalURL;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.activity_main_webview, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Membership Payment");


        c = getActivity();
        findViews(root);

        mWebView = root.findViewById(R.id.webviewid);


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

        return root;
    }

    private void findViews(View root) {
        mainLayoutPayment = root.findViewById(R.id.mainLayoutPayment);
    }
}
