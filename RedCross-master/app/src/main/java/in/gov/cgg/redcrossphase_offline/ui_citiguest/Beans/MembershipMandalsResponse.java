package in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipMandalsResponse {


    @SerializedName("mandalID")
    @Expose
    private Integer mandalID;
    @SerializedName("mandalCode")
    @Expose
    private Object mandalCode;
    @SerializedName("mandalName")
    @Expose
    private String mandalName;
    @SerializedName("delFlag")
    @Expose
    private String delFlag;


    public Integer getMandalID() {
        return mandalID;
    }

    public void setMandalID(Integer mandalID) {
        this.mandalID = mandalID;
    }

    public Object getMandalCode() {
        return mandalCode;
    }

    public void setMandalCode(Object mandalCode) {
        this.mandalCode = mandalCode;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


}
