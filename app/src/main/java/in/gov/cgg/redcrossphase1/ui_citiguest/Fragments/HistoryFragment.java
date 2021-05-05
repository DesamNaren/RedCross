package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;


public class HistoryFragment extends Fragment {
    LinearLayout ll_history;
    int selectedThemeColor = -1;

    private WebView wb;

    //private TextView tv_first;
    private ProgressDialog pd;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        View root = inflater.inflate(R.layout.fragment_pp, container, false);


        //tv_first = root.findViewById(R.id.text1);
        wb = root.findViewById(R.id.help_webview);


        String url = getResources().getString(R.string.historyurl);


        pd = new ProgressDialog(getActivity());
        pd.setMessage(getResources().getString(R.string.loading_wait_msg));


        pd.show();
        wb.setWebViewClient(new HistoryFragment.MyWebViewHelpClient());
        wb.getSettings().setJavaScriptEnabled(true);
        wb.loadUrl(url);


        return root;

    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_backpress, menu);
        MenuItem logout = menu.findItem(R.id.home);
        logout.setIcon(R.drawable.ic_home_white_48dp);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), CitiGuestMainActivity.class));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
}