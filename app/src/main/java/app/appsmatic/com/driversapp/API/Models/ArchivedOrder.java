package app.appsmatic.com.driversapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mido PC on 8/17/2016.
 */
public class ArchivedOrder {


    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("AssignedDate")
    @Expose
    private String assignedDate;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("StatusID")
    @Expose
    private Integer statusID;
    @SerializedName("DeliveryAddress")
    @Expose
    private String deliveryAddress;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("Longtitude")
    @Expose
    private String longtitude;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("TotalAmount")
    @Expose
    private Float totalAmount;


    /**
     *
     * @return
     * The customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     * The Customer
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     *
     * @return
     * The assignedDate
     */
    public String getAssignedDate() {
        return assignedDate;
    }

    /**
     *
     * @param assignedDate
     * The AssignedDate
     */
    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    /**
     *
     * @return
     * The orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     *
     * @param orderDate
     * The OrderDate
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     *
     * @return
     * The orderID
     */
    public Integer getOrderID() {
        return orderID;
    }

    /**
     *
     * @param orderID
     * The OrderID
     */
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    /**
     *
     * @return
     * The statusID
     */
    public Integer getStatusID() {
        return statusID;
    }

    /**
     *
     * @param statusID
     * The StatusID
     */
    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    /**
     *
     * @return
     * The deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     *
     * @param deliveryAddress
     * The DeliveryAddress
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
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
     * The longtitude
     */
    public String getLongtitude() {
        return longtitude;
    }

    /**
     *
     * @param longtitude
     * The Longtitude
     */
    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The Latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The totalAmount
     */
    public Float getTotalAmount() {
        return totalAmount;
    }

    /**
     *
     * @param totalAmount
     * The TotalAmount
     */
    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
