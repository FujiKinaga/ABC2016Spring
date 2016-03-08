package jagsc.org.abc.info.domain.usecase;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jagsc.org.abc.info.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/28.
 */
public interface TimeTableUseCase {
    interface TimeTableUseCaseCallback {
        void onSuccess(SparseArrayCompat<ArrayList<Conference>> timeTableList);

        void onFailire();
    }

    void execute(TimeTableUseCaseCallback callback);

    void setCallback(TimeTableUseCaseCallback callback);

    void removeCallback();
}
