package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Membership {

    @SerializedName("enrollmentStartDate")
    @Expose
    private String enrollmentStartDate;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("memberShipType")
    @Expose
    private String memberShipType;
    @SerializedName("memberShipID")
    @Expose
    private Integer memberShipID;
    @SerializedName("memberID")
    @Expose
    private String memberID;

    public String getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    public void setEnrollmentStartDate(String enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMemberShipType() {
        return memberShipType;
    }

    public void setMemberShipType(String memberShipType) {
        this.memberShipType = memberShipType;
    }

    public Integer getMemberShipID() {
        return memberShipID;
    }

    public void setMemberShipID(Integer memberShipID) {
        this.memberShipID = memberShipID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }
}
