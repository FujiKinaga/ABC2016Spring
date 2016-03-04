package jp.android_group.student.abc2016spring.domain.usecase;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jp.android_group.student.abc2016spring.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/23.
 */
public interface ConferenceUseCase {
    interface ConferenceUseCaseCallback {
        void onSuccess(SparseArrayCompat<ArrayList<Conference>> conferenceList);

        void onFailire();
    }

    void execute(ConferenceUseCaseCallback callback);

    void setCallback(ConferenceUseCaseCallback callback);

    void removeCallback();
}
