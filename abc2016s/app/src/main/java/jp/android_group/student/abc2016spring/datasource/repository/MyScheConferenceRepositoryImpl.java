package jp.android_group.student.abc2016spring.datasource.repository;

import android.util.Xml;

import com.loopj.android.http.TextHttpResponseHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.android_group.student.abc2016spring.App;
import jp.android_group.student.abc2016spring.consts.Const;
import jp.android_group.student.abc2016spring.datasource.action.FavoriteAction;
import jp.android_group.student.abc2016spring.datasource.memory.XmlCache;
import jp.android_group.student.abc2016spring.domain.model.Conference;
import jp.android_group.student.abc2016spring.domain.model.Speaker;
import jp.android_group.student.abc2016spring.domain.repository.MyScheConferenceRepository;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class MyScheConferenceRepositoryImpl implements MyScheConferenceRepository {
    private static MyScheConferenceRepositoryImpl sConferenceRepository;

    public MyScheConferenceRepositoryImpl() {
    }

    public static MyScheConferenceRepositoryImpl getRepository() {
        if (sConferenceRepository == null) {
            sConferenceRepository = new MyScheConferenceRepositoryImpl();
        }
        return sConferenceRepository;
    }

    @Override
    public void loadMyScheConference(final MyScheConferenceRepositoryCallback cb) {
        String cacheResponse = XmlCache.getConferenceResponse(App.getInstance().getApplicationContext());
        if (cacheResponse.isEmpty()) {
            Const.sSyncHttpClient.get(Const.URL_CONFERENCE, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    cb.onFailure();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    parseConference(responseString, cb);
                    XmlCache.putConferenceResponse(App.getInstance().getApplicationContext(), responseString);
                }
            });
        } else {
            parseConference(cacheResponse, cb);
        }
    }

    private void parseConference(String response, MyScheConferenceRepositoryCallback cb) {
        try {
            XmlPullParser mParser = Xml.newPullParser();
            mParser.setInput(new StringReader(response));
            List<Conference> list = new ArrayList<>();
            int eventType = mParser.getEventType();
            Conference conference = new Conference();
            Speaker speaker = new Speaker();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = mParser.getName();
                    if (tagName.equals(Conference.TAG_LECTURE)) {
                        conference = new Conference();
                        speaker = new Speaker();
                    } else if (tagName.equals(Conference.TAG_ID)) {
                        conference.setId(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_TITLE)) {
                        conference.setTitle(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_ABSTRACT)) {
                        conference.setAbst(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_LEC_ORDER_NUM)) {
                        conference.setLec_order_num(Integer.parseInt(mParser.nextText()));
                    } else if (tagName.equals(Conference.TAG_ROOM_ORDER_NUM)) {
                        conference.setRoom_order_num(Integer.parseInt(mParser.nextText()));
                    } else if (tagName.equals(Conference.TAG_URL)) {
                        conference.setUrl(mParser.nextText());
                    } else if (tagName.equals(Speaker.TAG_SPEAKER_ID)) {
                        speaker.setSpeaker_id(mParser.nextText());
                    } else if (tagName.equals(Speaker.TAG_NAME)) {
                        speaker.setName(mParser.nextText());
                    } else if (tagName.equals(Speaker.TAG_PROFILE)) {
                        speaker.setProfile(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_START_TIME)) {
                        conference.setStartTime(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_END_TIME)) {
                        conference.setEndTime(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_ROOM)) {
                        conference.setRoom_id(Integer.parseInt(mParser.getAttributeValue(null, "id")));
                        conference.setRoom(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_CATEGORY)) {
                        conference.setCategory_id(Integer.parseInt(mParser.getAttributeValue(null, "id")));
                        conference.setCategory(mParser.nextText());
                    } else if (tagName.equals(Conference.TAG_TIME_FRAME)) {
                        conference.setTime_frame(Integer.parseInt(mParser.nextText()));
                        conference.setSpeaker(speaker);
                        if (FavoriteAction.isFavoriteConference(App.getInstance().getApplicationContext(), conference.getId())) {
                            list.add(conference);
                        }
                    } else {
                        mParser.next();
                    }
                }
                eventType = mParser.next();
            }
            cb.onSuccess(list);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            cb.onFailure();
        }
    }
}
