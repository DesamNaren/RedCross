package in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeNurseReqRequest {
    @SerializedName("applicantName")
    @Expose
    private String applicantName;
    @SerializedName("onBehalfOf")
    @Expose
    private String onBehalfOf;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("patientAge")
    @Expose
    private String patientAge;
    @SerializedName("natureOfDisability")
    @Expose
    private String natureOfDisability;
    @SerializedName("serviceStartDate")
    @Expose
    private String serviceStartDate;
    @SerializedName("serviceEndDate")
    @Expose
    private String serviceEndDate;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getOnBehalfOf() {
        return onBehalfOf;
    }

    public void setOnBehalfOf(String onBehalfOf) {
        this.onBehalfOf = onBehalfOf;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getNatureOfDisability() {
        return natureOfDisability;
    }

    public void setNatureOfDisability(String natureOfDisability) {
        this.natureOfDisability = natureOfDisability;
    }

    public String getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(String serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public String getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(String serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
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

}
