package app.appsmatic.com.driversapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mido PC on 8/18/2016.
 */
public class OrderDetail {

    @SerializedName("MealItemID")
    @Expose
    private Integer mealItemID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("ItemPrice")
    @Expose
    private Double itemPrice;
    @SerializedName("additions")
    @Expose
    private List<Addition> additions = new ArrayList<Addition>();
    @SerializedName("TotalPrice")
    @Expose
    private Double totalPrice;

    /**
     *
     * @return
     * The mealItemID
     */
    public Integer getMealItemID() {
        return mealItemID;
    }

    /**
     *
     * @param mealItemID
     * The MealItemID
     */
    public void setMealItemID(Integer mealItemID) {
        this.mealItemID = mealItemID;
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
    public Double getItemPrice() {
        return itemPrice;
    }

    /**
     *
     * @param itemPrice
     * The ItemPrice
     */
    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     *
     * @return
     * The additions
     */
    public List<Addition> getAdditions() {
        return additions;
    }

    /**
     *
     * @param additions
     * The additions
     */
    public void setAdditions(List<Addition> additions) {
        this.additions = additions;
    }

    /**
     *
     * @return
     * The totalPrice
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     *
     * @param totalPrice
     * The TotalPrice
     */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
