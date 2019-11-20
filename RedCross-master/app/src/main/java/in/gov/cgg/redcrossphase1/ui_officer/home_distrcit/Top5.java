package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Top5 {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("rank")
    @Expose
    private String rank;

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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
