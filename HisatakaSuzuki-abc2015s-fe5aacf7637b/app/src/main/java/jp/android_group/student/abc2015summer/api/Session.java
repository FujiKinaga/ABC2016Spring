package jp.android_group.student.abc2015summer.api;

public class Session {

    public static final String TAG_SESSION = "session",
            TAG_ID = "session_id",
            TAG_GROUP = "group",
            TAG_TITLE = "title",
            TAG_CONTENT = "content",
            TAG_URL = "url",
            TAG_START_TIME = "start_time",
            TAG_END_TIME = "end_time",
            TAG_ROOM_ID = "room_id",
            TAG_ROOM = "room";

    private int id;
    private String group, title, content, url , startTime, endTime, roomId, room;

    public Session() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
