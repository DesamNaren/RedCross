package in.gov.cgg.redcrossphase1.ui_officer.govtpvt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GovtVsPvtResponse {
    @SerializedName("dataGraph")
    @Expose
    private String dataGraph;
    @SerializedName("types")
    @Expose
    private List<GovType> types = null;
    @SerializedName("dateGraph")
    @Expose
    private String dateGraph;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getDataGraph() {
        return dataGraph;
    }

    public void setDataGraph(String dataGraph) {
        this.dataGraph = dataGraph;
    }

    public List<GovType> getTypes() {
        return types;
    }

    public void setTypes(List<GovType> types) {
        this.types = types;
    }

    public String getDateGraph() {
        return dateGraph;
    }

    public void setDateGraph(String dateGraph) {
        this.dateGraph = dateGraph;
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
