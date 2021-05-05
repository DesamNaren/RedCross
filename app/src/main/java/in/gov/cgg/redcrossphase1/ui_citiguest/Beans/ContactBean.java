package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactBean {

    @SerializedName("additionscenters")
    @Expose
    private List<ContactAdditionscenter> additionscenters = null;

    public List<ContactAdditionscenter> getAdditionscenters() {
        return additionscenters;
    }

    public void setAdditionscenters(List<ContactAdditionscenter> additionscenters) {
        this.additionscenters = additionscenters;
    }
}
