package app.appsmatic.com.driversapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mido PC on 9/20/2016.
 */
public class Addition {


    @SerializedName("AdditionID")
    @Expose
    private Integer additionID;
    @SerializedName("AdditionName")
    @Expose
    private String additionName;
    @SerializedName("AdditionQuantity")
    @Expose
    private Integer additionQuantity;
    @SerializedName("AdditionPrice")
    @Expose
    private Double additionPrice;

    /**
     *
     * @return
     * The additionID
     */
    public Integer getAdditionID() {
        return additionID;
    }

    /**
     *
     * @param additionID
     * The AdditionID
     */
    public void setAdditionID(Integer additionID) {
        this.additionID = additionID;
    }

    /**
     *
     * @return
     * The additionName
     */
    public String getAdditionName() {
        return additionName;
    }

    /**
     *
     * @param additionName
     * The AdditionName
     */
    public void setAdditionName(String additionName) {
        this.additionName = additionName;
    }

    /**
     *
     * @return
     * The additionQuantity
     */
    public Integer getAdditionQuantity() {
        return additionQuantity;
    }

    /**
     *
     * @param additionQuantity
     * The AdditionQuantity
     */
    public void setAdditionQuantity(Integer additionQuantity) {
        this.additionQuantity = additionQuantity;
    }

    /**
     *
     * @return
     * The additionPrice
     */
    public Double getAdditionPrice() {
        return additionPrice;
    }

    /**
     *
     * @param additionPrice
     * The AdditionPrice
     */
    public void setAdditionPrice(Double additionPrice) {
        this.additionPrice = additionPrice;
    }



}
