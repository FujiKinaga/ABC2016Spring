package jp.android_group.student.abc2016winter.datasource.api;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.android_group.student.abc2016winter.consts.Const;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class VoteAction {

    private static final String VOTE_URL = "https://script.google.com/macros/s/AKfycbzCoMf77sBSPA3Hi7UStkf_373cHZzkOoWcK0_qB8UtHR-2U-E/exec";

    //主催用
    public static void voteStart(Context context) {
        Const.sAsyncHttpClient.post(context, VOTE_URL, new RequestParams("action", "start"), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("成功ログ", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("失敗ログ", errorResponse.toString());
            }
        });
    }

    public static void voteEnd(Context context) {
        Const.sAsyncHttpClient.get(context, VOTE_URL, new RequestParams("action", "end"), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("成功ログ", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("失敗ログ", errorResponse.toString());
            }
        });
    }

    //参加者用
    public static void votePush(Context context) {
        Const.sAsyncHttpClient.post(context, VOTE_URL, new RequestParams("action", "vote"), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("成功ログ", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("失敗ログ", errorResponse.toString());
            }
        });
    }
}
