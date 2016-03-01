package jp.android_group.student.abc2016winter.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kinagafuji on 16/02/23.
 */
public class Speaker implements Parcelable {

    public static final String TAG_SPEAKER_ID = "speaker_id",
            TAG_NAME = "name",
            TAG_PROFILE = "profile";

    private String name, profile, speaker_id;

    public Speaker() {

    }

    protected Speaker(Parcel in) {
        name = in.readString();
        profile = in.readString();
        speaker_id = in.readString();
    }

    public static final Creator<Speaker> CREATOR = new Creator<Speaker>() {
        @Override
        public Speaker createFromParcel(Parcel in) {
            return new Speaker(in);
        }

        @Override
        public Speaker[] newArray(int size) {
            return new Speaker[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSpeaker_id() {
        return speaker_id;
    }

    public void setSpeaker_id(String speaker_id) {
        this.speaker_id = speaker_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(profile);
        dest.writeString(speaker_id);
    }
}
