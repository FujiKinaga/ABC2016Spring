package jagsc.org.abc.info.ui.fragment;


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
import jagsc.org.abc.info.ui.view.Fab;

public class AboutFragment extends Fragment {

    @Bind(R.id.join)
    TextView mJoin;

    private Fab mFab;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);
        if (mFab.isShown()) {
            mFab.hide();
        }

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
        ButterKnife.unbind(this);
    }
}
