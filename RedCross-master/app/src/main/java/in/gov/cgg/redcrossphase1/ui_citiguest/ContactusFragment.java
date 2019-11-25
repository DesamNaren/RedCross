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


public class ContactusFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tc, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Contact us");

        TextView tv_first = root.findViewById(R.id.text1);

        String longDescription = "ADILABAD DISTRICT\n" +
                "Indian Red Cross Society,\n" +
                "Adilabad District Branch,\n" +
                "Adilabad 504001\n" +
                "\n" +
                "BHADRADRI KOTTAGUDEM DISTRICT\n" +
                "Indian Red Cross Society,\n" +
                "Old LIC Office Road,\n" +
                "Bhadrachalam, Bhadradri -Kothagudem-507111\n" +
                "\n" +
                "HYDERABAD DISTRICT\n" +
                "Indian Red Cross Society,\n" +
                "District Collectorate Office,\n" +
                "Nampally Station Road, Abids,\n" +
                "HYDERABAD -500001.\n" +
                "\n" +
                "\n" +
                "JAGTIAL DISTRICT\n" +
                "H.No.1-4-181, Balaji Nagar,\n" +
                "Jagityal Dist-505327\n" +
                "\n" +
                "KUMARAM BHEEM ASIFABAD DISTRICT\n" +
                "H.No.1-16-273/6,\n" +
                "Opp: Indian Oil Petrol Pump, Industrial Area,\n" +
                "Sirpur Khagaz Nagar - Komaram Bheem Asifabad Dist-504299\n" +
                "\n" +
                "MAHABUBABAD DISTRICT\n" +
                "D.No.4-4-10/1,U.Town,\n" +
                "Mahaboobabad District.-506101\n" +
                "\n" +
                "MAHABUBNAGAR DISTRICT\n" +
                "H.No.6-1-92/C, Ganesh Nagar,\n" +
                "Near Rto Office, Mahabubnagar-509001\n" +
                "\n" +
                "MANCHERIAL DISTRICT\n" +
                "Blood Bank,I B Chowrasta\n" +
                "Mancherial Dist.-504208\n" +
                "\n" +
                "MEDAK DISTRICT\n" +
                "H.No:- 16-83, Reddy Colony,\n" +
                "Ramayanpet,\n" +
                "Medak District-502101\n" +
                "\n" +
                "MEDCHAL MALKAJGIRI DISTRICT\n" +
                "C/o. Misrilal Mangilal Maternity & Children Hospital Premises, Bowenpally, Sec-bad\n" +
                "\n" +
                "MULUGU DISTRICT\n" +
                "H.No.5-38,\n" +
                "Govindaraopet,(Village & Mandal),\n" +
                "Mulugu District -506344\n" +
                "\n" +
                "NAGAR KURNOOL DISTRICT\n" +
                "DM&HO and Chairman,\n" +
                "Indian Red Cross Society,\n" +
                "DM&HO, Office\n" +
                "Nagarkurnool District-509209\n" +
                "\n" +
                "NARAYANPET DISTRICT\n" +
                "H.No.1-7-48/4,\n" +
                "Teachers Colony,\n" +
                "Narayanapet -509210\n" +
                "\n" +
                "NALGONDA DISTRICT\n" +
                "H.No.5-6-300,\n" +
                "Red Cross Bhavan,\n" +
                "Near Bus Stand,\n" +
                "Nalgonda-508001\n" +
                "\n" +
                "NIRMAL DISTRICT\n" +
                "Plot No.26,\n" +
                "52 G N R Colony,\n" +
                "Nirmal District - 504106\n" +
                "\n" +
                "NIZAMABAD DISTRICT\n" +
                "Behind: Tahasildar Office,\n" +
                "KhaleelWadi,\n" +
                "Nizamabad-503001\n" +
                "\n" +
                "PEDDAPALLY DISTRICT\n" +
                "Office of D.M&H.O,\n" +
                "Railway Station Road,\n" +
                "Peddapally District -505172\n" +
                "\n" +
                "RAJANNA SIRICILLA DISTRICT\n" +
                "H.No.6-7-34, Vidya Nagar,\n" +
                "Siricilla, Rajanna siricilla District - 505301\n" +
                "\n" +
                "RANGA REDDY DISTRICT\n" +
                "Collectorate Campus,\n" +
                "lakdikapool, Hyd\n" +
                "\n" +
                "SANGA REDDY DISTRICT\n" +
                "Opp:Shree Gayathri School,\n" +
                "Behind: I.B.Sanga Reddy,\n" +
                "SANGA REDDY District.-502001\n" +
                "\n" +
                "SURYAPET DISTRICT\n" +
                "H.No.1-9-37/B,\n" +
                "Main Road,\n" +
                "C/o.Bharat Gas Office,\n" +
                "Gopalpuram,\n" +
                "Suryapet District-508213\n" +
                "\n" +
                "WANAPARTHY DISTRICT\n" +
                "R.No:-124, Dist Hospital,\n" +
                "wanaparthy district-509103\n" +
                "\n" +
                "WARANGAL (RURAL) DISTRICT\n" +
                "DM&HO Office,\n" +
                "Warangal (Rural)-506164\n" +
                "\n" +
                "WARANGAL (URBAN) DISTRICT\n" +
                "Opp: District Collect rate,\n" +
                "Subedari, Hanamkonda - 506001\n" +
                "\n" +
                "SIDDIPET DISTRICT\n" +
                "NA\n" +
                "\n" +
                "VIKARABAD DISTRICT\n" +
                "NA";

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