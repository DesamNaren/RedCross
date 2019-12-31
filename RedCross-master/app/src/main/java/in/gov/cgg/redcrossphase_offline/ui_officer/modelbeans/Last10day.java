package in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Last10day {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("gov")
    @Expose
    private String gov;
    @SerializedName("pvt")
    @Expose
    private String pvt;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGov() {
        return gov;
    }

    public void setGov(String gov) {
        this.gov = gov;
    }

    public String getPvt() {
        return pvt;
    }

    public void setPvt(String pvt) {
        this.pvt = pvt;
    }


}
