package jp.android_group.student.abc2016spring.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kinagafuji on 16/02/23.
 */
public class Live implements Parcelable {

    public static final String TAG_ITEM = "item",
            TAG_ID = "room_id",
            TAG_PLACE = "room_place",
            TAG_NAME = "room_name",
            TAG_STATUS_ID = "roomstatus_id",
            TAG_STATUS_TEXT = "roomstatus_text",
            TAG_UPDATE_TIME = "update_time";

    private String place, name, status_text, update_time, id, status_id;

    public Live(String id, String place, String name, String status_id, String status_text, String update_time) {
        this.id = id;
        this.place = place;
        this.name = name;
        this.status_id = status_id;
        this.status_text = status_text;
        this.update_time = update_time;
    }

    public Live(Parcel in) {
        id = in.readString();
        place = in.readString();
        name = in.readString();
        status_id = in.readString();
        status_text = in.readString();
        update_time = in.readString();
    }

    public Live() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(place);
        out.writeString(name);
        out.writeString(status_id);
        out.writeString(status_text);
        out.writeString(update_time);
    }

    public static final Parcelable.Creator<Bazaar> CREATOR
            = new Parcelable.Creator<Bazaar>() {
        public Bazaar createFromParcel(Parcel in) {
            return new Bazaar(in);
        }

        public Bazaar[] newArray(int size) {
            return new Bazaar[size];
        }
    };
}
