package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Upcoming_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Upcoming_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Upcoming_fragment extends Fragment {
    private WebView wb;

    //private TextView tv_first;
    private ProgressDialog pd;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_upcoming_fragment, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Upcoming Events");

        //tv_first = root.findViewById(R.id.text1);
        wb = root.findViewById(R.id.help_webview);


        String url = BuildConfig.SERVER_URL + "events";
        //   String url = "http://10.2.9.81:8081/redcross/trainingDetails";
        //String url = "http://mobileapps.cgg.gov.in/privacy.html";
        //String url = "http://qa2.cgg.gov.in:8081/redcross/policy";


        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...Please wait ");


        pd.show();
        wb.setWebViewClient(new Upcoming_fragment.MyWebViewHelpClient());
        wb.getSettings().setJavaScriptEnabled(true);
//        wb.getSettings().setBuiltInZoomControls(true);
        wb.loadUrl(url);


//        String longDescription = "Fee once paid will not be given refund. Candidate should go through the Detailed Notification and ensure the eligibility before making payment.\n" +"\n"+
//                "When a candidate makes double payment by mistake, refund can be considered only on bringing it to the notice of RED CROSS by mail within 7 days of making such payment. Any kind of requests beyond this 7 days period will not be entertained. The refund process via same source of payments made in such cases will be initiated in Three to Five working days after receiving the refund request in all eligible cases of refund. This clause is applicable only in case of multiple payments by a candidate for the notification.\n" +"\n"+
//                "During the process of making payment , by any chance the amount is deducted and the same has not reached RED CROSS account is automatically refunded by the Payment gateway provider on bringing the issue to the notice of Payment Gateway service provider.\n" ;
//
//        String arr[] = longDescription.split("\n");
//
//        int bulletGap = (int) dp(10);
//
//        SpannableStringBuilder ssb = new SpannableStringBuilder();
//        for (int i = 0; i < arr.length; i++) {
//            String line = arr[i];
//            SpannableString ss = new SpannableString(line);
//            ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ssb.append(ss);
//
//            //avoid last "\n"
//            if(i+1<arr.length)
//                ssb.append("\n");
//
//        }
//
//        tv_first.setText(ssb);
        return root;

    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

    public class MyWebViewHelpClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if (!pd.isShowing()) {
                pd.show();
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }

//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
//        inflater.inflate(R.menu.activity_backpress, menu);
//        MenuItem logout = menu.findItem(R.id.logout);
//        logout.setIcon(R.drawable.ic_home_white_48dp);
//
//        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                startActivity(new Intent(getActivity(), CitiGuestMainActivity.class));
//                return false;
//            }
//        });
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search:
//                // Not implemented here
//                return false;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


}