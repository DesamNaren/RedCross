package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonorregResponse {
    @SerializedName("bloodDonorForm")
    @Expose
    private BloodDonorForm bloodDonorForm;
    @SerializedName("SaveStatus")
    @Expose
    private String saveStatus;

    public BloodDonorForm getBloodDonorForm() {
        return bloodDonorForm;
    }

    public void setBloodDonorForm(BloodDonorForm bloodDonorForm) {
        this.bloodDonorForm = bloodDonorForm;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public class BloodDonorForm {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("fatherName")
        @Expose
        private String fatherName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("phoneNo")
        @Expose
        private String phoneNo;
        @SerializedName("office")
        @Expose
        private String office;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("donateType")
        @Expose
        private String donateType;
        @SerializedName("dateOfBirth")
        @Expose
        private String dateOfBirth;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("bloodGroup")
        @Expose
        private String bloodGroup;
        @SerializedName("education")
        @Expose
        private String education;
        @SerializedName("occupation")
        @Expose
        private String occupation;
        @SerializedName("married")
        @Expose
        private String married;
        @SerializedName("prevDonationDate")
        @Expose
        private String prevDonationDate;
        @SerializedName("willingToDonateYearly")
        @Expose
        private Boolean willingToDonateYearly;
        @SerializedName("noOfPrevDonations")
        @Expose
        private Object noOfPrevDonations;
        @SerializedName("districts")
        @Expose
        private Integer districts;
        @SerializedName("mandals")
        @Expose
        private Integer mandals;
        @SerializedName("village")
        @Expose
        private Integer village;
        @SerializedName("pincode")
        @Expose
        private String pincode;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getOffice() {
            return office;
        }

        public void setOffice(String office) {
            this.office = office;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDonateType() {
            return donateType;
        }

        public void setDonateType(String donateType) {
            this.donateType = donateType;
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

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
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

        public String getPrevDonationDate() {
            return prevDonationDate;
        }

        public void setPrevDonationDate(String prevDonationDate) {
            this.prevDonationDate = prevDonationDate;
        }

        public Boolean getWillingToDonateYearly() {
            return willingToDonateYearly;
        }

        public void setWillingToDonateYearly(Boolean willingToDonateYearly) {
            this.willingToDonateYearly = willingToDonateYearly;
        }

        public Object getNoOfPrevDonations() {
            return noOfPrevDonations;
        }

        public void setNoOfPrevDonations(Object noOfPrevDonations) {
            this.noOfPrevDonations = noOfPrevDonations;
        }

        public Integer getDistricts() {
            return districts;
        }

        public void setDistricts(Integer districts) {
            this.districts = districts;
        }

        public Integer getMandals() {
            return mandals;
        }

        public void setMandals(Integer mandals) {
            this.mandals = mandals;
        }

        public Integer getVillage() {
            return village;
        }

        public void setVillage(Integer village) {
            this.village = village;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

    }
}
