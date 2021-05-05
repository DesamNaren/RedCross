package in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatelevelDistrictViewCountResponse {
    @SerializedName("districtChairManName")
    @Expose
    private String districtChairManName;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("Membership")
    @Expose
    private Integer membership;
    @SerializedName("JRC")
    @Expose
    private Integer jRC;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("YRC")
    @Expose
    private Integer yRC;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("Name")
    @Expose
    private String name;

    private Integer totalCounts;

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public String getDistrictChairManName() {
        return districtChairManName;
    }

    public void setDistrictChairManName(String districtChairManName) {
        this.districtChairManName = districtChairManName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYRC() {
        return yRC;
    }

    public void setYRC(Integer yRC) {
        this.yRC = yRC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
