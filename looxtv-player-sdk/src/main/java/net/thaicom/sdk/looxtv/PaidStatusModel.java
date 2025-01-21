package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaidStatusModel {

    @SerializedName("nam_group")
    @Expose
    private String namGroup;
    @SerializedName("ref_code")
    @Expose
    private String refCode;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("expired")
    @Expose
    private Boolean expired;
    @SerializedName("remain_days")
    @Expose
    private Integer remainDays;
    @SerializedName("id_firebase_uid")
    @Expose
    private String idFirebaseUid;
    @SerializedName("name_package")
    @Expose
    private String name_package;
    @SerializedName("expire_date")
    @Expose
    private String expireDate;
    @SerializedName("color_code")
    @Expose
    private String colorCode;
    @SerializedName("quality_group_mobile")
    @Expose
    private String qualityGroupMobile;
    @SerializedName("quality_group_atv")
    @Expose
    private String qualityGroupAtv;
    @SerializedName("next_charge_date")
    @Expose
    private String nextChargeDate;
    @SerializedName("max_concurrent")
    @Expose
    private Integer maxConcurrent;

    public String getNamGroup() {
        return namGroup;
    }

    public void setNamGroup(String namGroup) {
        this.namGroup = namGroup;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Integer getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(Integer remainDays) {
        this.remainDays = remainDays;
    }

    public String getIdFirebaseUid() {
        return idFirebaseUid;
    }

    public void setIdFirebaseUid(String idFirebaseUid) {
        this.idFirebaseUid = idFirebaseUid;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getQualityGroupMobile() {
        return qualityGroupMobile;
    }

    public void setQualityGroupMobile(String qualityGroupMobile) {
        this.qualityGroupMobile = qualityGroupMobile;
    }

    public String getQualityGroupAtv() {
        return qualityGroupAtv;
    }

    public void setQualityGroupAtv(String qualityGroupAtv) {
        this.qualityGroupAtv = qualityGroupAtv;
    }

    public String getNextChargeDate() {
        return nextChargeDate;
    }

    public void setNextChargeDate(String nextChargeDate) {
        this.nextChargeDate = nextChargeDate;
    }

    public void setMaxConcurrent(Integer maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

    public Integer getMaxConcurrent() {
        return maxConcurrent;
    }

    public String getName_package() {
        return name_package;
    }

    public void setName_package(String name_package) {
        this.name_package = name_package;
    }
}
