package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyBlooddonor {
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("prevDonationDate")
    @Expose
    private String prevDonationDate;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("donateType")
    @Expose
    private String donateType;
    @SerializedName("office")
    @Expose
    private String office;
    @SerializedName("willingToDonateYearly")
    @Expose
    private Boolean willingToDonateYearly;
    @SerializedName("isMarried")
    @Expose
    private String isMarried;
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mandal")
    @Expose
    private String mandal;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPrevDonationDate() {
        return prevDonationDate;
    }

    public void setPrevDonationDate(String prevDonationDate) {
        this.prevDonationDate = prevDonationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDonateType() {
        return donateType;
    }

    public void setDonateType(String donateType) {
        this.donateType = donateType;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Boolean getWillingToDonateYearly() {
        return willingToDonateYearly;
    }

    public void setWillingToDonateYearly(Boolean willingToDonateYearly) {
        this.willingToDonateYearly = willingToDonateYearly;
    }

    public String getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMandal() {
        return mandal;
    }

    public void setMandal(String mandal) {
        this.mandal = mandal;
    }
}
