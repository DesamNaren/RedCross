package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

public class MembershipDetails_Bean {

    private String TypeOfMembership;
    private String RatesOfSubscription;

    public MembershipDetails_Bean(String TOM, String ROS) {
        this.setTypeOfMembership(TOM);
        this.setRatesOfSubscription(ROS);
    }


    public String getTypeOfMembership() {
        return TypeOfMembership;
    }

    public void setTypeOfMembership(String typeOfMembership) {
        TypeOfMembership = typeOfMembership;
    }

    public String getRatesOfSubscription() {
        return RatesOfSubscription;
    }

    public void setRatesOfSubscription(String ratesOfSubscription) {
        RatesOfSubscription = ratesOfSubscription;
    }


}
