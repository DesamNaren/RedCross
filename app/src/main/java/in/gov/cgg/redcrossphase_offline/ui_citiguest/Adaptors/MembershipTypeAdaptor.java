package in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.MembershipDetails_Bean;

public class MembershipTypeAdaptor extends ArrayAdapter<MembershipDetails_Bean> {

    LayoutInflater flater;

    public MembershipTypeAdaptor(Activity context, int resouceId, int textviewId, List<MembershipDetails_Bean> list) {

        super(context, resouceId, textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        MembershipDetails_Bean rowItem = getItem(position);

        MembershipTypeAdaptor.viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new MembershipTypeAdaptor.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.listitems_layout, null, false);

            holder.txtTitle = rowview.findViewById(R.id.title);
            //holder.imageView = (ImageView) rowview.findViewById(R.id.icon);
            rowview.setTag(holder);
        } else {
            holder = (MembershipTypeAdaptor.viewHolder) rowview.getTag();
        }
        //holder.imageView.setImageResource(rowItem.getImageId());
        holder.txtTitle.setText(rowItem.getMembershipType());

        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
        //ImageView imageView;
    }
}