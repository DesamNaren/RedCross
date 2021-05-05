package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

public class BloodBankdetails_Bean {
    private String distictName;
    private String officerName;
    private String ofcaddress;
    private String contactNumber;


    public BloodBankdetails_Bean(String distictName, String contactNumber, String ofcAddress, String officerName) {


        this.setDistictName(distictName);
        this.setOfficerName(officerName);
        this.setOfcaddress(ofcAddress);
        this.setContactNumber(contactNumber);

    }

    public String getDistictName() {
        return distictName;
    }

    public void setDistictName(String distictName) {
        this.distictName = distictName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getOfcaddress() {
        return ofcaddress;
    }

    public void setOfcaddress(String ofcaddress) {
        this.ofcaddress = ofcaddress;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

//    public BloodBankdetails_Bean(String[] district_names, String[] officer_names, String[] ofc_places, String[] contact_number) {
//        this.setDistict(district_names);
//    }


}
