package jp.android_group.student.abc2016winter.datasource.memory;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class XmlCache {

    private static final String PREFERENCE_CACHE = "ABC2016W_Cache";

    public static void putConferenceResponse(Context context, String response) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("conference", response);
        editor.apply();
    }

    public static void putBazaarResponse(Context context, String response) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_CACHE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("bazaar", response);
        editor.apply();
    }

    public static String getConferenceResponse(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_CACHE, Context.MODE_PRIVATE);
        return pref.getString("conference", "");
    }

    public static String getBazaarResponse(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_CACHE, Context.MODE_PRIVATE);
        return pref.getString("bazaar", "");
    }
}
