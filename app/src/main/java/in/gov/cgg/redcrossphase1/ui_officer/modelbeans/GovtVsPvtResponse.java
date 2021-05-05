package in.gov.cgg.redcrossphase1.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GovtVsPvtResponse {
    @SerializedName("last10days")
    @Expose
    private List<Last10day> last10days = null;
    @SerializedName("dates")
    @Expose
    private String dates;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("gov")
    @Expose
    private String gov;
    @SerializedName("pvt")
    @Expose
    private String pvt;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Last10day> getLast10days() {
        return last10days;
    }

    public void setLast10days(List<Last10day> last10days) {
        this.last10days = last10days;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGov() {
        return gov;
    }

    public void setGov(String gov) {
        this.gov = gov;
    }

    public String getPvt() {
        return pvt;
    }

    public void setPvt(String pvt) {
        this.pvt = pvt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
