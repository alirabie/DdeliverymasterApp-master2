package app.appsmatic.com.driversapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by Mido PC on 8/31/2016.
 */
public class DriverUpdate {

    private String userID;
    private Integer driverID;
    private String fullName;
    private String address;
    private String vehiclePlateNo;
    private String mobileNo;
    private String personalPhoto;
    private String branchCode;
    private Boolean available;
    private Integer objectState;

    public DriverUpdate(String userID, Integer driverID, String fullName, String address, String vehiclePlateNo, String mobileNo, String personalPhoto, String branchCode, Boolean available, Integer objectState) {
        this.userID = userID;
        this.driverID = driverID;
        this.fullName = fullName;
        this.address = address;
        this.vehiclePlateNo = vehiclePlateNo;
        this.mobileNo = mobileNo;
        this.personalPhoto = personalPhoto;
        this.branchCode = branchCode;
        this.available = available;
        this.objectState = objectState;
    }

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
