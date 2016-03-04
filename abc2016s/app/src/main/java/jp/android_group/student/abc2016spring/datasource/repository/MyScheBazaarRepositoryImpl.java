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
import jp.android_group.student.abc2016spring.domain.model.Bazaar;
import jp.android_group.student.abc2016spring.domain.repository.MyScheBazaarRepository;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class MyScheBazaarRepositoryImpl implements MyScheBazaarRepository {
    private static MyScheBazaarRepositoryImpl sBazaarRepository;

    public MyScheBazaarRepositoryImpl() {
    }

    public static MyScheBazaarRepositoryImpl getRepository() {
        if (sBazaarRepository == null) {
            sBazaarRepository = new MyScheBazaarRepositoryImpl();
        }
        return sBazaarRepository;
    }

    @Override
    public void loadMyScheBazaar(final MyScheBazaarRepositoryCallback cb) {
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

    private void parseBazaar(String response, MyScheBazaarRepositoryCallback cb) {
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
                        if (FavoriteAction.isFavoriteBazaar(App.getInstance().getApplicationContext(), bazaar.getId())) {
                            list.add(bazaar);
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
