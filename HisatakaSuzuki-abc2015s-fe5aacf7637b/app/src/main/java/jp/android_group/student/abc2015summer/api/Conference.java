package jp.android_group.student.abc2015summer.api;

public class Conference {

    public static final String TAG_LECTURE = "lecture",
            TAG_ID = "lec_id",
            TAG_TITLE = "title",
            TAG_ABSTRACT = "abstract",
            TAG_SPEAKER = "speaker",
            TAG_PROFILE = "profile",
            TAG_START_TIME = "start_time",
            TAG_END_TIME = "end_time",
            TAG_LOCATION_ID = "location_id",
            TAG_LOCATION = "location";

    private String id, title, abst, speaker, profile, startTime, endTime, locationId, location;

    public Conference() {
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
}