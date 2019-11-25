package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Top5 {
    @SerializedName("districtId")
    @Expose
    private String districtId;
    @SerializedName("chairmanPhoneNo")
    @Expose
    private String chairmanPhoneNo;
    @SerializedName("chairmanName")
    @Expose
    private String chairmanName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("y")
    @Expose
    private Integer y;
    @SerializedName("rank")
    @Expose
    private String rank;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getChairmanPhoneNo() {
        return chairmanPhoneNo;
    }

    public void setChairmanPhoneNo(String chairmanPhoneNo) {
        this.chairmanPhoneNo = chairmanPhoneNo;
    }

    public String getChairmanName() {
        return chairmanName;
    }

    public void setChairmanName(String chairmanName) {
        this.chairmanName = chairmanName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

}
