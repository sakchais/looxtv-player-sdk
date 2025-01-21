package net.thaicom.sdk.looxtv;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("JarvisWS/streamingtoken/api_pay_tv_get_token")
    Call<GetPayTvTokenResponse> GetPayTvToken(@Body GetPayTvTokenRequest body);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("JarvisWS/bigscreen/get_info_by_device_id")
    Call<GetInfoByDeviceIDResponse> GetInfoByDeviceID(@Body GetInfoByDeviceIDRequest body);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("JarvisWS/paidstatus/v2/api_pay_tv_get_member_paid_status")
    Call<GetMemberPaidStatusResponse> GetMemberPaidStatus(@Body GetMemberPaidStatusRequest body);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("JarvisWS/feed/v2/get_feed_live_settopbox_token")
    Call<GetLooxChannelInfoResponse> GetLooxChannelInfoToken(@Body GetLooxChannelInfoTokenRequest body);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("loox_tizen_update_current_concurrent")
    Call<SetConcurrentResponse> SetConcurrent(@Body SetConcurrentRequest body);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("loox_tizen_get_current_concurrent")
    Call<GetConcurrentResponse> GetConcurrent(@Body GetConcurrentRequest body);

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @POST("/v1/shortLinks?key=AIzaSyDxjfTu__mDICdyRXgIPq6TYEie5gdJ1kg")
    Call<DynamicLinkResponse> GetDynamicLink(@Body DynamicLinkRequest body);


}

