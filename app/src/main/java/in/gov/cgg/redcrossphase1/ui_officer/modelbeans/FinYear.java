package in.gov.cgg.redcrossphase1.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinYear {

    @SerializedName("finYear")
    @Expose
    private String finYear;
    @SerializedName("finYearId")
    @Expose
    private Integer finYearId;

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public Integer getFinYearId() {
        return finYearId;
    }

    public void setFinYearId(Integer finYearId) {
        this.finYearId = finYearId;
    }

}
