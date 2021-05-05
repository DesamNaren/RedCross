package in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllDistrictResponse {
    @SerializedName("allDistricts")
    @Expose
    private List<AllDistrict> allDistricts = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<AllDistrict> getAllDistricts() {
        return allDistricts;
    }

    public void setAllDistricts(List<AllDistrict> allDistricts) {
        this.allDistricts = allDistricts;
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
