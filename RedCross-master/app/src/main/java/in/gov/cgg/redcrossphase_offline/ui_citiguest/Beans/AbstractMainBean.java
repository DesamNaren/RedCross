package in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbstractMainBean {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("vals")
    @Expose
    private List<AbstractMemberbean> vals = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<AbstractMemberbean> getVals() {
        return vals;
    }

    public void setVals(List<AbstractMemberbean> vals) {
        this.vals = vals;
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
