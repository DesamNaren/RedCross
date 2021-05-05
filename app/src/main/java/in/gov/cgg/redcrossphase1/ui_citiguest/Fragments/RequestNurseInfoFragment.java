package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.app.ProgressDialog;
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
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;

import static android.content.Context.MODE_PRIVATE;

public class RequestNurseInfoFragment extends Fragment {

    Button b;
    int selectedThemeColor = -1;
    private WebView wb;
    //private TextView tv_first;
    private ProgressDialog pd;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        View root = inflater.inflate(R.layout.fragment_capacity_buildings, container, false);

        Objects.requireNonNull(getActivity()).setTitle(R.string.request_for_a_home_nurse);


        wb = root.findViewById(R.id.help_webview);
        b = root.findViewById(R.id.btn_upcoming_event);

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            b.setBackgroundColor(getResources().getColor(selectedThemeColor));
        } catch (Exception e) {
            e.printStackTrace();
        }

        b.setText(R.string.request_for_a_home_nurse);


//        String url = "http://uat2.cgg.gov.in:8081/redcross/getHowToPage?type=becomeDonor&reqFrom=Mobile";
        String url = BuildConfig.SERVER_URL + "getHowToPage?type=reqForHomeNurse&reqFrom=Mobile";


        pd = new ProgressDialog(getActivity());
        pd.setMessage(getResources().getString(R.string.loading_wait_msg));


        pd.show();
        wb.setWebViewClient(new RequestNurseInfoFragment.MyWebViewHelpClient());
        wb.getSettings().setJavaScriptEnabled(true);
//        wb.getSettings().setBuiltInZoomControls(true);
        wb.loadUrl(url);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalDeclaration.FARG_TAG = HomeNurseRequest.class.getSimpleName();
                Fragment fragment = new HomeNurseRequest();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, HomeNurseRequest.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });


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
                Fragment fragment = new CitizendashboardFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, CitizendashboardFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
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
