package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import in.gov.cgg.redcrossphase1.R;


public class LocateMapAdaptor implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public LocateMapAdaptor(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.locate_marker_layout, null);

        TextView tv_crl = view.findViewById(R.id.tv_crl);
        final TextView tv_ctc = view.findViewById(R.id.tv_ctc);
        final LinearLayout ll_ctc = view.findViewById(R.id.ll_ctc);

        tv_crl.setText(marker.getTitle());

        if (marker.getTitle().contains("\n")) {
            final String[] strings = marker.getTitle().split("\n");

            tv_crl.setText(strings[0].concat("\n").concat(strings[1]));
            tv_ctc.setText(strings[2]);
            if (!strings[2].equals("0")) {
                String[] ctc = strings[2].split(":");

                if (ctc[1] != null && ctc[1].length() >= 10) {
                    ll_ctc.setVisibility(View.VISIBLE);
                } else {
                    ll_ctc.setVisibility(View.GONE);
                }

            }
        }
        return view;

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;

    }


}