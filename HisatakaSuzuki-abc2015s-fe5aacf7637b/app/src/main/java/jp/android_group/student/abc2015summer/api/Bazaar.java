package jp.android_group.student.abc2015summer.api;

import android.os.Parcel;
import android.os.Parcelable;

public class Bazaar implements Parcelable {

    public static final String TAG_BAZAAR = "bazaar",
                        TAG_ID = "bazaar_id",
                        TAG_GROUP = "group",
                        TAG_TITLE = "title",
                        TAG_CONTENT = "content",
                        TAG_LOCATION = "location",
                        TAG_COLOR = "color";

    private int id;
    private String group, title, content, location, color;

    public Bazaar(String title, String group, String content, String location, String color) {
        this.title = title;
        this.group = group;
        this.content = content;
        this.location = location;
        this.color = color;
    }

    public Bazaar(Parcel in) {
        id = in.readInt();
        title = in.readString();
        group = in.readString();
        content = in.readString();
        location = in.readString();
    }

    public Bazaar() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(group);
        out.writeString(content);
        out.writeString(location);
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
