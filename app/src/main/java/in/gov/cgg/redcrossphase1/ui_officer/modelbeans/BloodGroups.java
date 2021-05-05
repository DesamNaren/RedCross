package in.gov.cgg.redcrossphase1.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloodGroups {

    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("count")
    @Expose
    private int count;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
