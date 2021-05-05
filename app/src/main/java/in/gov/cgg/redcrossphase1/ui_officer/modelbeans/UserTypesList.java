package in.gov.cgg.redcrossphase1.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTypesList {

    int total;
    @SerializedName("JRC")
    @Expose
    private Integer jRC;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("YRC")
    @Expose
    private Integer yRC;
    @SerializedName("Name")
    @Expose
    private String iname;


    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Integer getJRC() {
        return jRC;
    }

    public void setJRC(Integer jRC) {
        this.jRC = jRC;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYRC() {
        return yRC;
    }

    public void setYRC(Integer yRC) {
        this.yRC = yRC;
    }


}