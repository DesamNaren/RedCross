package in.gov.cgg.redcrossphase1.ui_officer.bloodwise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloodGroups {

    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("count")
    @Expose
    private String count;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
