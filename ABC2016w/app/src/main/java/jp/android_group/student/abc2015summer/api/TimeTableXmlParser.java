package jp.android_group.student.abc2015summer.api;


import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2015summer.R;


public class TimeTableXmlParser {

    private XmlPullParser mParser_conference;
    private XmlPullParser mParser_session;

    public TimeTableXmlParser(Context context) {
        mParser_conference = context.getResources().getXml(R.xml.conference);
        mParser_session = context.getResources().getXml(R.xml.session);
    }

    public List<TimeTable> loadTimeTable() {
        List<TimeTable> list = new ArrayList<>();
        try {

            TimeTable timeTable = new TimeTable();

            int eventType_conference = mParser_conference.getEventType();
            while (eventType_conference != XmlPullParser.END_DOCUMENT) {
                if (eventType_conference == XmlPullParser.START_TAG) {
                    String tagName = mParser_conference.getName();
                    if (tagName.equals(TimeTable.TAG_LECTURE)) {
                        timeTable = new TimeTable();
                    } else if (tagName.equals(TimeTable.TAG_ID_LEC)) {
                        timeTable.setType("conference");
                        timeTable.setId(mParser_conference.nextText());
                    } else if (tagName.equals(TimeTable.TAG_TITLE)) {
                        timeTable.setTitle(mParser_conference.nextText());
                    } else if (tagName.equals(TimeTable.TAG_ABSTRACT)) {
                        timeTable.setAbst(mParser_conference.nextText());
                    } else if (tagName.equals(TimeTable.TAG_SPEAKER)) {
                        timeTable.setSpeaker(mParser_conference.nextText());
                    } else if (tagName.equals(TimeTable.TAG_PROFILE)) {
                        timeTable.setProfile(mParser_conference.nextText());
                    } else if (tagName.equals(TimeTable.TAG_START_TIME)) {
                        timeTable.setStartTime(mParser_conference.nextText());
                    } else if (tagName.equals(TimeTable.TAG_END_TIME)) {
                        timeTable.setEndTime(mParser_conference.nextText());
                    } else if (tagName.equals(TimeTable.TAG_LOCATION_ID)) {
                        timeTable.setLocationId(mParser_conference.nextText());
                    } else if (tagName.equals(TimeTable.TAG_LOCATION)) {
                        timeTable.setLocation(mParser_conference.nextText());
                        list.add(timeTable);
                    }else {
                        mParser_conference.next();
                    }
                }
                eventType_conference = mParser_conference.next();
            }

            int eventType_session = mParser_session.getEventType();
            while (eventType_session != XmlPullParser.END_DOCUMENT) {
                if (eventType_session == XmlPullParser.START_TAG) {
                    String tagName = mParser_session.getName();
                    if (tagName.equals(TimeTable.TAG_SESSION)) {
                        timeTable = new TimeTable();
                    } else if (tagName.equals(TimeTable.TAG_ID_SESSION)) {
                        timeTable.setType("session");
                        timeTable.setId(mParser_session.nextText());
                    } else if (tagName.equals(TimeTable.TAG_TITLE)) {
                        timeTable.setTitle(mParser_session.nextText());
                    } else if (tagName.equals(TimeTable.TAG_GROUP)) {
                        timeTable.setGroup(mParser_session.nextText());
                    } else if (tagName.equals(TimeTable.TAG_CONTENT)) {
                        timeTable.setContent(mParser_session.nextText());
                    } else if (tagName.equals(TimeTable.TAG_URL)) {
                        timeTable.setUrl(mParser_session.nextText());
                    } else if (tagName.equals(TimeTable.TAG_START_TIME)) {
                        timeTable.setStartTime(mParser_session.nextText());
                    } else if (tagName.equals(TimeTable.TAG_END_TIME)) {
                        timeTable.setEndTime(mParser_session.nextText());
                    } else if (tagName.equals(TimeTable.TAG_ROOM_ID)) {
                        timeTable.setRoomId(mParser_session.nextText());
                    } else if (tagName.equals(TimeTable.TAG_ROOM)) {
                        timeTable.setRoom(mParser_session.nextText());
                        list.add(timeTable);
                    }else {
                        mParser_session.next();
                    }
                }
                eventType_session = mParser_session.next();
            }




        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
