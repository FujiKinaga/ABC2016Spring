package jp.android_group.student.abc2016winter.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.android_group.student.abc2016winter.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfficialFragment extends Fragment {

    @Bind(R.id.web_view)
    WebView mWebView;

    private static final String URL_HP = "http://abc.android-group.jp/2014w/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_official, container, false);
        ButterKnife.bind(this, rootView);

        mWebView = (WebView) rootView.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(URL_HP);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
