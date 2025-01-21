package net.thaicom.sdk.looxtv;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by TC45005362 on 2/19/2018.
 */

public class GetInfoByDeviceIDResponse {
    @Expose @SerializedName("msgCode") private Integer msgCode;
    @Expose @SerializedName("msgText") private String msgText;
    @Expose @SerializedName("msgDesc") private MsgDesc msgDesc;

    /**
     * @return The msgCode
     */
    public Integer getMsgCode() {
        return msgCode;
    }

    /**
     * @param msgCode The msgCode
     */
    public void setMsgCode(Integer msgCode) {
        this.msgCode = msgCode;
    }

    /**
     * @return The msgText
     */


    public String getMsgText() {
        return msgText;
    }

    /**
     * @param msgText The msgText
     */
    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }




    /**
     * @return The msgDesc
     */
    public MsgDesc getMsgDesc() {
        return msgDesc;
    }

    /**
     * @param msgDesc The msgDesc
     */
    public void setMsgDesc(MsgDesc msgDesc) {
        this.msgDesc = msgDesc;
    }



    public class MsgDesc {
        @SerializedName("member_id") @Expose private String memberId;
        @SerializedName("firebaseUid") @Expose private String firebaseUid;
        @SerializedName("first_name") @Expose private String firstName;
        @SerializedName("last_name") @Expose private String lastName;
        @SerializedName("dsp_name") @Expose private String dspName;
        @SerializedName("photo_uri") @Expose private String photoUri;
        @SerializedName("email") @Expose private String email;
        @SerializedName("flg_gender") @Expose private String flgGender;
        @SerializedName("dte_birth") @Expose private String dteBirth;
        @SerializedName("idcard") @Expose private String idcard;
        @SerializedName("phone_no") @Expose private String phoneNo;
        @SerializedName("zip") @Expose private String zip;
        @SerializedName("district") @Expose private String district;
        @SerializedName("city") @Expose private String city;
        @SerializedName("province") @Expose private String province;
        @SerializedName("contact_address") @Expose private String contactAddress;
        @SerializedName("date_create") @Expose private String dateCreate;


        public String getMemberId() {
            return memberId;
        }
        public void setMemberId(String channelId) {
            this.memberId = memberId;
        }


        public String getFirebaseUid() {
            return firebaseUid;
        }
        public void setFirebaseUid(String firebaseUid) {
            this.firebaseUid = firebaseUid;
        }

        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName(){
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDspName() {
            return dspName;
        }
        public void setDspName(String dspName) {
            this.dspName = dspName;
        }

        public String getPhotoUri() {
            return photoUri;
        }
        public void setPhotoUri(String photoUri) {
            this.photoUri = photoUri;
        }


        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getFlgGender() {
            return flgGender;
        }
        public void setFlgGender(String flgGender) {
            this.flgGender = flgGender;
        }

        public String getDteBirth() {
            return dteBirth;
        }
        public void setDteBirth(String dteBirth) {
            this.dteBirth = dteBirth;
        }

        public String getIdcard() {
            return idcard;
        }
        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getPhoneNo() {
            return phoneNo;
        }
        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getZip() {
            return zip;
        }
        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getDistrict() {
            return district;
        }
        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCity() {
            return city;
        }
        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }
        public void setProvince(String province) {
            this.province = province;
        }

        public String getContactAddress() {
            return contactAddress;
        }
        public void setContactAddress(String contactAddress) {
            this.contactAddress = contactAddress;
        }

        public String getDateCreate() {
            return dateCreate;
        }
        public void setDateCreate(String dateCreate) {
            this.dateCreate = dateCreate;
        }
    }

}

