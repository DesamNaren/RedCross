package in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans;

import java.util.ArrayList;
import java.util.List;

public class BloodBanksBean {

    private ArrayList<eRaktkoshResponseBean> arrayList;
    private ArrayList<BloodDonorResponse> bloodDonorResponses;
    private ArrayList<AbstractMemberbean> abstractMemberbeans;
    private List<MembershipDetails_Bean> membershipDetails_beans;

    public ArrayList<eRaktkoshResponseBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<eRaktkoshResponseBean> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<BloodDonorResponse> getBloodDonorResponses() {
        return bloodDonorResponses;
    }

    public void setBloodDonorResponses(ArrayList<BloodDonorResponse> bloodDonorResponses) {
        this.bloodDonorResponses = bloodDonorResponses;
    }

    public ArrayList<AbstractMemberbean> getAbstractMemberbeans() {
        return abstractMemberbeans;
    }

    public void setAbstractMemberbeans(ArrayList<AbstractMemberbean> abstractMemberbeans) {
        this.abstractMemberbeans = abstractMemberbeans;
    }

    public List<MembershipDetails_Bean> getMembershipDetails_beans() {
        return membershipDetails_beans;
    }

    public void setMembershipDetails_beans(List<MembershipDetails_Bean> membershipDetails_beans) {
        this.membershipDetails_beans = membershipDetails_beans;
    }
}
