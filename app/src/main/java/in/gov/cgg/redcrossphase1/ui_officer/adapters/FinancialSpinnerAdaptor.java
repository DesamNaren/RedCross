package in.gov.cgg.redcrossphase1.ui_officer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.FinYear;

public class FinancialSpinnerAdaptor extends ArrayAdapter<FinYear> {

    LayoutInflater flater;

    public FinancialSpinnerAdaptor(Activity context, int resouceId, int textviewId, List<FinYear> list) {

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

        FinYear rowItem = getItem(position);

        viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.year_listitems_layout, null, false);

            holder.txtTitle = rowview.findViewById(R.id.title);
            //holder.imageView = (ImageView) rowview.findViewById(R.id.icon);
            rowview.setTag(holder);
        } else {
            holder = (viewHolder) rowview.getTag();
        }
        //holder.imageView.setImageResource(rowItem.getImageId());
        holder.txtTitle.setText(rowItem.getFinYear());

        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
        //ImageView imageView;
    }
}