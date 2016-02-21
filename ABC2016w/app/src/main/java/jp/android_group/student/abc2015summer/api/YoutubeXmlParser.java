package jp.android_group.student.abc2015summer.api;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2015summer.R;

public class YoutubeXmlParser {
    private XmlPullParser mParser;

    public YoutubeXmlParser(Context context) {
        mParser = context.getResources().getXml(R.xml.youtube);
    }

    public List<Youtube> loadYoutube() {
        List<Youtube> list = new ArrayList<>();
        try {
            int eventType = mParser.getEventType();
            Youtube youtube = new Youtube();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = mParser.getName();
                    if (tagName.equals(Youtube.TAG_YOUTUBE)) {
                        youtube = new Youtube();
                    } else if (tagName.equals(Session.TAG_ID)) {
                        youtube.setId(Integer.parseInt(mParser.nextText()));
                    } else if (tagName.equals(Session.TAG_TITLE)) {
                        youtube.setTitle(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_GROUP)) {
                        youtube.setGroup(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_CONTENT)) {
                        youtube.setContent(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_URL)) {
                        youtube.setUrl(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_START_TIME)) {
                        youtube.setStartTime(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_END_TIME)) {
                        youtube.setEndTime(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_ROOM_ID)) {
                        youtube.setRoomId(mParser.nextText());
                    } else if (tagName.equals(Session.TAG_ROOM)) {
                        youtube.setRoom(mParser.nextText());
                        list.add(youtube);
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
