package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AbstractMemberbean {

    @SerializedName("Vice Patron")
    @Expose
    private String vicePatron;
    @SerializedName("Life Member")
    @Expose
    private String lifeMember;
    @SerializedName("Annual Member")
    @Expose
    private String annualMember;
    @SerializedName("Life Associate")
    @Expose
    private String lifeAssociate;
    @SerializedName("ms")
    @Expose
    private String ms;
    @SerializedName("Annual Associate")
    @Expose
    private String annualAssociate;
    @SerializedName("jrc")
    @Expose
    private String jrc;
    @SerializedName("yrc")
    @Expose
    private String yrc;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Institutional Member")
    @Expose
    private String institutionalMember;
    @SerializedName("Patron")
    @Expose
    private String patron;
    @SerializedName("status")
    @Expose
    private String status;

    public String getVicePatron() {
        return vicePatron;
    }

    public void setVicePatron(String vicePatron) {
        this.vicePatron = vicePatron;
    }

    public String getLifeMember() {
        return lifeMember;
    }

    public void setLifeMember(String lifeMember) {
        this.lifeMember = lifeMember;
    }

    public String getAnnualMember() {
        return annualMember;
    }

    public void setAnnualMember(String annualMember) {
        this.annualMember = annualMember;
    }

    public String getLifeAssociate() {
        return lifeAssociate;
    }

    public void setLifeAssociate(String lifeAssociate) {
        this.lifeAssociate = lifeAssociate;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getAnnualAssociate() {
        return annualAssociate;
    }

    public void setAnnualAssociate(String annualAssociate) {
        this.annualAssociate = annualAssociate;
    }

    public String getJrc() {
        return jrc;
    }

    public void setJrc(String jrc) {
        this.jrc = jrc;
    }

    public String getYrc() {
        return yrc;
    }

    public void setYrc(String yrc) {
        this.yrc = yrc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstitutionalMember() {
        return institutionalMember;
    }

    public void setInstitutionalMember(String institutionalMember) {
        this.institutionalMember = institutionalMember;
    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
