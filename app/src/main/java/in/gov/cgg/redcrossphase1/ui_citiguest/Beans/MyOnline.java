package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOnline {
    @SerializedName("memberonlinedonations")
    @Expose
    private List<Memberonlinedonation> memberonlinedonations = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Memberonlinedonation> getMemberonlinedonations() {
        return memberonlinedonations;
    }

    public void setMemberonlinedonations(List<Memberonlinedonation> memberonlinedonations) {
        this.memberonlinedonations = memberonlinedonations;
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

