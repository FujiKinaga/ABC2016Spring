package jp.android_group.student.abc2015summer.api;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2015summer.R;

public class ConferenceXmlParser {

    private XmlPullParser mParser;

    public ConferenceXmlParser(Context context) {
        mParser = context.getResources().getXml(R.xml.conference);
    }

    public List<Conference> loadConference() {
        List<Conference> list = new ArrayList<>();
        try {
            int eventType = mParser.getEventType();
            Conference conference = new Conference();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = mParser.getName();
                    if (tagName.equals(Conference.TAG_LECTURE)) {
                        conference = new Conference();
                    } else if (tagName.equals(Conference.TAG_ID)) {
                        conference.setId(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_TITLE)) {
                        conference.setTitle(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_ABSTRACT)) {
                        conference.setAbst(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_SPEAKER)) {
                        conference.setSpeaker(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_PROFILE)) {
                        conference.setProfile(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_START_TIME)) {
                        conference.setStartTime(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_END_TIME)) {
                        conference.setEndTime(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_LOCATION_ID)) {
                        conference.setLocationId(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_LOCATION)) {
                        conference.setLocation(mParser.nextText());
                        list.add(conference);
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

    // 適当にメソッド名つけちゃったけど、仮です、もちろん笑
    public List<String> loadSomething(String xmlTag) {
        List<String> list = new ArrayList<>();
        try {
            // eventTypeがDOCUMENT_ENDを示すまで次を読み込む
            for (int eventType = mParser.getEventType(); eventType != XmlPullParser.END_DOCUMENT; eventType = mParser.next()) {
                // 読み込んでいる箇所が開始タグで、かつそのタグ名が求めているタグ名であるならば
                if (eventType == XmlPullParser.START_TAG && mParser.getName().equals(xmlTag)) {
                    // listに格納
                    list.add(mParser.nextText());
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
