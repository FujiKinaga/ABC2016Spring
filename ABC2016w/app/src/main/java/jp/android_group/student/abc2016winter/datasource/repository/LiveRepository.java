package jp.android_group.student.abc2016winter.datasource.repository;

import java.util.List;

import jp.android_group.student.abc2016winter.domain.model.Live;

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
