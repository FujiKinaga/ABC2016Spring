package jagsc.org.abc.info.datasource.repository;

import android.support.v4.util.SparseArrayCompat;
import android.util.Xml;

import com.loopj.android.http.TextHttpResponseHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jagsc.org.abc.info.App;
import jagsc.org.abc.info.consts.Const;
import jagsc.org.abc.info.datasource.memory.XmlCache;
import jagsc.org.abc.info.domain.model.Conference;
import jagsc.org.abc.info.domain.model.Speaker;
import jagsc.org.abc.info.domain.repository.TimeTableRepository;

/**
 * Created by kinagafuji on 16/02/28.
 */
public class TimeTableRepositoryImpl implements TimeTableRepository {
    private static TimeTableRepositoryImpl sTimeTableRepository;

    private static final int[] time_frames = {11, 12, 13, 14, 15, 16};

    public TimeTableRepositoryImpl() {
    }

    public static TimeTableRepositoryImpl getRepository() {
        if (sTimeTableRepository == null) {
            sTimeTableRepository = new TimeTableRepositoryImpl();
        }
        return sTimeTableRepository;
    }

    @Override
    public void loadTimeTable(final TimeTableRepositoryCallback cb) {
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

    private void parseConference(String response, TimeTableRepositoryCallback cb) {
        try {
            XmlPullParser mParser = Xml.newPullParser();
            mParser.setInput(new StringReader(response));
            SparseArrayCompat<ArrayList<Conference>> sparseList = new SparseArrayCompat<>();
            for (int i : time_frames) {
                sparseList.put(i, new ArrayList<Conference>());
            }
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
                        if (conference.getTime_frame() > 11) {
                            conference.setTime_frame(conference.getTime_frame() - 1);
                        }
                        conference.setSpeaker(speaker);
                        sparseList.get(conference.getTime_frame()).add(conference);
                    } else {
                        mParser.next();
                    }
                }
                eventType = mParser.next();
            }
            cb.onSuccess(sparseList);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            cb.onFailure();
        }
    }
}
