package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipVillagesResponse {
    @SerializedName("villageID")
    @Expose
    private Integer villageID;
    @SerializedName("villageCode")
    @Expose
    private Object villageCode;
    @SerializedName("villageName")
    @Expose
    private String villageName;
    @SerializedName("delFlag")
    @Expose
    private String delFlag;

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getVillageID() {
        return villageID;
    }

    public void setVillageID(Integer villageID) {
        this.villageID = villageID;
    }

    public Object getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(Object villageCode) {
        this.villageCode = villageCode;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

}
