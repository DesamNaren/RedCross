package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitizenDonarRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("married")
    @Expose
    private String married;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("office")
    @Expose
    private String office;
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
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("donateType")
    @Expose
    private String donateType;
    @SerializedName("prevDonationDate")
    @Expose
    private String prevDonationDate;
    @SerializedName("willingToDonateYearly")
    @Expose
    private String willingToDonateYearly;
    @SerializedName("noOfPrevDonations")
    @Expose
    private String noOfPrevDonations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
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

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
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

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDonateType() {
        return donateType;
    }

    public void setDonateType(String donateType) {
        this.donateType = donateType;
    }

    public String getPrevDonationDate() {
        return prevDonationDate;
    }

    public void setPrevDonationDate(String prevDonationDate) {
        this.prevDonationDate = prevDonationDate;
    }

    public String getWillingToDonateYearly() {
        return willingToDonateYearly;
    }

    public void setWillingToDonateYearly(String willingToDonateYearly) {
        this.willingToDonateYearly = willingToDonateYearly;
    }

    public String getNoOfPrevDonations() {
        return noOfPrevDonations;
    }

    public void setNoOfPrevDonations(String noOfPrevDonations) {
        this.noOfPrevDonations = noOfPrevDonations;
    }


}
