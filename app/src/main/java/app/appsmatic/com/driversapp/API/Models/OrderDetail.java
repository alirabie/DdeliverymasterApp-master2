package app.appsmatic.com.driversapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mido PC on 8/18/2016.
 */
public class OrderDetail {


    @SerializedName("PickupBranchCode")
    @Expose
    private Object pickupBranchCode;
    @SerializedName("PickupBranch")
    @Expose
    private Object pickupBranch;
    @SerializedName("DeliveryBranchCode")
    @Expose
    private Object deliveryBranchCode;
    @SerializedName("DeliveryBranch")
    @Expose
    private Object deliveryBranch;
    @SerializedName("OrderID")
    @Expose
    private Integer orderID;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("ItemPrice")
    @Expose
    private Integer itemPrice;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Customer")
    @Expose
    private Object customer;
    @SerializedName("StatusID")
    @Expose
    private Integer statusID;
    @SerializedName("Status")
    @Expose
    private Object status;

    /**
     *
     * @return
     * The pickupBranchCode
     */
    public Object getPickupBranchCode() {
        return pickupBranchCode;
    }

    /**
     *
     * @param pickupBranchCode
     * The PickupBranchCode
     */
    public void setPickupBranchCode(Object pickupBranchCode) {
        this.pickupBranchCode = pickupBranchCode;
    }

    /**
     *
     * @return
     * The pickupBranch
     */
    public Object getPickupBranch() {
        return pickupBranch;
    }

    /**
     *
     * @param pickupBranch
     * The PickupBranch
     */
    public void setPickupBranch(Object pickupBranch) {
        this.pickupBranch = pickupBranch;
    }

    /**
     *
     * @return
     * The deliveryBranchCode
     */
    public Object getDeliveryBranchCode() {
        return deliveryBranchCode;
    }

    /**
     *
     * @param deliveryBranchCode
     * The DeliveryBranchCode
     */
    public void setDeliveryBranchCode(Object deliveryBranchCode) {
        this.deliveryBranchCode = deliveryBranchCode;
    }

    /**
     *
     * @return
     * The deliveryBranch
     */
    public Object getDeliveryBranch() {
        return deliveryBranch;
    }

    /**
     *
     * @param deliveryBranch
     * The DeliveryBranch
     */
    public void setDeliveryBranch(Object deliveryBranch) {
        this.deliveryBranch = deliveryBranch;
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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The Quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The itemPrice
     */
    public Integer getItemPrice() {
        return itemPrice;
    }

    /**
     *
     * @param itemPrice
     * The ItemPrice
     */
    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
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
     * The customer
     */
    public Object getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     * The Customer
     */
    public void setCustomer(Object customer) {
        this.customer = customer;
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
     * The status
     */
    public Object getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The Status
     */
    public void setStatus(Object status) {
        this.status = status;
    }

}
