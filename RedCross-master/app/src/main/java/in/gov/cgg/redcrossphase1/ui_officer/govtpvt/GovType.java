package in.gov.cgg.redcrossphase1.ui_officer.govtpvt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GovType {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("y")
    @Expose
    private Integer y;
    @SerializedName("type")
    @Expose
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
