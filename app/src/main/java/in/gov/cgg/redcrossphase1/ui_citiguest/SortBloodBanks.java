package in.gov.cgg.redcrossphase1.ui_citiguest;

import java.util.Comparator;

import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.eRaktkoshResponseBean;


public class SortBloodBanks implements Comparator<eRaktkoshResponseBean> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(eRaktkoshResponseBean a, eRaktkoshResponseBean b) {
        return Float.compare(a.getDistance(), b.getDistance());
    }
}