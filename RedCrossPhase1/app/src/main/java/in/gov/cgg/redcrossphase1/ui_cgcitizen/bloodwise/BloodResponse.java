package in.gov.cgg.redcrossphase1.ui_cgcitizen.bloodwise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodResponse {
    @SerializedName("bloodGroups")
    @Expose
    public List<BloodGroups> bloodGroups;

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
