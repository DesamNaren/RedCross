package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatelevelDistrictViewCountResponse {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("Name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
