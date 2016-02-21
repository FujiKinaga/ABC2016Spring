package jp.android_group.student.abc2015summer.api;


public class TimeTable {

    public static final String TAG_LECTURE = "lecture",
            TAG_SESSION = "session",
            TAG_ID_LEC = "lec_id",
            TAG_ID_SESSION = "session_id",
            TAG_TITLE = "title",
            TAG_ABSTRACT = "abstract",
            TAG_SPEAKER = "speaker",
            TAG_PROFILE = "profile",
            TAG_START_TIME = "start_time",
            TAG_END_TIME = "end_time",
            TAG_LOCATION_ID = "location_id",
            TAG_LOCATION = "location",
            TAG_GROUP = "group",
            TAG_CONTENT = "content",
            TAG_URL = "url",
            TAG_ROOM_ID = "room_id",
            TAG_ROOM = "room",
            TAG_TYPE = "type";

    private String type, id, title, abst, speaker, profile, startTime, endTime, locationId, location, group, content, url, roomId, room;

    public TimeTable() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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