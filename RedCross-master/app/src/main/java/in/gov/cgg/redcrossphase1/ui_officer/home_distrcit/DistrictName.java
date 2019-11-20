package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictName {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
