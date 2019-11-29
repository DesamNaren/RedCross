package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatelevelDistrictViewCountResponse {
    @SerializedName("Membership")
    @Expose
    private Integer membership;
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
    private String name;

    public Integer getMembership() {
        return membership;
    }

    public void setMembership(Integer membership) {
        this.membership = membership;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
