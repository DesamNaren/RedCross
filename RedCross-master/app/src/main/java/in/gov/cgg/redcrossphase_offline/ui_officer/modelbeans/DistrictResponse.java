package in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistrictResponse {
    @SerializedName("names")
    @Expose
    private List<DistrictName> names = null;
    @SerializedName("top5")
    @Expose
    private List<Top5> top5 = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<DistrictName> getNames() {
        return names;
    }

    public void setNames(List<DistrictName> names) {
        this.names = names;
    }

    public List<Top5> getTop5() {
        return top5;
    }

    public void setTop5(List<Top5> top5) {
        this.top5 = top5;
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

