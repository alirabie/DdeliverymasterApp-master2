package app.appsmatic.com.driversapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mido PC on 8/17/2016.
 */
public class DriverID {

    @SerializedName("driverid")
    @Expose
    private String driverid;
    @SerializedName("message")
    @Expose
    private String message;


    /**
     *
     * @return
     * The driverid
     */
    public String getDriverid() {
        return driverid;
    }

    /**
     *
     * @param driverid
     * The driverid
     */
    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }


    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }


}
