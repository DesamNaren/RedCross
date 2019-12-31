package in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Age {

    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("y")
    @Expose
    private Integer y;
    @SerializedName("age")
    @Expose
    private String age;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}

