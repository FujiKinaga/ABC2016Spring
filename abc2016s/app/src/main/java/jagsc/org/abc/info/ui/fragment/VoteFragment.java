package jagsc.org.abc.info.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andexert.library.RippleView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.datasource.action.VoteAction;
import jagsc.org.abc.info.ui.view.Fab;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class VoteFragment extends Fragment {

    @Bind(R.id.vote_ripple)
    RippleView mVoteRipple;
    @Bind(R.id.vote_view)
    LinearLayout mVoteView;

    private Fab mFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vote, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);
        if (mFab.isShown()) {
            mFab.hide();
        }

        mVoteRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(final RippleView rippleView) {
                rippleView.setClickable(false);
                VoteAction.votePush(getActivity(), new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        rippleView.setClickable(true);
                        try {
                            String res = response.getString("response");
                            if (res.equals("success")) {
                                Snackbar.make(mVoteView, "正常に投票されました！", Snackbar.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        rippleView.setClickable(true);
                        Snackbar.make(mVoteView, "投票に失敗しました。", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
