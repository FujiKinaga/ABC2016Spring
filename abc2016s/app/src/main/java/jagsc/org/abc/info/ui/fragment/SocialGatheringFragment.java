package jagsc.org.abc.info.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import jagsc.org.abc.info.R;
import jagsc.org.abc.info.consts.Const;
import jagsc.org.abc.info.ui.view.Fab;

/**
 * Created by kinagafuji on 16/03/08.
 */
public class SocialGatheringFragment extends Fragment {

    @Bind(R.id.web_view)
    WebView mWebView;

    private Fab mFab;
    private Fab mNavFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_social_gathering, container, false);
        ButterKnife.bind(this, rootView);

        mFab = (Fab) getActivity().findViewById(R.id.fab);
        mNavFab = (Fab) getActivity().findViewById(R.id.nav_fab);

        if (mFab.isShown()) {
            mFab.hide();
        }
        mNavFab.hide();
        mNavFab.setVisibility(View.VISIBLE);
        mNavFab.show();

        mNavFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Const.URI_NAVIGATION);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(Const.URL_SOCIALGATHERING);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNavFab.hide();
        mNavFab.setVisibility(View.GONE);
        ButterKnife.unbind(this);
    }
}
