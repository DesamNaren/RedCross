package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.Language;

public class LangSpinnerCustomAdapter extends BaseAdapter implements SpinnerAdapter {

    final LayoutInflater inflater;
    private final List<Language> arrayList;
    Context activity;
    String LanguageShortCode = "";
    String LanguageShortid = "";


    public LangSpinnerCustomAdapter(List<Language> langBeanList, Context applicationContext) {
        this.arrayList = langBeanList;
        activity = applicationContext;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    public int getCount() {
        return arrayList.size();
    }

    public Object getItem(int i) {
        return arrayList.get(i);
    }

    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
     /*   TextView txt = new TextView(activity);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(arrayList.get(position).getSUBCATEGORYNAME());*/
//        txt.setTextColor(Color.parseColor("#ffffff"));


        convertView = inflater.inflate(R.layout.custom_spinner, null);


        TextView txt = convertView.findViewById(R.id.name);

        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(arrayList.get(position).getLanguageName());


        return txt;
    }

    public View getView(int i, View convertView, ViewGroup viewgroup) {
    /*    TextView txt = new TextView(activity);
        txt.setGravity(Gravity.LEFT);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
//        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
        txt.setText(arrayList.get(i).getSUBCATEGORYNAME());
        txt.setTextColor(Color.parseColor("#000000"));*/

        convertView = inflater.inflate(R.layout.custom_spinner, null);

        TextView txt = convertView.findViewById(R.id.name);

        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(arrayList.get(i).getLanguageName());

       /* txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageShortCode = arrayList.get(0).getLanguageShortCode();
                LanguageShortid = arrayList.get(0).getLanguageId();

                Log.d("LID",LanguageShortCode+"==="+LanguageShortid);

            }
        });*/

        return txt;
    }

}
