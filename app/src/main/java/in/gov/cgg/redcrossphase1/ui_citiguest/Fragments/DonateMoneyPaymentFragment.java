package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;

public class DonateMoneyPaymentFragment extends Fragment {

    CustomProgressDialog progressDialog;
    LinearLayout mainLayoutPayment;
    WebView mWebView;
    String FinalURL;
    private FragmentActivity c;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        View root = inflater.inflate(R.layout.activity_main_webview, container, false);
        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.online_donation_payment));


        c = getActivity();
        findViews(root);

        mWebView = root.findViewById(R.id.webviewid);
        progressDialog = new CustomProgressDialog(getActivity());

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //setProgressBarVisibility(View.VISIBLE);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                FinalURL = url;
                Log.e("TAG", "==== " + url);
                if (url.contains(BuildConfig.SERVER_PAYMENT_URL_DONATION.concat("0300/Success/"))) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            paymentSuccess();
                        }
                    }, 2000);

                } else if (url.contains(BuildConfig.SERVER_PAYMENT_URL_DONATION.concat("0399"))
                        || url.contains(BuildConfig.SERVER_PAYMENT_URL_DONATION.concat("NA"))
                        || url.contains(BuildConfig.SERVER_PAYMENT_URL_DONATION.concat("0002"))
                        || url.contains(BuildConfig.SERVER_PAYMENT_URL_DONATION.concat("0001"))
                        || url.contains(BuildConfig.SERVER_PAYMENT_URL_DONATION.concat("na"))) {
                    paymentFailed();

                }
                progressDialog.dismiss();

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


    private void paymentSuccess() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(getResources().getString(R.string.success_donated
        ));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Fragment fragment = new DonateMoneyFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, fragment, DonateMoneyFragment.class.getSimpleName());
                        transaction.addToBackStack(null);
                        transaction.commitAllowingStateLoss();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void paymentFailed() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(getResources().getString(R.string.failed_trtagain));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), CitiGuestMainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
