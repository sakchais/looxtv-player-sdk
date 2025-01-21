package net.thaicom.sdk.looxtv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.concurrent.TimeUnit;

public class QrActivity extends FragmentActivity implements AccountFragment.OnFragmentInteractionListener {

    private CountDownTimer countDownTimer;
    private final String TAG = "QrActivity";
    private int finish_round = 1;
    private int tick_round = 0;

    private String firebaseUid;
    private String qualityGroupAtv;

    private int retry;

    private Handler memberHandler;
    private Handler signoutHandler;

    private Button back_bt;

    TextView deviceIdTv;
    private String dID;

    private static boolean isRunning = false;

    public static boolean isRunning() {
        return isRunning;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true; // Mark as running
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false; // Mark as not running
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        String shortLink = getIntent().getStringExtra("shortLink");
        String uUid = getIntent().getStringExtra("uUid");
        String firebaseUid = getIntent().getStringExtra("firebaseUid");
        String memberId = getIntent().getStringExtra("memberId");
        String email = getIntent().getStringExtra("email");

        final FragmentTransaction fragmentTransaction = getFragmentTransaction(shortLink, uUid, firebaseUid, memberId, email);
        fragmentTransaction.commit();







//        ImageView qr_img = findViewById(R.id.register_qr_image);
//        generateQRCode(shortLink, qr_img);
//        TextView qr_link = findViewById(R.id.register_qr_url);
//        if(qr_link!=null)
//            qr_link.setText(shortLink!=null?shortLink:"");
//
//        dID  = getIntent().getStringExtra("uUid");
//
//        deviceIdTv=findViewById(R.id.register_device_id_text);
//        if(deviceIdTv!=null) {
//            String deviceText=dID;
//            deviceIdTv.setText(deviceText!=null?deviceText:"");
//        }
//
//        back_bt = (Button) findViewById(R.id.register_back_button);
//        back_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "Back button clicked");
//                onBackPressed();
//            }
//        });
//
//
//        dID += (" (Android TV: v" + Build.VERSION.RELEASE + ", TV SDK: v" + "1.0" + ")");
//        if(deviceIdTv!=null) deviceIdTv.setText(dID);

    }

    @NonNull
    private FragmentTransaction getFragmentTransaction(String shortLink, String uUid, String firebaseUid, String memberId, String email) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString("shortLink", shortLink);
        args.putString("uUid", uUid);
        args.putString("firebaseUid", firebaseUid);
        args.putString("memberId", memberId);
        args.putString("email", email);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace or add the fragment in the container
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        return fragmentTransaction;
    }

    private void checkMemberOnTick(CountDownTimer cdTimer) {

        retry = 0;
        firebaseUid = "";
        qualityGroupAtv = "";

        if (memberHandler == null)
            memberHandler = new Handler(Looper.getMainLooper());
        else
            memberHandler.removeCallbacksAndMessages(null);

        memberHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                retry++;
                /*firebaseUid = getUtils().readStringGlobalSetting("firebaseUid");
                Log.d(TAG, String.format("MEMBER_DEBUG: firebaseUid=%s", firebaseUid));
                if (firebaseUid.isEmpty()) {
                    Log.d(TAG, String.format("MEMBER_DEBUG: Not link to mobile, need to subscribe"));
                    Utils.DeviceID(getApplicationContext(), FirebaseManager.getInstance().getUUIDString(getFirebaseManager().getDeviceIDExtension()));
                } else {
                    Utils.PaidStatus(getApplicationContext(), firebaseUid);
                    qualityGroupAtv = getUtils().readStringGlobalSetting("qualityGroupAtv");
                    Log.d(TAG, String.format("MEMBER_DEBUG: qualityGroupAtv=%s", qualityGroupAtv));
                    if (qualityGroupAtv.isEmpty() || qualityGroupAtv.equals("null")) {
                        Utils.PaidStatus(getApplicationContext(), firebaseUid);
                        memberHandler.postDelayed(this, TimeUnit.SECONDS.toMillis(1));
                    } else {
                        boolean expired = getUtils().tryBooleanParse(getUtils().readStringGlobalSetting("expired"));
                        int remain_days = getUtils().tryIntParse(getUtils().readStringGlobalSetting("remain_day"));
//                        String expired = "true";
                        Log.d(TAG, String.format("MEMBER_DEBUG: expired=%s, remain_days=%d", expired+"", remain_days));

                        if (remain_days<=0) {

                            Log.d(TAG, String.format("MEMBER_DEBUG: Memer expired, need to resubscribe"));
                            getUtils().writeBooleanGlobalSetting("member_expired", true);
                            getUtils().writeBooleanGlobalSetting("allow_to_view", false);


                        } else {

                            boolean nearlyExpireCheck=getUtils().readBooleanGlobalSetting("nearlyExpireCheck");
                            String nextChargeDate=getUtils().readStringGlobalSetting("nextChargeDate");

                            if(!nearlyExpireCheck || (nearlyExpireCheck && remain_days > 3)) {
                                Log.d(TAG, String.format("MEMBER_DEBUG: Member status is ok1 "+retry));

                                getUtils().writeBooleanGlobalSetting("allow_to_view", true);
                                getUtils().writeBooleanGlobalSetting("member_expired", false);

//                                if(retry==2) {
//                                    PaidStatusModel[] values = PowerPreference.getDefaultFile().getObject("paidStatus", PaidStatusModel[].class);
//                                    PaidStatusModel values2[] = {values[0], values[0]};
//                                    PowerPreference.getDefaultFile().put("paidStatus", values2);
//
//                                    Log.d(TAG, String.format("MEMBER_DEBUG: "+ PowerPreference.getDefaultFile().getObject("paidStatus", PaidStatusModel[].class)));
//                                }


                                LooxTVPackages newPackages=readPackages();
                                newPackages.showPackages();
                                if(currentPackages.isPackagesChanged(newPackages)){
                                    if(cdTimer!=null)
                                        cdTimer.cancel();

                                    getUtils().writeBooleanGlobalSetting("need_update_package_info", false);
                                    finish();
                                }

//                                showMemberStatus(MSG_SUCCESS);
//                                cdTimer.cancel();
//                                getMainContext().getMainAppActivity().mMainAppFragment.updateAccountHeaderItem();
//                                restartActivity();
                            }
                            else if(nextChargeDate!=null && !nextChargeDate.isEmpty() && !nextChargeDate.equals("null")) {
                                Log.d(TAG, String.format("MEMBER_DEBUG: Member status is ok2"));

                                getUtils().writeBooleanGlobalSetting("allow_to_view", true);
                                getUtils().writeBooleanGlobalSetting("member_expired", false);
                                LooxTVPackages newPackages=readPackages();

                                newPackages.showPackages();
                                if(currentPackages.isPackagesChanged(newPackages)){
                                    if(cdTimer!=null)
                                        cdTimer.cancel();
                                    getUtils().writeBooleanGlobalSetting("need_update_package_info", false);
                                    finish();
                                }

//                                showMemberStatus(MSG_SUCCESS);
//                                cdTimer.cancel();
//                                getMainContext().getMainAppActivity().mMainAppFragment.updateAccountHeaderItem();
//                                restartActivity();
                            }

                        }

                    }
                }*/

            }

        }, TimeUnit.MILLISECONDS.toMillis(1));


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

    private void setCountDownTimer(long duration, long tick){

        Log.d(TAG, String.format("setCountDownTimer: finish_round=%d", finish_round));

        countDownTimer = new CountDownTimer(duration, tick) {
            @Override
            public void onTick(long millisUntilFinished) {

                checkMemberOnTick(countDownTimer);
//                deviceIdTv.setText(dID+" ("+tick_round+")");

                tick_round++;


            }

            @Override
            public void onFinish() {
                switch (finish_round){
                    case 1:
                        setCountDownTimer(60000, 10000);
                        break;
                    case 2:
                        setCountDownTimer(60000,15000);
                        break;
                    case 3:
                        setCountDownTimer(60000,20000);
                        break;
                    case 4:
                        setCountDownTimer(60000,30000);
                        break;
                    default:
//                        if(finish_round>=5)
//                            qr_button.setVisibility(View.VISIBLE);
//                        qr_login_3.setVisibility(View.GONE);
//                        qr_login_4.setVisibility(View.GONE);
                        break;
                }
                finish_round++;
            }
        };
        countDownTimer.start();

    }

    @Override
    public void onFragmentInteraction(String data) {
        Log.d(TAG, "onFragmentInteraction: " + data);
        Intent intent = new Intent();
        intent.putExtra("data", data);
        setResult(RESULT_OK, intent);
        finish();
    }
}