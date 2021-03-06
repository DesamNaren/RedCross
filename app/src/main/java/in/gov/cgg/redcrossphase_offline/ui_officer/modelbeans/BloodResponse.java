package in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodResponse {
    @SerializedName("bloodGroups")
    @Expose
    private List<BloodGroups> bloodGroups = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<BloodGroups> getBloodGroups() {
        return bloodGroups;
    }

    public void setBloodGroups(List<BloodGroups> bloodGroups) {
        this.bloodGroups = bloodGroups;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
