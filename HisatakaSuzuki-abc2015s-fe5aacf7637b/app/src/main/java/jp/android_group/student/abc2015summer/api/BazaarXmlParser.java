package jp.android_group.student.abc2015summer.api;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2015summer.R;

public class BazaarXmlParser {

    private XmlPullParser mParser;

    public BazaarXmlParser(Context context) {
        mParser = context.getResources().getXml(R.xml.bazaar);
    }

    public List<Bazaar> loadBazaar() {
        List<Bazaar> list = new ArrayList<>();
        try {
            int eventType = mParser.getEventType();
            Bazaar bazaar = new Bazaar();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = mParser.getName();
                    if (tagName.equals(Bazaar.TAG_BAZAAR)) {
                        bazaar = new Bazaar();
                    } else if (tagName.equals(Bazaar.TAG_ID)) {
                        bazaar.setId(Integer.parseInt(mParser.nextText()));
                    } else if (tagName.equals(Bazaar.TAG_TITLE)) {
                        bazaar.setTitle(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_GROUP)) {
                        bazaar.setGroup(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_CONTENT)) {
                        bazaar.setContent(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_LOCATION)) {
                        bazaar.setLocation(mParser.nextText());
                        list.add(bazaar);
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

    public List<Bazaar> loadBazaarOn4F() {
        List<Bazaar> list = new ArrayList<>();
        try {
            int eventType = mParser.getEventType();
            Bazaar bazaar = new Bazaar();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = mParser.getName();
                    if (tagName.equals(Bazaar.TAG_BAZAAR)) {
                        bazaar = new Bazaar();
                    } else if (tagName.equals(Bazaar.TAG_ID)) {
                        bazaar.setId(Integer.parseInt(mParser.nextText()));
                    } else if (tagName.equals(Bazaar.TAG_TITLE)) {
                        bazaar.setTitle(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_GROUP)) {
                        bazaar.setGroup(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_CONTENT)) {
                        bazaar.setContent(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_LOCATION)) {
                        bazaar.setLocation(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_COLOR)) {
                        bazaar.setColor(mParser.nextText());
                        if (bazaar.getLocation().startsWith("No")) {
                            list.add(bazaar);
                        }
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

    public List<Bazaar> loadBazaarOn1F() {
        List<Bazaar> list = new ArrayList<>();
        try {
            int eventType = mParser.getEventType();
            Bazaar bazaar = new Bazaar();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = mParser.getName();
                    if (tagName.equals(Bazaar.TAG_BAZAAR)) {
                        bazaar = new Bazaar();
                    } else if (tagName.equals(Bazaar.TAG_ID)) {
                        bazaar.setId(Integer.parseInt(mParser.nextText()));
                    } else if (tagName.equals(Bazaar.TAG_TITLE)) {
                        bazaar.setTitle(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_GROUP)) {
                        bazaar.setGroup(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_CONTENT)) {
                        bazaar.setContent(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_LOCATION)) {
                        bazaar.setLocation(mParser.nextText());
                    } else if (tagName.equals(Bazaar.TAG_COLOR)) {
                        bazaar.setColor(mParser.nextText());
                        if (bazaar.getLocation().startsWith("1F")) {
                            list.add(bazaar);
                        }
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
                String name = mParser.getName();
                Log.d("mikami EventType is", String.valueOf(eventType) + name);
                if (eventType == XmlPullParser.START_TAG && name.equals(xmlTag)) {
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
