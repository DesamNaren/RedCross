package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembershipTypeBeansClass {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("membershipType")
    @Expose
    private String membershipType;
    @SerializedName("membTypeShortName")
    @Expose
    private String membTypeShortName;
    @SerializedName("fees")
    @Expose
    private Integer fees;
    @SerializedName("perAnum")
    @Expose
    private Boolean perAnum;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembTypeShortName() {
        return membTypeShortName;
    }

    public void setMembTypeShortName(String membTypeShortName) {
        this.membTypeShortName = membTypeShortName;
    }

    public Integer getFees() {
        return fees;
    }

    public void setFees(Integer fees) {
        this.fees = fees;
    }

    public Boolean getPerAnum() {
        return perAnum;
    }

    public void setPerAnum(Boolean perAnum) {
        this.perAnum = perAnum;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
