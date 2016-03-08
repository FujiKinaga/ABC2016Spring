package jagsc.org.abc.info.domain.repository;

import java.util.List;

import jagsc.org.abc.info.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/29.
 */
public interface MyScheConferenceRepository {
    void loadMyScheConference(MyScheConferenceRepositoryCallback cb);

    interface MyScheConferenceRepositoryCallback {
        void onSuccess(List<Conference> conferenceList);

        void onFailure();
    }
}
