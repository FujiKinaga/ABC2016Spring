package jp.android_group.student.abc2016spring.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kinagafuji on 16/02/23.
 */
public class Speaker implements Parcelable {

    public static final String TAG_SPEAKER_ID = "speaker_id",
            TAG_NAME = "name",
            TAG_PROFILE = "profile";

    private List<String> name = new ArrayList<>();
    private List<String> profile = new ArrayList<>();
    private List<String> speaker_id = new ArrayList<>();

    public Speaker() {

    }

    protected Speaker(Parcel in) {
        name = in.createStringArrayList();
        profile = in.createStringArrayList();
        speaker_id = in.createStringArrayList();

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

    public List<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.add(name);
    }

    public List<String> getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile.add(profile);
    }

    public List<String> getSpeaker_id() {
        return speaker_id;
    }

    public void setSpeaker_id(String speaker_id) {
        this.speaker_id.add(speaker_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(name);
        dest.writeStringList(profile);
        dest.writeStringList(speaker_id);
    }
}
