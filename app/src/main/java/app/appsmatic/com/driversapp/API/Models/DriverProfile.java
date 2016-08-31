package app.appsmatic.com.driversapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mido PC on 8/30/2016.
 */
public class DriverProfile {


    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("DriverID")
    @Expose
    private Integer driverID;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("VehiclePlateNo")
    @Expose
    private String vehiclePlateNo;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("PersonalPhoto")
    @Expose
    private String personalPhoto;
    @SerializedName("BranchCode")
    @Expose
    private String branchCode;
    @SerializedName("Available")
    @Expose
    private Boolean available;
    @SerializedName("ObjectState")
    @Expose
    private Integer objectState;

    /**
     *
     * @return
     * The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     * The UserID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     * The driverID
     */
    public Integer getDriverID() {
        return driverID;
    }

    /**
     *
     * @param driverID
     * The DriverID
     */
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The FullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The vehiclePlateNo
     */
    public String getVehiclePlateNo() {
        return vehiclePlateNo;
    }

    /**
     *
     * @param vehiclePlateNo
     * The VehiclePlateNo
     */
    public void setVehiclePlateNo(String vehiclePlateNo) {
        this.vehiclePlateNo = vehiclePlateNo;
    }

    /**
     *
     * @return
     * The mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     *
     * @param mobileNo
     * The MobileNo
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     *
     * @return
     * The personalPhoto
     */
    public String getPersonalPhoto() {
        return personalPhoto;
    }

    /**
     *
     * @param personalPhoto
     * The PersonalPhoto
     */
    public void setPersonalPhoto(String personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    /**
     *
     * @return
     * The branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     *
     * @param branchCode
     * The BranchCode
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     *
     * @return
     * The available
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     *
     * @param available
     * The Available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     *
     * @return
     * The objectState
     */
    public Integer getObjectState() {
        return objectState;
    }

    /**
     *
     * @param objectState
     * The ObjectState
     */
    public void setObjectState(Integer objectState) {
        this.objectState = objectState;
    }
}
