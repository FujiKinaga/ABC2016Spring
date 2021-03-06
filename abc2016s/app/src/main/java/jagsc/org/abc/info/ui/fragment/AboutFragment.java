package jagsc.org.abc.info.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.consts.Const;
import jagsc.org.abc.info.ui.view.Fab;

public class AboutFragment extends Fragment {

    @Bind(R.id.join)
    TextView mJoin;

    private Fab mFab;
    private Fab mReviewFab;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);
        mReviewFab = (Fab) getActivity().findViewById(R.id.review_fab);

        if (mFab.isShown()) {
            mFab.hide();
        }
        mReviewFab.hide();
        mReviewFab.setVisibility(View.VISIBLE);
        mReviewFab.show();

        mReviewFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Const.URL_REVIEW);
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });

        MovementMethod movementMethod = LinkMovementMethod.getInstance();
        mJoin.setMovementMethod(movementMethod);

        String html = "入部歓迎（入部は<a href=\"http://student.android-group.jp/\">こちら</a>から）";
        CharSequence charSequence = Html.fromHtml(html);
        mJoin.setAutoLinkMask(0);
        mJoin.setText(charSequence);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mReviewFab.hide();
        mReviewFab.setVisibility(View.GONE);
        ButterKnife.unbind(this);
    }
}
