package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyMembership {


    @SerializedName("blooddonor")
    @Expose
    private List<MyBlooddonor> blooddonor = null;

    @SerializedName("memberships")
    @Expose
    private List<Membership> memberships = null;

    public List<MyBlooddonor> getBlooddonor() {
        return blooddonor;
    }

    public void setBlooddonor(List<MyBlooddonor> blooddonor) {
        this.blooddonor = blooddonor;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }


}

