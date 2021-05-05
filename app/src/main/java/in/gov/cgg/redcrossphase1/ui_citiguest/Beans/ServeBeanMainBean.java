package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServeBeanMainBean {

    @SerializedName("additionscenters")
    @Expose
    private List<ServeBean> additionscenters;

    public List<ServeBean> getAdditionscenters() {
        return additionscenters;
    }

    public void setAdditionscenters(List<ServeBean> additionscenters) {
        this.additionscenters = additionscenters;
    }

}
