package jagsc.org.abc.info.datasource.repository;

import android.util.Xml;

import com.loopj.android.http.TextHttpResponseHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import jagsc.org.abc.info.consts.Const;
import jagsc.org.abc.info.domain.model.Live;
import jagsc.org.abc.info.domain.repository.LiveRepository;

/**
 * Created by kinagafuji on 16/02/23.
 */
public class LiveRepositoryImpl implements LiveRepository {
    private static LiveRepositoryImpl sLiveRepository;

    public LiveRepositoryImpl() {
    }

    public static LiveRepositoryImpl getRepository() {
        if (sLiveRepository == null) {
            sLiveRepository = new LiveRepositoryImpl();
        }
        return sLiveRepository;
    }

    @Override
    public void loadLive(final LiveRepositoryCallback cb) {
        Const.sSyncHttpClient.get(Const.URL_LIVE, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                cb.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    XmlPullParser mParser = Xml.newPullParser();
                    mParser.setInput(new StringReader(responseString));

                    List<Live> list = new ArrayList<>();
                    int eventType = mParser.getEventType();
                    Live live = new Live();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            String tagName = mParser.getName();
                            if (tagName.equals(Live.TAG_ITEM)) {
                                live = new Live();
                            } else if (tagName.equals(Live.TAG_ID)) {
                                live.setId(mParser.nextText());
                            } else if (tagName.equals(Live.TAG_PLACE)) {
                                live.setPlace(mParser.nextText());
                            } else if (tagName.equals(Live.TAG_NAME)) {
                                live.setName(mParser.nextText());
                            } else if (tagName.equals(Live.TAG_STATUS_ID)) {
                                live.setStatus_id(mParser.nextText());
                            } else if (tagName.equals(Live.TAG_STATUS_TEXT)) {
                                live.setStatus_text(mParser.nextText());
                            } else if (tagName.equals(Live.TAG_UPDATE_TIME)) {
                                live.setUpdate_time(mParser.nextText());
                                list.add(live);
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
        });
    }
}
