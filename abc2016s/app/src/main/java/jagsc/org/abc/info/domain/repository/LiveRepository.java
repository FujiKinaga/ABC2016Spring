package jagsc.org.abc.info.domain.repository;

import java.util.List;

import jagsc.org.abc.info.domain.model.Live;

/**
 * Created by kinagafuji on 16/02/23.
 */
public interface LiveRepository {
    void loadLive(LiveRepositoryCallback cb);

    interface LiveRepositoryCallback {
        void onSuccess(List<Live> liveList);

        void onFailure();
    }
}
