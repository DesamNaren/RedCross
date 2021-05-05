package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

public class ContactusDetails_Bean {
    private String distarictName;
    private String adress;

    public ContactusDetails_Bean(String distarictName, String adress) {
        this.setDistarictName(distarictName);
        this.setAdress(adress);
    }

    public String getDistarictName() {
        return distarictName;
    }

    public void setDistarictName(String distarictName) {
        this.distarictName = distarictName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
