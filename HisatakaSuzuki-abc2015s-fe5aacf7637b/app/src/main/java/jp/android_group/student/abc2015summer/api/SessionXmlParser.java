package jp.android_group.student.abc2015summer.api;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2015summer.R;

public class SessionXmlParser {

    private XmlPullParser mParser;

    public SessionXmlParser(Context context) {
        mParser = context.getResources().getXml(R.xml.session);
    }

    public List<Session> loadSession() {
        List<Session> list = new ArrayList<>();
        try {
            int eventType = mParser.getEventType();
            Session session = new Session();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = mParser.getName();
                    if (tagName.equals(Session.TAG_SESSION)) {
                        session = new Session();
                    } else if (tagName.equals(Session.TAG_ID)) {
                        session.setId(Integer.parseInt(mParser.nextText()));
                    } else if (tagName.equals(Session.TAG_TITLE)) {
                        session.setTitle(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_GROUP)) {
                        session.setGroup(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_CONTENT)) {
                        session.setContent(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_URL)) {
                        session.setUrl(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_START_TIME)) {
                        session.setStartTime(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_END_TIME)) {
                        session.setEndTime(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_ROOM_ID)) {
                        session.setRoomId(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_ROOM)) {
                        session.setRoom(mParser.nextText());
                        list.add(session);
                    }
                    else {
                        mParser.next();
                    }
                }
                eventType = mParser.next();

            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
