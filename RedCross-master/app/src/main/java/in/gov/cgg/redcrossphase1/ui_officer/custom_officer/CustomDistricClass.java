package in.gov.cgg.redcrossphase1.ui_officer.custom_officer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.AllDistrictsViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.BloodwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.DaywiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.DistrictViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.GenderwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.GovtPvtViewModel;

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
        } else if (vtype.equalsIgnoreCase("alldistrict")) {
            return (T) new AllDistrictsViewModel(context);
        } else if (vtype.equalsIgnoreCase("day")) {
            return (T) new DaywiseViewModel(context);
        }


        return null;
    }
}
