package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllScreenMain {

    @SerializedName("allScreens")
    @Expose
    private List<AllScreen> allScreens = null;

    public List<AllScreen> getAllScreens() {
        return allScreens;
    }

    public void setAllScreens(List<AllScreen> allScreens) {
        this.allScreens = allScreens;
    }

}