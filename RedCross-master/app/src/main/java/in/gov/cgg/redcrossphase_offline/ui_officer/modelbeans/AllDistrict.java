package in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDistrict {
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("Total")
    @Expose
    private Integer total;
    @SerializedName("Membership")
    @Expose
    private Integer membership;
    @SerializedName("JRC")
    @Expose
    private Integer jRC;
    @SerializedName("YRC")
    @Expose
    private Integer yRC;

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMembership() {
        return membership;
    }

    public void setMembership(Integer membership) {
        this.membership = membership;
    }

    public Integer getJRC() {
        return jRC;
    }

    public void setJRC(Integer jRC) {
        this.jRC = jRC;
    }

    public Integer getYRC() {
        return yRC;
    }

    public void setYRC(Integer yRC) {
        this.yRC = yRC;
    }
}
