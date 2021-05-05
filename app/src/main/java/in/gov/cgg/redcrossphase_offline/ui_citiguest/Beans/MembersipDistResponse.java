package in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembersipDistResponse {

    @SerializedName("districtID")
    @Expose
    private Integer districtID;
    @SerializedName("districtCode")
    @Expose
    private String districtCode;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("delFlag")
    @Expose
    private String delFlag;
    @SerializedName("states")
    @Expose
    private States states;
    @SerializedName("shortForm")
    @Expose
    private String shortForm;

    public Integer getDistrictID() {
        return districtID;
    }

    public void setDistrictID(Integer districtID) {
        this.districtID = districtID;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    public String getShortForm() {
        return shortForm;
    }

    public void setShortForm(String shortForm) {
        this.shortForm = shortForm;
    }

}





