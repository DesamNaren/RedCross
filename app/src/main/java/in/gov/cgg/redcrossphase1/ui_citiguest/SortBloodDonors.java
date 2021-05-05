package in.gov.cgg.redcrossphase1.ui_citiguest;

import java.util.Comparator;

import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.BloodDonorResponse;


public class SortBloodDonors implements Comparator<BloodDonorResponse> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(BloodDonorResponse a, BloodDonorResponse b) {
        return Float.compare(a.getDistance(), b.getDistance());
    }
}