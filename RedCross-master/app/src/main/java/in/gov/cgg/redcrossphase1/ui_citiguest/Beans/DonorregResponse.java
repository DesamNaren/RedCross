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

}
