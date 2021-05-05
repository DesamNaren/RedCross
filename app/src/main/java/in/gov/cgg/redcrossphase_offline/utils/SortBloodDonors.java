package in.gov.cgg.redcrossphase_offline.utils;

import java.util.Comparator;

import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.BloodDonorResponse;

public class SortBloodDonors implements Comparator<BloodDonorResponse> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(BloodDonorResponse a, BloodDonorResponse b) {
        return Float.compare(a.getDistance(), b.getDistance());
    }
}