package in.gov.cgg.redcrossphase1.ui_officer;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;

public class TCFragment extends Fragment {


    private TextView tv_first;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tc, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Terms & conditions");

        tv_first = root.findViewById(R.id.text1);

        String longDescription = "Fee once paid will not be given refund. Candidate should go through the Detailed Notification and ensure the eligibility before making payment.\n" + "\n" +
                "When a candidate makes double payment by mistake, refund can be considered only on bringing it to the notice of RED CROSS by mail within 7 days of making such payment. Any kind of requests beyond this 7 days period will not be entertained. The refund process via same source of payments made in such cases will be initiated in Three to Five working days after receiving the refund request in all eligible cases of refund. This clause is applicable only in case of multiple payments by a candidate for the notification.\n" + "\n" +
                "During the process of making payment , by any chance the amount is deducted and the same has not reached RED CROSS account is automatically refunded by the Payment gateway provider on bringing the issue to the notice of Payment Gateway service provider.\n";

        String[] arr = longDescription.split("\n");

        int bulletGap = (int) dp(10);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String line = arr[i];
            SpannableString ss = new SpannableString(line);
            ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(ss);

            //avoid last "\n"
            if (i + 1 < arr.length)
                ssb.append("\n");

        }

        tv_first.setText(ssb);
        return root;

    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

}
