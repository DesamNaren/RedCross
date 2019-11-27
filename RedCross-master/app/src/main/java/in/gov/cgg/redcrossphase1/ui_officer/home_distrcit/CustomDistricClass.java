package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtPvtViewModel;

public class CustomDistricClass implements ViewModelProvider.Factory {
    String vtype;
    private Context context;


    public CustomDistricClass(FragmentActivity activity, String vtype) {
        this.context = activity;
        this.vtype = vtype;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (vtype.equalsIgnoreCase("district")) {
            return (T) new DistrictViewModel(context);
        } else if (vtype.equalsIgnoreCase("age")) {
            return (T) new AgewiseViewModel(context);
        } else if (vtype.equalsIgnoreCase("blood")) {
            return (T) new BloodwiseViewModel(context);
        } else if (vtype.equalsIgnoreCase("gender")) {
            return (T) new GenderwiseViewModel(context);
        } else if (vtype.equalsIgnoreCase("gvtpvt")) {
            return (T) new GovtPvtViewModel(context);
        }


        return null;
    }
}
