package jagsc.org.abc.info.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jagsc.org.abc.info.R;
import jagsc.org.abc.info.ui.view.Fab;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class VoteFragment extends Fragment {

    private Fab mFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vote, container, false);

        mFab = (Fab) getActivity().findViewById(R.id.fab);
        if (mFab.isShown()) {
            mFab.hide();
        }

        return rootView;
    }
}
