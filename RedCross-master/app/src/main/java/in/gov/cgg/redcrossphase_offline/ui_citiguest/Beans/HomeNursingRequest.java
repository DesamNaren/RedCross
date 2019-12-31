package in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeNursingRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fatherName")
    @Expose
    private String fatherName;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("married")
    @Expose
    private String married;
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
    @SerializedName("instituteName")
    @Expose
    private String instituteName;
    @SerializedName("prevWorkYears")
    @Expose
    private String prevWorkYears;
    @SerializedName("photoPath")
    @Expose
    private String photoPath;

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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getPrevWorkYears() {
        return prevWorkYears;
    }

    public void setPrevWorkYears(String prevWorkYears) {
        this.prevWorkYears = prevWorkYears;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

}