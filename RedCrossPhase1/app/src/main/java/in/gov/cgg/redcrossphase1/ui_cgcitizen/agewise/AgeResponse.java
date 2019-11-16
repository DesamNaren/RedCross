package in.gov.cgg.redcrossphase1.ui_cgcitizen.agewise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgeResponse {
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
