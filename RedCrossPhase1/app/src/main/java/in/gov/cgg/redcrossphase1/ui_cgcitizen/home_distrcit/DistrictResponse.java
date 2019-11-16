package in.gov.cgg.redcrossphase1.ui_cgcitizen.home_distrcit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictResponse {
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("districtName1")
    @Expose
    private String districtName1;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDistrictName1() {
        return districtName1;
    }

    public void setDistrictName1(String districtName1) {
        this.districtName1 = districtName1;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
