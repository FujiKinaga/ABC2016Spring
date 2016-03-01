package jp.android_group.student.abc2016winter.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Conference implements Parcelable {

    public static final String TAG_LECTURE = "lecture",
            TAG_ID = "lec_id",
            TAG_TITLE = "title",
            TAG_ABSTRACT = "abstract",
            TAG_LEC_ORDER_NUM = "lec_order_num",
            TAG_ROOM_ORDER_NUM = "room_order_num",
            TAG_URL = "url",
            TAG_SPEAKERS = "speakers",
            TAG_SPEAKER = "speaker",
            TAG_START_TIME = "start_time",
            TAG_END_TIME = "end_time",
            TAG_ROOM_ID = "room_id",
            TAG_ROOM = "room",
            TAG_CATEGORY_ID = "category_id",
            TAG_CATEGORY = "category",
            TAG_TIME_FRAME = "time_frame";

    private String id, title, abst, url, startTime, endTime, room, category, lec_order_num, room_order_num, room_id, category_id, time_frame;
    private Speaker speaker;

    public Conference() {
    }

    protected Conference(Parcel in) {
        id = in.readString();
        title = in.readString();
        abst = in.readString();
        url = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        room = in.readString();
        category = in.readString();
        lec_order_num = in.readString();
        room_order_num = in.readString();
        room_id = in.readString();
        category_id = in.readString();
        time_frame = in.readString();
        speaker = in.readParcelable(Speaker.class.getClassLoader());
    }


    public static final Creator<Conference> CREATOR = new Creator<Conference>() {
        @Override
        public Conference createFromParcel(Parcel in) {
            return new Conference(in);
        }

        @Override
        public Conference[] newArray(int size) {
            return new Conference[size];
        }
    };

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

    public String getLec_order_num() {
        return lec_order_num;
    }

    public void setLec_order_num(String lec_order_num) {
        this.lec_order_num = lec_order_num;
    }

    public String getRoom_order_num() {
        return room_order_num;
    }

    public void setRoom_order_num(String room_order_num) {
        this.room_order_num = room_order_num;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTime_frame() {
        return time_frame;
    }

    public void setTime_frame(String time_frame) {
        this.time_frame = time_frame;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(abst);
        dest.writeString(url);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(room);
        dest.writeString(category);
        dest.writeString(lec_order_num);
        dest.writeString(room_order_num);
        dest.writeString(room_id);
        dest.writeString(category_id);
        dest.writeString(time_frame);
        dest.writeParcelable(speaker, flags);
    }
}