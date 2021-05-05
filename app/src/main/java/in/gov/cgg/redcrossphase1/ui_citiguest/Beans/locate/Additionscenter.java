package in.gov.cgg.redcrossphase1.ui_citiguest.Beans.locate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Additionscenter {

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
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lat")
    @Expose
    private String lat;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocateFlag() {
        return locateFlag;
    }

    public void setLocateFlag(String locateFlag) {
        this.locateFlag = locateFlag;
    }
}
