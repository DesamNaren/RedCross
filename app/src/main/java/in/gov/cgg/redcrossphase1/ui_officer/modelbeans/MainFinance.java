package in.gov.cgg.redcrossphase1.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainFinance {
    @SerializedName("finYears")
    @Expose
    private List<FinYear> finYears = null;

    public List<FinYear> getFinYears() {
        return finYears;
    }

    public void setFinYears(List<FinYear> finYears) {
        this.finYears = finYears;
    }
}


