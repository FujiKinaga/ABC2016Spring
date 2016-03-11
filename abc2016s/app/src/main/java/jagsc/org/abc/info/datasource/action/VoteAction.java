package jagsc.org.abc.info.datasource.action;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import jagsc.org.abc.info.consts.Const;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class VoteAction {

    //主催用
    public static void voteStart(Context context, JsonHttpResponseHandler handler) {
        Const.sAsyncHttpClient.post(context, Const.URL_VOTE, new RequestParams("action", "start"), handler);
    }

    public static void voteEnd(Context context, JsonHttpResponseHandler handler) {
        Const.sAsyncHttpClient.get(context, Const.URL_VOTE, new RequestParams("action", "end"), handler);
    }

    //参加者用
    public static void votePush(Context context, JsonHttpResponseHandler handler) {
        Const.sAsyncHttpClient.get(context, Const.URL_VOTE, new RequestParams("action", "vote"), handler);
    }
}
