package jp.android_group.student.abc2016winter.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.android_group.student.abc2016winter.R;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class VoteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vote, container, false);

        return rootView;
    }
}
