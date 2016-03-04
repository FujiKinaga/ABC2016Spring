package jp.android_group.student.abc2016spring.datasource.action;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kinagafuji on 16/02/28.
 */
public class FavoriteAction {

    //conferenceはT?-??、bazaarは数字だけなのでかぶることはないが、いちよ汎用性を上げるため、処理を分けておく。
    private static final String PREFERENCE_CONFERENCE = "ABC2016W_Conference";
    private static final String PREFERENCE_BAZAAR = "ABC2016W_Bazaar";

    public static void favoriteConference(Context context, String id) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_CONFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(id, true);
        editor.apply();
    }

    public static void unFavoriteConference(Context context, String id) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_CONFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(id, false);
        editor.apply();
    }

    public static boolean isFavoriteConference(Context context, String id) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_CONFERENCE, Context.MODE_PRIVATE);
        return pref.getBoolean(id, false);
    }

    public static void favoriteBazaar(Context context, String id) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_BAZAAR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(id, true);
        editor.apply();
    }

    public static void unFavoriteBazaar(Context context, String id) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_BAZAAR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(id, false);
        editor.apply();
    }

    public static boolean isFavoriteBazaar(Context context, String id) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_BAZAAR, Context.MODE_PRIVATE);
        return pref.getBoolean(id, false);
    }

}
