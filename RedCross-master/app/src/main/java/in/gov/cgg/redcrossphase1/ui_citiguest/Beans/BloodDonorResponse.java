package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloodDonorResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("mandal")
    @Expose
    private String mandal;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    public final static Creator<BloodDonorResponse> CREATOR = new Creator<BloodDonorResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public BloodDonorResponse createFromParcel(Parcel in) {
            return new BloodDonorResponse(in);
        }

        public BloodDonorResponse[] newArray(int size) {
            return (new BloodDonorResponse[size]);
        }

    };
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    private float distance;

    protected BloodDonorResponse(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneNo = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.district = ((String) in.readValue((String.class.getClassLoader())));
        this.mandal = ((String) in.readValue((String.class.getClassLoader())));
        this.village = ((String) in.readValue((String.class.getClassLoader())));
        this.bloodGroup = ((String) in.readValue((String.class.getClassLoader())));
        this.latitude = ((String) in.readValue((String.class.getClassLoader())));
        this.longitude = ((String) in.readValue((String.class.getClassLoader())));
        this.distance = ((float) in.readValue((String.class.getClassLoader())));
    }

    public BloodDonorResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMandal() {
        return mandal;
    }

    public void setMandal(String mandal) {
        this.mandal = mandal;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(phoneNo);
        dest.writeValue(email);
        dest.writeValue(address);
        dest.writeValue(district);
        dest.writeValue(mandal);
        dest.writeValue(village);
        dest.writeValue(bloodGroup);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(distance);
    }

    public int describeContents() {
        return 0;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}