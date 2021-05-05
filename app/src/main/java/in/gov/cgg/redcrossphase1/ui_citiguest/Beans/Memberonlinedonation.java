package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Memberonlinedonation {

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("donatedDistrict")
    @Expose
    private String donatedDistrict;

    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("donationAmount")
    @Expose
    private String donationAmount;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mandal")
    @Expose
    private String mandal;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(String donationAmount) {
        this.donationAmount = donationAmount;
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

    public String getDonatedDistrict() {
        return donatedDistrict;
    }

    public void setDonatedDistrict(String donatedDistrict) {
        this.donatedDistrict = donatedDistrict;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
