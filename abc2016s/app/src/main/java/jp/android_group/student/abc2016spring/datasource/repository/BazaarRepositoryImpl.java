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
import jp.android_group.student.abc2016spring.datasource.memory.XmlCache;
import jp.android_group.student.abc2016spring.domain.model.Bazaar;
import jp.android_group.student.abc2016spring.domain.repository.BazaarRepository;

/**
 * Created by kinagafuji on 16/02/23.
 */
public class BazaarRepositoryImpl implements BazaarRepository {
    private static BazaarRepositoryImpl sBazaarRepository;

    public BazaarRepositoryImpl() {
    }

    public static BazaarRepositoryImpl getRepository() {
        if (sBazaarRepository == null) {
            sBazaarRepository = new BazaarRepositoryImpl();
        }
        return sBazaarRepository;
    }

    @Override
    public void loadBazaar(final BazaarRepositoryCallback cb) {
        String cacheResponse = XmlCache.getBazaarResponse(App.getInstance().getApplicationContext());
        if (cacheResponse.isEmpty()) {
            Const.sSyncHttpClient.get(Const.URL_BAZZAAR, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    cb.onFailure();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    parseBazaar(responseString, cb);
                    XmlCache.putBazaarResponse(App.getInstance().getApplicationContext(), responseString);
                }
            });
        } else {
            parseBazaar(cacheResponse, cb);
        }
    }

    private void parseBazaar(String response, BazaarRepositoryCallback cb) {
        try {
            XmlPullParser mParser = Xml.newPullParser();
            mParser.setInput(new StringReader(response));

            List<Bazaar> list = new ArrayList<>();
            int eventType = mParser.getEventType();
            Bazaar bazaar = new Bazaar();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = mParser.getName();
                    if (tagName.equals(Bazaar.TAG_BAZAAR)) {
                        bazaar = new Bazaar();
                    } else if (tagName.equals(Bazaar.TAG_ID)) {
                        bazaar.setId(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_GROUP)) {
                        bazaar.setGroup(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_TITLE)) {
                        bazaar.setTitle(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_CONTENT)) {
                        bazaar.setContent(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_LOCATION)) {
                        bazaar.setLocation(mParser.nextText());
                        list.add(bazaar);
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
