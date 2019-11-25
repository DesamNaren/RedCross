package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;


public class HistoryFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tc, container, false);

        Objects.requireNonNull(getActivity()).setTitle("History");

        TextView tv_first = root.findViewById(R.id.text1);

        String longDescription = "Young Swiss businessman, Jean Henry Dunant was appalled by the condition of the wounded he happened to see in the battle field of Solferino, Italy in 1859 during the Franco - Austrian war. He immediately arranged relief services with the help of the local community. As a result he wrote the book 'Memory of Solferino' suggesting that a neutral organization be established to aid the wounded soldiers in times of war. Just a year after the release of this book, an international conference was convened in Geneva to consider the suggestions of Henry dunant and thus the Red Cross Movement was born. International Red Cross Movement was established by Geneva Convention of 1864. The name and the emblem of the movement are derived from the reversal of the Swiss national flag, to honour the country in which Red Cross was founded. 1863 -An international conference met in Geneva to try and find means of remedying this ineffectiveness, it adopted a Red Cross on white background as a distinctive sign for relief societies for wounded soldiers - the future National Red Cross and Red Crescent Societies. In the First Geneva Convention, the Red Cross on white background was officially recognized as the distinctive sign of medical services of armed sources. 1876 - During the Russo Turkish war, the Ottoman Empire decided to use a Red Crescent on a white background. Egypt opted Red Crescent, Persia chose Red Lion, and these exceptional signs were written into the 1929 convention. 1882 -the International Federation of Red Cross and Red Crescent societies adopted as its emblem the Red Cross and Red Crescent on white background. 1949 -Article 38 of the first Geneva Convention of 1949 confirmed the emblem of the Red Cross, Red Crescent, Red Lion and Sun as the protective signs for any army medical services. 1980 - Islamic republic of Iran decided to give up Red Lion and Sun and use the Red Crescent in its place";

        String[] arr = longDescription.split("\n");

        int bulletGap = (int) dp(10);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String line = arr[i];
            SpannableString ss = new SpannableString(line);
            // ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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