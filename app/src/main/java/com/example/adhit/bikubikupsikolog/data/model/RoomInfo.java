package com.example.adhit.bikubikupsikolog.data.model;

/**
 * Created by adhit on 29/01/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomInfo {

    @SerializedName("is_removed")
    @Expose
    private Boolean isRemoved;
    @SerializedName("last_comment_id")
    @Expose
    private Integer lastCommentId;
    @SerializedName("last_comment_id_str")
    @Expose
    private String lastCommentIdStr;
    @SerializedName("last_comment_message")
    @Expose
    private String lastCommentMessage;
    @SerializedName("last_comment_timestamp")
    @Expose
    private String lastCommentTimestamp;
    @SerializedName("room_avatar_url")
    @Expose
    private String roomAvatarUrl;
    @SerializedName("room_id")
    @Expose
    private Integer roomId;
    @SerializedName("room_id_str")
    @Expose
    private String roomIdStr;
    @SerializedName("room_name")
    @Expose
    private String roomName;
    @SerializedName("room_type")
    @Expose
    private String roomType;
    @SerializedName("unread_count")
    @Expose
    private Integer unreadCount;

    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Integer getLastCommentId() {
        return lastCommentId;
    }

    public void setLastCommentId(Integer lastCommentId) {
        this.lastCommentId = lastCommentId;
    }

    public String getLastCommentIdStr() {
        return lastCommentIdStr;
    }

    public void setLastCommentIdStr(String lastCommentIdStr) {
        this.lastCommentIdStr = lastCommentIdStr;
    }

    public String getLastCommentMessage() {
        return lastCommentMessage;
    }

    public void setLastCommentMessage(String lastCommentMessage) {
        this.lastCommentMessage = lastCommentMessage;
    }

    public String getLastCommentTimestamp() {
        return lastCommentTimestamp;
    }

    public void setLastCommentTimestamp(String lastCommentTimestamp) {
        this.lastCommentTimestamp = lastCommentTimestamp;
    }

    public String getRoomAvatarUrl() {
        return roomAvatarUrl;
    }

    public void setRoomAvatarUrl(String roomAvatarUrl) {
        this.roomAvatarUrl = roomAvatarUrl;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomIdStr() {
        return roomIdStr;
    }

    public void setRoomIdStr(String roomIdStr) {
        this.roomIdStr = roomIdStr;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }
}
