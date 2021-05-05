package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonateMoneyRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("districts")
    @Expose
    private String districts;
    @SerializedName("mandals")
    @Expose
    private String mandals;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("donationAmount")
    @Expose
    private String donationAmount;
    @SerializedName("donationDistrict")
    @Expose
    private String donationDistrict;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("donationAmountInwords")
    @Expose
    private String donationAmountInwords;
    @SerializedName("requestType")
    @Expose
    private String requestType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistricts() {
        return districts;
    }

    public void setDistricts(String districts) {
        this.districts = districts;
    }

    public String getMandals() {
        return mandals;
    }

    public void setMandals(String mandals) {
        this.mandals = mandals;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(String donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getDonationDistrict() {
        return donationDistrict;
    }

    public void setDonationDistrict(String donationDistrict) {
        this.donationDistrict = donationDistrict;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDonationAmountInwords() {
        return donationAmountInwords;
    }

    public void setDonationAmountInwords(String donationAmountInwords) {
        this.donationAmountInwords = donationAmountInwords;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

}
