package jagsc.org.abc.info.domain.repository;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jagsc.org.abc.info.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/28.
 */
public interface TimeTableRepository {
    void loadTimeTable(TimeTableRepositoryCallback cb);

    interface TimeTableRepositoryCallback {
        void onSuccess(SparseArrayCompat<ArrayList<Conference>> timeTableList);

        void onFailure();
    }
}
