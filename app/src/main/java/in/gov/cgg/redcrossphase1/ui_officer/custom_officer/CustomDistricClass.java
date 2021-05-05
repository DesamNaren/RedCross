package in.gov.cgg.redcrossphase1.ui_officer.custom_officer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import in.gov.cgg.redcrossphase1.viewmodels.AllDistrictsViewModel;

public class CustomDistricClass implements ViewModelProvider.Factory {
    private final String vtype;
    private final Context context;


    public CustomDistricClass(FragmentActivity activity, String vtype) {
        this.context = activity;
        this.vtype = vtype;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (vtype.equalsIgnoreCase("d")) {
            return (T) new AllDistrictsViewModel(context);
        }
        return null;
    }
}
