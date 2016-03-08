package jagsc.org.abc.info.domain.repository;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jagsc.org.abc.info.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/23.
 */
public interface ConferenceRepository {
    void loadConference(ConferenceRepositoryCallback cb);

    interface ConferenceRepositoryCallback {
        void onSuccess(SparseArrayCompat<ArrayList<Conference>> conferenceList);

        void onFailure();
    }
}
