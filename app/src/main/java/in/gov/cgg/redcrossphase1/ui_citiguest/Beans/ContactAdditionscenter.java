package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactAdditionscenter {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("locateFlag")
    @Expose
    private String locateFlag;
    @SerializedName("distirctid")
    @Expose
    private String distirctid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocateFlag() {
        return locateFlag;
    }

    public void setLocateFlag(String locateFlag) {
        this.locateFlag = locateFlag;
    }

    public String getDistirctid() {
        return distirctid;
    }

    public void setDistirctid(String distirctid) {
        this.distirctid = distirctid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
