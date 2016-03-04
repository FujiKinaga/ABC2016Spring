package jp.android_group.student.abc2016spring.domain.usecase;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jp.android_group.student.abc2016spring.domain.model.Conference;

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
