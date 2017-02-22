package app.appsmatic.com.driversapp.API;

import java.util.HashMap;
import java.util.List;

import app.appsmatic.com.driversapp.API.Models.ArchivedOrder;
import app.appsmatic.com.driversapp.API.Models.ChangeStautMsg;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.DriverProfile;
import app.appsmatic.com.driversapp.API.Models.DriverUpdate;
import app.appsmatic.com.driversapp.API.Models.LoginData;
import app.appsmatic.com.driversapp.API.Models.Msg;
import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.API.Models.OrderDetail;
import app.appsmatic.com.driversapp.API.Models.ResArchived;
import app.appsmatic.com.driversapp.API.Models.ResConfirmOrder;
import app.appsmatic.com.driversapp.API.Models.ResLocationUpdate;
import app.appsmatic.com.driversapp.API.Models.ResOrderDetails;
import app.appsmatic.com.driversapp.API.Models.ResOrders;
import app.appsmatic.com.driversapp.API.Models.ResProfile;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Mido PC on 8/14/2016.
 */
public interface DriversApi {



    @POST("account/login")
    Call<DriverID> login(@Body Object logindtat);



    @POST("driver/Orders")
    Call<ResOrders> getOrders(@Body Object userID);



    @POST("driver/ArchivedOrders")
    Call<ResArchived> getArchivedOrders(@Body Object userID);



    @POST("driver/OrderDetails")
    Call<ResOrderDetails> getOrderDetails(@Body Object orderID);



    @POST("driver/ChangeOrderStatus")
    Call<ResConfirmOrder> changeStautMsg(@Body Object st);



    @POST("driver/ConfirmOrderPickup")
    Call<ResConfirmOrder> ConfirmOrder(@Body Object conf );


    @POST("driver/profile")
    Call<ResProfile> getProfile(@Body Object userID);




    @POST("driver/update")
    Call<ResponseBody> updateDriverinfo(@Body Object driver);

    @POST("driver/Location")
    Call<ResLocationUpdate> updateDriverLocation(@Body Object driver);


}
