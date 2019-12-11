package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeNurseReqResponse {
    @SerializedName("nurseId")
    @Expose
    private String nurseId;
    @SerializedName("SaveStatus")
    @Expose
    private String saveStatus;

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }
}
