package net.thaicom.sdk.looxtv;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIUtil {

    private final String TAG = "APIUtil";
    private final String looxWSUrl = "https://pegasus.thaicom.io";
    private final String firebaseWSUrl = "https://us-central1-firebase-tc-app.cloudfunctions.net";
    private final String dynamicLinkWSUrl = "https://firebasedynamiclinks.googleapis.com";
    private Util mUtil=new Util();

//    private String deviceId=deviceID = Settings.Secure.getString(getMainContext().getContentResolver(), Settings.Secure.ANDROID_ID);

    public APIUtil() {

    }

    public void getToken(int id_channel, String uuid, String quality, int duration, TokenCallback callback) {

        APIInterface mApiInterface = new APIClient().getClient(looxWSUrl).create(APIInterface.class);

        GetPayTvTokenRequest request = new GetPayTvTokenRequest(id_channel, uuid, quality, duration);
        Call<GetPayTvTokenResponse> call = mApiInterface.GetPayTvToken(request);

        call.enqueue(new Callback<GetPayTvTokenResponse>() {
            @Override
            public void onResponse(Call<GetPayTvTokenResponse> c, retrofit2.Response<GetPayTvTokenResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "GetToken fail response: " + response.code());
                    if (callback != null) {
                        callback.onFailure(new Exception("Failed with code: " + response.code()));
                    }
                    return;
                }
                GetPayTvTokenResponse data = response.body();

                if (data == null || data.getCode() != 0) {
                    Log.d(TAG, "GetToken: response body is null or error code");
                    if (callback != null) {
                        callback.onFailure(new Exception("Invalid response body or error code"));
                    }
                    return;
                }
                // Pass the response body to the callback
                if (callback != null) {
                    callback.onSuccess(new TokenInfo(
                            data.getToken(),
                            data.getValidFrom(),
                            data.getValidTo(),
                            data.getValidDuration()));
                }
            }

            @Override
            public void onFailure(Call<GetPayTvTokenResponse> c, Throwable t) {
                c.cancel();
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }

    public void getDeviceInfo(String deviceId, DeviceInfoCallback callback) {
        DeviceInfo deviceInfo = new DeviceInfo();
        APIInterface mApiInterface = new APIClient().getClient(looxWSUrl).create(APIInterface.class);

        GetInfoByDeviceIDRequest request = new GetInfoByDeviceIDRequest(deviceId);
        Call<GetInfoByDeviceIDResponse> call = mApiInterface.GetInfoByDeviceID(request);

        call.enqueue(new Callback<GetInfoByDeviceIDResponse>() {
            @Override
            public void onResponse(Call<GetInfoByDeviceIDResponse> call, Response<GetInfoByDeviceIDResponse> response) {
                if (!response.isSuccessful()) {
//                    Log.d(TAG, "GetToken fail response: " + response.code());
                    if (callback != null) {
                        callback.onFailure(new Exception("Failed with code: " + response.code()));
                    }
                    return;
                }

                GetInfoByDeviceIDResponse data = response.body();

                if (data == null || data.getMsgCode() != 0) {
//                    Log.d(TAG, "GetToken: response body is null or error code");
                    if (callback != null) {
                        callback.onFailure(new Exception("Invalid response body or error code"));
                    }
                    return;
                }

                Log.d(TAG, data.getMsgCode()+"");
                Log.d(TAG, data.getMsgText());
                Log.d(TAG, data.getMsgDesc().getFirebaseUid());

                deviceInfo.firebaseUid = data.getMsgDesc().getFirebaseUid();
                deviceInfo.memberId = data.getMsgDesc().getMemberId();
                deviceInfo.dspName=data.getMsgDesc().getDspName();
                deviceInfo.email=data.getMsgDesc().getEmail();

                // Pass the response body to the callback
                if (callback != null) {
                    callback.onSuccess(deviceInfo);
                }
            }
            @Override
            public void onFailure(Call<GetInfoByDeviceIDResponse> call, Throwable t) {
                call.cancel();
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });

    }

    public void getPaidStatus(String firebaseUid, MemberPaidStatusCallback callback) {
        MemberInfo memberInfo = new MemberInfo();

        APIInterface mApiInterface = new APIClient().getClient(looxWSUrl).create(APIInterface.class);

        GetMemberPaidStatusRequest request = new GetMemberPaidStatusRequest(firebaseUid);

        Call<GetMemberPaidStatusResponse> call = mApiInterface.GetMemberPaidStatus(request);

        call.enqueue(new Callback<GetMemberPaidStatusResponse>() {
            @Override
            public void onResponse(Call<GetMemberPaidStatusResponse> call, Response<GetMemberPaidStatusResponse> response) {
                if (!response.isSuccessful()) {
//                    Log.d(TAG, "GetToken fail response: " + response.code());
                    if (callback != null) {
                        callback.onFailure(new Exception("Failed with code: " + response.code()));
                    }
                    return;
                }

                GetMemberPaidStatusResponse data = response.body();

                if (data == null || data.getCode() != 0) {
//                    Log.d(TAG, "GetToken: response body is null or error code");
                    if (callback != null) {
                        callback.onFailure(new Exception("Invalid response body or error code"));
                    }
                    return;
                }

                ArrayList<PaidStatusModel> ArrayD = data.getData();
                List<String> GroupsTagsList = new ArrayList<>();

                int maxCon=1;
                int remain_days=0;
                String activeTag="big";
                int activeItem=-1;
                String activeNamePackage="";
                String activeExpiredDate="";

                for (int i = 0; i < ArrayD.size(); i++) {

                    PaidStatusModel item = ArrayD.get(i);
                    //item = null;
//                    if(item!=null && item.getTag()!=null && item.getTag().equals("big")) {
                    if (item != null && !mUtil.tryStringEmpty(item.getTag())) {

                        String namGroup = (item.getNamGroup() != null) ? item.getNamGroup() : "";
                        String refCode = (item.getRefCode() != null) ? item.getRefCode() : "null";
                        String tag = (item.getTag() != null) ? item.getTag() : "null";
                        String expired = (item.getExpired() != null) ? item.getExpired().toString() : "null";
                        String remainDays = (item.getRemainDays() != null) ? item.getRemainDays().toString() : "0";
                        String idFirebaseUid = (item.getIdFirebaseUid() != null) ? item.getIdFirebaseUid() : "null";
                        String expireDate = (item.getExpireDate() != null) ? item.getExpireDate() : "null";
                        String colorCode = (item.getColorCode() != null) ? item.getColorCode() : "null";
                        String qualityGroupMobile = (item.getQualityGroupMobile() != null) ? item.getQualityGroupMobile() : "null";
                        String qualityGroupAtv = (item.getQualityGroupAtv() != null) ? item.getQualityGroupAtv() : "null";
                        String nextChargeDate = (item.getNextChargeDate() != null) ? item.getNextChargeDate() : "null";
                        String maxConcurrent = (item.getMaxConcurrent() != null) ? item.getMaxConcurrent().toString() : "1";

                        String name_package = (item.getName_package() != null) ? item.getName_package() : namGroup;

                        if(activeItem == -1){
                            activeItem=i;
                            remain_days=mUtil.tryIntParse(remainDays)>0?mUtil.tryIntParse(remainDays):0;
                            activeTag=tag;
                            activeNamePackage=name_package;
                            activeExpiredDate=expireDate;

                            if(remain_days>0)
                                maxCon=mUtil.tryIntParse(maxConcurrent)>0?mUtil.tryIntParse(maxConcurrent):1;

                        }else{
                            if(mUtil.tryIntParse(remainDays)>0)
                                maxCon += (mUtil.tryIntParse(maxConcurrent)>0?mUtil.tryIntParse(maxConcurrent):1);

                            if(mUtil.tryIntParse(remainDays) > remain_days){
                                activeItem=i;
                                remain_days=mUtil.tryIntParse(remainDays)>0?mUtil.tryIntParse(remainDays):0;

                                activeTag=tag;
                                activeNamePackage=name_package;
                                activeExpiredDate=expireDate;

                            }
                        }
                        //tag="hp1";
                        Log.d(TAG, String.format("getPaidStatus[%s] qualityGroupAtv: %s, expired: %s, remain_days: %s, nextChargeDate: %s, expireDate: %s, maxConcurrent: %s", tag, qualityGroupAtv, expired, remainDays, nextChargeDate, expireDate, maxConcurrent));
                        GroupsTagsList.add(tag);

                    }
                }


                Log.d(TAG, "getPaidStatus: activeItem: " + activeItem+", remain_days: "+remain_days+", maxCon: "+maxCon);

                // save all tag
                if (!GroupsTagsList.isEmpty()) {
                    String GroupTags = TextUtils.join(",", GroupsTagsList);
                    Log.d(TAG, "getPaidStatus: GroupsTagsList:" + GroupsTagsList);
                    Log.d(TAG, "getPaidStatus: GroupTags:" + GroupTags);

                    memberInfo.groupTags = GroupTags;
                }


                memberInfo.isExpired = remain_days<=0;
                memberInfo.remainDay = remain_days;
                memberInfo.maxConcurrent = maxCon;
                memberInfo.expiredDate = activeExpiredDate;

                // Pass the response body to the callback
                if (callback != null) {
                    callback.onSuccess(memberInfo);
                }
            }
            @Override
            public void onFailure(Call<GetMemberPaidStatusResponse> call, Throwable t) {
                call.cancel();
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });

    }


    public void getLooxChannelInfo(String groupTags, ChannelInfoCallback callback) {
        try {

            ArrayList<ChannelInfo> channelInfos = new ArrayList<ChannelInfo>();

            APIInterface mApiInterface = new APIClient().getClient(looxWSUrl).create(APIInterface.class);

            ArrayList<String> tags = new ArrayList<String>(Arrays.asList(groupTags.split(",")));
            Log.d(TAG, "GroupTags:" + groupTags + " tags:" + tags.toString());

            GetLooxChannelInfoTokenRequest request = new GetLooxChannelInfoTokenRequest(tags);

            Call<GetLooxChannelInfoResponse> call = mApiInterface.GetLooxChannelInfoToken(request);

            call.enqueue(new Callback<GetLooxChannelInfoResponse>() {
                @Override
                public void onResponse(Call<GetLooxChannelInfoResponse> call, retrofit2.Response<GetLooxChannelInfoResponse> response) {
                    try {

                        if (!response.isSuccessful()) {
                            Log.d(TAG, "GetLooxChannelInfo fail response: " + response.code() + "");
                            if (callback != null) {
                                callback.onFailure(new Exception("Failed with code: " + response.code()));
                            }
                            return;
                        }


                        GetLooxChannelInfoResponse LooxChannelInfo = response.body();
                        if (LooxChannelInfo == null) {
                            Log.d(TAG, "GetLooxChannelInfo:response body is null ");
                            if (callback != null) {
                                callback.onFailure(new Exception("Invalid response body or error code"));
                            }
                            return;
                        }

                        ArrayList<FeedModel> chList = LooxChannelInfo.getData();
                        for (int i = 0; i < chList.size(); i++) {
                            FeedModel item = chList.get(i);
                            ChannelInfo channelInfo = new ChannelInfo();
                            channelInfo.chId = item.getIdChannel();
                            channelInfo.urlLive = item.getUrlLive();
                            channelInfos.add(channelInfo);
                        }

//                        Log.d(TAG, "GetLooxChannelInfo: success");
                        if (callback != null) {
                            callback.onSuccess(channelInfos);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetLooxChannelInfoResponse> call, Throwable t) {
                    Log.d(TAG, "GetLooxChannelInfo: Load fail");
                    call.cancel();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setConcurrent(String firebaseUid, String deviceId, boolean isOnline, boolean isPlaying, SetConcurrentCallback callback) {
        try {

//            ArrayList<ConcurrentInfo> concurrentInfos = new ArrayList<ConcurrentInfo>();

            APIInterface mApiInterface = new APIClient().getClient(firebaseWSUrl).create(APIInterface.class);


            SetConcurrentRequest request = new SetConcurrentRequest(firebaseUid,
                    deviceId,
                    isOnline,
                    isPlaying,
                    System.currentTimeMillis());

            Call<SetConcurrentResponse> call = mApiInterface.SetConcurrent(request);

            call.enqueue(new Callback<SetConcurrentResponse>() {
                @Override
                public void onResponse(Call<SetConcurrentResponse> call, retrofit2.Response<SetConcurrentResponse> response) {
                    try {

                        if (!response.isSuccessful()) {
                            Log.d(TAG, "SetConcurrentResponse fail response: " + response.code() + "");
                            if (callback != null) {
                                callback.onFailure(new Exception("Failed with code: " + response.code()));
                            }
                            return;
                        }


                        SetConcurrentResponse concurrentResponse = response.body();

                        if (concurrentResponse == null || concurrentResponse.getCode()!=0) {
                            Log.d(TAG, "SetConcurrentResponse:response body is null ");
                            if (callback != null) {
                                callback.onFailure(new Exception("Invalid response body or error code"));
                            }
                            return;
                        }

//                        ArrayList<ConcurrentInfo> chList = concurrentResponse.;
//                        for (int i = 0; i < chList.size(); i++) {
//                            FeedModel item = chList.get(i);
//                            ChannelInfo channelInfo = new ChannelInfo();
//                            channelInfo.chId = item.getIdChannel();
//                            channelInfo.urlLive = item.getUrlLive();
//                            channelInfos.add(channelInfo);
//                        }

//                        Log.d(TAG, "GetLooxChannelInfo: success");
                        if (callback != null) {
                            callback.onSuccess();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SetConcurrentResponse> call, Throwable t) {
                    Log.d(TAG, "SetConcurrent: Load fail");
                    call.cancel();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getConcurrent(String firebaseUid, String deviceId, ConcurrentCallback callback) {
        try {



            APIInterface mApiInterface = new APIClient().getClient(firebaseWSUrl).create(APIInterface.class);


            GetConcurrentRequest request = new GetConcurrentRequest(firebaseUid);

            Call<GetConcurrentResponse> call = mApiInterface.GetConcurrent(request);

            call.enqueue(new Callback<GetConcurrentResponse>() {
                @Override
                public void onResponse(Call<GetConcurrentResponse> call, retrofit2.Response<GetConcurrentResponse> response) {
                    try {

                        if (!response.isSuccessful()) {
                            Log.d(TAG, "GetConcurrentResponse fail response: " + response.code() + "");
                            if (callback != null) {
                                callback.onFailure(new Exception("Failed with code: " + response.code()));
                            }
                            return;
                        }

                        GetConcurrentResponse concurrentResponse = response.body();

                        if (concurrentResponse == null || concurrentResponse.getCode()!=0) {
                            Log.d(TAG, "GetConcurrentResponse:response body is null ");
                            if (callback != null) {
                                callback.onFailure(new Exception("Invalid response body or error code"));
                            }
                            return;
                        }

                        int numConcurrent=0;
                        ArrayList<ConcurrentInfo> conList = concurrentResponse.getData();
                        for (int i = 0; i < conList.size(); i++) {
                            ConcurrentInfo item = conList.get(i);
                            if(item.isPlaying(deviceId))
                                numConcurrent++;

                        }

//                        Log.d(TAG, "GetLooxChannelInfo: success");
                        if (callback != null) {
                            callback.onSuccess(numConcurrent);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetConcurrentResponse> call, Throwable t) {
                    Log.d(TAG, "GetConcurrent: Load fail");
                    call.cancel();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getUrlByChId(int chId, ArrayList<ChannelInfo> channelInfos) {
        if(channelInfos==null || channelInfos.isEmpty())
            return "";
        for (int i = 0; i < channelInfos.size(); i++) {
            ChannelInfo item = channelInfos.get(i);
            if (item.chId == chId) {
                return item.urlLive;
            }
        }
        return "";
    }

    public void getDynamicLinkFromRestApi(String url, DynamicLinkCallback callback) {


        APIInterface jsonApiInterface = new APIClient().getClient(dynamicLinkWSUrl).create(APIInterface.class);

        DynamicLinkRequest request = new DynamicLinkRequest(url);
        Log.d(TAG,"GetDynamicLinkFromRestApi: ");

        Call<DynamicLinkResponse> call = jsonApiInterface.GetDynamicLink(request);
        call.enqueue(new Callback<DynamicLinkResponse>() {

            @Override
            public void onResponse(Call<DynamicLinkResponse> call, retrofit2.Response<DynamicLinkResponse> response) {


                if(!response.isSuccessful() ){
                    Log.d(TAG, "GetDynamicLinkFromRestApi: response code: "+ response.code());
                    if (callback != null) {
                        callback.onSuccess(null);
                    }
                    return;
                }

                DynamicLinkResponse res = response.body();

                if(res==null){
                    Log.d(TAG, "GetDynamicLinkFromRestApi: response body is null");
                    if (callback != null) {
                        callback.onSuccess(null);
                    }
                    return;
                }
                String shortLink= res.getShortLink();

                if(shortLink==null){
                    Log.d(TAG, "GetDynamicLinkFromRestApi: short link is null");
                    if (callback != null) {
                        callback.onSuccess(null);
                    }
                    return;
                }

                Log.d(TAG, "GetDynamicLinkFromRestApi: short link ="+shortLink);
                if (callback != null) {
                    callback.onSuccess(shortLink);
                }
//                getUtils().writeStringGlobalSetting("shortLink", shortLink);

//                if(!shortLink.isEmpty())
//                    QrEncoder.getInstance().generateQrBitmap(shortLink, 400, activity);
//                else
//                    QrEncoder.getInstance().generateQrBitmap(url, 400, activity);
            }

            @Override
            public void onFailure(Call<DynamicLinkResponse> call, Throwable t) {
                Log.i(TAG, "GetDynamicLinkFromRestApi: Load fail" );
                call.cancel();
                if (callback != null) {
                    callback.onFailure(t);
                }

            }
        });

    }




}
