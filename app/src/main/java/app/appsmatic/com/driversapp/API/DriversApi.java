package app.appsmatic.com.driversapp.API;

import java.util.List;

import app.appsmatic.com.driversapp.API.Models.ArchivedOrder;
import app.appsmatic.com.driversapp.API.Models.ChangeStautMsg;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.API.Models.OrderDetail;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Mido PC on 8/14/2016.
 */
public interface DriversApi {

    @FormUrlEncoded
    @POST("Account/DriverLogin")
    Call<DriverID> login(@Field("username") String username,@Field("password")String password);


    @FormUrlEncoded
    @POST("DriverApp/GetDriverOrders")
    Call<List<Order>> getOrders(@Field("UserID") String userID);


    @FormUrlEncoded
    @POST("DriverApp/GetArchivedOrders")
    Call<List<ArchivedOrder>> getArchivedOrders(@Field("UserID") String userID);


    @FormUrlEncoded
    @POST("DriverApp/OrderDetails")
    Call<List<OrderDetail>> getOrderDetails(@Field("OrderID") String orderID);


    @FormUrlEncoded
    @POST("DriverApp/ChangeOrderStatus")
    Call<ChangeStautMsg> changeStautMsg(@Field("OrderID") String orderID,@Field("status") String statusID);


    @FormUrlEncoded
    @POST("DriverApp/ConfirmOrderPickup")
    Call<DriverID> ConfirmOrder(@Field("DriverId") String driverId,@Field("OrderId") int orderID );










}
