package in.gov.cgg.redcrossphase1.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCountResponse {


    @SerializedName("ms")
    @Expose
    private Integer ms;
    @SerializedName("jrc")
    @Expose
    private Integer jrc;
    @SerializedName("yrc")
    @Expose
    private Integer yrc;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getMs() {
        return ms;
    }

    public void setMs(Integer ms) {
        this.ms = ms;
    }

    public Integer getJrc() {
        return jrc;
    }

    public void setJrc(Integer jrc) {
        this.jrc = jrc;
    }

    public Integer getYrc() {
        return yrc;
    }

    public void setYrc(Integer yrc) {
        this.yrc = yrc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
