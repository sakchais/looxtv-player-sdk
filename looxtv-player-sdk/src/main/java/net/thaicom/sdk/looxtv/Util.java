package net.thaicom.sdk.looxtv;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Util {

    private final String TAG="Util";

    public String getCurrentDateTimeString(){
        String currentDateandTime ="0000-00-00 00:00:00";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            currentDateandTime = sdf.format(new Date());
        }catch (Exception e){

        }
        return currentDateandTime;
    }

    public long getCurrenTimeStamp(){
        long currentTm =0L;

        try {
            currentTm=new Date().getTime()/1000;
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentTm;
    }

    public Integer tryIntParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Long tryLongParse(String text) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            return Long.valueOf(0);
        }
    }

    public Double tryDoubleParse(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return Double.valueOf(0);
        }
    }

    public Boolean tryBooleanParse(String text) {
        if (text != null){
            if ( (text.compareToIgnoreCase("1") == 0)
                    || (text.compareToIgnoreCase("true") == 0) ){
                return true;
            }
        }
        return false;
    }

    public String tryStringParse(String text) {
        if ( text != null){
            return text;
        }
        return "";
    }

    public boolean tryStringEmpty(String text) {
        if ( text != null){
            return text.isEmpty();
        }
        return true;
    }


}
