package net.thaicom.sdk.looxtv;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.app.BrowseSupportFragment;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;



@SuppressLint("AccountFragment")
public class AccountFragment extends BaseFragment implements BrowseSupportFragment.MainFragmentAdapterProvider {

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String data);
    }

    private OnFragmentInteractionListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Attach the listener to the activity
            listener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    // Call this method when you want to send data to the activity
    public void sendDataToActivity(String data) {
        if (listener != null) {
            listener.onFragmentInteraction(data);
        }
    }

    private static String TAG = "AccountFragment";


    private boolean trial_mode=true;

    private String firebaseUid=null;
    private String qualityGroupAtv;
    private CountDownTimer countDownTimer;
    private int retry;
    private boolean online;
    Handler memberHandler;
    Handler signoutHandler;

    private int finish_round = 1;
    private int tick_round = 0;
    TextView deviceIdTv;

    private String uUid=null;
    private String shortLink=null;

    private APIUtil apiUtil = new APIUtil();

    private MemberInfo mMemberInfo=null;
    private DeviceInfo mDeviceInfo=null;

    private String memberId=null;
    private String email=null;

    private String expireDate=null;

    Button subscribe_bt;
    Button logout_bt;
    Button back_bt;
    Button start_bt;

    private String need_focus="back";


    private BrowseSupportFragment.MainFragmentAdapter mMainFragmentAdapter = new BrowseSupportFragment.MainFragmentAdapter(this);

    @Override
    public BrowseSupportFragment.MainFragmentAdapter getMainFragmentAdapter() {
        return mMainFragmentAdapter;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {


        if (getArguments() != null) {
            firebaseUid = getArguments().getString("firebaseUid");
            uUid = getArguments().getString("uUid");
            shortLink = getArguments().getString("shortLink");
            memberId = getArguments().getString("memberId");
            email = getArguments().getString("email");

        }
        checkSubscription();


        Log.d(TAG, "onCreate");
//        getUtils().writeStringGlobalSetting("need_focus", "");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        Log.d(TAG, "onCreateView, firebaseUid="+ firebaseUid);

        view = inflater.inflate(R.layout.fragment_trial_qr, container, false);
        trial_mode=true;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fill_qr_data(view);

    }
    @Override
    public void onResume() {

        Log.d(TAG, "onResume");

        super.onResume();

    }

    private void generateQRCode(String text, ImageView qrCodeIV)
    {
        BarcodeEncoder barcodeEncoder
                = new BarcodeEncoder();
        try {
            // This method returns a Bitmap image of the
            // encoded text with a height and width of 400
            // pixels.
            Bitmap bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);
            qrCodeIV.setImageBitmap(bitmap); // Sets the Bitmap to ImageView
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public String GetDeviceIdText(){

        String d="Device Info: "+uUid;

        d += (" (Android TV: v" + Build.VERSION.RELEASE + ", TV SDK: v0.1" + ")");


        Log.d(TAG, String.format("deviceText=%s", d));

        return d;
    }

    private void getDeviceInfo(){

        apiUtil.getDeviceInfo(uUid, new DeviceInfoCallback() {
            @Override
            public void onSuccess(DeviceInfo deviceInfo) {
                mDeviceInfo = deviceInfo;
                Log.d(TAG, "Device is registered");

                if (mDeviceInfo.firebaseUid != null) {
                    getMemberStatus(mDeviceInfo.firebaseUid);
                }


            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Device is not registered");
            }
        });
    }

    private void getMemberStatus(String fUid){
        apiUtil.getPaidStatus(fUid, new MemberPaidStatusCallback() {
            @Override
            public void onSuccess(MemberInfo memberInfo) {
                mMemberInfo = memberInfo;
                Log.d(TAG, String.format("groupTags: %s, isExpired: %s, maxConcurrent: %d, remainDay: %d",
                        mMemberInfo.groupTags,
                        mMemberInfo.isExpired?"true":"false",
                        mMemberInfo.maxConcurrent,
                        mMemberInfo.remainDay));

                update_member_info();


//                sendDataToActivity("OK");


            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void checkSubscription(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count=0;



                while (true){
                    Log.d(TAG, "checkSubscription: "+count+" "+mMemberInfo);
                    if (mDeviceInfo == null || mDeviceInfo.firebaseUid == null){
                        getDeviceInfo();
                    } else if (mMemberInfo == null || mMemberInfo.remainDay <= 0){
                        getMemberStatus(mDeviceInfo.firebaseUid);
                    }/*else {
                        break;
                    }*/

                    if(mMemberInfo!=null){

                        Log.d(TAG, "checkSubscription: "+mMemberInfo.remainDay + ", " +mMemberInfo.isAllowToPlay());
                        if(mMemberInfo.isAllowToPlay()) {
                            Log.d(TAG, "checkSubscription: Exit");
                            sendDataToActivity("OK");
                            break;
                        }
                    }

                    count++;

//                    if (count>1){
//                        sendDataToActivity("OK");
//                      break;
//                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void update_member_info(){
        if(getView()==null) return;

        if(mMemberInfo!=null) {
            expireDate = mMemberInfo.expiredDate;
        }
        if(mDeviceInfo!=null){

            memberId=mDeviceInfo.memberId;
            email=mDeviceInfo.email;

            TextView member_info = getView().findViewById(R.id.member_info_text);
            String member_info_text ="";
            if(memberId!=null && !memberId.isEmpty()){
                member_info_text = "Member Id: "+memberId+"\n";
            }
            if(email!=null && !email.isEmpty()){
                member_info_text += "Email: "+email+"\n";
            }
            if(expireDate!=null && !expireDate.isEmpty()){
                member_info_text += "Expired Date: "+expireDate;
            }

            if(member_info!=null && !member_info_text.isEmpty())
                member_info.setText(member_info_text);

        }


    }




    private void fill_qr_data(View view){
        ImageView qr_img = view.findViewById(R.id.register_qr_image);
        generateQRCode(shortLink, qr_img);


        TextView qr_link = view.findViewById(R.id.register_qr_url);
        if(qr_link!=null)
            qr_link.setText(shortLink!=null?shortLink:"");

        TextView deviceIdTv=view.findViewById(R.id.register_device_id_text);
        if(deviceIdTv!=null) {
            deviceIdTv.setText(GetDeviceIdText()!=null?GetDeviceIdText():"");
        }

        Button back_bt = view.findViewById(R.id.register_back_button);
        if(back_bt!=null) {
            back_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Back button clicked");
                    requireActivity().finish();
                }
            });
        }

        TextView member_info = view.findViewById(R.id.member_info_text);
        String member_info_text ="";
        if(memberId!=null && !memberId.isEmpty()){
            member_info_text = "Member Id: "+memberId+"\n";
        }
        if(email!=null && !email.isEmpty()){
            member_info_text += "Email: "+email;
        }

        if(member_info!=null && !member_info_text.isEmpty())
            member_info.setText(member_info_text);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");



    }

}

