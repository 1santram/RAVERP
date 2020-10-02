package com.rav.raverp.network;
import com.rav.raverp.data.model.api.ApiResponse;
import com.rav.raverp.data.model.api.GetBlock;
import com.rav.raverp.data.model.api.GetProject;
import com.rav.raverp.data.model.api.Login;
import com.rav.raverp.data.model.api.PlotAvailable;
import com.rav.raverp.data.model.local.ChangePassword;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiHelper {

    @Headers({"Content-Type: application/json"})
    @POST(ApiEndPoint.LOGIN)
    Call<ApiResponse<List<Login>>> userLogin(@Query("LoginId") String LoginId, @Query("Password") String Password);

    @Headers({"Content-Type: application/json"})
    @GET(ApiEndPoint.AvailablePlot)
    Call<ApiResponse<List<PlotAvailable>>> getPlotAvailable();
    @Headers({"Content-Type: application/json"})
    @POST(ApiEndPoint.ChangePassword)
    Call<ApiResponse<List<ChangePassword>>> UpdateChangePassword(@Query("LoginId") String LoginId,
                                                                 @Query("OldPassword") String Password,
                                                                 @Query("NewPassword") String NewPassword,
                                                                 @Query("ConfirmPassword")String ConfirmPassword);
    @Headers({"Content-Type: application/json"})
    @GET(ApiEndPoint.Get_Project)
    Call<ApiResponse<List<GetProject>>> getProject();

    @Headers({"Content-Type: application/json"})
    @POST(ApiEndPoint.Get_Blocks)
    Call<ApiResponse<List<GetBlock>>> getBlocks(@Query("projectId") String projectId);

    @Headers({"Content-Type: application/json"})
    @POST(ApiEndPoint.PlotAvailability)
    Call<ApiResponse<List<PlotAvailable>>> getPlotAvailabilitylistfilter(@Query("projectId") String projectId,
                                                                                      @Query("blockId") String blockId);

}
