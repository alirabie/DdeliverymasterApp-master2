package app.appsmatic.com.driversapp.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mido PC on 12/16/2016.
 */
public class ResArchived {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private List<ArchivedOrder> message = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<ArchivedOrder> getMessage() {
        return message;
    }

    public void setMessage(List<ArchivedOrder> message) {
        this.message = message;
    }

}
