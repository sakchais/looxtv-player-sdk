package net.thaicom.sdk.looxtv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedModel {

    @SerializedName("id_post")
    @Expose
    private Integer idPost;
    @SerializedName("id_epg")
    @Expose
    private Integer idEpg;
    @SerializedName("card_type")
    @Expose
    private String cardType;
    @SerializedName("json_data")
    @Expose
    private Object jsonData;
    @SerializedName("epg_image")
    @Expose
    private String epgImage;
    @SerializedName("nam_epg_th")
    @Expose
    private String namEpgTh;
    @SerializedName("desc_epg_th")
    @Expose
    private String descEpgTh;
    @SerializedName("epg_start")
    @Expose
    private String epgStart;
    @SerializedName("epg_stop")
    @Expose
    private String epgStop;
    @SerializedName("epg_video_type")
    @Expose
    private Object epgVideoType;
    @SerializedName("epg_video_url")
    @Expose
    private Object epgVideoUrl;
    @SerializedName("url_live")
    @Expose
    private String urlLive;
    @SerializedName("live_ep_flag")
    @Expose
    private Integer liveEpFlag;
    @SerializedName("like_flag")
    @Expose
    private Integer likeFlag;
    @SerializedName("id_program")
    @Expose
    private Integer idProgram;
    @SerializedName("id_channel")
    @Expose
    private Integer idChannel = -1;
    @SerializedName("channel_image")
    @Expose
    private String channelImage;
    @SerializedName("channel_image_big")
    @Expose
    private String channel_image_big;
    @SerializedName("nam_channel_th")
    @Expose
    private String namChannelTh;
    @SerializedName("nam_channel_en")
    @Expose
    private String namChannelEn;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("owner_name")
    @Expose
    private Object ownerName;
    @SerializedName("owner_img")
    @Expose
    private Object ownerImg;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("community")
    @Expose
    private Object community;
    @SerializedName("ondemand")
    @Expose
    private Integer ondemand;
    @SerializedName("timeshift")
    @Expose
    private Integer timeshift;
    @SerializedName("comment_off")
    @Expose
    private Integer commentOff;
    @SerializedName("program_type")
    @Expose
    private Object program_type;
    @SerializedName("channel_pin")
    @Expose
    private Integer channel_pin;
    @SerializedName("channel_order")
    @Expose
    private Integer channel_order;


    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public Integer getIdEpg() {
        return idEpg;
    }

    public void setIdEpg(Integer idEpg) {
        this.idEpg = idEpg;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Object getJsonData() {
        return jsonData;
    }

    public void setJsonData(Object jsonData) {
        this.jsonData = jsonData;
    }

    public String getEpgImage() {
        return epgImage;
    }

    public void setEpgImage(String epgImage) {
        this.epgImage = epgImage;
    }

    public String getNamEpgTh() {
        return namEpgTh;
    }

    public void setNamEpgTh(String namEpgTh) {
        this.namEpgTh = namEpgTh;
    }

    public String getDescEpgTh() {
        return descEpgTh;
    }

    public void setDescEpgTh(String descEpgTh) {
        this.descEpgTh = descEpgTh;
    }

    public String getEpgStart() {
        return epgStart;
    }

    public void setEpgStart(String epgStart) {
        this.epgStart = epgStart;
    }

    public String getEpgStop() {
        return epgStop;
    }

    public void setEpgStop(String epgStop) {
        this.epgStop = epgStop;
    }

    public Object getEpgVideoType() {
        return epgVideoType;
    }

    public void setEpgVideoType(Object epgVideoType) {
        this.epgVideoType = epgVideoType;
    }

    public Object getEpgVideoUrl() {
        return epgVideoUrl;
    }

    public void setEpgVideoUrl(Object epgVideoUrl) {
        this.epgVideoUrl = epgVideoUrl;
    }

    public String getUrlLive() {
        return urlLive;
    }

    public void setUrlLive(String urlLive) {
        this.urlLive = urlLive;
    }

    public Integer getLiveEpFlag() {
        return liveEpFlag;
    }

    public void setLiveEpFlag(Integer liveEpFlag) {
        this.liveEpFlag = liveEpFlag;
    }

    public Integer getLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(Integer likeFlag) {
        this.likeFlag = likeFlag;
    }

    public Integer getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
    }

    public Integer getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(Integer idChannel) {
        this.idChannel = idChannel;
    }

    public String getChannelImage() {
        return channelImage;
    }
    public String getChannelImageBig(){
        return channel_image_big;
    }

    public void setChannelImage(String channelImage) {
        this.channelImage = channelImage;
    }

    public String getNamChannelTh() {
        return namChannelTh;
    }

    public String getNamChannelEn() { return namChannelEn; }

    public void setNamChannelTh(String namChannelTh) {
        this.namChannelTh = namChannelTh;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Object getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(Object ownerName) {
        this.ownerName = ownerName;
    }

    public Object getOwnerImg() {
        return ownerImg;
    }

    public void setOwnerImg(Object ownerImg) {
        this.ownerImg = ownerImg;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getCommunity() {
        return community;
    }

    public void setCommunity(Object community) {
        this.community = community;
    }

    public Integer getOndemand() {
        return ondemand;
    }

    public void setOndemand(Integer ondemand) {
        this.ondemand = ondemand;
    }

    public Integer getTimeshift() {
        return timeshift;
    }

    public void setTimeshift(Integer timeshift) {
        this.timeshift = timeshift;
    }

    public Integer getCommentOff() {
        return commentOff;
    }

    public void setCommentOff(Integer commentOff) {
        this.commentOff = commentOff;
    }

    public Object getProgramType() {
        return program_type;
    }

    public void setProgram_type(Object program_type) {
        this.program_type = program_type;
    }

    public Integer getChannelPin() {
        return channel_pin;
    }

    public void setChannelPin(Integer channel_pin) {
        this.channel_pin = channel_pin;
    }

    public Integer getChannelOrder() {
        return channel_order;
    }

    public void setChannelOrder(Integer channel_order) {
        this.channel_order = channel_order;
    }
}

