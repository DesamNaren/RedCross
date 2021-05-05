package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import in.gov.cgg.redcrossphase1.R;


public class MapAdaptor implements GoogleMap.InfoWindowAdapter {

    private final Context context;

    public MapAdaptor(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.map_marker_layout, null);

        TextView tv_crl = view.findViewById(R.id.tv_crl);

        tv_crl.setText(marker.getTitle());
        return view;

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;

    }

}
//Reviewd by swapna