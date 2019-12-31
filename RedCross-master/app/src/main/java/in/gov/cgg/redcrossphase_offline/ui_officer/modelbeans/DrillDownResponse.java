package in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrillDownResponse {
    @SerializedName("headers")
    @Expose
    private List<String> headers = null;
    @SerializedName("totalRecords")
    @Expose
    private Integer totalRecords;
    @SerializedName("totalPageNumber")
    @Expose
    private Integer totalPageNumber;
    @SerializedName("StudentsList")
    @Expose
    private List<List<String>> studentsList = null;
    @SerializedName("pageTitle")
    @Expose
    private Object pageTitle;

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(Integer totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public List<List<String>> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<List<String>> studentsList) {
        this.studentsList = studentsList;
    }

    public Object getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(Object pageTitle) {
        this.pageTitle = pageTitle;
    }
}
