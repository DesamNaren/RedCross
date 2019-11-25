package in.gov.cgg.redcrossphase1.ui_citiguest;

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


public class SevenFundamentalFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tc, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Seven Fundamental Principles");

        TextView tv_first = root.findViewById(R.id.text1);

        String longDescription = "Humanity\n" +
                "The International Red Cross and Red Crescent Movement, born of a desire to bring assistance without discrimination to the wounded on the battlefield, endeavors, in its international and national capacity, to prevent and alleviate human suffering wherever it may be found. Its purpose is to protect life and health and to ensure respect for the human being. It promotes mutual understanding, friendship, cooperation and lasting peace amongst all peoples.\n" +
                "\n" +
                "Impartiality\n" +
                "It makes no discrimination as to nationally, race, religious beliefs, class or political opinions. It endeavors to relieve the suffering of individuals, being solely by their needs, and to give priority to the most urgent cases of distress.\n" +
                "\n" +
                "Neutrality\n" +
                "In order to enjoy the confidence of all, the Movement may not take sides in hostilities or engage in controversies of a political, racial, religious or ideological nature.\n" +
                "\n" +
                "Independence\n" +
                "The Movement is independent. The National Societies, while auxiliaries in the humanitarian services of their governments and subject to the laws of their respective countries, must always maintain their autonomy so that they may be able at all times to act in accordance with the principles of the Movement.\n" +
                "\n" +
                "Voluntary Service\n" +
                "It is voluntary relief movement not prompted in any manner by desire for gain.\n" +
                "\n" +
                "Unity\n" +
                "There can be only one Red Cross Or Red Crescent in any one country. It must be open to all. It must carry on its humanitarian work throughout its territory.\n" +
                "\n" +
                "Universality\n" +
                "The International Red Cross and Red Crescent Movement, in which all societies have equal status and share equal responsibilities and duties in helping each other, is worldwide.";

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