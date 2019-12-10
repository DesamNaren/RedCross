package in.gov.cgg.redcrossphase1.ui_citiguest.Beans.locate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocateResponse {

    @SerializedName("additionscenters")
    @Expose
    private List<Additionscenter> additionscenters = null;

    public List<Additionscenter> getAdditionscenters() {
        return additionscenters;
    }

    public void setAdditionscenters(List<Additionscenter> additionscenters) {
        this.additionscenters = additionscenters;
    }

}
