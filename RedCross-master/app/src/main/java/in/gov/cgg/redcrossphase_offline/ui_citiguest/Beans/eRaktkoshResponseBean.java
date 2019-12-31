package in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class eRaktkoshResponseBean implements Parcelable {

    public final static Parcelable.Creator<eRaktkoshResponseBean> CREATOR = new Creator<eRaktkoshResponseBean>() {
        @SuppressWarnings({
                "unchecked"
        })
        public eRaktkoshResponseBean createFromParcel(Parcel in) {
            return new eRaktkoshResponseBean(in);
        }

        public eRaktkoshResponseBean[] newArray(int size) {
            return (new eRaktkoshResponseBean[size]);
        }

    };
    @SerializedName("TOTAL")
    @Expose
    private Integer tOTAL;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("add")
    @Expose
    private String add;
    @SerializedName("ph")
    @Expose
    private String ph;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("h_code")
    @Expose
    private String hCode;
    @SerializedName("dis")
    @Expose
    private String dis;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("not_available")
    @Expose
    private String notAvailable;
    @SerializedName("moderate")
    @Expose
    private String moderate;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;
    @SerializedName("currentStatus")
    @Expose
    private String currentStatus;
    @SerializedName("available_WithQty")
    @Expose
    private String availableWithQty;
    @SerializedName("not_available_WithQty")
    @Expose
    private String notAvailableWithQty;
    @SerializedName("moderate_WithQty")
    @Expose
    private String moderateWithQty;
    @SerializedName("disCode")
    @Expose
    private String disCode;
    @SerializedName("disName")
    @Expose
    private String disName;
    private float distance;

    protected eRaktkoshResponseBean(Parcel in) {
        this.tOTAL = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.add = ((String) in.readValue((String.class.getClassLoader())));
        this.ph = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.hCode = ((String) in.readValue((String.class.getClassLoader())));
        this.dis = ((String) in.readValue((String.class.getClassLoader())));
        this.lat = ((String) in.readValue((String.class.getClassLoader())));
        this._long = ((String) in.readValue((String.class.getClassLoader())));
        this.available = ((String) in.readValue((String.class.getClassLoader())));
        this.notAvailable = ((String) in.readValue((String.class.getClassLoader())));
        this.moderate = ((String) in.readValue((String.class.getClassLoader())));
        this.lastUpdate = ((String) in.readValue((String.class.getClassLoader())));
        this.currentStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.availableWithQty = ((String) in.readValue((String.class.getClassLoader())));
        this.notAvailableWithQty = ((String) in.readValue((String.class.getClassLoader())));
        this.moderateWithQty = ((String) in.readValue((String.class.getClassLoader())));
        this.disCode = ((String) in.readValue((String.class.getClassLoader())));
        this.disName = ((String) in.readValue((String.class.getClassLoader())));
        this.distance = ((float) in.readValue((String.class.getClassLoader())));
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Integer getTOTAL() {
        return tOTAL;
    }

    public void setTOTAL(Integer tOTAL) {
        this.tOTAL = tOTAL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHCode() {
        return hCode;
    }

    public void setHCode(String hCode) {
        this.hCode = hCode;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getNotAvailable() {
        return notAvailable;
    }

    public void setNotAvailable(String notAvailable) {
        this.notAvailable = notAvailable;
    }

    public String getModerate() {
        return moderate;
    }

    public void setModerate(String moderate) {
        this.moderate = moderate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getAvailableWithQty() {
        return availableWithQty;
    }

    public void setAvailableWithQty(String availableWithQty) {
        this.availableWithQty = availableWithQty;
    }

    public String getNotAvailableWithQty() {
        return notAvailableWithQty;
    }

    public void setNotAvailableWithQty(String notAvailableWithQty) {
        this.notAvailableWithQty = notAvailableWithQty;
    }

    public String getModerateWithQty() {
        return moderateWithQty;
    }

    public void setModerateWithQty(String moderateWithQty) {
        this.moderateWithQty = moderateWithQty;
    }

    public String getDisCode() {
        return disCode;
    }

    public void setDisCode(String disCode) {
        this.disCode = disCode;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(tOTAL);
        dest.writeValue(name);
        dest.writeValue(add);
        dest.writeValue(ph);
        dest.writeValue(type);
        dest.writeValue(email);
        dest.writeValue(hCode);
        dest.writeValue(dis);
        dest.writeValue(lat);
        dest.writeValue(_long);
        dest.writeValue(available);
        dest.writeValue(notAvailable);
        dest.writeValue(moderate);
        dest.writeValue(lastUpdate);
        dest.writeValue(currentStatus);
        dest.writeValue(availableWithQty);
        dest.writeValue(notAvailableWithQty);
        dest.writeValue(moderateWithQty);
        dest.writeValue(disCode);
        dest.writeValue(disName);
        dest.writeValue(distance);
    }

    public int describeContents() {
        return 0;
    }

}
